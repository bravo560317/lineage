package com.lineage.server.model.Instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.datatables.DeNameTable;
import com.lineage.server.datatables.DeTitleTable;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_NPCPack_De2;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.templates.DeName;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.templates.L1Npc;
import com.lineage.config.ConfigDrop;

/**
 * 掛網專用假人
 */
public class L1DeInstance2 extends L1NpcInstance {

    private static final long serialVersionUID = 1L;
    private static final Log _log = LogFactory.getLog(L1DeInstance2.class);
    private static final Random _random = new Random();
    private static final ArrayList<DeName> _denameList = new ArrayList<>();

    // 假人屬性
    private DeName _de_name;
    private int _classId;
    
    /** 假人可使用的輔助魔法技能特效ID列表 */
    private int[] _myBuffList;
    
    // 假人行為旗標
    /** 是否已完成變身 */
    private boolean _isPolyDone = false;
    /** 是否為會移動的假人 */
    private boolean _isMovingDummy = false;
    /** 是否能召喚魔法娃娃 */
    private boolean _canSummonDoll = false;
    /** 是否能使用輔助魔法 */
    private boolean _canUseBuff = false;
    /** 是否能喝藥水 */
    private boolean _canUsePotion = false;
    /** 是否能變身 */
    private long _lastMoveTime = 0L;
    /** 上次喝藥水時間 */
    private long _lastPotionTime = 0L;
    /** 上次施放輔助魔法時間 */
    private long _lastBuffTime = 0L;
    /** 移動間隔 (10秒) */
    private final long MOVE_INTERVAL = 10000;

    /**
     * 職業外觀ID列表
     * 格式: { {性別(0:男), 王族, 騎士, 妖精, 法師, 黑妖, 龍騎, 幻術}, {性別(1:女), ...} }
     */
    private static final int[][] _class_list = {
            { 0, 61, 138, 734, 2786, 6658, 6671 }, // 男
            { 1, 48, 37, 1186, 2796, 6661, 6650 }  // 女
    };

    public L1DeInstance2(L1Npc template) {
        super(template);
    }

    /**
     * 當被玩家感知時觸發
     */
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        perceivedFrom.addKnownObject(this);
        perceivedFrom.sendPackets(new S_NPCPack_De2(this));
        if (this.getBraveSpeed() == 1) {
            perceivedFrom.sendPackets(new S_SkillBrave(this.getId(), 1, 600000));
        }
    }

    /**
     * NPC的AI處理
     */
    @Override
    public void onNpcAI() {
        if (this.isAiRunning()) return;
        this.setActived(true);
        this.startAI();
    }

    /**
     * AI搜尋目標 (此處被用來處理假人的週期性行為)
     */
    @Override
    public void searchTarget() {
        if (!this.isAiRunning()) this.startAI();
        long currentTime = System.currentTimeMillis();

        // 2. 移動邏輯
        if (this._isMovingDummy && (currentTime - _lastMoveTime) > MOVE_INTERVAL && _random.nextInt(100) < 30) {
            int steps = _random.nextInt(3) + 1; // 隨機走 1-3 步
            int dir = _random.nextInt(8);       // 隨機一個方向
            this.setHeading(dir); // 先轉向

            for (int i = 0; i < steps; i++) {
                // 每一步都檢查前方是否有障礙物
                if (this.getMove().checkObject(this.getHeading()) != -1) {
                    this.getMove().setDirectionMove(this.getHeading()); // 移動一步
                } else {
                    break; // 如果有障礙物，就停止這次的移動
                }
            }
            _lastMoveTime = currentTime;
        }

         // 3. 喝藥邏輯
        if (this._canUsePotion && (currentTime - _lastPotionTime > (20000 + _random.nextInt(30000)))) {
            // 從藥水特效列表中隨機挑選兩種播放
            List<Integer> potionEffects = new ArrayList<>(Arrays.asList(191, 189, 751,190,194,197));
            java.util.Collections.shuffle(potionEffects); // 打亂列表順序
            this.broadcastPacketAll(new S_SkillSound(this.getId(), potionEffects.get(0)));
            this.broadcastPacketAll(new S_SkillSound(this.getId(), potionEffects.get(1)));
            _lastPotionTime = currentTime;
        }

        // 4. 召喚娃娃行為：如果可召喚且尚未召喚，則機率性地召喚一個隨機娃娃
        if (this._canSummonDoll && this.getDolls().isEmpty() && _random.nextInt(100) < 3) {
            int[] allowedDolls = { 70646,70647,70648,70649,70650,70651,70652,70653,70654,70655,70656,70657,70658,70659,70660,70681,70682,70683,70684,70685,70686,70687,70688,70689,70690,70691,70692,70693,70694,70695,70696,70697,70698,70699,70700,70712,70713,70714,70715,70716,70717,70718,70719 };
            int dollItemId = allowedDolls[_random.nextInt(allowedDolls.length)];
            L1Npc template = NpcTable.get().getTemplate(71082);
            L1Doll type = DollPowerTable.get().get_type(dollItemId);
            if (template != null && type != null) {
                new L1DollInstance(template, this, type).setNpcMoveSpeed();
            }
        }

        // 5. 施放輔助魔法行為：隨機間隔時間播放施法動作與 1~4 個技能特效
        // if (this._canUseBuff && this._myBuffList != null && this._myBuffList.length > 0 && (currentTime - _lastBuffTime > (15000 + _random.nextInt(20000)))) {
        //    try {
                // 隨機決定這次要放幾個技能特效 (1~2個)
        //        int buffCount = _random.nextInt(2) + 1;
        //        for (int i = 0; i < buffCount; i++) {
                    // 隨機挑選一個技能特效
        //            int randomGfxId = _myBuffList[_random.nextInt(_myBuffList.length)];
                    // 將施法動作(19)和技能特效合併在一個封包裡發送，或分開但有間隔
         //           this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 19)); // 播放施法動作
        //            this.broadcastPacketAll(new S_SkillSound(this.getId(), randomGfxId)); // 播放技能特效
                }
        //        _lastBuffTime = currentTime;
        //    } catch (Exception e) {
        //        _log.error("假人施放輔助魔法時發生錯誤", e);
        //    }
    //    }
    // }

    /**
     * 初始化並啟動假人
     */
    public void startNpc() {
        // 隨機決定假人的行為模式
        this._isMovingDummy = (_random.nextInt(100) < 30); //移動能力
        this._canSummonDoll = (_random.nextInt(100) < 20); //召喚魔法娃娃
        this._canUseBuff = (_random.nextInt(100) < 30);  //輔助魔法
        this._canUsePotion = (_random.nextInt(100) < 30); //是否會喝藥水。
        
        // 從名稱列表中隨機選取一個名字
        DeName[] des = DeNameTable.get().getDeNameList();
        this._de_name = des[_random.nextInt(des.length)];
        _denameList.add(this._de_name);
        this.setNameId(this._de_name.get_name());
        this.setName(this._de_name.get_name());

        // 套用樣板的隨機正義值
        applyTemplateRandomLawful();

        // 根據名稱資訊設定職業ID
        int classid = _class_list[this._de_name.get_sex()][this._de_name.get_type()];
        this.setClassId(classid);
        
        // 根據職業類型設定對應的 Buff 特效ID清單
        switch (this._de_name.get_type()) {
            case 0: _myBuffList = new int[]{ 221 }; break; // 王族
            case 1: _myBuffList = new int[]{ 221 }; break;   // 騎士
            case 2: _myBuffList = new int[]{ 2176, 2170, 748, 750, 6342, 830, 221, 2165 }; break;  // 妖精
            case 3: _myBuffList = new int[]{ 2176, 2170, 748, 750, 6342, 830, 221, 2165 }; break;  // 法師
            default: _myBuffList = new int[]{ 221 }; break;       // 其他
        }

        // 設定外觀 (30% 機率變身)
        this.setGfxId(classid);
        if (_random.nextInt(100) < 60) {
            int[] polyList = { 20398,20401,20526,20512,20556,20560,20687,20727,20661,20617,20642,20653,20666,20692,20709,20657,20480,20484,20712,20707,
20725,20552,20593,20774,20890,20633,20720,20255,20305,20265,20392,20239,20243,20547,20574,20589,20671,20508,20395,19307,20169,20155,18978,20281,20523,20445,
20415,20333,20790,20812,20532,20451,20423,20339,20795,20817,20565,20469,20489,20381,20800,20822,20571,20475,20495,20385,20785,
12233,20235,20674,20658,18897,20372,20247,20259,20404,20389,20602,20618,20417,20342,20505,20778,20541,20586,20893,20912,20908,20905,20949,20915,
20918,20936,20159,20172,20839,20696,18894,18900,19163,17987,18903,18939
};
            this.setTempCharGfx(polyList[_random.nextInt(polyList.length)]);
        } else {
            this.setTempCharGfx(classid);
        }

        
        // 根據職業隨機設定手持武器的動作狀態
        int weaponType = this.getRandomWeaponType(this._de_name.get_type());
        this.setStatus(weaponType);
        
        // 設定稱號與速度
        this.setTitle(DeTitleTable.get().getTitle());
        this.setBraveSpeed(1);
        this.setMoveSpeed(1);
        this.setPassispeed(SprTable.get().getMoveSpeed(classid, this.getStatus()));
        this.setAtkspeed(SprTable.get().getAttackSpeed(classid, 1));

        // 啟動AI
        this.onNpcAI();
    }

    /**
     * 根據NPC樣板設定隨機正義值
     */
    private void applyTemplateRandomLawful() {
        L1Npc template = this.getNpcTemplate();
        if (template == null) return;
        // 計算隨機正義值
        int lawful = template.get_lawful();
        if (template.get_randomlawful() != 0) {
            double rate = 0.0;
            if (template.get_randomlevel() != 0) {
                int diff = template.get_randomlevel() - template.get_level();
                if (diff > 0) rate = (double)_random.nextInt(diff + 1) / (double)diff;
            }
            lawful = (int)((double)template.get_lawful() + rate * (double)(template.get_randomlawful() - template.get_lawful()));
        }
        // 設定正義值
        this.setLawful(lawful);
        this.setTempLawful(lawful);
    }

    /**
     * 根據職業類型隨機回傳一個武器動作狀態 (status)
     * @param classType 職業類型
     */
    private int getRandomWeaponType(int classType) {
        int rnd = _random.nextInt(100);
        switch (classType) {
            case 0: return 4;
            case 1: return (rnd < 60) ? 50 : (rnd < 90) ? 4 : (rnd < 95) ? 11 : 24;
            case 2: return (rnd < 80) ? 20 : (rnd < 98) ? 4 : 11;
            case 3: return (rnd < 80) ? 40 : 4;
            case 4: return (rnd < 50) ? 54 : (rnd < 90) ? 58 : 20;
            case 5: return (rnd < 80) ? 24 : 50;
            case 6: return (rnd < 50) ? 40 : 4;
            default: return 4;
        }
    }

    // 以下為覆寫的空方法或固定行為，防止假人有非預期的互動

    /** 刪除自身時播放消失特效 */
    @Override public void deleteMe() { 
        this.broadcastPacketAll(new S_SkillSound(this.getId(), 169)); 
        super.deleteMe(); 
    }
    /** AI目標搜尋，直接返回false，行為改由 searchTarget() 處理 */
    @Override public boolean noTarget() { return false; }
    /** 被玩家攻擊時的行為，空方法 */
    @Override 
    public void onAction(L1PcInstance pc) {
        try {
            L1AttackMode attack = new L1AttackPc(pc, this);
            attack.action();
        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }
    /** 與玩家對話時的行為，空方法 */
    @Override public void onTalkAction(L1PcInstance pc) {}

    // Getter & Setter

    /** 取得職業ID */
    public int getClassId() { return this._classId; }
    /** 設定職業ID */
    public void setClassId(int i) { this._classId = i; }
}
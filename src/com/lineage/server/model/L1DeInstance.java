package com.lineage.server.model.Instance;

import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.utils.ListMapUtil;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.types.Point;
import com.lineage.server.datatables.DeShopItemTable;
import com.lineage.server.thread.DeAiThreadPool;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1IllusoryInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.serverpackets.S_ChangeShape;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import java.util.Iterator;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_Chat;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ChatTransaction;
import com.lineage.server.serverpackets.S_ChatGlobal;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.datatables.DeTitleTable;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.datatables.DeNameTable;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_Fishing;
import com.lineage.server.serverpackets.S_DoActionShop;
import com.lineage.server.serverpackets.S_NPCPack_De;
import java.util.HashMap;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Character;
import com.lineage.server.templates.L1EmblemIcon;
import java.util.Map;
import com.lineage.server.templates.DeName;
import java.util.ArrayList;
import java.util.Random;
import org.apache.commons.logging.Log;

// 魔法娃娃所需匯入
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.datatables.NpcTable;
import com.lineage.config.ConfigDrop;
import com.lineage.server.templates.L1Doll;

public class L1DeInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private static final Random _random;
    private static final CopyOnWriteArrayList<DeName> _denameList;
    private final Map<L1ItemInstance, Integer> _sellList;
    private final Map<Integer, int[]> _buyList;
    private String _shop_chat1;
    private String _shop_chat2;
    private boolean _is_shop;
    private int _clanid;
    private String _clan_name;
    private L1EmblemIcon _emblem;
    private DeName _de_name;
    private int _classId;
    private static final int[][] _class_list;
    private static final int maxThinkingCycle = 40;
    private int[] _myBuffList;
    private int thinkingCounter;
    private String _global_chat;
    private int _chat_mode;
    private boolean _chat;
    private boolean _is_fishing;
    private int _fishX;
    private int _fishY;
    private L1Character attacker_target;
    
    static {
        _log = LogFactory.getLog(L1DeInstance.class);
        _random = new Random();
        _denameList = new CopyOnWriteArrayList<>();
        _class_list = new int[][] { { 0, 61, 138, 734, 2786, 6658, 6671 }, { 1, 48, 37, 1186, 2796, 6661, 6650 } };
    }
    
    public L1DeInstance(L1Npc template) {
        super(template);
        this._sellList = new java.util.HashMap<>();
        this._buyList = new java.util.HashMap<>();
        this._shop_chat1 = null;
        this._shop_chat2 = null;
        this._is_shop = false;
        this._clanid = 0;
        this._clan_name = null;
        this._emblem = null;
        this._de_name = null;
        this._global_chat = null;
        this._chat_mode = 0;
        this._chat = false;
        this._is_fishing = false;
        this._fishX = -1;
        this._fishY = -1;
    }

    // ==========================================
    // 【喚醒機制】強制刷新並喚醒身上的魔法娃娃 AI
    // ==========================================
    private void refreshDollsAI() {
        try {
            if (!this.getDolls().isEmpty()) {
                Object[] dollsArray = this.getDolls().values().toArray();
                for (Object obj : dollsArray) {
                    L1DollInstance doll = (L1DollInstance) obj;
                    if (doll != null && !doll.isDead()) {
                        // 1. 同步移動速度，避免假人加速但娃娃沒加速
                        doll.setNpcMoveSpeed(); 
                        
                        // 2. 如果娃娃距離假人太遠 (超過 10 格)，強制把它拉過來 (防卡牆/迷路)
                        if (this.getLocation().getTileLineDistance(doll.getLocation()) > 10) {
                            L1Location loc = L1Location.randomLocation(this.getLocation(), 1, 2, false);
                            doll.teleport(loc.getX(), loc.getY(), this.getHeading());
                        } 
                        // 3. 如果娃娃正在發呆，喚醒它的 AI
                        else if (!doll.isAiRunning()) {
                            doll.startAI();
                        }
                    }
                }
            }
        } catch (Exception e) {
            L1DeInstance._log.error("假人刷新娃娃 AI 時發生異常: " + e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            // 只要玩家看到假人，就順便刷新娃娃狀態 (已移除舊版 autoSummonDoll 避免報錯)
            this.refreshDollsAI(); 

            if (!this._is_shop) {
                this.start_shop();
            }
            perceivedFrom.addKnownObject(this);
            perceivedFrom.sendPackets(new S_NPCPack_De(this));
            if (this.isShop()) {
                perceivedFrom.sendPackets(new S_DoActionShop(this.getId(), this._shop_chat1));
                return;
            }
            else if (this.isFishing()) {
                perceivedFrom.sendPackets(new S_Fishing(this.getId(), 71, this._fishX, this._fishY));
                return;
            }
            else {
                this.onNpcAI();
                if (this.getBraveSpeed() == 1) {
                    perceivedFrom.sendPackets(new S_SkillBrave(this.getId(), 1, 600000));
                }
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void startNpc(DeName de_name) {
        try {
            if (this._de_name == null) {
                DeName[] des;
                int length;
                DeName de;
                for (des = DeNameTable.get().getDeNameList(), length = des.length, de = des[L1DeInstance._random.nextInt(length)]; L1DeInstance._denameList.contains(de); de = des[L1DeInstance._random.nextInt(length)]) {}
                this._de_name = de;
            }
            L1DeInstance._denameList.add(this._de_name);
            this.setNameId(this._de_name.get_name());
            int classid = L1DeInstance._class_list[this._de_name.get_sex()][this._de_name.get_type()];
            int starus = 0;
            switch (classid) {
                case 0:
                case 1: {
                    starus = 4;
                    break;
                }
                case 37:
                case 138: {
                    starus = 20;
                    this.setBowActId(66);
                    this.set_ranged(10);
                    break;
                }
                case 48:
                case 61: {
                    starus = 50;
                    break;
                }
                case 734:
                case 1186: {
                    starus = 40;
                    break;
                }
                case 2786:
                case 2796: {
                    starus = 54;
                    break;
                }
                case 6650:
                case 6671: {
                    starus = 58;
                    break;
                }
                case 6658:
                case 6661: {
                    starus = 24;
                    break;
                }
            }
            if (this._de_name.get_clanid() != 0) {
                ClanReading.get().getTemplate(this._de_name.get_clanid());
            }
            this.setStatus(starus);
            this.setClassId(classid);
            this.setTempCharGfx(classid);
            this.setGfxId(classid);
            this.setTitle(DeTitleTable.get().getTitle());
            this.setBraveSpeed(1);
            this.setMoveSpeed(1);
            int attack = SprTable.get().getAttackSpeed(classid, 1);
            int move = SprTable.get().getMoveSpeed(classid, this.getStatus()); // 修正點：將 getStatus() 移回括號內
            
            // 根據職業類型設定對應的 Buff 特效ID清單
            switch (this._de_name.get_type()) {
                case 0: _myBuffList = new int[]{ 11772, 2943, 3942,2165,750,6342,221 }; break; // 王族
                case 1: _myBuffList = new int[]{ 221, 2165, 4824, 4648, 5831,2165,750,6342}; break;   // 騎士
                case 2: _myBuffList = new int[]{ 2176, 2170, 748, 750, 6342, 830, 221, 2165,2186,2179,2180,2184,2178,2285,2182,2246,2247,2249,2286,2288,2243 }; break;  // 妖精
                case 3: _myBuffList = new int[]{ 2176, 2170, 748, 750, 6342, 830, 221, 2165 }; break;  // 法師
                default: _myBuffList = new int[]{ 221 }; break;       // 其他
            }
            this.setPassispeed(move);
            this.setAtkspeed(attack);
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
        
        // 創建時即隨機變身
        try {
            int[] bowClassIds   = {20480,20712,20243,20415,20790,20423,20653};
            int[] meleeClassIds = {20398,20401,20556,20560,20617,20642,20692,20657,20484,20552,20633,20255,20239,20547,20169,20155};
    
            int classid;
            if (this.getBowActId() > 0) { // 弓手
                classid = bowClassIds[_random.nextInt(bowClassIds.length)];
            } else { // 近戰
                classid = meleeClassIds[_random.nextInt(meleeClassIds.length)];
            }
            this.setTempCharGfx(classid);
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }

    
    public int getClassId() {
        return this._classId;
    }
    
    public void setClassId(int i) {
        this._classId = i;
    }
    
    public DeName get_deName() {
        return this._de_name;
    }
    
    public int get_chat_mode() {
        return this._chat_mode;
    }
    
    public String get_chat() {
        return this._global_chat;
    }
    
    public void set_chat(String chat, int cmd) {
        this._global_chat = chat;
        this._chat_mode = cmd;
        if (L1DeInstance._random.nextInt(100) <= 30) {
            this._chat = true;
        }
    }
    
    public void globalChat() {
        try {
            if (L1DeInstance._random.nextBoolean()) {
                return;
            }
            else {
                ServerBasePacket pack = null;
                if (this._chat_mode != 27) {
                    pack = new S_ChatGlobal(this, this._global_chat);
                }
                else if (this._chat_mode != 28) {
                    pack = new S_ChatTransaction(this, this._global_chat);
                }
                for (L1PcInstance listner : World.get().getAllPlayers()) {
                    if (listner.getExcludingList().contains(this.getNameId())) {
                        continue;
                    }
                    else if (!listner.isShowTradeChat()) {
                        continue;
                    }
                    else {
                        listner.sendPackets(pack);
                    }
                }
                if (this._chat) {
                    return;
                }
                else {
                    pack = new S_Chat(this, this._global_chat);
                    for (L1PcInstance listner2 : World.get().getRecognizePlayer(this)) {
                        if (!listner2.getExcludingList().contains(this.getNameId()) && this.get_showId() == listner2.get_showId()) {
                            listner2.sendPackets(pack);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean isFishing() {
        return this._is_fishing;
    }
    
    public void start_fishingAI() {
        if (!this._is_fishing) {
            L1DeInstance.Fishing_Runnable runnable = new L1DeInstance.Fishing_Runnable(this);
            runnable.startCmd();
        }
    }
    
    public void stop_fishing() {
        if (this._is_fishing) {
            this.broadcastPacketAll(new S_CharVisualUpdate(this.getId(), 0));
        }
        this._is_fishing = false;
    }
    
    public void start_fishing() {
        try {
            int locx = this.getX();
            int locy = this.getY();
            for (int count = 3 + L1DeInstance._random.nextInt(2), i = 0; i < count; ++i) {
                locx += L1DeInstance.HEADING_TABLE_X[this.getHeading()];
                locy += L1DeInstance.HEADING_TABLE_Y[this.getHeading()];
            }
            int gab = this.getMap().getOriginalTile(locx, locy);
            if (gab % 28 == 0) {
                this._fishX = locx;
                this._fishY = locy;
                this.setHeading(this.targetDirection(this._fishX, this._fishY));
                this.broadcastPacketAll(new S_ChangeHeading(this));
                this.broadcastPacketAll(new S_Fishing(this.getId(), 71, this._fishX, this._fishY));
                this._is_fishing = true;
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int get_fishX() {
        return this._fishX;
    }
    
    public int get_fishY() {
        return this._fishY;
    }
    
    public void setNpcSpeed() {
        try {
            if (!this.getDolls().isEmpty()) {
                Object[] array;
                for (int length = (array = this.getDolls().values().toArray()).length, i = 0; i < length; ++i) {
                    Object obj = array[i];
                    L1DollInstance doll = (L1DollInstance)obj;
                    if (doll != null) {
                        doll.setNpcMoveSpeed();
                    }
                }
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
@Override
    public void searchTarget() {
        try {
            if (this.isShop()) {
                return;
            }
            
            L1NpcInstance targetNpc = this.search_target();
            if (targetNpc != null) {
                this._hateList.add(targetNpc, 0);
                this._target = targetNpc;

                
                // ==========================================
                // 【打怪時 1% 機率自動召喚娃娃 (限定一般娃娃)】
                // ==========================================
                if (this.getDolls().isEmpty() && L1DeInstance._random.nextInt(100) < 1) {
                    try {
                        int[] allowedDolls = {
                            70681, 70682, 70683, 70684, 70685, 70686, 70687, 70688, 70689, 70690,
                            70691, 70692, 70693, 70694, 70695, 70696, 70697, 70698, 70699, 70700,
                            70712, 70713, 70714, 70715, 70716, 70717, 70718, 70719
                        };
                        int dollid = allowedDolls[L1DeInstance._random.nextInt(allowedDolls.length)];
                        
                        L1Npc template = NpcTable.get().getTemplate(71082);
                        if (template != null) {
                            L1Doll type = DollPowerTable.get().get_type(dollid);
                            if (type != null) {
                                L1DollInstance dollInstance = new L1DollInstance(template, this, type);
                                dollInstance.setNpcMoveSpeed(); 
                            }
                        }
                    } catch (Exception e) {
                        L1DeInstance._log.error("假人戰鬥中召喚指定娃娃異常: " + e.getLocalizedMessage(), e);
                    }
                }
                
                // 原本的寵物/召喚物跟隨攻擊邏輯
                if (!this.getPetList().isEmpty()) {
                    for (L1NpcInstance summon : this.getPetList().values()) {
                        if (summon != null && summon instanceof L1SummonInstance) {
                            L1SummonInstance su = (L1SummonInstance)summon;
                            su.setMasterTarget(this._target);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private L1NpcInstance search_target() {
        if (this.isDead()) {
            return null;
        }
        else if (this.isParalyzed() || this.isSleeped() || this.hasSkillEffect(50) || this.hasSkillEffect(87) || this.hasSkillEffect(157)) {
            return null;
        }
        else if (this.thinkingCounter >= 40) {
            this.thinkingCounter = 10;
            L1Location newLoc = this.getLocation().randomLocation(200, true);
            this.teleport(newLoc.getX(), newLoc.getY(), 5);
            return null;
        }
        else {
            L1NpcInstance attacker_target = null;
            if (this._target == null) {
                L1NpcInstance next_target = null;
                int minDistance = 32767;
                int distance = 0;
                ArrayList<L1Object> screenObjects = World.get().getVisibleObjects(this, this.thinkingCounter);
                for (L1Object visible : screenObjects) {
                    if (visible instanceof L1MonsterInstance) {
                        L1MonsterInstance npc = (L1MonsterInstance)visible;
                        // 調整尋找怪物的血量範圍 20 ~ 5000
                        if (npc.getCurrentHp() > 0 && !npc.isDead() && (!npc.isParalyzed() || npc.hasSkillEffect(87)) && npc.getHiddenStatus() == 0 && npc.getMaxHp() >= 20 && npc.getMaxHp() <= 5000 && npc.getLevel() > 5 && npc.getCurrentHp() >= npc.getMaxHp()) {
                            if (!this.glanceCheck(npc.getX(), npc.getY())) {
                                continue;
                            }
                            else if (npc._target == this) {
                                distance = this.getLocation().getTileLineDistance(npc.getLocation());
                                if (minDistance > distance) {
                                    minDistance = distance;
                                    next_target = npc;
                                }
                                else {
                                    continue;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
                if (next_target == null) {
                    for (L1Object visible : screenObjects) {
                        if (visible instanceof L1MonsterInstance) {
                            L1MonsterInstance npc = (L1MonsterInstance)visible;
                            // 調整尋找怪物的血量範圍 20 ~ 5000
                            if (npc.getCurrentHp() > 0 && !npc.isDead() && (!npc.isParalyzed() || npc.hasSkillEffect(87)) && npc.getHiddenStatus() == 0 && npc.getMaxHp() >= 20 && npc.getMaxHp() <= 5000 && npc.getLevel() > 5 && npc.getCurrentHp() >= npc.getMaxHp()) {
                                if (!this.glanceCheck(npc.getX(), npc.getY())) {
                                    continue;
                                }
                                else if (npc.getHateList().isEmpty() || npc.getHateList().containsKey(this)) {
                                    distance = this.getLocation().getTileLineDistance(npc.getLocation());
                                    if (minDistance > distance) {
                                        minDistance = distance;
                                        next_target = npc;
                                    }
                                    else {
                                        continue;
                                    }
                                }
                                else {
                                    continue;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                if (next_target != null) {
                    attacker_target = next_target;
                }
            }
            if (attacker_target == null) {
                this.thinkingCounter += 5;
                return null;
            }
            else {
                return attacker_target;
            }
        }
    }
    
    @Override
    public boolean noTarget() {
        return this.isShop();
    }
    
    @Override
    public void onNpcAI() {
        try {
            // 每次假人思考時，順便叫醒娃娃！(已移除舊版 autoSummonDoll)
            this.refreshDollsAI(); 
            
            if (this.isShop()) {
                return;
            }
            else if (this.isAiRunning()) {
                return;
            }
            else {
                this.setActived(false);
                this.startAI();
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance pc) {
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            if (this.isSafetyZone() || pc.isSafetyZone()) {
                L1AttackMode attack_mortion = new L1AttackPc(pc, this);
                attack_mortion.action();
                return;
            }
            else if (this.getCurrentHp() > 0 && !this.isDead()) {

                L1AttackMode attack = new L1AttackPc(pc, this);
                if (attack.calcHit()) {
                    attack.calcDamage();
                }
                attack.action();
                attack.commit();
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void ReceiveManaDamage(L1Character attacker, int mpDamage) {
        if (mpDamage > 0 && !this.isDead()) {
            this.setHate(attacker, mpDamage);
            this.onNpcAI();
            if (attacker instanceof L1PcInstance) {
                this.serchLink((L1PcInstance)attacker, this.getNpcTemplate().get_family());
            }
            int newMp = this.getCurrentMp() - mpDamage;
            if (newMp < 0) {
                newMp = 0;
            }
            this.setCurrentMp(newMp);
        }
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        if (this.getCurrentHp() > 0 && !this.isDead()) {
            if (this.getHiddenStatus() == 1 || this.getHiddenStatus() == 2) {
                return;
            }
            else {
                this.onNpcAI();
                if (attacker instanceof L1PcInstance) {
                    if (damage > 0) {
                        L1PcInstance player = (L1PcInstance)attacker;
                        player.setPetTarget(this);
                        this.thinkingCounter += 15;
                        return;
                    }
                }
                else {
                    damage += 20;
                }
                if (damage >= 0 && !(attacker instanceof L1EffectInstance)) {
                    if (attacker instanceof L1IllusoryInstance) {
                        L1IllusoryInstance ill = (L1IllusoryInstance)attacker;
                        attacker = ill.getMaster();
                        this.setHate(attacker, damage);
                    }
                    else {
                        this.setHate(attacker, damage);
                    }
                }
                if (damage > 0) {
                    this.removeSkillEffect(66);
                }

// ==========================================
                // 【優化】擬真喝水邏輯
                // ==========================================
                // 計算假人目前的血量百分比
                double hpRatio = (double) this.getCurrentHp() / this.getMaxHp();
                
                // 條件: 1. 怪物真的有打出傷害(大於0)  2. 假人的血量已經低於 85%
                if (damage > 0 && hpRatio < 0.85) {
                    // 血越少喝越快：如果血量低於 50%，90% 機率喝水；否則只有 60% 機率喝水
                    int drinkChance = (hpRatio < 0.5) ? 90 : 60;
                    
                    if (L1DeInstance._random.nextInt(100) < drinkChance) {
                        // 讓補血量也有一點浮動 (例如 50 ~ 100 之間)，比較像真實喝水
                        int healAmount = 50 + L1DeInstance._random.nextInt(51); 
                        int newHp = this.getCurrentHp() + healAmount;
                        this.setCurrentHp(newHp);
                        
                        // 播放喝水特效 (189)
                        this.broadcastPacketX8(new S_SkillSound(this.getId(), 189));
                    }
                }
                // ==========================================
                int newHp = this.getCurrentHp() - damage;
                if (newHp <= 0 && !this.isDead()) {
                    this.setCurrentHpDirect(0);
                    this.setDead(true);
                    this.setStatus(8);
                    L1DeInstance.Death death = new L1DeInstance.Death(attacker);
                    DeAiThreadPool.get().execute(death);
                }
                if (newHp > 0) {
                    this.setCurrentHp(newHp);
                }
            }
        }
        else if (!this.isDead()) {
            this.setDead(true);
            this.setStatus(8);
            L1DeInstance.Death death2 = new L1DeInstance.Death(attacker);
            DeAiThreadPool.get().execute(death2);
        }
    }
    
    @Override
    public void setCurrentHp(int i) {
        int currentHp = Math.min(i, this.getMaxHp());
        if (this.getCurrentHp() == currentHp) {
            return;
        }
        else {
            this.setCurrentHpDirect(currentHp);
            return;
        }
    }
    
    @Override
    public void setCurrentMp(int i) {
        int currentMp = Math.min(i, this.getMaxMp());
        if (this.getCurrentMp() == currentMp) {
            return;
        }
        else {
            this.setCurrentMpDirect(currentMp);
            return;
        }
    }
    
    public int getClanid() {
        return this._clanid;
    }
    
    public String getClanname() {
        return this._clan_name;
    }
    
    public L1EmblemIcon getEmblem() {
        return this._emblem;
    }
    
    public void setShopChat(String shopChat1, String shopChat2) {
        this._shop_chat1 = shopChat1;
        this._shop_chat2 = shopChat2;
    }
    
    public void shopChat() {
        try {
            if (L1DeInstance._random.nextBoolean()) {
                String info = "";
                if (L1DeInstance._random.nextBoolean()) {
                    info = this._shop_chat1;
                }
                else {
                    info = this._shop_chat2;
                }
                this.broadcastPacketAll(new S_DoActionShop(this.getId(), info));
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void stop_shop() {
        try {
            if (this.isShop()) {
                this.set_isShop(false);
                this._sellList.clear();
                this._buyList.clear();
                this._shop_chat1 = null;
                this._shop_chat2 = null;
                this.broadcastPacketAll(new S_CharVisualUpdate(this.getId(), 0));
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void start_shop() {
        try {
            if (!this.isShop()) {
                boolean isShopMap = false;
                boolean isShopMap2 = false;
                switch (this.getMapId()) {
                    case 340: {
                        isShopMap2 = true;
                        break;
                    }
                    case 0:
                    case 4:
                    case 350:
                    case 360:
                    case 370:
                    case 800: {
                        isShopMap = true;
                        break;
                    }
                }
                if (isShopMap) {
                    this.set_isShop(true);
                    DeShopItemTable.get().getItems(this);
                    int h = L1DeInstance._random.nextInt(7);
                    this.setHeading(h);
                    this.broadcastPacketX8(new S_ChangeHeading(this));
                    this.broadcastPacketX8(new S_DoActionShop(this.getId(), this._shop_chat1));
                    int[] PolyList = { 10032, 11427, 10047, 9688, 11322, 10069, 10034, 10032 };
                    int polyIndex = L1DeInstance._random.nextInt(8);
                    this.setStatus(0);
                    this.setTempCharGfx(PolyList[polyIndex]);
                    this.broadcastPacketX8(new S_CharVisualUpdate(this.getId(), 70));
                }
            }
        }
        catch (Exception e) {
            L1DeInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public Map<L1ItemInstance, Integer> get_sellList() {
        return this._sellList;
    }
    
    public Map<Integer, int[]> get_buyList() {
        return this._buyList;
    }
    
    public void sellList(Map<L1ItemInstance, Integer> sellList) {
        this._sellList.putAll(sellList);
    }
    
    public void buyList(Map<Integer, int[]> buyList) {
        this._buyList.putAll(buyList);
    }
    
    public void updateBuyList(Integer key, int[] value) {
        this._buyList.put(key, value);
    }

    // ==========================================
    // 【怪物仇恨清除】消失或飛走前主動清除，防止怪物揮空氣
    // ==========================================
    private void clearMonsterAggro() {
        try {
            for (L1Object obj : World.get().getVisibleObjects(this)) {
                if (obj instanceof L1NpcInstance) {
                    L1NpcInstance npc = (L1NpcInstance) obj;
                    boolean isModified = false;
                    
                    if (npc.getHateList() != null && npc.getHateList().containsKey(this)) {
                        npc.getHateList().remove(this);
                        isModified = true;
                    }
                    if (npc._target == this) {
                        npc._target = null;
                        isModified = true;
                    }
                    if (isModified) {
                        npc.searchTarget(); 
                    }
                }
            }
        } catch (Exception e) {
            _log.error("假人清除怪物仇恨時發生異常: " + e.getLocalizedMessage(), e);
        }
    }

    

    // ==========================================
    // 【刪除防呆】安全移除娃娃 + 解除怪物仇恨
    // ==========================================
    @Override
    public void deleteMe() {
        this.clearMonsterAggro();
        try {
            if (!this.getDolls().isEmpty()) {
                Object[] dollsArray = this.getDolls().values().toArray();
                for (Object obj : dollsArray) {
                    L1DollInstance doll = (L1DollInstance) obj;
                    if (doll != null) {
                        doll.deleteMe(); 
                    }
                }
                this.getDolls().clear(); 
            }
        } catch (Exception e) {
            _log.error("假人刪除娃娃時發生防呆異常: " + e.getLocalizedMessage(), e);
        }
        super.deleteMe();
    }

    // ==========================================
    // 【瞬移防呆】讓娃娃跟著飛 + 解除舊座標怪物仇恨
    // ==========================================
    @Override
    public void teleport(int x, int y, int heading) {
        this.clearMonsterAggro();
        super.teleport(x, y, heading);
        try {
            if (!this.getDolls().isEmpty()) {
                Object[] dollsArray = this.getDolls().values().toArray();
                for (Object obj : dollsArray) {
                    L1DollInstance doll = (L1DollInstance) obj;
                    if (doll != null) {
                        doll.teleport(x, y, heading);
                    }
                }
            }
        } catch (Exception e) {
            _log.error("假人娃娃跟隨瞬移時發生防呆異常: " + e.getLocalizedMessage(), e);
        }
    }
    
    private class Fishing_Runnable implements Runnable
    {
        private L1DeInstance _npc;
        
        private Fishing_Runnable(L1DeInstance npc) {
            this._npc = null;
            this._npc = npc;
        }
        
        private void startCmd() {
            DeAiThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            try {
                int x = this._npc.getX();
                int y = this._npc.getY();
                int x2 = x - 18;
                int y2 = y - 18;
                int x3 = x + 18;
                int y3 = y + 18;
                int rows = x3 - x2;
                int columns = y3 - y2;
                int tgx = 0;
                int tgy = 0;
                L1Map map = this._npc.getMap();
                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < columns; ++j) {
                        int cx = x2 + i;
                        int cy = y2 + j;
                        int gab = map.getOriginalTile(cx, cy);
                        if (gab % 28 == 0) {
                            tgx = cx;
                            tgy = cy;
                        }
                    }
                }
                Thread.sleep(10L);
                int i = 20;
                while (this._npc.getX() != tgx && this._npc.getY() != tgy) {
                    if (this._npc == null) {
                        break;
                    }
                    else {
                        double d = this._npc.getLocation().getLineDistance(new Point(tgx, tgy));
                        if (d <= 1.0) {
                            break;
                        }
                        else {
                            int moveDirection = this._npc.getMove().moveDirection(tgx, tgy);
                            int dir = this._npc.getMove().checkObject(moveDirection);
                            if (dir != -1) {
                                this._npc.getMove().setDirectionMove(dir);
                                this._npc.setNpcSpeed();
                            }
                            Thread.sleep((long)this._npc.calcSleepTime(this._npc.getPassispeed(), 0));
                            if (--i <= 0) {
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                Thread.sleep(5000L);
                L1DeInstance.this.start_fishing();
            }
            catch (InterruptedException e) {
                L1DeInstance._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
    
    class Death implements Runnable
    {
        L1Character _lastAttacker;
        
        public Death(L1Character lastAttacker) {
            this._lastAttacker = lastAttacker;
        }
        
        @Override
        public void run() {
            try {
                L1DeInstance mob = L1DeInstance.this;
                L1DeInstance._denameList.remove(L1DeInstance.this._de_name);
                mob.setDeathProcessing(true);
                mob.setCurrentHpDirect(0);
                mob.setDead(true);
                mob.setStatus(8);
                mob.broadcastPacketAll(new S_DoActionGFX(mob.getId(), 8));
                mob.getMap().setPassable(mob.getLocation(), true);
                mob.startChat(1);
                mob.setDeathProcessing(false);
                mob.allTargetClear();
                int deltime = 5;
                mob.startDeleteTimer(deltime);
                ListMapUtil.clear(L1DeInstance.this._sellList);
                ListMapUtil.clear(L1DeInstance.this._buyList);
            }
            catch (Exception e) {
                L1DeInstance._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
        /**
     * 取得該假人可用的輔助魔法特效ID列表
     * @return
     */
    public int[] getBuffList() {
        return this._myBuffList;
    }

}
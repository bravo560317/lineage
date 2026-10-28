package com.add;

import java.sql.Timestamp;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.datatables.SkillsTable;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.datatables.lock.VIPReading;
import com.lineage.server.serverpackets.S_SkillBuy;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.config.ConfigAi;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigGuaji;
import java.util.Calendar;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

public class AutoAttackUpdate
{
    private static Logger _log;
    private static AutoAttackUpdate _instance;
    
    static {
        AutoAttackUpdate._log = Logger.getLogger(AutoAttackUpdate.class.getName());
    }
    
    public static AutoAttackUpdate getInstance() {
        if (AutoAttackUpdate._instance == null) {
            AutoAttackUpdate._instance = new AutoAttackUpdate();
        }
        return AutoAttackUpdate._instance;
    }

    private boolean hasActiveVip(L1PcInstance pc) {
        Timestamp vipTime = VIPReading.get().getOther(pc);
        return vipTime != null && vipTime.getTime() > System.currentTimeMillis();
    }

    public boolean PcCommand(L1PcInstance _pc, String cmd) {
        return this.PcCommand(_pc, cmd, false);
    }
    
public boolean PcCommand(L1PcInstance _pc, String cmd, boolean simpleAutoHunt) {
        
// ===== 暫時取消 AI4、AI5、AI6 等指令的 VIP 月卡判斷 =====
        /*
        if (cmd.equalsIgnoreCase("AI4") ||
            cmd.equalsIgnoreCase("AI5") ||
            cmd.equalsIgnoreCase("AI6") ||
            cmd.equalsIgnoreCase("naas0") ||
            cmd.equalsIgnoreCase("naaM0")) {

            if (_pc.getNetConnection() == null ||
                _pc.getNetConnection().getAccount() == null ||
                (
                    !hasActiveVip(_pc)
                )
            ) {
                _pc.sendPackets(new S_ServerMessage("高階練功未解鎖（需VIP月卡）"));
                _pc.sendPackets(new S_SystemMessage("\\fU提醒:月卡可藉由簽到累積進行月卡製作"));
                return false;
            }
        }
        */
     

        if (cmd.equalsIgnoreCase("guaji_start")) {
            Calendar date = Calendar.getInstance();
            int nowHour = date.get(11);
            if (ConfigGuaji.checktimeguaji) {
                int[] GUAJI_TIME = ConfigGuaji.GUAJI_ITEM;
                int[] array;
                int length = (array = GUAJI_TIME).length;
                int i = 0;
                while (i < length) {
                    int guajitimme = array[i];
                    if (nowHour == guajitimme) {
                        _pc.sendPackets(new S_ServerMessage("此時間未開放使用掛機"));
                        return false;
                    }
                    else {
                        ++i;
                    }
                }
            }
            if (ConfigGuaji.Guaji_action) {
                if (!_pc.getMap().isGuaji()) {
                    _pc.sendPackets(new S_ServerMessage("此地圖無法掛機，請前往指定地圖"));
                    return false;
                }
                else if (!_pc.isActived()) {
                    if (_pc.hasSkillEffect(99666)) {
                        _pc.sendPackets(new S_ServerMessage("該道具使用延遲5秒，請稍候再執行"));
                        return false;
                    }
                    else if (_pc.isParalyzed_guaji()) {
                        _pc.sendPackets(new S_ServerMessage("負面狀態中無法開啟內掛功能"));
                        return false;
                    }
                    else {
                        _pc.setSimpleAutoHunt(simpleAutoHunt);
                        L1PcUnlock.Pc_Unlock(_pc);
                        _pc.sendPackets(new S_ServerMessage("\\fU自動練功已開始。"));
                        _pc.sendPackets(new S_ServerMessage("\\fU掛機中:不顯示傷害 掉落物品。"));
                        if (simpleAutoHunt) {
                            _pc.sendPackets(new S_ServerMessage("\\fU自動練功已開始。"));
                           // _pc.sendPackets(new S_ServerMessage("若30 秒沒有可攻擊怪物系統自動瞬移"));
                           // _pc.sendPackets(new S_ServerMessage("開通月卡可享更多內掛功能！"));
                        }
                        RecordTable.get().guaji(_pc.getName(), "道具啟動");
                        _pc.killSkillEffectTimer(6930);
                        _pc.killSkillEffectTimer(6931);
                        _pc.killSkillEffectTimer(6932);
                        if (ConfigAi.longntimeai_3) {
                            _pc.setSkillEffect(6930, 300000);
                        }
                        if (!simpleAutoHunt && _pc.getAu_AutoSet(0) > 0 && _pc.getAu_AutoSet(1) > 0) {
                            _pc.sendPackets(new S_ServerMessage("[定點巡邏] 此座標已記錄。"));
                            _pc.setAutoX(_pc.getX());
                            _pc.setAutoY(_pc.getY());
                            _pc.setAutoMap((int)_pc.getMapId());
                        }
                        _pc.startAI();
						
					// 記錄開始掛機的地圖
_pc.setAutoMap((int)_pc.getMapId());

                        _pc.setSkillEffect(99666, 5000);
                    }
                }
                else {
                    _pc.sendPackets(new S_ServerMessage("目前正在內掛中。"));
                }
            }
        }
        else if (cmd.equalsIgnoreCase("guaji_stop")) {
            if (_pc.isActived()) {
                _pc.setSimpleAutoHunt(false);
                _pc.setActived(false);
                _pc.sendPackets(new S_ServerMessage(" 自動狩獵已停止。"));
                L1PcUnlock.Pc_Unlock(_pc);
                if (_pc.getAutoX() > 0) {
                    _pc.setAutoX(0);
                    _pc.setAutoY(0);
                    _pc.setAutoMap(0);
                }
                _pc.killSkillEffectTimer(8853);
                _pc.killSkillEffectTimer(6930);
                _pc.killSkillEffectTimer(6931);
                _pc.killSkillEffectTimer(6932);
                if (ConfigAi.longntimeai_3) {
                    _pc.setSkillEffect(6930, 300000);
                }
            }
        }
        else if (cmd.equalsIgnoreCase("Au_19")) {
            NewAutoPractice.get().ClearAutoLog(_pc.getId());
            _pc.sendPackets(new S_CloseList(_pc.getId()));
            _pc.sendPackets(new S_ServerMessage("\\fU紀錄已刪除。"));
        }
        else if (cmd.equalsIgnoreCase("naas0")) {
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas1")) {
            if (_pc.IsAu_Shop()) {
                _pc.setAu_Shop(false);
                _pc.get_other1().set_type1(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買。"));
            }
            else {
                _pc.setAu_Shop(true);
                _pc.get_other1().set_type1(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買。"));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas2")) {
            if (_pc.getAu_BuyItemSwitch(0) > 0) {
                _pc.setAu_BuyItemSwitch(0, 0);
                _pc.get_other1().set_type2(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname1));
            }
            else {
                _pc.setAu_BuyItemSwitch(0, 1);
                _pc.get_other1().set_type2(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname1));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas3")) {
            if (_pc.getAu_BuyItemSwitch(0) == 0) {
                _pc.sendPackets(new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            else {
                _pc.setAu_SetShop(true);
                _pc.SetAu_SetShopType(0);
                _pc.sendPackets(new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname1 + "的數量。"));
            }
        }
        else if (cmd.equalsIgnoreCase("naas4")) {
            if (_pc.getAu_BuyItemSwitch(1) > 0) {
                _pc.setAu_BuyItemSwitch(1, 0);
                _pc.get_other1().set_type4(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname2 + "。"));
            }
            else {
                _pc.setAu_BuyItemSwitch(1, 1);
                _pc.get_other1().set_type4(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname2 + "。"));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas5")) {
            if (_pc.getAu_BuyItemSwitch(1) == 0) {
                _pc.sendPackets(new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            else {
                _pc.setAu_SetShop(true);
                _pc.SetAu_SetShopType(1);
                _pc.sendPackets(new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname2 + "的數量。"));
            }
        }
        else if (cmd.equalsIgnoreCase("naas6")) {
            if (_pc.getAu_BuyItemSwitch(2) > 0) {
                _pc.setAu_BuyItemSwitch(2, 0);
                _pc.get_other1().set_type6(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname3 + "。"));
            }
            else {
                _pc.setAu_BuyItemSwitch(2, 1);
                _pc.get_other1().set_type6(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname3 + "。"));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas7")) {
            if (_pc.getAu_BuyItemSwitch(2) == 0) {
                _pc.sendPackets(new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            else {
                _pc.setAu_SetShop(true);
                _pc.SetAu_SetShopType(2);
                _pc.sendPackets(new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname3 + "的數量。"));
            }
        }
        else if (cmd.equalsIgnoreCase("naas8")) {
            if (_pc.getAu_BuyItemSwitch(3) > 0) {
                _pc.setAu_BuyItemSwitch(3, 0);
                _pc.get_other1().set_type8(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname4 + "。"));
            }
            else {
                _pc.setAu_BuyItemSwitch(3, 1);
                _pc.get_other1().set_type8(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname4 + "。"));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas9")) {
            if (_pc.getAu_BuyItemSwitch(3) == 0) {
                _pc.sendPackets(new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            else {
                _pc.setAu_SetShop(true);
                _pc.SetAu_SetShopType(3);
                _pc.sendPackets(new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname4 + "的數量。"));
            }
        }
        else if (cmd.equalsIgnoreCase("naas10")) {
            if (_pc.getAu_BuyItemSwitch(4) > 0) {
                _pc.setAu_BuyItemSwitch(4, 0);
                _pc.get_other1().set_type10(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname5 + "。"));
            }
            else {
                _pc.setAu_BuyItemSwitch(4, 1);
                _pc.get_other1().set_type10(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname5 + "。"));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas11")) {
            if (_pc.getAu_BuyItemSwitch(4) == 0) {
                _pc.sendPackets(new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            else {
                _pc.setAu_SetShop(true);
                _pc.SetAu_SetShopType(4);
                _pc.sendPackets(new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname5 + "的數量。"));
            }
        }
        else if (cmd.equalsIgnoreCase("naas12")) {
            if (_pc.getAu_BuyItemSwitch(5) > 0) {
                _pc.setAu_BuyItemSwitch(5, 0);
                _pc.get_other1().set_type12(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname6 + "。"));
            }
            else {
                _pc.setAu_BuyItemSwitch(5, 1);
                _pc.get_other1().set_type12(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname6 + "。"));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas13")) {
            if (_pc.getAu_BuyItemSwitch(5) == 0) {
                _pc.sendPackets(new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            else {
                _pc.setAu_SetShop(true);
                _pc.SetAu_SetShopType(5);
                _pc.sendPackets(new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname6 + "的數量。"));
            }
        }
        else if (cmd.equalsIgnoreCase("naas14")) {
            if (_pc.getAu_BuyItemSwitch(6) > 0) {
                _pc.setAu_BuyItemSwitch(6, 0);
                _pc.get_other1().set_type14(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname7 + "。"));
            }
            else {
                _pc.setAu_BuyItemSwitch(6, 1);
                _pc.get_other1().set_type14(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname7 + "。"));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas15")) {
            if (_pc.getAu_BuyItemSwitch(6) == 0) {
                _pc.sendPackets(new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            else {
                _pc.setAu_SetShop(true);
                _pc.SetAu_SetShopType(6);
                _pc.sendPackets(new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname7 + "的數量。"));
            }
        }
        else if (cmd.equalsIgnoreCase("naas16")) {
            if (_pc.getAu_BuyItemSwitch(7) > 0) {
                _pc.setAu_BuyItemSwitch(7, 0);
                _pc.get_other1().set_type16(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname8 + "。"));
            }
            else {
                _pc.setAu_BuyItemSwitch(7, 1);
                _pc.get_other1().set_type16(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname8 + "。"));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas17")) {
            if (_pc.getAu_BuyItemSwitch(7) == 0) {
                _pc.sendPackets(new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            else {
                _pc.setAu_SetShop(true);
                _pc.SetAu_SetShopType(7);
                _pc.sendPackets(new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname8 + "的數量。"));
            }
        }
        else if (cmd.equalsIgnoreCase("naas18")) {
            if (_pc.getAu_BuyItemSwitch(8) > 0) {
                _pc.setAu_BuyItemSwitch(8, 0);
                _pc.get_other1().set_type18(0);
                _pc.sendPackets(new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname9 + "。"));
            }
            else {
                _pc.setAu_BuyItemSwitch(8, 1);
                _pc.get_other1().set_type18(1);
                _pc.sendPackets(new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname9 + "。"));
            }
            this.Au_Shop(_pc);
        }
        else if (cmd.equalsIgnoreCase("naas19")) {
            if (_pc.getAu_BuyItemSwitch(8) == 0) {
                _pc.sendPackets(new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            else {
                _pc.setAu_SetShop(true);
                _pc.SetAu_SetShopType(8);
                _pc.sendPackets(new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname9 + "的數量。"));
            }
        }
        else if (cmd.equalsIgnoreCase("naam0")) {
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam1")) {
            if (_pc.getAu_AutoSkill(2) > 0) {
                _pc.setAu_AutoSkill(2, 0);
                _pc.get_other1().set_type20(0);
            }
            else {
                _pc.setAu_AutoSkill(2, 1);
                _pc.get_other1().set_type20(1);
            }
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam21")) {
            _pc.setAu_AutoSkill(3, 5);
            _pc.get_other1().set_type21(5);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam22")) {
            _pc.setAu_AutoSkill(3, 10);
            _pc.get_other1().set_type21(10);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam23")) {
            _pc.setAu_AutoSkill(3, 20);
            _pc.get_other1().set_type21(20);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam24")) {
            _pc.setAu_AutoSkill(3, 30);
            _pc.get_other1().set_type21(30);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam25")) {
            _pc.setAu_AutoSkill(3, 40);
            _pc.get_other1().set_type21(40);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam2")) {
            _pc.setAu_AutoSkill(3, 50);
            _pc.get_other1().set_type21(50);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam3")) {
            _pc.setAu_AutoSkill(3, 60);
            _pc.get_other1().set_type21(60);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam4")) {
            _pc.setAu_AutoSkill(3, 70);
            _pc.get_other1().set_type21(70);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam5")) {
            _pc.setAu_AutoSkill(3, 80);
            _pc.get_other1().set_type21(80);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam6")) {
            _pc.setAu_AutoSkill(3, 90);
            _pc.get_other1().set_type21(90);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam7")) {
            if (_pc.getAu_AutoSkill(4) > 0) {
                _pc.setAu_AutoSkill(4, 0);
                _pc.get_other1().set_type22(0);
                _pc.setAu_AutoSkill(5, 0);
                _pc.get_other1().set_type23(0);
                _pc.sendPackets(new S_ServerMessage("您取消[單體技能]攻擊。"));
                _pc.sendPackets(new S_ServerMessage("請再點擊一次進行選擇。"));
                getInstance().Au_MagicSet(_pc);
                return false;
            }
            else {
                _pc.setAu_AutoSkill(0, 1);
                if (_pc.getAu_AutoSkill(1) > 0) {
                    _pc.setAu_AutoSkill(1, 0);
                }
                _pc.sendPackets(new S_SkillBuy(_pc));
            }
        }
        else if (cmd.equalsIgnoreCase("naam8")) {
            _pc.setAu_AutoSkill(5, 1);
            _pc.get_other1().set_type23(1);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam9")) {
            _pc.setAu_AutoSkill(5, 2);
            _pc.get_other1().set_type23(2);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam10")) {
            _pc.setAu_AutoSkill(5, 3);
            _pc.get_other1().set_type23(3);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam11")) {
            _pc.setAu_AutoSkill(5, 4);
            _pc.get_other1().set_type23(4);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam12")) {
            _pc.setAu_AutoSkill(5, 5);
            _pc.get_other1().set_type23(5);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam13")) {
            if (_pc.getAu_AutoSkill(6) > 0) {
                _pc.setAu_AutoSkill(6, 0);
                _pc.get_other1().set_type24(0);
                _pc.setAu_AutoSkill(7, 0);
                _pc.get_other1().set_type25(0);
                _pc.sendPackets(new S_ServerMessage("您取消[群體技能]攻擊。"));
                _pc.sendPackets(new S_ServerMessage("請再點擊一次進行選擇。"));
                getInstance().Au_MagicSet(_pc);
                return false;
            }
            else {
                _pc.setAu_AutoSkill(1, 1);
                if (_pc.getAu_AutoSkill(0) > 0) {
                    _pc.setAu_AutoSkill(0, 0);
                }
                _pc.sendPackets(new S_SkillBuy(_pc));
            }
        }
        else if (cmd.equalsIgnoreCase("naam14")) {
            _pc.setAu_AutoSkill(7, 2);
            _pc.get_other1().set_type25(2);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam15")) {
            _pc.setAu_AutoSkill(7, 3);
            _pc.get_other1().set_type25(3);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam16")) {
            _pc.setAu_AutoSkill(7, 4);
            _pc.get_other1().set_type25(4);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam17")) {
            _pc.setAu_AutoSkill(7, 5);
            _pc.get_other1().set_type25(5);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam18")) {
            _pc.setAu_AutoSkill(7, 6);
            _pc.get_other1().set_type25(6);
            this.Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam19")) {
            if (!CharSkillReading.get().spellCheck(_pc.getId(), 51)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習召喚術"));
                return false;
            }
            else if (!_pc.getInventory().checkItem(40318, 3L)) {
                _pc.sendPackets(new S_ServerMessage("魔法寶石不足,無法開啟"));
                return false;
            }
            else if (_pc.getsummon_skillid() == 0) {
                _pc.sendPackets(new S_SystemMessage("請先設定要治癒的技能"));
                return false;
            }
            else {
                if (_pc.getAu_AutoSkill(8) > 0) {
                    _pc.setAu_AutoSkill(8, 0);
                    _pc.get_other1().set_type26(0);
                }
                else {
                    _pc.setAu_AutoSkill(8, 1);
                    _pc.get_other1().set_type26(1);
                }
                this.Au_MagicSet_summ(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("Au_44")) {
            if (_pc.getsummon_skillid() == 1) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 19)) {
                    _pc.sendPackets(new S_SystemMessage("您尚未學習中治"));
                    return false;
                }
                else {
                    _pc.setsummon_skillid(19);
                    _pc.setsummon_skillidmp(15);
                    _pc.get_other1().set_type27(19);
                    _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
                }
            }
            else if (_pc.getsummon_skillid() == 19) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 35)) {
                    _pc.sendPackets(new S_SystemMessage("您尚未學習高治"));
                    return false;
                }
                else {
                    _pc.setsummon_skillid(35);
                    _pc.setsummon_skillidmp(20);
                    _pc.get_other1().set_type27(35);
                    _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
                }
            }
            else if (_pc.isWizard() && _pc.getsummon_skillid() == 35) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 57)) {
                    _pc.sendPackets(new S_SystemMessage("您尚未學習全治"));
                    _pc.sendPackets(new S_SystemMessage("將返回初治"));
                    _pc.setsummon_skillid(1);
                    _pc.setsummon_skillidmp(4);
                    _pc.get_other1().set_type27(1);
                    _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
                    this.Au_MagicSet_summ(_pc);
                    return false;
                }
                else {
                    _pc.setsummon_skillid(57);
                    _pc.setsummon_skillidmp(48);
                    _pc.get_other1().set_type27(57);
                    _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
                }
            }
            else if (_pc.isElf() && _pc.getsummon_skillid() == 35) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 164)) {
                    _pc.sendPackets(new S_SystemMessage("您尚未學習生祝.系統返回初治"));
                    _pc.setsummon_skillid(1);
                    _pc.setsummon_skillidmp(4);
                    _pc.get_other1().set_type27(1);
                    _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
                    this.Au_MagicSet_summ(_pc);
                    return false;
                }
                else {
                    _pc.setsummon_skillid(164);
                    _pc.setsummon_skillidmp(30);
                    _pc.get_other1().set_type27(164);
                    _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
                }
            }
            else if (_pc.isElf() && _pc.getsummon_skillid() == 164) {
                _pc.setsummon_skillid(1);
                _pc.setsummon_skillidmp(4);
                _pc.get_other1().set_type27(1);
                _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
            }
            else if (_pc.getsummon_skillid() == 57) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 49)) {
                    _pc.sendPackets(new S_SystemMessage("您尚未學習體回"));
                    _pc.sendPackets(new S_SystemMessage("將返回初治"));
                    _pc.setsummon_skillid(1);
                    _pc.setsummon_skillidmp(4);
                    _pc.get_other1().set_type27(1);
                    _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
                    this.Au_MagicSet_summ(_pc);
                    return false;
                }
                else {
                    _pc.setsummon_skillid(49);
                    _pc.setsummon_skillidmp(50);
                    _pc.get_other1().set_type27(49);
                    _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
                }
            }
            else if (_pc.getsummon_skillid() == 49) {
                _pc.setsummon_skillid(1);
                _pc.setsummon_skillidmp(4);
                _pc.get_other1().set_type27(1);
                _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
            }
            else if (_pc.getsummon_skillid() == 0) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 1)) {
                    _pc.sendPackets(new S_SystemMessage("您尚未學習初治"));
                    return false;
                }
                else {
                    _pc.setsummon_skillid(1);
                    _pc.setsummon_skillidmp(4);
                    _pc.get_other1().set_type27(1);
                    _pc.get_other1().set_type29(_pc.getsummon_skillidmp());
                }
            }
            this.Au_MagicSet_summ(_pc);
        }
        else if (cmd.equalsIgnoreCase("Au_46")) {
            if (!_pc.getInventory().checkEquipped(20284)) {
                _pc.sendPackets(new S_SystemMessage("未裝備[召喚控制戒指]"));
                return false;
            }
            else {
                _pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "guajisummon"));
            }
        }
        else if (cmd.equalsIgnoreCase("Au_47")) {
            _pc.setsummon_skillidmp_1(30);
            _pc.get_other1().set_type28(30);
            this.Au_MagicSet_summ(_pc);
        }
        else if (cmd.equalsIgnoreCase("Au_48")) {
            _pc.setsummon_skillidmp_1(40);
            _pc.get_other1().set_type28(40);
            this.Au_MagicSet_summ(_pc);
        }
        else if (cmd.equalsIgnoreCase("Au_49")) {
            _pc.setsummon_skillidmp_1(50);
            _pc.get_other1().set_type28(50);
            this.Au_MagicSet_summ(_pc);
        }
        else if (cmd.equalsIgnoreCase("Au_50")) {
            _pc.setsummon_skillidmp_1(60);
            _pc.get_other1().set_type28(60);
            this.Au_MagicSet_summ(_pc);
        }
        else if (cmd.equalsIgnoreCase("Au_51")) {
            _pc.setsummon_skillidmp_1(70);
            _pc.get_other1().set_type28(70);
            this.Au_MagicSet_summ(_pc);
        }
        else if (cmd.equalsIgnoreCase("summ_7")) {
            _pc.setSummon_npcid("7");
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_263")) {
            _pc.setSummon_npcid("263");
            this.Au_MagicSet_summ(_pc);
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_519")) {
            _pc.setSummon_npcid("519");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_8")) {
            _pc.setSummon_npcid("8");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_264")) {
            _pc.setSummon_npcid("264");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_520")) {
            _pc.setSummon_npcid("520");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_9")) {
            _pc.setSummon_npcid("9");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_265")) {
            _pc.setSummon_npcid("265");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_521")) {
            _pc.setSummon_npcid("521");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_10")) {
            _pc.setSummon_npcid("10");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_266")) {
            _pc.setSummon_npcid("266");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_522")) {
            _pc.setSummon_npcid("522");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_11")) {
            _pc.setSummon_npcid("11");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_267")) {
            _pc.setSummon_npcid("267");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_523")) {
            _pc.setSummon_npcid("523");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_12")) {
            _pc.setSummon_npcid("12");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_268")) {
            _pc.setSummon_npcid("268");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_524")) {
            _pc.setSummon_npcid("524");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_13")) {
            _pc.setSummon_npcid("13");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_269")) {
            _pc.setSummon_npcid("269");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_525")) {
            _pc.setSummon_npcid("525");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_14")) {
            _pc.setSummon_npcid("14");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_270")) {
            _pc.setSummon_npcid("270");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_526")) {
            _pc.setSummon_npcid("526");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_15")) {
            _pc.setSummon_npcid("15");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_271")) {
            _pc.setSummon_npcid("271");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_527")) {
            _pc.setSummon_npcid("527");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_16")) {
            _pc.setSummon_npcid("16");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_17")) {
            _pc.setSummon_npcid("17");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_18")) {
            _pc.setSummon_npcid("18");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("summ_274")) {
            _pc.setSummon_npcid("274");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            this.Au_MagicSet_summ(_pc);
            _pc.sendPackets(new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("AI4")) {
            this.Au_MagicSet_summ(_pc);
        }
        else if (cmd.equalsIgnoreCase("AI5")) {
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa0")) {
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa1")) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets(new S_ServerMessage("\\fU高寵狀態下禁止定點巡視。"));
                return false;
            }
            else {
                if (_pc.getAu_AutoSet(0) > 0) {
                    _pc.setAu_AutoSet(0, 0);
                    _pc.get_other1().set_type42(0);
                    _pc.setAutoX(0);
                    _pc.setAutoY(0);
                    _pc.setAutoMap(0);
                }
                else {
                    _pc.setAu_AutoSet(0, 1);
                    _pc.get_other1().set_type42(1);
                    _pc.setAu_AutoSet(3, 0);
                    _pc.get_other1().set_type43(0);
                    _pc.setAu_AutoSet(4, 0);
                    _pc.get_other1().set_type45(0);
                    _pc.setAu_AutoSet(5, 0);
                    _pc.get_other1().set_type44(0);
                }
                this.Au_AutoTeleport(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("naaa2")) {
            _pc.setAu_AutoSet(1, 5);
            _pc.get_other1().set_type41(5);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa3")) {
            _pc.setAu_AutoSet(1, 10);
            _pc.get_other1().set_type41(10);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa4")) {
            _pc.setAu_AutoSet(1, 15);
            _pc.get_other1().set_type41(15);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa5")) {
            _pc.setAu_AutoSet(1, 20);
            _pc.get_other1().set_type41(20);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa6")) {
            _pc.setAu_AutoSet(1, 25);
            _pc.get_other1().set_type41(25);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa7")) {
            _pc.setAu_AutoSet(1, 50);
            _pc.get_other1().set_type41(50);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa8")) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets(new S_ServerMessage("\\fU高寵狀態下禁止瞬移。"));
                return false;
            }
            else {
                _pc.setAu_AutoSet(3, 1);
                _pc.get_other1().set_type43(1);
                if (_pc.getAu_AutoSet(0) > 0) {
                    _pc.setAu_AutoSet(0, 0);
                    _pc.get_other1().set_type42(0);
                }
                this.Au_AutoTeleport(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("naaa9")) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets(new S_ServerMessage("\\fU高寵狀態下禁止瞬移。"));
                return false;
            }
            else {
                _pc.setAu_AutoSet(3, 2);
                _pc.get_other1().set_type43(2);
                if (_pc.getAu_AutoSet(0) > 0) {
                    _pc.setAu_AutoSet(0, 0);
                    _pc.get_other1().set_type42(0);
                }
                this.Au_AutoTeleport(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("naaa10")) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets(new S_ServerMessage("\\fU高寵狀態下禁止瞬移。"));
                return false;
            }
            else {
                _pc.setAu_AutoSet(3, 0);
                _pc.get_other1().set_type43(0);
                this.Au_AutoTeleport(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("naaa11")) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets(new S_ServerMessage("\\fU高寵狀態下禁止此設定。"));
                return false;
            }
            else {
                if (_pc.getAu_AutoSet(4) > 0) {
                    _pc.setAu_AutoSet(4, 0);
                    _pc.get_other1().set_type45(0);
                }
                else {
                    _pc.setAu_AutoSet(4, 1);
                    _pc.get_other1().set_type45(1);
                    _pc.setAu_AutoSet(0, 0);
                    _pc.get_other1().set_type42(0);
                    _pc.setAu_AutoSet(1, 0);
                    _pc.get_other1().set_type41(0);
                }
                this.Au_AutoTeleport(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("naaa12")) {
            _pc.setAu_AutoSet(5, 2);
            _pc.get_other1().set_type44(2);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa13")) {
            _pc.setAu_AutoSet(5, 3);
            _pc.get_other1().set_type44(3);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa14")) {
            _pc.setAu_AutoSet(5, 5);
            _pc.get_other1().set_type44(5);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa15")) {
            _pc.setAu_AutoSet(5, 10);
            _pc.get_other1().set_type44(10);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa16")) {
            _pc.setAu_AutoSet(5, 15);
            _pc.get_other1().set_type44(15);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa17")) {
            _pc.setAu_AutoSet(5, 20);
            _pc.get_other1().set_type44(20);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("naaa18")) {
            _pc.setAu_AutoSet(5, 30);
            _pc.get_other1().set_type44(30);
            this.Au_AutoTeleport(_pc);
        }
        else if (cmd.equalsIgnoreCase("AI6")) {
            this.Au_OtherSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naao1")) {
            if (_pc.getAu_OtherSet(0) > 0) {
                _pc.setAu_OtherSet(0, 0);
            }
            else {
                _pc.setAu_OtherSet(0, 1);
            }
            this.Au_OtherSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naao2")) {
            if (_pc.getAu_OtherSet(1) > 0) {
                _pc.setAu_OtherSet(1, 0);
                _pc.get_other1().set_type46(0);
            }
            else {
                _pc.setAu_OtherSet(1, 1);
                _pc.get_other1().set_type46(1);
            }
            this.Au_OtherSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naao3")) {
            this.EnemyList(_pc);
        }
        else if (cmd.equalsIgnoreCase("naao4")) {
            _pc.setKeyInEnemy(true);
            _pc.sendPackets(new S_ServerMessage("\\fU請輸入玩家名稱。"));
        }
        else if (cmd.equalsIgnoreCase("naao44")) {
            _pc.setKeyOutEnemy(true);
            _pc.sendPackets(new S_ServerMessage("\\fU請輸入清除仇人名稱。"));
        }
        else if (cmd.equalsIgnoreCase("naao5")) {
            if (_pc.getAu_OtherSet(2) > 0) {
                _pc.setAu_OtherSet(2, 0);
                _pc.get_other1().set_type47(0);
            }
            else {
                _pc.setAu_OtherSet(2, 1);
                _pc.get_other1().set_type47(1);
            }
            this.Au_OtherSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naao6")) {
            _pc.setAu_OtherSet(3, 0);
            this.Au_OtherSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naao7")) {
            if (_pc.getAu_OtherSet(3) > 0) {
                _pc.setAu_OtherSet(3, 0);
                _pc.get_other1().set_type48(0);
            }
            else {
                _pc.setAu_OtherSet(3, 1);
                _pc.get_other1().set_type48(1);
            }
            this.Au_OtherSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naao8")) {
            if (_pc.getAu_OtherSet(4) > 0) {
                _pc.setAu_OtherSet(4, 0);
                _pc.get_other1().set_type49(0);
            }
            else {
                _pc.setAu_OtherSet(4, 1);
                _pc.get_other1().set_type49(1);
            }
            this.Au_OtherSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naao9")) {
            _pc.setAu_OtherSet(5, 0);
            this.Au_OtherSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naao10")) {
            if (_pc.getAu_OtherSet(5) > 0) {
                _pc.setAu_OtherSet(5, 0);
                _pc.get_other1().set_type50(0);
            }
            else {
                _pc.get_other1().set_type50(1);
                _pc.setAu_OtherSet(5, 1);
            }
            this.Au_OtherSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("Au_6")) {
            NewAutoPractice.get().SearchAutoLog(_pc);
        }
        else {
            return false;
        }
        return true;
    }
    
    public void EnemyList(L1PcInstance pc) {
        StringBuilder msg = new StringBuilder();
        for (String name : pc.InEnemyList()) {
            msg.append(name + ",");
        }
        String[] clientStrAry = msg.toString().split(",");
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "x_autolist2", clientStrAry));
    }
    
    public void Au_MagicSet(L1PcInstance pc) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 2; i < 11; ++i) {
                switch (i) {
                    case 2: {
                        if (pc.getAu_AutoSkill(2) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 3: {
                        stringBuilder.append("【" + pc.getAu_AutoSkill(3) + "】,");
                        break;
                    }
                    case 4: {
                        if (pc.getAu_AutoSkill(4) > 0) {
                            L1Skills skill = SkillsTable.get().getTemplate(pc.getAu_AutoSkill(4));
                            if (skill != null) {
                                stringBuilder.append(skill.getName() + ",");
                                break;
                            }
                            else {
                                stringBuilder.append("【查無資料】,");
                                break;
                            }
                        }
                        else {
                            stringBuilder.append("【選擇】,");
                            break;
                        }
                    }
                    case 5: {
                        stringBuilder.append(String.valueOf(pc.getAu_AutoSkill(5)) + ",");
                        break;
                    }
                    case 6: {
                        if (pc.getAu_AutoSkill(6) > 0) {
                            L1Skills skill = SkillsTable.get().getTemplate(pc.getAu_AutoSkill(6));
                            if (skill != null) {
                                stringBuilder.append(skill.getName() + ",");
                                break;
                            }
                            else {
                                stringBuilder.append("【查無資料】,");
                                break;
                            }
                        }
                        else {
                            stringBuilder.append("【選擇】,");
                            break;
                        }
                    }
                    case 7: {
                        stringBuilder.append(String.valueOf(pc.getAu_AutoSkill(7)) + ",");
                        break;
                    }
                    case 8: {
                        if (pc.getAu_AutoSkill(8) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 9: {
                        if (pc.getsummon_skillid() == 1) {
                            stringBuilder.append("【初級治癒術】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 19) {
                            stringBuilder.append("【中級治癒術】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 35) {
                            stringBuilder.append("【高級治癒術】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 57) {
                            stringBuilder.append("【全部治癒術】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 164) {
                            stringBuilder.append("【生命的祝福】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 49) {
                            stringBuilder.append("【體力回復術】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【尚未設定】,");
                            break;
                        }
                    }
                    case 10: {
                        if (pc.getsummon_skillidmp_1() > 0) {
                            stringBuilder.append("【" + pc.getsummon_skillidmp_1() + "】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【未設】,");
                            break;
                        }
                    }
                }
            }
            String[] clientStrAry = stringBuilder.toString().split(",");
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "AI3", clientStrAry));
        }
        catch (Throwable t) {}
    }
    
    public void Au_MagicSet_summ(L1PcInstance pc) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 2; i < 11; ++i) {
                switch (i) {
                    case 2: {
                        if (pc.getAu_AutoSkill(2) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 3: {
                        stringBuilder.append("【" + pc.getAu_AutoSkill(3) + "】,");
                        break;
                    }
                    case 4: {
                        if (pc.getAu_AutoSkill(4) > 0) {
                            L1Skills skill = SkillsTable.get().getTemplate(pc.getAu_AutoSkill(4));
                            if (skill != null) {
                                stringBuilder.append(skill.getName() + ",");
                                break;
                            }
                            else {
                                stringBuilder.append("【查無資料】,");
                                break;
                            }
                        }
                        else {
                            stringBuilder.append("【選擇】,");
                            break;
                        }
                    }
                    case 5: {
                        stringBuilder.append(String.valueOf(pc.getAu_AutoSkill(5)) + ",");
                        break;
                    }
                    case 6: {
                        if (pc.getAu_AutoSkill(6) > 0) {
                            L1Skills skill = SkillsTable.get().getTemplate(pc.getAu_AutoSkill(6));
                            if (skill != null) {
                                stringBuilder.append(skill.getName() + ",");
                                break;
                            }
                            else {
                                stringBuilder.append("【查無資料】,");
                                break;
                            }
                        }
                        else {
                            stringBuilder.append("【選擇】,");
                            break;
                        }
                    }
                    case 7: {
                        stringBuilder.append(String.valueOf(pc.getAu_AutoSkill(7)) + ",");
                        break;
                    }
                    case 8: {
                        if (pc.getAu_AutoSkill(8) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 9: {
                        if (pc.getsummon_skillid() == 1) {
                            stringBuilder.append("【初級治癒術】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 19) {
                            stringBuilder.append("【中級治癒術】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 35) {
                            stringBuilder.append("【高級治癒術】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 57) {
                            stringBuilder.append("【全部治癒術】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 164) {
                            stringBuilder.append("【生命的祝福】,");
                            break;
                        }
                        else if (pc.getsummon_skillid() == 49) {
                            stringBuilder.append("【體力回復術】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【尚未設定】,");
                            break;
                        }
                    }
                    case 10: {
                        if (pc.getsummon_skillidmp_1() > 0) {
                            stringBuilder.append("【" + pc.getsummon_skillidmp_1() + "】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【未設】,");
                            break;
                        }
                    }
                }
            }
            String[] clientStrAry = stringBuilder.toString().split(",");
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "AI4", clientStrAry));
        }
        catch (Throwable t) {}
    }
    
    public void Au_Shop(L1PcInstance pc) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            if (pc.IsAu_Shop()) {
                stringBuilder.append("【開啟】,");
            }
            else {
                stringBuilder.append("【關閉】,");
            }
            for (int i = 0; i < 9; ++i) {
                switch (pc.getAu_BuyItemSwitch(i)) {
                    case 0: {
                        stringBuilder.append(String.valueOf(pc.getAu_BuyItemCount(i)) + ",");
                        stringBuilder.append("【關閉】,");
                        break;
                    }
                    case 1: {
                        stringBuilder.append(String.valueOf(pc.getAu_BuyItemCount(i)) + ",");
                        stringBuilder.append("【開啟】,");
                        break;
                    }
                }
            }
            String[] clientStrAry = stringBuilder.toString().split(",");
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "AI2", clientStrAry));
        }
        catch (Throwable t) {}
    }
    
    public boolean CheckAreaAttackSkill(int Skillid) {
        Iterator<Integer> iterator = ConfigGuaji.AREA_SKILL.iterator();
        while (iterator.hasNext()) {
            int skillId = (Integer)iterator.next();
            if (skillId == Skillid) {
                return true;
            }

        }
        return false;
    }
    
    public boolean CheckAttackSkill(int Skillid) {
        Iterator<Integer> iterator = ConfigGuaji.USE_SKILL.iterator();
        while (iterator.hasNext()) {
            int skillId = (Integer)iterator.next();
            if (skillId == Skillid) {
                return true;
            }
        }
        return false;
    }
    
    public void Au_AutoTeleport(L1PcInstance pc) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 10; ++i) {
                switch (i) {
                    case 0: {
                        if (pc.getAu_AutoSet(i) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 1: {
                        stringBuilder.append(String.valueOf(pc.getAu_AutoSet(i)) + ",");
                        break;
                    }
                    case 2: {
                        if (pc.getAu_AutoSet(i) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 3: {
                        if (pc.getAu_AutoSet(i) == 0) {
                            stringBuilder.append("【無】,");
                            break;
                        }
                        else if (pc.getAu_AutoSet(i) == 1) {
                            stringBuilder.append("【指定傳送】,");
                            break;
                        }
                        else if (pc.getAu_AutoSet(i) == 2) {
                            stringBuilder.append("【瞬移卷軸】,");
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 4: {
                        if (pc.getAu_AutoSet(i) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 5: {
                        stringBuilder.append(String.valueOf(pc.getAu_AutoSet(i)) + ",");
                        break;
                    }
                    case 6: {
                        if (pc.getAu_AutoSet(i) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 7: {
                        stringBuilder.append(String.valueOf(pc.getAu_AutoSet(i)) + ",");
                        break;
                    }
                }
            }
            String[] clientStrAry = stringBuilder.toString().split(",");
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "AI5", clientStrAry));
        }
        catch (Throwable t) {}
    }
    
    public void Au_OtherSet(L1PcInstance pc) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 8; ++i) {
                switch (i) {
                    case 0: {
                        if (pc.getAu_OtherSet(i) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 1: {
                        if (pc.getAu_OtherSet(i) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 2: {
                        if (pc.getAu_OtherSet(i) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 3: {
                        if (pc.getAu_OtherSet(i) > 0) {
                            stringBuilder.append("【瞬移】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 4: {
                        if (pc.getAu_OtherSet(i) > 0) {
                            stringBuilder.append("【開啟】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【關閉】,");
                            break;
                        }
                    }
                    case 5: {
                        if (pc.getAu_OtherSet(i) > 0) {
                            stringBuilder.append("【瞬移】,");
                            break;
                        }
                        else {
                            stringBuilder.append("【未設定】,");
                            break;
                        }
                    }
                }
            }
            String[] clientStrAry = stringBuilder.toString().split(",");
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "AI6", clientStrAry));
        }
        catch (Throwable t) {}
    }

}

package com.lineage;

import java.sql.Timestamp;
import com.lineage.data.item_etcitem.Z1BagSort;
import com.lineage.server.datatables.lock.VIPReading;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.model.L1PetMatch;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.utils.FaceToFace;
import com.add.L1PcUnlock;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import java.util.logging.Logger;

public class FollowAutoAttackUpdate
{
    private static Logger _log;
    private static FollowAutoAttackUpdate _instance;
    
    static {
        FollowAutoAttackUpdate._log = Logger.getLogger(FollowAutoAttackUpdate.class.getName());
    }
    
    public static FollowAutoAttackUpdate getInstance() {
        if (FollowAutoAttackUpdate._instance == null) {
            FollowAutoAttackUpdate._instance = new FollowAutoAttackUpdate();
        }
        return FollowAutoAttackUpdate._instance;
    }
    
    public boolean PcCommand(L1PcInstance _pc, String cmd) {
        if (cmd.equalsIgnoreCase("followatk")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("您不是高寵狀態無法設定。"));
                return false;
            }
            else if (_pc.getfollowatk()) {
                _pc.setfollowatk(false);
                if (_pc.isActived()) {
                    _pc.setActived(false);
                    L1PcUnlock.Pc_Unlock(_pc);
                    if (_pc.getAutoX() > 0) {
                        _pc.setAutoX(0);
                        _pc.setAutoY(0);
                        _pc.setAutoMap(0);
                    }
                    _pc.killSkillEffectTimer(8853);
                }
                _pc.sendPackets(new S_SystemMessage("高寵自動打怪：關閉"));
            }
            else {
                _pc.setfollowatk(true);
                if (!_pc.isActived()) {
                    _pc.startAI();
                }
                _pc.sendPackets(new S_SystemMessage("高寵自動打怪：開啟"));
            }
        }
        else if (cmd.equalsIgnoreCase("followatkmagic")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("您不是高寵狀態無法設定。"));
                return false;
            }
            else if (_pc.getfollowatkmagic()) {
                _pc.setfollowatkmagic(false);
                _pc.sendPackets(new S_SystemMessage("高寵自動打怪施放攻擊魔法：關閉"));
            }
            else {
                _pc.setfollowatkmagic(true);
                _pc.sendPackets(new S_SystemMessage("高寵自動打怪施放攻擊魔法：開啟"));
            }
        }
        else if (cmd.equals("followmecheck")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (_pc.isfollowcheck()) {
                if (_pc.get_followmaster() != null && _pc.getfollowatk()) {
                    _pc.setfollowatk(false);
                    if (_pc.isActived()) {
                        _pc.setActived(false);
                        L1PcUnlock.Pc_Unlock(_pc);
                        if (_pc.getAutoX() > 0) {
                            _pc.setAutoX(0);
                            _pc.setAutoY(0);
                            _pc.setAutoMap(0);
                        }
                        _pc.killSkillEffectTimer(8853);
                    }
                }
                _pc.setfollowcheck(false);
                _pc.set_followmaster(null);
                _pc.setfollow(false);
                _pc.sendPackets(new S_SystemMessage("[關閉]禁止高寵跟隨狀態"));
                L1PcUnlock.Pc_Unlock(_pc);
                this.followpc(_pc);
            }
            else {
                _pc.setfollowcheck(true);
                _pc.sendPackets(new S_SystemMessage("[開啟]允許高寵跟隨狀態"));
                this.followpc(_pc);
            }
        }
        else if (cmd.equals("followme")) {
            // 原本90116
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("缺少高寵啟動道具(90116)"));
                return false;
            } else {
                L1PcInstance target = FaceToFace.faceToFace(_pc);
                if (target != null) {
                    // 高寵啟動限制：檢查雙方是否持有有效 VIP 月卡
                    boolean pcUnlocked = hasActiveVip(_pc);
                    boolean targetUnlocked = hasActiveVip(target);

                    if (!pcUnlocked && !targetUnlocked) {
                        _pc.sendPackets(new S_SystemMessage("啟動高寵，至少一方需持有 VIP 月卡"));
                        _pc.sendPackets(new S_SystemMessage("\\fU提醒:月卡可藉由簽到累積進行月卡製作"));
                        return false;
                    }

                    if (!target.isfollowcheck()) {
                        target.setfollowcheck(true);
                    }
                    else if (!_pc.isfollowcheck()) {
                        _pc.sendPackets(new S_ServerMessage("您目前關閉高寵狀態,請先開啟。"));
                        return false;
                    }

                    else if (target.get_followmaster() != null) {
                        _pc.sendPackets(new S_ServerMessage("你指定的對象已有追隨者。"));
                        return false;
                    }
                    else if (_pc.get_followmaster() != null) {
                        _pc.sendPackets(new S_ServerMessage("你已有追隨者。"));
                        return false;
                    }
                    else if (target.isfollow()) {
                        _pc.sendPackets(new S_ServerMessage("你指定的對象已有追隨者。"));
                        return false;
                    }
                    else if (_pc.isfollow()) {
                        _pc.sendPackets(new S_ServerMessage("你已有追隨者,請重新關閉再開啟。"));
                        return false;
                    }
                    else {
                        _pc.set_followmaster(target);
                        target.setfollow(true);
                        _pc.sendPackets(new S_ServerMessage("您成為:[" + target.getName() + "]高寵補助"));
                        target.sendPackets(new S_ServerMessage("[" + _pc.getName() + " ]成為您的高寵補助"));
						
                    }
                }
            }
        }
        else if (cmd.equals("followmeclose")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (_pc.hasSkillEffect(95414)) {
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_SystemMessage("您尚未有跟隨主人對象"));
                return false;
            }
            else {
                _pc.setSkillEffect(95414, 10000);
                _pc.set_followmaster(null);
                _pc.set_followstep(0);
                _pc.setfollow(false);
                L1PcUnlock.Pc_Unlock(_pc);
                L1Teleport.teleport(_pc, _pc.getLocation(), _pc.getHeading(), false);
                _pc.sendPackets(new S_SystemMessage("[關閉]高寵跟隨輔助"));
            }
        }
        else if (cmd.equals("followmeskill")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else {
                if (_pc.getfollowskilltype() == 0) {
                    _pc.setfollowskilltype(1);
                }
                else if (_pc.getfollowskilltype() == 1) {
                    _pc.setfollowskilltype(2);
                }
                else if (_pc.getfollowskilltype() == 2) {
                    _pc.setfollowskilltype(3);
                }
                else if (_pc.getfollowskilltype() == 3) {
                    _pc.setfollowskilltype(4);
                }
                else if (_pc.getfollowskilltype() == 4) {
                    _pc.setfollowskilltype(0);
                }
                if (_pc.getfollowskilltype() == 1 && !CharSkillReading.get().spellCheck(_pc.getId(), 1)) {
                    _pc.setfollowskilltype(0);
                    _pc.sendPackets(new S_SystemMessage("您尚未學習初級治癒術"));
                    this.followpc(_pc);
                }
                if (_pc.getfollowskilltype() == 2 && !CharSkillReading.get().spellCheck(_pc.getId(), 19)) {
                    _pc.setfollowskilltype(0);
                    _pc.sendPackets(new S_SystemMessage("您尚未學習中級治癒術"));
                    this.followpc(_pc);
                }
                if (_pc.getfollowskilltype() == 3 && !CharSkillReading.get().spellCheck(_pc.getId(), 35)) {
                    _pc.setfollowskilltype(0);
                    _pc.sendPackets(new S_SystemMessage("您尚未學習高級治癒術"));
                    this.followpc(_pc);
                }
                if (_pc.getfollowskilltype() == 4 && !CharSkillReading.get().spellCheck(_pc.getId(), 57)) {
                    _pc.setfollowskilltype(0);
                    _pc.sendPackets(new S_SystemMessage("您尚未學習全部治癒術"));
                    this.followpc(_pc);
                }
                this.followpc(_pc);
            }
        }
        else if (cmd.equals("followmeHp1")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (_pc.getfollowskillhp() >= 100) {
                return false;
            }
            else {
                _pc.setfollowskillhp(_pc.getfollowskillhp() + 5);
                this.followpc(_pc);
            }
        }
        else if (cmd.equals("followmeHp2")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (_pc.getfollowskillhp() == 0) {
                return false;
            }
            else {
                _pc.setfollowskillhp(_pc.getfollowskillhp() - 5);
                this.followpc(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("skill1")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (!CharSkillReading.get().spellCheck(_pc.getId(), 42)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習體魄術"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (_pc.isfollowskill42()) {
                _pc.setfollowskill42(false);
                _pc.sendPackets(new S_SystemMessage("關閉自動補助技能:體魄術"));
                this.followpc(_pc);
            }
            else {
                _pc.setfollowskill42(true);
                _pc.sendPackets(new S_SystemMessage("開啟自動補助技能:體魄術"));
                this.followpc(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("skill2")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (!CharSkillReading.get().spellCheck(_pc.getId(), 26)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習通暢術"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (_pc.isfollowskill26()) {
                _pc.setfollowskill26(false);
                _pc.sendPackets(new S_SystemMessage("關閉自動補助技能:通暢術"));
                this.followpc(_pc);
            }
            else {
                _pc.setfollowskill26(true);
                _pc.sendPackets(new S_SystemMessage("開啟自動補助技能:通暢術"));
                this.followpc(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("skill3")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (!CharSkillReading.get().spellCheck(_pc.getId(), 55)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習狂暴術"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (!_pc.isWizard()) {
                _pc.sendPackets(new S_SystemMessage("您不是法師,無法開啟狂暴術"));
                return false;
            }
            else if (_pc.isfollowskill55()) {
                _pc.setfollowskill55(false);
                _pc.sendPackets(new S_SystemMessage("關閉自動補助技能:狂暴術"));
                this.followpc(_pc);
            }
            else {
                _pc.setfollowskill55(true);
                _pc.sendPackets(new S_SystemMessage("開啟自動補助技能:狂暴術"));
                this.followpc(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("skill4")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (!CharSkillReading.get().spellCheck(_pc.getId(), 68)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習聖結界"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (_pc.isfollowskill68()) {
                _pc.setfollowskill68(false);
                _pc.sendPackets(new S_SystemMessage("關閉自動補助技能:聖結界"));
                this.followpc(_pc);
            }
            else {
                _pc.setfollowskill68(true);
                _pc.sendPackets(new S_SystemMessage("開啟自動補助技能:聖結界"));
                this.followpc(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("skill6")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (!CharSkillReading.get().spellCheck(_pc.getId(), 79)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習靈魂昇華"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (!_pc.isWizard()) {
                _pc.sendPackets(new S_SystemMessage("您不是法師,無法開啟靈魂昇華"));
                return false;
            }
            else if (_pc.isfollowskill79()) {
                _pc.setfollowskill79(false);
                _pc.sendPackets(new S_SystemMessage("關閉自動補助技能:靈魂昇華"));
                this.followpc(_pc);
            }
            else {
                _pc.setfollowskill79(true);
                _pc.sendPackets(new S_SystemMessage("開啟自動補助技能:靈魂昇華"));
                this.followpc(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("skill7")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (!CharSkillReading.get().spellCheck(_pc.getId(), 148)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習火焰武器"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (_pc.isfollowskill148()) {
                _pc.setfollowskill148(false);
                _pc.sendPackets(new S_SystemMessage("關閉自動補助技能:火焰武器"));
                this.followpc(_pc);
            }
            else {
                _pc.setfollowskill148(true);
                _pc.sendPackets(new S_SystemMessage("開啟自動補助技能:火焰武器"));
                this.followpc(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("skill8")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (!CharSkillReading.get().spellCheck(_pc.getId(), 151)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習大地防護"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (_pc.isfollowskill151()) {
                _pc.setfollowskill151(false);
                _pc.sendPackets(new S_SystemMessage("關閉自動補助技能:大地防護"));
                this.followpc(_pc);
            }
            else {
                _pc.setfollowskill151(true);
                _pc.sendPackets(new S_SystemMessage("開啟自動補助技能:大地防護"));
                this.followpc(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("skill9")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (!CharSkillReading.get().spellCheck(_pc.getId(), 149)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習風之神射"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (_pc.isfollowskill149()) {
                _pc.setfollowskill149(false);
                _pc.sendPackets(new S_SystemMessage("關閉自動補助技能:風之神射"));
                this.followpc(_pc);
            }
            else {
                _pc.setfollowskill149(true);
                _pc.sendPackets(new S_SystemMessage("開啟自動補助技能:風之神射"));
                this.followpc(_pc);
            }
        }
        else if (cmd.equalsIgnoreCase("skill10")) {
            L1ItemInstance AiITEM = _pc.getInventory().findItemId(90116);
            if (AiITEM == null) {
                _pc.sendPackets(new S_SystemMessage("休想竄改對話檔,我已經紀錄你了。"));
                return false;
            }
            else if (!CharSkillReading.get().spellCheck(_pc.getId(), 158)) {
                _pc.sendPackets(new S_SystemMessage("您尚未學習生命之泉"));
                return false;
            }
            else if (_pc.get_followmaster() == null) {
                _pc.sendPackets(new S_ServerMessage("你不是高寵無法使用此功能。"));
                return false;
            }
            else if (_pc.isfollowskill158()) {
                _pc.setfollowskill158(false);
                _pc.sendPackets(new S_SystemMessage("關閉自動補助技能:生命之泉"));
                this.followpc(_pc);
            }
            else {
                _pc.setfollowskill158(true);
                _pc.sendPackets(new S_SystemMessage("開啟自動補助技能:生命之泉"));
                this.followpc(_pc);
            }
        }
if (cmd.equalsIgnoreCase("followpc")) {
    this.showHtml(_pc);
    return true;
}

if (cmd.equalsIgnoreCase("bosslist")) {
    com.lineage.data.item_etcitem.ItemBoss.get().showBossList(_pc);
    return true;
}

if (cmd.equalsIgnoreCase("tfcount")) {
    com.add.TFUpdate.getInstance().TF(_pc);
    return true;
}

if (cmd.equalsIgnoreCase("tfreset") || cmd.equalsIgnoreCase("tfre")) {
    com.add.TFUpdate.getInstance().resetTalent(_pc);
    return true;
}

if (cmd.equalsIgnoreCase("exptime")) {
    ((com.lineage.data.item_etcitem.event.ExpTime)
        com.lineage.data.item_etcitem.event.ExpTime.get())
        .showExpTime(_pc);
    return true;
}

if (cmd.equalsIgnoreCase("pcmenaother")) {
    ((com.lineage.data.item_etcitem.PcMenaOther) 
        com.lineage.data.item_etcitem.PcMenaOther.get())
        .showPcMenaOther(_pc);
    return true;
}
if (cmd.equalsIgnoreCase("bagsort")) {
    Z1BagSort.get().execute(null, _pc, null);
    return true;
}
if (cmd.equalsIgnoreCase("selloptmode")) {
    com.lineage.data.item_etcitem.Z1itemationPotion.cycleSellMode();
    _pc.sendPackets(new S_SystemMessage("一鍵出售模式：[" + com.lineage.data.item_etcitem.Z1itemationPotion.getSellModeLabel() + "]"));
    return true;
}
if (cmd.equalsIgnoreCase("selloptgo")) {
    com.lineage.data.item_etcitem.Z1itemationPotion.get().execute(null, _pc, null);
    return true;
}
if (cmd.equalsIgnoreCase("selloptstatus")) {
    _pc.sendPackets(new S_SystemMessage(com.lineage.data.item_etcitem.Z1itemationPotion.getSettingsSummary()));
    return true;
}
if (cmd.equalsIgnoreCase("pattern")) {
    com.lineage.server.serverpackets.S_NPCTalkReturn packet =
        new com.lineage.server.serverpackets.S_NPCTalkReturn(_pc.getId(), "patternlist");
    _pc.sendPackets(packet);
    return true;
}
//城堡時間
if (cmd.equalsIgnoreCase("castlelist")) {

    String[] data = com.add.L1CastleTime.getCastleTime();

    _pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "castlelist", data));
    return true;
}
//任務狀態
if (cmd.equalsIgnoreCase("mainquest")) {

    String[] data = com.add.L1QuestNow.getNowQuest(_pc);

    _pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "mainquest", data));
    return true;
}
//綜合排行榜
if (cmd.equalsIgnoreCase("allranking")) {
	// 檢查並刪除上一個召喚的 NPC
	if (_pc.get_temp_spawn_npc() != null) {
		_pc.get_temp_spawn_npc().deleteMe();
		_pc.set_temp_spawn_npc(null);
	}

	// 綜合排行榜 NPC 的 ID
	final int npcId = 190001;

	// 在玩家身邊產生一個暫時的 NPC
	final com.lineage.server.model.Instance.L1NpcInstance npc =
		com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

	if (npc == null) {
		_pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟排行榜，請稍後再試。"));
		return true;
	}

	// 儲存這次召喚的 NPC
	_pc.set_temp_spawn_npc(npc);

	// 將 NPC 設定為跟隨玩家
	npc.setMaster(_pc);

	// 直接呼叫 Npc_AllRanking 的 talk 方法，並傳入新產生的 NPC
	com.lineage.data.npc.Npc_AllRanking.get().talk(_pc, npc);

	// 60 秒後自動刪除這個暫時的 NPC
	com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
	return true;
}

//交易所
if (cmd.equalsIgnoreCase("y_x_e")) {
	// 綜合排行榜 NPC 的 ID
	final int npcId = 70535;

	// 在玩家身邊產生一個暫時的 NPC
	final com.lineage.server.model.Instance.L1NpcInstance npc =
		com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

	if (npc == null) {
		_pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟交易所，請稍後再試。"));
		return true;
	}

	// 儲存這次召喚的 NPC
	_pc.set_temp_spawn_npc(npc);

	// 將 NPC 設定為跟隨玩家
	npc.setMaster(_pc);

	// 直接呼叫 Npc_ShopX 的 talk 方法，並傳入新產生的 NPC
	com.lineage.data.npc.shop.Npc_ShopX.get().talk(_pc, npc);


	// 120 秒後自動刪除這個暫時的 NPC
	com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 120000L);
	return true;
}

// 雜貨回收商人
if (cmd.equalsIgnoreCase("tzmerchant")) {
	// 檢查並刪除上一個召喚的 NPC
	if (_pc.get_temp_spawn_npc() != null) {
		_pc.get_temp_spawn_npc().deleteMe();
		_pc.set_temp_spawn_npc(null);
	}

	// 雜貨回收商人 NPC 的 ID
	final int npcId = 93011;

	// 在玩家身邊產生一個暫時的 NPC
	final com.lineage.server.model.Instance.L1NpcInstance npc =
		com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

	if (npc == null) {
		_pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法召喚雜貨回收商人，請稍後再試。"));
		return true;
	}

	// 儲存這次召喚的 NPC
	_pc.set_temp_spawn_npc(npc);

	// 將 NPC 設定為跟隨玩家
	npc.setMaster(_pc);

	// 直接呼叫 Npc_Recycling 的 talk 方法，並傳入新產生的 NPC
	com.lineage.data.npc.Npc_Recycling.get().talk(_pc, npc);

	// 120 秒後自動刪除這個暫時的 NPC
	com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
	return true;
}

// 商品領取
if (cmd.equalsIgnoreCase("y_e_1")) {
	final int npcId = 93055;
	final com.lineage.server.model.Instance.L1NpcInstance npc = com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);
	if (npc != null) {
		com.lineage.data.npc.shop.NPC_Ezpay.get().talk(_pc, npc);
		com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 10000L);
	}
	return true;
}

// 贊助領取
if (cmd.equalsIgnoreCase("y_e_2")) {
	final int npcId = 93060;
	final com.lineage.server.model.Instance.L1NpcInstance npc = com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);
	if (npc != null) {
		com.lineage.data.npc.shop.NPC_Ezpay_1.get().talk(_pc, npc);
		com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 10000L);
	}
	return true;
}

// 倉庫
if (cmd.equalsIgnoreCase("storage")) {

    // 倉庫開啟限制：需持有有效 VIP 月卡
	if (_pc.getNetConnection() == null ||
		_pc.getNetConnection().getAccount() == null ||
        !hasActiveVip(_pc)
	) {
        _pc.sendPackets(new S_SystemMessage("遠程倉庫/盟倉，未解鎖（需VIP 月卡）"));
        _pc.sendPackets(new S_SystemMessage("\\fU提醒:月卡可藉由簽到累積進行月卡製作"));
		return true;
	}
	final int npcId = 60024;
	final com.lineage.server.model.Instance.L1NpcInstance npc = com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);
	if (npc != null) {
		_pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "storage"));
		com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 120000L);
	}
	return true;
}

// 每日簽到
if (cmd.equalsIgnoreCase("dailybonus")) {
    // 檢查並刪除上一個召喚的 NPC
    if (_pc.get_temp_spawn_npc() != null) {
        _pc.get_temp_spawn_npc().deleteMe();
        _pc.set_temp_spawn_npc(null);
    }

    // 每日簽到 NPC 的 ID (93057)
    final int npcId = 93057;

    // 在玩家身邊產生一個暫時的 NPC
    final com.lineage.server.model.Instance.L1NpcInstance npc =
        com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

    if (npc == null) {
        _pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟每日簽到，請稍後再試。"));
        return true;
    }

    // 儲存這次召喚的 NPC
    _pc.set_temp_spawn_npc(npc);

    // 將 NPC 設定為跟隨玩家
    npc.setMaster(_pc);

    // 直接呼叫 Npc_dailybonus 的 talk 方法，並傳入新產生的 NPC
    com.lineage.data.npc.Npc_dailybonus.get().talk(_pc, npc);

    // 60 秒後自動刪除這個暫時的 NPC
    com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
    return true;
}

// 每日問答
if (cmd.equalsIgnoreCase("quiz")) {
    if (_pc.get_temp_spawn_npc() != null) {
        _pc.get_temp_spawn_npc().deleteMe();
        _pc.set_temp_spawn_npc(null);
    }

    final int npcId = 93052;
    final com.lineage.server.model.Instance.L1NpcInstance npc =
        com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

    if (npc == null) {
        _pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟每日問答，請稍後再試。"));
        return true;
    }

    _pc.set_temp_spawn_npc(npc);
    npc.setMaster(_pc);

    com.lineage.data.npc.gam.Npc_QuizSet.get().talk(_pc, npc);

    com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
    return true;
}

// 賭博
if (cmd.equalsIgnoreCase("dubogame")) {
    if (_pc.get_temp_spawn_npc() != null) {
        _pc.get_temp_spawn_npc().deleteMe();
        _pc.set_temp_spawn_npc(null);
    }

    final int npcId = 93073;
    final com.lineage.server.model.Instance.L1NpcInstance npc =
        com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

    if (npc == null) {
        _pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟賭博，請稍後再試。"));
        return true;
    }

    _pc.set_temp_spawn_npc(npc);
    npc.setMaster(_pc);

    com.lineage.data.npc.shop.Npc_DuBo.get().talk(_pc, npc);

    com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
    return true;
}

// 剪刀石頭布遠端召喚
if (cmd.equalsIgnoreCase("rpsgame")) {
    if (_pc.get_temp_spawn_npc() != null) {
        _pc.get_temp_spawn_npc().deleteMe();
        _pc.set_temp_spawn_npc(null);
    }

    final int npcId = 93078; // 您的猜拳 NPC 編號
    final com.lineage.server.model.Instance.L1NpcInstance npc =
        com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

    if (npc == null) {
        _pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟猜拳對決，請稍後再試。"));
        return true;
    }

    _pc.set_temp_spawn_npc(npc);
    npc.setMaster(_pc);

    // 呼叫猜拳 NPC 的 talk 方法，自動彈出 rps.html 面板
    com.lineage.data.npc.shop.Npc_RpsNpc.get().talk(_pc, npc);

    // 因為猜拳不需要像骰寶那樣看 17 秒的骰子特效，可以設定存在 30 秒或 60 秒後自動刪除
    com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
    return true;
}

// 彩券遠端召喚
if (cmd.equalsIgnoreCase("lotterygame")) {
    if (_pc.get_temp_spawn_npc() != null) {
        _pc.get_temp_spawn_npc().deleteMe();
        _pc.set_temp_spawn_npc(null);
    }

    final int npcId = 93079; // 彩券 NPC 編號
    final com.lineage.server.model.Instance.L1NpcInstance npc =
        com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

    if (npc == null) {
        _pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟彩券中心，請稍後再試。"));
        return true;
    }

    _pc.set_temp_spawn_npc(npc);
    npc.setMaster(_pc);

    // 呼叫彩券 NPC 的 talk 方法，自動彈出 lottery.html 面板
    com.lineage.data.npc.shop.Npc_LotteryNpc.get().talk(_pc, npc);

    com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
    return true;
}

// 怪物擂台遠端召喚
if (cmd.equalsIgnoreCase("arenagame")) {
    if (_pc.get_temp_spawn_npc() != null) {
        _pc.get_temp_spawn_npc().deleteMe();
        _pc.set_temp_spawn_npc(null);
    }

    final int npcId = 93080; // 怪物擂台 NPC 編號
    final com.lineage.server.model.Instance.L1NpcInstance npc =
        com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

    if (npc == null) {
        _pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟怪物擂台，請稍後再試。"));
        return true;
    }

    _pc.set_temp_spawn_npc(npc);
    npc.setMaster(_pc);

    // 呼叫怪物擂台 NPC 的 talk 方法，自動彈出 arena.html 面板
    com.lineage.data.npc.shop.Npc_MonsterArena.get().talk(_pc, npc);

    com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
    return true;
}

// 3v3 怪物擂台遠端召喚
if (cmd.equalsIgnoreCase("teamarenagame")) {
    if (_pc.get_temp_spawn_npc() != null) {
        _pc.get_temp_spawn_npc().deleteMe();
        _pc.set_temp_spawn_npc(null);
    }

    final int npcId = 93083; // 3v3 怪物擂台 NPC 編號
    final com.lineage.server.model.Instance.L1NpcInstance npc =
        com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

    if (npc == null) {
        _pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟 3v3 怪物擂台，請稍後再試。"));
        return true;
    }

    _pc.set_temp_spawn_npc(npc);
    npc.setMaster(_pc);

    // 呼叫 3v3 怪物擂台 NPC 的 talk 方法，自動彈出 team_arena.html 面板
    com.lineage.data.npc.shop.Npc_TeamMonsterArena.get().talk(_pc, npc);

    com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
    return true;
}

// 寵物對決遠端召喚
if (cmd.equalsIgnoreCase("petmatchgame")) {
    if (_pc.get_temp_spawn_npc() != null) {
        _pc.get_temp_spawn_npc().deleteMe();
        _pc.set_temp_spawn_npc(null);
    }

    final int npcId = 80088; // 寵物對決 NPC 編號
    final com.lineage.server.model.Instance.L1NpcInstance npc =
        com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

    if (npc == null) {
        _pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟寵物對決，請稍後再試。"));
        return true;
    }

    _pc.set_temp_spawn_npc(npc);
    npc.setMaster(_pc);

    // 直接呼叫原生的 talk 方法或直接彈出對話視窗
    com.lineage.server.serverpackets.S_NPCTalkReturn talkPacket = new com.lineage.server.serverpackets.S_NPCTalkReturn(npc.getId(), "petmatcher", new String[] {});
    _pc.sendPackets(talkPacket);

    com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
    return true;
}

// 媽祖遠端召喚
if (cmd.equalsIgnoreCase("mazugame")) {
    if (_pc.get_temp_spawn_npc() != null) {
        _pc.get_temp_spawn_npc().deleteMe();
        _pc.set_temp_spawn_npc(null);
    }

    final int npcId = 93068;
    final com.lineage.server.model.Instance.L1NpcInstance npc =
        com.lineage.server.utils.L1SpawnUtil.spawn(npcId, _pc.getX(), _pc.getY(), _pc.getMapId(), 5, 6262);

    if (npc == null) {
        _pc.sendPackets(new com.lineage.server.serverpackets.S_SystemMessage("無法開啟媽祖面板，請稍後再試。"));
        return true;
    }

    _pc.set_temp_spawn_npc(npc);
    npc.setMaster(_pc);

    // 呼叫媽祖 NPC 的 talk 方法，自動依狀態彈出對話面板
    com.lineage.data.npc.event.Npc_Mazu.get().talk(_pc, npc);

    com.lineage.server.thread.GeneralThreadPool.get().schedule(() -> { if (npc != null) { npc.deleteMe(); } }, 60000L);
    return true;
}

// ⭐ 最後只留這個
return false;

}
    public void showHtml(L1PcInstance _pc) {
    try {
        String type1 = "尚未設置";
        String type2 = "0";
        String type3 = "禁止跟隨";
        String type4 = "關閉";
        String type5 = "關閉";
        String type6 = "關閉";
        String type7 = "關閉";
        String type8 = "關閉";
        String type9 = "關閉";
        String type10 = "關閉";
        String type11 = "關閉";
        String type12 = "關閉";
        String type13 = "關閉";

        if (_pc.getfollowskilltype() == 1) type1 = "初級治癒術";
        else if (_pc.getfollowskilltype() == 2) type1 = "中級治癒術";
        else if (_pc.getfollowskilltype() == 3) type1 = "高級治癒術";
        else if (_pc.getfollowskilltype() == 4) type1 = "全部治癒術";

        if (_pc.getfollowskillhp() > 0) {
            type2 = String.valueOf(_pc.getfollowskillhp());
        }

        if (_pc.isfollowcheck()) type3 = "允許跟隨";
        if (_pc.isfollowskill42()) type4 = "開啟";
        if (_pc.isfollowskill26()) type5 = "開啟";
        if (_pc.isfollowskill55()) type6 = "開啟";
        if (_pc.isfollowskill68()) type7 = "開啟";
        if (_pc.isfollowskill79()) type8 = "開啟";
        if (_pc.isfollowskill148()) type9 = "開啟";
        if (_pc.isfollowskill151()) type10 = "開啟";
        if (_pc.isfollowskill149()) type11 = "開啟";
        if (_pc.isfollowskill158()) type12 = "開啟";

        _pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "Lh8",
            new String[]{type1, type2, type3, type4, type5, type6,
                         type7, type8, type9, type10, type11, type12, type13}));

    } catch (Exception e) {}
}
    public void followpc(L1PcInstance _pc) {
        try {
            String type1 = "尚未設置";
            String type2 = "0";
            String type3 = "禁止跟隨";
            String type4 = "關閉";
            String type5 = "關閉";
            String type6 = "關閉";
            String type7 = "關閉";
            String type8 = "關閉";
            String type9 = "關閉";
            String type10 = "關閉";
            String type11 = "關閉";
            String type12 = "關閉";
            String type13 = "關閉";
            if (_pc.getfollowskilltype() == 0) {
                type1 = "尚未設置";
            }
            else if (_pc.getfollowskilltype() == 1) {
                type1 = "初級治癒術";
            }
            else if (_pc.getfollowskilltype() == 2) {
                type1 = "中級治癒術";
            }
            else if (_pc.getfollowskilltype() == 3) {
                type1 = "高級治癒術";
            }
            else if (_pc.getfollowskilltype() == 4) {
                type1 = "全部治癒術";
            }
            if (_pc.getfollowskillhp() > 0) {
                type2 = new StringBuilder().append(_pc.getfollowskillhp()).toString();
            }
            if (_pc.isfollowcheck()) {
                type3 = "允許跟隨";
            }
            if (_pc.isfollowskill42()) {
                type4 = "開啟";
            }
            if (_pc.isfollowskill26()) {
                type5 = "開啟";
            }
            if (_pc.isfollowskill55()) {
                type6 = "開啟";
            }
            if (_pc.isfollowskill68()) {
                type7 = "開啟";
            }
            if (_pc.isfollowskill79()) {
                type8 = "開啟";
            }
            if (_pc.isfollowskill148()) {
                type9 = "開啟";
            }
            if (_pc.isfollowskill151()) {
                type10 = "開啟";
            }
            if (_pc.isfollowskill149()) {
                type11 = "開啟";
            }
            if (_pc.isfollowskill158()) {
                type12 = "開啟";
            }
            _pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "Lh8", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13 }));
        }
        catch (Exception ex) {}
    }

    private boolean hasActiveVip(L1PcInstance pc) {
        Timestamp vipTime = VIPReading.get().getOther(pc);
        return vipTime != null && vipTime.getTime() > System.currentTimeMillis();
    }
}

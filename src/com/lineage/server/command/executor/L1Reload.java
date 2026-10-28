package com.lineage.server.command.executor;

import com.lineage.config.ConfigWho;
import com.lineage.config.ConfigErrorException;
import com.lineage.config.Configtype;
import com.lineage.server.datatables.SkillsProbabilityTable;
import com.lineage.server.datatables.BeginnerTable;
import com.lineage.server.datatables.ItemPowerUpdateTable;
import com.lineage.server.datatables.sql.AuctionBoardTable;
import com.lineage.server.datatables.ItemBoxTable;
import com.add.system.L1BlendTable;
import com.lineage.server.datatables.DropMobTable;
import com.lineage.server.datatables.ShopCnTable;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.DungeonTable;
import com.lineage.server.datatables.sql.DwarfForChaTable;
import com.lineage.server.datatables.DropLimitTable;
import com.lineage.server.datatables.WeaponSkillTable;
import com.lineage.server.datatables.WeaponSkillPowerTable;
import com.lineage.server.datatables.MobSkillTable;
import com.lineage.server.datatables.EventTable;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.datatables.ShopXTable;
import com.lineage.server.datatables.NPCTalkDataTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.datatables.DropMapTable;
import com.lineage.william.ItemAction;
import com.lineage.server.datatables.DropTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Reload implements L1CommandExecutor
{
    static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Reload.class);
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Reload();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        if (arg.equalsIgnoreCase("掉落")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入掉寶資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入掉寶資料數量。"));
            }
            DropTable.get().load();
        }
        else if (arg.equalsIgnoreCase("道具製作")) {
            ItemAction.reload();
            pc.sendPackets(new S_SystemMessage("w_道具製作系統-資料庫更新"));
        }
        else if (arg.equalsIgnoreCase("dropmap")) {
            DropMapTable.get().load();
            pc.sendPackets(new S_SystemMessage("william_droplist_map-掉落資料庫更新"));
        }
        else if (arg.equalsIgnoreCase("shop")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入商店販賣資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入商店販賣資料數量。"));
            }
            ShopTable.get().load();
        }
        else if (arg.equalsIgnoreCase("armor")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入防具資料。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入防具資料。"));
            }
            ItemTable.get().loadarmors();
        }
        else if (arg.equalsIgnoreCase("weapon")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入武器資料。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入武器資料。"));
            }
            ItemTable.get().loadweapons();
        }
        else if (arg.equalsIgnoreCase("etcitem")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入道具資料。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入道具資料。"));
            }
            ItemTable.get().loaditems();
        }
        else if (arg.equalsIgnoreCase("npcaction")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入NPC對話資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入NPC對話資料數量。"));
            }
            NPCTalkDataTable.get().load();
        }
        else if (arg.equalsIgnoreCase("禁止拍賣")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入禁止拍賣物品資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入禁止拍賣物品資料數量。"));
            }
            ShopXTable.get().load();
        }
        else if (arg.equalsIgnoreCase("技能")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入技能設置資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入技能設置資料數量。"));
            }
            SkillsTable.get().load();
        }
        else if (arg.equalsIgnoreCase("server_event")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入活動設置資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入活動設置資料數量。"));
            }
            EventTable.get().load();
        }
        else if (arg.equalsIgnoreCase("mobskill")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入MOB技能資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入MOB技能資料數量。"));
            }
            MobSkillTable.getInstance().loadMobSkillData();
        }
        else if (arg.equalsIgnoreCase("weapon_skillpower")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入技能武器設置資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入技能武器設置資料數量。"));
            }
            WeaponSkillPowerTable.get().load();
        }
        else if (arg.equalsIgnoreCase("weapon_skill")) {
            WeaponSkillTable.get().load();
            pc.sendPackets(new S_SystemMessage("weapon_skill-資料庫更新"));
        }
        else if (arg.equalsIgnoreCase("限制掉落")) {
            DropLimitTable.getInstance().reload();
            pc.sendPackets(new S_SystemMessage("william_drop_limit_item-資料庫更新"));
        }
        else if (arg.equalsIgnoreCase("角色倉庫")) {
            DwarfForChaTable.get().load();
            pc.sendPackets(new S_SystemMessage("角色倉庫-資料庫更新"));
        }
        else if (arg.equalsIgnoreCase("dungeon")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入地圖切換點設置數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入地圖切換點設置數量。"));
            }
            DungeonTable.get().load();
        }
        else if (arg.equalsIgnoreCase("npc")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入NPC設置資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("重新載入NPC設置資料數量。"));
            }
            NpcTable.get().load();
        }
        else if (arg.equalsIgnoreCase("shopcn")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入商店販賣資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("shop_cn-資料庫更新。"));
            }
            ShopCnTable.get().restshopCn();
        }
        else if (arg.equalsIgnoreCase("全怪掉寶")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入全怪掉寶資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("william_droplist_mob-資料庫更新。"));
            }
            DropMobTable.get().load();
        }
        else if (arg.equalsIgnoreCase("火神")) {
            if (pc == null) {
                L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入w_火神裝備製作資料數量。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("w_火神裝備製作-資料庫更新。"));
            }
            L1BlendTable.getInstance().loadBlendTable();
        }
        else if (arg.equalsIgnoreCase("寶箱") || arg.equalsIgnoreCase("box")) {
            ItemBoxTable.reload();
            pc.sendPackets(new S_SystemMessage("[etcitem_box].[etcitem_box_key].[etcitem_boxs]寶箱更新完成"));
        }
        else if (arg.equalsIgnoreCase("board")) {
            AuctionBoardTable.reload();
            pc.sendPackets(new S_SystemMessage("血盟小屋更新完成"));
        }
        else if (arg.equalsIgnoreCase("娃娃升級表")) {
            ItemPowerUpdateTable.reload();
            pc.sendPackets(new S_SystemMessage("server_item_power_update更新完成"));
        }
        else if (arg.equalsIgnoreCase("beginner")) {
            BeginnerTable.reload();
            pc.sendPackets(new S_SystemMessage("BeginnerTable更新完成"));
        }
        else if (arg.equalsIgnoreCase("技能機率")) {
            SkillsProbabilityTable.get();
            SkillsProbabilityTable.reload();
            pc.sendPackets(new S_SystemMessage("skills_pc_probability更新完成"));
        }
        else if (arg.equalsIgnoreCase("武卷防捲設定表")) {
            try {
                Configtype.load();
            }
            catch (ConfigErrorException e) {
                e.printStackTrace();
            }
            pc.sendPackets(new S_SystemMessage("武卷防捲設定表-資料庫更新"));
        }
        else if (arg.equalsIgnoreCase("WHO設定表")) {
            try {
                ConfigWho.load();
            }
            catch (ConfigErrorException e) {
                e.printStackTrace();
            }
            pc.sendPackets(new S_SystemMessage("WHO設定表-資料庫更新"));
        }
    }
}

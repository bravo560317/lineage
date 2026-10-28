package com.lineage.server.datatables;

import java.sql.Timestamp;
import com.lineage.server.utils.L1QueryUtil;

public class RecordTable
{
    private static RecordTable _instance;
    
    public static RecordTable get() {
        if (RecordTable._instance == null) {
            RecordTable._instance = new RecordTable();
        }
        return RecordTable._instance;
    }
    
    public void recordblend(String pcName, String itemName, int itemCount, String ip) {
        String sql = "INSERT INTO 紀錄_火神_製作 (玩家,道具,數量,IP,時間) VALUE (?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, itemCount, ip });
    }
    
    public void recordPcChangePassWord1(String accName, String pcName) {
        String sql = "INSERT INTO 紀錄_角色_刪人 (帳號, 玩家, 時間) VALUE (?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { accName, pcName });
    }
    
    public void recordFailureArmor2(String pcName, String oldname, String newname, String ip) {
        String sql = "INSERT INTO 紀錄_物品_升級 (玩家,被升級,後升級,IP,時間) VALUE (?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, oldname, newname, ip });
    }
    
    public void recordtakeitem(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
        String sql = "INSERT INTO 紀錄_角色_撿物 (玩家,撿取物品,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, itemCount, itemObjid, ip });
    }
    
    public void recordgivenpc(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
        String sql = "INSERT INTO 紀錄_角色_丟怪 (玩家,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, itemCount, itemObjid, ip });
    }
    
    public void recorddropitem(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
        String sql = "INSERT INTO 紀錄_角色_丟物 (玩家,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, itemCount, itemObjid, ip });
    }
    
    public void recordDeleItem(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
        String sql = "INSERT INTO 紀錄_角色_刪物 (玩家,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, itemCount, itemObjid, ip });
    }
    
    public void recordeDeadItem(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
        String sql = "INSERT INTO 紀錄_角色_噴裝 (玩家,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, itemCount, itemObjid, ip });
    }
    
    public void recordeShop(String pcName, String targeName, String itemName, int itemCount, int itemObjid, int money, String moneyName, String ipA, String ipB) {
        String sql = "INSERT INTO 紀錄_角色_擺攤 (買方,賣方,物品,數量,編號,花費,幣種,買方IP,賣方IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, targeName, itemName, itemCount, itemObjid, money, moneyName, ipA, ipB });
    }
    
    public void recordeTrade(String pcName, String targeName, String itemName, int itemCount, int itemObjid, String ipA, String ipB) {
        String sql = "INSERT INTO 紀錄_角色_交易 (交易者,接受者,物品,數量,編號,交易者IP,接受者IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, targeName, itemName, itemCount, itemObjid, ipA, ipB });
    }
    
    public void recordeTalk(String mod, String pcName, String pcClan, String targeName, String content) {
        String sql = "INSERT INTO 紀錄_聊天 (頻道,發話,血盟,對象,內容,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { mod, pcName, pcClan, targeName, content });
    }
    
    public void aikill(String pcName) {
        String sql = "INSERT INTO 紀錄_AI驗證未通過 (玩家,時間) VALUE (?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName });
    }
    
    public void recordeCmd(String pcName, String cmd, String note, String ip) {
        String sql = "INSERT INTO 紀錄_gm_指令 (GM,指令,說明,IP,時間) VALUE (?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, cmd, note, ip });
    }
    
    public void recordeSponsorItem(String accName, String pcName, int sponsor, String ip) {
        String sql = "INSERT INTO 紀錄_贊助_滿額禮 (帳號,玩家,滿額,IP,時間) VALUE (?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { accName, pcName, sponsor, ip });
    }
    
    public void recordeItemEcho(String pcName, String itemName, int count, int item_obj, String s, String ip) {
        String sql = "INSERT INTO 紀錄_掉寶 (玩家,道具,數量,編號,來源,IP,時間) VALUE (?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, count, item_obj, s, ip });
    }
    
    public void recordPcChangePassWord(int pcid, String accName, String pcName, String changPass, String ip) {
        String sql = "INSERT INTO 紀錄_角色_改密 (編號, 帳號, 玩家, 改密, IP, 時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcid, accName, pcName, changPass, ip });
    }
    
    public void recordPcChangeName(int pcid, String accName, String pcName, String changName, String ip) {
        String sql = "INSERT INTO 紀錄_角色_更名 (編號, 帳號, 玩家, 改名, IP, 時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcid, accName, pcName, changName, ip });
    }
    
    public void recordCastleReward(String castle, String pcName, String itemName, int count, int item_obj, String ip) {
        String sql = "INSERT INTO 紀錄_攻城獎勵 (城堡,玩家,物品,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { castle, pcName, itemName, count, item_obj, ip });
    }
    
    public void r_teleport(String pcName, String note) {
        String sql = "INSERT INTO 除錯_傳送 (玩家, 說明, 時間) VALUE (?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, note });
    }
    
    public void r_bug(String note) {
        String sql = "INSERT INTO 除錯_封包 (說明, 時間) VALUE (?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { note });
    }
    
    public void r_speed(String pcName, String note) {
        String sql = "INSERT INTO 紀錄_加速 (玩家, 說明, 時間) VALUE (?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, note });
    }
    
    public void recordFailureArmor(String pcName, String itemName, String armorName, int armorObjid, String note, String ip) {
        String sql = "INSERT INTO 紀錄_角色_衝裝 (玩家,使用,裝備,編號,說明,IP,時間) VALUE (?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, armorName, armorObjid, note, ip });
    }
    
    public void recordFailureArmor1(String pcName, String itemName, String armorName, int armorObjid, String note, String ip) {
        String sql = "INSERT INTO 紀錄_裝武_保護 (玩家,使用,裝備,編號,說明,IP,時間) VALUE (?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, armorName, armorObjid, note, ip });
    }
    
    public void recordeWarehouse_elf(String pcName, String action, String warehouse, String itemName, int itemCount, int itemObjid, String ip) {
        String sql = "INSERT INTO 紀錄_角色_妖倉 (玩家,執行,倉庫,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, action, warehouse, itemName, itemCount, itemObjid, ip });
    }
    
    public void recordeWarehouse_clan(String pcName, String action, String warehouse, String itemName, int itemCount, int itemObjid, String ip) {
        String sql = "INSERT INTO 紀錄_角色_盟倉 (玩家,執行,倉庫,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, action, warehouse, itemName, itemCount, itemObjid, ip });
    }
    
    public void recordeWarehouse_pc(String pcName, String action, String warehouse, String itemName, int itemCount, int itemObjid, String ip) {
        String sql = "INSERT INTO 紀錄_角色_個倉 (玩家,執行,倉庫,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, action, warehouse, itemName, itemCount, itemObjid, ip });
    }
    
    public void recordeSponsor(String accName, String pcName, int sponsor, int allowance, String clanid, String ip) {
        String sql = "INSERT INTO 紀錄_贊助 (帳號,玩家,贊助,額外,血盟,IP,時間) VALUE (?,?, ?, ?, ?, ?,SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { accName, pcName, sponsor, allowance, clanid, ip });
    }
    
    public void recordeShop1(String pcName, String moneyName, int moneyCount, String itemName, int itemCount, int itemObjid, String ip) {
        String sql = "INSERT INTO 紀錄_消費 (玩家,幣種,消費,物品,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, moneyName, moneyCount, itemName, itemCount, itemObjid, ip });
    }
    
    public void killpc(String pcName, String targeName) {
        String sql = "INSERT INTO 紀錄_角色_殺人 (生存,死亡,時間) VALUE (?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, targeName });
    }
    
    public void rangiveitem(String pcName) {
        String sql = "INSERT INTO 紀錄_角色_隨機禮物 (玩家,時間) VALUE (?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName });
    }
    
    public void recordbox(String pcName, String itemNamebox, String itemName, int itemCount) {
        String sql = "INSERT INTO 紀錄_角色_寶箱 (玩家,使用寶箱,獲得道具,數量,時間) VALUE (?, ?, ?, ?,  SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemNamebox, itemName, itemCount });
    }
    
    public void recordItemTime(String pcName, String itemName, Timestamp timer, int item_obj) {
        String sql = "INSERT INTO 紀錄_物品_時間 (玩家,道具,期限,編號,時間) VALUE (?, ?, ?, ?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, itemName, timer, item_obj });
    }
    
    public void guaji(String pcName, String action) {
        String sql = "INSERT INTO 紀錄_角色_使用掛機 (玩家,開啟方式,時間) VALUE (?,?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName, action });
    }
    
    public void reshp(String pcName) {
        String sql = "INSERT INTO 紀錄_玩家洗血 (玩家,時間) VALUE (?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName });
    }
    
    public void reshp1(String pcName) {
        String sql = "INSERT INTO 紀錄_回憶蠟燭(玩家,時間) VALUE (?, SYSDATE())";
        L1QueryUtil.execute(sql, new Object[] { pcName });
    }
}

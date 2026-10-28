package com.lineage.william;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.sql.Timestamp;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;

public class PayBonus
{
    public static void getItem(L1PcInstance pc, int count) {
        Connection conn = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM ezpay_滿額送禮");
            ArrayList<?> arraylist = null;
            if (rs != null) {
                while (rs.next()) {
                    Timestamp now_time = new Timestamp(System.currentTimeMillis());
                    int min_money = rs.getInt("min_money");
                    int max_money = rs.getInt("max_money");
                    int itemid = rs.getInt("give_item");
                    int itemcount = rs.getInt("give_count");
                    Timestamp end_time = rs.getTimestamp("限制結束時間");
                    if (end_time != null && now_time.after(end_time)) {
                        continue;
                    }
                    else if (count >= min_money && count <= max_money) {
                        L1ItemInstance items = pc.getInventory().storeItem(itemid, (long)itemcount);
                        pc.sendPackets(new S_ServerMessage("\\fW獲得贊助滿額好禮:" + items.getName() + "(" + itemcount + ")"));
                        RecordTable.get().recordeSponsorItem(pc.getAccountName(), pc.getName(), max_money, pc.getIp());
                    }
                    else {
                        continue;
                    }
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (Exception ex) {}
    }
}

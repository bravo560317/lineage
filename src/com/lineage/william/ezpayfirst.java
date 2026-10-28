package com.lineage.william;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Account;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.sql.Timestamp;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;

public class ezpayfirst
{
    public static void getItem(L1PcInstance pc, long count) {
        Connection conn = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM ezpay_首儲獎勵");
            L1Account account = pc.getNetConnection().getAccount();
            int nowb = account.get_first_pay();
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
                    else if (count >= (long)min_money && count <= (long)max_money) {
                        L1ItemInstance items = pc.getInventory().storeItem(itemid, (long)itemcount);
                        pc.sendPackets(new S_ServerMessage("\\aD獲得首儲好禮:" + items.getName() + "(" + itemcount + ")"));
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

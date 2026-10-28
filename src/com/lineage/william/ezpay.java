package com.lineage.william;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Account;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;

public class ezpay
{
    public static void getItem(L1PcInstance pc, long count) {
        Connection conn = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM ezpay_贊助累積金額");
            L1Account account = pc.getNetConnection().getAccount();
            int nowb = pc.getfirst_pay() + 1;
            if (rs != null) {
                while (rs.next()) {
                    int money = rs.getInt("money");
                    int itemid = rs.getInt("give_item");
                    int itemcount = rs.getInt("give_count");
                    if (nowb <= money && account.get_first_pay() >= money) {
                        L1ItemInstance items = pc.getInventory().storeItem(itemid, (long)itemcount);
                        pc.sendPackets(new S_ServerMessage("\\fW累積贊助達滿:" + money));
                        pc.sendPackets(new S_ServerMessage("\\fW贈禮:" + items.getName() + "(" + itemcount + ")"));
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

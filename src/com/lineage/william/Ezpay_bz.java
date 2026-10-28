package com.lineage.william;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;

public class Ezpay_bz
{
    public static void getItem(L1PcInstance pc, int count) {
        Connection conn = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM ezpay_贊助比值");
            ArrayList<?> arraylist = null;
            if (rs != null) {
                while (rs.next()) {
                    int cou = 0;
                    int itemid = rs.getInt("itemid");
                    int min_money = rs.getInt("min_money");
                    int max_money = rs.getInt("max_money");
                    double itemcountez = rs.getDouble("bz");
                    if (count >= min_money && count <= max_money && !pc.hasSkillEffect(852236)) {
                        cou = count;
                        count = (int)((double)count * itemcountez);
                        pc.getInventory().storeItem(itemid, (long)count);
                        pc.sendPackets(new S_ServerMessage("\\fW獲得贊助幣:" + count));
                        pc.setSkillEffect(852236, 2000);
                        pc.save();
                        RecordTable.get().recordeSponsor(pc.getAccountName(), pc.getName(), cou, count, pc.getClanname(), pc.getIp());
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

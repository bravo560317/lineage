package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_ShopSellListCn;
import com.lineage.server.serverpackets.S_ScrollShopSellList;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class ItemShop
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    
    static {
        ItemShop.aData = new ArrayList();
        ItemShop.NO_GET_DATA = false;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc) {
        ArrayList aTempData = null;
        if (!ItemShop.NO_GET_DATA) {
            ItemShop.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < ItemShop.aData.size(); ++i) {
            aTempData = ItemShop.aData.get(i);
            if (((String)aTempData.get(0)).equals(s) && pc.getInventory().checkItem((Integer)aTempData.get(3), 1L)) {
                int npcId = (Integer)aTempData.get(1);
                pc.set_temp_adena((Integer)aTempData.get(2));
                if (pc.get_temp_adena() == 40308) {
                    pc.set_temp_adena(40308);
                    pc.sendPackets(new S_ScrollShopSellList(NpcTable.get().getTemplate(npcId)));
                }
                else {
                    pc.set_temp_adena((Integer)aTempData.get(2));
                    pc.sendPackets(new S_ShopSellListCn(pc, npcId));
                }
            }
        }
        return false;
    }
    
    private static void getData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_自訂道具商城");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    sTemp = rset.getString("action");
                    aReturn.add(0, sTemp);
                    aReturn.add(1, rset.getInt("npc編號"));
                    aReturn.add(2, rset.getInt("使用貨幣"));
                    aReturn.add(3, rset.getInt("itemid"));
                    ItemShop.aData.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
    }
}

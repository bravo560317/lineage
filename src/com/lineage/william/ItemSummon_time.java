package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1Item;
import com.lineage.server.world.World;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class ItemSummon_time
{
    private static ArrayList aData17;
    private static boolean NO_MORE_GET_DATA17;
    public static final String TOKEN = ",";
    
    static {
        ItemSummon_time.aData17 = new ArrayList();
        ItemSummon_time.NO_MORE_GET_DATA17 = false;
    }
    
    public static void main(String[] a) {
        while (true) {
            try {
                Server.main((String[])null);
                continue;
            }
            catch (Exception ex) {}
        }
    }
    
    public static void forresolvent(L1PcInstance pc, L1ItemInstance item) {
        int itemid = item.getItemId();
        ArrayList aTempData = null;
        int[] iTemp = (int[])null;
        if (!ItemSummon_time.NO_MORE_GET_DATA17) {
            ItemSummon_time.NO_MORE_GET_DATA17 = true;
            getData17();
        }
        for (int i = 0; i < ItemSummon_time.aData17.size(); ++i) {
            aTempData = (ArrayList)ItemSummon_time.aData17.get(i);
            if ((Integer)aTempData.get(0) == itemid) {
                if ((Integer)aTempData.get(2) != 0) {
                    byte class_id = 0;
                    String msg = "";
                    if (pc.isCrown()) {
                        class_id = 1;
                    }
                    else if (pc.isKnight()) {
                        class_id = 2;
                    }
                    else if (pc.isWizard()) {
                        class_id = 3;
                    }
                    else if (pc.isElf()) {
                        class_id = 4;
                    }
                    else if (pc.isDarkelf()) {
                        class_id = 5;
                    }
                    else if (pc.isDragonKnight()) {
                        class_id = 6;
                    }
                    else if (pc.isIllusionist()) {
                        class_id = 7;
                    }
                    switch ((Integer)aTempData.get(2)) {
                        case 1: {
                            msg = "王族";
                            break;
                        }
                        case 2: {
                            msg = "騎士";
                            break;
                        }
                        case 3: {
                            msg = "法師";
                            break;
                        }
                        case 4: {
                            msg = "妖精";
                            break;
                        }
                        case 5: {
                            msg = "黑暗妖精";
                            break;
                        }
                        case 6: {
                            msg = "龍騎士";
                            break;
                        }
                        case 7: {
                            msg = "幻術士";
                            break;
                        }
                    }
                    if ((int)(Integer)aTempData.get(2) != class_id) {
                        pc.sendPackets(new S_SystemMessage("你的職業無法使用" + msg + "的專屬道具。"));
                        return;
                    }
                }
                if ((Integer)aTempData.get(3) != 0 && pc.getLevel() < (Integer)aTempData.get(3)) {
                    pc.sendPackets(new S_SystemMessage("等級最低需求" + (int)(Integer)aTempData.get(3) + "才可使用"));
                    return;
                }
                else if ((Integer)aTempData.get(4) != 0 && pc.getLevel() > (Integer)aTempData.get(4)) {
                    pc.sendPackets(new S_SystemMessage("等級最高需求" + (int)(Integer)aTempData.get(4) + "才可使用"));
                    return;
                }
                else if ((Integer)aTempData.get(5) != 0 && pc.getMeteLevel() < (Integer)aTempData.get(5)) {
                    pc.sendPackets(new S_SystemMessage("轉身最低需求" + (int)(Integer)aTempData.get(5) + "才可使用"));
                    return;
                }
                else if ((Integer)aTempData.get(6) != 0 && pc.getMeteLevel() > (Integer)aTempData.get(6)) {
                    pc.sendPackets(new S_SystemMessage("轉身最高限制" + (int)(Integer)aTempData.get(6) + "才可使用"));
                    return;
                }
                else {
                    int[] materials = (int[])aTempData.get(7);
                    int[] counts = (int[])aTempData.get(8);
                    int j = 0;
                    while (j < materials.length) {
                        if (!pc.getInventory().checkItem(materials[j], (long)counts[j])) {
                            L1Item temp = ItemTable.get().getTemplate(materials[j]);
                            pc.sendPackets(new S_ServerMessage(337, temp.getName() + "(" + ((long)counts[j] - pc.getInventory().countItems(temp.getItemId())) + ")"));
                            return;
                        }
                        else {
                            ++j;
                        }
                    }
                    if ((Integer)aTempData.get(11) != 0 && pc.getMapId() != (int)(Integer)aTempData.get(11)) {
                        pc.sendPackets(new S_ServerMessage("此地圖無法使用該道具"));
                        return;
                    }
                    else {
                        L1SpawnUtil.spawn(pc, (Integer)aTempData.get(12), 0, (Integer)aTempData.get(13) * 60);
                        pc.sendPackets(new S_ServerMessage("所招換的時間只有" + (int)(Integer)aTempData.get(13) + "分就會消失"));
                        World.get().broadcastPacketToAll(new S_SystemMessage("\\fY" + (String)aTempData.get(14)));
                        for (int k = 0; k < materials.length; ++k) {
                            if ((Integer)aTempData.get(9) != 0 && (Integer)aTempData.get(9) == 1) {
                                pc.getInventory().consumeItem(materials[k], (long)counts[k]);
                            }
                        }
                        L1ItemInstance item2 = pc.getInventory().findItemId((Integer)aTempData.get(0));
                        if ((Integer)aTempData.get(10) != 0 && (Integer)aTempData.get(10) == 1) {
                            pc.getInventory().removeItem(item2, 1L);
                        }
                    }
                }
            }
        }
    }
    
    private static void getData17() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_道具召喚時間");
            ArrayList aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("itemid")));
                    aReturn.add(1, rset.getString("note"));
                    aReturn.add(2, new Integer(rset.getInt("check_Class")));
                    aReturn.add(3, new Integer(rset.getInt("check_Level_min")));
                    aReturn.add(4, new Integer(rset.getInt("check_Level_max")));
                    aReturn.add(5, new Integer(rset.getInt("check_power_min")));
                    aReturn.add(6, new Integer(rset.getInt("check_power_max")));
                    if (rset.getString("check_item") != null && !rset.getString("check_item").equals("") && !rset.getString("check_item").equals("0")) {
                        aReturn.add(7, getArray(rset.getString("check_item"), ",", 1));
                    }
                    else {
                        aReturn.add(7, null);
                    }
                    if (rset.getString("check_count") != null && !rset.getString("check_count").equals("") && !rset.getString("check_count").equals("0")) {
                        aReturn.add(8, getArray(rset.getString("check_count"), ",", 1));
                    }
                    else {
                        aReturn.add(8, null);
                    }
                    aReturn.add(9, new Integer(rset.getInt("dele_checkitem")));
                    aReturn.add(10, new Integer(rset.getInt("dele_itemid")));
                    aReturn.add(11, new Integer(rset.getInt("check_mapid")));
                    aReturn.add(12, new Integer(rset.getInt("npcid")));
                    aReturn.add(13, new Integer(rset.getInt("npcid_time")));
                    aReturn.add(14, rset.getString("world_message"));
                    ItemSummon_time.aData17.add(aReturn);
                }
            }
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        }
        catch (Exception ex) {}
    }
    
    private static Object getArray(String s, String sToken, int iType) {
        StringTokenizer st = new StringTokenizer(s, sToken);
        int iSize = st.countTokens();
        String sTemp = null;
        if (iType == 1) {
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                iReturn[i] = Integer.parseInt(sTemp);
            }
            return iReturn;
        }
        else if (iType == 2) {
            String[] sReturn = new String[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                sReturn[i] = sTemp;
            }
            return sReturn;
        }
        else if (iType == 3) {
            String sReturn2 = null;
            for (int i = 0; i < iSize; ++i) {
                sTemp = (sReturn2 = st.nextToken());
            }
            return sReturn2;
        }
        else {
            return null;
        }
    }
}

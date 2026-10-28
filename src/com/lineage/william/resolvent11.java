package com.lineage.william;

import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.Random;
import java.util.ArrayList;

public class resolvent11
{
    private static ArrayList aData17;
    private static boolean NO_MORE_GET_DATA17;
    private static Random _random;
    public static final String TOKEN = ",";
    
    static {
        resolvent11.aData17 = new ArrayList();
        resolvent11.NO_MORE_GET_DATA17 = false;
        resolvent11._random = new Random();
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
    
    public static void forresolvent(L1PcInstance pc, L1ItemInstance item, int l) {
        int itemid = item.getItemId();
        L1ItemInstance tgitem = pc.getInventory().getItem(l);
        ArrayList aTempData = null;
        int[] iTemp = (int[])null;
        if (!resolvent11.NO_MORE_GET_DATA17) {
            resolvent11.NO_MORE_GET_DATA17 = true;
            getData17();
        }
        for (int i = 0; i < resolvent11.aData17.size(); ++i) {
            aTempData = (ArrayList)resolvent11.aData17.get(i);
            if ((Integer)aTempData.get(0) == itemid) {
                if ((Integer)aTempData.get(0) == item.getItemId() && (Integer)aTempData.get(5) == tgitem.getItemId()) {
                    if (tgitem.getBless() >= 4) {
                        pc.sendPackets(new S_SystemMessage("該裝備受到封印卷軸的影響。"));
                        return;
                    }
                    else if (tgitem.isEquipped()) {
                        pc.sendPackets(new S_SystemMessage("請先把裝備脫掉在使用。"));
                        return;
                    }
                    else if (pc.getInventory().getSize() >= 170) {
                        pc.sendPackets(new S_SystemMessage("身上物品太多無法進行升級"));
                        return;
                    }
                    else if ((double)pc.getInventory().getWeight() / pc.getMaxWeight() * 100.0 > 90.0) {
                        pc.sendPackets(new S_SystemMessage("身上負重限制無法進行升級"));
                        return;
                    }
                    else if (pc.getDoll(tgitem.getId()) != null) {
                        pc.sendPackets(new S_ServerMessage(1181));
                        return;
                    }
                    else {
                        if ((Integer)aTempData.get(6) >= resolvent11._random.nextInt(100) + 1) {
                            pc.getInventory().storeItem((Integer)aTempData.get(7), (long)(int)(Integer)aTempData.get(8));
                            pc.sendPackets(new S_SystemMessage((String)aTempData.get(9)));
                        }
                        else {
                            pc.sendPackets(new S_SystemMessage((String)aTempData.get(10)));
                        }
                        pc.getInventory().removeItem(tgitem.getId(), 1L);
                        L1ItemInstance item2 = pc.getInventory().findItemId((Integer)aTempData.get(0));
                        pc.getInventory().removeItem(item2.getId(), 1L);
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
            ResultSet rset = stat.executeQuery("SELECT * FROM w_物品分解系統");
            ArrayList aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("道具編號")));
                    aReturn.add(1, 0);
                    aReturn.add(2, 0);
                    aReturn.add(3, 0);
                    aReturn.add(4, 0);
                    aReturn.add(5, new Integer(rset.getInt("被分解物品")));
                    aReturn.add(6, new Integer(rset.getInt("機率")));
                    aReturn.add(7, new Integer(rset.getInt("獲得物品")));
                    aReturn.add(8, new Integer(rset.getInt("獲得數量")));
                    aReturn.add(9, rset.getString("分解成功訊息"));
                    aReturn.add(10, rset.getString("分解失敗訊息"));
                    aReturn.add(11, 0);
                    resolvent11.aData17.add(aReturn);
                }
            }
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        }
        catch (Exception ex) {}
    }
    
    public static void 升級成功(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[升級成功].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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

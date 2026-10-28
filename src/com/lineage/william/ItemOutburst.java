package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1SkinInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import java.util.ArrayList;

public class ItemOutburst
{
    private static ArrayList<ArrayList<Object>> aData;
    public static final HashMap<Integer, ItemOutburst> _questionIdIndex;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    private static ItemOutburst _instance;
    
    static {
        ItemOutburst.aData = new ArrayList();
        _questionIdIndex = new HashMap();
        ItemOutburst.NO_GET_DATA = false;
    }
    
    public static ItemOutburst getInstance() {
        if (ItemOutburst._instance == null) {
            ItemOutburst._instance = new ItemOutburst();
        }
        return ItemOutburst._instance;
    }
    
    public static void reload() {
        ItemOutburst._questionIdIndex.clear();
        ItemOutburst._instance = null;
        getInstance();
    }
    
    private ItemOutburst() {
        getData();
    }
    
    public static void trueOutburst(L1PcInstance pc, L1ItemInstance item) {
        ArrayList aTempData = null;
        if (!ItemOutburst.NO_GET_DATA) {
            ItemOutburst.NO_GET_DATA = true;
            getData();
        }
        int itemid = item.getItemId();
        L1ItemInstance weapon = pc.getWeapon();
        for (int i = 0; i < ItemOutburst.aData.size(); ++i) {
            aTempData = ItemOutburst.aData.get(i);
            if ((Integer)aTempData.get(0) == itemid) {
                if ((Integer)aTempData.get(0) == itemid && pc.getLevel() >= (Integer)aTempData.get(1) && pc.getLevel() <= (Integer)aTempData.get(2) && !pc.getOutbur()) {
                    pc.setOutbur(true);
                    if ((Integer)aTempData.get(3) != 0) {
                        pc.addDmgup((Integer)aTempData.get(3));
                        pc.sendPackets(new S_ServerMessage("近距離攻擊+" + (int)(Integer)aTempData.get(3)));
                    }
                    if ((Integer)aTempData.get(4) != 0) {
                        pc.addBowDmgup((Integer)aTempData.get(4));
                        pc.sendPackets(new S_ServerMessage("遠距離攻擊+" + (int)(Integer)aTempData.get(4)));
                    }
                    if ((Integer)aTempData.get(5) != 0) {
                        pc.addClan_ReductionDmg((Integer)aTempData.get(5));
                        pc.sendPackets(new S_ServerMessage("減免傷害+" + (int)(Integer)aTempData.get(5)));
                    }
                    if ((Integer)aTempData.get(6) != 0) {
                        pc.addMaxHp((Integer)aTempData.get(6));
                        pc.setCurrentHp(pc.getCurrentHp() + (Integer)aTempData.get(6));
                        pc.sendPackets(new S_ServerMessage("血量增加+" + (int)(Integer)aTempData.get(6)));
                    }
                    if ((Integer)aTempData.get(7) != 0) {
                        pc.addMaxMp((Integer)aTempData.get(7));
                        pc.setCurrentMp(pc.getCurrentMp() + (Integer)aTempData.get(7));
                        pc.sendPackets(new S_ServerMessage("魔量增加+" + (int)(Integer)aTempData.get(7)));
                    }
                    if ((Integer)aTempData.get(8) != 0) {
                        pc.addSp((Integer)aTempData.get(8));
                        pc.sendPackets(new S_ServerMessage("魔攻增加+" + (int)(Integer)aTempData.get(8)));
                    }
                    if ((Integer)aTempData.get(9) != 0) {
                        L1SkinInstance skin = L1SpawnUtil.spawnSkin(pc, (Integer)aTempData.get(9));
                        if (skin != null) {
                            skin.setMoveType(1);
                            pc.addSkin(skin, (Integer)aTempData.get(9));
                        }
                    }
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                    pc.sendPackets(new S_SPMR(pc));
                    if ((String)aTempData.get(11) != null) {
                        pc.sendPackets(new S_SystemMessage((String)aTempData.get(11)));
                    }
                    if ((Integer)aTempData.get(10) == 1) {
                        pc.setcheckOutbur(true);
                    }
                    return;
                }
            }
        }
    }
    
    public static void falseOutburst(L1PcInstance pc, L1ItemInstance item) {
        ArrayList aTempData = null;
        if (!ItemOutburst.NO_GET_DATA) {
            ItemOutburst.NO_GET_DATA = true;
            getData();
        }
        int itemid = item.getItemId();
        for (int i = 0; i < ItemOutburst.aData.size(); ++i) {
            aTempData = ItemOutburst.aData.get(i);
            if ((Integer)aTempData.get(0) == itemid) {
                if ((Integer)aTempData.get(0) == itemid && pc.getLevel() >= (Integer)aTempData.get(1) && pc.getLevel() <= (Integer)aTempData.get(2) && pc.getOutbur()) {
                    pc.setOutbur(false);
                    if ((Integer)aTempData.get(3) != 0) {
                        pc.addDmgup(-(int)(Integer)aTempData.get(3));
                        pc.sendPackets(new S_ServerMessage("近距離攻擊-" + (int)(Integer)aTempData.get(3)));
                    }
                    if ((Integer)aTempData.get(4) != 0) {
                        pc.addBowDmgup(-(int)(Integer)aTempData.get(4));
                        pc.sendPackets(new S_ServerMessage("遠距離攻擊-" + (int)(Integer)aTempData.get(4)));
                    }
                    if ((Integer)aTempData.get(5) != 0) {
                        pc.addClan_ReductionDmg(-(int)(Integer)aTempData.get(5));
                        pc.sendPackets(new S_ServerMessage("減免傷害-" + (int)(Integer)aTempData.get(5)));
                    }
                    if ((Integer)aTempData.get(6) != 0) {
                        pc.addMaxHp(-(int)(Integer)aTempData.get(6));
                        pc.setCurrentHp(pc.getCurrentHp() + (Integer)aTempData.get(6));
                        pc.sendPackets(new S_ServerMessage("血量增加-" + (int)(Integer)aTempData.get(6)));
                    }
                    if ((Integer)aTempData.get(7) != 0) {
                        pc.addMaxMp(-(int)(Integer)aTempData.get(7));
                        pc.setCurrentMp(pc.getCurrentMp() + (Integer)aTempData.get(7));
                        pc.sendPackets(new S_ServerMessage("魔量增加-" + (int)(Integer)aTempData.get(7)));
                    }
                    if ((Integer)aTempData.get(8) != 0) {
                        pc.addSp(-(int)(Integer)aTempData.get(8));
                        pc.sendPackets(new S_ServerMessage("魔攻增加-" + (int)(Integer)aTempData.get(8)));
                    }
                    if ((Integer)aTempData.get(9) != 0 && pc.getSkin((Integer)aTempData.get(9)) != null) {
                        pc.getSkin((Integer)aTempData.get(9)).deleteMe();
                        pc.removeSkin((Integer)aTempData.get(9));
                    }
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                    pc.sendPackets(new S_SPMR(pc));
                    if ((String)aTempData.get(12) != null) {
                        pc.sendPackets(new S_SystemMessage((String)aTempData.get(12)));
                    }
                    if ((Integer)aTempData.get(10) == 1) {
                        pc.setcheckOutbur(false);
                    }
                    return;
                }
            }
        }
    }
    
    private static void getData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_道具爆氣系統");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("道具編號")));
                    aReturn.add(1, new Integer(rset.getInt("最小等級")));
                    aReturn.add(2, new Integer(rset.getInt("最高等級")));
                    aReturn.add(3, new Integer(rset.getInt("近增傷")));
                    aReturn.add(4, new Integer(rset.getInt("遠增傷")));
                    aReturn.add(5, new Integer(rset.getInt("減傷")));
                    aReturn.add(6, new Integer(rset.getInt("增血")));
                    aReturn.add(7, new Integer(rset.getInt("增魔")));
                    aReturn.add(8, new Integer(rset.getInt("魔攻")));
                    aReturn.add(9, new Integer(rset.getInt("特效")));
                    aReturn.add(10, new Integer(rset.getInt("每秒扣一怒氣值")));
                    aReturn.add(11, rset.getString("啟動顯示"));
                    aReturn.add(12, rset.getString("關閉顯示"));
                    ItemOutburst.aData.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
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

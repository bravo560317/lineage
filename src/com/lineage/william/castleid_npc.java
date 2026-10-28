package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.templates.L1Item;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Calendar;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class castleid_npc
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    
    static {
        castleid_npc.aData = new ArrayList();
        castleid_npc.NO_GET_DATA = false;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        ArrayList aTempData = null;
        if (!castleid_npc.NO_GET_DATA) {
            castleid_npc.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < castleid_npc.aData.size(); ++i) {
            aTempData = castleid_npc.aData.get(i);
            if (aTempData.get(0) != null && (Integer)aTempData.get(0) == npcid && ((String)aTempData.get(1)).equals(s)) {
                boolean hascastle = checkHasCastle(pc, (Integer)aTempData.get(15));
                if (hascastle) {
                    if ((Integer)aTempData.get(2) != 0 && pc.getLevel() < (Integer)aTempData.get(2)) {
                        pc.sendPackets(new S_SystemMessage("等級最低需求" + (int)(Integer)aTempData.get(2) + "才可使用"));
                        return false;
                    }
                    else {
                        if ((Integer)aTempData.get(3) != 0) {
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
                            switch ((Integer)aTempData.get(3)) {
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
                            if ((int)(Integer)aTempData.get(3) != class_id) {
                                pc.sendPackets(new S_SystemMessage("你的職業無法使用" + msg + "的專屬道具。"));
                                return false;
                            }
                        }
                        Calendar date = Calendar.getInstance();
                        int nowWeek = date.get(7) - 1;
                        int nowHour = date.get(11);
                        if (nowWeek == 0) {
                            nowWeek += 7;
                        }
                        String week = null;
                        if ((Integer)aTempData.get(12) == 1) {
                            week = "ㄧ";
                        }
                        else if ((Integer)aTempData.get(12) == 2) {
                            week = "二";
                        }
                        else if ((Integer)aTempData.get(12) == 3) {
                            week = "三";
                        }
                        else if ((Integer)aTempData.get(12) == 4) {
                            week = "四";
                        }
                        else if ((Integer)aTempData.get(12) == 5) {
                            week = "五";
                        }
                        else if ((Integer)aTempData.get(12) == 6) {
                            week = "六";
                        }
                        else if ((Integer)aTempData.get(12) == 7) {
                            week = "日";
                        }
                        if ((Integer)aTempData.get(12) != nowWeek) {
                            if ((Integer)aTempData.get(12) == 1) {
                                pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【一】"));
                            }
                            else if ((Integer)aTempData.get(12) == 2) {
                                pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【二】"));
                            }
                            else if ((Integer)aTempData.get(12) == 3) {
                                pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【三】"));
                            }
                            else if ((Integer)aTempData.get(12) == 4) {
                                pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【四】"));
                            }
                            else if ((Integer)aTempData.get(12) == 5) {
                                pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【五】"));
                            }
                            else if ((Integer)aTempData.get(12) == 6) {
                                pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【六】"));
                            }
                            else if ((Integer)aTempData.get(12) == 7) {
                                pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【日】"));
                            }
                        }
                        else if ((Integer)aTempData.get(12) > 0 && (Integer)aTempData.get(13) > 0 && (Integer)aTempData.get(14) > 0) {
                            if ((Integer)aTempData.get(12) != nowWeek || nowHour < (Integer)aTempData.get(13) || nowHour >= (Integer)aTempData.get(14)) {
                                pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【" + week + "】時間【" + (int)(Integer)aTempData.get(13) + "】點，至【" + (int)(Integer)aTempData.get(14) + "】點"));
                                return false;
                            }
                        }
                        else if ((Integer)aTempData.get(12) < 0 && (Integer)aTempData.get(13) >= 0 && (Integer)aTempData.get(14) >= 0 && (nowHour < (Integer)aTempData.get(13) || nowHour >= (Integer)aTempData.get(14))) {
                            pc.sendPackets(new S_ServerMessage(166, "使用時間限制:時間【" + (int)(Integer)aTempData.get(13) + "】點，至【" + (int)(Integer)aTempData.get(14) + "】點"));
                            return false;
                        }
                        boolean isCreate = true;
                        if ((Integer)aTempData.get(4) != 0 && (Integer)aTempData.get(5) != 0) {
                            int materials = (Integer)aTempData.get(4);
                            int counts = (Integer)aTempData.get(5);
                            if (!pc.getInventory().checkItem(materials, (long)counts)) {
                                L1Item temp = ItemTable.get().getTemplate(materials);
                                pc.sendPackets(new S_ServerMessage(337, temp.getName() + "(" + ((long)counts - pc.getInventory().countItems(temp.getItemId())) + ")"));
                                isCreate = false;
                            }
                        }
                        if (isCreate) {
                            pc.getInventory().consumeItem((Integer)aTempData.get(4), (long)(int)(Integer)aTempData.get(5));
                            if ((int[])aTempData.get(20) != null) {
                                int[] Skills = (int[])aTempData.get(20);
                                int time = (Integer)aTempData.get(21);
                                for (int j = 0; j < Skills.length; ++j) {
                                    L1SkillUse l1skilluse = new L1SkillUse();
                                    l1skilluse.handleCommands(pc, Skills[j], pc.getId(), pc.getX(), pc.getY(), time, 4);
                                    pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 19));
                                }
                            }
                            if ((String)aTempData.get(11) != null) {
                                pc.sendPackets(new S_SystemMessage("\\fY" + (String)aTempData.get(11)));
                            }
                            if ((Integer)aTempData.get(9) != 0) {
                                L1Teleport.teleport(pc, (Integer)aTempData.get(7), (Integer)aTempData.get(8), (short)(int)(Integer)aTempData.get(9), 5, true);
                                if ((Integer)aTempData.get(9) != 0 && (Integer)aTempData.get(10) != 0) {
                                    pc.get_other().set_usemap((Integer)aTempData.get(9));
                                    ServerUseMapTimer.put(pc, (Integer)aTempData.get(10));
                                    pc.sendPackets(new S_ServerMessage("時間限制:" + (int)(Integer)aTempData.get(10) + "秒"));
                                }
                            }
                        }
                    }
                }
                else {
                    if (pc.getInventory().checkItem((Integer)aTempData.get(4), (long)(int)(Integer)aTempData.get(5))) {
                        pc.getInventory().consumeItem((Integer)aTempData.get(4), (long)(int)(Integer)aTempData.get(5));
                    }
                    pc.sendPackets(new S_SystemMessage("\\fY" + (String)aTempData.get(16)));
                    if ((Integer)aTempData.get(19) != 0) {
                        L1Teleport.teleport(pc, (Integer)aTempData.get(17), (Integer)aTempData.get(18), (short)(int)(Integer)aTempData.get(19), 5, true);
                    }
                }
            }
        }
        return false;
    }
    
    private static boolean checkHasCastle(L1PcInstance player, int castle_id) {
        if (player.getClanid() != 0) {
            L1Clan clan = WorldClan.get().getClan(player.getClanname());
            if (clan != null && clan.getCastleId() == castle_id) {
                return true;
            }
        }
        return false;
    }
    
    private static void getData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_城堡狀態NPC");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("npcid")));
                    sTemp = rset.getString("action");
                    aReturn.add(1, sTemp);
                    aReturn.add(2, rset.getInt("確認等級"));
                    aReturn.add(3, rset.getInt("確認職業"));
                    aReturn.add(4, rset.getInt("確認道具"));
                    aReturn.add(5, rset.getInt("確認道具數量"));
                    aReturn.add(6, null);
                    aReturn.add(7, new Integer(rset.getInt("座標X")));
                    aReturn.add(8, new Integer(rset.getInt("座標Y")));
                    aReturn.add(9, new Integer(rset.getInt("地圖ID")));
                    aReturn.add(10, new Integer(rset.getInt("進入時間")));
                    aReturn.add(11, rset.getString("進入字語"));
                    aReturn.add(12, new Integer(rset.getInt("限制星期")));
                    aReturn.add(13, new Integer(rset.getInt("開始進入時間")));
                    aReturn.add(14, new Integer(rset.getInt("結束進入時間")));
                    aReturn.add(15, new Integer(rset.getInt("城堡編號")));
                    aReturn.add(16, rset.getString("非法進入字語"));
                    aReturn.add(17, new Integer(rset.getInt("非城堡傳輸座標X")));
                    aReturn.add(18, new Integer(rset.getInt("非城堡傳輸座標Y")));
                    aReturn.add(19, new Integer(rset.getInt("非城堡傳輸地圖ID")));
                    if (rset.getString("魔法Buff") != null && !rset.getString("魔法Buff").equals("") && !rset.getString("魔法Buff").equals("0")) {
                        aReturn.add(20, getArray(rset.getString("魔法Buff"), ",", 1));
                    }
                    else {
                        aReturn.add(20, null);
                    }
                    aReturn.add(21, new Integer(rset.getInt("魔法Buff時間")));
                    castleid_npc.aData.add(aReturn);
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

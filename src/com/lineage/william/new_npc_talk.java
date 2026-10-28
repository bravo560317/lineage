package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;
import java.util.Random;

public class new_npc_talk
{
    protected static final Random _random;
    private static final ArrayList<ArrayList<Object>> datas;
    
    static {
        _random = new Random();
        datas = new ArrayList();
    }
    
    private new_npc_talk() {
    }
    
    public static boolean giveBuff(L1PcInstance pc, L1NpcInstance npc) {
        for (ArrayList<Object> os : new_npc_talk.datas) {
            int[] npcids = (int[])os.get(0);
            int[] skills = (int[])os.get(1);
            int[] times = (int[])os.get(2);
            int[] m = (int[])os.get(3);
            int[] mc = (int[])os.get(4);
            int 獲得道具機率 = (Integer)os.get(5);
            int[] 給予物品 = (int[])os.get(6);
            int[] 給予物品數量 = (int[])os.get(7);
            String 未獲得物品顯示 = (String)os.get(8);
            int 傳送座標X = (Integer)os.get(9);
            int 傳送座標Y = (Integer)os.get(10);
            int 傳送座標Mapid = (Integer)os.get(11);
            int 傳送時間 = (Integer)os.get(12);
            String 顯示Msg = (String)os.get(13);
            int[] array;
            for (int length = (array = npcids).length, k = 0; k < length; ++k) {
                int id = array[k];
                if (id == npc.getNpcId()) {
                    if (m != null && mc != null) {
                        int i = 0;
                        while (i < m.length) {
                            if (!pc.getInventory().checkItem(m[i], (long)mc[i])) {
                                L1Item item = ItemTable.get().getTemplate(m[i]);
                                pc.sendPackets(new S_SystemMessage(item.getNameId() + "不足，無法使用。"));
                                return false;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    for (int i = 0; i < m.length; ++i) {
                        pc.getInventory().consumeItem(m[i], (long)mc[i]);
                    }
                    if (skills != null && times != null) {
                        for (int i = 0; i < skills.length; ++i) {
                            pc.sendPackets(new S_SystemMessage(顯示Msg));
                            new L1SkillUse().handleCommands(pc, skills[i], pc.getId(), pc.getX(), pc.getY(), times[i], 4);
                        }
                    }
                    if (給予物品 != null && 給予物品數量 != null && 獲得道具機率 != 0) {
                        if (獲得道具機率 != 0 && RandomArrayList.getInc(100, 1) <= 獲得道具機率) {
                            for (int j = 0; j < 給予物品.length; ++j) {
                                L1ItemInstance item2 = ItemTable.get().createItem(給予物品[j]);
                                if (item2.isStackable()) {
                                    item2.setCount((long)給予物品數量[j]);
                                }
                                else {
                                    item2.setCount(1L);
                                }
                                if (pc.getInventory().checkAddItem(item2, (long)給予物品數量[j]) == 0) {
                                    pc.getInventory().storeItem(item2);
                                }
                                else {
                                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item2);
                                }
                                pc.sendPackets(new S_ServerMessage(403, item2.getLogName()));
                            }
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(未獲得物品顯示));
                        }
                    }
                    if (傳送座標X != 0 && 傳送座標Y != 0 && 傳送座標Mapid != 0) {
                        L1Teleport.teleport(pc, 傳送座標X, 傳送座標Y, (short)傳送座標Mapid, 5, true);
                        if (傳送時間 != 0) {
                            pc.get_other().set_usemap(傳送座標Mapid);
                            ServerUseMapTimer.put(pc, 傳送時間);
                            pc.sendPackets(new S_ServerMessage("使用時間限制:" + 傳送時間 + "秒"));
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_npc觸碰`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ArrayList<Object> data = new ArrayList();
                data.add(convert(rs.getString("npcid").split(",")));
                if (rs.getString("skills") != null) {
                    data.add(convert(rs.getString("skills").split(",")));
                }
                else {
                    data.add(null);
                }
                if (rs.getString("times") != null) {
                    data.add(convert(rs.getString("times").split(",")));
                }
                else {
                    data.add(null);
                }
                if (rs.getString("material") != null) {
                    data.add(convert(rs.getString("material").split(",")));
                }
                else {
                    data.add(null);
                }
                if (rs.getString("material_count") != null) {
                    data.add(convert(rs.getString("material_count").split(",")));
                }
                else {
                    data.add(null);
                }
                if (rs.getInt("獲得道具機率") != 0) {
                    data.add(rs.getInt("獲得道具機率"));
                }
                else {
                    data.add(0);
                }
                if (rs.getString("給予物品") != null) {
                    data.add(convert(rs.getString("給予物品").split(",")));
                }
                else {
                    data.add(null);
                }
                if (rs.getString("給予物品數量") != null) {
                    data.add(convert(rs.getString("給予物品數量").split(",")));
                }
                else {
                    data.add(null);
                }
                if (rs.getString("未獲得物品顯示") != null) {
                    data.add(rs.getString("未獲得物品顯示"));
                }
                else {
                    data.add(null);
                }
                if (rs.getInt("傳送座標X") != 0) {
                    data.add(rs.getInt("傳送座標X"));
                }
                else {
                    data.add(0);
                }
                if (rs.getInt("傳送座標Y") != 0) {
                    data.add(rs.getInt("傳送座標Y"));
                }
                else {
                    data.add(0);
                }
                if (rs.getInt("傳送座標Mapid") != 0) {
                    data.add(rs.getInt("傳送座標Mapid"));
                }
                else {
                    data.add(0);
                }
                if (rs.getInt("傳送時間") != 0) {
                    data.add(rs.getInt("傳送時間"));
                }
                else {
                    data.add(0);
                }
                if (rs.getString("顯示Msg") != null) {
                    data.add(rs.getString("顯示Msg"));
                }
                else {
                    data.add(0);
                }
                new_npc_talk.datas.add(data);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static int[] convert(String[] data) {
        int[] i32 = new int[data.length];
        for (int j = 0; j < data.length; ++j) {
            i32[j] = Integer.parseInt(data[j]);
        }
        return i32;
    }
}

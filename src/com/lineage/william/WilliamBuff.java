package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class WilliamBuff
{
    private static final ArrayList<ArrayList<Object>> datas;
    
    static {
        datas = new ArrayList();
    }
    
    private WilliamBuff() {
    }
    
    public static boolean giveBuff(L1PcInstance pc, L1NpcInstance npc, String args) {
        boolean f = false;
        for (ArrayList<Object> os : WilliamBuff.datas) {
            int[] npcids = (int[])os.get(0);
            int[] array;
            int length = (array = npcids).length;
            int j = 0;
            while (j < length) {
                int id = array[j];
                if (id == npc.getNpcId()) {
                    f = true;
                    break;
                }
                else {
                    ++j;
                }
            }
            if (!f) {
                continue;
            }
            else {
                f = false;
                String[] cmd = (String[])os.get(1);
                String[] array2;
                int length2 = (array2 = cmd).length;
                int k = 0;
                while (k < length2) {
                    String c = array2[k];
                    if (c.equals(args)) {
                        f = true;
                        break;
                    }
                    else {
                        ++k;
                    }
                }
                if (!f) {
                    continue;
                }
                else {
                    int[] skills = (int[])os.get(2);
                    int[] times = (int[])os.get(3);
                    int[] m = (int[])os.get(4);
                    int[] mc = (int[])os.get(5);
                    int i = 0;
                    while (i < m.length) {
                        if (!pc.getInventory().consumeItem(m[i], (long)mc[i])) {
                            L1Item item = ItemTable.get().getTemplate(m[i]);
                            pc.sendPackets(new S_SystemMessage(item.getNameId() + " 不足，無法為您施放輔助魔法。"));
                            return true;
                        }
                        else {
                            ++i;
                        }
                    }
                    for (i = 0; i < skills.length; ++i) {
                        new L1SkillUse().handleCommands(pc, skills[i], pc.getId(), pc.getX(), pc.getY(), times[i], 4);
                    }
                    if (f) {
                        return true;
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        return f;
    }
    
    public static void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_npc賦予魔法狀態`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ArrayList<Object> data = new ArrayList();
                data.add(convert(rs.getString("npcid").split(",")));
                data.add(rs.getString("action").split(","));
                data.add(convert(rs.getString("skills").split(",")));
                data.add(convert(rs.getString("times").split(",")));
                data.add(convert(rs.getString("material").split(",")));
                data.add(convert(rs.getString("material_count").split(",")));
                WilliamBuff.datas.add(data);
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

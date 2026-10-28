package com.lineage.data.item_etcitem;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.L1Object;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_WhoCharinfo;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Pc_Npc_show extends ItemExecutor
{
    private Pc_Npc_show() {
    }
    
    public static ItemExecutor get() {
        return new Pc_Npc_show();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance useItem) {
        int spellsc_objid = data[0];
        L1Object target = World.get().findObject(spellsc_objid);
        if (target != null) {
            String msg0 = "";
            String msg2 = "";
            String msg3 = "";
            String msg4 = "";
            String msg5 = "";
            String msg6 = "";
            String msg7 = "";
            String msg8 = "";
            String msg9 = "";
            String msg10 = "";
            String msg11 = "";
            String msg12 = "";
            String msg13 = "";
            String msg14 = "";
            String msg15 = "";
            String msg16 = "";
            String msg17 = "";
            String msg18 = "";
            String msg19 = "";
            String msg20 = "";
            if (target instanceof L1PcInstance) {
                L1PcInstance target_pc = (L1PcInstance)target;
                int mr = 0;
                switch (target_pc.guardianEncounter()) {
                    case 0: {
                        mr = 3;
                        break;
                    }
                    case 1: {
                        mr = 6;
                        break;
                    }
                    case 2: {
                        mr = 9;
                        break;
                    }
                }
                msg0 = target_pc.getName();
                msg2 = new StringBuilder().append(target_pc.getLevel()).toString();
                msg3 = target_pc.getCurrentHp() + " / " + target_pc.getMaxHp();
                msg4 = target_pc.getCurrentMp() + " / " + target_pc.getMaxMp();
                msg5 = new StringBuilder().append(target_pc.getAc()).toString();
                msg6 = new StringBuilder().append((int)target_pc.getStr()).toString();
                msg7 = new StringBuilder().append((int)target_pc.getDex()).toString();
                msg8 = new StringBuilder().append((int)target_pc.getInt()).toString();
                msg9 = new StringBuilder().append((int)target_pc.getCon()).toString();
                msg10 = new StringBuilder().append((int)target_pc.getWis()).toString();
                msg11 = new StringBuilder().append((int)target_pc.getCha()).toString();
                msg12 = target_pc.getMr() + mr + " %";
                msg19 = new StringBuilder().append(target_pc.getLawful()).toString();
                msg20 = new StringBuilder().append(target_pc.getSp()).toString();
                S_WhoCharinfo whoChar = new S_WhoCharinfo(pc, target_pc);
                pc.sendPackets(whoChar);
                pc.sendPackets(new S_SystemMessage("\\fT等級:[" + msg2 + "]"));
                pc.sendPackets(new S_SystemMessage("\\fW血量:[" + msg3 + "] // 魔力:[" + msg4 + "]"));
                pc.sendPackets(new S_SystemMessage("\\fU防禦:[" + msg5 + "]  //  魔防:[" + msg12 + "]"));
                pc.sendPackets(new S_SystemMessage("\\fU正義:[" + msg19 + "]  //  魔攻:[" + msg20 + "]"));
                pc.sendPackets(new S_SystemMessage("\\fR力量:[" + msg6 + "]  //  敏捷:[" + msg7 + "]"));
                pc.sendPackets(new S_SystemMessage("\\fR智力:[" + msg8 + "]  //  體質:[" + msg9 + "]"));
                pc.sendPackets(new S_SystemMessage("\\fR精神:[" + msg10 + "]  //  魅力:[" + msg11 + "]"));
            }
            else if (target instanceof L1MonsterInstance) {
                L1MonsterInstance target_npc = (L1MonsterInstance)target;
                msg0 = target_npc.getName();
                msg2 = new StringBuilder().append(target_npc.getLevel()).toString();
                msg3 = target_npc.getCurrentHp() + " / " + target_npc.getMaxHp();
                msg4 = target_npc.getCurrentMp() + " / " + target_npc.getMaxMp();
                msg5 = new StringBuilder().append(target_npc.getAc()).toString();
                msg6 = "0";
                msg7 = target_npc.getMr() + " %";
                msg8 = target_npc.getFire() + " %";
                msg9 = target_npc.getWater() + " %";
                msg10 = target_npc.getWind() + " %";
                msg11 = target_npc.getEarth() + " %";
                msg12 = "0";
                msg13 = new StringBuilder().append((int)target_npc.getStr()).toString();
                msg14 = new StringBuilder().append((int)target_npc.getInt()).toString();
                msg15 = new StringBuilder().append((int)target_npc.getCon()).toString();
                msg16 = new StringBuilder().append((int)target_npc.getDex()).toString();
                msg17 = new StringBuilder().append((int)target_npc.getWis()).toString();
                msg18 = new StringBuilder().append(target_npc.getExp()).toString();
                String[] msg21 = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20 };
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ajplayer1", msg21));
                L1Npc npc = NpcTable.get().getTemplate(target_npc.getNpcId());
                if (npc == null) {
                    pc.sendPackets(new S_SystemMessage("\\aT不存在該怪物。你可以在怪物附近輸入.4查看id"));
                    return;
                }
                else {
                    Connection con = null;
                    PreparedStatement pstm = null;
                    ResultSet rs = null;
                    try {
                        con = DatabaseFactory.get().getConnection();
                        pstm = con.prepareStatement("SELECT itemId,min,max,chance FROM droplist WHERE mobId=?");
                        pstm.setInt(1, target_npc.getNpcId());
                        rs = pstm.executeQuery();
                        rs.last();
                        int rows = rs.getRow();
                        int[] itemID = new int[rows];
                        int[] min = new int[rows];
                        int[] max = new int[rows];
                        double[] chance = new double[rows];
                        String[] name = new String[rows];
                        rs.beforeFirst();
                        int i = 0;
                        while (rs.next()) {
                            itemID[i] = rs.getInt("itemId");
                            min[i] = rs.getInt("min");
                            max[i] = rs.getInt("max");
                            chance[i] = (double)rs.getInt("chance") / 10000.0;
                            ++i;
                        }
                        rs.close();
                        pstm.close();
                        if (pc.isGm()) {
                            pc.sendPackets(new S_SystemMessage("\\fR" + npc.get_name() + "(" + target_npc.getNpcId() + ") 常規掉落查詢:"));
                        }
                        else {
                            pc.sendPackets(new S_SystemMessage("\\fR(" + npc.get_name() + ") 常規掉落查詢:"));
                        }
                        for (int j = 0; j < itemID.length; ++j) {
                            pstm = con.prepareStatement("SELECT name FROM etcitem WHERE item_id=?");
                            pstm.setInt(1, itemID[j]);
                            rs = pstm.executeQuery();
                            while (rs.next()) {
                                name[j] = rs.getString("name");
                            }
                            rs.close();
                            pstm.close();
                            pstm = con.prepareStatement("SELECT name FROM weapon WHERE item_id=?");
                            pstm.setInt(1, itemID[j]);
                            rs = pstm.executeQuery();
                            while (rs.next()) {
                                name[j] = rs.getString("name");
                            }
                            rs.close();
                            pstm.close();
                            pstm = con.prepareStatement("SELECT name FROM armor WHERE item_id=?");
                            pstm.setInt(1, itemID[j]);
                            rs = pstm.executeQuery();
                            while (rs.next()) {
                                name[j] = rs.getString("name");
                            }
                            rs.close();
                            pstm.close();
                            if (pc.isGm()) {
                                pc.sendPackets(new S_SystemMessage("\\fY物品: " + name[j] + " " + " 幾率:" + chance[j] + "%"));
                            }
                            else {
                                pc.sendPackets(new S_SystemMessage("\\fY物品: " + name[j]));
                            }
                        }
                    }
                    catch (Exception ex) {}
                    finally {
                        SQLUtil.close(rs);
                        SQLUtil.close(pstm);
                        SQLUtil.close(con);
                    }
                }
            }
        }
    }
}

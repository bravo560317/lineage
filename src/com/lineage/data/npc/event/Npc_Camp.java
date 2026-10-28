package com.lineage.data.npc.event;

import com.lineage.server.templates.L1User_Power;
import java.util.HashMap;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.datatables.NpcTeleportTable;
import com.lineage.data.npc.teleport.Npc_Teleport;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.datatables.lock.CharacterC1Reading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.C1_Name_Table;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Camp extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Camp.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Camp();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            String type = "無";
            String score = "0";
            String scorelv = "";
            if (pc.get_c_power() != null && pc.get_c_power().get_c1_type() != 0) {
                type = C1_Name_Table.get().get(pc.get_c_power().get_c1_type());
                score = String.valueOf(pc.get_other().get_score());
                scorelv = pc.get_c_power().get_power().get_c1_name_type();
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_camp_01", new String[] { type, score, scorelv }));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_camp_00"));
            }
        }
        catch (Exception e) {
            Npc_Camp._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            boolean isClose = false;
            if (cmd.equalsIgnoreCase("exit1")) {
                if (pc.get_c_power() != null) {
                    if (pc.get_c_power().get_c1_type() == 0) {
                        pc.sendPackets(new S_ServerMessage("\\fR您目前沒有陣營"));
                    }
                    else {
                        pc.get_c_power().set_c1_type(0);
                        pc.get_c_power().set_note("無");
                        pc.get_other().set_score(0);
                        CharacterC1Reading.get().updateCharacterC1(pc.getId(), 0, "無");
                        pc.get_c_power().set_power(pc, false);
                        pc.sendPacketsAll(new S_ChangeName(pc, true));
                        pc.save();
                        pc.sendPackets(new S_ServerMessage("\\fR成功退出陣營"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("\\fR您目前沒有陣營"));
                }
                isClose = true;
            }
            else if (cmd.equalsIgnoreCase("exit2")) {
                isClose = true;
            }
            else if (cmd.matches("[0-9]+")) {
                String pagecmd = String.valueOf(pc.get_other().get_page()) + cmd;
                Npc_Teleport.teleport(pc, npc, Integer.valueOf(pagecmd));
            }
            else if (cmd.startsWith("add")) {
                String newString = cmd.substring(3);
                this.add_type(newString, pc);
                isClose = true;
            }
            else {
                pc.get_other().set_page(0);
                HashMap teleportMap = NpcTeleportTable.get().get_teles(cmd);
                if (teleportMap != null) {
                    if (teleportMap.size() <= 0) {
                        pc.sendPackets(new S_ServerMessage(1447));
                        return;
                    }
                    else {
                        pc.get_otherList().teleport(teleportMap);
                        Npc_Teleport.showPage(pc, npc, 0);
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(1447));
                }
            }
            if (isClose) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        catch (Exception e) {
            Npc_Camp._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void add_type(String cmd, L1PcInstance pc) {
        int type = Integer.parseInt(cmd);
        String typeName = C1_Name_Table.get().get(type);
        if (pc.get_c_power() == null) {
            L1User_Power power = new L1User_Power();
            power.set_object_id(pc.getId());
            power.set_c1_type(type);
            power.set_note(typeName);
            pc.set_c_power(power);
            CharacterC1Reading.get().storeCharacterC1(pc);
        }
        else {
            pc.get_c_power().set_c1_type(type);
            pc.get_c_power().set_note(typeName);
            CharacterC1Reading.get().updateCharacterC1(pc.getId(), type, typeName);
        }
        pc.get_c_power().set_power(pc, false);
        pc.sendPacketsAll(new S_ChangeName(pc, true));
        pc.sendPackets(new S_ServerMessage("\\fR成功加入陣營：" + typeName));
    }
}

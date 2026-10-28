package com.lineage.data.npc;

import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class clan_lv extends NpcExecutor
{
    private clan_lv() {
    }
    
    public static NpcExecutor get() {
        return new clan_lv();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getClanid() == 0) {
            pc.sendPackets(new S_SystemMessage("\\fT請先加入血盟"));
            return;
        }
        else {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            String[] info = new String[7];
            if (pc.getClanid() > 0) {
                info[0] = pc.getClanname();
            }
            else {
                info[0] = "無";
            }
            if (pc.getClanid() > 0) {
                info[1] = String.valueOf(clan.getClanLevel());
            }
            else {
                info[1] = "無";
            }
            if (pc.getClanid() > 0) {
                info[2] = String.valueOf(clan.getClanContribution());
            }
            else {
                info[2] = "無";
            }
            info[3] = String.valueOf(pc.getPcContribution());
            info[4] = String.valueOf(pc.getClanContribution());
            info[5] = String.valueOf(pc.getclanadena());
            info[6] = String.valueOf(clan.getclanadena());
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "clanlvadd", info));
            return;
        }
    }
}

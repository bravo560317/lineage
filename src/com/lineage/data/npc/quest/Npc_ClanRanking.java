package com.lineage.data.npc.quest;

import java.util.Iterator;
import java.util.List;
import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.world.World;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.timecontroller.event.ranking.RankingClanTimer;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_ClanRanking extends NpcExecutor
{
    private Npc_ClanRanking() {
    }
    
    public static NpcExecutor get() {
        return new Npc_ClanRanking();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        String[] userName = RankingClanTimer.userName();
        String[] out = new String[20];
        for (int i = 0; i < userName.length; ++i) {
            if (!userName[i].equals(" ")) {
                out[i] = userName[i].replace(",", "(") + ")";
                out[i + 10] = "對" + out[i] + "宣戰";
            }
        }
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c_1", out));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (pc.getClanid() == 0) {
            pc.sendPackets(new S_ServerMessage(1064));
            return;
        }
        else {
            L1Clan clan = pc.getClan();
            if (clan == null) {
                pc.sendPackets(new S_ServerMessage(1064));
                return;
            }
            else if (!pc.isCrown()) {
                pc.sendPackets(new S_ServerMessage(478));
                return;
            }
            else if (clan.getLeaderId() != pc.getId()) {
                pc.sendPackets(new S_ServerMessage(518));
                return;
            }
            else {
                String[] clanNameList = RankingClanTimer.userName();
                int i = -1;
                if (cmd.matches("[0-9]+")) {
                    i = Integer.valueOf(cmd);
                }
                if (i != -1) {
                    String tg = clanNameList[i];
                    if (tg.equals(" ")) {
                        return;
                    }
                    else {
                        String[] set = tg.split(",");
                        String tgClanName = set[0];
                        L1Clan clanX = WorldClan.get().getClan(tgClanName);
                        if (clanX.getCastleId() != 0) {
                            pc.sendPackets(new S_ServerMessage(166, "不能對傭有城堡的血盟宣戰!"));
                            pc.sendPackets(new S_CloseList(pc.getId()));
                            return;
                        }
                        else if (clanX.getClanId() == pc.getClanid()) {
                            return;
                        }
                        else {
                            List<L1War> warList = WorldWar.get().getWarList();
                            String clanName = clan.getClanName();
                            for (L1War war : warList) {
                                if (!war.checkClanInSameWar(clanName, tgClanName)) {
                                    return;
                                }
                                else if (war.checkClanInWar(tgClanName)) {
                                    pc.sendPackets(new S_ServerMessage(236, tgClanName));
                                    return;
                                }
                                else {
                                    continue;
                                }
                            }
                            L1PcInstance enemyLeader = World.get().getPlayer(clanX.getLeaderName());
                            if (enemyLeader == null) {
                                pc.sendPackets(new S_ServerMessage(218, tgClanName));
                                return;
                            }
                            else {
                                int castle_id = 1;
                                while (castle_id < 8) {
                                    if (ServerWarExecutor.get().isNowWar(castle_id)) {
                                        pc.sendPackets(new S_ServerMessage("城戰期間禁止宣戰"));
                                        return;
                                    }
                                    else {
                                        ++castle_id;
                                    }
                                }
                                enemyLeader.setTempID(pc.getId());
                                enemyLeader.sendPackets(new S_Message_YN(217, clanName, pc.getName()));
                                pc.sendPackets(new S_CloseList(pc.getId()));
                            }
                        }
                    }
                }
                return;
            }
        }
    }
}

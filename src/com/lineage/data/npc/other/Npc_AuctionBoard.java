package com.lineage.data.npc.other;

import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_ApplyAuction;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_HouseMap;
import com.lineage.server.serverpackets.S_AuctionBoardRead;
import com.lineage.server.serverpackets.S_AuctionBoard;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_AuctionBoard extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_AuctionBoard.class);
    }
    
    private Npc_AuctionBoard() {
    }
    
    public static NpcExecutor get() {
        return new Npc_AuctionBoard();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_AuctionBoard(npc));
        }
        catch (Exception e) {
            Npc_AuctionBoard._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            boolean isCloseList = false;
            String[] temp = cmd.split(",");
            int objid = npc.getId();
            if (temp[0].equalsIgnoreCase("select")) {
                pc.sendPackets(new S_AuctionBoardRead(objid, temp[1]));
            }
            else if (temp[0].equalsIgnoreCase("map")) {
                pc.sendPackets(new S_HouseMap(objid, temp[1]));
            }
            else if (temp[0].equalsIgnoreCase("apply")) {
                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                if (clan != null) {
                    if (pc.isCrown() && pc.getId() == clan.getLeaderId()) {
                        if (pc.getLevel() >= 15) {
                            if (clan.getHouseId() == 0) {
                                pc.sendPackets(new S_ApplyAuction(objid, temp[1]));
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage(521));
                                isCloseList = true;
                            }
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(519));
                            isCloseList = true;
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(518));
                        isCloseList = true;
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(518));
                    isCloseList = true;
                }
            }
            if (isCloseList) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        catch (Exception e) {
            Npc_AuctionBoard._log.error(e.getLocalizedMessage(), e);
        }
    }
}

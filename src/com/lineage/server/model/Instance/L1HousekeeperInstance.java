package com.lineage.server.model.Instance;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.L1NpcTalkData;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.L1Clan;
import com.lineage.server.templates.L1House;
import com.lineage.server.datatables.lock.HouseReading;
import com.lineage.server.world.WorldClan;
import com.lineage.server.datatables.NPCTalkDataTable;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1HousekeeperInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1HousekeeperInstance.class);
    }
    
    public L1HousekeeperInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            L1AttackMode attack = new L1AttackPc(pc, this);
            attack.calcHit();
            attack.action();
        }
        catch (Exception e) {
            L1HousekeeperInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance pc) {
        int objid = this.getId();
        L1NpcTalkData talking = NPCTalkDataTable.get().getTemplate(this.getNpcTemplate().get_npcId());
        int npcid = this.getNpcTemplate().get_npcId();
        String htmlid = null;
        String[] htmldata = (String[])null;
        boolean isOwner = false;
        if (talking != null) {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null) {
                int houseId = clan.getHouseId();
                if (houseId != 0) {
                    L1House house = HouseReading.get().getHouseTable(houseId);
                    if (npcid == house.getKeeperId()) {
                        isOwner = true;
                    }
                }
            }
            if (!isOwner) {
                L1House targetHouse = null;
                Collection<L1House> houseList = (Collection)HouseReading.get().getHouseTableList().values();
                for (L1House house2 : houseList) {
                    if (npcid == house2.getKeeperId()) {
                        targetHouse = house2;
                        break;
                    }
                }
                boolean isOccupy = false;
                String clanName = null;
                String leaderName = null;
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan targetClan : allClans) {
                    if (targetHouse.getHouseId() == targetClan.getHouseId()) {
                        isOccupy = true;
                        clanName = targetClan.getClanName();
                        leaderName = targetClan.getLeaderName();
                        break;
                    }
                }
                if (isOccupy) {
                    htmlid = "agname";
                    htmldata = new String[] { clanName, leaderName, targetHouse.getHouseName() };
                }
                else {
                    htmlid = "agnoname";
                    htmldata = new String[] { targetHouse.getHouseName() };
                }
            }
            if (htmlid != null) {
                if (htmldata != null) {
                    pc.sendPackets(new S_NPCTalkReturn(objid, htmlid, htmldata));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(objid, htmlid));
                }
            }
            else if (pc.getLawful() < -1000) {
                pc.sendPackets(new S_NPCTalkReturn(talking, objid, 2));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(talking, objid, 1));
            }
        }
    }
    
    @Override
    public void onFinalAction(L1PcInstance pc, String action) {
    }
    
    public void doFinalAction(L1PcInstance pc) {
    }
}

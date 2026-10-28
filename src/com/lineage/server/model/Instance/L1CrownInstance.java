package com.lineage.server.model.Instance;

import com.lineage.server.serverpackets.S_RemoveObject;
import java.util.Iterator;
import java.util.List;
import com.lineage.server.datatables.DoorSpawnTable;
import com.lineage.server.model.L1WarSpawn;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_PacketBoxWar;
import com.lineage.server.timecontroller.pc.PcWarTP;
import com.lineage.server.serverpackets.S_CastleMaster;
import com.lineage.server.world.World;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.WorldClan;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1CrownInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1CrownInstance.class);
    }
    
    public L1CrownInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onAction(L1PcInstance player) {
        try {
            boolean in_war = false;
            if (player.getClanid() == 0) {
                return;
            }
            else {
                String playerClanName = player.getClanname();
                L1Clan clan = WorldClan.get().getClan(playerClanName);
                if (clan == null) {
                    return;
                }
                else if (player.isDead()) {
                    return;
                }
                else if (player.getCurrentHp() <= 0) {
                    return;
                }
                else if (player.isGhost()) {
                    return;
                }
                else if (player.isTeleport()) {
                    return;
                }
                else if (!player.isCrown()) {
                    return;
                }
                else if (player.getTempCharGfx() != 0 && player.getTempCharGfx() != 1) {
                    return;
                }
                else if (player.getId() != clan.getLeaderId()) {
                    return;
                }
                else if (!this.checkRange(player)) {
                    return;
                }
                else if (clan.getCastleId() != 0) {
                    player.sendPackets(new S_ServerMessage(474));
                    return;
                }
                else {
                    int castle_id = L1CastleLocation.getCastleId(this.getX(), this.getY(), this.getMapId());
                    boolean existDefenseClan = false;
                    L1Clan defence_clan = L1CastleLocation.mapCastle().get(new Integer(castle_id));
                    if (defence_clan != null) {
                        existDefenseClan = true;
                    }
                    List<L1War> wars = WorldWar.get().getWarList();
                    for (L1War war : wars) {
                        if (castle_id == war.getCastleId()) {
                            in_war = war.checkClanInWar(playerClanName);
                            break;
                        }
                    }
                    if (existDefenseClan && !in_war) {
                        return;
                    }
                    else {
                        for (L1War war : wars) {
                            if (war.checkClanInWar(playerClanName) && existDefenseClan) {
                                war.winCastleWar(playerClanName);
                                break;
                            }
                        }
                        if (existDefenseClan && defence_clan != null) {
                            defence_clan.setCastleId(0);
                            ClanReading.get().updateClan(defence_clan);
                            if (L1CastleLocation.mapCastle().get(new Integer(castle_id)) != null) {
                                L1CastleLocation.removeCastle(new Integer(castle_id));
                            }
                            World.get().broadcastPacketToAll(new S_CastleMaster(0, defence_clan.getLeaderId()));
                        }
                        clan.setCastleId(castle_id);
                        ClanReading.get().updateClan(clan);
                        if (L1CastleLocation.mapCastle().get(new Integer(castle_id)) == null) {
                            L1CastleLocation.putCastle(castle_id, clan);
                        }
                        if (castle_id == 2) {
                            World.get().broadcastPacketToAll(new S_CastleMaster(8, player.getId()));
                        }
                        else {
                            World.get().broadcastPacketToAll(new S_CastleMaster(castle_id, player.getId()));
                        }
                        int[] loc = new int[3];
                        for (L1PcInstance pc : World.get().getAllPlayers()) {
                            if (pc.getClanid() != player.getClanid() && !pc.isGm() && L1CastleLocation.checkInWarArea(castle_id, pc)) {
                                loc = L1CastleLocation.getGetBackLoc(castle_id);
                                int locx = loc[0];
                                int locy = loc[1];
                                short mapid = (short)loc[2];
                                PcWarTP teleportWar = new PcWarTP();
                                teleportWar.start(pc, locx, locy, (int)mapid);
                            }
                        }
                        L1PcInstance[] clanMember = clan.getOnlineClanMember();
                        if (clanMember.length > 0) {
                            L1PcInstance[] array;
                            for (int length = (array = clanMember).length, i = 0; i < length; ++i) {
                                L1PcInstance pc2 = array[i];
                                if (pc2.getId() == clan.getLeaderId()) {
                                    pc2.sendPackets(new S_PacketBoxWar(3));
                                }
                                pc2.sendPackets(new S_PacketBoxWar(4));
                            }
                        }
                        this.deleteMe();
                        for (L1Object l1object : World.get().getObject()) {
                            if (l1object instanceof L1TowerInstance) {
                                L1TowerInstance tower = (L1TowerInstance)l1object;
                                if (L1CastleLocation.checkInWarArea(castle_id, tower)) {
                                    tower.deleteMe();
                                }
                            }
                            if (l1object instanceof L1CatapultInstance) {
                                L1CatapultInstance catapult = (L1CatapultInstance)l1object;
                                if (L1CastleLocation.checkInWarArea(castle_id, catapult)) {
                                    catapult.deleteMe();
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                        L1WarSpawn warspawn = new L1WarSpawn();
                        warspawn.spawnTower(castle_id);
                        warspawn.SpawnCatapult(castle_id);
                        L1DoorInstance[] doorList;
                        for (int length2 = (doorList = DoorSpawnTable.get().getDoorList()).length, j = 0; j < length2; ++j) {
                            L1DoorInstance door = doorList[j];
                            if (L1CastleLocation.checkInWarArea(castle_id, door)) {
                                door.repairGate();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            L1CrownInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void deleteMe() {
        this._destroyed = true;
        if (this.getInventory() != null) {
            this.getInventory().clearItems();
        }
        this.allTargetClear();
        this._master = null;
        World.get().removeVisibleObject(this);
        World.get().removeObject(this);
        for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
            pc.removeKnownObject(this);
            pc.sendPackets(new S_RemoveObject(this));
        }
        this.removeAllKnownObjects();
    }
    
    private boolean checkRange(L1PcInstance pc) {
        return this.getX() - 1 <= pc.getX() && pc.getX() <= this.getX() + 1 && this.getY() - 1 <= pc.getY() && pc.getY() <= this.getY() + 1;
    }
}

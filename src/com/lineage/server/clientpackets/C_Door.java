package com.lineage.server.clientpackets;

import java.util.Timer;
import java.util.TimerTask;
import com.lineage.server.templates.L1House;
import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.lock.HouseReading;
import com.lineage.server.world.WorldClan;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import java.util.HashMap;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Door extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Door.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {}
            if (pc.isDead() || pc.isTeleport()) {
                return;
            }
            else {
                int locX = this.readH();
                int locY = this.readH();
                int objectId = this.readD();
                L1Object obj = World.get().findObject(objectId);
                if (obj instanceof L1DoorInstance) {
                    L1DoorInstance door = (L1DoorInstance)World.get().findObject(objectId);
                    if (door == null) {
                        return;
                    }
                    else if (door.getDoorId() >= 5001 && door.getDoorId() <= 5010) {
                        return;
                    }
                    else {
                        switch (door.getDoorId()) {
                            case 3501:
                            case 3502: {
                                break;
                            }
                            case 6006: {
                                if (door.getOpenStatus() == 28) {
                                    return;
                                }
                                else if (pc.getInventory().consumeItem(40163, 1L)) {
                                    door.open();
                                    C_Door.CloseTimer closetimer = new C_Door.CloseTimer(door);
                                    closetimer.begin();
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 6007: {
                                if (door.getOpenStatus() == 28) {
                                    return;
                                }
                                else if (pc.getInventory().consumeItem(40313, 1L)) {
                                    door.open();
                                    C_Door.CloseTimer closetimer = new C_Door.CloseTimer(door);
                                    closetimer.begin();
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 6033:
                            case 6034: {
                                break;
                            }
                            case 10000: {
                                if (door.getOpenStatus() == 28) {
                                    return;
                                }
                                else if (!pc.getInventory().consumeItem(40581, 1L)) {
                                    break;
                                }
                                else {
                                    door.open();
                                    break;
                                }
                            }
                            case 10001: {
                                if (door.getOpenStatus() == 28) {
                                    return;
                                }
                                else if (!pc.getInventory().consumeItem(40594, 1L)) {
                                    break;
                                }
                                else {
                                    door.open();
                                    break;
                                }
                            }
                            case 10002: {
                                if (door.getOpenStatus() == 28) {
                                    return;
                                }
                                else if (!pc.getInventory().consumeItem(40604, 1L)) {
                                    break;
                                }
                                else {
                                    door.open();
                                    break;
                                }
                            }
                            case 10003: {
                                break;
                            }
                            case 10004: {
                                if (door.getOpenStatus() == 28) {
                                    return;
                                }
                                else if (!pc.getInventory().consumeItem(40543, 1L)) {
                                    break;
                                }
                                else {
                                    door.open();
                                    break;
                                }
                            }
                            case 10005:
                            case 10006:
                            case 10007: {
                                break;
                            }
                            case 10008:
                            case 10009:
                            case 10010: {
                                break;
                            }
                            case 10011:
                            case 10012:
                            case 10013: {
                                break;
                            }
                            case 10015: {
                                if (!pc.get_hardinR().DOOR_1) {
                                    break;
                                }
                                else if (door.getOpenStatus() == 28) {
                                    door.close();
                                    break;
                                }
                                else if (door.getOpenStatus() == 29) {
                                    door.open();
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 10016: {
                                if (!pc.get_hardinR().DOOR_2) {
                                    break;
                                }
                                else if (door.getOpenStatus() == 28) {
                                    door.close();
                                    break;
                                }
                                else if (door.getOpenStatus() == 29) {
                                    door.open();
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 10017: {
                                if (!pc.get_hardinR().DOOR_2) {
                                    break;
                                }
                                else if (door.getOpenStatus() == 28) {
                                    door.close();
                                    break;
                                }
                                else if (door.getOpenStatus() == 29) {
                                    door.open();
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 10019:
                            case 10036: {
                                break;
                            }
                            case 10020: {
                                if (!pc.get_hardinR().DOOR_4) {
                                    break;
                                }
                                else if (door.getOpenStatus() == 28) {
                                    door.close();
                                    break;
                                }
                                else if (door.getOpenStatus() == 29) {
                                    door.open();
                                    pc.get_hardinR().DOOR_4OPEN = true;
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 10037:
                            case 10038:
                            case 10039: {
                                break;
                            }
                            default: {
                                if (this.isExistKeeper(pc, door.getKeeperId())) {
                                    break;
                                }
                                else if (door.getOpenStatus() == 28) {
                                    door.close();
                                    break;
                                }
                                else if (door.getOpenStatus() == 29) {
                                    door.open();
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                        }
                    }
                }
                else if (obj instanceof L1NpcInstance) {
                    L1NpcInstance npc = (L1NpcInstance)obj;
                    switch (npc.getNpcId()) {
                        case 70918: {
                            this.openDeLv50(pc, npc);
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    private void openDeLv50(L1PcInstance pc, L1NpcInstance npc) {
        L1ItemInstance item = pc.getInventory().checkItemX(40600, 1L);
        if (item != null) {
            pc.getInventory().removeItem(item, 1L);
            HashMap<Integer, L1Object> mapList = new HashMap();
            mapList.putAll(World.get().getVisibleObjects(306));
            npc.setStatus(28);
            npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 28));
            for (L1Object tgobj : mapList.values()) {
                if (tgobj instanceof L1NpcInstance) {
                    L1NpcInstance tgnpc = (L1NpcInstance)tgobj;
                    if (tgnpc.getNpcId() == 70905 && tgnpc.get_showId() == npc.get_showId()) {
                        tgnpc.deleteMe();
                    }
                    else {
                        continue;
                    }
                }
            }
            mapList.clear();
        }
    }
    
    private boolean isExistKeeper(L1PcInstance pc, int keeperId) {
        if (keeperId == 0) {
            return false;
        }
        else if (pc.isGm()) {
            return false;
        }
        else {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null) {
                int houseId = clan.getHouseId();
                if (houseId != 0) {
                    L1House house = HouseReading.get().getHouseTable(houseId);
                    if (keeperId == house.getKeeperId()) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    public class CloseTimer extends TimerTask
    {
        private L1DoorInstance _door;
        
        public CloseTimer(L1DoorInstance door) {
            this._door = door;
        }
        
        @Override
        public void run() {
            if (this._door.getOpenStatus() == 28) {
                this._door.close();
            }
        }
        
        public void begin() {
            Timer timer = new Timer();
            timer.schedule(this, 5000L);
        }
    }
}

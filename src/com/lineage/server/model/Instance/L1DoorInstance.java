package com.lineage.server.model.Instance;

import com.lineage.server.datatables.DoorSpawnTable;
import com.lineage.server.serverpackets.S_Door;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Character;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_DoorPack;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1DoorInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    public static final int PASS = 0;
    public static final int NOT_PASS = 65;
    private static final Log _log;
    private int _doorId;
    private int _direction;
    private int _leftEdgeLocation;
    private int _rightEdgeLocation;
    private int _openStatus;
    private int _passable;
    private int _keeperId;
    private int _crackStatus;
    
    static {
        _log = LogFactory.getLog(L1DoorInstance.class);
    }
    
    public L1DoorInstance(L1Npc template) {
        super(template);
        this._doorId = 0;
        this._direction = 0;
        this._leftEdgeLocation = 0;
        this._rightEdgeLocation = 0;
        this._openStatus = 29;
        this._passable = 65;
        this._keeperId = 0;
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            if (this.getMaxHp() == 0 || this.getMaxHp() == 1) {
                return;
            }
            else if (this.getCurrentHp() > 0 && !this.isDead()) {
                L1AttackMode attack = new L1AttackPc(pc, this);
                if (attack.calcHit()) {
                    attack.calcDamage();
                }
                attack.action();
                attack.commit();
            }
        }
        catch (Exception e) {
            L1DoorInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            else {
                perceivedFrom.addKnownObject(this);
                if (this.getOpenStatus() == 28) {
                    this.setOpenStatus(28);
                    this.setPassable(0);
                }
                else {
                    this.setOpenStatus(29);
                    this.setPassable(65);
                }
                perceivedFrom.sendPackets(new S_DoorPack(this));
                this.sendDoorPacket();
            }
        }
        catch (Exception e) {
            L1DoorInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void deleteMe() {
        try {
            this.setPassable(0);
            this.sendDoorPacket();
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
        catch (Exception e) {
            L1DoorInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        if (this.getMaxHp() == 0 || this.getMaxHp() == 1) {
            return;
        }
        else {
            if (this.getCurrentHp() > 0 && !this.isDead()) {
                int newHp = this.getCurrentHp() - damage;
                if (newHp <= 0 && !this.isDead()) {
                    this.setStatus(37);
                    L1DoorInstance.Death death = new L1DoorInstance.Death(attacker);
                    GeneralThreadPool.get().execute(death);
                }
                if (newHp > 0) {
                    this.setCurrentHp(newHp);
                    if (this.getMaxHp() * 1 / 6 > this.getCurrentHp()) {
                        if (this._crackStatus != 5) {
                            this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 36));
                            this.setStatus(36);
                            this._crackStatus = 5;
                        }
                    }
                    else if (this.getMaxHp() * 2 / 6 > this.getCurrentHp()) {
                        if (this._crackStatus != 4) {
                            this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 35));
                            this.setStatus(35);
                            this._crackStatus = 4;
                        }
                    }
                    else if (this.getMaxHp() * 3 / 6 > this.getCurrentHp()) {
                        if (this._crackStatus != 3) {
                            this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 34));
                            this.setStatus(34);
                            this._crackStatus = 3;
                        }
                    }
                    else if (this.getMaxHp() * 4 / 6 > this.getCurrentHp()) {
                        if (this._crackStatus != 2) {
                            this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 33));
                            this.setStatus(33);
                            this._crackStatus = 2;
                        }
                    }
                    else if (this.getMaxHp() * 5 / 6 > this.getCurrentHp() && this._crackStatus != 1) {
                        this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 32));
                        this.setStatus(32);
                        this._crackStatus = 1;
                    }
                }
            }
            else if (!this.isDead()) {
                this.setStatus(37);
                L1DoorInstance.Death death2 = new L1DoorInstance.Death(attacker);
                GeneralThreadPool.get().execute(death2);
            }
            return;
        }
    }
    
    @Override
    public void setCurrentHp(int i) {
        int currentHp = Math.min(i, this.getMaxHp());
        if (this.getCurrentHp() == currentHp) {
            return;
        }
        else {
            this.setCurrentHpDirect(currentHp);
            return;
        }
    }
    
    private void sendDoorPacket() {
        int entranceX = this.getEntranceX();
        int entranceY = this.getEntranceY();
        int leftEdgeLocation = this.getLeftEdgeLocation();
        int rightEdgeLocation = this.getRightEdgeLocation();
        int size = rightEdgeLocation - leftEdgeLocation;
        if (size == 0) {
            this.sendPacket(entranceX, entranceY);
        }
        else if (this.getDirection() == 0) {
            for (int x = leftEdgeLocation; x <= rightEdgeLocation; ++x) {
                this.sendPacket(x, entranceY);
            }
        }
        else {
            for (int y = leftEdgeLocation; y <= rightEdgeLocation; ++y) {
                this.sendPacket(entranceX, y);
            }
        }
    }
    
    private void sendPacket(int x, int y) {
        S_Door packet = new S_Door(x, y, this.getDirection(), this.getPassable());
        this.broadcastPacketAll(packet);
    }
    
    public void open() {
        if (this.isDead()) {
            return;
        }
        else {
            if (this.getOpenStatus() == 29) {
                this.setOpenStatus(28);
                this.setPassable(0);
                this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 28));
                this.sendDoorPacket();
            }
            return;
        }
    }
    
    public void close() {
        if (this.isDead()) {
            return;
        }
        else {
            if (this.getOpenStatus() == 28) {
                this.setOpenStatus(29);
                this.setPassable(65);
                this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 29));
                this.sendDoorPacket();
            }
            return;
        }
    }
    
    public void repairGate() {
        if (this.getMaxHp() > 1) {
            this.setDead(false);
            this.setCurrentHp(this.getMaxHp());
            this.setStatus(0);
            this.setCrackStatus(0);
            this.setOpenStatus(28);
            this.close();
        }
    }
    
    public int getDoorId() {
        return this._doorId;
    }
    
    public void setDoorId(int i) {
        this._doorId = i;
    }
    
    public int getDirection() {
        return this._direction;
    }
    
    public void setDirection(int i) {
        if (i == 0 || i == 1) {
            this._direction = i;
        }
    }
    
    public int getEntranceX() {
        int entranceX = 0;
        if (this.getDirection() == 0) {
            entranceX = this.getX();
        }
        else {
            entranceX = this.getX() - 1;
        }
        return entranceX;
    }
    
    public int getEntranceY() {
        int entranceY = 0;
        if (this.getDirection() == 0) {
            entranceY = this.getY() + 1;
        }
        else {
            entranceY = this.getY();
        }
        return entranceY;
    }
    
    public int getLeftEdgeLocation() {
        return this._leftEdgeLocation;
    }
    
    public void setLeftEdgeLocation(int i) {
        this._leftEdgeLocation = i;
    }
    
    public int getRightEdgeLocation() {
        return this._rightEdgeLocation;
    }
    
    public void setRightEdgeLocation(int i) {
        this._rightEdgeLocation = i;
    }
    
    public int getOpenStatus() {
        return this._openStatus;
    }
    
    public void setOpenStatus(int i) {
        if (i == 28 || i == 29) {
            this._openStatus = i;
        }
    }
    
    public int getPassable() {
        return this._passable;
    }
    
    private void setPassable(int i) {
        if (i == 0 || i == 65) {
            this._passable = i;
        }
    }
    
    public int getKeeperId() {
        return this._keeperId;
    }
    
    public void setKeeperId(int i) {
        this._keeperId = i;
    }
    
    private void setCrackStatus(int i) {
        this._crackStatus = i;
    }
    
    public static void openDoor() {
        L1DoorInstance[] allDoor = DoorSpawnTable.get().getDoorList();
        if (allDoor.length <= 0) {
            return;
        }
        else {
            L1DoorInstance[] arrayOfL1DoorInstance1 = allDoor;
            for (int j = allDoor.length, i = 0; i < j; ++i) {
                L1DoorInstance door = arrayOfL1DoorInstance1[i];
                switch (door.getDoorId()) {
                    case 5001:
                    case 5002:
                    case 5003:
                    case 5004:
                    case 5005:
                    case 5006:
                    case 5007:
                    case 5008:
                    case 5009:
                    case 5010:
                    case 6006:
                    case 6007:
                    case 10000:
                    case 10001:
                    case 10002:
                    case 10003:
                    case 10004:
                    case 10005:
                    case 10006:
                    case 10007:
                    case 10008:
                    case 10009:
                    case 10010:
                    case 10011:
                    case 10012:
                    case 10013:
                    case 10015:
                    case 10016:
                    case 10017:
                    case 10019:
                    case 10020:
                    case 10036: {
                        door.close();
                        break;
                    }
                    default: {
                        if (door.getMaxHp() > 1) {
                            door.close();
                            break;
                        }
                        else if (door.getKeeperId() != 0) {
                            door.close();
                            break;
                        }
                        else {
                            door.open();
                            break;
                        }
                    }
                }
            }
            return;
        }
    }
    
    public static void openGam(boolean isOpen) {
        L1DoorInstance[] allDoor = DoorSpawnTable.get().getDoorList();
        if (allDoor.length <= 0) {
            return;
        }
        else {
            L1DoorInstance[] array;
            for (int length = (array = allDoor).length, i = 0; i < length; ++i) {
                L1DoorInstance door = array[i];
                switch (door.getDoorId()) {
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55: {
                        if (isOpen) {
                            door.open();
                            break;
                        }
                        else {
                            door.close();
                            break;
                        }
                    }
                }
            }
            return;
        }
    }
    
    class Death implements Runnable
    {
        L1Character _lastAttacker;
        
        public Death(L1Character lastAttacker) {
            this._lastAttacker = lastAttacker;
        }
        
        @Override
        public void run() {
            L1DoorInstance.this.setCurrentHpDirect(0);
            L1DoorInstance.this.setDead(true);
            L1DoorInstance.this.getMap().setPassable(L1DoorInstance.this.getLocation(), true);
            L1DoorInstance.this.broadcastPacketAll(new S_DoActionGFX(L1DoorInstance.this.getId(), 37));
            L1DoorInstance.this.setPassable(0);
            L1DoorInstance.this.setOpenStatus(28);
            L1DoorInstance.this.setPassable(0);
            L1DoorInstance.this.sendDoorPacket();
        }
    }
}

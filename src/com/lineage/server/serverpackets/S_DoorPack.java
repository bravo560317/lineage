package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1DoorInstance;

public class S_DoorPack extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_DoorPack(L1DoorInstance door) {
        this._byte = null;
        this.buildPacket(door);
    }
    
    private void buildPacket(L1DoorInstance door) {
        this.writeC(87);
        this.writeH(door.getX());
        this.writeH(door.getY());
        this.writeD(door.getId());
        this.writeH(door.getGfxId());
        int doorStatus = door.getStatus();
        int openStatus = door.getOpenStatus();
        if (door.isDead()) {
            this.writeC(doorStatus);
        }
        else if (openStatus == 28) {
            this.writeC(openStatus);
        }
        else if (door.getMaxHp() > 1 && doorStatus != 0) {
            this.writeC(doorStatus);
        }
        else {
            this.writeC(openStatus);
        }
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeD(1);
        this.writeH(0);
        this.writeS(null);
        this.writeS(null);
        this.writeC(0);
        this.writeD(0);
        this.writeS(null);
        this.writeS(null);
        this.writeC(0);
        this.writeC(255);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(255);
        this.writeC(255);
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

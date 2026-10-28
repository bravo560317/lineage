package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1DoorInstance;

public class S_Door extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Door(L1DoorInstance door) {
        this._byte = null;
        this.buildPacket(door.getEntranceX(), door.getEntranceY(), door.getDirection(), door.getPassable());
    }
    
    public S_Door(int x, int y, int direction, int passable) {
        this._byte = null;
        this.buildPacket(x, y, direction, passable);
    }
    
    private void buildPacket(int x, int y, int direction, int passable) {
        this.writeC(209);
        this.writeH(x);
        this.writeH(y);
        this.writeC(direction);
        this.writeC(passable);
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

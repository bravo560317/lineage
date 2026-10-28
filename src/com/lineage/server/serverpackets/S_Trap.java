package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1TrapInstance;

public class S_Trap extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Trap(L1TrapInstance trap, String name) {
        this._byte = null;
        this.writeC(87);
        this.writeH(trap.getX());
        this.writeH(trap.getY());
        this.writeD(trap.getId());
        this.writeH(7);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeD(0);
        this.writeH(0);
        this.writeS(name);
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

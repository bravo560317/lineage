package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_DragonDoor extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_DragonDoor(L1PcInstance pc, boolean[] i) {
        this._byte = null;
        this.writeC(250);
        this.writeC(102);
        this.writeD(pc.getId());
        this.writeC(i[0] ? 1 : 0);
        this.writeC(i[1] ? 1 : 0);
        this.writeC(i[2] ? 1 : 0);
        this.writeC(i[3] ? 1 : 0);
    }
    
    public S_DragonDoor(L1PcInstance pc) {
        this._byte = null;
        this.writeC(250);
        this.writeC(102);
        this.writeD(pc.getId());
        this.writeC(1);
        this.writeC(1);
        this.writeC(1);
        this.writeC(0);
    }
    
    public S_DragonDoor(int itemobjid, int a, int b, int c, int d) {
        this._byte = null;
        this.writeC(250);
        this.writeC(102);
        this.writeD(itemobjid);
        this.writeC(a);
        this.writeC(b);
        this.writeC(c);
        this.writeC(d);
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

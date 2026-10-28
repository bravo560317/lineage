package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_Dexup extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Dexup(L1PcInstance pc, int type, int time) {
        this._byte = null;
        this.writeC(188);
        this.writeH(time);
        this.writeC((int)pc.getDex());
        this.writeC(type);
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

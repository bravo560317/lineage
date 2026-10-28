package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_ChatOut extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ChatOut(int objid) {
        this._byte = null;
        this.buildPacket(objid);
    }
    
    public S_ChatOut(L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pc.getId());
    }
    
    private void buildPacket(int objid) {
        this.writeD(objid);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
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

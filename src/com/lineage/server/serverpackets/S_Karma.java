package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_Karma extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Karma(L1PcInstance pc) {
        this._byte = null;
        this.writeC(250);
        this.writeC(87);
        this.writeD(pc.getKarma());
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

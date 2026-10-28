package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_HireSoldier extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_HireSoldier(L1PcInstance pc) {
        this._byte = null;
        this.writeH(0);
        this.writeH(0);
        this.writeH(0);
        this.writeS(pc.getName());
        this.writeD(0);
        this.writeH(0);
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

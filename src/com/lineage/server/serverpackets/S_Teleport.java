package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_Teleport extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Teleport(L1PcInstance pc) {
        this._byte = null;
        this.writeC(241);
        this.writeH((int)pc.getTeleportMapId());
        this.writeD(pc.getId());
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

package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Object;

public class S_RemoveObject extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_RemoveObject(L1Object obj) {
        this._byte = null;
        this.writeC(120);
        this.writeD(obj.getId());
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

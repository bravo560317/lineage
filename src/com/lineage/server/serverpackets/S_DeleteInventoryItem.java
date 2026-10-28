package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1ItemInstance;

public class S_DeleteInventoryItem extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_DeleteInventoryItem(L1ItemInstance item) {
        this._byte = null;
        this.writeC(57);
        this.writeD(item.getId());
    }
    
    public S_DeleteInventoryItem(int objid) {
        this._byte = null;
        this.writeC(57);
        this.writeD(objid);
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

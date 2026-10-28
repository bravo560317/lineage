package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1ItemInstance;

public class S_ItemName extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ItemName(L1ItemInstance item) {
        this._byte = null;
        if (item == null) {
            return;
        }
        else {
            this.writeC(100);
            this.writeD(item.getId());
            this.writeS(item.getViewName());
            return;
        }
    }
    
    public S_ItemName(L1ItemInstance item, int id) {
        this._byte = null;
        if (item == null) {
            return;
        }
        else {
            this.writeC(100);
            this.writeD(item.getId());
            this.writeS(item.getViewName() + " ($" + id + ")");
            return;
        }
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

package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1ItemInstance;

public class S_ItemColor extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ItemColor(L1ItemInstance item) {
        this._byte = null;
        if (item == null) {
            return;
        }
        else {
            this.buildPacket(item);
            return;
        }
    }
    
    private void buildPacket(L1ItemInstance item) {
        this.writeC(240);
        this.writeD(item.getId());
        this.writeC(item.getBless());
    }
    
    public S_ItemColor(L1ItemInstance item, int id) {
        this._byte = null;
        this.writeC(240);
        this.writeD(item.getId());
        this.writeC(id);
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

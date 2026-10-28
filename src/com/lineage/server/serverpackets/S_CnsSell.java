package com.lineage.server.serverpackets;

import com.lineage.data.event.ShopXSet;

public class S_CnsSell extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CnsSell(int objectId, String htmlid, String command) {
        this._byte = null;
        this.buildPacket(objectId, htmlid, command);
    }
    
    private void buildPacket(int objectId, String htmlid, String command) {
        this.writeC(136);
        this.writeD(objectId);
        this.writeD(0);
        this.writeD(ShopXSet.MIN);
        this.writeD(ShopXSet.MIN);
        this.writeD(ShopXSet.MAX);
        this.writeH(0);
        this.writeS(htmlid);
        this.writeS(command);
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

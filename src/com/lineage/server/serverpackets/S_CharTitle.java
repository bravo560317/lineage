package com.lineage.server.serverpackets;

public class S_CharTitle extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CharTitle(int objid, StringBuilder title) {
        this._byte = null;
        this.writeC(183);
        this.writeD(objid);
        this.writeS(title.toString());
    }
    
    public S_CharTitle(int objid) {
        this._byte = null;
        this.writeC(183);
        this.writeD(objid);
        this.writeS("");
    }
    
    public S_CharTitle(int objid, String title) {
        this._byte = null;
        this.writeC(183);
        this.writeD(objid);
        this.writeS(title);
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

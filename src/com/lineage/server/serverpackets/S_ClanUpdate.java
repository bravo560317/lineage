package com.lineage.server.serverpackets;

public class S_ClanUpdate extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ClanUpdate(int objid, String Clanname, int rank) {
        this._byte = null;
        this.writeC(72);
        this.writeD(objid);
        this.writeS(Clanname);
        this.writeS(null);
        this.writeD(0);
        this.writeC(rank);
    }
    
    public S_ClanUpdate(int objid) {
        this._byte = null;
        this.writeC(72);
        this.writeD(objid);
        this.writeS(null);
        this.writeS(null);
        this.writeD(0);
        this.writeC(0);
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

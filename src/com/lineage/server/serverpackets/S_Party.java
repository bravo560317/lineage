package com.lineage.server.serverpackets;

public class S_Party extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Party(String htmlid, int objid) {
        this._byte = null;
        this.buildPacket(htmlid, objid, "", "", 0);
    }
    
    public S_Party(String htmlid, int objid, String partyname, String partymembers) {
        this._byte = null;
        this.buildPacket(htmlid, objid, partyname, partymembers, 1);
    }
    
    private void buildPacket(String htmlid, int objid, String partyname, String partymembers, int type) {
        this.writeC(39);
        this.writeD(objid);
        this.writeS(htmlid);
        this.writeH(type);
        this.writeH(2);
        this.writeS(partyname);
        this.writeS(partymembers);
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

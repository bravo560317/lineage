package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Character;

public class S_ShowPolyList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShowPolyList(int objid) {
        this._byte = null;
        this.writeC(39);
        this.writeD(objid);
        this.writeS("monlist");
    }
    
    public S_ShowPolyList(L1Character target) {
        this._byte = null;
        this.writeC(39);
        this.writeD(target.getId());
        this.writeS("monlist");
    }
    
    public S_ShowPolyList(int objid, String htmlid) {
        this._byte = null;
        this.writeC(39);
        this.writeD(objid);
        this.writeS(htmlid);
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

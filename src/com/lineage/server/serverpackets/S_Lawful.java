package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_Lawful extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Lawful(L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pc);
    }
    
    private void buildPacket(L1PcInstance pc) {
        this.writeC(34);
        this.writeD(pc.getId());
        this.writeH(pc.getLawful());
    }
    
    public S_Lawful(int objid) {
        this._byte = null;
        this.writeC(34);
        this.writeD(objid);
        this.writeH(-32768);
        this.writeH(-32768);
        this.writeH(-32768);
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

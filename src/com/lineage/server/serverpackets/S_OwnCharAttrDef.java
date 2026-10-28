package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_OwnCharAttrDef extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_OwnCharAttrDef(L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pc);
    }
    
    private void buildPacket(L1PcInstance pc) {
        this.writeC(174);
        int ac = pc.getAc();
        this.writeC(ac);
        this.writeH(pc.getFire());
        this.writeH(pc.getWater());
        this.writeH(pc.getWind());
        this.writeH(pc.getEarth());
    }
    
    public S_OwnCharAttrDef() {
        this._byte = null;
        this.writeC(174);
        this.writeC(-99);
        this.writeC(90);
        this.writeC(85);
        this.writeC(80);
        this.writeC(75);
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

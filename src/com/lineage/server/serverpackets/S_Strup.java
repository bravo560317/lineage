package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_Strup extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Strup(L1PcInstance pc, int type, int time) {
        this._byte = null;
        this.writeC(166);
        this.writeH(time);
        this.writeC((int)pc.getStr());
        this.writeC(pc.getInventory().getWeight240());
        this.writeC(type);
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

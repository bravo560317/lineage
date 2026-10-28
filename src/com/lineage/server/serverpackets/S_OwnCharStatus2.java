package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_OwnCharStatus2 extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_OwnCharStatus2(L1PcInstance pc) {
        this._byte = null;
        if (pc == null) {
            return;
        }
        else {
            this.buildPacket(pc);
            return;
        }
    }
    
    private void buildPacket(L1PcInstance pc) {
        this.writeC(155);
        this.writeC((int)pc.getStr());
        this.writeC((int)pc.getInt());
        this.writeC((int)pc.getWis());
        this.writeC((int)pc.getDex());
        this.writeC((int)pc.getCon());
        this.writeC((int)pc.getCha());
        this.writeC(pc.getInventory().getWeight240());
    }
    
    public S_OwnCharStatus2(L1PcInstance pc, int str) {
        this._byte = null;
        this.writeC(155);
        this.writeC(str);
        this.writeC((int)pc.getInt());
        this.writeC((int)pc.getWis());
        this.writeC((int)pc.getDex());
        this.writeC((int)pc.getCon());
        this.writeC((int)pc.getCha());
        this.writeC(pc.getInventory().getWeight240());
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

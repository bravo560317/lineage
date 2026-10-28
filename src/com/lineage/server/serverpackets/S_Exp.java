package com.lineage.server.serverpackets;

import com.lineage.server.datatables.ExpTable;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_Exp extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Exp(L1PcInstance pc) {
        this._byte = null;
        this.writeC(113);
        this.writeC(pc.getLevel());
        this.writeEXP(pc.getExp());
    }
    
    public S_Exp() {
        this._byte = null;
        this.writeC(113);
        this.writeC(59);
        this.writeD(414931028);
        this.writeC(1);
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    public S_Exp(int level) {
        this._byte = null;
        this.writeC(113);
        this.writeC(level);
        this.writeExp(ExpTable.getExpByLevel(level));
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

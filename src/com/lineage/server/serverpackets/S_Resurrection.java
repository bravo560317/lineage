package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_Resurrection extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Resurrection(L1PcInstance target, L1Character use, int type) {
        this._byte = null;
        this.writeC(85);
        this.writeD(target.getId());
        this.writeC(type);
        this.writeD(use.getId());
        this.writeD(target.getClassId());
    }
    
    public S_Resurrection(L1Character target, L1Character use, int type) {
        this._byte = null;
        this.writeC(85);
        this.writeD(target.getId());
        this.writeC(type);
        this.writeD(use.getId());
        this.writeD(target.getGfxId());
    }
    
    public S_Resurrection(L1PcInstance target, int opid, int type) {
        this._byte = null;
        this.writeC(opid);
        this.writeD(target.getId());
        this.writeC(type);
        this.writeD(target.getId());
        this.writeD(target.getClassId());
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

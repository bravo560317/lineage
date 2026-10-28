package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_EnterGame extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_EnterGame(L1PcInstance pc) {
        this._byte = null;
        this.writeC(223);
        this.writeC(3);
        if (pc.getClanid() > 0) {
            this.writeD(pc.getClanMemberId());
        }
        else {
            this.writeC(83);
            this.writeC(1);
            this.writeC(0);
            this.writeC(139);
        }
        this.writeC(156);
        this.writeC(31);
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

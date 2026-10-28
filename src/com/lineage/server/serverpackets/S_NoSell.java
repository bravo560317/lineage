package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;

public class S_NoSell extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NoSell(L1NpcInstance npc) {
        this._byte = null;
        this.buildPacket(npc);
    }
    
    private void buildPacket(L1NpcInstance npc) {
        this.writeC(39);
        this.writeD(npc.getId());
        this.writeS("nosell");
        this.writeC(1);
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

package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;

public class S_NewMaster extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NewMaster(String name, L1NpcInstance npc) {
        this._byte = null;
        this.buildPacket(name, npc);
    }
    
    private void buildPacket(String name, L1NpcInstance npc) {
        this.writeC(88);
        this.writeD(npc.getId());
        this.writeS(name);
    }
    
    public S_NewMaster(L1NpcInstance npc) {
        this._byte = null;
        this.writeC(88);
        this.writeD(npc.getId());
        this.writeS("");
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

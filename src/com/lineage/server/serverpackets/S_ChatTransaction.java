package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ChatTransaction extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ChatTransaction(L1PcInstance pc, String chat) {
        this._byte = null;
        this.buildPacket(pc, chat);
    }
    
    private void buildPacket(L1PcInstance pc, String chat) {
        this.writeC(243);
        this.writeC(12);
        this.writeS("[" + pc.getName() + "] " + chat);
    }
    
    public S_ChatTransaction(L1NpcInstance npc, String chat) {
        this._byte = null;
        this.writeC(243);
        this.writeC(12);
        this.writeS("[" + npc.getNameId() + "] " + chat);
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

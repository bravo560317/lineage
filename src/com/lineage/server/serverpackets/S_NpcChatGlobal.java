package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;

public class S_NpcChatGlobal extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NpcChatGlobal(L1NpcInstance npc, String chat) {
        this._byte = null;
        this.buildPacket(npc, chat);
    }
    
    private void buildPacket(L1NpcInstance npc, String chat) {
        this.writeC(161);
        this.writeC(3);
        this.writeD(npc.getId());
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

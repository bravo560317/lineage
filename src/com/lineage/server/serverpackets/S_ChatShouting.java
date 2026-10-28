package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ChatShouting extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ChatShouting(L1PcInstance pc, String chat) {
        this._byte = null;
        this.buildPacket(pc, chat);
    }
    
    private void buildPacket(L1PcInstance pc, String chat) {
        this.writeC(81);
        this.writeC(2);
        this.writeD(pc.isInvisble() ? 0 : pc.getId());
        if (pc.isProtector()) {
            this.writeS("<**守護者**> " + chat);
        }
        else {
            this.writeS("<" + pc.getName() + "> " + chat);
        }
        this.writeH(pc.getX());
        this.writeH(pc.getY());
    }
    
    public S_ChatShouting(L1NpcInstance npc, String chat) {
        this._byte = null;
        this.writeC(81);
        this.writeC(2);
        this.writeD(npc.isInvisble() ? 0 : npc.getId());
        this.writeS("<" + npc.getNameId() + "> " + chat);
        this.writeH(npc.getX());
        this.writeH(npc.getY());
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

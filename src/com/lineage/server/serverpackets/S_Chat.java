package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_Chat extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Chat(L1PcInstance pc, String chat) {
        this._byte = null;
        this.buildPacket(pc, chat);
    }
    
    private void buildPacket(L1PcInstance pc, String chat) {
        this.writeC(81);
        this.writeC(0);
        this.writeD(pc.isInvisble() ? 0 : pc.getId());
        this.writeS(pc.getViewName() + ": " + chat);
    }
    
    public S_Chat(L1NpcInstance npc, String chat) {
        this._byte = null;
        this.writeC(81);
        this.writeC(0);
        this.writeD(npc.isInvisble() ? 0 : npc.getId());
        this.writeS(npc.getNameId() + ": " + chat);
    }
    
    public S_Chat(L1Object object, String chat) {
        this._byte = null;
        this.writeC(81);
        this.writeC(0);
        this.writeD(object.getId());
        this.writeS(chat);
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

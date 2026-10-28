package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;

public class S_NpcChat extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NpcChat(L1NpcInstance npc, String chat) {
        this._byte = null;
        this.writeC(161);
        this.writeC(0);
        this.writeD(npc.getId());
        this.writeS(npc.getNameId() + ": " + chat);
    }
    
    public S_NpcChat(int objid, String chat) {
        this._byte = null;
        this.writeC(161);
        this.writeC(0);
        this.writeD(objid);
        this.writeS(chat);
    }
    
    public S_NpcChat(L1NpcInstance npc, String chat, boolean name) {
        this._byte = null;
        this.writeC(161);
        this.writeC(0);
        this.writeD(npc.getId());
        this.writeS(String.valueOf(name ? new StringBuilder(npc.getNameId()).append(": ").toString() : "") + chat);
    }
    
    public S_NpcChat(L1NpcInstance npc, L1PcInstance pc, String chat) {
        this._byte = null;
        this.writeC(161);
        this.writeC(0);
        this.writeD(npc.getId());
        this.writeS(npc.getNameId() + ": " + chat + "! " + pc.getName());
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

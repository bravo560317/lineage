package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ChatGlobal extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ChatGlobal(L1PcInstance pc, String chat) {
        this._byte = null;
        this.buildPacket(pc, chat);
    }
    
    private void buildPacket(L1PcInstance pc, String chat) {
        this.writeC(243);
        this.writeC(3);
        if (pc.isGm()) {
            this.writeS("[******] " + chat);
        }
        else if (pc.isProtector()) {
            this.writeS("\\aD[**守護者**] " + chat);
        }
        else {
            this.writeS("[" + pc.getName() + "] " + chat);
        }
    }
    
    public S_ChatGlobal(L1NpcInstance npc, String chat) {
        this._byte = null;
        this.writeC(243);
        this.writeC(3);
        this.writeS("[" + npc.getNameId() + "] " + chat);
    }
    
    public S_ChatGlobal(String chat) {
        this._byte = null;
        this.writeC(243);
        this.writeC(3);
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

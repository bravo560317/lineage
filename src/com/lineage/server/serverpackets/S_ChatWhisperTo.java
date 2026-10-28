package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ChatWhisperTo extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ChatWhisperTo(L1PcInstance pc, String chat) {
        this._byte = null;
        this.buildPacket(pc, chat);
    }
    
    private void buildPacket(L1PcInstance pc, String chat) {
        this.writeC(243);
        this.writeC(9);
        this.writeS("-> (" + pc.getName() + ") " + chat);
    }
    
    public S_ChatWhisperTo(L1DeInstance de, String chat) {
        this._byte = null;
        this.writeC(243);
        this.writeC(9);
        this.writeS("-> (" + de.getNameId() + ") " + chat);
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

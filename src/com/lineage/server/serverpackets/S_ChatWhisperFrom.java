package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_ChatWhisperFrom extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ChatWhisperFrom(L1PcInstance pc, String chat) {
        this._byte = null;
        this.buildPacket(pc, chat);
    }
    
    private void buildPacket(L1PcInstance pc, String chat) {
        this.writeC(73);
        this.writeS(pc.getName());
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

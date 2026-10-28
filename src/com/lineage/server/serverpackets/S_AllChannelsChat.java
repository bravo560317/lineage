package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public final class S_AllChannelsChat extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_AllChannelsChat(L1PcInstance pc, String chat, int color) {
        this._byte = null;
        this.writeC(176);
        this.writeC(18);
        this.writeH(color);
    }
    
    public S_AllChannelsChat(String chat, int color) {
        this._byte = null;
        this.writeC(176);
        this.writeC(18);
        this.writeS(chat);
        this.writeH(color);
    }
    
    @Override
    public String getType() {
        return "[S] " + this.getClass().getSimpleName();
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
}

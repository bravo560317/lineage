package com.lineage.server.serverpackets;

import com.lineage.config.Config;

public class S_ServerVersion extends ServerBasePacket
{
    private static final int CLIENT_LANGUAGE;
    private static final int UPTIME;
    private byte[] _byte;
    
    static {
        CLIENT_LANGUAGE = Config.CLIENT_LANGUAGE;
        UPTIME = (int)(System.currentTimeMillis() / 1000L);
    }
    
    public S_ServerVersion() {
        this._byte = null;
        this.writeC(139);
        this.writeC(0);
        this.writeC(13);
        this.writeD(130807005);
        this.writeD(130807005);
        this.writeD(2013030701);
        this.writeD(130807001);
        this.writeD(120913201);
        this.writeC(0);
        this.writeC(0);
        this.writeC(S_ServerVersion.CLIENT_LANGUAGE);
        this.writeD(490882);
        this.writeD(S_ServerVersion.UPTIME);
        this.writeH(1);
        this.writeC(0);
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

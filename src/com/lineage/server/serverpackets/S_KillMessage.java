package com.lineage.server.serverpackets;

public class S_KillMessage extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_KillMessage(String msg) {
        this._byte = null;
        this.writeC(243);
        this.writeC(9);
        this.writeS(msg);
    }
    
    public S_KillMessage(String name, String msg, int i) {
        this._byte = null;
        this.writeC(161);
        this.writeC(0);
        this.writeD(0);
        this.writeS(" \\fY[" + name + "] " + msg);
    }
    
    public S_KillMessage(String name, String msg) {
        this._byte = null;
        this.writeC(161);
        this.writeC(0);
        this.writeD(0);
        this.writeS(" \\fY[" + name + "] " + msg);
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

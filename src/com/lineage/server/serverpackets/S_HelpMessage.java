package com.lineage.server.serverpackets;

public class S_HelpMessage extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_HelpMessage(String name, String info) {
        this._byte = null;
        this.writeC(161);
        this.writeC(0);
        this.writeD(0);
        this.writeS(name + " --> \\f4" + info);
    }
    
    public S_HelpMessage(String string) {
        this._byte = null;
        this.writeC(161);
        this.writeC(0);
        this.writeD(0);
        this.writeS(string);
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

package com.lineage.server.serverpackets;

public class S_CharSynAck extends ServerBasePacket
{
    private byte[] _byte;
    public static final int SYN = 10;
    public static final int ACK = 64;
    
    public S_CharSynAck(int type) {
        this._byte = null;
        this.buildPacket(type);
    }
    
    private void buildPacket(int type) {
        this.writeC(64);
        this.writeC(type);
        if (type == 10) {
            this.writeC(2);
            this.writeC(0);
            this.writeC(0);
            this.writeC(0);
            this.writeC(8);
            this.writeC(0);
        }
        else {
            this.writeD(0);
            this.writeH(0);
        }
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

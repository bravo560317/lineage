package com.lineage.server.serverpackets;

public class S_PacketBoxTest extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_PacketBoxTest() {
        this._byte = null;
        this.writeC(250);
        this.writeC(82);
        this.writeC(150);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
    }
    
    public S_PacketBoxTest(byte ocid, int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC((int)ocid);
        this.writeC(time);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
    }
    
    public S_PacketBoxTest(int type, int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC(type);
        this.writeH(time);
        this.writeH(0);
    }
    
    public S_PacketBoxTest(int value, String[] clanName) {
        this._byte = null;
        this.writeC(250);
        this.writeC(value);
        for (int i = 0; i < value; ++i) {
            this.writeS(clanName[i]);
        }
    }
    
    public S_PacketBoxTest(int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC(79);
        this.writeC(2);
        this.writeS("TEMP");
        this.writeS("AASS");
    }
    
    public S_PacketBoxTest(byte[] bs) {
        this._byte = null;
        for (byte outbs : bs) {
            this.writeC((int)outbs);
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

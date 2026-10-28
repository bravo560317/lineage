package com.lineage.server.serverpackets;

public class S_DoActionShop extends ServerBasePacket
{
    public S_DoActionShop(int object, byte[] message) {
        this.writeC(158);
        this.writeD(object);
        this.writeC(70);
        this.writeByte(message);
    }
    
    public S_DoActionShop(int object, String message) {
        this.writeC(158);
        this.writeD(object);
        this.writeC(70);
        this.writeS(message);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
}

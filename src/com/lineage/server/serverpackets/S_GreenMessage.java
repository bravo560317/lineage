package com.lineage.server.serverpackets;

public class S_GreenMessage extends ServerBasePacket
{
    private static final String S_GREENMESSAGE = "[S] S_GreenMessage";
    
    public S_GreenMessage(String s) {
        this.writeC(250);
        this.writeC(84);
        this.writeC(2);
        this.writeS(s);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
    
    @Override
    public String getType() {
        return "[S] S_GreenMessage";
    }
}

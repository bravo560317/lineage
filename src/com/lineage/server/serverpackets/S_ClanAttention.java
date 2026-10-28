package com.lineage.server.serverpackets;

public class S_ClanAttention extends ServerBasePacket
{
    private static final String S_ClanAttention = "[S] S_ClanAttention";
    
    public S_ClanAttention() {
        this.writeC(200);
        this.writeD(2);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
    
    @Override
    public String getType() {
        return "[S] S_ClanAttention";
    }
}

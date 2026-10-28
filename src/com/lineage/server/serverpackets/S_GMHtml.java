package com.lineage.server.serverpackets;

public class S_GMHtml extends ServerBasePacket
{
    public S_GMHtml(int _objid, String html) {
        this.writeC(39);
        this.writeD(_objid);
        this.writeS(html);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
}

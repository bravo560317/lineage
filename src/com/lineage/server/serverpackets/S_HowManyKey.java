package com.lineage.server.serverpackets;

import java.io.IOException;
import com.lineage.server.model.Instance.L1NpcInstance;

public class S_HowManyKey extends ServerBasePacket
{
    public S_HowManyKey(L1NpcInstance npc, int price, int min, int max, String htmlId) {
        this.writeC(136);
        this.writeD(npc.getId());
        this.writeD(price);
        this.writeD(min);
        this.writeD(min);
        this.writeD(max);
        this.writeH(0);
        this.writeS(htmlId);
        this.writeC(0);
        this.writeH(2);
        this.writeS(npc.getName());
        this.writeS(String.valueOf(price));
    }
    
    @Override
    public byte[] getContent() throws IOException {
        return this.getBytes();
    }
}

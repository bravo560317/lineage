package com.lineage.server.serverpackets;

import com.lineage.server.model.L1NpcTalkData;

public class S_NPCTalkActionTPUrl extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NPCTalkActionTPUrl(L1NpcTalkData cha, Object[] prices, int objid) {
        this._byte = null;
        this.buildPacket(cha, prices, objid);
    }
    
    private void buildPacket(L1NpcTalkData npc, Object[] prices, int objid) {
        String htmlid = "";
        htmlid = npc.getTeleportURL();
        this.writeC(39);
        this.writeD(objid);
        this.writeS(htmlid);
        this.writeH(1);
        this.writeH(prices.length);
        for (Object price : prices) {
            this.writeS(String.valueOf((Integer)price));
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

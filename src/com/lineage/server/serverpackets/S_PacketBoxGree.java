package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.templates.L1Rank;
import java.util.List;

public class S_PacketBoxGree extends ServerBasePacket
{
    private byte[] _byte;
    private static final int GREEN_MESSAGE = 84;
    private static final int SECRETSTORY_GFX = 83;
    
    public S_PacketBoxGree(String msg) {
        this._byte = null;
        this.writeC(250);
        this.writeC(84);
        this.writeC(2);
        this.writeS(msg);
    }
    
    public S_PacketBoxGree(int type) {
        this._byte = null;
        this.writeC(250);
        this.writeC(83);
        this.writeD(type);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
    }
    
    public S_PacketBoxGree(int type, String msg) {
        this._byte = null;
        this.writeC(250);
        this.writeC(84);
        this.writeC(type);
        this.writeS(msg);
    }
    
    public S_PacketBoxGree(List<?> totalList, int totalSize, int this_order, int this_score) {
        this._byte = null;
        this._byte = null;
        this.writeC(250);
        this.writeC(112);
        this.writeD(0);
        this.writeD(totalSize);
        for (Object obj : totalList) {
        	L1Rank rank = (L1Rank)obj;
            this.writeC(rank.getMemberSize());
            this.writeD(rank.getScore());
            this.writeS(rank.getPartyLeader());
            for (String memberName : rank.getPartyMember()) {
                this.writeS(memberName);
            }
        }
        this.writeC(this_order);
        this.writeD(this_score);
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

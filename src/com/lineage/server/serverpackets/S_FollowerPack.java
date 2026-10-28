package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1FollowerInstance;

public class S_FollowerPack extends ServerBasePacket
{
    private static final int STATUS_POISON = 1;
    private byte[] _byte;
    
    public S_FollowerPack(L1FollowerInstance follower, L1PcInstance pc) {
        this._byte = null;
        this.writeC(87);
        this.writeH(follower.getX());
        this.writeH(follower.getY());
        this.writeD(follower.getId());
        this.writeH(follower.getGfxId());
        this.writeC(follower.getStatus());
        this.writeC(follower.getHeading());
        this.writeC(follower.getChaLightSize());
        this.writeC(follower.getMoveSpeed());
        this.writeD(0);
        this.writeH(0);
        this.writeS(follower.getNameId());
        this.writeS(follower.getTitle());
        int status = 0;
        if (follower.getPoison() != null && follower.getPoison().getEffectId() == 1) {
            status |= 0x1;
        }
        this.writeC(status);
        this.writeD(0);
        this.writeS(null);
        this.writeS(null);
        this.writeC(0);
        this.writeC(255);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(255);
        this.writeC(255);
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

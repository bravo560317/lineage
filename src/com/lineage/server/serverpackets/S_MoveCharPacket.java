package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Character;

public class S_MoveCharPacket extends ServerBasePacket
{
    private byte[] _byte;
    private static final byte[] HEADING_TABLE_XR;
    private static final byte[] HEADING_TABLE_YR;
    
    static {
        HEADING_TABLE_XR = new byte[] { 0, -1, -1, -1, 0, 1, 1, 1 };
        HEADING_TABLE_YR = new byte[] { 1, 1, 0, -1, -1, -1, 0, 1 };
    }
    
    public S_MoveCharPacket(L1Character cha) {
        this._byte = null;
        int locx = cha.getX();
        int locy = cha.getY();
        int heading = Math.floorMod(cha.getHeading(), 8);
        locx += S_MoveCharPacket.HEADING_TABLE_XR[heading];
        locy += S_MoveCharPacket.HEADING_TABLE_YR[heading];
        this.writeC(10);
        this.writeD(cha.getId());
        this.writeH(locx);
        this.writeH(locy);
        this.writeC(cha.getHeading());
        this.writeC(129);
        this.writeD(0);
    }
    
    public S_MoveCharPacket(L1Character cha, int locx, int locy) {
        this._byte = null;
        int heading = Math.floorMod(cha.getHeading(), 8);
        locx += S_MoveCharPacket.HEADING_TABLE_XR[heading];
        locy += S_MoveCharPacket.HEADING_TABLE_YR[heading];
        this.writeC(10);
        this.writeD(cha.getId());
        this.writeH(locx);
        this.writeH(locy);
        this.writeC(cha.getHeading());
        this.writeC(129);
        this.writeD(0);
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

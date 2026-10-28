package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Location;
import com.lineage.server.types.Point;

public class S_EffectLocation extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_EffectLocation(Point pt, int gfxId) {
        this(pt.getX(), pt.getY(), gfxId);
    }
    
    public S_EffectLocation(L1Location loc, int gfxId) {
        this(loc.getX(), loc.getY(), gfxId);
    }
    
    public S_EffectLocation(int x, int y, int gfxId) {
        this._byte = null;
        this.writeC(106);
        this.writeH(x);
        this.writeH(y);
        this.writeH(gfxId);
    }
    
    public S_EffectLocation(int opid, L1Location loc) {
        this._byte = null;
        this.writeC(opid);
        this.writeH(loc.getX());
        this.writeH(loc.getY());
        this.writeH(4842);
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

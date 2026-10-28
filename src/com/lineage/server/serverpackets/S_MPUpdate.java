package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.RangeInt;

public class S_MPUpdate extends ServerBasePacket
{
    private byte[] _byte;
    private static final RangeInt _mpRangeA;
    private static final RangeInt _mpRangeX;
    
    static {
        _mpRangeA = new RangeInt(0, 32767);
        _mpRangeX = new RangeInt(1, 32767);
    }
    
    public S_MPUpdate(int currentmp, int maxmp) {
        this._byte = null;
        this.buildPacket(currentmp, maxmp);
    }
    
    public S_MPUpdate(L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pc.getCurrentMp(), pc.getMaxMp());
    }
    
    private void buildPacket(int currentmp, int maxmp) {
        this.writeC(33);
        this.writeH(S_MPUpdate._mpRangeA.ensure(currentmp));
        this.writeH(S_MPUpdate._mpRangeX.ensure(maxmp));
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

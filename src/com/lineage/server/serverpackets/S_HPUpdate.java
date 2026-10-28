package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.RangeInt;

public class S_HPUpdate extends ServerBasePacket
{
    private byte[] _byte;
    private static final RangeInt _hpRange;
    
    static {
        _hpRange = new RangeInt(1, 32767);
    }
    
    public S_HPUpdate(int currentHp, int maxHp) {
        this._byte = null;
        this.buildPacket(currentHp, maxHp);
    }
    
    public S_HPUpdate(L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pc.getCurrentHp(), pc.getMaxHp());
    }
    
    public void buildPacket(int currentHp, int maxHp) {
        this.writeC(225);
        this.writeH(S_HPUpdate._hpRange.ensure(currentHp));
        this.writeH(S_HPUpdate._hpRange.ensure(maxHp));
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

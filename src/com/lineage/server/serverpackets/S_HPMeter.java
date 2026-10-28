package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Character;

public class S_HPMeter extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_HPMeter(int objId, int hpRatio) {
        this._byte = null;
        this.buildPacket(objId, hpRatio);
    }
    
    public S_HPMeter(L1Character cha) {
        this._byte = null;
        int objId = cha.getId();
        int hpRatio = 100;
        if (cha.getMaxHp() > 0) {
            hpRatio = 100 * cha.getCurrentHp() / cha.getMaxHp();
        }
        this.buildPacket(objId, hpRatio);
    }
    
    private void buildPacket(int objId, int hpRatio) {
        this.writeC(237);
        this.writeD(objId);
        this.writeC(hpRatio);
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

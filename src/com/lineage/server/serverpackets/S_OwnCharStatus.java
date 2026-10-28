package com.lineage.server.serverpackets;

import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_OwnCharStatus extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_OwnCharStatus(L1PcInstance pc) {
        this._byte = null;
        int time = L1GameTimeClock.getInstance().currentTime().getSeconds();
        time -= time % 300;
        this.writeC(8);
        this.writeD(pc.getId());
        this.writeC(pc.getLevel());
        this.writeExp(pc.getExp());
        this.writeC((int)pc.getStr());
        this.writeC((int)pc.getInt());
        this.writeC((int)pc.getWis());
        this.writeC((int)pc.getDex());
        this.writeC((int)pc.getCon());
        this.writeC((int)pc.getCha());
        this.writeH(pc.getCurrentHp());
        this.writeH(pc.getMaxHp());
        this.writeH(pc.getCurrentMp());
        this.writeH(pc.getMaxMp());
        this.writeC(pc.getAc());
        this.writeD(time);
        this.writeC(pc.get_food());
        this.writeC(pc.getInventory().getWeight240());
        this.writeH(pc.getLawful());
        this.writeH(pc.getFire());
        this.writeH(pc.getWater());
        this.writeH(pc.getWind());
        this.writeH(pc.getEarth());
        this.writeD(pc.getKillCount());
    }
    
    public S_OwnCharStatus(L1PcInstance pc, int str) {
        this._byte = null;
        int time = L1GameTimeClock.getInstance().currentTime().getSeconds();
        time -= time % 300;
        this.writeC(8);
        this.writeD(pc.getId());
        this.writeC(pc.getLevel());
        this.writeExp(pc.getExp());
        this.writeC(str);
        this.writeC((int)pc.getInt());
        this.writeC((int)pc.getWis());
        this.writeC((int)pc.getDex());
        this.writeC((int)pc.getCon());
        this.writeC((int)pc.getCha());
        this.writeH(pc.getCurrentHp());
        this.writeH(pc.getMaxHp());
        this.writeH(pc.getCurrentMp());
        this.writeH(pc.getMaxMp());
        this.writeC(pc.getAc());
        this.writeD(time);
        this.writeC(pc.get_food());
        this.writeC(pc.getInventory().getWeight240());
        this.writeH(pc.getLawful());
        this.writeH(pc.getFire());
        this.writeH(pc.getWater());
        this.writeH(pc.getWind());
        this.writeH(pc.getEarth());
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

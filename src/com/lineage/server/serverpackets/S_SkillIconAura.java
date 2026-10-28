package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_SkillIconAura extends ServerBasePacket
{
    public S_SkillIconAura(int i, int j) {
        this.writeC(250);
        this.writeC(22);
        this.writeC(i);
        this.writeH(j);
    }
    
    public S_SkillIconAura(int type, int time, L1PcInstance pc) {
        this.writeC(250);
        this.writeC(22);
        this.writeC(type);
        this.writeH(time);
        this.writeC(pc.getAc());
    }
    
    public S_SkillIconAura() {
        this.writeC(250);
        this.writeC(22);
        this.writeC(147);
        this.writeH(500);
        this.writeC(-22);
        this.writeC(-33);
        this.writeC(-44);
    }
    
    public S_SkillIconAura(int i, int j, int k) {
        this.writeC(250);
        this.writeC(22);
        this.writeC(i);
        this.writeH(j);
        this.writeC(k);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
    
    @Override
    public String getType() {
        return "[S] " + this.getClass().getSimpleName() + " [S->C 發送封包盒子(王族精靈魔法圖示 & 火焰之影 炎魔攻擊圖示)]";
    }
}

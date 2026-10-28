package com.lineage.server.serverpackets;

import com.lineage.william.IntSp;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_SPMR extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_SPMR(L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pc);
    }
    
    private void buildPacket(L1PcInstance pc) {
        this.writeC(37);
        int sp = pc.getSp() - pc.getTrueSp();
        if (pc.getInt() <= 77) {
            int othersp = IntSp.getIntSpSkill(pc, (int)pc.getInt());
            if (othersp != 0) {
                sp += othersp;
            }
        }
        if (pc.getInt() >= 78) {
            sp += 0;
        }
        if (pc.hasSkillEffect(1004)) {
            sp -= 2;
        }
        int mr = pc.getTrueMr() - pc.getBaseMr();
        switch (pc.guardianEncounter()) {
            case 0: {
                mr += 3;
                break;
            }
            case 1: {
                mr += 6;
                break;
            }
            case 2: {
                mr += 9;
                break;
            }
            case 3: {
                ++sp;
                break;
            }
            case 4: {
                sp += 2;
                break;
            }
            case 5: {
                sp += 3;
                break;
            }
        }
        this.writeC(sp);
        if (mr >= 250) {
            this.writeC(250);
        }
        else if (mr > 0 && mr < 250) {
            this.writeC(mr);
        }
        else {
            this.writeC(0);
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

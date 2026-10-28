package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_PacketBoxActiveSpells extends ServerBasePacket
{
    private byte[] _byte;
    private int icon_list_size;
    
    public S_PacketBoxActiveSpells(L1PcInstance pc) {
        this._byte = null;
        this.icon_list_size = 104;
        this.writeC(250);
        this.writeC(20);
        this.writeD(this.icon_list_size);
        int[] type = this.activeSpells(pc);
        int[] array;
        for (int length = (array = type).length, j = 0; j < length; ++j) {
            int i = array[j];
            switch (i) {
                case 72: {
                    this.writeD((int)(System.currentTimeMillis() / 1000L));
                    break;
                }
                default: {
                    this.writeC(i);
                    break;
                }
            }
        }
    }
    
    private int[] activeSpells(L1PcInstance pc) {
        int[] data = new int[this.icon_list_size];
        if (pc.hasSkillEffect(1017)) {
            data[61] = pc.getSkillEffectTimeSec(1017) >> 2;
        }
        if (pc.hasSkillEffect(111)) {
            data[17] = pc.getSkillEffectTimeSec(111) >> 2;
        }
        if (pc.hasSkillEffect(188)) {
            data[57] = pc.getSkillEffectTimeSec(188) >> 2;
        }
        int[] bs_gx = { 4401, 4402, 4403, 4404, 4405, 4406, 4407, 4408, 4409 };
        for (int i = 0; i < bs_gx.length; ++i) {
            if (pc.hasSkillEffect(bs_gx[i])) {
                data[102] = pc.getSkillEffectTimeSec(bs_gx[i]) >> 5;
                if (data[102] != 0) {
                    data[103] = bs_gx[i] - 4317;
                }
            }
        }
        int[] bs_ax = { 4411, 4412, 4413, 4414, 4415, 4416, 4417, 4418, 4419 };
        for (int j = 0; j < bs_ax.length; ++j) {
            if (pc.hasSkillEffect(bs_ax[j])) {
                data[102] = pc.getSkillEffectTimeSec(bs_ax[j]) >> 5;
                if (data[102] != 0) {
                    data[103] = bs_ax[j] - 4318;
                }
            }
        }
        int[] bs_wx = { 4421, 4422, 4423, 4424, 4425, 4426, 4427, 4428, 4429 };
        for (int k = 0; k < bs_wx.length; ++k) {
            if (pc.hasSkillEffect(bs_wx[k])) {
                data[102] = pc.getSkillEffectTimeSec(bs_wx[k]) >> 5;
                if (data[102] != 0) {
                    data[103] = bs_wx[k] - 4319;
                }
            }
        }
        int[] bs_asx = { 4431, 4432, 4433, 4434, 4435, 4436, 4437, 4438, 4439 };
        for (int l = 0; l < bs_asx.length; ++l) {
            if (pc.hasSkillEffect(bs_asx[l])) {
                data[102] = pc.getSkillEffectTimeSec(bs_asx[l]) >> 5;
                if (data[102] != 0) {
                    data[103] = bs_asx[l] - 4320;
                }
            }
        }
        if (pc.hasSkillEffect(6683)) {
            data[78] = pc.getSkillEffectTimeSec(6683) >> 5;
            if (data[78] != 0) {
                data[79] = 49;
            }
        }
        if (pc.hasSkillEffect(6684)) {
            data[78] = pc.getSkillEffectTimeSec(6684) >> 5;
            if (data[78] != 0) {
                data[79] = 46;
            }
        }
        if (pc.hasSkillEffect(6685)) {
            data[78] = pc.getSkillEffectTimeSec(6685) >> 5;
            if (data[78] != 0) {
                data[79] = 47;
            }
        }
        if (pc.hasSkillEffect(6686)) {
            data[78] = pc.getSkillEffectTimeSec(6686) >> 5;
            if (data[78] != 0) {
                data[79] = 48;
            }
        }
        if (pc.hasSkillEffect(6687)) {
            data[78] = pc.getSkillEffectTimeSec(6687) >> 5;
            if (data[78] != 0) {
                data[79] = 52;
            }
        }
        if (pc.hasSkillEffect(6688)) {
            data[78] = pc.getSkillEffectTimeSec(6688) >> 5;
            if (data[78] != 0) {
                data[79] = 50;
            }
        }
        if (pc.hasSkillEffect(6689)) {
            data[78] = pc.getSkillEffectTimeSec(6689) >> 5;
            if (data[78] != 0) {
                data[79] = 51;
            }
        }
        if (pc.hasSkillEffect(4009)) {
            data[76] = pc.getSkillEffectTimeSec(4009) >> 5;
            if (data[76] != 0) {
                data[77] = 45;
            }
        }
        if (pc.hasSkillEffect(4010)) {
            data[76] = pc.getSkillEffectTimeSec(4010) >> 5;
            if (data[76] != 0) {
                data[77] = 60;
            }
        }
        if (pc.hasSkillEffect(8060)) {
            data[46] = pc.getSkillEffectTimeSec(8060) / 16;
            if (data[46] != 0) {
                data[47] = 2;
            }
        }
        return data;
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

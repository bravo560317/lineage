package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_InitialAbilityGrowth extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_InitialAbilityGrowth(L1PcInstance pc) {
        this._byte = null;
        int Str = pc.getOriginalStr();
        int Dex = pc.getOriginalDex();
        int Con = pc.getOriginalCon();
        int Wis = pc.getOriginalWis();
        int Cha = pc.getOriginalCha();
        int Int = pc.getOriginalInt();
        int[] growth = new int[6];
        if (pc.isCrown()) {
            int[] Initial = { 13, 10, 10, 11, 13, 10 };
            growth[0] = Str - Initial[0];
            growth[1] = Dex - Initial[1];
            growth[2] = Con - Initial[2];
            growth[3] = Wis - Initial[3];
            growth[4] = Cha - Initial[4];
            growth[5] = Int - Initial[5];
        }
        if (pc.isWizard()) {
            int[] Initial = { 8, 7, 12, 12, 8, 12 };
            growth[0] = Str - Initial[0];
            growth[1] = Dex - Initial[1];
            growth[2] = Con - Initial[2];
            growth[3] = Wis - Initial[3];
            growth[4] = Cha - Initial[4];
            growth[5] = Int - Initial[5];
        }
        if (pc.isKnight()) {
            int[] Initial = { 16, 12, 14, 9, 12, 8 };
            growth[0] = Str - Initial[0];
            growth[1] = Dex - Initial[1];
            growth[2] = Con - Initial[2];
            growth[3] = Wis - Initial[3];
            growth[4] = Cha - Initial[4];
            growth[5] = Int - Initial[5];
        }
        if (pc.isElf()) {
            int[] Initial = { 11, 12, 12, 12, 9, 12 };
            growth[0] = Str - Initial[0];
            growth[1] = Dex - Initial[1];
            growth[2] = Con - Initial[2];
            growth[3] = Wis - Initial[3];
            growth[4] = Cha - Initial[4];
            growth[5] = Int - Initial[5];
        }
        if (pc.isDarkelf()) {
            int[] Initial = { 12, 15, 8, 10, 9, 11 };
            growth[0] = Str - Initial[0];
            growth[1] = Dex - Initial[1];
            growth[2] = Con - Initial[2];
            growth[3] = Wis - Initial[3];
            growth[4] = Cha - Initial[4];
            growth[5] = Int - Initial[5];
        }
        if (pc.isDragonKnight()) {
            int[] Initial = { 13, 11, 14, 12, 8, 11 };
            growth[0] = Str - Initial[0];
            growth[1] = Dex - Initial[1];
            growth[2] = Con - Initial[2];
            growth[3] = Wis - Initial[3];
            growth[4] = Cha - Initial[4];
            growth[5] = Int - Initial[5];
        }
        if (pc.isIllusionist()) {
            int[] Initial = { 11, 10, 12, 12, 8, 12 };
            growth[0] = Str - Initial[0];
            growth[1] = Dex - Initial[1];
            growth[2] = Con - Initial[2];
            growth[3] = Wis - Initial[3];
            growth[4] = Cha - Initial[4];
            growth[5] = Int - Initial[5];
        }
        this.buildPacket(pc, growth[0], growth[1], growth[2], growth[3], growth[4], growth[5]);
    }
    
    private void buildPacket(L1PcInstance pc, int Str, int Dex, int Con, int Wis, int Cha, int Int) {
        int write1 = Int * 16 + Str;
        int write2 = Dex * 16 + Wis;
        int write3 = Cha * 16 + Con;
        this.writeC(64);
        this.writeC(4);
        this.writeC(write1);
        this.writeC(write2);
        this.writeC(write3);
        this.writeC(0);
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

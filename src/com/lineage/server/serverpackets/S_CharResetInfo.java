package com.lineage.server.serverpackets;

import com.lineage.server.clientpackets.C_CreateChar;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_CharResetInfo extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CharResetInfo(L1PcInstance pc) {
        this._byte = null;
        int baseStr = C_CreateChar.ORIGINAL_STR[pc.getType()];
        int baseDex = C_CreateChar.ORIGINAL_DEX[pc.getType()];
        int baseCon = C_CreateChar.ORIGINAL_CON[pc.getType()];
        int baseWis = C_CreateChar.ORIGINAL_WIS[pc.getType()];
        int baseCha = C_CreateChar.ORIGINAL_CHA[pc.getType()];
        int baseInt = C_CreateChar.ORIGINAL_INT[pc.getType()];
        int originalStr = pc.getOriginalStr();
        int originalDex = pc.getOriginalDex();
        int originalCon = pc.getOriginalCon();
        int originalWis = pc.getOriginalWis();
        int originalCha = pc.getOriginalCha();
        int originalInt = pc.getOriginalInt();
        int upStr = originalStr - baseStr;
        int upDex = originalDex - baseDex;
        int upCon = originalCon - baseCon;
        int upWis = originalWis - baseWis;
        int upCha = originalCha - baseCha;
        int upInt = originalInt - baseInt;
        this.writeC(64);
        this.writeC(4);
        this.writeC((upInt << 4) + upStr);
        this.writeC((upDex << 4) + upWis);
        this.writeC((upCha << 4) + upCon);
        this.writeC(0);
        this.writeH(0);
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

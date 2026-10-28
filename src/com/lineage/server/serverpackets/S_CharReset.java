package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_CharReset extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CharReset(L1PcInstance pc, int lv, int hp, int mp, int ac, int str, int intel, int wis, int dex, int con, int cha) {
        this._byte = null;
        this.writeC(64);
        this.writeC(2);
        this.writeC(lv);
        this.writeC(pc.getTempMaxLevel());
        this.writeH(hp);
        this.writeH(mp);
        this.writeH(ac);
        this.writeC(str);
        this.writeC(intel);
        this.writeC(wis);
        this.writeC(dex);
        this.writeC(con);
        this.writeC(cha);
    }
    
    public S_CharReset(int point) {
        this._byte = null;
        this.writeC(64);
        this.writeC(3);
        this.writeC(point);
    }
    
    public S_CharReset(L1PcInstance pc) {
        this._byte = null;
        this.writeC(64);
        this.writeC(1);
        if (pc.isCrown()) {
            this.writeH(14);
            this.writeH(2);
        }
        else if (pc.isKnight()) {
            this.writeH(16);
            this.writeH(1);
        }
        else if (pc.isElf()) {
            this.writeH(15);
            this.writeH(4);
        }
        else if (pc.isWizard()) {
            this.writeH(12);
            this.writeH(6);
        }
        else if (pc.isDarkelf()) {
            this.writeH(12);
            this.writeH(3);
        }
        else if (pc.isDragonKnight()) {
            this.writeH(15);
            this.writeH(4);
        }
        else if (pc.isIllusionist()) {
            this.writeH(15);
            this.writeH(4);
        }
        this.writeC(10);
        this.writeC(pc.getTempMaxLevel());
    }
    
    public S_CharReset(int pcObjId, int emblemId) {
        this._byte = null;
        this.writeC(64);
        this.writeC(60);
        this.writeD(pcObjId);
        this.writeD(emblemId);
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

package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1MonsterEnhanceInstance;
import com.lineage.server.datatables.MonsterEnhanceTable;
import com.lineage.data.event.Npc_Dead;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;

public class S_NPCPack extends ServerBasePacket
{
    private static final int STATUS_POISON = 1;
    private static final int STATUS_PC = 4;
    private byte[] _byte;
    
    public S_NPCPack(L1NpcInstance npc, L1PcInstance pc) {
        this._byte = null;
        this.writeC(87);
        this.writeH(npc.getX());
        this.writeH(npc.getY());
        this.writeD(npc.getId());
        if (npc.getTempCharGfx() == 0) {
            this.writeH(npc.getGfxId());
        }
        else {
            this.writeH(npc.getTempCharGfx());
        }
        if (npc.getNpcTemplate().is_doppel() && npc.getGfxId() != 31) {
            this.writeC(4);
        }
        else {
            this.writeC(npc.getStatus());
        }
        this.writeC(npc.getHeading());
        this.writeC(npc.getChaLightSize());
        this.writeC(npc.getMoveSpeed());
        this.writeD((int)npc.getExp());
        this.writeH(npc.getTempLawful());
        int CurrentDc = 0;
        if (Npc_Dead.START && MonsterEnhanceTable.getInstance().getTemplate(npc.getNpcId()) != null) {
            L1MonsterEnhanceInstance mei = MonsterEnhanceTable.getInstance().getTemplate(npc.getNpcId());
            if (mei.getCurrentDc() > 0) {
                CurrentDc = mei.getCurrentDc();
            }
        }
        if (CurrentDc > 0) {
            this.writeS(npc.getNameId() + "強化(" + CurrentDc + ")次");
        }
        else {
            this.writeS(npc.getNameId());
        }
        this.writeS(npc.getTitle());
        int status = 0;
        if (npc.getPoison() != null && npc.getPoison().getEffectId() == 1) {
            status |= 0x1;
        }
        if (npc.getNpcTemplate().is_doppel() && npc.getNpcTemplate().get_npcId() != 81069) {
            status |= 0x4;
        }
        if (npc.getNpcTemplate().get_npcId() == 90024) {
            status |= 0x1;
        }
        this.writeC(status);
        this.writeD(0);
        this.writeS(null);
        this.writeS(null);
        this.writeC(0);
        this.writeC(255);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(255);
        this.writeC(255);
    }
    
    public S_NPCPack(L1NpcInstance npc) {
        this._byte = null;
        this.writeC(87);
        this.writeH(npc.getX());
        this.writeH(npc.getY());
        this.writeD(npc.getId());
        if (npc.getTempCharGfx() == 0) {
            this.writeH(npc.getGfxId());
        }
        else {
            this.writeH(npc.getTempCharGfx());
        }
        if (npc.getNpcTemplate().is_doppel() && npc.getGfxId() != 31) {
            this.writeC(4);
        }
        else {
            this.writeC(npc.getStatus());
        }
        this.writeC(npc.getHeading());
        this.writeC(npc.getChaLightSize());
        this.writeC(npc.getMoveSpeed());
        this.writeD((int)npc.getExp());
        this.writeH(npc.getTempLawful());
        this.writeS(npc.getNameId());
        this.writeS(npc.getTitle());
        int status = 0;
        if (npc.getPoison() != null && npc.getPoison().getEffectId() == 1) {
            status |= 0x1;
        }
        if (npc.getNpcTemplate().is_doppel() && npc.getNpcTemplate().get_npcId() != 81069) {
            status |= 0x4;
        }
        if (npc.getNpcTemplate().get_npcId() == 90024) {
            status |= 0x1;
        }
        this.writeC(status);
        this.writeD(0);
        this.writeS(null);
        this.writeS(null);
        this.writeC(0);
        this.writeC(255);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(255);
        this.writeC(255);
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

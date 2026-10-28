package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1SummonInstance;

public class S_NPCPack_Summon extends ServerBasePacket
{
    private static final int STATUS_POISON = 1;
    private byte[] _byte;
    
    public S_NPCPack_Summon(L1SummonInstance pet, L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pet, pc, true);
    }
    
    public S_NPCPack_Summon(L1SummonInstance pet, L1PcInstance pc, boolean isCheckMaster) {
        this._byte = null;
        this.buildPacket(pet, pc, isCheckMaster);
    }
    
    private void buildPacket(L1SummonInstance pet, L1PcInstance pc, boolean isCheckMaster) {
        this.writeC(87);
        this.writeH(pet.getX());
        this.writeH(pet.getY());
        this.writeD(pet.getId());
        this.writeH(pet.getGfxId());
        this.writeC(pet.getStatus());
        this.writeC(pet.getHeading());
        this.writeC(pet.getChaLightSize());
        this.writeC(pet.getMoveSpeed());
        this.writeD(0);
        this.writeH(0);
        this.writeS(pet.getNameId());
        this.writeS(pet.getTitle());
        int status = 0;
        if (pet.getPoison() != null && pet.getPoison().getEffectId() == 1) {
            status |= 0x1;
        }
        this.writeC(status);
        this.writeD(0);
        this.writeS(null);
        StringBuilder stringBuilder = new StringBuilder();
        if (isCheckMaster && pet.isExsistMaster()) {
            if (pet.getMaster() instanceof L1PcInstance) {
                L1PcInstance master = (L1PcInstance)pet.getMaster();
                if (master != null) {
                    if (master.get_other().get_color() != 0) {
                        stringBuilder.append(master.get_other().color());
                    }
                    if (master.isProtector()) {
                        stringBuilder.append("**守護者**");
                    }
                    else {
                        stringBuilder.append(master.getViewName());
                        if (master.get_c_power() != null && master.get_c_power().get_c1_type() != 0) {
                            String type = master.get_c_power().get_power().get_c1_name_type();
                            stringBuilder.append(type);
                        }
                    }
                }
            }
        }
        else {
            stringBuilder.append("");
        }
        if (pet.getMaster() instanceof L1NpcInstance) {
            L1NpcInstance npc = (L1NpcInstance)pet.getMaster();
            stringBuilder.append(npc.getNameId());
        }
        this.writeS(stringBuilder.toString());
        this.writeC(0);
        if (!(pet.getMaster() instanceof L1NpcInstance)) {
            if (pet.getMaster() != null && pet.getMaster().getId() == pc.getId()) {
                int percent = (pet.getMaxHp() != 0) ? (100 * pet.getCurrentHp() / pet.getMaxHp()) : 100;
                this.writeC(percent);
            }
            else {
                this.writeC(255);
            }
        }
        else {
            this.writeC(255);
        }
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

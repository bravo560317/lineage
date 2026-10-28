package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1HierarchInstance;

public class S_NPCPack_Hierarch extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NPCPack_Hierarch(L1HierarchInstance pet) {
        this._byte = null;
        this.writeC(87);
        this.writeH(pet.getX());
        this.writeH(pet.getY());
        this.writeD(pet.getId());
        this.writeH(pet.getGfxId());
        this.writeC(pet.getStatus());
        this.writeC(pet.getHeading());
        this.writeC(0);
        this.writeC(pet.getMoveSpeed());
        this.writeD(0);
        this.writeH(0);
        this.writeS(pet.getNameId());
        this.writeS(pet.getTitle());
        this.writeC(0);
        this.writeD(0);
        this.writeS(null);
        StringBuilder stringBuilder = new StringBuilder();
        if (pet.getMaster() != null) {
            if (pet.getMaster() instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)pet.getMaster();
                if (pc.isProtector()) {
                    stringBuilder.append("**守護者**");
                }
                else {
                    stringBuilder.append(pc.getViewName());
                }
            }
        }
        else if (pet.getMaster() instanceof L1NpcInstance) {
            L1NpcInstance npc = (L1NpcInstance)pet.getMaster();
            stringBuilder.append(npc.getNameId());
        }
        this.writeS(stringBuilder.toString());
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

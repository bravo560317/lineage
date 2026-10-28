package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1DeInstance2;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1DollInstance;

public class S_NPCPack_Doll extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NPCPack_Doll(L1DollInstance pet, L1PcInstance pc) {
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
        if (pet.getMaster() != null) {
            if (pet.getMaster() instanceof L1PcInstance) {
                L1PcInstance master = (L1PcInstance)pet.getMaster();
                if (master.isProtector()) {
                    this.writeS("**守護者**");
                }
                else {
                    this.writeS(master.getName());
                }
            }
            // ★ 修正：如果主人是 L1DeInstance2 (掛網假人)，則讀取其 Title 作為主人名稱
            else if (pet.getMaster() instanceof L1DeInstance2) {
                L1DeInstance2 npc = (L1DeInstance2)pet.getMaster();
                this.writeS(npc.getNameId()); // 讀取假人的 NameId (真名)
            }
            else if (pet.getMaster() instanceof L1NpcInstance) {
                L1NpcInstance npc = (L1NpcInstance) pet.getMaster();
                this.writeS(npc.getNameId());
            }
        }
        else {
            this.writeS("");
        }
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

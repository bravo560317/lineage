package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1NpcInstance;

public class S_PetMenuPacket extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_PetMenuPacket(L1NpcInstance npc, int exppercet) {
        this._byte = null;
        this.buildpacket(npc, exppercet);
    }
    
    private void buildpacket(L1NpcInstance npc, int exppercet) {
        this.writeC(39);
        if (npc instanceof L1PetInstance) {
            L1PetInstance pet = (L1PetInstance)npc;
            this.writeD(pet.getId());
            this.writeS("anicom");
            this.writeC(0);
            this.writeH(10);
            switch (pet.getCurrentPetStatus()) {
                case 1: {
                    this.writeS("$469");
                    break;
                }
                case 2: {
                    this.writeS("$470");
                    break;
                }
                case 3: {
                    this.writeS("$471");
                    break;
                }
                case 5: {
                    this.writeS("$472");
                    break;
                }
                default: {
                    this.writeS("$471");
                    break;
                }
            }
            this.writeS(Integer.toString(pet.getCurrentHp()));
            this.writeS(Integer.toString(pet.getMaxHp()));
            this.writeS(Integer.toString(pet.getCurrentMp()));
            this.writeS(Integer.toString(pet.getMaxMp()));
            this.writeS(Integer.toString(pet.getLevel()));
            this.writeS(pet.getName());
            this.writeS("$611");
            this.writeS(Integer.toString(exppercet));
            this.writeS(Integer.toString(pet.getLawful()));
        }
        else if (npc instanceof L1SummonInstance) {
            L1SummonInstance summon = (L1SummonInstance)npc;
            this.writeD(summon.getId());
            this.writeS("moncom");
            this.writeC(0);
            this.writeH(6);
            switch (summon.get_currentPetStatus()) {
                case 1: {
                    this.writeS("$469");
                    break;
                }
                case 2: {
                    this.writeS("$470");
                    break;
                }
                case 3: {
                    this.writeS("$471");
                    break;
                }
                case 5: {
                    this.writeS("$472");
                    break;
                }
                default: {
                    this.writeS("$471");
                    break;
                }
            }
            this.writeS(Integer.toString(summon.getCurrentHp()));
            this.writeS(Integer.toString(summon.getMaxHp()));
            this.writeS(Integer.toString(summon.getCurrentMp()));
            this.writeS(Integer.toString(summon.getMaxMp()));
            this.writeS(Integer.toString(summon.getLevel()));
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

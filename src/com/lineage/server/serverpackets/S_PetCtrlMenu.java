package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;

public class S_PetCtrlMenu extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_PetCtrlMenu(L1Character cha, L1NpcInstance npc, boolean open) {
        this._byte = null;
        this.writeC(64);
        this.writeC(12);
        if (open) {
            this.writeH(3);
            this.writeD(0);
            this.writeD(npc.getId());
            this.writeD(0);
            this.writeH(npc.getX());
            this.writeH(npc.getY());
            this.writeS(npc.getName());
        }
        else {
            this.writeH(0);
            this.writeD(1);
            this.writeD(npc.getId());
            this.writeS(null);
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

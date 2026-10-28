package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1MonsterInstance;

public class S_MoveNpcPacket extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_MoveNpcPacket(L1MonsterInstance npc, int x, int y, int heading) {
        this._byte = null;
        this.writeC(10);
        this.writeD(npc.getId());
        this.writeH(x);
        this.writeH(y);
        this.writeC(heading);
        this.writeC(128);
    }
    
    public S_MoveNpcPacket(L1Character cha) {
        this._byte = null;
        this.writeC(10);
        this.writeD(cha.getId());
        this.writeH(cha.getX());
        this.writeH(cha.getY());
        this.writeC(cha.getHeading());
        this.writeC(128);
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

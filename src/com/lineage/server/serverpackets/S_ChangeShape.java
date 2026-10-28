package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;

public class S_ChangeShape extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ChangeShape(L1Character obj, int polyId) {
        this._byte = null;
        this.buildPacket(obj, polyId, false);
    }
    
    public S_ChangeShape(L1Character obj, int polyId, boolean weaponTakeoff) {
        this._byte = null;
        this.buildPacket(obj, polyId, weaponTakeoff);
    }
    
    private void buildPacket(L1Character obj, int polyId, boolean weaponTakeoff) {
        this.writeC(76);
        this.writeD(obj.getId());
        this.writeH(polyId);
        this.writeH(weaponTakeoff ? 0 : 29);
    }
    
    public S_ChangeShape(L1PcInstance pc, L1NpcInstance npc, int polyId) {
        this._byte = null;
        this.writeC(76);
        this.writeD(npc.getId());
        this.writeD(pc.getId());
        this.writeH(polyId);
        this.writeS(pc.getName());
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
}

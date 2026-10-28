package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.config.ConfigOther;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1NpcInstance;

public class S_AttackPacketNpc extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_AttackPacketNpc(L1NpcInstance npc, L1Character target, int actid, int dmg) {
        this._byte = null;
        this.writeC(30);
        this.writeC(actid);
        this.writeD(npc.getId());
        int targetobj = target.getId();
        L1Object obj = World.get().findObject(targetobj);
        if (obj instanceof L1NpcInstance) {
            L1NpcInstance tgnpc = (L1NpcInstance)obj;
            int[] bow_GFX_Arrow = ConfigOther.AtkNo;
            int[] array;
            for (int length = (array = bow_GFX_Arrow).length, i = 0; i < length; ++i) {
                int gfx = array[i];
                if (tgnpc.getTempCharGfx() == gfx) {
                    targetobj = 0;
                }
            }
            switch (tgnpc.getTempCharGfx()) {
                case 2544:
                case 10913: {
                    targetobj = 0;
                    break;
                }
            }
        }
        if (obj instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)obj;
            int[] atkpc = ConfigOther.AtkNo_pc;
            int[] array2;
            for (int length2 = (array2 = atkpc).length, j = 0; j < length2; ++j) {
                int gfx = array2[j];
                if (pc.getTempCharGfx() == gfx) {
                    targetobj = 0;
                }
            }
        }
        this.writeD(targetobj);
        if (dmg > 0) {
            this.writeH(10);
        }
        else {
            this.writeH(0);
        }
        this.writeC(npc.getHeading());
        this.writeH(0);
        this.writeH(0);
        this.writeC(0);
    }
    
    public S_AttackPacketNpc(L1NpcInstance npc, L1Character target, int actid) {
        this._byte = null;
        this.writeC(30);
        this.writeC(actid);
        this.writeD(npc.getId());
        this.writeD(target.getId());
        this.writeH(0);
        this.writeC(npc.getHeading());
        this.writeH(0);
        this.writeH(0);
        this.writeC(0);
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

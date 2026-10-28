package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Object;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_AttackPacketPc extends ServerBasePacket
{
    private byte[] _byte;
    private int _actid;
    
    private int SpecialActid(L1PcInstance pc) {
        int tempgfxid = pc.getTempCharGfx();
        L1ItemInstance weapon = pc.getWeapon();
        if (weapon == null) {
            return this._actid;
        }
        else {
            int weapontype = weapon.getItem().getType1();
            if (tempgfxid == 13715 && weapontype == 4) {
                this._actid = 73;
            }
            if (tempgfxid == 13717 && weapontype == 4) {
                this._actid = 73;
            }
            if (tempgfxid == 13719 && weapontype == 50) {
                this._actid = 79;
            }
            if (tempgfxid == 13721 && weapontype == 50) {
                this._actid = 79;
            }
            if (tempgfxid == 13723 && weapontype == 4) {
                this._actid = 73;
            }
            if (tempgfxid == 13725 && weapontype == 4) {
                this._actid = 73;
            }
            if (tempgfxid == 13727 && weapontype == 40) {
                this._actid = 77;
            }
            if (tempgfxid == 13729 && weapontype == 40) {
                this._actid = 77;
            }
            if (tempgfxid == 13731 && weapontype == 54) {
                this._actid = 80;
            }
            if (tempgfxid == 13731 && weapontype == 58) {
                this._actid = 81;
            }
            if (tempgfxid == 13733 && weapontype == 54) {
                this._actid = 80;
            }
            if (tempgfxid == 13733 && weapontype == 58) {
                this._actid = 81;
            }
            if (tempgfxid == 13735 && weapontype == 24) {
                this._actid = 41;
            }
            if (tempgfxid == 13737 && weapontype == 24) {
                this._actid = 41;
            }
            if (tempgfxid == 13741 && weapontype == 40) {
                this._actid = 77;
            }
            if (tempgfxid == 13743 && weapontype == 11) {
                this._actid = 93;
            }
            if (tempgfxid == 13743 && weapontype == 24) {
                this._actid = 76;
            }
            if (tempgfxid == 13745 && weapontype == 11) {
                this._actid = 93;
            }
            if (tempgfxid == 13745 && weapontype == 24) {
                this._actid = 76;
            }
            if (tempgfxid >= 13216 && tempgfxid <= 13220) {
                this._actid = 30;
            }
            if (tempgfxid == 12280) {
                this._actid = 30;
            }
            if (tempgfxid == 12283) {
                this._actid = 30;
            }
            if (tempgfxid == 12286) {
                this._actid = 30;
            }
            if (tempgfxid == 12295) {
                this._actid = 30;
            }
            return this._actid;
        }
    }
    
    public S_AttackPacketPc(L1PcInstance pc, L1Character target, int type, int dmg) {
        this._byte = null;
        this._actid = 1;
        this.writeC(30);
        this.writeC(this._actid);
        this.writeD(pc.getId());
        int targetobj = target.getId();
        L1Object obj = World.get().findObject(targetobj);
        if (obj instanceof L1NpcInstance) {
            L1NpcInstance npc = (L1NpcInstance)obj;
            int[] bow_GFX_Arrow = ConfigOther.AtkNo;
            int[] array;
            for (int length = (array = bow_GFX_Arrow).length, i = 0; i < length; ++i) {
                int gfx = array[i];
                if (npc.getTempCharGfx() == gfx) {
                    targetobj = 0;
                }
            }
            switch (npc.getTempCharGfx()) {
                case 2544:
                case 16778: {
                    targetobj = 0;
                    pc.sendPackets(new S_Sound(1151));
                    break;
                }
            }
        }
        if (obj instanceof L1PcInstance) {
            L1PcInstance pc2 = (L1PcInstance)obj;
            int[] atkpc = ConfigOther.AtkNo_pc;
            int[] array2;
            for (int length2 = (array2 = atkpc).length, j = 0; j < length2; ++j) {
                int gfx = array2[j];
                if (pc2.getTempCharGfx() == gfx) {
                    targetobj = 1;
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
        this.writeC(pc.getHeading());
        this.writeH(0);
        this.writeH(0);
        this.writeC(type);
    }
    
    public S_AttackPacketPc(L1PcInstance pc, L1Character target) {
        this._byte = null;
        this._actid = 1;
        this.writeC(30);
        this.writeC(this._actid);
        this.writeD(pc.getId());
        this.writeD(target.getId());
        this.writeH(0);
        this.writeC(pc.getHeading());
        this.writeH(0);
        this.writeH(0);
        this.writeC(0);
    }
    
    public S_AttackPacketPc(L1PcInstance pc) {
        this._byte = null;
        this._actid = 1;
        this.writeC(30);
        this.writeC(this._actid);
        this.writeD(pc.getId());
        this.writeD(0);
        this.writeH(0);
        this.writeC(pc.getHeading());
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

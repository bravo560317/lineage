package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Object;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import java.util.concurrent.atomic.AtomicInteger;

public class S_UseArrowSkill extends ServerBasePacket
{
    private byte[] _byte;
    private int _actid;
    private static AtomicInteger _sequentialNumber;
    
    static {
        S_UseArrowSkill._sequentialNumber = new AtomicInteger(0);
    }
    
    private int SpecialActid(L1Character cha) {
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            int tempgfxid = cha.getTempCharGfx();
            L1ItemInstance weapon = pc.getWeapon();
            if (weapon == null) {
                return this._actid;
            }
            else {
                int weapontype = weapon.getItem().getType1();
                if (tempgfxid == 13723 && weapontype == 20) {
                    this._actid = 75;
                }
                if (tempgfxid == 13725 && weapontype == 20) {
                    this._actid = 75;
                }
                if (tempgfxid == 13218) {
                    this._actid = 30;
                }
                if (tempgfxid == 12314) {
                    this._actid = 30;
                }
            }
        }
        return this._actid;
    }
    
    public S_UseArrowSkill(L1Character cha, int targetobj, int spellgfx, int targetx, int targety, int dmg, int actId) {
        this._byte = null;
        this._actid = 1;
        switch (cha.getTempCharGfx()) {
            case 2716: {
                this._actid = 19;
                break;
            }
            case 3860: {
                this._actid = 21;
                break;
            }
            case 5127:
            case 10649:
            case 11714: {
                this._actid = 30;
                break;
            }
            case 10566: {
                this._actid = 18;
                break;
            }
        }
        this.writeC(30);
        if (actId > 1) {
            this._actid = actId;
        }
        this.writeC(this._actid);
        this.writeD(cha.getId());
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
                case 10913: {
                    targetobj = 0;
                    break;
                }
            }
        }
        if (obj instanceof L1PcInstance) {
            L1PcInstance pc1 = (L1PcInstance)obj;
            int[] atkpc = ConfigOther.AtkNo_pc;
            int[] array2;
            for (int length2 = (array2 = atkpc).length, j = 0; j < length2; ++j) {
                int gfx = array2[j];
                if (pc1.getTempCharGfx() == gfx) {
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
        this.writeC(cha.getHeading());
        this.writeD(S_UseArrowSkill._sequentialNumber.incrementAndGet());
        this.writeH(spellgfx);
        this.writeC(0);
        this.writeH(cha.getX());
        this.writeH(cha.getY());
        this.writeH(targetx);
        this.writeH(targety);
        this.writeD(0);
        this.writeC(0);
    }
    
    public S_UseArrowSkill(L1Character cha, int spellgfx, int targetx, int targety, int actId) {
        this._byte = null;
        this._actid = 1;
        switch (cha.getTempCharGfx()) {
            case 2716: {
                this._actid = 19;
                break;
            }
            case 3860: {
                this._actid = 21;
                break;
            }
            case 5127:
            case 10649:
            case 11714: {
                this._actid = 30;
                break;
            }
            case 10566: {
                this._actid = 18;
                break;
            }
        }
        this.writeC(30);
        if (actId > 1) {
            this._actid = actId;
        }
        this.writeC(this._actid);
        this.writeD(cha.getId());
        this.writeD(0);
        this.writeH(0);
        this.writeC(cha.getHeading());
        this.writeD(S_UseArrowSkill._sequentialNumber.incrementAndGet());
        this.writeH(spellgfx);
        this.writeC(0);
        this.writeH(cha.getX());
        this.writeH(cha.getY());
        this.writeH(targetx);
        this.writeH(targety);
        this.writeD(0);
        this.writeC(0);
    }
    
    public S_UseArrowSkill(L1Character cha) {
        this._byte = null;
        this._actid = 1;
        switch (cha.getTempCharGfx()) {
            case 2716: {
                this._actid = 19;
                break;
            }
            case 3860: {
                this._actid = 21;
                break;
            }
            case 5127:
            case 10649:
            case 11714: {
                this._actid = 30;
                break;
            }
            case 10566: {
                this._actid = 18;
                break;
            }
        }
        this.writeC(30);
        this.writeC(this._actid);
        this.writeD(cha.getId());
        this.writeD(0);
        this.writeH(1);
        this.writeC(cha.getHeading());
        this.writeD(S_UseArrowSkill._sequentialNumber.incrementAndGet());
        this.writeH(2510);
        this.writeC(0);
        this.writeH(cha.getX());
        this.writeH(cha.getY());
        this.writeH(0);
        this.writeH(0);
        this.writeD(0);
        this.writeC(0);
    }
    
    public S_UseArrowSkill(L1Character cha, int targetobj, int spellgfx, int x, int y, int dmg) {
        this._byte = null;
        this._actid = 1;
        int aid = 1;
        switch (cha.getTempCharGfx()) {
            case 2716: {
                aid = 19;
                break;
            }
            case 3860: {
                aid = 21;
                break;
            }
        }
        this.writeC(30);
        this.writeC(aid);
        this.writeD(cha.getId());
        this.writeD(targetobj);
        if (dmg > 0) {
            this.writeH(10);
        }
        else {
            this.writeH(0);
        }
        this.writeC(cha.getHeading());
        this.writeD(338);
        this.writeH(spellgfx);
        this.writeC(127);
        this.writeH(cha.getX());
        this.writeH(cha.getY());
        this.writeH(x);
        this.writeH(y);
        this.writeD(0);
        this.writeC(0);
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this._bao.toByteArray();
        }
        else {
            int seq = 0;
            synchronized (this) {
                seq = S_UseArrowSkill._sequentialNumber.incrementAndGet();
            }
            this._byte[13] = (byte)(seq & 0xFF);
            this._byte[14] = (byte)(seq >> 8 & 0xFF);
            this._byte[15] = (byte)(seq >> 16 & 0xFF);
            this._byte[16] = (byte)(seq >> 24 & 0xFF);
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

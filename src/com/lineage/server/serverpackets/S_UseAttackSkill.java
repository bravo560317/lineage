package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Object;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.Random;
import com.lineage.server.model.L1Character;
import java.util.concurrent.atomic.AtomicInteger;

public class S_UseAttackSkill extends ServerBasePacket
{
    private static AtomicInteger _sequentialNumber;
    private byte[] _byte;
    
    static {
        S_UseAttackSkill._sequentialNumber = new AtomicInteger(0);
    }
    
    private int SpecialActid(L1Character cha) {
        int actionId = 18;
        int tempgfxid = cha.getTempCharGfx();
        if (Random.nextInt(100) < 25) {
            if (tempgfxid == 13727 || tempgfxid == 13729) {
                actionId = 31;
            }
            if (tempgfxid == 13731 || tempgfxid == 13733) {
                actionId = 31;
            }
        }
        if (tempgfxid == 5727 || tempgfxid == 5730) {
            actionId = 19;
        }
        if (tempgfxid == 5733 || tempgfxid == 5736) {
            actionId = 1;
        }
        return actionId;
    }
    
    public S_UseAttackSkill(L1Character cha, int targetobj, int spellgfx, int x, int y, int actionId, boolean motion) {
        this._byte = null;
        this.buildPacket(cha, targetobj, spellgfx, x, y, actionId, 0, motion);
    }
    
    public S_UseAttackSkill(L1Character cha, int targetobj, int spellgfx, int x, int y, int actionId, int dmg) {
        this._byte = null;
        this.buildPacket(cha, targetobj, spellgfx, x, y, actionId, dmg, true);
    }
    
    public S_UseAttackSkill(L1Character cha, int targetobj, int x, int y, int[] data, boolean withCastMotion) {
        this._byte = null;
        this.buildPacket(cha, targetobj, x, y, data, withCastMotion);
    }
    
    private void buildPacket(L1Character cha, int targetobj, int x, int y, int[] data, boolean withCastMotion) {
        if (cha instanceof L1PcInstance && cha.hasSkillEffect(67) && data[0] == 18) {
            int tempchargfx = cha.getTempCharGfx();
            if (tempchargfx == 5727 || tempchargfx == 5730) {
                data[0] = 19;
            }
            else if (tempchargfx == 5733 || tempchargfx == 5736) {
                data[0] = 1;
            }
        }
        if (cha.getTempCharGfx() == 4013) {
            data[0] = 1;
        }
        int newheading = calcheading(cha.getX(), cha.getY(), x, y);
        cha.setHeading(newheading);
        this.writeC(30);
        this.writeC(data[0]);
        this.writeD(withCastMotion ? cha.getId() : 0);
        this.writeD(targetobj);
        this.writeH(data[1]);
        this.writeC(newheading);
        this.writeD(S_UseAttackSkill._sequentialNumber.incrementAndGet());
        this.writeH(data[2]);
        this.writeC(data[3]);
        this.writeH(cha.getX());
        this.writeH(cha.getY());
        this.writeH(x);
        this.writeH(y);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
    }
    
    private void buildPacket(L1Character cha, int targetobj, int spellgfx, int x, int y, int actionId, int dmg, boolean withCastMotion) {
        if (cha instanceof L1PcInstance && cha.hasSkillEffect(67) && actionId == 18) {
            actionId = this.SpecialActid(cha);
        }
        if (cha.getTempCharGfx() == 4013) {
            actionId = 1;
        }
        int newheading = calcheading(cha.getX(), cha.getY(), x, y);
        cha.setHeading(newheading);
        this.writeC(30);
        this.writeC(actionId);
        this.writeD(withCastMotion ? cha.getId() : 0);
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
        this.writeC(newheading);
        this.writeD(S_UseAttackSkill._sequentialNumber.incrementAndGet());
        this.writeH(spellgfx);
        this.writeC(0);
        this.writeH(cha.getX());
        this.writeH(cha.getY());
        this.writeH(x);
        this.writeH(y);
        this.writeD(0);
        this.writeC(0);
    }
    
    private static int calcheading(int myx, int myy, int tx, int ty) {
        int newheading = 0;
        if (tx > myx && ty > myy) {
            newheading = 3;
        }
        if (tx < myx && ty < myy) {
            newheading = 7;
        }
        if (tx > myx && ty == myy) {
            newheading = 2;
        }
        if (tx < myx && ty == myy) {
            newheading = 6;
        }
        if (tx == myx && ty < myy) {
            newheading = 0;
        }
        if (tx == myx && ty > myy) {
            newheading = 4;
        }
        if (tx < myx && ty > myy) {
            newheading = 5;
        }
        if (tx > myx && ty < myy) {
            newheading = 1;
        }
        return newheading;
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this._bao.toByteArray();
        }
        else {
            int seq = 0;
            synchronized (this) {
                seq = S_UseAttackSkill._sequentialNumber.incrementAndGet();
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

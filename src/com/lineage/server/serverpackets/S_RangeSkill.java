package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Object;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.skill.TargetStatus;
import java.util.ArrayList;
import com.lineage.server.model.L1Character;
import java.util.concurrent.atomic.AtomicInteger;

public class S_RangeSkill extends ServerBasePacket
{
    private static AtomicInteger _sequentialNumber;
    private byte[] _byte;
    public static final int TYPE_NODIR = 0;
    public static final int TYPE_DIR = 8;
    
    static {
        S_RangeSkill._sequentialNumber = new AtomicInteger(9000000);
    }
    
    public S_RangeSkill(L1Character cha, ArrayList<TargetStatus> targetList, int spellgfx, int actionId, int type) {
        this._byte = null;
        this.writeC(42);
        this.writeC(actionId);
        this.writeD(cha.getId());
        this.writeH(cha.getX());
        this.writeH(cha.getY());
        switch (type) {
            case 0: {
                this.writeC(cha.getHeading());
                break;
            }
            case 8: {
                int newHeading = calcheading(cha.getX(), cha.getY(), (targetList.get(0)).getTarget().getX(), (targetList.get(0)).getTarget().getY());
                cha.setHeading(newHeading);
                this.writeC(cha.getHeading());
                break;
            }
        }
        this.writeD(S_RangeSkill._sequentialNumber.incrementAndGet());
        this.writeH(spellgfx);
        this.writeC(type);
        this.writeH(0);
        this.writeH(targetList.size());
        for (TargetStatus target : targetList) {
            int targetobj = target.getTarget().getId();
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
                    case 45024: {
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
            if (target.isCalc()) {
                this.writeH(32);
            }
            else {
                this.writeH(0);
            }
        }
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
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

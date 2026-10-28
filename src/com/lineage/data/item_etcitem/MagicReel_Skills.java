package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Character;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class MagicReel_Skills extends ItemExecutor
{
    private static final Random _random;
    private int _type1;
    private int _type2;
    private int _type3;
    private int _type4;
    private int _type5;
    
    static {
        _random = new Random();
    }
    
    private MagicReel_Skills() {
    }
    
    public static ItemExecutor get() {
        return new MagicReel_Skills();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null) {
            return;
        }
        else if (item == null) {
            return;
        }
        else if (pc.isInvisble() || pc.isInvisDelay()) {
            pc.sendPackets(new S_ServerMessage(281));
            return;
        }
        else {
            L1BuffUtil.cancelAbsoluteBarrier(pc);
            switch (this._type1) {
                case 1: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else if (!pc.isCrown()) {
                        pc.sendPackets(new S_ServerMessage("\\fY您的職業不是王族無法使用"));
                        return;
                    }
                    else if (pc.getWeapon() == null) {
                        pc.sendPackets(new S_ServerMessage("空手狀態無法施法。"));
                        return;
                    }
                    else if (pc.getWeapon().getItem().getType() != 3) {
                        pc.sendPackets(new S_ServerMessage("非雙手武器狀態無法施法。"));
                        return;
                    }
                    else if (cha.hasSkillEffect(8877)) {
                        return;
                    }
                    else if (pc.hasSkillEffect(8878)) {
                        pc.sendPackets(new S_ServerMessage("該道具技能延遲中"));
                        return;
                    }
                    else if (MagicReel_Skills._random.nextInt(100) < this._type2) {
                        int shock = this._type3 + MagicReel_Skills._random.nextInt(this._type4) + 1;
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance targetPc = (L1PcInstance)cha;
                            L1BuffUtil.cancelAbsoluteBarrier(targetPc);
                            targetPc.setSkillEffect(8877, shock * 1000);
                            pc.setSkillEffect(8878, this._type5 * 1000);
                            targetPc.sendPackets(new S_Paralysis(5, true));
                            pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 1));
                            L1SpawnUtil.spawnEffect(81162, shock, targetPc.getX(), targetPc.getY(), targetPc.getMapId(), targetPc, 0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        pc.setSkillEffect(8878, this._type5 * 1000);
                        pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 1));
                        pc.sendPackets(new S_ServerMessage(280));
                        return;
                    }
                }
            }
            return;
        }
    }
    
    private final L1Character getTarget(L1PcInstance pc, int targetID) {
        if (targetID == pc.getId() || targetID == 0) {
            pc.sendPackets(new S_ServerMessage(281));
            return null;
        }
        else {
            L1Object target = World.get().findObject(targetID);
            if (target == null || !(target instanceof L1Character)) {
                pc.sendPackets(new S_ServerMessage(281));
                return null;
            }
            else {
                return (L1Character)target;
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._type1 = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._type2 = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._type3 = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this._type4 = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
        try {
            this._type5 = Integer.parseInt(set[5]);
        }
        catch (Exception ex5) {}
    }
}

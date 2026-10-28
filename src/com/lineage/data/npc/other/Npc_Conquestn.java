package com.lineage.data.npc.other;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Conquestn extends NpcExecutor
{
    private static final Log _log;
    private boolean _checkDead;
    
    static {
        _log = LogFactory.getLog(Npc_Conquestn.class);
    }
    
    private Npc_Conquestn() {
    }
    
    public static NpcExecutor get() {
        return new Npc_Conquestn();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null) {
                ArrayList<L1Character> targetList = npc.getHateList().toTargetArrayList();
                if (!targetList.isEmpty()) {
                    for (L1Character cha : targetList) {
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance l1PcInstance = (L1PcInstance)cha;
                        }
                    }
                    for (L1Character cha : targetList) {
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance find_pc = (L1PcInstance)cha;
                            if (this._checkDead && find_pc.getCurrentHp() > 0) {
                                find_pc.isDead();
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            ArrayList<L1Object> list = World.get().getVisibleObjects(pc, -1);
            if (list == null) {
                return pc;
            }
            else {
                for (L1Object object : list) {
                    if (object instanceof L1MonsterInstance) {
                        L1MonsterInstance dota = (L1MonsterInstance)object;
                        dota.broadcastPacketAll(new S_DoActionGFX(dota.getId(), 2));
                        dota.receiveDamage(pc, 300);
                    }
                }
                pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7771));
            }
        }
        catch (Exception e) {
            Npc_Conquestn._log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}

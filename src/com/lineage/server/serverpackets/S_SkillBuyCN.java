package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.data.event.SkillTeacherSet;
import java.util.ArrayList;
import com.lineage.list.PcLvSkillList;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class S_SkillBuyCN extends ServerBasePacket
{
    private static final Log _log;
    private byte[] _byte;
    public static final int[] PCTYPE;
    
    static {
        _log = LogFactory.getLog(S_SkillBuyCN.class);
        PCTYPE = new int[] { 2150, 2450, 1800, 5580, 1950, 620, 470 };
    }
    
    public S_SkillBuyCN(L1PcInstance pc, L1NpcInstance npc) {
        this._byte = null;
        ArrayList<Integer> skillList = null;
        if (pc.isCrown()) {
            skillList = PcLvSkillList.isCrown(pc);
        }
        else if (pc.isKnight()) {
            skillList = PcLvSkillList.isKnight(pc);
        }
        else if (pc.isElf()) {
            skillList = PcLvSkillList.isElf(pc);
        }
        else if (pc.isWizard()) {
            skillList = PcLvSkillList.isWizard(pc);
        }
        else if (pc.isDarkelf()) {
            skillList = PcLvSkillList.isDarkelf(pc);
        }
        else if (pc.isDragonKnight()) {
            skillList = PcLvSkillList.isDragonKnight(pc);
        }
        else if (pc.isIllusionist()) {
            skillList = PcLvSkillList.isIllusionist(pc);
        }
        ArrayList<Integer> newSkillList = new ArrayList();
        for (Integer integer : skillList) {
            if (SkillTeacherSet.RESKILLLIST.get(integer) == null && !CharSkillReading.get().spellCheck(pc.getId(), integer + 1)) {
                newSkillList.add(integer);
            }
        }
        if (newSkillList.size() <= 0) {
            this.writeC(39);
            this.writeD(npc.getId());
            this.writeS("y_skill_02");
            this.writeH(0);
            this.writeH(0);
        }
        else {
            int startAdena = S_SkillBuyCN.PCTYPE[pc.getType()];
            try {
                this.writeC(41);
                this.writeD(startAdena);
                this.writeH(newSkillList.size());
                for (Integer integer2 : newSkillList) {
                    this.writeD(integer2);
                }
            }
            catch (Exception e) {
                S_SkillBuyCN._log.error(e.getLocalizedMessage(), e);
            }
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

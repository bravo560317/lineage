package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.datatables.lock.CharSkillReading;
import java.util.ArrayList;
import com.lineage.list.PcLvSkillList;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class S_SkillBuyItem extends ServerBasePacket
{
    private static final Log _log;
    private byte[] _byte;
    
    static {
        _log = LogFactory.getLog(S_SkillBuyItem.class);
    }
    
    public S_SkillBuyItem(L1PcInstance pc, L1NpcInstance npc) {
        this._byte = null;
        ArrayList<Integer> skillList = PcLvSkillList.scount(pc);
        ArrayList<Integer> newSkillList = new ArrayList();
        for (Integer integer : skillList) {
            if (!CharSkillReading.get().spellCheck(pc.getId(), integer + 1)) {
                newSkillList.add(integer);
            }
        }
        if (newSkillList.size() <= 0) {
            this.writeC(41);
            this.writeH(0);
        }
        else {
            try {
                this.writeC(41);
                this.writeH(newSkillList.size());
                for (Integer integer : newSkillList) {
                    this.writeD(integer);
                }
            }
            catch (Exception e) {
                S_SkillBuyItem._log.error(e.getLocalizedMessage(), e);
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

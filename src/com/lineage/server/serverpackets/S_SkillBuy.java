package com.lineage.server.serverpackets;

import com.lineage.server.datatables.lock.CharSkillReading;
import com.add.AutoAttackUpdate;
import com.lineage.list.PcLvSkillList;
import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class S_SkillBuy extends ServerBasePacket
{
    private static final Log _log;
    private byte[] _byte;
    
    static {
        _log = LogFactory.getLog(S_SkillBuy.class);
    }
    
    public S_SkillBuy(L1PcInstance pc, ArrayList<Integer> newSkillList) {
        this._byte = null;
        try {
            if (newSkillList.size() <= 0) {
                this.writeC(41);
                this.writeH(0);
            }
            else {
                this.writeC(41);
                this.writeD(6000);
                this.writeH(newSkillList.size());
                for (Integer integer : newSkillList) {
                    this.writeD(integer);
                }
            }
        }
        catch (Exception e) {
            S_SkillBuy._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public S_SkillBuy(L1PcInstance pc) {
        this._byte = null;
        try {
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
                // 攻擊技能 or 群體技能開關檢查
                if (pc.getAu_AutoSkill(0) > 0) {
                    if (!AutoAttackUpdate.getInstance().CheckAttackSkill(integer)) {
                        continue; // 不在允許攻擊清單內 → 跳過
                    }
                } else if (pc.getAu_AutoSkill(1) > 0) {
                    if (!AutoAttackUpdate.getInstance().CheckAreaAttackSkill(integer)) {
                        continue; // 不在允許群體技能清單內 → 跳過
                    }
                }

                // 判斷是否學會此技能
                if (CharSkillReading.get().spellCheck(pc.getId(), integer)) {
                    newSkillList.add(integer);
                }
            }

            if (newSkillList.size() <= 0) {
                pc.sendPackets(new S_ServerMessage("\\fU沒有可以設定的技能。"));
                pc.setAu_AutoSkill(0, 0);
                pc.setAu_AutoSkill(1, 0);
            }
            else {
                this.writeC(41);
                this.writeD(0);
                this.writeH(newSkillList.size());
                for (Integer integer : newSkillList) {
                    int q = integer - 1;
                    this.writeD(q);
                }
            }
        }
        catch (Exception e) {
            S_SkillBuy._log.error(e.getLocalizedMessage(), e);
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

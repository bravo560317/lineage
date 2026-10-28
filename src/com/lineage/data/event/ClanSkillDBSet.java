package com.lineage.data.event;

import com.lineage.server.templates.L1ClanSkills;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.RewardClanSkillsTable;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class ClanSkillDBSet extends EventExecutor
{
    private static final Log _log;
    public static boolean START;
    public static String[] _skillNameNote;
    
    static {
        _log = LogFactory.getLog(ClanSkillDBSet.class);
        ClanSkillDBSet.START = false;
        ClanSkillDBSet._skillNameNote = null;
    }
    
    public static EventExecutor get() {
        return new ClanSkillDBSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            if (ClanSkillSet.START) {
                ClanSkillDBSet._log.info("警告!活動設置:伊薇版血盟技能已啟動狀態下無法同時啟動111項血盟技能");
            }
            else {
                RewardClanSkillsTable.get();
                ClanSkillDBSet._skillNameNote = RewardClanSkillsTable.get().getSkillsNameNote();
                ClanSkillDBSet.START = true;
            }
        }
        catch (Exception e) {
            ClanSkillDBSet._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void add(L1PcInstance pc) {
        if (!ClanSkillDBSet.START) {
            return;
        }
        else {
            int clanSkillsId = pc.getClan().getClanSkillId();
            if (clanSkillsId == 0) {
                return;
            }
            else {
                int clanSkillsLv = pc.getClan().getClanSkillLv();
                if (clanSkillsLv == 0) {
                    return;
                }
                else {
                    L1ClanSkills skill = RewardClanSkillsTable.get().getClanSkillsList(clanSkillsId, clanSkillsLv);
                    if (skill == null) {
                        return;
                    }
                    else {
                        int CheckLvturn = skill.getCheckLvturn();
                        if (CheckLvturn != 0 && pc.getMeteLevel() < CheckLvturn) {
                            return;
                        }
                        else {
                            int CheckLevel = skill.getCheckLevel();
                            if (CheckLevel != 0 && pc.getHighLevel() < CheckLevel) {
                                return;
                            }
                            else {
                                boolean OwnCharStatus = false;
                                boolean hp = false;
                                boolean mp = false;
                                boolean spmr = false;
                                int AddMaxHp = skill.getAddMaxHp();
                                if (AddMaxHp != 0) {
                                    pc.addMaxHp(AddMaxHp);
                                    hp = true;
                                }
                                int AddMaxMp = skill.getAddMaxMp();
                                if (AddMaxMp != 0) {
                                    pc.addMaxMp(AddMaxMp);
                                    mp = true;
                                }
                                int AddHpr = skill.getAddHpr();
                                if (AddHpr != 0) {
                                    pc.addHpr(AddHpr);
                                }
                                int AddMpr = skill.getAddMpr();
                                if (AddMpr != 0) {
                                    pc.addMpr(AddMpr);
                                }
                                int AddStr = skill.getAddStr();
                                if (AddStr != 0) {
                                    pc.addStr(AddStr);
                                    OwnCharStatus = true;
                                }
                                int AddDex = skill.getAddDex();
                                if (AddDex != 0) {
                                    pc.addDex(AddDex);
                                    OwnCharStatus = true;
                                }
                                int AddCon = skill.getAddCon();
                                if (AddCon != 0) {
                                    pc.addCon(AddCon);
                                    OwnCharStatus = true;
                                }
                                int AddInt = skill.getAddInt();
                                if (AddInt != 0) {
                                    pc.addInt(AddInt);
                                    OwnCharStatus = true;
                                }
                                int AddWis = skill.getAddWis();
                                if (AddWis != 0) {
                                    pc.addWis(AddWis);
                                    OwnCharStatus = true;
                                }
                                int AddCha = skill.getAddCha();
                                if (AddCha != 0) {
                                    pc.addCha(AddCha);
                                    OwnCharStatus = true;
                                }
                                int AddAc = skill.getAddAc();
                                if (AddAc != 0) {
                                    pc.addAc(-AddAc);
                                    OwnCharStatus = true;
                                }
                                int AddSp = skill.getAddSp();
                                if (AddSp != 0) {
                                    pc.addSp(AddSp);
                                }
                                int AddMr = skill.getAddMr();
                                if (AddMr != 0) {
                                    pc.addMr(AddMr);
                                    spmr = true;
                                }
                                int AddDmg = skill.getAddDmg();
                                if (AddDmg != 0) {
                                    pc.addDmgup(AddDmg);
                                    spmr = true;
                                }
                                int AddBowDmg = skill.getAddBowDmg();
                                if (AddBowDmg != 0) {
                                    pc.addBowDmgup(AddBowDmg);
                                }
                                int AddHit = skill.getAddHit();
                                if (AddHit != 0) {
                                    pc.addHitup(AddHit);
                                }
                                int AddBowHit = skill.getAddBowHit();
                                if (AddBowHit != 0) {
                                    pc.addBowHitup(AddBowHit);
                                }
                                int ReductionDmg = skill.getReductionDmg();
                                if (ReductionDmg != 0) {
                                    pc.add_reduction_dmg(ReductionDmg);
                                }
                                int ReductionMagicDmg = skill.getReductionMagicDmg();
                                if (ReductionMagicDmg != 0) {
                                    pc.add_magic_reduction_dmg(ReductionMagicDmg);
                                }
                                int AddWater = skill.getAddWater();
                                if (AddWater != 0) {
                                    pc.addWater(AddWater);
                                    OwnCharStatus = true;
                                }
                                int AddWind = skill.getAddWind();
                                if (AddWind != 0) {
                                    pc.addWind(AddWind);
                                    OwnCharStatus = true;
                                }
                                int AddFire = skill.getAddFire();
                                if (AddFire != 0) {
                                    pc.addFire(AddFire);
                                    OwnCharStatus = true;
                                }
                                int AddEarth = skill.getAddEarth();
                                if (AddEarth != 0) {
                                    pc.addEarth(AddEarth);
                                    OwnCharStatus = true;
                                }
                                if (OwnCharStatus) {
                                    pc.sendPackets(new S_OwnCharStatus(pc));
                                }
                                if (hp) {
                                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                                }
                                if (mp) {
                                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                                }
                                if (spmr) {
                                    pc.sendPackets(new S_SPMR(pc));
                                }
                                String ClanSkillName = skill.getClanSkillName();
                                String Note = skill.getNote();
                                if (ClanSkillName != null && Note != null) {
                                    pc.sendPackets(new S_ServerMessage("\\fU" + ClanSkillName + ":"));
                                    pc.sendPackets(new S_ServerMessage("\\fU" + Note));
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}

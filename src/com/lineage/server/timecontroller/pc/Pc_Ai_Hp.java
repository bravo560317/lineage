package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1PolyMorph;
import com.add.system.CardBookCmd_doll;
import com.lineage.william.Eq_give_skill;
import com.lineage.data.event.Eqitemskill;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class Pc_Ai_Hp extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static int _time;
    
    static {
        _log = LogFactory.getLog(Pc_Ai_Hp.class);
        Pc_Ai_Hp._time = 0;
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : all) {
                    if (check(tgpc)) {
                        if (Eqitemskill.START) {
                            Eq_give_skill.forIntensifyArmor_login(tgpc);
                        }
                        if (tgpc.getdollcount() == 0 && tgpc.getdollcheck() > 0 && tgpc.getdollcheckitem() > 0 && tgpc.getQuest().get_step(tgpc.getdollcheck()) == 1) {
                            CardBookCmd_doll.useMagicDoll(tgpc, tgpc.getdollcheckitem(), tgpc.getdollcheckitem());
                        }
                        if (tgpc.getdollcount1() == 0 && tgpc.getdollcheck1() > 0 && tgpc.getdollcheckitem1() > 0 && tgpc.getQuest().get_step(tgpc.getdollcheck1()) == 1) {
                            CardBookCmd_doll.useMagicDoll(tgpc, tgpc.getdollcheckitem1(), tgpc.getdollcheckitem1());
                        }
                     // 自動循環變身
                        if (!tgpc.isSimpleAutoHunt() && tgpc.getcardspoly() > 0 && tgpc.getTempCharGfx() != tgpc.getcardspoly()) {

                            // 檢查是否有 40088
                            if (!tgpc.getInventory().checkItem(40088, 1)) {
                                // 沒有道具 → 關閉循環並提示
                                tgpc.setcardspoly(0);
                                tgpc.sendPackets(new S_SystemMessage("缺少變形卷軸，已關閉循環變身"));
                                continue; // 不進行變身
                            }
                            tgpc.getInventory().consumeItem(40088, 1);

                            // 有道具才變身
                            L1PolyMorph.doPoly(tgpc, tgpc.getcardspoly(), 1800, 1);
                        }

                        if (tgpc.isActived() && !tgpc.isSimpleAutoHunt()) {
                            if (tgpc.getAu_AutoSkill(8) > 0 && !tgpc.hasSkillEffect(9981)) {
                                tgpc.setSkillEffect(9981, 1800000);
                                L1SkillUse skillUse = new L1SkillUse();
                                skillUse.handleCommands(tgpc, 51, tgpc.getId(), tgpc.getX(), tgpc.getY(), 0, 4);
                            }
                            if (tgpc.getAu_AutoSkill(8) > 0) {
                                for (L1NpcInstance petNpc : tgpc.getPetList().values()) {
                                    double e = (double)tgpc.getCurrentMp();
                                    double f = (double)tgpc.getMaxMp();
                                    if (petNpc instanceof L1SummonInstance) {
                                        L1SummonInstance summon = (L1SummonInstance)petNpc;
                                        if (e / f * 100.0 > (double)tgpc.getsummon_skillidmp_1() && !summon.isDead() && petNpc != null) {
                                            if (tgpc.getCurrentMp() > tgpc.getsummon_skillidmp() && summon.getCurrentHp() < summon.getMaxHp() / 2) {
                                                L1SkillUse skillUse2 = new L1SkillUse();
                                                skillUse2.handleCommands(tgpc, tgpc.getsummon_skillid(), summon.getId(), summon.getX(), summon.getY(), 0, 2);
                                                tgpc.setCurrentMp(tgpc.getCurrentMp() - tgpc.getsummon_skillidmp());
                                            }
                                            if (CharSkillReading.get().spellCheck(tgpc.getId(), 43) && tgpc.getCurrentMp() > 40 && !summon.hasSkillEffect(43)) {
                                                L1SkillUse skillUse2 = new L1SkillUse();
                                                skillUse2.handleCommands(tgpc, 43, summon.getId(), summon.getX(), summon.getY(), 0, 2);
                                                tgpc.setCurrentMp(tgpc.getCurrentMp() - 40);
                                            }
                                            else {
                                                continue;
                                            }
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                        if (!tgpc.isSimpleAutoHunt()) for (L1NpcInstance petNpc : tgpc.getPetList().values()) {
                            double e = (double)tgpc.getCurrentMp();
                            double f = (double)tgpc.getMaxMp();
                            if (petNpc instanceof L1PetInstance) {
                                L1PetInstance pet = (L1PetInstance)petNpc;
                                if (e / f * 100.0 > (double)tgpc.getsummon_skillidmp_1() && !pet.isDead() && petNpc != null) {
                                    if (tgpc.getCurrentMp() > tgpc.getsummon_skillidmp() && pet.getCurrentHp() < pet.getMaxHp() / 2) {
                                        L1SkillUse skillUse2 = new L1SkillUse();
                                        skillUse2.handleCommands(tgpc, tgpc.getsummon_skillid(), pet.getId(), pet.getX(), pet.getY(), 0, 2);
                                        tgpc.setCurrentMp(tgpc.getCurrentMp() - tgpc.getsummon_skillidmp());
                                    }
                                    if (CharSkillReading.get().spellCheck(tgpc.getId(), 43) && tgpc.getCurrentMp() > 40 && !pet.hasSkillEffect(43)) {
                                        L1SkillUse skillUse2 = new L1SkillUse();
                                        skillUse2.handleCommands(tgpc, 43, pet.getId(), pet.getX(), pet.getY(), 0, 2);
                                        tgpc.setCurrentMp(tgpc.getCurrentMp() - 40);
                                    }
                                    else {
                                        continue;
                                    }
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            Pc_Ai_Hp._log.error("自動購水處理時間軸異常重啟", e2);
            PcOtherThreadPool.get().cancel(this._timer, false);
            Pc_Ai_Hp lawfulTimer = new Pc_Ai_Hp();
            lawfulTimer.start();
        }
    }
    
    private static boolean check(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
            else if (tgpc.isTeleport()) {
                return false;
            }
            else if (tgpc.isDead()) {
                return false;
            }
            else if (tgpc.getCurrentHp() <= 0) {
                return false;
            }
            else if (tgpc.isParalyzed() || tgpc.hasSkillEffect(33) || tgpc.hasSkillEffect(4000) || tgpc.hasSkillEffect(192) || tgpc.hasSkillEffect(50) || tgpc.hasSkillEffect(66) || tgpc.hasSkillEffect(87) || tgpc.hasSkillEffect(4017) || tgpc.hasSkillEffect(192)) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}

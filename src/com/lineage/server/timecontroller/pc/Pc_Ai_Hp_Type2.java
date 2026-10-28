package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.skill.L1SkillUse;
import java.util.concurrent.ScheduledFuture;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class Pc_Ai_Hp_Type2 extends TimerTask
{
    private static final Log _log;
    L1Character target;
    private ScheduledFuture<?> _timer;
    final L1SkillUse skillUse;
    
    static {
        _log = LogFactory.getLog(Pc_Ai_Hp_Type2.class);
    }
    
    public Pc_Ai_Hp_Type2() {
        this.target = null;
        this.skillUse = new L1SkillUse();
    }
    
    public void start() {
        int timeMillis = 1700;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 1700L, 1700L);
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
                    if (check(tgpc) && tgpc.getParty() != null && tgpc.get_followmaster() != null && (!tgpc.hasSkillEffect(33) || !tgpc.hasSkillEffect(4000) || !tgpc.hasSkillEffect(192) || !tgpc.hasSkillEffect(50) || !tgpc.hasSkillEffect(66) || !tgpc.hasSkillEffect(87) || !tgpc.hasSkillEffect(4017) || !tgpc.hasSkillEffect(192))) {
                        L1SkillUse skillUse = new L1SkillUse();
                        for (L1PcInstance player : World.get().getVisiblePlayer(tgpc, 10)) {
                            if (tgpc.getParty().isMember(player)) {
                                if (tgpc.getfollowskilltype() == 1 && tgpc.getCurrentMp() > 10) {
                                    if (player.getMaxHp() / 100 * tgpc.getfollowskillhp() > player.getCurrentHp()) {
                                        skillUse.handleCommands(tgpc, 1, player.getId(), player.getX(), player.getY(), 0, 2);
                                        tgpc.setCurrentMp(tgpc.getCurrentMp() - 4);
                                    }
                                }
                                else if (tgpc.getfollowskilltype() == 2 && tgpc.getCurrentMp() > 20) {
                                    if (player.getMaxHp() / 100 * tgpc.getfollowskillhp() > player.getCurrentHp()) {
                                        skillUse.handleCommands(tgpc, 19, player.getId(), player.getX(), player.getY(), 0, 2);
                                        tgpc.setCurrentMp(tgpc.getCurrentMp() - 15);
                                    }
                                }
                                else if (tgpc.getfollowskilltype() == 3 && tgpc.getCurrentMp() > 20) {
                                    if (player.getMaxHp() / 100 * tgpc.getfollowskillhp() > player.getCurrentHp()) {
                                        skillUse.handleCommands(tgpc, 35, player.getId(), player.getX(), player.getY(), 0, 2);
                                        tgpc.setCurrentMp(tgpc.getCurrentMp() - 20);
                                    }
                                }
else if (tgpc.getfollowskilltype() == 4 && tgpc.getCurrentMp() > 48) {
    if (player.getMaxHp() / 100 * tgpc.getfollowskillhp() > player.getCurrentHp()) {
        skillUse.handleCommands(tgpc, 57, player.getId(), player.getX(), player.getY(), 0, 2);
        tgpc.setCurrentMp(tgpc.getCurrentMp() - 48);
    }
}
                                if (tgpc.isfollowskill26() && !player.hasSkillEffect(26) && tgpc.getCurrentMp() > 50) {
                                    tgpc.setCurrentMp(tgpc.getCurrentMp() - 50);
                                    skillUse.handleCommands(tgpc, 26, player.getId(), player.getX(), player.getY(), 0, 2);
                                }
                                if (tgpc.isfollowskill42() && !player.hasSkillEffect(42) && tgpc.getCurrentMp() > 50) {
                                    tgpc.setCurrentMp(tgpc.getCurrentMp() - 50);
                                    skillUse.handleCommands(tgpc, 42, player.getId(), player.getX(), player.getY(), 0, 2);
                                }
                                if (tgpc.isfollowskill55() && !player.hasSkillEffect(55) && tgpc.getCurrentMp() > 40) {
                                    tgpc.setCurrentMp(tgpc.getCurrentMp() - 40);
                                    skillUse.handleCommands(tgpc, 55, player.getId(), player.getX(), player.getY(), 0, 2);
                                }
                                if (tgpc.isfollowskill68() && !player.hasSkillEffect(68) && tgpc.getCurrentMp() > 30 && tgpc.getInventory().checkItem(40318, 2L)) {
                                    tgpc.getInventory().consumeItem(40318, 2L);
                                    tgpc.setCurrentMp(tgpc.getCurrentMp() - 30);
                                    skillUse.handleCommands(tgpc, 68, player.getId(), player.getX(), player.getY(), 0, 2);
                                }
                                if (tgpc.isfollowskill79() && !player.hasSkillEffect(79) && tgpc.getCurrentMp() > 40) {
                                    tgpc.setCurrentMp(tgpc.getCurrentMp() - 40);
                                    skillUse.handleCommands(tgpc, 79, player.getId(), player.getX(), player.getY(), 0, 2);
                                }
                                if (tgpc.isfollowskill148() && !player.hasSkillEffect(148) && tgpc.getCurrentMp() > 15) {
                                    tgpc.setCurrentMp(tgpc.getCurrentMp() - 15);
                                    skillUse.handleCommands(player, 148, player.getId(), player.getX(), player.getY(), 0, 2);
                                }
                                if (tgpc.isfollowskill151() && !player.hasSkillEffect(151) && tgpc.getCurrentMp() > 15) {
                                    tgpc.setCurrentMp(tgpc.getCurrentMp() - 15);
                                    skillUse.handleCommands(player, 151, player.getId(), player.getX(), player.getY(), 0, 2);
                                }
                                if (tgpc.isfollowskill149() && !player.hasSkillEffect(149) && tgpc.getCurrentMp() > 15) {
                                    tgpc.setCurrentMp(tgpc.getCurrentMp() - 15);
                                    skillUse.handleCommands(player, 149, player.getId(), player.getX(), player.getY(), 0, 2);
                                }
                                if (tgpc.isfollowskill158() && !player.hasSkillEffect(158) && tgpc.getCurrentMp() > 20) {
                                    tgpc.setCurrentMp(tgpc.getCurrentMp() - 20);
                                    skillUse.handleCommands(player, 158, player.getId(), player.getX(), player.getY(), 0, 2);
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
        catch (Exception e) {
            Pc_Ai_Hp_Type2._log.error("高寵時間軸義長", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            Pc_Ai_Hp_Type2 lawfulTimer = new Pc_Ai_Hp_Type2();
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

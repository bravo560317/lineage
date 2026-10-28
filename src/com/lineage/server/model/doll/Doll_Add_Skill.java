package com.lineage.server.model.doll;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Doll_Add_Skill extends L1DollExecutor {

    private static final Log _log = LogFactory.getLog(Doll_Add_Skill.class);

    /** 監控旗標：key = pcId#skillId → 是否持續監控 */
    private static final ConcurrentHashMap<String, AtomicBoolean> MON_FLAGS = new ConcurrentHashMap<>();

    private int _int1;      // 技能ID
    private String _note;

    public static L1DollExecutor get() {
        return new Doll_Add_Skill();
    }

    @Override
    public void set_power(int int1, int int2, int int3, double int4) {
        try {
            this._int1 = int1;
        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void set_note(String note) {
        this._note = note;
    }

    @Override
    public String get_note() {
        return this._note;
    }

    @Override
    public void setDoll(final L1PcInstance pc) {
        try {
            tryCastIfNeeded(pc, _int1);
            startMonitor(pc, _int1);

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void removeDoll(final L1PcInstance pc) {
        final String key = buildKey(pc, _int1);
        final AtomicBoolean flag = MON_FLAGS.remove(key);
        if (flag != null) flag.set(false);
    }

    @Override
    public boolean is_reset() {
        return true;
    }

    private void cast(final L1PcInstance pc, final int skillId) {
        L1Skills sk = SkillsTable.get().getTemplate(skillId);
        if (sk == null) return;
        final int dur = Math.max(3, sk.getBuffDuration());
        L1SkillUse su = new L1SkillUse();
        su.handleCommands(pc, skillId, pc.getId(), pc.getX(), pc.getY(), dur, 4);
    }

    private void tryCastIfNeeded(final L1PcInstance pc, final int skillId) {
        if (pc == null || pc.isDead() || pc.getNetConnection() == null) return;

        L1ItemInstance targetItem = null;
        switch (skillId) {
            case 8:
            case 12:
            case 48:
            case 107:
                targetItem = pc.getWeapon();
                if (targetItem == null) return;
                if (!targetItem.isRunning()) cast(pc, skillId);
                return;

            case 21:
                targetItem = pc.getInventory().getItemEquipped(2, 2);
                if (targetItem == null) return;
                if (!targetItem.isRunning()) cast(pc, skillId);
                return;

            default:
                if (!pc.hasSkillEffect(skillId)) cast(pc, skillId);
                return;
        }
    }

    private void startMonitor(final L1PcInstance pc, final int skillId) {
        final String key = buildKey(pc, skillId);
        AtomicBoolean newFlag = new AtomicBoolean(true);
        final AtomicBoolean flag = MON_FLAGS.putIfAbsent(key, newFlag);
        if (flag != null) return;
        scheduleNext(pc, skillId, newFlag);
    }

    private void scheduleNext(final L1PcInstance pc, final int skillId, final AtomicBoolean flag) {
        if (flag == null || !flag.get()) return;
        GeneralThreadPool.get().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!flag.get()) return;

                    if (pc == null || pc.isDead() || pc.getNetConnection() == null) {
                        flag.set(false);
                        MON_FLAGS.remove(buildKey(pc, skillId));
                        return;
                    }

                    tryCastIfNeeded(pc, skillId);

                } catch (Throwable t) {
                    _log.warn("Doll_Add_Skill monitor tick error: " + t.getMessage(), t);
                } finally {
                    if (flag.get()) scheduleNext(pc, skillId, flag);
                }
            }
        }, 500);
    }

    private static String buildKey(final L1PcInstance pc, final int skillId) {
        final int pid = (pc != null) ? pc.getId() : 0;
        return pid + "#" + skillId;
    }
}
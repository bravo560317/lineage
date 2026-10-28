package com.lineage.server.command.executor;

import java.util.Iterator;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_PacketBoxGm;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1AllBuff implements L1CommandExecutor
{
    private static final Log _log;
    private static final int[] allBuffSkill;
    
    static {
        _log = LogFactory.getLog(L1AllBuff.class);
        allBuffSkill = new int[] { 26, 42, 48, 55, 68, 79, 88, 89, 90, 102, 104, 105, 106, 111, 114, 117, 129, 137, 147, 160, 163, 168, 169, 170, 171, 175, 176, 181, 182 };
    }
    
    private L1AllBuff() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1AllBuff();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String name = st.nextToken();
            if (name.equalsIgnoreCase("all")) {
                L1AllBuff.AllBuffRunnable allBuffRunnable = new L1AllBuff.AllBuffRunnable();
                GeneralThreadPool.get().execute(allBuffRunnable);
                return;
            }
            else if (name.equalsIgnoreCase("gm")) {
                startGm(pc);
                return;
            }
            else {
                L1PcInstance target = World.get().getPlayer(name);
                if (target == null) {
                    pc.sendPackets(new S_ServerMessage(73, name));
                    return;
                }
                else {
                    startPc(target);
                }
            }
        }
        catch (Exception e) {
            int mode = 4;
            pc.sendPackets(new S_PacketBoxGm(pc, 4));
        }
    }
    
    public static void startPc(L1PcInstance target) {
        L1BuffUtil.haste(target, 3600000);
        L1BuffUtil.brave(target, 3600000);
        L1ItemInstance weapon = target.getWeapon();
        if (weapon != null) {
            int weaponType = weapon.getItem().getType();
            int polyid = -1;
            switch (weaponType) {
                case 1:
                case 2:
                case 3:
                case 6:
                case 15: {
                    polyid = 6276;
                    break;
                }
                case 4:
                case 10:
                case 13: {
                    polyid = 6278;
                    break;
                }
                case 5:
                case 14:
                case 18: {
                    polyid = 7341;
                    break;
                }
                case 7:
                case 16:
                case 17: {
                    polyid = 6277;
                    break;
                }
                case 11:
                case 12: {
                    polyid = 6282;
                    break;
                }
            }
            if (polyid != -1) {
                L1PolyMorph.doPoly(target, polyid, 7200, 1);
            }
        }
        for (int i = 0; i < L1AllBuff.allBuffSkill.length; ++i) {
            L1Skills skill = SkillsTable.get().getTemplate(L1AllBuff.allBuffSkill[i]);
            L1SkillUse skillUse = new L1SkillUse();
            skillUse.handleCommands(target, L1AllBuff.allBuffSkill[i], target.getId(), target.getX(), target.getY(), skill.getBuffDuration(), 4);
        }
    }
    
    public static void startGm(L1PcInstance target) {
        L1BuffUtil.haste(target, 3600000);
        L1BuffUtil.brave(target, 3600000);
        L1PolyMorph.doPoly(target, 5641, 7200, 2);
        for (int i = 0; i < L1AllBuff.allBuffSkill.length; ++i) {
            L1Skills skill = SkillsTable.get().getTemplate(L1AllBuff.allBuffSkill[i]);
            new L1SkillUse().handleCommands(target, L1AllBuff.allBuffSkill[i], target.getId(), target.getX(), target.getY(), skill.getBuffDuration(), 4);
        }
    }
    
    private class AllBuffRunnable implements Runnable
    {
        @Override
        public void run() {
            try {
                for (L1PcInstance tgpc : World.get().getAllPlayers()) {
                    L1AllBuff.startPc(tgpc);
                    Thread.sleep(1L);
                }
            }
            catch (Exception e) {
                L1AllBuff._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}

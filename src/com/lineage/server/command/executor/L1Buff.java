package com.lineage.server.command.executor;

import java.util.Iterator;
import com.lineage.server.templates.L1Skills;
import java.util.Collection;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.world.World;
import java.util.ArrayList;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Buff implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Buff.class);
    }
    
    private L1Buff() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Buff();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            Collection<L1PcInstance> players = null;
            String s = tok.nextToken();
            if (s.equals("me")) {
                players = new ArrayList();
                players.add(pc);
                s = tok.nextToken();
            }
            else if (s.equals("all")) {
                players = World.get().getAllPlayers();
                s = tok.nextToken();
            }
            else {
                players = (Collection)World.get().getVisiblePlayer(pc);
            }
            int skillId = Integer.parseInt(s);
            int time = 0;
            if (tok.hasMoreTokens()) {
                time = Integer.parseInt(tok.nextToken());
            }
            L1Skills skill = SkillsTable.get().getTemplate(skillId);
            if (skill.getTarget().equals("buff")) {
                for (L1PcInstance tg : players) {
                    new L1SkillUse().handleCommands(pc, skillId, tg.getId(), tg.getX(), tg.getY(), time, 2);
                }
            }
            else if (skill.getTarget().equals("none")) {
                for (L1PcInstance tg : players) {
                    new L1SkillUse().handleCommands(tg, skillId, tg.getId(), tg.getX(), tg.getY(), time, 4);
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("buff系のスキルではありません。"));
            }
        }
        catch (Exception e) {
            L1Buff._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}

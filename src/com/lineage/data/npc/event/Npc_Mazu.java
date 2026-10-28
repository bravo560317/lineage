package com.lineage.data.npc.event;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Mazu extends NpcExecutor
{
    private static final Log _log;
    private static final Map<Integer, String> _playList;
    
    static {
        _log = LogFactory.getLog(Npc_Mazu.class);
        _playList = new HashMap();
    }
    
    public static NpcExecutor get() {
        return new Npc_Mazu();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (!pc.is_mazu()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_e_g_01"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_e_g_02"));
            }
        }
        catch (Exception e) {
            Npc_Mazu._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            if (cmd.equalsIgnoreCase("0") && !pc.is_mazu()) {
                if (!pc.getInventory().checkItem(ConfigOther.monbossitem, (long)ConfigOther.monbossitemcount)) {
                    pc.sendPackets(new S_SystemMessage("金幣不足" + ConfigOther.monbossitemcount));
                    return;
                }
                else if (pc.getQuest().get_step(9955) == 1) {
                    pc.sendPackets(new S_SystemMessage("您今日媽祖已使用完畢，隔天再來找我吧。"));
                    return;
                }
                else {
                    pc.getInventory().consumeItem(ConfigOther.monbossitem, (long)ConfigOther.monbossitemcount);
                    pc.set_mazu(true);
                    pc.setSkillEffect(8591, ConfigOther.montime * 60 * 1000);
                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7321, 3600000));
                    pc.set_mazu_time((long)(ConfigOther.montime * 60 * 1000));
                    pc.getQuest().set_step(9955, 1);
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("目前正在使用媽祖狀態中"));
            }
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
        catch (Exception e) {
            Npc_Mazu._log.error(e.getLocalizedMessage(), e);
        }
    }
}

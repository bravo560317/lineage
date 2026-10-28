package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class NewOM_TeleportRune_Phantom extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(NewOM_TeleportRune_Phantom.class);
    }
    
    public static ItemExecutor get() {
        return new NewOM_TeleportRune_Phantom();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            if (pc.hasSkillEffect(4017)) {
                pc.sendPackets(new S_ServerMessage(1413));
                return;
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "telBook4"));
                if (!pc.isPhantomTeleport()) {
                    pc.setPhantomTeleport(true);
                }
            }
        }
        catch (Exception e) {
            NewOM_TeleportRune_Phantom._log.error(e.getLocalizedMessage(), e);
        }
    }
}

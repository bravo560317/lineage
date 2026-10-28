package com.lineage.data.item_etcitem.dragon;

import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.model.skill.L1SkillMode;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Baphomet_Etcitem extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Baphomet_Etcitem.class);
    }
    
    public static ItemExecutor get() {
        return new Baphomet_Etcitem();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            if (item == null) {
                return;
            }
            else if (pc == null) {
                return;
            }
            else if (pc.hasSkillEffect(8030)) {
                pc.sendPackets(new S_ServerMessage("無法與變身書狀態共存"));
                return;
            }
            else {
                int time = L1BuffUtil.cancelBaphomet(pc);
                if (time != -1) {
                    pc.sendPackets(new S_ServerMessage(1139, String.valueOf(time / 60)));
                    return;
                }
                else {
                    L1PolyMorph.doPoly(pc, 13450, 600, 1);
                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), 10165));
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    item.setLastUsed(ts);
                    pc.getInventory().updateItem(item, 32);
                    pc.getInventory().saveItem(item, 32);
                    SkillMode mode = L1SkillMode.get().getSkill(40001);
                    if (mode != null) {
                        mode.start(pc, null, null, 600);
                    }
                }
            }
        }
        catch (Exception e) {
            Baphomet_Etcitem._log.error(e.getLocalizedMessage(), e);
        }
    }
}

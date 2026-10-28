package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Transmission_Reel extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Transmission_Reel.class);
    }
    
    public static ItemExecutor get() {
        return new Transmission_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            int locx = 0;
            int locy = 0;
            short mapid = 0;
            String nameId = item.getName();
            if (nameId.equals("$2400")) {
                locx = 32631;
                locy = 32935;
                mapid = 111;
            }
            else if (nameId.equals("$2678")) {
                locx = 32669;
                locy = 32814;
                mapid = 151;
            }
            else if (nameId.equals("$2401")) {
                locx = 32631;
                locy = 32935;
                mapid = 121;
            }
            else if (nameId.equals("$2679")) {
                locx = 32669;
                locy = 32814;
                mapid = 161;
            }
            else if (nameId.equals("$2402")) {
                locx = 32631;
                locy = 32935;
                mapid = 131;
            }
            else if (nameId.equals("$2680")) {
                locx = 32669;
                locy = 32814;
                mapid = 171;
            }
            else if (nameId.equals("$2403")) {
                locx = 32631;
                locy = 32935;
                mapid = 141;
            }
            else if (nameId.equals("$2681")) {
                locx = 32669;
                locy = 32814;
                mapid = 181;
            }
            else if (nameId.equals("$2682")) {
                locx = 32669;
                locy = 32814;
                mapid = 191;
            }
            if (pc.getMap().isEscapable()) {
                L1Teleport.teleport(pc, locx, locy, mapid, 5, true);
                if (pc.isActived()) {
                    pc.setActived(false);
                    pc.killSkillEffectTimer(8853);
                    pc.sendPackets(new S_ServerMessage("掛機中請勿使用手動卷軸。"));
                    pc.sendPackets(new S_ServerMessage("自動狩獵已停止。"));
                    if (pc.getAutoX() > 0) {
                        pc.setAutoX(0);
                        pc.setAutoY(0);
                        pc.setAutoMap(0);
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(647));
                pc.sendPackets(new S_Paralysis(7, false));
            }
            return;
        }
    }
}

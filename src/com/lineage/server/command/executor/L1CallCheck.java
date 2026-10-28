package com.lineage.server.command.executor;

import java.util.Iterator;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public final class L1CallCheck implements L1CommandExecutor
{
    private static final Log _log;
    private final int[] HEADING_TABLE_X;
    private final int[] HEADING_TABLE_Y;
    
    static {
        _log = LogFactory.getLog(L1CallCheck.class);
    }
    
    private L1CallCheck() {
        this.HEADING_TABLE_X = new int[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        this.HEADING_TABLE_Y = new int[] { -1, -1, 0, 1, 1, 1, 0, -1 };
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1CallCheck();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            String name = tok.nextToken();
            L1PcInstance tgpc = World.get().getPlayer(name);
            if (tgpc != null) {
                L1Map map = pc.getMap();
                int locX = pc.getX();
                int locY = pc.getY();
                int heading = pc.getHeading();
                locX += this.HEADING_TABLE_X[heading];
                locY += this.HEADING_TABLE_Y[heading];
                heading = (heading + 4) % 4;
                short mapId = pc.getMapId();
                boolean isExsistCharacter = false;
                for (L1Object object : World.get().getVisibleObjects(pc, 1)) {
                    if (object instanceof L1Character) {
                        L1Character cha = (L1Character)object;
                        if (cha.getX() == locX && cha.getY() == locY && cha.getMapId() == mapId) {
                            isExsistCharacter = true;
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                }
                if ((locX == 0 && locY == 0) || !map.isPassable(locX, locY, null) || isExsistCharacter) {
                    pc.sendPackets(new S_ServerMessage("你的前方有障礙物或對象，請空出1格再試。"));
                    return;
                }
                else {
                    L1Teleport.teleport(tgpc, locX, locY, mapId, heading, true, 1);
                    this.execute(pc, tgpc);
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage("沒有此玩家或對方沒有在線。"));
            }
        }
        catch (Exception e) {
            L1CallCheck._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
        }
    }
    
    private void execute(L1PcInstance pc, L1PcInstance tgpc) {
        if (tgpc.getCurrentHp() > 50) {
            pc.sendPackets(new S_ServerMessage("[外掛自動加血測試]玩家(" + tgpc.getName() + ") " + "測試開始。"));
            this.execute(tgpc);
            pc.sendPackets(new S_ServerMessage("測試結束。"));
        }
        else {
            pc.sendPackets(new S_ServerMessage("[外掛自動加血測試]玩家(" + tgpc.getName() + ") " + "血量低於50 自動加滿血並繼續測試。"));
            tgpc.setCurrentHp(tgpc.getMaxHp());
            this.execute(tgpc);
            pc.sendPackets(new S_ServerMessage("測試結束。"));
        }
    }
    
    private void execute(L1PcInstance tgpc) {
        tgpc.setCurrentHp(50);
    }
}

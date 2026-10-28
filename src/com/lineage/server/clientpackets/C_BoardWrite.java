package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_BoardWrite extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_BoardWrite.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {}
            if (pc.isDead() || pc.isTeleport()) {
                return;
            }
            else {
                int objId = this.readD();
                L1NpcInstance npc = WorldNpc.get().map().get((Integer)objId);
                if (npc == null) {
                    return;
                }
                else {
                    String title = this.readS();
                    if (title.length() > 16) {
                        pc.sendPackets(new S_ServerMessage(166, "標題過長"));
                        return;
                    }
                    else {
                        String content = this.readS();
                        if (title.length() > 1000) {
                            pc.sendPackets(new S_ServerMessage(166, "內容過長"));
                            return;
                        }
                        else if (npc.ACTION != null) {
                            pc.set_board_title(title);
                            pc.set_board_content(content);
                            npc.ACTION.action(pc, npc, "w", 0L);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

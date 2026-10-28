package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Board extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Board.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int objId = this.readD();
            L1NpcInstance npc = WorldNpc.get().map().get((Integer)objId);
            if (npc == null) {
                return;
            }
            else {
                L1PcInstance pc = client.getActiveChar();
                if (pc == null) {
                    return;
                }
                else if (npc.ACTION != null) {
                    npc.ACTION.talk(pc, npc);
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

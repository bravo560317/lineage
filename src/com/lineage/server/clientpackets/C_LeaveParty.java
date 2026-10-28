package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_LeaveParty extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_LeaveParty.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance pc = client.getActiveChar();
            if (pc.getMapId() == 9000 || pc.getMapId() == 9101) {
                return;
            }
            else if (pc.isInParty()) {
                pc.getParty().leaveMember(pc);
            }
            else {
                pc.sendPackets(new S_ServerMessage(425));
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

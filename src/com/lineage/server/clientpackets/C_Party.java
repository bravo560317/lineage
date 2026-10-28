package com.lineage.server.clientpackets;

import com.lineage.server.model.L1Party;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_Party;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Party extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Party.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {
                return;
            }
            else {
                L1Party party = pc.getParty();
                if (pc.isInParty()) {
                    pc.sendPackets(new S_Party("party", pc.getId(), party.getLeader().getName(), party.getMembersNameList()));
                }
                else {
                    pc.sendPackets(new S_ServerMessage(425));
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

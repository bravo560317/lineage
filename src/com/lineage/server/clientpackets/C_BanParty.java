package com.lineage.server.clientpackets;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_BanParty extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_BanParty.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.getParty().isLeader(pc)) {
                pc.sendPackets(new S_ServerMessage(427));
            }
            if (pc.getMapId() == 9000 || pc.getMapId() == 9101) {
                return;
            }
            else {
                String userName = this.readS();
                ConcurrentHashMap pcs = pc.getParty().partyUsers();
                if (pcs.isEmpty()) {
                    return;
                }
                else if (pcs.size() <= 0) {
                    return;
                }
                else {
                    for (Object obj : pcs.values()) {
                    	L1PcInstance member = (L1PcInstance)obj;
                        String memberName = member.getName().toLowerCase();
                        if (memberName.equals(userName.toLowerCase())) {
                            pc.getParty().kickMember(member);
                            return;
                        }
                    }
                    pc.sendPackets(new S_ServerMessage(426, userName));
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

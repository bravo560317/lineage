package com.lineage.server.clientpackets;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.server.templates.L1BuddyTmp;
import com.lineage.server.datatables.lock.BuddyReading;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_AddBuddy extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_AddBuddy.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            String charName = this.readS().toLowerCase();
            L1PcInstance pc = client.getActiveChar();
            ArrayList<L1BuddyTmp> list = BuddyReading.get().userBuddy(pc.getId());
            if (list != null) {
                if (charName.equalsIgnoreCase(pc.getName())) {
                    return;
                }
                else {
                    for (L1BuddyTmp buddyTmp : list) {
                        if (charName.equalsIgnoreCase(buddyTmp.get_buddy_name())) {
                            return;
                        }
                    }
                }
            }
            int objid = CharObjidTable.get().charObjid(charName);
            if (objid != 0) {
                String name = CharObjidTable.get().isChar(objid);
                BuddyReading.get().addBuddy(pc.getId(), objid, name);
                return;
            }
            else {
                pc.sendPackets(new S_ServerMessage(109, charName));
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

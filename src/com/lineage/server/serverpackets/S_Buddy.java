package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.world.WorldDe;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1BuddyTmp;
import com.lineage.server.datatables.lock.BuddyReading;

public class S_Buddy extends ServerBasePacket
{
    private static final String _buddy = "buddy";
    private byte[] _byte;
    
    public S_Buddy(int objId) {
        this._byte = null;
        this.buildPacket(objId);
    }
    
    private void buildPacket(int objId) {
        this.writeC(39);
        this.writeD(objId);
        this.writeS("buddy");
        this.writeH(2);
        this.writeH(2);
        String result = new String("");
        String onlineBuddy = new String("");
        ArrayList<L1BuddyTmp> list = BuddyReading.get().userBuddy(objId);
        if (list != null) {
            for (L1BuddyTmp buddyTmp : BuddyReading.get().userBuddy(objId)) {
                String buddy_name = buddyTmp.get_buddy_name();
                result = result + buddy_name + " ";
                L1PcInstance find = World.get().getPlayer(buddy_name);
                if (find != null) {
                    onlineBuddy = onlineBuddy + find.getName() + " ";
                }
                L1DeInstance de = WorldDe.get().getDe(buddy_name);
                if (de != null) {
                    onlineBuddy = onlineBuddy + de.getNameId() + " ";
                }
            }
        }
        this.writeS(result);
        this.writeS(onlineBuddy);
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

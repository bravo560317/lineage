package com.lineage.data.item_etcitem;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import com.lineage.server.model.L1Party;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class AllCall_party extends ItemExecutor
{
    private AllCall_party() {
    }
    
    public static ItemExecutor get() {
        return new AllCall_party();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        L1Party party = pc.getParty();
        if (!pc.isInParty()) {
            pc.sendPackets(new S_ServerMessage("尚未有組隊無法使用此物品"));
            return;
        }
        else if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
            pc.sendPackets(new S_ServerMessage("旗子內禁止使用"));
            return;
        }
        else if (!pc.getMap().isPartyPc()) {
            pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
            return;
        }
        else {
            ConcurrentHashMap<Integer, L1PcInstance> pcs = party.partyUsers();
            if (pcs.isEmpty()) {
                return;
            }
            else if (pcs.size() <= 0) {
                return;
            }
            else if (!pc.isInParty()) {
                return;
            }
            else if (!pc.getInventory().checkItem(ConfigOther.Call_party_itemid, (long)ConfigOther.Call_party_count)) {
                pc.sendPacketsAll(new S_SystemMessage(ConfigOther.clanmsg5));
                return;
            }
            else {
                for (L1Object object : World.get().getVisibleObjects(pc, 14)) {
                    if (object instanceof L1PcInstance) {
                        L1PcInstance red = (L1PcInstance)object;
                        if (red.getcallclanal() != pc.getId() && !pc.getParty().isMember(red)) {
                            pc.sendPackets(new S_ServerMessage("偵測到周圍有非本隊伍玩家,無法使用。"));
                            return;
                        }
                        else {
                            continue;
                        }
                    }
                }
                pc.getInventory().consumeItem(ConfigOther.Call_party_itemid, (long)ConfigOther.Call_party_count);
                for (L1PcInstance pc2 : pcs.values()) {
                    if (pc.isInParty() && pc.getParty().isMember(pc2)) {
                        pc2.setcallclanal(pc.getId());
                        pc.sendPackets(new S_SkillSound(pc.getId(), 2047));
                        pc.setcallclanal(1);
                        if (pc2.getcallclanal() != 1) {
                            pc2.sendPackets(new S_Message_YN(4000));
                        }
                        else {
                            continue;
                        }
                    }
                }
                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(ConfigOther.partymsg, pc.getName(), MapsTable.get().getMapName((int)pc.getMapId()))));
                World.get().broadcastPacketToAll(new S_PacketBoxGree(2, String.format(ConfigOther.partymsg, pc.getName(), MapsTable.get().getMapName((int)pc.getMapId()))));
                return;
            }
        }
    }
}

package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.world.World;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class AllCall_clan extends ItemExecutor
{
    private AllCall_clan() {
    }
    
    public static ItemExecutor get() {
        return new AllCall_clan();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
            pc.sendPackets(new S_ServerMessage("旗子內禁止使用"));
            return;
        }
        else if (pc.getClanid() == 0) {
            pc.sendPackets(new S_ServerMessage("\\fY您尚未加入血盟無法使用"));
            return;
        }
        else if (ConfigOther.AllCall_clan_Crown && !pc.isCrown()) {
            pc.sendPackets(new S_ServerMessage("\\fY您的職業不是王族無法使用"));
            return;
        }
        else if (!pc.getMap().isClanPc()) {
            pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
            return;
        }
        else {
            if (pc.getInventory().checkItem(ConfigOther.Call_clan_itemid, (long)ConfigOther.Call_clan_count)) {
                pc.getInventory().consumeItem(ConfigOther.Call_clan_itemid, (long)ConfigOther.Call_clan_count);
                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(ConfigOther.clanmsg, pc.getName(), MapsTable.get().getMapName((int)pc.getMapId()))));
                World.get().broadcastPacketToAll(new S_PacketBoxGree(2, String.format(ConfigOther.clanmsg, pc.getName(), MapsTable.get().getMapName((int)pc.getMapId()))));
                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                L1PcInstance[] array;
                for (int length = (array = clanMembers).length, i = 0; i < length; ++i) {
                    L1PcInstance clanMember1 = array[i];
                    if (clanMember1.getId() != pc.getId()) {
                        clanMember1.setcallclanal(pc.getId());
                        clanMember1.sendPackets(new S_Message_YN(729));
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(ConfigOther.clanmsg1));
            }
            return;
        }
    }
}

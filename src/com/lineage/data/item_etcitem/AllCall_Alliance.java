package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1Alliance;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.world.WorldClan;
import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.lock.ClanAllianceReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class AllCall_Alliance extends ItemExecutor
{
    private AllCall_Alliance() {
    }
    
    public static ItemExecutor get() {
        return new AllCall_Alliance();
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
        else {
            L1Alliance alliance = ClanAllianceReading.get().getAlliance(pc.getClan().getClanId());
            if (alliance == null) {
                pc.sendPackets(new S_ServerMessage("尚未加入同盟 無法使用此道具"));
                return;
            }
            else if (!pc.getMap().isClanPc()) {
                pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                return;
            }
            else {
                if (pc.getInventory().checkItem(ConfigOther.Call_clan_itemid2, (long)ConfigOther.Call_clan_count2)) {
                    pc.getInventory().consumeItem(ConfigOther.Call_clan_itemid2, (long)ConfigOther.Call_clan_count2);
                    L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                    Integer[] allianceids = clan.Alliance();
                    if (allianceids.length > 0) {
                        String TargetClanName = null;
                        L1Clan TargegClan = null;
                        for (int j = 0; j < allianceids.length; ++j) {
                            TargegClan = clan.getAlliance(allianceids[j]);
                            if (TargegClan != null) {
                                TargetClanName = TargegClan.getClanName();
                                if (TargetClanName != null) {
                                    L1PcInstance[] onlineClanMember;
                                    for (int length = (onlineClanMember = TargegClan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                                        L1PcInstance alliancelistner = onlineClanMember[i];
                                        alliancelistner.setcallclana2(pc.getId());
                                        alliancelistner.sendPackets(new S_Message_YN(4001));
                                        World.get().broadcastPacketToAll(new S_SystemMessage(String.format(ConfigOther.clanmsg13, pc.getName(), MapsTable.get().getMapName((int)pc.getMapId()))));
                                        World.get().broadcastPacketToAll(new S_PacketBoxGree(2, String.format(ConfigOther.clanmsg13, pc.getName(), MapsTable.get().getMapName((int)pc.getMapId()))));
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(ConfigOther.clanmsg15));
                }
                return;
            }
        }
    }
}

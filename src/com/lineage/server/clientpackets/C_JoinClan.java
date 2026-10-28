package com.lineage.server.clientpackets;

import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.FaceToFace;
import com.lineage.echo.ClientExecutor;

public class C_JoinClan extends ClientBasePacket
{
    private static final String C_JOIN_CLAN = "[C] C_JoinClan";
    
    @Override
    public void start(byte[] abyte0, ClientExecutor client) throws Exception {
        L1PcInstance pc = client.getActiveChar();
        if (pc == null || pc.isGhost()) {
            return;
        }
        else {
            L1PcInstance target = FaceToFace.faceToFace(pc);
            if (target != null) {
                this.JoinClan(pc, target);
            }
            return;
        }
    }
    
    private void JoinClan(L1PcInstance player, L1PcInstance target) {
        if (!target.isCrown() && target.getClanRank() != 9 && target.getClanRank() != 6) {
            player.sendPackets(new S_ServerMessage(92, target.getName()));
            return;
        }
        else if (player.isCrown() && (target.getClanRank() == 9 || target.getClanRank() == 6)) {
            return;
        }
        else if (player.isCrown() && target.getClanRank() == 3) {
            player.sendPackets(new S_ServerMessage(2504));
            return;
        }
        else if (player.getClanid() == target.getClanid()) {
            player.sendPackets(new S_ServerMessage("已經是加入血盟狀態。"));
            return;
        }
        else {
            int clan_id = target.getClanid();
            String clan_name = target.getClanname();
            if (clan_id == 0) {
                player.sendPackets(new S_ServerMessage(90, target.getName()));
                return;
            }
            else {
                L1Clan clan = WorldClan.get().getClan(clan_name);
                if (clan == null) {
                    return;
                }
                else if (target.getClanRank() != 10 && target.getClanRank() != 9 && target.getClanRank() != 6 && target.getClanRank() != 4 && target.getClanRank() != 3) {
                    player.sendPackets(new S_ServerMessage(92, target.getName()));
                    return;
                }
                else {
                    if (player.getClanid() != 0) {
                        if (player.isCrown()) {
                            String player_clan_name = player.getClanname();
                            L1Clan player_clan = WorldClan.get().getClan(player_clan_name);
                            if (player_clan == null) {
                                return;
                            }
                            else if (player.getId() != player_clan.getLeaderId()) {
                                player.sendPackets(new S_ServerMessage(89));
                                return;
                            }
                            else if (player_clan.getCastleId() != 0 || player_clan.getHouseId() != 0) {
                                player.sendPackets(new S_ServerMessage(665));
                                return;
                            }
                        }
                        else {
                            player.sendPackets(new S_ServerMessage(89));
                            return;
                        }
                    }
                    if (player.getRejoinClanTime() != null && player.getRejoinClanTime().getTime() > System.currentTimeMillis()) {
                        int time = (int)(player.getRejoinClanTime().getTime() - System.currentTimeMillis()) / 3600000;
                        player.sendPackets(new S_ServerMessage(1925, new StringBuilder(String.valueOf(time)).toString()));
                        return;
                    }
                    else {
                        target.setTempID(player.getId());
                        target.sendPackets(new S_Message_YN(97, player.getName()));
                        return;
                    }
                }
            }
        }
    }
    
    @Override
    public String getType() {
        return "[C] C_JoinClan";
    }
}

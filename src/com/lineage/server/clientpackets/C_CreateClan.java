package com.lineage.server.clientpackets;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.CharObjidTable;
import com.add.L1PcUnlock;
import com.lineage.server.datatables.ClanMembersTable;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;

public class C_CreateClan extends ClientBasePacket
{
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        String s = this.readS();
                        int i = s.length();
                        if (i > 16) {
                            pc.sendPackets(new S_ServerMessage(98));
                            return;
                        }
                        else if (!C_CreateChar.isInvalidName(s)) {
                            pc.sendPackets(new S_ServerMessage(53));
                            return;
                        }
                        else if (pc.isCrown()) {
                            if (pc.getClanid() == 0) {
                                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                                for (L1Clan clan : allClans) {
                                    if (clan.getClanName().toLowerCase().equals(s.toLowerCase())) {
                                        pc.sendPackets(new S_ServerMessage(99));
                                        return;
                                    }
                                }
                                if (pc.getInventory().checkItem(40308, 30000L)) {
                                    L1Clan clan2 = ClanReading.get().createClan(pc, s);
                                    ClanMembersTable.getInstance().newMember(pc);
                                    if (clan2 != null) {
                                        pc.getInventory().consumeItem(40308, 30000L);
                                        pc.sendPackets(new S_ServerMessage(84, s));
                                        L1PcUnlock.Pc_Unlock(pc);
                                        CharObjidTable.get().addClan(clan2.getClanId(), clan2.getClanName());
                                        return;
                                    }
                                    else {
                                        return;
                                    }
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage(189));
                                    return;
                                }
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage(86));
                                return;
                            }
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(85));
                            return;
                        }
                    }
                }
            }
            return;
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

package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.add.L1PcUnlock;
import com.lineage.server.datatables.ClanMembersTable;
import com.lineage.config.ConfigClan;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldClan;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_BanClan extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_BanClan.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            String s = this.readS();
            L1PcInstance pc = client.getActiveChar();
            if (pc == null) {
                return;
            }
            else {
                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                if (clan != null) {
                    String[] clanMemberName = clan.getAllMembers();
                    if (pc.isCrown() && pc.getId() == clan.getLeaderId()) {
                        int i = 0;
                        while (i < clanMemberName.length) {
                            if (pc.getName().equalsIgnoreCase(s)) {
                                return;
                            }
                            else {
                                ++i;
                            }
                        }
                        L1PcInstance tempPc = World.get().getPlayer(s);
                        if (tempPc != null) {
                            try {
                                if (tempPc.getClanid() == pc.getClanid()) {
                                    L1ItemInstance item = tempPc.getInventory().findItemId(92164);
                                    if (item != null) {
                                        tempPc.getInventory().deleteItem(item);
                                    }
                                    tempPc.setClanid(0);
                                    tempPc.setClanname("");
                                    tempPc.setClanRank(0);
                                    tempPc.setClanMemberId(0);
                                    tempPc.sendPacketsAll(new S_CharTitle(tempPc.getId(), ""));
                                    tempPc.sendPacketsAll(new S_CharReset(tempPc.getId(), 0));
                                    if (ConfigClan.clandelt) {
                                        tempPc.setPcContribution(0);
                                    }
                                    tempPc.setClanContribution(0);
                                    tempPc.setclanadena(0);
                                    tempPc.setClanNameContribution("");
                                    tempPc.save();
                                    clan.delMemberName(tempPc.getName());
                                    ClanMembersTable.getInstance().deleteMember(tempPc.getId());
                                    L1PcUnlock.Pc_Unlock(tempPc);
                                    tempPc.sendPackets(new S_ServerMessage(238, pc.getClanname()));
                                    pc.sendPackets(new S_ServerMessage(240, tempPc.getName()));
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage(109, s));
                                }
                            }
                            catch (Exception e) {
                                C_BanClan._log.error(e.getLocalizedMessage(), e);
                            }
                        }
                        else {
                            try {
                                L1PcInstance restorePc = CharacterTable.get().restoreCharacter(s);
                                if (restorePc != null && restorePc.getClanid() == pc.getClanid()) {
                                    L1ItemInstance item2 = restorePc.getInventory().findItemId(92164);
                                    if (item2 != null) {
                                        restorePc.getInventory().deleteItem(item2);
                                    }
                                    restorePc.setClanid(0);
                                    restorePc.setClanname("");
                                    restorePc.setClanRank(0);
                                    restorePc.setClanMemberId(0);
                                    restorePc.setTitle("");
                                    restorePc.save();
                                    clan.delMemberName(restorePc.getName());
                                    ClanMembersTable.getInstance().deleteMember(restorePc.getId());
                                    pc.sendPackets(new S_ServerMessage(240, restorePc.getName()));
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage(109, s));
                                }
                            }
                            catch (Exception e) {
                                C_BanClan._log.error(e.getLocalizedMessage(), e);
                            }
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(518));
                    }
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

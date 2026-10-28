package com.lineage.server.clientpackets;

import java.util.logging.Level;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.config.ConfigOther;
import com.lineage.server.world.WorldClan;
import com.lineage.list.BadNamesList;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import java.util.logging.Logger;

public class C_Title extends ClientBasePacket
{
    private static final String C_TITLE = "[C] C_Title";
    private static Logger _log;
    
    static {
        C_Title._log = Logger.getLogger(C_Title.class.getName());
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        this.read(decrypt);
        L1PcInstance pc = client.getActiveChar();
        if (pc == null) {
            return;
        }
        else {
            String charName = this.readS();
            String title = this.readS();
            if (charName.isEmpty() || title.isEmpty()) {
                pc.sendPackets(new S_ServerMessage(196));
                return;
            }
            else {
                L1PcInstance target = World.get().getPlayer(charName);
                if (target == null) {
                    return;
                }
                else if (pc.isGm()) {
                    this.changeTitle(target, title);
                    return;
                }
                else {
                    if (this.isClanLeader(pc)) {
                        if (pc.getId() == target.getId()) {
                            if (pc.getLevel() < 10) {
                                pc.sendPackets(new S_ServerMessage(197));
                                return;
                            }
                            else {
                                this.changeTitle(pc, title);
                            }
                        }
                        else if (pc.getClanid() != target.getClanid()) {
                            pc.sendPackets(new S_ServerMessage(201));
                            return;
                        }
                        else if (target.getLevel() < 10) {
                            pc.sendPackets(new S_ServerMessage(202, charName));
                            return;
                        }
                        else if (BadNamesList.get().isBadName(title)) {
                            pc.sendPackets(new S_ServerMessage("此稱號無效名稱"));
                            return;
                        }
                        else {
                            this.changeTitle(target, title);
                            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                            if (clan != null) {
                                L1PcInstance[] onlineClanMember;
                                for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                                    L1PcInstance clanPc = onlineClanMember[i];
                                    clanPc.sendPackets(new S_ServerMessage(203, pc.getName(), charName, title));
                                }
                            }
                        }
                    }
                    else if (pc.getId() == target.getId()) {
                        if (pc.getClanid() != 0 && !ConfigOther.CLANTITLE) {
                            pc.sendPackets(new S_ServerMessage(198));
                            return;
                        }
                        else if (target.getLevel() < 40) {
                            pc.sendPackets(new S_ServerMessage(200));
                            return;
                        }
                        else if (BadNamesList.get().isBadName(title)) {
                            pc.sendPackets(new S_ServerMessage("此稱號無效名稱"));
                            return;
                        }
                        else {
                            this.changeTitle(pc, title);
                        }
                    }
                    else if (pc.isCrown()) {
                        if (pc.getClanid() == target.getClanid()) {
                            if (target.getClanRank() == 10 || target.getClanRank() == 4 || target.getClanRank() == 3) {
                                pc.sendPackets(new S_ServerMessage(2065));
                                return;
                            }
                            else {
                                this.changeTitle(target, title);
                                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                if (clan != null) {
                                    L1PcInstance[] onlineClanMember2;
                                    for (int length2 = (onlineClanMember2 = clan.getOnlineClanMember()).length, j = 0; j < length2; ++j) {
                                        L1PcInstance clanPc = onlineClanMember2[j];
                                        clanPc.sendPackets(new S_ServerMessage(203, pc.getName(), charName, title));
                                    }
                                }
                            }
                        }
                    }
                    else if (pc.getClanid() == target.getClanid()) {
                        if (pc.getClanRank() == 9 || pc.getClanRank() == 6) {
                            pc.sendPackets(new S_ServerMessage(2474));
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(2143));
                        }
                        return;
                    }
                    return;
                }
            }
        }
    }
    
    private void changeTitle(L1PcInstance pc, String title) {
        int objectId = pc.getId();
        pc.setTitle(title);
        pc.sendPackets(new S_CharTitle(objectId, title));
        pc.broadcastPacketAll(new S_CharTitle(objectId, title));
        try {
            pc.save();
        }
        catch (Exception e) {
            C_Title._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }
    
    private boolean isClanLeader(L1PcInstance pc) {
        boolean isClanLeader = false;
        if (pc.getClanid() != 0) {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null && pc.isCrown() && pc.getId() == clan.getLeaderId()) {
                isClanLeader = true;
            }
        }
        return isClanLeader;
    }
    
    @Override
    public String getType() {
        return "[C] C_Title";
    }
}

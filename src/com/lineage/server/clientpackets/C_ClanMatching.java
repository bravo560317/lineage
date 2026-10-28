package com.lineage.server.clientpackets;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.model.L1ClanJoin;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_ClanMatching;
import com.lineage.server.model.L1ClanMatching;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ClanMatching extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ClanMatching.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        int type = this.readC();
                        int objid = 0;
                        String text = null;
                        int htype = 0;
                        if (type == 0) {
                            L1ClanMatching cml = L1ClanMatching.getInstance();
                            htype = this.readC();
                            text = this.readS();
                            if (!cml.isClanMatchingList(pc.getClanname())) {
                                cml.writeClanMatching(pc.getClanname(), text, htype);
                            }
                            else {
                                cml.updateClanMatching(pc.getClanname(), text, htype);
                            }
                            pc.sendPackets(new S_ClanMatching(true, pc.getClanname()));
                            pc.sendPackets(new S_ServerMessage(3261));
                            return;
                        }
                        else if (type == 1) {
                            L1ClanMatching cml = L1ClanMatching.getInstance();
                            if (cml.isClanMatchingList(pc.getClanname())) {
                                cml.deleteClanMatching(pc);
                                cml.deleteClanMatchingApcList(pc.getClanname());
                            }
                            pc.sendPackets(new S_ClanMatching(false, pc.getClanname()));
                            pc.sendPackets(new S_ServerMessage(3262));
                            return;
                        }
                        else if (type >= 2 && type <= 4) {
                            L1ClanMatching cml = L1ClanMatching.getInstance();
                            if (pc.getClanid() == 0) {
                                if (!pc.isCrown()) {
                                    cml.loadClanMatchingApcList_User(pc);
                                }
                            }
                            else {
                                switch (pc.getClanRank()) {
                                    case 3:
                                    case 4:
                                    case 6:
                                    case 9:
                                    case 10: {
                                        cml.loadClanMatchingApcList_Crown(pc);
                                        break;
                                    }
                                }
                            }
                            pc.sendPackets(new S_ClanMatching(pc, type, objid, htype));
                            return;
                        }
                        else if (type == 5) {
                            objid = this.readD();
                            L1Clan clan = WorldClan.get().getClan(objid);
                            if (clan != null && !pc.getCMAList().contains(clan.getClanName())) {
                                L1ClanMatching cml2 = L1ClanMatching.getInstance();
                                cml2.writeClanMatchingApcList_User(pc, clan);
                            }
                            pc.sendPackets(new S_ClanMatching(pc, type, objid, htype));
                            return;
                        }
                        else if (type == 6) {
                            objid = this.readD();
                            htype = this.readC();
                            L1ClanMatching cml = L1ClanMatching.getInstance();
                            if (htype == 1) {
                                L1Object target = World.get().findObject(objid);
                                if (target != null && target instanceof L1PcInstance) {
                                    L1PcInstance joinpc = (L1PcInstance)target;
                                    if (!pc.getInviteList().contains(joinpc.getName())) {
                                        pc.sendPackets(new S_SystemMessage("此玩家已取消加入請求。"));
                                    }
                                    else if (L1ClanJoin.getInstance().ClanJoin(pc, joinpc)) {
                                        cml.deleteClanMatchingApcList(joinpc);
                                    }
                                }
                                else if (target == null) {
                                    pc.sendPackets(new S_SystemMessage("此玩家已離線。"));
                                }
                            }
                            else if (htype == 2) {
                                L1Object target = World.get().findObject(objid);
                                if (target != null) {
                                    if (target instanceof L1PcInstance) {
                                        L1PcInstance user = (L1PcInstance)target;
                                        cml.deleteClanMatchingApcList(user, user.getId(), pc.getClan());
                                        user.sendPackets(new S_ServerMessage(3267));
                                    }
                                }
                                else {
                                    String charname = CharacterTable.get().getCharName(objid);
                                    cml.deleteClanMatchingApcList(pc.getClan(), charname);
                                }
                            }
                            else if (htype == 3) {
                                L1Clan clan2 = WorldClan.get().getClan(objid);
                                if (clan2 != null && pc.getCMAList().contains(clan2.getClanName())) {
                                    cml.deleteClanMatchingApcList(pc, clan2);
                                }
                            }
                            pc.sendPackets(new S_ClanMatching(pc, type, objid, htype));
                            return;
                        }
                        else {
                            return;
                        }
                    }
                }
            }
            return;
        }
        catch (Exception e) {
            C_ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

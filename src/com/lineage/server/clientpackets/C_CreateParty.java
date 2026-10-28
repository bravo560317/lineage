package com.lineage.server.clientpackets;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Party;
import java.util.ArrayList;
import java.util.HashMap;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_CreateParty extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_CreateParty.class);
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
                        switch (type) {
                            case 0:
                            case 1: {
                                int targetId = this.readD();
                                L1Object temp = World.get().findObject(targetId);
                                if (temp instanceof L1PcInstance) {
                                    L1PcInstance targetPc = (L1PcInstance)temp;
                                    if (pc.getId() == targetPc.getId()) {
                                        return;
                                    }
                                    else if (!pc.getLocation().isInScreen(targetPc.getLocation()) || pc.getLocation().getTileLineDistance(targetPc.getLocation()) > 7) {
                                        pc.sendPackets(new S_ServerMessage(952));
                                        return;
                                    }
                                    else if (targetPc.isInParty()) {
                                        pc.sendPackets(new S_ServerMessage(415));
                                        return;
                                    }
                                    else if (pc.isInParty()) {
                                        if (pc.getParty().isLeader(pc)) {
                                            targetPc.setPartyID(pc.getId());
                                            targetPc.sendPackets(new S_Message_YN(953, pc.getName()));
                                            break;
                                        }
                                        else {
                                            pc.sendPackets(new S_ServerMessage(416));
                                            break;
                                        }
                                    }
                                    else {
                                        targetPc.setPartyID(pc.getId());
                                        targetPc.sendPackets(new S_Message_YN(953, pc.getName()));
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                            case 2: {
                                String name = this.readS();
                                L1PcInstance targetPc2 = World.get().getPlayer(name);
                                L1DeInstance de = null;
                                if (targetPc2 == null) {
                                    de = C_ChatWhisper.getDe(name);
                                }
                                if (targetPc2 == null && de == null) {
                                    pc.sendPackets(new S_ServerMessage(109));
                                    return;
                                }
                                else if (targetPc2 == null) {
                                    pc.sendPackets(new S_ServerMessage(109));
                                    return;
                                }
                                else if (pc.getId() == targetPc2.getId()) {
                                    return;
                                }
                                else if (!pc.getLocation().isInScreen(targetPc2.getLocation()) || pc.getLocation().getTileLineDistance(targetPc2.getLocation()) > 7) {
                                    pc.sendPackets(new S_ServerMessage(952));
                                    return;
                                }
                                else if (targetPc2.isInChatParty()) {
                                    pc.sendPackets(new S_ServerMessage(415));
                                    return;
                                }
                                else if (pc.isInChatParty()) {
                                    if (pc.getChatParty().isLeader(pc)) {
                                        targetPc2.setPartyID(pc.getId());
                                        targetPc2.sendPackets(new S_Message_YN(951, pc.getName()));
                                        break;
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage(416));
                                        break;
                                    }
                                }
                                else {
                                    targetPc2.setPartyID(pc.getId());
                                    targetPc2.sendPackets(new S_Message_YN(951, pc.getName()));
                                    break;
                                }
                            }
                            case 3: {
                                if (pc.isInParty()) {
                                    if (pc.getParty().isLeader(pc)) {
                                        int objid = this.readD();
                                        L1Object object = World.get().findObject(objid);
                                        if (object instanceof L1PcInstance) {
                                            L1PcInstance tgpc = (L1PcInstance)object;
                                            if (tgpc.getMapId() != pc.getMapId()) {
                                                pc.sendPackets(new S_Message_YN(1695));
                                            }
                                            if (pc.getLocation().isInScreen(tgpc.getLocation())) {
                                                HashMap<Integer, L1PcInstance> map = new HashMap();
                                                map.putAll(pc.getParty().partyUsers());
                                                ArrayList<L1PcInstance> newList = new ArrayList();
                                                for (L1PcInstance newpc : map.values()) {
                                                    if (!newpc.equals(tgpc)) {
                                                        newList.add(newpc);
                                                    }
                                                }
                                                map.clear();
                                                pc.getParty().breakup();
                                                L1Party party = new L1Party();
                                                party.addMember(tgpc);
                                                for (L1PcInstance newpc2 : newList) {
                                                    party.addMember(newpc2);
                                                    tgpc.sendPackets(new S_ServerMessage(424, newpc2.getName()));
                                                }
                                                party.msgToAll();
                                                newList.clear();
                                                break;
                                            }
                                            else {
                                                pc.sendPackets(new S_ServerMessage(1695));
                                                break;
                                            }
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage(1697));
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        return;
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

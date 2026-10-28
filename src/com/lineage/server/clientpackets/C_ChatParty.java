package com.lineage.server.clientpackets;

import com.lineage.server.model.L1ChatParty;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Party;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ChatParty extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ChatParty.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {}
            if (pc.isTeleport()) {
                return;
            }
            else {
                int type = this.readC();
                switch (type) {
                    case 0: {
                        String name = this.readS();
                        if (!pc.isInChatParty()) {
                            pc.sendPackets(new S_ServerMessage(425));
                            return;
                        }
                        else if (!pc.getChatParty().isLeader(pc)) {
                            pc.sendPackets(new S_ServerMessage(427));
                            return;
                        }
                        else {
                            L1PcInstance targetPc = World.get().getPlayer(name);
                            L1DeInstance de = null;
                            if (targetPc == null) {
                                de = C_ChatWhisper.getDe(name);
                            }
                            if (targetPc == null && de == null) {
                                pc.sendPackets(new S_ServerMessage(109, name));
                                return;
                            }
                            else if (pc.getId() == targetPc.getId()) {
                                return;
                            }
                            else {
                                L1PcInstance[] members;
                                int length = (members = pc.getChatParty().getMembers()).length;
                                int i = 0;
                                while (i < length) {
                                    L1PcInstance member = members[i];
                                    if (member.getName().toLowerCase().equals(name.toLowerCase())) {
                                        pc.getChatParty().kickMember(member);
                                        return;
                                    }
                                    else {
                                        ++i;
                                    }
                                }
                                pc.sendPackets(new S_ServerMessage(426, name));
                                break;
                            }
                        }
                    }
                    case 1: {
                        if (pc.isInChatParty()) {
                            pc.getChatParty().leaveMember(pc);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 2: {
                        L1ChatParty chatParty = pc.getChatParty();
                        if (pc.isInChatParty()) {
                            pc.sendPackets(new S_Party("party", pc.getId(), chatParty.getLeader().getName(), chatParty.getMembersNameList()));
                            break;
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(425));
                            break;
                        }
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

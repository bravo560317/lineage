package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.logging.Logger;

public class S_NpcChatPacket extends ServerBasePacket
{
    private static final String S_NPC_CHAT_PACKET = "[S] S_NpcChatPacket";
    private static Logger _log;
    private byte[] _byte;
    
    static {
        S_NpcChatPacket._log = Logger.getLogger(S_NpcChatPacket.class.getName());
    }
    
    public S_NpcChatPacket(L1NpcInstance npc, String chat, int type) {
        this._byte = null;
        this.buildPacket(npc, chat, type);
    }
    
    public S_NpcChatPacket(L1PcInstance pc, String string, int type) {
        this._byte = null;
        this.buildPacket(pc, string, type);
    }
    
    public S_NpcChatPacket(L1NpcInstance npc, String chat) {
        this._byte = null;
        this.buildPacket(npc, chat, 0);
    }
    
    public S_NpcChatPacket(L1PcInstance pc, String string) {
        this._byte = null;
        this.buildPacket(pc, string, 0);
    }
    
    private void buildPacket(L1NpcInstance npc, String chat, int type) {
        switch (type) {
            case 0: {
                this.writeC(161);
                this.writeC(type);
                this.writeD(npc.getId());
                this.writeS(npc.getName() + ": " + chat);
                break;
            }
            case 2: {
                this.writeC(161);
                this.writeC(type);
                this.writeD(npc.getId());
                this.writeS("<" + npc.getName() + "> " + chat);
                break;
            }
            case 3: {
                this.writeC(161);
                this.writeC(type);
                this.writeD(npc.getId());
                this.writeS("[" + npc.getName() + "] " + chat);
                break;
            }
        }
    }
    
    public void buildPacket(L1PcInstance pc, String chat, int type) {
        this.writeC(161);
        this.writeC(type);
        this.writeD(pc.getId());
        this.writeS(chat);
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return "[S] S_NpcChatPacket";
    }
}

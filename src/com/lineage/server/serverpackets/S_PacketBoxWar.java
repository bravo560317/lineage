package com.lineage.server.serverpackets;

import java.util.HashMap;
import com.lineage.server.world.WorldClan;

public class S_PacketBoxWar extends ServerBasePacket
{
    public static final int MSG_WAR_BEGIN = 0;
    public static final int MSG_WAR_END = 1;
    public static final int MSG_WAR_GOING = 2;
    public static final int MSG_WAR_INITIATIVE = 3;
    public static final int MSG_WAR_OCCUPY = 4;
    public static final int MSG_WIN_LASTAVARD = 30;
    public static final int MSG_WAR_OVER = 79;
    private byte[] _byte;
    
    public S_PacketBoxWar() {
        this._byte = null;
        HashMap<Integer, String> map = WorldClan.get().castleClanMap();
        int count = 7;
        this.writeC(250);
        this.writeC(79);
        this.writeC(count);
        for (int key = 1; key < count; ++key) {
            String clanName = map.get((Integer)key);
            if (clanName != null) {
                this.writeS(clanName);
            }
            else {
                this.writeS(" ");
            }
        }
        map.clear();
    }
    
    public S_PacketBoxWar(int subCode) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
    }
    
    public S_PacketBoxWar(int subCode, int value) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        this.writeC(value);
        this.writeH(0);
    }
    
    public S_PacketBoxWar(int subCode, int id, String name, String clanName) {
        this._byte = null;
        this.writeC(250);
        this.writeC(30);
        this.writeD(id);
        this.writeS(name);
        this.writeS(clanName);
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
        return this.getClass().getSimpleName();
    }
}

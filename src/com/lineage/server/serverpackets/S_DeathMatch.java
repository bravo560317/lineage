package com.lineage.server.serverpackets;

public class S_DeathMatch extends ServerBasePacket
{
    private static final String S_DEATHMACTH = "[S] S_DeathMatch";
    private byte[] _byte;
    public static final int CountDown = 71;
    public static final int CountDownOff = 72;
    
    public S_DeathMatch(int type) {
        this._byte = null;
        this.writeC(250);
        this.writeC(type);
        if (type == 71) {
            this.writeH(600);
        }
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
        return "[S] S_DeathMatch";
    }
}

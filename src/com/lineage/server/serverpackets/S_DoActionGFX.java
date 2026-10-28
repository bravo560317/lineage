package com.lineage.server.serverpackets;

public class S_DoActionGFX extends ServerBasePacket
{
    public static int ACTION_MAGIC;
    private byte[] _byte;
    
    static {
        S_DoActionGFX.ACTION_MAGIC = 22;
    }
    
    public S_DoActionGFX(int objectId, int actionId) {
        this._byte = null;
        this.writeC(158);
        this.writeD(objectId);
        this.writeC(actionId);
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

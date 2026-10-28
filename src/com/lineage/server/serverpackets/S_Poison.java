package com.lineage.server.serverpackets;

public class S_Poison extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Poison(int objId, int type) {
        this._byte = null;
        this.writeC(165);
        this.writeD(objId);
        switch (type) {
            case 0: {
                this.writeC(0);
                this.writeC(0);
                break;
            }
            case 1: {
                this.writeC(1);
                this.writeC(0);
                break;
            }
            case 2: {
                this.writeC(0);
                this.writeC(1);
                break;
            }
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
        return this.getClass().getSimpleName();
    }
}

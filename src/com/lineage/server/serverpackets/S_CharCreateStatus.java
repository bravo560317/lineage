package com.lineage.server.serverpackets;

public class S_CharCreateStatus extends ServerBasePacket
{
    private byte[] _byte;
    public static final int REASON_OK = 2;
    public static final int REASON_ALREADY_EXSISTS = 6;
    public static final int REASON_INVALID_NAME = 9;
    public static final int REASON_CLASS_NOT_AVAILABLE = 20;
    public static final int REASON_WRONG_AMOUNT = 21;
    
    public S_CharCreateStatus(int reason) {
        this._byte = null;
        this.writeC(98);
        this.writeC(reason);
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

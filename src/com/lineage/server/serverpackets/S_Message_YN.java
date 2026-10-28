package com.lineage.server.serverpackets;

import java.util.concurrent.atomic.AtomicInteger;

public class S_Message_YN extends ServerBasePacket
{
    private byte[] _byte;
    private static AtomicInteger _sequentialNumber;
    
    static {
        S_Message_YN._sequentialNumber = new AtomicInteger(1);
    }
    
    public S_Message_YN(int type) {
        this._byte = null;
        this.writeC(219);
        this.writeH(0);
        this.writeD(0);
        this.writeH(type);
    }
    
    public S_Message_YN(String name) {
        this._byte = null;
        this.writeC(219);
        this.writeH(0);
        this.writeD(S_Message_YN._sequentialNumber.incrementAndGet());
        this.writeH(252);
        this.writeS(name);
    }
    
    public S_Message_YN(int type, String msg) {
        this._byte = null;
        this.writeC(219);
        this.writeH(0);
        this.writeD(0);
        this.writeH(type);
        this.writeS(msg);
    }
    
    public S_Message_YN(int type, String msg1, String msg2) {
        this._byte = null;
        this.writeC(219);
        this.writeH(0);
        this.writeD(0);
        this.writeH(type);
        this.writeS(msg1);
        this.writeS(msg2);
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

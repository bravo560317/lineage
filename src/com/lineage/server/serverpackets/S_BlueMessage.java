package com.lineage.server.serverpackets;

public class S_BlueMessage extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_BlueMessage(int type) {
        this._byte = null;
        this.buildPacket(type, (String[])null);
    }
    
    public S_BlueMessage(int type, String msg1) {
        this._byte = null;
        this.buildPacket(type, new String[] { msg1 });
    }
    
    public S_BlueMessage(int type, String msg1, String msg2) {
        this._byte = null;
        this.buildPacket(type, new String[] { msg1, msg2 });
    }
    
    public S_BlueMessage(int type, String[] info) {
        this._byte = null;
        this.buildPacket(type, info);
    }
    
    private void buildPacket(int type, String[] info) {
        this.writeC(105);
        this.writeH(type);
        if (info == null) {
            this.writeC(0);
        }
        else {
            this.writeC(info.length);
            for (int i = 0; i < info.length; ++i) {
                this.writeS(info[i]);
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

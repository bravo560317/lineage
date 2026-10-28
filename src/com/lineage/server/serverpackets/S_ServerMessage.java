package com.lineage.server.serverpackets;

public class S_ServerMessage extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ServerMessage(String msg) {
        this._byte = null;
        this.writeC(81);
        this.writeC(0);
        this.writeD(0);
        this.writeS(msg);
    }
    
    public S_ServerMessage(String name, int color) {
        this._byte = null;
        this.writeC(243);
        this.writeC(color);
        this.writeS(name);
    }
    
    public S_ServerMessage(int type) {
        this._byte = null;
        this.writeC(71);
        this.writeH(type);
        this.writeC(0);
    }
    
    public S_ServerMessage(int type, String msg1) {
        this._byte = null;
        this.buildPacket(type, new String[] { msg1 });
    }
    
    public S_ServerMessage(int type, String msg1, String msg2) {
        this._byte = null;
        this.buildPacket(type, new String[] { msg1, msg2 });
    }
    
    public S_ServerMessage(int type, String msg1, String msg2, String msg3) {
        this._byte = null;
        this.buildPacket(type, new String[] { msg1, msg2, msg3 });
    }
    
    public S_ServerMessage(int type, String msg1, String msg2, String msg3, String msg4) {
        this._byte = null;
        this.buildPacket(type, new String[] { msg1, msg2, msg3, msg4 });
    }
    
    public S_ServerMessage(int type, String msg1, String msg2, String msg3, String msg4, String msg5) {
        this._byte = null;
        this.buildPacket(type, new String[] { msg1, msg2, msg3, msg4, msg5 });
    }
    
    public S_ServerMessage(int type, String[] info) {
        this._byte = null;
        this.buildPacket(type, info);
    }
    
    private void buildPacket(int type, String[] info) {
        this.writeC(71);
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

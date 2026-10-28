package com.lineage.server.serverpackets;

public class S_RedMessage extends ServerBasePacket
{
    private static final String _S__18_REDMESSAGE = "[S] S_RedMessage";
    private byte[] _byte;
    
    public S_RedMessage(int type, String msg1) {
        this._byte = null;
        this.buildPacket(type, msg1, null, null, 1);
    }
    
    public S_RedMessage(int type, String msg1, String msg2) {
        this._byte = null;
        this.buildPacket(type, msg1, msg2, null, 2);
    }
    
    public S_RedMessage(int type, String msg1, String msg2, String msg3) {
        this._byte = null;
        this.buildPacket(type, msg1, msg2, msg3, 3);
    }
    
    private void buildPacket(int type, String msg1, String msg2, String msg3, int check) {
        this.writeC(105);
        this.writeH(type);
        if (check == 1) {
            if (msg1.length() <= 0) {
                this.writeC(0);
            }
            else {
                this.writeC(1);
                this.writeS(msg1);
            }
        }
        else if (check == 2) {
            this.writeC(2);
            this.writeS(msg1);
            this.writeS(msg2);
        }
        else if (check == 3) {
            this.writeC(3);
            this.writeS(msg1);
            this.writeS(msg2);
            this.writeS(msg3);
        }
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this._bao.toByteArray();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return "[S] S_RedMessage";
    }
}

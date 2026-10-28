package com.lineage.server.serverpackets;

public class S_ItemCount extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ItemCount(int objId, int max, String cmd) {
        this._byte = null;
        this.writeC(136);
        this.writeD(objId);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(max);
        this.writeH(0);
        this.writeS("request");
        this.writeS(cmd);
    }
    
    public S_ItemCount(int objId, int max, String html, String cmd) {
        this._byte = null;
        this.writeC(136);
        this.writeD(objId);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(max);
        this.writeH(0);
        this.writeS(html);
        this.writeS(cmd);
    }
    
    public S_ItemCount(int objId, int min, int max, String html, String cmd, String[] data) {
        this._byte = null;
        this.writeC(136);
        this.writeD(objId);
        this.writeD(0);
        this.writeD(min);
        this.writeD(min);
        this.writeD(max);
        this.writeH(0);
        this.writeS(html);
        this.writeS(cmd);
        if (data != null && 1 <= data.length) {
            this.writeH(data.length);
            for (String datum : data) {
                this.writeS(datum);
            }
        }
    }
    
    public S_ItemCount(int objId, int min, int max, String html, String cmd) {
        this._byte = null;
        this.writeC(136);
        this.writeD(objId);
        this.writeD(0);
        this.writeD(min);
        this.writeD(min);
        this.writeD(max);
        this.writeH(0);
        this.writeS(html);
        this.writeS(cmd);
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

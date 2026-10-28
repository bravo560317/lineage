package com.lineage.server.templates;

public class L1Config
{
    private int objid;
    private int length;
    private byte[] data;
    
    public L1Config() {
        this.objid = 0;
        this.length = 0;
        this.data = null;
    }
    
    public int getObjid() {
        return this.objid;
    }
    
    public void setObjid(int objid) {
        this.objid = objid;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public void setData(byte[] data) {
        this.data = data;
    }
}

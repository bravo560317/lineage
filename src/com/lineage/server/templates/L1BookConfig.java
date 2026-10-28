package com.lineage.server.templates;

public class L1BookConfig
{
    private int objid;
    private int maxSize;
    private byte[] data;
    
    public int getObjid() {
        return this.objid;
    }
    
    public void setObjid(int objid) {
        this.objid = objid;
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public void setData(byte[] data) {
        this.data = data;
    }
}

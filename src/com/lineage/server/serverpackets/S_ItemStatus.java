package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1ItemStatus;
import com.lineage.server.model.Instance.L1ItemInstance;

public class S_ItemStatus extends ServerBasePacket
{
    static {
        System.out.println("### S_ItemStatus ORIGINAL VERSION LOADED ###");
    }
    private byte[] _byte;
    
    public S_ItemStatus(L1ItemInstance item) {
        this._byte = null;
        if (item == null) {
            return;
        }
        else {
            this.buildPacket(item);
            return;
        }
    }
    
    private void buildPacket(L1ItemInstance item) {
        this.writeC(24);
        this.writeD(item.getId());
        this.writeS(item.getViewName());
        int count = (int)Math.min(item.getCount(), 2000000000L);
        this.writeD(count);
        if (!item.isIdentified()) {
            this.writeC(0);
        }
        else {
            L1ItemStatus itemInfo = new L1ItemStatus(item);
            byte[] status = itemInfo.getStatusBytes(false).getBytes();
            this.writeC(status.length);
            byte[] array;
            for (int length = (array = status).length, i = 0; i < length; ++i) {
                byte b = array[i];
                this.writeC((int)b);
            }
        }
    }
    
    public S_ItemStatus(L1ItemInstance item, long count) {
        this._byte = null;
        this.writeC(24);
        this.writeD(item.getId());
        this.writeS(item.getNumberedViewName(count));
        int out_count = (int)Math.min(count, 2000000000L);
        this.writeD(out_count);
        if (!item.isIdentified()) {
            this.writeC(0);
        }
        else {
            L1ItemStatus itemInfo = new L1ItemStatus(item);
            byte[] status = itemInfo.getStatusBytes(false).getBytes();
            this.writeC(status.length);
            byte[] array;
            for (int length = (array = status).length, i = 0; i < length; ++i) {
                byte b = array[i];
                this.writeC((int)b);
            }
        }
    }
    
    public S_ItemStatus(L1ItemInstance item, boolean isIdentified) {
        this._byte = null;
        this.writeC(24);
        this.writeD(item.getId());
        this.writeS(item.getViewName());
        int count = (int)Math.min(item.getCount(), 2000000000L);
        this.writeD(count);
        if (!isIdentified) {
            this.writeC(0);
        }
        else {
            L1ItemStatus itemInfo = new L1ItemStatus(item);
            byte[] status = itemInfo.getStatusBytes(true).getBytes();
            this.writeC(status.length);
            byte[] array;
            for (int length = (array = status).length, i = 0; i < length; ++i) {
                byte b = array[i];
                this.writeC((int)b);
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

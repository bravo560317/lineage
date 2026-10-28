package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1ItemStatus;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.List;

public class S_InvList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_InvList(List<L1ItemInstance> items) {
        this._byte = null;
        this.writeC(5);
        this.writeC(items.size());
        for (L1ItemInstance item : items) {
            this.writeD(item.getId());
            switch (item.getItem().getItemId()) {
                case 40318: {
                    this.writeH(166);
                    break;
                }
                case 40319: {
                    this.writeH(569);
                    break;
                }
                case 40321: {
                    this.writeH(837);
                    break;
                }
                case 49156: {
                    this.writeH(3606);
                    break;
                }
                case 49157: {
                    this.writeH(3605);
                    break;
                }
                case 49158: {
                    this.writeH(3674);
                    break;
                }
                default: {
                    this.writeH(0);
                    break;
                }
            }
            int type = item.getItem().getUseType();
            if (type < 0) {
                type = 0;
            }
            this.writeC(type);
            if (item.getChargeCount() <= 0) {
                this.writeC(0);
            }
            else {
                this.writeC(item.getChargeCount());
            }
            this.writeH(item.get_gfxid());
            this.writeC(item.getBless());
            this.writeD((int)Math.min(item.getCount(), 2000000000L));
            this.writeC(item.getItemStatusX());
            this.writeS(item.getViewName());
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
            this.writeC(10);
            this.writeH(0);
            this.writeD(0);
            this.writeD(0);
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

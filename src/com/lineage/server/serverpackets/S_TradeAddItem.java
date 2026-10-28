package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1ItemStatus;
import com.lineage.server.model.Instance.L1ItemInstance;

public class S_TradeAddItem extends ServerBasePacket
{
    public S_TradeAddItem(L1ItemInstance item, long count, int type) {
        this.writeC(35);
        this.writeC(type);
        this.writeH(item.getItem().getGfxId());
        String name = item.getNumberedViewName(count);
        this.writeS(name);
        if (!item.isIdentified()) {
            this.writeC(3);
            this.writeC(0);
            this.writeC(7);
            this.writeC(0);
        }
        else {
            this.writeC(item.getBless());
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
    
    public S_TradeAddItem() {
        this.writeC(35);
        this.writeC(1);
        this.writeH(714);
        this.writeS("測試物品(55)");
        this.writeC(0);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

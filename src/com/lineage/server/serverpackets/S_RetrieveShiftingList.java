package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.List;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_RetrieveShiftingList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_RetrieveShiftingList(L1PcInstance pc, List<L1ItemInstance> items) {
        this._byte = null;
        this.writeC(176);
        this.writeD(pc.getId());
        this.writeH(items.size());
        this.writeC(2);
        for (L1ItemInstance item : items) {
            int itemid = item.getId();
            this.writeD(itemid);
            this.writeC(0);
            this.writeH(item.get_gfxid());
            this.writeC(item.getBless());
            this.writeD(1);
            this.writeC(item.isIdentified() ? 1 : 0);
            this.writeS(item.getViewName());
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

package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.templates.L1MapsLimitTime;
import com.lineage.server.datatables.MapsGroupTable;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_PacketBoxMapTimer extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_PacketBoxMapTimer(L1PcInstance pc) {
        this._byte = null;
        Collection<L1MapsLimitTime> mapLimitList = (Collection)MapsGroupTable.get().getGroupMaps().values();
        this.writeC(250);
        this.writeC(159);
        this.writeD(mapLimitList.size());
        for (L1MapsLimitTime mapLimit : mapLimitList) {
            int order_id = mapLimit.getOrderId();
            int used_time = pc.getMapsTime(order_id);
            int time_str = (mapLimit.getLimitTime() - used_time) / 60;
            this.writeD(order_id);
            this.writeS(mapLimit.getMapName());
            this.writeD(time_str);
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

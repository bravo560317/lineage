package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.templates.L1MapsLimitTime;
import com.lineage.server.datatables.MapsGroupTable;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_MapTimerOut extends ServerBasePacket
{
    private byte[] _byte;
    private static final String S_MAP_TIMER_OUT = "[S] S_MapTimerOut";
    public static final int DISPLAY_MAP_TIME = 159;
    
    public S_MapTimerOut(L1PcInstance pc) {
        this._byte = null;
        Collection<L1MapsLimitTime> mapLimitList = (Collection)MapsGroupTable.get().getGroupMaps().values();
        this.writeC(250);
        this.writeC(159);
        this.writeD(mapLimitList.size());
        for (L1MapsLimitTime mapLimit : mapLimitList) {
            int order_id = mapLimit.getOrderId();
            int time_str = 0;
            this.writeD(order_id);
            this.writeS(mapLimit.getMapName());
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
        return "[S] S_MapTimerOut";
    }
}

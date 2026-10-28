package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.data.event.GamblingSet;
import com.lineage.server.templates.L1Gambling;
import java.util.Map;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ShopBuyListGam extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShopBuyListGam(L1PcInstance pc, L1NpcInstance npc, Map<Integer, L1Gambling> sellList) {
        this._byte = null;
        this.writeC(65);
        this.writeD(npc.getId());
        if (sellList.isEmpty()) {
            this.writeH(0);
            return;
        }
        else if (sellList.size() <= 0) {
            this.writeH(0);
            return;
        }
        else {
            this.writeH(sellList.size());
            for (Integer itemobjid : sellList.keySet()) {
                this.writeD(itemobjid);
                L1Gambling gam = sellList.get(itemobjid);
                int adena = (int)((double)GamblingSet.GAMADENA * gam.get_rate());
                this.writeD(adena);
            }
            this.writeH(7);
            return;
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

package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.templates.L1Item;
import java.util.Map;
import com.lineage.data.event.gambling.Gambling;
import com.lineage.data.event.GamblingSet;
import com.lineage.data.event.gambling.GamblingNpc;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.timecontroller.event.GamblingTime;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ShopSellListGam extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShopSellListGam(L1PcInstance pc, L1NpcInstance npc) {
        this._byte = null;
        this.writeC(70);
        this.writeD(npc.getId());
        Gambling gambling = GamblingTime.get_gambling();
        Map<Integer, GamblingNpc> list = gambling.get_allNpc();
        if (list.size() <= 0) {
            this.writeH(0);
            return;
        }
        else {
            this.writeH(list.size());
            L1Item item = ItemTable.get().getTemplate(40309);
            int i = 0;
            for (GamblingNpc gamblingNpc : list.values()) {
                ++i;
                pc.get_otherList().add_gamList(gamblingNpc, i);
                this.writeD(i);
                this.writeH(item.getGfxId());
                this.writeD(GamblingSet.GAMADENA);
                int no = GamblingTime.get_gamblingNo();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(gamblingNpc.get_npc().getNameId());
                stringBuilder.append(" [" + no + "-" + gamblingNpc.get_npc().getNpcId() + "]");
                this.writeS(stringBuilder.toString());
                this.writeC(0);
            }
            this.writeH(6100);
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

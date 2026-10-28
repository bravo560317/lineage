package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_RetrievePledgeList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_RetrievePledgeList(int objid, L1PcInstance pc) {
        this._byte = null;
        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        if (clan == null) {
            return;
        }
        else if (clan.getWarehouseUsingChar() != 0 && clan.getWarehouseUsingChar() != pc.getId()) {
            pc.sendPackets(new S_ServerMessage(209));
            return;
        }
        else {
            if (pc.getInventory().getSize() < 170) {
                int size = clan.getDwarfForClanInventory().getSize();
                if (size > 0) {
                    clan.setWarehouseUsingChar(pc.getId());
                    pc.setSkillEffect(7944, 50000);
                    this.writeC(176);
                    this.writeD(objid);
                    this.writeH(size);
                    this.writeC(5);
                    for (L1ItemInstance object51 : clan.getDwarfForClanInventory().getItems()) {
                        Object itemObject = object51;
                        L1ItemInstance item = (L1ItemInstance)itemObject;
                        this.writeD(item.getId());
                        int i = item.getItem().getUseType();
                        if (i < 0) {
                            i = 0;
                        }
                        this.writeC(i);
                        this.writeH(item.get_gfxid());
                        this.writeC(item.getBless());
                        this.writeD((int)Math.min(item.getCount(), 2000000000L));
                        this.writeC(item.isIdentified() ? 1 : 0);
                        this.writeS(item.getViewName());
                    }
                    this.writeH(30);
                    this.writeD(0);
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(263));
            }
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

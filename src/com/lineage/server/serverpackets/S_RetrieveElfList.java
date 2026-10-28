package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_RetrieveElfList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_RetrieveElfList(int objid, L1PcInstance pc) {
        this._byte = null;
        if (pc.getInventory().getSize() < 180) {
            int size = pc.getDwarfForElfInventory().getSize();
            if (size > 0) {
                this.writeC(176);
                this.writeD(objid);
                this.writeH(size);
                this.writeC(9);
                for (L1ItemInstance object49 : pc.getDwarfForElfInventory().getItems()) {
                    Object itemObject = object49;
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

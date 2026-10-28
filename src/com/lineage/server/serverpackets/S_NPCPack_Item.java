package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.server.model.Instance.L1ItemInstance;

public class S_NPCPack_Item extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NPCPack_Item(L1ItemInstance item) {
        this._byte = null;
        this.buildPacket(item);
    }
    
    private void buildPacket(L1ItemInstance item) {
        this.writeC(87);
        this.writeH(item.getX());
        this.writeH(item.getY());
        this.writeD(item.getId());
        this.writeH(item.getItem().getGroundGfxId());
        this.writeC(0);
        this.writeC(0);
        this.writeC(item.isNowLighting() ? item.getItem().getLightRange() : 0);
        this.writeC(0);
        this.writeD((int)Math.min(item.getCount(), 2000000000L));
        this.writeH(0);
        String name = "";
        if (item.getCount() > 1L) {
            name = item.getItem().getNameId() + " (" + item.getCount() + ")";
        }
        else {
            switch (item.getItemId()) {
                case 20383:
                case 41235:
                case 41236: {
                    name = item.getItem().getNameId() + " [" + item.getChargeCount() + "]";
                    break;
                }
                case 40006:
                case 40007:
                case 40008:
                case 40009:
                case 140006:
                case 140008: {
                    if (item.isIdentified()) {
                        name = item.getItem().getNameId() + " (" + item.getChargeCount() + ")";
                        break;
                    }
                    else {
                        break;
                    }
                }
                default: {
                    if (item.getItem().getLightRange() != 0 && item.isNowLighting()) {
                        name = item.getItem().getNameId() + " ($10)";
                        break;
                    }
                    else {
                        name = item.getItem().getNameId();
                        break;
                    }
                }
            }
        }
        L1ItemSpecialAttributeChar attr_char = item.get_ItemAttrName();
        if (attr_char != null) {
            L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
            this.writeS(attr.get_colour() + attr.get_name() + name);
        }
        else {
            this.writeS(name);
        }
        this.writeS(null);
        this.writeC(0);
        this.writeD(0);
        this.writeS(null);
        this.writeS(null);
        this.writeC(0);
        this.writeC(255);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(255);
        this.writeC(255);
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

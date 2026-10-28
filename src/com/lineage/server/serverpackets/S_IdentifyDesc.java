package com.lineage.server.serverpackets;

import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.server.model.Instance.L1ItemInstance;

public class S_IdentifyDesc extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_IdentifyDesc(L1ItemInstance item) {
        this._byte = null;
        this.buildPacket(item);
    }
    
    private void buildPacket(L1ItemInstance item) {
        this.writeC(245);
        this.writeH(item.getItem().getItemDescId());
        StringBuilder name = new StringBuilder();
        switch (item.getItem().getBless()) {
            case 0: {
                name.append("$227 ");
                break;
            }
            case 2: {
                name.append("$228 ");
                break;
            }
        }
        name.append(item.getItem().getNameId());
        switch (item.getItem().getType2()) {
            case 0: {
                switch (item.getItem().getType()) {
                    case 1: {
                        this.writeH(137);
                        this.writeC(3);
                        this.writeS(name.toString());
                        this.writeS(String.valueOf(item.getChargeCount()));
                        break;
                    }
                    case 2: {
                        this.writeH(138);
                        this.writeC(2);
                        name.append(": $231 ");
                        name.append(String.valueOf(item.getRemainingTime()));
                        this.writeS(name.toString());
                        break;
                    }
                    case 7: {
                        this.writeH(136);
                        this.writeC(3);
                        this.writeS(name.toString());
                        this.writeS(String.valueOf(item.getItem().getFoodVolume()));
                        break;
                    }
                    default: {
                        this.writeH(138);
                        this.writeC(2);
                        this.writeS(name.toString());
                        break;
                    }
                }
                this.writeS(String.valueOf(item.getWeight()));
                break;
            }
            case 1: {
                this.writeH(134);
                this.writeC(3);
                this.writeS(name.toString());
                L1ItemSpecialAttributeChar attr_char = item.get_ItemAttrName();
                if (attr_char != null) {
                    L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
                    this.writeS(String.valueOf(item.getItem().getDmgSmall() + attr.get_dmg_small()) + "+" + item.getEnchantLevel());
                    this.writeS(String.valueOf(item.getItem().getDmgLarge() + attr.get_dmg_large()) + "+" + item.getEnchantLevel());
                    break;
                }
                else {
                    this.writeS(String.valueOf(item.getItem().getDmgSmall()) + "+" + item.getEnchantLevel());
                    this.writeS(String.valueOf(item.getItem().getDmgLarge()) + "+" + item.getEnchantLevel());
                    break;
                }
            }
            case 2: {
                item.getItem().getItemId();
                this.writeH(135);
                this.writeC(2);
                this.writeS(name.toString());
                this.writeS(String.valueOf(Math.abs(item.getItem().get_ac())) + "+" + item.getEnchantLevel());
                break;
            }
        }
    }
    
    public S_IdentifyDesc() {
        this._byte = null;
        L1ItemInstance item = ItemTable.get().createItem(2);
        this.writeC(245);
        this.writeH(item.getItem().getItemDescId());
        this.writeH(134);
        this.writeC(3);
        this.writeS(item.getName());
        this.writeS(String.valueOf(item.getItem().getDmgSmall()) + "+" + item.getEnchantLevel());
        this.writeS(String.valueOf(item.getItem().getDmgLarge()) + "+" + item.getEnchantLevel());
        this.writeS(String.valueOf(item.getWeight()));
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

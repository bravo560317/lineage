package com.lineage.server.serverpackets;

import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.List;

public class S_FixWeaponList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_FixWeaponList(List<L1ItemInstance> weaponList) {
        this._byte = null;
        this.writeC(83);
        this.writeD(200);
        this.writeH(weaponList.size());
        for (L1ItemInstance weapon : weaponList) {
            this.writeD(weapon.getId());
            this.writeC(weapon.get_durability());
        }
    }
    
    public S_FixWeaponList(L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pc);
    }
    
    private void buildPacket(L1PcInstance pc) {
        this.writeC(83);
        this.writeD(200);
        List<L1ItemInstance> weaponList = new ArrayList();
        List<L1ItemInstance> itemList = pc.getInventory().getItems();
        for (L1ItemInstance item : itemList) {
            switch (item.getItem().getType2()) {
                case 1: {
                    if (item.get_durability() > 0) {
                        weaponList.add(item);
                        continue;
                    }
                    else {
                        continue;
                    }
                }
                default: {
                    continue;
                }
            }
        }
        this.writeH(weaponList.size());
        for (L1ItemInstance weapon : weaponList) {
            this.writeD(weapon.getId());
            this.writeC(weapon.get_durability());
        }
    }
    
    public S_FixWeaponList(L1ItemInstance weapon) {
        this._byte = null;
        this.writeC(83);
        this.writeD(200);
        this.writeH(1);
        this.writeD(weapon.getId());
        this.writeC(weapon.get_durability());
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

package com.add.system;

import com.lineage.server.model.Instance.L1ItemInstance;

public class L1FireCrystal
{
    private int _itemid;
    private int _enchant_lv0;
    private int _enchant_lv1;
    private int _enchant_lv2;
    private int _enchant_lv3;
    private int _enchant_lv4;
    private int _enchant_lv5;
    private int _enchant_lv6;
    private int _enchant_lv7;
    private int _enchant_lv8;
    private int _enchant_lv9;
    private int _enchant_lv10;
    private int _enchant_lv11;
    private int _enchant_lv12;
    private int _enchant_lv13;
    private int _enchant_lv14;
    
    public L1FireCrystal(int itemid, int enchant_lv0, int enchant_lv1, int enchant_lv2, int enchant_lv3, int enchant_lv4, int enchant_lv5, int enchant_lv6, int enchant_lv7, int enchant_lv8, int enchant_lv9, int enchant_lv10, int enchant_lv11, int enchant_lv12, int enchant_lv13, int enchant_lv14) {
        this._itemid = itemid;
        this._enchant_lv0 = enchant_lv0;
        this._enchant_lv1 = enchant_lv1;
        this._enchant_lv2 = enchant_lv2;
        this._enchant_lv3 = enchant_lv3;
        this._enchant_lv4 = enchant_lv4;
        this._enchant_lv5 = enchant_lv5;
        this._enchant_lv6 = enchant_lv6;
        this._enchant_lv7 = enchant_lv7;
        this._enchant_lv8 = enchant_lv8;
        this._enchant_lv9 = enchant_lv9;
        this._enchant_lv10 = enchant_lv10;
        this._enchant_lv11 = enchant_lv11;
        this._enchant_lv12 = enchant_lv12;
        this._enchant_lv13 = enchant_lv13;
        this._enchant_lv14 = enchant_lv14;
    }
    
    public int get_itemid() {
        return this._itemid;
    }
    
    public int get_CrystalCount(L1ItemInstance item) {
        int enchantlv = item.getEnchantLevel();
        int crystalcount = 0;
        switch (enchantlv) {
            case 0: {
                crystalcount = this._enchant_lv0;
                break;
            }
            case 1: {
                crystalcount = this._enchant_lv1;
                break;
            }
            case 2: {
                crystalcount = this._enchant_lv2;
                break;
            }
            case 3: {
                crystalcount = this._enchant_lv3;
                break;
            }
            case 4: {
                crystalcount = this._enchant_lv4;
                break;
            }
            case 5: {
                crystalcount = this._enchant_lv5;
                break;
            }
            case 6: {
                crystalcount = this._enchant_lv6;
                break;
            }
            case 7: {
                crystalcount = this._enchant_lv7;
                break;
            }
            case 8: {
                crystalcount = this._enchant_lv8;
                break;
            }
            case 9: {
                crystalcount = this._enchant_lv9;
                break;
            }
            case 10: {
                crystalcount = this._enchant_lv10;
                break;
            }
            case 11: {
                crystalcount = this._enchant_lv11;
                break;
            }
            case 12: {
                crystalcount = this._enchant_lv12;
                break;
            }
            case 13: {
                crystalcount = this._enchant_lv13;
                break;
            }
            case 14: {
                crystalcount = this._enchant_lv14;
                break;
            }
        }
        if (enchantlv > 14) {
            crystalcount = this._enchant_lv14;
        }
        int safeenchant = item.getItem().get_safeenchant();
        if (item.getItem().getType2() == 2 && safeenchant >= 4) {
            if (enchantlv > 12) {
                crystalcount = this._enchant_lv12;
            }
        }
        else if (item.getItem().getType2() == 2 && safeenchant == 0) {
            if (enchantlv > 3) {
                crystalcount = this._enchant_lv3;
            }
        }
        else if (item.getItem().getType2() == 2 && safeenchant == -1) {
            crystalcount = this._enchant_lv0;
        }
        return crystalcount;
    }
}

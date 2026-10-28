package com.lineage.server.templates;

public class L1DropEnchant
{
    int _itemId;
    int[] _enchants;
    
    public L1DropEnchant(int itemId, int[] enchants) {
        this._enchants = enchants;
        this._itemId = itemId;
    }
    
    public int getItemid() {
        return this._itemId;
    }
    
    public int[] getEnchants() {
        return this._enchants;
    }
}

package com.lineage.server.templates;

public class L1DropScreen
{
    int _mobId;
    int _itemId;
    int _min;
    int _max;
    int _chance;
    int _enchant_min;
    int _enchant_max;
    boolean _is_attack;
    
    public L1DropScreen(int mobId, int itemId, int min, int max, int chance, int enchant_min, int enchant_max, boolean is_attack) {
        this._mobId = mobId;
        this._itemId = itemId;
        this._min = min;
        this._max = max;
        this._chance = chance;
        this._enchant_min = enchant_min;
        this._enchant_min = enchant_max;
        this._is_attack = is_attack;
    }
    
    public int getChance() {
        return this._chance;
    }
    
    public int getItemid() {
        return this._itemId;
    }
    
    public int getMax() {
        return this._max;
    }
    
    public int getMin() {
        return this._min;
    }
    
    public int getEnchantMin() {
        return this._enchant_min;
    }
    
    public int getEnchantMax() {
        return this._enchant_max;
    }
    
    public int getMobid() {
        return this._mobId;
    }
    
    public boolean is_attack() {
        return this._is_attack;
    }
}

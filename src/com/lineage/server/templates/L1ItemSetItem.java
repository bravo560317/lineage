package com.lineage.server.templates;

public class L1ItemSetItem
{
    private final int id;
    private final int amount;
    private final int enchant;
    
    public L1ItemSetItem(int id, int amount, int enchant) {
        this.id = id;
        this.amount = amount;
        this.enchant = enchant;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public int getEnchant() {
        return this.enchant;
    }
}

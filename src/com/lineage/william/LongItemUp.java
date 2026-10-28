package com.lineage.william;

public class LongItemUp
{
    private final int item;
    private final int itemin;
    private final int giveItem;
    private final int chance;
    
    public LongItemUp(int item, int itemin, int giveItem, int chance) {
        this.item = item;
        this.itemin = itemin;
        this.giveItem = giveItem;
        this.chance = chance;
    }
    
    public final int getItem() {
        return this.item;
    }
    
    public final int getItemin() {
        return this.itemin;
    }
    
    public final int getGiveItem() {
        return this.giveItem;
    }
    
    public final int getChance() {
        return this.chance;
    }
}

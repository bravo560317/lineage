package com.lineage.server.model.classes;

class L1DragonKnightClassFeature extends L1ClassFeature
{
    @Override
    public int getAcDefenseMax(int ac) {
        return ac / 3;
    }
    
    @Override
    public int getMagicLevel(int playerLevel) {
        return Math.min(4, playerLevel / 9);
    }
    
    @Override
    public int getAttackLevel(int playerLevel) {
        return playerLevel / 10;
    }
    
    @Override
    public int getHitLevel(int playerLevel) {
        return playerLevel / 4;
    }
}

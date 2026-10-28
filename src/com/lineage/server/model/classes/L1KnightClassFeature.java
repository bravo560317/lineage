package com.lineage.server.model.classes;

class L1KnightClassFeature extends L1ClassFeature
{
    @Override
    public int getAcDefenseMax(int ac) {
        return ac / 2;
    }
    
    @Override
    public int getMagicLevel(int playerLevel) {
        return playerLevel / 50;
    }
    
    @Override
    public int getAttackLevel(int playerLevel) {
        return playerLevel / 10;
    }
    
    @Override
    public int getHitLevel(int playerLevel) {
        return playerLevel / 10;
    }
}

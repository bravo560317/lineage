package com.lineage.server.model.classes;

class L1IllusionistClassFeature extends L1ClassFeature
{
    @Override
    public int getAcDefenseMax(int ac) {
        return ac / 4;
    }
    
    @Override
    public int getMagicLevel(int playerLevel) {
        return Math.min(10, playerLevel / 6);
    }
    
    @Override
    public int getAttackLevel(int playerLevel) {
        return playerLevel / 16;
    }
    
    @Override
    public int getHitLevel(int playerLevel) {
        return playerLevel / 40;
    }
}

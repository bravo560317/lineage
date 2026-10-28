package com.lineage.server.model.classes;

class L1WizardClassFeature extends L1ClassFeature
{
    @Override
    public int getAcDefenseMax(int ac) {
        return ac / 5;
    }
    
    @Override
    public int getMagicLevel(int playerLevel) {
        return Math.min(10, playerLevel >> 2);
    }
    
    @Override
    public int getAttackLevel(int playerLevel) {
        return playerLevel / 10;
    }
    
    @Override
    public int getHitLevel(int playerLevel) {
        return playerLevel / 40;
    }
}

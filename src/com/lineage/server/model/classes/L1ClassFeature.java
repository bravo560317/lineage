package com.lineage.server.model.classes;

public abstract class L1ClassFeature
{
    private static final int[] _intMagicConsumeReduction;
    
    static {
        _intMagicConsumeReduction = new int[] { 0, 0, 1, 2, 2, 3, 4, 4, 5, 6, 6, 7, 8, 8, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15, 16, 16, 17, 18, 18, 19, 20, 20, 21, 22, 22, 23, 24, 24, 25, 26, 26, 27, 28, 28, 29, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
    }
    
    public static L1ClassFeature newClassFeature(int classId) {
        switch (classId) {
            case 0:
            case 1: {
                return new L1RoyalClassFeature();
            }
            case 37:
            case 138: {
                return new L1ElfClassFeature();
            }
            case 48:
            case 61: {
                return new L1KnightClassFeature();
            }
            case 734:
            case 1186: {
                return new L1WizardClassFeature();
            }
            case 2786:
            case 2796: {
                return new L1DarkElfClassFeature();
            }
            case 6650:
            case 6671: {
                return new L1IllusionistClassFeature();
            }
            case 6658:
            case 6661: {
                return new L1DragonKnightClassFeature();
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }
    
    public abstract int getAcDefenseMax(int p0);
    
    public abstract int getMagicLevel(int p0);
    
    public abstract int getAttackLevel(int p0);
    
    public abstract int getHitLevel(int p0);
    
    public static int calcIntMagicConsumeReduction(int intel) {
        return L1ClassFeature._intMagicConsumeReduction[intel];
    }
}

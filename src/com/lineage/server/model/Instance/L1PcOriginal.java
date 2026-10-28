package com.lineage.server.model.Instance;

public class L1PcOriginal
{
    public static int resetOriginalHpup(L1PcInstance pc) {
        int originalCon = pc.getOriginalCon();
        int originalHpup = 0;
        if (pc.isCrown()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11: {
                    originalHpup = 0;
                    break;
                }
                case 12:
                case 13: {
                    originalHpup = 1;
                    break;
                }
                case 14:
                case 15: {
                    originalHpup = 2;
                    break;
                }
                default: {
                    originalHpup = 3;
                    break;
                }
            }
        }
        else if (pc.isKnight()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14: {
                    originalHpup = 0;
                    break;
                }
                case 15:
                case 16: {
                    originalHpup = 1;
                    break;
                }
                default: {
                    originalHpup = 3;
                    break;
                }
            }
        }
        else if (pc.isElf()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12: {
                    originalHpup = 0;
                    break;
                }
                case 13:
                case 14:
                case 15:
                case 16:
                case 17: {
                    originalHpup = 1;
                    break;
                }
                default: {
                    originalHpup = 2;
                    break;
                }
            }
        }
        else if (pc.isDarkelf()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9: {
                    originalHpup = 0;
                    break;
                }
                case 10:
                case 11: {
                    originalHpup = 1;
                    break;
                }
                default: {
                    originalHpup = 2;
                    break;
                }
            }
        }
        else if (pc.isWizard()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13: {
                    originalHpup = 0;
                    break;
                }
                case 14:
                case 15: {
                    originalHpup = 1;
                    break;
                }
                default: {
                    originalHpup = 2;
                    break;
                }
            }
        }
        else if (pc.isDragonKnight()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14: {
                    originalHpup = 0;
                    break;
                }
                case 15:
                case 16: {
                    originalHpup = 1;
                    break;
                }
                default: {
                    originalHpup = 3;
                    break;
                }
            }
        }
        else if (pc.isIllusionist()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12: {
                    originalHpup = 0;
                    break;
                }
                case 13:
                case 14: {
                    originalHpup = 1;
                    break;
                }
                default: {
                    originalHpup = 2;
                    break;
                }
            }
        }
        return originalHpup;
    }
    
    public static int resetOriginalMpup(L1PcInstance pc) {
        int originalWis = pc.getOriginalWis();
        int originalMpup = 0;
        if (pc.isCrown()) {
            switch (originalWis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15: {
                    originalMpup = 0;
                    break;
                }
                default: {
                    originalMpup = 1;
                    break;
                }
            }
        }
        else if (pc.isKnight()) {
            originalMpup = 0;
        }
        else if (pc.isElf()) {
            switch (originalWis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13: {
                    originalMpup = 0;
                    break;
                }
                case 14:
                case 15:
                case 16: {
                    originalMpup = 1;
                    break;
                }
                default: {
                    originalMpup = 2;
                    break;
                }
            }
        }
        else if (pc.isDarkelf()) {
            switch (originalWis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11: {
                    originalMpup = 0;
                    break;
                }
                default: {
                    originalMpup = 1;
                    break;
                }
            }
        }
        else if (pc.isWizard()) {
            switch (originalWis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12: {
                    originalMpup = 0;
                    break;
                }
                case 13:
                case 14:
                case 15:
                case 16: {
                    originalMpup = 1;
                    break;
                }
                default: {
                    originalMpup = 2;
                    break;
                }
            }
        }
        else if (pc.isDragonKnight()) {
            switch (originalWis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12: {
                    originalMpup = 0;
                    break;
                }
                case 13:
                case 14:
                case 15: {
                    originalMpup = 1;
                    break;
                }
                default: {
                    originalMpup = 2;
                    break;
                }
            }
        }
        else if (pc.isIllusionist()) {
            switch (originalWis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12: {
                    originalMpup = 0;
                    break;
                }
                case 13:
                case 14:
                case 15: {
                    originalMpup = 1;
                    break;
                }
                default: {
                    originalMpup = 2;
                    break;
                }
            }
        }
        return originalMpup;
    }
    
    public static int resetOriginalStrWeightReduction(L1PcInstance pc) {
        int originalStr = pc.getOriginalStr();
        int originalStrWeightReduction = 0;
        if (pc.isCrown()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13: {
                    originalStrWeightReduction = 0;
                    break;
                }
                case 14:
                case 15:
                case 16: {
                    originalStrWeightReduction = 1;
                    break;
                }
                case 17:
                case 18:
                case 19: {
                    originalStrWeightReduction = 2;
                    break;
                }
                default: {
                    originalStrWeightReduction = 3;
                    break;
                }
            }
        }
        else if (pc.isKnight()) {
            originalStrWeightReduction = 0;
        }
        else if (pc.isElf()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15: {
                    originalStrWeightReduction = 0;
                    break;
                }
                default: {
                    originalStrWeightReduction = 2;
                    break;
                }
            }
        }
        else if (pc.isDarkelf()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12: {
                    originalStrWeightReduction = 0;
                    break;
                }
                case 13:
                case 14:
                case 15: {
                    originalStrWeightReduction = 2;
                    break;
                }
                default: {
                    originalStrWeightReduction = 3;
                    break;
                }
            }
        }
        else if (pc.isWizard()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9: {
                    originalStrWeightReduction = 0;
                    break;
                }
                default: {
                    originalStrWeightReduction = 1;
                    break;
                }
            }
        }
        else if (pc.isDragonKnight()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15: {
                    originalStrWeightReduction = 0;
                    break;
                }
                default: {
                    originalStrWeightReduction = 1;
                    break;
                }
            }
        }
        else if (pc.isIllusionist()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17: {
                    originalStrWeightReduction = 0;
                    break;
                }
                default: {
                    originalStrWeightReduction = 1;
                    break;
                }
            }
        }
        return originalStrWeightReduction;
    }
    
    public static int resetOriginalDmgup(L1PcInstance pc) {
        int originalStr = pc.getOriginalStr();
        int originalDmgup = 0;
        if (pc.isCrown()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14: {
                    originalDmgup = 0;
                    break;
                }
                case 15:
                case 16:
                case 17: {
                    originalDmgup = 1;
                    break;
                }
                default: {
                    originalDmgup = 2;
                    break;
                }
            }
        }
        else if (pc.isKnight()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17: {
                    originalDmgup = 0;
                    break;
                }
                case 18:
                case 19: {
                    originalDmgup = 2;
                    break;
                }
                default: {
                    originalDmgup = 4;
                    break;
                }
            }
        }
        else if (pc.isElf()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11: {
                    originalDmgup = 0;
                    break;
                }
                case 12:
                case 13: {
                    originalDmgup = 1;
                    break;
                }
                default: {
                    originalDmgup = 2;
                    break;
                }
            }
        }
        else if (pc.isDarkelf()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13: {
                    originalDmgup = 0;
                    break;
                }
                case 14:
                case 15:
                case 16:
                case 17: {
                    originalDmgup = 1;
                    break;
                }
                default: {
                    originalDmgup = 2;
                    break;
                }
            }
        }
        else if (pc.isWizard()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9: {
                    originalDmgup = 0;
                    break;
                }
                case 10:
                case 11: {
                    originalDmgup = 1;
                    break;
                }
                default: {
                    originalDmgup = 2;
                    break;
                }
            }
        }
        else if (pc.isDragonKnight()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14: {
                    originalDmgup = 0;
                    break;
                }
                case 15:
                case 16:
                case 17: {
                    originalDmgup = 1;
                    break;
                }
                default: {
                    originalDmgup = 3;
                    break;
                }
            }
        }
        else if (pc.isIllusionist()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11: {
                    originalDmgup = 0;
                    break;
                }
                case 12:
                case 13: {
                    originalDmgup = 1;
                    break;
                }
                default: {
                    originalDmgup = 2;
                    break;
                }
            }
        }
        return originalDmgup;
    }
    
    public static int resetOriginalConWeightReduction(L1PcInstance pc) {
        int originalCon = pc.getOriginalCon();
        int originalConWeightReduction = 0;
        if (pc.isCrown()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10: {
                    originalConWeightReduction = 0;
                    break;
                }
                default: {
                    originalConWeightReduction = 1;
                    break;
                }
            }
        }
        else if (pc.isKnight()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14: {
                    originalConWeightReduction = 0;
                    break;
                }
                default: {
                    originalConWeightReduction = 1;
                    break;
                }
            }
        }
        else if (pc.isElf()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14: {
                    originalConWeightReduction = 0;
                    break;
                }
                default: {
                    originalConWeightReduction = 2;
                    break;
                }
            }
        }
        else if (pc.isDarkelf()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8: {
                    originalConWeightReduction = 0;
                    break;
                }
                default: {
                    originalConWeightReduction = 1;
                    break;
                }
            }
        }
        else if (pc.isWizard()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12: {
                    originalConWeightReduction = 0;
                    break;
                }
                case 13:
                case 14: {
                    originalConWeightReduction = 1;
                    break;
                }
                default: {
                    originalConWeightReduction = 2;
                    break;
                }
            }
        }
        else if (pc.isDragonKnight()) {
            originalConWeightReduction = 0;
        }
        else if (pc.isIllusionist()) {
            switch (originalCon) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16: {
                    originalConWeightReduction = 0;
                    break;
                }
                case 17: {
                    originalConWeightReduction = 1;
                    break;
                }
                default: {
                    originalConWeightReduction = 2;
                    break;
                }
            }
        }
        return originalConWeightReduction;
    }
    
    public static int resetOriginalBowDmgup(L1PcInstance pc) {
        int originalDex = pc.getOriginalDex();
        int originalBowDmgup = 0;
        if (pc.isCrown()) {
            switch (originalDex) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12: {
                    originalBowDmgup = 0;
                    break;
                }
                default: {
                    originalBowDmgup = 1;
                    break;
                }
            }
        }
        else if (pc.isKnight()) {
            originalBowDmgup = 0;
        }
        else if (pc.isElf()) {
            switch (originalDex) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13: {
                    originalBowDmgup = 0;
                    break;
                }
                case 14:
                case 15:
                case 16: {
                    originalBowDmgup = 2;
                    break;
                }
                default: {
                    originalBowDmgup = 3;
                    break;
                }
            }
        }
        else if (pc.isDarkelf()) {
            switch (originalDex) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17: {
                    originalBowDmgup = 0;
                    break;
                }
                default: {
                    originalBowDmgup = 2;
                    break;
                }
            }
        }
        else if (pc.isWizard()) {
            originalBowDmgup = 0;
        }
        else if (pc.isDragonKnight()) {
            originalBowDmgup = 0;
        }
        else if (pc.isIllusionist()) {
            originalBowDmgup = 0;
        }
        return originalBowDmgup;
    }
    
    public static int resetOriginalHitup(L1PcInstance pc) {
        int originalStr = pc.getOriginalStr();
        int originalHitup = 0;
        if (pc.isCrown()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15: {
                    originalHitup = 0;
                    break;
                }
                case 16:
                case 17:
                case 18: {
                    originalHitup = 1;
                    break;
                }
                default: {
                    originalHitup = 2;
                    break;
                }
            }
        }
        else if (pc.isKnight()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16: {
                    originalHitup = 0;
                    break;
                }
                case 17:
                case 18: {
                    originalHitup = 2;
                    break;
                }
                default: {
                    originalHitup = 4;
                    break;
                }
            }
        }
        else if (pc.isElf()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12: {
                    originalHitup = 0;
                    break;
                }
                case 13:
                case 14: {
                    originalHitup = 1;
                    break;
                }
                default: {
                    originalHitup = 2;
                    break;
                }
            }
        }
        else if (pc.isDarkelf()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14: {
                    originalHitup = 0;
                    break;
                }
                case 15:
                case 16:
                case 17: {
                    originalHitup = 1;
                    break;
                }
                default: {
                    originalHitup = 2;
                    break;
                }
            }
        }
        else if (pc.isWizard()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10: {
                    originalHitup = 0;
                    break;
                }
                case 11:
                case 12: {
                    originalHitup = 1;
                    break;
                }
                default: {
                    originalHitup = 2;
                    break;
                }
            }
        }
        else if (pc.isDragonKnight()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13: {
                    originalHitup = 0;
                    break;
                }
                case 14:
                case 15:
                case 16: {
                    originalHitup = 1;
                    break;
                }
                default: {
                    originalHitup = 3;
                    break;
                }
            }
        }
        else if (pc.isIllusionist()) {
            switch (originalStr) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11: {
                    originalHitup = 0;
                    break;
                }
                case 12:
                case 13: {
                    originalHitup = 1;
                    break;
                }
                case 14:
                case 15: {
                    originalHitup = 2;
                    break;
                }
                case 16: {
                    originalHitup = 3;
                    break;
                }
                default: {
                    originalHitup = 4;
                    break;
                }
            }
        }
        return originalHitup;
    }
    
    public static int resetOriginalBowHitup(L1PcInstance pc) {
        int originalDex = pc.getOriginalDex();
        int originalBowHitup = 0;
        if (pc.isCrown()) {
            originalBowHitup = 0;
        }
        else if (pc.isKnight()) {
            originalBowHitup = 0;
        }
        else if (pc.isElf()) {
            if (originalDex >= 13 && originalDex <= 15) {
                originalBowHitup = 2;
            }
            else if (originalDex >= 16) {
                originalBowHitup = 3;
            }
            else {
                originalBowHitup = 0;
            }
        }
        else if (pc.isDarkelf()) {
            if (originalDex == 17) {
                originalBowHitup = 1;
            }
            else if (originalDex == 18) {
                originalBowHitup = 2;
            }
            else {
                originalBowHitup = 0;
            }
        }
        else if (pc.isWizard()) {
            originalBowHitup = 0;
        }
        else if (pc.isDragonKnight()) {
            originalBowHitup = 0;
        }
        else if (pc.isIllusionist()) {
            originalBowHitup = 0;
        }
        return originalBowHitup;
    }
    
    public static int resetOriginalMr(L1PcInstance pc) {
        int originalWis = pc.getOriginalWis();
        int originalMr = 0;
        if (pc.isCrown()) {
            if (originalWis == 12 || originalWis == 13) {
                originalMr = 1;
            }
            else if (originalWis >= 14) {
                originalMr = 2;
            }
            else {
                originalMr = 0;
            }
        }
        else if (pc.isKnight()) {
            if (originalWis == 10 || originalWis == 11) {
                originalMr = 1;
            }
            else if (originalWis >= 12) {
                originalMr = 2;
            }
            else {
                originalMr = 0;
            }
        }
        else if (pc.isElf()) {
            if (originalWis >= 13 && originalWis <= 15) {
                originalMr = 1;
            }
            else if (originalWis >= 16) {
                originalMr = 2;
            }
            else {
                originalMr = 0;
            }
        }
        else if (pc.isDarkelf()) {
            if (originalWis >= 11 && originalWis <= 13) {
                originalMr = 1;
            }
            else if (originalWis == 14) {
                originalMr = 2;
            }
            else if (originalWis == 15) {
                originalMr = 3;
            }
            else if (originalWis >= 16) {
                originalMr = 4;
            }
            else {
                originalMr = 0;
            }
        }
        else if (pc.isWizard()) {
            if (originalWis >= 15) {
                originalMr = 1;
            }
            else {
                originalMr = 0;
            }
        }
        else if (pc.isDragonKnight()) {
            if (originalWis >= 14) {
                originalMr = 2;
            }
            else {
                originalMr = 0;
            }
        }
        else if (pc.isIllusionist()) {
            if (originalWis >= 15 && originalWis <= 17) {
                originalMr = 2;
            }
            else if (originalWis == 18) {
                originalMr = 4;
            }
            else {
                originalMr = 0;
            }
        }
        return originalMr;
    }
    
    public static int resetOriginalMagicHit(L1PcInstance pc) {
        int originalInt = pc.getOriginalInt();
        int originalMagicHit = 0;
        if (pc.isCrown()) {
            if (originalInt == 12 || originalInt == 13) {
                originalMagicHit = 1;
            }
            else if (originalInt >= 14) {
                originalMagicHit = 2;
            }
            else {
                originalMagicHit = 0;
            }
        }
        else if (pc.isKnight()) {
            if (originalInt == 10 || originalInt == 11) {
                originalMagicHit = 1;
            }
            else if (originalInt == 12) {
                originalMagicHit = 2;
            }
            else {
                originalMagicHit = 0;
            }
        }
        else if (pc.isElf()) {
            if (originalInt == 13 || originalInt == 14) {
                originalMagicHit = 1;
            }
            else if (originalInt >= 15) {
                originalMagicHit = 2;
            }
            else {
                originalMagicHit = 0;
            }
        }
        else if (pc.isDarkelf()) {
            if (originalInt == 12 || originalInt == 13) {
                originalMagicHit = 1;
            }
            else if (originalInt >= 14) {
                originalMagicHit = 2;
            }
            else {
                originalMagicHit = 0;
            }
        }
        else if (pc.isWizard()) {
            if (originalInt <= 14) {
                originalMagicHit = 0;
            }
            else if (originalInt > 14 && originalInt <= 80) {
                originalMagicHit = 1;
            }
            else if (originalInt > 80) {
                originalMagicHit = 2;
            }
        }
        else if (pc.isDragonKnight()) {
            if (originalInt == 12 || originalInt == 13) {
                originalMagicHit = 2;
            }
            else if (originalInt == 14 || originalInt == 15) {
                originalMagicHit = 3;
            }
            else if (originalInt >= 16) {
                originalMagicHit = 4;
            }
            else {
                originalMagicHit = 0;
            }
        }
        else if (pc.isIllusionist()) {
            if (originalInt >= 13) {
                originalMagicHit = 1;
            }
            else {
                originalMagicHit = 0;
            }
        }
        return originalMagicHit;
    }
    
    public static int resetOriginalMagicCritical(L1PcInstance pc) {
        int originalInt = pc.getOriginalInt();
        int originalMagicCritical = 0;
        if (pc.isCrown()) {
            originalMagicCritical = 0;
        }
        else if (pc.isKnight()) {
            originalMagicCritical = 0;
        }
        else if (pc.isElf()) {
            if (originalInt == 14 || originalInt == 15) {
                originalMagicCritical = 2;
            }
            else if (originalInt >= 16) {
                originalMagicCritical = 4;
            }
            else {
                originalMagicCritical = 0;
            }
        }
        else if (pc.isDarkelf()) {
            originalMagicCritical = 0;
        }
        else if (pc.isWizard()) {
            if (originalInt == 15) {
                originalMagicCritical = 2;
            }
            else if (originalInt == 16) {
                originalMagicCritical = 4;
            }
            else if (originalInt == 17) {
                originalMagicCritical = 6;
            }
            else if (originalInt == 18) {
                originalMagicCritical = 8;
            }
            else {
                originalMagicCritical = 0;
            }
        }
        else if (pc.isDragonKnight()) {
            originalMagicCritical = 0;
        }
        else if (pc.isIllusionist()) {
            originalMagicCritical = 0;
        }
        return originalMagicCritical;
    }
    
    public static int resetOriginalMagicConsumeReduction(L1PcInstance pc) {
        int originalInt = pc.getOriginalInt();
        int originalMagicConsumeReduction = 0;
        if (pc.isCrown()) {
            if (originalInt == 11 || originalInt == 12) {
                originalMagicConsumeReduction = 1;
            }
            else if (originalInt >= 13) {
                originalMagicConsumeReduction = 2;
            }
            else {
                originalMagicConsumeReduction = 0;
            }
        }
        else if (pc.isKnight()) {
            if (originalInt == 9 || originalInt == 10) {
                originalMagicConsumeReduction = 1;
            }
            else if (originalInt >= 11) {
                originalMagicConsumeReduction = 2;
            }
            else {
                originalMagicConsumeReduction = 0;
            }
        }
        else if (pc.isElf()) {
            originalMagicConsumeReduction = 0;
        }
        else if (pc.isDarkelf()) {
            if (originalInt == 13 || originalInt == 14) {
                originalMagicConsumeReduction = 1;
            }
            else if (originalInt >= 15) {
                originalMagicConsumeReduction = 2;
            }
            else {
                originalMagicConsumeReduction = 0;
            }
        }
        else if (pc.isWizard()) {
            originalMagicConsumeReduction = 0;
        }
        else if (pc.isDragonKnight()) {
            originalMagicConsumeReduction = 0;
        }
        else if (pc.isIllusionist()) {
            if (originalInt == 14) {
                originalMagicConsumeReduction = 1;
            }
            else if (originalInt >= 15) {
                originalMagicConsumeReduction = 2;
            }
            else {
                originalMagicConsumeReduction = 0;
            }
        }
        return originalMagicConsumeReduction;
    }
    
    public static int resetOriginalMagicDamage(L1PcInstance pc) {
        int originalInt = pc.getOriginalInt();
        int originalMagicDamage = 0;
        if (pc.isCrown()) {
            originalMagicDamage = 0;
        }
        else if (pc.isKnight()) {
            originalMagicDamage = 0;
        }
        else if (pc.isElf()) {
            originalMagicDamage = 0;
        }
        else if (pc.isDarkelf()) {
            originalMagicDamage = 0;
        }
        else if (pc.isWizard()) {
            if (originalInt >= 13) {
                originalMagicDamage = 1;
            }
            else {
                originalMagicDamage = 0;
            }
        }
        else if (pc.isDragonKnight()) {
            if (originalInt == 13 || originalInt == 14) {
                originalMagicDamage = 1;
            }
            else if (originalInt == 15 || originalInt == 16) {
                originalMagicDamage = 2;
            }
            else if (originalInt == 17) {
                originalMagicDamage = 3;
            }
            else {
                originalMagicDamage = 0;
            }
        }
        else if (pc.isIllusionist()) {
            if (originalInt == 16) {
                originalMagicDamage = 1;
            }
            else if (originalInt == 17) {
                originalMagicDamage = 2;
            }
            else {
                originalMagicDamage = 0;
            }
        }
        return originalMagicDamage;
    }
    
    public static int resetOriginalAc(L1PcInstance pc) {
        int originalDex = pc.getOriginalDex();
        int originalAc = 0;
        if (pc.isCrown()) {
            if (originalDex >= 12 && originalDex <= 14) {
                originalAc = 1;
            }
            else if (originalDex == 15 || originalDex == 16) {
                originalAc = 2;
            }
            else if (originalDex >= 17) {
                originalAc = 3;
            }
            else {
                originalAc = 0;
            }
        }
        else if (pc.isKnight()) {
            if (originalDex == 13 || originalDex == 14) {
                originalAc = 1;
            }
            else if (originalDex >= 15) {
                originalAc = 3;
            }
            else {
                originalAc = 0;
            }
        }
        else if (pc.isElf()) {
            if (originalDex >= 15 && originalDex <= 17) {
                originalAc = 1;
            }
            else if (originalDex == 18) {
                originalAc = 2;
            }
            else {
                originalAc = 0;
            }
        }
        else if (pc.isDarkelf()) {
            if (originalDex >= 17) {
                originalAc = 1;
            }
            else {
                originalAc = 0;
            }
        }
        else if (pc.isWizard()) {
            if (originalDex == 8 || originalDex == 9) {
                originalAc = 1;
            }
            else if (originalDex >= 10) {
                originalAc = 2;
            }
            else {
                originalAc = 0;
            }
        }
        else if (pc.isDragonKnight()) {
            if (originalDex == 12 || originalDex == 13) {
                originalAc = 1;
            }
            else if (originalDex >= 14) {
                originalAc = 2;
            }
            else {
                originalAc = 0;
            }
        }
        else if (pc.isIllusionist()) {
            if (originalDex == 11 || originalDex == 12) {
                originalAc = 1;
            }
            else if (originalDex >= 13) {
                originalAc = 2;
            }
            else {
                originalAc = 0;
            }
        }
        return originalAc;
    }
    
    public static int resetOriginalEr(L1PcInstance pc) {
        int originalDex = pc.getOriginalDex();
        int originalEr = 0;
        if (pc.isCrown()) {
            if (originalDex == 14 || originalDex == 15) {
                originalEr = 1;
            }
            else if (originalDex == 16 || originalDex == 17) {
                originalEr = 2;
            }
            else if (originalDex == 18) {
                originalEr = 3;
            }
            else {
                originalEr = 0;
            }
        }
        else if (pc.isKnight()) {
            if (originalDex == 14 || originalDex == 15) {
                originalEr = 1;
            }
            else if (originalDex == 16) {
                originalEr = 3;
            }
            else {
                originalEr = 0;
            }
        }
        else if (pc.isElf()) {
            originalEr = 0;
        }
        else if (pc.isDarkelf()) {
            if (originalDex >= 16) {
                originalEr = 2;
            }
            else {
                originalEr = 0;
            }
        }
        else if (pc.isWizard()) {
            if (originalDex == 9 || originalDex == 10) {
                originalEr = 1;
            }
            else if (originalDex == 11) {
                originalEr = 2;
            }
            else {
                originalEr = 0;
            }
        }
        else if (pc.isDragonKnight()) {
            if (originalDex == 13 || originalDex == 14) {
                originalEr = 1;
            }
            else if (originalDex >= 15) {
                originalEr = 2;
            }
            else {
                originalEr = 0;
            }
        }
        else if (pc.isIllusionist()) {
            if (originalDex == 12 || originalDex == 13) {
                originalEr = 1;
            }
            else if (originalDex >= 14) {
                originalEr = 2;
            }
            else {
                originalEr = 0;
            }
        }
        return originalEr;
    }
    
    public static short resetOriginalHpr(L1PcInstance pc) {
        int originalCon = pc.getOriginalCon();
        short originalHpr = 0;
        if (pc.isCrown()) {
            if (originalCon == 13 || originalCon == 14) {
                originalHpr = 1;
            }
            else if (originalCon == 15 || originalCon == 16) {
                originalHpr = 2;
            }
            else if (originalCon == 17) {
                originalHpr = 3;
            }
            else if (originalCon == 18) {
                originalHpr = 4;
            }
            else {
                originalHpr = 0;
            }
        }
        else if (pc.isKnight()) {
            if (originalCon == 16 || originalCon == 17) {
                originalHpr = 2;
            }
            else if (originalCon == 18) {
                originalHpr = 4;
            }
            else {
                originalHpr = 0;
            }
        }
        else if (pc.isElf()) {
            if (originalCon == 14 || originalCon == 15) {
                originalHpr = 1;
            }
            else if (originalCon == 16) {
                originalHpr = 2;
            }
            else if (originalCon >= 17) {
                originalHpr = 3;
            }
            else {
                originalHpr = 0;
            }
        }
        else if (pc.isDarkelf()) {
            if (originalCon == 11 || originalCon == 12) {
                originalHpr = 1;
            }
            else if (originalCon >= 13) {
                originalHpr = 2;
            }
            else {
                originalHpr = 0;
            }
        }
        else if (pc.isWizard()) {
            if (originalCon == 17) {
                originalHpr = 1;
            }
            else if (originalCon == 18) {
                originalHpr = 2;
            }
            else {
                originalHpr = 0;
            }
        }
        else if (pc.isDragonKnight()) {
            if (originalCon == 16 || originalCon == 17) {
                originalHpr = 1;
            }
            else if (originalCon == 18) {
                originalHpr = 3;
            }
            else {
                originalHpr = 0;
            }
        }
        else if (pc.isIllusionist()) {
            if (originalCon == 14 || originalCon == 15) {
                originalHpr = 1;
            }
            else if (originalCon >= 16) {
                originalHpr = 2;
            }
            else {
                originalHpr = 0;
            }
        }
        return originalHpr;
    }
    
    public static short resetOriginalMpr(L1PcInstance pc) {
        int originalWis = pc.getOriginalWis();
        short originalMpr = 0;
        if (pc.isCrown()) {
            if (originalWis == 13 || originalWis == 14) {
                originalMpr = 1;
            }
            else if (originalWis >= 15) {
                originalMpr = 2;
            }
            else {
                originalMpr = 0;
            }
        }
        else if (pc.isKnight()) {
            if (originalWis == 11 || originalWis == 12) {
                originalMpr = 1;
            }
            else if (originalWis == 13) {
                originalMpr = 2;
            }
            else {
                originalMpr = 0;
            }
        }
        else if (pc.isElf()) {
            if (originalWis >= 15 && originalWis <= 17) {
                originalMpr = 1;
            }
            else if (originalWis == 18) {
                originalMpr = 2;
            }
            else {
                originalMpr = 0;
            }
        }
        else if (pc.isDarkelf()) {
            if (originalWis >= 13) {
                originalMpr = 1;
            }
            else {
                originalMpr = 0;
            }
        }
        else if (pc.isWizard()) {
            if (originalWis == 14 || originalWis == 15) {
                originalMpr = 1;
            }
            else if (originalWis == 16 || originalWis == 17) {
                originalMpr = 2;
            }
            else if (originalWis == 18) {
                originalMpr = 3;
            }
            else {
                originalMpr = 0;
            }
        }
        else if (pc.isDragonKnight()) {
            if (originalWis == 15 || originalWis == 16) {
                originalMpr = 1;
            }
            else if (originalWis >= 17) {
                originalMpr = 2;
            }
            else {
                originalMpr = 0;
            }
        }
        else if (pc.isIllusionist()) {
            if (originalWis >= 14 && originalWis <= 16) {
                originalMpr = 1;
            }
            else if (originalWis >= 17) {
                originalMpr = 2;
            }
            else {
                originalMpr = 0;
            }
        }
        return originalMpr;
    }
}

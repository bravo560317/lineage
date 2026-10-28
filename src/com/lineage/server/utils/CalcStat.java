package com.lineage.server.utils;

import com.lineage.config.ConfigCharSetting;
import java.util.Random;

public class CalcStat
{
    private static Random rnd;
    
    static {
        CalcStat.rnd = new Random();
    }
    
    public static int calcAc(int level, int dex) {
        int acBonus = 10;
        switch (dex) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: {
                acBonus -= level >> 3;
                break;
            }
            case 10:
            case 11:
            case 12: {
                acBonus -= level / 7;
                break;
            }
            case 13:
            case 14:
            case 15: {
                acBonus -= level / 6;
                break;
            }
            case 16:
            case 17: {
                acBonus -= level / 5;
                break;
            }
            default: {
                acBonus -= level >> 2;
                break;
            }
        }
        return acBonus;
    }
    
    public static int calcStatMr(int wis) {
        int mrBonus = 0;
        if (wis <= 14) {
            mrBonus = 0;
        }
        else if (wis >= 15 && wis <= 16) {
            mrBonus = 3;
        }
        else if (wis == 17) {
            mrBonus = 6;
        }
        else if (wis == 18) {
            mrBonus = 10;
        }
        else if (wis == 19) {
            mrBonus = 15;
        }
        else if (wis == 20) {
            mrBonus = 21;
        }
        else if (wis == 21) {
            mrBonus = 28;
        }
        else if (wis == 22) {
            mrBonus = 37;
        }
        else if (wis == 23) {
            mrBonus = 47;
        }
        else if (wis >= 24 && wis <= 29) {
            mrBonus = 50;
        }
        else if (wis >= 30 && wis <= 34) {
            mrBonus = 52;
        }
        else if (wis >= 35 && wis <= 39) {
            mrBonus = 55;
        }
        else if (wis >= 40 && wis <= 43) {
            mrBonus = 59;
        }
        else if (wis >= 44 && wis <= 46) {
            mrBonus = 62;
        }
        else if (wis >= 47 && wis <= 49) {
            mrBonus = 64;
        }
        else if (wis == 50) {
            mrBonus = 65;
        }
        else {
            mrBonus = 65;
        }
        return mrBonus;
    }
    
    public static int calcDiffMr(int wis, int diff) {
        return calcStatMr(wis + diff) - calcStatMr(wis);
    }
    
    public static short calcStatHp(int charType, int baseMaxHp, int baseCon, int originalHpup) {
        short randomhp = 0;
        if (baseCon > 15 && baseCon <= 30) {
            randomhp = (short)(baseCon - 15);
        }
        else if (baseCon >= 31) {
            randomhp = 15;
        }
        switch (charType) {
            case 0: {
                randomhp += (short)(ConfigCharSetting.PRINCE_HP + CalcStat.rnd.nextInt(2));
                if (baseMaxHp + randomhp > ConfigCharSetting.PRINCE_MAX_HP) {
                    randomhp = (short)(ConfigCharSetting.PRINCE_MAX_HP - baseMaxHp);
                    break;
                }
                else {
                    break;
                }
            }
            case 1: {
                randomhp += (short)(ConfigCharSetting.KNIGHT_HP + CalcStat.rnd.nextInt(2));
                if (baseMaxHp + randomhp > ConfigCharSetting.KNIGHT_MAX_HP) {
                    randomhp = (short)(ConfigCharSetting.KNIGHT_MAX_HP - baseMaxHp);
                    break;
                }
                else {
                    break;
                }
            }
            case 2: {
                randomhp += (short)(ConfigCharSetting.ELF_HP + CalcStat.rnd.nextInt(2));
                if (baseMaxHp + randomhp > ConfigCharSetting.ELF_MAX_HP) {
                    randomhp = (short)(ConfigCharSetting.ELF_MAX_HP - baseMaxHp);
                    break;
                }
                else {
                    break;
                }
            }
            case 3: {
                randomhp += (short)(ConfigCharSetting.WIZARD_HP + CalcStat.rnd.nextInt(2));
                if (baseMaxHp + randomhp > ConfigCharSetting.WIZARD_MAX_HP) {
                    randomhp = (short)(ConfigCharSetting.WIZARD_MAX_HP - baseMaxHp);
                    break;
                }
                else {
                    break;
                }
            }
            case 4: {
                randomhp += (short)(ConfigCharSetting.DARKELF_HP + CalcStat.rnd.nextInt(2));
                if (baseMaxHp + randomhp > ConfigCharSetting.DARKELF_MAX_HP) {
                    randomhp = (short)(ConfigCharSetting.DARKELF_MAX_HP - baseMaxHp);
                    break;
                }
                else {
                    break;
                }
            }
            case 5: {
                randomhp += (short)(ConfigCharSetting.DRAGONKNIGHT_HP + CalcStat.rnd.nextInt(2));
                if (baseMaxHp + randomhp > ConfigCharSetting.DRAGONKNIGHT_MAX_HP) {
                    randomhp = (short)(ConfigCharSetting.DRAGONKNIGHT_MAX_HP - baseMaxHp);
                    break;
                }
                else {
                    break;
                }
            }
            case 6: {
                randomhp += (short)(ConfigCharSetting.ILLUSIONIST_HP + CalcStat.rnd.nextInt(2));
                if (baseMaxHp + randomhp > ConfigCharSetting.ILLUSIONIST_MAX_HP) {
                    randomhp = (short)(ConfigCharSetting.ILLUSIONIST_MAX_HP - baseMaxHp);
                    break;
                }
                else {
                    break;
                }
            }
        }
        randomhp += (short)originalHpup;
        if (randomhp < 0) {
            randomhp = 0;
        }
        return randomhp;
    }
    
    public static short calcStatMp(int charType, int baseMaxMp, int baseWis) {
        int addmp = 0;
        switch (charType) {
            case 0: {
                switch (baseWis) {
                    case 0:
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
                        addmp = CalcStat.rnd.nextInt(2) + 3;
                        break;
                    }
                    case 12:
                    case 13:
                    case 14: {
                        addmp = CalcStat.rnd.nextInt(3) + 3;
                        break;
                    }
                    case 15:
                    case 16:
                    case 17: {
                        addmp = CalcStat.rnd.nextInt(3) + 4;
                        break;
                    }
                    case 18:
                    case 19: {
                        addmp = CalcStat.rnd.nextInt(4) + 4;
                        break;
                    }
                    case 20: {
                        addmp = CalcStat.rnd.nextInt(3) + 5;
                        break;
                    }
                    case 21:
                    case 22:
                    case 23: {
                        addmp = CalcStat.rnd.nextInt(4) + 5;
                        break;
                    }
                    case 24: {
                        addmp = CalcStat.rnd.nextInt(5) + 5;
                        break;
                    }
                    case 25:
                    case 26: {
                        addmp = CalcStat.rnd.nextInt(4) + 6;
                        break;
                    }
                    case 27:
                    case 28:
                    case 29: {
                        addmp = CalcStat.rnd.nextInt(5) + 6;
                        break;
                    }
                    case 30:
                    case 31:
                    case 32: {
                        addmp = CalcStat.rnd.nextInt(5) + 7;
                        break;
                    }
                    case 33:
                    case 34: {
                        addmp = CalcStat.rnd.nextInt(6) + 7;
                        break;
                    }
                    case 35: {
                        addmp = CalcStat.rnd.nextInt(5) + 8;
                        break;
                    }
                    case 36:
                    case 37:
                    case 38: {
                        addmp = CalcStat.rnd.nextInt(6) + 8;
                        break;
                    }
                    case 39: {
                        addmp = CalcStat.rnd.nextInt(7) + 8;
                        break;
                    }
                    case 40:
                    case 41: {
                        addmp = CalcStat.rnd.nextInt(6) + 9;
                        break;
                    }
                    case 42:
                    case 43:
                    case 44: {
                        addmp = CalcStat.rnd.nextInt(7) + 9;
                        break;
                    }
                    case 45: {
                        addmp = CalcStat.rnd.nextInt(7) + 10;
                        break;
                    }
                }
                if (baseWis > 45) {
                    addmp = CalcStat.rnd.nextInt(7) + 10;
                    break;
                }
                else {
                    break;
                }
            }
            case 1: {
                switch (baseWis) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9: {
                        addmp = CalcStat.rnd.nextInt(3);
                        break;
                    }
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14: {
                        addmp = CalcStat.rnd.nextInt(2) + 1;
                        break;
                    }
                    case 15:
                    case 16:
                    case 17: {
                        addmp = CalcStat.rnd.nextInt(2) + 2;
                        break;
                    }
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23: {
                        addmp = CalcStat.rnd.nextInt(3) + 2;
                        break;
                    }
                    case 24: {
                        addmp = CalcStat.rnd.nextInt(4) + 2;
                        break;
                    }
                    case 25:
                    case 26: {
                        addmp = CalcStat.rnd.nextInt(3) + 3;
                        break;
                    }
                    case 27:
                    case 28:
                    case 29: {
                        addmp = CalcStat.rnd.nextInt(4) + 3;
                        break;
                    }
                    case 30:
                    case 31:
                    case 32: {
                        addmp = CalcStat.rnd.nextInt(3) + 4;
                        break;
                    }
                    case 33:
                    case 34:
                    case 35: {
                        addmp = CalcStat.rnd.nextInt(4) + 4;
                        break;
                    }
                    case 36:
                    case 37:
                    case 38:
                    case 39: {
                        addmp = CalcStat.rnd.nextInt(5) + 4;
                        break;
                    }
                    case 40:
                    case 41: {
                        addmp = CalcStat.rnd.nextInt(4) + 5;
                        break;
                    }
                    case 42:
                    case 43:
                    case 44: {
                        addmp = CalcStat.rnd.nextInt(5) + 5;
                        break;
                    }
                    case 45: {
                        addmp = CalcStat.rnd.nextInt(5) + 6;
                        break;
                    }
                }
                if (baseWis > 45) {
                    addmp = CalcStat.rnd.nextInt(5) + 6;
                    break;
                }
                else {
                    break;
                }
            }
            case 2: {
                switch (baseWis) {
                    case 0:
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
                        addmp = CalcStat.rnd.nextInt(4) + 4;
                        break;
                    }
                    case 15:
                    case 16:
                    case 17: {
                        addmp = CalcStat.rnd.nextInt(4) + 5;
                        break;
                    }
                    case 18:
                    case 19: {
                        addmp = CalcStat.rnd.nextInt(6) + 5;
                        break;
                    }
                    case 20: {
                        addmp = CalcStat.rnd.nextInt(4) + 7;
                        break;
                    }
                    case 21:
                    case 22:
                    case 23: {
                        addmp = CalcStat.rnd.nextInt(5) + 7;
                        break;
                    }
                    case 24: {
                        addmp = CalcStat.rnd.nextInt(7) + 7;
                        break;
                    }
                    case 25:
                    case 26: {
                        addmp = CalcStat.rnd.nextInt(6) + 8;
                        break;
                    }
                    case 27:
                    case 28:
                    case 29: {
                        addmp = CalcStat.rnd.nextInt(7) + 8;
                        break;
                    }
                    case 30:
                    case 31:
                    case 32: {
                        addmp = CalcStat.rnd.nextInt(7) + 10;
                        break;
                    }
                    case 33:
                    case 34: {
                        addmp = CalcStat.rnd.nextInt(8) + 10;
                        break;
                    }
                    case 35: {
                        addmp = CalcStat.rnd.nextInt(7) + 11;
                        break;
                    }
                    case 36:
                    case 37:
                    case 38: {
                        addmp = CalcStat.rnd.nextInt(9) + 11;
                        break;
                    }
                    case 39: {
                        addmp = CalcStat.rnd.nextInt(10) + 11;
                        break;
                    }
                    case 40:
                    case 41: {
                        addmp = CalcStat.rnd.nextInt(8) + 13;
                        break;
                    }
                    case 42:
                    case 43:
                    case 44: {
                        addmp = CalcStat.rnd.nextInt(10) + 13;
                        break;
                    }
                    case 45: {
                        addmp = CalcStat.rnd.nextInt(10) + 14;
                        break;
                    }
                }
                if (baseWis > 45) {
                    addmp = CalcStat.rnd.nextInt(10) + 14;
                    break;
                }
                else {
                    break;
                }
            }
            case 3: {
                switch (baseWis) {
                    case 0:
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
                        addmp = CalcStat.rnd.nextInt(5) + 6;
                        break;
                    }
                    case 15:
                    case 16:
                    case 17: {
                        addmp = CalcStat.rnd.nextInt(5) + 8;
                        break;
                    }
                    case 18:
                    case 19: {
                        addmp = CalcStat.rnd.nextInt(7) + 8;
                        break;
                    }
                    case 20: {
                        addmp = CalcStat.rnd.nextInt(5) + 10;
                        break;
                    }
                    case 21:
                    case 22:
                    case 23: {
                        addmp = CalcStat.rnd.nextInt(7) + 10;
                        break;
                    }
                    case 24: {
                        addmp = CalcStat.rnd.nextInt(9) + 10;
                        break;
                    }
                    case 25:
                    case 26: {
                        addmp = CalcStat.rnd.nextInt(7) + 12;
                        break;
                    }
                    case 27:
                    case 28:
                    case 29: {
                        addmp = CalcStat.rnd.nextInt(9) + 12;
                        break;
                    }
                    case 30:
                    case 31:
                    case 32: {
                        addmp = CalcStat.rnd.nextInt(9) + 14;
                        break;
                    }
                    case 33:
                    case 34: {
                        addmp = CalcStat.rnd.nextInt(11) + 14;
                        break;
                    }
                    case 35: {
                        addmp = CalcStat.rnd.nextInt(9) + 16;
                        break;
                    }
                    case 36:
                    case 37:
                    case 38: {
                        addmp = CalcStat.rnd.nextInt(11) + 16;
                        break;
                    }
                    case 39: {
                        addmp = CalcStat.rnd.nextInt(13) + 16;
                        break;
                    }
                    case 40:
                    case 41: {
                        addmp = CalcStat.rnd.nextInt(11) + 18;
                        break;
                    }
                    case 42:
                    case 43:
                    case 44: {
                        addmp = CalcStat.rnd.nextInt(13) + 18;
                        break;
                    }
                    case 45: {
                        addmp = CalcStat.rnd.nextInt(13) + 20;
                        break;
                    }
                }
                if (baseWis > 45) {
                    addmp = CalcStat.rnd.nextInt(13) + 20;
                    break;
                }
                else {
                    break;
                }
            }
            case 4: {
                switch (baseWis) {
                    case 0:
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
                        addmp = CalcStat.rnd.nextInt(2) + 4;
                        break;
                    }
                    case 12:
                    case 13:
                    case 14: {
                        addmp = CalcStat.rnd.nextInt(4) + 4;
                        break;
                    }
                    case 15:
                    case 16:
                    case 17: {
                        addmp = CalcStat.rnd.nextInt(4) + 5;
                        break;
                    }
                    case 18:
                    case 19: {
                        addmp = CalcStat.rnd.nextInt(6) + 5;
                        break;
                    }
                    case 20: {
                        addmp = CalcStat.rnd.nextInt(4) + 7;
                        break;
                    }
                    case 21:
                    case 22:
                    case 23: {
                        addmp = CalcStat.rnd.nextInt(5) + 7;
                        break;
                    }
                    case 24: {
                        addmp = CalcStat.rnd.nextInt(7) + 7;
                        break;
                    }
                    case 25:
                    case 26: {
                        addmp = CalcStat.rnd.nextInt(6) + 8;
                        break;
                    }
                    case 27:
                    case 28:
                    case 29: {
                        addmp = CalcStat.rnd.nextInt(7) + 8;
                        break;
                    }
                    case 30:
                    case 31:
                    case 32: {
                        addmp = CalcStat.rnd.nextInt(7) + 10;
                        break;
                    }
                    case 33:
                    case 34: {
                        addmp = CalcStat.rnd.nextInt(8) + 10;
                        break;
                    }
                    case 35: {
                        addmp = CalcStat.rnd.nextInt(7) + 11;
                        break;
                    }
                    case 36:
                    case 37:
                    case 38: {
                        addmp = CalcStat.rnd.nextInt(9) + 11;
                        break;
                    }
                    case 39: {
                        addmp = CalcStat.rnd.nextInt(10) + 11;
                        break;
                    }
                    case 40:
                    case 41: {
                        addmp = CalcStat.rnd.nextInt(8) + 13;
                        break;
                    }
                    case 42:
                    case 43:
                    case 44: {
                        addmp = CalcStat.rnd.nextInt(10) + 13;
                        break;
                    }
                    case 45: {
                        addmp = CalcStat.rnd.nextInt(10) + 14;
                        break;
                    }
                }
                if (baseWis > 45) {
                    addmp = CalcStat.rnd.nextInt(10) + 14;
                    break;
                }
                else {
                    break;
                }
            }
            case 5: {
                switch (baseWis) {
                    case 0:
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
                        addmp = CalcStat.rnd.nextInt(2) + 2;
                        break;
                    }
                    case 15:
                    case 16:
                    case 17: {
                        addmp = CalcStat.rnd.nextInt(2) + 3;
                        break;
                    }
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23: {
                        addmp = CalcStat.rnd.nextInt(3) + 3;
                        break;
                    }
                    case 24: {
                        addmp = CalcStat.rnd.nextInt(4) + 3;
                        break;
                    }
                    case 25:
                    case 26: {
                        addmp = CalcStat.rnd.nextInt(3) + 4;
                        break;
                    }
                    case 27:
                    case 28:
                    case 29: {
                        addmp = CalcStat.rnd.nextInt(4) + 4;
                        break;
                    }
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35: {
                        addmp = CalcStat.rnd.nextInt(4) + 5;
                        break;
                    }
                    case 36:
                    case 37:
                    case 38: {
                        addmp = CalcStat.rnd.nextInt(5) + 5;
                        break;
                    }
                    case 39: {
                        addmp = CalcStat.rnd.nextInt(6) + 5;
                        break;
                    }
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44: {
                        addmp = CalcStat.rnd.nextInt(5) + 6;
                        break;
                    }
                    case 45: {
                        addmp = CalcStat.rnd.nextInt(5) + 7;
                        break;
                    }
                }
                if (baseWis > 45) {
                    addmp = CalcStat.rnd.nextInt(5) + 7;
                    break;
                }
                else {
                    break;
                }
            }
            case 6: {
                switch (baseWis) {
                    case 0:
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
                        addmp = CalcStat.rnd.nextInt(4) + 4;
                        break;
                    }
                    case 15:
                    case 16:
                    case 17: {
                        addmp = CalcStat.rnd.nextInt(4) + 6;
                        break;
                    }
                    case 18:
                    case 19: {
                        addmp = CalcStat.rnd.nextInt(6) + 6;
                        break;
                    }
                    case 20: {
                        addmp = CalcStat.rnd.nextInt(5) + 7;
                        break;
                    }
                    case 21:
                    case 22:
                    case 23: {
                        addmp = CalcStat.rnd.nextInt(6) + 7;
                        break;
                    }
                    case 24: {
                        addmp = CalcStat.rnd.nextInt(8) + 7;
                        break;
                    }
                    case 25:
                    case 26: {
                        addmp = CalcStat.rnd.nextInt(6) + 9;
                        break;
                    }
                    case 27:
                    case 28:
                    case 29: {
                        addmp = CalcStat.rnd.nextInt(8) + 9;
                        break;
                    }
                    case 30:
                    case 31:
                    case 32: {
                        addmp = CalcStat.rnd.nextInt(8) + 11;
                        break;
                    }
                    case 33:
                    case 34: {
                        addmp = CalcStat.rnd.nextInt(9) + 11;
                        break;
                    }
                    case 35: {
                        addmp = CalcStat.rnd.nextInt(8) + 12;
                        break;
                    }
                    case 36:
                    case 37:
                    case 38: {
                        addmp = CalcStat.rnd.nextInt(10) + 12;
                        break;
                    }
                    case 39: {
                        addmp = CalcStat.rnd.nextInt(12) + 12;
                        break;
                    }
                    case 40:
                    case 41: {
                        addmp = CalcStat.rnd.nextInt(10) + 14;
                        break;
                    }
                    case 42:
                    case 43:
                    case 44: {
                        addmp = CalcStat.rnd.nextInt(11) + 14;
                        break;
                    }
                    case 45: {
                        addmp = CalcStat.rnd.nextInt(11) + 16;
                        break;
                    }
                }
                if (baseWis > 45) {
                    addmp = CalcStat.rnd.nextInt(11) + 16;
                    break;
                }
                else {
                    break;
                }
            }
            case 7: {
                switch (baseWis) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8: {
                        addmp = CalcStat.rnd.nextInt(2);
                        break;
                    }
                    case 9: {
                        addmp = CalcStat.rnd.nextInt(3);
                        break;
                    }
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14: {
                        addmp = CalcStat.rnd.nextInt(2) + 1;
                        break;
                    }
                    case 15:
                    case 16:
                    case 17: {
                        addmp = CalcStat.rnd.nextInt(2) + 2;
                        break;
                    }
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23: {
                        addmp = CalcStat.rnd.nextInt(3) + 2;
                        break;
                    }
                    case 24: {
                        addmp = CalcStat.rnd.nextInt(4) + 2;
                        break;
                    }
                    case 25:
                    case 26: {
                        addmp = CalcStat.rnd.nextInt(3) + 3;
                        break;
                    }
                    case 27:
                    case 28:
                    case 29: {
                        addmp = CalcStat.rnd.nextInt(4) + 3;
                        break;
                    }
                    case 30:
                    case 31:
                    case 32: {
                        addmp = CalcStat.rnd.nextInt(3) + 4;
                        break;
                    }
                    case 33:
                    case 34:
                    case 35: {
                        addmp = CalcStat.rnd.nextInt(4) + 4;
                        break;
                    }
                    case 36:
                    case 37:
                    case 38:
                    case 39: {
                        addmp = CalcStat.rnd.nextInt(5) + 4;
                        break;
                    }
                    case 40:
                    case 41: {
                        addmp = CalcStat.rnd.nextInt(4) + 5;
                        break;
                    }
                    case 42:
                    case 43:
                    case 44: {
                        addmp = CalcStat.rnd.nextInt(5) + 5;
                        break;
                    }
                    case 45: {
                        addmp = CalcStat.rnd.nextInt(5) + 6;
                        break;
                    }
                }
                if (baseWis > 45) {
                    addmp = CalcStat.rnd.nextInt(5) + 6;
                    break;
                }
                else {
                    break;
                }
            }
        }
        if (baseWis <= 0) {
            addmp = CalcStat.rnd.nextInt(2);
        }
        switch (charType) {
            case 0: {
                if (baseMaxMp + addmp > ConfigCharSetting.PRINCE_MAX_MP) {
                    addmp = ConfigCharSetting.PRINCE_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 1: {
                if (baseMaxMp + addmp > ConfigCharSetting.KNIGHT_MAX_MP) {
                    addmp = ConfigCharSetting.KNIGHT_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 2: {
                if (baseMaxMp + addmp > ConfigCharSetting.ELF_MAX_MP) {
                    addmp = ConfigCharSetting.ELF_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 3: {
                if (baseMaxMp + addmp > ConfigCharSetting.WIZARD_MAX_MP) {
                    addmp = ConfigCharSetting.WIZARD_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 4: {
                if (baseMaxMp + addmp > ConfigCharSetting.DARKELF_MAX_MP) {
                    addmp = ConfigCharSetting.DARKELF_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 5: {
                if (baseMaxMp + addmp > ConfigCharSetting.DRAGONKNIGHT_MAX_MP) {
                    addmp = ConfigCharSetting.DRAGONKNIGHT_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 6: {
                if (baseMaxMp + addmp > ConfigCharSetting.ILLUSIONIST_MAX_MP) {
                    addmp = ConfigCharSetting.ILLUSIONIST_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
        }
        return (short)addmp;
    }
    
    public static short calcStatMp(int charType, int baseMaxMp, int baseWis, int originalMpup) {
        int randommp = 0;
        int seedY = 0;
        int seedZ = 0;
        switch (baseWis) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11: {
                seedY = 2;
                break;
            }
            case 9:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17: {
                seedY = 3;
                break;
            }
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
            case 29:
            case 30:
            case 34: {
                seedY = 4;
                break;
            }
            default: {
                seedY = 5;
                break;
            }
        }
        switch (baseWis) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: {
                seedZ = 0;
                break;
            }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14: {
                seedZ = 1;
                break;
            }
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20: {
                seedZ = 2;
                break;
            }
            case 21:
            case 22:
            case 23:
            case 24: {
                seedZ = 3;
                break;
            }
            case 25:
            case 26:
            case 27:
            case 28: {
                seedZ = 4;
                break;
            }
            case 29:
            case 30:
            case 31:
            case 32: {
                seedZ = 5;
                break;
            }
            default: {
                seedZ = 6;
                break;
            }
        }
        randommp = CalcStat.rnd.nextInt(seedY) + 1 + seedZ;
        switch (charType) {
            case 0: {
                if (baseMaxMp + randommp > ConfigCharSetting.PRINCE_MAX_MP) {
                    randommp = ConfigCharSetting.PRINCE_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 1: {
                randommp = randommp * 2 / 3;
                if (baseMaxMp + randommp > ConfigCharSetting.KNIGHT_MAX_MP) {
                    randommp = ConfigCharSetting.KNIGHT_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 2: {
                randommp = (int)((double)randommp * 1.5);
                if (baseMaxMp + randommp > ConfigCharSetting.ELF_MAX_MP) {
                    randommp = ConfigCharSetting.ELF_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 3: {
                randommp *= 2;
                if (baseMaxMp + randommp > ConfigCharSetting.WIZARD_MAX_MP) {
                    randommp = ConfigCharSetting.WIZARD_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 4: {
                randommp = (int)((double)randommp * 1.5);
                if (baseMaxMp + randommp > ConfigCharSetting.DARKELF_MAX_MP) {
                    randommp = ConfigCharSetting.DARKELF_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 5: {
                randommp = randommp * 2 / 3;
                if (baseMaxMp + randommp > ConfigCharSetting.DRAGONKNIGHT_MAX_MP) {
                    randommp = ConfigCharSetting.DRAGONKNIGHT_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
            case 6: {
                randommp = randommp * 5 / 3;
                if (baseMaxMp + randommp > ConfigCharSetting.ILLUSIONIST_MAX_MP) {
                    randommp = ConfigCharSetting.ILLUSIONIST_MAX_MP - baseMaxMp;
                    break;
                }
                else {
                    break;
                }
            }
        }
        randommp += originalMpup;
        if (randommp < 0) {
            randommp = 0;
        }
        return (short)randommp;
    }
}

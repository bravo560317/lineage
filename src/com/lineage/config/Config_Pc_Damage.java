package com.lineage.config;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class Config_Pc_Damage
{
    public static int Crown_ADD_HIT;
    public static int Knight_ADD_HIT;
    public static int Elf_ADD_HIT;
    public static int Darkelf_ADD_HIT;
    public static int Wizard_ADD_HIT;
    public static int DragonKnight_ADD_HIT;
    public static int Illusionist_ADD_HIT;
    public static double Other_To_isCrownnpc;
    public static double Other_To_isKnightnpc;
    public static double Other_To_isWizardnpc;
    public static double Other_To_isElfnpc;
    public static double Other_To_isDarkelfnpc;
    public static double Other_To_isDragonKnightnpc;
    public static double Other_To_isIllusionistnpc;
    public static double Other_To_isCrownpc;
    public static double Other_To_isKnightpc;
    public static double Other_To_isWizardpc;
    public static double Other_To_isElfpc;
    public static double Other_To_isDarkelfpc;
    public static double Other_To_isDragonKnightpc;
    public static double Other_To_isIllusionistpc;
    public static double Crown_ADD_MagicPC;
    public static double Knight_ADD_MagicPC;
    public static double Elf_ADD_MagicPC;
    public static double Darkelf_ADD_MagicPC;
    public static double Wizard_ADD_MagicPC;
    public static double DragonKnight_ADD_MagicPC;
    public static double Illusionist_ADD_MagicPC;
    public static double ModDmg;
    public static double BossDmg;
    public static int AllMobHit_chance;
    public static int BossMobHit_chance;
    private static final String Config_Occupational_Damage = "./config/其他控制端/職業傷害設定表.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/職業傷害設定表.properties"));
            set.load(is);
            is.close();
            Config_Pc_Damage.Crown_ADD_HIT = Integer.parseInt(set.getProperty("Crown_ADD_HIT", "10"));
            Config_Pc_Damage.Knight_ADD_HIT = Integer.parseInt(set.getProperty("Knight_ADD_HIT", "10"));
            Config_Pc_Damage.Elf_ADD_HIT = Integer.parseInt(set.getProperty("Elf_ADD_HIT", "10"));
            Config_Pc_Damage.Darkelf_ADD_HIT = Integer.parseInt(set.getProperty("Darkelf_ADD_HIT", "10"));
            Config_Pc_Damage.Wizard_ADD_HIT = Integer.parseInt(set.getProperty("Wizard_ADD_HIT", "10"));
            Config_Pc_Damage.DragonKnight_ADD_HIT = Integer.parseInt(set.getProperty("DragonKnight_ADD_HIT", "10"));
            Config_Pc_Damage.Illusionist_ADD_HIT = Integer.parseInt(set.getProperty("Illusionist_ADD_HIT", "10"));
            Config_Pc_Damage.AllMobHit_chance = Integer.parseInt(set.getProperty("AllMobHit_chance", "0"));
            Config_Pc_Damage.BossMobHit_chance = Integer.parseInt(set.getProperty("BossMobHit_chance", "0"));
            Config_Pc_Damage.Other_To_isCrownnpc = Double.parseDouble(set.getProperty("Other_To_isCrown_npc", "0.0"));
            Config_Pc_Damage.Other_To_isKnightnpc = Double.parseDouble(set.getProperty("Other_To_isKnight_npc", "0.0"));
            Config_Pc_Damage.Other_To_isWizardnpc = Double.parseDouble(set.getProperty("Other_To_isWizard_npc", "0.0"));
            Config_Pc_Damage.Other_To_isElfnpc = Double.parseDouble(set.getProperty("Other_To_isElf_npc", "0.0"));
            Config_Pc_Damage.Other_To_isDarkelfnpc = Double.parseDouble(set.getProperty("Other_To_isDarkelf_npc", "0.0"));
            Config_Pc_Damage.Other_To_isDragonKnightnpc = Double.parseDouble(set.getProperty("Other_To_isDragonKnight_npc", "0.0"));
            Config_Pc_Damage.Other_To_isIllusionistnpc = Double.parseDouble(set.getProperty("Other_To_isIllusionist_npc", "0.0"));
            Config_Pc_Damage.Other_To_isCrownpc = Double.parseDouble(set.getProperty("Other_To_isCrown_pc", "0.0"));
            Config_Pc_Damage.Other_To_isKnightpc = Double.parseDouble(set.getProperty("Other_To_isKnight_pc", "0.0"));
            Config_Pc_Damage.Other_To_isWizardpc = Double.parseDouble(set.getProperty("Other_To_isWizard_pc", "0.0"));
            Config_Pc_Damage.Other_To_isElfpc = Double.parseDouble(set.getProperty("Other_To_isElf_pc", "0.0"));
            Config_Pc_Damage.Other_To_isDarkelfpc = Double.parseDouble(set.getProperty("Other_To_isDarkelf_pc", "0.0"));
            Config_Pc_Damage.Other_To_isDragonKnightpc = Double.parseDouble(set.getProperty("Other_To_isDragonKnight_pc", "0.0"));
            Config_Pc_Damage.Other_To_isIllusionistpc = Double.parseDouble(set.getProperty("Other_To_isIllusionist_pc", "0.0"));
            Config_Pc_Damage.Crown_ADD_MagicPC = Double.parseDouble(set.getProperty("Other_ADD_isCrown_MagicPC", "1.0"));
            Config_Pc_Damage.Knight_ADD_MagicPC = Double.parseDouble(set.getProperty("Other_ADD_isKnight_MagicPC", "1.0"));
            Config_Pc_Damage.Elf_ADD_MagicPC = Double.parseDouble(set.getProperty("Other_ADD_isElf_MagicPC", "1.0"));
            Config_Pc_Damage.Darkelf_ADD_MagicPC = Double.parseDouble(set.getProperty("Other_ADD_isDarkelf_MagicPC", "1.0"));
            Config_Pc_Damage.Wizard_ADD_MagicPC = Double.parseDouble(set.getProperty("Other_ADD_isWizard_MagicPC", "1.0"));
            Config_Pc_Damage.DragonKnight_ADD_MagicPC = Double.parseDouble(set.getProperty("Other_ADD_isDragonKnight_MagicPC", "1.0"));
            Config_Pc_Damage.Illusionist_ADD_MagicPC = Double.parseDouble(set.getProperty("Other_ADD_isIllusionist_MagicPC", "1.0"));
            Config_Pc_Damage.ModDmg = Double.parseDouble(set.getProperty("ModDmg", "1.0"));
            Config_Pc_Damage.BossDmg = Double.parseDouble(set.getProperty("BossDmg", "1.0"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失:./config/powerKz/各職業傷害設定表.properties");
        }
        finally {
            set.clear();
        }
    }
}

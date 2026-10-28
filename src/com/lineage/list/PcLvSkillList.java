package com.lineage.list;

import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;

public class PcLvSkillList
{
    public static ArrayList<Integer> scount(L1PcInstance pc) {
        ArrayList<Integer> skillList = new ArrayList();
        switch (pc.getType()) {
            case 0: {
                if (pc.getLevel() >= 10) {
                    for (int skillid = 0; skillid < 8; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                }
                if (pc.getLevel() >= 20) {
                    for (int skillid = 8; skillid < 16; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                    break;
                }
                else {
                    break;
                }
            }
            case 1: {
                if (pc.getLevel() >= 50) {
                    for (int skillid = 0; skillid < 8; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                    break;
                }
                else {
                    break;
                }
            }
            case 2: {
                if (pc.getLevel() >= 8) {
                    for (int skillid = 0; skillid < 8; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                }
                if (pc.getLevel() >= 16) {
                    for (int skillid = 8; skillid < 16; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                }
                if (pc.getLevel() >= 24) {
                    for (int skillid = 16; skillid < 23; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                    break;
                }
                else {
                    break;
                }
            }
            case 3: {
                if (pc.getLevel() >= 4) {
                    for (int skillid = 0; skillid < 8; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                }
                if (pc.getLevel() >= 8) {
                    for (int skillid = 8; skillid < 16; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                }
                if (pc.getLevel() >= 12) {
                    for (int skillid = 16; skillid < 23; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                    break;
                }
                else {
                    break;
                }
            }
            case 4: {
                if (pc.getLevel() >= 12) {
                    for (int skillid = 0; skillid < 8; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                }
                if (pc.getLevel() >= 24) {
                    for (int skillid = 8; skillid < 16; ++skillid) {
                        skillList.add(new Integer(skillid));
                    }
                    break;
                }
                else {
                    break;
                }
            }
        }
        return skillList;
    }
    
    public static ArrayList<Integer> isIllusionist(L1PcInstance pc) {
        ArrayList<Integer> skillList = new ArrayList();
        if (pc.getLevel() >= 10) {
            for (int skillid = 200; skillid < 208; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 20) {
            for (int skillid = 208; skillid < 212; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 30) {
            for (int skillid = 212; skillid < 216; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 40) {
            for (int skillid = 216; skillid < 220; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        return skillList;
    }
    
    public static ArrayList<Integer> isDragonKnight(L1PcInstance pc) {
        ArrayList<Integer> skillList = new ArrayList();
        if (pc.getLevel() >= 15) {
            for (int skillid = 180; skillid < 184; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 30) {
            for (int skillid = 184; skillid < 192; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 45) {
            for (int skillid = 192; skillid < 195; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        return skillList;
    }
    
    public static ArrayList<Integer> isDarkelf(L1PcInstance pc) {
        ArrayList<Integer> skillList = new ArrayList();
        if (pc.getLevel() >= 12) {
            for (int skillid = 0; skillid < 8; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 24) {
            for (int skillid = 8; skillid < 16; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 15) {
            for (int skillid = 96; skillid < 100; ++skillid) {
                skillList.add(new Integer(skillid));
            }
            skillList.add(new Integer(108));
        }
        if (pc.getLevel() >= 30) {
            for (int skillid = 100; skillid < 104; ++skillid) {
                skillList.add(new Integer(skillid));
            }
            skillList.add(new Integer(109));
        }
        if (pc.getLevel() >= 45) {
            for (int skillid = 104; skillid < 108; ++skillid) {
                skillList.add(new Integer(skillid));
            }
            skillList.add(new Integer(110));
        }
        return skillList;
    }
    
    public static ArrayList<Integer> isWizard(L1PcInstance pc) {
        ArrayList<Integer> skillList = new ArrayList();
        if (pc.getLevel() >= 4) {
            for (int skillid = 0; skillid < 8; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 8) {
            for (int skillid = 8; skillid < 16; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 12) {
            for (int skillid = 16; skillid < 23; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 16) {
            for (int skillid = 24; skillid < 32; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 20) {
            for (int skillid = 32; skillid < 40; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 24) {
            for (int skillid = 40; skillid < 48; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 28) {
            for (int skillid = 48; skillid < 56; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 32) {
            for (int skillid = 56; skillid < 64; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 36) {
            for (int skillid = 64; skillid < 72; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 40) {
            for (int skillid = 72; skillid < 80; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        return skillList;
    }
    
    public static ArrayList<Integer> isElf(L1PcInstance pc) {
        ArrayList<Integer> skillList = new ArrayList();
        if (pc.getLevel() >= 8) {
            for (int skillid = 0; skillid < 8; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 16) {
            for (int skillid = 8; skillid < 16; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 24) {
            for (int skillid = 16; skillid < 23; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 32) {
            for (int skillid = 24; skillid < 32; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 40) {
            for (int skillid = 32; skillid < 40; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 48) {
            for (int skillid = 40; skillid < 48; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 10) {
            skillList.add(new Integer(128));
            skillList.add(new Integer(129));
            skillList.add(new Integer(130));
        }
        if (pc.getLevel() >= 20) {
            skillList.add(new Integer(136));
            skillList.add(new Integer(137));
        }
        if (pc.getLevel() >= 30) {
            skillList.add(new Integer(131));
            skillList.add(new Integer(144));
            skillList.add(new Integer(145));
            skillList.add(new Integer(146));
            switch (pc.getElfAttr()) {
                case 1: {
                    skillList.add(new Integer(150));
                    skillList.add(new Integer(151));
                    break;
                }
                case 2: {
                    skillList.add(new Integer(147));
                    break;
                }
                case 4: {
                    skillList.add(new Integer(169));
                    break;
                }
                case 8: {
                    skillList.add(new Integer(148));
                    skillList.add(new Integer(149));
                    break;
                }
            }
        }
        if (pc.getLevel() >= 40) {
            skillList.add(new Integer(132));
            skillList.add(new Integer(152));
            skillList.add(new Integer(153));
            switch (pc.getElfAttr()) {
                case 1: {
                    skillList.add(new Integer(156));
                    skillList.add(new Integer(158));
                    break;
                }
                case 2: {
                    skillList.add(new Integer(154));
                    break;
                }
                case 4: {
                    skillList.add(new Integer(157));
                    skillList.add(new Integer(159));
                    break;
                }
                case 8: {
                    skillList.add(new Integer(155));
                    break;
                }
            }
        }
        if (pc.getLevel() >= 50) {
            skillList.add(new Integer(133));
            skillList.add(new Integer(160));
            skillList.add(new Integer(161));
            switch (pc.getElfAttr()) {
                case 1: {
                    skillList.add(new Integer(167));
                    skillList.add(new Integer(168));
                    break;
                }
                case 2: {
                    skillList.add(new Integer(162));
                    skillList.add(new Integer(170));
                    skillList.add(new Integer(174));
                    skillList.add(new Integer(175));
                    break;
                }
                case 4: {
                    skillList.add(new Integer(163));
                    skillList.add(new Integer(164));
                    skillList.add(new Integer(172));
                    break;
                }
                case 8: {
                    skillList.add(new Integer(165));
                    skillList.add(new Integer(166));
                    skillList.add(new Integer(173));
                    break;
                }
            }
        }
        return skillList;
    }
    
    public static ArrayList<Integer> isKnight(L1PcInstance pc) {
        ArrayList<Integer> skillList = new ArrayList();
        if (pc.getLevel() >= 50) {
            for (int skillid = 0; skillid < 8; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 50) {
            skillList.add(new Integer(86));
            skillList.add(new Integer(87));
            skillList.add(new Integer(89));
            skillList.add(new Integer(90));
        }
        if (pc.getLevel() >= 60) {
            skillList.add(new Integer(89));
        }
        return skillList;
    }
    
    public static ArrayList<Integer> isCrown(L1PcInstance pc) {
        ArrayList<Integer> skillList = new ArrayList();
        if (pc.getLevel() >= 10) {
            for (int skillid = 0; skillid < 8; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 20) {
            for (int skillid = 8; skillid < 16; ++skillid) {
                skillList.add(new Integer(skillid));
            }
        }
        if (pc.getLevel() >= 15) {
            skillList.add(new Integer(112));
        }
        if (pc.getLevel() >= 30) {
            skillList.add(new Integer(115));
        }
        if (pc.getLevel() >= 40) {
            skillList.add(new Integer(113));
        }
        if (pc.getLevel() >= 45) {
            skillList.add(new Integer(117));
        }
        if (pc.getLevel() >= 50) {
            skillList.add(new Integer(116));
        }
        if (pc.getLevel() >= 55) {
            skillList.add(new Integer(114));
        }
        return skillList;
    }
}

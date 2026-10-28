package com.lineage.server.model;

import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.william.IntSp;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public abstract class L1MagicMode
{
    private static final Log _log;
    protected int _calcType;
    protected static final int PC_PC = 1;
    protected static final int PC_NPC = 2;
    protected static final int NPC_PC = 3;
    protected static final int NPC_NPC = 4;
    protected L1PcInstance _pc;
    protected L1PcInstance _targetPc;
    protected L1NpcInstance _npc;
    protected L1NpcInstance _targetNpc;
    protected int _leverage;
    protected static final Random _random;
    
    static {
        _log = LogFactory.getLog(L1MagicMode.class);
        _random = new Random();
    }
    
    public L1MagicMode() {
        this._pc = null;
        this._targetPc = null;
        this._npc = null;
        this._targetNpc = null;
        this._leverage = 10;
    }
    
    protected static boolean dmg0(L1Character character) {
        try {
            if (character == null) {
                return false;
            }
            else if (character.getSkillisEmpty()) {
                return false;
            }
            else if (character.getSkillEffect().size() <= 0) {
                return false;
            }
            else {
                for (Integer key : character.getSkillEffect()) {
                    Integer integer = L1AttackList.SKM0.get(key);
                    if (integer != null) {
                        return true;
                    }
                }
            }
        }
        catch (ConcurrentModificationException ex) {}
        catch (Exception e) {
            L1MagicMode._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return false;
    }
    
    protected static double getDamageUpByClan(L1PcInstance pc) {
        double dmg = 0.0;
        try {
            if (pc == null) {
                return 0.0;
            }
            else {
                dmg += (double)pc.get_magic_modifier_dmg();
                L1Clan clan = pc.getClan();
                if (clan == null) {
                    return dmg;
                }
                else if (clan.isClanskill() && pc.get_other().get_clanskill() == 4) {
                    int clanMan = clan.getOnlineClanMemberSize();
                    dmg += 0.25 * (double)clanMan;
                }
            }
        }
        catch (Exception e) {
            L1MagicMode._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
        return dmg;
    }
    
    protected static double getDamageReductionByClan(L1PcInstance targetPc) {
        double dmg = 0.0;
        try {
            if (targetPc == null) {
                return 0.0;
            }
            else {
                dmg += (double)targetPc.get_magic_reduction_dmg();
                L1Clan clan = targetPc.getClan();
                if (clan == null) {
                    return 0.0;
                }
                else if (clan.isClanskill() && targetPc.get_other().get_clanskill() == 8) {
                    int clanMan = clan.getOnlineClanMemberSize();
                    dmg += 0.25 * (double)clanMan;
                }
            }
        }
        catch (Exception e) {
            L1MagicMode._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
        return dmg;
    }
    
    public void setLeverage(int i) {
        this._leverage = i;
    }
    
    protected int getLeverage() {
        return this._leverage;
    }
    
    protected int getTargetSp() {
        int sp = 0;
        switch (this._calcType) {
            case 1:
            case 2: {
                if (this._pc != null) {
                    sp = this._pc.getSp() - this._pc.getTrueSp();
                    switch (this._pc.guardianEncounter()) {
                        case 3: {
                            ++sp;
                            break;
                        }
                        case 4: {
                            sp += 2;
                            break;
                        }
                        case 5: {
                            sp += 3;
                            break;
                        }
                    }
                }
            }
            case 3:
            case 4: {
                if (this._npc != null) {
                    sp = this._npc.getSp() - this._npc.getTrueSp();
                }
                break;
            }
        }
        return sp;
    }

    
    protected int getTargetMr() {
        int mr = 0;
        switch (this._calcType) {
            case 1: {
                if (this._targetPc == null) {
                    return 0;
                }
                else {
                    mr = this._targetPc.getMr();
                    int Mgs = 0;
                    int othersp = IntSp.getIntSpSkillMgs2(this._pc, (int)this._pc.getInt());
                    if (othersp != 0) {
                        Mgs += othersp;
                        mr -= Mgs;
                        if (mr < 0) {
                            mr = 0;
                        }
                        if (this._pc.isGm()) {
                            this._pc.sendPackets(new S_ServerMessage("\\fUGM顯示對抗抗魔減少+" + Mgs));
                            this._pc.sendPackets(new S_ServerMessage("\\fUGM顯示對方抗+" + mr));
                        }
                    }
                    switch (this._targetPc.guardianEncounter()) {
                        case 0: {
                            mr += 3;
                            break;
                        }
                        case 1: {
                            mr += 6;
                            break;
                        }
                        case 2: {
                            mr += 9;
                            break;
                        }
                    }
                    break;
                }
            }
            case 2:
            case 4: {
                if (this._targetNpc == null) {
                    return 0;
                }
                else {
                    mr = this._targetNpc.getMr();
                    break;
                }
            }
            case 3: {
                if (this._targetPc == null) {
                    return 0;
                }
                else {
                    mr = this._targetPc.getMr();
                    switch (this._targetPc.guardianEncounter()) {
                        case 0: {
                            mr += 3;
                            break;
                        }
                        case 1: {
                            mr += 6;
                            break;
                        }
                        case 2: {
                            mr += 9;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return mr;
    }
    
    protected boolean calcEvasion() {
        if (this._targetPc == null) {
            return false;
        }
        else {
            int ev = this._targetPc.get_evasion();
            if (ev == 0) {
                return false;
            }
            else {
                int rnd = L1MagicMode._random.nextInt(1000) + 1;
                if (rnd <= ev) {
                    if (!this._targetPc.getDolls().isEmpty()) {
                        for (L1DollInstance doll : this._targetPc.getDolls().values()) {
                            doll.show_action(2);
                        }
                    }
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }
    
    protected double calcAttrResistance(int attr) {
        int resist = 0;
        switch (this._calcType) {
            case 1:
            case 3: {
                if (this._targetPc == null) {
                    return 0.0;
                }
                else
                    Label_0133: {
                        switch (attr) {
                            case 1: {
                                resist = this._targetPc.getEarth();
                                break Label_0133;
                            }
                            case 2: {
                                resist = this._targetPc.getFire();
                                break Label_0133;
                            }
                            case 4: {
                                resist = this._targetPc.getWater();
                                break Label_0133;
                            }
                            case 8: {
                                resist = this._targetPc.getWind();
                                break Label_0133;
                            }
                        }
                    }
            }
            case 2:
            case 4: {
                if (this._targetNpc == null) {
                    return 0.0;
                }
                else {
                    switch (attr) {
                        case 1: {
                            resist = this._targetNpc.getEarth();
                            break;
                        }
                        case 2: {
                            resist = this._targetNpc.getFire();
                            break;
                        }
                        case 4: {
                            resist = this._targetNpc.getWater();
                            break;
                        }
                        case 8: {
                            resist = this._targetNpc.getWind();
                            break;
                        }
                    }
                }
            }
        }
        int resistFloor = (int)(0.16 * (double)Math.abs(resist));
        if (resist >= 0) {
            resistFloor *= 1;
        }
        else {
            resistFloor *= -1;
        }
        double attrDeffence = (double)resistFloor / 32.0;
        return attrDeffence;
    }
    
    public abstract boolean calcProbabilityMagic(int p0);
    
    public abstract int calcMagicDamage(int p0);
    
    public abstract int calcHealing(int p0);
    
    public abstract void commit(int p0, int p1);
}

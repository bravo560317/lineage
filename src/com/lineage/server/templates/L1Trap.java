package com.lineage.server.templates;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.model.poison.L1ParalysisPoison;
import com.lineage.server.model.poison.L1SilencePoison;
import com.lineage.server.model.poison.L1DamagePoison;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.utils.L1SpawnUtil;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.L1Object;
import java.sql.ResultSet;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Location;
import com.lineage.server.utils.Dice;
import org.apache.commons.logging.Log;

public class L1Trap
{
    private static final Log _log;
    private String _type;
    private int _trap;
    private int _id;
    private int _gfxId;
    private boolean _isDetectionable;
    private Dice _dice;
    private int _base;
    private int _diceCount;
    private int _npcId;
    private int _count;
    private int _poisonType;
    private int _delay;
    private int _time;
    private int _damage;
    private int _skillId;
    private int _skillTimeSeconds;
    private L1Location _loc;
    
    static {
        _log = LogFactory.getLog(L1Trap.class);
    }
    
    public L1Trap(ResultSet rs) {
        try {
            this._id = rs.getInt("id");
            this._gfxId = rs.getInt("gfxId");
            this._isDetectionable = rs.getBoolean("isDetectionable");
            this._type = rs.getString("type");
            if (this._type.equalsIgnoreCase("L1DamageTrap")) {
                this._trap = 1;
                this._dice = new Dice(rs.getInt("dice"));
                this._base = rs.getInt("base");
                this._diceCount = rs.getInt("diceCount");
            }
            else if (this._type.equalsIgnoreCase("L1HealingTrap")) {
                this._trap = 2;
                this._dice = new Dice(rs.getInt("dice"));
                this._base = rs.getInt("base");
                this._diceCount = rs.getInt("diceCount");
            }
            else if (this._type.equalsIgnoreCase("L1MonsterTrap")) {
                this._trap = 3;
                this._npcId = rs.getInt("monsterNpcId");
                this._count = rs.getInt("monsterCount");
            }
            else if (this._type.equalsIgnoreCase("L1PoisonTrap")) {
                this._trap = 4;
                String poisonType = rs.getString("poisonType");
                if (poisonType.equalsIgnoreCase("d")) {
                    this._poisonType = 1;
                }
                else if (poisonType.equalsIgnoreCase("s")) {
                    this._poisonType = 2;
                }
                else if (poisonType.equalsIgnoreCase("p")) {
                    this._poisonType = 3;
                }
                this._delay = rs.getInt("poisonDelay");
                this._time = rs.getInt("poisonTime");
                this._damage = rs.getInt("poisonDamage");
            }
            else if (this._type.equalsIgnoreCase("L1SkillTrap")) {
                this._trap = 5;
                this._skillId = rs.getInt("skillId");
                this._skillTimeSeconds = rs.getInt("skillTimeSeconds");
            }
            else if (this._type.equalsIgnoreCase("L1TeleportTrap")) {
                this._trap = 6;
                int x = rs.getInt("teleportX");
                int y = rs.getInt("teleportY");
                int mapId = rs.getInt("teleportMapId");
                this._loc = new L1Location(x, y, mapId);
            }
        }
        catch (Exception e) {
            L1Trap._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public String getType() {
        return this._type + "(" + this._trap + "-" + this._id + ")";
    }
    
    public int getId() {
        return this._id;
    }
    
    public int getGfxId() {
        return this._gfxId;
    }
    
    private void sendEffect(L1Object trapObj) {
        try {
            if (this.getGfxId() == 0) {
                return;
            }
            else {
                S_EffectLocation effect = new S_EffectLocation(trapObj.getLocation(), this.getGfxId());
                for (L1PcInstance pc : World.get().getRecognizePlayer(trapObj)) {
                    pc.sendPackets(effect);
                }
            }
        }
        catch (Exception e) {
            L1Trap._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void onTrod(L1PcInstance trodFrom, L1Object trapObj) {
        switch (this._trap) {
            case 0: {
                L1Trap._log.error("陷阱的處理未定義: " + this._id);
                break;
            }
            case 1: {
                this.onType1(trodFrom, trapObj);
                break;
            }
            case 2: {
                this.onType2(trodFrom, trapObj);
                break;
            }
            case 3: {
                this.onType3(trodFrom, trapObj);
                break;
            }
            case 4: {
                this.onType4(trodFrom, trapObj);
                break;
            }
            case 5: {
                this.onType5(trodFrom, trapObj);
                break;
            }
            case 6: {
                this.onType6(trodFrom, trapObj);
                break;
            }
        }
    }
    
    public void onDetection(L1PcInstance caster, L1Object trapObj) {
        if (this._isDetectionable) {
            this.sendEffect(trapObj);
        }
    }
    
    private void onType1(L1PcInstance trodFrom, L1Object trapObj) {
        try {
            if (this._trap != 1) {
                return;
            }
            else if (this._base <= 0) {
                return;
            }
            else {
                this.sendEffect(trapObj);
                int dmg = this._dice.roll(this._diceCount) + this._base;
                trodFrom.receiveDamage(trodFrom, (double)dmg, false, true);
            }
        }
        catch (Exception e) {
            L1Trap._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void onType2(L1PcInstance trodFrom, L1Object trapObj) {
        try {
            if (this._trap != 2) {
                return;
            }
            else if (this._base <= 0) {
                return;
            }
            else {
                this.sendEffect(trapObj);
                int pt = this._dice.roll(this._diceCount) + this._base;
                trodFrom.healHp(pt);
            }
        }
        catch (Exception e) {
            L1Trap._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void onType3(L1PcInstance trodFrom, L1Object trapObj) {
        try {
            if (this._trap != 3) {
                return;
            }
            else if (this._npcId <= 0) {
                return;
            }
            else {
                this.sendEffect(trapObj);
                for (int i = 0; i < this._count; ++i) {
                    L1Location loc = trodFrom.getLocation().randomLocation(5, false);
                    L1NpcInstance newNpc = L1SpawnUtil.spawn(this._npcId, loc);
                    newNpc.setLink(trodFrom);
                }
            }
        }
        catch (Exception e) {
            L1Trap._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void onType4(L1PcInstance trodFrom, L1Object trapObj) {
        try {
            if (this._trap != 4) {
                return;
            }
            else {
                this.sendEffect(trapObj);
                switch (this._poisonType) {
                    case 1: {
                        L1DamagePoison.doInfection(trodFrom, trodFrom, this._time, this._damage);
                        break;
                    }
                    case 2: {
                        L1SilencePoison.doInfection(trodFrom);
                        break;
                    }
                    case 3: {
                        L1ParalysisPoison.doInfection(trodFrom, this._delay, this._time);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            L1Trap._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void onType5(L1PcInstance trodFrom, L1Object trapObj) {
        try {
            if (this._trap != 5) {
                return;
            }
            else if (this._skillId <= 0) {
                return;
            }
            else {
                this.sendEffect(trapObj);
                new L1SkillUse().handleCommands(trodFrom, this._skillId, trodFrom.getId(), trodFrom.getX(), trodFrom.getY(), this._skillTimeSeconds, 4);
            }
        }
        catch (Exception e) {
            L1Trap._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void onType6(L1PcInstance trodFrom, L1Object trapObj) {
        try {
            if (this._trap != 6) {
                return;
            }
            else if (this._loc == null) {
                return;
            }
            else {
                this.sendEffect(trapObj);
                L1Teleport.teleport(trodFrom, this._loc.getX(), this._loc.getY(), (short)this._loc.getMapId(), 5, true, 2);
            }
        }
        catch (Exception e) {
            L1Trap._log.error(e.getLocalizedMessage(), e);
        }
    }
}

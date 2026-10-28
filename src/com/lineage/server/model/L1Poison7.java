package com.lineage.server.model;

import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.logging.Logger;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1TowerInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1Poison7
{
    private L1Object _l1object;
    private L1PcInstance _player;
    private L1MonsterInstance _mob;
    private L1PetInstance _pet;
    private L1SummonInstance _summon;
    private L1TowerInstance _tower;
    private L1NpcInstance _npc;
    private int _targetId;
    private int _time;
    private int _damage;
    private boolean _isPoisonTimerDelete;
    private boolean _isParalysisPoisonTimerDelete;
    private L1Poison7.NormalPoisonTimer _normal_poison_timer;
    private L1Poison7.ParalysisPoisonTimer _paralysis_poison_timer;
    private L1Poison7.ParalysisTimer _paralysis_timer;
    private static final Logger _log;
    private static L1Poison7 _instance;
    
    static {
        _log = Logger.getLogger(L1Poison7.class.getName());
    }
    
    public L1Poison7() {
        this._l1object = null;
        this._player = null;
        this._mob = null;
        this._pet = null;
        this._summon = null;
        this._tower = null;
        this._npc = null;
        this._targetId = 0;
        this._isPoisonTimerDelete = false;
        this._isParalysisPoisonTimerDelete = false;
    }
    
    public static L1Poison7 getInstance() {
        if (L1Poison7._instance == null) {
            L1Poison7._instance = new L1Poison7();
        }
        return L1Poison7._instance;
    }
    
    public boolean handleCommands(L1Object l1object, int type, int time, int damage) {
        if (l1object == null) {
            return false;
        }
        else {
            if (l1object instanceof L1PcInstance) {
                this._player = (L1PcInstance)l1object;
                this._targetId = this._player.getId();
                if (this._player.get_poisonStatus7() != 0) {
                    return false;
                }
                else {
                    this._player.set_poisonStatus7(type);
                }
            }
            else if (l1object instanceof L1MonsterInstance) {
                this._mob = (L1MonsterInstance)l1object;
                this._targetId = this._mob.getId();
                if (this._mob.get_poisonStatus7() != 0) {
                    return false;
                }
                else {
                    this._mob.set_poisonStatus7(type);
                }
            }
            else if (l1object instanceof L1PetInstance) {
                this._pet = (L1PetInstance)l1object;
                this._targetId = this._pet.getId();
                if (this._pet.get_poisonStatus7() != 0) {
                    return false;
                }
                else {
                    this._pet.set_poisonStatus7(type);
                }
            }
            else if (l1object instanceof L1SummonInstance) {
                this._summon = (L1SummonInstance)l1object;
                this._targetId = this._summon.getId();
                if (this._summon.get_poisonStatus7() != 0) {
                    return false;
                }
                else {
                    this._summon.set_poisonStatus7(type);
                }
            }
            else if (l1object instanceof L1TowerInstance) {
                type = 1;
                this._tower = (L1TowerInstance)l1object;
                this._targetId = this._tower.getId();
                this._tower.set_poisonStatus7(type);
                return true;
            }
            else {
                type = 1;
                this._npc = (L1NpcInstance)l1object;
                this._targetId = this._npc.getId();
                this._npc.set_poisonStatus7(type);
                return true;
            }
            if (type == 1) {
                this._normal_poison_timer = new L1Poison7.NormalPoisonTimer(l1object, time, damage);
                GeneralThreadPool.get().execute(this._normal_poison_timer);
            }
            else if (type == 4) {
                this._paralysis_poison_timer = new L1Poison7.ParalysisPoisonTimer(l1object, time);
                GeneralThreadPool.get().execute(this._paralysis_poison_timer);
            }
            return true;
        }
    }
    
    public void CurePoison(L1Object l1object) {
        if (l1object instanceof L1PcInstance) {
            this._player = (L1PcInstance)l1object;
            this._targetId = this._player.getId();
            if (this._player.get_poisonStatus7() == 1) {
                this._isPoisonTimerDelete = true;
            }
            else if (this._player.get_poisonStatus7() == 4) {
                this._isParalysisPoisonTimerDelete = true;
            }
            else {
                return;
            }
            this._player.set_poisonStatus7(0);
        }
        else if (l1object instanceof L1MonsterInstance) {
            this._mob = (L1MonsterInstance)l1object;
            this._targetId = this._mob.getId();
            if (this._mob.get_poisonStatus7() == 1) {
                this._isPoisonTimerDelete = true;
            }
            else if (this._mob.get_poisonStatus7() == 4) {
                this._isParalysisPoisonTimerDelete = true;
            }
            else {
                return;
            }
            this._mob.set_poisonStatus7(0);
        }
        else if (l1object instanceof L1PetInstance) {
            this._pet = (L1PetInstance)l1object;
            this._targetId = this._pet.getId();
            if (this._pet.get_poisonStatus7() == 1) {
                this._isPoisonTimerDelete = true;
            }
            else if (this._pet.get_poisonStatus7() == 4) {
                this._isParalysisPoisonTimerDelete = true;
            }
            else {
                return;
            }
            this._pet.set_poisonStatus7(0);
        }
        else if (l1object instanceof L1SummonInstance) {
            this._summon = (L1SummonInstance)l1object;
            this._targetId = this._summon.getId();
            if (this._summon.get_poisonStatus7() == 1) {
                this._isPoisonTimerDelete = true;
            }
            else if (this._summon.get_poisonStatus7() == 4) {
                this._isParalysisPoisonTimerDelete = true;
            }
            else {
                return;
            }
            this._summon.set_poisonStatus7(0);
        }
        else if (l1object instanceof L1TowerInstance) {
            this._tower = (L1TowerInstance)l1object;
            this._targetId = this._tower.getId();
            this._tower.set_poisonStatus7(0);
        }
        else {
            this._npc = (L1NpcInstance)l1object;
            this._targetId = this._npc.getId();
            this._npc.set_poisonStatus7(0);
        }
    }
    
    /*
    static void access$0(L1Poison7 l1Poison7, L1Object l1object) {
        l1Poison7._l1object = l1object;
    }
    */
    
    /*
    static void access$1(L1Poison7 l1Poison7, int time) {
        l1Poison7._time = time;
    }
    */
    
    /*
    static void access$2(L1Poison7 l1Poison7, int damage) {
        l1Poison7._damage = damage;
    }
    */
    
    /*
    static void access$6(L1Poison7 l1Poison7, L1PcInstance player) {
        l1Poison7._player = player;
    }
    */
    
    /*
    static void access$9(L1Poison7 l1Poison7, L1MonsterInstance mob) {
        l1Poison7._mob = mob;
    }
    */
    
    /*
    static void access$11(L1Poison7 l1Poison7, L1PetInstance pet) {
        l1Poison7._pet = pet;
    }
    */
    
    /*
    static void access$13(L1Poison7 l1Poison7, L1SummonInstance summon) {
        l1Poison7._summon = summon;
    }
    */
    
    /*
    static void access$16(L1Poison7 l1Poison7, int targetId) {
        l1Poison7._targetId = targetId;
    }
    */
    
    /*
    static void access$17(L1Poison7 l1Poison7, L1Poison7.ParalysisTimer paralysis_timer) {
        l1Poison7._paralysis_timer = paralysis_timer;
    }
    */
    
    class NormalPoisonTimer extends Thread
    {
        public NormalPoisonTimer(L1Object l1object, int time, int damage) {
            L1Poison7.this._l1object = l1object;
            L1Poison7.this._time = time;
            L1Poison7.this._damage = damage;
        }
        
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep((long)L1Poison7.this._time);
                }
                catch (Exception exception) {
                    break;
                }
                if (L1Poison7.this._isPoisonTimerDelete) {
                    return;
                }
                else if (L1Poison7.this._l1object instanceof L1PcInstance) {
                    L1Poison7.this._player = (L1PcInstance)L1Poison7.this._l1object;
                    L1Poison7.this._player.receiveDamage((L1Character)L1Poison7.this._l1object, (double)L1Poison7.this._damage, L1Poison7.this._damage);
                    if (L1Poison7.this._player.isDead()) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
                else if (L1Poison7.this._l1object instanceof L1MonsterInstance) {
                    L1Poison7.this._mob = (L1MonsterInstance)L1Poison7.this._l1object;
                    L1Poison7.this._mob.receiveDamage((L1Character)L1Poison7.this._l1object, L1Poison7.this._damage);
                    if (L1Poison7.this._mob.isDead()) {
                        return;
                    }
                    else {
                        continue;
                    }
                }
                else if (L1Poison7.this._l1object instanceof L1PetInstance) {
                    L1Poison7.this._pet = (L1PetInstance)L1Poison7.this._l1object;
                    L1Poison7.this._pet.receiveDamage((L1Character)L1Poison7.this._l1object, L1Poison7.this._damage);
                    if (L1Poison7.this._pet.isDead()) {
                        return;
                    }
                    else {
                        continue;
                    }
                }
                else if (L1Poison7.this._l1object instanceof L1SummonInstance) {
                    L1Poison7.this._summon = (L1SummonInstance)L1Poison7.this._l1object;
                    L1Poison7.this._summon.receiveDamage((L1Character)L1Poison7.this._l1object, L1Poison7.this._damage);
                    if (L1Poison7.this._summon.isDead()) {
                        return;
                    }
                    else {
                        continue;
                    }
                }
                else {
                    continue;
                }
            }
            L1Poison7.this.CurePoison(L1Poison7.this._l1object);
        }
    }
    
    class ParalysisPoisonTimer extends Thread
    {
        public ParalysisPoisonTimer(L1Object l1object, int time) {
            L1Poison7.this._l1object = l1object;
            L1Poison7.this._time = time;
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep(10L);
            }
            catch (Exception ex) {}
            if (L1Poison7.this._isParalysisPoisonTimerDelete) {
                return;
            }
            else {
                if (L1Poison7.this._l1object instanceof L1PcInstance) {
                    L1Poison7.this._player = (L1PcInstance)L1Poison7.this._l1object;
                    if (!L1Poison7.this._player.isDead()) {
                        L1Poison7.this._targetId = L1Poison7.this._player.getId();
                        L1Poison7.this._player.sendPackets(new S_Paralysis(4, true, 0));
                        L1Poison7.this._paralysis_timer = new L1Poison7.ParalysisTimer(L1Poison7.this._l1object, L1Poison7.this._time);
                        GeneralThreadPool.get().execute(L1Poison7.this._paralysis_timer);
                    }
                }
                if (L1Poison7.this._l1object instanceof L1MonsterInstance) {
                    L1Poison7.this._mob = (L1MonsterInstance)L1Poison7.this._l1object;
                    if (!L1Poison7.this._mob.isDead()) {
                        L1Poison7.this._targetId = L1Poison7.this._mob.getId();
                        L1Poison7.this._paralysis_timer = new L1Poison7.ParalysisTimer(L1Poison7.this._l1object, L1Poison7.this._time);
                        GeneralThreadPool.get().execute(L1Poison7.this._paralysis_timer);
                    }
                }
                if (L1Poison7.this._l1object instanceof L1PetInstance) {
                    L1Poison7.this._pet = (L1PetInstance)L1Poison7.this._l1object;
                    if (!L1Poison7.this._pet.isDead()) {
                        L1Poison7.this._targetId = L1Poison7.this._pet.getId();
                        L1Poison7.this._pet.setParalyzed(true);
                        L1Poison7.this._paralysis_timer = new L1Poison7.ParalysisTimer(L1Poison7.this._l1object, L1Poison7.this._time);
                        GeneralThreadPool.get().execute(L1Poison7.this._paralysis_timer);
                    }
                }
                if (L1Poison7.this._l1object instanceof L1SummonInstance) {
                    L1Poison7.this._summon = (L1SummonInstance)L1Poison7.this._l1object;
                    if (!L1Poison7.this._summon.isDead()) {
                        L1Poison7.this._targetId = L1Poison7.this._summon.getId();
                        L1Poison7.this._summon.setParalyzed(true);
                        L1Poison7.this._paralysis_timer = new L1Poison7.ParalysisTimer(L1Poison7.this._l1object, L1Poison7.this._time);
                        GeneralThreadPool.get().execute(L1Poison7.this._paralysis_timer);
                    }
                }
                return;
            }
        }
    }
    
    class ParalysisTimer extends Thread
    {
        public ParalysisTimer(L1Object l1object, int time) {
            L1Poison7.this._l1object = l1object;
            L1Poison7.this._time = time;
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep((long)L1Poison7.this._time);
            }
            catch (Exception ex) {}
            if (L1Poison7.this._l1object instanceof L1PcInstance) {
                L1Poison7.this._player = (L1PcInstance)L1Poison7.this._l1object;
                if (!L1Poison7.this._player.isDead()) {
                    L1Poison7.this._player.sendPackets(new S_Paralysis(4, false, 0));
                    L1Poison7.this.CurePoison(L1Poison7.this._l1object);
                }
            }
            if (L1Poison7.this._l1object instanceof L1MonsterInstance) {
                L1Poison7.this._mob = (L1MonsterInstance)L1Poison7.this._l1object;
                if (!L1Poison7.this._mob.isDead()) {
                    L1Poison7.this.CurePoison(L1Poison7.this._l1object);
                }
            }
            if (L1Poison7.this._l1object instanceof L1PetInstance) {
                L1Poison7.this._pet = (L1PetInstance)L1Poison7.this._l1object;
                if (!L1Poison7.this._pet.isDead()) {
                    L1Poison7.this._pet.setParalyzed(false);
                    L1Poison7.this.CurePoison(L1Poison7.this._l1object);
                }
            }
            if (L1Poison7.this._l1object instanceof L1SummonInstance) {
                L1Poison7.this._summon = (L1SummonInstance)L1Poison7.this._l1object;
                if (!L1Poison7.this._summon.isDead()) {
                    L1Poison7.this._summon.setParalyzed(false);
                    L1Poison7.this.CurePoison(L1Poison7.this._l1object);
                }
            }
        }
    }
}

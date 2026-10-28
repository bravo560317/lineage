package com.lineage.server.model.Instance;

import java.util.ArrayList;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Character;
import com.lineage.server.serverpackets.S_UseArrowSkill;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1BowInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private int _bowid;
    private int _time;
    private int _dmg;
    private int _out_x;
    private int _out_y;
    private boolean _start;
    
    static {
        _log = LogFactory.getLog(L1BowInstance.class);
    }
    
    public L1BowInstance(L1Npc template) {
        super(template);
        this._bowid = 66;
        this._time = 1000;
        this._dmg = 15;
        this._out_x = 0;
        this._out_y = 0;
        this._start = true;
    }
    
    public void set_info(int bowid, int h, int dmg, int time) {
        this._bowid = bowid;
        this._dmg = dmg;
        this._time = time;
    }
    
    public int get_dmg() {
        return this._dmg;
    }
    
    public void set_dmg(int dmg) {
        this._dmg = dmg;
    }
    
    public int get_time() {
        return this._time;
    }
    
    public void set_time(int time) {
        this._time = time;
    }
    
    public int get_bowid() {
        return this._bowid;
    }
    
    public void set_bowid(int bowid) {
        this._bowid = bowid;
    }
    
    public boolean get_start() {
        return this._start;
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (this._out_x == 0 && this._out_y == 0) {
                this.set_atkLoc();
            }
            if (!this._start) {
                this._start = true;
            }
        }
        catch (Exception e) {
            L1BowInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void deleteMe() {
        try {
            this._destroyed = true;
            World.get().removeVisibleObject(this);
            World.get().removeObject(this);
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                pc.removeKnownObject(this);
                pc.sendPackets(new S_RemoveObject(this));
            }
            this.removeAllKnownObjects();
        }
        catch (Exception e) {
            L1BowInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void atkTrag() {
        try {
            int out_x = this._out_x;
            int out_y = this._out_y;
            int tgid = 0;
            L1Character tg = this.checkTg();
            if (tg != null) {
                tgid = tg.getId();
                switch (this.getHeading()) {
                    case 0: {
                        out_y = tg.getY();
                        break;
                    }
                    case 2: {
                        out_x = tg.getX();
                        break;
                    }
                    case 4: {
                        out_y = tg.getY();
                        break;
                    }
                    case 6: {
                        out_x = tg.getX();
                        break;
                    }
                }
                if (tg instanceof L1PcInstance) {
                    L1PcInstance trag = (L1PcInstance)tg;
                    trag.receiveDamage(null, (double)this._dmg, false, true);
                }
                else if (tg instanceof L1PetInstance) {
                    L1PetInstance trag2 = (L1PetInstance)tg;
                    trag2.receiveDamage(null, this._dmg);
                }
                else if (tg instanceof L1SummonInstance) {
                    L1SummonInstance trag3 = (L1SummonInstance)tg;
                    trag3.receiveDamage(null, this._dmg);
                }
                else if (tg instanceof L1MonsterInstance) {
                    L1MonsterInstance trag4 = (L1MonsterInstance)tg;
                    trag4.receiveDamage(null, this._dmg);
                }
            }
            this.broadcastPacketAll(new S_UseArrowSkill(this, tgid, this._bowid, out_x, out_y, this._dmg, 1));
        }
        catch (Exception e) {
            L1BowInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean checkPc() {
        try {
            if (World.get().getRecognizePlayer(this).size() <= 0) {
                return this._start = false;
            }
        }
        catch (Exception e) {
            L1BowInstance._log.error(e.getLocalizedMessage(), e);
        }
        return true;
    }
    
    private L1Character checkTg() {
        ArrayList<L1Object> tgs = World.get().getVisibleObjects(this, -1);
        for (L1Object object : tgs) {
            if (object instanceof L1Character) {
                L1Character cha = (L1Character)object;
                boolean isCheck = false;
                if (cha instanceof L1PcInstance) {
                    isCheck = true;
                }
                else if (cha instanceof L1PetInstance) {
                    isCheck = true;
                }
                else if (cha instanceof L1SummonInstance) {
                    isCheck = true;
                }
                else if (cha instanceof L1MonsterInstance) {
                    isCheck = true;
                }
                if (isCheck) {
                    switch (this.getHeading()) {
                        case 0: {
                            if (object.getX() == this.getX() && object.getY() <= this.getY() && object.getY() >= this._out_y) {
                                return cha;
                            }
                            else {
                                continue;
                            }
                        }
                        case 2: {
                            if (object.getX() >= this.getX() && object.getX() <= this._out_x && object.getY() == this.getY()) {
                                return cha;
                            }
                            else {
                                continue;
                            }
                        }
                        case 4: {
                            if (object.getX() == this.getX() && object.getY() >= this.getY() && object.getY() <= this._out_y) {
                                return cha;
                            }
                            else {
                                continue;
                            }
                        }
                        case 6: {
                            if (object.getX() <= this.getX() && object.getX() >= this._out_x && object.getY() == this.getY()) {
                                return cha;
                            }
                            else {
                                continue;
                            }
                        }
                        default: {
                            continue;
                        }
                    }
                }
                else {
                    continue;
                }
            }
        }
        return null;
    }
    
    private void set_atkLoc() {
        try {
            boolean test = true;
            int x = this.getX();
            int y = this.getY();
            switch (this.getHeading()) {
                case 0: {
                    while (test) {
                        int gab = this.getMap().getOriginalTile(x, y--);
                        if (gab == 0) {
                            test = false;
                        }
                    }
                    break;
                }
                case 2: {
                    while (test) {
                        int gab = this.getMap().getOriginalTile(x++, y);
                        if (gab == 0) {
                            test = false;
                        }
                    }
                    break;
                }
                case 4: {
                    while (test) {
                        int gab = this.getMap().getOriginalTile(x, y++);
                        if (gab == 0) {
                            test = false;
                        }
                    }
                    break;
                }
                case 6: {
                    while (test) {
                        int gab = this.getMap().getOriginalTile(x--, y);
                        if (gab == 0) {
                            test = false;
                        }
                    }
                    break;
                }
            }
            if (!test) {
                this._out_x = x;
                this._out_y = y;
            }
        }
        catch (Exception e) {
            L1BowInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
}

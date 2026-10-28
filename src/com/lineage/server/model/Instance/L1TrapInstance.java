package com.lineage.server.model.Instance;

import com.lineage.server.serverpackets.S_Trap;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.model.map.L1Map;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.model.L1Location;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.List;
import com.lineage.server.types.Point;
import com.lineage.server.templates.L1Trap;
import org.apache.commons.logging.Log;
import com.lineage.server.model.L1Object;

public class L1TrapInstance extends L1Object
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private final L1Trap _trap;
    private final Point _baseLoc;
    private final Point _rndPt;
    private int _span;
    private int _stop;
    private boolean _isEnable;
    private List<L1PcInstance> _knownPlayers;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(L1TowerInstance.class);
        _random = new Random();
    }
    
    public L1TrapInstance(int id, L1Trap trap, L1Location loc, Point rndPt, int span) {
        this._baseLoc = new Point();
        this._rndPt = new Point();
        this._stop = 0;
        this._isEnable = true;
        this._knownPlayers = new CopyOnWriteArrayList();
        this.setId(id);
        this._trap = trap;
        this.getLocation().set(loc);
        this._baseLoc.set(loc);
        this._rndPt.set(rndPt);
        if (span > 0) {
            this._span = span / 1000;
        }
        this.resetLocation();
    }
    
    public L1Trap get_trap() {
        return this._trap;
    }
    
    public void set_stop(int _stop) {
        this._stop = _stop;
    }
    
    public int get_stop() {
        return this._stop;
    }
    
    public void resetLocation() {
        try {
            if (this._rndPt.getX() == 0 && this._rndPt.getY() == 0) {
                return;
            }
            else {
                this.enableTrap();
                int i = 0;
                while (i < 50) {
                    int rndX = L1TrapInstance._random.nextInt(this._rndPt.getX() + 1) * (L1TrapInstance._random.nextBoolean() ? 1 : -1);
                    int rndY = L1TrapInstance._random.nextInt(this._rndPt.getY() + 1) * (L1TrapInstance._random.nextBoolean() ? 1 : -1);
                    rndX += this._baseLoc.getX();
                    rndY += this._baseLoc.getY();
                    L1Map map = this.getLocation().getMap();
                    if (map.isInMap(rndX, rndY) && map.isPassable(rndX, rndY, null)) {
                        this.getLocation().set(rndX, rndY);
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
        }
        catch (Exception e) {
            L1TrapInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int getSpan() {
        return this._span;
    }
    
    public void enableTrap() {
        this.set_stop(0);
        this._isEnable = true;
    }
    
    public void disableTrap() {
        this._isEnable = false;
        for (L1PcInstance pc : this._knownPlayers) {
            pc.removeKnownObject(this);
            pc.sendPackets(new S_RemoveObject(this));
        }
        this._knownPlayers.clear();
    }
    
    public boolean isEnable() {
        return this._isEnable;
    }
    
    public void onTrod(L1PcInstance trodFrom) {
        this._trap.onTrod(trodFrom, this);
    }
    
    public void onDetection(L1PcInstance caster) {
        this._trap.onDetection(caster, this);
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.hasSkillEffect(2002)) {
                perceivedFrom.addKnownObject(this);
                perceivedFrom.sendPackets(new S_Trap(this, this._trap.getType()));
                this._knownPlayers.add(perceivedFrom);
            }
        }
        catch (Exception e) {
            L1TrapInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
}

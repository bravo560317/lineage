package com.lineage.server.model.Instance;

import com.lineage.server.model.poison.L1DamagePoison;
import com.lineage.server.model.L1GroundInventory;
import java.util.ArrayList;
import java.util.Iterator;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.serverpackets.S_MoveCharPacket;
import com.lineage.server.model.L1Character;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_NPCPack_Eff;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1EffectInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    public static final int FW_DAMAGE_INTERVAL = 1650;
    public static final int CUBE_INTERVAL = 500;
    public static final int CUBE_TIME = 8000;
    public static final int OTHER = 500;
    private L1EffectType _effectType;
    private int _skillId;
    
    static {
        _log = LogFactory.getLog(L1EffectInstance.class);
    }
    
    public L1EffectInstance(L1Npc template) {
        super(template);
        int npcId = this.getNpcTemplate().get_npcId();
        switch (npcId) {
            case 80149: {
                this._effectType = L1EffectType.isCubeBurn;
                break;
            }
            case 80150: {
                this._effectType = L1EffectType.isCubeEruption;
                break;
            }
            case 80151: {
                this._effectType = L1EffectType.isCubeShock;
                break;
            }
            case 80152: {
                this._effectType = L1EffectType.isCubeHarmonize;
                break;
            }
            case 81157: {
                this._effectType = L1EffectType.isFirewall;
                break;
            }
            case 86125: {
                GeneralThreadPool.get().schedule(new L1EffectInstance.PoisonTimer(this), 0L);
                break;
            }
            default: {
                this._effectType = L1EffectType.isOther;
                break;
            }
        }
    }
    
    public L1EffectType effectType() {
        return this._effectType;
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            else {
                perceivedFrom.addKnownObject(this);
                perceivedFrom.sendPackets(new S_NPCPack_Eff(this));
                if (this.getBraveSpeed() > 0) {
                    perceivedFrom.sendPackets(new S_SkillBrave(this.getId(), this.getBraveSpeed(), 600000));
                }
            }
        }
        catch (Exception e) {
            L1EffectInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
    }
    
    public void setFollowSpeed(L1Character target) {
        if (target != null && target.isInvisble()) {
            this.deleteMe();
            return;
        }
        else if (target.isDead()) {
            this.deleteMe();
            return;
        }
        else {
            if (target.getMoveSpeed() != this.getMoveSpeed()) {
                this.setMoveSpeed(target.getMoveSpeed());
            }
            if (target.getBraveSpeed() != this.getBraveSpeed()) {
                this.setBraveSpeed(target.getBraveSpeed());
            }
            return;
        }
    }
    
    public void Follow(L1Character target) {
        try {
            if (target == null) {
                this.deleteMe();
                return;
            }
            else if (target != null && target.isInvisble()) {
                this.deleteMe();
                return;
            }
            else if (target.isDead()) {
                this.deleteMe();
                return;
            }
            else if (!this.destroyed()) {
                while (!this.getLocation().isSamePoint(target.getLocation())) {
                    int dir = this.targetDirection(target.getX(), target.getY());
                    if (dir != -1) {
                        this.setDirectionMove(dir);
                    }
                }
            }
        }
        catch (Exception e) {
            L1EffectInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void setDirectionMove(int heading) {
        int locx = this.getX();
        int locy = this.getY();
        locx += L1EffectInstance.HEADING_TABLE_X[heading];
        locy += L1EffectInstance.HEADING_TABLE_Y[heading];
        this.setHeading(heading);
        this.setX(locx);
        this.setY(locy);
        this.broadcastPacketAll(new S_MoveCharPacket(this));
    }
    
    @Override
    public void deleteMe() {
        try {
            this._destroyed = true;
            if (this.getInventory() != null) {
                this.getInventory().clearItems();
            }
            this.allTargetClear();
            this._master = null;
            int showid = this.get_showId();
            if (WorldQuest.get().isQuest(showid)) {
                WorldQuest.get().remove(showid, this);
            }
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                if (pc != null) {
                    if (this.getNpcId() == 86131) {
                        pc.sendPackets(new S_DoActionGFX(this.getId(), 11));
                    }
                    pc.removeKnownObject(this);
                    pc.sendPackets(new S_RemoveObject(this));
                }
            }
            for (L1Object obj : World.get().getVisibleObjects(this)) {
                if (obj != null && (obj instanceof L1PcInstance || obj instanceof L1NpcInstance)) {
                    L1Character cha = (L1Character)obj;
                    ArrayList<L1EffectInstance> effectlist = cha.get_TrueTargetEffectList();
                    if (effectlist.contains(this)) {
                        effectlist.remove(this);
                    }
                    else {
                        continue;
                    }
                }
            }
            this.removeAllKnownObjects();
            World.get().removeVisibleObject(this);
            World.get().removeObject(this);
        }
        catch (Exception e) {
            L1EffectInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void setSkillId(int i) {
        this._skillId = i;
    }
    
    public int getSkillId() {
        return this._skillId;
    }
    
    class PoisonTimer implements Runnable
    {
        private static final int POISON_INTERVAL = 1000;
        private L1EffectInstance _effect;
        
        public PoisonTimer(L1EffectInstance effect) {
            this._effect = effect;
        }
        
        @Override
        public void run() {
            while (!L1EffectInstance.this._destroyed) {
                try {
                    for (L1Object objects : World.get().getVisibleObjects(this._effect, 0)) {
                        if (!(objects instanceof L1MonsterInstance) && !(objects instanceof L1GroundInventory)) {
                            L1Character cha = (L1Character)objects;
                            L1DamagePoison.doInfection(this._effect, cha, 3000, 100);
                        }
                    }
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex) {}
            }
        }
    }
}

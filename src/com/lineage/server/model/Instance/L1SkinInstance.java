package com.lineage.server.model.Instance;

import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_SkillHaste;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_NPCPack_Skin;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SkinInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private int _srcMoveSpeed;
    private int _srcBraveSpeed;
    private int _move_type;
    
    static {
        _log = LogFactory.getLog(L1SkinInstance.class);
    }
    
    public L1SkinInstance(L1Npc template) {
        super(template);
        this._move_type = 0;
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            else {
                perceivedFrom.addKnownObject(this);
                perceivedFrom.sendPackets(new S_NPCPack_Skin(this));
            }
        }
        catch (Exception e) {
            L1SkinInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
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
            World.get().removeVisibleObject(this);
            World.get().removeObject(this);
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                pc.removeKnownObject(this);
                pc.sendPackets(new S_RemoveObject(this));
            }
            this.removeAllKnownObjects();
        }
        catch (Exception e) {
            L1SkinInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void setNpcMoveSpeed() {
        if (this._master == null) {
            this.deleteMe();
            return;
        }
        else {
            try {
                if (this._master.isInvisble()) {
                    this.deleteMe();
                    return;
                }
                else if (this._master.isDead()) {
                    this.deleteMe();
                    return;
                }
                else {
                    if (this._master.getMoveSpeed() != this._srcMoveSpeed) {
                        this.set_srcMoveSpeed(this._master.getMoveSpeed());
                        this.setMoveSpeed(this._srcMoveSpeed);
                    }
                    if (this._master.getBraveSpeed() != this._srcBraveSpeed) {
                        this.set_srcBraveSpeed(this._master.getBraveSpeed());
                        this.setBraveSpeed(this._srcBraveSpeed);
                    }
                }
            }
            catch (Exception e) {
                L1SkinInstance._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    private void set_srcMoveSpeed(int srcMoveSpeed) {
        try {
            this._srcMoveSpeed = srcMoveSpeed;
            this.broadcastPacketAll(new S_SkillHaste(this.getId(), this._srcMoveSpeed, 0));
        }
        catch (Exception e) {
            L1SkinInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void set_srcBraveSpeed(int srcBraveSpeed) {
        try {
            this._srcBraveSpeed = srcBraveSpeed;
            this.broadcastPacketAll(new S_SkillBrave(this.getId(), this._srcBraveSpeed, 0));
        }
        catch (Exception e) {
            L1SkinInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int getMoveType() {
        return this._move_type;
    }
    
    public void setMoveType(int i) {
        this._move_type = i;
    }
}

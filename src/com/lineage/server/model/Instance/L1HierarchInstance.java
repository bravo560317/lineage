package com.lineage.server.model.Instance;

import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_MoveCharPacket;
import com.lineage.server.serverpackets.S_NPCPack_Hierarch;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.templates.L1Hierarch;
import java.util.Iterator;
import com.lineage.server.datatables.NpcHierarchTable;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.model.L1Character;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.SkillsTable;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class L1HierarchInstance extends L1NpcInstance
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    private static Random _random;
    private int _itemObjId;
    private int[] _skill_id;
    private int[] _skill_mp;
    private int _srcMoveSpeed;
    private int _srcBraveSpeed;
    
    static {
        _log = LogFactory.getLog(L1HierarchInstance.class);
        L1HierarchInstance._random = new Random();
    }
    
    @Override
    public boolean noTarget() {
        if (this._master == null || this._master.isInvisble() || this._master.isDead()) {
            this.deleteHierarch();
            return true;
        }
        else if (this._master.getMapId() != this.getMapId()) {
            return true;
        }
        else {
            if (this.getHierarch() == 1 && this._master instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)this._master;
                if (this._skill_id != null && this._skill_mp != null) {
                    int i = 0;
                    while (i < this._skill_id.length) {
                        int _skillid = this._skill_id[i];
                        int _skillmp = this._skill_mp[i];
                        if (!pc.hasSkillEffect(_skillid) && this.getCurrentMp() > _skillmp) {
                            this.setCurrentMp(this.getCurrentMp() - _skillmp);
                            L1Skills _skill = SkillsTable.get().getTemplate(_skillid);
                            L1SkillUse skillUse = new L1SkillUse();
                            skillUse.handleCommands(pc, _skillid, pc.getId(), pc.getX(), pc.getX(), _skill.getBuffDuration(), 4);
                            this.setSleepTime(this.calcSleepTime(this.getAtkspeed(), 1));
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
                if (this.getCurrentMp() > 15 && pc.getCurrentHp() != pc.getMaxHp() && pc.getCurrentHp() < pc.getMaxHp() * pc.getHierarch() / 10) {
                    int chance = L1HierarchInstance._random.nextInt(75) + 1;
                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), 830));
                    pc.setCurrentHp(pc.getCurrentHp() + (this.getLawful() + chance));
                    this.setCurrentMp(this.getCurrentMp() - 15);
                    this.setSleepTime(this.calcSleepTime(this.getAtkspeed(), 1));
                }
            }
            if (this.getLocation().getTileLineDistance(this._master.getLocation()) > 1 && this._npcMove != null) {
                this._npcMove.setDirectionMove(this._npcMove.moveDirection(this._master.getX(), this._master.getY()));
                this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 1));
            }
            return false;
        }
    }
    
    public L1HierarchInstance(L1Npc template, L1Character master, int itemObjId) {
        super(template);
        this._skill_id = null;
        this._skill_mp = null;
        try {
            this.setId(IdFactoryNpc.get().nextId());
            this.set_showId(master.get_showId());
            this.setItemObjId(itemObjId);
            this.setMaster(master);
            this.setX(master.getX() + L1HierarchInstance._random.nextInt(5) - 2);
            this.setY(master.getY() + L1HierarchInstance._random.nextInt(5) - 2);
            this.setMap(master.getMapId());
            this.setHeading(5);
            this.setLightSize(template.getLightSize());
            World.get().storeObject(this);
            World.get().addVisibleObject(this);
            this.startAI();
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                this.onPerceive(pc);
            }
            master.addHierarch(this);
            this.broadcastPacketX8(new S_SkillSound(this.getId(), 5935));
            L1Hierarch hierarch = NpcHierarchTable.get().getHierarch(this.getNpcId());
            if (hierarch != null) {
                this._skill_id = hierarch.getSkillId();
                this._skill_mp = hierarch.getSkillMp();
            }
        }
        catch (Exception e) {
            L1HierarchInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void deleteHierarch() {
        try {
            this.broadcastPacketX8(new S_SkillSound(this.getId(), 5936));
            this._master.removeHierarch();
            this.deleteMe();
        }
        catch (Exception e) {
            L1HierarchInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onAction(L1PcInstance player) {
        if (this.isDead()) {
            return;
        }
        else {
            if (this._master.equals(player)) {
                String msg0 = "跟隨";
                if (this.getHierarch() == 1) {
                    msg0 = "輔助";
                }
                String msg2 = String.valueOf(player.getHierarch() * 10);
                String[] htmldata = { this.getName(), String.valueOf(this.getCurrentMp()), String.valueOf(this.getMaxMp()), msg0, msg2 };
                player.sendPackets(new S_NPCTalkReturn(this.getId(), "Hierarch", htmldata));
            }
            return;
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance pc) {
        if (this.isDead()) {
            return;
        }
        else {
            if (this._master.equals(pc)) {
                String msg0 = "跟隨";
                if (this.getHierarch() == 1) {
                    msg0 = "輔助";
                }
                String msg2 = String.valueOf(pc.getHierarch() * 10);
                String[] htmldata = { this.getName(), String.valueOf(this.getCurrentMp()), String.valueOf(this.getMaxMp()), msg0, msg2 };
                pc.sendPackets(new S_NPCTalkReturn(this.getId(), "Hierarch", htmldata));
            }
            return;
        }
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            else {
                perceivedFrom.addKnownObject(this);
                perceivedFrom.sendPackets(new S_NPCPack_Hierarch(this));
            }
        }
        catch (Exception e) {
            L1HierarchInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void setCurrentMp(int i) {
        int currentMp = i;
        if (currentMp >= this.getMaxMp()) {
            currentMp = this.getMaxMp();
        }
        this.setCurrentMpDirect(currentMp);
        this.broadcastPacketX8(new S_SkillSound(this.getId(), 6321));
    }
    
    public void setNpcMoveSpeed() {
        try {
            if (this._master != null && this._master.isInvisble()) {
                this.deleteHierarch();
                return;
            }
            else if (this._master.isDead()) {
                this.deleteHierarch();
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
                if (this._master != null && this._master.getMapId() == this.getMapId()) {
                    if (this.getLocation().getTileLineDistance(this._master.getLocation()) > 1) {
                        int dir = this.targetDirection(this._master.getX(), this._master.getY());
                        this.setDirectionMoveSrc(dir);
                        this.broadcastPacketAll(new S_MoveCharPacket(this));
                    }
                }
                else {
                    this.deleteHierarch();
                }
            }
        }
        catch (Exception e) {
            L1HierarchInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void set_srcMoveSpeed(int srcMoveSpeed) {
        try {
            this._srcMoveSpeed = srcMoveSpeed;
            this.broadcastPacketAll(new S_SkillHaste(this.getId(), this._srcMoveSpeed, 0));
        }
        catch (Exception e) {
            L1HierarchInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void set_srcBraveSpeed(int srcBraveSpeed) {
        try {
            this._srcBraveSpeed = srcBraveSpeed;
            this.broadcastPacketAll(new S_SkillBrave(this.getId(), this._srcBraveSpeed, 0));
        }
        catch (Exception e) {
            L1HierarchInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int getItemObjId() {
        return this._itemObjId;
    }
    
    public void setItemObjId(int i) {
        this._itemObjId = i;
    }
}

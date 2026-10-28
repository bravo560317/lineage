package com.lineage.server.model.Instance;

import java.util.ArrayList;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import java.util.Iterator;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1NpcTalkData;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.world.WorldClan;
import com.lineage.server.datatables.NPCTalkDataTable;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.templates.L1Npc;

public class L1CatapultInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private int _castle_id;
    
    public L1CatapultInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        if (perceivedFrom == null) {
            return;
        }
        else {
            perceivedFrom.addKnownObject(this);
            perceivedFrom.sendPackets(new S_NPCPack(this));
            return;
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance pc) {
        if (pc == null) {
            return;
        }
        else {
            int objid = this.getId();
            L1NpcTalkData talking = NPCTalkDataTable.get().getTemplate(this.getNpcTemplate().get_npcId());
            String htmlid = null;
            String[] htmldata = null;
            int npcId = this.getNpcTemplate().get_npcId();
            if (npcId >= 90327 && npcId <= 90330) {
                this._castle_id = 1;
            }
            else if (npcId >= 90331 && npcId <= 90334) {
                this._castle_id = 4;
            }
            else if (npcId >= 90335 && npcId <= 90337) {
                this._castle_id = 2;
            }
            boolean existDefenseClan = false;
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null) {
                int clanCastleId = clan.getCastleId();
                if (clanCastleId == this._castle_id) {
                    existDefenseClan = true;
                }
            }
            boolean isProclamation = false;
            for (L1War war : WorldWar.get().getWarList()) {
                if (this._castle_id == war.get_castleId()) {
                    isProclamation = war.checkClanInWar(pc.getClanname());
                    break;
                }
            }
            if (!pc.isGm()) {
                if (clan != null) {
                    if (!ServerWarExecutor.get().isNowWar(this._castle_id)) {
                        if (pc.isCrown() && pc.getId() == clan.getLeaderId()) {
                            pc.sendPackets(new S_ServerMessage(3683));
                            return;
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(2498));
                            return;
                        }
                    }
                    else if (isProclamation) {
                        if (pc.isCrown() && pc.getId() == clan.getLeaderId()) {
                            if (existDefenseClan) {
                                if (npcId == 90327 || npcId == 90328 || npcId == 90331 || npcId == 90332 || npcId == 90335 || npcId == 90336) {
                                    pc.sendPackets(new S_ServerMessage(3681));
                                    return;
                                }
                            }
                            else if (npcId == 90329 || npcId == 90330 || npcId == 90333 || npcId == 90334 || npcId == 90337) {
                                pc.sendPackets(new S_ServerMessage(3682));
                                return;
                            }
                        }
                        else if (npcId == 90327 || npcId == 90328 || npcId == 90331 || npcId == 90332 || npcId == 90335 || npcId == 90336) {
                            pc.sendPackets(new S_ServerMessage(3681));
                            return;
                        }
                        else if (npcId == 90329 || npcId == 90330 || npcId == 90333 || npcId == 90334 || npcId == 90337) {
                            pc.sendPackets(new S_ServerMessage(3682));
                            return;
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(2498));
                        return;
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(2498));
                    return;
                }
            }
            switch (npcId) {
                case 90327:
                case 90328: {
                    htmlid = "ckenta";
                    break;
                }
                case 90329:
                case 90330: {
                    htmlid = "ckentd";
                    break;
                }
                case 90331:
                case 90332: {
                    htmlid = "cgirana";
                    break;
                }
                case 90333:
                case 90334: {
                    htmlid = "cgirand";
                    break;
                }
                case 90335:
                case 90336: {
                    htmlid = "corca";
                    break;
                }
                case 90337: {
                    htmlid = "corcd";
                    break;
                }
            }
            if (htmlid != null) {
                pc.sendPackets(new S_NPCTalkReturn(objid, htmlid));
            }
            else if (pc.getLawful() < -1000) {
                pc.sendPackets(new S_NPCTalkReturn(talking, objid, 2));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(talking, objid, 1));
            }
            return;
        }
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        if (pc != null && !this.isDead()) {
            if (this.getCurrentHp() > 0) {
                L1AttackMode attack = new L1AttackPc(pc, this);
                if (attack.calcHit()) {
                    attack.calcDamage();
                }
                attack.action();
                attack.commit();
            }
            else {
                L1AttackMode attack = new L1AttackPc(pc, this);
                attack.calcHit();
                attack.action();
            }
        }
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        int npcId = this.getNpcTemplate().get_npcId();
        if (npcId >= 90327 && npcId <= 90330) {
            this._castle_id = 1;
        }
        else if (npcId >= 90331 && npcId <= 90334) {
            this._castle_id = 4;
        }
        else if (npcId >= 90335 && npcId <= 90337) {
            this._castle_id = 2;
        }
        if (this._castle_id > 0 && ServerWarExecutor.get().isNowWar(this._castle_id)) {
            if (this.getCurrentHp() > 0 && !this.isDead()) {
                if (damage >= 0 && !(attacker instanceof L1EffectInstance)) {
                    this.setHate(attacker, damage);
                }
                L1PcInstance pc = null;
                if (attacker instanceof L1PcInstance) {
                    pc = (L1PcInstance)attacker;
                }
                else if (attacker instanceof L1PetInstance) {
                    pc = (L1PcInstance)((L1PetInstance)attacker).getMaster();
                }
                else if (attacker instanceof L1SummonInstance) {
                    pc = (L1PcInstance)((L1SummonInstance)attacker).getMaster();
                }
                if (pc == null) {
                    return;
                }
                else {
                    boolean existDefenseClan = false;
                    for (L1Clan clan : WorldClan.get().getAllClans()) {
                        int clanCastleId = clan.getCastleId();
                        if (clanCastleId == this._castle_id) {
                            existDefenseClan = true;
                            break;
                        }
                    }
                    boolean isProclamation = false;
                    for (L1War war : WorldWar.get().getWarList()) {
                        if (this._castle_id == war.get_castleId()) {
                            isProclamation = war.checkClanInWar(pc.getClanname());
                        }
                    }
                    if (existDefenseClan && !isProclamation) {
                        return;
                    }
                    else {
                        int newHp = this.getCurrentHp() - damage;
                        if (newHp <= 0 && !this.isDead()) {
                            this.setCurrentHp(0);
                            this.setDead(true);
                            this.setStatus(8);
                            this.death();
                        }
                        if (newHp > 0) {
                            this.setCurrentHp(newHp);
                        }
                    }
                }
            }
            else if (!this.isDead()) {
                this.setDead(true);
                this.setStatus(8);
                this.death();
            }
        }
    }
    
    private void death() {
        try {
            this.setDeathProcessing(true);
            this.setCurrentHp(0);
            this.setDead(true);
            this.setStatus(8);
            this.getMap().setPassable(this.getLocation(), true);
            this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 8));
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void deleteMe() {
        this._destroyed = true;
        if (this.getInventory() != null) {
            this.getInventory().clearItems();
        }
        World.get().removeVisibleObject(this);
        World.get().removeObject(this);
        ArrayList<L1PcInstance> list = null;
        list = World.get().getRecognizePlayer(this);
        for (L1PcInstance pc : list) {
            if (pc == null) {
                continue;
            }
            else {
                pc.removeKnownObject(this);
                pc.sendPackets(new S_RemoveObject(this));
            }
        }
        this.removeAllKnownObjects();
    }
}

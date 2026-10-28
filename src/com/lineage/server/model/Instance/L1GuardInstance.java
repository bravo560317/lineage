package com.lineage.server.model.Instance;

import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Character;
import java.util.Collection;
import com.lineage.server.model.L1NpcTalkData;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.datatables.NPCTalkDataTable;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.types.Point;
import java.util.Iterator;
import com.lineage.server.world.World;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1GuardInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1GuardInstance.class);
    }
    
    @Override
    public void searchTarget() {
        L1PcInstance targetPlayer = searchTarget(this);
        if (targetPlayer != null) {
            this._hateList.add(targetPlayer, 0);
            this._target = targetPlayer;
        }
    }
    
    private static L1PcInstance searchTarget(L1GuardInstance npc) {
        L1PcInstance targetPlayer = null;
        for (L1PcInstance pc : World.get().getVisiblePlayer(npc)) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e) {
                L1GuardInstance._log.error(e.getLocalizedMessage(), e);
            }
            if (pc.getCurrentHp() > 0 && !pc.isDead() && !pc.isGm() && !pc.isGhost() && npc.get_showId() == pc.get_showId() && (!pc.isInvisble() || npc.getNpcTemplate().is_agrocoi()) && pc.isWanted()) {
                targetPlayer = pc;
                break;
            }
        }
        return targetPlayer;
    }
    
    public void setTarget(L1PcInstance targetPlayer) {
        if (targetPlayer != null) {
            this._hateList.add(targetPlayer, 0);
            this._target = targetPlayer;
        }
    }
    
    @Override
    public boolean noTarget() {
        if (this.getLocation().getTileLineDistance(new Point(this.getHomeX(), this.getHomeY())) > 0) {
            if (this._npcMove != null) {
                int dir = this._npcMove.moveDirection(this.getHomeX(), this.getHomeY());
                if (dir != -1) {
                    this._npcMove.setDirectionMove(dir);
                    this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                }
                else {
                    this.teleport(this.getHomeX(), this.getHomeY(), 1);
                }
            }
        }
        else if (World.get().getRecognizePlayer(this).size() == 0) {
            return true;
        }
        return false;
    }
    
    public L1GuardInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onNpcAI() {
        if (this.isAiRunning()) {
            return;
        }
        else {
            this.setActived(false);
            this.startAI();
            return;
        }
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            if (!this.isDead()) {
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
        catch (Exception e) {
            L1GuardInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance player) {
        int objid = this.getId();
        L1NpcTalkData talking = NPCTalkDataTable.get().getTemplate(this.getNpcTemplate().get_npcId());
        int npcid = this.getNpcTemplate().get_npcId();
        String htmlid = null;
        String[] htmldata = (String[])null;
        boolean hascastle = false;
        String clan_name = " ";
        String pri_name = " ";
        if (talking != null) {
            if (npcid == 70549 || npcid == 70985) {
                hascastle = this.checkHasCastle(player, 1);
                if (hascastle) {
                    htmlid = "gateokeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70656) {
                hascastle = this.checkHasCastle(player, 1);
                if (hascastle) {
                    htmlid = "gatekeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70600 || npcid == 70986) {
                hascastle = this.checkHasCastle(player, 2);
                if (hascastle) {
                    htmlid = "orckeeper";
                }
                else {
                    htmlid = "orckeeperop";
                }
            }
            else if (npcid == 70687 || npcid == 70987) {
                hascastle = this.checkHasCastle(player, 3);
                if (hascastle) {
                    htmlid = "gateokeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70778) {
                hascastle = this.checkHasCastle(player, 3);
                if (hascastle) {
                    htmlid = "gatekeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70800 || npcid == 70988 || npcid == 70989 || npcid == 70990 || npcid == 70991) {
                hascastle = this.checkHasCastle(player, 4);
                if (hascastle) {
                    htmlid = "gateokeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70817) {
                hascastle = this.checkHasCastle(player, 4);
                if (hascastle) {
                    htmlid = "gatekeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70862 || npcid == 70992) {
                hascastle = this.checkHasCastle(player, 5);
                if (hascastle) {
                    htmlid = "gateokeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70863) {
                hascastle = this.checkHasCastle(player, 5);
                if (hascastle) {
                    htmlid = "gatekeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70993 || npcid == 70994) {
                hascastle = this.checkHasCastle(player, 6);
                if (hascastle) {
                    htmlid = "gateokeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70995) {
                hascastle = this.checkHasCastle(player, 6);
                if (hascastle) {
                    htmlid = "gatekeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 70996) {
                hascastle = this.checkHasCastle(player, 7);
                if (hascastle) {
                    htmlid = "gatekeeper";
                    htmldata = new String[] { player.getName() };
                }
                else {
                    htmlid = "gatekeeperop";
                }
            }
            else if (npcid == 60514) {
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan clan : allClans) {
                    if (clan.getCastleId() == 1) {
                        clan_name = clan.getClanName();
                        pri_name = clan.getLeaderName();
                        break;
                    }
                }
                htmlid = "ktguard6";
                htmldata = new String[] { this.getName(), clan_name, pri_name };
            }
            else if (npcid == 60560) {
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan clan : allClans) {
                    if (clan.getCastleId() == 2) {
                        clan_name = clan.getClanName();
                        pri_name = clan.getLeaderName();
                        break;
                    }
                }
                htmlid = "orcguard6";
                htmldata = new String[] { this.getName(), clan_name, pri_name };
            }
            else if (npcid == 60552) {
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan clan : allClans) {
                    if (clan.getCastleId() == 3) {
                        clan_name = clan.getClanName();
                        pri_name = clan.getLeaderName();
                        break;
                    }
                }
                htmlid = "wdguard6";
                htmldata = new String[] { this.getName(), clan_name, pri_name };
            }
            else if (npcid == 60524 || npcid == 60525 || npcid == 60529) {
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan clan : allClans) {
                    if (clan.getCastleId() == 4) {
                        clan_name = clan.getClanName();
                        pri_name = clan.getLeaderName();
                        break;
                    }
                }
                htmlid = "grguard6";
                htmldata = new String[] { this.getName(), clan_name, pri_name };
            }
            else if (npcid == 70857) {
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan clan : allClans) {
                    if (clan.getCastleId() == 5) {
                        clan_name = clan.getClanName();
                        pri_name = clan.getLeaderName();
                        break;
                    }
                }
                htmlid = "heguard6";
                htmldata = new String[] { this.getName(), clan_name, pri_name };
            }
            else if (npcid == 60530 || npcid == 60531) {
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan clan : allClans) {
                    if (clan.getCastleId() == 6) {
                        clan_name = clan.getClanName();
                        pri_name = clan.getLeaderName();
                        break;
                    }
                }
                htmlid = "dcguard6";
                htmldata = new String[] { this.getName(), clan_name, pri_name };
            }
            else if (npcid == 60533 || npcid == 60534) {
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan clan : allClans) {
                    if (clan.getCastleId() == 7) {
                        clan_name = clan.getClanName();
                        pri_name = clan.getLeaderName();
                        break;
                    }
                }
                htmlid = "adguard6";
                htmldata = new String[] { this.getName(), clan_name, pri_name };
            }
            else if (npcid == 81156) {
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan clan : allClans) {
                    if (clan.getCastleId() == 8) {
                        clan_name = clan.getClanName();
                        pri_name = clan.getLeaderName();
                        break;
                    }
                }
                htmlid = "ktguard6";
                htmldata = new String[] { this.getName(), clan_name, pri_name };
            }
            if (htmlid != null) {
                if (htmldata != null) {
                    player.sendPackets(new S_NPCTalkReturn(objid, htmlid, htmldata));
                }
                else {
                    player.sendPackets(new S_NPCTalkReturn(objid, htmlid));
                }
            }
            else if (player.getLawful() < -1000) {
                player.sendPackets(new S_NPCTalkReturn(talking, objid, 2));
            }
            else {
                player.sendPackets(new S_NPCTalkReturn(talking, objid, 1));
            }
        }
    }
    
    public void onFinalAction() {
    }
    
    public void doFinalAction() {
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        this.ISASCAPE = false;
        if (this.getCurrentHp() > 0 && !this.isDead()) {
            if (damage >= 0 && !(attacker instanceof L1EffectInstance)) {
                if (attacker instanceof L1IllusoryInstance) {
                    L1IllusoryInstance ill = (L1IllusoryInstance)attacker;
                    attacker = ill.getMaster();
                    this.setHate(attacker, damage);
                }
                else {
                    this.setHate(attacker, damage);
                }
            }
            if (damage > 0) {
                this.removeSkillEffect(66);
                this.removeSkillEffect(212);
            }
            this.onNpcAI();
            if (attacker instanceof L1PcInstance && damage > 0) {
                L1PcInstance pc = (L1PcInstance)attacker;
                pc.setPetTarget(this);
            }
            int newHp = this.getCurrentHp() - damage;
            if (newHp <= 0 && !this.isDead()) {
                this.setCurrentHpDirect(0);
                this.setDead(true);
                this.setStatus(8);
                L1GuardInstance.Death death = new L1GuardInstance.Death(attacker);
                GeneralThreadPool.get().execute(death);
            }
            if (newHp > 0) {
                this.setCurrentHp(newHp);
            }
        }
        else if ((this.getCurrentHp() != 0 || this.isDead()) && !this.isDead()) {
            this.setDead(true);
            this.setStatus(8);
            L1GuardInstance.Death death2 = new L1GuardInstance.Death(attacker);
            GeneralThreadPool.get().execute(death2);
        }
    }
    
    @Override
    public void setCurrentHp(int i) {
        int currentHp = Math.min(i, this.getMaxHp());
        if (this.getCurrentHp() == currentHp) {
            return;
        }
        else {
            this.setCurrentHpDirect(currentHp);
            return;
        }
    }
    
    private boolean checkHasCastle(L1PcInstance pc, int castleId) {
        boolean isExistDefenseClan = false;
        Collection<L1Clan> allClans = WorldClan.get().getAllClans();
        for (L1Clan clan : allClans) {
            if (castleId == clan.getCastleId()) {
                isExistDefenseClan = true;
                break;
            }
        }
        if (!isExistDefenseClan) {
            return true;
        }
        else {
            if (pc.getClanid() != 0) {
                L1Clan clan2 = WorldClan.get().getClan(pc.getClanname());
                if (clan2 != null && clan2.getCastleId() == castleId) {
                    return true;
                }
            }
            return false;
        }
    }
    
    class Death implements Runnable
    {
        L1Character _lastAttacker;
        
        public Death(L1Character lastAttacker) {
            this._lastAttacker = lastAttacker;
        }
        
        @Override
        public void run() {
            L1GuardInstance.this.setDeathProcessing(true);
            L1GuardInstance.this.setCurrentHpDirect(0);
            L1GuardInstance.this.setDead(true);
            L1GuardInstance.this.setStatus(8);
            L1GuardInstance.this.getMap().setPassable(L1GuardInstance.this.getLocation(), true);
            L1GuardInstance.this.broadcastPacketAll(new S_DoActionGFX(L1GuardInstance.this.getId(), 8));
            L1GuardInstance.this.startChat(1);
            L1GuardInstance.this.setDeathProcessing(false);
            L1GuardInstance.this.allTargetClear();
            L1GuardInstance.this.startDeleteTimer(ConfigAlt.NPC_DELETION_TIME);
        }
    }
}

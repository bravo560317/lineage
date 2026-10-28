package com.lineage.server.model.Instance;

import java.util.TimerTask;
import com.lineage.server.model.drop.DropShareExecutor;
import java.util.ArrayList;
import com.lineage.config.ConfigAlt;
import com.lineage.config.ConfigRate;
import com.lineage.server.model.drop.DropShare;
import com.lineage.server.utils.CalcExp;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1NpcTalkData;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.datatables.NPCTalkDataTable;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.serverpackets.S_NpcChatPacket;
import com.lineage.server.utils.Random;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Character;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.serverpackets.S_NpcChatShouting;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1GuardianInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private L1GuardianInstance _npc;
    private int GDROPITEM_TIME;
    private static final long REST_MILLISEC = 10000L;
    private L1GuardianInstance.RestMonitor _monitor;
    
    static {
        _log = LogFactory.getLog(L1GuardianInstance.class);
    }
    
    public L1GuardianInstance(L1Npc template) {
        super(template);
        this._npc = this;
        this.GDROPITEM_TIME = 10;
        if (!this.isDropitems()) {
            this.doGDropItem(0);
        }
    }
    
    @Override
    public void searchTarget() {
        L1PcInstance targetPlayer = searchTarget(this);
        if (targetPlayer != null) {
            this._hateList.add(targetPlayer, 0);
            this._target = targetPlayer;
        }
    }
    
    private static L1PcInstance searchTarget(L1GuardianInstance npc) {
        L1PcInstance targetPlayer = null;
        for (L1PcInstance pc : World.get().getVisiblePlayer(npc)) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e) {
                L1GuardianInstance._log.error(e.getLocalizedMessage(), e);
            }
            if (pc.getCurrentHp() > 0 && !pc.isDead() && !pc.isGm()) {
                if (pc.isGhost()) {
                    continue;
                }
                else if (npc.get_showId() != pc.get_showId()) {
                    continue;
                }
                else if (!pc.isInvisble() || npc.getNpcTemplate().is_agrocoi()) {
                    if (!pc.isElf()) {
                        targetPlayer = pc;
                        npc.wideBroadcastPacket(new S_NpcChatShouting(npc, "$804"));
                        break;
                    }
                    else if (pc.isElf() && pc.isWantedForElf()) {
                        targetPlayer = pc;
                        npc.wideBroadcastPacket(new S_NpcChat(npc, "$815"));
                        break;
                    }
                    else {
                        continue;
                    }
                }
                else {
                    continue;
                }
            }
        }
        return targetPlayer;
    }
    
    @Override
    public void setLink(L1Character cha) {
        if (cha != null && this._hateList.isEmpty()) {
            this._hateList.add(cha, 0);
            this.checkTarget();
        }
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
    
    public void doGDropItem(int timer) {
        L1GuardianInstance.GDropItemTask task = new L1GuardianInstance.GDropItemTask();
        GeneralThreadPool.get().schedule(task, (long)(timer * 60000));
    }
    
    @Override
    public void onAction(L1PcInstance player) {
        try {
            if (player.getType() == 2 && player.getCurrentWeapon() == 0 && player.isElf()) {
                L1AttackMode attack = new L1AttackPc(player, this);
                if (attack.calcHit()) {
                    if (player.getInventory().getSize() >= 160) {
                        this.broadcastPacketX10(new S_NpcChatShouting(this, "你身上東西太多,我不想給予了。"));
                        return;
                    }
                    else {
                        try {
                            int chance = 0;
                            int npcId = this.getNpcTemplate().get_npcId();
                            String npcName = this.getNpcTemplate().get_name();
                            String itemName = "";
                            int itemCount = 0;
                            int 樹皮剩餘量 = 0;
                            L1Item item40499 = ItemTable.get().getTemplate(40499);
                            L1Item item40500 = ItemTable.get().getTemplate(40503);
                            L1Item item40501 = ItemTable.get().getTemplate(40505);
                            L1Item item40502 = ItemTable.get().getTemplate(40506);
                            L1Item item40503 = ItemTable.get().getTemplate(40507);
                            L1Item item40504 = ItemTable.get().getTemplate(40519);
                            if (npcId == 70848) {
                                if (this._inventory.checkItem(40499) && this._inventory.checkItem(40505)) {
                                    itemName = item40501.getName();
                                    itemCount = (int)this._inventory.countItems(40499);
                                    if (itemCount > 20) {
                                        itemCount = 20;
                                    }
                                    if (itemCount > 1) {
                                        itemName = itemName + " (" + itemCount + ")";
                                    }
                                    樹皮剩餘量 = (int)this._inventory.countItems(40505);
                                    if (樹皮剩餘量 >= itemCount) {
                                        this._inventory.consumeItem(40499, (long)itemCount);
                                        this._inventory.consumeItem(40505, (long)itemCount);
                                        player.getInventory().storeItem(40505, (long)itemCount);
                                        player.sendPackets(new S_ServerMessage(143, npcName, itemName));
                                    }
                                    else {
                                        this._inventory.consumeItem(40499, (long)樹皮剩餘量);
                                        this._inventory.consumeItem(40505, (long)樹皮剩餘量);
                                        player.getInventory().storeItem(40505, (long)樹皮剩餘量);
                                        player.sendPackets(new S_ServerMessage(143, npcName, itemName));
                                    }
                                }
                                else if (this._inventory.checkItem(40507)) {
                                    chance = Random.nextInt(100) + 1;
                                    if (chance <= 40 && chance >= 25) {
                                        itemName = item40503.getName();
                                        itemName = itemName + " (6)";
                                        this._inventory.consumeItem(40507, 6L);
                                        player.getInventory().storeItem(40507, 6L);
                                        player.sendPackets(new S_ServerMessage(143, npcName, itemName));
                                    }
                                    else {
                                        itemName = item40499.getName();
                                        player.sendPackets(new S_ServerMessage(337, itemName));
                                    }
                                }
                                else if (this._inventory.checkItem(40506) && !this._inventory.checkItem(40507)) {
                                    chance = Random.nextInt(100) + 1;
                                    if (chance <= 90 && chance >= 85) {
                                        itemName = item40502.getName();
                                        this._inventory.consumeItem(40506, 1L);
                                        player.getInventory().storeItem(40506, 1L);
                                        player.sendPackets(new S_ServerMessage(143, npcName, itemName));
                                    }
                                    else {
                                        itemName = item40499.getName();
                                        player.sendPackets(new S_ServerMessage(337, itemName));
                                    }
                                }
                                else {
                                    if (!this.forDropitems()) {
                                        this.setDropItems(false);
                                        this.doGDropItem(this.GDROPITEM_TIME);
                                    }
                                    chance = Random.nextInt(100) + 1;
                                    if (chance <= 80 && chance >= 40) {
                                        this.broadcastPacketAll(new S_NpcChatPacket(this._npc, "$822", 0));
                                    }
                                    else if (this._inventory.checkItem(40505)) {
                                        itemName = item40499.getName();
                                        player.sendPackets(new S_ServerMessage(337, itemName));
                                    }
                                }
                            }
                            if (npcId == 70850) {
                                if (this._inventory.checkItem(40519)) {
                                    chance = Random.nextInt(100) + 1;
                                    if (chance <= 25) {
                                        itemName = item40504.getName();
                                        itemName = itemName + " (5)";
                                        this._inventory.consumeItem(40519, 5L);
                                        player.getInventory().storeItem(40519, 5L);
                                        player.sendPackets(new S_ServerMessage(143, npcName, itemName));
                                    }
                                }
                                else {
                                    if (!this.forDropitems()) {
                                        this.setDropItems(false);
                                        this.doGDropItem(this.GDROPITEM_TIME);
                                    }
                                    chance = Random.nextInt(100) + 1;
                                    if (chance <= 80 && chance >= 40) {
                                        this.broadcastPacketAll(new S_NpcChatPacket(this._npc, "$824", 0));
                                    }
                                }
                            }
                            if (npcId == 70846) {
                                if (this._inventory.checkItem(40507)) {
                                    itemName = item40500.getName();
                                    itemCount = (int)this._inventory.countItems(40507);
                                    if (itemCount > 1) {
                                        itemName = itemName + " (" + itemCount + ")";
                                    }
                                    this._inventory.consumeItem(40507, (long)itemCount);
                                    player.getInventory().storeItem(40503, (long)itemCount);
                                    player.sendPackets(new S_ServerMessage(143, npcName, itemName));
                                }
                                else {
                                    itemName = item40503.getName();
                                    player.sendPackets(new S_ServerMessage(337, itemName));
                                }
                            }
                        }
                        catch (Exception e) {
                            L1GuardianInstance._log.error(e.getLocalizedMessage(), e);
                        }
                        attack.calcDamage();
                    }
                }
                attack.action();
                attack.commit();
            }
            else if (this.getCurrentHp() > 0 && !this.isDead()) {
                L1AttackMode attack = new L1AttackPc(player, this);
                if (attack.calcHit()) {
                    attack.calcDamage();
                }
                attack.action();
                attack.commit();
            }
        }
        catch (Exception e2) {
            L1GuardianInstance._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance player) {
        int objid = this.getId();
        L1NpcTalkData talking = NPCTalkDataTable.get().getTemplate(this.getNpcTemplate().get_npcId());
        L1Object object = World.get().findObject(this.getId());
        L1NpcInstance target = (L1NpcInstance)object;
        if (talking != null) {
            int pcx = player.getX();
            int pcy = player.getY();
            int npcx = target.getX();
            int npcy = target.getY();
            if (pcx == npcx && pcy < npcy) {
                this.setHeading(0);
            }
            else if (pcx > npcx && pcy < npcy) {
                this.setHeading(1);
            }
            else if (pcx > npcx && pcy == npcy) {
                this.setHeading(2);
            }
            else if (pcx > npcx && pcy > npcy) {
                this.setHeading(3);
            }
            else if (pcx == npcx && pcy > npcy) {
                this.setHeading(4);
            }
            else if (pcx < npcx && pcy > npcy) {
                this.setHeading(5);
            }
            else if (pcx < npcx && pcy == npcy) {
                this.setHeading(6);
            }
            else if (pcx < npcx && pcy < npcy) {
                this.setHeading(7);
            }
            this.broadcastPacketAll(new S_ChangeHeading(this));
            if (player.getLawful() < -1000) {
                player.sendPackets(new S_NPCTalkReturn(talking, objid, 2));
            }
            else {
                player.sendPackets(new S_NPCTalkReturn(talking, objid, 1));
            }
            synchronized (this) {
                if (this._monitor != null) {
                    this._monitor.cancel();
                }
                this.setRest(true);
                this._monitor = new L1GuardianInstance.RestMonitor();
                GeneralThreadPool.get().schedule(this._monitor, 10000L);
            }
        }
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        this.ISASCAPE = false;
        if (attacker instanceof L1PcInstance && damage > 0) {
            L1PcInstance pc = (L1PcInstance)attacker;
            if (pc.getType() != 2 || pc.getCurrentWeapon() != 0) {
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
                    }
                    this.onNpcAI();
                    this.serchLink(pc, this.getNpcTemplate().get_family());
                    if (damage > 0) {
                        pc.setPetTarget(this);
                    }
                    int newHp = this.getCurrentHp() - damage;
                    if (newHp <= 0 && !this.isDead()) {
                        this.setCurrentHpDirect(0);
                        this.setDead(true);
                        this.setStatus(8);
                        L1GuardianInstance.Death death = new L1GuardianInstance.Death(attacker);
                        GeneralThreadPool.get().execute(death);
                    }
                    if (newHp > 0) {
                        this.setCurrentHp(newHp);
                    }
                }
                else if (!this.isDead()) {
                    this.setDead(true);
                    this.setStatus(8);
                    L1GuardianInstance.Death death2 = new L1GuardianInstance.Death(attacker);
                    GeneralThreadPool.get().execute(death2);
                }
            }
        }
    }
    
    @Override
    public void setCurrentHp(int i) {
        int currentHp = i;
        if (currentHp >= this.getMaxHp()) {
            currentHp = this.getMaxHp();
        }
        this.setCurrentHpDirect(currentHp);
        if (this.getMaxHp() > this.getCurrentHp()) {
            this.startHpRegeneration();
        }
    }
    
    @Override
    public void setCurrentMp(int i) {
        int currentMp = i;
        if (currentMp >= this.getMaxMp()) {
            currentMp = this.getMaxMp();
        }
        this.setCurrentMpDirect(currentMp);
        if (this.getMaxMp() > this.getCurrentMp()) {
            this.startMpRegeneration();
        }
    }
    
    @Override
    public void onFinalAction(L1PcInstance player, String action) {
    }
    
    public void doFinalAction(L1PcInstance player) {
    }
    
    private class GDropItemTask implements Runnable
    {
        int npcId;
        
        private GDropItemTask() {
            this.npcId = L1GuardianInstance.this.getNpcTemplate().get_npcId();
        }
        
        @Override
        public void run() {
            try {
                if (L1GuardianInstance.this.GDROPITEM_TIME > 0 && !L1GuardianInstance.this.isDropitems()) {
                    if (this.npcId == 70848 && !L1GuardianInstance.this._inventory.checkItem(40506) && !L1GuardianInstance.this._inventory.checkItem(40507)) {
                        L1GuardianInstance.this._inventory.storeItem(40506, 1L);
                        L1GuardianInstance.this._inventory.storeItem(40507, 66L);
                        if (!L1GuardianInstance.this._inventory.checkItem(40505)) {
                            L1GuardianInstance.this._inventory.storeItem(40505, 300L);
                        }
                        else {
                            int 樹皮剩餘量 = (int)L1GuardianInstance.this._inventory.countItems(40505);
                            L1GuardianInstance.this._inventory.consumeItem(40505, (long)樹皮剩餘量);
                            L1GuardianInstance.this._inventory.storeItem(40505, 300L);
                        }
                    }
                    if (this.npcId == 70850 && !L1GuardianInstance.this._inventory.checkItem(40519)) {
                        L1GuardianInstance.this._inventory.storeItem(40519, 30L);
                    }
                    L1GuardianInstance.this.setDropItems(true);
                    L1GuardianInstance.this.giveDropItems(true);
                    L1GuardianInstance.this.doGDropItem(L1GuardianInstance.this.GDROPITEM_TIME);
                }
                else {
                    L1GuardianInstance.this.giveDropItems(false);
                }
            }
            catch (Exception e) {
                L1GuardianInstance._log.error(e.getLocalizedMessage(), e);
            }
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
            L1GuardianInstance.this.setDeathProcessing(true);
            L1GuardianInstance.this.setCurrentHpDirect(0);
            L1GuardianInstance.this.setDead(true);
            L1GuardianInstance.this.setStatus(8);
            int targetobjid = L1GuardianInstance.this.getId();
            L1GuardianInstance.this.getMap().setPassable(L1GuardianInstance.this.getLocation(), true);
            L1GuardianInstance.this.broadcastPacketAll(new S_DoActionGFX(targetobjid, 8));
            L1PcInstance player = null;
            if (this._lastAttacker instanceof L1PcInstance) {
                player = (L1PcInstance)this._lastAttacker;
            }
            else if (this._lastAttacker instanceof L1PetInstance) {
                player = (L1PcInstance)((L1PetInstance)this._lastAttacker).getMaster();
            }
            else if (this._lastAttacker instanceof L1SummonInstance) {
                player = (L1PcInstance)((L1SummonInstance)this._lastAttacker).getMaster();
            }
            else if (this._lastAttacker instanceof L1IllusoryInstance) {
                player = (L1PcInstance)((L1IllusoryInstance)this._lastAttacker).getMaster();
            }
            else if (this._lastAttacker instanceof L1EffectInstance) {
                player = (L1PcInstance)((L1EffectInstance)this._lastAttacker).getMaster();
            }
            if (player != null) {
                ArrayList<L1Character> targetList = L1GuardianInstance.this._hateList.toTargetArrayList();
                ArrayList<Integer> hateList = L1GuardianInstance.this._hateList.toHateArrayList();
                long exp = L1GuardianInstance.this.getExp();
                CalcExp.calcExp(player, targetobjid, targetList, hateList, exp);
                ArrayList<L1Character> dropTargetList = L1GuardianInstance.this._dropHateList.toTargetArrayList();
                ArrayList<Integer> dropHateList = L1GuardianInstance.this._dropHateList.toHateArrayList();
                try {
                    DropShareExecutor dropShareExecutor = new DropShare();
                    dropShareExecutor.dropShare(L1GuardianInstance.this._npc, dropTargetList, dropHateList);
                }
                catch (Exception e) {
                    L1GuardianInstance._log.error(e.getLocalizedMessage(), e);
                }
                player.addKarma((int)((double)L1GuardianInstance.this.getKarma() * ConfigRate.RATE_KARMA));
            }
            L1GuardianInstance.this.setDeathProcessing(false);
            L1GuardianInstance.this.setKarma(0);
            L1GuardianInstance.this.setExp(0L);
            L1GuardianInstance.this.allTargetClear();
            L1GuardianInstance.this.startDeleteTimer(ConfigAlt.NPC_DELETION_TIME);
        }
    }
    
    public class RestMonitor extends TimerTask
    {
        @Override
        public void run() {
            L1GuardianInstance.this.setRest(false);
        }
    }
}

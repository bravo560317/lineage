package com.lineage.data.quest;

import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.Instance.L1MerchantInstance;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_SkillSound;
import java.util.ArrayList;
import java.util.List;
import com.lineage.server.templates.L1Rank;
import com.lineage.server.datatables.lock.BoardOrimReading;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.WorldMob;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1Party;
import com.lineage.server.templates.L1QuestUser;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class Chapter02R extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static final Random _random;
    public static final int DELAY_SPEED = 1;
    private final L1QuestUser quest;
    public final L1Party party;
    private final L1Location loc;
    private static final L1Location[] CABIN_LOC_LIST;
    private final L1NpcInstance door;
    private final L1NpcInstance npc1;
    private final L1NpcInstance npc2;
    private L1NpcInstance ship;
    public L1NpcInstance portal;
    private volatile int ship_step;
    private int counter1;
    private int counter2;
    private int counter3;
    private boolean fire_order;
    private boolean defense_okay;
    private int critical_hit;
    private boolean hit_okay;
    private int counter_alt;
    private int counter_alt2;
    private L1PcInstance quest_pc;
    private int mimic_quest_order;
    private int mimic_quest_count;
    private boolean squid_version;
    private int round;
    private int old_score;
    private int check_power;
    private int mob_power;
    private int count_down;
    private int fire_point;
    private L1Location next_fire_loc;
    private boolean send_gift;
    
    static {
        _log = LogFactory.getLog(Chapter02R.class);
        _random = new Random();
        (CABIN_LOC_LIST = new L1Location[4])[0] = new L1Location(32671, 32802, 9101);
        Chapter02R.CABIN_LOC_LIST[1] = new L1Location(32735, 32802, 9101);
        Chapter02R.CABIN_LOC_LIST[2] = new L1Location(32735, 32862, 9101);
        Chapter02R.CABIN_LOC_LIST[3] = new L1Location(32799, 32863, 9101);
    }
    
    public Chapter02R(L1QuestUser quest, L1Party party, L1NpcInstance door, L1NpcInstance npc1, L1NpcInstance npc2) {
        this.ship_step = -1;
        this.quest = quest;
        this.party = party;
        this.loc = new L1Location(32798, 32803, quest.get_mapid());
        this.door = door;
        this.npc1 = npc1;
        this.npc2 = npc2;
        this.next_fire_loc = this.loc.randomLocation(8, true);
    }
    
    public void startR() {
        int timeMillis = 10000;
        this._timer = GeneralThreadPool.get().schedule(this, (long)timeMillis);
    }
    
    @Override
    public void run() {
        this.cancel();
        try {
            if (this.round <= 0) {
                this.sendPacketsToAll(new S_NpcChat(this.party.getLeader().getId(), "$9529"));
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9529"));
                Thread.sleep(4000L);
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9530"));
                Thread.sleep(4000L);
                if (this.party.getLeader().get_actionId() != 68) {
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9531"));
                    Thread.sleep(4000L);
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9532"));
                    Thread.sleep(4000L);
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9533"));
                    Thread.sleep(4000L);
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9534"));
                    Thread.sleep(4000L);
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9535"));
                    Thread.sleep(4000L);
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9536"));
                    L1NpcInstance effect1 = L1SpawnUtil.spawnT(97109, 32797, 32808, (short)this.quest.get_mapid(), 0, 100);
                    effect1.set_showId(this.quest.get_id());
                    this.quest.addNpc(effect1);
                    Thread.sleep(4000L);
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9537"));
                    Thread.sleep(4000L);
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9538"));
                    Thread.sleep(4000L);
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9539"));
                    L1NpcInstance effect2 = L1SpawnUtil.spawnT(97110, 32801, 32808, (short)this.quest.get_mapid(), 0, 100);
                    effect2.set_showId(this.quest.get_id());
                    this.quest.addNpc(effect2);
                    Thread.sleep(5000L);
                    effect1.set_spawnTime(1);
                    effect2.set_spawnTime(1);
                }
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9540"));
                Thread.sleep(5000L);
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9603"));
                this.sendPacketsToAll(new S_PacketBoxGree(1));
                this.quest.spawnQuestMob(0, 5);
                Thread.sleep(10000L);
                this.round = 1;
            }
            int n = 12;
            while (this.round <= n) {
                Thread.sleep(3000L);
                if (this.send_gift) {
                    break;
                }
                else if (this.quest.size() < 3) {
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9547"));
                    break;
                }
                else if (this.fire_point >= 10) {
                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9562"));
                    break;
                }
                else {
                    this.hit_okay = false;
                    this.counter_alt = 0;
                    this.counter_alt2 = 0;
                    this.mimic_quest_order = -1;
                    if (this.round % 4 == 0) {
                        this.critical_hit = 0;
                    }
                    if (this.fire_point >= 7) {
                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$12097"));
                    }
                    else {
                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9548"));
                    }
                    Thread.sleep(4000L);
                    int score_before = this.quest.get_score();
                    int type = this.randomHintSeaMonster();
                    if (type == 1) {
                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9541"));
                        this.sendPacketsToAll(new S_EffectLocation(32801, 32789, 8142));
                        this.sendPacketsToAll(new S_EffectLocation(32798, 32820, 8142));
                    }
                    else if (type == 2) {
                        if (++this.counter1 < 3) {
                            this.sendPacketsToAll(new S_PacketBoxGree(2, "$9542"));
                            this.spawnSeaMonster(97097);
                            Thread.sleep(10000L);
                        }
                    }
                    else if (type == 3) {
                        if (++this.counter2 < 3) {
                            this.sendPacketsToAll(new S_PacketBoxGree(2, "$9543"));
                            this.spawnSeaMonster(97098);
                            Thread.sleep(10000L);
                        }
                    }
                    else if (type == 4 && ++this.counter3 < 3) {
                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9544"));
                        this.sendPacketsToAll(new S_EffectLocation(32800, 32794, 8241));
                        Thread.sleep(5000L);
                        this.sendPacketsToAll(new S_EffectLocation(32800, 32794, 8241));
                    }
                    Thread.sleep(10000L);
                    if (this.round >= 12) {
                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9556"));
                        Thread.sleep(3000L);
                        int locx = this.fire_order ? 32796 : 32802;
                        int locy = 32824;
                        this.ship = L1SpawnUtil.spawn(97099, new L1Location(locx, locy, this.quest.get_mapid()), 0, this.quest.get_id());
                        this.ship.WORK.work(this.ship);
                    }
                    else {
                        if (this.counter1 >= 3 || this.counter2 >= 3 || this.counter3 >= 3) {
                            this.sendPacketsToAll(new S_PacketBoxGree(2, "$9549"));
                            Thread.sleep(4000L);
                            this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f=$" + (9608 + this.round)));
                            this.sendPacketsToAll(new S_PacketBoxGree(1));
                            Thread.sleep(3000L);
                            this.sendPacketsToAll(new S_PacketBoxGree(2, "$9558"));
                            if (this.counter1 >= 3) {
                                for (int i = 0, size = 3; i < size; ++i) {
                                    L1Location new_loc = this.loc.randomLocation(5, true);
                                    L1SpawnUtil.spawn(97095, new_loc, 0, this.quest.get_id()).set_quest_id(56254);
                                }
                                this.counter1 = 0;
                                Thread.sleep(20000L);
                            }
                            else if (this.counter2 >= 3) {
                                for (int i = 0, size = 3; i < size; ++i) {
                                    L1Location new_loc = this.loc.randomLocation(5, true);
                                    L1SpawnUtil.spawn(97096, new_loc, 0, this.quest.get_id()).set_quest_id(56255);
                                }
                                this.counter2 = 0;
                                Thread.sleep(20000L);
                            }
                            else if (this.counter3 >= 3) {
                                L1SpawnUtil.spawn(97103, new L1Location(32795, 32795, this.loc.getMapId()), 0, this.quest.get_id());
                                L1SpawnUtil.spawn(97104, new L1Location(32804, 32796, this.loc.getMapId()), 0, this.quest.get_id());
                                Thread.sleep(4000L);
                                L1NpcInstance squid = L1SpawnUtil.spawn(97105, new L1Location(32800, 32794, this.loc.getMapId()), 0, this.quest.get_id());
                                this.counter3 = 0;
                                Thread.sleep(5000L);
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f=$10720"));
                                Thread.sleep(55000L);
                                boolean checkNotDead = false;
                                for (L1NpcInstance npc : this.quest.npcList()) {
                                    if (npc.getNpcId() == 97105 && !npc.isDead()) {
                                        checkNotDead = true;
                                        break;
                                    }
                                }
                                if (!checkNotDead) {
                                    L1SpawnUtil.spawn(97118, new L1Location(32796, 32803, this.quest.get_mapid()), 0, this.quest.get_id()).set_quest_id(49311);
                                }
                                else {
                                    squid.set_spawnTime(1);
                                }
                                Thread.sleep(10000L);
                            }
                        }
                        else {
                            if (this.check_power >= 120 || Chapter02R._random.nextInt(5) == 0) {
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9588"));
                                this.mob_power = Math.min(this.mob_power + 1, 2);
                            }
                            else if (this.check_power < 60 || Chapter02R._random.nextInt(5) == 0) {
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9589"));
                                this.mob_power = Math.max(this.mob_power - 1, 0);
                            }
                            else {
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9550"));
                            }
                            this.check_power = 0;
                            this.randomMagicCircle();
                            Thread.sleep(10000L);
                            this.defenseFailed();
                            Thread.sleep(2000L);
                            this.randomMagicCircle();
                            Thread.sleep(6000L);
                            this.sendPacketsToAll(new S_PacketBoxGree(2, "$9551"));
                            this.spawnEnemyShip();
                            Thread.sleep(4000L);
                            this.defenseFailed();
                            Thread.sleep(2000L);
                            this.randomMagicCircle();
                            Thread.sleep(10000L);
                            this.defenseFailed();
                            this.counter_alt = 0;
                            this.counter_alt2 = 0;
                        }
                        for (int i = 0; i < 300; ++i) {
                            Thread.sleep(500L);
                            if (this.ship_step == 0) {
                                this.ship_step = -1;
                                this.count_down = 60;
                                this.sendPacketsToAll(new S_PacketBoxGree(2));
                                if (this.door.getCurrentHp() > 377) {
                                    this.door.setCurrentHp(this.door.getCurrentHp() - 377);
                                    int nowStatus = 36 - this.door.getCurrentHp() / 1000;
                                    if (this.door.getStatus() != nowStatus) {
                                        this.door.setStatus(Math.min(nowStatus, 36));
                                        this.door.broadcastPacketAll(new S_DoActionGFX(this.door.getId(), this.door.getStatus()));
                                    }
                                }
                                if (this.round < 12) {
                                    if (this.ship.getGfxId() == 8263) {
                                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9552"));
                                    }
                                    else if (this.ship.getGfxId() == 8260) {
                                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9553"));
                                    }
                                    else if (this.ship.getGfxId() == 8166) {
                                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9554"));
                                    }
                                    else {
                                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9555"));
                                    }
                                }
                            }
                            else if (this.ship_step == 1) {
                                this.ship_step = -1;
                                Thread.sleep(2000L);
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f=$" + (9608 + this.round)));
                                this.sendPacketsToAll(new S_PacketBoxGree(1));
                                Thread.sleep(4000L);
                                if (this.critical_hit >= 3) {
                                    this.sendPacketsToAll(new S_PacketBoxGree(2, "$9559"));
                                    Thread.sleep(2000L);
                                    this.portal = L1SpawnUtil.spawn(97111, new L1Location(32799, 32809, this.quest.get_mapid()), 0, this.quest.get_id());
                                }
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f=$9563"));
                                this.quest.add_score(Chapter02R._random.nextInt(30));
                                if (this.round >= 12) {
                                    if (this.quest.get_score() < 1000) {
                                        this.quest.spawnQuestMob(252);
                                    }
                                    else if (this.quest.get_score() < 1200) {
                                        this.quest.spawnQuestMob(253);
                                    }
                                    else if (this.quest.get_score() < 2000) {
                                        this.quest.spawnQuestMob(254);
                                    }
                                    else {
                                        this.quest.spawnQuestMob(255);
                                    }
                                }
                                if (this.portal == null) {
                                    this.quest.spawnQuestMob(1 + this.mob_power + (this.round - 1) * 10, 4);
                                }
                                else {
                                    int ship_type = this.getCabinLocation();
                                    if (ship_type == 1) {
                                        this.quest.spawnQuestMob(191 + this.mob_power, 4);
                                        this.quest.spawnQuestMob(201 + this.mob_power, 4);
                                    }
                                    else if (ship_type == 2) {
                                        this.quest.spawnQuestMob(211 + this.mob_power, 4);
                                        this.quest.spawnQuestMob(221 + this.mob_power, 4);
                                    }
                                    else if (ship_type == 3) {
                                        this.quest.spawnQuestMob(231 + this.mob_power, 4);
                                        this.quest.spawnQuestMob(241 + this.mob_power, 4);
                                    }
                                }
                                Thread.sleep(15000L);
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f=$9564"));
                                if (this.portal == null) {
                                    this.quest.spawnQuestMob(4 + this.mob_power + (this.round - 1) * 10, 4);
                                }
                                else {
                                    int ship_type = this.getCabinLocation();
                                    if (ship_type == 1) {
                                        this.quest.spawnQuestMob(194 + this.mob_power, 4);
                                        this.quest.spawnQuestMob(204 + this.mob_power, 4);
                                    }
                                    else if (ship_type == 2) {
                                        this.quest.spawnQuestMob(214 + this.mob_power, 4);
                                        this.quest.spawnQuestMob(224 + this.mob_power, 4);
                                    }
                                    else if (ship_type == 3) {
                                        this.quest.spawnQuestMob(234 + this.mob_power, 4);
                                        this.quest.spawnQuestMob(244 + this.mob_power, 4);
                                    }
                                }
                                Thread.sleep(15000L);
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f=$9565"));
                                if (this.portal == null) {
                                    this.quest.spawnQuestMob(7 + this.mob_power + (this.round - 1) * 10, 4);
                                }
                                else {
                                    int ship_type = this.getCabinLocation();
                                    if (ship_type == 1) {
                                        this.quest.spawnQuestMob(197 + this.mob_power, 4);
                                        this.quest.spawnQuestMob(207 + this.mob_power, 4);
                                    }
                                    else if (ship_type == 2) {
                                        this.quest.spawnQuestMob(217 + this.mob_power, 4);
                                        this.quest.spawnQuestMob(227 + this.mob_power, 4);
                                    }
                                    else if (ship_type == 3) {
                                        this.quest.spawnQuestMob(237 + this.mob_power, 4);
                                        this.quest.spawnQuestMob(247 + this.mob_power, 4);
                                    }
                                }
                                Thread.sleep(15000L);
                                int this_round = (this.round - 1) * 10;
                                for (int j = 0, k = 60; j < k; ++j) {
                                    Thread.sleep(1000L);
                                    boolean isRemainMob = false;
                                    for (L1MonsterInstance mob : WorldMob.get().getVisibleMob(this.party.getLeader())) {
                                        if (!mob.isDead()) {
                                            isRemainMob = true;
                                            break;
                                        }
                                    }
                                    if (!isRemainMob) {
                                        this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f3$9560"));
                                        this.quest.add_score(Math.max(Chapter02R._random.nextInt(k) - j, 5));
                                        boolean checkOkayInFirstTime = false;
                                        int mobId3;
                                        int mobId2;
                                        int mobId1;
                                        if (this.squid_version) {
                                            mobId1 = (mobId2 = (mobId3 = this.quest.spawnQuestMob(this_round, 0, 0)));
                                        }
                                        else if (Chapter02R._random.nextInt(100) < 30) {
                                            if (this.mimic_quest_order <= -1) {
                                                this.mimic_quest_order = 2 << Chapter02R._random.nextInt(3);
                                            }
                                            if (this.mimic_quest_order == 2) {
                                                mobId1 = this.quest.spawnQuestMob(this_round, 0, 0);
                                                mobId3 = (mobId2 = this.quest.spawnQuestMob(this_round, mobId1, 0));
                                            }
                                            else if (this.mimic_quest_order == 4) {
                                                mobId3 = this.quest.spawnQuestMob(this_round, 0, 0);
                                                mobId1 = (mobId2 = this.quest.spawnQuestMob(this_round, mobId3, 0));
                                            }
                                            else if (this.mimic_quest_order == 8) {
                                                mobId2 = this.quest.spawnQuestMob(this_round, 0, 0);
                                                mobId1 = (mobId3 = this.quest.spawnQuestMob(this_round, mobId2, 0));
                                            }
                                            else {
                                                mobId1 = this.quest.spawnQuestMob(this_round, 0, 0);
                                                mobId3 = this.quest.spawnQuestMob(this_round, mobId1, 0);
                                                mobId2 = this.quest.spawnQuestMob(this_round, mobId1, mobId3);
                                            }
                                        }
                                        else {
                                            mobId1 = this.quest.spawnQuestMob(this_round, 0, 0);
                                            mobId3 = this.quest.spawnQuestMob(this_round, mobId1, 0);
                                            mobId2 = this.quest.spawnQuestMob(this_round, mobId1, mobId3);
                                        }
                                        L1Location new_loc_1 = new L1Location(32793, 32800, this.quest.get_mapid()).randomLocation(4, true);
                                        L1Location new_loc_2 = new L1Location(32802, 32800, this.quest.get_mapid()).randomLocation(4, true);
                                        L1Location new_loc_3 = new L1Location(32799, 32805, this.quest.get_mapid()).randomLocation(4, true);
                                        this.sendPacketsToAll(new S_EffectLocation(new_loc_1.getX(), new_loc_1.getY(), 7930));
                                        this.sendPacketsToAll(new S_EffectLocation(new_loc_2.getX(), new_loc_2.getY(), 7930));
                                        this.sendPacketsToAll(new S_EffectLocation(new_loc_3.getX(), new_loc_3.getY(), 7930));
                                        L1NpcInstance mob2 = L1SpawnUtil.spawn(mobId1, new_loc_1, 0, this.quest.get_id());
                                        L1NpcInstance mob3 = L1SpawnUtil.spawn(mobId3, new_loc_2, 0, this.quest.get_id());
                                        L1NpcInstance mob4 = L1SpawnUtil.spawn(mobId2, new_loc_3, 0, this.quest.get_id());
                                        int m = 0;
                                        int p = 12;
                                        while (m < p) {
                                            Thread.sleep(500L);
                                            if (this.ship_step == 2) {
                                                break;
                                            }
                                            else {
                                                if (!checkOkayInFirstTime && (mob2.isDead() || mob3.isDead() || mob4.isDead())) {
                                                    checkOkayInFirstTime = true;
                                                    if ((this.mimic_quest_order == 2 && mob2.isDead()) || (this.mimic_quest_order == 4 && mob3.isDead()) || (this.mimic_quest_order == 8 && mob4.isDead() && ++this.mimic_quest_count >= 4)) {
                                                        this.mimic_quest_count = 0;
                                                        this.squid_version = true;
                                                    }
                                                }
                                                ++m;
                                            }
                                        }
                                    }
                                }
                            }
                            else if (this.ship_step == 2) {
                                this.ship_step = -1;
                                if (this.portal == null) {
                                    break;
                                }
                                else {
                                    this.portal.deleteMe();
                                    this.portal = null;
                                    for (L1PcInstance pc : this.quest.pcList()) {
                                        if (!pc.getLocation().isInScreen(this.loc)) {
                                            this.teleport(pc, -1);
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (this.round <= 10) {
                        this.quest.add_score(Chapter02R._random.nextInt(5) + 11);
                        this.sendPacketsToAll(new S_PacketBoxGree(4));
                        this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f3$9608"));
                        Thread.sleep(3000L);
                        L1PcInstance partyMember = this.party.partyUser();
                        if (partyMember != null) {
                            this.quest_pc = partyMember;
                            L1Location new_loc = Chapter02R.CABIN_LOC_LIST[0];
                            L1Location new_loc2 = new L1Location(new_loc.getX() + 6, new_loc.getY() - 6, new_loc.getMapId());
                            Object hardin = L1SpawnUtil.spawn(97119, new_loc2, 4, this.quest.get_id());
                            ((L1NpcInstance)hardin).set_spawnTime(40);
                            L1SpawnUtil.spawn(97120, new_loc2, 4, this.quest.get_id()).set_spawnTime(60);
                            L1NpcInstance mimic_1 = L1SpawnUtil.spawn(97121, new L1Location(new_loc.getX() - 3, new_loc.getY() - 4, new_loc.getMapId()), 0, this.quest.get_id());
                            mimic_1.set_quest_id(2);
                            mimic_1.set_spawnTime(40);
                            L1NpcInstance mimic_2 = L1SpawnUtil.spawn(97121, new L1Location(new_loc.getX() + 6, new_loc.getY() - 2, new_loc.getMapId()), 0, this.quest.get_id());
                            mimic_2.set_quest_id(4);
                            mimic_2.set_spawnTime(40);
                            L1NpcInstance mimic_3 = L1SpawnUtil.spawn(97121, new L1Location(new_loc.getX() - 1, new_loc.getY() + 5, new_loc.getMapId()), 0, this.quest.get_id());
                            mimic_3.set_quest_id(8);
                            mimic_3.set_spawnTime(40);
                            this.teleport(partyMember, 0);
                            Thread.sleep(2000L);
                            ((L1NpcInstance)hardin).broadcastPacketX10(new S_NpcChat(((L1NpcInstance)hardin).getId(), "$12122"));
                            Thread.sleep(3000L);
                            this.sendPacketsToAll(new S_PacketBoxGree(2, "$9606"));
                            ((L1NpcInstance)hardin).broadcastPacketX10(new S_NpcChat(((L1NpcInstance)hardin).getId(), "$12123"));
                            Thread.sleep(3000L);
                            ((L1NpcInstance)hardin).broadcastPacketX10(new S_NpcChat(((L1NpcInstance)hardin).getId(), "$12124"));
                        }
                        if (this.quest_pc != null) {
                            int l = 0;
                            while (l < 15) {
                                Thread.sleep(1000L);
                                if (this.quest_pc.getLocation().isInScreen(this.loc)) {
                                    this.quest.add_score(Math.max(Chapter02R._random.nextInt(35) - l * 2, 0));
                                    break;
                                }
                                else {
                                    if (l == 7) {
                                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9545"));
                                    }
                                    else if (l == 14) {
                                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9546"));
                                        Thread.sleep(4000L);
                                        if (!this.quest_pc.getLocation().isInScreen(this.loc)) {
                                            this.teleport(this.quest_pc, -1);
                                        }
                                        Thread.sleep(4000L);
                                        break;
                                    }
                                    ++l;
                                }
                            }
                        }
                        if (this.check_power <= 0) {
                            this.check_power = this.quest.get_score() - score_before;
                        }
                    }
                    else if (this.round >= 12) {
                        int i2 = 0;
                        while (i2 < 100) {
                            Thread.sleep(6000L);
                            if (this.fire_point >= 10) {
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9587"));
                                Thread.sleep(4000L);
                                this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f3$9586"));
                                break;
                            }
                            else {
                                int mob_count = 0;
                                Object hardin = WorldMob.get().getVisibleMob(this.party.getLeader()).iterator();
                                while (((Iterator)hardin).hasNext()) {
                                    L1MonsterInstance mob5 = (L1MonsterInstance)((Iterator)hardin).next();
                                    if (!mob5.isDead()) {
                                        ++mob_count;
                                    }
                                }
                                if (mob_count <= 0) {
                                    int bonus_box_id = Math.min(this.quest.get_score() / 100, 16);
                                    L1NpcInstance mimic = L1SpawnUtil.spawn(97122, new L1Location(32796, 32803, this.quest.get_mapid()), 0, this.quest.get_id());
                                    if (bonus_box_id <= 0) {
                                        break;
                                    }
                                    else {
                                        mimic.set_quest_id(56235 + bonus_box_id);
                                        this.send_gift = true;
                                        break;
                                    }
                                }
                                else {
                                    if (mob_count <= 4) {
                                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9585"));
                                    }
                                    ++i2;
                                }
                            }
                        }
                    }
                    ++this.round;
                }
            }
            if (this.round >= 12 && this.fire_point < 10) {
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9579"));
                Thread.sleep(4000L);
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9580"));
                Thread.sleep(4000L);
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9581"));
                Thread.sleep(4000L);
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9582"));
                Thread.sleep(4000L);
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9583"));
                Thread.sleep(4000L);
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9584"));
                Thread.sleep(10000L);
                int score = this.quest.get_score();
                int rankId = BoardOrimReading.get().writeTopic(score, this.party.getLeader().getName(), this.party.getPartyMembers());
                List<L1Rank> totalList = BoardOrimReading.get().getTotalList();
                int totalSize = 0;
                for (int i2 = 0, r = 5, n2 = totalList.size(); i2 < r && i2 < n2; ++i2) {
                    L1Rank rank = totalList.get(i2);
                    if (rank != null) {
                        totalSize += rank.getMemberSize();
                    }
                }
                this.sendPacketsToAll(new S_PacketBoxGree(totalList, totalSize, rankId, score));
            }
            Thread.sleep(15000L);
            this.clearAllObject();
        }
        catch (Exception e) {
            Chapter02R._log.error("歐林海戰副本發生錯誤", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            this.startR();
        }
    }
    
    public final void calcScore() {
        if (this.old_score < this.quest.get_score()) {
            this.old_score = this.quest.get_score();
            for (L1PcInstance pc : this.quest.pcList()) {
                pc.sendPackets(new S_PacketBoxGree(4, String.valueOf(this.old_score)));
            }
        }
        if (this.count_down > 0) {
            --this.count_down;
            if (this.count_down == 0) {
                boolean isRemainMob = false;
                for (L1MonsterInstance mob : WorldMob.get().getVisibleMob(this.party.getLeader())) {
                    if (!mob.isDead()) {
                        isRemainMob = true;
                        break;
                    }
                }
                if (isRemainMob) {
                    this.sendPacketsToAll(new S_PacketBoxGree(1));
                    L1Location new_loc = this.loc.randomLocation(8, true);
                    L1SpawnUtil.spawn(Chapter02R._random.nextInt(5) + 97112, new_loc, 0, this.quest.get_id());
                    if (++this.fire_point < 10) {
                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9561"));
                        this.count_down = 10;
                    }
                    else {
                        this.sendPacketsToAll(new S_PacketBoxGree(2, "$9562"));
                    }
                }
            }
        }
    }
    
    private final int randomHintSeaMonster() {
        if (this.squid_version) {
            this.squid_version = false;
            return 4;
        }
        else {
            int chance = Chapter02R._random.nextInt(100);
            if (chance < 35) {
                return 1;
            }
            else if (chance < 65) {
                return 2;
            }
            else if (chance < 95) {
                return 3;
            }
            else {
                return 4;
            }
        }
    }
    
    private final void spawnSeaMonster(int npcId) {
        L1NpcInstance monster_0 = L1SpawnUtil.spawn(npcId, new L1Location(32778, 32800, this.quest.get_mapid()), 0, this.quest.get_id());
        monster_0.set_quest_id(0);
        monster_0.WORK.work(monster_0);
        L1NpcInstance monster_2 = L1SpawnUtil.spawn(npcId, new L1Location(32801, 32784, this.quest.get_mapid()).randomLocation(1, true), 0, this.quest.get_id());
        monster_2.set_quest_id(1);
        monster_2.WORK.work(monster_2);
        L1NpcInstance monster_3 = L1SpawnUtil.spawn(npcId, new L1Location(32810, 32787, this.quest.get_mapid()).randomLocation(1, true), 0, this.quest.get_id());
        monster_3.set_quest_id(2);
        monster_3.WORK.work(monster_3);
        for (int i = 0, n = 3; i < n; ++i) {
            L1NpcInstance spawn_monster = L1SpawnUtil.spawn(npcId, new L1Location(32793, 32803, this.quest.get_mapid()).randomLocation(2, true), 0, this.quest.get_id());
            spawn_monster.set_quest_id(3 + i);
            spawn_monster.WORK.work(spawn_monster);
        }
    }
    
    private final void randomMagicCircle() {
        this.defense_okay = false;
        L1Location new_loc = this.loc.randomLocation(7, true);
        L1NpcInstance npc = L1SpawnUtil.spawnT(97109, new_loc.getX(), new_loc.getY(), (short)new_loc.getMapId(), 0, 12);
        npc.set_showId(this.quest.get_id());
        this.quest.addNpc(npc);
        for (int i = 0, n = this.quest.size() - 2; i < n; ++i) {
            new_loc = this.loc.randomLocation(7, true);
            L1NpcInstance npc2 = L1SpawnUtil.spawnT(97110, new_loc.getX(), new_loc.getY(), (short)new_loc.getMapId(), 0, 12);
            npc2.set_showId(this.quest.get_id());
            this.quest.addNpc(npc2);
        }
    }
    
    public final void attack() {
        L1NpcInstance checkNpc = null;
        for (L1NpcInstance npc : this.quest.npcList()) {
            if (npc.getGfxId() == 8322) {
                for (L1PcInstance pc : this.quest.pcList()) {
                    if (pc.getLocation().isSamePoint(npc.getLocation())) {
                        checkNpc = npc;
                        break;
                    }
                }
                break;
            }
        }
        if (checkNpc != null) {
            if (this.fire_order) {
                this.npc1.broadcastPacketAll(new S_DoActionGFX(this.npc1.getId(), 2));
                if (Chapter02R._random.nextInt(100) < 75) {
                    this.sendPacketsToAll(new S_EffectLocation(32790, Chapter02R._random.nextInt(5) + 32816, 8233));
                }
            }
            else {
                this.npc2.broadcastPacketAll(new S_DoActionGFX(this.npc2.getId(), 2));
                if (Chapter02R._random.nextInt(100) < 75) {
                    this.sendPacketsToAll(new S_EffectLocation(32801, Chapter02R._random.nextInt(5) + 32816, 8233));
                }
            }
            this.fire_order = !this.fire_order;
            if (this.round <= 0) {
                return;
            }
            else {
                ++this.counter_alt;
            }
        }
        else if (this.round <= 0) {
            return;
        }
        else {
            ++this.counter_alt2;
        }
        if (this.counter_alt > this.counter_alt2 + 5) {
            if (!this.hit_okay) {
                ++this.critical_hit;
            }
            this.hit_okay = true;
            this.counter_alt = 0;
            this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f3Critical HIT!"));
            this.quest.add_score(3);
        }
        else if (this.counter_alt > this.counter_alt2 + 4) {
            this.sendPacketsToAll(new S_PacketBoxGree(2, "Double HIT!"));
            this.quest.add_score(1);
        }
        else if (this.counter_alt > this.counter_alt2 + 2) {
            this.sendPacketsToAll(new S_PacketBoxGree(2, "\\f=HIT!"));
        }
    }
    
    public final void defense() {
        if (this.round <= 0) {
            return;
        }
        else {
            List<L1NpcInstance> checkList = new ArrayList();
            for (L1NpcInstance npc : this.quest.npcList()) {
                if (npc.getGfxId() == 8323) {
                    checkList.add(npc);
                }
            }
            if (!checkList.isEmpty()) {
                int checkCount = 0;
                for (L1NpcInstance npc2 : checkList) {
                    for (L1PcInstance pc : this.quest.pcList()) {
                        if (pc.getLocation().isSamePoint(npc2.getLocation())) {
                            ++checkCount;
                        }
                    }
                }
                if (checkCount >= this.quest.size() - 2) {
                    for (L1NpcInstance npc : checkList) {
                        npc.deleteMe();
                    }
                    if (this.defense_okay) {
                        return;
                    }
                    else {
                        this.defense_okay = true;
                        for (L1PcInstance pc2 : this.quest.pcList()) {
                            pc2.sendPackets(new S_SkillSound(pc2.getId(), 10165));
                        }
                        return;
                    }
                }
            }
            ++this.counter_alt2;
            return;
        }
    }
    
    private final void defenseFailed() {
        if (!this.defense_okay) {
            this.defense_okay = true;
            this.sendPacketsToAll(new S_EffectLocation(this.next_fire_loc.getX(), this.next_fire_loc.getY(), 762));
            try {
                Thread.sleep(500L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.sendPacketsToAll(new S_EffectLocation(this.next_fire_loc.getX(), this.next_fire_loc.getY(), 762));
            this.sendPacketsToAll(new S_PacketBoxGree(2));
            try {
                Thread.sleep(500L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.sendPacketsToAll(new S_EffectLocation(this.next_fire_loc.getX(), this.next_fire_loc.getY(), 762));
            try {
                Thread.sleep(500L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            L1SpawnUtil.spawn(Chapter02R._random.nextInt(5) + 97112, this.next_fire_loc, 0, this.quest.get_id());
            this.next_fire_loc = this.loc.randomLocation(8, true);
            if (++this.fire_point < 10) {
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9604"));
            }
            else {
                this.sendPacketsToAll(new S_PacketBoxGree(2, "$9562"));
            }
        }
    }
    
    public final int getCabinLocation() {
        if (this.ship == null) {
            return -1;
        }
        else {
            int type = 0;
            switch (this.ship.getGfxId()) {
                case 8164:
                case 8165:
                case 8166: {
                    type = 1;
                    break;
                }
                case 8260: {
                    type = 2;
                    break;
                }
                case 8263: {
                    type = 3;
                    break;
                }
            }
            return type;
        }
    }
    
    public final void teleport(L1PcInstance pc, int type) {
        if (pc.getId() == this.party.getLeader().getId() || pc.getMapId() != this.loc.getMapId() || !this.quest.pcList().contains(pc)) {
            return;
        }
        else {
            if (type <= -1) {
                L1Location new_loc = this.loc.randomLocation(5, true);
                L1Teleport.teleport(pc, new_loc, pc.getHeading(), true);
            }
            else if (type < Chapter02R.CABIN_LOC_LIST.length) {
                if (type != 0) {
                    if (this.portal != null && pc.getLocation().getTileLineDistance(this.portal.getLocation()) > 1) {}
                }
                else {
                    L1Location new_loc = Chapter02R.CABIN_LOC_LIST[type].randomLocation(5, true);
                    L1Teleport.teleport(pc, new_loc, pc.getHeading(), true);
                }
            }
            return;
        }
    }
    
    private final void spawnEnemyShip() {
        L1NpcInstance spawn_ship = null;
        int locx = this.fire_order ? 32796 : 32802;
        int locy = 32824;
        if (this.critical_hit >= 4) {
            if (this.round == 4) {
                spawn_ship = L1SpawnUtil.spawn(97107, new L1Location(locx, locy, this.quest.get_mapid()), 0, this.quest.get_id());
                this.quest.spawnQuestMob(257);
            }
            else if (this.round == 8) {
                spawn_ship = L1SpawnUtil.spawn(97106, new L1Location(locx, locy, this.quest.get_mapid()), 0, this.quest.get_id());
                this.quest.spawnQuestMob(258);
            }
        }
        if (spawn_ship == null) {
            spawn_ship = L1SpawnUtil.spawn(Chapter02R._random.nextInt(3) + 97099, new L1Location(locx, locy, this.quest.get_mapid()), 0, this.quest.get_id());
            this.quest.spawnQuestMob(256);
        }
        this.ship = spawn_ship;
        this.ship.WORK.work(this.ship);
    }
    
    public final void checkQuestOrder(L1PcInstance pc, int quest_id) {
        this.mimic_quest_order -= quest_id;
        if (this.mimic_quest_order <= 0) {
            if (this.mimic_quest_order == 0 && ++this.mimic_quest_count >= 4) {
                this.mimic_quest_count = 0;
                this.squid_version = true;
            }
            this.quest.get_orimR().teleport(pc, -1);
        }
    }
    
    public final void shipReturnStep(int step) {
        this.ship_step = step;
    }
    
    private final void sendPacketsToAll(ServerBasePacket packet) {
        if (this.quest.size() > 0) {
            for (L1PcInstance pc : this.quest.pcList()) {
                pc.sendPackets(packet);
            }
        }
    }
    
    private final void clearAllObject() {
        for (L1Object obj : World.get().getVisibleObjects(this.quest.get_mapid()).values()) {
            if (obj.get_showId() == this.quest.get_id()) {
                if (obj instanceof L1EffectInstance) {
                    L1EffectInstance mob = (L1EffectInstance)obj;
                    mob.deleteMe();
                }
                else if (obj instanceof L1MerchantInstance) {
                    L1MerchantInstance mob2 = (L1MerchantInstance)obj;
                    mob2.setreSpawn(false);
                    mob2.deleteMe();
                }
                else if (obj instanceof L1MonsterInstance) {
                    L1MonsterInstance mob3 = (L1MonsterInstance)obj;
                    if (!mob3.isDead()) {
                        mob3.setDead(true);
                        mob3.setStatus(8);
                        mob3.setCurrentHpDirect(0);
                        mob3.deleteMe();
                    }
                    else {
                        continue;
                    }
                }
                else if (obj instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)obj;
                    L1Location loc = new L1Location(32580, 32931, 0).randomLocation(5, true);
                    L1Teleport.teleport(pc, loc, pc.getHeading(), true);
                }
                else if (obj instanceof L1Inventory) {
                    L1Inventory inventory = (L1Inventory)obj;
                    inventory.clearItems();
                }
                else {
                    continue;
                }
            }
        }
    }
}

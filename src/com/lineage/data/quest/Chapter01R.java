package com.lineage.data.quest;

import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_MoveCharPacket;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1GroundInventory;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.L1Teleport;
import com.lineage.data.QuestClass;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.L1SpawnUtil;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Party;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.concurrent.ConcurrentHashMap;
import com.lineage.server.model.Instance.L1FieldObjectInstance;
import com.lineage.server.model.Instance.L1DoorInstance;
import java.util.ArrayList;
import com.lineage.server.model.L1Location;
import java.util.Random;
import org.apache.commons.logging.Log;

public class Chapter01R implements Runnable
{
    private static final Log _log;
    public static final Random _random;
    private static final int _mapid = 9000;
    private static final int _count_spawn = 25;
    private static final int _sleep = 5;
    private static final byte[] _h_x;
    private static final byte[] _h_y;
    private static int[] _spawn_01;
    private static int[] _spawn_02;
    private static int[] _attack_01;
    private static int[] _attack_02;
    private static int[] _attack_03;
    private static int[] _attack_04;
    private static int[] _attack_05;
    private static int[] _attack_06;
    private static int[] _attack_07;
    private static int[] _attack_08;
    private static int[] _attack_09;
    private static int[] _attack_10;
    private static int[] _attack_11;
    private static int[] _attack_12;
    private static int[][] _light_loc1;
    private static int[] _light_loc2;
    private static final L1Location _loc1;
    private static final L1Location _loc2;
    private static final L1Location _loc3;
    private static final L1Location _loc4;
    private final ArrayList<L1DoorInstance> _doors;
    private final ArrayList<L1FieldObjectInstance> _dooropens;
    private final ConcurrentHashMap<Integer, L1NpcInstance> _npclist;
    private int _user_count;
    private int _showid;
    private int _qid;
    private L1NpcInstance _hardin;
    private int _hardin_tak;
    private L1NpcInstance _cerenis;
    private int _cerenis_tak;
    private L1Party _party;
    private boolean _start;
    private boolean _msgInLeader;
    private boolean _gfx_01;
    private int _door_01;
    private int _door_02;
    private int _door_03;
    private int _door_04;
    private int _door_04open;
    public boolean DOOR_1;
    public boolean DOOR_2;
    public boolean DOOR_3;
    public boolean DOOR_4;
    public boolean DOOR_4OPEN;
    private boolean _boss_a_death;
    private boolean _boss_b_death;
    private int _spawn;
    private int _time;
    private int _time12;
    private int _time_over;
    private int _mode_s;
    private int _mode12;
    private int _count_01;
    private int _count_02;
    private int _count_03;
    private int _count_04;
    private int _count_05;
    private int _count_06;
    private int _count_07;
    private int _count_08;
    private int _count_09;
    private int _count_10;
    private int _count_11;
    private int _count_12;
    private int _user_02;
    private int _alt2_02;
    private int _leaderTime;
    
    static {
        _log = LogFactory.getLog(Chapter01R.class);
        _random = new Random();
        _h_x = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        _h_y = new byte[] { -1, -1, 0, 1, 1, 1, 0, -1 };
        Chapter01R._spawn_01 = new int[] { 91340, 91341, 91342 };
        Chapter01R._spawn_02 = new int[] { 91265, 91266, 91267, 91268 };
        Chapter01R._attack_01 = new int[] { 91265, 91266, 91267, 91268, 91270 };
        Chapter01R._attack_02 = new int[] { 91265, 91266, 91267, 91268, 91270, 91271 };
        Chapter01R._attack_03 = new int[] { 91270, 91271, 91272, 91273, 91268 };
        Chapter01R._attack_04 = new int[] { 91274, 91275, 91276, 91272, 91273, 91268 };
        Chapter01R._attack_05 = new int[] { 91274, 91275, 91276, 91277, 91278, 91279 };
        Chapter01R._attack_06 = new int[] { 91280, 91282, 91281, 91282, 91283, 91284 };
        Chapter01R._attack_07 = new int[] { 91286, 91287, 91288, 91300, 91301, 91302 };
        Chapter01R._attack_08 = new int[] { 91299, 91300, 91302, 91280, 91279, 91305 };
        Chapter01R._attack_09 = new int[] { 91299, 91300, 91302, 91283, 91284, 91305 };
        Chapter01R._attack_10 = new int[] { 91299, 91300, 91302, 91301, 91302, 91303, 91305 };
        Chapter01R._attack_11 = new int[] { 91299, 91300, 91302, 91301, 91302, 91303, 91304 };
        Chapter01R._attack_12 = new int[] { 91299, 91300, 91302, 91301, 91302, 91303, 91305, 91304 };
        Chapter01R._light_loc1 = new int[][] { { 32803, 32866 }, { 32801, 32865 }, { 32800, 32864 }, { 32798, 32866 }, { 32799, 32872 }, { 32800, 32873 }, { 32803, 32873 } };
        Chapter01R._light_loc2 = new int[] { 32801, 32869 };
        _loc1 = new L1Location(32702, 32837, 9000);
        _loc2 = new L1Location(32714, 32836, 9000);
        _loc3 = new L1Location(32713, 32855, 9000);
        _loc4 = new L1Location(32699, 32856, 9000);
    }
    
    private void spawn_light() {
        int[][] light_loc1;
        for (int length = (light_loc1 = Chapter01R._light_loc1).length, i = 0; i < length; ++i) {
            int[] locyx = light_loc1[i];
            L1SpawnUtil.spawn(this._showid, 6708, locyx[0], locyx[1], 9000, 0);
        }
    }
    
    private void spawn_light_check() {
        int ch = 0;
        for (L1PcInstance pc : this._party.partyUsers().values()) {
            if (pc.getMapId() == 9000) {
                if (pc.getX() == Chapter01R._light_loc1[0][0] && pc.getY() == Chapter01R._light_loc1[0][1]) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7578));
                    ++ch;
                }
                if (pc.getX() == Chapter01R._light_loc1[1][0] && pc.getY() == Chapter01R._light_loc1[1][1]) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7578));
                    ++ch;
                }
                if (pc.getX() == Chapter01R._light_loc1[2][0] && pc.getY() == Chapter01R._light_loc1[2][1]) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7578));
                    ++ch;
                }
                if (pc.getX() == Chapter01R._light_loc1[3][0] && pc.getY() == Chapter01R._light_loc1[3][1]) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7578));
                    ++ch;
                }
                if (pc.getX() == Chapter01R._light_loc1[4][0] && pc.getY() == Chapter01R._light_loc1[4][1]) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7578));
                    ++ch;
                }
                if (pc.getX() == Chapter01R._light_loc1[5][0] && pc.getY() == Chapter01R._light_loc1[5][1]) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7578));
                    ++ch;
                }
                if (pc.getX() == Chapter01R._light_loc1[6][0] && pc.getY() == Chapter01R._light_loc1[6][1]) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7578));
                    ++ch;
                }
                else {
                    continue;
                }
            }
        }
        if (ch >= this._user_count) {
            L1SpawnUtil.spawn(this._showid, 6709, Chapter01R._light_loc2[0], Chapter01R._light_loc2[1], 9000, 0);
            this._hardin_tak = 52;
        }
    }
    
    private void spawn_light_check2() {
        for (L1PcInstance pc : this._party.partyUsers().values()) {
            if (pc.getMapId() == 9000 && pc.getX() == Chapter01R._light_loc2[0] && pc.getY() == Chapter01R._light_loc2[1]) {
                this.outWindows1();
                pc.sendPacketsAll(new S_SkillSound(pc.getId(), 7579));
                this._hardin_tak = 53;
            }
        }
    }
    
    public void boss_a_death() {
        try {
            this._boss_a_death = true;
            this.outMsgWindows("$8703");
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void boss_b_death() {
        try {
            this._boss_b_death = true;
            this.outMsgWindows("$7707");
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void add(L1NpcInstance value) {
        try {
            this._npclist.put(value.getId(), value);
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void remove(L1NpcInstance value) {
        try {
            this._npclist.remove((Integer)value.getId());
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void down_count(L1NpcInstance npc) {
        try {
            this.remove(npc);
            String wmsg = null;
            int i = this._npclist.size();
            switch (this._mode12) {
                case 1: {
                    this._count_01 = this._npclist.size();
                    break;
                }
                case 2: {
                    this._count_02 = this._npclist.size();
                    break;
                }
                case 3: {
                    this._count_03 = this._npclist.size();
                    break;
                }
                case 4: {
                    this._count_04 = this._npclist.size();
                    break;
                }
                case 5: {
                    this._count_05 = this._npclist.size();
                    break;
                }
                case 6: {
                    this._count_06 = this._npclist.size();
                    break;
                }
                case 7: {
                    this._count_07 = this._npclist.size();
                    break;
                }
                case 8: {
                    this._count_08 = this._npclist.size();
                    break;
                }
                case 9: {
                    this._count_09 = this._npclist.size();
                    break;
                }
                case 10: {
                    this._count_10 = this._npclist.size();
                    break;
                }
                case 11: {
                    this._count_11 = this._npclist.size();
                    break;
                }
                case 12: {
                    this._count_12 = this._npclist.size();
                    break;
                }
            }
            if (this._mode12 < 1) {
                return;
            }
            else if (this._mode12 > 12) {
                return;
            }
            else {
                switch (i) {
                    case 0: {
                        wmsg = "$8692";
                        break;
                    }
                    case 6: {
                        wmsg = "$8691";
                        break;
                    }
                    case 12: {
                        wmsg = "$8690";
                        break;
                    }
                    case 18: {
                        wmsg = "$8689";
                        break;
                    }
                }
                if (wmsg != null) {
                    this.outMsgWindows(wmsg);
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public Chapter01R(L1Party party, int qid, int i) {
        this._doors = new ArrayList();
        this._dooropens = new ArrayList();
        this._npclist = new ConcurrentHashMap();
        this._user_count = 4;
        this._showid = -1;
        this._qid = -1;
        this._hardin_tak = 0;
        this._cerenis_tak = 0;
        this._party = null;
        this._start = true;
        this._msgInLeader = false;
        this._gfx_01 = false;
        this._door_01 = 0;
        this._door_02 = 0;
        this._door_03 = 0;
        this._door_04 = 0;
        this._door_04open = 0;
        this.DOOR_1 = false;
        this.DOOR_2 = false;
        this.DOOR_3 = false;
        this.DOOR_4 = false;
        this.DOOR_4OPEN = false;
        this._boss_a_death = false;
        this._boss_b_death = false;
        this._spawn = 0;
        this._time = 0;
        this._time12 = 0;
        this._time_over = 0;
        this._mode_s = 0;
        this._mode12 = 0;
        this._count_01 = 0;
        this._count_02 = 0;
        this._count_03 = 0;
        this._count_04 = 0;
        this._count_05 = 0;
        this._count_06 = 0;
        this._count_07 = 0;
        this._count_08 = 0;
        this._count_09 = 0;
        this._count_10 = 0;
        this._count_11 = 0;
        this._count_12 = 0;
        this._user_02 = 0;
        this._alt2_02 = 0;
        this._leaderTime = 0;
        this._party = party;
        for (L1PcInstance pc : this._party.partyUsers().values()) {
            pc.set_hardinR(this);
            int count = 4;
            if (pc.getParty().isLeader(pc) && pc.isGm()) {
                count = 1;
            }
            this._user_count = count;
        }
        this._showid = qid;
        this._qid = i;
    }
    
    public void startR() {
        GeneralThreadPool.get().execute(this);
    }
    
    @Override
    public void run() {
        try {
            while (this._start) {
                this.checkParty();
                this.getLeader();
                this.getMembers();
                if (!this.checkParty()) {
                    this._start = false;
                }
                if (WorldQuest.get().get(this._showid) == null) {
                    this._start = false;
                }
                Thread.sleep(1000L);
                ++this._time;
            }
            Thread.sleep(3000L);
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this.outUser();
            this._doors.clear();
            this._dooropens.clear();
            this._npclist.clear();
        }
    }
    
    private boolean checkParty() {
        int user = 0;
        try {
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000) {
                    ++user;
                }
            }
        }
        catch (Exception ex) {}
        return user >= this._user_count;
    }
    
    public int get_time() {
        return this._time;
    }
    
    private void outMsgWindows(String msg) {
        try {
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000) {
                    if (this._msgInLeader) {
                        pc.sendPackets(new S_PacketBoxGree(2, msg));
                    }
                    else if (!this._party.isLeader(pc)) {
                        pc.sendPackets(new S_PacketBoxGree(2, msg));
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void outMsg(String msg) {
        try {
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000) {
                    pc.sendPackets(new S_NpcChat(this._hardin, msg));
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void outWindows2() {
        try {
            this.outWindows1();
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000) {
                    pc.sendPackets(new S_PacketBoxGree(1));
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void outWindows1() {
        try {
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000) {
                    pc.sendPackets(new S_PacketBoxGree(2));
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void getMembers() {
        try {
            if (this._hardin == null) {
                for (L1Object object : World.get().getVisibleObjects(this._party.getLeader())) {
                    if (object instanceof L1NpcInstance) {
                        L1NpcInstance npc = (L1NpcInstance)object;
                        if (npc.getNpcId() == 91330) {
                            this._hardin = npc;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            else {
                String msg = null;
                String wmsg = null;
                switch (this._hardin_tak) {
                    case 0: {
                        if (this._time >= 5) {
                            msg = "$8693";
                            this._hardin_tak = 1;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 1: {
                        if (this.all_ok()) {
                            this._hardin_tak = 12;
                            break;
                        }
                        else {
                            msg = "$8702";
                            this._hardin_tak = 2;
                            break;
                        }
                    }
                    case 2:
                    case 3:
                    case 4: {
                        if (this.all_ok()) {
                            this._hardin_tak = 12;
                            break;
                        }
                        else {
                            ++this._hardin_tak;
                            break;
                        }
                    }
                    case 5: {
                        if (this.all_ok()) {
                            this._hardin_tak = 12;
                            break;
                        }
                        else {
                            msg = "$7599";
                            this._hardin_tak = 6;
                            break;
                        }
                    }
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10: {
                        if (this.all_ok()) {
                            this._hardin_tak = 12;
                            break;
                        }
                        else {
                            ++this._hardin_tak;
                            break;
                        }
                    }
                    case 11: {
                        if (this.all_ok()) {
                            this._hardin_tak = 12;
                            break;
                        }
                        else {
                            msg = "$8694";
                            this._hardin_tak = 1;
                            break;
                        }
                    }
                    case 12: {
                        if (!this._gfx_01) {
                            L1SpawnUtil.spawn(this._showid, 7392, 32726, 32724, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7392, 32716, 32846, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32668, 32817, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32666, 32817, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32668, 32819, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32666, 32819, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32712, 32793, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32703, 32791, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32710, 32803, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32703, 32800, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32809, 32837, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32807, 32837, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32809, 32839, 9000, 0);
                            L1SpawnUtil.spawn(this._showid, 7480, 32807, 32839, 9000, 0);
                            L1QuestUser quest = WorldQuest.get().get(this._showid);
                            L1SpawnUtil.spawnDoor(quest, 10014, 92, 32664, 32807, (short)9000, 0);
                            L1SpawnUtil.spawnDoor(quest, 10015, 93, 32673, 32820, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10016, 92, 32725, 32795, (short)9000, 0);
                            L1SpawnUtil.spawnDoor(quest, 10017, 93, 32726, 32812, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10020, 93, 32723, 32848, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10019, 92, 32802, 32821, (short)9000, 0);
                            L1SpawnUtil.spawnDoor(quest, 10018, 93, 32705, 32816, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10021, 88, 32681, 32797, (short)9000, 0);
                            L1SpawnUtil.spawnDoor(quest, 10022, 88, 32699, 32803, (short)9000, 0);
                            L1SpawnUtil.spawnDoor(quest, 10023, 89, 32740, 32788, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10024, 89, 32750, 32793, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10025, 89, 32766, 32791, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10026, 88, 32803, 32832, (short)9000, 0);
                            L1SpawnUtil.spawnDoor(quest, 10027, 88, 32799, 32844, (short)9000, 0);
                            L1SpawnUtil.spawnDoor(quest, 10028, 88, 32803, 32862, (short)9000, 0);
                            L1SpawnUtil.spawnDoor(quest, 10029, 89, 32795, 32870, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10030, 89, 32740, 32872, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10031, 89, 32736, 32872, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10032, 89, 32732, 32872, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10033, 89, 32759, 32847, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10034, 89, 32672, 32858, (short)9000, 1);
                            L1SpawnUtil.spawnDoor(quest, 10035, 88, 32808, 32792, (short)9000, 0);
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32703, 32866, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32704, 32866, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32705, 32866, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32706, 32866, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32707, 32866, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32708, 32866, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32709, 32866, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32710, 32866, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32711, 32866, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32703, 32872, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32704, 32872, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32705, 32872, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32706, 32872, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32707, 32872, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32708, 32872, (short)9000, 0));
                            this._doors.add(L1SpawnUtil.spawnDoor(quest, 10036, 7536, 32709, 32872, (short)9000, 0));
                            L1SpawnUtil.spawn(70558, new L1Location(32674, 32856, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32678, 32856, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32680, 32856, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32682, 32856, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32683, 32857, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32683, 32859, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32683, 32861, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32671, 32864, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32671, 32865, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32673, 32868, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32673, 32869, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32672, 32873, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32670, 32863, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32666, 32863, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32721, 32842, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32721, 32834, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32737, 32845, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32735, 32845, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32733, 32845, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32731, 32845, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32729, 32845, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32727, 32845, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32734, 32840, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32732, 32840, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32730, 32840, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32728, 32840, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32729, 32832, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32734, 32832, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32742, 32832, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32760, 32828, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32751, 32828, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32749, 32828, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32747, 32828, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32760, 32833, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32754, 32842, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32754, 32855, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32760, 32863, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32744, 32860, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32736, 32860, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32733, 32858, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32733, 32858, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32729, 32858, 9000), 4, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32803, 32841, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32803, 32838, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32827, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32829, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32831, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32833, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32835, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32837, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32839, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32841, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32843, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32845, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32815, 32847, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32759, 32800, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32759, 32801, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32759, 32802, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32759, 32803, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32759, 32804, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32759, 32805, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32744, 32806, 9000), 6, this._showid);
                            L1SpawnUtil.spawn(70558, new L1Location(32744, 32807, 9000), 6, this._showid);
                            this._gfx_01 = true;
                        }
                        if (this.all_teleport()) {
                            this._hardin_tak = 13;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 13: {
                        wmsg = "$7597: $8695";
                        this._user_02 = 0;
                        this._alt2_02 = 0;
                        this._hardin_tak = 14;
                        break;
                    }
                    case 14: {
                        if (this.DOOR_1) {
                            this._hardin_tak = 15;
                            break;
                        }
                        else {
                            if (this.check_loc1()) {
                                this.outWindows1();
                                L1SpawnUtil.spawn(this._showid, 7392, 32667, 32818, 9000, 0);
                                this.DOOR_1 = true;
                            }
                            switch (++this._door_01) {
                                case 5: {
                                    wmsg = "$7597: $8696";
                                    break;
                                }
                                case 10: {
                                    wmsg = "$7597: $8697";
                                    break;
                                }
                                case 15: {
                                    wmsg = "$7597: $8698";
                                    break;
                                }
                                case 90: {
                                    wmsg = "$7560: $7618";
                                    ++this._mode_s;
                                    break;
                                }
                                case 120: {
                                    wmsg = "$7597: $7683";
                                    break;
                                }
                                case 125: {
                                    wmsg = "$7597: $7811";
                                    break;
                                }
                                case 240: {
                                    wmsg = "$7597: $7683";
                                    this._start = false;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 15: {
                        if (this.DOOR_2) {
                            this._hardin_tak = 16;
                            break;
                        }
                        else {
                            if (this.check_loc2()) {
                                this.outWindows1();
                                L1SpawnUtil.spawn(this._showid, 7392, 32708, 32797, 9000, 0);
                                this.DOOR_2 = true;
                            }
                            switch (++this._door_02) {
                                case 5: {
                                    wmsg = "$7597: $8699";
                                    break;
                                }
                                case 10: {
                                    wmsg = "$7597: $8700";
                                    break;
                                }
                                case 15: {
                                    wmsg = "$7597: $8701";
                                    break;
                                }
                                case 90: {
                                    wmsg = "$7560: $7627";
                                    ++this._mode_s;
                                    break;
                                }
                                case 120: {
                                    wmsg = "$7597: $7683";
                                    break;
                                }
                                case 125: {
                                    wmsg = "$7597: $7811";
                                    break;
                                }
                                case 240: {
                                    wmsg = "$7597: $7683";
                                    this._start = false;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 16: {
                        if (this.DOOR_3) {
                            this._hardin_tak = 17;
                            break;
                        }
                        else {
                            if (this.check_loc3()) {
                                this.outWindows1();
                                L1SpawnUtil.spawn(this._showid, 7392, 32808, 32838, 9000, 0);
                                L1SpawnUtil.spawn(this._showid, 7392, 32788, 32822, 9000, 0);
                                this.DOOR_3 = true;
                            }
                            switch (++this._door_03) {
                                case 5: {
                                    wmsg = "$7597: $7649";
                                    break;
                                }
                                case 10: {
                                    wmsg = "$7597: $7650";
                                    break;
                                }
                                case 15: {
                                    wmsg = "$7597: $7651";
                                    break;
                                }
                                case 90: {
                                    wmsg = "$7560: $7642";
                                    ++this._mode_s;
                                    break;
                                }
                                case 120: {
                                    wmsg = "$7597: $7683";
                                    break;
                                }
                                case 125: {
                                    wmsg = "$7597: $7811";
                                    break;
                                }
                                case 240: {
                                    wmsg = "$7597: $7683";
                                    this._start = false;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 17: {
                        if (this.DOOR_3) {
                            this._npclist.clear();
                            L1Location loc = new L1Location(32776, 32846, 9000);
                            for (int i = 0; i < 25; ++i) {
                                int npcid = Chapter01R._spawn_02[Chapter01R._random.nextInt(Chapter01R._spawn_02.length)];
                                int rr = Chapter01R._random.nextInt(4) + 4;
                                L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 0);
                                if (mob != null) {
                                    this.add(mob);
                                }
                            }
                            this._hardin_tak = 18;
                            this._mode12 = 0;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 18: {
                        if (this.DOOR_4) {
                            this._hardin_tak = 19;
                            break;
                        }
                        else {
                            if (this.check_loc4()) {
                                this.outWindows1();
                                this.DOOR_4 = true;
                            }
                            switch (++this._door_04) {
                                case 60: {
                                    wmsg = "$7560: $7642";
                                    break;
                                }
                                case 120: {
                                    wmsg = "$7597: $7683";
                                    break;
                                }
                                case 125: {
                                    wmsg = "$7597: $7811";
                                    break;
                                }
                                case 240: {
                                    wmsg = "$7597: $7683";
                                    this._start = false;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 19: {
                        if (this._msgInLeader) {
                            this._hardin_tak = 20;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 20: {
                        ++this._door_04open;
                        if (this.DOOR_4OPEN && this._door_04open >= 15) {
                            this._hardin_tak = 21;
                            this._time_over = 0;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 21: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._cerenis_tak == 0) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = 91337;
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                this._hardin_tak = 22;
                                this._mode12 = 1;
                                wmsg = "$7597: $7832";
                            }
                            else {
                                this._hardin_tak = 24;
                                wmsg = "$7597: $7663";
                            }
                            this._time12 = 0;
                            this._time_over = 0;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 22: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 23;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7639";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7702";
                                    break;
                                }
                                case 120: {
                                    wmsg = "$7597: $7682";
                                    break;
                                }
                                case 180: {
                                    wmsg = "$7597: $7811";
                                    break;
                                }
                                case 240: {
                                    wmsg = "$7597: $7683";
                                    this._hardin_tak = 23;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 23: {
                        this._start = false;
                        break;
                    }
                    case 24: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 0) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_01[Chapter01R._random.nextInt(Chapter01R._attack_01.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                this._hardin_tak = 25;
                                this._mode12 = 1;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 25: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 26;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7694";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7695";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7693";
                                    }
                                    this._hardin_tak = 26;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 26: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 1) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_02[Chapter01R._random.nextInt(Chapter01R._attack_02.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                this._hardin_tak = 27;
                                this._mode12 = 2;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8708";
                            break;
                        }
                    }
                    case 27: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 28;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7589";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7590";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7681";
                                    }
                                    this._hardin_tak = 28;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 28: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 2) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_03[Chapter01R._random.nextInt(Chapter01R._attack_03.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                this._hardin_tak = 29;
                                this._mode12 = 3;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8709";
                            break;
                        }
                    }
                    case 29: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 30;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7588";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7584";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7684";
                                    }
                                    this._hardin_tak = 30;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 30: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 3) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_04[Chapter01R._random.nextInt(Chapter01R._attack_04.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                this._hardin_tak = 31;
                                this._mode12 = 4;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8710";
                            break;
                        }
                    }
                    case 31: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 32;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7663";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7645";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7683";
                                    }
                                    this._hardin_tak = 32;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 32: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 4) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_05[Chapter01R._random.nextInt(Chapter01R._attack_05.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                this._hardin_tak = 33;
                                this._mode12 = 5;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8704";
                            break;
                        }
                    }
                    case 33: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 34;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7700";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7701";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7682";
                                    }
                                    this._hardin_tak = 34;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 34: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 5) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_06[Chapter01R._random.nextInt(Chapter01R._attack_06.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                this._hardin_tak = 35;
                                this._mode12 = 6;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8711";
                            break;
                        }
                    }
                    case 35: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 36;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7684";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7675";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7685";
                                    }
                                    this._hardin_tak = 36;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 36: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 6) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_07[Chapter01R._random.nextInt(Chapter01R._attack_07.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                this._hardin_tak = 37;
                                this._mode12 = 7;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8712";
                            break;
                        }
                    }
                    case 37: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 38;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7694";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7695";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7693";
                                    }
                                    this._hardin_tak = 38;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 38: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 7) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_08[Chapter01R._random.nextInt(Chapter01R._attack_08.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                L1SpawnUtil.spawnRx(loc, 91285, this._showid, 10, 240);
                                this._hardin_tak = 39;
                                this._mode12 = 8;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8713";
                            break;
                        }
                    }
                    case 39: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 40;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7589";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7590";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7681";
                                    }
                                    this._hardin_tak = 40;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 40: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 8) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_09[Chapter01R._random.nextInt(Chapter01R._attack_09.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                L1SpawnUtil.spawnRx(loc, 91293, this._showid, 10, 240);
                                this._hardin_tak = 41;
                                this._mode12 = 9;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8706";
                            break;
                        }
                    }
                    case 41: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 42;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7588";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7584";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7684";
                                    }
                                    this._hardin_tak = 42;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 42: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 9) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_10[Chapter01R._random.nextInt(Chapter01R._attack_10.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                L1SpawnUtil.spawnRx(loc, 91289, this._showid, 10, 240);
                                this._hardin_tak = 43;
                                this._mode12 = 10;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8714";
                            break;
                        }
                    }
                    case 43: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 44;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7663";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7645";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7683";
                                    }
                                    this._hardin_tak = 44;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 44: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 10) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_11[Chapter01R._random.nextInt(Chapter01R._attack_11.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                L1SpawnUtil.spawnRx(loc, 91292, this._showid, 10, 240);
                                this._hardin_tak = 45;
                                this._mode12 = 11;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8715";
                            break;
                        }
                    }
                    case 45: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 46;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7700";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7701";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7682";
                                    }
                                    this._hardin_tak = 46;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 46: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 11) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_12[Chapter01R._random.nextInt(Chapter01R._attack_12.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                L1SpawnUtil.spawnRx(loc, 91298, this._showid, 10, 240);
                                this._hardin_tak = 47;
                                this._mode12 = 12;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8716";
                            break;
                        }
                    }
                    case 47: {
                        ++this._time12;
                        if (this._npclist.size() <= 0) {
                            this._hardin_tak = 48;
                            break;
                        }
                        else {
                            switch (this._time12) {
                                case 5: {
                                    wmsg = "$7597: $7700";
                                    break;
                                }
                                case 30: {
                                    wmsg = "$7597: $7701";
                                    break;
                                }
                                case 120: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7653";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 180: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7811";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 240: {
                                    if (this._npclist.size() >= 4) {
                                        wmsg = "$7597: $7682";
                                    }
                                    this._hardin_tak = 48;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    case 48: {
                        ++this._time_over;
                        if (this._time_over == 5) {
                            if (this._mode12 == 12) {
                                this._npclist.clear();
                                L1Location loc = new L1Location(32707, 32846, 9000);
                                for (int i = 0; i < 25; ++i) {
                                    switch (Chapter01R._random.nextInt(4)) {
                                        case 0: {
                                            loc = Chapter01R._loc1;
                                            break;
                                        }
                                        case 1: {
                                            loc = Chapter01R._loc2;
                                            break;
                                        }
                                        case 2: {
                                            loc = Chapter01R._loc3;
                                            break;
                                        }
                                        case 3: {
                                            loc = Chapter01R._loc4;
                                            break;
                                        }
                                    }
                                    int npcid = Chapter01R._attack_12[Chapter01R._random.nextInt(Chapter01R._attack_12.length)];
                                    int rr = Chapter01R._random.nextInt(2) + 2;
                                    L1NpcInstance mob = L1SpawnUtil.spawnRx(loc, npcid, this._showid, rr, 240);
                                    if (mob != null) {
                                        this.add(mob);
                                    }
                                }
                                this._hardin_tak = 49;
                                this._mode12 = 13;
                                this._time12 = 0;
                            }
                            this._time_over = 0;
                            this.outWindows2();
                            break;
                        }
                        else {
                            wmsg = "$8717";
                            break;
                        }
                    }
                    case 49: {
                        switch (++this._time12) {
                            case 1: {
                                wmsg = "$7597: $8705";
                                break;
                            }
                            case 5: {
                                wmsg = "$7597: $7654";
                                break;
                            }
                            case 10: {
                                this._spawn = this.checkBoss();
                                if (this._spawn > 0) {
                                    if (this._spawn >= 32) {
                                        this._spawn -= 32;
                                        wmsg = "$7597: $7668";
                                    }
                                    if (this._spawn >= 16) {
                                        this._spawn -= 16;
                                        wmsg = "$7597: $7660";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                            case 15: {
                                if (this._spawn > 0) {
                                    if (this._spawn >= 8) {
                                        this._spawn -= 8;
                                        wmsg = "$7597: $7674";
                                    }
                                    if (this._spawn >= 4) {
                                        this._spawn -= 4;
                                        wmsg = "$7597: $7661";
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                            case 20: {
                                if (this._spawn > 0 && this._spawn == 2) {
                                    wmsg = "$7597: $7689";
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 35: {
                                if (this._spawn > 0 && this._spawn == 2) {
                                    wmsg = "$7597: $7705";
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 40: {
                                wmsg = "$7597: $7705";
                                break;
                            }
                            case 300: {
                                this._npclist.clear();
                                break;
                            }
                        }
                        if (this._npclist.size() <= 0 && this._boss_a_death && this._boss_b_death) {
                            this._hardin_tak = 50;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 50: {
                        switch (++this._time_over) {
                            case 1: {
                                this.spawn_light();
                                wmsg = "$7597: $8707";
                                for (L1DoorInstance door : this._doors) {
                                    door.open();
                                }
                                this.outWindows2();
                                break;
                            }
                            case 15: {
                                wmsg = "$7597: $7687";
                                break;
                            }
                            case 30: {
                                if (this._dooropens.size() > 0) {
                                    for (L1FieldObjectInstance door2 : this._dooropens) {
                                        door2.deleteMe();
                                    }
                                    this._dooropens.clear();
                                }
                                for (L1DoorInstance door : this._doors) {
                                    door.close();
                                }
                                this._hardin_tak = 51;
                                break;
                            }
                        }
                        if (this._time_over >= 1 && this._time_over <= 15) {
                            this.openBookDoor();
                        }
                        else if (this._time_over > 15 && this._time_over <= 29) {
                            this.closeBookDoor();
                        }
                        this.spawn_light_check();
                        break;
                    }
                    case 51: {
                        this.spawn_light_check();
                        break;
                    }
                    case 52: {
                        this.spawn_light_check2();
                        break;
                    }
                    case 53: {
                        for (L1PcInstance pc : this._party.partyUsers().values()) {
                            if (pc.getMapId() == 9000) {
                                L1GroundInventory gInventory = World.get().getInventory(pc.getLocation());
                                L1ItemInstance item = ItemTable.get().createItem(49314);
                                item.setEnchantLevel(0);
                                item.setCount(1L);
                                item.set_showId(this._showid);
                                gInventory.storeItem(item);
                                QuestClass.get().endQuest(pc, Chapter01.QUEST.get_id());
                            }
                        }
                        this._hardin_tak = 54;
                        break;
                    }
                    case 54: {
                        Thread.sleep(10000L);
                        this._hardin_tak = 55;
                        break;
                    }
                    case 55: {
                        this._start = false;
                        break;
                    }
                }
                if (this.DOOR_3 && this._hardin_tak < 19) {
                    for (L1PcInstance pc : this._party.partyUsers().values()) {
                        if (pc.getMapId() == 9000 && !this._party.isLeader(pc) && pc.getX() == 32808 && pc.getY() == 32838) {
                            L1Teleport.teleport(pc, 32788, 32822, (short)9000, 4, true);
                        }
                    }
                }
                if (msg != null) {
                    this.outMsg(msg);
                }
                if (wmsg != null) {
                    this.outMsgWindows(wmsg);
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private int checkBoss() {
        int mode = 0;
        try {
            L1Location loc_a = new L1Location(32711, 32842, 9000);
            L1Location loc_b = new L1Location(32712, 32846, 9000);
            L1NpcInstance npc = null;
            L1NpcInstance s_npc = null;
            if (this._count_01 >= 4 && this._count_02 >= 4 && this._count_03 >= 4 && this._count_04 >= 4 && this._count_05 >= 4 && this._count_06 >= 4 && this._count_07 >= 4 && this._count_08 >= 4 && this._count_09 >= 4 && this._count_10 >= 4 && this._count_11 >= 4 && this._count_12 >= 4) {
                npc = L1SpawnUtil.spawn(91290, loc_a, 5, this._showid);
                ++mode;
                Chapter01R._log.info("副本編號: " + this._showid + " 召喚 鐮刀死神的使者");
            }
            else if (this._count_01 == 0 && this._count_02 == 0 && this._count_03 == 0 && this._count_04 == 0 && this._count_05 == 0 && this._count_06 == 0 && this._count_07 == 0 && this._count_08 == 0 && this._count_09 == 0 && this._count_10 == 0 && this._count_11 == 0 && this._count_12 == 0) {
                npc = L1SpawnUtil.spawn(91294, loc_a, 5, this._showid);
                mode += 2;
                Chapter01R._log.info("副本編號: " + this._showid + " 召喚 巴風特");
            }
            else {
                npc = L1SpawnUtil.spawn(91294, loc_a, 5, this._showid);
                mode += 2;
                Chapter01R._log.info("副本編號: " + this._showid + " 召喚 巴風特");
            }
            if (this._cerenis_tak == 4) {
                s_npc = L1SpawnUtil.spawn(91295, loc_b, 5, this._showid);
                Chapter01R._log.info("副本編號: " + this._showid + " 召喚 黑翼賽尼斯");
                mode += 16;
                if (this._mode_s == 3) {
                    L1Location loc_c = new L1Location(32717, 32864, 9000);
                    L1SpawnUtil.spawn(91344, loc_c, 0, this._showid);
                    Chapter01R._log.info("副本編號: " + this._showid + " 召喚 炎魔NPC");
                    mode += 8;
                }
            }
            else {
                s_npc = L1SpawnUtil.spawn(91296, loc_b, 5, this._showid);
                Chapter01R._log.info("副本編號: " + this._showid + " 召喚 賽尼斯");
                s_npc.setHate(npc, 10);
                mode += 32;
                if (this._mode_s == 3) {
                    L1Location loc_c = new L1Location(32717, 32864, 9000);
                    L1SpawnUtil.spawn(91343, loc_c, 0, this._showid);
                    Chapter01R._log.info("副本編號: " + this._showid + " 召喚 火焰之影NPC");
                    mode += 4;
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
        return mode;
    }
    
    private void closeBookDoor() {
        for (L1FieldObjectInstance door : this._dooropens) {
            int h = door.targetDirection(door.getHomeX(), door.getHomeY());
            this.setDoorMove(h, door);
        }
    }
    
    private void openBookDoor() {
        if (this._dooropens.size() <= 0) {
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32703, 32866, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32704, 32866, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32705, 32866, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32706, 32866, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32707, 32866, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32708, 32866, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32709, 32866, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32710, 32866, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32711, 32866, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32703, 32872, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32704, 32872, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32705, 32872, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32706, 32872, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32707, 32872, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32708, 32872, 9000, 0));
            this._dooropens.add(L1SpawnUtil.spawn(this._showid, 7536, 32709, 32872, 9000, 0));
        }
        for (L1FieldObjectInstance door : this._dooropens) {
            int h = Chapter01R._random.nextInt(8);
            this.setDoorMove(h, door);
        }
    }
    
    private void setDoorMove(int heading, L1FieldObjectInstance door) {
        if (heading >= 0) {
            int locx = door.getX();
            int locy = door.getY();
            locx += Chapter01R._h_x[heading];
            locy += Chapter01R._h_y[heading];
            door.setHeading(5);
            door.setX(locx);
            door.setY(locy);
            door.broadcastPacketAll(new S_MoveCharPacket(door));
        }
    }
    
    private boolean check_loc1() {
        try {
            this._alt2_02 = 0;
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000 && !this._party.isLeader(pc)) {
                    if (pc.getX() == 32668 && pc.getY() == 32817) {
                        ++this._alt2_02;
                    }
                    if (pc.getX() == 32666 && pc.getY() == 32817) {
                        ++this._alt2_02;
                    }
                    if (pc.getX() == 32668 && pc.getY() == 32819) {
                        ++this._alt2_02;
                    }
                    if (pc.getX() == 32666 && pc.getY() == 32819) {
                        ++this._alt2_02;
                    }
                    else {
                        continue;
                    }
                }
            }
            if (this._alt2_02 >= this._user_count) {
                return true;
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private boolean check_loc2() {
        try {
            this._alt2_02 = 0;
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000 && !this._party.isLeader(pc)) {
                    if (pc.getX() == 32712 && pc.getY() == 32793) {
                        ++this._alt2_02;
                    }
                    if (pc.getX() == 32703 && pc.getY() == 32791) {
                        ++this._alt2_02;
                    }
                    if (pc.getX() == 32710 && pc.getY() == 32803) {
                        ++this._alt2_02;
                    }
                    if (pc.getX() == 32703 && pc.getY() == 32800) {
                        ++this._alt2_02;
                    }
                    else {
                        continue;
                    }
                }
            }
            if (this._alt2_02 >= this._user_count) {
                return true;
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private boolean check_loc3() {
        try {
            this._alt2_02 = 0;
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000 && !this._party.isLeader(pc)) {
                    if (pc.getX() == 32809 && pc.getY() == 32837) {
                        ++this._alt2_02;
                    }
                    if (pc.getX() == 32807 && pc.getY() == 32837) {
                        ++this._alt2_02;
                    }
                    if (pc.getX() == 32809 && pc.getY() == 32839) {
                        ++this._alt2_02;
                    }
                    if (pc.getX() == 32807 && pc.getY() == 32839) {
                        ++this._alt2_02;
                    }
                    else {
                        continue;
                    }
                }
            }
            if (this._alt2_02 >= this._user_count) {
                return true;
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private boolean check_loc4() {
        try {
            if (this._npclist.size() <= 0) {
                return true;
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private void outUser() {
        try {
            this._start = false;
            if (this._party.getNumOfMembers() <= 0) {
                ConcurrentHashMap<Integer, L1Object> objs = World.get().getVisibleObjects(9000);
                for (L1Object obj : objs.values()) {
                    if (obj instanceof L1PcInstance) {
                        L1PcInstance pc = (L1PcInstance)obj;
                        if (pc.getMapId() == 9000 && pc.get_showId() == this._showid) {
                            L1Teleport.teleport(pc, 32594, 32917, (short)0, 4, true);
                            QuestClass.get().endQuest(pc, Chapter01.QUEST.get_id());
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            else {
                for (L1PcInstance pc2 : this._party.partyUsers().values()) {
                    if (pc2.getMapId() == 9000) {
                        L1Teleport.teleport(pc2, 32594, 32917, (short)0, 4, true);
                    }
                    QuestClass.get().endQuest(pc2, Chapter01.QUEST.get_id());
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private boolean all_teleport() {
        try {
            this._user_02 = 0;
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000) {
                    ++this._user_02;
                    if (pc.getX() == 32726 && pc.getY() == 32724) {
                        ++this._alt2_02;
                        if (!this._party.isLeader(pc)) {
                            L1Teleport.teleport(pc, 32664, 32790, (short)9000, 4, true);
                        }
                        else {
                            L1Teleport.teleport(pc, 32732, 32930, (short)9000, 2, true);
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
            if (this._user_02 == this._alt2_02) {
                return true;
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private boolean all_ok() {
        try {
            for (L1PcInstance pc : this._party.partyUsers().values()) {
                if (pc.getMapId() == 9000 && this._party.isLeader(pc) && pc.get_actionId() == 69) {
                    return true;
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private void getLeader() {
        try {
            if (this._msgInLeader) {
                return;
            }
            else if (this._party.getLeader().isDead()) {
                this._cerenis_tak = 0;
                return;
            }
            else if (this._party.getLeader().getMapId() != 9000) {
                this._cerenis_tak = 0;
                return;
            }
            else if (this._cerenis == null) {
                for (L1Object object : World.get().getVisibleObjects(this._party.getLeader())) {
                    if (object instanceof L1NpcInstance) {
                        L1NpcInstance npc = (L1NpcInstance)object;
                        if (npc.getNpcId() == 91297) {
                            this._cerenis = npc;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            else {
                ++this._leaderTime;
                String msg = null;
                switch (this._leaderTime) {
                    case 4: {
                        msg = "$7607";
                        break;
                    }
                    case 12: {
                        msg = "$7608";
                        break;
                    }
                    case 24: {
                        msg = "$7609";
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 32: {
                        msg = "$7610";
                        break;
                    }
                    case 40: {
                        if (this._party.getLeader().get_actionId() == 69) {
                            this._cerenis_tak = 1;
                            break;
                        }
                        else if (this._party.getLeader().get_actionId() == 66) {
                            msg = "$7614";
                            this._cerenis_tak = 2;
                            break;
                        }
                        else {
                            this._cerenis_tak = 0;
                            break;
                        }
                    }
                    case 42: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7611";
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 44: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7573";
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 46: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7576";
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 48: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7578";
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 50: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7579";
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 52: {
                        msg = "$7580";
                        break;
                    }
                    case 54: {
                        msg = "$7581";
                        break;
                    }
                    case 56: {
                        if (this._cerenis_tak == 1) {
                            if (this._party.getLeader().get_actionId() == 69) {
                                msg = "$7613";
                                this._cerenis_tak = 1;
                            }
                            else if (this._party.getLeader().get_actionId() == 66) {
                                msg = "$7614";
                                this._cerenis_tak = 2;
                            }
                            else {
                                msg = "$7612";
                                this._cerenis_tak = 0;
                            }
                        }
                        else {
                            msg = "$7612";
                        }
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 58: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7583";
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 60: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7585";
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 62: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7586";
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 64: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7616";
                        }
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 72: {
                        if (this._cerenis_tak == 1) {
                            if (this._party.getLeader().get_actionId() == 69) {
                                msg = "$7615";
                                this._cerenis_tak = 1;
                                break;
                            }
                            else if (this._party.getLeader().get_actionId() == 66) {
                                msg = "$7622";
                                this._cerenis_tak = 2;
                                break;
                            }
                            else {
                                msg = "$7617";
                                this._cerenis_tak = 0;
                                break;
                            }
                        }
                        else {
                            msg = "$7617";
                            break;
                        }
                    }
                    case 74: {
                        L1SpawnUtil.spawn(this._party.getLeader(), Chapter01R._spawn_01[Chapter01R._random.nextInt(Chapter01R._spawn_01.length)], Chapter01R._random.nextInt(4) + 4, 120);
                        msg = "$7623";
                        break;
                    }
                    case 76: {
                        for (int i = 0; i < 2; ++i) {
                            int npcid = Chapter01R._spawn_01[Chapter01R._random.nextInt(Chapter01R._spawn_01.length)];
                            int rr = Chapter01R._random.nextInt(4) + 4;
                            L1SpawnUtil.spawn(this._party.getLeader(), npcid, rr, 120);
                        }
                        msg = "$7587";
                        break;
                    }
                    case 78: {
                        for (int i = 0; i < 3; ++i) {
                            int npcid = Chapter01R._spawn_01[Chapter01R._random.nextInt(Chapter01R._spawn_01.length)];
                            int rr = Chapter01R._random.nextInt(4) + 4;
                            L1SpawnUtil.spawn(this._party.getLeader(), npcid, rr, 120);
                        }
                        msg = "$7572";
                        break;
                    }
                    case 80: {
                        msg = "$7577";
                        break;
                    }
                    case 82: {
                        for (int i = 0; i < 4; ++i) {
                            int npcid = Chapter01R._spawn_01[Chapter01R._random.nextInt(Chapter01R._spawn_01.length)];
                            int rr = Chapter01R._random.nextInt(4) + 4;
                            L1SpawnUtil.spawn(this._party.getLeader(), npcid, rr, 120);
                        }
                        break;
                    }
                    case 84: {
                        for (int i = 0; i < 5; ++i) {
                            int npcid = Chapter01R._spawn_01[Chapter01R._random.nextInt(Chapter01R._spawn_01.length)];
                            int rr = Chapter01R._random.nextInt(4) + 4;
                            L1SpawnUtil.spawn(this._party.getLeader(), npcid, rr, 120);
                        }
                        break;
                    }
                    case 88: {
                        msg = "$7618";
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 96: {
                        int count = 0;
                        for (L1Object object2 : World.get().getVisibleObjects(this._party.getLeader())) {
                            if (object2 instanceof L1MonsterInstance && !((L1MonsterInstance)object2).isDead()) {
                                ++count;
                            }
                        }
                        if (count >= 5) {
                            if (Chapter01R._random.nextBoolean()) {
                                msg = "$7624";
                                break;
                            }
                            else {
                                msg = "$7574";
                                break;
                            }
                        }
                        else if (this._party.getLeader().get_actionId() == 69) {
                            msg = "$7620";
                            this._cerenis_tak = 1;
                            break;
                        }
                        else if (this._party.getLeader().get_actionId() == 66) {
                            msg = "$7631";
                            this._cerenis_tak = 2;
                            break;
                        }
                        else {
                            msg = "$7633";
                            break;
                        }
                    }
                    case 104: {
                        msg = "$7625";
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 112: {
                        if (this._party.getLeader().get_actionId() == 69) {
                            msg = "$7629";
                            this._cerenis_tak = 1;
                        }
                        else if (this._party.getLeader().get_actionId() == 66) {
                            msg = "$7627";
                            this._cerenis_tak = 2;
                        }
                        else {
                            msg = "$7626";
                        }
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 120: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7634";
                        }
                        else if (this._cerenis_tak == 2) {
                            msg = "$7635";
                        }
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 128: {
                        msg = "$7636";
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 136: {
                        msg = "$7637";
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 140: {
                        msg = "$7638";
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 148: {
                        if (this._party.getLeader().get_actionId() == 69) {
                            msg = "$7641";
                            this._cerenis_tak = 1;
                        }
                        else if (this._party.getLeader().get_actionId() == 66) {
                            msg = "$7639";
                            this._cerenis_tak = 2;
                        }
                        else {
                            msg = "$7640";
                        }
                        this._party.getLeader().set_actionId(-1);
                        break;
                    }
                    case 156: {
                        if (this._cerenis_tak == 1) {
                            msg = "$7643";
                            this._cerenis_tak = 1;
                            break;
                        }
                        else if (this._cerenis_tak == 2) {
                            msg = "$7642";
                            this._cerenis_tak = 2;
                            break;
                        }
                        else {
                            msg = "$7644";
                            this._party.getLeader().set_actionId(-1);
                            this._cerenis_tak = 0;
                            break;
                        }
                    }
                    case 164: {
                        if (this._cerenis_tak == 1) {
                            if (this._party.getLeader().get_actionId() == 69) {
                                msg = "$7646";
                                this._cerenis_tak = 1;
                            }
                        }
                        else if (this._cerenis_tak == 2) {
                            if (this._party.getLeader().get_actionId() == 69) {
                                msg = "$7645";
                                this._cerenis_tak = 2;
                            }
                        }
                        else if (this._party.getLeader().get_actionId() == 69) {
                            msg = "$7568";
                            this._cerenis_tak = 4;
                            Chapter01R._log.info("副本編號: " + this._qid + "-" + this._showid + " 達成黑翼賽尼斯條件!");
                        }
                        else {
                            this._cerenis_tak = 0;
                        }
                        this._party.getLeader().sendPackets(new S_PacketBoxGree(2, "$7647"));
                        break;
                    }
                }
                if (this._time > 164 && this._time % 6 == 0 && this._cerenis_tak == 1) {
                    int[] m = { 7576, 7578, 7579, 7581, 7582, 7583, 7584, 7585, 7586, 7589, 7590, 7592, 7594, 7595 };
                    msg = "$" + m[Chapter01R._random.nextInt(m.length)];
                }
                if (this.DOOR_4OPEN && !this._msgInLeader) {
                    for (L1PcInstance pc : this._party.partyUsers().values()) {
                        if (pc.getMapId() == 9000 && !this._party.isLeader(pc)) {
                            L1Teleport.teleport(this._party.getLeader(), pc.getX(), pc.getY(), (short)9000, 2, true);
                            this._msgInLeader = true;
                        }
                    }
                }
                if (msg != null) {
                    this._party.getLeader().sendPackets(new S_NpcChat(this._cerenis, msg));
                }
            }
        }
        catch (Exception e) {
            Chapter01R._log.error(e.getLocalizedMessage(), e);
        }
    }
}

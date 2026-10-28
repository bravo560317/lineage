package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_PacketBox extends ServerBasePacket
{
    private byte[] _byte;
    public static final int MSG_WAR_BEGIN = 0;
    public static final int MSG_WAR_END = 1;
    public static final int MSG_WAR_GOING = 2;
    public static final int MSG_WAR_INITIATIVE = 3;
    public static final int MSG_WAR_OCCUPY = 4;
    public static final int MSG_DUEL = 5;
    public static final int MSG_SMS_SENT = 6;
    public static final int MSG_MARRIED = 9;
    public static final int WEIGHT = 10;
    public static final int FOOD = 11;
    public static final int MSG_LEVEL_OVER = 12;
    public static final int HTML_UB = 14;
    public static final int MSG_ELF = 15;
    public static final int ADD_EXCLUDE2 = 17;
    public static final int ADD_EXCLUDE = 18;
    public static final int REM_EXCLUDE = 19;
    public static final int ICON_ACTIVESPELLS = 20;
    public static final int ICONS2 = 21;
    public static final int ICON_AURA = 22;
    public static final int MSG_TOWN_LEADER = 23;
    public static final int HTML_PLEDGE_RECOMMENDATION_ACCEPT = 25;
    public static final int MSG_RANK_CHANGED = 27;
    public static final int MSG_WIN_LASTAVARD = 30;
    public static final int MSG_FEEL_GOOD = 31;
    public static final int SOMETHING1 = 33;
    public static final int ICON_BLUEPOTION = 34;
    public static final int ICON_POLYMORPH = 35;
    public static final int ICON_CHATBAN = 36;
    public static final int GUI_PET_EQUITMENT = 37;
    public static final int HTML_CLAN1 = 38;
    public static final int ICON_I2H = 40;
    public static final int CHARACTER_CONFIG = 41;
    public static final int LOGOUT = 42;
    public static final int MSG_CANT_LOGOUT = 43;
    public static final int ICON_WIND_SHACKLE = 44;
    public static final int CALL_SOMETHING = 45;
    public static final int MSG_COLOSSEUM = 49;
    public static final int HTML_CLAN2 = 51;
    public static final int COOK_WINDOW = 52;
    public static final int ICON_COOKING = 53;
    public static final int FISHING = 55;
    public static final int DOLL = 56;
    public static final int ICON_WIS_POTION = 57;
    public static final int ICON_WATER_LIFE_CANCEL = 59;
    public static final int ICON_THIRD_SPEED = 60;
    public static final int PLEDGE_UNION = 62;
    public static final int MINIGAME_GAMESTART = 64;
    public static final int MINIGAME_COUNTDOWN_1 = 65;
    public static final int MINIGAME_PLAYERINFO = 66;
    public static final int MINIGAME_LAP = 67;
    public static final int MINIGAME_WINNER = 68;
    public static final int MINIGAME_GAMEOVER = 69;
    public static final int MINIGAME_COUNTDOWN_1_END = 70;
    public static final int MINIGAME_COUNTDOWN_2 = 71;
    public static final int MINIGAME_COUNTDOWN_2_END = 72;
    public static final int ICON_WEAPON_BREAKER = 74;
    public static final int ICON_WEAKNESS = 75;
    public static final int MSG_WAR_BEGIN1 = 78;
    public static final int MSG_WAR_END1 = 79;
    public static final int MSG_WAR_GOING1 = 80;
    public static final int ICON_EINHASADS_BLESS = 82;
    public static final int BLUE_EXPREPLY = 86;
    public static final int GUI_VISUAL_EFFECT = 83;
    public static final int MSG_COLOR_MESSAGE = 84;
    public static final int GUI_KARMA = 87;
    public static final int DODGE_RATE_PLUS = 88;
    public static final int ICON_BLOOD_STAIN = 100;
    public static final int DODGE_RATE_MINUS = 101;
    public static final int GUI_DRAGON_GATE = 102;
    public static final int UPDATE_OLD_PART_MEMBER = 104;
    public static final int PATRY_UPDATE_MEMBER = 105;
    public static final int PATRY_SET_MASTER = 106;
    public static final int PATRY_MEMBERS = 110;
    public static final int MSG_LOCATION = 111;
    public static final int ICON_FIGHT = 114;
    public static final int HTML_CLAN_WARHOUSE_RECORD = 117;
    public static final int ICON_SECURITY_SERVICES = 125;
    public static final int ICON_OTHER2 = 127;
    public static final int UPDATE_ER = 132;
    public static final int MSG_BOOKMARK_SPACE = 141;
    public static final int ICON_SOUL_GUARDIAN = 144;
    public static final int HTML_MASTER = 146;
    public static final int ICON_NO_TIMER = 147;
    public static final int ROUND_NUMBER = 156;
    public static final int NONE_TIME_ICON = 180;
    public static final int MAP_TIMER = 153;
    public static final int ICON_OTHER3 = 154;
    public static final int MAP_TIME = 159;
    public static final int CLAN_EXP = 165;
    public static final int HTML_PLEDGE_ANNOUNCE = 167;
    public static final int HTML_PLEDGE_REALEASE_ANNOUNCE = 168;
    public static final int HTML_PLEDGE_WRITE_NOTES = 169;
    public static final int HTML_PLEDGE_MEMBERS = 170;
    public static final int HTML_PLEDGE_ONLINE_MEMBERS = 171;
    public static final int PLEDGE_EMBLEM_STATUS = 173;
    public static final int TOWN_TELEPORT = 176;
    public static final int SOULTOWERSTART = 195;
    public static final int SOULTOWEREND = 196;
    
    public S_PacketBox(int subCode, L1PcInstance pc) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        switch (subCode) {
            case 176: {
                this.writeC(1);
                this.writeH(pc.getX());
                this.writeH(pc.getY());
                break;
            }
        }
    }
    
    public S_PacketBox(int subCode, int value) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        switch (subCode) {
            case 6:
            case 10:
            case 11:
            case 144: {
                this.writeC(value);
                break;
            }
            case 15:
            case 49: {
                this.writeC(value);
                break;
            }
            case 21: {
                this.writeC(0);
                this.writeC(0);
                this.writeC(0);
                this.writeC(value);
                break;
            }
            case 34:
            case 35:
            case 36:
            case 40: {
                this.writeH(value);
                break;
            }
            case 52: {
                this.writeC(219);
                this.writeC(49);
                this.writeC(223);
                this.writeC(2);
                this.writeC(1);
                this.writeC(value);
                break;
            }
            case 56: {
                this.writeH(value);
                break;
            }
            case 57: {
                this.writeC(44);
                this.writeH(value);
                break;
            }
            case 74:
            case 75: {
                int time = value >> 2;
                this.writeC(time);
                break;
            }
            case 83: {
                this.writeC(value);
                break;
            }
            case 120: {
                this.writeH(value);
                break;
            }
            case 132: {
                this.writeC(value);
                break;
            }
            case 153: {
                this.writeD(value);
                break;
            }
            case 165: {
                this.writeH(value);
                break;
            }
            case 173: {
                this.writeC(1);
                if (value == 0) {
                    this.writeC(0);
                }
                else if (value == 1) {
                    this.writeC(1);
                }
                this.writeD(0);
                break;
            }
            case 195: {
                this.writeD(value);
                this.writeH(13532);
                this.writeH(0);
                break;
            }
            case 196: {
                this.writeD(0);
                this.writeH(value);
                this.writeH(0);
                break;
            }
        }
    }
    
    public S_PacketBox(int subCode, int type, int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        switch (subCode) {
            case 5: {
                this.writeD(type);
                this.writeD(time);
                break;
            }
            case 22: {
                this.writeC(221);
                this.writeH(time);
                this.writeC(type);
                break;
            }
            case 53: {
                this.writeC(19);
                this.writeC(11);
                this.writeC(10);
                this.writeC(18);
                this.writeH(2313);
                this.writeC(208);
                this.writeC(7);
                this.writeC(type);
                this.writeC(38);
                this.writeH(time);
                this.writeC(97);
                break;
            }
            case 86: {
                this.writeC(112);
                this.writeC(1);
                this.writeC(type);
                this.writeH(time);
                break;
            }
            case 100: {
                this.writeC(type);
                this.writeH(time);
                break;
            }
            case 147: {
                this.writeC(type);
                this.writeC(time);
                break;
            }
            case 154: {
                this.writeH(time);
                this.writeH(type);
                break;
            }
            case 156: {
                this.writeD(type);
                this.writeD(time);
                this.writeH(0);
                break;
            }
            case 180: {
                this.writeC(type);
                this.writeD(time);
                this.writeD(3431);
                this.writeH(0);
                break;
            }
        }
    }
    
    public S_PacketBox(L1PcInstance master, ArrayList<L1PcInstance> totalList) {
        this._byte = null;
        this.writeC(250);
        this.writeC(146);
        this.writeC(totalList.size());
        for (L1PcInstance l1Char : totalList) {
            this.writeS(l1Char.getName());
            this.writeC(l1Char.getLevel());
            this.writeC(l1Char.getType());
        }
        this.writeS(master.getName());
        this.writeC(master.getLevel());
        this.writeC(master.getType());
    }
    
    public S_PacketBox(int subCode, String name) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        switch (subCode) {
            case 18:
            case 19:
            case 23:
            case 62:
            case 168: {
                this.writeS(name);
                break;
            }
            case 66: {
                this.writeC(1);
                this.writeC(0);
                this.writeC(0);
                this.writeC(0);
                this.writeS(name);
                break;
            }
        }
    }
    
    public S_PacketBox(int subCode, Object[] names) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        switch (subCode) {
            case 17: {
                this.writeC(names.length);
                for (Object name : names) {
                    this.writeS(name.toString());
                }
                break;
            }
            case 171: {
                this.writeH(names.length);
                for (Object name : names) {
                    L1PcInstance pc = (L1PcInstance)name;
                    this.writeS(pc.getName());
                    this.writeC(0);
                }
                break;
            }
        }
    }
    
    public S_PacketBox(int subCode, int value, String msg) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        switch (subCode) {
            case 27: {
                this.writeC(value);
                this.writeS(msg);
                break;
            }
            case 84: {
                this.writeC(value);
                this.writeS(msg);
                break;
            }
        }
    }
    
    public S_PacketBox(int subCode, int type, int type2, int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        switch (subCode) {
            case 180: {
                this.writeC(time);
                this.writeD(type);
                this.writeD(type2);
                this.writeH(0);
                break;
            }
        }
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

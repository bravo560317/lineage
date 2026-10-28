package com.lineage.server.clientpackets;

import java.io.UnsupportedEncodingException;
import com.lineage.config.Config;
import com.lineage.list.BadNamesList;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.datatables.BeginnerTable;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.serverpackets.S_NewCharPacket;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.utils.CalcInitHpMp;
import com.lineage.config.ConfigOther;
import com.lineage.server.IdFactory;
import com.lineage.server.templates.L1Account;
import com.lineage.config.ConfigIpCheck;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.server.serverpackets.S_CharCreateStatus;
import com.lineage.config.ConfigAlt;
import java.util.regex.Matcher;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_CreateChar extends ClientBasePacket
{
    private static final Log _log;
    public static final int[] ORIGINAL_STR;
    public static final int[] ORIGINAL_DEX;
    public static final int[] ORIGINAL_CON;
    public static final int[] ORIGINAL_WIS;
    public static final int[] ORIGINAL_CHA;
    public static final int[] ORIGINAL_INT;
    public static final int[] ORIGINAL_AMOUNT;
    public static final int[] MAX_STR;
    public static final int[] MAX_DEX;
    public static final int[] MAX_CON;
    public static final int[] MAX_WIS;
    public static final int[] MAX_CHA;
    public static final int[] MAX_INT;
    private static final int[][] CLASS_LIST;
    private static final int[][] LOC_LIST;
    public static final String[] BANLIST;
    
    static {
        _log = LogFactory.getLog(C_CreateChar.class);
        ORIGINAL_STR = new int[] { 13, 16, 11, 8, 12, 13, 11 };
        ORIGINAL_DEX = new int[] { 10, 12, 12, 7, 15, 11, 10 };
        ORIGINAL_CON = new int[] { 10, 14, 12, 12, 8, 14, 12 };
        ORIGINAL_WIS = new int[] { 11, 9, 12, 12, 10, 12, 12 };
        ORIGINAL_CHA = new int[] { 13, 12, 9, 8, 9, 8, 8 };
        ORIGINAL_INT = new int[] { 10, 8, 12, 12, 11, 11, 12 };
        ORIGINAL_AMOUNT = new int[] { 8, 4, 7, 16, 10, 6, 10 };
        MAX_STR = new int[] { 20, 20, 18, 20, 18, 20, 18 };
        MAX_DEX = new int[] { 18, 16, 18, 18, 18, 18, 18 };
        MAX_CON = new int[] { 18, 18, 18, 18, 18, 18, 18 };
        MAX_WIS = new int[] { 18, 13, 18, 18, 18, 18, 18 };
        MAX_CHA = new int[] { 18, 16, 16, 18, 18, 18, 18 };
        MAX_INT = new int[] { 18, 12, 18, 18, 18, 18, 18 };
        CLASS_LIST = new int[][] { { 0, 61, 138, 734, 2786, 6658, 6671 }, { 1, 48, 37, 1186, 2796, 6661, 6650 } };
        LOC_LIST = new int[][] { { 32684, 32870, 2005 }, { 32686, 32867, 2005 }, { 32691, 32864, 2005 }, { 32684, 32870, 2005 }, { 32686, 32867, 2005 }, { 32691, 32864, 2005 }, { 32691, 32864, 2005 } };
        BANLIST = new String[] { "あ", "ア", "い", "イ", "う", "ウ", "え", "エ", "お", "オ", "か", "カ", "き", "キ", "く", "ク", "け", "ケ", "こ", "コ", "さ", "サ", "し", "シ", "す", "ス", "せ", "セ", "そ", "ソ", "た", "タ", "ち", "チ", "つ", "ツ", "て", "テ", "と", "ト", "な", "ナ", "に", "ニ", "ぬ", "ヌ", "ね", "ネ", "の", "ノ", "は", "ハ", "ひ", "ヒ", "ふ", "フ", "へ", "ヘ", "ほ", "ホ", "ま", "マ", "み", "ミ", "む", "ム", "め", "メ", "も", "モ", "や", "ヤ", "ゆ", "ユ", "よ", "ヨ", "ら", "ラ", "り", "リ", "る", "ル", "れ", "レ", "ろ", "ロ", "わ", "ワ", "を", "ヲ", "ん", "ン", "丶", "", "", "-", "/", "+", "*", "?", "!", "@", "#", "$", "%", "^", "&", "(", ")", "[", "]", "<", ">", "{", "}", ";", ":", "'", "\"", ",", ".", "~", "`" };
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = new L1PcInstance();
            String name = Matcher.quoteReplacement(this.readS());
            L1Account account = client.getAccount();
            int characterSlot = account.get_character_slot();
            int maxAmount = ConfigAlt.DEFAULT_CHARACTER_SLOT + characterSlot;
            name = name.replaceAll("\\s", "");
            name = name.replaceAll("\u3000", "");
            if (name.length() == 0) {
                client.out().encrypt(new S_CharCreateStatus(9));
            }
            else if (!isInvalidName(name)) {
                client.out().encrypt(new S_CharCreateStatus(9));
            }
            else if (CharObjidTable.get().charObjid(name) != 0) {
                client.out().encrypt(new S_CharCreateStatus(6));
            }
            else {
                int countCharacters = client.getAccount().get_countCharacters();
                if (countCharacters >= maxAmount) {
                    client.out().encrypt(new S_CharCreateStatus(21));
                    return;
                }
                else {
                    pc.setName(name);
                    pc.setTheEnemy(name);
                    pc.setType(this.readC());
                        if ((pc.getType() == 4 && !ConfigOther.AllowDarkelf)
                            || (pc.getType() == 5 && !ConfigOther.AllowDragonKnight)
                            || (pc.getType() == 6 && !ConfigOther.AllowIllusionist)) {
                        client.out().encrypt(new S_CharCreateStatus(S_CharCreateStatus.REASON_CLASS_NOT_AVAILABLE));
                        return;
                    }
                    pc.set_sex(this.readC());
                    pc.addBaseStr((int)(byte)this.readC());
                    pc.addBaseDex((int)(byte)this.readC());
                    pc.addBaseCon((int)(byte)this.readC());
                    pc.addBaseWis((int)(byte)this.readC());
                    pc.addBaseCha((int)(byte)this.readC());
                    pc.addBaseInt((int)(byte)this.readC());
                    boolean isStatusError = false;
                    int originalStr = C_CreateChar.ORIGINAL_STR[pc.getType()];
                    int originalDex = C_CreateChar.ORIGINAL_DEX[pc.getType()];
                    int originalCon = C_CreateChar.ORIGINAL_CON[pc.getType()];
                    int originalWis = C_CreateChar.ORIGINAL_WIS[pc.getType()];
                    int originalCha = C_CreateChar.ORIGINAL_CHA[pc.getType()];
                    int originalInt = C_CreateChar.ORIGINAL_INT[pc.getType()];
                    int originalAmount = C_CreateChar.ORIGINAL_AMOUNT[pc.getType()];
                    if (pc.getBaseStr() < originalStr || pc.getBaseDex() < originalDex || pc.getBaseCon() < originalCon || pc.getBaseWis() < originalWis || pc.getBaseCha() < originalCha || pc.getBaseInt() < originalInt || pc.getBaseStr() > originalStr + originalAmount || pc.getBaseDex() > originalDex + originalAmount || pc.getBaseCon() > originalCon + originalAmount || pc.getBaseWis() > originalWis + originalAmount || pc.getBaseCha() > originalCha + originalAmount || pc.getBaseInt() > originalInt + originalAmount) {
                        isStatusError = true;
                    }
                    int statusAmount = pc.getDex() + pc.getCha() + pc.getCon() + pc.getInt() + pc.getStr() + pc.getWis();
                    if (statusAmount != 75 || isStatusError) {
                        client.out().encrypt(new S_CharCreateStatus(21));
                        return;
                    }
                    else {
                        client.getAccount().set_countCharacters(countCharacters + 1);
                        client.out().encrypt(new S_CharCreateStatus(2));
                        initNewChar(client, pc);
                        if (ConfigIpCheck.timeOutSocket != 0) {
                            client.SetSocket(ConfigIpCheck.timeOutSocket);
                            return;
                        }
                        else {
                            return;
                        }
                    }
                }
            }
            return;
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    private static void initNewChar(ClientExecutor client, L1PcInstance pc) {
        try {
            L1Account account = client.getAccount();
            pc.setId(IdFactory.get().nextId());
            int classid = C_CreateChar.CLASS_LIST[pc.get_sex()][pc.getType()];
            pc.setClassId(classid);
            pc.setTempCharGfx(classid);
            pc.setGfxId(classid);
            int[] loc = null;
            if (ConfigOther.NEW_CHAR_LOC != null) {
                loc = ConfigOther.NEW_CHAR_LOC;
            }
            pc.setX(loc[0]);
            pc.setY(loc[1]);
            pc.setMap((short)loc[2]);
            pc.setHeading(0);
            pc.setLawful(0);
            int initHp = CalcInitHpMp.calcInitHp(pc);
            int initMp = CalcInitHpMp.calcInitMp(pc);
            pc.addBaseMaxHp((short)initHp);
            pc.setCurrentHp((int)(short)initHp);
            pc.addBaseMaxMp((short)initMp);
            pc.setCurrentMp((int)(short)initMp);
            pc.resetBaseAc();
            pc.setTitle("");
            pc.setClanid(0);
            pc.setClanRank(0);
            pc.set_food(40);
            if (account.get_access_level() >= 200) {
                pc.setAccessLevel((short)account.get_access_level());
                pc.setGm(true);
                pc.setMonitor(false);
            }
            else {
                pc.setAccessLevel((short)0);
                pc.setGm(false);
                pc.setMonitor(false);
            }
            pc.setGmInvis(false);
            pc.setExp(0L);
            pc.setHighLevel(0);
            pc.setStatus(0);
            pc.setClanname("");
            pc.setBonusStats(0);
            pc.setElixirStats(0);
            pc.resetBaseMr();
            pc.setElfAttr(0);
            pc.set_PKcount(0);
            pc.setPkCountForElf(0);
            pc.setExpRes(0);
            pc.setPartnerId(0);
            pc.setOnlineStatus(0);
            pc.setHomeTownId(0);
            pc.setContribution(0);
            pc.setBanned(false);
            pc.setKarma(0);
            if (pc.isWizard()) {
                pc.sendPackets(new S_AddSkill(pc, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                int object_id = pc.getId();
                L1Skills l1skills = SkillsTable.get().getTemplate(4);
                String skill_name = l1skills.getName();
                int skill_id = l1skills.getSkillId();
                CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
            }
            pc.setAccountName(client.getAccountName());
            pc.refresh();
            client.out().encrypt(new S_NewCharPacket(pc));
            CharacterTable.get().storeNewCharacter(pc);
            CharacterTable.saveCharStatus(pc);
            BeginnerTable.get().giveItem(pc);
            CharObjidTable.get().addChar(pc.getId(), pc.getName());
            if (ConfigOther.CreateCharInfo != null) {
                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(ConfigOther.CreateCharInfo, pc.getName())));
            }
        }
        catch (Exception e) {
            C_CreateChar._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static boolean isInvalidName(String name) {
        try {
            String[] banlist;
            int length = (banlist = C_CreateChar.BANLIST).length;
            int i = 0;
            while (i < length) {
                String ban = banlist[i];
                if (name.indexOf(ban) != -1) {
                    return false;
                }
                else {
                    ++i;
                }
            }
            if (BadNamesList.get().isBadName(name)) {
                return false;
            }
            else {
                int numOfNameBytes = name.getBytes(Config.CLIENT_LANGUAGE_CODE).length;
                return ConfigOther.CharNamebyte >= numOfNameBytes - name.length() && ConfigOther.CharNameEnbyte >= numOfNameBytes;
            }
        }
        catch (UnsupportedEncodingException e) {
            return false;
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

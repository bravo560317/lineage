package com.lineage.server.storage.mysql;

import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.datatables.CharObjidTable;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.templates.L1PcOther3;
import com.lineage.server.datatables.lock.CharOtherReading3;
import com.lineage.server.templates.L1PcOther2;
import com.lineage.server.datatables.lock.CharOtherReading2;
import com.lineage.server.templates.L1PcOther1;
import com.lineage.server.datatables.lock.CharOtherReading1;
import com.lineage.server.templates.L1PcOther;
import com.lineage.server.datatables.lock.CharOtherReading;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.storage.CharacterStorage;

public class MySqlCharacterStorage implements CharacterStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(MySqlCharacterStorage.class);
    }
    
    @Override
    public L1PcInstance loadCharacter(String charName) {
        L1PcInstance pc = null;
        Connection con = null;
        PreparedStatement pstm = null;
        PreparedStatement pstm2 = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM characters WHERE char_name=?");
            pstm.setString(1, charName);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;
            }
            else {
                pc = new L1PcInstance();
                String loginName = rs.getString("account_name").toLowerCase();
                pc.setAccountName(loginName);
                int objid = rs.getInt("objid");
                pc.setId(objid);
                pc.set_showId(-1);
                L1PcOther other = CharOtherReading.get().getOther(pc);
                if (other == null) {
                    other = new L1PcOther();
                    other.set_objid(objid);
                }
                pc.set_other(other);
                L1PcOther1 other2 = CharOtherReading1.get().getOther(pc);
                if (other2 == null) {
                    other2 = new L1PcOther1();
                    other2.set_objid(objid);
                }
                pc.set_other1(other2);
                L1PcOther2 l1PcOther2 = CharOtherReading2.get().getOther(pc);
                if (l1PcOther2 == null) {
                    l1PcOther2 = new L1PcOther2();
                    l1PcOther2.set_objid(objid);
                }
                pc.set_other2(l1PcOther2);
                L1PcOther3 other3 = CharOtherReading3.get().getOther(pc);
                if (other3 == null) {
                    other3 = new L1PcOther3();
                    other3.set_objid(objid);
                }
                pc.set_other3(other3);
                pc.setName(rs.getString("char_name"));
                pc.setHighLevel(rs.getInt("HighLevel"));
                pc.setExp(rs.getLong("Exp"));
                pc.addBaseMaxHp(rs.getShort("MaxHp"));
                short currentHp = rs.getShort("CurHp");
                if (currentHp < 1) {
                    currentHp = 1;
                }
                pc.setDead(false);
                pc.setCurrentHpDirect((int)currentHp);
                pc.setStatus(0);
                pc.addBaseMaxMp(rs.getShort("MaxMp"));
                pc.setCurrentMpDirect((int)rs.getShort("CurMp"));
                pc.addBaseStr(rs.getInt("Str"));
                pc.addBaseCon(rs.getInt("Con"));
                pc.addBaseDex(rs.getInt("Dex"));
                pc.addBaseCha(rs.getInt("Cha"));
                pc.addBaseInt(rs.getInt("Intel"));
                pc.addBaseWis(rs.getInt("Wis"));
                int status = rs.getInt("Status");
                pc.setCurrentWeapon(status);
                int classId = rs.getInt("Class");
                pc.setClassId(classId);
                pc.setTempCharGfx(classId);
                pc.setGfxId(classId);
                pc.set_sex(rs.getInt("Sex"));
                pc.setType(rs.getInt("Type"));
                int head = rs.getInt("Heading");
                if (head > 7) {
                    head = 0;
                }
                pc.setHeading(head);
                pc.setX(rs.getInt("locX"));
                pc.setY(rs.getInt("locY"));
                pc.setMap(rs.getShort("MapID"));
                pc.set_food(rs.getInt("Food"));
                pc.setLawful(rs.getInt("Lawful"));
                pc.setTitle(rs.getString("Title"));
                pc.setClanid(rs.getInt("ClanID"));
                pc.setClanname(rs.getString("Clanname"));
                pc.setClanRank(rs.getInt("ClanRank"));
                pc.setRejoinClanTime(rs.getTimestamp("rejoin_clan_time"));
                pc.setBonusStats(rs.getInt("BonusStatus"));
                pc.setElixirStats(rs.getInt("ElixirStatus"));
                pc.setElfAttr(rs.getInt("ElfAttr"));
                pc.set_PKcount(rs.getInt("PKcount"));
                pc.setPkCountForElf(rs.getInt("PkCountForElf"));
                pc.setExpRes(rs.getInt("ExpRes"));
                pc.setPartnerId(rs.getInt("PartnerID"));
                pc.setAccessLevel(rs.getShort("AccessLevel"));
                if (pc.getAccessLevel() >= 200) {
                    pc.setGm(true);
                    pc.setMonitor(false);
                }
                else if (pc.getAccessLevel() == 100) {
                    pc.setGm(false);
                    pc.setMonitor(true);
                }
                else {
                    pc.setGm(false);
                    pc.setMonitor(false);
                }
                pc.setOnlineStatus(rs.getInt("OnlineStatus"));
                pc.setHomeTownId(rs.getInt("HomeTownID"));
                pc.setContribution(rs.getInt("Contribution"));
                pc.setPay(rs.getInt("Pay"));
                pc.setHellTime(rs.getInt("HellTime"));
                pc.setBanned(rs.getBoolean("Banned"));
                pc.setKarma(rs.getInt("Karma"));
                pc.setLastPk(rs.getTimestamp("LastPk"));
                pc.setLastPkForElf(rs.getTimestamp("LastPkForElf"));
                pc.setDeleteTime(rs.getTimestamp("DeleteTime"));
                pc.setOriginalStr(rs.getInt("OriginalStr"));
                pc.setOriginalCon(rs.getInt("OriginalCon"));
                pc.setOriginalDex(rs.getInt("OriginalDex"));
                pc.setOriginalCha(rs.getInt("OriginalCha"));
                pc.setOriginalInt(rs.getInt("OriginalInt"));
                pc.setOriginalWis(rs.getInt("OriginalWis"));
                pc.setCreateTime(rs.getTimestamp("CreateTime"));
                pc.setMeteLevel(rs.getInt("logpcpower"));
                pc.setPunishTime(rs.getTimestamp("PunishTime"));
                pc.setBanError(rs.getInt("BanError"));
                pc.setInputBanError(rs.getInt("InputBanError"));
                pc.setSpeedError(rs.getInt("SpeedError"));
                pc.setRocksPrisonTime(rs.getInt("RocksPrisonTime"));
                pc.setIvoryTowerTime(rs.getInt("IvorytowerTime"));
                pc.setLastabardTime(rs.getInt("LastabardTime"));
                pc.setDragonValleyTime(rs.getInt("DragonValleyTime"));
                pc.setPcContribution(rs.getInt("PcContribution"));
                pc.setClanContribution(rs.getInt("ClanContribution"));
                pc.setClanNameContribution(rs.getString("ClanNameContribution"));
                pc.setlogpcpower_SkillCount(rs.getInt("logpcpower_SkillCount"));
                pc.setlogpcpower_SkillFor1(rs.getInt("logpcpower_SkillFor1"));
                pc.setlogpcpower_SkillFor2(rs.getInt("logpcpower_SkillFor2"));
                pc.setlogpcpower_SkillFor3(rs.getInt("logpcpower_SkillFor3"));
                pc.setlogpcpower_SkillFor4(rs.getInt("logpcpower_SkillFor4"));
                pc.setlogpcpower_SkillFor5(rs.getInt("logpcpower_SkillFor5"));
                pc.setReductionDmg(rs.getInt("ReductionDmg"));
                pc.setpcdmg(rs.getInt("pcdmg"));
                pc.setpaycount(rs.getInt("paycount"));
                pc.setlogintime(rs.getInt("logintime"));
                pc.setloginpoly(rs.getInt("loginpoly"));
                pc.setguaji_count(rs.getInt("guaji_count"));
                pc.setday(rs.getInt("day"));
                pc.setPrestige(rs.getInt("PrestigeLv"));
                pc.setQuburcount(rs.getInt("Quburcount"));
                pc.setclanadena(rs.getInt("clanadena"));
                pc.setcardpoly(rs.getInt("cardpoly"));
                pc.setpcezpay(rs.getInt("pcezpay"));
                pc.setTheEnemy(rs.getString("TheEnemy"));
                rs.close();
                pc.refresh();
                pc.setMoveSpeed(0);
                pc.setBraveSpeed(0);
                pc.setGmInvis(false);
                if (pc.getClanid() > 0) {
                    pstm2 = con.prepareStatement("SELECT * FROM clan_members WHERE char_id=?");
                    pstm2.setInt(1, pc.getId());
                    rs = pstm2.executeQuery();
                    if (!rs.next()) {
                        return null;
                    }
                    else {
                        pc.setClanMemberId(rs.getInt("index_id"));
                        pc.setClanMemberNotes(rs.getString("notes"));
                    }
                }
            }
        }
        catch (SQLException e) {
            MySqlCharacterStorage._log.error(e.getLocalizedMessage(), e);
            return null;
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return pc;
    }
    
    @Override
    public void createCharacter(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            int i = 0;
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO characters SET account_name=?,objid=?,char_name=?,level=?,HighLevel=?,Exp=?,MaxHp=?,CurHp=?,MaxMp=?,CurMp=?,Ac=?,Str=?,Con=?,Dex=?,Cha=?,Intel=?,Wis=?,Status=?,Class=?,Sex=?,Type=?,Heading=?,LocX=?,LocY=?,MapID=?,Food=?,Lawful=?,Title=?,ClanID=?,Clanname=?,ClanRank=?,rejoin_clan_time=?,BonusStatus=?,ElixirStatus=?,ElfAttr=?,PKcount=?,PkCountForElf=?,ExpRes=?,PartnerID=?,AccessLevel=?,OnlineStatus=?,HomeTownID=?,Contribution=?,Pay=?,HellTime=?,Banned=?,Karma=?,LastPk=?,LastPkForElf=?,DeleteTime=?,CreateTime=?,logpcpower=?,PunishTime=?,BanError=?,InputBanError=?,SpeedError=?,RocksPrisonTime=?,IvorytowerTime=?,LastabardTime=?,DragonValleyTime=?,PcContribution=?,ClanContribution=?,ClanNameContribution=?,logpcpower_SkillCount=?,logpcpower_SkillFor1=?,logpcpower_SkillFor2=?,logpcpower_SkillFor3=?,logpcpower_SkillFor4=?,logpcpower_SkillFor5=?,ReductionDmg=?,pcdmg=?,paycount=?,logintime=?,loginpoly=?,guaji_count=?,day=?,PrestigeLv=?,clanadena=?,Quburcount=?,cardpoly=?,pcezpay=?,TheEnemy=?");
            pstm.setString(++i, pc.getAccountName());
            pstm.setInt(++i, pc.getId());
            pstm.setString(++i, pc.getName());
            pstm.setInt(++i, pc.getLevel());
            pstm.setInt(++i, pc.getHighLevel());
            pstm.setLong(++i, pc.getExp());
            pstm.setInt(++i, (int)pc.getBaseMaxHp());
            int hp = pc.getCurrentHp();
            if (hp < 1) {
                hp = 1;
            }
            pstm.setInt(++i, hp);
            pstm.setInt(++i, (int)pc.getBaseMaxMp());
            pstm.setInt(++i, pc.getCurrentMp());
            pstm.setInt(++i, pc.getAc());
            pstm.setInt(++i, pc.getBaseStr());
            pstm.setInt(++i, pc.getBaseCon());
            pstm.setInt(++i, pc.getBaseDex());
            pstm.setInt(++i, pc.getBaseCha());
            pstm.setInt(++i, pc.getBaseInt());
            pstm.setInt(++i, pc.getBaseWis());
            pstm.setInt(++i, pc.getCurrentWeapon());
            pstm.setInt(++i, pc.getClassId());
            pstm.setInt(++i, (int)pc.get_sex());
            pstm.setInt(++i, pc.getType());
            pstm.setInt(++i, pc.getHeading());
            pstm.setInt(++i, pc.getX());
            pstm.setInt(++i, pc.getY());
            pstm.setInt(++i, (int)pc.getMapId());
            pstm.setInt(++i, pc.get_food());
            pstm.setInt(++i, pc.getLawful());
            pstm.setString(++i, pc.getTitle());
            pstm.setInt(++i, pc.getClanid());
            pstm.setString(++i, pc.getClanname());
            pstm.setInt(++i, pc.getClanRank());
            pstm.setTimestamp(++i, pc.getRejoinClanTime());
            pstm.setInt(++i, pc.getBonusStats());
            pstm.setInt(++i, pc.getElixirStats());
            pstm.setInt(++i, pc.getElfAttr());
            pstm.setInt(++i, pc.get_PKcount());
            pstm.setInt(++i, pc.getPkCountForElf());
            pstm.setInt(++i, pc.getExpRes());
            pstm.setInt(++i, pc.getPartnerId());
            short accesslevel = pc.getAccessLevel();
            if (accesslevel > 200) {
                accesslevel = 0;
            }
            pstm.setShort(++i, accesslevel);
            pstm.setInt(++i, pc.getOnlineStatus());
            pstm.setInt(++i, pc.getHomeTownId());
            pstm.setInt(++i, pc.getContribution());
            pstm.setInt(++i, pc.getPay());
            pstm.setInt(++i, pc.getHellTime());
            pstm.setBoolean(++i, pc.isBanned());
            pstm.setInt(++i, pc.getKarma());
            pstm.setTimestamp(++i, pc.getLastPk());
            pstm.setTimestamp(++i, pc.getLastPkForElf());
            pstm.setTimestamp(++i, pc.getDeleteTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String times = sdf.format((Long)System.currentTimeMillis());
            int time = Integer.parseInt(times.replace("-", ""));
            pstm.setInt(++i, time);
            pstm.setInt(++i, pc.getMeteLevel());
            pstm.setTimestamp(++i, pc.getPunishTime());
            pstm.setInt(++i, pc.getBanError());
            pstm.setInt(++i, pc.getInputBanError());
            pstm.setInt(++i, pc.getSpeedError());
            pstm.setInt(++i, pc.getRocksPrisonTime());
            pstm.setInt(++i, pc.getIvoryTowerTime());
            pstm.setInt(++i, pc.getLastabardTime());
            pstm.setInt(++i, pc.getDragonValleyTime());
            pstm.setInt(++i, pc.getPcContribution());
            pstm.setInt(++i, pc.getClanContribution());
            pstm.setString(++i, pc.getClanNameContribution());
            pstm.setInt(++i, pc.getlogpcpower_SkillCount());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor1());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor2());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor3());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor4());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor5());
            pstm.setInt(++i, pc.getReductionDmg());
            pstm.setInt(++i, pc.getpcdmg());
            pstm.setInt(++i, pc.getpaycount());
            pstm.setInt(++i, pc.getlogintime());
            pstm.setInt(++i, pc.getloginpoly());
            pstm.setInt(++i, pc.getguaji_count());
            pstm.setInt(++i, pc.getday());
            pstm.setInt(++i, pc.getPrestige());
            pstm.setInt(++i, pc.getclanadena());
            pstm.setInt(++i, pc.getQuburcount());
            pstm.setInt(++i, pc.getcardpoly());
            pstm.setInt(++i, pc.getpcezpay());
            pstm.setString(++i, pc.getTheEnemy());
            pstm.execute();
        }
        catch (SQLException e) {
            MySqlCharacterStorage._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void deleteCharacter(String accountName, String charName) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM characters WHERE account_name=? AND char_name=?");
            pstm.setString(1, accountName);
            pstm.setString(2, charName);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("could not delete character");
            }
            else {
                int objid = CharObjidTable.get().charObjid(charName);
                if (objid != 0) {
                    CharItemsReading.get().delUserItems(objid);
                }
                pstm = con.prepareStatement("DELETE FROM character_buddys WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
                pstm.setString(1, charName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_buff WHERE char_obj_id IN (SELECT objid FROM characters WHERE char_name = ?)");
                pstm.setString(1, charName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_config WHERE object_id IN (SELECT objid FROM characters WHERE char_name = ?)");
                pstm.setString(1, charName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_quests WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
                pstm.setString(1, charName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_skills WHERE char_obj_id IN (SELECT objid FROM characters WHERE char_name = ?)");
                pstm.setString(1, charName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM character_teleport WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
                pstm.setString(1, charName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM characters WHERE char_name=?");
                pstm.setString(1, charName);
                pstm.execute();
                pstm = con.prepareStatement("DELETE FROM clan_members WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
                pstm.setString(1, charName);
                pstm.execute();
            }
        }
        catch (SQLException e) {
            MySqlCharacterStorage._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void storeCharacter(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            int i = 0;
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE characters SET level=?,HighLevel=?,Exp=?,MaxHp=?,CurHp=?,MaxMp=?,CurMp=?,Ac=?,Str=?,Con=?,Dex=?,Cha=?,Intel=?,Wis=?,Status=?,Class=?,Sex=?,Type=?,Heading=?,LocX=?,LocY=?,MapID=?,Food=?,Lawful=?,Title=?,ClanID=?,Clanname=?,ClanRank=?,rejoin_clan_time=?,BonusStatus=?,ElixirStatus=?,ElfAttr=?,PKcount=?,PkCountForElf=?,ExpRes=?,PartnerID=?,AccessLevel=?,OnlineStatus=?,HomeTownID=?,Contribution=?,HellTime=?,Banned=?,Karma=?,LastPk=?,LastPkForElf=?,DeleteTime=?,logpcpower=?,PunishTime=?,BanError=?,InputBanError=?,SpeedError=?,RocksPrisonTime=?,IvorytowerTime=?,LastabardTime=?,DragonValleyTime=?,PcContribution=?,ClanContribution=?,ClanNameContribution=?,logpcpower_SkillCount=?,logpcpower_SkillFor1=?,logpcpower_SkillFor2=?,logpcpower_SkillFor3=?,logpcpower_SkillFor4=?,logpcpower_SkillFor5=?,ReductionDmg=?,pcdmg=?,paycount=?,logintime=?,loginpoly=?,guaji_count=?,day=?,PrestigeLv=?,clanadena=?,Quburcount=?,cardpoly=?,pcezpay=?,TheEnemy=? WHERE objid=?");
            pstm.setInt(++i, pc.getLevel());
            pstm.setInt(++i, pc.getHighLevel());
            pstm.setLong(++i, pc.getExp());
            pstm.setInt(++i, (int)pc.getBaseMaxHp());
            int hp = pc.getCurrentHp();
            if (hp < 1) {
                hp = 1;
            }
            pstm.setInt(++i, hp);
            pstm.setInt(++i, (int)pc.getBaseMaxMp());
            pstm.setInt(++i, pc.getCurrentMp());
            pstm.setInt(++i, pc.getAc());
            pstm.setInt(++i, pc.getBaseStr());
            pstm.setInt(++i, pc.getBaseCon());
            pstm.setInt(++i, pc.getBaseDex());
            pstm.setInt(++i, pc.getBaseCha());
            pstm.setInt(++i, pc.getBaseInt());
            pstm.setInt(++i, pc.getBaseWis());
            pstm.setInt(++i, pc.getCurrentWeapon());
            pstm.setInt(++i, pc.getClassId());
            pstm.setInt(++i, (int)pc.get_sex());
            pstm.setInt(++i, pc.getType());
            pstm.setInt(++i, pc.getHeading());
            pstm.setInt(++i, pc.getX());
            pstm.setInt(++i, pc.getY());
            pstm.setInt(++i, (int)pc.getMapId());
            pstm.setInt(++i, pc.get_food());
            pstm.setInt(++i, pc.getLawful());
            pstm.setString(++i, pc.getTitle());
            pstm.setInt(++i, pc.getClanid());
            pstm.setString(++i, pc.getClanname());
            pstm.setInt(++i, pc.getClanRank());
            pstm.setTimestamp(++i, pc.getRejoinClanTime());
            pstm.setInt(++i, pc.getBonusStats());
            pstm.setInt(++i, pc.getElixirStats());
            pstm.setInt(++i, pc.getElfAttr());
            pstm.setInt(++i, pc.get_PKcount());
            pstm.setInt(++i, pc.getPkCountForElf());
            pstm.setInt(++i, pc.getExpRes());
            pstm.setInt(++i, pc.getPartnerId());
            short accesslevel = pc.getAccessLevel();
            if (accesslevel > 200) {
                accesslevel = 0;
            }
            pstm.setShort(++i, accesslevel);
            pstm.setInt(++i, pc.getOnlineStatus());
            pstm.setInt(++i, pc.getHomeTownId());
            pstm.setInt(++i, pc.getContribution());
            pstm.setInt(++i, pc.getHellTime());
            pstm.setBoolean(++i, pc.isBanned());
            pstm.setInt(++i, pc.getKarma());
            pstm.setTimestamp(++i, pc.getLastPk());
            pstm.setTimestamp(++i, pc.getLastPkForElf());
            pstm.setTimestamp(++i, pc.getDeleteTime());
            pstm.setInt(++i, pc.getMeteLevel());
            pstm.setTimestamp(++i, pc.getPunishTime());
            pstm.setInt(++i, pc.getBanError());
            pstm.setInt(++i, pc.getInputBanError());
            pstm.setInt(++i, pc.getSpeedError());
            pstm.setInt(++i, pc.getRocksPrisonTime());
            pstm.setInt(++i, pc.getIvoryTowerTime());
            pstm.setInt(++i, pc.getLastabardTime());
            pstm.setInt(++i, pc.getDragonValleyTime());
            pstm.setInt(++i, pc.getPcContribution());
            pstm.setInt(++i, pc.getClanContribution());
            pstm.setString(++i, pc.getClanNameContribution());
            pstm.setInt(++i, pc.getlogpcpower_SkillCount());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor1());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor2());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor3());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor4());
            pstm.setInt(++i, pc.getlogpcpower_SkillFor5());
            pstm.setInt(++i, pc.getReductionDmg());
            pstm.setInt(++i, pc.getpcdmg());
            pstm.setInt(++i, pc.getpaycount());
            pstm.setInt(++i, pc.getlogintime());
            pstm.setInt(++i, pc.getloginpoly());
            pstm.setInt(++i, pc.getguaji_count());
            pstm.setInt(++i, pc.getday());
            pstm.setInt(++i, pc.getPrestige());
            pstm.setInt(++i, pc.getclanadena());
            pstm.setInt(++i, pc.getQuburcount());
            pstm.setInt(++i, pc.getcardpoly());
            pstm.setInt(++i, pc.getpcezpay());
            pstm.setString(++i, pc.getTheEnemy());
            pstm.setInt(++i, pc.getId());
            pstm.execute();
        }
        catch (SQLException e) {
            MySqlCharacterStorage._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void alldeleteCharacter(String accountName, String charName) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM characters WHERE account_name=? AND char_name=?");
            pstm.setString(1, accountName);
            pstm.setString(2, charName);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("could not delete character");
            }
            else {
                pstm = con.prepareStatement("DELETE FROM characters WHERE char_name=?");
                pstm.setString(1, charName);
                pstm.execute();
            }
        }
        catch (SQLException e) {
            throw e;
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

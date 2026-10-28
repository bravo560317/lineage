package com.lineage.server.datatables.sql;

import com.lineage.server.IdFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import java.util.Collection;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.world.WorldClan;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Clan;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.ClanStorage;

public class ClanTable implements ClanStorage
{
    private static final Log _log;
    private final Map<Integer, L1Clan> _clans;
    private static ClanTable _instance;
    
    static {
        _log = LogFactory.getLog(ClanTable.class);
    }
    
    public ClanTable() {
        this._clans = new HashMap();
    }
    
    public static ClanTable getInstance() {
        if (ClanTable._instance == null) {
            ClanTable._instance = new ClanTable();
        }
        return ClanTable._instance;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `clan_data` ORDER BY `clan_id`");
            rs = ps.executeQuery();
            while (rs.next()) {
                L1Clan clan = new L1Clan();
                int clan_id = rs.getInt("clan_id");
                clan.setClanId(clan_id);
                clan.setClanName(rs.getString("clan_name"));
                clan.setLeaderId(rs.getInt("leader_id"));
                clan.setLeaderName(rs.getString("leader_name"));
                clan.setCastleId(rs.getInt("hascastle"));
                clan.setHouseId(rs.getInt("hashouse"));
                clan.setFoundDate(rs.getTimestamp("found_date"));
                clan.setAnnouncement(rs.getString("announcement"));
                clan.setEmblemId(rs.getInt("emblem_id"));
                clan.setEmblemStatus(rs.getInt("emblem_status"));
                clan.setClanLevel(rs.getInt("clan_level"));
                clan.setClanContribution(rs.getInt("clan_contribution"));
                clan.setClanSkillId(rs.getInt("clanskill_id"));
                clan.setClanSkillLv(rs.getInt("clanskill_lv"));
                clan.setclanadena(rs.getInt("clan_adena"));
                boolean clanskill = rs.getBoolean("clanskill");
                if (clanskill) {
                    clan.set_clanskill(clanskill);
                    Timestamp skilltime = rs.getTimestamp("skilltime");
                    clan.set_skilltime(skilltime);
                }
                clan.addAlliance(rs.getInt("alliance1"));
                clan.addAlliance(rs.getInt("alliance2"));
                clan.addAlliance(rs.getInt("alliance3"));
                clan.addAlliance(rs.getInt("alliance4"));
                WorldClan.get().storeClan(clan);
                this._clans.put(clan_id, clan);
            }
        }
        catch (SQLException e) {
            ClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        ClanTable._log.info("載入血盟資料資料數量: " + this._clans.size() + "(" + timer.get() + "ms)");
        Collection<L1Clan> AllClan = WorldClan.get().getAllClans();
        for (L1Clan clan2 : AllClan) {
            Connection cn2 = null;
            PreparedStatement ps2 = null;
            ResultSet rs2 = null;
            try {
                cn2 = DatabaseFactory.get().getConnection();
                ps2 = cn2.prepareStatement("SELECT `char_name` FROM `characters` WHERE `ClanID`=?");
                ps2.setInt(1, clan2.getClanId());
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    clan2.addMemberName(rs2.getString("char_name"));
                }
            }
            catch (SQLException e2) {
                ClanTable._log.error(e2.getLocalizedMessage(), e2);
            }
            finally {
                SQLUtil.close(rs2);
                SQLUtil.close(ps2);
                SQLUtil.close(cn2);
            }
        }
        for (L1Clan clan2 : AllClan) {
            clan2.getDwarfForClanInventory().loadItems();
        }
    }
    
    @Override
    public void addDeClan(Integer integer, L1Clan l1Clan) {
        WorldClan.get().storeClan(l1Clan);
        this._clans.put(integer, l1Clan);
    }
    
    @Override
    public L1Clan createClan(L1PcInstance player, String clan_name) {
        Collection<L1Clan> allClans = WorldClan.get().getAllClans();
        for (L1Clan oldClans : allClans) {
            if (oldClans.getClanName().equalsIgnoreCase(clan_name)) {
                return null;
            }
        }
        L1Clan clan = new L1Clan();
        clan.setClanId(IdFactory.get().nextId());
        clan.setClanName(clan_name);
        clan.setLeaderId(player.getId());
        clan.setLeaderName(player.getName());
        clan.setCastleId(0);
        clan.setHouseId(0);
        clan.setFoundDate(new Timestamp(System.currentTimeMillis()));
        clan.setAnnouncement("");
        clan.setEmblemId(0);
        clan.setEmblemStatus(0);
        clan.setClanLevel(0);
        clan.setClanContribution(0);
        clan.set_clanskill(false);
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `clan_data` SET `clan_id`=?,`clan_name`=?,`leader_id`=?,`leader_name`=?,`hascastle`=?,`hashouse`=?, found_date=?, announcement=?, emblem_id=?, emblem_status=?,`clan_level`=?,`clan_contribution`=?,`clanskill`=?,`skilltime`=?,`clan_adena`=?");
            int i = 0;
            ps.setInt(++i, clan.getClanId());
            ps.setString(++i, clan.getClanName());
            ps.setInt(++i, clan.getLeaderId());
            ps.setString(++i, clan.getLeaderName());
            ps.setInt(++i, clan.getCastleId());
            ps.setInt(++i, clan.getHouseId());
            ps.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
            ps.setString(++i, "");
            ps.setInt(++i, 0);
            ps.setInt(++i, 0);
            ps.setInt(++i, clan.getClanLevel());
            ps.setInt(++i, clan.getClanContribution());
            ps.setBoolean(++i, clan.isClanskill());
            ps.setTimestamp(++i, clan.get_skilltime());
            ps.setInt(++i, clan.getclanadena());
            ps.execute();
        }
        catch (SQLException e) {
            ClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        WorldClan.get().storeClan(clan);
        this._clans.put(clan.getClanId(), clan);
        player.setClanid(clan.getClanId());
        player.setClanname(clan.getClanName());
        player.setClanRank(10);
        clan.addMemberName(player.getName());
        try {
            player.save();
        }
        catch (Exception e2) {
            ClanTable._log.error(e2.getLocalizedMessage(), e2);
        }
        return clan;
    }
    
    @Override
    public void updateClan(L1Clan clan) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE clan_data SET `clan_id`=?,`leader_id`=?,`leader_name`=?,`hascastle`=?,`hashouse`=?, found_date=?, announcement=?, emblem_id=?, emblem_status=?,`clan_level`=?,`clan_contribution`=?,`clanskill`=?,`skilltime`=?,`clan_adena`=?,alliance1=?, alliance2=?, alliance3=?, alliance4=? WHERE `clan_name`=?");
            ps.setInt(1, clan.getClanId());
            ps.setInt(2, clan.getLeaderId());
            ps.setString(3, clan.getLeaderName());
            ps.setInt(4, clan.getCastleId());
            ps.setInt(5, clan.getHouseId());
            ps.setTimestamp(6, clan.getFoundDate());
            ps.setString(7, clan.getAnnouncement());
            ps.setInt(8, clan.getEmblemId());
            ps.setInt(9, clan.getEmblemStatus());
            ps.setInt(10, clan.getClanLevel());
            ps.setInt(11, clan.getClanContribution());
            ps.setBoolean(12, clan.isClanskill());
            ps.setTimestamp(13, clan.get_skilltime());
            ps.setInt(14, clan.getclanadena());
            int count = 15;
            Integer[] alliance;
            for (int length = (alliance = clan.Alliance()).length, j = 0; j < length; ++j) {
                int i1 = alliance[j];
                ps.setInt(count, i1);
                ++count;
            }
            if (count < 19) {
                while (count < 19) {
                    ps.setInt(count, 0);
                    ++count;
                }
            }
            ps.setString(19, clan.getClanName());
            ps.execute();
        }
        catch (SQLException e) {
            ClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public void deleteClan(String clan_name) {
        L1Clan clan = WorldClan.get().getClan(clan_name);
        if (clan == null) {
            return;
        }
        else {
            Connection cn = null;
            PreparedStatement ps = null;
            try {
                cn = DatabaseFactory.get().getConnection();
                ps = cn.prepareStatement("DELETE FROM `clan_data` WHERE `clan_name`=?");
                ps.setString(1, clan_name);
                ps.execute();
            }
            catch (SQLException e) {
                ClanTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(cn);
            }
            clan.getDwarfForClanInventory().clearItems();
            clan.getDwarfForClanInventory().deleteAllItems();
            WorldClan.get().removeClan(clan);
            this._clans.remove((Integer)clan.getClanId());
            return;
        }
    }
    
    @Override
    public L1Clan getTemplate(int clan_id) {
        return this._clans.get((Integer)clan_id);
    }
    
    @Override
    public Map<Integer, L1Clan> get_clans() {
        return this._clans;
    }
    
    @Override
    public void updateClanSkill(L1Clan clan) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE clan_data SET `clanskill_id`=?,`clanskill_lv`=? WHERE `clan_id`=?");
            ps.setInt(1, clan.getClanSkillId());
            ps.setInt(2, clan.getClanSkillLv());
            ps.setInt(3, clan.getClanId());
            ps.execute();
        }
        catch (SQLException e) {
            ClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}

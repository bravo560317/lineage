package com.lineage.server.datatables;

import java.util.Collection;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.server.templates.L1EmblemIcon;
import com.lineage.server.IdFactory;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.config.Config;
import com.lineage.DatabaseFactoryLogin;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.DeClan;
import java.util.Map;
import org.apache.commons.logging.Log;

public class DeClanTable
{
    private static final Log _log;
    private static final Map<Integer, DeClan> _declanList;
    private static DeClanTable _instance;
    
    static {
        _log = LogFactory.getLog(DeClanTable.class);
        _declanList = new HashMap();
    }
    
    public static DeClanTable get() {
        if (DeClanTable._instance == null) {
            DeClanTable._instance = new DeClanTable();
        }
        return DeClanTable._instance;
    }
    
    public void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `de_clan`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                String clanname = "";
                if (Config.CLIENT_LANGUAGE == 3) {
                    clanname = rs.getString("clannamebig5");
                }
                else {
                    clanname = rs.getString("clanname");
                }
                int clan_id = rs.getInt("clan_id");
                String leader_name = "";
                if (Config.CLIENT_LANGUAGE == 3) {
                    leader_name = rs.getString("leader_namebig5");
                }
                else {
                    leader_name = rs.getString("leader_name");
                }
                int emblemid = rs.getInt("emblemid");
                DeClan deClan = new DeClan(clanname, clan_id, leader_name, emblemid);
                DeClanTable._declanList.put(clan_id, deClan);
            }
        }
        catch (SQLException e) {
            DeClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void loadIcon() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `de_emblem_tmp`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int clanid = rs.getInt("clan_id");
                byte[] icon = rs.getBytes("emblemicon");
                int update = rs.getInt("update");
                int emblemid = rs.getInt("emblemid");
                if (emblemid == 0) {
                    emblemid = IdFactory.get().nextId();
                    this.updata_de_emblem(emblemid, clanid);
                }
                L1EmblemIcon emblemIcon = new L1EmblemIcon();
                emblemIcon.set_clanid(clanid);
                emblemIcon.set_clanIcon(icon);
                emblemIcon.set_update(update);
                emblemIcon.set_emblemid(emblemid);
                this.updata_deClan(emblemIcon);
                ClanEmblemReading.get().addDeclanEmblem(clanid, emblemIcon);
            }
        }
        catch (SQLException e) {
            DeClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void updata_deClan(L1EmblemIcon emblemIcon) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactoryLogin.get().getConnection();
            ps = cn.prepareStatement("UPDATE `de_clan` SET `emblemid`=? WHERE `clan_id`=?");
            int i = 0;
            ps.setInt(++i, emblemIcon.get_emblemid());
            ps.setInt(++i, emblemIcon.get_clanid());
            ps.execute();
        }
        catch (SQLException e) {
            DeClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void updata_de_emblem(int emblemid, int clanid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactoryLogin.get().getConnection();
            ps = cn.prepareStatement("UPDATE `de_emblem_tmp` SET `emblemid`=? WHERE `clan_id`=?");
            int i = 0;
            ps.setInt(++i, emblemid);
            ps.setInt(++i, clanid);
            ps.execute();
        }
        catch (SQLException e) {
            DeClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public DeClan get(int clanid) {
        return DeClanTable._declanList.get((Integer)clanid);
    }
    
    public Collection<DeClan> getList() {
        return (Collection)DeClanTable._declanList.values();
    }
}

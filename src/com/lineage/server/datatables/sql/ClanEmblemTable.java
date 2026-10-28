package com.lineage.server.datatables.sql;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1EmblemIcon;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.ClanEmblemStorage;

public class ClanEmblemTable implements ClanEmblemStorage
{
    private static final Log _log;
    private static final Map<Integer, L1EmblemIcon> _iconList;
    
    static {
        _log = LogFactory.getLog(ClanEmblemTable.class);
        _iconList = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `clan_emblem`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int clanid = rs.getInt("clan_id");
                if (ClanReading.get().getTemplate(clanid) != null) {
                    byte[] icon = rs.getBytes("emblemicon");
                    int update = rs.getInt("update");
                    int emblemid = rs.getInt("emblemid");
                    L1EmblemIcon emblemIcon = new L1EmblemIcon();
                    emblemIcon.set_clanid(clanid);
                    emblemIcon.set_clanIcon(icon);
                    emblemIcon.set_update(update);
                    emblemIcon.set_emblemid(emblemid);
                    ClanEmblemTable._iconList.put(clanid, emblemIcon);
                }
                else {
                    this.deleteIcon(clanid);
                }
            }
        }
        catch (SQLException e) {
            ClanEmblemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ClanEmblemTable._log.info("載入盟輝圖檔紀錄資料數量: " + ClanEmblemTable._iconList.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public L1EmblemIcon get(int clan_id) {
        return ClanEmblemTable._iconList.get((Integer)clan_id);
    }
    
    @Override
    public L1EmblemIcon getEmblem(int emblemid) {
        for (L1EmblemIcon icon : ClanEmblemTable._iconList.values()) {
            if (icon.get_emblemid() == emblemid) {
                return icon;
            }
        }
        return null;
    }
    
    @Override
    public void addDeclanEmblem(int clan_id, L1EmblemIcon emblemIcon) {
        ClanEmblemTable._iconList.put(clan_id, emblemIcon);
    }
    
    @Override
    public void deleteIcon(int clan_id) {
        ClanEmblemTable._iconList.remove((Integer)clan_id);
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `clan_emblem` WHERE `clan_id`=?");
            ps.setInt(1, clan_id);
            ps.execute();
        }
        catch (SQLException e) {
            ClanEmblemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public L1EmblemIcon storeClanIcon(int clan_id, byte[] icon, int emblemid) {
        L1EmblemIcon emblemIcon = new L1EmblemIcon();
        emblemIcon.set_clanid(clan_id);
        emblemIcon.set_clanIcon(icon);
        emblemIcon.set_update(0);
        emblemIcon.set_emblemid(emblemid);
        ClanEmblemTable._iconList.put(clan_id, emblemIcon);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `clan_emblem` SET `clan_id`=?,`emblemicon`=?,`update`=?,`emblemid`=?");
            int i = 0;
            pstm.setInt(++i, clan_id);
            pstm.setBytes(++i, icon);
            pstm.setInt(++i, 0);
            pstm.setInt(++i, emblemid);
            pstm.execute();
        }
        catch (SQLException e) {
            ClanEmblemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return emblemIcon;
    }
    
    @Override
    public void updateClanIcon(L1EmblemIcon emblemIcon) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `clan_emblem` SET `emblemicon`=?,`update`=?,`emblemid`=? WHERE `clan_id`=?");
            int i = 0;
            pstm.setBytes(++i, emblemIcon.get_clanIcon());
            pstm.setInt(++i, emblemIcon.get_update());
            pstm.setInt(++i, emblemIcon.get_emblemid());
            pstm.setInt(++i, emblemIcon.get_clanid());
            pstm.execute();
        }
        catch (SQLException e) {
            ClanEmblemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

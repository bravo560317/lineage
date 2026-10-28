package com.lineage.server.datatables;

import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import com.lineage.server.model.drop.SetDropExecutor;
import com.lineage.server.templates.L1DropMob;
import java.util.Map;
import com.lineage.server.model.drop.SetDrop;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class DropMobTable
{
    private static final Log _log;
    private static DropMobTable _instance;
    
    static {
        _log = LogFactory.getLog(DropMobTable.class);
    }
    
    public static DropMobTable get() {
        if (DropMobTable._instance == null) {
            DropMobTable._instance = new DropMobTable();
        }
        return DropMobTable._instance;
    }
    
    public void load() {
        Map<Integer, L1DropMob> droplists = this.allMobDropList();
        SetDropExecutor setDropExecutor = new SetDrop();
        setDropExecutor.addDropMob(droplists);
    }
    
    private Map<Integer, L1DropMob> allMobDropList() {
        Map<Integer, L1DropMob> _droplistMob = new HashMap();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Label_0321: {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("SELECT * FROM `系統_掉落全怪物`");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    int itemId = rs.getInt("itemid");
                    int min = rs.getInt("min");
                    int max = rs.getInt("max");
                    int chance = rs.getInt("chance");
                    int week = rs.getInt("限制星期");
                    int starttime = rs.getInt("幾點開始");
                    int unitytime = rs.getInt("幾點結束");
                    String note = rs.getString("note");
                    if (!note.contains("=>")) {
                        updata_name(itemId);
                    }
                    if (this.check_item(itemId)) {
                        L1DropMob drop = new L1DropMob();
                        drop.setMin(min);
                        drop.setMax(max);
                        drop.setChance(chance);
                        drop.setweek(week);
                        drop.setStarttime(starttime);
                        drop.setunitytime(unitytime);
                        _droplistMob.put(itemId, drop);
                    }
                }
            }
            catch (SQLException e) {
                DropMobTable._log.error(e.getLocalizedMessage(), e);
                break Label_0321;
            }
            finally {
                SQLUtil.close(rs);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
        }
        DropMobTable._log.info("載入全部怪物掉落:" + _droplistMob.size());
        return _droplistMob;
    }
    
    private static void updata_name(int itemid) {
        Connection cn = null;
        PreparedStatement ps = null;
        String itemname = ItemTable.get().getTemplate(itemid).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `系統_掉落全怪物` SET `note`=? WHERE `itemid`=?");
            int i = 0;
            ps.setString(++i, "全怪掉落->" + itemname);
            ps.setInt(++i, itemid);
            ps.execute();
        }
        catch (SQLException e) {
            DropMobTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private boolean check_item(int itemId) {
        L1Item itemTemplate = ItemTable.get().getTemplate(itemId);
        if (itemTemplate == null) {
            DropMobTable._log.info("全部怪物掉落錯誤物品->" + itemId);
            return false;
        }
        else {
            return true;
        }
    }
}

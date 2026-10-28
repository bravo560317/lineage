package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ItemHtml;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class ItemHtmlTable
{
    public static final Log _log;
    private static final HashMap<Integer, L1ItemHtml> _htmlMap;
    private static ItemHtmlTable _instance;
    
    static {
        _log = LogFactory.getLog(ItemHtmlTable.class);
        _htmlMap = new HashMap();
    }
    
    public static ItemHtmlTable get() {
        if (ItemHtmlTable._instance == null) {
            ItemHtmlTable._instance = new ItemHtmlTable();
        }
        return ItemHtmlTable._instance;
    }
    
    private ItemHtmlTable() {
        this.load();
    }
    
    public void load() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `w_道具讀取對話檔`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                int quest_id = rs.getInt("quest_id");
                int quest_step = rs.getInt("quest_step");
                String html = rs.getString("html");
                if (ItemTable.get().getTemplate(itemid) == null) {
                    ItemHtmlTable._log.error("自定對話道具資料錯誤: 沒有這個編號的道具:" + itemid);
                }
                else {
                    L1ItemHtml value = new L1ItemHtml();
                    value.setItemId(itemid);
                    value.setQuestId(quest_id);
                    value.setQuestStep(quest_step);
                    value.setHtml(html);
                    ItemHtmlTable._htmlMap.put(itemid, value);
                }
            }
        }
        catch (SQLException e) {
            ItemHtmlTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        ItemHtmlTable._log.info("載入自定對話道具:" + ItemHtmlTable._htmlMap.size());
    }
    
    public L1ItemHtml getHtml(int itemid) {
        try {
            if (ItemHtmlTable._htmlMap.containsKey((Integer)itemid)) {
                return ItemHtmlTable._htmlMap.get((Integer)itemid);
            }
        }
        catch (Exception ex) {}
        return null;
    }
}

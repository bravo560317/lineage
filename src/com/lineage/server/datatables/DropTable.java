package com.lineage.server.datatables;

import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.model.drop.SetDropExecutor;
import com.lineage.server.model.drop.SetDrop;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Drop;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;

public class DropTable
{
    private static final Log _log;
    private static DropTable _instance;
    private Map<Integer, ArrayList<L1Drop>> droplistMapcopy;
    
    static {
        _log = LogFactory.getLog(DropTable.class);
    }
    
    public DropTable() {
        this.droplistMapcopy = new HashMap();
    }
    
    public static DropTable get() {
        if (DropTable._instance == null) {
            DropTable._instance = new DropTable();
        }
        return DropTable._instance;
    }
    
    public void load() {
        Map<Integer, ArrayList<L1Drop>> droplists = this.allDropList();
        SetDropExecutor setDropExecutor = new SetDrop();
        setDropExecutor.addDropMap(droplists);
    }
    
    private Map<Integer, ArrayList<L1Drop>> allDropList() {
        PerformanceTimer timer = new PerformanceTimer();
        Map<Integer, ArrayList<L1Drop>> droplistMap = new HashMap();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `droplist`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int mobId = rs.getInt("mobId");
                int itemId = rs.getInt("itemId");
                int min = rs.getInt("min");
                int max = rs.getInt("max");
                int chance = rs.getInt("chance");
                String note = rs.getString("note");
                if (NpcTable.get().getTemplate(mobId) == null) {
                    DropTable._log.info("→(droplist)怪物編號：" + mobId + " 不存在。");
                }
                if (ItemTable.get().getTemplate(itemId) == null) {
                    DropTable._log.info("→(droplist)道具編號：" + itemId + " 不存在。");
                }
                if (min > max) {
                    DropTable._log.info("→(droplist)怪物：" + mobId + " 道具：" + itemId + " 最小值與最大值設定錯誤。");
                }
                if (chance > 1000000) {
                    System.out.println("→怪物：" + mobId + " 道具：" + itemId + " 機率設定錯誤。");
                    DropTable._log.info("→(droplist)怪物：" + mobId + " 道具：" + itemId + " 機率設定錯誤。");
                }
                if (this.check_item(itemId, mobId, note)) {
                    L1Drop drop = new L1Drop(mobId, itemId, min, max, chance);
                    ArrayList<L1Drop> dropList = droplistMap.get((Integer)drop.getMobid());
                    if (dropList == null) {
                        dropList = new ArrayList();
                        droplistMap.put(new Integer(drop.getMobid()), dropList);
                    }
                    dropList.add(drop);
                }
            }
            this.droplistMapcopy.putAll(droplistMap);
        }
        catch (SQLException e) {
            DropTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        DropTable._log.info("載入掉落物品資料數量: " + droplistMap.size() + "(" + timer.get() + "ms)");
        return droplistMap;
    }
    
    private boolean check_item(int itemId, int mobId, String note) {
        L1Item itemTemplate = ItemTable.get().getTemplate(itemId);
        if (itemTemplate == null) {
            errorItem(itemId);
            return false;
        }
        else if (!note.contains("=>")) {
            String itemname = itemTemplate.getName();
            this.updata_name(itemname, itemId, mobId);
            return true;
        }
        else {
            return true;
        }
    }
    
    private void updata_name(String itemname, int itemId, int mobId) {
        Connection cn = null;
        PreparedStatement ps = null;
        String npcname = NpcTable.get().getNpcName(mobId);
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `droplist` SET `note`=? WHERE `itemId`=? AND `mobId`=?");
            int i = 0;
            ps.setString(++i, npcname + "=>" + itemname);
            ps.setInt(++i, itemId);
            ps.setInt(++i, mobId);
            ps.execute();
        }
        catch (SQLException e) {
            DropTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void errorItem(int itemid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `droplist` WHERE `itemId`=?");
            pstm.setInt(1, itemid);
            pstm.execute();
        }
        catch (SQLException e) {
            DropTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public ArrayList<L1Drop> getdroplist(int mobid) {
        return this.droplistMapcopy.get((Integer)mobid);
    }
}

package com.lineage.server.datatables;

import java.util.Iterator;
import com.lineage.server.templates.L1Account;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import com.lineage.server.templates.L1Acc_use_item;
import java.util.Map;
import org.apache.commons.logging.Log;

public class Acc_use_Item
{
    public static final Log _log;
    private static final Map<Integer, L1Acc_use_item> _offList;
    private static final ArrayList<Integer> _itemList;
    private static final ArrayList<Integer> _itemList1;
    private static Acc_use_Item _instance;
    
    static {
        _log = LogFactory.getLog(Acc_use_Item.class);
        _offList = new HashMap();
        _itemList = new ArrayList();
        _itemList1 = new ArrayList();
    }
    
    public static Acc_use_Item get() {
        if (Acc_use_Item._instance == null) {
            Acc_use_Item._instance = new Acc_use_Item();
        }
        return Acc_use_Item._instance;
    }
    
    private Acc_use_Item() {
        this.load();
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `characters_帳號使用道具`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("id");
                int item_id = rs.getInt("item_id");
                int obj = rs.getInt("obj");
                L1Acc_use_item value = new L1Acc_use_item();
                value.set_item_id(item_id);
                value.set_obj(obj);
                Acc_use_Item._offList.put(key, value);
                if (!Acc_use_Item._itemList.contains((Integer)item_id) && !Acc_use_Item._itemList1.contains((Integer)obj)) {
                    Acc_use_Item._itemList1.add(obj);
                    Acc_use_Item._itemList.add(item_id);
                }
            }
        }
        catch (SQLException e) {
            Acc_use_Item._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public boolean checkAreaArmorOff(L1PcInstance pc, int itemId) {
        if (Acc_use_Item._offList == null) {
            return true;
        }
        else {
            L1Account account = pc.getNetConnection().getAccount();
            for (Integer key : Acc_use_Item._offList.keySet()) {
                L1Acc_use_item off = Acc_use_Item._offList.get(key);
                if (off.get_item_id() == itemId && off.get_obj() == account.get_id_card()) {
                    return false;
                }
            }
            return true;
        }
    }
    
    public void Add(L1PcInstance pc, int itemid) {
        Connection con = null;
        PreparedStatement pstm = null;
        L1Account account = pc.getNetConnection().getAccount();
        try {
            int i = 0;
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO characters_帳號使用道具 SET item_id=?,obj=?");
            ++i;
            pstm.setInt(i, itemid);
            ++i;
            pstm.setInt(i, account.get_id_card());
            ++i;
            pstm.execute();
        }
        catch (SQLException e) {
            Acc_use_Item._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

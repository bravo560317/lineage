package com.lineage.server.datatables;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.config.ConfigBoxs;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import com.lineage.server.templates.L1Box;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import org.apache.commons.logging.Log;

public class ItemBoxTable
{
    private static final Log _log;
    private static ItemBoxTable _instance;
    private static final Random _random;
    private static final Map<Integer, ArrayList<L1Box>> _box;
    private static final Map<Integer, ArrayList<L1Box>> _boxs;
    private static final Map<Integer, HashMap<Integer, ArrayList<L1Box>>> _boxkey;
    
    static {
        _log = LogFactory.getLog(ItemBoxTable.class);
        _random = new Random();
        _box = new HashMap();
        _boxs = new HashMap();
        _boxkey = new HashMap();
    }
    
    public static ItemBoxTable get() {
        if (ItemBoxTable._instance == null) {
            ItemBoxTable._instance = new ItemBoxTable();
        }
        return ItemBoxTable._instance;
    }
    
    public static void reload() {
        ItemBoxTable._instance = new ItemBoxTable();
        ItemBoxTable._box.clear();
        ItemBoxTable._boxs.clear();
        ItemBoxTable._boxkey.clear();
        ItemBoxTable._instance.load();
    }
    
    public void load() {
        this.load_box();
        this.load_boxs();
        this.load_box_key();
    }
    
    public void load_box() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int i = 0;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `etcitem_box`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("box_item_id");
                L1Item temp = ItemTable.get().getTemplate(key);
                if (temp == null) {
                    this.del_box(key);
                }
                else if (temp.getType() != 16) {
                    continue;
                }
                else {
                    int get_item_id = rs.getInt("get_item_id");
                    String note = rs.getString("name");
                    L1Item tempget = ItemTable.get().getTemplate(key);
                    if (tempget == null) {
                        this.del_box2(key);
                    }
                    else {
                        L1Box box = new L1Box();
                        box.set_box_item_id(key);
                        box.set_get_item_id(get_item_id);
                        box.set_randomint(rs.getInt("randomint"));
                        box.set_random(rs.getInt("random"));
                        box.set_min_count(rs.getInt("min_count"));
                        box.set_max_count(rs.getInt("max_count"));
                        box.set_out(rs.getBoolean("out"));
                        box.set_enchantlvl(rs.getInt("enchantlvl"));
                        ArrayList<L1Box> list = ItemBoxTable._box.get((Integer)key);
                        if (list == null) {
                            list = new ArrayList();
                        }
                        list.add(box);
                        ItemBoxTable._box.put(key, list);
                        ++i;
                        if (!note.contains("=>")) {
                            updata_name(key, get_item_id);
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemBoxTable._log.info("載入箱子開出物設置: " + ItemBoxTable._box.size() + "/" + i + "(" + timer.get() + "ms)");
    }
    
    private void del_box(int id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `etcitem_box` WHERE `box_item_id`=?");
            ps.setInt(1, id);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void del_box2(int get_item_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `etcitem_box` WHERE `get_item_id`=?");
            ps.setInt(1, get_item_id);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void load_boxs() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int i = 0;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `etcitem_boxs`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("box_item_id");
                L1Item temp = ItemTable.get().getTemplate(key);
                if (temp == null) {
                    this.del_boxs(key);
                }
                else if (temp.getType() != 16) {
                    continue;
                }
                else {
                    int get_item_id = rs.getInt("get_item_id");
                    String note = rs.getString("name");
                    L1Item tempget = ItemTable.get().getTemplate(key);
                    if (tempget == null) {
                        this.del_boxs2(key);
                    }
                    else {
                        L1Box box = new L1Box();
                        box.set_box_item_id(key);
                        box.set_get_item_id(get_item_id);
                        box.set_randomint(100);
                        box.set_random(-1);
                        int count = rs.getInt("count");
                        box.set_min_count(count);
                        box.set_max_count(count);
                        box.set_use_type(rs.getInt("use_type"));
                        box.set_enchantlvl(rs.getInt("enchantlvl"));
                        box.set_out(rs.getBoolean("out"));
                        ArrayList<L1Box> list = ItemBoxTable._boxs.get((Integer)key);
                        if (list == null) {
                            list = new ArrayList();
                        }
                        list.add(box);
                        ItemBoxTable._boxs.put(key, list);
                        ++i;
                        if (!note.contains("=>")) {
                            updata_name2(key, get_item_id);
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemBoxTable._log.info("載入箱子開出物設置(多種): " + ItemBoxTable._boxs.size() + "/" + i + "(" + timer.get() + "ms)");
    }
    
    private static void updata_name(int key, int itemId) {
        Connection cn = null;
        PreparedStatement ps = null;
        String boxname = ItemTable.get().getTemplate(key).getName();
        String itemname = ItemTable.get().getTemplate(itemId).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `etcitem_box` SET `name`=? WHERE `box_item_id`=? AND `get_item_id`=?");
            int i = 0;
            ps.setString(++i, boxname + "=>" + itemname);
            ps.setInt(++i, key);
            ps.setInt(++i, itemId);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void updata_name2(int key, int itemId) {
        Connection cn = null;
        PreparedStatement ps = null;
        String boxname = ItemTable.get().getTemplate(key).getName();
        String itemname = ItemTable.get().getTemplate(itemId).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `etcitem_boxs` SET `name`=? WHERE `box_item_id`=? AND `get_item_id`=?");
            int i = 0;
            ps.setString(++i, boxname + "=>" + itemname);
            ps.setInt(++i, key);
            ps.setInt(++i, itemId);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void updata_name3(int key, int itemId) {
        Connection cn = null;
        PreparedStatement ps = null;
        String boxname = ItemTable.get().getTemplate(key).getName();
        String itemname = ItemTable.get().getTemplate(itemId).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `etcitem_box_key` SET `name`=? WHERE `box_item_id`=? AND `get_item_id`=?");
            int i = 0;
            ps.setString(++i, boxname + "=>" + itemname);
            ps.setInt(++i, key);
            ps.setInt(++i, itemId);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void del_boxs(int id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `etcitem_boxs` WHERE `box_item_id`=?");
            ps.setInt(1, id);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void del_boxs2(int get_item_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `etcitem_boxs` WHERE `get_item_id`=?");
            ps.setInt(1, get_item_id);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void load_box_key() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int i = 0;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `etcitem_box_key`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int keyId = rs.getInt("key_itemid");
                L1Item tempkey = ItemTable.get().getTemplate(keyId);
                if (tempkey == null) {
                    this.del_box_key(keyId);
                }
                else {
                    int key = rs.getInt("box_item_id");
                    String note = rs.getString("name");
                    L1Item temp = ItemTable.get().getTemplate(key);
                    if (temp == null) {
                        this.del_box_key2(key);
                    }
                    else if (temp.getType() != 16) {
                        continue;
                    }
                    else {
                        int get_item_id = rs.getInt("get_item_id");
                        L1Item tempget = ItemTable.get().getTemplate(get_item_id);
                        if (tempget == null) {
                            this.del_box_key3(get_item_id);
                        }
                        else {
                            L1Box box = new L1Box();
                            box.set_box_item_id(key);
                            box.set_get_item_id(get_item_id);
                            box.set_randomint(rs.getInt("randomint"));
                            box.set_random(rs.getInt("random"));
                            box.set_min_count(rs.getInt("min_count"));
                            box.set_max_count(rs.getInt("max_count"));
                            box.set_out(rs.getBoolean("out"));
                            box.set_use_type(127);
                            box.set_enchantlvl(rs.getInt("enchantlvl"));
                            HashMap<Integer, ArrayList<L1Box>> map = ItemBoxTable._boxkey.get((Integer)key);
                            if (map == null) {
                                map = new HashMap();
                            }
                            ArrayList<L1Box> keylist = map.get((Integer)keyId);
                            if (keylist == null) {
                                keylist = new ArrayList();
                            }
                            keylist.add(box);
                            map.put(keyId, keylist);
                            ItemBoxTable._boxkey.put(key, map);
                            ++i;
                            if (!note.contains("=>")) {
                                updata_name3(key, get_item_id);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemBoxTable._log.info("載入箱子開出物設置(指定使用物品開啟): " + ItemBoxTable._boxkey.size() + "/" + i + "(" + timer.get() + "ms)");
    }
    
    private void del_box_key(int key_itemid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `etcitem_box_key` WHERE `key_itemid`=?");
            ps.setInt(1, key_itemid);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void del_box_key2(int box_item_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `etcitem_box_key` WHERE `box_item_id`=?");
            ps.setInt(1, box_item_id);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void del_box_key3(int get_item_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `etcitem_box_key` WHERE `get_item_id`=?");
            ps.setInt(1, get_item_id);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public ArrayList<L1Box> get(L1PcInstance pc, L1ItemInstance tgitem) {
        try {
            ArrayList<L1Box> list = ItemBoxTable._box.get((Integer)tgitem.getItemId());
            if (list != null) {
                ItemBoxTable.BoxRandom boxs = new ItemBoxTable.BoxRandom(pc, tgitem, list);
                boxs.getStart();
                return list;
            }
        }
        catch (Exception e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    public void get_all(L1PcInstance pc, L1ItemInstance tgitem) {
        try {
            ArrayList<L1Box> list = ItemBoxTable._boxs.get((Integer)tgitem.getItemId());
            if (list != null) {
                if (list.size() <= 0) {
                    return;
                }
                else if (list.isEmpty()) {
                    return;
                }
                else {
                    int MaxChargeCount = tgitem.getItem().getMaxChargeCount();
                    int chargecount = tgitem.getChargeCount();
                    String name = tgitem.getName();
                    int newchargecount = chargecount - 1;
                    if (MaxChargeCount > 0) {
                        if (chargecount > 0) {
                            tgitem.setChargeCount(newchargecount);
                            pc.getInventory().updateItem(tgitem, 128);
                            for (L1Box box : list) {
                                if (!box.is_use(pc)) {
                                    continue;
                                }
                                else {
                                    outItem(pc, box, name);
                                }
                            }
                        }
                        if (newchargecount <= 0) {
                            pc.getInventory().deleteItem(tgitem);
                        }
                    }
                    else if (pc.getInventory().removeItem(tgitem, 1L) == 1L) {
                        for (L1Box box : list) {
                            if (!box.is_use(pc)) {
                                continue;
                            }
                            else {
                                outItem(pc, box, name);
                            }
                        }
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
        }
        catch (Exception e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean is_key(int tgid, int keyid) {
        HashMap<Integer, ArrayList<L1Box>> map = ItemBoxTable._boxkey.get((Integer)tgid);
        if (map != null) {
            ArrayList<L1Box> keylist = map.get((Integer)keyid);
            if (keylist != null) {
                return true;
            }
        }
        return false;
    }
    
    public void get_key(L1PcInstance pc, L1ItemInstance tgitem, int keyid) {
        try {
            HashMap<Integer, ArrayList<L1Box>> map = ItemBoxTable._boxkey.get((Integer)tgitem.getItemId());
            if (map != null) {
                ArrayList<L1Box> keylist = map.get((Integer)keyid);
                if (keylist != null) {
                    if (keylist.size() <= 0) {
                        return;
                    }
                    else if (keylist.isEmpty()) {
                        return;
                    }
                    else {
                        ItemBoxTable.BoxRandom boxs = new ItemBoxTable.BoxRandom(pc, tgitem, keylist);
                        boxs.getStart();
                    }
                }
            }
        }
        catch (Exception e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
private static void outItem(L1PcInstance pc, L1Box box, String tgitemName) {
    if (box == null || pc == null) {
        return;
    }

    L1Item temp = ItemTable.get().getTemplate(box.get_item_id());
    if (temp == null) {
        ItemBoxTable._log.error("箱子掉落物模板不存在: item_id=" + box.get_item_id());
        return;
    }

    int count = (box.get_min_count() < box.get_max_count())
            ? ItemBoxTable._random.nextInt(box.get_max_count() - box.get_min_count()) + box.get_min_count()
            : box.get_min_count();

    if (count <= 0) {
        ItemBoxTable._log.error("箱子掉落數量為 0，item_id=" + box.get_item_id());
        return;
    }

    int enchantlvl = box.get_enchantlvl();
    L1ItemInstance item = null;

    try {
        if (temp.isStackable()) {
            item = ItemTable.get().createItem(box.get_item_id());
            if (item == null) {
                ItemBoxTable._log.error("createItem 失敗: " + box.get_item_id());
                return;
            }
            item.setCount(count);
            if (enchantlvl > 0) {
                item.setEnchantLevel(enchantlvl);
            }
            createNewItem(pc, item);

        } else {
            for (int i = 0; i < count; ++i) {
                L1ItemInstance tmp = ItemTable.get().createItem(box.get_item_id());
                if (tmp == null) {
                    ItemBoxTable._log.error("createItem 失敗(非堆疊): " + box.get_item_id());
                    continue;
                }
                if (enchantlvl > 0) {
                    tmp.setEnchantLevel(enchantlvl);
                }
                tmp.setIdentified(true);
                createNewItem(pc, tmp);
                item = tmp;
            }
        }

        if (item == null) {
            ItemBoxTable._log.error("最終 item 為 null，無法取得 logName，item_id=" + box.get_item_id());
            return;
        }

        String itemName = item.getLogName();

        if (box.is_out()) {
            ConfigBoxs.msg(pc.getName(), tgitemName, itemName + "(" + item.getCount() + ")");
        }

        RecordTable.get().recordbox(pc.getName(), tgitemName, itemName, count);

    } catch (Exception e) {
        ItemBoxTable._log.error("outItem 例外 item_id=" + box.get_item_id(), e);
    }
}

    
private static void createNewItem(L1PcInstance pc, L1ItemInstance item) {
        try {
            // --- 這是你原本的程式碼 ---
            pc.getInventory().storeItem(item);

// ==========================================
            // [單純追加] 武器防具獲得時，套用隨機炫色能力 (無訊息干擾)
            // ==========================================
            if (item.getItem().getType2() == 1) { 
                try {
                    // 配合原作者筆誤，武器的檔案裡面也是叫 forIntensifyArmor
                    com.lineage.william.drop_type_weapon_item.forIntensifyArmor(pc, item);
                } catch (Exception e) {}
            } 
            else if (item.getItem().getType2() == 2) { 
                try {
                    com.lineage.william.drop_type_armor_item.forIntensifyArmor(pc, item);
                } catch (Exception e) {}
            }
            // ==========================================

            // --- 這是你原本的程式碼 ---
            pc.sendPackets(new com.lineage.server.serverpackets.S_ServerMessage(403, item.getLogName()));
            pc.saveInventory();
            
            // --- [追加] 確保背包圖示立刻更新顯示 ---
            pc.sendPackets(new com.lineage.server.serverpackets.S_ItemStatus(item));
        }
        catch (Exception e) {
            // --- 這是你原本的報錯處理 ---
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void set_box(int box_item_id, int get_item_id, String name, int randomint, int random, int min_count, int max_count) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `etcitem_box` SET `box_item_id`=?,`get_item_id`=?,`name`=?,`randomint`=?,`random`=?,`min_count`=?,`max_count`=?,`out`=?");
            int i = 0;
            ps.setInt(++i, box_item_id);
            ps.setInt(++i, get_item_id);
            ps.setString(++i, name);
            ps.setInt(++i, randomint);
            ps.setInt(++i, random);
            ps.setInt(++i, min_count);
            ps.setInt(++i, max_count);
            ps.setBoolean(++i, false);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public void set_boxs(int box_item_id, int get_item_id, String name, int count) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `etcitem_boxs` SET `box_item_id`=?,`get_item_id`=?,`name`=?,`count`=?,`out`=?");
            int i = 0;
            ps.setInt(++i, box_item_id);
            ps.setInt(++i, get_item_id);
            ps.setString(++i, name);
            ps.setInt(++i, count);
            ps.execute();
        }
        catch (SQLException e) {
            ItemBoxTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private class BoxRandom implements Runnable
    {
        private final L1PcInstance _pc;
        private final ArrayList<L1Box> _list_tmp;
        private final L1ItemInstance _tgitem;
        
        public BoxRandom(L1PcInstance pc, L1ItemInstance tgitem, ArrayList<L1Box> list) {
            this._list_tmp = new ArrayList();
            this._pc = pc;
            this._tgitem = tgitem;
            this._list_tmp.addAll(list);
        }
        
        public void getStart() {
            GeneralThreadPool.get().schedule(this, 0L);
        }
        
        @Override
        public void run() {
            try {
                if (this._list_tmp.size() <= 0) {
                    return;
                }
                else if (this._list_tmp.isEmpty()) {
                    return;
                }
                else {
                    Map<Integer, Integer> tempList = new HashMap();
                    L1Box box = null;
                    int i = 0;
                    while (box == null) {
                        box = this.runItem(tempList);
                        Thread.sleep(1L);
                        if (++i >= 300) {
                            Object[] obj = this._list_tmp.toArray();
                            box = (L1Box)obj[ItemBoxTable._random.nextInt(obj.length)];
                            if (box != null) {
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    if (box != null) {
                        int MaxChargeCount = this._tgitem.getItem().getMaxChargeCount();
                        int chargecount = this._tgitem.getChargeCount();
                        String name = this._tgitem.getName();
                        int newchargecount = chargecount - 1;
                        if (MaxChargeCount > 0) {
                            if (chargecount > 0) {
                                this._tgitem.setChargeCount(newchargecount);
                                this._pc.getInventory().updateItem(this._tgitem, 128);
                                outItem(this._pc, box, name);
                            }
                            if (newchargecount <= 0) {
                                this._pc.getInventory().deleteItem(this._tgitem);
                            }
                        }
                        else if (this._pc.getInventory().removeItem(this._tgitem, 1L) == 1L) {
                            outItem(this._pc, box, name);
                        }
                    }
                }
            }
            catch (Exception e) {
                ItemBoxTable._log.error("寶盒物品設置或取率可能太低,本次開啟未獲得任何物品(寶盒不會被刪除) 寶盒編號:" + this._tgitem.getItemId());
            }
        }
        
        private L1Box runItem(Map<Integer, Integer> tempList) {
            try {
                if (this._list_tmp.size() <= 0) {
                    return null;
                }
                else if (this._list_tmp.isEmpty()) {
                    return null;
                }
                else {
                    int index = ItemBoxTable._random.nextInt(this._list_tmp.size());
                    L1Box score = this._list_tmp.get(index);
                    int random = ItemBoxTable._random.nextInt(score.get_randomint());
                    int srcrandom = score.get_random();
                    if (random < srcrandom) {
                        return score;
                    }
                    else {
                        Integer tmp = tempList.get((Integer)score.get_item_id());
                        if (tmp != null) {
                            tmp = (Integer)(tmp + 1);
                            tempList.put(score.get_item_id(), tmp);
                        }
                        else {
                            tmp = 1;
                            tempList.put(score.get_item_id(), tmp);
                        }
                        boolean isremove = false;
                        if (srcrandom < 5000) {
                            isremove = true;
                        }
                        else if (srcrandom >= 5000 && srcrandom < 10000) {
                            if (tmp > 2) {
                                isremove = true;
                            }
                        }
                        else if (srcrandom >= 10000 && srcrandom < 20000) {
                            if (tmp > 4) {
                                isremove = true;
                            }
                        }
                        else if (srcrandom >= 20000 && srcrandom < 40000) {
                            if (tmp > 8) {
                                isremove = true;
                            }
                        }
                        else if (srcrandom >= 40000 && srcrandom < 80000) {
                            if (tmp > 16) {
                                isremove = true;
                            }
                        }
                        else if (srcrandom >= 80000 && srcrandom < 160000) {
                            if (tmp > 32) {
                                isremove = true;
                            }
                        }
                        else if (srcrandom >= 160000 && srcrandom < 320000 && tmp > 64) {
                            isremove = true;
                        }
                        if (isremove) {
                            this._list_tmp.remove(score);
                        }
                    }
                }
            }
            catch (Exception e) {
                ItemBoxTable._log.error(e.getLocalizedMessage(), e);
                return null;
            }
            return null;
        }
    }
}

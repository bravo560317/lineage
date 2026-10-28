package com.lineage.server.datatables;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.lock.CharItemsTimeReading;
import java.sql.Timestamp;
import com.lineage.server.datatables.lock.CharItemsReading;
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
import com.lineage.server.templates.L1Beginner;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;

public class BeginnerTable {

    public static final Log _log;
    private static final Map<String, ArrayList<L1Beginner>> _beginnerList;
    private static BeginnerTable _instance;

    static {
        _log = LogFactory.getLog(BeginnerTable.class);
        _beginnerList = new HashMap<String, ArrayList<L1Beginner>>();
    }

    public static BeginnerTable get() {
        if (_instance == null) {
            _instance = new BeginnerTable();
        }
        return _instance;
    }

    // ★ 保留原本的空建構子（不能 load()，避免多次載入導致重複給物）
    private BeginnerTable() {
    }

    public static void reload() {
        _instance = new BeginnerTable();
        _beginnerList.clear();
        _instance.load();
    }

    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        int i = 0;

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `beginner`");
            rs = ps.executeQuery();

            while (rs.next()) {
                String activate = rs.getString("activate").toUpperCase();
                int itemid = rs.getInt("item_id");
                int count = rs.getInt("count");
                int enchantlvl = rs.getInt("enchantlvl");
                int charge_count = rs.getInt("charge_count");
                int time = rs.getInt("time");

                // ★ 新增穿戴欄位
                int wearing = 0;
                try {
                    wearing = rs.getInt("wearing");
                } catch (Exception ex) {
                    wearing = 0;
                }

                if (count > 0) {
                    L1Beginner beginner = new L1Beginner();
                    beginner.set_activate(activate);
                    beginner.set_itemid(itemid);
                    beginner.set_count(count);
                    beginner.set_enchantlvl(enchantlvl);
                    beginner.set_charge_count(charge_count);
                    beginner.set_time(time);
                    beginner.set_wearing(wearing); // ★ 新增

                    add(beginner);
                    i++;
                }
            }

        } catch (SQLException e) {
            _log.error(e.getLocalizedMessage(), e);

        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }

        _log.info("載入新手物品資料數量: " + _beginnerList.size() + "/" + i
                + " (" + timer.get() + "ms)");
    }

    private void add(L1Beginner beginner) {
        String key = beginner.get_activate();
        ArrayList<L1Beginner> list = _beginnerList.get(key);

        if (list == null) {
            list = new ArrayList<L1Beginner>();
        }

        list.add(beginner);
        _beginnerList.put(key, list);
    }

    public void giveItem(L1PcInstance pc) {

        String key = "A";
        if (pc.isCrown()) key = "P";
        else if (pc.isKnight()) key = "K";
        else if (pc.isElf()) key = "E";
        else if (pc.isWizard()) key = "W";
        else if (pc.isDarkelf()) key = "D";
        else if (pc.isDragonKnight()) key = "R";
        else if (pc.isIllusionist()) key = "I";

        ArrayList<L1Beginner> list = _beginnerList.get(key);
        if (list != null && !list.isEmpty()) {
            for (L1Beginner beginner : list) {
                get_item(pc, pc.getId(), beginner);
            }
        }

        ArrayList<L1Beginner> listAll = _beginnerList.get("A");
        if (listAll != null && !listAll.isEmpty()) {
            for (L1Beginner beginner : listAll) {
                get_item(pc, pc.getId(), beginner);
            }
        }
    }

    private void get_item(L1PcInstance pc, int objid, L1Beginner beginner) {

        try {
            L1ItemInstance item = ItemTable.get().createItem(beginner.get_itemid());
            if (item != null) {

                // ★ 立即鑑定
                item.setIdentified(true);

                item.setCount(beginner.get_count());
                item.setEnchantLevel(beginner.get_enchantlvl());
                item.setChargeCount(beginner.get_charge_count());
                item.setBless(item.getBless());

                // 存入背包
                CharItemsReading.get().storeItem(objid, item);

                // 時效裝備
                if (beginner.get_time() > 0) {
                    long time = System.currentTimeMillis();
                    long x1 = (long) beginner.get_time() * 60 * 60;
                    long x2 = x1 * 1000L;
                    Timestamp ts = new Timestamp(time + x2);
                    item.set_time(ts);
                    CharItemsTimeReading.get().addTime(item.getId(), ts);
                }

                // ★★★ 自動穿戴（wearing = 1）★★★
                if (beginner.get_wearing() == 1) {
                    pc.getInventory().setEquipped(item, true);
                }
            }

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }
}

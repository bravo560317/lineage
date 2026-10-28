package com.lineage.server.datatables;

import java.lang.reflect.InvocationTargetException;
import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import com.lineage.server.model.doll.L1DollExecutor;
import com.lineage.server.templates.L1Doll;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class DollPowerTable
{
    private static final Log _log;
    private static DollPowerTable _instance;
    private static final HashMap<Integer, L1Doll> _powerMap;
    private static final HashMap<Integer, L1DollExecutor> _classList;
    private static final ArrayList<String> _checkList;
    
    static {
        _log = LogFactory.getLog(DollPowerTable.class);
        _powerMap = new HashMap();
        _classList = new HashMap();
        _checkList = new ArrayList();
    }
    
    public static DollPowerTable get() {
        if (DollPowerTable._instance == null) {
            DollPowerTable._instance = new DollPowerTable();
        }
        return DollPowerTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `etcitem_doll_power`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String classname = rs.getString("classname");
                int type1 = rs.getInt("type1");
                int type2 = rs.getInt("type2");
                int type3 = rs.getInt("type3");
                double type4 = rs.getDouble("type4");
                String note = rs.getString("note");
                String ch = classname + "=" + type1 + "=" + type2 + "=" + type3 + "=" + note;
                if (DollPowerTable._checkList.lastIndexOf(ch) == -1) {
                    DollPowerTable._checkList.add(ch);
                    this.addList(id, classname, type1, type2, type3, type4, note);
                }
                else {
                    DollPowerTable._log.error("娃娃能力設置重複:id=" + id + " type1=" + type1 + " type2=" + type2 + " type3=" + type3);
                }
            }
        }
        catch (SQLException e) {
            DollPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        catch (Exception e2) {
            DollPowerTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            DollPowerTable._checkList.clear();
        }
        DollPowerTable._log.info("載入娃娃能力資料數量: " + DollPowerTable._classList.size() + "(" + timer.get() + "ms)");
        this.setDollType();
    }
    
    private void setDollType() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `etcitem_doll_type`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                String powers = rs.getString("powers").replaceAll(" ", "");
                String need = rs.getString("need").replaceAll(" ", "");
                String count = rs.getString("count").replaceAll(" ", "");
                int time = rs.getInt("time");
                int gfxid = rs.getInt("gfxid");
                String nameid = rs.getString("nameid");
                int mode = rs.getInt("mode");
                int ArrowGifx = rs.getInt("卡冊編號");
                boolean iserr = false;
                ArrayList<L1DollExecutor> powerList = new ArrayList();
                if (powers != null && !powers.equals("")) {
                    String[] set1 = powers.split(",");
                    String[] array;
                    for (int length = (array = set1).length, k = 0; k < length; ++k) {
                        String string = array[k];
                        L1DollExecutor e = DollPowerTable._classList.get((Integer)Integer.parseInt(string));
                        if (e != null) {
                            powerList.add(e);
                        }
                        else {
                            DollPowerTable._log.error("娃娃能力取回錯誤-沒有這個編號:" + string);
                            iserr = true;
                        }
                    }
                }
                int[] needs = (int[])null;
                if (need != null && !need.equals("")) {
                    String[] set2 = need.split(",");
                    needs = new int[set2.length];
                    for (int i = 0; i < set2.length; ++i) {
                        int itemid_n = Integer.parseInt(set2[i]);
                        L1Item temp = ItemTable.get().getTemplate(itemid_n);
                        if (temp == null) {
                            DollPowerTable._log.error("物品資訊取回錯誤-沒有這個編號:" + itemid_n);
                            iserr = true;
                        }
                        needs[i] = itemid_n;
                    }
                }
                int[] counts = (int[])null;
                if (count != null && !count.equals("")) {
                    String[] set3 = count.split(",");
                    counts = new int[set3.length];
                    if (set3.length != needs.length) {
                        DollPowerTable._log.error("物品資訊對應錯誤-長度不吻合: itemid:" + itemid);
                        iserr = true;
                    }
                    for (int j = 0; j < set3.length; ++j) {
                        int count_n = Integer.parseInt(set3[j]);
                        counts[j] = count_n;
                    }
                }
                if (!iserr) {
                    L1Doll doll_power = new L1Doll();
                    doll_power.set_itemid(itemid);
                    doll_power.setPowerList(powerList);
                    doll_power.set_need(needs);
                    doll_power.set_counts(counts);
                    doll_power.set_time(time);
                    doll_power.set_gfxid(gfxid);
                    doll_power.set_nameid(nameid);
                    doll_power.set_mode(mode);
                    doll_power.set_ArrowGifx(ArrowGifx);
                    DollPowerTable._powerMap.put(itemid, doll_power);
                }
            }
        }
        catch (SQLException e2) {
            DollPowerTable._log.error(e2.getLocalizedMessage(), e2);
        }
        catch (Exception e3) {
            DollPowerTable._log.error(e3.getLocalizedMessage(), e3);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            DollPowerTable._classList.clear();
        }
        DollPowerTable._log.info("載入娃娃能力資料數量: " + DollPowerTable._classList.size() + "(" + timer.get() + "ms)");
    }
    
    private void addList(int powerid, String className, int int1, int int2, int int3, double int4, String note) {
        if (className.equals("0")) {
            return;
        }
        else {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("com.lineage.server.model.doll.");
                stringBuilder.append(className);
                Class<?> cls = Class.forName(stringBuilder.toString());
                L1DollExecutor exe = (L1DollExecutor)cls.getMethod("get", new Class[0]).invoke(null, new Object[0]);
                exe.set_power(int1, int2, int3, int4);
                exe.set_note(note);
                DollPowerTable._classList.put(new Integer(powerid), exe);
            }
            catch (ClassNotFoundException e) {
                String error = "發生[娃娃能力檔案]錯誤, 檢查檔案是否存在:" + className + " 娃娃能力編號:" + powerid;
                DollPowerTable._log.error(error);
            }
            catch (IllegalArgumentException e2) {
                DollPowerTable._log.error(e2.getLocalizedMessage(), e2);
            }
            catch (IllegalAccessException e3) {
                DollPowerTable._log.error(e3.getLocalizedMessage(), e3);
            }
            catch (InvocationTargetException e4) {
                DollPowerTable._log.error(e4.getLocalizedMessage(), e4);
            }
            catch (SecurityException e5) {
                DollPowerTable._log.error(e5.getLocalizedMessage(), e5);
            }
            catch (NoSuchMethodException e6) {
                DollPowerTable._log.error(e6.getLocalizedMessage(), e6);
            }
            return;
        }
    }
    
    public L1Doll get_type(int key) {
        return DollPowerTable._powerMap.get((Integer)key);
    }
    
    public HashMap<Integer, L1Doll> map() {
        return DollPowerTable._powerMap;
    }
}

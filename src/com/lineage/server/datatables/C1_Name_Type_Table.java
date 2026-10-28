package com.lineage.server.datatables;

import java.lang.reflect.InvocationTargetException;
import com.lineage.server.model.c1.C1Executor;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Name_Power;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;

public class C1_Name_Type_Table {

    private static final Log _log;
    private static final Map<Integer, HashMap<Integer, L1Name_Power>> _types;
    private static final Map<Integer, HashMap<Integer, Integer>> _typesLv;
    private static final Map<Integer, HashMap<Integer, Integer>> _typesLv_down;
    private static C1_Name_Type_Table _instance;

    static {
        _log = LogFactory.getLog(C1_Name_Type_Table.class);
        _types = new HashMap<>();
        _typesLv = new HashMap<>();
        _typesLv_down = new HashMap<>();
    }

    public static C1_Name_Type_Table get() {
        if (_instance == null) {
            _instance = new C1_Name_Type_Table();
        }
        return _instance;
    }

    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int i = 0;

        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_血盟_陣營能力`");
            rs = pstm.executeQuery();

            while (rs.next()) {
                int c1_id = rs.getInt("c1_id");
                int c1_type = rs.getInt("c1_type");
                String c1_name_type = rs.getString("c1_name_type");
                String c1_classname = rs.getString("c1_classname");
                int set = rs.getInt("set");
                int down = rs.getInt("down");

                // 依資料表欄位讀取
                int int1 = rs.getInt("近距離攻擊");
                int int2 = rs.getInt("近距離命中");
                int int3 = rs.getInt("遠距離攻擊");
                int int4 = rs.getInt("遠距離命中");
                int int5 = rs.getInt("傷害減免");
                int int6 = rs.getInt("防禦");
                int int7 = rs.getInt("魔法攻擊");
                int int8 = rs.getInt("回魔量");
                int int9 = rs.getInt("回血量");
                int int10 = rs.getInt("經驗值加成");

                String c1_name_title = rs.getString("c1_name_title");
                int getscore = rs.getInt("getscore");

                L1Name_Power power = new L1Name_Power();
                power.set_c1_id(c1_id);
                power.set_c1_name_type(c1_name_type);
                power.set_c1_name_title(c1_name_title);

                // ★★★ 修正 mapping：對應 C1_Camp 的順序 ★★★
                //
                // C1_Camp expects:
                //   int1 = 近攻
                //   int2 = 遠攻
                //   int3 = 近命中
                //   int4 = 遠命中
                //
                // →正確 mapping：
                C1Executor classname = this.power(
                    c1_classname,
                    int1,   // 近攻
                    int3,   // 遠攻 (原本傳錯 int2)
                    int2,   // 近命中
                    int4,   // 遠命中
                    int5, int6, int7, int8, int9, int10
                );

                if (classname != null) {
                    power.set_c1_classname(classname);
                    power.set_set(set);
                    power.set_down(down);
                    power.set_getscore(getscore);

                    HashMap<Integer, L1Name_Power> types = _types.get(c1_type);
                    if (types == null) {
                        types = new HashMap<>();
                    }
                    types.put(c1_id, power);

                    HashMap<Integer, Integer> typesLv = _typesLv.get(c1_type);
                    if (typesLv == null) {
                        typesLv = new HashMap<>();
                    }
                    typesLv.put(c1_id, set);

                    HashMap<Integer, Integer> typesLv_down = _typesLv_down.get(c1_type);
                    if (typesLv_down == null) {
                        typesLv_down = new HashMap<>();
                    }
                    typesLv_down.put(c1_id, down);

                    _types.put(c1_type, types);
                    _typesLv.put(c1_type, typesLv);

                    i++;
                }
            }

        } catch (SQLException e) {
            _log.error(e.getLocalizedMessage(), e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }

        _log.info("載入陣營階級能力記錄數量: " + i + " (" + timer.get() + "ms)");
    }

    public L1Name_Power get(int key1, int key2) {
        HashMap<Integer, L1Name_Power> powers = _types.get(key1);
        if (powers != null) {
            return powers.get(key2);
        }
        return null;
    }

    public HashMap<Integer, L1Name_Power> get(int key1) {
        return _types.get(key1);
    }

    public int getLv(int key1, int score) {
        HashMap<Integer, Integer> powers = _typesLv.get(key1);
        if (powers == null) return 0;

        int i = powers.size();
        while (i > 0) {
            Integer ps = powers.get(i);
            if (score >= ps) {
                return i;
            }
            i--;
        }
        return 0;
    }

    private C1Executor power(String className, int int1, int int2, int int3, int int4,
                             int int5, int int6, int int7, int int8, int int9, int int10) {

        if (className.equals("0")) {
            return null;
        }

        try {
            String classPath = "com.lineage.server.model.c1." + className;
            Class<?> cls = Class.forName(classPath);
            C1Executor exe = (C1Executor) cls.getMethod("get").invoke(null);
            exe.set_power(int1, int2, int3, int4, int5, int6, int7, int8, int9, int10);
            return exe;

        } catch (Exception e) {
            _log.error("陣營階級能力錯誤：class " + className, e);
        }

        return null;
    }
}

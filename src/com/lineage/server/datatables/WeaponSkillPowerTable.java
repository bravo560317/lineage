package com.lineage.server.datatables;

import java.lang.reflect.InvocationTargetException;
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
import com.lineage.server.model.weaponskill.L1WeaponSkillType;
import java.util.Map;
import org.apache.commons.logging.Log;

public class WeaponSkillPowerTable
{
    private static final Log _log;
    private static WeaponSkillPowerTable _instance;
    private static final Map<Integer, L1WeaponSkillType> _weaponIdIndex;
    private static final Map<Integer, ArrayList<L1WeaponSkillType>> _weaponSkill;
    
    static {
        _log = LogFactory.getLog(WeaponSkillPowerTable.class);
        _weaponIdIndex = new HashMap();
        _weaponSkill = new HashMap();
    }
    
    public static WeaponSkillPowerTable get() {
        if (WeaponSkillPowerTable._instance == null) {
            WeaponSkillPowerTable._instance = new WeaponSkillPowerTable();
        }
        return WeaponSkillPowerTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        this.load1();
        this.load2();
        WeaponSkillPowerTable._log.info("載入技能武器能力資料數量: " + WeaponSkillPowerTable._weaponIdIndex.size() + "(" + timer.get() + "ms)");
        WeaponSkillPowerTable._log.info("載入技能武器設置資料數量: " + WeaponSkillPowerTable._weaponSkill.size() + "(" + timer.get() + "ms)");
    }
    
    private void load1() {
        Connection cn = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            pm = cn.prepareStatement("SELECT * FROM `weapon_skill_power`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String classname = rs.getString("classname");
                int level = rs.getInt("level");
                int type1 = rs.getInt("type1");
                int type2 = rs.getInt("type2");
                int type3 = rs.getInt("type3");
                int attr = rs.getInt("attr");
                int ac_mr = rs.getInt("ac_mr");
                int gfxid1 = rs.getInt("gfxid1");
                int gfxid2 = rs.getInt("gfxid2");
                int gfxid3 = rs.getInt("gfxid3");
                int gfxid4 = rs.getInt("gfxid4");
                int gfxid4_count = rs.getInt("gfxid4_count");
                int power = rs.getInt("power");
                int srcdmg = rs.getInt("srcdmg");
                int addsrcdmg = rs.getInt("addsrcdmg");
                int random1 = rs.getInt("random1");
                int random2 = rs.getInt("random2");
                boolean boss_holdout = rs.getBoolean("boss_holdout");
                L1WeaponSkillType class_name = this.get_class(id, classname);
                if (class_name != null) {
                    class_name.set_level(level);
                    class_name.set_type1(type1);
                    class_name.set_type2(type2);
                    class_name.set_type3(type3);
                    class_name.set_attr(attr);
                    class_name.set_ac_mr(ac_mr);
                    class_name.set_gfxid1(gfxid1);
                    class_name.set_gfxid2(gfxid2);
                    class_name.set_gfxid3(gfxid3);
                    class_name.set_gfxid4(gfxid4);
                    class_name.set_gfxid4_count(gfxid4_count);
                    class_name.set_power(power);
                    class_name.set_srcdmg(srcdmg);
                    class_name.set_addsrcdmg(addsrcdmg);
                    class_name.set_random1(random1);
                    class_name.set_random2(random2);
                    class_name.set_boss_holdout(boss_holdout);
                }
                WeaponSkillPowerTable._weaponIdIndex.put(id, class_name);
            }
        }
        catch (SQLException e) {
            WeaponSkillPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(cn);
        }
    }
    
    private void load2() {
        Connection cn = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            pm = cn.prepareStatement("SELECT * FROM `weapon_skill_in`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                if (ItemTable.get().getTemplate(item_id) == null) {
                    WeaponSkillPowerTable._log.error("技能武器設置資料錯誤: 沒有這個編號的武器:" + item_id);
                }
                else {
                    String powers = rs.getString("powers").replaceAll(" ", "");
                    ArrayList<L1WeaponSkillType> list = WeaponSkillPowerTable._weaponSkill.get((Integer)item_id);
                    if (list == null) {
                        list = new ArrayList();
                    }
                    if (!powers.equals("")) {
                        String[] set = powers.split(",");
                        for (int i = 0; i < set.length; ++i) {
                            int itemid = Integer.parseInt(set[i]);
                            L1WeaponSkillType class_name = WeaponSkillPowerTable._weaponIdIndex.get((Integer)itemid);
                            list.add(class_name);
                        }
                    }
                    WeaponSkillPowerTable._weaponSkill.put(item_id, list);
                }
            }
        }
        catch (SQLException e) {
            WeaponSkillPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(cn);
        }
    }
    
    private L1WeaponSkillType get_class(int id, String className) {
        if (className.equals("0")) {
            return null;
        }
        else {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("com.lineage.server.model.weaponskill.");
                stringBuilder.append(className);
                Class<?> cls = Class.forName(stringBuilder.toString());
                return (L1WeaponSkillType)cls.getMethod("get", new Class[0]).invoke(null, new Object[0]);
            }
            catch (ClassNotFoundException e) {
                String error = "發生[技能武器檔案]錯誤, 檢查檔案是否存在:" + className + " 技能武器編號:" + id;
                WeaponSkillPowerTable._log.error(error);
            }
            catch (IllegalArgumentException e2) {
                WeaponSkillPowerTable._log.error(e2.getLocalizedMessage(), e2);
            }
            catch (IllegalAccessException e3) {
                WeaponSkillPowerTable._log.error(e3.getLocalizedMessage(), e3);
            }
            catch (InvocationTargetException e4) {
                WeaponSkillPowerTable._log.error(e4.getLocalizedMessage(), e4);
            }
            catch (SecurityException e5) {
                WeaponSkillPowerTable._log.error(e5.getLocalizedMessage(), e5);
            }
            catch (NoSuchMethodException e6) {
                WeaponSkillPowerTable._log.error(e6.getLocalizedMessage(), e6);
            }
            return null;
        }
    }
    
    public ArrayList<L1WeaponSkillType> getTemplate(int itemid) {
        return WeaponSkillPowerTable._weaponSkill.get((Integer)itemid);
    }
}

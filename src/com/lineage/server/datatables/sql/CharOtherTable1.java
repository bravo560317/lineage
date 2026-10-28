package com.lineage.server.datatables.sql;

import java.util.StringTokenizer;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1PcOther1;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharOtherStorage1;

public class CharOtherTable1 implements CharOtherStorage1
{
    private static final Log _log;
    private static final Map<Integer, L1PcOther1> _otherMap;
    
    static {
        _log = LogFactory.getLog(CharOtherTable1.class);
        _otherMap = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_內掛`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_obj_id = rs.getInt("char_obj_id");
                if (CharObjidTable.get().isChar(char_obj_id) != null) {
                    int type1 = rs.getInt("type1");
                    int type2 = rs.getInt("type2");
                    int type3 = rs.getInt("type3");
                    int type4 = rs.getInt("type4");
                    int type5 = rs.getInt("type5");
                    int type6 = rs.getInt("type6");
                    int type7 = rs.getInt("type7");
                    int type8 = rs.getInt("type8");
                    int type9 = rs.getInt("type9");
                    int type10 = rs.getInt("type10");
                    int type11 = rs.getInt("type11");
                    int type12 = rs.getInt("type12");
                    int type13 = rs.getInt("type13");
                    int type14 = rs.getInt("type14");
                    int type15 = rs.getInt("type15");
                    int type16 = rs.getInt("type16");
                    int type17 = rs.getInt("type17");
                    int type18 = rs.getInt("type18");
                    int type19 = rs.getInt("type19");
                    int type20 = rs.getInt("type20");
                    int type21 = rs.getInt("type21");
                    int type22 = rs.getInt("type22");
                    int type23 = rs.getInt("type23");
                    int type24 = rs.getInt("type24");
                    int type25 = rs.getInt("type25");
                    int type26 = rs.getInt("type26");
                    int type27 = rs.getInt("type27");
                    int type28 = rs.getInt("type28");
                    int type29 = rs.getInt("type29");
                    String type30 = rs.getString("type30");
                    String type31 = rs.getString("type31");
                    String type32 = rs.getString("type32");
                    String type33 = rs.getString("type33");
                    String type34 = rs.getString("type34");
                    String type35 = rs.getString("type35");
                    String type36 = rs.getString("type36");
                    String type37 = rs.getString("type37");
                    String type38 = rs.getString("type38");
                    String type39 = rs.getString("type39");
                    String type40 = rs.getString("type40");
                    int type41 = rs.getInt("type41");
                    int type42 = rs.getInt("type42");
                    int type43 = rs.getInt("type43");
                    int type44 = rs.getInt("type44");
                    int type45 = rs.getInt("type45");
                    int type46 = rs.getInt("type46");
                    int type47 = rs.getInt("type47");
                    int type48 = rs.getInt("type48");
                    int type49 = rs.getInt("type49");
                    int type50 = rs.getInt("type50");
                    L1PcOther1 other = new L1PcOther1();
                    other.set_objid(char_obj_id);
                    other.set_type1(type1);
                    other.set_type2(type2);
                    other.set_type3(type3);
                    other.set_type4(type4);
                    other.set_type5(type5);
                    other.set_type6(type6);
                    other.set_type7(type7);
                    other.set_type8(type8);
                    other.set_type9(type9);
                    other.set_type10(type10);
                    other.set_type11(type11);
                    other.set_type12(type12);
                    other.set_type13(type13);
                    other.set_type14(type14);
                    other.set_type15(type15);
                    other.set_type16(type16);
                    other.set_type17(type17);
                    other.set_type18(type18);
                    other.set_type19(type19);
                    other.set_type20(type20);
                    other.set_type21(type21);
                    other.set_type22(type22);
                    other.set_type23(type23);
                    other.set_type24(type24);
                    other.set_type25(type25);
                    other.set_type26(type26);
                    other.set_type27(type27);
                    other.set_type28(type28);
                    other.set_type29(type29);
                    other.set_type30(type30);
                    other.set_type31(type31);
                    other.set_type32(type32);
                    other.set_type33(type33);
                    other.set_type34(type34);
                    other.set_type35(type35);
                    other.set_type36(type36);
                    other.set_type37(type37);
                    other.set_type38(type38);
                    other.set_type39(type39);
                    other.set_type40(type40);
                    other.set_type41(type41);
                    other.set_type42(type42);
                    other.set_type43(type43);
                    other.set_type44(type44);
                    other.set_type45(type45);
                    other.set_type46(type46);
                    other.set_type47(type47);
                    other.set_type48(type48);
                    other.set_type49(type49);
                    other.set_type50(type50);
                    addMap(char_obj_id, other);
                }
                else {
                    delete(char_obj_id);
                }
            }
        }
        catch (SQLException e) {
            CharOtherTable1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharOtherTable1._log.info("載入額外紀錄資料數量: " + CharOtherTable1._otherMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_內掛` WHERE `char_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
            CharOtherTable1._otherMap.remove((Integer)objid);
        }
        catch (SQLException e) {
            CharOtherTable1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addMap(int objId, L1PcOther1 other) {
        L1PcOther1 otherTmp = CharOtherTable1._otherMap.get((Integer)objId);
        if (otherTmp == null) {
            CharOtherTable1._otherMap.put(objId, other);
        }
    }
    
    @Override
    public L1PcOther1 getOther(L1PcInstance pc) {
        L1PcOther1 otherTmp = CharOtherTable1._otherMap.get((Integer)pc.getId());
        return otherTmp;
    }
    
    @Override
    public void storeOther(int objId, L1PcOther1 other) {
        L1PcOther1 otherTmp = CharOtherTable1._otherMap.get((Integer)objId);
        if (otherTmp == null) {
            addMap(objId, other);
            this.addNewOther(other);
        }
        else {
            this.updateOther(other);
        }
    }
    
    private void updateOther(L1PcOther1 other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            int type1 = other.get_type1();
            int type2 = other.get_type2();
            int type3 = other.get_type3();
            int type4 = other.get_type4();
            int type5 = other.get_type5();
            int type6 = other.get_type6();
            int type7 = other.get_type7();
            int type8 = other.get_type8();
            int type9 = other.get_type9();
            int type10 = other.get_type10();
            int type11 = other.get_type11();
            int type12 = other.get_type12();
            int type13 = other.get_type13();
            int type14 = other.get_type14();
            int type15 = other.get_type15();
            int type16 = other.get_type16();
            int type17 = other.get_type17();
            int type18 = other.get_type18();
            int type19 = other.get_type19();
            int type20 = other.get_type20();
            int type21 = other.get_type21();
            int type22 = other.get_type22();
            int type23 = other.get_type23();
            int type24 = other.get_type24();
            int type25 = other.get_type25();
            int type26 = other.get_type26();
            int type27 = other.get_type27();
            int type28 = other.get_type28();
            int type29 = other.get_type29();
            String type30 = other.get_type30();
            String type31 = other.get_type31();
            String type32 = other.get_type32();
            String type33 = other.get_type33();
            String type34 = other.get_type34();
            String type35 = other.get_type35();
            String type36 = other.get_type36();
            String type37 = other.get_type37();
            String type38 = other.get_type38();
            String type39 = other.get_type39();
            String type40 = other.get_type40();
            int type41 = other.get_type41();
            int type42 = other.get_type42();
            int type43 = other.get_type43();
            int type44 = other.get_type44();
            int type45 = other.get_type45();
            int type46 = other.get_type46();
            int type47 = other.get_type47();
            int type48 = other.get_type48();
            int type49 = other.get_type49();
            int type50 = other.get_type50();
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_內掛` SET `type1`=?,`type2`=?,`type3`=?,`type4`=?,`type5`=?,`type6`=?,`type7`=?,`type8`=?,`type9`=?,`type10`=?,`type11`=?,`type12`=?,`type13`=?,`type14`=?,`type15`=?,`type16`=?,`type17`=?,`type18`=?,`type19`=?,`type20`=?,`type21`=?,`type22`=?,`type23`=?,`type24`=?,`type25`=?,`type26`=?,`type27`=?,`type28`=?,`type29`=?,`type30`=?,`type31`=?,`type32`=?,`type33`=?,`type34`=?,`type35`=?,`type36`=?,`type37`=?,`type38`=?,`type39`=?,`type40`=?,`type41`=?,`type42`=?,`type43`=?,`type44`=?,`type45`=?,`type46`=?,`type47`=?,`type48`=?,`type49`=?,`type50`=? WHERE `char_obj_id`=?");
            int i = 0;
            ps.setInt(++i, type1);
            ps.setInt(++i, type2);
            ps.setInt(++i, type3);
            ps.setInt(++i, type4);
            ps.setInt(++i, type5);
            ps.setInt(++i, type6);
            ps.setInt(++i, type7);
            ps.setInt(++i, type8);
            ps.setInt(++i, type9);
            ps.setInt(++i, type10);
            ps.setInt(++i, type11);
            ps.setInt(++i, type12);
            ps.setInt(++i, type13);
            ps.setInt(++i, type14);
            ps.setInt(++i, type15);
            ps.setInt(++i, type16);
            ps.setInt(++i, type17);
            ps.setInt(++i, type18);
            ps.setInt(++i, type19);
            ps.setInt(++i, type20);
            ps.setInt(++i, type21);
            ps.setInt(++i, type22);
            ps.setInt(++i, type23);
            ps.setInt(++i, type24);
            ps.setInt(++i, type25);
            ps.setInt(++i, type26);
            ps.setInt(++i, type27);
            ps.setInt(++i, type28);
            ps.setInt(++i, type29);
            ps.setString(++i, type30);
            ps.setString(++i, type31);
            ps.setString(++i, type32);
            ps.setString(++i, type33);
            ps.setString(++i, type34);
            ps.setString(++i, type35);
            ps.setString(++i, type36);
            ps.setString(++i, type37);
            ps.setString(++i, type38);
            ps.setString(++i, type39);
            ps.setString(++i, type40);
            ps.setInt(++i, type41);
            ps.setInt(++i, type42);
            ps.setInt(++i, type43);
            ps.setInt(++i, type44);
            ps.setInt(++i, type45);
            ps.setInt(++i, type46);
            ps.setInt(++i, type47);
            ps.setInt(++i, type48);
            ps.setInt(++i, type49);
            ps.setInt(++i, type50);
            ps.setInt(++i, other.get_objid());
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void addNewOther(L1PcOther1 other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            int oid = other.get_objid();
            int type1 = other.get_type1();
            int type2 = other.get_type2();
            int type3 = other.get_type3();
            int type4 = other.get_type4();
            int type5 = other.get_type5();
            int type6 = other.get_type6();
            int type7 = other.get_type7();
            int type8 = other.get_type8();
            int type9 = other.get_type9();
            int type10 = other.get_type10();
            int type11 = other.get_type11();
            int type12 = other.get_type12();
            int type13 = other.get_type13();
            int type14 = other.get_type14();
            int type15 = other.get_type15();
            int type16 = other.get_type16();
            int type17 = other.get_type17();
            int type18 = other.get_type18();
            int type19 = other.get_type19();
            int type20 = other.get_type20();
            int type21 = other.get_type21();
            int type22 = other.get_type22();
            int type23 = other.get_type23();
            int type24 = other.get_type24();
            int type25 = other.get_type25();
            int type26 = other.get_type26();
            int type27 = other.get_type27();
            int type28 = other.get_type28();
            int type29 = other.get_type29();
            String type30 = other.get_type30();
            String type31 = other.get_type31();
            String type32 = other.get_type32();
            String type33 = other.get_type33();
            String type34 = other.get_type34();
            String type35 = other.get_type35();
            String type36 = other.get_type36();
            String type37 = other.get_type37();
            String type38 = other.get_type38();
            String type39 = other.get_type39();
            String type40 = other.get_type40();
            int type41 = other.get_type41();
            int type42 = other.get_type42();
            int type43 = other.get_type43();
            int type44 = other.get_type44();
            int type45 = other.get_type45();
            int type46 = other.get_type46();
            int type47 = other.get_type47();
            int type48 = other.get_type48();
            int type49 = other.get_type49();
            int type50 = other.get_type50();
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `character_內掛` SET `char_obj_id`=?,`type1`=?,`type2`=?,`type3`=?,`type4`=?,`type5`=?,`type6`=?,`type7`=?,`type8`=?,`type9`=?,`type10`=?,`type11`=?,`type12`=?,`type13`=?,`type14`=?,`type15`=?,`type16`=?,`type17`=?,`type18`=?,`type19`=?,`type20`=?,`type21`=?,`type22`=?,`type23`=?,`type24`=?,`type25`=?,`type26`=?,`type27`=?,`type28`=?,`type29`=?,`type30`=?,`type31`=?,`type32`=?,`type33`=?,`type34`=?,`type35`=?,`type36`=?,`type37`=?,`type38`=?,`type39`=?,`type40`=?,`type41`=?,`type42`=?,`type43`=?,`type44`=?,`type45`=?,`type46`=?,`type47`=?,`type48`=?,`type49`=?,`type50`=?");
            int i = 0;
            ps.setInt(++i, oid);
            ps.setInt(++i, type1);
            ps.setInt(++i, type2);
            ps.setInt(++i, type3);
            ps.setInt(++i, type4);
            ps.setInt(++i, type5);
            ps.setInt(++i, type6);
            ps.setInt(++i, type7);
            ps.setInt(++i, type8);
            ps.setInt(++i, type9);
            ps.setInt(++i, type10);
            ps.setInt(++i, type11);
            ps.setInt(++i, type12);
            ps.setInt(++i, type13);
            ps.setInt(++i, type14);
            ps.setInt(++i, type15);
            ps.setInt(++i, type16);
            ps.setInt(++i, type17);
            ps.setInt(++i, type18);
            ps.setInt(++i, type19);
            ps.setInt(++i, type20);
            ps.setInt(++i, type21);
            ps.setInt(++i, type22);
            ps.setInt(++i, type23);
            ps.setInt(++i, type24);
            ps.setInt(++i, type25);
            ps.setInt(++i, type26);
            ps.setInt(++i, type27);
            ps.setInt(++i, type28);
            ps.setInt(++i, type29);
            ps.setString(++i, type30);
            ps.setString(++i, type31);
            ps.setString(++i, type32);
            ps.setString(++i, type33);
            ps.setString(++i, type34);
            ps.setString(++i, type35);
            ps.setString(++i, type36);
            ps.setString(++i, type37);
            ps.setString(++i, type38);
            ps.setString(++i, type39);
            ps.setString(++i, type40);
            ps.setInt(++i, type41);
            ps.setInt(++i, type42);
            ps.setInt(++i, type43);
            ps.setInt(++i, type44);
            ps.setInt(++i, type45);
            ps.setInt(++i, type46);
            ps.setInt(++i, type47);
            ps.setInt(++i, type48);
            ps.setInt(++i, type49);
            ps.setInt(++i, type50);
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public void tam() {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            for (L1PcOther1 l1PcOther1 : CharOtherTable1._otherMap.values()) {}
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_內掛` SET `killCount`='0' AND `deathCount`='0'");
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static int[] getArray(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        else {
            StringTokenizer st = new StringTokenizer(s, ",");
            int iSize = st.countTokens();
            String sTemp = null;
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                iReturn[i] = Integer.parseInt(sTemp);
            }
            return iReturn;
        }
    }
}

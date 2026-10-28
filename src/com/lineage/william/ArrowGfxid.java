package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class ArrowGfxid
{
    private static ArrayList aData17;
    private static boolean NO_MORE_GET_DATA17;
    public static final String TOKEN = ",";
    
    static {
        ArrowGfxid.aData17 = new ArrayList();
        ArrowGfxid.NO_MORE_GET_DATA17 = false;
    }
    
    public static void main(String[] a) {
        try {
            Server.main((String[])null);
        }
        catch (Exception ex) {}
    }
    
    public static void forItemUSe(L1PcInstance user, int poly) {
        ArrayList aTempData = null;
        if (!ArrowGfxid.NO_MORE_GET_DATA17) {
            ArrowGfxid.NO_MORE_GET_DATA17 = true;
            getData15b();
        }
        for (int i = 0; i < ArrowGfxid.aData17.size(); ++i) {
            aTempData = (ArrayList)ArrowGfxid.aData17.get(i);
            if (!user.hasSkillEffect(67)) {
                user.setpolyarrow(0);
            }
            else if ((Integer)aTempData.get(0) == poly && user.getpolyarrow() != (Integer)aTempData.get(1)) {
                user.setpolyarrow((Integer)aTempData.get(1));
                break;
            }
            else {
                user.setpolyarrow(66);
            }
        }
    }
    
    private static void getData15b() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM 系統_變身箭矢特效");
            ArrayList aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("polyid")));
                    aReturn.add(1, new Integer(rset.getInt("arrowgfxid")));
                    ArrowGfxid.aData17.add(aReturn);
                }
            }
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        }
        catch (Exception ex) {}
    }
}

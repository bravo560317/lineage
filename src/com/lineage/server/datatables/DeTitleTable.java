package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.config.Config;
import com.lineage.DatabaseFactoryLogin;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class DeTitleTable
{
    private static final Log _log;
    private static final ArrayList<String> _detitleList;
    private static DeTitleTable _instance;
    private static final Random _random;
    private static final String[] _titleList;
    
    static {
        _log = LogFactory.getLog(DeTitleTable.class);
        _detitleList = new ArrayList();
        _random = new Random();
        _titleList = new String[] { "\\f2", "\\f3", "\\f4", "\\f5", "\\f6", "\\f7", "\\f8", "\\f9", "\\f0", "\\f:", "\\f<", "\\f>", "\\f?", "\\fA", "\\fB", "\\fC", "\\fD", "\\fE", "\\fF", "\\fG", "\\fH", "\\fI", "\\fJ", "\\fK", "\\fL", "\\fM", "\\fN", "\\fO", "\\fP", "\\fQ", "\\fR", "\\fS", "\\fT", "\\fU", "\\fV", "\\fW", "\\fX", "\\fY", "\\fZ", "\\f@" };
    }
    
    public static DeTitleTable get() {
        if (DeTitleTable._instance == null) {
            DeTitleTable._instance = new DeTitleTable();
        }
        return DeTitleTable._instance;
    }
    
    public void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `de_title`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                String detitle = "";
                if (Config.CLIENT_LANGUAGE == 3) {
                    detitle = rs.getString("detitlebig5");
                }
                else {
                    detitle = rs.getString("detitle");
                }
                DeTitleTable._detitleList.add(detitle);
            }
        }
        catch (SQLException e) {
            DeTitleTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public String getTitle() {
        int index = DeTitleTable._random.nextInt(DeTitleTable._detitleList.size());
        String c = DeTitleTable._titleList[DeTitleTable._random.nextInt(DeTitleTable._titleList.length)];
        return c + DeTitleTable._detitleList.get(index);
    }
}

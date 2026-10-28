package com.add;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.text.SimpleDateFormat;
import com.lineage.DatabaseFactory;
import java.util.Calendar;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ResetMapTime
{
    private static final Log _log;
    private static L1ResetMapTime _instance;
    
    static {
        _log = LogFactory.getLog(L1ResetMapTime.class);
    }
    
    public static L1ResetMapTime get() {
        if (L1ResetMapTime._instance == null) {
            L1ResetMapTime._instance = new L1ResetMapTime();
        }
        return L1ResetMapTime._instance;
    }
    
    private void updateResetMapTime(int id, Calendar reset_cal) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `config_other` SET `resetMaptime`=? WHERE `id`=?");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fm = sdf.format(reset_cal.getTime());
            int i = 0;
            pstm.setString(++i, fm);
            pstm.setInt(++i, id);
            pstm.execute();
        }
        catch (Exception e) {
            L1ResetMapTime._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

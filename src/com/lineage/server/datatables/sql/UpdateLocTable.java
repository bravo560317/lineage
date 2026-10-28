package com.lineage.server.datatables.sql;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.UpdateLocStorage;

public class UpdateLocTable implements UpdateLocStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(UpdateLocTable.class);
    }
    
    @Override
    public void setPcLoc(String accName) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `characters` SET `LocX`=33443,`LocY`=32797,`MapID`=4 WHERE `account_name`=?");
            pstm.setString(1, accName);
            pstm.execute();
            pstm.close();
            con.close();
        }
        catch (SQLException e) {
            UpdateLocTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

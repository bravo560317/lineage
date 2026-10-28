package com.lineage.server.datatables.sql;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.sql.Timestamp;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.ServerGmCommandStorage;

public class ServerGmCommandTable implements ServerGmCommandStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(ServerGmCommandTable.class);
    }
    
    @Override
    public void create(L1PcInstance pc, String cmd) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            Timestamp lastactive = new Timestamp(System.currentTimeMillis());
            cn = DatabaseFactory.get().getConnection();
            String sqlstr = "INSERT INTO `other_gmcommand` SET `gmobjid`=?,`gmname`=?,`cmd`=?,`time`=?";
            ps = cn.prepareStatement("INSERT INTO `other_gmcommand` SET `gmobjid`=?,`gmname`=?,`cmd`=?,`time`=?");
            int i = 0;
            if (pc == null) {
                ps.setInt(++i, 0);
                ps.setString(++i, "--視窗命令--");
                ps.setString(++i, cmd);
                ps.setTimestamp(++i, lastactive);
            }
            else {
                ps.setInt(++i, pc.getId());
                ps.setString(++i, pc.getName());
                ps.setString(++i, cmd);
                ps.setTimestamp(++i, lastactive);
                ServerGmCommandTable._log.info("建立GM指令使用紀錄: " + pc.getName() + " " + cmd);
            }
            ps.execute();
        }
        catch (SQLException e) {
            ServerGmCommandTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}

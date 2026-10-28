package com.lineage.server.datatables.sql;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.ItemTable;
import com.lineage.DatabaseFactory;
import java.sql.Timestamp;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.ServerCnInfoStorage;

public class ServerCnInfoTable implements ServerCnInfoStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(ServerCnInfoTable.class);
    }
    
    @Override
    public void create(L1PcInstance pc, L1Item itemtmp, long count, boolean mode, int itemid_cn) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            Timestamp lastactive = new Timestamp(System.currentTimeMillis());
            con = DatabaseFactory.get().getConnection();
            String sqlstr = "INSERT INTO `other_cn_shop` SET `itemname`=?,`itemid`=?,`selling_price`=?,`time`=?,`pcobjid`=?,`mode`=?";
            pstm = con.prepareStatement(sqlstr);
            int i = 0;
            String pcinfo = "(玩家)";
            if (pc.isGm()) {
                pcinfo = "(管理者)";
            }
            L1Item temp = ItemTable.get().getTemplate(itemid_cn);
            pstm.setString(++i, itemtmp.getName() + pcinfo + "-(" + temp.getName() + ")");
            pstm.setInt(++i, itemtmp.getItemId());
            pstm.setLong(++i, count);
            pstm.setTimestamp(++i, lastactive);
            pstm.setInt(++i, pc.getId());
            pstm.setBoolean(++i, mode);
            pstm.execute();
            if (mode) {
                ServerCnInfoTable._log.info("建立商城紀錄 人物:" + pc.getName() + " 買入:" + itemtmp.getName() + " 花費商城幣:" + count);
            }
            else {
                ServerCnInfoTable._log.info("建立商城紀錄 人物:" + pc.getName() + " 賣出:" + itemtmp.getName() + " 獲得商城幣:" + count);
            }
        }
        catch (SQLException e) {
            ServerCnInfoTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

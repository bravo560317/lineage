package com.lineage.server.datatables.sql;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.OtherUserBuyStorage;

public class OtherUserBuyTable implements OtherUserBuyStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(OtherUserBuyTable.class);
    }
    
    @Override
    public void add(String itemname, int itemobjid, int itemadena, long itemcount, int pcobjid, String pcname, int srcpcobjid, String srcpcname) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `other_pcbuy` SET `itemname`=?,`itemobjid`=?,`itemadena`=?,`itemcount`=?,`pcobjid`=?,`pcname`=?,`srcpcobjid`=?,`srcpcname`=?,`datetime`=SYSDATE()");
            int i = 0;
            ps.setString(++i, itemname);
            ps.setInt(++i, itemobjid);
            ps.setInt(++i, itemadena);
            ps.setLong(++i, itemcount);
            ps.setInt(++i, pcobjid);
            ps.setString(++i, pcname + "(買家)");
            ps.setInt(++i, srcpcobjid);
            ps.setString(++i, srcpcname + "(賣家-商店)");
            ps.execute();
        }
        catch (Exception e) {
            SqlError.isError(OtherUserBuyTable._log, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
}

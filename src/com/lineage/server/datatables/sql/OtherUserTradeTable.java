package com.lineage.server.datatables.sql;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.OtherUserTradeStorage;

public class OtherUserTradeTable implements OtherUserTradeStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(OtherUserTradeTable.class);
    }
    
    @Override
    public void add(String itemname, int itemobjid, int itemadena, long itemcount, int pcobjid, String pcname, int srcpcobjid, String srcpcname, String ip, String acc) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `other_pctrade` SET `itemname`=?,`itemobjid`=?,`itemadena`=?,`itemcount`=?,`pcobjid`=?,`pcname`=?,`srcpcobjid`=?,`srcpcname`=?,`ip`=?,`acc`=?,`datetime`=SYSDATE()");
            int i = 0;
            ps.setString(++i, itemname);
            ps.setInt(++i, itemobjid);
            ps.setInt(++i, itemadena);
            ps.setLong(++i, itemcount);
            ps.setInt(++i, pcobjid);
            ps.setString(++i, "移入人物:" + pcname);
            ps.setInt(++i, srcpcobjid);
            ps.setString(++i, "移出人物:" + srcpcname);
            ps.setString(++i, ip);
            ps.setString(++i, acc);
            ps.execute();
        }
        catch (Exception e) {
            SqlError.isError(OtherUserTradeTable._log, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
}

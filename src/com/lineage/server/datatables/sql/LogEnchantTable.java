package com.lineage.server.datatables.sql;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.LogEnchantStorage;

public class LogEnchantTable implements LogEnchantStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(LogEnchantTable.class);
    }
    
    @Override
    public void failureEnchant(L1PcInstance pc, L1ItemInstance item) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `other_enchant` SET `id`=?,`item_id`=?,`char_id`=?,`item_name`=?,`count`=?,`is_equipped`=?,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`datetime`=SYSDATE(),`ipmac`=?");
            int i = 0;
            ps.setInt(++i, item.getId());
            ps.setInt(++i, item.getItem().getItemId());
            ps.setInt(++i, pc.getId());
            ps.setString(++i, item.getItem().getName());
            ps.setLong(++i, item.getCount());
            ps.setInt(++i, 0);
            ps.setInt(++i, item.getEnchantLevel());
            ps.setInt(++i, item.isIdentified() ? 1 : 0);
            ps.setInt(++i, item.get_durability());
            ps.setInt(++i, item.getChargeCount());
            ps.setInt(++i, item.getRemainingTime());
            ps.setTimestamp(++i, item.getLastUsed());
            ps.setInt(++i, item.getBless());
            ps.setInt(++i, item.getAttrEnchantKind());
            ps.setInt(++i, item.getAttrEnchantLevel());
            StringBuilder ip = pc.getNetConnection().getIp();
            StringBuilder mac = pc.getNetConnection().getMac();
            ps.setString(++i, ip + "/" + mac);
            ps.execute();
        }
        catch (Exception e) {
            LogEnchantTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}

package com.lineage.server.datatables.sql;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharShiftingStorage;

public class CharShiftingTable implements CharShiftingStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(CharShiftingTable.class);
    }
    
    @Override
    public void newShifting(L1PcInstance pc, int tgId, String tgName, int srcObjid, L1Item srcItem, L1ItemInstance newItem, int mode) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `other_shifting` SET `srcObjid`=?,`srcItemid`=?,`srcName`=?,`newObjid`=?,`newItemid`=?,`newName`=?,`enchantLevel`=?,`attrEnchant`=?,`weaponSkill`=?,`pcObjid`=?,`pcName`=?,`tgPcObjid`=?,`tgPcName`=?,`time`=?,`note`=?");
            int i = 0;
            if (srcItem != null) {
                ps.setInt(++i, srcObjid);
                ps.setInt(++i, srcItem.getItemId());
                ps.setString(++i, srcItem.getName());
            }
            else {
                ps.setInt(++i, 0);
                ps.setInt(++i, 0);
                ps.setString(++i, "");
            }
            ps.setInt(++i, newItem.getId());
            ps.setInt(++i, newItem.getItemId());
            ps.setString(++i, newItem.getItem().getName());
            ps.setInt(++i, newItem.getEnchantLevel());
            ps.setString(++i, String.valueOf(newItem.getAttrEnchantKind()) + "/" + newItem.getAttrEnchantLevel());
            StringBuilder cnSkillInfo = new StringBuilder();
            if (cnSkillInfo.length() > 0) {
                ps.setString(++i, cnSkillInfo.toString());
            }
            else {
                ps.setString(++i, "無效果");
            }
            ps.setInt(++i, pc.getId());
            ps.setString(++i, pc.getName());
            Timestamp lastactive = new Timestamp(System.currentTimeMillis());
            switch (mode) {
                case 0: {
                    ps.setInt(++i, 0);
                    ps.setString(++i, "");
                    ps.setTimestamp(++i, lastactive);
                    ps.setString(++i, "交換裝備");
                    break;
                }
                case 1: {
                    ps.setInt(++i, 0);
                    ps.setString(++i, "");
                    ps.setTimestamp(++i, lastactive);
                    ps.setString(++i, "裝備升級");
                    break;
                }
                case 2: {
                    ps.setInt(++i, tgId);
                    ps.setString(++i, tgName);
                    ps.setTimestamp(++i, lastactive);
                    ps.setString(++i, "轉移裝備");
                    break;
                }
            }
            ps.execute();
        }
        catch (SQLException e) {
            CharShiftingTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}

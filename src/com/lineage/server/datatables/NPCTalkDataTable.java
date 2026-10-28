package com.lineage.server.datatables;

import com.lineage.server.templates.L1Npc;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1NpcTalkData;
import java.util.Map;
import org.apache.commons.logging.Log;

public class NPCTalkDataTable
{
    private static final Log _log;
    private static NPCTalkDataTable _instance;
    private static final Map<Integer, L1NpcTalkData> _datatable;
    
    static {
        _log = LogFactory.getLog(NPCTalkDataTable.class);
        _datatable = new HashMap();
    }
    
    public static NPCTalkDataTable get() {
        if (NPCTalkDataTable._instance == null) {
            NPCTalkDataTable._instance = new NPCTalkDataTable();
        }
        return NPCTalkDataTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM npcaction");
            rs = pm.executeQuery();
            while (rs.next()) {
                L1NpcTalkData action = new L1NpcTalkData();
                int npcTemplateId = rs.getInt("npcid");
                action.setNpcID(rs.getInt("npcid"));
                L1Npc temp1 = NpcTable.get().getTemplate(npcTemplateId);
                if (temp1 == null) {
                    NPCTalkDataTable._log.error("NPC對話資料編號: " + npcTemplateId + " 不存在資料庫中!");
                    delete(npcTemplateId);
                }
                else {
                    action.setNormalAction(rs.getString("normal_action"));
                    action.setCaoticAction(rs.getString("caotic_action"));
                    action.setTeleportURL(rs.getString("teleport_url"));
                    action.setTeleportURLA(rs.getString("teleport_urla"));
                    action.setCurrencyItemId(rs.getInt("currency_itemid"));
                    NPCTalkDataTable._datatable.put(new Integer(action.getNpcID()), action);
                }
            }
        }
        catch (SQLException e) {
            NPCTalkDataTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        NPCTalkDataTable._log.info("載入NPC對話資料數量: " + NPCTalkDataTable._datatable.size() + "(" + timer.get() + "ms)");
    }
    
    public L1NpcTalkData getTemplate(int i) {
        return NPCTalkDataTable._datatable.get(new Integer(i));
    }
    
    private static void delete(int npc_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `npcaction` WHERE `npcid`=?");
            ps.setInt(1, npc_id);
            ps.execute();
        }
        catch (SQLException e) {
            NPCTalkDataTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}

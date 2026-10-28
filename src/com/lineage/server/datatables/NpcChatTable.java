package com.lineage.server.datatables;

import java.util.Collections;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.templates.L1NpcChat;
import java.util.Map;
import org.apache.commons.logging.Log;

public class NpcChatTable
{
    private static final Log _log;
    private static NpcChatTable _instance;
    private static final Map<Integer, L1NpcChat> _npcChatAppearance;
    private static final Map<Integer, L1NpcChat> _npcChatDead;
    private static final Map<Integer, L1NpcChat> _npcChatHide;
    private static final Map<Integer, L1NpcChat> _npcChatGameTime;
    private Collection<L1NpcChat> _allTimeValues;
    
    static {
        _log = LogFactory.getLog(NpcChatTable.class);
        _npcChatAppearance = new HashMap();
        _npcChatDead = new HashMap();
        _npcChatHide = new HashMap();
        _npcChatGameTime = new HashMap();
    }
    
    public static NpcChatTable get() {
        if (NpcChatTable._instance == null) {
            NpcChatTable._instance = new NpcChatTable();
        }
        return NpcChatTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `npcchat`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1NpcChat npcChat = new L1NpcChat();
                npcChat.setNpcId(rs.getInt("npc_id"));
                npcChat.setChatTiming(rs.getInt("chat_timing"));
                npcChat.setStartDelayTime(rs.getInt("start_delay_time"));
                npcChat.setChatId1(rs.getString("chat_id1"));
                npcChat.setChatId2(rs.getString("chat_id2"));
                npcChat.setChatId3(rs.getString("chat_id3"));
                npcChat.setChatId4(rs.getString("chat_id4"));
                npcChat.setChatId5(rs.getString("chat_id5"));
                npcChat.setChatInterval(rs.getInt("chat_interval"));
                npcChat.setShout(rs.getBoolean("is_shout"));
                npcChat.setWorldChat(rs.getBoolean("is_world_chat"));
                npcChat.setRepeat(rs.getBoolean("is_repeat"));
                npcChat.setRepeatInterval(rs.getInt("repeat_interval"));
                npcChat.setGameTime(rs.getInt("game_time"));
                if (npcChat.getChatTiming() == 0) {
                    NpcChatTable._npcChatAppearance.put(new Integer(npcChat.getNpcId()), npcChat);
                }
                else if (npcChat.getChatTiming() == 1) {
                    NpcChatTable._npcChatDead.put(new Integer(npcChat.getNpcId()), npcChat);
                }
                else if (npcChat.getChatTiming() == 2) {
                    NpcChatTable._npcChatHide.put(new Integer(npcChat.getNpcId()), npcChat);
                }
                else if (npcChat.getChatTiming() == 3) {
                    NpcChatTable._npcChatGameTime.put(new Integer(npcChat.getNpcId()), npcChat);
                }
                else {
                    continue;
                }
            }
        }
        catch (SQLException e) {
            NpcChatTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        NpcChatTable._log.info("載入NPC會話資料數量: " + (NpcChatTable._npcChatAppearance.size() + NpcChatTable._npcChatDead.size() + NpcChatTable._npcChatHide.size() + NpcChatTable._npcChatGameTime.size()) + "(" + timer.get() + "ms)");
    }
    
    public L1NpcChat getTemplateAppearance(int i) {
        return NpcChatTable._npcChatAppearance.get(new Integer(i));
    }
    
    public L1NpcChat getTemplateDead(int i) {
        return NpcChatTable._npcChatDead.get(new Integer(i));
    }
    
    public L1NpcChat getTemplateHide(int i) {
        return NpcChatTable._npcChatHide.get(new Integer(i));
    }
    
    public L1NpcChat getTemplateGameTime(int i) {
        return NpcChatTable._npcChatGameTime.get(new Integer(i));
    }
    
    public Collection<L1NpcChat> all() {
        try {
            Collection vs = this._allTimeValues;
            return this._allTimeValues = (Collection)Collections.unmodifiableCollection(NpcChatTable._npcChatGameTime.values());
        }
        catch (Exception e) {
            NpcChatTable._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public L1NpcChat[] getAllGameTime() {
        return (L1NpcChat[])NpcChatTable._npcChatGameTime.values().toArray(new L1NpcChat[NpcChatTable._npcChatGameTime.size()]);
    }
}

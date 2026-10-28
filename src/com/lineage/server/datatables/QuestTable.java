package com.lineage.server.datatables;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.data.QuestClass;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Quest;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class QuestTable
{
    private static final Log _log;
    private static QuestTable _instance;
    public static int MINQID;
    public static int MAXQID;
    private static final HashMap<Integer, L1Quest> _questList;
    
    static {
        _log = LogFactory.getLog(QuestTable.class);
        QuestTable.MINQID = 0;
        QuestTable.MAXQID = 0;
        _questList = new HashMap();
    }
    
    public static QuestTable get() {
        if (QuestTable._instance == null) {
            QuestTable._instance = new QuestTable();
        }
        return QuestTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_quest` ORDER BY `id`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String questname = rs.getString("questname");
                String questclass = rs.getString("questclass");
                if (!questclass.equals("0")) {
                    boolean queststart = rs.getBoolean("queststart");
                    if (queststart) {
                        if (id > QuestTable.MAXQID) {
                            QuestTable.MAXQID = id;
                        }
                        boolean del = rs.getBoolean("del");
                        int questuser = rs.getInt("questuser");
                        int questlevel = rs.getInt("questlevel");
                        int difficulty = rs.getInt("difficulty");
                        String note = rs.getString("note");
                        if (queststart) {
                            L1Quest quest = new L1Quest();
                            quest.set_id(id);
                            quest.set_questname(questname);
                            quest.set_questclass(questclass);
                            quest.set_queststart(queststart);
                            quest.set_del(del);
                            quest.set_questuser(questuser);
                            quest.set_questlevel(questlevel);
                            quest.set_difficulty(difficulty);
                            quest.set_note(note);
                            QuestClass.get().addList(id, questclass);
                            QuestTable._questList.put(new Integer(id), quest);
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
            QuestTable._log.info("載入Quest(任務)設置資料數量: " + QuestTable._questList.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            QuestTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            this.start();
        }
    }
    
    private void start() {
        try {
            for (L1Quest quest : QuestTable._questList.values()) {
                QuestClass.get().execute(quest);
                Thread.sleep(20L);
            }
        }
        catch (Exception e) {
            QuestTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public L1Quest getTemplate(int id) {
        return QuestTable._questList.get(new Integer(id));
    }
    
    public HashMap<Integer, L1Quest> getList() {
        return QuestTable._questList;
    }
    
    public int size() {
        return QuestTable._questList.size();
    }
    
    public int levelQuest(L1PcInstance pc, int level) {
        int i = 0;
        for (Integer key : QuestTable._questList.keySet()) {
            L1Quest value = QuestTable._questList.get(key);
            if (level >= value.get_questlevel() && !pc.getQuest().isEnd(key) && value.check(pc)) {
                ++i;
            }
        }
        return i;
    }
}

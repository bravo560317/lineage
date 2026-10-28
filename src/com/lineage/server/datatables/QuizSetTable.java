package com.lineage.server.datatables;

import java.util.Random;
import com.lineage.config.ConfigAlt;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public final class QuizSetTable
{
    private static final Log _log;
    public static int GS_quizId;
    public static String GS_showQuiz;
    public static String[] GS_option;
    public static byte GS_answer;
    private static QuizSetTable _instance;
    
    static {
        _log = LogFactory.getLog(QuizSetTable.class);
        QuizSetTable.GS_option = new String[4];
    }
    
    public static QuizSetTable getInstance() {
        if (QuizSetTable._instance == null) {
            QuizSetTable._instance = new QuizSetTable();
        }
        return QuizSetTable._instance;
    }
    
    public final void load() {
        this.updateQuizInfo();
        QuizSetTable._log.info("載入每日一題資料 (quiz_id: " + QuizSetTable.GS_quizId + ")");
    }
    
    public final void updateQuizInfo() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("SELECT * FROM w_npc每日問答題 WHERE is_active=1");
            rs = pstm.executeQuery();
            if (rs.next()) {
                QuizSetTable.GS_quizId = rs.getInt("quiz_id");
                QuizSetTable.GS_showQuiz = rs.getString("show_quiz");
                QuizSetTable.GS_option[0] = rs.getString("optionA");
                QuizSetTable.GS_option[1] = rs.getString("optionB");
                QuizSetTable.GS_option[2] = rs.getString("optionC");
                QuizSetTable.GS_option[3] = rs.getString("optionD");
                QuizSetTable.GS_answer = rs.getByte("answer");
            }
        }
        catch (SQLException e) {
            QuizSetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
    }
    
    public final void updateQuizToNext() {
        int quiz_id = 0;
        switch (ConfigAlt.QUIZ_SET_TYPE) {
            case 0: {
                return;
            }
            case 1: {
                if (this.checkQuizExist(QuizSetTable.GS_quizId + 1)) {
                    quiz_id = QuizSetTable.GS_quizId + 1;
                    break;
                }
                else {
                    break;
                }
            }
            case 2: {
                quiz_id = new Random().nextInt(this.getQuizSize());
                break;
            }
        }
        QuizSetTable.GS_showQuiz = null;
        QuizSetTable.GS_option[0] = null;
        QuizSetTable.GS_option[1] = null;
        QuizSetTable.GS_option[2] = null;
        QuizSetTable.GS_option[3] = null;
        QuizSetTable.GS_answer = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        PreparedStatement pstm2 = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("UPDATE w_npc每日問答題 SET is_active=0");
            pstm.execute();
            pstm2 = conn.prepareStatement("UPDATE w_npc每日問答題 SET is_active=1 WHERE quiz_id=?");
            pstm2.setInt(1, quiz_id);
            pstm2.execute();
        }
        catch (SQLException e) {
            QuizSetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm2);
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
    }
    
    private final boolean checkQuizExist(int quiz_id) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("SELECT * FROM w_npc每日問答題 WHERE quiz_id=?");
            pstm.setInt(1, quiz_id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            QuizSetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
        return false;
    }
    
    private final int getQuizSize() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("SELECT count(*) FROM w_npc每日問答題");
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        catch (SQLException e) {
            QuizSetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
        return 0;
    }
    
    public final void updateAllPcQuizSet() {
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("UPDATE character_quests SET quest_step=0 WHERE quest_id=81245 AND quest_step=1");
            pstm.execute();
        }
        catch (SQLException e) {
            QuizSetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
    }
}

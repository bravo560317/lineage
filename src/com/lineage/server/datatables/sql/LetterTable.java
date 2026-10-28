package com.lineage.server.datatables.sql;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class LetterTable
{
    private static final Log _log;
    private static LetterTable _instance;
    
    static {
        _log = LogFactory.getLog(LetterTable.class);
    }
    
    public static LetterTable getInstance() {
        if (LetterTable._instance == null) {
            LetterTable._instance = new LetterTable();
        }
        return LetterTable._instance;
    }
    
    public void writeLetter(int itemObjectId, int code, String sender, String receiver, String date, int templateId, byte[] subject, byte[] content) {
        Connection con = null;
        PreparedStatement pstm1 = null;
        ResultSet rs = null;
        PreparedStatement pstm2 = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm1 = con.prepareStatement("SELECT * FROM `character_letter` ORDER BY `item_object_id`");
            rs = pstm1.executeQuery();
            pstm2 = con.prepareStatement("INSERT INTO `character_letter` SET `item_object_id`=?,`code`=?,`sender`=?,`receiver`=?,`date`=?,`template_id`=?,`subject`=?,`content`=?");
            pstm2.setInt(1, itemObjectId);
            pstm2.setInt(2, code);
            pstm2.setString(3, sender);
            pstm2.setString(4, receiver);
            pstm2.setString(5, date);
            pstm2.setInt(6, templateId);
            pstm2.setBytes(7, subject);
            pstm2.setBytes(8, content);
            pstm2.execute();
        }
        catch (SQLException e) {
            LetterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm1);
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
        }
    }
    
    public void deleteLetter(int itemObjectId) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `character_letter` WHERE `item_object_id`=?");
            pstm.setInt(1, itemObjectId);
            pstm.execute();
        }
        catch (SQLException e) {
            LetterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

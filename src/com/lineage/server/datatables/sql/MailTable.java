package com.lineage.server.datatables.sql;

import java.util.Iterator;
import com.lineage.server.IdFactory;
import java.sql.Timestamp;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Mail;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class MailTable
{
    private static final Log _log;
    private static MailTable _instance;
    private static ArrayList<L1Mail> _allMail;
    
    static {
        _log = LogFactory.getLog(MailTable.class);
        MailTable._allMail = new ArrayList();
    }
    
    public static MailTable get() {
        if (MailTable._instance == null) {
            MailTable._instance = new MailTable();
        }
        return MailTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM mails");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1Mail mail = new L1Mail();
                mail.setId(rs.getInt("id"));
                mail.setType(rs.getInt("type"));
                mail.setSenderName(rs.getString("sender"));
                mail.setReceiverName(rs.getString("receiver"));
                mail.setDate(rs.getTimestamp("date"));
                mail.setReadStatus(rs.getInt("read_status"));
                mail.setInBoxId(rs.getInt("inbox_id"));
                mail.setSubject(rs.getBytes("subject"));
                mail.setContent(rs.getBytes("content"));
                MailTable._allMail.add(mail);
            }
        }
        catch (SQLException e) {
            MailTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        MailTable._log.info("載入信件資料數量: " + MailTable._allMail.size() + "(" + timer.get() + "ms)");
    }
    
    public void setReadStatus(int mailId) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE mails SET read_status=? WHERE id=?");
            pstm.setInt(1, 1);
            pstm.setInt(2, mailId);
            pstm.execute();
            this.changeMailStatus(mailId);
        }
        catch (SQLException e) {
            MailTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void setMailType(int mailId, int type) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE mails SET type=? WHERE id=?");
            pstm.setInt(1, type);
            pstm.setInt(2, mailId);
            pstm.execute();
            this.changeMailType(mailId, type);
        }
        catch (SQLException e) {
            MailTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteMail(int mailId) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM mails WHERE id=?");
            pstm.setInt(1, mailId);
            pstm.execute();
            this.delMail(mailId);
        }
        catch (SQLException e) {
            MailTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public int writeMail(int type, String receiver, L1PcInstance writer, byte[] text, int inboxId) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        int readStatus = 0;
        int id = 0;
        int spacePosition1 = 0;
        int spacePosition2 = 0;
        for (int i = 0; i < text.length; i += 2) {
            if (text[i] == 0 && text[i + 1] == 0) {
                if (spacePosition1 == 0) {
                    spacePosition1 = i;
                }
                else if (spacePosition1 != 0 && spacePosition2 == 0) {
                    spacePosition2 = i;
                    break;
                }
            }
        }
        int subjectLength = spacePosition1 + 2;
        int contentLength = spacePosition2 - spacePosition1;
        if (contentLength <= 0) {
            contentLength = 1;
        }
        byte[] subject = new byte[subjectLength];
        byte[] content = new byte[contentLength];
        System.arraycopy(text, 0, subject, 0, subjectLength);
        System.arraycopy(text, subjectLength, content, 0, contentLength);
        Connection con = null;
        PreparedStatement pstm2 = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm2 = con.prepareStatement("INSERT INTO mails SET id=?, type=?, sender=?, receiver=?, date=?, read_status=?, inbox_id=?, subject=?, content=?");
            id = IdFactory.get().nextId();
            pstm2.setInt(1, id);
            pstm2.setInt(2, type);
            pstm2.setString(3, writer.getName());
            pstm2.setString(4, receiver);
            pstm2.setTimestamp(5, date);
            pstm2.setInt(6, readStatus);
            pstm2.setInt(7, inboxId);
            pstm2.setBytes(8, subject);
            pstm2.setBytes(9, content);
            pstm2.execute();
            L1Mail mail = new L1Mail();
            mail.setId(id);
            mail.setType(type);
            mail.setSenderName(writer.getName());
            mail.setReceiverName(receiver);
            mail.setDate(date);
            mail.setReadStatus(readStatus);
            mail.setInBoxId(inboxId);
            mail.setSubject(subject);
            mail.setContent(content);
            MailTable._allMail.add(mail);
        }
        catch (SQLException e) {
            MailTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
        }
        return id;
    }
    
    public ArrayList<L1Mail> getAllMail() {
        return MailTable._allMail;
    }
    
    public L1Mail getMail(int mailId) {
        for (L1Mail mail : MailTable._allMail) {
            if (mail.getId() == mailId) {
                return mail;
            }
        }
        return null;
    }
    
    private void changeMailStatus(int mailId) {
        for (L1Mail mail : MailTable._allMail) {
            if (mail.getId() == mailId) {
                L1Mail newMail = mail;
                newMail.setReadStatus(1);
                MailTable._allMail.remove(mail);
                MailTable._allMail.add(newMail);
                break;
            }
        }
    }
    
    private void changeMailType(int mailId, int type) {
        for (L1Mail mail : MailTable._allMail) {
            if (mail.getId() == mailId) {
                L1Mail newMail = mail;
                newMail.setType(type);
                MailTable._allMail.remove(mail);
                MailTable._allMail.add(newMail);
                break;
            }
        }
    }
    
    private void delMail(int mailId) {
        for (L1Mail mail : MailTable._allMail) {
            if (mail.getId() == mailId) {
                MailTable._allMail.remove(mail);
                break;
            }
        }
    }
}

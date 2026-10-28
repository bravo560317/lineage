package com.lineage.server.datatables.sql;

import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Board;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.BoardStorage;

public class BoardTable implements BoardStorage
{
    private static final Log _log;
    private static final Map<Integer, L1Board> _boards;
    private static int _maxid;
    
    static {
        _log = LogFactory.getLog(BoardTable.class);
        _boards = new HashMap();
        BoardTable._maxid = 0;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `server_board` ORDER BY `id`");
            rs = ps.executeQuery();
            while (rs.next()) {
                L1Board board = new L1Board();
                int id = rs.getInt("id");
                if (id > BoardTable._maxid) {
                    BoardTable._maxid = id;
                }
                board.set_id(id);
                board.set_name(rs.getString("name"));
                board.set_date(rs.getString("date"));
                board.set_title(rs.getString("title"));
                board.set_content(rs.getString("content"));
                BoardTable._boards.put(board.get_id(), board);
            }
        }
        catch (SQLException e) {
            BoardTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        BoardTable._log.info("載入佈告欄資料數量: " + BoardTable._boards.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public Map<Integer, L1Board> getBoardMap() {
        return BoardTable._boards;
    }
    
    @Override
    public L1Board[] getBoardTableList() {
        return (L1Board[])BoardTable._boards.values().toArray(new L1Board[BoardTable._boards.size()]);
    }
    
    @Override
    public L1Board getBoardTable(int houseId) {
        return BoardTable._boards.get((Integer)houseId);
    }
    
    @Override
    public int getMaxId() {
        return BoardTable._maxid;
    }
    
    @Override
    public void writeTopic(L1PcInstance pc, String date, String title, String content) {
        L1Board board = new L1Board();
        board.set_id(++BoardTable._maxid);
        board.set_name(pc.getName());
        board.set_date(date);
        board.set_title(title);
        board.set_content(content);
        BoardTable._boards.put(board.get_id(), board);
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `server_board` SET `id`=?,`name`=?,`date`=?,`title`=?,`content`=?");
            ps.setInt(1, board.get_id());
            ps.setString(2, board.get_name());
            ps.setString(3, board.get_date());
            ps.setString(4, board.get_title());
            ps.setString(5, board.get_content());
            ps.execute();
        }
        catch (SQLException e) {
            BoardTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void deleteTopic(int number) {
        L1Board board = BoardTable._boards.get((Integer)number);
        if (board != null) {
            Connection co = null;
            PreparedStatement ps = null;
            try {
                co = DatabaseFactory.get().getConnection();
                ps = co.prepareStatement("DELETE FROM `server_board` WHERE `id`=?");
                ps.setInt(1, board.get_id());
                ps.execute();
                BoardTable._boards.remove((Integer)number);
            }
            catch (SQLException e) {
                BoardTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(co);
            }
        }
    }
}

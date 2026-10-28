package com.lineage.server.datatables.sql;

import com.lineage.server.templates.L1BookConfig;
import com.lineage.server.serverpackets.S_Bookmarks;
import com.lineage.server.IdFactory;
import com.lineage.server.datatables.lock.CharBookConfigReading;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1TownLocation;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1BookMark;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharBookStorage;

public class CharBookTable implements CharBookStorage
{
    private static final Log _log;
    private static final Map<Integer, ArrayList<L1BookMark>> _bookmarkMap;
    
    static {
        _log = LogFactory.getLog(CharBookTable.class);
        _bookmarkMap = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM character_teleport");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int char_id = rs.getInt("char_id");
                if (CharObjidTable.get().isChar(char_id) != null) {
                    String name = rs.getString("name");
                    int locx = rs.getInt("locx");
                    int locy = rs.getInt("locy");
                    short mapid = rs.getShort("mapid");
                    L1BookMark bookmark = new L1BookMark();
                    bookmark.setId(id);
                    bookmark.setCharId(char_id);
                    bookmark.setName(name);
                    bookmark.setLocX(locx);
                    bookmark.setLocY(locy);
                    bookmark.setMapId(mapid);
                    addMap(char_id, bookmark);
                }
                else {
                    delete(char_id);
                }
            }
        }
        catch (SQLException e) {
            CharBookTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharBookTable._log.info("載入記憶座標紀錄資料數量: " + CharBookTable._bookmarkMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM character_teleport WHERE char_id=?");
            ps.setInt(1, objid);
            ps.execute();
            CharBookTable._bookmarkMap.remove((Integer)objid);
        }
        catch (SQLException e) {
            CharBookTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addMap(int objId, L1BookMark bookmark) {
        ArrayList<L1BookMark> bookmarks = CharBookTable._bookmarkMap.get((Integer)objId);
        if (!L1TownLocation.isGambling(bookmark.getLocX(), bookmark.getLocY(), bookmark.getMapId())) {
            if (bookmarks == null) {
                bookmarks = new ArrayList();
                bookmarks.add(bookmark);
                CharBookTable._bookmarkMap.put(objId, bookmarks);
            }
            else {
                bookmarks.add(bookmark);
            }
        }
        else {
            deleteBookmark(bookmark.getId());
        }
    }
    
    private static void deleteBookmark(int id) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_teleport WHERE id=?");
            pstm.setInt(1, id);
            pstm.execute();
        }
        catch (SQLException e) {
            CharBookTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public ArrayList<L1BookMark> getBookMarks(L1PcInstance pc) {
        ArrayList<L1BookMark> bookmarks = CharBookTable._bookmarkMap.get((Integer)pc.getId());
        return bookmarks;
    }
    
    @Override
    public L1BookMark getBookMark(L1PcInstance pc, int i) {
        ArrayList<L1BookMark> bookmarks = CharBookTable._bookmarkMap.get((Integer)pc.getId());
        if (bookmarks != null) {
            for (L1BookMark book : bookmarks) {
                if (book.getId() == i) {
                    return book;
                }
            }
        }
        return null;
    }
    
    @Override
    public void deleteBookmark(L1PcInstance pc, String s) {
        ArrayList<L1BookMark> bookmarks = CharBookTable._bookmarkMap.get((Integer)pc.getId());
        if (bookmarks != null) {
            for (L1BookMark book : bookmarks) {
                if (book.getName().equalsIgnoreCase(s)) {
                    Connection con = null;
                    PreparedStatement pstm = null;
                    try {
                        con = DatabaseFactory.get().getConnection();
                        pstm = con.prepareStatement("DELETE FROM character_teleport WHERE id=?");
                        pstm.setInt(1, book.getId());
                        pstm.execute();
                        bookmarks.remove(book);
                    }
                    catch (SQLException e) {
                        CharBookTable._log.error(e.getLocalizedMessage(), e);
                    }
                    finally {
                        SQLUtil.close(pstm);
                        SQLUtil.close(con);
                    }
                }
            }
        }
    }
    
    @Override
    public void addBookmark(L1PcInstance pc, String s) {
        if (!pc.getMap().isMarkable()) {
            pc.sendPackets(new S_ServerMessage(214));
            return;
        }
        else {
            ArrayList<L1BookMark> bookmarks = CharBookTable._bookmarkMap.get((Integer)pc.getId());
            if (bookmarks == null) {
                bookmarks = new ArrayList();
                CharBookTable._bookmarkMap.put(pc.getId(), bookmarks);
            }
            int maxSize = ConfigAlt.CHAR_BOOK_INIT_COUNT;
            L1BookConfig config = CharBookConfigReading.get().get(pc.getId());
            if (config != null) {
                maxSize += config.getMaxSize();
            }
            int size = bookmarks.size();
            if (size > maxSize) {
                pc.sendPackets(new S_ServerMessage(676));
                return;
            }
            else {
                for (L1BookMark book : bookmarks) {
                    if (book.getName().equalsIgnoreCase(s)) {
                        pc.sendPackets(new S_ServerMessage(327));
                        return;
                    }
                }
                L1BookMark book = new L1BookMark();
                book.setId(IdFactory.get().nextId());
                book.setCharId(pc.getId());
                book.setName(s);
                book.setLocX(pc.getX());
                book.setLocY(pc.getY());
                book.setMapId(pc.getMapId());
                Connection con = null;
                PreparedStatement pstm = null;
                try {
                    con = DatabaseFactory.get().getConnection();
                    pstm = con.prepareStatement("INSERT INTO character_teleport SET id=?,char_id=?,name=?,locx=?,locy=?,mapid=?");
                    pstm.setInt(1, book.getId());
                    pstm.setInt(2, book.getCharId());
                    pstm.setString(3, book.getName());
                    pstm.setInt(4, book.getLocX());
                    pstm.setInt(5, book.getLocY());
                    pstm.setInt(6, (int)book.getMapId());
                    pstm.execute();
                }
                catch (SQLException e) {
                    CharBookTable._log.error(e.getLocalizedMessage(), e);
                }
                finally {
                    SQLUtil.close(pstm);
                    SQLUtil.close(con);
                }
                bookmarks.add(book);
                pc.sendPackets(new S_Bookmarks(s, (int)book.getMapId(), book.getId()));
                return;
            }
        }
    }
    
    @Override
    public void updateBookmarkName(L1BookMark bookmark) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE character_teleport SET name=? WHERE id=?");
            pstm.setString(1, bookmark.getName());
            pstm.setInt(2, bookmark.getId());
            pstm.execute();
        }
        catch (SQLException e) {
            CharBookTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

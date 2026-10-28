package com.lineage.server.datatables.sql;

import java.util.TimeZone;
import com.lineage.config.Config;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1AuctionBoardTmp;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.AuctionBoardStorage;

public class AuctionBoardTable implements AuctionBoardStorage
{
    private static final Log _log;
    private static final Map<Integer, L1AuctionBoardTmp> _boards;
    private static final Map<Integer, Integer> _traps;
    private static AuctionBoardTable _instance;
    
    static {
        _log = LogFactory.getLog(AuctionBoardTable.class);
        _boards = new HashMap();
        _traps = new HashMap();
    }
    
    private Calendar timestampToCalendar(Timestamp ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        return cal;
    }
    
    public static void reload() {
        AuctionBoardTable._instance = new AuctionBoardTable();
        AuctionBoardTable._traps.clear();
        AuctionBoardTable._instance.load();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM server_board_auction");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1AuctionBoardTmp board = new L1AuctionBoardTmp();
                int houseId = rs.getInt("house_id");
                String house_name = rs.getString("house_name");
                int house_area = rs.getInt("house_area");
                Calendar deadline = this.timestampToCalendar((Timestamp)rs.getObject("deadline"));
                String location = rs.getString("location");
                long price = rs.getLong("price");
                String old_owner = rs.getString("old_owner");
                int old_owner_id = rs.getInt("old_owner_id");
                String bidder = rs.getString("bidder");
                int bidder_id = rs.getInt("bidder_id");
                if (!location.startsWith("$")) {
                    String townName = "";
                    if (houseId >= 262145 && houseId <= 262189) {
                        townName = "$1242 ";
                    }
                    if (houseId >= 327681 && houseId <= 327691) {
                        townName = "$1513 ";
                    }
                    if (houseId >= 458753 && houseId <= 458819) {
                        townName = "$2129 ";
                    }
                    if (houseId >= 524289 && houseId <= 524294) {
                        townName = "$381 ";
                    }
                    location = townName + " " + location;
                }
                if (house_name.equals("null")) {
                    house_name = location + "$1195";
                }
                board.setHouseId(houseId);
                board.setHouseName(house_name);
                board.setHouseArea(house_area);
                board.setDeadline(deadline);
                board.setPrice(price);
                board.setLocation(location);
                board.setOldOwner(old_owner);
                board.setOldOwnerId(old_owner_id);
                board.setBidder(bidder);
                board.setBidderId(bidder_id);
                AuctionBoardTable._boards.put(board.getHouseId(), board);
            }
        }
        catch (SQLException e) {
            AuctionBoardTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        AuctionBoardTable._log.info("載入盟屋拍賣公告欄資料數量: " + AuctionBoardTable._boards.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public Map<Integer, L1AuctionBoardTmp> getAuctionBoardTableList() {
        return AuctionBoardTable._boards;
    }
    
    @Override
    public L1AuctionBoardTmp getAuctionBoardTable(int houseId) {
        return AuctionBoardTable._boards.get((Integer)houseId);
    }
    
    @Override
    public void insertAuctionBoard(L1AuctionBoardTmp board) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO server_board_auction SET house_id=?,house_name=?,house_area=?,deadline=?,price=?,location=?,old_owner=?,old_owner_id=?,bidder=?,bidder_id=?");
            pstm.setInt(1, board.getHouseId());
            pstm.setString(2, board.getHouseName());
            pstm.setInt(3, board.getHouseArea());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
            String fm = sdf.format(Calendar.getInstance(tz).getTime());
            pstm.setString(4, fm);
            pstm.setLong(5, board.getPrice());
            pstm.setString(6, board.getLocation());
            pstm.setString(7, board.getOldOwner());
            pstm.setInt(8, board.getOldOwnerId());
            pstm.setString(9, board.getBidder());
            pstm.setInt(10, board.getBidderId());
            pstm.execute();
            AuctionBoardTable._boards.put(board.getHouseId(), board);
        }
        catch (SQLException e) {
            AuctionBoardTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateAuctionBoard(L1AuctionBoardTmp board) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE server_board_auction SET house_name=?,house_area=?,deadline=?,price=?,location=?,old_owner=?,old_owner_id=?,bidder=?,bidder_id=? WHERE house_id=?");
            pstm.setString(1, board.getHouseName());
            pstm.setInt(2, board.getHouseArea());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fm = sdf.format(board.getDeadline().getTime());
            pstm.setString(3, fm);
            pstm.setLong(4, board.getPrice());
            pstm.setString(5, board.getLocation());
            pstm.setString(6, board.getOldOwner());
            pstm.setInt(7, board.getOldOwnerId());
            pstm.setString(8, board.getBidder());
            pstm.setInt(9, board.getBidderId());
            pstm.setInt(10, board.getHouseId());
            pstm.execute();
        }
        catch (SQLException e) {
            AuctionBoardTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void deleteAuctionBoard(int houseId) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM server_board_auction WHERE house_id=?");
            pstm.setInt(1, houseId);
            pstm.execute();
            AuctionBoardTable._boards.remove((Integer)houseId);
        }
        catch (SQLException e) {
            AuctionBoardTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

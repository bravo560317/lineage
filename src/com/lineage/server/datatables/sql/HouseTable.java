package com.lineage.server.datatables.sql;

import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.model.L1HouseLocation;
import com.lineage.server.templates.L1HouseLocTmp;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1House;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.HouseStorage;

public class HouseTable implements HouseStorage
{
    private static final Log _log;
    private static final Map<Integer, L1House> _house;
    
    static {
        _log = LogFactory.getLog(HouseTable.class);
        _house = new HashMap();
    }
    
    private Calendar timestampToCalendar(Timestamp ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        return cal;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_house` ORDER BY `house_id`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1House house = new L1House();
                int houseId = rs.getInt("house_id");
                house.setHouseId(houseId);
                String house_name = rs.getString("house_name");
                int house_area = rs.getInt("house_area");
                String location = rs.getString("location");
                int keeper_id = rs.getInt("keeper_id");
                boolean is_on_sale = rs.getInt("is_on_sale") == 1;
                boolean is_purchase_basement = rs.getInt("is_purchase_basement") == 1;
                Timestamp tax_deadline = rs.getTimestamp("tax_deadline");
                house.setHouseName(house_name);
                house.setHouseArea(house_area);
                house.setLocation(location);
                house.setKeeperId(keeper_id);
                house.setOnSale(is_on_sale);
                house.setPurchaseBasement(is_purchase_basement);
                house.setTaxDeadline(this.timestampToCalendar(tax_deadline));
                HouseTable._house.put(houseId, house);
                int locx1 = rs.getInt("locx1");
                int locx2 = rs.getInt("locx2");
                int locy1 = rs.getInt("locy1");
                int locy2 = rs.getInt("locy2");
                int locx3 = rs.getInt("locx3");
                int locx4 = rs.getInt("locx4");
                int locy3 = rs.getInt("locy3");
                int locy4 = rs.getInt("locy4");
                int mapid = rs.getInt("mapid");
                int basement = rs.getInt("basement");
                int homelocx = rs.getInt("homelocx");
                int homelocy = rs.getInt("homelocy");
                if (locx1 != 0) {
                    L1HouseLocTmp locTmp = new L1HouseLocTmp();
                    locTmp.set_locx1(locx1);
                    locTmp.set_locx2(locx2);
                    locTmp.set_locy1(locy1);
                    locTmp.set_locy2(locy2);
                    locTmp.set_locx3(locx3);
                    locTmp.set_locx4(locx4);
                    locTmp.set_locy3(locy3);
                    locTmp.set_locy4(locy4);
                    locTmp.set_mapid(mapid);
                    locTmp.set_basement(basement);
                    locTmp.set_homelocx(homelocx);
                    locTmp.set_homelocy(homelocy);
                    L1HouseLocation.put(houseId, locTmp);
                }
            }
        }
        catch (SQLException e) {
            HouseTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        HouseTable._log.info("載入盟屋資料數量: " + HouseTable._house.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public Map<Integer, L1House> getHouseTableList() {
        return HouseTable._house;
    }
    
    @Override
    public L1House getHouseTable(int houseId) {
        return HouseTable._house.get((Integer)houseId);
    }
    
    @Override
    public void updateHouse(L1House house) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `server_house` SET `house_name`=?,`house_area`=?,`location`=?, `keeper_id`=?,`is_on_sale`=?,`is_purchase_basement`=?,`tax_deadline`=? WHERE `house_id`=?");
            pstm.setString(1, house.getHouseName());
            pstm.setInt(2, house.getHouseArea());
            pstm.setString(3, house.getLocation());
            pstm.setInt(4, house.getKeeperId());
            pstm.setInt(5, house.isOnSale() ? 1 : 0);
            pstm.setInt(6, house.isPurchaseBasement() ? 1 : 0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fm = sdf.format(house.getTaxDeadline().getTime());
            pstm.setString(7, fm);
            pstm.setInt(8, house.getHouseId());
            pstm.execute();
        }
        catch (SQLException e) {
            HouseTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}

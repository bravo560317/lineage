package com.lineage.server.datatables.sql;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.storage.mysql.MySqlCharacterStorage;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1CharName;
import java.util.Map;
import com.lineage.server.storage.CharacterStorage;
import org.apache.commons.logging.Log;

public class CharacterTable
{
    private static final Log _log;
    private CharacterStorage _charStorage;
    private static CharacterTable _instance;
    private static final Map<String, L1CharName> _charNameList;
    
    static {
        _log = LogFactory.getLog(CharacterTable.class);
        _charNameList = new HashMap();
    }
    
    private CharacterTable() {
        this._charStorage = new MySqlCharacterStorage();
    }
    
    public static CharacterTable get() {
        if (CharacterTable._instance == null) {
            CharacterTable._instance = new CharacterTable();
        }
        return CharacterTable._instance;
    }
    
    public void storeNewCharacter(L1PcInstance pc) throws Exception {
        synchronized (pc) {
            this._charStorage.createCharacter(pc);
            String name = pc.getName();
            if (!CharacterTable._charNameList.containsKey(name)) {
                L1CharName cn = new L1CharName();
                cn.setName(name);
                cn.setId(pc.getId());
                CharacterTable._charNameList.put(name, cn);
            }
        }
    }
    
    public void storeCharacter(L1PcInstance pc) throws Exception {
        synchronized (pc) {
            this._charStorage.storeCharacter(pc);
        }
    }
    
    public void deleteCharacter(String accountName, String charName) throws Exception {
        this._charStorage.deleteCharacter(accountName, charName);
        if (CharacterTable._charNameList.containsKey(charName)) {
            CharacterTable._charNameList.remove(charName);
        }
    }
    
    public L1PcInstance restoreCharacter(String charName) throws Exception {
        L1PcInstance pc = this._charStorage.loadCharacter(charName);
        return pc;
    }
    
    public L1PcInstance loadCharacter(String charName) throws Exception {
        L1PcInstance pc = null;
        try {
            pc = this.restoreCharacter(charName);
            L1Map map = L1WorldMap.get().getMap(pc.getMapId());
            if (map == null) {
                pc.setX(33087);
                pc.setY(33396);
                pc.setMap((short)4);
            }
            else if (!map.isInMap(pc.getX(), pc.getY())) {
                pc.setX(33087);
                pc.setY(33396);
                pc.setMap((short)4);
            }
        }
        catch (Exception e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        return pc;
    }
    
    public static void clearSpeedError() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `characters` SET `SpeedError`='0'");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public static void clearOnlineStatus() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `characters` SET `OnlineStatus`='0'");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public static void updateOnlineStatus(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `characters` SET `OnlineStatus`=? WHERE `objid`=?");
            pstm.setInt(1, pc.getOnlineStatus());
            pstm.setInt(2, pc.getId());
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public static void updatePartnerId(int targetId) {
        updatePartnerId(targetId, 0);
    }
    
    public static void updatePartnerId(int targetId, int partnerId) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `characters` SET `PartnerID`=? WHERE `objid`=?");
            pstm.setInt(1, partnerId);
            pstm.setInt(2, targetId);
            pstm.execute();
        }
        catch (Exception e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public static void saveCharStatus(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `characters` SET `OriginalStr`=?,`OriginalCon`=?,`OriginalDex`=?,`OriginalCha`=?,`OriginalInt`=?,`OriginalWis`=? WHERE `objid`=?");
            pstm.setInt(1, pc.getBaseStr());
            pstm.setInt(2, pc.getBaseCon());
            pstm.setInt(3, pc.getBaseDex());
            pstm.setInt(4, pc.getBaseCha());
            pstm.setInt(5, pc.getBaseInt());
            pstm.setInt(6, pc.getBaseWis());
            pstm.setInt(7, pc.getId());
            pstm.execute();
        }
        catch (Exception e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public static void restoreInventory(L1PcInstance pc) {
        pc.getInventory().loadItems();
        pc.getDwarfInventory().loadItems();
        pc.getDwarfForChaInventory().loadItems();
        pc.getDwarfForElfInventory().loadItems();
    }
    
    public static boolean doesCharNameExist(String name) {
        boolean result = true;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT `account_name` FROM `characters` WHERE `char_name`=?");
            pstm.setString(1, name);
            rs = pstm.executeQuery();
            result = rs.next();
        }
        catch (SQLException e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return result;
    }
    
    public void newCharName(int objid, String name) {
        L1CharName cn = new L1CharName();
        cn.setName(name);
        cn.setId(objid);
        CharacterTable._charNameList.put(name, cn);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `characters` SET `char_name`=? WHERE `objid`=?");
            pstm.setString(1, name);
            pstm.setInt(2, objid);
            pstm.execute();
        }
        catch (Exception e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void newCharName_warehouse(String name) {
        L1CharName cn = new L1CharName();
        cn.setName(name);
        CharacterTable._charNameList.put(name, cn);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `character_warehouse_for_cha` WHERE `owner_name`=?");
            pstm.setString(1, name);
            pstm.execute();
        }
        catch (Exception e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public static void loadAllCharName() {
        L1CharName cn = null;
        String name = null;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `characters`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                cn = new L1CharName();
                name = rs.getString("char_name");
                cn.setName(name);
                cn.setId(rs.getInt("objid"));
                CharacterTable._charNameList.put(name, cn);
            }
        }
        catch (SQLException e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public L1CharName[] getCharNameList() {
        return (L1CharName[])CharacterTable._charNameList.values().toArray(new L1CharName[CharacterTable._charNameList.size()]);
    }
    
    public static String getAccountName(String name) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT `account_name` FROM `characters` WHERE `char_name`=?");
            pstm.setString(1, name);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        }
        catch (SQLException e) {
            CharacterTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return "";
    }
    
    public String getCharName(int objid) {
        for (L1CharName charName : CharacterTable._charNameList.values()) {
            if (charName.getId() == objid) {
                return charName.getName();
            }
        }
        return null;
    }
    
    public int getCharObjid(String name) {
        for (L1CharName allchar : CharacterTable._charNameList.values()) {
            if (name.equalsIgnoreCase(allchar.getName())) {
                return allchar.getId();
            }
        }
        return 0;
    }
}

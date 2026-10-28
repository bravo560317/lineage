package com.lineage.server.datatables;

import com.lineage.server.templates.L1Item;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Npc;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1TeleportLoc;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;

public class NpcTeleportTable
{
    private static final Log _log;
    private static NpcTeleportTable _instance;
    private static final Map<String, HashMap<Integer, L1TeleportLoc>> _teleportLocList;
    private static final Map<Integer, Integer> _timeMap;
    private static final Map<Integer, Integer> _partyMap;
    private static final Map<Integer, HashMap<String, L1TeleportLoc>> _srcMap;
    
    static {
        _log = LogFactory.getLog(NpcTeleportTable.class);
        _teleportLocList = new HashMap();
        _timeMap = new HashMap();
        _partyMap = new HashMap();
        _srcMap = new HashMap();
    }
    
    public static NpcTeleportTable get() {
        if (NpcTeleportTable._instance == null) {
            NpcTeleportTable._instance = new NpcTeleportTable();
        }
        return NpcTeleportTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `npcaction_teleport` ORDER BY `id`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String orderid = rs.getString("orderid");
                int locx = rs.getInt("locx");
                int locy = rs.getInt("locy");
                int mapid = rs.getInt("mapid");
                int itemid = rs.getInt("itemid");
                int price = rs.getInt("price");
                int time = rs.getInt("time");
                int user = rs.getInt("user");
                int min = rs.getInt("min");
                int max = rs.getInt("max");
                String note = rs.getString("note");
                L1TeleportLoc teleportLoc = new L1TeleportLoc();
                teleportLoc.set_id(id);
                teleportLoc.set_name(name);
                teleportLoc.set_orderid(orderid);
                teleportLoc.set_locx(locx);
                teleportLoc.set_locy(locy);
                teleportLoc.set_mapid(mapid);
                teleportLoc.set_itemid(itemid);
                teleportLoc.set_price(price);
                teleportLoc.set_user(user);
                teleportLoc.set_min(min);
                teleportLoc.set_max(max);
                if (name.equalsIgnoreCase(orderid)) {
                    String[] set = note.replace(" ", "").split(",");
                    int[] result = new int[set.length];
                    for (int i = 0; i < result.length; ++i) {
                        result[i] = Integer.parseInt(set[i]);
                    }
                    int[] array;
                    for (int length = (array = result).length, j = 0; j < length; ++j) {
                        int npcid = array[j];
                        L1Npc npc = NpcTable.get().getTemplate(npcid);
                        if (npc == null) {
                            this.del(id);
                        }
                        else if (!npc.getImpl().equalsIgnoreCase("L1Teleporter")) {
                            this.del(id);
                        }
                        else {
                            HashMap<String, L1TeleportLoc> list = NpcTeleportTable._srcMap.get((Integer)npcid);
                            if (list != null) {
                                list.put(orderid, teleportLoc);
                            }
                            else {
                                list = new HashMap();
                                list.put(orderid, teleportLoc);
                            }
                            NpcTeleportTable._srcMap.put(npcid, list);
                        }
                    }
                }
                else if (price > 0) {
                    if (time != 0) {
                        NpcTeleportTable._timeMap.put(mapid, time);
                    }
                    teleportLoc.set_time(time);
                    if (user != 0) {
                        NpcTeleportTable._partyMap.put(mapid, user);
                    }
                    this.addList(orderid, teleportLoc);
                }
                else {
                    continue;
                }
            }
        }
        catch (SQLException e) {
            NpcTeleportTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        NpcTeleportTable._log.info("載入NPC傳送點設置數量: " + NpcTeleportTable._teleportLocList.size() + "(" + timer.get() + "ms)");
        NpcTeleportTable._log.info("載入時間地圖設置數量: " + NpcTeleportTable._timeMap.size() + "(" + timer.get() + "ms)");
        NpcTeleportTable._log.info("載入團隊地圖設置數量: " + NpcTeleportTable._partyMap.size() + "(" + timer.get() + "ms)");
        NpcTeleportTable._log.info("載入官方傳送點設置數量: " + NpcTeleportTable._srcMap.size() + "(" + timer.get() + "ms)");
    }
    
    private void del(int id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `npcaction_teleport` WHERE `id`=?");
            ps.setInt(1, id);
            ps.execute();
        }
        catch (SQLException e) {
            NpcTeleportTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void addList(String orderid, L1TeleportLoc teleportLoc) {
        HashMap<Integer, L1TeleportLoc> map = NpcTeleportTable._teleportLocList.get(orderid);
        int id = 0;
        if (map != null) {
            id = map.size();
            map.put(new Integer(id), teleportLoc);
        }
        else {
            map = new HashMap();
            map.put(new Integer(id), teleportLoc);
        }
        NpcTeleportTable._teleportLocList.put(orderid, map);
    }
    
    public Map<String, HashMap<Integer, L1TeleportLoc>> get_locs() {
        return NpcTeleportTable._teleportLocList;
    }
    
    public Integer isPartyMap(int mapid) {
        return NpcTeleportTable._partyMap.get((Integer)mapid);
    }
    
    public Map<Integer, Integer> partyMaps() {
        return NpcTeleportTable._partyMap;
    }
    
    public boolean isTimeMap(int mapid) {
        return NpcTeleportTable._timeMap.get((Integer)mapid) != null;
    }
    
    public Map<Integer, Integer> timeMaps() {
        return NpcTeleportTable._timeMap;
    }
    
    public HashMap<Integer, L1TeleportLoc> get_teles(String orderid) {
        if (NpcTeleportTable._teleportLocList.get(orderid) != null) {
            return NpcTeleportTable._teleportLocList.get(orderid);
        }
        else {
            return null;
        }
    }
    
    public L1TeleportLoc get_loc(String orderid, int id) {
        HashMap<Integer, L1TeleportLoc> map = NpcTeleportTable._teleportLocList.get(orderid);
        if (map != null) {
            return map.get(new Integer(id));
        }
        else {
            return null;
        }
    }
    
    public boolean get_teleport(L1PcInstance pc, String orderid, int npcid) {
        HashMap<String, L1TeleportLoc> map = NpcTeleportTable._srcMap.get((Integer)npcid);
        if (map != null) {
            L1TeleportLoc t = map.get(orderid);
            if (t != null) {
                if (t.get_price() > 0) {
                    if (!pc.getInventory().checkItem(t.get_itemid(), (long)t.get_price())) {
                        L1Item itemtmp = ItemTable.get().getTemplate(t.get_itemid());
                        pc.sendPackets(new S_ServerMessage(337, itemtmp.getNameId()));
                        return true;
                    }
                    else {
                        pc.getInventory().consumeItem(t.get_itemid(), (long)t.get_price());
                    }
                }
                L1Teleport.teleport(pc, t.get_locx(), t.get_locy(), (short)t.get_mapid(), 5, true);
                return true;
            }
        }
        return false;
    }
    
    public void set(String orderid, int locx, int locy, int mapid, int price, String npcids) {
        npcids = (npcids.equals("") ? "---" : npcids);
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `npcaction_teleport` SET `name`=?,`orderid`=?,`locx`=?,`locy`=?,`mapid`=?,`itemid`=?,`price`=?,`time`=?,`user`=?,`min`=?,`max`=?,`note`=?");
            int i = 0;
            ps.setString(++i, orderid);
            ps.setString(++i, orderid);
            ps.setInt(++i, locx);
            ps.setInt(++i, locy);
            ps.setInt(++i, mapid);
            ps.setInt(++i, 40308);
            ps.setInt(++i, price);
            ps.setInt(++i, 0);
            ps.setInt(++i, 0);
            ps.setInt(++i, 0);
            ps.setInt(++i, 200);
            ps.setString(++i, npcids);
            ps.execute();
        }
        catch (SQLException e) {
            System.out.println("npcids:" + npcids);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}

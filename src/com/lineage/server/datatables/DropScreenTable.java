package com.lineage.server.datatables;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Random;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import com.lineage.server.templates.L1DropScreen;
import java.util.Map;
import org.apache.commons.logging.Log;

public class DropScreenTable
{
    private static final Log _log;
    private final Map<Integer, L1DropScreen> _list;
    private static final ArrayList<Integer> _npcList;
    private static DropScreenTable _instance;
    
    static {
        _log = LogFactory.getLog(DropScreenTable.class);
        _npcList = new ArrayList();
    }
    
    public static DropScreenTable get() {
        if (DropScreenTable._instance == null) {
            DropScreenTable._instance = new DropScreenTable();
        }
        return DropScreenTable._instance;
    }
    
    public static void reload() {
        DropScreenTable oldInstance = DropScreenTable._instance;
        DropScreenTable._instance = new DropScreenTable();
        oldInstance._list.clear();
        oldInstance = null;
    }
    
    private DropScreenTable() {
        this._list = new HashMap();
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `william_droplist_screen`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("id");
                int npc_id = rs.getInt("npc_id");
                int item_id = rs.getInt("item_id");
                int min = rs.getInt("min");
                int max = rs.getInt("max");
                int random = rs.getInt("random");
                int enchant_min = rs.getInt("enchant_min");
                int enchant_max = rs.getInt("enchant_max");
                boolean is_attack = rs.getBoolean("is_attack");
                if (this.check_item(item_id)) {
                    L1DropScreen drop = new L1DropScreen(npc_id, item_id, min, max, random, enchant_min, enchant_max, is_attack);
                    this._list.put(key, drop);
                    if (!DropScreenTable._npcList.contains((Integer)npc_id)) {
                        DropScreenTable._npcList.add(npc_id);
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (SQLException e) {
            DropScreenTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        DropScreenTable._log.info("載入掉落物品資料數量(全畫面掉落): " + this._list.size() + "(" + timer.get() + "ms)");
    }
    
    private boolean check_item(int itemId) {
        L1Item itemTemplate = ItemTable.get().getTemplate(itemId);
        if (itemTemplate == null) {
            DropScreenTable._log.info("掉落物品資料數量(全畫面)錯誤物品->" + itemId);
            return false;
        }
        else {
            return true;
        }
    }
    
    public void runDropScreen(L1MonsterInstance mob, ArrayList<L1Character> acquisitorList) {
        if (this._list.isEmpty() || DropScreenTable._npcList.isEmpty()) {
            return;
        }
        else {
            int npc_id = mob.getNpcId();
            if (!DropScreenTable._npcList.contains((Integer)npc_id)) {
                return;
            }
            else {
                for (Integer key : this._list.keySet()) {
                    L1DropScreen drop = this._list.get(key);
                    if (drop.getMobid() == npc_id) {
                        for (L1PcInstance pc : World.get().getVisiblePlayer(mob)) {
                            if (pc.isDead()) {
                                continue;
                            }
                            else if (drop.is_attack() && !acquisitorList.contains(pc)) {
                                continue;
                            }
                            else {
                                Random _random = new Random();
                                int random = _random.nextInt(1000) + 1;
                                if (drop.getChance() < random) {
                                    continue;
                                }
                                else {
                                    int itemCount = drop.getMin();
                                    int addCount = drop.getMax() - itemCount + 1;
                                    if (addCount > 1) {
                                        itemCount += _random.nextInt(addCount);
                                    }
                                    L1ItemInstance item = pc.getInventory().storeItem(drop.getItemid(), (long)itemCount);
                                    if (pc.isInParty()) {
                                        Object[] pcs = pc.getParty().partyUsers().values().toArray();
                                        if (pcs.length <= 0) {
                                            return;
                                        }
                                        else {
                                            Object[] array;
                                            for (int length = (array = pcs).length, i = 0; i < length; ++i) {
                                                Object obj = array[i];
                                                if (obj instanceof L1PcInstance) {
                                                    L1PcInstance tgpc = (L1PcInstance)obj;
                                                    tgpc.sendPackets(new S_ServerMessage(813, mob.getNameId(), item.getName() + " (" + itemCount + ")", pc.getName()));
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage(143, mob.getNameId(), item.getName() + " (" + itemCount + ")"));
                                    }
                                }
                            }
                        }
                    }
                }
                return;
            }
        }
    }
}

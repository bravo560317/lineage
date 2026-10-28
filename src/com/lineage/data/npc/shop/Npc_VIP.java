package com.lineage.data.npc.shop;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Item;
import java.sql.Timestamp;
import com.lineage.data.event.VIPSet;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import java.text.SimpleDateFormat;
import com.lineage.server.datatables.lock.VIPReading;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_VIP extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_VIP.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_VIP();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        Timestamp time = VIPReading.get().getOther(pc);
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String key = sdf.format(time);
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_v2", new String[] { key }));
        }
        else {
            L1Item item = ItemTable.get().getTemplate(VIPSet.ITEMID);
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_v1", new String[] { String.valueOf(VIPSet.ADENA), item.getNameId(), String.valueOf(VIPSet.DATETIME) }));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        int mapid = -1;
        int x = -1;
        int y = -1;
        int levelup = pc.getLevel();
        if (cmd.equalsIgnoreCase("1")) {
            Timestamp time = VIPReading.get().getOther(pc);
            if (time != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String key = sdf.format(time);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_v2", new String[] { key }));
                return;
            }
            else {
                L1ItemInstance itemT = pc.getInventory().checkItemX(VIPSet.ITEMID, (long)VIPSet.ADENA);
                if (itemT == null) {
                    pc.sendPackets(new S_ServerMessage(337, "貨幣"));
                }
                else {
                    pc.getInventory().removeItem(itemT, (long)VIPSet.ADENA);
                    long timeNow = System.currentTimeMillis();
                    long x2 = (long)(VIPSet.DATETIME * 24 * 60 * 60);
                    long x3 = x2 * 1000L;
                    long upTime = x3 + timeNow;
                    Timestamp value = new Timestamp(upTime);
                    VIPReading.get().storeOther(pc.getId(), value);
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String key2 = sdf2.format(value);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_v2", new String[] { key2 }));
                }
            }
        }
        else if (cmd.equalsIgnoreCase("2")) {
            if (levelup >= 6) {
                mapid = 1002;
                x = 32756;
                y = 32679;
            }
        }
        else if (cmd.equalsIgnoreCase("3")) {
            if (levelup >= 6) {
                mapid = 200;
                x = 32756;
                y = 32942;
            }
        }
        else if (cmd.equalsIgnoreCase("4")) {
            if (levelup >= 3 && levelup <= 5) {
                mapid = 10022;
                x = 32756;
                y = 32679;
            }
        }
        else if (cmd.equalsIgnoreCase("5")) {
            if (levelup >= 3 && levelup <= 5) {
                mapid = 6661;
                x = 32800;
                y = 32751;
            }
        }
        else if (cmd.equalsIgnoreCase("6")) {
            if (levelup >= 0 && levelup <= 2) {
                mapid = 10021;
                x = 32756;
                y = 32679;
            }
        }
        else if (cmd.equalsIgnoreCase("7") && levelup >= 0 && levelup <= 2) {
            mapid = 7781;
            x = 32739;
            y = 32686;
        }
        if (mapid != -1) {
            Timestamp time = VIPReading.get().getOther(pc);
            if (time != null) {
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                if (time.after(ts)) {
                    this.teleport(pc, x, y, mapid);
                }
                else {
                    VIPReading.get().delOther(pc.getId());
                }
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage("等級(" + levelup + ")已經超過限制"));
        }
        pc.sendPackets(new S_CloseList(pc.getId()));
    }
    
    private void teleport(L1PcInstance pc, int x, int y, int mapid) {
        try {
            L1Location location = new L1Location(x, y, mapid);
            L1Location newLocation = location.randomLocation(200, false);
            int newX = newLocation.getX();
            int newY = newLocation.getY();
            short mapId = (short)newLocation.getMapId();
            L1Teleport.teleport(pc, newX, newY, mapId, 5, true);
        }
        catch (Exception e) {
            Npc_VIP._log.error(e.getLocalizedMessage(), e);
        }
    }
}

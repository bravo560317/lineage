package com.lineage.data.event;

import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.timecontroller.event.GamblingTime;
import com.lineage.server.datatables.lock.GamblingReading;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class GamblingSet extends EventExecutor
{
    private static final Log _log;
    public static int GAMADENATIME;
    public static int ADENAITEM;
    public static int GAMADENA;
    public static String GAMADENANAME;
    
    static {
        _log = LogFactory.getLog(GamblingSet.class);
        GamblingSet.ADENAITEM = 40308;
    }
    
    public static EventExecutor get() {
        return new GamblingSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            try {
                GamblingSet.GAMADENATIME = Integer.parseInt(set[0]);
            }
            catch (Exception e) {
                GamblingSet.GAMADENATIME = 30;
                GamblingSet._log.error("未設定每場比賽間隔時間(分鐘)(使用預設30分鐘)");
            }
            try {
                GamblingSet.GAMADENA = Integer.parseInt(set[1]);
            }
            catch (Exception e) {
                GamblingSet.GAMADENA = 5000;
                GamblingSet._log.error("未設定奇岩賭場 下注額(每張賭票售價)(使用預設5000)");
            }
            try {
                GamblingSet.ADENAITEM = Integer.parseInt(set[2]);
                L1Item item = ItemTable.get().getTemplate(GamblingSet.ADENAITEM);
                if (item != null) {
                    GamblingSet.GAMADENANAME = item.getNameId();
                }
            }
            catch (Exception e) {
                GamblingSet.ADENAITEM = 40308;
                GamblingSet.GAMADENANAME = "$4";
                GamblingSet._log.error("未設定奇岩賭場 下注物品編號(使用預設40308)");
            }
            GamblingReading.get().load();
            GamblingTime gamblingTimeController = new GamblingTime();
            gamblingTimeController.start();
            this.spawnDoor();
        }
        catch (Exception e2) {
            GamblingSet._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    private void spawnDoor() {
        int[][] gamDoors = { { 51, 1487, 33521, 32861, 4, 1, 33523, 33523, 0, -1 }, { 52, 1487, 33519, 32863, 4, 1, 33523, 33523, 0, -1 }, { 53, 1487, 33517, 32865, 4, 1, 33523, 33523, 0, -1 }, { 54, 1487, 33515, 32867, 4, 1, 33523, 33523, 0, -1 }, { 55, 1487, 33513, 32869, 4, 1, 33523, 33523, 0, -1 } };
        int[][] array;
        for (int length = (array = gamDoors).length, i = 0; i < length; ++i) {
            int[] doorInfo = array[i];
            L1DoorInstance door = (L1DoorInstance)NpcTable.get().newNpcInstance(81158);
            if (door != null) {
                door.setId(IdFactoryNpc.get().nextId());
                int id = doorInfo[0];
                int gfxid = doorInfo[1];
                int locx = doorInfo[2];
                int locy = doorInfo[3];
                int mapid = doorInfo[4];
                int direction = doorInfo[5];
                int left_edge_location = doorInfo[6];
                int right_edge_location = doorInfo[7];
                int hp = doorInfo[8];
                int keeper = doorInfo[9];
                door.setDoorId(id);
                door.setGfxId(gfxid);
                door.setX(locx);
                door.setY(locy);
                door.setMap((short)mapid);
                door.setHomeX(locx);
                door.setHomeY(locy);
                door.setDirection(direction);
                door.setLeftEdgeLocation(left_edge_location);
                door.setRightEdgeLocation(right_edge_location);
                door.setMaxHp(hp);
                door.setCurrentHp(hp);
                door.setKeeperId(keeper);
                World.get().storeObject(door);
                World.get().addVisibleObject(door);
            }
        }
    }
}

package com.lineage.data.item_etcitem.extra;

import java.util.Iterator;
import com.lineage.server.world.World;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class DRAGON_OF_KEY2 extends ItemExecutor
{
    private int _type;
    private int _time;
    
    private DRAGON_OF_KEY2() {
        this._type = 0;
        this._time = 0;
    }
    
    public static ItemExecutor get() {
        return new DRAGON_OF_KEY2();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        boolean isChecked = false;
        Iterator<?> localIterator1 = ConfigAlt.DRAGON_KEY_MAP_LIST.iterator();
        while (localIterator1.hasNext()) {
            int mapid = (Integer)localIterator1.next();
            if (mapid == pc.getMapId()) {
                isChecked = true;
                break;
            }
        }
        if (!isChecked) {
            pc.sendPackets(new S_ServerMessage(1892));
            return;
        }
        else {
            if (!L1CastleLocation.checkInAllWarArea(pc.getLocation())) {
                int[] dragonGate = { 0, 70932, 70937, 70934, 70933 };
                if (this._type >= 1 && this._type <= 4) {
                    if (!pc.getInventory().consumeItem(item.getItemId(), 1L)) {
                        pc.sendPackets(new S_ServerMessage(1566));
                        return;
                    }
                    else {
                        L1SpawnUtil.spawn(pc, dragonGate[this._type], 0, this._time);
                        World.get().broadcastPacketToAll(new S_ServerMessage(2921));
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(1892));
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        this._type = Integer.parseInt(set[1]);
        this._time = Integer.parseInt(set[2]);
    }
}

package com.lineage.data.item_etcitem.magicreel;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.L1Location;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class FireBadge extends ItemExecutor
{
    private final Random _random;
    
    public FireBadge() {
        this._random = new Random();
    }
    
    public static ItemExecutor get() {
        return new FireBadge();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null) {
            return;
        }
        else if (item == null) {
            return;
        }
        else {
            L1BuffUtil.cancelAbsoluteBarrier(pc);
            if (pc.isInvisble()) {
                pc.delInvis();
            }
            pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 19));
            int chargeCount = item.getChargeCount();
            if (chargeCount <= 0) {
                if (pc.getInventory().removeItem(item, 1L) == 1L) {
                    pc.sendPackets(new S_ServerMessage(154));
                }
            }
            else {
                item.setChargeCount(item.getChargeCount() - 1);
                pc.getInventory().updateItem(item, 128);
                int x = pc.getX();
                int y = pc.getY();
                int mapId = pc.getMapId();
                pc.sendPacketsXR(new S_EffectLocation(new L1Location(x + 1, y + 1, mapId), 1819), 7);
                pc.sendPacketsXR(new S_EffectLocation(new L1Location(x - 1, y + 1, mapId), 1819), 7);
                pc.sendPacketsXR(new S_EffectLocation(new L1Location(x - 1, y - 1, mapId), 1819), 7);
                pc.sendPacketsXR(new S_EffectLocation(new L1Location(x + 1, y - 1, mapId), 1819), 7);
                L1PcInstance tgpc = (L1PcInstance)pc.getNowTarget();
                if (tgpc != null && !tgpc.isSafetyZone()) {
                    double dmg = (double)(this._random.nextInt(60) + 160) * 1.0;
                    tgpc.receiveDamage(pc, dmg, false, false);
                    for (L1PcInstance tgClanPc : World.get().getVisiblePlayer(tgpc, 4)) {
                        if (tgClanPc.getClanid() == tgpc.getClanid() && !tgpc.isSafetyZone()) {
                            tgClanPc.receiveDamage(pc, dmg * 0.8, false, false);
                            tgClanPc.broadcastPacketX8(new S_DoActionGFX(tgClanPc.getId(), 2));
                        }
                    }
                }
                for (L1Object object : World.get().getVisibleObjects(pc, 5)) {
                    if (object instanceof L1MonsterInstance) {
                        L1MonsterInstance mob = (L1MonsterInstance)object;
                        int dmg2 = this._random.nextInt(60) + 160;
                        mob.receiveDamage(pc, dmg2);
                        mob.broadcastPacketX8(new S_DoActionGFX(mob.getId(), 2));
                    }
                }
            }
            return;
        }
    }
}

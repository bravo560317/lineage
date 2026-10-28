package com.lineage.data.item_etcitem.wand;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.L1Location;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class FireWand extends ItemExecutor
{
    public static ItemExecutor get() {
        return new FireWand();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getMapId() < 2101) {
            L1ItemInstance[] itemlist = pc.getInventory().findItemsId(92122);
            for (int i = 0; i < itemlist.length; ++i) {
                pc.getInventory().removeItem(itemlist[i]);
            }
            itemlist = pc.getInventory().findItemsId(92123);
            for (int i = 0; i < itemlist.length; ++i) {
                pc.getInventory().removeItem(itemlist[i]);
            }
            return;
        }
        else if (pc.getMapId() > 2151) {
            L1ItemInstance[] itemlist = pc.getInventory().findItemsId(92122);
            for (int i = 0; i < itemlist.length; ++i) {
                pc.getInventory().removeItem(itemlist[i]);
            }
            itemlist = pc.getInventory().findItemsId(92123);
            for (int i = 0; i < itemlist.length; ++i) {
                pc.getInventory().removeItem(itemlist[i]);
            }
            return;
        }
        else {
            int spellsc_objid = data[0];
            int spellsc_x = data[1];
            int spellsc_y = data[2];
            L1Object target = World.get().findObject(spellsc_objid);
            if (item.getChargeCount() == 0) {
                pc.getInventory().deleteItem(item);
                return;
            }
            else {
                if (target != null) {
                    this.doWandAction(pc, target);
                }
                else {
                    pc.sendPacketsXR(new S_EffectLocation(new L1Location(spellsc_x, spellsc_y, (int)pc.getMapId()), 762), 7);
                }
                pc.sendPacketsX8(new S_DoActionGFX(pc.getId(), 17));
                int useCount = item.getChargeCount() - 1;
                if (useCount > 0) {
                    item.setChargeCount(useCount);
                    pc.getInventory().updateItem(item, 128);
                }
                else {
                    pc.getInventory().deleteItem(item);
                }
                return;
            }
        }
    }
    
    private void doWandAction(L1PcInstance user, L1Object target) {
        if (user.getId() == target.getId()) {
            return;
        }
        else if (!user.glanceCheck(target.getX(), target.getY())) {
            return;
        }
        else {
            ArrayList<L1Object> list = World.get().getVisibleObjects(target, 3);
            if (list == null) {
                return;
            }
            else {
                for (L1Object object : list) {
                    if (object instanceof L1MonsterInstance) {
                        L1MonsterInstance dota = (L1MonsterInstance)object;
                        dota.broadcastPacketAll(new S_DoActionGFX(dota.getId(), 2));
                        dota.receiveDamage(user, 400);
                    }
                }
                if (target instanceof L1MonsterInstance) {
                    L1MonsterInstance mob = (L1MonsterInstance)target;
                    mob.broadcastPacketAll(new S_DoActionGFX(target.getId(), 2));
                    mob.receiveDamage(user, 400);
                    user.sendPacketsXR(new S_EffectLocation(new L1Location(mob.getX(), mob.getY(), (int)user.getMapId()), 762), 7);
                }
                user.setHeading(user.targetDirection(target.getX(), target.getY()));
                user.sendPacketsX10(new S_ChangeHeading(user));
                return;
            }
        }
    }
}

package com.lineage.server.model.Instance;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1HauntedHouse;
import com.lineage.server.serverpackets.S_NPCPack_F;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1FieldObjectInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1FieldObjectInstance.class);
    }
    
    public L1FieldObjectInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            perceivedFrom.addKnownObject(this);
            perceivedFrom.sendPackets(new S_NPCPack_F(this));
        }
        catch (Exception e) {
            L1FieldObjectInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        if (this.getNpcTemplate().get_npcId() == 81171 && L1HauntedHouse.getInstance().getHauntedHouseStatus() == 2) {
            int winnersCount = L1HauntedHouse.getInstance().getWinnersCount();
            int goalCount = L1HauntedHouse.getInstance().getGoalCount();
            if (winnersCount == goalCount + 1) {
                L1ItemInstance item = ItemTable.get().createItem(49280);
                int count = 1;
                if (item != null && pc.getInventory().checkAddItem(item, (long)count) == 0) {
                    item.setCount((long)count);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                }
                L1HauntedHouse.getInstance().endHauntedHouse();
            }
            else if (winnersCount > goalCount + 1) {
                L1HauntedHouse.getInstance().setGoalCount(goalCount + 1);
                L1HauntedHouse.getInstance().removeMember(pc);
                L1ItemInstance item = null;
                if (winnersCount == 3) {
                    if (goalCount == 1) {
                        item = ItemTable.get().createItem(49278);
                    }
                    else if (goalCount == 2) {
                        item = ItemTable.get().createItem(49279);
                    }
                }
                else if (winnersCount == 2) {
                    item = ItemTable.get().createItem(49279);
                }
                int count = 1;
                if (item != null && pc.getInventory().checkAddItem(item, (long)count) == 0) {
                    item.setCount((long)count);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                }
                L1SkillUse l1skilluse = new L1SkillUse();
                l1skilluse.handleCommands(pc, 44, pc.getId(), pc.getX(), pc.getY(), 0, 1);
                L1Teleport.teleport(pc, 32624, 32813, (short)4, 5, true);
            }
        }
    }
    
    @Override
    public void deleteMe() {
        try {
            this._destroyed = true;
            if (this.getInventory() != null) {
                this.getInventory().clearItems();
            }
            World.get().removeVisibleObject(this);
            World.get().removeObject(this);
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                pc.removeKnownObject(this);
                pc.sendPackets(new S_RemoveObject(this));
            }
            this.removeAllKnownObjects();
        }
        catch (Exception e) {
            L1FieldObjectInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
}

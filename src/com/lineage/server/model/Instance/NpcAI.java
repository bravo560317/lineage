package com.lineage.server.model.Instance;

import com.lineage.server.model.L1Inventory;
import com.lineage.server.world.World;
import com.lineage.server.thread.NpcAiThreadPool;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class NpcAI implements Runnable
{
    private static final Log _log;
    protected final L1NpcInstance _npc;
    
    static {
        _log = LogFactory.getLog(NpcAI.class);
    }
    
    public NpcAI(L1NpcInstance npc) {
        this._npc = npc;
    }
    
    public void startAI() {
        NpcAiThreadPool.get().execute(this);
    }
    
    @Override
    public void run() {
        try {
            while (this._npc.getMaxHp() > 0) {
                while (this._npc.isParalyzed() || this._npc.isSleeped()) {
                    Thread.sleep(200L);
                }
                if (this.AIProcess()) {
                    break;
                }
                else {
                    try {
                        Thread.sleep((long)this._npc.getSleepTime());
                    }
                    catch (Exception e) {
                        break;
                    }
                }
            }
            this._npc.mobSkill().resetAllSkillUseCount();
            do {
                try {
                    Thread.sleep((long)this._npc.getSleepTime());
                }
                catch (Exception e) {
                    break;
                }
            } while (this._npc.isDeathProcessing());
            this._npc.allTargetClear();
            this._npc.setAiRunning(false);
            Thread.sleep(10L);
        }
        catch (Exception e) {
            NpcAI._log.error("NpcAI發生例外狀況: " + this._npc.getName(), e);
        }
    }
    
    protected boolean AIProcess() {
        try {
            if (this._npc.isDead()) {
                return true;
            }
            else if (this._npc.destroyed()) {
                return true;
            }
            else if (this._npc.getCurrentHp() <= 0) {
                return true;
            }
            else if (this._npc.getHiddenStatus() != 0) {
                return true;
            }
            else {
                this._npc.checkTarget();
                boolean searchTarget = true;
                if (this._npc.is_now_target() != null) {
                    searchTarget = false;
                }
                if (this._npc.getMaster() != null && searchTarget) {
                    searchTarget = false;
                }
                if (searchTarget) {
                    this._npc.searchTarget();
                }
                this._npc.onItemUse();
                if (this._npc.is_now_target() == null) {
                    this._npc.checkTargetItem();
                    if (this._npc.isPickupItem() && this._npc.is_now_targetItem() == null) {
                        this._npc.searchTargetItem();
                    }
                    if (this._npc.is_now_targetItem() == null) {
                        boolean noTarget = this._npc.noTarget();
                        if (noTarget) {
                            return true;
                        }
                    }
                    else {
                        L1Inventory groundInventory = World.get().getInventory(this._npc.is_now_targetItem().getX(), this._npc.is_now_targetItem().getY(), this._npc.is_now_targetItem().getMapId());
                        if (groundInventory.checkItem(this._npc.is_now_targetItem().getItemId())) {
                            this._npc.onTargetItem();
                        }
                        else {
                            this._npc._targetItemList.remove(this._npc.is_now_targetItem());
                            this._npc.set_now_targetItem(null);
                            return false;
                        }
                    }
                }
                else if (this._npc.getHiddenStatus() == 0) {
                    this._npc.onTarget();
                }
                else {
                    return true;
                }
                Thread.sleep(10L);
            }
        }
        catch (Exception e) {
            NpcAI._log.error("NpcAI發生例外狀況: " + this._npc.getName(), e);
        }
        return false;
    }
}

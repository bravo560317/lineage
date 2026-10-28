package com.lineage.server.model.Instance;

import java.util.Iterator;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Npc;

public class L1DotaInstance extends L1MonsterInstance
{
    public L1DotaInstance(L1Npc template) {
        super(template);
        this.set_storeDroped(false);
        this.onNpcAI();
    }
    
    @Override
    public void searchTarget() {
        for (L1Object object : World.get().getVisibleObjects(this, 1000)) {
            if (object instanceof L1NpcInstance) {
                L1NpcInstance npc = (L1NpcInstance)object;
                if (npc.getNpcId() == 95128) {
                    if (this.getCurrentHp() > 500) {
                        this._hateList.add(npc, 0);
                    }
                    else {
                        this._hateList.add(npc, this.getCurrentHp());
                    }
                    this._target = npc;
                }
                else {
                    continue;
                }
            }
        }
    }
}

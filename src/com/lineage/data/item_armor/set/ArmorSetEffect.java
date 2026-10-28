package com.lineage.data.item_armor.set;

import com.lineage.server.model.Instance.L1PcInstance;

public interface ArmorSetEffect
{
    public abstract void giveEffect(L1PcInstance p0);
    
    public abstract void cancelEffect(L1PcInstance p0);
    
    public abstract int get_mode();
}

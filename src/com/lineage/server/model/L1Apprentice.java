package com.lineage.server.model;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public final class L1Apprentice
{
    private static final int MAX_SIZE = 4;
    private final ArrayList<L1PcInstance> totalList;
    private L1PcInstance _master;
    
    public L1Apprentice(L1PcInstance master, L1PcInstance[] mentorList) {
        this._master = master;
        this.totalList = new ArrayList(4);
        for (L1PcInstance apprentice : mentorList) {
            this.totalList.add(apprentice);
        }
    }
    
    public final L1PcInstance getMaster() {
        return this._master;
    }
    
    public final void setMaster(L1PcInstance master) {
        this._master = master;
    }
    
    public final ArrayList<L1PcInstance> getTotalList() {
        return this.totalList;
    }
    
    public final boolean addApprentice(L1PcInstance l1char) {
        return this.checkSize() && this.totalList.add(l1char);
    }
    
    public final boolean checkSize() {
        return this.totalList.size() < 4;
    }
}

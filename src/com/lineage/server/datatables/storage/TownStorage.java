package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Town;

public interface TownStorage
{
    public abstract void load();
    
    public abstract L1Town[] getTownTableList();
    
    public abstract L1Town getTownTable(int p0);
    
    public abstract boolean isLeader(L1PcInstance p0, int p1);
    
    public abstract void addSalesMoney(int p0, int p1);
    
    public abstract void updateTaxRate();
    
    public abstract void updateSalesMoneyYesterday();
    
    public abstract String totalContribution(int p0);
    
    public abstract void clearHomeTownID();
    
    public abstract int getPay(int p0);
}

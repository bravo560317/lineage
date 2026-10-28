package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1UserSkillTmp;
import java.util.ArrayList;

public interface CharSkillStorage
{
    public abstract void load();
    
    public abstract ArrayList<L1UserSkillTmp> skills(int p0);
    
    public abstract void spellMastery(int p0, int p1, String p2, int p3, int p4);
    
    public abstract void spellLost(int p0, int p1);
    
    public abstract boolean spellCheck(int p0, int p1);
    
    public abstract void setAuto(int p0, int p1, int p2);
}

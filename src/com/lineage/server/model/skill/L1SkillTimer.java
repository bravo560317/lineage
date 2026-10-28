package com.lineage.server.model.skill;

public interface L1SkillTimer
{
    public abstract int getRemainingTime();
    
    public abstract void begin();
    
    public abstract void end();
    
    public abstract void kill();
    
    public abstract void setRemainingTime(int p0);
}

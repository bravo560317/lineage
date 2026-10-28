package com.lineage.data.executor;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public abstract class NpcExecutor
{
    public abstract int type();
    
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
    }
    
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
    }
    
    public void attack(L1PcInstance pc, L1NpcInstance npc) {
    }
    
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        return null;
    }
    
    public int workTime() {
        return 0;
    }
    
    public void work(L1NpcInstance npc) {
    }
    
    public void spawn(L1NpcInstance npc) {
    }
    
    public String[] get_set() {
        return null;
    }
    
    public void set_set(String[] set) {
    }
}

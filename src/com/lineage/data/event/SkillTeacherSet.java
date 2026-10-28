package com.lineage.data.event;

import com.lineage.server.templates.L1Event;
import java.util.HashMap;
import java.util.Map;
import com.lineage.data.executor.EventExecutor;

public class SkillTeacherSet extends EventExecutor
{
    public static final Map<Integer, Integer> RESKILLLIST;
    
    static {
        RESKILLLIST = new HashMap();
    }
    
    public static EventExecutor get() {
        return new SkillTeacherSet();
    }
    
    @Override
    public void execute(L1Event event) {
        String[] set = event.get_eventother().split(",");
        String[] array;
        for (int length = (array = set).length, i = 0; i < length; ++i) {
            String string = array[i];
            SkillTeacherSet.RESKILLLIST.put((Integer)(Integer.parseInt(string) - 1), Integer.parseInt(string));
        }
    }
}

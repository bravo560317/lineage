package com.lineage.server.model.drop;

import com.lineage.server.model.L1Character;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1NpcInstance;

public interface DropShareExecutor
{
    public abstract void dropShare(L1NpcInstance p0, ArrayList<L1Character> p1, ArrayList<Integer> p2);
}

package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1BookMark;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;

public interface CharBookStorage
{
    public abstract void load();
    
    public abstract ArrayList<L1BookMark> getBookMarks(L1PcInstance p0);
    
    public abstract L1BookMark getBookMark(L1PcInstance p0, int p1);
    
    public abstract void deleteBookmark(L1PcInstance p0, String p1);
    
    public abstract void addBookmark(L1PcInstance p0, String p1);
    
    public abstract void updateBookmarkName(L1BookMark p0);
}

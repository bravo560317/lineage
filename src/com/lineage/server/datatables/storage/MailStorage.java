package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1Mail;
import java.util.Map;
import com.lineage.server.model.Instance.L1PcInstance;

public interface MailStorage
{
    public abstract void load();
    
    public abstract void setReadStatus(int p0);
    
    public abstract void setMailType(int p0, int p1);
    
    public abstract void deleteMail(int p0);
    
    public abstract void writeMail(int p0, String p1, L1PcInstance p2, String p3, String p4, int p5);
    
    public abstract Map<Integer, L1Mail> getAllMail();
    
    public abstract L1Mail getMail(int p0);
}

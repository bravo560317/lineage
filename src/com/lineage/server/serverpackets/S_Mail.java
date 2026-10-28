package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.templates.L1Mail;
import com.lineage.server.datatables.sql.MailTable;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_Mail extends ServerBasePacket
{
    private static final String S_MAIL = "[S] S_Mail";
    private byte[] _byte;
    
    public S_Mail(L1PcInstance pc, int type) {
        this._byte = null;
        ArrayList<L1Mail> mails = new ArrayList();
        for (L1Mail mail : MailTable.get().getAllMail()) {
            if (mail.getInBoxId() == pc.getId() && mail.getType() == type) {
                mails.add(mail);
            }
        }
        if (mails.isEmpty()) {
            return;
        }
        else {
            this.writeC(186);
            this.writeC(type);
            this.writeH(mails.size());
            for (int i = 0; i < mails.size(); ++i) {
                L1Mail mail2 = mails.get(i);
                this.writeD(mail2.getId());
                this.writeC(mail2.getReadStatus());
                this.writeD((int)(mail2.getDate().getTime() / 1000L));
                this.writeC(mail2.getSenderName().equalsIgnoreCase(pc.getName()) ? 1 : 0);
                this.writeS(mail2.getSenderName().equalsIgnoreCase(pc.getName()) ? mail2.getReceiverName() : mail2.getSenderName());
                this.writeByte(mail2.getSubject());
            }
            return;
        }
    }
    
    public S_Mail(L1PcInstance pc, int mailId, boolean isDraft) {
        this._byte = null;
        L1Mail mail = MailTable.get().getMail(mailId);
        this.writeC(186);
        this.writeC(80);
        this.writeD(mailId);
        this.writeC(isDraft ? 1 : 0);
        this.writeS(pc.getName());
        this.writeByte(mail.getSubject());
    }
    
    public S_Mail(int type, boolean isDelivered) {
        this._byte = null;
        this.writeC(186);
        this.writeC(type);
        this.writeC(isDelivered ? 1 : 0);
    }
    
    public S_Mail(int mailId, int type) {
        this._byte = null;
        switch (type) {
            case 48:
            case 49:
            case 50:
            case 64: {
                this.writeC(186);
                this.writeC(type);
                this.writeD(mailId);
                this.writeC(1);
                return;
            }
            default: {
                L1Mail mail = MailTable.get().getMail(mailId);
                if (mail != null) {
                    this.writeC(186);
                    this.writeC(type);
                    this.writeD(mail.getId());
                    this.writeByte(mail.getContent());
                }
                return;
            }
        }
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return "[S] S_Mail";
    }
}

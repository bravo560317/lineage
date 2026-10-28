package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.list.Announcements;

public class S_CommonNews extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CommonNews() {
        this._byte = null;
        ArrayList<String> info = Announcements.get().list();
        this.writeC(48);
        StringBuilder messagePack = new StringBuilder();
        for (String message : info) {
            messagePack.append(message + "\n");
        }
        this.writeS(messagePack.toString());
    }
    
    public S_CommonNews(String s) {
        this._byte = null;
        this.writeC(48);
        this.writeS(s);
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
        return this.getClass().getSimpleName();
    }
}

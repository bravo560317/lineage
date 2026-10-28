package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.templates.L1BookMark;
import java.util.List;

public class S_Bookmarks extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Bookmarks(String name, int map, int id) {
        this._byte = null;
        this.buildPacket(name, map, id);
    }
    
    private void buildPacket(String name, int map, int id) {
        this.writeC(92);
        this.writeS(name);
        this.writeH(map);
        this.writeD(id);
    }
    
    public S_Bookmarks(byte[] data, int maxSize, List<L1BookMark> bookList) {
        this._byte = null;
        this.writeC(64);
        this.writeC(42);
        if (data != null) {
            this.writeH(data.length);
            for (int value : data) {
                this.writeC(value);
            }
        }
        else {
            this.writeH(128);
            for (int i = 0; i < 128; ++i) {
                this.writeC(0);
            }
        }
        this.writeH(maxSize);
        this.writeH(bookList.size());
        for (L1BookMark book : bookList) {
            this.writeD(book.getId());
            this.writeS(book.getName());
            this.writeH((int)book.getMapId());
            this.writeD(book.getId());
        }
    }
    
    public S_Bookmarks(int value) {
        this._byte = null;
        this.writeC(250);
        this.writeC(141);
        this.writeC(value);
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

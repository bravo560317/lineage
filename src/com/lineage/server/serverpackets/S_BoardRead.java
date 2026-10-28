package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1Board;
import com.lineage.server.datatables.lock.BoardReading;

public class S_BoardRead extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_BoardRead(int number) {
        this._byte = null;
        this.buildPacket(number);
    }
    
    private void buildPacket(int number) {
        L1Board board = BoardReading.get().getBoardTable(number);
        this.writeC(148);
        this.writeD(board.get_id());
        this.writeS(board.get_name());
        this.writeS(board.get_date());
        this.writeS(board.get_title());
        this.writeS(board.get_content());
    }
    
    public S_BoardRead() {
        this._byte = null;
        this.writeC(148);
        this.writeD(10);
        this.writeS("測試NAME");
        this.writeS("2010-02-02");
        this.writeS("測試TITLE");
        this.writeS("測試內容");
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

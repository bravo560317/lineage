package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1Board;
import com.lineage.server.datatables.lock.BoardReading;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1NpcInstance;

public class S_Board extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Board(L1NpcInstance npc) {
        this._byte = null;
        this.buildPacket(npc, 0);
    }
    
    public S_Board(L1NpcInstance npc, int number) {
        this._byte = null;
        this.buildPacket(npc, number);
    }
    
    private void buildPacket(L1NpcInstance npc, int number) {
        int count = 0;
        ArrayList<L1Board> showList = new ArrayList();
        for (int maxid = BoardReading.get().getMaxId(); count < 8 && maxid > 0; ++count) {
            L1Board boardInfo = BoardReading.get().getBoardTable(maxid--);
            if (boardInfo != null && (boardInfo.get_id() <= number || number == 0)) {
                showList.add(count, boardInfo);
            }
        }
        this.writeC(68);
        this.writeC(0);
        this.writeD(npc.getId());
        this.writeC(255);
        this.writeC(255);
        this.writeC(255);
        this.writeC(127);
        this.writeH(showList.size());
        this.writeH(300);
        for (int i = 0; i < showList.size(); ++i) {
            L1Board boardInfo2 = showList.get(i);
            if (boardInfo2 != null) {
                this.writeD(boardInfo2.get_id());
                this.writeS(boardInfo2.get_name());
                this.writeS(boardInfo2.get_date());
                this.writeS(boardInfo2.get_title());
            }
        }
    }
    
    public S_Board(int objectid) {
        this._byte = null;
        this.writeC(68);
        this.writeC(0);
        this.writeD(objectid);
        this.writeC(255);
        this.writeC(255);
        this.writeC(255);
        this.writeC(127);
        this.writeH(2);
        this.writeH(300);
        this.writeD(2);
        this.writeS("佈告欄列表");
        this.writeS("2010-2-2");
        this.writeS("佈告欄列表TITLE");
        this.writeD(1);
        this.writeS("佈告欄列表2");
        this.writeS("2010-2-3");
        this.writeS("佈告欄列表TITLE2");
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

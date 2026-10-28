package com.lineage.server.serverpackets;

import java.util.Calendar;
import com.lineage.server.templates.L1AuctionBoardTmp;
import com.lineage.server.datatables.lock.AuctionBoardReading;

public class S_AuctionBoardRead extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_AuctionBoardRead(int objectId, String house_number) {
        this._byte = null;
        this.buildPacket(objectId, house_number);
    }
    
    private void buildPacket(int objectId, String house_number) {
        int number = Integer.valueOf(house_number);
        L1AuctionBoardTmp board = AuctionBoardReading.get().getAuctionBoardTable(number);
        this.writeC(39);
        this.writeD(objectId);
        this.writeS("agsel");
        this.writeS(house_number);
        this.writeH(9);
        this.writeS(board.getHouseName());
        this.writeS(board.getLocation() + "$1195");
        this.writeS(String.valueOf(board.getHouseArea()));
        this.writeS(board.getOldOwner());
        this.writeS(board.getBidder());
        this.writeS(String.valueOf(board.getPrice()));
        Calendar cal = board.getDeadline();
        int month = cal.get(2) + 1;
        int day = cal.get(5);
        int hour = cal.get(11);
        this.writeS(String.valueOf(month));
        this.writeS(String.valueOf(day));
        this.writeS(String.valueOf(hour));
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

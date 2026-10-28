package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1AuctionBoardTmp;
import com.lineage.server.datatables.lock.AuctionBoardReading;

public class S_ApplyAuction extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ApplyAuction(int objectId, String houseNumber) {
        this._byte = null;
        this.buildPacket(objectId, houseNumber);
    }
    
    private void buildPacket(int objectId, String houseNumber) {
        int number = Integer.valueOf(houseNumber);
        L1AuctionBoardTmp board = AuctionBoardReading.get().getAuctionBoardTable(number);
        this.writeC(136);
        this.writeD(objectId);
        this.writeD(0);
        if (board.getBidderId() == 0) {
            this.writeD((int)board.getPrice());
            this.writeD((int)board.getPrice());
        }
        else {
            this.writeD((int)board.getPrice() + 1);
            this.writeD((int)board.getPrice() + 1);
        }
        this.writeD(2000000000);
        this.writeH(0);
        this.writeS("agapply");
        this.writeS("agapply " + houseNumber);
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

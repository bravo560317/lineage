package com.lineage.server.serverpackets;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.templates.L1AuctionBoardTmp;
import com.lineage.server.datatables.lock.AuctionBoardReading;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1NpcInstance;

public class S_AuctionBoard extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_AuctionBoard(L1NpcInstance board) {
        this._byte = null;
        this.buildPacket(board);
    }
    
    private void buildPacket(L1NpcInstance board) {
        ArrayList<L1AuctionBoardTmp> houseListX = new ArrayList();
        Collection<L1AuctionBoardTmp> boardList = (Collection)AuctionBoardReading.get().getAuctionBoardTableList().values();
        for (L1AuctionBoardTmp boardX : boardList) {
            int houseId = boardX.getHouseId();
            if (board.getX() == 33421 && board.getY() == 32823) {
                if (houseId >= 262145 && houseId <= 262189) {
                    houseListX.add(boardX);
                }
                else {
                    continue;
                }
            }
            else if (board.getX() == 33585 && board.getY() == 33235) {
                if (houseId >= 327681 && houseId <= 327691) {
                    houseListX.add(boardX);
                }
                else {
                    continue;
                }
            }
            else if (board.getX() == 33959 && board.getY() == 33253) {
                if (houseId >= 458753 && houseId <= 458819) {
                    houseListX.add(boardX);
                }
                else {
                    continue;
                }
            }
            else if (board.getX() == 32611 && board.getY() == 32775 && houseId >= 524289 && houseId <= 524294) {
                houseListX.add(boardX);
            }
            else {
                continue;
            }
        }
        this.writeC(156);
        this.writeD(board.getId());
        this.writeH(houseListX.size());
        for (L1AuctionBoardTmp boardX : houseListX) {
            this.writeD(boardX.getHouseId());
            this.writeS(boardX.getHouseName());
            this.writeH(boardX.getHouseArea());
            Calendar cal = boardX.getDeadline();
            this.writeC(cal.get(2) + 1);
            this.writeC(cal.get(5));
            this.writeD((int)boardX.getPrice());
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
        return this.getClass().getSimpleName();
    }
}

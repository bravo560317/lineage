package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1AuctionBoardTmp;
import java.util.Map;

public interface AuctionBoardStorage
{
    public abstract void load();
    
    public abstract Map<Integer, L1AuctionBoardTmp> getAuctionBoardTableList();
    
    public abstract L1AuctionBoardTmp getAuctionBoardTable(int p0);
    
    public abstract void insertAuctionBoard(L1AuctionBoardTmp p0);
    
    public abstract void updateAuctionBoard(L1AuctionBoardTmp p0);
    
    public abstract void deleteAuctionBoard(int p0);
}

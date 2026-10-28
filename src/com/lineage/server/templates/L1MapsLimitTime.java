package com.lineage.server.templates;

import java.util.List;

public final class L1MapsLimitTime
{
    private final int _orderId;
    private final List<Integer> _mapList;
    private final String _mapName;
    private final int _limitTime;
    private final int _outMapX;
    private final int _outMapY;
    private final short _outMapId;
    
    public L1MapsLimitTime(int orderId, List<Integer> mapList, String mapName, int limitTime, int outMapX, int outMapY, short outMapId) {
        this._orderId = orderId;
        this._mapList = mapList;
        this._mapName = mapName;
        this._limitTime = limitTime;
        this._outMapX = outMapX;
        this._outMapY = outMapY;
        this._outMapId = outMapId;
    }
    
    public final int getOrderId() {
        return this._orderId;
    }
    
    public final List<Integer> getMapList() {
        return this._mapList;
    }
    
    public final String getMapName() {
        return this._mapName;
    }
    
    public final int getLimitTime() {
        return this._limitTime;
    }
    
    public final int getOutMapX() {
        return this._outMapX;
    }
    
    public final int getOutMapY() {
        return this._outMapY;
    }
    
    public final short getOutMapId() {
        return this._outMapId;
    }
}

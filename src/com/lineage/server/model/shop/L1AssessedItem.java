package com.lineage.server.model.shop;

public class L1AssessedItem
{
    private final int _targetId;
    private final int _assessedPrice;
    private final String _targetName;
    
    L1AssessedItem(int targetId, int assessedPrice, String targetName) {
        this._targetId = targetId;
        this._assessedPrice = assessedPrice;
        this._targetName = targetName;
    }
    
    public int getTargetId() {
        return this._targetId;
    }
    
    public String getTargetName() {
        return this._targetName;
    }
    
    public int getAssessedPrice() {
        return this._assessedPrice;
    }
}

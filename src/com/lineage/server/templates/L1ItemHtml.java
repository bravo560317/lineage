package com.lineage.server.templates;

public class L1ItemHtml
{
    private int _itemid;
    private int _questid;
    private int _queststep;
    private String _html;
    
    public int getItemId() {
        return this._itemid;
    }
    
    public void setItemId(int i) {
        this._itemid = i;
    }
    
    public int getQuestId() {
        return this._questid;
    }
    
    public void setQuestId(int i) {
        this._questid = i;
    }
    
    public int getQuestStep() {
        return this._queststep;
    }
    
    public void setQuestStep(int i) {
        this._queststep = i;
    }
    
    public String getHtml() {
        return this._html;
    }
    
    public void setHtml(String s) {
        this._html = s;
    }
}

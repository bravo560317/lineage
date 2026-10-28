package com.add;

import java.util.Calendar;

public class L1SystemMessage
{
    private int _id;
    private String _message;
    private Calendar _resetmaptime;
    
    public L1SystemMessage(int id, String message, Calendar resetmaptime) {
        this._id = id;
        this._message = message;
        this._resetmaptime = resetmaptime;
    }
    
    public int getId() {
        return this._id;
    }
    
    public String getMessage() {
        return this._message;
    }
    
    public Calendar get_resetmaptime() {
        return this._resetmaptime;
    }
}

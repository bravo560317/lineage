package com.lineage.server.templates;

import java.sql.Timestamp;

public class L1Mail
{
    private int _id;
    private int _type;
    private String _senderName;
    private String _receiverName;
    private Timestamp _date;
    private int _readStatus;
    private byte[] _subject;
    private byte[] _content;
    private int _inBoxId;
    
    public L1Mail() {
        this._date = null;
        this._readStatus = 0;
        this._subject = null;
        this._content = null;
        this._inBoxId = 0;
    }
    
    public int getId() {
        return this._id;
    }
    
    public void setId(int i) {
        this._id = i;
    }
    
    public int getType() {
        return this._type;
    }
    
    public void setType(int i) {
        this._type = i;
    }
    
    public String getSenderName() {
        return this._senderName;
    }
    
    public void setSenderName(String s) {
        this._senderName = s;
    }
    
    public String getReceiverName() {
        return this._receiverName;
    }
    
    public void setReceiverName(String s) {
        this._receiverName = s;
    }
    
    public Timestamp getDate() {
        return this._date;
    }
    
    public void setDate(Timestamp s) {
        this._date = s;
    }
    
    public int getReadStatus() {
        return this._readStatus;
    }
    
    public void setReadStatus(int i) {
        this._readStatus = i;
    }
    
    public byte[] getSubject() {
        return this._subject;
    }
    
    public void setSubject(byte[] arg) {
        this._subject = arg;
    }
    
    public byte[] getContent() {
        return this._content;
    }
    
    public void setContent(byte[] arg) {
        this._content = arg;
    }
    
    public int getInBoxId() {
        return this._inBoxId;
    }
    
    public void setInBoxId(int i) {
        this._inBoxId = i;
    }
}

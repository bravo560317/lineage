package com.lineage.server.templates;

public class L1NpcChat
{
    private int _npcId;
    private int _chatTiming;
    private int _startDelayTime;
    private String _chatId1;
    private String _chatId2;
    private String _chatId3;
    private String _chatId4;
    private String _chatId5;
    private int _chatInterval;
    private boolean _isShout;
    private boolean _isWorldChat;
    private boolean _isRepeat;
    private int _repeatInterval;
    private int _gameTime;
    
    public int getNpcId() {
        return this._npcId;
    }
    
    public void setNpcId(int i) {
        this._npcId = i;
    }
    
    public int getChatTiming() {
        return this._chatTiming;
    }
    
    public void setChatTiming(int i) {
        this._chatTiming = i;
    }
    
    public int getStartDelayTime() {
        return this._startDelayTime;
    }
    
    public void setStartDelayTime(int i) {
        this._startDelayTime = i;
    }
    
    public String getChatId1() {
        return this._chatId1;
    }
    
    public void setChatId1(String s) {
        this._chatId1 = s;
    }
    
    public String getChatId2() {
        return this._chatId2;
    }
    
    public void setChatId2(String s) {
        this._chatId2 = s;
    }
    
    public String getChatId3() {
        return this._chatId3;
    }
    
    public void setChatId3(String s) {
        this._chatId3 = s;
    }
    
    public String getChatId4() {
        return this._chatId4;
    }
    
    public void setChatId4(String s) {
        this._chatId4 = s;
    }
    
    public String getChatId5() {
        return this._chatId5;
    }
    
    public void setChatId5(String s) {
        this._chatId5 = s;
    }
    
    public int getChatInterval() {
        return this._chatInterval;
    }
    
    public void setChatInterval(int i) {
        this._chatInterval = i;
    }
    
    public boolean isShout() {
        return this._isShout;
    }
    
    public void setShout(boolean flag) {
        this._isShout = flag;
    }
    
    public boolean isWorldChat() {
        return this._isWorldChat;
    }
    
    public void setWorldChat(boolean flag) {
        this._isWorldChat = flag;
    }
    
    public boolean isRepeat() {
        return this._isRepeat;
    }
    
    public void setRepeat(boolean flag) {
        this._isRepeat = flag;
    }
    
    public int getRepeatInterval() {
        return this._repeatInterval;
    }
    
    public void setRepeatInterval(int i) {
        this._repeatInterval = i;
    }
    
    public int getGameTime() {
        return this._gameTime;
    }
    
    public void setGameTime(int i) {
        this._gameTime = i;
    }
}

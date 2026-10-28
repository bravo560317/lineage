package com.lineage.william;

import java.util.logging.Logger;

public class L1WilliamSystemMessage
{
    private static Logger _log;
    private int _id;
    private String _message;
    
    static {
        L1WilliamSystemMessage._log = Logger.getLogger(L1WilliamSystemMessage.class.getName());
    }
    
    public L1WilliamSystemMessage(int id, String message) {
        this._id = id;
        this._message = message;
    }
    
    public int getId() {
        return this._id;
    }
    
    public String getMessage() {
        return this._message;
    }
    
    public static String ShowMessage(int id) {
        L1WilliamSystemMessage System_Message = SystemMessage.getInstance().getTemplate(id);
        if (System_Message == null) {
            return "";
        }
        else {
            String Message = System_Message.getMessage();
            return Message;
        }
    }
}

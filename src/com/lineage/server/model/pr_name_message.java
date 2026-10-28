package com.lineage.server.model;

public class pr_name_message
{
    private int _id;
    private String _message;
    
    public pr_name_message(int id, String message) {
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
        pr_name_message System_Message = pr_nametable.getInstance().getTemplate(id);
        if (System_Message == null) {
            return "";
        }
        else {
            String Message = System_Message.getMessage();
            return Message;
        }
    }
}

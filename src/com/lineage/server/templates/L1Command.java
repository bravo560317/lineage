package com.lineage.server.templates;

public class L1Command
{
    private final String _name;
    private final boolean _system;
    private final int _level;
    private final String _executorClassName;
    private final String _note;
    
    public L1Command(String name, boolean system, int level, String executorClassName, String note) {
        this._name = name;
        this._system = system;
        this._level = level;
        this._executorClassName = executorClassName;
        this._note = note;
    }
    
    public String getName() {
        return this._name;
    }
    
    public boolean isSystem() {
        return this._system;
    }
    
    public int getLevel() {
        return this._level;
    }
    
    public String getExecutorClassName() {
        return this._executorClassName;
    }
    
    public String get_note() {
        return this._note;
    }
}

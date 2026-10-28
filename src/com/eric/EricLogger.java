package com.eric;

import java.util.logging.LogRecord;
import java.util.Calendar;
import com.eric.gui.J_Main;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class EricLogger extends Logger
{
    private static String className;
    private static String classResourceBundleName;
    
    static {
        EricLogger.className = null;
        EricLogger.classResourceBundleName = null;
    }
    
    public EricLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
        EricLogger.className = name;
    }
    
    public static EricLogger getLogger2(String name) {
        EricLogger.className = name;
        EricLogger.classResourceBundleName = Logger.getLogger(name).getResourceBundleName();
        return new EricLogger(EricLogger.className, EricLogger.classResourceBundleName);
    }
    
    @Override
    public synchronized void addHandler(Handler handler) throws SecurityException {
        super.addHandler(handler);
    }
    
    @Override
    public void config(String msg) {
        super.config(msg);
    }
    
    @Override
    public void entering(String sourceClass, String sourceMethod, Object param1) {
        super.entering(sourceClass, sourceMethod, param1);
    }
    
    @Override
    public void entering(String sourceClass, String sourceMethod, Object[] params) {
        super.entering(sourceClass, sourceMethod, params);
    }
    
    @Override
    public void entering(String sourceClass, String sourceMethod) {
        super.entering(sourceClass, sourceMethod);
    }
    
    @Override
    public void exiting(String sourceClass, String sourceMethod, Object result) {
        super.exiting(sourceClass, sourceMethod, result);
    }
    
    @Override
    public void exiting(String sourceClass, String sourceMethod) {
        super.exiting(sourceClass, sourceMethod);
    }
    
    @Override
    public void fine(String msg) {
        super.fine(msg);
    }
    
    @Override
    public void finer(String msg) {
        super.finer(msg);
    }
    
    @Override
    public void finest(String msg) {
        super.finest(msg);
    }
    
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }
    
    @Override
    public synchronized Handler[] getHandlers() {
        return super.getHandlers();
    }
    
    @Override
    public Level getLevel() {
        return super.getLevel();
    }
    
    @Override
    public String getName() {
        return super.getName();
    }
    
    @Override
    public Logger getParent() {
        return super.getParent();
    }
    
    @Override
    public ResourceBundle getResourceBundle() {
        return super.getResourceBundle();
    }
    
    @Override
    public String getResourceBundleName() {
        return super.getResourceBundleName();
    }
    
    @Override
    public synchronized boolean getUseParentHandlers() {
        return super.getUseParentHandlers();
    }
    
    @Override
    public void info(String msg) {
        J_Main.getInstance().addConsol(Calendar.getInstance().getTime().toString() + " " + EricLogger.className);
        J_Main.getInstance().addConsol(msg);
        super.info(msg);
    }
    
    @Override
    public boolean isLoggable(Level level) {
        return super.isLoggable(level);
    }
    
    @Override
    public void log(Level level, String msg, Object param1) {
        super.log(level, msg, param1);
    }
    
    @Override
    public void log(Level level, String msg, Object[] params) {
        super.log(level, msg, params);
    }
    
    @Override
    public void log(Level level, String msg, Throwable thrown) {
        J_Main.getInstance().addConsol(Calendar.getInstance().getTime().toString() + " " + EricLogger.className);
        J_Main.getInstance().addConsol(level + ": " + msg + "\n" + thrown);
        super.log(level, msg, thrown);
    }
    
    @Override
    public void log(Level level, String msg) {
        super.log(level, msg);
    }
    
    @Override
    public void log(LogRecord record) {
        super.log(record);
    }
    
    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1) {
        super.logp(level, sourceClass, sourceMethod, msg, param1);
    }
    
    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params) {
        super.logp(level, sourceClass, sourceMethod, msg, params);
    }
    
    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown) {
        super.logp(level, sourceClass, sourceMethod, msg, thrown);
    }
    
    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, String msg) {
        super.logp(level, sourceClass, sourceMethod, msg);
    }
    
    @Override
    public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object param1) {
        super.logrb(level, sourceClass, sourceMethod, bundleName, msg, param1);
    }
    
    @Override
    public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object[] params) {
        super.logrb(level, sourceClass, sourceMethod, bundleName, msg, params);
    }
    
    @Override
    public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Throwable thrown) {
        super.logrb(level, sourceClass, sourceMethod, bundleName, msg, thrown);
    }
    
    @Override
    public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg) {
        super.logrb(level, sourceClass, sourceMethod, bundleName, msg);
    }
    
    @Override
    public synchronized void removeHandler(Handler handler) throws SecurityException {
        super.removeHandler(handler);
    }
    
    @Override
    public void setFilter(Filter newFilter) throws SecurityException {
        super.setFilter(newFilter);
    }
    
    @Override
    public void setLevel(Level newLevel) throws SecurityException {
        super.setLevel(newLevel);
    }
    
    @Override
    public void setParent(Logger parent) {
        super.setParent(parent);
    }
    
    @Override
    public synchronized void setUseParentHandlers(boolean useParentHandlers) {
        super.setUseParentHandlers(useParentHandlers);
    }
    
    @Override
    public void severe(String msg) {
        super.severe(msg);
    }
    
    @Override
    public void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
        super.throwing(sourceClass, sourceMethod, thrown);
    }
    
    @Override
    public void warning(String msg) {
        J_Main.getInstance().addConsol(Calendar.getInstance().getTime().toString() + " " + EricLogger.className);
        J_Main.getInstance().addConsol("Warning: " + msg);
        super.warning(msg);
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public boolean equals(Object arg0) {
        return super.equals(arg0);
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}

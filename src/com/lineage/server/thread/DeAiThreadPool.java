package com.lineage.server.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Executors;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executor;
import org.apache.commons.logging.Log;

public class DeAiThreadPool
{
    private static final Log _log;
    private static DeAiThreadPool _instance;
    private static final int SCHEDULED_CORE_POOL_SIZE = 100;
    private Executor _executor;
    private ScheduledExecutorService _scheduler;
    
    static {
        _log = LogFactory.getLog(DeAiThreadPool.class);
    }
    
    public static DeAiThreadPool get() {
        if (DeAiThreadPool._instance == null) {
            DeAiThreadPool._instance = new DeAiThreadPool();
        }
        return DeAiThreadPool._instance;
    }
    
    private DeAiThreadPool() {
        this._executor = Executors.newCachedThreadPool();
        this._scheduler = Executors.newScheduledThreadPool(100, new DeAiThreadPool.PriorityThreadFactory("DeTPool", 5));
    }
    
    public void execute(Runnable r) {
        try {
            if (this._executor == null) {
                Thread t = new Thread(r);
                t.start();
            }
            else {
                this._executor.execute(r);
            }
        }
        catch (Exception e) {
            DeAiThreadPool._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void execute(Thread t) {
        try {
            t.start();
        }
        catch (Exception e) {
            DeAiThreadPool._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public ScheduledFuture<?> schedule(Runnable r, long delay) {
        try {
            if (delay <= 0L) {
                this._executor.execute(r);
                return null;
            }
            else {
                return this._scheduler.schedule(r, delay, TimeUnit.MILLISECONDS);
            }
        }
        catch (RejectedExecutionException e) {
            DeAiThreadPool._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    private class PriorityThreadFactory implements ThreadFactory
    {
        private final int _prio;
        private final String _name;
        private final AtomicInteger _threadNumber;
        private final ThreadGroup _group;
        
        public PriorityThreadFactory(String name, int prio) {
            this._threadNumber = new AtomicInteger(1);
            this._prio = prio;
            this._name = name;
            this._group = new ThreadGroup(this._name);
        }
        
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(this._group, r);
            t.setName(this._name + "-" + this._threadNumber.getAndIncrement());
            t.setPriority(this._prio);
            return t;
        }
        
        public ThreadGroup getGroup() {
            return this._group;
        }
    }
}

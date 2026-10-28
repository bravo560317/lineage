package com.lineage.server.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadFactory;
import java.util.TimerTask;
import com.lineage.server.model.monitor.L1PcMonitor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Executors;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executor;
import org.apache.commons.logging.Log;

public class PcOtherThreadPool
{
    private static final Log _log;
    private static PcOtherThreadPool _instance;
    private Executor _executor;
    private ScheduledExecutorService _scheduler;
    private final int _pcSchedulerPoolSize = 4;
    
    static {
        _log = LogFactory.getLog(PcOtherThreadPool.class);
    }
    
    public static PcOtherThreadPool get() {
        if (PcOtherThreadPool._instance == null) {
            PcOtherThreadPool._instance = new PcOtherThreadPool();
        }
        return PcOtherThreadPool._instance;
    }
    
    private PcOtherThreadPool() {
        this._executor = Executors.newCachedThreadPool();
        this._scheduler = Executors.newScheduledThreadPool(4, new PcOtherThreadPool.PriorityThreadFactory("PcOth", 5));
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
            PcOtherThreadPool._log.error(e.getLocalizedMessage(), e);
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
            PcOtherThreadPool._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ScheduledFuture<?> pcSchedule(L1PcMonitor r, long delay) {
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
            PcOtherThreadPool._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ScheduledFuture<?> scheduleAtFixedRate(TimerTask command, long initialDelay, long period) {
        try {
            return this._scheduler.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.MILLISECONDS);
        }
        catch (RejectedExecutionException e) {
            PcOtherThreadPool._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public void cancel(ScheduledFuture<?> future, boolean mayInterruptIfRunning) {
        try {
            future.cancel(mayInterruptIfRunning);
        }
        catch (RejectedExecutionException e) {
            PcOtherThreadPool._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void cancel(TimerTask task) {
        try {
            task.cancel();
        }
        catch (Exception e) {
            PcOtherThreadPool._log.error(e.getLocalizedMessage(), e);
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

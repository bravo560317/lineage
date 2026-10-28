package com.lineage.echo;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.commons.logging.LogFactory;
import java.util.Queue;
import org.apache.commons.logging.Log;

public class PacketHc implements Runnable
{
    private static final Log _log;
    private final Queue<byte[]> _queue;
    private final PacketHandlerExecutor _handler;
    private final ClientExecutor _executor;
    
    static {
        _log = LogFactory.getLog(PacketHc.class);
    }
    
    public PacketHc(ClientExecutor executor) {
        this._executor = executor;
        this._queue = new ConcurrentLinkedQueue();
        this._handler = new PacketHandler(this._executor);
    }
    
    public PacketHc(ClientExecutor executor, int capacity) {
        this._executor = executor;
        this._queue = new LinkedBlockingQueue(capacity);
        this._handler = new PacketHandler(this._executor);
    }
    
    public void requestWork(byte[] data) {
        this._queue.offer(data);
    }
    
    @Override
    public void run() {
        try {
            while (this._executor.get_socket() != null) {
                Iterator<byte[]> iter = (Iterator)this._queue.iterator();
                while (iter.hasNext()) {
                    byte[] decrypt = iter.next();
                    iter.remove();
                    this._handler.handlePacket(decrypt);
                    Thread.sleep(1L);
                }
                Thread.sleep(10L);
            }
        }
        catch (Exception e) {
            PacketHc._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this._queue.clear();
        }
    }
}

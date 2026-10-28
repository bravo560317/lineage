package com.lineage.echo;

import java.io.IOException;
import java.net.SocketException;
import java.util.Iterator;
import com.lineage.server.types.UByte8;
import com.lineage.server.types.UChar8;
import com.lineage.echo.encryptions.PacketPrint;
import com.lineage.server.serverpackets.ServerBasePacket;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.lineage.config.Config;
import org.apache.commons.logging.LogFactory;
import com.lineage.echo.encryptions.Encryption;
import java.util.Queue;
import org.apache.commons.logging.Log;

public class PacketSc implements Runnable
{
    private static final Log _log;
    private static boolean _debug;
    private final Queue<byte[]> _queue;
    private final ClientExecutor _client;
    private final EncryptExecutor _executor;
    private Encryption _keys;
    
    static {
        _log = LogFactory.getLog(PacketSc.class);
        PacketSc._debug = Config.DEBUG;
    }
    
    public PacketSc(ClientExecutor client, EncryptExecutor executor) {
        this._client = client;
        this._keys = client.get_keys();
        this._executor = executor;
        this._queue = new ConcurrentLinkedQueue();
    }
    
    private void requestWork(byte[] data) {
        this._queue.offer(data);
    }
    
    public void encrypt(ServerBasePacket packet) throws Exception {
        byte[] encrypt = packet.getContent();
        if (encrypt.length > 0 && this._executor.out() != null) {
            if (PacketSc._debug) {
                PacketSc._log.info("服務端: " + packet.getType() + "\nOP ID: " + (encrypt[0] & 0xFF) + "\nInfo:\n" + PacketPrint.get().printData(encrypt, encrypt.length));
            }
            char[] ac = new char[encrypt.length];
            ac = UChar8.fromArray(encrypt);
            ac = this._keys.encrypt(ac);
            if (ac == null) {
                return;
            }
            else {
                encrypt = UByte8.fromArray(ac);
                this.requestWork(encrypt);
            }
        }
    }
    
    @Override
    public void run() {
        try {
            while (this._client.get_socket() != null) {
                Iterator<byte[]> iter = (Iterator)this._queue.iterator();
                while (iter.hasNext()) {
                    byte[] decrypt = iter.next();
                    iter.remove();
                    if (!this.outPacket(decrypt)) {
                        return;
                    }
                    Thread.sleep(5L);
                }
                Thread.sleep(10L);
            }
        }
        catch (Exception e) {
            PacketSc._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this._queue.clear();
        }
    }
    
    private boolean outPacket(byte[] decrypt) {
        try {
            if (decrypt == null || this._executor.out() == null) {
                return false;
            }
            int outLength = decrypt.length + 2;
            this._executor.out().write(outLength & 0xFF);
            this._executor.out().write(outLength >> 8 & 0xFF);
            this._executor.out().write(decrypt);
            this._executor.out().flush();
            return true;
        }
        catch (IOException e) {
            if (e instanceof SocketException) {
                PacketSc._log.info("客戶端已中斷連線，停止傳送封包: " + this._client.getIp()
                        + " 封包長度: " + (decrypt != null ? decrypt.length : 0)
                        + " 待傳數量: " + this._queue.size());
            }
            else {
                PacketSc._log.warn("傳送封包失敗，客戶端可能已斷線: " + this._client.getIp(), e);
            }
            this._executor.stop();
            return false;
        }
    }
    
    public void stop() {
        this._queue.clear();
    }
}

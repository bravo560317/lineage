package com.lineage.echo;

import com.lineage.server.utils.StreamUtil;
import java.io.Closeable;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.config.Config;
import java.io.BufferedOutputStream;
import org.apache.commons.logging.LogFactory;
import com.lineage.echo.encryptions.Encryption;
import java.io.OutputStream;
import org.apache.commons.logging.Log;
import com.lineage.server.serverpackets.OpcodesServer;

public class EncryptExecutor extends OpcodesServer
{
    private static final Log _log;
    private final ClientExecutor _client;
    private final OutputStream _out;
    private final PacketSc _scPacket;
    private Encryption _keys;
    
    static {
        _log = LogFactory.getLog(EncryptExecutor.class);
    }
    
    public EncryptExecutor(ClientExecutor client, OutputStream out) {
        this._client = client;
        this._keys = client.get_keys();
        this._out = new BufferedOutputStream(out);
        this._scPacket = new PacketSc(client, this);
    }
    
    public void outStart() {
        try {
            synchronized (this) {
                if (this._out != null) {
                    if (Config.LOGINS_TO_AUTOENTICATION) {
                        this._out.write((int)(this._client._authdata & 0xFFL));
                        this._out.write((int)(this._client._authdata >> 8 & 0xFFL));
                        this._out.write((int)(this._client._authdata >> 16 & 0xFFL));
                        this._out.write((int)(this._client._authdata >> 24 & 0xFFL));
                        this._out.flush();
                    }
                    this._out.write(OpcodesClient._firstPacket);
                    this._out.flush();
                    try {
                        this._keys.initKeys(1881492746L);
                    }
                    catch (Exception e) {
                        this.stop();
                        throw new EncryptErrorException("設置加密公式發生異常: " + this._client.getIp(), e);
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public void encrypt(ServerBasePacket packet) {
        try {
            if (packet == null) {
                return;
            }
            else {
                synchronized (this) {
                    this._scPacket.encrypt(packet);
                }
            }
        }
        catch (Exception e) {
            EncryptExecutor._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public OutputStream out() {
        return this._out;
    }
    
    public void satrt() {
        GeneralThreadPool.get().schedule(this._scPacket, 0L);
    }
    
    public void stop() {
        try {
            this._scPacket.stop();
            StreamUtil.close(new Closeable[] { this._out });
        }
        catch (Exception ex) {}
    }
}

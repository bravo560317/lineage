package com.lineage.echo;

import com.lineage.server.utils.StreamUtil;
import java.io.Closeable;
import com.lineage.config.Config;
import org.apache.commons.logging.LogFactory;
import com.lineage.echo.encryptions.Encryption;
import java.io.InputStream;
import org.apache.commons.logging.Log;

public class DecryptExecutor
{
    private static final Log _log;
    private final ClientExecutor _client;
    private final InputStream _in;
    private Encryption _keys;
    
    static {
        _log = LogFactory.getLog(DecryptExecutor.class);
    }
    
    public DecryptExecutor(ClientExecutor client, InputStream in) {
        this._client = client;
        this._keys = client.get_keys();
        this._in = in;
    }
    
    public ClientExecutor get_client() {
        return this._client;
    }
    
    public byte[] decrypt() throws Exception {
        try {
            int hiByte = this._in.read();
            int loByte = this._in.read();
            if (loByte < 0) {
                throw new DecryptErrorException();
            }
            else {
                if (Config.LOGINS_TO_AUTOENTICATION) {
                    hiByte ^= this._client._xorByte;
                    loByte ^= this._client._xorByte;
                }
                int dataLength = (loByte << 8) + hiByte - 2;
                byte[] data = new byte[dataLength];
                int readSize = 0;
                for (int i = 0; i != -1 && readSize < dataLength; i = this._in.read(data, readSize, dataLength - readSize), readSize += i) {}
                if (readSize != dataLength) {
                    throw new RuntimeException();
                }
                else {
                    if (Config.LOGINS_TO_AUTOENTICATION) {
                        for (int i = 0; i < dataLength; ++i) {
                            data[i] ^= (byte)this._client._xorByte;
                        }
                    }
                    return this._keys.decrypt(data);
                }
            }
        }
        catch (Exception e) {
            throw new DecryptErrorException();
        }
    }
    
    public void stop() {
        try {
            StreamUtil.close(new Closeable[] { this._in });
        }
        catch (Exception e) {
            DecryptExecutor._log.error(e.getLocalizedMessage(), e);
        }
    }
}

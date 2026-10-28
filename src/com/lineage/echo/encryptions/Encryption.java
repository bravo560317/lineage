package com.lineage.echo.encryptions;

import com.lineage.server.types.UChar8;
import com.lineage.server.types.ULong32;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class Encryption
{
    private static final Log _log;
    private Keys _keys;
    
    static {
        _log = LogFactory.getLog(Encryption.class);
    }
    
    public Encryption() {
        this._keys = null;
    }
    
    public void initKeys(long seed) throws Exception {
        try {
            Keys keys = new Keys();
            long[] key = { seed, 2467289058L };
            Blowfish.getSeeds(key);
            keys.EKEY[0] = (keys.DKEY[0] = key[0]);
            keys.EKEY[1] = (keys.DKEY[1] = key[1]);
            this._keys = keys;
        }
        catch (Exception e) {
            Encryption._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public char[] encrypt(char[] buf) throws Exception {
        try {
            if (this._keys == null) {
                throw new Exception();
            }
            else {
                long mask = ULong32.fromArray(buf);
                this._encrypt(buf);
                long[] ekey = this._keys.EKEY;
                int n = 0;
                ekey[n] ^= mask;
                this._keys.EKEY[1] = ULong32.add(this._keys.EKEY[1], 679411651L);
                return buf;
            }
        }
        catch (Exception e) {
            Encryption._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    private char[] _encrypt(char[] buf) {
        try {
            int size = buf.length;
            char[] ek = UChar8.fromArray(this._keys.EKEY);
            int n = 0;
            buf[n] ^= ek[0];
            for (int i = 1; i < size; ++i) {
                int n2 = i;
                buf[n2] ^= (char)(buf[i - 1] ^ ek[i & 0x7]);
            }
            buf[3] ^= ek[2];
            buf[2] = (char)(buf[2] ^ buf[3] ^ ek[3]);
            buf[1] = (char)(buf[1] ^ buf[2] ^ ek[4]);
            buf[0] = (char)(buf[0] ^ buf[1] ^ ek[5]);
            return buf;
        }
        catch (Exception e) {
            Encryption._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public byte[] decrypt(byte[] buf) throws Exception {
        try {
            if (this._keys == null) {
                throw new Exception();
            }
            else {
                this._decrypt(buf);
                long mask = ULong32.fromArray(buf);
                long[] dkey = this._keys.DKEY;
                int n = 0;
                dkey[n] ^= mask;
                this._keys.DKEY[1] = ULong32.add(this._keys.DKEY[1], 679411651L);
                return buf;
            }
        }
        catch (Exception e) {
            Encryption._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    private byte[] _decrypt(byte[] buf) {
        try {
            char[] dk = UChar8.fromArray(this._keys.DKEY);
            byte b3 = buf[3];
            int n = 3;
            buf[n] ^= (byte)dk[2];
            byte b4 = buf[2];
            int n2 = 2;
            buf[n2] ^= (byte)(b3 ^ dk[3]);
            byte b5 = buf[1];
            int n3 = 1;
            buf[n3] ^= (byte)(b4 ^ dk[4]);
            byte k = (byte)(buf[0] ^ b5 ^ dk[5]);
            buf[0] = (byte)(k ^ dk[0]);
            for (int i = 1; i < buf.length; ++i) {
                byte t = buf[i];
                int n4 = i;
                buf[n4] ^= (byte)(dk[i & 0x7] ^ k);
                k = t;
            }
            return buf;
        }
        catch (Exception e) {
            Encryption._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}

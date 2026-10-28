package com.lineage.server.clientpackets;

import com.lineage.echo.ClientExecutor;
import com.lineage.config.Config;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public abstract class ClientBasePacket
{
    private static final Log _log;
    private static final String CLIENT_LANGUAGE_CODE;
    private byte[] _decrypt;
    private int _off;
    
    static {
        _log = LogFactory.getLog(ClientBasePacket.class);
        CLIENT_LANGUAGE_CODE = Config.CLIENT_LANGUAGE_CODE;
    }
    
    public ClientBasePacket() {
        this._decrypt = null;
        this._off = 0;
    }
    
    public abstract void start(byte[] p0, ClientExecutor p1) throws Exception;
    
    protected void read(byte[] abyte0) {
        try {
            this._decrypt = abyte0;
            this._off = 1;
        }
        catch (Exception e) {
            ClientBasePacket._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    protected int readD() {
        try {
            if (this._decrypt == null) {
                return 0;
            }
            else if (this._decrypt.length < this._off + 4) {
                return 0;
            }
            else {
                int i = this._decrypt[this._off++] & 0xFF;
                i |= (this._decrypt[this._off++] << 8 & 0xFF00);
                i |= (this._decrypt[this._off++] << 16 & 0xFF0000);
                i |= (this._decrypt[this._off++] << 24 & 0xFF000000);
                return i;
            }
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    protected int readC() {
        try {
            if (this._decrypt == null) {
                return 0;
            }
            else if (this._decrypt.length < this._off + 1) {
                return 0;
            }
            else {
                int i = this._decrypt[this._off++] & 0xFF;
                return i;
            }
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    protected int readH() {
        try {
            if (this._decrypt == null) {
                return 0;
            }
            else if (this._decrypt.length < this._off + 2) {
                return 0;
            }
            else {
                int i = this._decrypt[this._off++] & 0xFF;
                i |= (this._decrypt[this._off++] << 8 & 0xFF00);
                return i;
            }
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    protected int readCH() {
        try {
            if (this._decrypt == null) {
                return 0;
            }
            else if (this._decrypt.length < this._off + 3) {
                return 0;
            }
            else {
                int i = this._decrypt[this._off++] & 0xFF;
                i |= (this._decrypt[this._off++] << 8 & 0xFF00);
                i |= (this._decrypt[this._off++] << 16 & 0xFF0000);
                return i;
            }
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    protected double readF() {
        try {
            if (this._decrypt == null) {
                return 0.0;
            }
            else if (this._decrypt.length < this._off + 8) {
                return 0.0;
            }
            else {
                long l = (long)(this._decrypt[this._off++] & 0xFF);
                l |= (long)(this._decrypt[this._off++] << 8 & 0xFF00);
                l |= (long)(this._decrypt[this._off++] << 16 & 0xFF0000);
                l |= (long)(this._decrypt[this._off++] << 24 & 0xFF000000);
                l |= ((long)this._decrypt[this._off++] << 32 & 0xFF00000000L);
                l |= ((long)this._decrypt[this._off++] << 40 & 0xFF0000000000L);
                l |= ((long)this._decrypt[this._off++] << 48 & 0xFF000000000000L);
                l |= ((long)this._decrypt[this._off++] << 56 & 0xFF00000000000000L);
                return Double.longBitsToDouble(l);
            }
        }
        catch (Exception ex) {
            return 0.0;
        }
    }
    
    protected String readS() {
        String s = null;
        try {
            if (this._decrypt == null) {
                return s;
            }
            else {
                s = new String(this._decrypt, this._off, this._decrypt.length - this._off, ClientBasePacket.CLIENT_LANGUAGE_CODE);
                s = s.substring(0, s.indexOf(0));
                this._off += s.getBytes(ClientBasePacket.CLIENT_LANGUAGE_CODE).length + 1;
            }
        }
        catch (Exception ex) {}
        return s;
    }
    
    protected byte[] readByte() {
        byte[] result = new byte[this._decrypt.length - this._off];
        try {
            System.arraycopy(this._decrypt, this._off, result, 0, this._decrypt.length - this._off);
            this._off = this._decrypt.length;
        }
        catch (Exception ex) {}
        return result;
    }
    
    protected byte[] readBytes() {
        int index = this._off;
        int length = this._decrypt.length - this._off;
        int endOfLine = -1;
        while (index < length && this._decrypt[index] != -1) {
            ++index;
        }
        byte[] result = new byte[index - this._off];
        try {
            System.arraycopy(this._decrypt, this._off, result, 0, index - this._off);
            this._off = index;
        }
        catch (Exception ex) {}
        return result;
    }
    
    public void over() {
        try {
            this._decrypt = null;
            this._off = 0;
        }
        catch (Exception e) {
            ClientBasePacket._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

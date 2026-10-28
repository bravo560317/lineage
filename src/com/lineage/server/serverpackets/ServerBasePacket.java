package com.lineage.server.serverpackets;

import java.io.IOException;
import java.util.Iterator;
import com.lineage.config.ConfigBad;
import com.lineage.config.Config;
import org.apache.commons.logging.LogFactory;
import java.io.ByteArrayOutputStream;
import org.apache.commons.logging.Log;

public abstract class ServerBasePacket extends OpcodesServer
{
    private static final Log _log;
    private static final String CLIENT_LANGUAGE_CODE;
    protected ByteArrayOutputStream _bao;
    
    static {
        _log = LogFactory.getLog(ServerBasePacket.class);
        CLIENT_LANGUAGE_CODE = Config.CLIENT_LANGUAGE_CODE;
    }
    
    public ServerBasePacket() {
        this._bao = new ByteArrayOutputStream();
    }
    
    protected Object writeBoolean(boolean b) {
        this._bao.write(b ? 1 : 0);
        return null;
    }
    
    protected void writeD(int value) {
        this._bao.write(value & 0xFF);
        this._bao.write(value >> 8 & 0xFF);
        this._bao.write(value >> 16 & 0xFF);
        this._bao.write(value >> 24 & 0xFF);
    }
    
    protected void writeH(int value) {
        this._bao.write(value & 0xFF);
        this._bao.write(value >> 8 & 0xFF);
    }
    
    protected void writeC(int value) {
        this._bao.write(value & 0xFF);
    }
    
    protected void writeP(int value) {
        this._bao.write(value);
    }
    
    protected void writeL(long value) {
        this._bao.write((int)(value & 0xFFL));
    }
    
    protected void writeEXP(long value) {
        this._bao.write((int)(value & 0xFFL));
        this._bao.write((int)(value >> 8 & 0xFFL));
        this._bao.write((int)(value >> 16 & 0xFFL));
        this._bao.write((int)(value >> 24 & 0xFFL));
    }
    
    protected void writeF(double org) {
        long value = Double.doubleToRawLongBits(org);
        this._bao.write((int)(value & 0xFFL));
        this._bao.write((int)(value >> 8 & 0xFFL));
        this._bao.write((int)(value >> 16 & 0xFFL));
        this._bao.write((int)(value >> 24 & 0xFFL));
        this._bao.write((int)(value >> 32 & 0xFFL));
        this._bao.write((int)(value >> 40 & 0xFFL));
        this._bao.write((int)(value >> 48 & 0xFFL));
        this._bao.write((int)(value >> 56 & 0xFFL));
    }
    
    protected void writeExp(long value) {
        this._bao.write((int)(value & 0xFFL));
        this._bao.write((int)(value >> 8 & 0xFFL));
        this._bao.write((int)(value >> 16 & 0xFFL));
        this._bao.write((int)(value >> 24 & 0xFFL));
    }
    
    protected void writeS(String text) {
        try {
            if (text != null) {
                String chtext = text;
                for (String bad : ConfigBad.BAD_TEXT_LIST) {
                    int index = chtext.indexOf(bad);
                    if (index != -1) {
                        chtext = text.substring(0, index);
                        chtext = chtext + text.substring(index + bad.length());
                    }
                }
                this._bao.write(chtext.getBytes(ServerBasePacket.CLIENT_LANGUAGE_CODE));
            }
        }
        catch (Exception e) {
            ServerBasePacket._log.error(e.getLocalizedMessage(), e);
        }
        this._bao.write(0);
    }
    
    protected void writeByte(byte[] text) {
        try {
            if (text != null) {
                this._bao.write(text);
            }
        }
        catch (Exception e) {
            ServerBasePacket._log.error(e.getLocalizedMessage(), e);
        }
        this._bao.write(0);
    }
    
    protected byte[] getBytes() {
        int padding = this._bao.size() % 8;
        if (padding != 0) {
            for (int i = padding; i < 8; ++i) {
                this.writeC(0);
            }
        }
        return this._bao.toByteArray();
    }
    
    public abstract byte[] getContent() throws IOException;
    
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

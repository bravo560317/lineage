package com.lineage.server.utils;

import java.io.IOException;
import com.lineage.config.Config;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class BinaryOutputStream extends OutputStream
{
    private static final String CLIENT_LANGUAGE_CODE;
    private final ByteArrayOutputStream _bao;
    
    static {
        CLIENT_LANGUAGE_CODE = Config.CLIENT_LANGUAGE_CODE;
    }
    
    public BinaryOutputStream() {
        this._bao = new ByteArrayOutputStream();
    }
    
    @Override
    public void write(int b) throws IOException {
        this._bao.write(b);
    }
    
    public void writeD(int value) {
        this._bao.write(value & 0xFF);
        this._bao.write(value >> 8 & 0xFF);
        this._bao.write(value >> 16 & 0xFF);
        this._bao.write(value >> 24 & 0xFF);
    }
    
    public void writeH(int value) {
        this._bao.write(value & 0xFF);
        this._bao.write(value >> 8 & 0xFF);
    }
    
    public void writeC(int value) {
        this._bao.write(value & 0xFF);
    }
    
    public void writeP(int value) {
        this._bao.write(value);
    }
    
    public void writeL(long value) {
        this._bao.write((int)(value & 0xFFL));
    }
    
    public void writeF(double org) {
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
    
    public void writeS(String text) {
        try {
            if (text != null) {
                this._bao.write(text.getBytes(BinaryOutputStream.CLIENT_LANGUAGE_CODE));
            }
        }
        catch (Exception ex) {}
        this._bao.write(0);
    }
    
    public void writeByte(byte[] text) {
        try {
            if (text != null) {
                this._bao.write(text);
            }
        }
        catch (Exception ex) {}
    }
    
    public int getLength() {
        return this._bao.size() + 2;
    }
    
    public byte[] getBytes() {
        return this._bao.toByteArray();
    }
}

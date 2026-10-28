package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.ArrayList;

public class S_PacketBoxGame extends ServerBasePacket
{
    private byte[] _byte;
    public static final int GAMESTART = 64;
    public static final int TIMESTART = 65;
    public static final int GAMEINFO = 66;
    public static final int GAMEOVER = 69;
    public static final int GAMECLEAR = 70;
    public static final int STARTTIME = 71;
    public static final int STARTTIMECLEAR = 72;
    
    public S_PacketBoxGame(int subCode) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        switch (subCode) {
            default: {
                return;
            }
        }
    }
    
    public S_PacketBoxGame(int subCode, int value) {
        this._byte = null;
        this.writeC(250);
        this.writeC(subCode);
        switch (subCode) {
            case 64:
            case 69: {
                this.writeC(value);
                break;
            }
            case 71: {
                this.writeH(value);
                break;
            }
        }
    }
    
    public S_PacketBoxGame(ArrayList<StringBuilder> list) {
        this._byte = null;
        this.writeC(250);
        this.writeC(66);
        this.writeC(list.size());
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        if (list != null) {
            for (StringBuilder string : list) {
                this.writeS(string.toString());
            }
        }
    }
    
    public S_PacketBoxGame(StringBuilder title, ArrayList<StringBuilder> list) {
        this._byte = null;
        this.writeC(250);
        this.writeC(66);
        this.writeC(list.size() + 1);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeS(title.toString());
        if (list != null) {
            for (StringBuilder c : list) {
                this.writeS(c.toString());
            }
        }
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

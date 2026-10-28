package com.lineage.server.serverpackets;

public class S_Paralysis extends ServerBasePacket
{
    private byte[] _byte;
    public static final int TYPE_PARALYSIS = 1;
    public static final int TYPE_PARALYSIS2 = 2;
    public static final int TYPE_SLEEP = 3;
    public static final int TYPE_FREEZE = 4;
    public static final int TYPE_STUN = 5;
    public static final int TYPE_BIND = 6;
    public static final int TYPE_TELEPORT_UNLOCK = 7;
    public static final int TYPE_POWERGRIP = 26;
    public static final int TYPE_DESPERADO = 30;
    
    public S_Paralysis(int type, boolean flag) {
        this._byte = null;
        this.writeC(202);
        switch (type) {
            case 1: {
                if (flag) {
                    this.writeC(2);
                    break;
                }
                else {
                    this.writeC(3);
                    break;
                }
            }
            case 2: {
                if (flag) {
                    this.writeC(4);
                    break;
                }
                else {
                    this.writeC(5);
                    break;
                }
            }
            case 3: {
                if (flag) {
                    this.writeC(10);
                    break;
                }
                else {
                    this.writeC(11);
                    break;
                }
            }
            case 4: {
                if (flag) {
                    this.writeC(12);
                    break;
                }
                else {
                    this.writeC(13);
                    break;
                }
            }
            case 5: {
                if (flag) {
                    this.writeC(22);
                    break;
                }
                else {
                    this.writeC(23);
                    break;
                }
            }
            case 6: {
                if (flag) {
                    this.writeC(24);
                    break;
                }
                else {
                    this.writeC(25);
                    break;
                }
            }
            case 7: {
                if (flag) {
                    this.writeC(6);
                    break;
                }
                else {
                    this.writeC(7);
                    break;
                }
            }
            case 26: {
                if (flag) {
                    this.writeC(26);
                    break;
                }
                else {
                    this.writeC(27);
                    break;
                }
            }
            case 30: {
                if (flag) {
                    this.writeC(30);
                    break;
                }
                else {
                    this.writeC(31);
                    break;
                }
            }
        }
    }
    
    public S_Paralysis(int type, boolean flag, int time) {
        this._byte = null;
        this.writeC(202);
        if (type == 1) {
            if (flag) {
                this.writeC(2);
                this.writeH(0);
                this.writeH(time);
            }
            else {
                this.writeC(3);
                this.writeH(0);
                this.writeH(0);
            }
        }
        else if (type == 2) {
            if (flag) {
                this.writeC(4);
                this.writeH(0);
                this.writeH(time);
            }
            else {
                this.writeC(5);
                this.writeH(0);
                this.writeH(0);
            }
        }
        else if (type == 7) {
            this.writeC(7);
            this.writeH(0);
            this.writeH(0);
        }
        else if (type == 3) {
            if (flag) {
                this.writeC(10);
                this.writeH(0);
                this.writeH(0);
            }
            else {
                this.writeC(11);
                this.writeH(0);
                this.writeH(0);
            }
        }
        else if (type == 4) {
            if (flag) {
                this.writeC(12);
                this.writeH(0);
                this.writeH(0);
            }
            else {
                this.writeC(13);
                this.writeH(0);
                this.writeH(0);
            }
        }
        else if (type == 5) {
            if (flag) {
                this.writeC(22);
                this.writeH(0);
                this.writeH(0);
            }
            else {
                this.writeC(23);
                this.writeH(0);
                this.writeH(0);
            }
        }
        else if (type == 6) {
            if (flag) {
                this.writeC(24);
                this.writeH(0);
                this.writeH(0);
            }
            else {
                this.writeC(25);
                this.writeH(0);
                this.writeH(0);
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

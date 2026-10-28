package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_PacketBoxCooking extends ServerBasePacket
{
    private byte[] _byte;
    public static final int COOK_WINDOW = 52;
    public static final int ICON_COOKING = 53;
    
    public S_PacketBoxCooking(int value) {
        this._byte = null;
        this.writeC(250);
        this.writeC(52);
        this.writeC(219);
        this.writeC(49);
        this.writeC(223);
        this.writeC(2);
        this.writeC(1);
        this.writeC(value);
    }
    
    public S_PacketBoxCooking(L1PcInstance pc, int type, int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC(53);
        int food = pc.get_food() * 10 - 250;
        if (food < 0) {
            food = 0;
        }
        switch (type) {
            case 7: {
                this.writeC((int)pc.getStr());
                this.writeC((int)pc.getInt());
                this.writeC((int)pc.getWis());
                this.writeC((int)pc.getDex());
                this.writeC((int)pc.getCon());
                this.writeC((int)pc.getCha());
                this.writeH(food);
                this.writeC(type);
                this.writeC(36);
                this.writeH(time);
                this.writeC(0);
                break;
            }
            case 54: {
                this.writeC(0);
                this.writeC(0);
                this.writeC(0);
                this.writeC(0);
                this.writeC(0);
                this.writeC(0);
                this.writeH(0);
                this.writeC(type);
                this.writeC(42);
                this.writeH(time);
                this.writeC(0);
                break;
            }
            default: {
                this.writeC((int)pc.getStr());
                this.writeC((int)pc.getInt());
                this.writeC((int)pc.getWis());
                this.writeC((int)pc.getDex());
                this.writeC((int)pc.getCon());
                this.writeC((int)pc.getCha());
                this.writeH(food);
                this.writeC(type);
                this.writeC(38);
                this.writeH(time);
                this.writeC(0);
                break;
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

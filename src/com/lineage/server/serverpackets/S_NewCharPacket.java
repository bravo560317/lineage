package com.lineage.server.serverpackets;

import java.text.SimpleDateFormat;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_NewCharPacket extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NewCharPacket(L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pc);
    }
    
    private void buildPacket(L1PcInstance pc) {
        this.writeC(127);
        this.writeS(pc.getName());
        this.writeS("");
        this.writeC(pc.getType());
        this.writeC((int)pc.get_sex());
        this.writeH(pc.getLawful());
        this.writeH(pc.getMaxHp());
        this.writeH(pc.getMaxMp());
        this.writeC(pc.getAc());
        this.writeC(pc.getLevel());
        this.writeC((int)pc.getStr());
        this.writeC((int)pc.getDex());
        this.writeC((int)pc.getCon());
        this.writeC((int)pc.getWis());
        this.writeC((int)pc.getCha());
        this.writeC((int)pc.getInt());
        this.writeC(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int time = Integer.parseInt(sdf.format((Long)System.currentTimeMillis()).replace("-", ""));
        String times = Integer.toHexString(time);
        if (times.length() < 8) {
            times = "0" + times;
        }
        this.writeC(Integer.decode("0x" + times.substring(6, 8)));
        this.writeC(Integer.decode("0x" + times.substring(4, 6)));
        this.writeC(Integer.decode("0x" + times.substring(2, 4)));
        this.writeC(Integer.decode("0x" + times.substring(0, 2)));
        int checkcode = pc.getLevel() ^ pc.getStr() ^ pc.getDex() ^ pc.getCon() ^ pc.getWis() ^ pc.getCha() ^ pc.getInt();
        this.writeC(checkcode & 0xFF);
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

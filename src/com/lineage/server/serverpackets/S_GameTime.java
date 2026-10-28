package com.lineage.server.serverpackets;

import com.lineage.server.model.gametime.L1GameTimeClock;

public class S_GameTime extends ServerBasePacket
{
    public S_GameTime(int time) {
        this.buildPacket(time);
    }
    
    public S_GameTime() {
        int time = L1GameTimeClock.getInstance().currentTime().getSeconds();
        this.buildPacket(time);
    }
    
    private void buildPacket(int time) {
        this.writeC(123);
        this.writeD(time);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
}

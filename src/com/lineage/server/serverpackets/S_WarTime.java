package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1Castle;
import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.config.Config;
import java.util.Calendar;

public class S_WarTime extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_WarTime(Calendar cal) {
        this._byte = null;
        Calendar base_cal = Calendar.getInstance();
        base_cal.set(1997, 0, 1, 17, 0);
        long base_millis = base_cal.getTimeInMillis();
        long millis = cal.getTimeInMillis();
        long diff = millis - base_millis;
        diff -= 72000000L;
        diff /= 60000L;
        int time = (int)(diff / 182L);
        this.writeC(231);
        this.writeH(6);
        this.writeS(Config.TIME_ZONE);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeD(time);
        this.writeC(0);
        this.writeD(time - 1);
        this.writeC(0);
        this.writeD(time - 2);
        this.writeC(0);
        this.writeD(time - 3);
        this.writeC(0);
        this.writeD(time - 4);
        this.writeC(0);
        this.writeD(time - 5);
        this.writeC(0);
    }
    
    public S_WarTime(int op) {
        this._byte = null;
        L1Castle l1castle = CastleReading.get().getCastleTable(5);
        Calendar cal = l1castle.getWarTime();
        Calendar base_cal = Calendar.getInstance();
        base_cal.set(1997, 0, 1, 17, 0);
        long base_millis = base_cal.getTimeInMillis();
        long millis = cal.getTimeInMillis();
        long diff = millis - base_millis;
        diff -= 72000000L;
        diff /= 60000L;
        int time = (int)(diff / 182L);
        this.writeC(op);
        this.writeH(6);
        this.writeS(Config.TIME_ZONE);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeD(time);
        this.writeC(0);
        this.writeD(time - 1);
        this.writeC(0);
        this.writeD(time - 2);
        this.writeC(0);
        this.writeD(time - 3);
        this.writeC(0);
        this.writeD(time - 4);
        this.writeC(0);
        this.writeD(time - 5);
        this.writeC(0);
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

package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1Account;
import java.util.Iterator;
import java.util.Calendar;
import java.util.TimeZone;
import com.lineage.config.Config;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_PacketBoxGm extends ServerBasePacket
{
    private byte[] _byte;
    public static final int CALL_SOMETHING = 45;
    
    public S_PacketBoxGm(L1PcInstance srcpc, int mode) {
        this._byte = null;
        this.writeC(250);
        this.writeC(45);
        if (srcpc.isGm()) {
            this.callSomething(srcpc, mode);
        }
    }
    
    private void callSomething(L1PcInstance srcpc, int mode) {
        Iterator<L1PcInstance> itr = (Iterator)World.get().getAllPlayers().iterator();
        this.writeC(World.get().getAllPlayers().size());
        while (itr.hasNext()) {
            L1PcInstance pc = itr.next();
            L1Account acc = pc.getNetConnection().getAccount();
            if (acc == null) {
                this.writeD(0);
            }
            else {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(Config.TIME_ZONE));
                long lastactive = acc.get_lastactive().getTime();
                cal.setTimeInMillis(lastactive);
                cal.set(1, 1970);
                int time = (int)(cal.getTimeInMillis() / 1000L);
                this.writeD(time);
            }
            this.writeS(String.valueOf(mode) + ":" + pc.getName());
            if (pc.isActived()) {
                this.writeS(String.valueOf(pc.getLevel()) + "(掛)");
            }
            else {
                this.writeS(String.valueOf(pc.getLevel()));
            }
        }
        String xmode = null;
        switch (mode) {
            case 0: {
                xmode = "刪除已存人物保留技能";
                break;
            }
            case 1: {
                xmode = "移動座標至指定人物身邊";
                break;
            }
            case 2: {
                xmode = "召回指定人物";
                break;
            }
            case 3: {
                xmode = "召回指定隊伍";
                break;
            }
            case 4: {
                xmode = "全技能";
                break;
            }
            case 5: {
                xmode = "踢除下線";
                break;
            }
            case 6: {
                xmode = "封鎖IP/MAC";
                break;
            }
            case 7: {
                xmode = "帳號封鎖";
                break;
            }
            case 8: {
                xmode = "殺死指定人物";
                break;
            }
        }
        if (xmode != null) {
            srcpc.setTempID(mode);
            srcpc.sendPackets(new S_ServerMessage(166, "請注意目前模式為: " + xmode));
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

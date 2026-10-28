package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_Weather;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_MapID;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.datatables.GetbackTable;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Restart extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Restart.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance pc = client.getActiveChar();
            if (pc != null) {
                int[] loc;
                if (pc.getHellTime() > 0) {
                    loc = new int[] { 32701, 32777, 666 };
                }
                else {
                    loc = GetbackTable.GetBack_Location(pc, true);
                }
                L1EffectInstance tomb = pc.get_tomb();
                if (tomb != null) {
                    tomb.broadcastPacketAll(new S_DoActionGFX(tomb.getId(), 8));
                    tomb.deleteMe();
                }
                pc.stopPcDeleteTimer();
                pc.removeAllKnownObjects();
                pc.broadcastPacketAll(new S_RemoveObject(pc));
                pc.setCurrentHp(pc.getLevel());
                pc.set_food(40);
                pc.setStatus(0);
                World.get().moveVisibleObject(pc, loc[2]);
                pc.setX(loc[0]);
                pc.setY(loc[1]);
                pc.setMap((short)loc[2]);
                pc.set_showId(-1);
                pc.sendPackets(new S_MapID(pc, (int)pc.getMapId(), pc.getMap().isUnderwater()));
                pc.broadcastPacketAll(new S_OtherCharPacks(pc));
                pc.sendPackets(new S_OwnCharPack(pc));
                pc.sendPackets(new S_CharVisualUpdate(pc));
                pc.startHpRegeneration();
                pc.startMpRegeneration();
                pc.sendPackets(new S_Weather(World.get().getWeather()));
                pc.sendPackets(new S_PacketBox(132, pc.getEr()));
                pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
                if (pc.getHellTime() > 0) {
                    pc.setHellTime(pc.getHellTime() - 1);
                    pc.beginHell(false);
                }
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

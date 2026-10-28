package com.lineage.server.serverpackets;

import com.lineage.server.datatables.MapsTable;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_MapID extends ServerBasePacket
{
    public S_MapID(L1PcInstance pc, int mapid, boolean isUnderwater) {
        if (!QuestMapTable.get().isQuestMap((int)pc.getMapId())) {
            if (pc.get_showId() != -1 && WorldQuest.get().isQuest(pc.get_showId())) {
                WorldQuest.get().remove(pc.get_showId(), pc);
            }
            pc.set_showId(-1);
        }
        int newmapid = MapsTable.get().getCopyMapId(mapid);
        if (newmapid >= 0) {
            mapid = newmapid;
        }
        this.writeC(206);
        this.writeH(mapid);
        this.writeC(isUnderwater ? 1 : 0);
    }
    
    public S_MapID(int mapid) {
        this.writeC(206);
        this.writeH(mapid);
        this.writeC(0);
        this.writeC(16);
        this.writeH(248);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(16);
        this.writeC(13);
        this.writeC(53);
        this.writeC(197);
    }
    
    public S_MapID(int mapid, boolean isUnderwater) {
        this.writeC(206);
        this.writeH(mapid);
        this.writeC(isUnderwater ? 1 : 0);
        this.writeC(0);
        this.writeH(0);
        this.writeC(0);
        this.writeD(0);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
}

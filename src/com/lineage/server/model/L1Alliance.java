package com.lineage.server.model;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.ServerBasePacket;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public final class L1Alliance
{
    private static final Log _log;
    private static final int MAX_SIZE = 4;
    private int _orderId;
    private final ArrayList<L1Clan> totalList;
    
    static {
        _log = LogFactory.getLog(L1Alliance.class);
    }
    
    public L1Alliance(int orderId, L1Clan... clanList) {
        this._orderId = orderId;
        this.totalList = new ArrayList(4);
        for (L1Clan alliance : clanList) {
            this.totalList.add(alliance);
        }
    }
    
    public int getOrderId() {
        return this._orderId;
    }
    
    public final ArrayList<L1Clan> getTotalList() {
        return this.totalList;
    }
    
    public final boolean addAlliance(L1Clan l1clan) {
        return this.checkSize() && this.totalList.add(l1clan);
    }
    
    public final boolean checkSize() {
        return this.totalList.size() < 4;
    }
    
    public void sendPacketsAll(String excludingPcName, ServerBasePacket packet) {
        try {
            for (L1Clan clan : this.totalList) {
                if (clan != null) {
                    if (WorldClan.get().getClan(clan.getClanName()) == null) {
                        continue;
                    }
                    else {
                        L1PcInstance[] onlineClanMember;
                        for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                            L1PcInstance listner = onlineClanMember[i];
                            if (!listner.getExcludingList().contains(excludingPcName)) {
                                listner.sendPackets(packet);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            L1Alliance._log.error(e.getLocalizedMessage(), e);
        }
    }
}

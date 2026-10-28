package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.HashMap;
import com.lineage.server.model.L1Party;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class S_PacketBoxParty extends ServerBasePacket
{
    private static final Log _log;
    public static final int MSG_PARTY1 = 104;
    public static final int MSG_PARTY2 = 105;
    public static final int MSG_PARTY3 = 110;
    public static final int MSG_LEADER = 106;
    private byte[] _byte;
    
    static {
        _log = LogFactory.getLog(S_PacketBoxParty.class);
    }
    
    public S_PacketBoxParty(L1PcInstance pc, L1Party party) {
        this._byte = null;
        try {
            HashMap<Integer, L1PcInstance> map = new HashMap();
            map.putAll(party.partyUsers());
            this.writeC(250);
            this.writeC(104);
            this.writeC(map.size() - 1);
            for (Integer key : map.keySet()) {
                L1PcInstance tgpc = map.get(key);
                if (tgpc == null) {
                    continue;
                }
                else if (pc.equals(tgpc)) {
                    continue;
                }
                else {
                    this.writeD(tgpc.getId());
                    this.writeS(tgpc.getName());
                    double nowhp = (double)tgpc.getCurrentHp();
                    double maxhp = (double)tgpc.getMaxHp();
                    this.writeC((int)(nowhp / maxhp * 100.0));
                    this.writeD((int)tgpc.getMapId());
                    this.writeH(tgpc.getX());
                    this.writeH(tgpc.getY());
                }
            }
            map.clear();
        }
        catch (Exception e) {
            S_PacketBoxParty._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public S_PacketBoxParty(L1Party party, L1PcInstance pc) {
        this._byte = null;
        try {
            HashMap<Integer, L1PcInstance> map = new HashMap();
            map.putAll(party.partyUsers());
            this.writeC(250);
            this.writeC(110);
            this.writeC(map.size() - 1);
            for (Integer key : map.keySet()) {
                L1PcInstance tgpc = map.get(key);
                if (tgpc == null) {
                    continue;
                }
                else if (pc.equals(tgpc)) {
                    continue;
                }
                else {
                    this.writeD(tgpc.getId());
                    this.writeD((int)tgpc.getMapId());
                    this.writeH(tgpc.getX());
                    this.writeH(tgpc.getY());
                }
            }
            map.clear();
        }
        catch (Exception e) {
            S_PacketBoxParty._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public S_PacketBoxParty(L1PcInstance tgpc) {
        this._byte = null;
        try {
            this.writeC(250);
            this.writeC(105);
            this.writeD(tgpc.getId());
            this.writeS(tgpc.getName());
            double nowhp = (double)tgpc.getCurrentHp();
            double maxhp = (double)tgpc.getMaxHp();
            this.writeC((int)(nowhp / maxhp * 100.0));
            this.writeD((int)tgpc.getMapId());
            this.writeH(tgpc.getX());
            this.writeH(tgpc.getY());
        }
        catch (Exception e) {
            S_PacketBoxParty._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public S_PacketBoxParty(int objid, String name) {
        this._byte = null;
        try {
            this.writeC(250);
            this.writeC(106);
            this.writeD(objid);
            this.writeS(name);
        }
        catch (Exception e) {
            S_PacketBoxParty._log.error(e.getLocalizedMessage(), e);
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

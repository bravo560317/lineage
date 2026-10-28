package com.lineage.data.npc.mob;

import java.util.Iterator;
import com.lineage.server.model.L1Party;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import java.io.IOException;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class MobTeleport extends NpcExecutor
{
    private static final Log _log;
    private int _locx;
    private int _locy;
    private int _mapid;
    private boolean _party;
    
    static {
        _log = LogFactory.getLog(MobTeleport.class);
    }
    
    private MobTeleport() {
        this._locx = 0;
        this._locy = 0;
        this._mapid = 0;
        this._party = false;
    }
    
    public static NpcExecutor get() {
        return new MobTeleport();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && this._locx != 0 && this._locy != 0) {
                MobTeleport.M_teleport teleport = new MobTeleport.M_teleport(pc);
                teleport.stsrt_cmd();
            }
            return pc;
        }
        catch (Exception e) {
            MobTeleport._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._locx = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._locy = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._mapid = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this._party = Boolean.parseBoolean(set[4]);
        }
        catch (Exception ex4) {}
    }
    
    private class M_teleport implements Runnable
    {
        private final L1PcInstance _pc;
        
        private M_teleport(L1PcInstance pc) {
            this._pc = pc;
        }
        
        private void stsrt_cmd() throws IOException {
            GeneralThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            try {
                this._pc.sendPackets(new S_PacketBoxGree(1));
                this._pc.sendPackets(new S_PacketBoxGree("$ 5秒後將會被傳送!"));
                Thread.sleep(1000L);
                this._pc.sendPackets(new S_PacketBoxGree("$ 4秒後將會被傳送!"));
                Thread.sleep(1000L);
                this._pc.sendPackets(new S_PacketBoxGree("$ 3秒後將會被傳送!"));
                Thread.sleep(1000L);
                this._pc.sendPackets(new S_PacketBoxGree("$ 2秒後將會被傳送!"));
                Thread.sleep(1000L);
                this._pc.sendPackets(new S_PacketBoxGree("$ 1秒後將會被傳送!"));
                Thread.sleep(1000L);
                this._pc.sendPackets(new S_PacketBoxGree("$ "));
                L1Teleport.teleport(this._pc, MobTeleport.this._locx, MobTeleport.this._locy, (short)MobTeleport.this._mapid, 5, true);
                if (MobTeleport.this._party) {
                    L1Party party = this._pc.getParty();
                    if (party != null) {
                        for (L1PcInstance otherPc : party.partyUsers().values()) {
                            if (otherPc.getId() != party.getLeaderID() && this._pc.getMapId() == otherPc.getMapId()) {
                                L1Teleport.teleport(otherPc, MobTeleport.this._locx, MobTeleport.this._locy, (short)MobTeleport.this._mapid, 5, true);
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                MobTeleport._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}

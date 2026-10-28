package com.lineage.data.event.gambling;

import com.lineage.server.serverpackets.S_MoveCharPacket;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.datatables.NpcTable;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.types.Point;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.Log;

public class GamblingNpc implements Runnable
{
    private static final Log _log;
    private Gambling _gambling;
    private L1NpcInstance _npc;
    private boolean _isOver;
    private Point[] _tgLoc;
    private int _xId;
    private int _sId;
    private long _adena;
    private double _rate;
    private Point _loc;
    private Random _random;
    private int _randomTime;
    private static final byte[] HEADING_TABLE_X;
    private static final byte[] HEADING_TABLE_Y;
    private static final int[][] xx;
    
    static {
        _log = LogFactory.getLog(GamblingNpc.class);
        HEADING_TABLE_X = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        HEADING_TABLE_Y = new byte[] { -1, -1, 0, 1, 1, 1, 0, -1 };
        xx = new int[5][1];
    }
    
    public GamblingNpc(Gambling gambling) {
        this._isOver = false;
        this._sId = 1;
        this._random = new Random();
        this._gambling = gambling;
    }
    
    public Gambling get_gambling() {
        return this._gambling;
    }
    
    public void set_rate(double rate) {
        this._rate = rate;
    }
    
    public double get_rate() {
        return this._rate;
    }
    
    public void add_adena(long adena) {
        this._adena += adena;
    }
    
    public long get_adena() {
        return this._adena;
    }
    
    public void delNpc() {
        this._npc.deleteMe();
    }
    
    public L1NpcInstance get_npc() {
        return this._npc;
    }
    
    public int get_xId() {
        return this._xId;
    }
    
    public void showNpc(int npcid, int i) {
        this._xId = i;
        L1Npc npc = NpcTable.get().getTemplate(npcid);
        if (npc != null) {
            this._tgLoc = GamblingConfig.TGLOC[i];
            int x = this._tgLoc[0].getX();
            int y = this._tgLoc[0].getY();
            short mapid = 4;
            int heading = 6;
            int[] gfxids;
            if (GamblingConfig.ISGFX) {
                gfxids = GamblingConfig.GFX[i];
            }
            else {
                gfxids = GamblingConfig.GFXD[i];
            }
            int gfxid = gfxids[this._random.nextInt(gfxids.length)];
            this._npc = L1SpawnUtil.spawn(npcid, x, y, (short)4, 6, gfxid);
        }
    }
    
    public void getStart() {
        GeneralThreadPool.get().schedule(this, 10L);
    }
    
    @Override
    public void run() {
        try {
            this._loc = this._tgLoc[1];
            while (!this._isOver) {
                if (this._xId == this._gambling.WIN) {
                    this._randomTime = 150;
                }
                else {
                    this._randomTime = 190;
                }
                int ss = 190;
                if (this._random.nextInt(100) < 25) {
                    ss = 150;
                }
                int speed = ss + this._random.nextInt(this._randomTime);
                Thread.sleep((long)speed);
                this.actionStart();
            }
        }
        catch (InterruptedException e) {
            GamblingNpc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void setDirectionMove(int heading) {
        if (heading >= 0) {
            int locx = this._npc.getX();
            int locy = this._npc.getY();
            locx += GamblingNpc.HEADING_TABLE_X[heading];
            locy += GamblingNpc.HEADING_TABLE_Y[heading];
            this._npc.setHeading(heading);
            this._npc.setX(locx);
            this._npc.setY(locy);
            this._npc.broadcastPacketAll(new S_MoveCharPacket(this._npc));
        }
    }
    
    private void actionStart() {
        int x = this._loc.getX();
        int y = this._loc.getY();
        try {
            int dir = this._npc.targetDirection(x, y);
            this.setDirectionMove(dir);
            if (this._npc.getLocation().getTileLineDistance(this._loc) < 2) {
                this._loc = this._tgLoc[this._sId];
                ++this._sId;
            }
        }
        catch (Exception e) {
            if (this._gambling.get_oneNpc() == null) {
                this._gambling.set_oneNpc(this);
                int[] array = GamblingNpc.xx[this._xId];
                int n = 0;
                ++array[n];
            }
            int dir2 = this._npc.targetDirection(x, y);
            this.setDirectionMove(dir2);
            this._isOver = true;
        }
    }
}

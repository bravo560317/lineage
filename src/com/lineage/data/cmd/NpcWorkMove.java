package com.lineage.data.cmd;

import com.lineage.server.serverpackets.S_MoveCharPacket;
import com.lineage.server.types.Point;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.Log;

public class NpcWorkMove
{
    private static final Log _log;
    private static final byte[] HEADING_TABLE_X;
    private static final byte[] HEADING_TABLE_Y;
    private L1NpcInstance _npc;
    
    static {
        _log = LogFactory.getLog(NpcWorkMove.class);
        HEADING_TABLE_X = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        HEADING_TABLE_Y = new byte[] { -1, -1, 0, 1, 1, 1, 0, -1 };
    }
    
    public NpcWorkMove(L1NpcInstance npc) {
        this._npc = npc;
    }
    
    public boolean actionStart(Point point) {
        int x = point.getX();
        int y = point.getY();
        try {
            int dir = this._npc.targetDirection(x, y);
            this.setDirectionMove(dir);
            if (this._npc.getLocation().getTileLineDistance(point) == 0) {
                return false;
            }
        }
        catch (Exception e) {
            NpcWorkMove._log.error(e.getLocalizedMessage(), e);
        }
        return true;
    }
    
    private void setDirectionMove(int heading) {
        int locx = this._npc.getX();
        int locy = this._npc.getY();
        locx += NpcWorkMove.HEADING_TABLE_X[heading];
        locy += NpcWorkMove.HEADING_TABLE_Y[heading];
        this._npc.setHeading(heading);
        this._npc.setX(locx);
        this._npc.setY(locy);
        this._npc.broadcastPacketAll(new S_MoveCharPacket(this._npc));
    }
}

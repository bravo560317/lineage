package com.lineage.server.templates;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.logging.Logger;

public class L1DoorGfx
{
    private static Logger _log;
    private final int _gfxId;
    private final int _direction;
    private final int _rightEdgeOffset;
    private final int _leftEdgeOffset;
    
    static {
        L1DoorGfx._log = Logger.getLogger(L1DoorGfx.class.getName());
    }
    
    public L1DoorGfx(int gfxId, int direction, int rightEdgeOffset, int leftEdgeOffset) {
        this._gfxId = gfxId;
        this._direction = direction;
        this._rightEdgeOffset = rightEdgeOffset;
        this._leftEdgeOffset = leftEdgeOffset;
    }
    
    public int getGfxId() {
        return this._gfxId;
    }
    
    public int getDirection() {
        return this._direction;
    }
    
    public int getRightEdgeOffset() {
        return this._rightEdgeOffset;
    }
    
    public int getLeftEdgeOffset() {
        return this._leftEdgeOffset;
    }
    
    public static L1DoorGfx findByGfxId(int gfxId) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM door_gfxs WHERE gfxid = ?");
            pstm.setInt(1, gfxId);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;
            }
            else {
                int id = rs.getInt("gfxid");
                int dir = rs.getInt("direction");
                int rEdge = rs.getInt("right_edge_offset");
                int lEdge = rs.getInt("left_edge_offset");
                return new L1DoorGfx(id, dir, rEdge, lEdge);
            }
        }
        catch (SQLException e) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return null;
    }
}

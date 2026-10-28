package com.lineage.server.serverpackets;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.lineage.DatabaseFactory;

public class S_PledgeWarehouseHistory extends ServerBasePacket
{
    private static final String S_PledgeWarehouseHistory = "[S] S_PledgeWarehouseHistory";
    private byte[] _byte;
    
    public S_PledgeWarehouseHistory(int clanId) {
        this._byte = null;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_warehouse_history WHERE clan_id=? AND record_time < ?");
            pstm.setInt(1, clanId);
            pstm.setTimestamp(2, new Timestamp(System.currentTimeMillis() - 259200000L));
            pstm.execute();
            pstm.close();
            pstm = con.prepareStatement("SELECT * FROM clan_warehouse_history WHERE clan_id=? ORDER BY id DESC");
            pstm.setInt(1, clanId);
            rs = pstm.executeQuery();
            rs.last();
            int rowcount = rs.getRow();
            rs.beforeFirst();
            this.writeC(250);
            this.writeC(117);
            this.writeD(rowcount);
            while (rs.next()) {
                this.writeS(rs.getString("char_name"));
                this.writeC(rs.getInt("type"));
                this.writeS(rs.getString("item_name"));
                this.writeD(rs.getInt("item_count"));
                this.writeD((int)((System.currentTimeMillis() - rs.getTimestamp("record_time").getTime()) / 60000L));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this._bao.toByteArray();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return "[S] S_PledgeWarehouseHistory";
    }
}

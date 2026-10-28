package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.templates.L1PcOtherList;
import com.lineage.server.templates.L1PcOther;
import com.lineage.server.IdFactory;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1FishingPc;
import java.util.ArrayList;
import java.util.Random;

public class DummyFishingTable
{
    private static DummyFishingTable I;
    private static final Random R;
    private static final int[][] CLASS_LIST;
    private ArrayList<L1FishingPc> list;
    private ArrayList<Integer> delays;
    
    static {
        R = new Random();
        CLASS_LIST = new int[][] { { 0, 61, 138, 734, 2786, 6658, 6671 }, { 1, 48, 37, 1186, 2796, 6661, 6650 } };
    }
    
    public static DummyFishingTable get() {
        if (DummyFishingTable.I == null) {
            DummyFishingTable.I = new DummyFishingTable();
        }
        return DummyFishingTable.I;
    }
    
    private DummyFishingTable() {
        this.list = new ArrayList();
        this.delays = new ArrayList();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `dummy_fishing`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1FishingPc pc = new L1FishingPc();
                pc.setId(IdFactory.get().nextId());
                pc.setType((int)rs.getByte("type"));
                pc.set_sex(rs.getBoolean("sex") ? 1 : 0);
                pc.setClassId(DummyFishingTable.CLASS_LIST[pc.get_sex()][pc.getType()]);
                pc.setLevel(rs.getByte("level") & 0xFF);
                pc.setName(rs.getString("name"));
                pc.setLawful((int)rs.getShort("lawful"));
                pc.setTitle((pc.getLevel() >= 40) ? rs.getString("title") : "");
                pc.set_other(new L1PcOther());
                pc.set_otherList(new L1PcOtherList(pc));
                int min_interval = rs.getInt("min_interval");
                int max_interval = rs.getInt("max_interval");
                int t = (DummyFishingTable.R.nextInt(1 + max_interval - min_interval) + min_interval) * 1000;
                this.list.add(pc);
                this.delays.add(t);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            SQLUtil.close(rs, pstm, con);
        }
        GeneralThreadPool.get().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < DummyFishingTable.this.list.size(); ++i) {
                    try {
                        Thread.sleep((long)(int)(Integer)DummyFishingTable.this.delays.get(i));
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    (DummyFishingTable.this.list.get(i)).join();
                }
            }
        });
    }
}

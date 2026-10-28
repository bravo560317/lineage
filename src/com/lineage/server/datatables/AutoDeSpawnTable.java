package com.lineage.server.datatables;

import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.templates.DeName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class AutoDeSpawnTable {

    private static final Random _random = new Random();
    private static final ArrayList<DeName> _deNames = new ArrayList<DeName>();

    public static void load() {
        loadDeNames();

        try (Connection conn = DatabaseFactory.get().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM auto_spawn_de ORDER BY id ASC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                final int locx1 = rs.getInt("locx1");
                final int locx2 = rs.getInt("locx2");
                final int locy1 = rs.getInt("locy1");
                final int locy2 = rs.getInt("locy2");
                final int map = rs.getInt("spawn_map");
                final int delay = rs.getInt("delay");
                // 讀取生成數量，如果沒有該欄位或出錯，預設為 1
                int count = 1;
                try {
                    count = rs.getInt("count");
                } catch (Exception e) {
                    // 忽略錯誤，維持預設值 1
                }
                // 根據指定數量重複生成
                for (int i = 0; i < count; i++) {
                    scheduleSpawn(locx1, locx2, locy1, locy2, map, delay);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void scheduleSpawn(
            final int locx1, final int locx2,
            final int locy1, final int locy2,
            final int map, final int delay) { // 移除 count 參數

        try {
            // --- 生成假人 ---
            int x = locx1;
            int y = locy1;

            int xRange = locx2 - locx1;
            int yRange = locy2 - locy1;

            if (xRange > 0) {
                x += _random.nextInt(xRange + 1);
            }
            if (yRange > 0) {
                y += _random.nextInt(yRange + 1);
            }

            DeName deObj = randomDeName();
            if (deObj == null) {
                return;
            }

            L1PcInstance dummyPc = new L1PcInstance();
            dummyPc.setX(x);
            dummyPc.setY(y);
            dummyPc.setMap((short) map);
            dummyPc.setHeading(_random.nextInt(8));

            L1DeInstance npc = L1SpawnUtil.spawn(dummyPc, deObj, 0);

            if (npc != null) {
                // --- 設定假人重生排程 ---
                npc.setHomeX(x);
                npc.setHomeY(y);
                npc.setHeading(dummyPc.getHeading());

                // 如果 delay > 0，則在指定秒數後執行重生
                if (delay > 0) {
                    GeneralThreadPool.get().schedule(new Runnable() {
                        @Override
                        public void run() {
                            // 刪除舊的假人
                            if (npc != null) npc.deleteMe();
                            // 重新呼叫生成與排程方法，形成循環
                            scheduleSpawn(locx1, locx2, locy1, locy2, map, delay);
                        }
                    }, delay * 1000L);
                }
            }

            dummyPc = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadDeNames() {
        _deNames.clear();

        try (Connection conn = DatabaseFactory.get().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM de_name");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int deobjid = rs.getInt("deobjid");
                String name = rs.getString("dename");
                int type = rs.getInt("type");
                int sex = rs.getInt("sex");
                int clanid = rs.getInt("clanid");

                _deNames.add(new DeName(deobjid, name, type, sex, clanid));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DeName randomDeName() {
        if (_deNames.isEmpty()) {
            return null;
        }
        return _deNames.get(_random.nextInt(_deNames.size()));
    }
}

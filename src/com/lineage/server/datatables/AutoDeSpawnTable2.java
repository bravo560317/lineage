package com.lineage.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lineage.DatabaseFactory;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.model.Instance.L1DeInstance2;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.World;

/**
 * 掛網假人自動生成與管理系統
 */
public class AutoDeSpawnTable2 {

    private static final Random _random = new Random();
    // 用於追蹤當前活躍的假人清單
    private static final List<L1DeInstance2> _activeDummies = new ArrayList<>();

    public static void load() {
        try (Connection conn = DatabaseFactory.get().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM auto_spawn_de2");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                final int locx1 = rs.getInt("locx1");
                final int locx2 = rs.getInt("locx2");
                final int locy1 = rs.getInt("locy1");
                final int locy2 = rs.getInt("locy2");
                final int map = rs.getInt("spawn_map");
                final int delay = rs.getInt("delay"); // 假人存活時間(秒)
                int count = rs.getInt("count");

                for (int i = 0; i < count; i++) {
                    spawnDummy(locx1, locx2, locy1, locy2, map, delay);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成單個假人並加入管理清單
     */
    public static void spawnDummy(int x1, int x2, int y1, int y2, int map, int delay) {
        L1Npc template = NpcTable.get().getTemplate(81471);
        if (template == null) return;

        // 計算範圍內隨機座標
        int x = x1 + (x2 > x1 ? _random.nextInt(x2 - x1 + 1) : 0);
        int y = y1 + (y2 > y1 ? _random.nextInt(y2 - y1 + 1) : 0);

        // 建立假人並覆寫 deleteMe 以實現自動補位
        L1DeInstance2 npc = new L1DeInstance2(template) {
            @Override
            public void deleteMe() {
                // 1. 從活躍清單移除
                _activeDummies.remove(this);
                super.deleteMe();
                
                // 2. 隨機 3 到 8 秒後生成新假人 (3000ms - 8000ms)
                int randomDelay = 3000 + _random.nextInt(5001); 
                
                GeneralThreadPool.get().schedule(() -> {
                    spawnDummy(x1, x2, y1, y2, map, delay);
                }, randomDelay);
            }
        };

        // 設定基礎資訊
        npc.setId(IdFactoryNpc.get().nextId());
        npc.setX(x);
        npc.setY(y);
        npc.setHomeX(x);
        npc.setHomeY(y);
        npc.setMap((short) map);
        npc.setHeading(_random.nextInt(8));
        npc.startNpc();

        // 登入世界
        World.get().storeObject(npc);
        World.get().addVisibleObject(npc);
        _activeDummies.add(npc);

        // 設定該假人的存活壽命 (時間到自動觸發 deleteMe)
        GeneralThreadPool.get().schedule(() -> {
            if (npc != null && !npc.isDead()) {
                npc.deleteMe();
            }
        }, delay * 1000L);
    }
}
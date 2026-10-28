package com.lineage.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1MonsterEnhanceInstance;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.utils.SQLUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MonsterEnhanceTable {

    private static final Log _log = LogFactory.getLog(MonsterEnhanceTable.class);
    private static final Map<Integer, L1MonsterEnhanceInstance> _meis = new HashMap<>();

    private static MonsterEnhanceTable _instance;
    private final boolean _initialized;

    public static MonsterEnhanceTable getInstance() {
        if (_instance == null) {
            _instance = new MonsterEnhanceTable();
        }
        return _instance;
    }

    public boolean isInitialized() {
        return _initialized;
    }

    private MonsterEnhanceTable() {
        load();
        _initialized = true;
    }

    public final void load() {

        PerformanceTimer timer = new PerformanceTimer();

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM monster_enhance");
            rs = pstm.executeQuery();

            while (rs.next()) {

                L1MonsterEnhanceInstance mei = new L1MonsterEnhanceInstance();
                int npcId = rs.getInt("npc_id");

                L1Npc npc = NpcTable.get().getTemplate(npcId);
                if (npc == null) {
                    _log.error("在 monster_enhance 中發現無效的 NPC ID: " + npcId);
                    continue;
                }

                mei.setNpcId(npcId);
                mei.setCurrentDc(rs.getInt("death_count"));
                mei.setDcEnhance(rs.getInt("death_enhance"));
                mei.setDcEnhance1(rs.getInt("death_reset"));
                mei.setDcEnhance2(rs.getInt("reset_on_restart"));

                mei.setLevel(rs.getInt("add_level"));
                mei.setHp(rs.getInt("add_hp"));
                mei.setMp(rs.getInt("add_mp"));
                mei.setAc(rs.getInt("add_ac"));
                mei.setStr(rs.getInt("add_str"));
                mei.setDex(rs.getInt("add_dex"));
                mei.setCon(rs.getInt("add_con"));
                mei.setWis(rs.getInt("add_wis"));
                mei.setInt(rs.getInt("add_int"));
                mei.setMr(rs.getInt("add_mr"));
                mei.setHpr(rs.getInt("add_hpr"));

                mei.setDamageReduction(rs.getInt("damage_reduction"));

                // 儲存原始數值
                mei.setOriginalLevel(npc.get_level());
                mei.setOriginalHp(npc.get_hp());
                mei.setOriginalMp(npc.get_mp());
                mei.setOriginalAc(npc.get_ac());
                mei.setOriginalStr(npc.get_str());
                mei.setOriginalDex(npc.get_dex());
                mei.setOriginalCon(npc.get_con());
                mei.setOriginalWis(npc.get_wis());
                mei.setOriginalInt(npc.get_int());
                mei.setOriginalMr(npc.get_mr());
                mei.setOriginalHpr(npc.get_hpr());
                mei.setOriginalDamageReduction(npc.get_damagereduction());

                _meis.put(npcId, mei);
            }

            // ★ 重啟才歸零
            for (L1MonsterEnhanceInstance mei : _meis.values()) {
                if (mei.getDcEnhance2() == 1) {
                    mei.setCurrentDc(0);
                    save(mei);
                }
            }

        } catch (SQLException e) {
            _log.error(e.getLocalizedMessage(), e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }

        _log.info("讀取->MonsterEnhance 資料數量: " + _meis.size() + " (" + timer.get() + "ms)");
    }

    public void save(L1MonsterEnhanceInstance mei) {

        Connection con = null;
        PreparedStatement pstm = null;

        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(
                "UPDATE monster_enhance SET death_count=? WHERE npc_id=?"
            );

            pstm.setInt(1, mei.getCurrentDc());
            pstm.setInt(2, mei.getNpcId());

            pstm.execute();

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        } finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public L1MonsterEnhanceInstance getTemplate(int npcId) {
        return _meis.get(npcId);
    }
}

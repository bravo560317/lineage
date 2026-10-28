package com.lineage.server.datatables;

import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1MagicWeapon;
import java.util.Map;
import org.apache.commons.logging.Log;

public final class ExtraMagicWeaponTable
{
    private static final Log _log;
    private static final Map<Integer, L1MagicWeapon> _magicList;
    private static ExtraMagicWeaponTable _instance;
    
    static {
        _log = LogFactory.getLog(ExtraMagicWeaponTable.class);
        _magicList = new HashMap();
    }
    
    public static ExtraMagicWeaponTable getInstance() {
        if (ExtraMagicWeaponTable._instance == null) {
            ExtraMagicWeaponTable._instance = new ExtraMagicWeaponTable();
        }
        return ExtraMagicWeaponTable._instance;
    }
    
    public final void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM weapon_magic_skill ORDER BY 道具編號");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int item_id = rs.getInt("道具編號");
                String skill_name = rs.getString("顯示武器名稱");
                int success_random = rs.getInt("強化成功機率");
                int max_use_time = rs.getInt("附加武器時間");
                String success_msg = rs.getString("附加成功訊息");
                String failure_msg = rs.getString("附加失敗訊息");
                int probability = rs.getInt("發動機率");
                boolean isLongRange = rs.getBoolean("遠距武器發動率");
                int fixDamage = rs.getInt("固定傷害");
                int randomDamage = rs.getInt("隨機傷害");
                double doubleDmgValue = (double)rs.getInt("暴擊倍率");
                int gfxId = rs.getInt("特效編號");
                boolean gfxIdTarget = rs.getBoolean("目標對象");
                String gfxIdOtherLocStr = rs.getString("額外發動特效座標");
                List<int[]> gfxIdOtherLoc = null;
                if (gfxIdOtherLocStr != null && !gfxIdOtherLocStr.isEmpty()) {
                    gfxIdOtherLoc = new ArrayList();
                    gfxIdOtherLocStr = gfxIdOtherLocStr.replace(" ", "");
                    String[] split;
                    for (int length = (split = gfxIdOtherLocStr.split(",")).length, i = 0; i < length; ++i) {
                        String value = split[i];
                        String[] value2 = value.split("/");
                        gfxIdOtherLoc.add(new int[] { Integer.parseInt(value2[0]), Integer.parseInt(value2[1]) });
                    }
                }
                int area = rs.getInt("範圍傷害");
                boolean arrowType = rs.getBoolean("飛行效果開關");
                int effectId = rs.getInt("額外技能(skill)");
                int effectTime = rs.getInt("額外技能時間");
                int attr = rs.getInt("傷害附加屬性");
                int hpAbsorb = rs.getInt("吸血量");
                int mpAbsorb = rs.getInt("吸魔量");
                int steps = rs.getInt("階段");
                L1MagicWeapon magicStone = new L1MagicWeapon(item_id, skill_name, success_random, max_use_time, success_msg, failure_msg, probability, isLongRange, fixDamage, randomDamage, doubleDmgValue, gfxId, gfxIdTarget, gfxIdOtherLoc, area, arrowType, effectId, effectTime, attr, hpAbsorb, mpAbsorb, steps);
                int index = item_id * 100 + steps;
                ExtraMagicWeaponTable._magicList.put(index, magicStone);
            }
        }
        catch (SQLException e) {
            ExtraMagicWeaponTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ExtraMagicWeaponTable._log.info("讀取->武器魔法DIY資料數量: " + ExtraMagicWeaponTable._magicList.size() + "(" + timer.get() + "ms)");
    }
    
    public final L1MagicWeapon get(int id, int steps) {
        int index = id * 100 + steps;
        return ExtraMagicWeaponTable._magicList.get((Integer)index);
    }
}

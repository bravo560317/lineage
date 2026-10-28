package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ItemSpecialAttribute;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class ItemSpecialAttributeTable
{
    private static final Log _log;
    private static ItemSpecialAttributeTable _instance;
    private static final HashMap<Integer, L1ItemSpecialAttribute> _atrrList;
    
    static {
        _log = LogFactory.getLog(ItemSpecialAttributeTable.class);
        _atrrList = new HashMap();
    }
    
    public static ItemSpecialAttributeTable get() {
        if (ItemSpecialAttributeTable._instance == null) {
            ItemSpecialAttributeTable._instance = new ItemSpecialAttributeTable();
        }
        return ItemSpecialAttributeTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `炫色二_素質設定`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("流水號");
                String colour = rs.getString("顏色代號");
                String name = rs.getString("頭銜");
                int dmg_small = rs.getInt("最小攻擊力");
                int dmg_large = rs.getInt("最大攻擊力");
                int hitmodifier = rs.getInt("近戰命中");
                int dmgmodifier = rs.getInt("額外攻擊力");
                int add_str = rs.getInt("力量");
                int add_con = rs.getInt("體質");
                int add_dex = rs.getInt("敏捷");
                int add_int = rs.getInt("智力");
                int add_wis = rs.getInt("精神");
                int add_cha = rs.getInt("魅力");
                int add_hp = rs.getInt("血量");
                int add_mp = rs.getInt("魔力");
                int add_hpr = rs.getInt("回血量");
                int add_mpr = rs.getInt("回魔量");
                int add_sp = rs.getInt("魔攻");
                int add_m_def = rs.getInt("魔防");
                int add_rand = rs.getInt("獲取的機率");
                int drain_min_hp = rs.getInt("最小吸血量");
                int drain_max_hp = rs.getInt("最大吸血量");
                int drain_hp_rand = rs.getInt("發動吸血機率");
                int drain_min_mp = rs.getInt("最小吸魔量");
                int drain_max_mp = rs.getInt("最大吸魔量");
                int drain_mp_rand = rs.getInt("發動吸魔機率");
                int skill_rand = rs.getInt("魔法施展機率");
                int skill_gfxid = rs.getInt("施展魔法gfxid");
                int skill_dmg = rs.getInt("魔法傷害");
                int add物理格檔 = rs.getInt("物理格檔");
                int add魔法格檔 = rs.getInt("魔法格檔");
                int pvp_dmg = rs.getInt("PVP增傷");
                int pvp_redmg = rs.getInt("PVP減傷");
                int potion_heal = rs.getInt("藥水恢復增加");
                int dmgR = rs.getInt("物理減傷");
                String msg = rs.getString("廣播");
                String msg2 = rs.getString("文字顯示");
                L1ItemSpecialAttribute attr = new L1ItemSpecialAttribute();
                attr.set_id(id);
                attr.set_colour(colour);
                attr.set_name(name);
                attr.set_dmg_small(dmg_small);
                attr.set_dmg_large(dmg_large);
                attr.set_hitmodifier(hitmodifier);
                attr.set_dmgmodifier(dmgmodifier);
                attr.set_add_str(add_str);
                attr.set_add_con(add_con);
                attr.set_add_dex(add_dex);
                attr.set_add_int(add_int);
                attr.set_add_wis(add_wis);
                attr.set_add_cha(add_cha);
                attr.set_add_hp(add_hp);
                attr.set_add_mp(add_mp);
                attr.set_add_hpr(add_hpr);
                attr.set_add_mpr(add_mpr);
                attr.set_add_sp(add_sp);
                attr.set_add_m_def(add_m_def);
                attr.set_add_rand(add_rand);
                attr.set_add_drain_min_hp(drain_min_hp);
                attr.set_add_drain_max_hp(drain_max_hp);
                attr.set_drain_hp_rand(drain_hp_rand);
                attr.set_add_drain_min_mp(drain_min_mp);
                attr.set_add_drain_max_mp(drain_max_mp);
                attr.set_drain_mp_rand(drain_mp_rand);
                attr.set_add_skill_rand(skill_rand);
                attr.set_add_skill_gfxid(skill_gfxid);
                attr.set_add_skill_dmg(skill_dmg);
                attr.add物理格檔(add物理格檔);
                attr.add魔法格檔(add魔法格檔);
                attr.set_add_pvp_dmg(pvp_dmg);
                attr.set_add_pvp_redmg(pvp_redmg);
                attr.set_add_potion_heal(potion_heal);
                attr.set_msg(msg);
                attr.set_msg1(msg2);
                attr.set_add_dmgR(dmgR);
                ItemSpecialAttributeTable._atrrList.put(id, attr);
            }
        }
        catch (SQLException e) {
            ItemSpecialAttributeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        ItemSpecialAttributeTable._log.info("讀取->炫色二_素質設定數量: " + ItemSpecialAttributeTable._atrrList.size() + "(" + timer.get() + "ms)");
    }
    
    public L1ItemSpecialAttribute getAttrId(int id) {
        return ItemSpecialAttributeTable._atrrList.get((Integer)id);
    }
}

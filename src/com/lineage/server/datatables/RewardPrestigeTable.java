package com.lineage.server.datatables;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.pr_type_name;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1RewardPrestige;
import java.util.Map;
import org.apache.commons.logging.Log;

public class RewardPrestigeTable
{
    private static final Log _log;
    private static Map<Integer, L1RewardPrestige> _array;
    public static boolean START;
    private static RewardPrestigeTable _instance;
    
    static {
        _log = LogFactory.getLog(RewardPrestigeTable.class);
        RewardPrestigeTable._array = new HashMap();
        RewardPrestigeTable.START = false;
    }
    
    public static RewardPrestigeTable get() {
        if (RewardPrestigeTable._instance == null) {
            RewardPrestigeTable._instance = new RewardPrestigeTable();
        }
        return RewardPrestigeTable._instance;
    }
    
    private RewardPrestigeTable() {
        this.getData();
        RewardPrestigeTable._log.info("威望等級獎勵->" + RewardPrestigeTable._array.size());
        if (RewardPrestigeTable._array.size() <= 0) {
            RewardPrestigeTable._array.clear();
            RewardPrestigeTable._array = null;
        }
        else {
            RewardPrestigeTable.START = true;
        }
    }
    
    private void getData() {
        Connection cn = null;
        Statement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.createStatement();
            rs = ps.executeQuery("SELECT * FROM w_威望設置");
            while (rs.next()) {
                int id = rs.getInt("id");
                int prestige_min = rs.getInt("威望_最小值");
                int prestige_max = rs.getInt("威望_最大值");
                String title = rs.getString("title");
                int MaxHp = rs.getInt("MaxHp");
                int MaxMp = rs.getInt("MaxMp");
                int Dmgup = rs.getInt("Dmgup");
                int BowDmgup = rs.getInt("BowDmgup");
                int Hitup = rs.getInt("Hitup");
                int BowHitup = rs.getInt("BowHitup");
                int Mr = rs.getInt("Mr");
                int Sp = rs.getInt("Sp");
                int Str = rs.getInt("Str");
                int Dex = rs.getInt("Dex");
                int Int = rs.getInt("Int");
                int Con = rs.getInt("Con");
                int Wis = rs.getInt("Wis");
                int Cha = rs.getInt("Cha");
                int ReductionDmg = rs.getInt("ReductionDmg");
                int magic_reduction = rs.getInt("magic_reduction");
                int gfx_id = rs.getInt("gfxid");
                int gfx_time = rs.getInt("gfxid_time");
                L1RewardPrestige value = new L1RewardPrestige();
                value.set_prestige_min(prestige_min);
                value.set_prestige_max(prestige_max);
                if (title != null && !title.equalsIgnoreCase("") && !title.equalsIgnoreCase("null")) {
                    value.set_title(title);
                }
                value.set_MaxHp(MaxHp);
                value.set_MaxMp(MaxMp);
                value.set_Dmgup(Dmgup);
                value.set_BowDmgup(BowDmgup);
                value.set_Hitup(Hitup);
                value.set_BowHitup(BowHitup);
                value.set_Mr(Mr);
                value.set_Sp(Sp);
                value.set_Str(Str);
                value.set_Dex(Dex);
                value.set_Int(Int);
                value.set_Con(Con);
                value.set_Wis(Wis);
                value.set_Cha(Cha);
                value.set_ReductionDmg(ReductionDmg);
                value.set_magic_reduction(magic_reduction);
                value.set_gfx_id(gfx_id);
                value.set_gfx_time(gfx_time);
                RewardPrestigeTable._array.put(id, value);
            }
        }
        catch (SQLException e) {
            RewardPrestigeTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public int getLv(int prestige) {
        if (RewardPrestigeTable.START) {
            for (Integer id : RewardPrestigeTable._array.keySet()) {
                L1RewardPrestige v = RewardPrestigeTable._array.get(id);
                if (prestige >= v.get_prestige_min() && prestige <= v.get_prestige_max()) {
                    return id;
                }
            }
        }
        return 0;
    }
    
    public void addPrestige(L1PcInstance pc) {
        if (!RewardPrestigeTable.START) {
            return;
        }
        else {
            StringBuilder msg = new StringBuilder();
            L1RewardPrestige v = RewardPrestigeTable._array.get((Integer)pc.getPrestigeLv());
            if (v.get_MaxHp() != 0) {
                pc.addMaxHp(v.get_MaxHp());
                msg.append("\n" + pr_type_name._1 + "獎勵: 血量增加 +");
                msg.append(v.get_MaxHp());
            }
            if (v.get_MaxMp() > 0) {
                pc.addMaxMp(v.get_MaxMp());
                msg.append("\n" + pr_type_name._1 + "獎勵: 魔量增加 +");
                msg.append(v.get_MaxMp());
            }
            if (v.get_Dmgup() > 0) {
                pc.addDmgup(v.get_Dmgup());
                msg.append("\n" + pr_type_name._1 + "獎勵: 近戰物攻 +");
                msg.append(v.get_Dmgup());
            }
            if (v.get_BowDmgup() > 0) {
                pc.addBowDmgup(v.get_BowDmgup());
                msg.append("\n" + pr_type_name._1 + "獎勵: 遠戰物攻 +");
                msg.append(v.get_BowDmgup());
            }
            if (v.get_Hitup() > 0) {
                pc.addHitup(v.get_Hitup());
                msg.append("\n" + pr_type_name._1 + "獎勵: 近戰命中 +");
                msg.append(v.get_Hitup());
            }
            if (v.get_BowHitup() > 0) {
                pc.addBowHitup(v.get_BowHitup());
                msg.append("\n" + pr_type_name._1 + "獎勵: 遠戰命中 +");
                msg.append(v.get_BowHitup());
            }
            if (v.get_Mr() > 0) {
                pc.addMr(v.get_Mr());
                msg.append("\n" + pr_type_name._1 + "獎勵: 抗魔 +");
                msg.append(v.get_Mr());
            }
            if (v.get_Sp() > 0) {
                pc.addSp(v.get_Sp());
                msg.append("\n" + pr_type_name._1 + "獎勵: 魔攻 +");
                msg.append(v.get_Sp());
            }
            if (v.get_Str() > 0) {
                pc.addStr(v.get_Str());
                msg.append("\n" + pr_type_name._1 + "獎勵: 力量 +");
                msg.append(v.get_Str());
            }
            if (v.get_Dex() > 0) {
                pc.addDex(v.get_Dex());
                msg.append("\n" + pr_type_name._1 + "獎勵: 敏捷 +");
                msg.append(v.get_Dex());
            }
            if (v.get_Int() > 0) {
                pc.addInt(v.get_Int());
                msg.append("\n" + pr_type_name._1 + "獎勵: 智力 +");
                msg.append(v.get_Int());
            }
            if (v.get_Cha() > 0) {
                pc.addCha(v.get_Cha());
                msg.append("\n" + pr_type_name._1 + "獎勵: 魅力 +");
                msg.append(v.get_Cha());
            }
            if (v.get_Con() > 0) {
                pc.addCon(v.get_Con());
                msg.append("\n" + pr_type_name._1 + "獎勵: 體值 +");
                msg.append(v.get_Con());
            }
            if (v.get_Wis() > 0) {
                pc.addWis(v.get_Wis());
                msg.append("\n" + pr_type_name._1 + "獎勵: 精神 +");
                msg.append(v.get_Wis());
            }
            if (v.get_ReductionDmg() > 0) {
                pc.addClan_ReductionDmg(v.get_ReductionDmg());
                msg.append("\n物理減傷: 物理減免 +");
                msg.append(v.get_ReductionDmg());
            }
            if (v.get_magic_reduction() > 0) {
                pc.add_Clanmagic_reduction_dmg(v.get_magic_reduction());
                msg.append("\n魔法減傷: 魔法減免 +");
                msg.append(v.get_magic_reduction());
            }
            if (v.get_gfx_id() != 0 && v.get_gfx_time() != 0) {
                pc.startPcRewardPrestigeGfxTimer(v.get_gfx_id(), v.get_gfx_time());
            }
            if (msg != null) {
                pc.sendPackets(new S_ServerMessage(msg.toString(), 11));
            }
            return;
        }
    }
    
    public void removePrestige(L1PcInstance pc) {
        if (!RewardPrestigeTable.START) {
            return;
        }
        else {
            L1RewardPrestige v = RewardPrestigeTable._array.get((Integer)pc.getPrestigeLv());
            if (v == null) {
                return;
            }
            else {
                if (v.get_MaxHp() != 0) {
                    pc.addMaxHp(-v.get_MaxHp());
                }
                if (v.get_MaxMp() > 0) {
                    pc.addMaxMp(-v.get_MaxMp());
                }
                if (v.get_Dmgup() > 0) {
                    pc.addDmgup(-v.get_Dmgup());
                }
                if (v.get_BowDmgup() > 0) {
                    pc.addBowDmgup(-v.get_BowDmgup());
                }
                if (v.get_Hitup() > 0) {
                    pc.addHitup(-v.get_Hitup());
                }
                if (v.get_BowHitup() > 0) {
                    pc.addBowHitup(-v.get_BowHitup());
                }
                if (v.get_Mr() > 0) {
                    pc.addMr(-v.get_Mr());
                }
                if (v.get_Sp() > 0) {
                    pc.addSp(-v.get_Sp());
                }
                if (v.get_Str() > 0) {
                    pc.addStr(-v.get_Str());
                }
                if (v.get_Dex() > 0) {
                    pc.addDex(-v.get_Dex());
                }
                if (v.get_Int() > 0) {
                    pc.addInt(-v.get_Int());
                }
                if (v.get_Cha() > 0) {
                    pc.addCha(-v.get_Cha());
                }
                if (v.get_Con() > 0) {
                    pc.addCon(-v.get_Con());
                }
                if (v.get_Wis() > 0) {
                    pc.addWis(-v.get_Wis());
                }
                if (v.get_ReductionDmg() > 0) {
                    pc.addClan_ReductionDmg(-v.get_ReductionDmg());
                }
                if (v.get_magic_reduction() > 0) {
                    pc.add_Clanmagic_reduction_dmg(-v.get_magic_reduction());
                }
                if (v.get_gfx_id() != 0 && v.get_gfx_time() != 0) {
                    pc.stopPcRewardPrestigeGfxTimer();
                }
                return;
            }
        }
    }
    
    public String getTitle(int prestige_lv) {
        return (RewardPrestigeTable._array.get((Integer)prestige_lv)).get_title();
    }
    
    public L1RewardPrestige getSkill(int prestige_lv) {
        return RewardPrestigeTable._array.get((Integer)prestige_lv);
    }
}

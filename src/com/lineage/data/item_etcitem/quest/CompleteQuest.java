package com.lineage.data.item_etcitem.quest;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

public class CompleteQuest extends ItemExecutor {

    private static final Log _log = LogFactory.getLog(CompleteQuest.class);

    private int questId = 0;

    public static ItemExecutor get() {
        return new CompleteQuest();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {

        if (pc == null || item == null) {
            return;
        }

        try {

            if (questId <= 0) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }

            // ★ step >= 2 → 視為已完成
            if (pc.getQuest().get_step(questId) >= 2) {
                 // 給予 1000 金幣
                pc.getInventory().storeItem(40308, 1000);
                pc.sendPackets(new S_ServerMessage("此成就已完成，已將您轉化為金幣(1000)"));
                pc.getInventory().removeItem(item, 1); // 移除成就道具
                return;
            }

            String itemName = item.getItem().getName();

            // ★ 設定任務已完成
            pc.getQuest().set_end(questId);
            pc.getQuest().set_step(questId, 2);

            // ★★★【新增】寫入 NOTE = itemName ★★★
            writeQuestNote(pc.getId(), questId, itemName);

            // 實際套用 DB 獎勵
            String rewardMsg = applyRewardFromDB(pc, questId);

            // 效果音
            pc.sendPackets(new S_SkillSound(pc.getId(), 191));
            pc.broadcastPacketAll(new S_SkillSound(pc.getId(), 191));

            // 移除道具
            pc.getInventory().removeItem(item, 1);

            // 更新畫面
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            pc.sendPackets(new S_SPMR(pc));

            pc.sendPackets(new S_ServerMessage("成就：「" + itemName + "」 完成！"));
            if (!rewardMsg.isEmpty()) {
                pc.sendPackets(new S_ServerMessage("獲得能力：" + rewardMsg));
            }

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * ★ 從 DB 套用固定能力
     */
    private String applyRewardFromDB(L1PcInstance pc, int questId) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        StringBuilder msg = new StringBuilder();

        try {
            conn = DatabaseFactory.get().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM w_npc成就圖鑑獎勵 WHERE quest=?");
            pstmt.setInt(1, questId);
            rs = pstmt.executeQuery();

            if (!rs.next()) return "";

            int str = rs.getInt("Str");
            if (str != 0) { 
                pc.addStr(str); 
                pc.get_other().set_Str(pc.get_other().get_Str() + str);
                msg.append("力量 +").append(str).append("、"); 
            }
            
            int dex = rs.getInt("Dex");
            if (dex != 0) { 
                pc.addDex(dex); 
                pc.get_other().set_Dex(pc.get_other().get_Dex() + dex);
                msg.append("敏捷 +").append(dex).append("、"); 
            }
            
            int con = rs.getInt("Con");
            if (con != 0) { 
                pc.addCon(con); 
                pc.get_other().set_Con(pc.get_other().get_Con() + con);
                msg.append("體質 +").append(con).append("、"); 
            }
            
            int itel = rs.getInt("Int");
            if (itel != 0) { 
                pc.addInt(itel); 
                pc.get_other().set_Int(pc.get_other().get_Int() + itel);
                msg.append("智力 +").append(itel).append("、"); 
            }
            
            int wis = rs.getInt("Wis");
            if (wis != 0) { 
                pc.addWis(wis); 
                pc.get_other().set_Wis(pc.get_other().get_Wis() + wis);
                msg.append("精神 +").append(wis).append("、"); 
            }
            
            int cha = rs.getInt("Cha");
            if (cha != 0) { 
                pc.addCha(cha); 
                pc.get_other().set_Cha(pc.get_other().get_Cha() + cha);
                msg.append("魅力 +").append(cha).append("、"); 
            }

            int hp = rs.getInt("Hp");
            if (hp != 0) {
                pc.addMaxHp(hp);
                pc.get_other().set_Hp(pc.get_other().get_Hp() + hp);
                msg.append("HP +").append(hp).append("、");
            }

            int mp = rs.getInt("Mp");
            if (mp != 0) {
                pc.addMaxMp(mp);
                pc.get_other().set_Mp(pc.get_other().get_Mp() + mp);
                msg.append("MP +").append(mp).append("、");
            }

            int attack = rs.getInt("Attack");
            if (attack != 0) { 
                pc.addDmgup(attack); 
                pc.get_other().set_Attack(pc.get_other().get_Attack() + attack); 
                msg.append("近戰攻擊 +").append(attack).append("、"); 
            }

            int bowAttack = rs.getInt("BowAttack");
            if (bowAttack != 0) { 
                pc.addBowDmgup(bowAttack); 
                pc.get_other().set_BowAttack(pc.get_other().get_BowAttack() + bowAttack); 
                msg.append("遠距攻擊 +").append(bowAttack).append("、"); 
            }

            int hit = rs.getInt("Hit");
            if (hit != 0) { 
                pc.addHitup(hit); 
                pc.get_other().set_Hit(pc.get_other().get_Hit() + hit); 
                msg.append("近戰命中 +").append(hit).append("、"); 
            }

            int bowHit = rs.getInt("BowHit");
            if (bowHit != 0) { 
                pc.addBowHitup(bowHit); 
                pc.get_other().set_BowHit(pc.get_other().get_BowHit() + bowHit); 
                msg.append("遠距命中 +").append(bowHit).append("、"); 
            }

            int mr = rs.getInt("Mr");
            if (mr != 0) { 
                pc.addMr(mr); 
                pc.get_other().set_Mr(pc.get_other().get_Mr() + mr); 
                msg.append("魔防 +").append(mr).append("、"); 
            }

            int hpr = rs.getInt("Hpr");
            if (hpr != 0) { 
                pc.addHpr(hpr); 
                pc.get_other().set_Hpr(pc.get_other().get_Hpr() + hpr); 
                msg.append("HPR +").append(hpr).append("、"); 
            }

            int mpr = rs.getInt("Mpr");
            if (mpr != 0) { 
                pc.addMpr(mpr); 
                pc.get_other().set_Mpr(pc.get_other().get_Mpr() + mpr); 
                msg.append("MPR +").append(mpr).append("、"); 
            }

            int hppotion = rs.getInt("hppotion");
            if (hppotion != 0) { 
                pc.add_hppotion(hppotion); 
                pc.get_other().set_hppotion(pc.get_other().get_hppotion() + hppotion); 
                msg.append("藥水恢復量 +").append(hppotion).append("、"); 
            }

            int exp = rs.getInt("exp");
            if (exp != 0) { 
                pc.add_exp(exp); 
                pc.get_other().set_exp(pc.get_other().get_exp() + exp); 
                msg.append("經驗值 +").append(exp).append("%、"); 
            }

            int weight = rs.getInt("weight");
            if (weight != 0) { 
                pc.addWeightReduction(weight); 
                pc.get_other().set_weight(pc.get_other().get_weight() + weight); 
                msg.append("負重 +").append(weight).append("、"); 
            }

            int reduction = rs.getInt("ReductionDmg");
            if (reduction != 0) { 
                pc.addother_ReductionDmg(reduction); 
                pc.get_other().set_ReductionDmg(pc.get_other().get_ReductionDmg() + reduction); 
                msg.append("傷害減免 +").append(reduction).append("、"); 
            }

            int sp = rs.getInt("Sp");
            if (sp != 0) { 
                pc.addSp(sp); 
                pc.get_other().set_Sp(pc.get_other().get_Sp() + sp); 
                msg.append("魔攻 +").append(sp).append("、"); 
            }

            int ac = rs.getInt("ac");
            if (ac != 0) { 
                pc.addAc(ac); 
                pc.get_other().set_ac(pc.get_other().get_ac() + ac); 
                msg.append("防禦力 +").append(ac).append("、"); 
            }

            int registStun = rs.getInt("regist_stun");
            if (registStun != 0) { 
                pc.addRegistStun(registStun); 
                pc.get_other().set_regist_stun(pc.get_other().get_regist_stun() + registStun); 
                msg.append("暈眩抗性 +").append(registStun).append("、"); 
            }

            int registStone = rs.getInt("regist_stone");
            if (registStone != 0) { 
                pc.addRegistStone(registStone); 
                pc.get_other().set_regist_stone(pc.get_other().get_regist_stone() + registStone); 
                msg.append("石化抗性 +").append(registStone).append("、"); 
            }

            int registSleep = rs.getInt("regist_sleep");
            if (registSleep != 0) { 
                pc.addRegistSleep(registSleep); 
                pc.get_other().set_regist_sleep(pc.get_other().get_regist_sleep() + registSleep); 
                msg.append("睡眠抗性 +").append(registSleep).append("、"); 
            }

            int registFreeze = rs.getInt("regist_freeze");
            if (registFreeze != 0) { 
                pc.add_regist_freeze(registFreeze); 
                pc.get_other().set_regist_freeze(pc.get_other().get_regist_freeze() + registFreeze); 
                msg.append("寒冰抗性 +").append(registFreeze).append("、"); 
            }

            int registSustain = rs.getInt("regist_sustain");
            if (registSustain != 0) { 
                pc.addRegistSustain(registSustain); 
                pc.get_other().set_regist_sustain(pc.get_other().get_regist_sustain() + registSustain); 
                msg.append("支撐抗性 +").append(registSustain).append("、"); 
            }

            int registBlind = rs.getInt("regist_blind");
            if (registBlind != 0) { 
                pc.addRegistBlind(registBlind); 
                pc.get_other().set_regist_blind(pc.get_other().get_regist_blind() + registBlind); 
                msg.append("暗黑抗性 +").append(registBlind).append("、"); 
            }

            int pvpDmg = rs.getInt("PvP_dmg");
            if (pvpDmg != 0) { pc.get_other().set_PvP_dmg(pc.get_other().get_PvP_dmg() + pvpDmg); msg.append("PVP近戰增傷 +").append(pvpDmg).append("、"); }

            int pvpBowDmg = rs.getInt("PvP_bow_dmg");
            if (pvpBowDmg != 0) { pc.get_other().set_PvP_bow_dmg(pc.get_other().get_PvP_bow_dmg() + pvpBowDmg); msg.append("PVP遠距增傷 +").append(pvpBowDmg).append("、"); }

            int pvpHit = rs.getInt("PvP_hit");
            if (pvpHit != 0) { pc.get_other().set_PvP_hit(pc.get_other().get_PvP_hit() + pvpHit); msg.append("PVP近戰命中 +").append(pvpHit).append("、"); }

            int pvpBowHit = rs.getInt("PvP_bow_hit");
            if (pvpBowHit != 0) { pc.get_other().set_PvP_bow_hit(pc.get_other().get_PvP_bow_hit() + pvpBowHit); msg.append("PVP遠距命中 +").append(pvpBowHit).append("、"); }

            int pvpDmgR = rs.getInt("PvP_dmg_r");
            if (pvpDmgR != 0) { pc.get_other().set_PvP_dmg_r(pc.get_other().get_PvP_dmg_r() + pvpDmgR); msg.append("PVP減傷 +").append(pvpDmgR).append("、"); }
            
            int pvpMagicR = rs.getInt("PvP_magic_r");
            if (pvpMagicR != 0) { pc.get_other().set_PvP_magic_r(pc.get_other().get_PvP_magic_r() + pvpMagicR); msg.append("PVP魔防 +").append(pvpMagicR).append("、"); }

            int ranPvpDmgB = rs.getInt("Ran_PvP_dmg_b");
            if (ranPvpDmgB != 0) { pc.get_other().set_Ran_PvP_dmg_b(pc.get_other().get_Ran_PvP_dmg_b() + ranPvpDmgB); msg.append("PVP隨機物理增傷 +").append(ranPvpDmgB).append("、"); }

            double pvpDmgB = rs.getDouble("PvP_dmg_b");
            if (pvpDmgB != 0) { pc.get_other().setPvP_dmg_b(pc.get_other().getPvP_dmg_b() + pvpDmgB); msg.append("PVP物理倍率增傷 +").append(pvpDmgB).append("、"); }

            int ranPvpMagicB = rs.getInt("Ran_PvP_magic_b");
            if (ranPvpMagicB != 0) { pc.get_other().set_Ran_PvP_magic_b(pc.get_other().get_Ran_PvP_magic_b() + ranPvpMagicB); msg.append("PVP隨機魔法增傷 +").append(ranPvpMagicB).append("、"); }

            double pvpMagicB = rs.getDouble("PvP_magic_b");
            if (pvpMagicB != 0) { pc.get_other().setPvP_magic_b(pc.get_other().getPvP_magic_b() + pvpMagicB); msg.append("PVP魔法倍率增傷 +").append(pvpMagicB).append("、"); }

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstmt);
            SQLUtil.close(conn);
        }

        // 去掉最後的 "、"
        if (msg.length() > 1 && msg.charAt(msg.length() - 1) == '、') {
            msg.deleteCharAt(msg.length() - 1);
        }

        return msg.toString();
    }


    /**
     * ★★★ 寫入 NOTE（itemName） ★★★
     */
    private void writeQuestNote(int charId, int questId, String note) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();

            ps = con.prepareStatement("SELECT quest_id FROM character_quests WHERE char_id=? AND quest_id=?");
            ps.setInt(1, charId);
            ps.setInt(2, questId);
            rs = ps.executeQuery();

            boolean exist = rs.next();
            SQLUtil.close(rs);
            SQLUtil.close(ps);

            if (exist) {
                ps = con.prepareStatement("UPDATE character_quests SET note=? WHERE char_id=? AND quest_id=?");
                ps.setString(1, note);
                ps.setInt(2, charId);
                ps.setInt(3, questId);
                ps.execute();
            } else {
                ps = con.prepareStatement(
                        "INSERT INTO character_quests (char_id, quest_id, quest_step, note, mob_count, clean) VALUES (?, ?, ?, ?, 0, 0)");
                ps.setInt(1, charId);
                ps.setInt(2, questId);
                ps.setInt(3, 2); // step=2（完成）
                ps.setString(4, note);
                ps.execute();
            }

        } catch (SQLException e) {
            _log.error("writeQuestNote error: " + e.getLocalizedMessage(), e);
        } finally {
            SQLUtil.close(ps);
            SQLUtil.close(con);
        }
    }

    @Override
    public void set_set(String[] set) {
        try {
            this.questId = Integer.parseInt(set[1]);
        } catch (Exception ignore) {}
    }
}

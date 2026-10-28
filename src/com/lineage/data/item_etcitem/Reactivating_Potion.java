package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Teleport;
import com.lineage.william.logpcpower_new1;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Reactivating_Potion extends ItemExecutor {

    private static final Log _log = LogFactory.getLog(Reactivating_Potion.class);
    private int type1;

    public static ItemExecutor get() {
        return new Reactivating_Potion();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {

        if (pc.getLevel() < ConfigOther.logpclevel) {
            pc.sendPackets(new S_ServerMessage("等級太低以至於無法轉生。"));
            return;
        }

        if (pc.getMeteLevel() == this.type1) {
            pc.sendPackets(new S_ServerMessage("目前轉生限制最高:" + this.type1 + "轉"));
            return;
        }

        // 移除道具
        pc.getInventory().removeItem(item, 1L);

        int pcObjid = pc.getId();

        // ★ 轉生前 HP / MP
        int oldHp = pc.getMaxHp();
        int oldMp = pc.getMaxMp();

        // =====================================================
        // 重置等級（HP / MP 會變成職業初始值）
        // =====================================================
        pc.setExp(1L);
        pc.onChangeExp();
        pc.resetLevel();

        // 轉生點數
        if (ConfigOther.logpcpower) {
            pc.setBonusStats(0);
        } else {
            pc.setBonusStats(49);
        }

        // 增加轉生數
        pc.setMeteLevel(pc.getMeteLevel() + 1);

        pc.resetBaseAc();
        pc.resetBaseMr();
        pc.resetBaseHitup();
        pc.resetBaseDmgup();

        // =====================================================
        // ★ HP最終計算（0 = 保留原本，1~100 = 增加百分比）
        // =====================================================

        int finalHp; 
        if (ConfigOther.logpcresthp == 0) {
            // ✔ 0% → 完全保留
            finalHp = oldHp;
        } else {
            // ✔ 增加百分比
            int bonusHp = oldHp * ConfigOther.logpcresthp / 100;
            finalHp = oldHp + bonusHp;
        }

        // ★ resetLevel() 後的 HP
        int baseHpAfterReset = pc.getMaxHp();

        // ★ 補回差額（使用 addBaseMaxHp）
        int needAddHp = finalHp - baseHpAfterReset;
        if (needAddHp > 0) {
            pc.addBaseMaxHp((short) needAddHp);
        }

        // =====================================================
        // ★ MP 最終計算（同 HP）
        // =====================================================

        int finalMp;
        if (ConfigOther.logpcrestmp == 0) {
            finalMp = oldMp;
        } else {
            int bonusMp = oldMp * ConfigOther.logpcrestmp / 100;
            finalMp = oldMp + bonusMp;
        }

        int baseMpAfterReset = pc.getMaxMp();
        int needAddMp = finalMp - baseMpAfterReset;

        if (needAddMp > 0) {
            pc.addBaseMaxMp((short) needAddMp);
        }

        // ★ 設定當前 HP MP
        pc.setCurrentHp(finalHp);
        pc.setCurrentMp(finalMp);

        // 動畫與套用
        pc.sendPacketsX8(new S_SkillSound(pcObjid, 191));

        pc.resetMeteAbility();
        pc.sendPackets(new S_OwnCharStatus(pc));
        pc.sendPackets(new S_SPMR(pc));
        pc.sendPackets(new S_ServerMessage(822));

        pc.setlogpcpower_SkillCount(pc.getlogpcpower_SkillCount() + ConfigOther.logpctfcount);
        logpcpower_new1.forIntensifyArmor(pc);

        L1Teleport.teleport(pc, 33441, 32809, (short) 4, pc.getHeading(), true);

        if (ConfigOther.logpcallmsg) {
            World.get().broadcastPacketToAll(new S_ServerMessage("\\aH 恭喜玩家【" + pc.getName() + "】"));
        }

        World.get().broadcastPacketToAll(new S_ServerMessage(
            "\\aH 熬夜暴肝，轉生第 " + pc.getMeteLevel() + " 次了！"));

        if (ConfigOther.logpcallmsg) {
            World.get().broadcastPacketToAll(new S_PacketBoxGree(
                "\\fV 恭喜玩家 " + pc.getName() +
                "\\fV 熬夜暴肝，轉生第 " + pc.getMeteLevel() + "\\fV 次了！"
            ));
        }

        try {
            pc.save();
        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void set_set(String[] set) {
        try {
            this.type1 = Integer.parseInt(set[1]);
        } catch (Exception ex) {}
    }
}

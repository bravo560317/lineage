package com.lineage.data.item_etcitem;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.skillmode.SHOCK_STUN;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_ServerMessage;

public class ZpurificationPotion extends ItemExecutor {

    private ZpurificationPotion() {}

    public static ItemExecutor get() {
        return new ZpurificationPotion();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int targetObjId = data[0];
            L1Object obj = World.get().findObject(targetObjId);

            if (obj == null || !(obj instanceof L1Character)) {
                pc.sendPackets(new S_ServerMessage(79)); // 無效目標
                return;
            }

            L1Character target = (L1Character) obj;

            // ✅ 距離限制：必須在 3 格內
            int distance = pc.getLocation().getTileLineDistance(target.getLocation());
            if (distance > 3) {
                pc.sendPackets(new S_ServerMessage("距離太遠，施咒取消。"));
                return;
            }

            // ✅ 成功機率判定（例如 60%）
            int chance = 60; // 你可以自行調整機率
            if (com.lineage.server.utils.Random.nextInt(100) >= chance) {
                pc.sendPackets(new S_ServerMessage("施咒失敗。"));
                return;
            }

            // ✅ 執行 stun 技能效果
            SHOCK_STUN stunSkill = new SHOCK_STUN();
            L1Magic magic = new L1Magic(pc, target); // 傳入攻擊者與目標
            stunSkill.start(pc, target, magic, 0);

        } catch (Exception e) {
            pc.sendPackets(new S_ServerMessage(79));
            e.printStackTrace();
        }
    }
}

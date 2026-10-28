package com.lineage.server.model.c1;

import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C1_Camp extends C1Executor {

    private static final Log _log = LogFactory.getLog(C1_Camp.class);

    private int _int1;
    private int _int2;
    private int _int3;
    private int _int4;
    private int _int5;
    private int _int6;
    private int _int7;
    private int _int8;
    private int _int9;
    private int _int10;

    public static C1Executor get() {
        return new C1_Camp();
    }

    @Override
    public void set_power(
            int int1, int int2, int int3, int int4, int int5,
            int int6, int int7, int int8, int int9, int int10) {

        try {
            this._int1 = int1;   // 近戰攻擊
            this._int2 = int2;   // 遠距攻擊（弓攻）
            this._int3 = int3;   // 近戰命中
            this._int4 = int4;   // 遠距命中
            this._int5 = int5;   // 減傷
            this._int6 = int6;   // AC
            this._int7 = int7;   // SP
            this._int8 = int8;   // MPR
            this._int9 = int9;   // HPR
            this._int10 = int10; // 經驗加成
        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void set_c1(L1PcInstance pc) {
        try {
            // ✔ 正確：近戰攻擊
            pc.addDmgup(this._int1);

            // 🔥 正確：遠距離攻擊 → 弓攻用 BowDmgup()
            pc.addBowDmgup(this._int2);

            // ✔ 命中
            pc.addHitup(this._int3);
            pc.addBowHitup(this._int4);

            pc.addother_ReductionDmg(this._int5);
            pc.addAc(-this._int6);
            pc.addSp(this._int7);
            pc.addMpr(this._int8);
            pc.addHpr(this._int9);
            pc.addExpByArmor((double) this._int10);

            // ✔ 重算角色數值
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void remove_c1(L1PcInstance pc) {
        try {
            // ✔ 近戰攻擊移除
            pc.addDmgup(-this._int1);

            // 🔥 遠距離攻擊移除
            pc.addBowDmgup(-this._int2);

            pc.addHitup(-this._int3);
            pc.addBowHitup(-this._int4);

            pc.addother_ReductionDmg(-this._int5);
            pc.addAc(this._int6);
            pc.addSp(-this._int7);
            pc.addMpr(-this._int8);
            pc.addHpr(-this._int9);
            pc.addExpByArmor((double) (-this._int10));

            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }
}

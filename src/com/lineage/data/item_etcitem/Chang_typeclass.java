package com.lineage.data.item_etcitem;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.serverpackets.S_DelSkill;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class Chang_typeclass extends ItemExecutor
{
    private static Random _random;
    private int _type;
    private int _type1;
    private int _type2;
    
    static {
        Chang_typeclass._random = new Random();
    }
    
    private Chang_typeclass() {
    }
    
    public static ItemExecutor get() {
        return new Chang_typeclass();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc.isCrown()) {
            pc.sendPackets(new S_SystemMessage("王族無法使用轉職功能"));
            return;
        }
        else {
            for (int i = 0; i < 300; ++i) {
                L1Skills skill = SkillsTable.get().getTemplate(i);
                if (skill != null && skill.getSkillLevel() > 0) {
                    pc.sendPackets(new S_DelSkill(pc, i));
                }
                CharSkillReading.get().spellLost(pc.getId(), i);
            }
// 強制脫掉所有裝備
for (L1ItemInstance equip : pc.getInventory().getItems()) {
    if (equip.isEquipped()) {
        pc.getInventory().setEquipped(equip, false);
    }
}
            pc.setClassId(this._type);
            pc.set_sex(this._type1);
            pc.setType(this._type2);
            pc.getInventory().takeoffEquip(945);
            pc.getInventory().removeItem(item, 1L);
            pc.get_other1().set_type1(0);
            pc.get_other1().set_type2(0);
            pc.get_other1().set_type3(0);
            pc.get_other1().set_type4(0);
            pc.get_other1().set_type5(0);
            pc.get_other1().set_type6(0);
            pc.get_other1().set_type7(0);
            pc.get_other1().set_type8(0);
            pc.get_other1().set_type9(0);
            pc.get_other1().set_type10(0);
            pc.get_other1().set_type11(0);
            pc.get_other1().set_type12(0);
            pc.get_other1().set_type13(0);
            pc.get_other1().set_type14(0);
            pc.get_other1().set_type15(0);
            pc.get_other1().set_type16(0);
            pc.get_other1().set_type17(0);
            pc.get_other1().set_type18(0);
            pc.get_other1().set_type19(0);
            pc.get_other1().set_type20(0);
            pc.get_other1().set_type21(0);
            pc.get_other1().set_type22(0);
            pc.get_other1().set_type23(0);
            pc.get_other1().set_type24(0);
            pc.get_other1().set_type25(0);
            pc.get_other1().set_type26(0);
            pc.get_other1().set_type27(0);
            pc.get_other1().set_type28(0);
            pc.get_other1().set_type29(0);
            pc.get_other1().set_type30(null);
            pc.get_other1().set_type31(null);
            pc.get_other1().set_type32(null);
            pc.get_other1().set_type33(null);
            pc.get_other1().set_type34(null);
            pc.get_other1().set_type35(null);
            pc.get_other1().set_type36(null);
            pc.get_other1().set_type37(null);
            pc.get_other1().set_type38(null);
            pc.get_other1().set_type39(null);
            pc.get_other1().set_type40(null);
            pc.get_other1().set_type41(0);
            pc.get_other1().set_type42(0);
            pc.get_other1().set_type43(0);
            pc.get_other1().set_type44(0);
            pc.get_other1().set_type45(0);
            pc.get_other1().set_type46(0);
            pc.get_other1().set_type47(0);
            pc.get_other1().set_type48(0);
            pc.get_other1().set_type49(0);
            pc.get_other1().set_type50(0);
            pc.getQuest().set_step(30677, 1);
            pc.sendPackets(new S_SystemMessage("成功轉換職業,3秒後將會斷線。"));
            try {
                Thread.sleep(3000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            pc.getNetConnection().kick();
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._type = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._type1 = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._type2 = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
    }
}

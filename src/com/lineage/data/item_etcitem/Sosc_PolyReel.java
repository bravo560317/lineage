package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.datatables.PolyTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Sosc_PolyReel extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Sosc_PolyReel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        String text = pc.getText();
        if (text == null) {
            return;
        }
        else {
            pc.setText(null);
            int time = 1800;
            if (item.getBless() == 0) {
                time = 2100;
            }
            if (item.getBless() == 128) {
                time = 2100;
            }
            if (item.getItemId() == 42029) {
                time = 5150;
            }
            int awakeSkillId = pc.getAwakeSkillId();
            if (awakeSkillId == 185 || awakeSkillId == 190 || awakeSkillId == 195) {
                pc.sendPackets(new S_ServerMessage(1384));
                return;
            }
            else {
                L1PolyMorph poly = PolyTable.get().getTemplate(text);
                if (poly != null || text.equals("")) {
                    if (text.equals("")) {
                        L1PolyMorph.undoPoly(pc);
                        pc.setcardspoly(0);
                    }
                    else if (poly.getMinLevel() <= pc.getLevel() || pc.isGm()) {
                        if (poly.getArrowGifx() == 0) {
                            L1PolyMorph.doPoly(pc, poly.getPolyId(), time, 1);
                        }
                        else if (poly.getArrowGifx() != 0 && pc.getQuest().get_step(poly.getArrowGifx()) == 1) {
                            L1PolyMorph.doPoly(pc, poly.getPolyId(), time, 1);
                        }
                    }
                    pc.getInventory().removeItem(item, 1L);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(181));
                }
                return;
            }
        }
    }
}

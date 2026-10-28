package com.lineage.data.item_etcitem.add;

import com.lineage.server.templates.L1ItemHtml;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.ItemHtmlTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Html extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Html();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        L1ItemHtml html = ItemHtmlTable.get().getHtml(itemId);
        if (html == null) {
            return;
        }
        else {
            int quest_id = html.getQuestId();
            int quest_step = html.getQuestStep();
            if (quest_id != 0 && quest_step != 0 && pc.getQuest().get_step(quest_id) != quest_step) {
                return;
            }
            else {
                String htm = html.getHtml();
                if (htm != null) {
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), htm));
                }
                return;
            }
        }
    }
}

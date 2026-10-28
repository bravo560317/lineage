package com.lineage.data.item_etcitem.html;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Lashistory extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Lashistory();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItem().getItemId();
        switch (itemId) {
            case 41019: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory1"));
                break;
            }
            case 41020: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory2"));
                break;
            }
            case 41021: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory3"));
                break;
            }
            case 41022: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory4"));
                break;
            }
            case 41023: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory5"));
                break;
            }
            case 41024: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory6"));
                break;
            }
            case 41025: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory7"));
                break;
            }
            case 41026: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory8"));
                break;
            }
        }
    }
}

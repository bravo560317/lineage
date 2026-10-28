package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Tscroll extends ItemExecutor
{
    private static boolean ALT_TALKINGSCROLLQUEST;
    
    static {
        Tscroll.ALT_TALKINGSCROLLQUEST = true;
    }
    
    public static ItemExecutor get() {
        return new Tscroll();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (Tscroll.ALT_TALKINGSCROLLQUEST) {
            if (pc.getQuest().get_step(39) == 0) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrolla"));
            }
            else if (pc.getQuest().get_step(39) == 1) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollb"));
            }
            else if (pc.getQuest().get_step(39) == 2) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollc"));
            }
            else if (pc.getQuest().get_step(39) == 3) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrolld"));
            }
            else if (pc.getQuest().get_step(39) == 4) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrolle"));
            }
            else if (pc.getQuest().get_step(39) == 5) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollf"));
            }
            else if (pc.getQuest().get_step(39) == 6) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollg"));
            }
            else if (pc.getQuest().get_step(39) == 7) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollh"));
            }
            else if (pc.getQuest().get_step(39) == 8) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrolli"));
            }
            else if (pc.getQuest().get_step(39) == 9) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollj"));
            }
            else if (pc.getQuest().get_step(39) == 10) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollk"));
            }
            else if (pc.getQuest().get_step(39) == 11) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrolll"));
            }
            else if (pc.getQuest().get_step(39) == 12) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollm"));
            }
            else if (pc.getQuest().get_step(39) == 13) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrolln"));
            }
            else if (pc.getQuest().get_step(39) == 255) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollo"));
            }
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tscrollp"));
        }
    }
}

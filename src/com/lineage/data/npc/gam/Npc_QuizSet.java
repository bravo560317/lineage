package com.lineage.data.npc.gam;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.QuizSetTable;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.NpcExecutor;

public class Npc_QuizSet extends NpcExecutor
{
    private final Random _random;
    
    private Npc_QuizSet() {
        this._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new Npc_QuizSet();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (!ConfigAlt.QUIZ_SET_SWITCH || QuizSetTable.GS_showQuiz == null) {
            pc.sendPackets(new S_SystemMessage("題目沒有正常啟動或是正在重置中..."));
            return;
        }
        else {
            if (pc.getQuest().get_step(81245) == 0) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "quiz1"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "quiz4"));
            }
            return;
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (!ConfigAlt.QUIZ_SET_SWITCH || QuizSetTable.GS_showQuiz == null) {
            pc.sendPackets(new S_SystemMessage("題目沒有正常啟動或是正在重置中..."));
            return;
        }
        else if (pc.getLevel() < ConfigAlt.QUIZ_SET_LEVEL) {
            pc.sendPackets(new S_SystemMessage("等級低於[" + ConfigAlt.QUIZ_SET_LEVEL + "]無法進行答題。"));
            return;
        }
        else {
            if (cmd.equals("request show_quiz")) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "quiz2", new String[] { QuizSetTable.GS_showQuiz, QuizSetTable.GS_option[0], QuizSetTable.GS_option[1], QuizSetTable.GS_option[2], QuizSetTable.GS_option[3] }));
            }
            else if (cmd.equals("request optionA")) {
                this.checkResults(pc, npc.getId(), 1);
            }
            else if (cmd.equals("request optionB")) {
                this.checkResults(pc, npc.getId(), 2);
            }
            else if (cmd.equals("request optionC")) {
                this.checkResults(pc, npc.getId(), 3);
            }
            else if (cmd.equals("request optionD")) {
                this.checkResults(pc, npc.getId(), 4);
            }
            return;
        }
    }
    
    private final void checkResults(L1PcInstance pc, int objid, int answer) {
        if (pc.getQuest().get_step(81245) != 0) {
            pc.sendPackets(new S_NPCTalkReturn(objid, "quiz4"));
            return;
        }
        else {
            if (QuizSetTable.GS_answer == answer) {
                int[] itemGroup = ConfigAlt.QUIZ_SET_LIST[this._random.nextInt(ConfigAlt.QUIZ_SET_LIST.length)];
                L1ItemInstance item = ItemTable.get().createItem(itemGroup[0]);
                if (item != null) {
                    item.setCount((long)itemGroup[1]);
                    pc.getQuest().set_step(81245, 1);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_NPCTalkReturn(objid, "quiz5", new String[] { item.getItem().getName(), String.valueOf(item.getCount()) }));
                }
            }
            else {
                pc.getQuest().set_step(81245, 1);
                pc.sendPackets(new S_NPCTalkReturn(objid, "quiz3"));
            }
            return;
        }
    }
}

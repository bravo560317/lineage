package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.quest.DragonKnightLv30_1;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.QuestClass;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.DragonKnightLv45_1;
import com.lineage.data.quest.IllusionistLv15_1;
import com.lineage.data.quest.IllusionistLv30_1;
import com.lineage.data.quest.IllusionistLv45_1;
import com.lineage.data.quest.IllusionistLv50_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Silrein extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Silrein.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Silrein();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein1"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein1"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein1"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein1"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein1"));
            }
            else if (pc.isDragonKnight()) {
                this.isDragonKnight(pc, npc);
            }
            else if (pc.isIllusionist()) {
                this.isIllusionist(pc, npc);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein1"));
            }
        }
        catch (Exception e) {
            Npc_Silrein._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void isIllusionist(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getQuest().isEnd(IllusionistLv50_1.QUEST.get_id())) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein36"));
                return;
            }
            else if (pc.getQuest().isEnd(IllusionistLv45_1.QUEST.get_id())) {
                if (pc.getLevel() >= IllusionistLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(IllusionistLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein27"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein29"));
                            break;
                        }
                        case 2:
                        case 3:
                        case 4: {
                            if (pc.getInventory().checkItem(49206)) {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein35"));
                                break;
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein34"));
                                break;
                            }
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein26"));
                }
                return;
            }
            else if (pc.getQuest().isEnd(IllusionistLv30_1.QUEST.get_id())) {
                if (pc.getLevel() >= IllusionistLv45_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(IllusionistLv45_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein18"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein20"));
                            break;
                        }
                        case 2:
                        case 3: {
                            if (pc.getInventory().checkItem(49201)) {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein50"));
                                return;
                            }
                            else if (pc.getInventory().checkItem(49202)) {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein48"));
                                return;
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein23"));
                                break;
                            }
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein13"));
                }
                return;
            }
            else if (pc.getQuest().isEnd(IllusionistLv15_1.QUEST.get_id())) {
                if (pc.getLevel() >= IllusionistLv30_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(IllusionistLv30_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein11"));
                            break;
                        }
                        case 1:
                        case 2:
                        case 3: {
                            if (pc.getInventory().checkItem(49191)) {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein46"));
                                return;
                            }
                            else if (pc.getInventory().checkItem(49190)) {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein52"));
                                return;
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein14"));
                                break;
                            }
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein5"));
                }
            }
            else if (pc.getLevel() >= IllusionistLv15_1.QUEST.get_questlevel()) {
                switch (pc.getQuest().get_step(IllusionistLv15_1.QUEST.get_id())) {
                    case 0: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein2"));
                        break;
                    }
                    case 1: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein4"));
                        break;
                    }
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein3"));
            }
        }
        catch (Exception e) {
            Npc_Silrein._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void isDragonKnight(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getQuest().isEnd(DragonKnightLv45_1.QUEST.get_id())) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein43"));
                return;
            }
            else if (pc.getLevel() >= DragonKnightLv45_1.QUEST.get_questlevel()) {
                switch (pc.getQuest().get_step(DragonKnightLv45_1.QUEST.get_id())) {
                    case 1: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein37"));
                        break;
                    }
                    case 2: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein38"));
                        break;
                    }
                    case 3: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein40"));
                        break;
                    }
                    default: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein1"));
                        break;
                    }
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein1"));
            }
        }
        catch (Exception e) {
            Npc_Silrein._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isIllusionist()) {
            if (cmd.equalsIgnoreCase("a")) {
                if (pc.getLevel() >= IllusionistLv15_1.QUEST.get_questlevel()) {
                    CreateNewItem.getQuestItem(pc, npc, 49172, 1L);
                    QuestClass.get().startQuest(pc, IllusionistLv15_1.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein6"));
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("b")) {
                if (pc.getLevel() >= IllusionistLv15_1.QUEST.get_questlevel()) {
                    if (CreateNewItem.checkNewItem(pc, new int[] { 40510, 40512, 40511, 49169, 49170 }, new int[] { 1, 1, 1, 10, 1 }) < 1L) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein8"));
                    }
                    else {
                        CreateNewItem.createNewItem(pc, new int[] { 40510, 40512, 40511, 49169, 49170 }, new int[] { 1, 1, 1, 10, 1 }, new int[] { 49121, 269 }, 1L, new int[] { 1, 1 });
                        L1ItemInstance item = pc.getInventory().checkItemX(49172, 1L);
                        if (item != null) {
                            pc.getInventory().removeItem(item, 1L);
                        }
                        QuestClass.get().endQuest(pc, IllusionistLv15_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein7"));
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("c")) {
                if (pc.getQuest().isEnd(IllusionistLv15_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv30_1.QUEST.get_questlevel()) {
                        CreateNewItem.getQuestItem(pc, npc, 49173, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 49179, 1L);
                        QuestClass.get().startQuest(pc, IllusionistLv30_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein12"));
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("d")) {
                if (pc.getQuest().isEnd(IllusionistLv15_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv30_1.QUEST.get_questlevel()) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49191, 1L);
                        if (item != null) {
                            pc.getInventory().removeItem(item, 1L);
                            L1ItemInstance item2 = pc.getInventory().checkItemX(49173, 1L);
                            if (item2 != null) {
                                pc.getInventory().removeItem(item2, 1L);
                            }
                            QuestClass.get().endQuest(pc, IllusionistLv30_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein13"));
                            CreateNewItem.getQuestItem(pc, npc, 49131, 1L);
                            CreateNewItem.getQuestItem(pc, npc, 21101, 1L);
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(337, "$5634(1)"));
                            isCloseList = true;
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("o")) {
                if (pc.getQuest().isEnd(IllusionistLv15_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv30_1.QUEST.get_questlevel()) {
                        if (pc.getInventory().checkItem(49189)) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein17"));
                            return;
                        }
                        else if (pc.getInventory().checkItem(49186)) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein17"));
                        }
                        else {
                            CreateNewItem.getQuestItem(pc, npc, 49186, 1L);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein16"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("e")) {
                if (pc.getQuest().isEnd(IllusionistLv30_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv45_1.QUEST.get_questlevel()) {
                        CreateNewItem.getQuestItem(pc, npc, 49174, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 49180, 1L);
                        QuestClass.get().startQuest(pc, IllusionistLv45_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein19"));
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("f")) {
                if (pc.getQuest().isEnd(IllusionistLv30_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv45_1.QUEST.get_questlevel()) {
                        if (CreateNewItem.checkNewItem(pc, new int[] { 49194, 49195, 49196 }, new int[] { 1, 1, 1 }) < 1L) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein21"));
                        }
                        else {
                            CreateNewItem.createNewItem(pc, new int[] { 49194, 49195, 49196 }, new int[] { 1, 1, 1 }, new int[] { 49193 }, 1L, new int[] { 3 });
                            pc.getQuest().set_step(IllusionistLv45_1.QUEST.get_id(), 2);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein22"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("g")) {
                if (pc.getQuest().isEnd(IllusionistLv30_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv45_1.QUEST.get_questlevel()) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49202, 1L);
                        if (item != null) {
                            pc.getInventory().removeItem(item, 1L);
                            L1ItemInstance item2 = pc.getInventory().checkItemX(49174, 1L);
                            if (item2 != null) {
                                pc.getInventory().removeItem(item2, 1L);
                            }
                            QuestClass.get().endQuest(pc, IllusionistLv45_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein25"));
                            CreateNewItem.getQuestItem(pc, npc, 21100, 1L);
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein24"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("h")) {
                if (pc.getQuest().isEnd(IllusionistLv45_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv50_1.QUEST.get_questlevel()) {
                        CreateNewItem.getQuestItem(pc, npc, 49176, 1L);
                        QuestClass.get().startQuest(pc, IllusionistLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein28"));
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("i")) {
                if (pc.getQuest().isEnd(IllusionistLv45_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv50_1.QUEST.get_questlevel()) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49101, 100L);
                        if (item != null) {
                            pc.getInventory().removeItem(item, 100L);
                            L1ItemInstance item2 = pc.getInventory().checkItemX(49176, 1L);
                            if (item2 != null) {
                                pc.getInventory().removeItem(item2, 1L);
                            }
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein31"));
                            pc.getQuest().set_step(IllusionistLv50_1.QUEST.get_id(), 2);
                            CreateNewItem.getQuestItem(pc, npc, 49177, 1L);
                            CreateNewItem.getQuestItem(pc, npc, 49202, 1L);
                            CreateNewItem.getQuestItem(pc, npc, 49178, 1L);
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein30"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("j")) {
                if (pc.getQuest().isEnd(IllusionistLv45_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv50_1.QUEST.get_questlevel()) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49206, 1L);
                        if (item != null) {
                            pc.getInventory().removeItem(item);
                            L1ItemInstance item2 = pc.getInventory().checkItemX(49177, 1L);
                            if (item2 != null) {
                                pc.getInventory().removeItem(item2);
                            }
                            L1ItemInstance item3 = pc.getInventory().checkItemX(49207, 1L);
                            if (item3 != null) {
                                pc.getInventory().removeItem(item3);
                            }
                            L1ItemInstance item4 = pc.getInventory().checkItemX(49202, 1L);
                            if (item4 != null) {
                                pc.getInventory().removeItem(item4);
                            }
                            L1ItemInstance item5 = pc.getInventory().checkItemX(49178, 1L);
                            if (item5 != null) {
                                pc.getInventory().removeItem(item5);
                            }
                            L1ItemInstance item6 = pc.getInventory().checkItemX(49203, 1L);
                            if (item6 != null) {
                                pc.getInventory().removeItem(item6);
                            }
                            L1ItemInstance item7 = pc.getInventory().checkItemX(49204, 1L);
                            if (item7 != null) {
                                pc.getInventory().removeItem(item7);
                            }
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein45"));
                            QuestClass.get().endQuest(pc, IllusionistLv50_1.QUEST.get_id());
                            CreateNewItem.getQuestItem(pc, npc, 49181, 1L);
                            CreateNewItem.getQuestItem(pc, npc, 49205, 1L);
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein34"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("k")) {
                if (pc.getQuest().isEnd(IllusionistLv45_1.QUEST.get_id())) {
                    if (pc.getLevel() >= IllusionistLv50_1.QUEST.get_questlevel()) {
                        if (pc.getInventory().checkItem(49202)) {
                            isCloseList = true;
                        }
                        else {
                            if (!pc.getInventory().checkItem(49178)) {
                                CreateNewItem.getQuestItem(pc, npc, 49178, 1L);
                            }
                            CreateNewItem.getQuestItem(pc, npc, 49202, 1L);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein32"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
        }
        else if (pc.isDragonKnight()) {
            if (pc.getQuest().isEnd(DragonKnightLv30_1.QUEST.get_id())) {
                if (pc.getLevel() >= DragonKnightLv45_1.QUEST.get_questlevel()) {
                    if (cmd.equalsIgnoreCase("l")) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49209, 1L);
                        if (item != null) {
                            pc.getQuest().set_step(DragonKnightLv45_1.QUEST.get_id(), 2);
                            pc.getInventory().removeItem(item, 1L);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein38"));
                        }
                    }
                    else if (cmd.equalsIgnoreCase("m")) {
                        pc.getQuest().set_step(DragonKnightLv45_1.QUEST.get_id(), 3);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein39"));
                        CreateNewItem.getQuestItem(pc, npc, 49171, 1L);
                    }
                    else if (cmd.equalsIgnoreCase("n")) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49225, 10L);
                        if (item != null) {
                            L1ItemInstance item2 = pc.getInventory().checkItemX(49171, 1L);
                            if (item2 != null) {
                                pc.getInventory().removeItem(item2, 1L);
                            }
                            pc.getQuest().set_step(DragonKnightLv45_1.QUEST.get_id(), 4);
                            pc.getInventory().removeItem(item);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein41"));
                            CreateNewItem.getQuestItem(pc, npc, 49224, 1L);
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "silrein42"));
                        }
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else {
                isCloseList = true;
            }
        }
        else {
            isCloseList = true;
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}

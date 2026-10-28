package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.CrownLv45_1;
import com.lineage.data.quest.CrownLv50_1;
import com.lineage.data.quest.KnightLv45_1;
import com.lineage.data.quest.KnightLv50_1;
import com.lineage.data.quest.ElfLv45_1;
import com.lineage.data.quest.ElfLv50_1;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.data.quest.WizardLv45_1;
import com.lineage.data.quest.WizardLv50_1;
import com.lineage.data.quest.CKEWLv50_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Dicarding extends NpcExecutor
{
    private static final Log _log;
    private static int[] _itemIds;
    
    static {
        _log = LogFactory.getLog(Npc_Dicarding.class);
        Npc_Dicarding._itemIds = new int[] { 40742, 49165, 49166, 49167, 49168, 49239, 65, 133, 191, 192 };
    }
    
    public static NpcExecutor get() {
        return new Npc_Dicarding();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                this.isCrown(pc, npc);
            }
            else if (pc.isKnight()) {
                this.isKnight(pc, npc);
            }
            else if (pc.isElf()) {
                this.isElf(pc, npc);
            }
            else if (pc.isWizard()) {
                this.isWizard(pc, npc);
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
            }
        }
        catch (Exception e) {
            Npc_Dicarding._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void isWizard(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getQuest().isEnd(CKEWLv50_1.QUEST.get_id())) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw15"));
                return;
            }
            else if (pc.getQuest().isEnd(WizardLv50_1.QUEST.get_id())) {
                if (pc.getLevel() >= CKEWLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(CKEWLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw6"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw10"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw11"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw13"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
                }
                return;
            }
            else if (pc.getQuest().isEnd(WizardLv45_1.QUEST.get_id())) {
                if (pc.getLevel() >= WizardLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(WizardLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw1"));
                            break;
                        }
                        case 1:
                        case 2: {
                            L1ItemInstance item = pc.getInventory().checkItemX(49164, 1L);
                            if (item != null) {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw5"));
                                break;
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw4"));
                                break;
                            }
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
            }
        }
        catch (Exception e) {
            Npc_Dicarding._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void isElf(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getQuest().isEnd(CKEWLv50_1.QUEST.get_id())) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge17"));
                return;
            }
            else if (pc.getQuest().isEnd(ElfLv50_1.QUEST.get_id())) {
                if (pc.getLevel() >= CKEWLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(CKEWLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge10"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge12"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge13"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge15"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
                }
                return;
            }
            else if (pc.getQuest().isEnd(ElfLv45_1.QUEST.get_id())) {
                if (pc.getLevel() >= ElfLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(ElfLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge1"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge4"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge5"));
                            break;
                        }
                        case 3:
                        case 4:
                        case 5: {
                            L1ItemInstance item = pc.getInventory().checkItemX(49163, 1L);
                            if (item != null) {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge9"));
                                break;
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge8"));
                                break;
                            }
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
            }
        }
        catch (Exception e) {
            Npc_Dicarding._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void isKnight(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getQuest().isEnd(CKEWLv50_1.QUEST.get_id())) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk16"));
                return;
            }
            else if (pc.getQuest().isEnd(KnightLv50_1.QUEST.get_id())) {
                if (pc.getLevel() >= CKEWLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(CKEWLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk10"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk11"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk12"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk14"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
                }
                return;
            }
            else if (pc.getQuest().isEnd(KnightLv45_1.QUEST.get_id())) {
                if (pc.getLevel() >= KnightLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(KnightLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk1"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk4"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk6"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk9"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
            }
        }
        catch (Exception e) {
            Npc_Dicarding._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void isCrown(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getQuest().isEnd(CKEWLv50_1.QUEST.get_id())) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp15"));
                return;
            }
            else if (pc.getQuest().isEnd(CrownLv50_1.QUEST.get_id())) {
                if (pc.getLevel() >= CKEWLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(CKEWLv50_1.QUEST.get_id())) {
                        case 0:
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp10"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp11"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp13"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
                }
                return;
            }
            else if (pc.getQuest().isEnd(CrownLv45_1.QUEST.get_id())) {
                if (pc.getLevel() >= CrownLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(CrownLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp1"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp4"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp8"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicarding"));
            }
        }
        catch (Exception e) {
            Npc_Dicarding._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isCrown()) {
            switch (pc.getQuest().get_step(CrownLv50_1.QUEST.get_id())) {
                case 0: {
                    if (cmd.equalsIgnoreCase("f")) {
                        QuestClass.get().startQuest(pc, CrownLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp3"));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1: {
                    if (cmd.equalsIgnoreCase("e")) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49159, 1L);
                        if (item != null) {
                            pc.getQuest().set_step(CrownLv50_1.QUEST.get_id(), 2);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp7"));
                            L1PolyMorph.doPoly(pc, 4261, 1800, 1);
                            break;
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp4a"));
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (cmd.equalsIgnoreCase("c")) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp9"));
                        L1PolyMorph.doPoly(pc, 4261, 1800, 1);
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id())) {
                if (cmd.equalsIgnoreCase("b")) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp12"));
                }
                else if (cmd.equalsIgnoreCase("a")) {
                    L1ItemInstance item = pc.getInventory().checkItemX(49241, 1L);
                    if (item != null) {
                        QuestClass.get().endQuest(pc, CKEWLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp14"));
                        pc.getInventory().removeItem(item, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 51, 1L);
                        this.delItem(pc);
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingp11"));
                    }
                }
            }
        }
        else if (pc.isKnight()) {
            switch (pc.getQuest().get_step(KnightLv50_1.QUEST.get_id())) {
                case 0: {
                    if (cmd.equalsIgnoreCase("g")) {
                        QuestClass.get().startQuest(pc, KnightLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk3"));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1: {
                    if (cmd.equalsIgnoreCase("h")) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49160, 1L);
                        if (item != null) {
                            pc.getQuest().set_step(KnightLv50_1.QUEST.get_id(), 2);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk5"));
                            pc.getInventory().removeItem(item, 1L);
                            break;
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk8"));
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (cmd.equalsIgnoreCase("i")) {
                        pc.getQuest().set_step(KnightLv50_1.QUEST.get_id(), 3);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk7"));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 3: {
                    if (cmd.equalsIgnoreCase("j")) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49161, 10L);
                        if (item != null) {
                            QuestClass.get().endQuest(pc, KnightLv50_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk10"));
                            pc.getInventory().removeItem(item);
                            QuestClass.get().startQuest(pc, CKEWLv50_1.QUEST.get_id());
                            break;
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk9a"));
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
            }
            if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id())) {
                if (cmd.equalsIgnoreCase("k")) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk13"));
                }
                else if (cmd.equalsIgnoreCase("l")) {
                    L1ItemInstance item = pc.getInventory().checkItemX(49241, 1L);
                    if (item != null) {
                        QuestClass.get().endQuest(pc, CKEWLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk15"));
                        pc.getInventory().removeItem(item, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 56, 1L);
                        this.delItem(pc);
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingk12"));
                    }
                }
            }
        }
        else if (pc.isElf()) {
            switch (pc.getQuest().get_step(ElfLv50_1.QUEST.get_id())) {
                case 0: {
                    if (cmd.equalsIgnoreCase("m")) {
                        QuestClass.get().startQuest(pc, ElfLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge3"));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1: {
                    if (cmd.equalsIgnoreCase("n")) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49162, 1L);
                        if (item != null) {
                            pc.getQuest().set_step(ElfLv50_1.QUEST.get_id(), 2);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge5"));
                            pc.getInventory().removeItem(item, 1L);
                            break;
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge3"));
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (cmd.equalsIgnoreCase("o")) {
                        pc.getQuest().set_step(ElfLv50_1.QUEST.get_id(), 3);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge7"));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 3:
                case 4:
                case 5: {
                    if (cmd.equalsIgnoreCase("p")) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49163, 1L);
                        if (item != null) {
                            QuestClass.get().endQuest(pc, ElfLv50_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge10"));
                            pc.getInventory().removeItem(item, 1L);
                            QuestClass.get().startQuest(pc, CKEWLv50_1.QUEST.get_id());
                            break;
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge8"));
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
            }
            if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id())) {
                if (cmd.equalsIgnoreCase("q")) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge14"));
                }
                else if (cmd.equalsIgnoreCase("y")) {
                    L1ItemInstance item = pc.getInventory().checkItemX(49241, 1L);
                    if (item != null) {
                        QuestClass.get().endQuest(pc, CKEWLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge16"));
                        pc.getInventory().removeItem(item, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 184, 1L);
                        this.delItem(pc);
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge13"));
                    }
                }
                else if (cmd.equalsIgnoreCase("s")) {
                    L1ItemInstance item = pc.getInventory().checkItemX(49241, 1L);
                    if (item != null) {
                        QuestClass.get().endQuest(pc, CKEWLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge16"));
                        pc.getInventory().removeItem(item, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 50, 1L);
                        this.delItem(pc);
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardinge13"));
                    }
                }
            }
        }
        else if (pc.isWizard()) {
            switch (pc.getQuest().get_step(WizardLv50_1.QUEST.get_id())) {
                case 0: {
                    if (cmd.equalsIgnoreCase("t")) {
                        QuestClass.get().startQuest(pc, WizardLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw3"));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1:
                case 2: {
                    if (cmd.equalsIgnoreCase("u")) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49164, 1L);
                        if (item != null) {
                            QuestClass.get().endQuest(pc, WizardLv50_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw6"));
                            pc.getInventory().removeItem(item, 1L);
                            break;
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw4"));
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 255: {
                    if (cmd.equalsIgnoreCase("v")) {
                        QuestClass.get().startQuest(pc, CKEWLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw9"));
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id())) {
                if (cmd.equalsIgnoreCase("w")) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw12"));
                }
                else if (cmd.equalsIgnoreCase("x")) {
                    L1ItemInstance item = pc.getInventory().checkItemX(49241, 1L);
                    if (item != null) {
                        QuestClass.get().endQuest(pc, CKEWLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw14"));
                        pc.getInventory().removeItem(item, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 20225, 1L);
                        this.delItem(pc);
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dicardingw11"));
                    }
                }
            }
        }
        else {
            isCloseList = true;
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void delItem(L1PcInstance pc) {
        int[] itemIds;
        for (int length = (itemIds = Npc_Dicarding._itemIds).length, i = 0; i < length; ++i) {
            int itemId = itemIds[i];
            L1ItemInstance reitem = pc.getInventory().findItemId(itemId);
            if (reitem != null) {
                if (reitem.isEquipped()) {
                    pc.getInventory().setEquipped(reitem, false, false, false);
                }
                pc.sendPackets(new S_ServerMessage(165, reitem.getName()));
                pc.getInventory().removeItem(reitem);
            }
        }
    }
}

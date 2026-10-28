package com.add.system;

import com.lineage.william.L1Blend_buff;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1Blend1
{
    private int _npcid;
    private String _action;
    private String _note;
    private int _checkLevel;
    private int _checkClass;
    private int[] _materials;
    private int[] _materials_count;
    private int[] _materials_enchants;
    private String _sucesshtml;
    private String _failhtml;
    private String _Allmessage;
    private int _quest;
    private String _givebuff;
    
    public int get_npcid() {
        return this._npcid;
    }
    
    public void set_npcid(int npcid) {
        this._npcid = npcid;
    }
    
    public String get_action() {
        return this._action;
    }
    
    public void set_action(String action) {
        this._action = action;
    }
    
    public String get_note() {
        return this._note;
    }
    
    public void set_note(String note) {
        this._note = note;
    }
    
    public int getCheckLevel() {
        return this._checkLevel;
    }
    
    public void setCheckLevel(int checkLevel) {
        this._checkLevel = checkLevel;
    }
    
    public int getCheckClass() {
        return this._checkClass;
    }
    
    public void setCheckClass(int checkClass) {
        this._checkClass = checkClass;
    }
    
    public final int[] getMaterials() {
        return this._materials;
    }
    
    public void setMaterials(int[] needids) {
        this._materials = needids;
    }
    
    public final int[] getMaterials_count() {
        return this._materials_count;
    }
    
    public final void setMaterials_count(int[] needcounts) {
        this._materials_count = needcounts;
    }
    
    public final int[] get_materials_enchants() {
        return this._materials_enchants;
    }
    
    public final void set_materials_enchants(int[] needenchants) {
        this._materials_enchants = needenchants;
    }
    
    public String get_sucesshtml() {
        return this._sucesshtml;
    }
    
    public void set_sucesshtml(String sucesshtml) {
        this._sucesshtml = sucesshtml;
    }
    
    public String get_failhtml() {
        return this._failhtml;
    }
    
    public void set_failhtml(String failhtml) {
        this._failhtml = failhtml;
    }
    
    public String get_Allmessage() {
        return this._Allmessage;
    }
    
    public void set_Allmessage(String Allmessage) {
        this._Allmessage = Allmessage;
    }
    
    public int getquest() {
        return this._quest;
    }
    
    public void setquest(int quest) {
        this._quest = quest;
    }
    
    public String get_givebuff() {
        return this._givebuff;
    }
    
    public void set_givebuff(String givebuff) {
        this._givebuff = givebuff;
    }
    
    public void ShowCraftHtml(L1PcInstance pc, L1NpcInstance npc, L1Blend1 ItemBlend) {
        String msg0 = "";
        String msg2 = "";
        String msg3 = "";
        String msg4 = "";
        String msg5 = "";
        String msg6 = "";
        String msg7 = "";
        String msg8 = "";
        String msg9 = "";
        String msg10 = "";
        String msg11 = "";
        String msg12 = "";
        String msg13 = "";
        String msg14 = "";
        String msg15 = "";
        String msg16 = "";
        String msg17 = "";
        String msg18 = "";
        String msg19 = "";
        String msg20 = "";
        String msg21 = "";
        String msg22 = "";
        String msg23 = "";
        String msg24 = "";
        String msg25 = "";
        String msg26 = "";
        String msg27 = "";
        String msg28 = "";
        String msg29 = "";
        String msg30 = "";
        String msg31 = "";
        String msg32 = "";
        String msg33 = "";
        String msg34 = "";
        String msg35 = "";
        String msg36 = "";
        String msg37 = "";
        String msg38 = "";
        String msg39 = "";
        String msg40 = "";
        String msg41 = "";
        String msg42 = "";
        String msg43 = "";
        String msg44 = "";
        String msg45 = "";
        String msg46 = "";
        String msg47 = "";
        String msg48 = "";
        String msg49 = "";
        String msg50 = "";
        String msg51 = "";
        String msg52 = "";
        String msg53 = "";
        String msg54 = "";
        String msg55 = "";
        msg0 = this.get_note();
        msg2 = this.get_givebuff();
        if (ItemBlend.getCheckLevel() != 0) {
            msg3 = " " + ItemBlend.getCheckLevel() + "級以上。 ";
        }
        else {
            msg3 = " 無限制 ";
        }
        if (ItemBlend.getCheckClass() == 1) {
            msg4 = " 王族";
        }
        else if (ItemBlend.getCheckClass() == 2) {
            msg4 = " 騎士";
        }
        else if (ItemBlend.getCheckClass() == 3) {
            msg4 = " 法師";
        }
        else if (ItemBlend.getCheckClass() == 4) {
            msg4 = " 妖精";
        }
        else if (ItemBlend.getCheckClass() == 5) {
            msg4 = " 黑妖";
        }
        else if (ItemBlend.getCheckClass() == 0) {
            msg4 = " 所有職業";
        }
        if (pc.getQuest().get_step(ItemBlend.getquest()) == 2) {
            msg55 = "[已解鎖此成就]";
        }
        else {
            msg55 = "[未解此成就]";
        }
        int[] Materials = ItemBlend.getMaterials();
        int[] counts = ItemBlend.getMaterials_count();
        int[] enchants = ItemBlend.get_materials_enchants();
        if (Materials != null) {
            for (int i = 0; i < Materials.length; ++i) {
                L1ItemInstance temp = ItemTable.get().createItem(Materials[i]);
                temp.setEnchantLevel(enchants[i]);
                temp.setIdentified(true);
                switch (i) {
                    case 0: {
                        msg5 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 1: {
                        msg6 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 2: {
                        msg7 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 3: {
                        msg8 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 4: {
                        msg9 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 5: {
                        msg10 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 6: {
                        msg11 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 7: {
                        msg12 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 8: {
                        msg13 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 9: {
                        msg14 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 10: {
                        msg15 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 11: {
                        msg16 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 12: {
                        msg17 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 13: {
                        msg18 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 14: {
                        msg19 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 15: {
                        msg20 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 16: {
                        msg21 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 17: {
                        msg22 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 18: {
                        msg23 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 19: {
                        msg24 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 20: {
                        msg25 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 21: {
                        msg26 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 22: {
                        msg27 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 23: {
                        msg28 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 24: {
                        msg29 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 25: {
                        msg30 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 26: {
                        msg31 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 27: {
                        msg32 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 28: {
                        msg33 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 29: {
                        msg34 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 30: {
                        msg35 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 31: {
                        msg36 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 32: {
                        msg37 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 33: {
                        msg38 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 34: {
                        msg39 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 35: {
                        msg40 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 36: {
                        msg41 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 37: {
                        msg42 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 38: {
                        msg43 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 39: {
                        msg44 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 40: {
                        msg45 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 41: {
                        msg46 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 42: {
                        msg47 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 43: {
                        msg48 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 44: {
                        msg49 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 45: {
                        msg50 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 46: {
                        msg51 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 47: {
                        msg52 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 48: {
                        msg53 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 49: {
                        msg54 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                }
            }
        }
        String[] msgs = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20, msg21, msg22, msg23, msg24, msg25, msg26, msg27, msg28, msg29, msg30, msg31, msg32, msg33, msg34, msg35, msg36, msg37, msg38, msg39, msg40, msg41, msg42, msg43, msg44, msg45, msg46, msg47, msg48, msg49, msg50, msg51, msg52, msg53, msg54, msg55 };
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ItemBlend2", msgs));
    }
    
    public void CheckCraftItem(L1PcInstance pc, L1NpcInstance npc, L1Blend1 ItemBlend, int amount, boolean checked) {
        int[] Materials = ItemBlend.getMaterials();
        int[] Materials_counts = ItemBlend.getMaterials_count();
        int[] enchants = ItemBlend.get_materials_enchants();
        boolean isok = true;
        if (pc.getQuest().get_step(ItemBlend.getquest()) == 2) {
            pc.sendPackets(new S_ServerMessage("此成就已完成。"));
            isok = false;
        }
        if (ItemBlend.getCheckLevel() != 0 && pc.getLevel() < ItemBlend.getCheckLevel()) {
            pc.sendPackets(new S_ServerMessage("等級必須為" + ItemBlend.getCheckLevel() + "以上。"));
            isok = false;
        }
        if (ItemBlend.getCheckClass() != 0) {
            byte class_id = 0;
            String Classmsg = "";
            if (pc.isCrown()) {
                class_id = 1;
            }
            else if (pc.isKnight()) {
                class_id = 2;
            }
            else if (pc.isWizard()) {
                class_id = 3;
            }
            else if (pc.isElf()) {
                class_id = 4;
            }
            else if (pc.isDarkelf()) {
                class_id = 5;
            }
            else if (pc.isDragonKnight()) {
                class_id = 6;
            }
            else if (pc.isIllusionist()) {
                class_id = 7;
            }
            switch (ItemBlend.getCheckClass()) {
                case 1: {
                    Classmsg = "王族";
                    break;
                }
                case 2: {
                    Classmsg = "騎士";
                    break;
                }
                case 3: {
                    Classmsg = "法師";
                    break;
                }
                case 4: {
                    Classmsg = "妖精";
                    break;
                }
                case 5: {
                    Classmsg = "黑暗妖精";
                    break;
                }
            }
            if (ItemBlend.getCheckClass() != class_id) {
                pc.sendPackets(new S_ServerMessage(166, "職業必須是", Classmsg, "才能製造此道具"));
                isok = false;
            }
        }
        boolean enough = false;
        if (isok) {
            int num = 0;
            for (int i = 0; i < Materials.length; ++i) {
                if (Materials[i] != 0 && Materials_counts[i] != 0) {
                    if (!pc.getInventory().checkEnchantItem(Materials[i], enchants[i], (long)Materials_counts[i])) {
                        L1ItemInstance temp = ItemTable.get().createItem(Materials[i]);
                        temp.setEnchantLevel(enchants[i]);
                        temp.setIdentified(true);
                        pc.sendPackets(new S_ServerMessage(337, temp.getLogName() + "(" + Materials_counts[i] + ")"));
                        isok = false;
                    }
                    else {
                        ++num;
                    }
                }
            }
            if (num == Materials.length) {
                enough = true;
            }
        }
        if (isok && enough) {
            int[] newcounts = new int[Materials_counts.length];
            for (int i = 0; i < Materials_counts.length; ++i) {
                newcounts[i] = Materials_counts[i] * amount;
            }
            for (int i = 0; i < Materials.length; ++i) {
                pc.getInventory().consumeEnchantItem(Materials[i], enchants[i], (long)newcounts[i]);
            }
            if (ItemBlend.get_Allmessage() != null) {
                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(ItemBlend.get_Allmessage(), pc.getName(), this.get_note())));
            }
            pc.sendPackets(new S_ServerMessage(ItemBlend.get_sucesshtml()));
            pc.getQuest().set_step(ItemBlend.getquest(), 1);
            L1Blend_buff.forIntensifyArmor(pc);
            pc.getQuest().set_step(ItemBlend.getquest(), 2);
        }
    }
}

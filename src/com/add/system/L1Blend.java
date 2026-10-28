package com.add.system;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import java.util.Random;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemLimitTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1Blend
{
    boolean cheeckcount;
    private int _npcid;
    private String _action;
    private String _note;
    private int _checkLevel;
    private int _checkClass;
    private int _rnd;
    private int _hpConsume;
    private int _mpConsume;
    private int[] _materials;
    private int[] _materials_count;
    private int[] _materials_enchants;
    private int _new_item;
    private int _new_item_counts;
    private int _new_Enchantlvl_SW;
    private int _new_item_Enchantlvl;
    private int _new_item_Bless;
    private int _residue_item;
    private int _residue_count;
    private int _residue_Lv;
    private int _replacement_count;
    private boolean _inputamount;
    private boolean _all_in_once;
    private int _bonus_item;
    private int _bonus_item_count;
    private int _bonus_item_enchant;
    private String _sucesshtml;
    private String _failhtml;
    private String _Allmessage;
    private String _runmessage;
    private int _itembroad;
    private String _othermessage;
    private int _itemran;
    private int _itemranup;
    
    public L1Blend() {
        this.cheeckcount = false;
    }
    
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
    
    public int getRandom() {
        return this._rnd;
    }
    
    public void setRandom(int rnd) {
        this._rnd = rnd;
    }
    
    public int getHpConsume() {
        return this._hpConsume;
    }
    
    public void setHpConsume(int hpConsume) {
        this._hpConsume = hpConsume;
    }
    
    public int getMpConsume() {
        return this._mpConsume;
    }
    
    public void setMpConsume(int mpConsume) {
        this._mpConsume = mpConsume;
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
    
    public int getNew_item() {
        return this._new_item;
    }
    
    public void setNew_item(int new_item) {
        this._new_item = new_item;
    }
    
    public int getNew_item_counts() {
        return this._new_item_counts;
    }
    
    public void setNew_item_counts(int new_item_counts) {
        this._new_item_counts = new_item_counts;
    }
    
    public int getNew_Enchantlvl_SW() {
        return this._new_Enchantlvl_SW;
    }
    
    public void setNew_Enchantlvl_SW(int new_Enchantlvl_SW) {
        this._new_Enchantlvl_SW = new_Enchantlvl_SW;
    }
    
    public int getNew_item_Enchantlvl() {
        return this._new_item_Enchantlvl;
    }
    
    public void setNew_item_Enchantlvl(int new_item_Enchantlvl) {
        this._new_item_Enchantlvl = new_item_Enchantlvl;
    }
    
    public int getNew_item_Bless() {
        return this._new_item_Bless;
    }
    
    public void setNew_item_Bless(int new_item_bless) {
        this._new_item_Bless = new_item_bless;
    }
    
    public int getResidue_Item() {
        return this._residue_item;
    }
    
    public void setResidue_Item(int residueitem) {
        this._residue_item = residueitem;
    }
    
    public int getResidue_Count() {
        return this._residue_count;
    }
    
    public void setResidue_Count(int residuecount) {
        this._residue_count = residuecount;
    }
    
    public int getResidue_Lv() {
        return this._residue_Lv;
    }
    
    public void setResidue_Lv(int residueLv) {
        this._residue_Lv = residueLv;
    }
    
    public int getReplacement_count() {
        return this._replacement_count;
    }
    
    public void setReplacement_count(int replacement_count) {
        this._replacement_count = replacement_count;
    }
    
    public boolean isInputAmount() {
        return this._inputamount;
    }
    
    public void setInputAmount(boolean flag) {
        this._inputamount = flag;
    }
    
    public boolean isAll_In_Once() {
        return this._all_in_once;
    }
    
    public void setAll_In_Once(boolean flag) {
        this._all_in_once = flag;
    }
    
    public int getBonus_item() {
        return this._bonus_item;
    }
    
    public void setBonus_item(int bonusitem) {
        this._bonus_item = bonusitem;
    }
    
    public int getBonus_item_count() {
        return this._bonus_item_count;
    }
    
    public void setBonus_item_count(int bonusitemcount) {
        this._bonus_item_count = bonusitemcount;
    }
    
    public int getBonus_item_enchant() {
        return this._bonus_item_enchant;
    }
    
    public void setBonus_item_enchant(int bonusitemenchant) {
        this._bonus_item_enchant = bonusitemenchant;
    }
    
    public String get_sucesshtml() {
        return this._sucesshtml;
    }
    
    public void set_sucesshtml(String sucesshtml) {
        this._sucesshtml = sucesshtml;
    }
    
    public String get_runmessage() {
        return this._runmessage;
    }
    
    public void set_runmessage(String runmessage) {
        this._runmessage = runmessage;
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
    
    public int getitembroad() {
        return this._itembroad;
    }
    
    public void setitembroad(int itembroad) {
        this._itembroad = itembroad;
    }
    
    public String get_othermessage() {
        return this._othermessage;
    }
    
    public void set_othermessage(String othermessage) {
        this._othermessage = othermessage;
    }
    
    public int getitemran() {
        return this._itemran;
    }
    
    public void setitemran(int itemran) {
        this._itemran = itemran;
    }
    
    public void setitemranup(int itemranup) {
        this._itemranup = itemranup;
    }
    
    public int getitemranup() {
        return this._itemranup;
    }
    
    public void ShowCraftHtml(L1PcInstance pc, L1NpcInstance npc, L1Blend ItemBlend) {
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
        L1ItemInstance Newitem = ItemTable.get().createItem(ItemBlend.getNew_item());
        if (Newitem != null) {
            Newitem.setCount((long)ItemBlend.getNew_item_counts());
            Newitem.setEnchantLevel(ItemBlend.getNew_item_Enchantlvl());
            Newitem.setIdentified(true);
            msg0 = new StringBuilder().append(Newitem.getLogName()).toString();
        }
        L1ItemInstance Bonusitem = ItemTable.get().createItem(ItemBlend.getBonus_item());
        if (Bonusitem != null) {
            Bonusitem.setCount((long)ItemBlend.getBonus_item_count());
            Bonusitem.setEnchantLevel(ItemBlend.getBonus_item_enchant());
            Bonusitem.setIdentified(true);
            msg2 = "製造成功時額外獲得: " + Bonusitem.getLogName();
        }
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
        else if (ItemBlend.getCheckClass() == 6) {
            msg4 = " 龍騎士";
        }
        else if (ItemBlend.getCheckClass() == 7) {
            msg4 = " 幻術師";
        }
        else if (ItemBlend.getCheckClass() == 0) {
            msg4 = " 所有職業";
        }
        if (ItemBlend.getRandom() != -1 && ItemBlend.get_runmessage() != null) {
            msg5 = " " + ItemBlend.get_runmessage();
        }
        else {
            msg5 = " " + ItemBlend.getRandom() + " %";
        }
        int HammerRnd = this.CheckForFireHammerCount(pc, npc, ItemBlend);
        if (HammerRnd > 0 && ItemBlend.get_othermessage() != null) {
            msg6 = "[" + ItemBlend.get_othermessage() + "]成功機率+: " + HammerRnd + " %";
        }
        int[] Materials = ItemBlend.getMaterials();
        int[] counts = ItemBlend.getMaterials_count();
        int[] enchants = ItemBlend.get_materials_enchants();
        if (Materials != null) {
            for (int i = 0; i < Materials.length; ++i) {
                L1ItemInstance temp = ItemTable.get().createItem(Materials[i]);
                temp.setEnchantLevel(enchants[i]);
                temp.setIdentified(true);
                int replacementcount = ItemBlend.getReplacement_count();
                if (temp.getItemId() == 80028 && replacementcount != 0) {
                    msg7 = "可用(" + replacementcount + ")個火神痕跡替代火神契約";
                }
                switch (i) {
                    case 0: {
                        msg8 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 1: {
                        msg9 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 2: {
                        msg10 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 3: {
                        msg11 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 4: {
                        msg12 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 5: {
                        msg13 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 6: {
                        msg14 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 7: {
                        msg15 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 8: {
                        msg16 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 9: {
                        msg17 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 10: {
                        msg18 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 11: {
                        msg19 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 12: {
                        msg20 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                    case 13: {
                        msg21 = temp.getLogName() + " (" + counts[i] + ") 個";
                        break;
                    }
                }
            }
        }
        String[] msgs = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20, msg21 };
        pc.setblendcheckitem(ItemBlend.getMaterials());
        pc.setblendcheckitemcount(ItemBlend.getMaterials_count());
        pc.setblendcheckitemen(ItemBlend.get_materials_enchants());
        pc.setItemBlendcheckitem(Newitem.getItemId());
        pc.set_ItemBlendcheckitemname(Newitem.getName2());
        pc.setItemBlendcheckitemcount(ItemBlend.getNew_item_counts());
        pc.setItemBlendResdueItem(ItemBlend.getResidue_Item());
        pc.setItemBlendResdueItemcount(ItemBlend.getResidue_Count());
        pc.setItemBlendResdueItemLv(ItemBlend.getResidue_Lv());
        pc.setItemBlendrnd(ItemBlend.getRandom());
        pc.setItemBlendGvEnchantlvl(ItemBlend.getNew_item_Enchantlvl());
        pc.set_ItemBlendAllmsg(ItemBlend.get_Allmessage());
        if (ItemLimitTable.TIME.get((Integer)Newitem.getItemId()) != null) {
            int date = 0;
            date = ItemLimitTable.TIME.get((Integer)Newitem.getItemId());
            if (date > 0) {
                pc.sendPackets(new S_ServerMessage("伺服器製作該物品[" + ItemBlend.get_note() + "] 數量剩餘: " + date));
            }
            else {
                pc.sendPackets(new S_ServerMessage("該物品已達該伺服器製作上限"));
                pc.sendPackets(new S_CloseList(npc.getId()));
                return;
            }
        }
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ItemBlend", msgs));
    }
    
    private boolean CheckForReplacement(L1PcInstance pc, L1Blend ItemBlend) {
        boolean replace = false;
        int replacement = 80322;
        int replacementcount = ItemBlend.getReplacement_count();
        if (replacementcount != 0 && pc.getInventory().checkItem(replacement, (long)replacementcount)) {
            replace = true;
        }
        return replace;
    }
    
    private int CheckForFireHammerCount(L1PcInstance pc, L1NpcInstance npc, L1Blend ItemBlend) {
        int HammerCount = 0;
        L1ItemInstance firehammer = pc.getInventory().findItemId(ItemBlend.getitemran());
        if (firehammer != null) {
            HammerCount = (int)firehammer.getCount();
            if (HammerCount > ItemBlend.getitemranup()) {
                HammerCount = ItemBlend.getitemranup();
            }
        }
        return HammerCount;
    }
    
    public void CheckCraftItem(L1PcInstance pc, L1NpcInstance npc, L1Blend ItemBlend, int amount, boolean checked) {
        int[] Materials = pc.getblendcheckitem();
        int[] Materials_counts = pc.getblendcheckitemcount();
        int[] enchants = pc.getblendcheckitemen();
        if (!checked && ItemBlend.isInputAmount()) {
            long xcount = CreateNewItem.checkNewItem(pc, Materials, Materials_counts);
            if (xcount >= 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, null));
                return;
            }
            else {
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
        }
        else {
            boolean isok = true;
            int New_itemid = ItemBlend.getNew_item();
            int New_item_counts = ItemBlend.getNew_item_counts();
            L1ItemInstance newitem = ItemTable.get().createItem(New_itemid);
            if (New_itemid == 56148 && (pc.getInventory().findItemId(56147) != null || pc.getInventory().findItemId(56148) != null)) {
                pc.sendPackets(new S_ServerMessage("身上已有妲蒂斯的魔力。"));
                isok = false;
            }
            if (New_itemid == 56147 && pc.getInventory().findItemId(56147) != null) {
                pc.sendPackets(new S_ServerMessage("身上已有妲蒂斯的魔力。"));
                isok = false;
            }
            if (pc.getInventory().checkAddItem(newitem, (long)New_item_counts) != 0) {
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
                    case 6: {
                        Classmsg = "龍騎士";
                        break;
                    }
                    case 7: {
                        Classmsg = "幻術士";
                        break;
                    }
                }
                if (ItemBlend.getCheckClass() != class_id) {
                    pc.sendPackets(new S_ServerMessage(166, "職業必須是", Classmsg, "才能製造此道具"));
                    isok = false;
                }
            }
            if (ItemBlend.getHpConsume() != 0 || ItemBlend.getMpConsume() != 0) {
                if (pc.getCurrentHp() < ItemBlend.getHpConsume()) {
                    pc.sendPackets(new S_ServerMessage(166, "$1083", "必須有 (" + ItemBlend.getHpConsume() + ") ", "才能使用此道具", "以上"));
                    isok = false;
                }
                if (pc.getCurrentMp() < ItemBlend.getMpConsume()) {
                    pc.sendPackets(new S_ServerMessage(166, "$1084", "必須有 (" + ItemBlend.getMpConsume() + ") ", "才能使用此道具", "以上"));
                    isok = false;
                }
            }
            boolean enough = false;
            if (isok) {
                int num = 0;
                for (int i = 0; i < Materials.length; ++i) {
                    if (Materials[i] != 0 && Materials_counts[i] != 0) {
                        if (Materials[i] == 80028 && !pc.getInventory().checkItem(Materials[i], (long)Materials_counts[i]) && this.CheckForReplacement(pc, ItemBlend)) {
                            ++num;
                        }
                        else if (!pc.getInventory().checkEnchantItem(Materials[i], enchants[i], (long)Materials_counts[i])) {
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
                    if (pc.getInventory().getSize() + amount >= 170) {
                        pc.sendPackets(new S_ServerMessage("身上攜帶物品太多,請確認背包空間是否足夠"));
                        return;
                    }
                    else {
                        enough = true;
                    }
                }
            }
            if (isok && enough) {
                if (ItemBlend.isAll_In_Once()) {
                    int[] newcounts = new int[Materials_counts.length];
                    for (int i = 0; i < Materials_counts.length; ++i) {
                        newcounts[i] = Materials_counts[i] * amount;
                    }
                    for (int i = 0; i < Materials.length; ++i) {
                        pc.getInventory().consumeEnchantItem(Materials[i], enchants[i], (long)newcounts[i]);
                    }
                }
                else {
                    for (int a = 0; a < amount; ++a) {
                        for (int i = 0; i < Materials.length; ++i) {
                            pc.getInventory().consumeEnchantItem(Materials[i], enchants[i], (long)Materials_counts[i]);
                        }
                    }
                }
                if (ItemBlend.getHpConsume() > 0) {
                    pc.setCurrentHp(pc.getCurrentHp() - ItemBlend.getHpConsume());
                }
                if (ItemBlend.getMpConsume() > 0) {
                    pc.setCurrentMp(pc.getCurrentMp() - ItemBlend.getMpConsume());
                }
                this.CraftItem(pc, npc, ItemBlend, amount);
                String sucesshtml = ItemBlend.get_sucesshtml();
                int DBrnd = pc.getItemBlendrnd();
                if (sucesshtml != null && amount == 1 && DBrnd == 100) {
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), sucesshtml));
                }
                if (this.cheeckcount && ItemLimitTable.TIME.get((Integer)New_itemid) != null) {
                    int date = 0;
                    date = ItemLimitTable.TIME.get((Integer)New_itemid);
                    if (date > 0) {
                        try {
                            ItemLimitTable.get().update3(New_itemid);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        ItemLimitTable.get().load();
                        this.cheeckcount = false;
                        pc.sendPackets(new S_CloseList(npc.getId()));
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("該物品已達該伺服器製作上限。"));
                        this.cheeckcount = false;
                        isok = false;
                        pc.sendPackets(new S_CloseList(npc.getId()));
                    }
                }
            }
            else {
                String failhtml = ItemBlend.get_failhtml();
                int DBrnd = pc.getItemBlendrnd();
                if (failhtml != null && amount == 1 && DBrnd == 100) {
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), failhtml));
                }
                else {
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
            }
            return;
        }
    }
    
    private void CraftItem(L1PcInstance pc, L1NpcInstance npc, L1Blend ItemBlend, int amount) {
        Random _random = new Random();
        int DBrnd = ItemBlend.getRandom();
        int HammerRnd = this.CheckForFireHammerCount(pc, npc, ItemBlend);
        int TotalChance = DBrnd + HammerRnd;
        int New_itemid = pc.getItemBlendcheckitem();
        int New_item_counts = pc.getItemBlendcheckitemcount();
        int Bonus_itemid = ItemBlend.getBonus_item();
        int Bonusitem_count = ItemBlend.getBonus_item_count();
        int Bonusitem_enchant = ItemBlend.getBonus_item_enchant();
        int ItemLV_SW = ItemBlend.getNew_Enchantlvl_SW();
        int ItemLV = pc.getItemBlendGvEnchantlvl();
        int ItemBless = ItemBlend.getNew_item_Bless();
        int ResdueItem = pc.getItemBlendResdueItem();
        int ResdueCount = pc.getItemBlendResdueItemcount();
        int ResdueItemLv = pc.getItemBlendResdueItemLv();
        String npcName = npc.getNpcTemplate().get_name();
        int newamount = amount;
        int newcounts = New_item_counts;
        if (ItemBlend.isAll_In_Once()) {
            newamount = 1;
            if (ItemBlend.isInputAmount() && ItemBlend.isAll_In_Once()) {
                newcounts = New_item_counts * amount;
            }
            else if (ItemBlend.isInputAmount() && !ItemBlend.isAll_In_Once()) {
                newcounts = New_item_counts;
            }
        }
        for (int i = 0; i < newamount; ++i) {
            if (_random.nextInt(100) + 1 <= TotalChance) {
                L1ItemInstance newitem = ItemTable.get().createItem(pc.getItemBlendcheckitem());
                if (newitem != null && pc.getInventory().checkAddItem(newitem, (long)newcounts) == 0) {
                    if (newitem.isStackable()) {
                        if (ItemLV_SW == 0) {
                            newitem.setEnchantLevel(ItemLV);
                            newitem.setBless(ItemBless);
                            newitem.setIdentified(true);
                        }
                        else {
                            newitem.setEnchantLevel(_random.nextInt(ItemLV) + 1);
                            newitem.setBless(ItemBless);
                            newitem.setIdentified(true);
                        }
                        if (ItemBlend.isInputAmount() && ItemBlend.isAll_In_Once()) {
                            newitem.setCount((long)(pc.getItemBlendcheckitemcount() * amount));
                        }
                        else if (ItemBlend.isInputAmount() && !ItemBlend.isAll_In_Once()) {
                            newitem.setCount((long)pc.getItemBlendcheckitemcount());
                        }
                        if (ItemLimitTable.TIME.get((Integer)newitem.getItemId()) != null) {
                            int date = 0;
                            date = ItemLimitTable.TIME.get((Integer)newitem.getItemId());
                            if (date > 0) {
                                pc.sendPackets(new S_ServerMessage("伺服器製作該物品[" + ItemBlend.get_note() + "] 數量剩餘: " + date));
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage("該物品已達該伺服器製作上限"));
                                pc.sendPackets(new S_CloseList(npc.getId()));
                                return;
                            }
                        }
                        pc.getInventory().storeItem(newitem);
                        if (ItemLimitTable.TIME.get((Integer)newitem.getItemId()) != null) {
                            int date = 0;
                            date = ItemLimitTable.TIME.get((Integer)newitem.getItemId());
                            if (date > 0) {
                                this.cheeckcount = true;
                            }
                        }
                        if (newitem.getEnchantLevel() > 0) {
                            pc.sendPackets(new S_ServerMessage(143, npcName, "+" + newitem.getEnchantLevel() + pc.get_ItemBlendcheckitemname()));
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(143, npcName, pc.get_ItemBlendcheckitemname()));
                        }
                        if (ItemBlend.getitembroad() > 0 && pc.get_ItemBlendAllmsg() != null) {
                            if (ItemBlend.isInputAmount() && ItemBlend.isAll_In_Once()) {
                                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(pc.get_ItemBlendAllmsg(), pc.getName(), pc.get_ItemBlendcheckitemname() + "(" + pc.getItemBlendcheckitemcount() * amount + ")")));
                            }
                            else if (ItemBlend.isInputAmount() && !ItemBlend.isAll_In_Once()) {
                                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(pc.get_ItemBlendAllmsg(), pc.getName(), pc.get_ItemBlendcheckitemname())));
                            }
                            else if (!ItemBlend.isInputAmount() && !ItemBlend.isAll_In_Once()) {
                                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(pc.get_ItemBlendAllmsg(), pc.getName(), pc.get_ItemBlendcheckitemname())));
                            }
                        }
                        RecordTable.get().recordblend(pc.getName(), "(成功)" + pc.get_ItemBlendcheckitemname(), newcounts, pc.getIp());
                    }
                    else {
                        for (int c = 0; c < newcounts; ++c) {
                            L1ItemInstance newitem2 = ItemTable.get().createItem(New_itemid);
                            if (ItemLV_SW == 0) {
                                newitem2.setEnchantLevel(ItemLV);
                                newitem2.setBless(ItemBless);
                                newitem2.setIdentified(true);
                            }
                            else {
                                newitem2.setEnchantLevel(_random.nextInt(ItemLV) + 1);
                                newitem2.setBless(ItemBless);
                                newitem2.setIdentified(true);
                            }
                            if (ItemLimitTable.TIME.get((Integer)newitem.getItemId()) != null) {
                                int date2 = 0;
                                date2 = ItemLimitTable.TIME.get((Integer)newitem.getItemId());
                                if (date2 > 0) {
                                    pc.sendPackets(new S_ServerMessage("伺服器製作該物品[" + ItemBlend.get_note() + "] 數量剩餘: " + date2));
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage("該物品已達該伺服器製作上限"));
                                    pc.sendPackets(new S_CloseList(npc.getId()));
                                    return;
                                }
                            }
                            newitem2.setCount(1L);
                            pc.getInventory().storeItem(newitem2);
                            if (ItemLimitTable.TIME.get((Integer)newitem2.getItemId()) != null) {
                                int date2 = 0;
                                date2 = ItemLimitTable.TIME.get((Integer)newitem2.getItemId());
                                if (date2 > 0) {
                                    this.cheeckcount = true;
                                }
                            }
                            if (newitem2.getEnchantLevel() > 0) {
                                pc.sendPackets(new S_ServerMessage(143, npcName, "+" + newitem2.getEnchantLevel() + pc.get_ItemBlendcheckitemname()));
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage(143, npcName, pc.get_ItemBlendcheckitemname()));
                            }
                            if (ItemBlend.getitembroad() > 0 && pc.get_ItemBlendAllmsg() != null) {
                                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(pc.get_ItemBlendAllmsg(), pc.getName(), pc.get_ItemBlendcheckitemname())));
                            }
                            RecordTable.get().recordblend(pc.getName(), "(成功)" + pc.get_ItemBlendcheckitemname(), newcounts, pc.getIp());
                        }
                    }
                }
                L1ItemInstance bonusitem = ItemTable.get().createItem(Bonus_itemid);
                if (bonusitem != null && pc.getInventory().checkAddItem(bonusitem, (long)Bonusitem_count) == 0) {
                    if (bonusitem.isStackable()) {
                        bonusitem.setEnchantLevel(Bonusitem_enchant);
                        bonusitem.setIdentified(true);
                        bonusitem.setCount((long)Bonusitem_count);
                        pc.getInventory().storeItem(bonusitem);
                        pc.sendPackets(new S_ServerMessage(143, npcName, bonusitem.getLogName()));
                    }
                    else {
                        for (int c2 = 0; c2 < Bonusitem_count; ++c2) {
                            L1ItemInstance bonusitem2 = ItemTable.get().createItem(Bonus_itemid);
                            bonusitem2.setEnchantLevel(Bonusitem_enchant);
                            bonusitem2.setIdentified(true);
                            bonusitem2.setCount(1L);
                            pc.getInventory().storeItem(bonusitem2);
                            pc.sendPackets(new S_ServerMessage(143, npcName, bonusitem2.getLogName()));
                        }
                    }
                }
            }
            else {
                L1ItemInstance residueitem = ItemTable.get().createItem(ResdueItem);
                if (residueitem != null && ResdueCount > 0) {
                    if (pc.getInventory().checkAddItem(residueitem, (long)ResdueCount) == 0) {
                        residueitem.setEnchantLevel(ResdueItemLv);
                        residueitem.setIdentified(true);
                        residueitem.setCount((long)ResdueCount);
                        pc.getInventory().storeItem(residueitem);
                        pc.sendPackets(new S_ServerMessage(143, npcName, residueitem.getLogName()));
                        pc.sendPackets(new S_SystemMessage("道具製造失敗了"));
                        pc.setItemBlendResdueItem(0);
                        pc.setItemBlendResdueItemcount(0);
                        pc.setItemBlendResdueItemLv(0);
                        RecordTable.get().recordblend(pc.getName(), "(失敗)" + pc.get_ItemBlendcheckitemname(), newcounts, pc.getIp());
                    }
                }
                else {
                    pc.setItemBlendResdueItem(0);
                    pc.setItemBlendResdueItemcount(0);
                    pc.setItemBlendResdueItemLv(0);
                    pc.sendPackets(new S_SystemMessage("道具製造失敗了"));
                }
            }
            int Hammercount = this.CheckForFireHammerCount(pc, npc, ItemBlend);
            if (Hammercount > 0) {
                pc.getInventory().consumeItem(ItemBlend.getitemran(), (long)Hammercount);
            }
        }
        pc.sendPackets(new S_CloseList(pc.getId()));
    }
    
    public static void firenpc(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("玩家紀錄/製作NPC記錄.txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void firenpcfail(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("玩家紀錄/製作NPC失敗記錄.txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

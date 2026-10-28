package com.lineage.server.templates;

import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import java.util.Random;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.BlendTable;

public class L1Blend
{
    private int _gfxid;
    private String _checkmsgall;
    private int _checkmsg;
    private int _item_id;
    private int _checkLevel;
    private int _checkClass;
    private int _rnd;
    private int _checkItem;
    private int _hpConsume;
    private int _mpConsume;
    private int _material;
    private int _material_count;
    private int _material_2;
    private int _material_2_count;
    private int _material_3;
    private int _material_3_count;
    private int _material_4;
    private int _material_4_count;
    private int _material_5;
    private int _material_5_count;
    private int _new_item;
    private int _new_item_counts;
    private int _new_Enchantlvl_SW;
    private int _new_item_Enchantlvl;
    private int _removeItem;
    private String _message;
    private int _item_Html;
    
    public L1Blend(int item_id, int checkLevel, int checkClass, int rnd, int checkItem, int hpConsume, int mpConsume, int material, int material_count, int material_2, int material_2_count, int material_3, int material_3_count, int material_4, int material_4_count, int material_5, int material_5_count, int new_item, int new_item_counts, int new_Enchantlvl_SW, int new_item_Enchantlvl, int removeItem, String message, int item_Html, int gfxid, int checkmsg, String checkmsgall) {
        this._item_id = item_id;
        this._checkLevel = checkLevel;
        this._checkClass = checkClass;
        this._rnd = rnd;
        this._checkItem = checkItem;
        this._hpConsume = hpConsume;
        this._mpConsume = mpConsume;
        this._material = material;
        this._material_count = material_count;
        this._material_2 = material_2;
        this._material_2_count = material_2_count;
        this._material_3 = material_3;
        this._material_3_count = material_3_count;
        this._material_4 = material_4;
        this._material_4_count = material_4_count;
        this._material_5 = material_5;
        this._material_5_count = material_5_count;
        this._new_item = new_item;
        this._new_item_counts = new_item_counts;
        this._new_Enchantlvl_SW = new_Enchantlvl_SW;
        this._new_item_Enchantlvl = new_item_Enchantlvl;
        this._removeItem = removeItem;
        this._message = message;
        this._item_Html = item_Html;
        this._gfxid = gfxid;
        this._checkmsg = checkmsg;
        this._checkmsgall = checkmsgall;
    }
    
    public int getgfxid() {
        return this._gfxid;
    }
    
    public int getcheckmsg() {
        return this._checkmsg;
    }
    
    public String getcheckmsgall() {
        return this._checkmsgall;
    }
    
    public int getItemId() {
        return this._item_id;
    }
    
    public int getCheckLevel() {
        return this._checkLevel;
    }
    
    public int getCheckClass() {
        return this._checkClass;
    }
    
    public int getRandom() {
        return this._rnd;
    }
    
    public int getCheckItem() {
        return this._checkItem;
    }
    
    public int getHpConsume() {
        return this._hpConsume;
    }
    
    public int getMpConsume() {
        return this._mpConsume;
    }
    
    public int getMaterial() {
        return this._material;
    }
    
    public int getMaterial_count() {
        return this._material_count;
    }
    
    public int getMaterial_2() {
        return this._material_2;
    }
    
    public int getMaterial_2_count() {
        return this._material_2_count;
    }
    
    public int getMaterial_3() {
        return this._material_3;
    }
    
    public int getMaterial_3_count() {
        return this._material_3_count;
    }
    
    public int getMaterial_4() {
        return this._material_4;
    }
    
    public int getMaterial_4_count() {
        return this._material_4_count;
    }
    
    public int getMaterial_5() {
        return this._material_5;
    }
    
    public int getMaterial_5_count() {
        return this._material_5_count;
    }
    
    public int getNew_item() {
        return this._new_item;
    }
    
    public int getNew_item_counts() {
        return this._new_item_counts;
    }
    
    public int getNew_Enchantlvl_SW() {
        return this._new_Enchantlvl_SW;
    }
    
    public int getNew_item_Enchantlvl() {
        return this._new_item_Enchantlvl;
    }
    
    public int getRemoveItem() {
        return this._removeItem;
    }
    
    public String getMessage() {
        return this._message;
    }
    
    public int getitem_Html() {
        return this._item_Html;
    }
    
    public static int checkItemId(int itemId) {
        L1Blend Item_Blend = BlendTable.getInstance().getTemplate(itemId);
        if (Item_Blend == null) {
            return 0;
        }
        else {
            int item_id = Item_Blend.getItemId();
            return item_id;
        }
    }
    
    public static void getItemBlend(L1PcInstance pc, L1ItemInstance l1iteminstance, int itemId) {
        L1Blend Item_Blend = BlendTable.getInstance().getTemplate(itemId);
        boolean isBlend = false;
        boolean isConsumeItem = false;
        boolean isConsumeItem_2 = false;
        boolean isConsumeItem_3 = false;
        boolean isConsumeItem_4 = false;
        boolean isConsumeItem_5 = false;
        boolean isOK = false;
        boolean OpenHtml = false;
        int I_B_ma = Item_Blend.getMaterial();
        int I_B_ma2 = Item_Blend.getMaterial_2();
        int I_B_ma3 = Item_Blend.getMaterial_3();
        int I_B_ma4 = Item_Blend.getMaterial_4();
        int I_B_ma5 = Item_Blend.getMaterial_5();
        int ma_C = Item_Blend.getMaterial_count();
        int ma_C2 = Item_Blend.getMaterial_2_count();
        int ma_C3 = Item_Blend.getMaterial_3_count();
        int ma_C4 = Item_Blend.getMaterial_4_count();
        int ma_C5 = Item_Blend.getMaterial_5_count();
        L1Item temp = ItemTable.get().getTemplate(Item_Blend.getItemId());
        L1Item CheckItemtemp = ItemTable.get().getTemplate(Item_Blend.getCheckItem());
        L1Item maNeme = ItemTable.get().getTemplate(I_B_ma);
        L1Item maNeme2 = ItemTable.get().getTemplate(I_B_ma2);
        L1Item maNeme3 = ItemTable.get().getTemplate(I_B_ma3);
        L1Item maNeme4 = ItemTable.get().getTemplate(I_B_ma4);
        L1Item maNeme5 = ItemTable.get().getTemplate(I_B_ma5);
        L1Item NewItemNeme = ItemTable.get().getTemplate(Item_Blend.getNew_item());
        if (!isOK) {
            if (Item_Blend == null) {
                return;
            }
            else {
                if (Item_Blend.getCheckLevel() != 0 && pc.getLevel() < Item_Blend.getCheckLevel()) {
                    OpenHtml = true;
                    pc.sendPackets(new S_ServerMessage(318, new StringBuilder().append(Item_Blend.getCheckLevel()).toString()));
                }
                if (Item_Blend.getCheckClass() != 0) {
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
                    switch (Item_Blend.getCheckClass()) {
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
                    if (Item_Blend.getCheckClass() != class_id) {
                        if (!OpenHtml) {
                            OpenHtml = true;
                        }
                        pc.sendPackets(new S_ServerMessage(166, "職業必須是", Classmsg, "才能使用此道具"));
                    }
                }
                if (Item_Blend.getCheckItem() != 0 && !pc.getInventory().checkItem(Item_Blend.getCheckItem())) {
                    if (!OpenHtml) {
                        OpenHtml = true;
                    }
                    pc.sendPackets(new S_ServerMessage(166, "必須有", "【" + CheckItemtemp.getName() + "】", "才能使用此道具"));
                }
                if (Item_Blend.getHpConsume() != 0 || Item_Blend.getMpConsume() != 0) {
                    if (pc.getCurrentHp() < Item_Blend.getHpConsume()) {
                        if (!OpenHtml) {
                            OpenHtml = true;
                        }
                        pc.sendPackets(new S_ServerMessage(166, "$1083", "必須有 (" + Item_Blend.getHpConsume() + ") ", "才能使用此道具"));
                    }
                    if (pc.getCurrentMp() < Item_Blend.getMpConsume()) {
                        if (!OpenHtml) {
                            OpenHtml = true;
                        }
                        pc.sendPackets(new S_ServerMessage(166, "$1084", "必須有 (" + Item_Blend.getMpConsume() + ") ", "才能使用此道具"));
                    }
                }
                if (I_B_ma != 0 && ma_C != 0) {
                    if (!pc.getInventory().checkItem(I_B_ma, (long)ma_C)) {
                        if (!OpenHtml) {
                            OpenHtml = true;
                        }
                        pc.sendPackets(new S_ServerMessage(337, maNeme.getName() + "(" + ((long)ma_C - pc.getInventory().countItems(maNeme.getItemId())) + ")"));
                    }
                    else {
                        isConsumeItem = true;
                    }
                }
                if (I_B_ma2 != 0 && ma_C2 != 0) {
                    if (!pc.getInventory().checkItem(I_B_ma2, (long)ma_C2)) {
                        if (!OpenHtml) {
                            OpenHtml = true;
                        }
                        pc.sendPackets(new S_ServerMessage(337, maNeme2.getName() + "(" + ((long)ma_C2 - pc.getInventory().countItems(maNeme2.getItemId())) + ")"));
                    }
                    else {
                        isConsumeItem_2 = true;
                    }
                }
                if (I_B_ma3 != 0 && ma_C3 != 0) {
                    if (!pc.getInventory().checkItem(I_B_ma3, (long)ma_C3)) {
                        if (!OpenHtml) {
                            OpenHtml = true;
                        }
                        pc.sendPackets(new S_ServerMessage(337, maNeme3.getName() + "(" + ((long)ma_C3 - pc.getInventory().countItems(maNeme3.getItemId())) + ")"));
                    }
                    else {
                        isConsumeItem_3 = true;
                    }
                }
                if (I_B_ma4 != 0 && ma_C4 != 0) {
                    if (!pc.getInventory().checkItem(I_B_ma4, (long)ma_C4)) {
                        if (!OpenHtml) {
                            OpenHtml = true;
                        }
                        pc.sendPackets(new S_ServerMessage(337, maNeme4.getName() + "(" + ((long)ma_C4 - pc.getInventory().countItems(maNeme4.getItemId())) + ")"));
                    }
                    else {
                        isConsumeItem_4 = true;
                    }
                }
                if (I_B_ma5 != 0 && ma_C5 != 0) {
                    if (!pc.getInventory().checkItem(I_B_ma5, (long)ma_C5)) {
                        if (!OpenHtml) {
                            OpenHtml = true;
                        }
                        pc.sendPackets(new S_ServerMessage(337, maNeme5.getName() + "(" + ((long)ma_C5 - pc.getInventory().countItems(maNeme5.getItemId())) + ")"));
                    }
                    else {
                        isConsumeItem_5 = true;
                    }
                }
                if (OpenHtml) {
                    if (Item_Blend.getitem_Html() == 1) {
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
                        msg0 = temp.getName();
                        if (Item_Blend.getCheckLevel() != 0) {
                            msg2 = Item_Blend.getCheckLevel() + " 級 ";
                        }
                        else {
                            msg2 = " 無限制 ";
                        }
                        if (Item_Blend.getCheckClass() == 1) {
                            msg3 = " 王族";
                        }
                        else if (Item_Blend.getCheckClass() == 2) {
                            msg3 = " 騎士";
                        }
                        else if (Item_Blend.getCheckClass() == 3) {
                            msg3 = " 法師";
                        }
                        else if (Item_Blend.getCheckClass() == 4) {
                            msg3 = " 妖精";
                        }
                        else if (Item_Blend.getCheckClass() == 5) {
                            msg3 = " 黑妖";
                        }
                        else if (Item_Blend.getCheckClass() == 6) {
                            msg3 = " 龍騎士";
                        }
                        else if (Item_Blend.getCheckClass() == 7) {
                            msg3 = " 幻術師";
                        }
                        else if (Item_Blend.getCheckClass() == 0) {
                            msg3 = " 所有職業";
                        }
                        if (Item_Blend.getCheckItem() != 0) {
                            msg4 = new StringBuilder().append(CheckItemtemp.getName()).toString();
                        }
                        else {
                            msg4 = " 無";
                        }
                        if (Item_Blend.getHpConsume() != 0) {
                            msg5 = Item_Blend.getHpConsume() + " 滴 ";
                        }
                        else {
                            msg5 = " 無限制 ";
                        }
                        if (Item_Blend.getMpConsume() != 0) {
                            msg6 = Item_Blend.getMpConsume() + " 滴 ";
                        }
                        else {
                            msg6 = " 無限制 ";
                        }
                        if (I_B_ma != 0) {
                            msg7 = maNeme.getName() + "(" + ma_C + ")";
                        }
                        else {
                            msg7 = " 無";
                        }
                        if (I_B_ma2 != 0) {
                            msg8 = maNeme2.getName() + "(" + ma_C2 + ")";
                        }
                        else {
                            msg8 = " 無";
                        }
                        if (I_B_ma3 != 0) {
                            msg9 = maNeme3.getName() + "(" + ma_C3 + ")";
                        }
                        else {
                            msg9 = " 無";
                        }
                        if (I_B_ma4 != 0) {
                            msg10 = maNeme4.getName() + "(" + ma_C4 + ")";
                        }
                        else {
                            msg10 = " 無";
                        }
                        if (I_B_ma5 != 0) {
                            msg11 = maNeme5.getName() + "(" + ma_C5 + ")";
                        }
                        else {
                            msg11 = " 無";
                        }
                        msg12 = new StringBuilder().append(NewItemNeme.getName()).toString();
                        String[] msg13 = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12 };
                        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ItemBlend6", msg13));
                        return;
                    }
                    else {
                        return;
                    }
                }
                else {
                    isOK = true;
                }
            }
        }
        Random _random = new Random();
        int DBrnd = Item_Blend.getRandom();
        int Rnd100 = _random.nextInt(100) + 1;
        int New_item = Item_Blend.getNew_item();
        int New_item_counts = Item_Blend.getNew_item_counts();
        int ItemLV_SW = Item_Blend.getNew_Enchantlvl_SW();
        int ItemLV = Item_Blend.getNew_item_Enchantlvl();
        int ItemLV_rnd = _random.nextInt(ItemLV + 1);
        if (isOK) {
            if (Rnd100 <= DBrnd) {
                int type2 = NewItemNeme.getType2();
                if (Item_Blend.getMessage() != null) {
                    pc.sendPackets(new S_ServerMessage(166, Item_Blend.getMessage()));
                }
                else {
                    pc.sendPackets(new S_SystemMessage("進行道具融合，請稍後。"));
                }
                if (type2 == 1 || type2 == 2) {
                    if (ItemLV == 0) {
                        createNewItem(pc, New_item, 1);
                        if (Item_Blend.getgfxid() != 0) {
                            pc.sendPackets(new S_SkillSound(pc.getId(), Item_Blend.getgfxid()));
                            pc.broadcastPacketAll(new S_SkillSound(pc.getId(), Item_Blend.getgfxid()));
                        }
                        if (Item_Blend.getcheckmsg() == 1) {
                            World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Item_Blend.getcheckmsgall(), pc.getName())));
                        }
                    }
                    else if (ItemLV_SW == 0) {
                        createNewItem_LV(pc, New_item, 1, ItemLV);
                        if (Item_Blend.getgfxid() != 0) {
                            pc.sendPackets(new S_SkillSound(pc.getId(), Item_Blend.getgfxid()));
                            pc.broadcastPacketAll(new S_SkillSound(pc.getId(), Item_Blend.getgfxid()));
                        }
                        if (Item_Blend.getcheckmsg() == 1) {
                            World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Item_Blend.getcheckmsgall(), pc.getName())));
                        }
                    }
                    else {
                        createNewItem_LV(pc, New_item, 1, ItemLV_rnd);
                        if (Item_Blend.getgfxid() != 0) {
                            pc.sendPackets(new S_SkillSound(pc.getId(), Item_Blend.getgfxid()));
                            pc.broadcastPacketAll(new S_SkillSound(pc.getId(), Item_Blend.getgfxid()));
                        }
                        if (Item_Blend.getcheckmsg() == 1) {
                            World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Item_Blend.getcheckmsgall(), pc.getName())));
                        }
                    }
                }
                else {
                    createNewItem(pc, New_item, New_item_counts);
                    if (Item_Blend.getgfxid() != 0) {
                        pc.sendPackets(new S_SkillSound(pc.getId(), Item_Blend.getgfxid()));
                        pc.broadcastPacketAll(new S_SkillSound(pc.getId(), Item_Blend.getgfxid()));
                    }
                    if (Item_Blend.getcheckmsg() == 1) {
                        World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Item_Blend.getcheckmsgall(), pc.getName())));
                    }
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("道具融合失敗。"));
            }
            isBlend = true;
            if (isBlend) {
                if (Item_Blend.getRemoveItem() == 1) {
                    pc.getInventory().removeItem(l1iteminstance, 1L);
                }
                if (isConsumeItem) {
                    pc.getInventory().consumeItem(I_B_ma, (long)ma_C);
                }
                if (isConsumeItem_2) {
                    pc.getInventory().consumeItem(I_B_ma2, (long)ma_C2);
                }
                if (isConsumeItem_3) {
                    pc.getInventory().consumeItem(I_B_ma3, (long)ma_C3);
                }
                if (isConsumeItem_4) {
                    pc.getInventory().consumeItem(I_B_ma4, (long)ma_C4);
                }
                if (isConsumeItem_5) {
                    pc.getInventory().consumeItem(I_B_ma5, (long)ma_C5);
                }
                pc.setCurrentHp(pc.getCurrentHp() - Item_Blend.getHpConsume());
                pc.setCurrentMp(pc.getCurrentMp() - Item_Blend.getMpConsume());
            }
            return;
        }
        else {
            return;
        }
    }
    
    public static boolean createNewItem(L1PcInstance pc, int item_id, int count) {
        L1ItemInstance item = ItemTable.get().createItem(item_id);
        item.setCount((long)count);
        if (item != null) {
            if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                pc.getInventory().storeItem(item);
            }
            else {
                World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
            }
            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
            return true;
        }
        else {
            return false;
        }
    }
    
    public static boolean createNewItem_LV(L1PcInstance pc, int item_id, int count, int EnchantLevel) {
        L1ItemInstance item = ItemTable.get().createItem(item_id);
        item.setCount((long)count);
        item.setEnchantLevel(EnchantLevel);
        if (item != null) {
            if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                pc.getInventory().storeItem(item);
            }
            else {
                World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
            }
            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
            return true;
        }
        else {
            return false;
        }
    }
}

package com.lineage.server.model.Instance;

import java.util.TimerTask;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1AttrWeapon;
import com.lineage.server.datatables.ExtraAttrWeaponTable;
import com.lineage.server.datatables.ItemRestrictionsTable;
import java.util.Timer;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.server.utils.RangeLong;
import com.lineage.server.model.L1Armortype_name;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.server.datatables.PowerItemTable;
import com.lineage.william.L1WilliamSystemMessage;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.datatables.ItemVIPTable;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.lock.PetReading;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.server.templates.L1MagicWeapon;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.model.L1EquipmentTimer;
import java.sql.Timestamp;
import com.lineage.server.templates.L1Item;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import com.lineage.server.model.L1Object;

public class L1ItemInstance extends L1Object
{
    private static final Log _log;
    private final HashMap<Integer, Integer> _skilllist;
    private static final long serialVersionUID = 1L;
    private long _count;
    private int _itemId;
    private boolean _isEquipped;
    private boolean _isEquippedTemp;
    private int _enchantLevel;
    private boolean _isIdentified;
    private int _durability;
    private int _chargeCount;
    private int _remainingTime;
    private int _lastWeight;
    private boolean _isRunning;
    private int _bless;
    private int _savebless;
    private int _attrEnchantKind;
    private int _attrEnchantLevel;
    private int _ItemAttack;
    private int _ItemBowAttack;
    private int _ItemHit;
    private int _ItemBowHit;
    private int _ItemReductionDmg;
    private int _ItemSp;
    private int _Itemprobability;
    private int _ItemStr;
    private int _ItemDex;
    private int _ItemCon;
    private int _ItemCha;
    private int _ItemWis;
    private int _ItemInt;
    private int _ItemHp;
    private int _ItemMp;
    private int _ItemMr;
    private int _ItemAc;
    private int _ItemMag_Red;
    private int _ItemMag_Hit;
    private int _ItemDg;
    private int _ItemistSustain;
    private int _ItemistStun;
    private int _ItemistStone;
    private int _ItemistSleep;
    private int _ItemistFreeze;
    private int _ItemistBlind;
    private int _ItemArmorType;
    private int _ItemArmorLv;
    private int _ItemType;
    private int _ItemHpr;
    private int _ItemMpr;
    private int _Itemhppotion;
    private int _skilltype;
    private int _skilltypelv;
    private String _racegamno;
    private L1Item _item;
    private Timestamp _lastUsed;
    private final L1ItemInstance.LastStatus _lastStatus;
    private L1PcInstance _pc;
    private L1ItemInstance.EnchantTimer _timer;
    private int _gamNo;
    private int _gamNpcId;
    private String _starNpcId;
    private int _acByMagic;
    private int _dmgByMagic;
    private int _holyDmgByMagic;
    private int _hitByMagic;
    private int _itemOwnerId;
    private L1EquipmentTimer _equipmentTimer;
    private boolean _isNowLighting;
    private boolean _isMatch;
    private int _char_objid;
    private Timestamp _time;
    private int _card_use;
    private int _keyId;
    private int _innNpcId;
    private boolean _isHall;
    private Timestamp _dueTime;
    private L1ItemPower_name _powerdata;
    private int _ringId;
    private int _earringId;
    private boolean proctect10;
    private boolean proctect;
    private boolean proctect1;
    private boolean proctect2;
    private boolean proctect3;
    private boolean proctect4;
    private boolean proctect5;
    private boolean proctect6;
    private boolean proctect7;
    private L1ItemPower_name _power_name;
    private L1MagicWeapon _magic_weapon;
    private L1ItemSpecialAttributeChar _ItemAttrName;
    private int _dropitemcheck;
    private String _dropitemname;
    
    static {
        _log = LogFactory.getLog(L1ItemInstance.class);
    }
    
    public L1ItemInstance() {
        this._skilllist = new HashMap();
        this._lastStatus = new L1ItemInstance.LastStatus();
        this._acByMagic = 0;
        this._dmgByMagic = 0;
        this._holyDmgByMagic = 0;
        this._hitByMagic = 0;
        this._itemOwnerId = 0;
        this._isNowLighting = false;
        this._isMatch = false;
        this._char_objid = -1;
        this._time = null;
        this._card_use = 0;
        this._keyId = 0;
        this._innNpcId = 0;
        this._powerdata = null;
        this.proctect10 = false;
        this.proctect = false;
        this.proctect1 = false;
        this.proctect2 = false;
        this.proctect3 = false;
        this.proctect4 = false;
        this.proctect5 = false;
        this.proctect6 = false;
        this.proctect7 = false;
        this._power_name = null;
        this._ItemAttrName = null;
        this._count = 1L;
        this._enchantLevel = 0;
    }
    
    public L1ItemInstance(L1Item item, long count) {
        this();
        this.setItem(item);
        this.setCount(count);
    }
    
    public boolean isIdentified() {
        return this._isIdentified;
    }
    
    public void setIdentified(boolean identified) {
        this._isIdentified = identified;
    }
    
    public String getName() {
        return this._item.getName();
    }
    
    public long getCount() {
        return this._count;
    }
    
    public void setCount(long count) {
        this._count = count;
    }
    
    public String getraceGamNo() {
        return this._racegamno;
    }
    
    public void setraceGamNo(String racegamno) {
        this._racegamno = racegamno;
    }
    
    public boolean isEquipped() {
        return this._isEquipped;
    }
    
    public void setEquipped(boolean equipped) {
        this._isEquipped = equipped;
    }
    
    public L1Item getItem() {
        return this._item;
    }
    
    public void setItem(L1Item item) {
        this._item = item;
        this._itemId = item.getItemId();
    }
    
    public int getItemId() {
        return this._itemId;
    }
    
    public void setItemId(int itemId) {
        this._itemId = itemId;
    }
    
    public boolean isStackable() {
        return this._item.isStackable();
    }
    
    @Override
    public void onAction(L1PcInstance player) {
    }
    
    public int getEnchantLevel() {
        return this._enchantLevel;
    }
    
    public void setEnchantLevel(int enchantLevel) {
        this._enchantLevel = enchantLevel;
    }
    
    public int get_gfxid() {
        int gfxid = this._item.getGfxId();
        return gfxid;
    }
    
    public int get_durability() {
        return this._durability;
    }
    
    public int getChargeCount() {
        return this._chargeCount;
    }
    
    public void setChargeCount(int i) {
        this._chargeCount = i;
    }
    
    public int getRemainingTime() {
        return this._remainingTime;
    }
    
    public void setRemainingTime(int i) {
        this._remainingTime = i;
    }
    
    public void setLastUsed(Timestamp t) {
        this._lastUsed = t;
    }
    
    public Timestamp getLastUsed() {
        return this._lastUsed;
    }
    
    public int getLastWeight() {
        return this._lastWeight;
    }
    
    public void setLastWeight(int weight) {
        this._lastWeight = weight;
    }
    
    public void setBless(int i) {
        this._bless = i;
    }
    
    public int getBless() {
        return this._bless;
    }
    
    public void setsaveBless(int i) {
        this._savebless = i;
    }
    
    public int getsaveBless() {
        return this._savebless;
    }
    
    public void setAttrEnchantKind(int i) {
        this._attrEnchantKind = i;
    }
    
    public int getAttrEnchantKind() {
        return this._attrEnchantKind;
    }
    
    public void setAttrEnchantLevel(int i) {
        this._attrEnchantLevel = i;
    }
    
    public int getAttrEnchantLevel() {
        return this._attrEnchantLevel;
    }
    
    public void setItemAttack(int i) {
        this._ItemAttack = i;
    }
    
    public int getItemAttack() {
        return this._ItemAttack;
    }
    
    public void setItemBowAttack(int i) {
        this._ItemBowAttack = i;
    }
    
    public int getItemBowAttack() {
        return this._ItemBowAttack;
    }
    
    public void setItemHit(int i) {
        this._ItemHit = i;
    }
    
    public int getItemHit() {
        return this._ItemHit;
    }
    
    public void setItemBowHit(int i) {
        this._ItemBowHit = i;
    }
    
    public int getItemBowHit() {
        return this._ItemBowHit;
    }
    
    public void setItemReductionDmg(int i) {
        this._ItemReductionDmg = i;
    }
    
    public int getItemReductionDmg() {
        return this._ItemReductionDmg;
    }
    
    public void setItemSp(int i) {
        this._ItemSp = i;
    }
    
    public int getItemSp() {
        return this._ItemSp;
    }
    
    public void setItemprobability(int i) {
        this._Itemprobability = i;
    }
    
    public int getItemprobability() {
        return this._Itemprobability;
    }
    
    public void setItemStr(int i) {
        this._ItemStr = i;
    }
    
    public int getItemStr() {
        return this._ItemStr;
    }
    
    public void setItemDex(int i) {
        this._ItemDex = i;
    }
    
    public int getItemDex() {
        return this._ItemDex;
    }
    
    public void setItemInt(int i) {
        this._ItemInt = i;
    }
    
    public int getItemInt() {
        return this._ItemInt;
    }
    
    public void setItemHp(int i) {
        this._ItemHp = i;
    }
    
    public int getItemHp() {
        return this._ItemHp;
    }
    
    public void setItemMp(int i) {
        this._ItemMp = i;
    }
    
    public int getItemMp() {
        return this._ItemMp;
    }
    
    public int getItemCon() {
        return this._ItemCon;
    }
    
    public void setItemCon(int i) {
        this._ItemCon = i;
    }
    
    public int getItemWis() {
        return this._ItemWis;
    }
    
    public void setItemWis(int i) {
        this._ItemWis = i;
    }
    
    public int getItemCha() {
        return this._ItemCha;
    }
    
    public void setItemCha(int i) {
        this._ItemCha = i;
    }
    
    public void setskilltype(int _SkillType) {
        this._skilltype = _SkillType;
    }
    
    public int getskilltype() {
        return this._skilltype;
    }
    
    public void setskilltypelv(int _SkillTypelv) {
        this._skilltypelv = _SkillTypelv;
    }
    
    public int getskilltypelv() {
        return this._skilltypelv;
    }
    
    public int getItemMr() {
        return this._ItemMr;
    }
    
    public void setItemMr(int i) {
        this._ItemMr = i;
    }
    
    public int getItemAc() {
        return this._ItemAc;
    }
    
    public void setItemAc(int i) {
        this._ItemAc = i;
    }
    
    public int getItemMag_Red() {
        return this._ItemMag_Red;
    }
    
    public void setItemMag_Red(int i) {
        this._ItemMag_Red = i;
    }
    
    public int getItemMag_Hit() {
        return this._ItemMag_Hit;
    }
    
    public void setItemMag_Hit(int i) {
        this._ItemMag_Hit = i;
    }
    
    public int getItemDg() {
        return this._ItemDg;
    }
    
    public void setItemDg(int i) {
        this._ItemDg = i;
    }
    
    public int getItemistSustain() {
        return this._ItemistSustain;
    }
    
    public void setItemistSustain(int i) {
        this._ItemistSustain = i;
    }
    
    public int getItemistStun() {
        return this._ItemistStun;
    }
    
    public void setItemistStun(int i) {
        this._ItemistStun = i;
    }
    
    public int getItemistStone() {
        return this._ItemistStone;
    }
    
    public void setItemistStone(int i) {
        this._ItemistStone = i;
    }
    
    public int getItemistSleep() {
        return this._ItemistSleep;
    }
    
    public void setItemistSleep(int i) {
        this._ItemistSleep = i;
    }
    
    public int getItemistFreeze() {
        return this._ItemistFreeze;
    }
    
    public void setItemistFreeze(int i) {
        this._ItemistFreeze = i;
    }
    
    public int getItemistBlind() {
        return this._ItemistBlind;
    }
    
    public void setItemistBlind(int i) {
        this._ItemistBlind = i;
    }
    
    public int getItemArmorType() {
        return this._ItemArmorType;
    }
    
    public void setItemArmorType(int i) {
        this._ItemArmorType = i;
    }
    
    public int getItemArmorLv() {
        return this._ItemArmorLv;
    }
    
    public void setItemArmorLv(int i) {
        this._ItemArmorLv = i;
    }
    
    public void setItemHpr(int i) {
        this._ItemHpr = i;
    }
    
    public int getItemHpr() {
        return this._ItemHpr;
    }
    
    public void setItemMpr(int i) {
        this._ItemMpr = i;
    }
    
    public int getItemMpr() {
        return this._ItemMpr;
    }
    
    public void setItemhppotion(int i) {
        this._Itemhppotion = i;
    }
    
    public int getItemhppotion() {
        return this._Itemhppotion;
    }
    
    public int getItemType() {
        return this._ItemType;
    }
    
    public void setItemType(int i) {
        this._ItemType = i;
    }
    
    public void set_durability(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i > 127) {
            i = 127;
        }
        this._durability = i;
    }
    
    public int getWeight() {
        if (this.getItem().getWeight() == 0) {
            return 0;
        }
        else {
            return (int)Math.max(this.getCount() * (long)this.getItem().getWeight() / 1000L, 1L);
        }
    }
    
    public L1ItemInstance.LastStatus getLastStatus() {
        return this._lastStatus;
    }
    
    public int getRecordingColumns() {
        int column = 0;
        if (this.getCount() != this._lastStatus.count) {
            column += 16;
        }
        if (this.getItemId() != this._lastStatus.itemId) {
            column += 64;
        }
        if (this.isEquipped() != this._lastStatus.isEquipped) {
            column += 8;
        }
        if (this.getEnchantLevel() != this._lastStatus.enchantLevel) {
            column += 4;
        }
        if (this.get_durability() != this._lastStatus.durability) {
            ++column;
        }
        if (this.getChargeCount() != this._lastStatus.chargeCount) {
            column += 128;
        }
        if (this.getLastUsed() != this._lastStatus.lastUsed) {
            column += 32;
        }
        if (this.isIdentified() != this._lastStatus.isIdentified) {
            column += 2;
        }
        if (this.getRemainingTime() != this._lastStatus.remainingTime) {
            column += 256;
        }
        if (this.getBless() != this._lastStatus.bless) {
            column += 512;
        }
        if (this.getAttrEnchantKind() != this._lastStatus.attrEnchantKind) {
            column += 1024;
        }
        if (this.getAttrEnchantLevel() != this._lastStatus.attrEnchantLevel) {
            column += 2048;
        }
        return column;
    }
    
    public String getNumberedViewName(long count) {
        StringBuilder name = new StringBuilder(this.getNumberedName(count));
        if (this.getproctect() || this.getproctect1() || this.getproctect2()) {
            name.append("(保護中)");
        }
        else if (this.getproctect3() || this.getproctect4() || this.getproctect5()) {
            name.append("(機率保護中)");
        }
        else if (this.getproctect10()) {
            name.append("(可交易中)");
        }
        if (this.getproctect6() || this.getproctect7()) {
            name.append("(升階保護中)");
        }
        if (this._power_name != null) {
            name.append(" ");
            for (int i = 0; i < this._power_name.get_hole_count(); ++i) {
                switch (i) {
                    case 0: {
                        name.append(this.set_hole_name(this._power_name.get_hole_1()));
                        break;
                    }
                    case 1: {
                        name.append(this.set_hole_name(this._power_name.get_hole_2()));
                        break;
                    }
                    case 2: {
                        name.append(this.set_hole_name(this._power_name.get_hole_3()));
                        break;
                    }
                    case 3: {
                        name.append(this.set_hole_name(this._power_name.get_hole_4()));
                        break;
                    }
                    case 4: {
                        name.append(this.set_hole_name(this._power_name.get_hole_5()));
                        break;
                    }
                }
            }
        }
        switch (this._item.getUseType()) {
            case -12: {
                if (this.isEquipped()) {
                    name.append(" ($117)");
                    break;
                }
                else {
                    break;
                }
            }
            case -4: {
                L1Pet pet = PetReading.get().getTemplate(this.getId());
                if (pet != null) {
                    L1Npc npc = NpcTable.get().getTemplate(pet.get_npcid());
                    name.append("[Lv." + pet.get_level() + "]" + pet.get_name() + " HP" + pet.get_hp() + " " + npc.get_nameid());
                    break;
                }
                else {
                    break;
                }
            }
            case 1: {
                if (this.isEquipped()) {
                    name.append(" ($9)");
                    break;
                }
                else {
                    break;
                }
            }
            case 2:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 37:
            case 40:
            case 43:
            case 44:
            case 45:
            case 47:
            case 48:
            case 49:
            case 51: {
                if (this.isEquipped()) {
                    name.append(" ($117)");
                    break;
                }
                else {
                    break;
                }
            }
            case 10: {
                if (this.isNowLighting()) {
                    name.append(" ($10)");
                }
                switch (this._item.getItemId()) {
                    case 40001:
                    case 40002: {
                        if (this.getRemainingTime() <= 0) {
                            name.append(" ($11)");
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            default: {
                if (this.isEquippedTemp()) {
                    name.append(" ($117)");
                }
                if (ItemVIPTable.get().checkVIP(this._itemId) && this.isEquipped()) {
                    name.append(" ($117)");
                    break;
                }
                else {
                    break;
                }
            }
        }
        return name.toString();
    }
    
    public String getNumberedViewName_shop(long count) {
        StringBuilder name = new StringBuilder(this.getNumberedName(count));
        if (this._power_name != null) {
            name.append(" ");
            for (int i = 0; i < this._power_name.get_hole_count(); ++i) {
                switch (i) {
                    case 0: {
                        name.append(this.set_hole_name(this._power_name.get_hole_1()));
                        break;
                    }
                    case 1: {
                        name.append(this.set_hole_name(this._power_name.get_hole_2()));
                        break;
                    }
                    case 2: {
                        name.append(this.set_hole_name(this._power_name.get_hole_3()));
                        break;
                    }
                    case 3: {
                        name.append(this.set_hole_name(this._power_name.get_hole_4()));
                        break;
                    }
                    case 4: {
                        name.append(this.set_hole_name(this._power_name.get_hole_5()));
                        break;
                    }
                }
            }
        }
        switch (this._item.getUseType()) {
            case -12: {
                if (this.isEquipped()) {
                    name.append(" ($117)");
                    break;
                }
                else {
                    break;
                }
            }
            case -4: {
                L1Pet pet = PetReading.get().getTemplate(this.getId());
                if (pet != null) {
                    L1Npc npc = NpcTable.get().getTemplate(pet.get_npcid());
                    name.append("[Lv." + pet.get_level() + "]" + pet.get_name() + " HP" + pet.get_hp() + " " + npc.get_nameid());
                    break;
                }
                else {
                    break;
                }
            }
            case 1: {
                if (this.isEquipped()) {
                    name.append(" ($9)");
                    break;
                }
                else {
                    break;
                }
            }
            case 2:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 37:
            case 40:
            case 43:
            case 44:
            case 45:
            case 47:
            case 48:
            case 49:
            case 51: {
                if (this.isEquipped()) {
                    name.append(" ($117)");
                    break;
                }
                else {
                    break;
                }
            }
            case 10: {
                if (this.isNowLighting()) {
                    name.append(" ($10)");
                }
                switch (this._item.getItemId()) {
                    case 40001:
                    case 40002: {
                        if (this.getRemainingTime() <= 0) {
                            name.append(" ($11)");
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            default: {
                if (this.isEquippedTemp()) {
                    name.append(" ($117)");
                }
                if (ItemVIPTable.get().checkVIP(this._itemId) && this.isEquipped()) {
                    name.append(" ($117)");
                    break;
                }
                else {
                    break;
                }
            }
        }
        return name.toString();
    }
    
    private String set_hole_name(int hole) {
        String string = "";
        if (hole == 0) {
            string = L1WilliamSystemMessage.ShowMessage(10001);
        }
        else {
            string = PowerItemTable.get().getItem(hole).powername;
        }
        return string;
    }
    
    public String getViewName() {
        return this.getNumberedViewName(this._count);
    }
    
    public String getLogName() {
        return this.getNumberedName(this._count);
    }
    
    public String getLogName_attr() {
        return this.getNumberedName_attr(this._count);
    }
    
    public String getNumberedName(long count) {
        StringBuilder name = new StringBuilder();
        L1ItemSpecialAttributeChar attr_char = this.get_ItemAttrName();
        if (attr_char != null) {
            L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
            name.append(attr.get_colour());
            name.append(attr.get_name() + " ");
        }
if (this.getItemArmorType() != 0) {
            String name2 = "";
            switch (this.getItemArmorType()) {
                case 1: name2 = L1Armortype_name._1; break;
                case 2: name2 = L1Armortype_name._2; break;
                case 3: name2 = L1Armortype_name._3; break;
                case 4: name2 = L1Armortype_name._4; break;
                case 5: name2 = L1Armortype_name._5; break;
                case 6: name2 = L1Armortype_name._6; break;
                case 7: name2 = L1Armortype_name._7; break;
                case 8: name2 = L1Armortype_name._8; break;
                case 9: name2 = L1Armortype_name._9; break;
                case 10: name2 = L1Armortype_name._10; break;
                case 11: name2 = L1Armortype_name._11; break;
                case 12: name2 = L1Armortype_name._12; break;
                case 13: name2 = L1Armortype_name._13; break;
                case 14: name2 = L1Armortype_name._14; break;
                case 15: name2 = L1Armortype_name._15; break;
                case 16: name2 = L1Armortype_name._16; break;
                case 17: name2 = L1Armortype_name._17; break;
                case 18: name2 = L1Armortype_name._18; break;
                case 19: name2 = L1Armortype_name._19; break;
                case 20: name2 = L1Armortype_name._20; break;
                case 21: name2 = L1Armortype_name._21; break;
                case 22: name2 = L1Armortype_name._22; break;
                case 23: name2 = L1Armortype_name._23; break;
                case 24: name2 = L1Armortype_name._24; break;
                case 25: name2 = L1Armortype_name._25; break;
                case 26: name2 = L1Armortype_name._26; break;
                case 27: name2 = L1Armortype_name._27; break;
                case 28: name2 = L1Armortype_name._28; break;
                case 29: name2 = L1Armortype_name._29; break;
                case 30: name2 = L1Armortype_name._30; break;
                case 31: name2 = L1Armortype_name._31; break;
                case 32: name2 = L1Armortype_name._32; break;
                case 33: name2 = L1Armortype_name._33; break;
                case 34: name2 = L1Armortype_name._34; break;
                case 35: name2 = L1Armortype_name._35; break;
                case 36: name2 = L1Armortype_name._36; break;
                case 37: name2 = L1Armortype_name._37; break;
                case 38: name2 = L1Armortype_name._38; break;
                case 39: name2 = L1Armortype_name._39; break;
                case 40: name2 = L1Armortype_name._40; break;
                case 41: name2 = L1Armortype_name._41; break;
                case 42: name2 = L1Armortype_name._42; break;
                case 43: name2 = L1Armortype_name._43; break;
                case 44: name2 = L1Armortype_name._44; break;
                case 45: name2 = L1Armortype_name._45; break;
                case 46: name2 = L1Armortype_name._46; break;
                case 47: name2 = L1Armortype_name._47; break;
                case 48: name2 = L1Armortype_name._48; break;
                case 49: name2 = L1Armortype_name._49; break;
                case 50: name2 = L1Armortype_name._50; break;
                case 51: name2 = L1Armortype_name._51; break;
                case 52: name2 = L1Armortype_name._52; break;
                case 53: name2 = L1Armortype_name._53; break;
                case 54: name2 = L1Armortype_name._54; break;
                case 55: name2 = L1Armortype_name._55; break;
                case 56: name2 = L1Armortype_name._56; break;
                case 57: name2 = L1Armortype_name._57; break;
                case 58: name2 = L1Armortype_name._58; break;
                case 59: name2 = L1Armortype_name._59; break;
                case 60: name2 = L1Armortype_name._60; break;
                // --- 追加 61 ~ 100 ---
                case 61: name2 = L1Armortype_name._61; break;
                case 62: name2 = L1Armortype_name._62; break;
                case 63: name2 = L1Armortype_name._63; break;
                case 64: name2 = L1Armortype_name._64; break;
                case 65: name2 = L1Armortype_name._65; break;
                case 66: name2 = L1Armortype_name._66; break;
                case 67: name2 = L1Armortype_name._67; break;
                case 68: name2 = L1Armortype_name._68; break;
                case 69: name2 = L1Armortype_name._69; break;
                case 70: name2 = L1Armortype_name._70; break;
                case 71: name2 = L1Armortype_name._71; break;
                case 72: name2 = L1Armortype_name._72; break;
                case 73: name2 = L1Armortype_name._73; break;
                case 74: name2 = L1Armortype_name._74; break;
                case 75: name2 = L1Armortype_name._75; break;
                case 76: name2 = L1Armortype_name._76; break;
                case 77: name2 = L1Armortype_name._77; break;
                case 78: name2 = L1Armortype_name._78; break;
                case 79: name2 = L1Armortype_name._79; break;
                case 80: name2 = L1Armortype_name._80; break;
                case 81: name2 = L1Armortype_name._81; break;
                case 82: name2 = L1Armortype_name._82; break;
                case 83: name2 = L1Armortype_name._83; break;
                case 84: name2 = L1Armortype_name._84; break;
                case 85: name2 = L1Armortype_name._85; break;
                case 86: name2 = L1Armortype_name._86; break;
                case 87: name2 = L1Armortype_name._87; break;
                case 88: name2 = L1Armortype_name._88; break;
                case 89: name2 = L1Armortype_name._89; break;
                case 90: name2 = L1Armortype_name._90; break;
                case 91: name2 = L1Armortype_name._91; break;
                case 92: name2 = L1Armortype_name._92; break;
                case 93: name2 = L1Armortype_name._93; break;
                case 94: name2 = L1Armortype_name._94; break;
                case 95: name2 = L1Armortype_name._95; break;
                case 96: name2 = L1Armortype_name._96; break;
                case 97: name2 = L1Armortype_name._97; break;
                case 98: name2 = L1Armortype_name._98; break;
                case 99: name2 = L1Armortype_name._99; break;
                case 100: name2 = L1Armortype_name._100; break;
            }
            name.append(name2);
        }
		
        if (this.isIdentified()) {
            switch (this._item.getUseType()) {
                case 1: {
                    if (this.getEnchantLevel() >= 0) {
                        name.append("+" + this.getEnchantLevel() + " ");
                    }
                    else if (this.getEnchantLevel() < 0) {
                        name.append(String.valueOf(this.getEnchantLevel()) + " ");
                    }
                    int attrEnchantLevel = this.getAttrEnchantLevel();
                    if (attrEnchantLevel > 0) {
                        name.append(this.attrEnchantLevel());
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 37:
                case 40:
                case 47:
                case 48:
                case 49:
                case 51: {
                    if (this.getEnchantLevel() >= 0) {
                        name.append("+" + this.getEnchantLevel() + " ");
                        break;
                    }
                    else if (this.getEnchantLevel() < 0) {
                        name.append(String.valueOf(this.getEnchantLevel()) + " ");
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
        }
        name.append(this._item.getName());
        if (this._item.getUseType() == -5) {
            name.append("\\f_[" + this.getraceGamNo() + "]");
        }
        if (this.isIdentified()) {
            if (this.getItem().getMaxChargeCount() > 0) {
                name.append(" (" + this.getChargeCount() + ")");
            }
            else {
                switch (this._item.getItemId()) {
                    case 20383: {
                        name.append(" (" + this.getChargeCount() + ")");
                        break;
                    }
                }
            }
            if (this._time == null && this.getItem().getMaxUseTime() > 0) {
                name.append(" [" + this.getRemainingTime() + "]");
            }
        }
        if (count > 1L) {
            if (count < 1000000000L) {
                name.append(" (" + count + ")");
            }
            else {
                name.append(" (" + RangeLong.scount(count) + ")");
            }
        }
        return name.toString();
    }
    
    public String getNumberedName_attr(long count) {
        StringBuilder name = new StringBuilder();
        switch (this._item.getUseType()) {
            case 1: {
                if (this.getEnchantLevel() >= 0) {
                    name.append("+" + this.getEnchantLevel() + " ");
                }
                else if (this.getEnchantLevel() < 0) {
                    name.append(String.valueOf(this.getEnchantLevel()) + " ");
                }
                int attrEnchantLevel = this.getAttrEnchantLevel();
                if (attrEnchantLevel > 0) {
                    name.append(this.attrEnchantLevel());
                    break;
                }
                else {
                    break;
                }
            }
            case 2:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 37:
            case 40:
            case 47:
            case 48:
            case 49:
            case 51: {
                if (this.getEnchantLevel() >= 0) {
                    name.append("+" + this.getEnchantLevel() + " ");
                    break;
                }
                else if (this.getEnchantLevel() < 0) {
                    name.append(String.valueOf(this.getEnchantLevel()) + " ");
                    break;
                }
                else {
                    break;
                }
            }
        }
        name.append(this._item.getName());
        if (this._item.getUseType() == -5) {
            name.append("\\f_[" + this.getraceGamNo() + "]");
        }
        if (this.isIdentified()) {
            if (this.getItem().getMaxChargeCount() > 0) {
                name.append(" (" + this.getChargeCount() + ")");
            }
            else {
                switch (this._item.getItemId()) {
                    case 20383: {
                        name.append(" (" + this.getChargeCount() + ")");
                        break;
                    }
                }
            }
            if (this._time == null && this.getItem().getMaxUseTime() > 0) {
                name.append(" [" + this.getRemainingTime() + "]");
            }
        }
        if (count > 1L) {
            if (count < 1000000000L) {
                name.append(" (" + count + ")");
            }
            else {
                name.append(" (" + RangeLong.scount(count) + ")");
            }
        }
        return name.toString();
    }
    
    public String getWarehouseHistoryName() {
        StringBuilder name = new StringBuilder();
        if (this.isIdentified()) {
            if (this.getItem().getType2() == 1) {
                int attrEnchantLevel = this.getAttrEnchantLevel();
                if (attrEnchantLevel > 0) {
                    String attrStr = null;
                    switch (this.getAttrEnchantKind()) {
                        case 1: {
                            if (attrEnchantLevel == 1) {
                                attrStr = "$6124";
                                break;
                            }
                            else if (attrEnchantLevel == 2) {
                                attrStr = "$6125";
                                break;
                            }
                            else if (attrEnchantLevel == 3) {
                                attrStr = "$6126";
                                break;
                            }
                            else if (attrEnchantLevel == 4) {
                                attrStr = "$14364";
                                break;
                            }
                            else if (attrEnchantLevel == 5) {
                                attrStr = "$14368";
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        case 2: {
                            if (attrEnchantLevel == 1) {
                                attrStr = "$6115";
                                break;
                            }
                            else if (attrEnchantLevel == 2) {
                                attrStr = "$6116";
                                break;
                            }
                            else if (attrEnchantLevel == 3) {
                                attrStr = "$6117";
                                break;
                            }
                            else if (attrEnchantLevel == 4) {
                                attrStr = "$14361";
                                break;
                            }
                            else if (attrEnchantLevel == 5) {
                                attrStr = "$14365";
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        case 4: {
                            if (attrEnchantLevel == 1) {
                                attrStr = "$6118";
                                break;
                            }
                            else if (attrEnchantLevel == 2) {
                                attrStr = "$6119";
                                break;
                            }
                            else if (attrEnchantLevel == 3) {
                                attrStr = "$6120";
                                break;
                            }
                            else if (attrEnchantLevel == 4) {
                                attrStr = "$14362";
                                break;
                            }
                            else if (attrEnchantLevel == 5) {
                                attrStr = "$14366";
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        case 8: {
                            if (attrEnchantLevel == 1) {
                                attrStr = "$6121";
                                break;
                            }
                            else if (attrEnchantLevel == 2) {
                                attrStr = "$6122";
                                break;
                            }
                            else if (attrEnchantLevel == 3) {
                                attrStr = "$6123";
                                break;
                            }
                            else if (attrEnchantLevel == 4) {
                                attrStr = "$14363";
                                break;
                            }
                            else if (attrEnchantLevel == 5) {
                                attrStr = "$14367";
                                break;
                            }
                            else {
                                break;
                            }
                        }
                    }
                    name.append(attrStr + " ");
                }
            }
            if (this.getItem().getType2() == 1 || this.getItem().getType2() == 2) {
                if (this.getEnchantLevel() >= 0) {
                    name.append("+" + this.getEnchantLevel() + " ");
                }
                else if (this.getEnchantLevel() < 0) {
                    name.append(String.valueOf(this.getEnchantLevel()) + " ");
                }
            }
        }
        name.append(this._item.getName());
        if (this.isIdentified()) {
            if (this.getItem().getMaxChargeCount() > 0) {
                name.append(" (" + this.getChargeCount() + ")");
            }
            if (this.getItem().getItemId() == 20383) {
                name.append(" (" + this.getChargeCount() + ")");
            }
        }
        return name.toString();
    }
    
    public String getInnKeyName() {
        StringBuilder name = new StringBuilder();
        name.append(" #");
        String chatText = String.valueOf(this.getKeyId());
        String s1 = "";
        String s2 = "";
        for (int i = 0; i < chatText.length() && i < 5; ++i) {
            s1 = s1 + String.valueOf(chatText.charAt(i));
        }
        name.append(s1);
        for (int i = 0; i < chatText.length(); ++i) {
            if (i % 2 == 0) {
                s1 = String.valueOf(chatText.charAt(i));
            }
            else {
                s2 = s1 + String.valueOf(chatText.charAt(i));
                name.append(Integer.toHexString(Integer.valueOf(s2)).toLowerCase());
            }
        }
        return name.toString();
    }
    
    public int getMr() {
        int adddmg = 0;
        if (this._power_name != null && this.getItem().getType2() == 2) {
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_1()).m_def;
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_2()).m_def;
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_3()).m_def;
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_4()).m_def;
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_5()).m_def;
        }
        L1ItemPower itemPower = new L1ItemPower(this);
        return itemPower.getMr() + adddmg;
    }
    
    public int getMpr() {
        L1ItemPower itemPower = new L1ItemPower(this);
        return itemPower.getMpr();
    }
    
    public int getSp() {
        L1ItemPower itemPower = new L1ItemPower(this);
        return itemPower.getSp();
    }
    
    public int getDoubleDmgChance() {
        L1ItemPower itemPower = new L1ItemPower(this);
        return itemPower.getDoubleDmgChance();
    }
    
    public int getHitModifierByArmor() {
        L1ItemPower itemPower = new L1ItemPower(this);
        return itemPower.getHitModifierByArmor();
    }
    
    public int get_addhp() {
        L1ItemPower itemPower = new L1ItemPower(this);
        return itemPower.get_addhp();
    }
    
    public int get_addattack() {
        L1ItemPower itemPower = new L1ItemPower(this);
        return itemPower.get_addattack();
    }
    
    public int get_addbowattack() {
        L1ItemPower itemPower = new L1ItemPower(this);
        return itemPower.get_addbowattack();
    }
    
    public int getDamageReduction() {
        L1ItemPower itemPower = new L1ItemPower(this);
        return itemPower.getDamageReduction();
    }
    
    public void greater(L1PcInstance owner, boolean equipment) {
        L1ItemPower itemPower = new L1ItemPower(this);
        itemPower.greater(owner, equipment);
    }
    
    public void GreaterAtEnchant(L1PcInstance owner, int i) {
        L1ItemPower itemPower = new L1ItemPower(this);
        itemPower.GreaterAtEnchant(owner, i);
    }
    
    public int getAcByMagic() {
        return this._acByMagic;
    }
    
    public void setAcByMagic(int i) {
        this._acByMagic = i;
    }
    
    public int getDmgByMagic() {
        int adddmg = 0;
        if (this._power_name != null && this.getItem().getType2() == 1) {
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_1()).add_sp;
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_2()).add_sp;
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_3()).add_sp;
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_4()).add_sp;
            adddmg += PowerItemTable.get().getItem(this._power_name.get_hole_5()).add_sp;
        }
        return this._dmgByMagic + adddmg;
    }
    
    public void setDmgByMagic(int i) {
        this._dmgByMagic = i;
    }
    
    public int getHolyDmgByMagic() {
        return this._holyDmgByMagic;
    }
    
    public void setHolyDmgByMagic(int i) {
        this._holyDmgByMagic = i;
    }
    
    public int getHitByMagic() {
        return this._hitByMagic;
    }
    
    public void setHitByMagic(int i) {
        this._hitByMagic = i;
    }
    
    public int getGamNo() {
        return this._gamNo;
    }
    
    public void setGamNo(int i) {
        this._gamNo = i;
    }
    
    public int getGamNpcId() {
        return this._gamNpcId;
    }
    
    public void setGamNpcId(int i) {
        this._gamNpcId = i;
    }
    
    public String getStarNpcId() {
        return this._starNpcId;
    }
    
    public void setStarNpcId(String i) {
        this._starNpcId = i;
    }
    
    public void setSkillArmorEnchant(L1PcInstance pc, int skillId, int skillTime) {
        int type = this.getItem().getType();
        int type2 = this.getItem().getType2();
        if (this._isRunning) {
            this._timer.cancel();
            int objid = this.getId();
            if (pc != null && pc.getInventory().getItem(objid) != null && type == 2 && type2 == 2 && this.isEquipped()) {
                pc.addAc(3);
                pc.sendPackets(new S_OwnCharStatus(pc));
            }
            this.setAcByMagic(0);
            this._isRunning = false;
            this._timer = null;
        }
        if (type == 2 && type2 == 2 && this.isEquipped()) {
            pc.addAc(-3);
            pc.sendPackets(new S_OwnCharStatus(pc));
        }
        this.setAcByMagic(3);
        this._pc = pc;
        this._char_objid = this._pc.getId();
        this._timer = new L1ItemInstance.EnchantTimer();
        new Timer().schedule(this._timer, (long)skillTime);
        this._isRunning = true;
    }
    
    public void setSkillWeaponEnchant(L1PcInstance pc, int skillId, int skillTime) {
        if (this.getItem().getType2() != 1) {
            return;
        }
        else {
            if (this._isRunning) {
                this._timer.cancel();
                this.setDmgByMagic(0);
                this.setHolyDmgByMagic(0);
                this.setHitByMagic(0);
                this._isRunning = false;
                this._timer = null;
            }
            switch (skillId) {
                case 8: {
                    this.setHolyDmgByMagic(1);
                    this.setHitByMagic(1);
                    break;
                }
                case 12: {
                    this.setDmgByMagic(2);
                    break;
                }
                case 48: {
                    this.setDmgByMagic(2);
                    this.setHitByMagic(2);
                    break;
                }
                case 107: {
                    this.setDmgByMagic(5);
                    break;
                }
            }
            this._pc = pc;
            this._char_objid = this._pc.getId();
            this._timer = new L1ItemInstance.EnchantTimer();
            new Timer().schedule(this._timer, (long)skillTime);
            this._isRunning = true;
            return;
        }
    }
    
    public int getItemOwnerId() {
        return this._itemOwnerId;
    }
    
    public void setItemOwnerId(int i) {
        this._itemOwnerId = i;
    }
    
    public void startEquipmentTimer(L1PcInstance pc) {
        if (this._time != null) {
            return;
        }
        else {
            if (this.getRemainingTime() > 0) {
                this._equipmentTimer = new L1EquipmentTimer(pc, this);
                Timer timer = new Timer(true);
                timer.scheduleAtFixedRate(this._equipmentTimer, 1000L, 1000L);
            }
            return;
        }
    }
    
    public void stopEquipmentTimer(L1PcInstance pc) {
        if (this._time != null) {
            return;
        }
        else {
            if (this.getRemainingTime() > 0) {
                this._equipmentTimer.cancel();
                this._equipmentTimer = null;
            }
            return;
        }
    }
    
    public boolean isNowLighting() {
        return this._isNowLighting;
    }
    
    public void setNowLighting(boolean flag) {
        this._isNowLighting = flag;
    }
    
    public boolean isEquippedTemp() {
        return this._isEquippedTemp;
    }
    
    public void set_isEquippedTemp(boolean isEquippedTemp) {
        this._isEquippedTemp = isEquippedTemp;
    }
    
    public void setIsMatch(boolean isMatch) {
        this._isMatch = isMatch;
    }
    
    public boolean isMatch() {
        return this._isMatch;
    }
    
    public void set_char_objid(int char_objid) {
        this._char_objid = char_objid;
    }
    
    public int get_char_objid() {
        return this._char_objid;
    }
    
    public void set_time(Timestamp time) {
        this._time = time;
    }
    
    public Timestamp get_time() {
        return this._time;
    }
    
    public boolean isRunning() {
        return this._timer != null;
    }
    
    public void set_powerdata(L1ItemPower_name powerdata) {
        this._powerdata = powerdata;
    }
    
    public L1ItemPower_name get_powerdata() {
        return this._powerdata;
    }
    
    public int get_card_use() {
        return this._card_use;
    }
    
    public void set_card_use(int card_use) {
        this._card_use = card_use;
    }
    
    public String getNumberedName_to_String() {
        StringBuilder name = new StringBuilder();
        if (this.getEnchantLevel() >= 0) {
            name.append("+" + this.getEnchantLevel() + " ");
        }
        else if (this.getEnchantLevel() < 0) {
            name.append(String.valueOf(this.getEnchantLevel()) + " ");
        }
        name.append(this._item.getName());
        if (this.getItem().getMaxChargeCount() > 0) {
            name.append(" (" + this.getChargeCount() + ")");
        }
        else {
            switch (this._item.getItemId()) {
                case 20383: {
                    name.append(" (" + this.getChargeCount() + ")");
                    break;
                }
            }
        }
        if (this._power_name != null) {
            for (int i = 0; i < this._power_name.get_hole_count(); ++i) {
                switch (i) {
                    case 0: {
                        name.append(this.set_hole_name(this._power_name.get_hole_1()));
                        break;
                    }
                    case 1: {
                        name.append(this.set_hole_name(this._power_name.get_hole_2()));
                        break;
                    }
                    case 2: {
                        name.append(this.set_hole_name(this._power_name.get_hole_3()));
                        break;
                    }
                    case 3: {
                        name.append(this.set_hole_name(this._power_name.get_hole_4()));
                        break;
                    }
                    case 4: {
                        name.append(this.set_hole_name(this._power_name.get_hole_5()));
                        break;
                    }
                }
            }
        }
        long count = this.getCount();
        if (count > 1L) {
            if (count < 1000000000L) {
                name.append(" (" + count + ")");
            }
            else {
                name.append(" (" + RangeLong.scount(count) + ")");
            }
        }
        return name.toString();
    }
    
    public int getKeyId() {
        return this._keyId;
    }
    
    public void setKeyId(int i) {
        this._keyId = i;
    }
    
    public int getInnNpcId() {
        return this._innNpcId;
    }
    
    public void setInnNpcId(int i) {
        this._innNpcId = i;
    }
    
    public boolean checkRoomOrHall() {
        return this._isHall;
    }
    
    public void setHall(boolean i) {
        this._isHall = i;
    }
    
    public Timestamp getDueTime() {
        return this._dueTime;
    }
    
    public void setDueTime(Timestamp i) {
        this._dueTime = i;
    }
    
    public int getRingID() {
        return this._ringId;
    }
    
    public void setRingID(int ringId) {
        this._ringId = ringId;
    }
    
    public int getEarRingID() {
        return this._earringId;
    }
    
    public void setEarRingID(int earringId) {
        this._earringId = earringId;
    }
    
    public boolean getproctect10() {
        return this.proctect10;
    }
    
    public void setproctect10(boolean i) {
        this.proctect10 = i;
    }
    
    public boolean getproctect() {
        return this.proctect;
    }
    
    public void setproctect(boolean i) {
        this.proctect = i;
    }
    
    public boolean getproctect1() {
        return this.proctect1;
    }
    
    public void setproctect1(boolean i) {
        this.proctect1 = i;
    }
    
    public boolean getproctect2() {
        return this.proctect2;
    }
    
    public void setproctect2(boolean i) {
        this.proctect2 = i;
    }
    
    public boolean getproctect3() {
        return this.proctect3;
    }
    
    public void setproctect3(boolean i) {
        this.proctect3 = i;
    }
    
    public boolean getproctect4() {
        return this.proctect4;
    }
    
    public void setproctect4(boolean i) {
        this.proctect4 = i;
    }
    
    public boolean getproctect5() {
        return this.proctect5;
    }
    
    public void setproctect5(boolean i) {
        this.proctect5 = i;
    }
    
    public boolean getproctect6() {
        return this.proctect6;
    }
    
    public void setproctect6(boolean i) {
        this.proctect6 = i;
    }
    
    public boolean getproctect7() {
        return this.proctect7;
    }
    
    public void setproctect7(boolean i) {
        this.proctect7 = i;
    }
    
    public int getItemStatusX() {
        if (!this.isIdentified()) {
            return 0;
        }
        else {
            int statusX = 1;
            if (ItemRestrictionsTable.RESTRICTIONS.contains((Integer)this.getItemId())) {
                statusX |= 0x10;
            }
            int bless = this.getBless();
            if (bless >= 128 && bless <= 131) {
                statusX |= 0x2;
                statusX |= 0x4;
                statusX |= 0x8;
                statusX |= 0x20;
            }
            else if (bless > 131) {
                statusX |= 0x40;
            }
            if (this.getItem().isStackable()) {
                statusX |= 0x80;
            }
            return statusX;
        }
    }
    
    private StringBuilder attrEnchantLevel() {
        StringBuilder attrEnchant = new StringBuilder();
        int attrEnchantLevel = this.getAttrEnchantLevel();
        L1AttrWeapon attrWeapon = ExtraAttrWeaponTable.getInstance().get(this.getAttrEnchantKind(), attrEnchantLevel);
        if (attrWeapon != null) {
            attrEnchant.append(attrWeapon.getName());
        }
        return attrEnchant;
    }
    
    public void set_power_name(L1ItemPower_name power_name) {
        this._power_name = power_name;
    }
    
    public L1ItemPower_name get_power_name() {
        return this._power_name;
    }
    
    public String getAllName() {
        return this.getNumberedName(this._count);
    }
    
    public final L1MagicWeapon get_magic_weapon() {
        return this._magic_weapon;
    }
    
    public final void set_magic_weapon(L1MagicWeapon value) {
        this._magic_weapon = value;
    }
    
    public String getName2() {
        return this._item.getName();
    }
    
    public void set_ItemAttrName(L1ItemSpecialAttributeChar ItemAttrName) {
        this._ItemAttrName = ItemAttrName;
    }
    
    public L1ItemSpecialAttributeChar get_ItemAttrName() {
        return this._ItemAttrName;
    }
    
    public int getdropitemcheck() {
        return this._dropitemcheck;
    }
    
    public void setdropitemcheck(int dropitemcheck) {
        this._dropitemcheck = dropitemcheck;
    }
    
    public void setdropitemname(String text) {
        this._dropitemname = text;
    }
    
    public String getdropitemname() {
        return this._dropitemname;
    }
    
    public void addskill(int skillid, int time) {
        if (!this._skilllist.containsKey((Integer)skillid)) {
            switch (skillid) {
                case 21: {
                    L1PcInstance pc = World.get().getPlayer(this._char_objid);
                    if (pc == null) {
                        return;
                    }
                    else {
                        if (this.getItem().getType() == 2 && this.getItem().getType2() == 2 && this.isEquipped()) {
                            pc.addAc(-3);
                            pc.sendPackets(new S_OwnCharStatus(pc));
                        }
                        this.setAcByMagic(3);
                        break;
                    }
                }
            }
            this._isRunning = true;
        }
        this._skilllist.put(skillid, time);
    }
    
    public void removeskill(int skillid) {
        if (this._skilllist.remove((Integer)skillid) != 0) {
            L1PcInstance pc = World.get().getPlayer(this._char_objid);
            switch (skillid) {
                case 21: {
                    this.setAcByMagic(0);
                    if (pc == null) {
                        return;
                    }
                    else if (this.getItem().getType() == 2 && this.getItem().getType2() == 2 && this.isEquipped()) {
                        pc.addAc(3);
                        pc.sendPackets(new S_OwnCharStatus(pc));
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            this._isRunning = false;
            pc.sendPackets(new S_ServerMessage(308, this.getLogName()));
        }
    }
    
    /*
    static void access$1(L1ItemInstance l1ItemInstance, boolean isRunning) {
        l1ItemInstance._isRunning = isRunning;
    }
    */
    
    /*
    static void access$2(L1ItemInstance l1ItemInstance, L1ItemInstance.EnchantTimer timer) {
        l1ItemInstance._timer = timer;
    }
    */
    
    /*
    static void access$4(L1ItemInstance l1ItemInstance, int skilltypelv) {
        l1ItemInstance._skilltypelv = skilltypelv;
    }
    */
    
    class EnchantTimer extends TimerTask
    {
        public EnchantTimer() {
        }
        
        @Override
        public void run() {
            try {
                int type = L1ItemInstance.this.getItem().getType();
                int type2 = L1ItemInstance.this.getItem().getType2();
                int objid = L1ItemInstance.this.getId();
                if (L1ItemInstance.this._pc != null && L1ItemInstance.this._pc.getInventory().getItem(objid) != null && type == 2 && type2 == 2 && L1ItemInstance.this.isEquipped()) {
                    L1ItemInstance.this._pc.addAc(3);
                    L1ItemInstance.this._pc.sendPackets(new S_OwnCharStatus(L1ItemInstance.this._pc));
                }
                L1ItemInstance.this.setAcByMagic(0);
                L1ItemInstance.this.setDmgByMagic(0);
                L1ItemInstance.this.setHolyDmgByMagic(0);
                L1ItemInstance.this.setHitByMagic(0);
                L1ItemInstance.this._pc.sendPackets(new S_ServerMessage(308, L1ItemInstance.this.getLogName()));
                L1ItemInstance.this._isRunning = false;
                L1ItemInstance.this._timer = null;
            }
            catch (Exception e) {
                L1ItemInstance._log.warn("EnchantTimer: " + L1ItemInstance.this.getItemId());
            }
        }
    }
    
    public class LastStatus
    {
        public long count;
        public int itemId;
        public boolean isEquipped;
        public int enchantLevel;
        public boolean isIdentified;
        public int durability;
        public int chargeCount;
        public int remainingTime;
        public Timestamp lastUsed;
        public int bless;
        public int attrEnchantKind;
        public int attrEnchantLevel;
        public int ItemAttack;
        public int ItemBowAttack;
        public int ItemHit;
        public int ItemBowHit;
        public int ItemReductionDmg;
        public int ItemSp;
        public int Itemprobability;
        public int ItemStr;
        public int ItemDex;
        public int ItemInt;
        public int ItemCon;
        public int ItemCha;
        public int ItemWis;
        public int ItemHp;
        public int ItemMp;
        public int ItemMr;
        public int ItemAc;
        public int ItemMag_Red;
        public int ItemMag_Hit;
        public int ItemDg;
        public int ItemistSustain;
        public int ItemistStun;
        public int ItemistStone;
        public int ItemistSleep;
        public int ItemistFreeze;
        public int ItemistBlind;
        public int ItemArmorType;
        public int ItemArmorLv;
        public int skilltype;
        public int skilltypelv;
        public int Itemtype1;
        public int Itemtype2;
        public int Itemtype3;
        public int Itemtype4;
        public int ItemType;
        public int ItemHpr;
        public int ItemMpr;
        public int Itemhppotion;
        public int gamNo;
        public int gamNpcId;
        public String starNpcId;
        
        public LastStatus() {
            this.isEquipped = false;
            this.isIdentified = true;
            this.lastUsed = null;
        }
        
        public void updateAll() {
            this.count = L1ItemInstance.this.getCount();
            this.itemId = L1ItemInstance.this.getItemId();
            this.isEquipped = L1ItemInstance.this.isEquipped();
            this.isIdentified = L1ItemInstance.this.isIdentified();
            this.enchantLevel = L1ItemInstance.this.getEnchantLevel();
            this.durability = L1ItemInstance.this.get_durability();
            this.chargeCount = L1ItemInstance.this.getChargeCount();
            this.remainingTime = L1ItemInstance.this.getRemainingTime();
            this.lastUsed = L1ItemInstance.this.getLastUsed();
            this.bless = L1ItemInstance.this.getBless();
            this.attrEnchantKind = L1ItemInstance.this.getAttrEnchantKind();
            this.attrEnchantLevel = L1ItemInstance.this.getAttrEnchantLevel();
            this.ItemAttack = L1ItemInstance.this.getItemAttack();
            this.ItemBowAttack = L1ItemInstance.this.getItemBowAttack();
            this.ItemHit = L1ItemInstance.this.getItemHit();
            this.ItemBowHit = L1ItemInstance.this.getItemBowHit();
            this.ItemReductionDmg = L1ItemInstance.this.getItemReductionDmg();
            this.ItemSp = L1ItemInstance.this.getItemSp();
            this.Itemprobability = L1ItemInstance.this.getItemprobability();
            this.ItemStr = L1ItemInstance.this.getItemStr();
            this.ItemDex = L1ItemInstance.this.getItemDex();
            this.ItemInt = L1ItemInstance.this.getItemInt();
            this.ItemCon = L1ItemInstance.this.getItemCon();
            this.ItemCha = L1ItemInstance.this.getItemCha();
            this.ItemWis = L1ItemInstance.this.getItemWis();
            this.ItemHp = L1ItemInstance.this.getItemHp();
            this.ItemMp = L1ItemInstance.this.getItemMp();
            this.skilltype = L1ItemInstance.this.getskilltype();
            this.skilltypelv = L1ItemInstance.this.getskilltypelv();
            this.ItemMr = L1ItemInstance.this.getItemMr();
            this.ItemAc = L1ItemInstance.this.getItemAc();
            this.ItemMag_Red = L1ItemInstance.this.getItemMag_Red();
            this.ItemMag_Hit = L1ItemInstance.this.getItemMag_Hit();
            this.ItemDg = L1ItemInstance.this.getItemDg();
            this.ItemistSustain = L1ItemInstance.this.getItemistSustain();
            this.ItemistStun = L1ItemInstance.this.getItemistStun();
            this.ItemistStone = L1ItemInstance.this.getItemistStone();
            this.ItemistSleep = L1ItemInstance.this.getItemistSleep();
            this.ItemistFreeze = L1ItemInstance.this.getItemistFreeze();
            this.ItemistBlind = L1ItemInstance.this.getItemistBlind();
            this.ItemArmorType = L1ItemInstance.this.getItemArmorType();
            this.ItemArmorLv = L1ItemInstance.this.getItemArmorLv();
            this.ItemHpr = L1ItemInstance.this.getItemHpr();
            this.ItemMpr = L1ItemInstance.this.getItemMpr();
            this.Itemhppotion = L1ItemInstance.this.getItemhppotion();
            this.gamNo = L1ItemInstance.this.getGamNo();
            this.gamNpcId = L1ItemInstance.this.getGamNpcId();
            this.starNpcId = L1ItemInstance.this.getStarNpcId();
        }
        
        public void updateGamNo() {
            this.gamNo = L1ItemInstance.this.getGamNo();
        }
        
        public void updateGamNpcId() {
            this.gamNpcId = L1ItemInstance.this.getGamNpcId();
        }
        
        public void updateStarNpcId() {
            this.starNpcId = L1ItemInstance.this.getStarNpcId();
        }
        
        public void updateCount() {
            this.count = L1ItemInstance.this.getCount();
        }
        
        public void updateItemId() {
            this.itemId = L1ItemInstance.this.getItemId();
        }
        
        public void updateEquipped() {
            this.isEquipped = L1ItemInstance.this.isEquipped();
        }
        
        public void updateIdentified() {
            this.isIdentified = L1ItemInstance.this.isIdentified();
        }
        
        public void updateEnchantLevel() {
            this.enchantLevel = L1ItemInstance.this.getEnchantLevel();
        }
        
        public void updateDuraility() {
            this.durability = L1ItemInstance.this.get_durability();
        }
        
        public void updateChargeCount() {
            this.chargeCount = L1ItemInstance.this.getChargeCount();
        }
        
        public void updateRemainingTime() {
            this.remainingTime = L1ItemInstance.this.getRemainingTime();
        }
        
        public void updateLastUsed() {
            this.lastUsed = L1ItemInstance.this.getLastUsed();
        }
        
        public void updateBless() {
            this.bless = L1ItemInstance.this.getBless();
        }
        
        public void updateAttrEnchantKind() {
            this.attrEnchantKind = L1ItemInstance.this.getAttrEnchantKind();
        }
        
        public void updateAttrEnchantLevel() {
            this.attrEnchantLevel = L1ItemInstance.this.getAttrEnchantLevel();
        }
        
        public void updateItemAttack() {
            this.ItemAttack = L1ItemInstance.this.getItemAttack();
        }
        
        public void updateItemBowAttack() {
            this.ItemBowAttack = L1ItemInstance.this.getItemBowAttack();
        }
        
        public void updateItemHit() {
            this.ItemHit = L1ItemInstance.this.getItemHit();
        }
        
        public void updateItemBowHit() {
            this.ItemBowHit = L1ItemInstance.this.getItemBowHit();
        }
        
        public void updateItemReductionDmg() {
            this.ItemReductionDmg = L1ItemInstance.this.getItemReductionDmg();
        }
        
        public void updateItemSp() {
            this.ItemSp = L1ItemInstance.this.getItemSp();
        }
        
        public void updateItemprobability() {
            this.Itemprobability = L1ItemInstance.this.getItemprobability();
        }
        
        public void updateItemStr() {
            this.ItemStr = L1ItemInstance.this.getItemStr();
        }
        
        public void updateItemDex() {
            this.ItemDex = L1ItemInstance.this.getItemDex();
        }
        
        public void updateItemInt() {
            this.ItemInt = L1ItemInstance.this.getItemInt();
        }
        
        public void updateItemCon() {
            this.ItemCon = L1ItemInstance.this.getItemCon();
        }
        
        public void updateItemCha() {
            this.ItemCha = L1ItemInstance.this.getItemCha();
        }
        
        public void updateItemWis() {
            this.ItemWis = L1ItemInstance.this.getItemWis();
        }
        
        public void updateItemHp() {
            this.ItemHp = L1ItemInstance.this.getItemHp();
        }
        
        public void updateItemMp() {
            this.ItemMp = L1ItemInstance.this.getItemMp();
        }
        
        public void updateskilltype() {
            this.skilltype = L1ItemInstance.this.getskilltype();
        }
        
        public void updateskilltypelv() {
            L1ItemInstance.this._skilltypelv = L1ItemInstance.this.getskilltypelv();
        }
        
        public void updateItemHpr() {
            this.ItemHpr = L1ItemInstance.this.getItemHpr();
        }
        
        public void updateItemMpr() {
            this.ItemMpr = L1ItemInstance.this.getItemMpr();
        }
        
        public void updateItemhppotion() {
            this.Itemhppotion = L1ItemInstance.this.getItemhppotion();
        }
        
        public void updateItemMr() {
            this.ItemMr = L1ItemInstance.this.getItemMr();
        }
        
        public void updateItemAc() {
            this.ItemAc = L1ItemInstance.this.getItemAc();
        }
        
        public void updateItemMag_Red() {
            this.ItemMag_Red = L1ItemInstance.this.getItemMag_Red();
        }
        
        public void updateItemMag_Hit() {
            this.ItemMag_Hit = L1ItemInstance.this.getItemMag_Hit();
        }
        
        public void updateItemDg() {
            this.ItemDg = L1ItemInstance.this.getItemDg();
        }
        
        public void updateItemistSustain() {
            this.ItemistSustain = L1ItemInstance.this.getItemistSustain();
        }
        
        public void updateItemistStun() {
            this.ItemistStun = L1ItemInstance.this.getItemistStun();
        }
        
        public void updateItemistStone() {
            this.ItemistStone = L1ItemInstance.this.getItemistStone();
        }
        
        public void updateItemistSleep() {
            this.ItemistSleep = L1ItemInstance.this.getItemistSleep();
        }
        
        public void updateItemistFreeze() {
            this.ItemistFreeze = L1ItemInstance.this.getItemistFreeze();
        }
        
        public void updateItemistBlind() {
            this.ItemistBlind = L1ItemInstance.this.getItemistBlind();
        }
        
        public void updateItemArmorType() {
            this.ItemArmorType = L1ItemInstance.this.getItemArmorType();
        }
        
        public void updateItemArmorLv() {
            this.ItemArmorLv = L1ItemInstance.this.getItemArmorLv();
        }
        
        public void updateItemType() {
            this.ItemType = L1ItemInstance.this.getItemType();
        }
    }
}

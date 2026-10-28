package com.lineage.server.model.Instance;

import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.L1WeaponSkill;
import com.lineage.server.templates.L1MagicWeapon;
import com.lineage.server.datatables.WeaponSkillTable;
import com.lineage.william.L1WilliamEnchantAccessory;
import com.lineage.william.EnchantAccessory;
import com.lineage.william.L1WilliamEnchantOrginal;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.william.EnchantOrginal;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.model.doll.L1DollExecutor;
import com.lineage.server.datatables.DollPowerTable;
import java.text.SimpleDateFormat;
import com.lineage.server.templates.L1ItemVIP;
import java.util.Iterator;
import com.lineage.william.WilliamItemMessage;
import java.util.ArrayList;
import com.lineage.server.templates.L1CheckItemPower;
import com.lineage.server.templates.L1PetItem;
import com.lineage.server.datatables.CheckItemPowerTable;
import com.lineage.server.datatables.ItemVIPTable;
import com.lineage.server.datatables.PetItemTable;
import com.lineage.server.datatables.PowerItemTable;
import com.lineage.server.utils.BinaryOutputStream;
import com.lineage.server.templates.L1Item;

public class L1ItemStatus
{
    private final L1ItemInstance _itemInstance;
    private final L1Item _item;
    private final BinaryOutputStream _os;
    private final L1ItemPower _itemPower;
    private final PowerItemTable _l1power;
    private boolean _statusx;
    
    public L1ItemStatus(L1ItemInstance itemInstance) {
        this._itemInstance = itemInstance;
        this._item = itemInstance.getItem();
        this._os = new BinaryOutputStream();
        this._itemPower = new L1ItemPower(this._itemInstance);
        this._l1power = PowerItemTable.get();
    }
    
    public L1ItemStatus(L1Item template) {
        (this._itemInstance = new L1ItemInstance()).setItem(template);
        this._item = template;
        this._os = new BinaryOutputStream();
        this._itemPower = new L1ItemPower(this._itemInstance);
        this._l1power = PowerItemTable.get();
    }
    
    public BinaryOutputStream getStatusBytes(boolean statusx) {
        this._statusx = statusx;
        int use_type = this._item.getUseType();
        switch (use_type) {
            case -12: {
                L1PetItem petItem = PetItemTable.get().getTemplate(this._item.getItemId());
                if (petItem.isWeapom()) {
                    return this.petweapon(petItem);
                }
                else {
                    return this.petarmor(petItem);
                }
            }
            case -11:
            case -10:
            case -9:
            case -8:
            case -7:
            case -6:
            case -5:
            case -4:
            case -1:
            case 0:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 39:
            case 42:
            case 46:
            case 55: {
                String classname = this._item.getclassname();
                if (classname.equalsIgnoreCase("doll.Magic_Doll") || classname.equalsIgnoreCase("doll.Magic_Doll_Power")) {
                    return this.etcitem_doll();
                }
                else if (ItemVIPTable.get().checkVIP(this._item.getItemId())) {
                    return this.etcitem_card(ItemVIPTable.get().getVIP(this._item.getItemId()));
                }
                else if (this._item.getItemId() == 56147 || this._item.getItemId() == 56148) {
                    return this.effective_item();
                }
                else if (CheckItemPowerTable.get().checkItem(this._item.getItemId())) {
                    return this.etcitem_checkitempower(CheckItemPowerTable.get().getItem(this._item.getItemId()));
                }
                else {
                    return this.etcitem();
                }
            }
            case -3:
            case -2: {
                return this.arrow();
            }
            case 1: {
                return this.weapon();
            }
            case 2:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            case 47: {
                return this.armor();
            }
            case 10: {
                return this.lightitem();
            }
            case 23:
            case 24:
            case 37:
            case 40: {
                return this.accessories();
            }
            case 38: {
                return this.fooditem();
            }
            case 43:
            case 44:
            case 45:
            case 48:
            case 49:
            case 51: {
                return this.accessories2();
            }
            default: {
                return null;
            }
        }
    }
    
    private BinaryOutputStream etcitem_checkitempower(L1CheckItemPower power) {
        if (power.get_ac() != 0) {
            this._os.writeC(39);
            this._os.writeS("防禦: +" + power.get_ac());
        }
        if (power.get_str() != 0) {
            this._os.writeC(8);
            this._os.writeC(power.get_str());
        }
        if (power.get_dex() != 0) {
            this._os.writeC(9);
            this._os.writeC(power.get_dex());
        }
        if (power.get_con() != 0) {
            this._os.writeC(10);
            this._os.writeC(power.get_con());
        }
        if (power.get_wis() != 0) {
            this._os.writeC(11);
            this._os.writeC(power.get_wis());
        }
        if (power.get_intel() != 0) {
            this._os.writeC(12);
            this._os.writeC(power.get_intel());
        }
        if (power.get_cha() != 0) {
            this._os.writeC(13);
            this._os.writeC(power.get_cha());
        }
        if (power.get_hp() != 0) {
            this._os.writeC(14);
            this._os.writeH(power.get_hp());
        }
        if (power.get_mp() != 0) {
            this._os.writeC(32);
            this._os.writeC(power.get_mp());
        }
        if (power.get_mr() != 0) {
            this._os.writeC(15);
            this._os.writeH(power.get_mr());
        }
        if (power.get_sp() != 0) {
            this._os.writeC(17);
            this._os.writeC(power.get_sp());
        }
        if (power.get_dmg() != 0) {
            this._os.writeC(6);
            this._os.writeC(power.get_dmg());
        }
        if (power.get_bow_dmg() != 0) {
            this._os.writeC(35);
            this._os.writeC(power.get_bow_dmg());
        }
        if (power.get_hit() != 0) {
            this._os.writeC(5);
            this._os.writeC(power.get_hit());
        }
        if (power.get_bow_hit() != 0) {
            this._os.writeC(24);
            this._os.writeC(power.get_bow_hit());
        }
        if (power.get_dmg_r() != 0) {
            this._os.writeC(39);
            this._os.writeS("物理減傷 +" + power.get_dmg_r());
        }
        if (power.get_magic_r() != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法減傷 +" + power.get_magic_r());
        }
        if (power.get_fire() != 0) {
            this._os.writeC(27);
            this._os.writeC(power.get_fire());
        }
        if (power.get_water() != 0) {
            this._os.writeC(28);
            this._os.writeC(power.get_water());
        }
        if (power.get_wind() != 0) {
            this._os.writeC(29);
            this._os.writeC(power.get_wind());
        }
        if (power.get_earth() != 0) {
            this._os.writeC(30);
            this._os.writeC(power.get_earth());
        }
        if (power.get_freeze() != 0) {
            this._os.writeC(33);
            this._os.writeC(1);
            this._os.writeC(power.get_freeze());
        }
        if (power.get_stone() != 0) {
            this._os.writeC(33);
            this._os.writeC(2);
            this._os.writeC(power.get_stone());
        }
        if (power.get_sleep() != 0) {
            this._os.writeC(33);
            this._os.writeC(3);
            this._os.writeC(power.get_sleep());
        }
        if (power.get_blind() != 0) {
            this._os.writeC(33);
            this._os.writeC(4);
            this._os.writeC(power.get_blind());
        }
        if (power.get_stun() != 0) {
            this._os.writeC(33);
            this._os.writeC(5);
            this._os.writeC(power.get_stun());
        }
        if (power.get_sustain() != 0) {
            this._os.writeC(33);
            this._os.writeC(6);
            this._os.writeC(power.get_sustain());
        }
        if (power.get_hpr() != 0) {
            this._os.writeC(37);
            this._os.writeC(power.get_hpr());
        }
        if (power.get_mpr() != 0) {
            this._os.writeC(38);
            this._os.writeC(power.get_mpr());
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                if (s != null && !s.isEmpty()) {
                    this._os.writeC(39);
                    this._os.writeS(s);
                }
            }
        }
        finally {
            as.clear();
        }
        this._os.writeC(23);
        this._os.writeC(this._item.getMaterial());
        this._os.writeD(this._itemInstance.getWeight());
        return this._os;
    }
    
    private BinaryOutputStream etcitem_card(L1ItemVIP vip) {
        if (this._itemInstance.get_time() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this._os.writeC(39);
            this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
        }
        int add_str = vip.get_add_str();
        if (add_str != 0) {
            this._os.writeC(8);
            this._os.writeC(add_str);
        }
        int add_dex = vip.get_add_dex();
        if (add_dex != 0) {
            this._os.writeC(9);
            this._os.writeC(add_dex);
        }
        int add_con = vip.get_add_con();
        if (add_con != 0) {
            this._os.writeC(10);
            this._os.writeC(add_con);
        }
        int add_wis = vip.get_add_wis();
        if (add_wis != 0) {
            this._os.writeC(11);
            this._os.writeC(add_wis);
        }
        int add_int = vip.get_add_int();
        if (add_int != 0) {
            this._os.writeC(12);
            this._os.writeC(add_int);
        }
        int add_cha = vip.get_add_cha();
        if (add_cha != 0) {
            this._os.writeC(13);
            this._os.writeC(add_cha);
        }
        int add_dmg = vip.get_add_dmg();
        if (add_dmg != 0) {
            this._os.writeC(6);
            this._os.writeC(add_dmg);
        }
        int add_hit = vip.get_add_hit();
        if (add_hit != 0) {
            this._os.writeC(5);
            this._os.writeC(add_hit);
        }
        int add_bow_dmg = vip.get_add_bow_dmg();
        if (add_bow_dmg != 0) {
            this._os.writeC(35);
            this._os.writeC(add_bow_dmg);
        }
        int add_bow_hit = vip.get_add_bow_hit();
        if (add_bow_hit != 0) {
            this._os.writeC(24);
            this._os.writeC(add_bow_hit);
        }
        int add_mr = vip.get_add_mr();
        if (add_mr != 0) {
            this._os.writeC(15);
            this._os.writeH(add_mr);
        }
        int add_sp = vip.get_add_sp();
        if (add_sp != 0) {
            this._os.writeC(17);
            this._os.writeC(add_sp);
        }
        int add_fire = vip.get_add_fire();
        if (add_fire != 0) {
            this._os.writeC(27);
            this._os.writeC(add_fire);
        }
        int add_wind = vip.get_add_wind();
        if (add_wind != 0) {
            this._os.writeC(29);
            this._os.writeC(add_wind);
        }
        int add_earth = vip.get_add_earth();
        if (add_earth != 0) {
            this._os.writeC(30);
            this._os.writeC(add_earth);
        }
        int add_water = vip.get_add_water();
        if (add_water != 0) {
            this._os.writeC(28);
            this._os.writeC(add_water);
        }
        int add_ac = vip.get_add_ac();
        if (add_ac != 0) {
            this._os.writeC(39);
            this._os.writeS("防禦: +" + add_ac);
        }
        int add_hp = vip.get_add_hp();
        if (add_hp != 0) {
            this._os.writeC(39);
            this._os.writeS("血量: +" + add_hp);
        }
        int add_mp = vip.get_add_mp();
        if (add_mp != 0) {
            this._os.writeC(39);
            this._os.writeS("魔量: +" + add_mp);
        }
        int add_hpr = vip.get_add_hpr();
        if (add_hpr != 0) {
            this._os.writeC(39);
            this._os.writeS("回血: +" + add_hpr);
        }
        int add_mpr = vip.get_add_mpr();
        if (add_mpr != 0) {
            this._os.writeC(39);
            this._os.writeS("回魔: +" + add_mpr);
        }
        int add_freeze = vip.get_add_freeze();
        if (add_freeze != 0) {
            this._os.writeC(39);
            this._os.writeS("寒冰耐性: +" + add_freeze);
        }
        int add_stone = vip.get_add_stone();
        if (add_stone != 0) {
            this._os.writeC(39);
            this._os.writeS("昏迷耐性: +" + add_stone);
        }
        int add_sleep = vip.get_add_sleep();
        if (add_sleep != 0) {
            this._os.writeC(39);
            this._os.writeS("睡眠耐性: +" + add_sleep);
        }
        int add_blind = vip.get_add_blind();
        if (add_blind != 0) {
            this._os.writeC(39);
            this._os.writeS("暗黑耐性: +" + add_blind);
        }
        int add_stun = vip.get_add_stun();
        if (add_stun != 0) {
            this._os.writeC(39);
            this._os.writeS("暈眩耐性: +" + add_stun);
        }
        int add_sustain = vip.get_add_sustain();
        if (add_sustain != 0) {
            this._os.writeC(39);
            this._os.writeS("支撐耐性: +" + add_sustain);
        }
        int wmd = vip.get_add_wmd();
        if (wmd != 0) {
            this._os.writeC(39);
            this._os.writeS("魔武傷害增加: +" + wmd);
        }
        int wmc = vip.get_add_wmc();
        if (wmc != 0) {
            this._os.writeC(39);
            this._os.writeS("魔武發動增加: +" + wmc);
        }
        int dmgr = vip.get_add_dmg_r();
        if (dmgr != 0) {
            this._os.writeC(39);
            this._os.writeS("物理減傷: +" + dmgr);
        }
        int mdmgr = vip.get_add_magic_r();
        if (mdmgr != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法減傷: +" + mdmgr);
        }
        int exp = vip.get_add_exp();
        if (exp != 0) {
            this._os.writeC(39);
            this._os.writeS("經驗值增加:" + exp + "%。");
        }
        int gf = vip.get_add_adena();
        if (gf != 0) {
            this._os.writeC(39);
            this._os.writeS("金幣倍率增加:" + gf + "%。");
        }
        boolean item = vip.get_death_item();
        if (item) {
            this._os.writeC(39);
            this._os.writeS("死亡不會噴道具。");
        }
        boolean exp2 = vip.get_death_exp();
        if (exp2) {
            this._os.writeC(39);
            this._os.writeS("死亡不會噴經驗值。");
        }
        boolean skill = vip.get_death_skill();
        if (skill) {
            this._os.writeC(39);
            this._os.writeS("死亡不會噴技能。");
        }
        boolean score = vip.get_death_score();
        if (score) {
            this._os.writeC(39);
            this._os.writeS("死亡不會掉積分。");
        }
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream effective_item() {
        if (this._item.getItemId() == 56147) {
            this._os.writeC(14);
            this._os.writeH(50);
            this._os.writeC(32);
            this._os.writeC(30);
            this._os.writeC(6);
            this._os.writeC(3);
            this._os.writeC(35);
            this._os.writeC(3);
            this._os.writeC(17);
            this._os.writeC(3);
            this._os.writeC(39);
            this._os.writeS("傷害減免+3");
            this._os.writeC(37);
            this._os.writeC(3);
            this._os.writeC(38);
            this._os.writeC(3);
        }
        else if (this._item.getItemId() == 56148) {
            this._os.writeC(14);
            this._os.writeH(30);
            this._os.writeC(32);
            this._os.writeC(30);
            this._os.writeC(6);
            this._os.writeC(2);
            this._os.writeC(35);
            this._os.writeC(2);
            this._os.writeC(17);
            this._os.writeC(2);
            this._os.writeC(39);
            this._os.writeS("傷害減免+2");
            this._os.writeC(37);
            this._os.writeC(2);
            this._os.writeC(38);
            this._os.writeC(2);
        }
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        return this._os;
    }
    
    private BinaryOutputStream etcitem_doll() {
        L1Doll doll = DollPowerTable.get().get_type(this._item.getItemId());
        String msg = null;
        if (!doll.getPowerList().isEmpty()) {
            for (L1DollExecutor power : doll.getPowerList()) {
                if (power.get_note() != null) {
                    msg = power.get_note();
                    this._os.writeC(39);
                    this._os.writeS(msg);
                }
            }
        }
        if (this._itemInstance.get_time() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this._os.writeC(39);
            this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
        }
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        return this._os;
    }
    
    private BinaryOutputStream arrow() {
        this._os.writeC(1);
        this._os.writeC(this._item.getDmgSmall());
        this._os.writeC(this._item.getDmgLarge());
        this._os.writeC(this._item.getMaterial());
        this._os.writeD(this._itemInstance.getWeight());
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream fooditem() {
        this._os.writeC(21);
        this._os.writeH(this._item.getFoodVolume());
        this._os.writeC(this._item.getMaterial());
        this._os.writeD(this._itemInstance.getWeight());
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream lightitem() {
        this._os.writeC(22);
        this._os.writeH(this._item.getLightRange());
        this._os.writeC(this._item.getMaterial());
        this._os.writeD(this._itemInstance.getWeight());
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream armor() {
        int attr_str = 0;
        int attr_con = 0;
        int attr_dex = 0;
        int attr_int = 0;
        int attr_wis = 0;
        int attr_cha = 0;
        int attr_hp = 0;
        int attr_mp = 0;
        int attr_sp = 0;
        int attr_mr = 0;
        int drain_min_hp = 0;
        int drain_max_hp = 0;
        int drain_hp_rand = 0;
        int attr_hpr = 0;
        int attr_mpr = 0;
        int drain_min_mp = 0;
        int drain_max_mp = 0;
        int drain_mp_rand = 0;
        int skill_gfxid = 0;
        int skill_rand = 0;
        int skill_dmg = 0;
        int attr_魔法格檔 = 0;
        int attr_物理格檔 = 0;
        int PVP增傷 = 0;
        int PVP減傷 = 0;
        int attr_potion_heal = 0;
        int attr_dmgR = 0;
        L1ItemSpecialAttributeChar attr_char = this._itemInstance.get_ItemAttrName();
        if (attr_char != null) {
            L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
            attr_str = attr.get_add_str();
            attr_con = attr.get_add_con();
            attr_dex = attr.get_add_dex();
            attr_int = attr.get_add_int();
            attr_wis = attr.get_add_wis();
            attr_cha = attr.get_add_cha();
            attr_hp = attr.get_add_hp();
            attr_mp = attr.get_add_mp();
            attr_sp = attr.get_add_sp();
            attr_mr = attr.get_add_m_def();
            drain_min_hp = attr.get_add_drain_min_hp();
            drain_max_hp = attr.get_add_drain_max_hp();
            drain_hp_rand = attr.get_drain_hp_rand();
            drain_min_mp = attr.get_add_drain_min_mp();
            drain_max_mp = attr.get_add_drain_max_mp();
            drain_mp_rand = attr.get_drain_mp_rand();
            attr_hpr = attr.get_add_hpr();
            attr_mpr = attr.get_add_mpr();
            skill_gfxid = attr.get_add_skill_gfxid();
            skill_rand = attr.get_add_skill_rand();
            skill_dmg = attr.get_add_skill_dmg();
            attr_魔法格檔 = attr.get魔法格檔();
            attr_物理格檔 = attr.get物理格檔();
            PVP增傷 = attr.get_add_pvp_dmg();
            PVP減傷 = attr.get_add_pvp_redmg();
            attr_potion_heal = attr.get_add_potion_heal();
            attr_dmgR = attr.get_add_dmgR();
        }
        L1ItemPower_name power = this._itemInstance.get_power_name();
        String name2 = "";
        if (this._itemInstance.getskilltype() != 0) {
            int Skill_Level = this._itemInstance.getskilltypelv();
            int Skill_Type = this._itemInstance.getskilltype();
            switch (Skill_Type) {
                case 113: {
                    if (Skill_Level != 1) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv1減傷2%";
                        break;
                    }
                }
                case 114: {
                    if (Skill_Level != 2) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv2減傷5%";
                        break;
                    }
                }
                case 115: {
                    if (Skill_Level != 3) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv3減傷10%";
                        break;
                    }
                }
                case 116: {
                    if (Skill_Level != 4) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv4減傷15%";
                        break;
                    }
                }
                case 117: {
                    if (Skill_Level != 5) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv5減傷20%";
                        break;
                    }
                }
                case 118: {
                    if (Skill_Level != 1) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv1增傷+5";
                        break;
                    }
                }
                case 119: {
                    if (Skill_Level != 2) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv2增傷+10";
                        break;
                    }
                }
                case 120: {
                    if (Skill_Level != 3) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv3增傷+15";
                        break;
                    }
                }
                case 121: {
                    if (Skill_Level != 4) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv4增傷+20";
                        break;
                    }
                }
                case 122: {
                    if (Skill_Level != 5) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv5增傷+30";
                        break;
                    }
                }
                case 123: {
                    if (Skill_Level != 1) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv1爆擊1%";
                        break;
                    }
                }
                case 124: {
                    if (Skill_Level != 2) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv2爆擊3%";
                        break;
                    }
                }
                case 125: {
                    if (Skill_Level != 3) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv3爆擊7%";
                        break;
                    }
                }
                case 126: {
                    if (Skill_Level != 4) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv4爆擊12%";
                        break;
                    }
                }
                case 127: {
                    if (Skill_Level != 5) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv5爆擊20%";
                        break;
                    }
                }
                case 128: {
                    if (Skill_Level != 1) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv1閃避1%";
                        break;
                    }
                }
                case 129: {
                    if (Skill_Level != 2) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv2閃避2%";
                        break;
                    }
                }
                case 130: {
                    if (Skill_Level != 3) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv3閃避4%";
                        break;
                    }
                }
                case 131: {
                    if (Skill_Level != 4) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv4閃避7%";
                        break;
                    }
                }
                case 132: {
                    if (Skill_Level != 5) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv5閃避10%";
                        break;
                    }
                }
                case 133: {
                    if (Skill_Level != 1) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv1魔減2%";
                        break;
                    }
                }
                case 134: {
                    if (Skill_Level != 2) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv2魔減5%";
                        break;
                    }
                }
                case 135: {
                    if (Skill_Level != 3) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv3魔減10%";
                        break;
                    }
                }
                case 136: {
                    if (Skill_Level != 4) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv4魔減15%";
                        break;
                    }
                }
                case 137: {
                    if (Skill_Level != 5) {
                        break;
                    }
                    else {
                        name2 = "裝備天賦:Lv5魔減20%";
                        break;
                    }
                }
            }
            this._os.writeC(39);
            this._os.writeS(name2);
        }
        this._os.writeC(19);
        int power_ac = 0;
        if (power != null) {
            power_ac += this._l1power.getItem(power.get_hole_1()).add_ac;
            power_ac += this._l1power.getItem(power.get_hole_2()).add_ac;
            power_ac += this._l1power.getItem(power.get_hole_3()).add_ac;
            power_ac += this._l1power.getItem(power.get_hole_4()).add_ac;
            power_ac += this._l1power.getItem(power.get_hole_5()).add_ac;
        }
        int ac = this._item.get_ac() - this._itemInstance.getItemAc();
        if (ac < 0) {
            ac = Math.abs(ac);
        }
        this._os.writeC(ac);
        this._os.writeC(this._item.getMaterial());
        this._os.writeC(this._item.get_greater());
        this._os.writeD(this._itemInstance.getWeight());
        if (this._itemInstance.getEnchantLevel() != 0 || power_ac != 0) {
            this._os.writeC(2);
            this._os.writeC(this._itemInstance.getEnchantLevel() + power_ac);
        }
        if (this._itemInstance.get_time() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this._os.writeC(39);
            this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
        }
        if (this._itemInstance.get_durability() != 0) {
            this._os.writeC(3);
            this._os.writeC(this._itemInstance.get_durability());
        }
        int s6_1 = 0;
        int s6_2 = 0;
        int s6_3 = 0;
        int s6_4 = 0;
        int s6_5 = 0;
        int s6_6 = 0;
        int aH_1 = 0;
        int aM_1 = 0;
        int aMR_1 = 0;
        int aSP_1 = 0;
        int aSS_1 = 0;
        int d4_1 = 0;
        int d4_2 = 0;
        int d4_3 = 0;
        int d4_4 = 0;
        int k6_1 = 0;
        int k6_2 = 0;
        int k6_3 = 0;
        int k6_4 = 0;
        int k6_5 = 0;
        int k6_6 = 0;
        int aHpr = 0;
        int aMpr = 0;
        int admg = 0;
        int drd = 0;
        int mdmg = 0;
        int mdrd = 0;
        int bdmg = 0;
        int hit = 0;
        int mcri = 0;
        int addDmgModifier = 0;
        int addHitModifier = 0;
        int addBowDmgModifier = 0;
        int addBowHitModifier = 0;
        int weapon_pro = this._item.getweaponskillpro();
        int weapon_dmg = this._item.getweaponskilldmg();
        int pw_sHpr = this._item.get_addhpr() + this._itemInstance.getItemHpr() + attr_hpr;
        int pw_sMpr = this._itemPower.getMpr() + this._itemInstance.getItemMpr() + attr_mpr;
        if (this._itemInstance.isMatch()) {
            s6_1 = this._item.get_mode()[0];
            s6_2 = this._item.get_mode()[1];
            s6_3 = this._item.get_mode()[2];
            s6_4 = this._item.get_mode()[3];
            s6_5 = this._item.get_mode()[4];
            s6_6 = this._item.get_mode()[5];
            aH_1 = this._item.get_mode()[6];
            aM_1 = this._item.get_mode()[7];
            aMR_1 = this._item.get_mode()[8];
            aSP_1 = this._item.get_mode()[9];
            aSS_1 = this._item.get_mode()[10];
            d4_1 = this._item.get_mode()[11];
            d4_2 = this._item.get_mode()[12];
            d4_3 = this._item.get_mode()[13];
            d4_4 = this._item.get_mode()[14];
            k6_1 = this._item.get_mode()[15];
            k6_2 = this._item.get_mode()[16];
            k6_3 = this._item.get_mode()[17];
            k6_4 = this._item.get_mode()[18];
            k6_5 = this._item.get_mode()[19];
            k6_6 = this._item.get_mode()[20];
            aHpr = this._item.get_mode()[21];
            aMpr = this._item.get_mode()[22];
            admg = this._item.get_mode()[23];
            drd = this._item.get_mode()[24];
            mdmg = this._item.get_mode()[25];
            mdrd = this._item.get_mode()[26];
            bdmg = this._item.get_mode()[27];
            hit = this._item.get_mode()[28];
            mcri = this._item.get_mode()[30];
            weapon_pro = this._item.get_mode()[31];
            weapon_dmg = this._item.get_mode()[32];
        }
        if (power != null) {
            aHpr += this._l1power.getItem(power.get_hole_1()).add_hpr;
            aMpr += this._l1power.getItem(power.get_hole_1()).add_mpr;
            s6_1 += this._l1power.getItem(power.get_hole_1()).add_str;
            s6_2 += this._l1power.getItem(power.get_hole_1()).add_dex;
            s6_3 += this._l1power.getItem(power.get_hole_1()).add_con;
            s6_4 += this._l1power.getItem(power.get_hole_1()).add_wis;
            s6_5 += this._l1power.getItem(power.get_hole_1()).add_int;
            s6_6 += this._l1power.getItem(power.get_hole_1()).add_cha;
            aH_1 += this._l1power.getItem(power.get_hole_1()).addMaxHP;
            aM_1 += this._l1power.getItem(power.get_hole_1()).addMaxMP;
            aMR_1 += this._l1power.getItem(power.get_hole_1()).m_def;
            aSP_1 += this._l1power.getItem(power.get_hole_1()).add_sp;
            addDmgModifier += this._l1power.getItem(power.get_hole_1()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_1()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_1()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_1()).bow_hit_modifier;
            drd += this._l1power.getItem(power.get_hole_1()).addDamageReductionByArmor;
            aHpr += this._l1power.getItem(power.get_hole_2()).add_hpr;
            aMpr += this._l1power.getItem(power.get_hole_2()).add_mpr;
            s6_1 += this._l1power.getItem(power.get_hole_2()).add_str;
            s6_2 += this._l1power.getItem(power.get_hole_2()).add_dex;
            s6_3 += this._l1power.getItem(power.get_hole_2()).add_con;
            s6_4 += this._l1power.getItem(power.get_hole_2()).add_wis;
            s6_5 += this._l1power.getItem(power.get_hole_2()).add_int;
            s6_6 += this._l1power.getItem(power.get_hole_2()).add_cha;
            aH_1 += this._l1power.getItem(power.get_hole_2()).addMaxHP;
            aM_1 += this._l1power.getItem(power.get_hole_2()).addMaxMP;
            aMR_1 += this._l1power.getItem(power.get_hole_2()).m_def;
            aSP_1 += this._l1power.getItem(power.get_hole_2()).add_sp;
            addDmgModifier += this._l1power.getItem(power.get_hole_2()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_2()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_2()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_2()).bow_hit_modifier;
            drd += this._l1power.getItem(power.get_hole_2()).addDamageReductionByArmor;
            aHpr += this._l1power.getItem(power.get_hole_3()).add_hpr;
            aMpr += this._l1power.getItem(power.get_hole_3()).add_mpr;
            s6_1 += this._l1power.getItem(power.get_hole_3()).add_str;
            s6_2 += this._l1power.getItem(power.get_hole_3()).add_dex;
            s6_3 += this._l1power.getItem(power.get_hole_3()).add_con;
            s6_4 += this._l1power.getItem(power.get_hole_3()).add_wis;
            s6_5 += this._l1power.getItem(power.get_hole_3()).add_int;
            s6_6 += this._l1power.getItem(power.get_hole_3()).add_cha;
            aH_1 += this._l1power.getItem(power.get_hole_3()).addMaxHP;
            aM_1 += this._l1power.getItem(power.get_hole_3()).addMaxMP;
            aMR_1 += this._l1power.getItem(power.get_hole_3()).m_def;
            aSP_1 += this._l1power.getItem(power.get_hole_3()).add_sp;
            addDmgModifier += this._l1power.getItem(power.get_hole_3()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_3()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_3()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_3()).bow_hit_modifier;
            drd += this._l1power.getItem(power.get_hole_3()).addDamageReductionByArmor;
            aHpr += this._l1power.getItem(power.get_hole_4()).add_hpr;
            aMpr += this._l1power.getItem(power.get_hole_4()).add_mpr;
            s6_1 += this._l1power.getItem(power.get_hole_4()).add_str;
            s6_2 += this._l1power.getItem(power.get_hole_4()).add_dex;
            s6_3 += this._l1power.getItem(power.get_hole_4()).add_con;
            s6_4 += this._l1power.getItem(power.get_hole_4()).add_wis;
            s6_5 += this._l1power.getItem(power.get_hole_4()).add_int;
            s6_6 += this._l1power.getItem(power.get_hole_4()).add_cha;
            aH_1 += this._l1power.getItem(power.get_hole_4()).addMaxHP;
            aM_1 += this._l1power.getItem(power.get_hole_4()).addMaxMP;
            aMR_1 += this._l1power.getItem(power.get_hole_4()).m_def;
            aSP_1 += this._l1power.getItem(power.get_hole_4()).add_sp;
            addDmgModifier += this._l1power.getItem(power.get_hole_4()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_4()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_4()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_4()).bow_hit_modifier;
            drd += this._l1power.getItem(power.get_hole_4()).addDamageReductionByArmor;
            aHpr += this._l1power.getItem(power.get_hole_5()).add_hpr;
            aMpr += this._l1power.getItem(power.get_hole_5()).add_mpr;
            s6_1 += this._l1power.getItem(power.get_hole_5()).add_str;
            s6_2 += this._l1power.getItem(power.get_hole_5()).add_dex;
            s6_3 += this._l1power.getItem(power.get_hole_5()).add_con;
            s6_4 += this._l1power.getItem(power.get_hole_5()).add_wis;
            s6_5 += this._l1power.getItem(power.get_hole_5()).add_int;
            s6_6 += this._l1power.getItem(power.get_hole_5()).add_cha;
            aH_1 += this._l1power.getItem(power.get_hole_5()).addMaxHP;
            aM_1 += this._l1power.getItem(power.get_hole_5()).addMaxMP;
            aMR_1 += this._l1power.getItem(power.get_hole_5()).m_def;
            aSP_1 += this._l1power.getItem(power.get_hole_5()).add_sp;
            addDmgModifier += this._l1power.getItem(power.get_hole_5()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_5()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_5()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_5()).bow_hit_modifier;
            drd += this._l1power.getItem(power.get_hole_5()).addDamageReductionByArmor;
        }
        int pw_s1 = this._item.get_addstr() + attr_str;
        int pw_s2 = this._item.get_adddex() + attr_dex;
        int pw_s3 = this._item.get_addcon() + attr_con;
        int pw_s4 = this._item.get_addwis() + attr_wis;
        int pw_s5 = this._item.get_addint() + attr_int;
        int pw_s6 = this._item.get_addcha() + attr_cha;
        int pw_sHp = this._itemPower.get_addhp() + attr_hp;
        int pw_sMp = this._item.get_addmp() + attr_mp;
        int pw_sMr = this._itemPower.getMr() + this._itemInstance.getItemMr() + attr_mr;
        int pw_sSp = this._itemPower.getSp() + attr_sp;
        int pw_sAk = this._itemPower.get_addattack();
        int pw_sbAk = this._itemPower.get_addbowattack();
        int pw_sDg = this._item.getDmgModifierByArmor();
        int pw_sHi = this._itemPower.getHitModifierByArmor();
        int pw_mHi = this._item.getMagicHitModifierByArmor();
        int pw_bDg = this._item.getBowDmgModifierByArmor();
        int pw_bHi = this._item.getBowHitModifierByArmor() + this._itemInstance.getItemBowHit() + addBowHitModifier;
        int pw_d4_1 = this._item.get_defense_fire();
        int pw_d4_2 = this._item.get_defense_water();
        int pw_d4_3 = this._item.get_defense_wind();
        int pw_d4_4 = this._item.get_defense_earth();
        int pw_k6_1 = this._item.get_regist_freeze();
        int pw_k6_2 = this._item.get_regist_stone();
        int pw_k6_3 = this._item.get_regist_sleep();
        int pw_k6_4 = this._item.get_regist_blind();
        int pw_k6_5 = this._item.get_regist_stun();
        int pw_k6_6 = this._item.get_regist_sustain();
        int addexp = this._item.getExpPoint();
        int pw_drd = this._itemPower.getDamageReduction() + this._itemInstance.getItemReductionDmg() + attr_dmgR;
        int uhp = this._item.get_up_hp_potion() + this._itemInstance.getItemhppotion();
        int uhp_num = this._item.get_uhp_number();
        int PVPdmgReduction = this._item.getPVPdmgReduction() + PVP減傷;
        int PVPdmg = this._item.getPVPdmg() + PVP增傷;
        L1WilliamEnchantOrginal accessoryOrginal = null;
        L1WilliamEnchantOrginal accessoryOrginalOk = null;
        L1WilliamEnchantOrginal[] accessoryOrginalSize = EnchantOrginal.getInstance().getArmorList();
        int j = 0;
        while (j < accessoryOrginalSize.length) {
            accessoryOrginalOk = EnchantOrginal.getInstance().getTemplate(j);
            if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == this._itemInstance.getItem().getItemId() && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal = accessoryOrginalOk;
                break;
            }
            else if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == 0 && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal = accessoryOrginalOk;
                break;
            }
            else if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == 0 && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal = accessoryOrginalOk;
                break;
            }
            else {
                ++j;
            }
        }
        if (accessoryOrginal != null) {
            pw_s1 += accessoryOrginal.getAddStr();
            pw_s2 += accessoryOrginal.getAddDex();
            pw_s3 += accessoryOrginal.getAddCon();
            pw_s5 += accessoryOrginal.getAddInt();
            pw_s4 += accessoryOrginal.getAddWis();
            pw_s6 += accessoryOrginal.getAddCha();
            ac += accessoryOrginal.getAddAc();
            pw_sHp += accessoryOrginal.getAddMaxHp();
            pw_sMp += accessoryOrginal.getAddMaxMp();
            pw_sHpr += accessoryOrginal.getAddHpr();
            pw_sMpr += accessoryOrginal.getAddMpr();
            pw_sDg += accessoryOrginal.getAddDmg();
            hit += accessoryOrginal.getAddHit();
            pw_bDg += accessoryOrginal.getAddBowDmg();
            pw_bHi += accessoryOrginal.getAddBowHit();
            pw_drd += accessoryOrginal.getAddDmgReduction();
            pw_sMr += accessoryOrginal.getAddMr();
            pw_sSp += accessoryOrginal.getAddSp();
            PVPdmg += accessoryOrginal.getPVPdmg();
            PVPdmgReduction += accessoryOrginal.getPVPdmgReduction();
            uhp += accessoryOrginal.getPotion_Heal();
            uhp_num += accessoryOrginal.getPotion_Healling();
            pw_mHi += accessoryOrginal.getAddMagicHit();
        }
        int add_hit = pw_sHi + hit + addHitModifier + this._itemInstance.getItemHit();
        if (add_hit != 0) {
            this._os.writeC(5);
            this._os.writeC(add_hit);
        }
        int add_sdg = pw_sDg + this.greater()[2] + admg + this._itemInstance.getItemAttack() + pw_sAk + addDmgModifier;
        if (add_sdg != 0) {
            this._os.writeC(6);
            this._os.writeC(add_sdg);
        }
        int add_bdg = pw_bDg + this.greater()[3] + bdmg + this._itemInstance.getItemBowAttack() + pw_sbAk + addBowDmgModifier;
        if (add_bdg != 0) {
            this._os.writeC(35);
            this._os.writeC(add_bdg);
        }
        int weight = this._item.getWeightReduction();
        if (weight != 0) {
            this._os.writeC(39);
            this._os.writeS("負重增加 +" + weight);
        }
        if (mdmg != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法傷害+" + mdmg);
        }
        if (mdrd != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法傷害減免+" + mdrd);
        }
        int bit = 0;
        bit |= (this._item.isUseRoyal() ? 1 : 0);
        bit |= (this._item.isUseKnight() ? 2 : 0);
        bit |= (this._item.isUseElf() ? 4 : 0);
        bit |= (this._item.isUseMage() ? 8 : 0);
        bit |= (this._item.isUseDarkelf() ? 16 : 0);
        bit |= (this._item.isUseDragonknight() ? 32 : 0);
        bit |= (this._item.isUseIllusionist() ? 64 : 0);
        this._os.writeC(7);
        this._os.writeC(bit);
        if (pw_bHi > 0) {
            this._os.writeC(24);
            this._os.writeC(pw_bHi);
        }
        int safeenchant = this._item.get_safeenchant();
        if (safeenchant >= 0) {
            this._os.writeC(39);
            this._os.writeS("安定值: " + this._item.get_safeenchant());
        }
        int addstr = pw_s1 + s6_1 + this._itemInstance.getItemStr();
        int adddex = pw_s2 + s6_2 + this._itemInstance.getItemDex();
        int addcon = pw_s3 + s6_3 + this._itemInstance.getItemCon();
        int addwis = pw_s4 + s6_4 + this._itemInstance.getItemWis();
        int addint = pw_s5 + s6_5 + this._itemInstance.getItemInt();
        int addcha = pw_s6 + s6_6 + this._itemInstance.getItemCha();
        if (addstr == 1 && adddex == 1 && addcon == 1 && addwis == 1 && addint == 1 && addcha == 1) {
            this._os.writeC(39);
            this._os.writeS("全能力值 +1");
        }
        else {
            if (addstr != 0) {
                this._os.writeC(8);
                this._os.writeC(addstr);
            }
            if (adddex != 0) {
                this._os.writeC(9);
                this._os.writeC(adddex);
            }
            if (addcon != 0) {
                this._os.writeC(10);
                this._os.writeC(addcon);
            }
            if (addwis != 0) {
                this._os.writeC(11);
                this._os.writeC(addwis);
            }
            if (addint != 0) {
                this._os.writeC(12);
                this._os.writeC(addint);
            }
            if (addcha != 0) {
                this._os.writeC(13);
                this._os.writeC(addcha);
            }
        }
        int addhp = pw_sHp + aH_1 + this.greater()[0] + this._itemInstance.getItemHp();
        if (addhp != 0) {
            this._os.writeC(14);
            this._os.writeH(addhp);
        }
        int addmp = pw_sMp + aM_1 + this._itemInstance.getItemMp();
        if (addmp != 0) {
            if (addmp <= 120) {
                this._os.writeC(32);
                this._os.writeC(addmp);
            }
            else {
                this._os.writeC(39);
                this._os.writeS("魔力上限+" + addmp);
            }
        }
        int addhpr = pw_sHpr + aHpr;
        if (addhpr != 0) {
            this._os.writeC(37);
            this._os.writeC(addhpr);
        }
        int addmpr = pw_sMpr + aMpr;
        if (addmpr != 0) {
            this._os.writeC(38);
            this._os.writeC(addmpr);
        }
        int freeze = pw_k6_1 + k6_1;
        if (freeze != 0) {
            this._os.writeC(15);
            this._os.writeH(freeze);
            this._os.writeC(33);
            this._os.writeC(1);
        }
        int stone = pw_k6_2 + k6_2;
        if (stone != 0) {
            this._os.writeC(15);
            this._os.writeH(stone);
            this._os.writeC(33);
            this._os.writeC(2);
        }
        int sleep = pw_k6_3 + k6_3;
        if (sleep != 0) {
            this._os.writeC(15);
            this._os.writeH(sleep);
            this._os.writeC(33);
            this._os.writeC(3);
        }
        int blind = pw_k6_4 + k6_4;
        if (blind != 0) {
            this._os.writeC(15);
            this._os.writeH(blind);
            this._os.writeC(33);
            this._os.writeC(4);
        }
        int stun = pw_k6_5 + k6_5;
        if (stun != 0) {
            this._os.writeC(15);
            this._os.writeH(stun);
            this._os.writeC(33);
            this._os.writeC(5);
        }
        int sustain = pw_k6_6 + k6_6;
        if (sustain != 0) {
            this._os.writeC(15);
            this._os.writeH(sustain);
            this._os.writeC(33);
            this._os.writeC(6);
        }
        int addmr = pw_sMr + aMR_1;
        if (addmr != 0) {
            this._os.writeC(15);
            this._os.writeH(addmr);
        }
        int addsp = pw_sSp + aSP_1 + this._itemInstance.getItemSp();
        if (addsp != 0) {
            this._os.writeC(17);
            this._os.writeC(addsp);
        }
        boolean haste = this._item.isHasteItem();
        if (aSS_1 == 1) {
            haste = true;
        }
        if (haste) {
            this._os.writeC(18);
        }
        int fire = pw_d4_1 + d4_1;
        if (fire != 0) {
            this._os.writeC(27);
            this._os.writeC(fire);
        }
        int water = pw_d4_2 + d4_2;
        if (water != 0) {
            this._os.writeC(28);
            this._os.writeC(water);
        }
        int wind = pw_d4_3 + d4_3;
        if (wind != 0) {
            this._os.writeC(29);
            this._os.writeC(wind);
        }
        int earth = pw_d4_4 + d4_4;
        if (earth != 0) {
            this._os.writeC(30);
            this._os.writeC(earth);
        }
        if (drain_hp_rand != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(drain_hp_rand) + "%吸血:" + drain_min_hp + "~" + drain_max_hp);
        }
        if (drain_mp_rand != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(drain_mp_rand) + "%吸魔:" + drain_min_mp + "~" + drain_max_mp);
        }
        if (skill_gfxid != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(skill_rand) + "%特效增傷+" + skill_dmg);
        }
        if (attr_物理格檔 != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(attr_物理格檔) + "%物理格檔");
        }
        if (attr_魔法格檔 != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(attr_魔法格檔) + "%魔法格檔");
        }
        if (addexp != 0) {
            this._os.writeC(39);
            this._os.writeS("狩獵經驗值 +" + addexp);
        }
        if (this._itemInstance.getItemMag_Red() != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法減免-" + this._itemInstance.getItemMag_Red());
        }
        if (this._itemInstance.getItemDg() != 0) {
            this._os.writeC(39);
            this._os.writeS("物理閃避+" + this._itemInstance.getItemDg());
        }
        if (this._itemInstance.getItemistSustain() != 0) {
            this._os.writeC(39);
            this._os.writeS("支撐耐性+" + this._itemInstance.getItemistSustain());
        }
        if (this._itemInstance.getItemistStun() != 0) {
            this._os.writeC(39);
            this._os.writeS("昏迷耐性+" + this._itemInstance.getItemistStun());
        }
        if (this._itemInstance.getItemistStone() != 0) {
            this._os.writeC(39);
            this._os.writeS("石化耐性+" + this._itemInstance.getItemistStone());
        }
        if (this._itemInstance.getItemistSleep() != 0) {
            this._os.writeC(39);
            this._os.writeS("睡眠耐性+" + this._itemInstance.getItemistSleep());
        }
        if (this._itemInstance.getItemistFreeze() != 0) {
            this._os.writeC(39);
            this._os.writeS("寒冰耐性+" + this._itemInstance.getItemistFreeze());
        }
        if (this._itemInstance.getItemistBlind() != 0) {
            this._os.writeC(39);
            this._os.writeS("暗黑耐性+" + this._itemInstance.getItemistBlind());
        }
        int alldrd = pw_drd + drd + this.greater()[4];
        if (alldrd != 0) {
            this._os.writeC(39);
            this._os.writeS("傷害減免+" + alldrd);
        }
        if (pw_mHi != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法命中+" + pw_mHi);
        }
        if (mcri != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法爆擊率+" + mcri);
        }
        StringBuilder name3 = new StringBuilder();
        int adduhp = uhp + this.greater()[1];
        if (adduhp != 0 || uhp_num != 0) {
            name3.append("藥水回復量+" + adduhp + "%");
            if (uhp_num != 0) {
                name3.append("+" + uhp_num);
            }
            this._os.writeC(39);
            this._os.writeS(name3.toString());
        }
        if (attr_potion_heal != 0) {
            this._os.writeC(39);
            this._os.writeS("藥水回復增量+" + attr_potion_heal);
        }
        if (this._item.getclassname().equalsIgnoreCase("Venom_Resist") || this._item.getclassname().equalsIgnoreCase("ElitePlateMail_Antharas")) {
            this._os.writeC(39);
            this._os.writeS("防護中毒狀態");
        }
        if (weapon_pro != 0) {
            this._os.writeC(39);
            this._os.writeS("魔武發動率+" + weapon_pro);
        }
        if (weapon_dmg != 0) {
            this._os.writeC(39);
            this._os.writeS("魔武增傷害+" + weapon_dmg);
        }
        if (PVPdmgReduction != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害減免+" + PVPdmgReduction);
        }
        if (PVPdmg != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害增加+" + PVPdmg);
        }
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream accessories() {
        int attr_str = 0;
        int attr_con = 0;
        int attr_dex = 0;
        int attr_int = 0;
        int attr_wis = 0;
        int attr_cha = 0;
        int attr_hp = 0;
        int attr_mp = 0;
        int attr_sp = 0;
        int attr_mr = 0;
        int drain_min_hp = 0;
        int drain_max_hp = 0;
        int drain_hp_rand = 0;
        int attr_hpr = 0;
        int attr_mpr = 0;
        int drain_min_mp = 0;
        int drain_max_mp = 0;
        int drain_mp_rand = 0;
        int skill_gfxid = 0;
        int skill_rand = 0;
        int skill_dmg = 0;
        int attr_魔法格檔 = 0;
        int attr_物理格檔 = 0;
        int PVP增傷 = 0;
        int PVP減傷 = 0;
        int attr_potion_heal = 0;
        L1ItemSpecialAttributeChar attr_char = this._itemInstance.get_ItemAttrName();
        if (attr_char != null) {
            L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
            attr_str = attr.get_add_str();
            attr_con = attr.get_add_con();
            attr_dex = attr.get_add_dex();
            attr_int = attr.get_add_int();
            attr_wis = attr.get_add_wis();
            attr_cha = attr.get_add_cha();
            attr_hp = attr.get_add_hp();
            attr_mp = attr.get_add_mp();
            attr_sp = attr.get_add_sp();
            attr_mr = attr.get_add_m_def();
            drain_min_hp = attr.get_add_drain_min_hp();
            drain_max_hp = attr.get_add_drain_max_hp();
            drain_hp_rand = attr.get_drain_hp_rand();
            drain_min_mp = attr.get_add_drain_min_mp();
            drain_max_mp = attr.get_add_drain_max_mp();
            drain_mp_rand = attr.get_drain_mp_rand();
            attr_hpr = attr.get_add_hpr();
            attr_mpr = attr.get_add_mpr();
            skill_gfxid = attr.get_add_skill_gfxid();
            skill_rand = attr.get_add_skill_rand();
            skill_dmg = attr.get_add_skill_dmg();
            attr_魔法格檔 = attr.get魔法格檔();
            attr_物理格檔 = attr.get物理格檔();
            PVP增傷 = attr.get_add_pvp_dmg();
            PVP減傷 = attr.get_add_pvp_redmg();
            attr_potion_heal = attr.get_add_potion_heal();
        }
        int ac = this._item.get_ac() - this._itemInstance.getItemAc();
        if (ac < 0) {
            ac = Math.abs(ac);
        }
        this._os.writeC(19);
        this._os.writeC(ac);
        this._os.writeC(this._item.getMaterial());
        this._os.writeC(-1);
        this._os.writeD(this._itemInstance.getWeight());
        if (this._itemInstance.getItemArmorType() > 0) {
            this._os.writeC(2);
            this._os.writeC(this._itemInstance.getItemArmorType());
        }
        if (this._item.get_greater() == 0) {
            this._os.writeC(39);
            this._os.writeS("飾品級別：耐性");
        }
        else if (this._item.get_greater() == 1) {
            this._os.writeC(39);
            this._os.writeS("飾品級別：熱情");
        }
        else if (this._item.get_greater() == 2) {
            this._os.writeC(39);
            this._os.writeS("飾品級別：意志");
        }
        int s6_1 = 0;
        int s6_2 = 0;
        int s6_3 = 0;
        int s6_4 = 0;
        int s6_5 = 0;
        int s6_6 = 0;
        int aH_1 = 0;
        int aM_1 = 0;
        int aMR_1 = 0;
        int aSP_1 = 0;
        int aSS_1 = 0;
        int d4_1 = 0;
        int d4_2 = 0;
        int d4_3 = 0;
        int d4_4 = 0;
        int k6_1 = 0;
        int k6_2 = 0;
        int k6_3 = 0;
        int k6_4 = 0;
        int k6_5 = 0;
        int k6_6 = 0;
        int aHpr = 0;
        int aMpr = 0;
        int admg = 0;
        int drd = 0;
        int mdmg = 0;
        int mdrd = 0;
        int bdmg = 0;
        int hit = 0;
        int bhit = 0;
        int mcri = 0;
        int weapon_pro = this._item.getweaponskillpro();
        int weapon_dmg = this._item.getweaponskilldmg();
        if (this._itemInstance.isMatch()) {
            s6_1 = this._item.get_mode()[0];
            s6_2 = this._item.get_mode()[1];
            s6_3 = this._item.get_mode()[2];
            s6_4 = this._item.get_mode()[3];
            s6_5 = this._item.get_mode()[4];
            s6_6 = this._item.get_mode()[5];
            aH_1 = this._item.get_mode()[6];
            aM_1 = this._item.get_mode()[7];
            aMR_1 = this._item.get_mode()[8];
            aSP_1 = this._item.get_mode()[9];
            aSS_1 = this._item.get_mode()[10];
            d4_1 = this._item.get_mode()[11];
            d4_2 = this._item.get_mode()[12];
            d4_3 = this._item.get_mode()[13];
            d4_4 = this._item.get_mode()[14];
            k6_1 = this._item.get_mode()[15];
            k6_2 = this._item.get_mode()[16];
            k6_3 = this._item.get_mode()[17];
            k6_4 = this._item.get_mode()[18];
            k6_5 = this._item.get_mode()[19];
            k6_6 = this._item.get_mode()[20];
            aHpr = this._item.get_mode()[21];
            aMpr = this._item.get_mode()[22];
            admg = this._item.get_mode()[23];
            drd = this._item.get_mode()[24];
            mdmg = this._item.get_mode()[25];
            mdrd = this._item.get_mode()[26];
            bdmg = this._item.get_mode()[27];
            hit = this._item.get_mode()[28];
            bhit = this._item.get_mode()[29];
            mcri = this._item.get_mode()[30];
            weapon_pro = this._item.get_mode()[31];
            weapon_dmg = this._item.get_mode()[32];
        }
        if (this._itemInstance.get_time() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this._os.writeC(39);
            this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
        }
        int pw_s1 = this._item.get_addstr() + this._itemInstance.getItemStr() + attr_str;
        int pw_s2 = this._item.get_adddex() + this._itemInstance.getItemDex() + attr_dex;
        int pw_s3 = this._item.get_addcon() + this._itemInstance.getItemCon() + attr_con;
        int pw_s4 = this._item.get_addwis() + this._itemInstance.getItemWis() + attr_wis;
        int pw_s5 = this._item.get_addint() + this._itemInstance.getItemInt() + attr_int;
        int pw_s6 = this._item.get_addcha() + this._itemInstance.getItemCha() + attr_cha;
        int pw_sHp = this._itemPower.get_addhp() + attr_hp;
        int pw_sMp = this._item.get_addmp() + attr_mp;
        int pw_sMr = this._itemPower.getMr() + this._itemInstance.getItemMr() + attr_mr;
        int pw_sSp = this._itemPower.getSp() + this._itemInstance.getItemSp() + attr_sp;
        int pw_sDg = this._item.getDmgModifierByArmor() + this._itemInstance.getItemAttack();
        int pw_sHi = this._itemPower.getHitModifierByArmor();
        int pw_mHi = this._item.getMagicHitModifierByArmor();
        int pw_bDg = this._item.getBowDmgModifierByArmor() + this._itemInstance.getItemBowAttack();
        int pw_bHi = this._item.getBowHitModifierByArmor();
        int pw_d4_1 = this._item.get_defense_fire();
        int pw_d4_2 = this._item.get_defense_water();
        int pw_d4_3 = this._item.get_defense_wind();
        int pw_d4_4 = this._item.get_defense_earth();
        int pw_k6_1 = this._item.get_regist_freeze();
        int pw_k6_2 = this._item.get_regist_stone();
        int pw_k6_3 = this._item.get_regist_sleep();
        int pw_k6_4 = this._item.get_regist_blind();
        int pw_k6_5 = this._item.get_regist_stun();
        int pw_k6_6 = this._item.get_regist_sustain();
        int pw_sHpr = this._item.get_addhpr() + this._itemInstance.getItemHpr() + attr_hpr;
        int pw_sMpr = this._itemPower.getMpr() + this._itemInstance.getItemMpr() + attr_mpr;
        int addexp = this._item.getExpPoint();
        int pw_drd = this._itemPower.getDamageReduction() + this._itemInstance.getItemReductionDmg();
        int pvpdmg = 0;
        int pvpdmg_r = 0;
        int potion_heal = this._item.get_up_hp_potion() + this._itemInstance.getItemhppotion();
        int potion_healling = this._item.get_uhp_number();
        L1WilliamEnchantOrginal accessoryOrginal1 = null;
        L1WilliamEnchantOrginal accessoryOrginalOk1 = null;
        L1WilliamEnchantOrginal[] accessoryOrginalSize1 = EnchantOrginal.getInstance().getArmorList();
        int j = 0;
        while (j < accessoryOrginalSize1.length) {
            accessoryOrginalOk1 = EnchantOrginal.getInstance().getTemplate(j);
            if (accessoryOrginalOk1.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk1.getitemid() == this._itemInstance.getItem().getItemId() && accessoryOrginalOk1.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal1 = accessoryOrginalOk1;
                break;
            }
            else if (accessoryOrginalOk1.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk1.getitemid() == 0 && accessoryOrginalOk1.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal1 = accessoryOrginalOk1;
                break;
            }
            else if (accessoryOrginalOk1.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk1.getitemid() == 0 && accessoryOrginalOk1.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal1 = accessoryOrginalOk1;
                break;
            }
            else {
                ++j;
            }
        }
        if (accessoryOrginal1 != null) {
            pw_s1 += accessoryOrginal1.getAddStr();
            pw_s2 += accessoryOrginal1.getAddDex();
            pw_s3 += accessoryOrginal1.getAddCon();
            pw_s5 += accessoryOrginal1.getAddInt();
            pw_s4 += accessoryOrginal1.getAddWis();
            pw_s6 += accessoryOrginal1.getAddCha();
            ac += accessoryOrginal1.getAddAc();
            pw_sHp += accessoryOrginal1.getAddMaxHp();
            pw_sMp += accessoryOrginal1.getAddMaxMp();
            pw_sHpr += accessoryOrginal1.getAddHpr();
            pw_sMpr += accessoryOrginal1.getAddMpr();
            pw_sDg += accessoryOrginal1.getAddDmg();
            hit += accessoryOrginal1.getAddHit();
            pw_bDg += accessoryOrginal1.getAddBowDmg();
            pw_bHi += accessoryOrginal1.getAddBowHit();
            pw_drd += accessoryOrginal1.getAddDmgReduction();
            pw_sMr += accessoryOrginal1.getAddMr();
            pw_sSp += accessoryOrginal1.getAddSp();
            pvpdmg += accessoryOrginal1.getPVPdmg();
            pvpdmg_r += accessoryOrginal1.getPVPdmgReduction();
            potion_heal += accessoryOrginal1.getPotion_Heal();
            potion_healling += accessoryOrginal1.getPotion_Healling();
            pw_mHi += accessoryOrginal1.getAddMagicHit();
        }
        L1WilliamEnchantAccessory accessoryOrginal2 = null;
        L1WilliamEnchantAccessory accessoryOrginalOk2 = null;
        L1WilliamEnchantAccessory[] accessoryOrginalSize2 = EnchantAccessory.getInstance().getArmorList();
        int i = 0;
        while (i < accessoryOrginalSize2.length) {
            accessoryOrginalOk2 = EnchantAccessory.getInstance().getTemplate(i);
            if (accessoryOrginalOk2.getType() == this._itemInstance.getItem().getUseType() && accessoryOrginalOk2.getStrength() == this._itemInstance.getItem().get_greater() && accessoryOrginalOk2.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal2 = accessoryOrginalOk2;
                break;
            }
            else {
                ++i;
            }
        }
        if (accessoryOrginal2 != null) {
            pw_s1 += accessoryOrginal2.getAddStr();
            pw_s2 += accessoryOrginal2.getAddDex();
            pw_s3 += accessoryOrginal2.getAddCon();
            pw_s5 += accessoryOrginal2.getAddInt();
            pw_s4 += accessoryOrginal2.getAddWis();
            pw_s6 += accessoryOrginal2.getAddCha();
            ac += accessoryOrginal2.getAddAc();
            pw_sHp += accessoryOrginal2.getAddMaxHp();
            pw_sMp += accessoryOrginal2.getAddMaxMp();
            pw_sHpr += accessoryOrginal2.getAddHpr();
            pw_sMpr += accessoryOrginal2.getAddMpr();
            pw_sDg += accessoryOrginal2.getAddDmg();
            hit += accessoryOrginal2.getAddHit();
            pw_bDg += accessoryOrginal2.getAddBowDmg();
            pw_bHi += accessoryOrginal2.getAddBowHit();
            pw_drd += accessoryOrginal2.getAddDmgReduction();
            pw_sMr += accessoryOrginal2.getAddMr();
            pw_sSp += accessoryOrginal2.getAddSp();
            pvpdmg += accessoryOrginal2.getPVPdmg();
            pvpdmg_r += accessoryOrginal2.getPVPdmgReduction();
            potion_heal += accessoryOrginal2.getPotion_Heal();
            potion_healling += accessoryOrginal2.getPotion_Healling();
            pw_mHi += accessoryOrginal2.getAddMagicHit();
        }

        int add_hit = pw_sHi + hit + this._itemInstance.getItemHit();
        if (add_hit != 0) {
            this._os.writeC(5);
            this._os.writeC(add_hit);
        }
        int add_sdg = pw_sDg + this.greater()[4] + admg;
        if (add_sdg != 0) {
            this._os.writeC(6);
            this._os.writeC(add_sdg);
        }
        int add_bowhit = pw_bHi + bhit;
        if (add_bowhit != 0) {
            this._os.writeC(24);
            this._os.writeC(add_bowhit);
        }
        int add_bdg = pw_bDg + this.greater()[5] + bdmg;
        if (add_bdg != 0) {
            this._os.writeC(35);
            this._os.writeC(add_bdg);
        }
        int weight = this._item.getWeightReduction();
        if (weight != 0) {
            this._os.writeC(39);
            this._os.writeS("負重增加 +" + weight);
        }
        if (mdmg != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法傷害+" + mdmg);
        }
        if (mdrd != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法傷害減免+" + mdrd);
        }
        int PVPdmgReduction = pvpdmg_r;
        if (PVPdmgReduction != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害減免+" + PVPdmgReduction);
        }
        int PVPdmg = pvpdmg;
        if (PVPdmg != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害增加+" + PVPdmg);
        }
        if (drain_hp_rand != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(drain_hp_rand) + "%吸血:" + drain_min_hp + "~" + drain_max_hp);
        }
        if (drain_mp_rand != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(drain_mp_rand) + "%吸魔:" + drain_min_mp + "~" + drain_max_mp);
        }
        if (skill_gfxid != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(skill_rand) + "%特效增傷+" + skill_dmg);
        }
        if (attr_物理格檔 != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(attr_物理格檔) + "%物理格檔");
        }
        if (attr_魔法格檔 != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(attr_魔法格檔) + "%魔法格檔");
        }
        if (potion_heal != 0 && potion_healling != 0) {
            this._os.writeC(39);
            this._os.writeS("藥水回復量+" + potion_heal + "%+" + potion_healling);
        }
        else if (potion_heal != 0 && potion_healling == 0) {
            this._os.writeC(39);
            this._os.writeS("藥水回復量+" + potion_heal + "%");
        }
        if (attr_potion_heal != 0) {
            this._os.writeC(39);
            this._os.writeS("藥水回復增量+" + attr_potion_heal);
        }
        if (weapon_pro != 0) {
            this._os.writeC(39);
            this._os.writeS("魔武發動率+" + weapon_pro);
        }
        if (weapon_dmg != 0) {
            this._os.writeC(39);
            this._os.writeS("魔武增傷害+" + weapon_dmg);
        }
        int bit = 0;
        bit |= (this._item.isUseRoyal() ? 1 : 0);
        bit |= (this._item.isUseKnight() ? 2 : 0);
        bit |= (this._item.isUseElf() ? 4 : 0);
        bit |= (this._item.isUseMage() ? 8 : 0);
        bit |= (this._item.isUseDarkelf() ? 16 : 0);
        bit |= (this._item.isUseDragonknight() ? 32 : 0);
        bit |= (this._item.isUseIllusionist() ? 64 : 0);
        this._os.writeC(7);
        this._os.writeC(bit);
        int addstr = pw_s1 + s6_1;
        if (addstr != 0) {
            this._os.writeC(8);
            this._os.writeC(addstr);
        }
        int adddex = pw_s2 + s6_2;
        if (adddex != 0) {
            this._os.writeC(9);
            this._os.writeC(adddex);
        }
        int addcon = pw_s3 + s6_3;
        if (addcon != 0) {
            this._os.writeC(10);
            this._os.writeC(addcon);
        }
        int addwis = pw_s4 + s6_4;
        if (addwis != 0) {
            this._os.writeC(11);
            this._os.writeC(addwis);
        }
        int addint = pw_s5 + s6_5;
        if (addint != 0) {
            this._os.writeC(12);
            this._os.writeC(addint);
        }
        int addcha = pw_s6 + s6_6;
        if (addcha != 0) {
            this._os.writeC(13);
            this._os.writeC(addcha);
        }
        int addhp = pw_sHp + aH_1 + this.greater()[0] + this._itemInstance.getItemHp();
        if (addhp != 0) {
            this._os.writeC(14);
            this._os.writeH(addhp);
        }
        int addmp = pw_sMp + aM_1 + this.greater()[1] + this._itemInstance.getItemMp();
        if (addmp != 0) {
            if (addmp <= 120) {
                this._os.writeC(32);
                this._os.writeC(addmp);
            }
            else {
                this._os.writeC(39);
                this._os.writeS("魔力上限+" + addmp);
            }
        }
        int addhpr = pw_sHpr + aHpr;
        if (addhpr != 0) {
            this._os.writeC(37);
            this._os.writeC(addhpr);
        }
        int addmpr = pw_sMpr + aMpr;
        if (addmpr != 0) {
            this._os.writeC(38);
            this._os.writeC(addmpr);
        }
        int freeze = pw_k6_1 + k6_1;
        if (freeze != 0) {
            this._os.writeC(15);
            this._os.writeH(freeze);
            this._os.writeC(33);
            this._os.writeC(1);
        }
        int stone = pw_k6_2 + k6_2;
        if (stone != 0) {
            this._os.writeC(15);
            this._os.writeH(stone);
            this._os.writeC(33);
            this._os.writeC(2);
        }
        int sleep = pw_k6_3 + k6_3;
        if (sleep != 0) {
            this._os.writeC(15);
            this._os.writeH(sleep);
            this._os.writeC(33);
            this._os.writeC(3);
        }
        int blind = pw_k6_4 + k6_4;
        if (blind != 0) {
            this._os.writeC(15);
            this._os.writeH(blind);
            this._os.writeC(33);
            this._os.writeC(4);
        }
        int stun = pw_k6_5 + k6_5;
        if (stun != 0) {
            this._os.writeC(15);
            this._os.writeH(stun);
            this._os.writeC(33);
            this._os.writeC(5);
        }
        int sustain = pw_k6_6 + k6_6;
        if (sustain != 0) {
            this._os.writeC(15);
            this._os.writeH(sustain);
            this._os.writeC(33);
            this._os.writeC(6);
        }
        int addmr = pw_sMr + aMR_1 + this.greater()[10];
        if (addmr != 0) {
            this._os.writeC(15);
            this._os.writeH(addmr);
        }
        int addsp = pw_sSp + aSP_1 + this.greater()[9];
        if (addsp != 0) {
            this._os.writeC(17);
            this._os.writeC(addsp);
        }
        boolean haste = this._item.isHasteItem();
        if (aSS_1 == 1) {
            haste = true;
        }
        if (haste) {
            this._os.writeC(18);
        }
        int defense_fire = pw_d4_1 + d4_1;
        if (defense_fire != 0) {
            this._os.writeC(27);
            this._os.writeC(defense_fire);
        }
        int defense_water = pw_d4_2 + d4_2;
        if (defense_water != 0) {
            this._os.writeC(28);
            this._os.writeC(defense_water);
        }
        int defense_wind = pw_d4_3 + d4_3;
        if (defense_wind != 0) {
            this._os.writeC(29);
            this._os.writeC(defense_wind);
        }
        int defense_earth = pw_d4_4 + d4_4;
        if (defense_earth != 0) {
            this._os.writeC(30);
            this._os.writeC(defense_earth);
        }
        if (addexp != 0) {
            this._os.writeC(36);
            this._os.writeC(addexp);
        }
        int alldrd = pw_drd + drd + this.greater()[12];
        if (alldrd != 0) {
            this._os.writeC(39);
            this._os.writeS("傷害減免+" + alldrd);
        }
        int addMhit = pw_mHi + this.greater()[6];
        if (addMhit != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法命中+" + addMhit);
        }
        if (mcri != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法爆擊率+" + mcri);
        }
        if (PVP減傷 != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害減免+" + PVP減傷);
        }
        if (PVP增傷 != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害增加+" + PVP增傷);
        }
        if (this._item.getclassname().equalsIgnoreCase("Venom_Resist") || this._item.getclassname().equalsIgnoreCase("ElitePlateMail_Antharas")) {
            this._os.writeC(39);
            this._os.writeS("防護中毒狀態");
        }
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream accessories2() {
        int attr_str = 0;
        int attr_con = 0;
        int attr_dex = 0;
        int attr_int = 0;
        int attr_wis = 0;
        int attr_cha = 0;
        int attr_hp = 0;
        int attr_mp = 0;
        int attr_sp = 0;
        int attr_mr = 0;
        int drain_min_hp = 0;
        int drain_max_hp = 0;
        int drain_hp_rand = 0;
        int attr_hpr = 0;
        int attr_mpr = 0;
        int drain_min_mp = 0;
        int drain_max_mp = 0;
        int drain_mp_rand = 0;
        int skill_gfxid = 0;
        int skill_rand = 0;
        int skill_dmg = 0;
        int attr_魔法格檔 = 0;
        int attr_物理格檔 = 0;
        int PVP增傷 = 0;
        int PVP減傷 = 0;
        int attr_potion_heal = 0;
        L1ItemSpecialAttributeChar attr_char = this._itemInstance.get_ItemAttrName();
        if (attr_char != null) {
            L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
            attr_str = attr.get_add_str();
            attr_con = attr.get_add_con();
            attr_dex = attr.get_add_dex();
            attr_int = attr.get_add_int();
            attr_wis = attr.get_add_wis();
            attr_cha = attr.get_add_cha();
            attr_hp = attr.get_add_hp();
            attr_mp = attr.get_add_mp();
            attr_sp = attr.get_add_sp();
            attr_mr = attr.get_add_m_def();
            drain_min_hp = attr.get_add_drain_min_hp();
            drain_max_hp = attr.get_add_drain_max_hp();
            drain_hp_rand = attr.get_drain_hp_rand();
            drain_min_mp = attr.get_add_drain_min_mp();
            drain_max_mp = attr.get_add_drain_max_mp();
            drain_mp_rand = attr.get_drain_mp_rand();
            attr_hpr = attr.get_add_hpr();
            attr_mpr = attr.get_add_mpr();
            skill_gfxid = attr.get_add_skill_gfxid();
            skill_rand = attr.get_add_skill_rand();
            skill_dmg = attr.get_add_skill_dmg();
            attr_魔法格檔 = attr.get魔法格檔();
            attr_物理格檔 = attr.get物理格檔();
            PVP增傷 = attr.get_add_pvp_dmg();
            PVP減傷 = attr.get_add_pvp_redmg();
            attr_potion_heal = attr.get_add_potion_heal();
        }
        this._os.writeC(19);
        int ac = this._item.get_ac() - this._itemInstance.getItemAc();
        if (ac < 0) {
            ac = Math.abs(ac);
        }
        this._os.writeC(ac);
        this._os.writeC(this._item.getMaterial());
        this._os.writeC(-1);
        this._os.writeD(this._itemInstance.getWeight());
		
        // 👉 請在此處插入代碼
        if (this._itemInstance.getItemArmorType() > 0) {
            this._os.writeC(2); 
            this._os.writeC(this._itemInstance.getItemArmorType());
        }
        
        // 補上缺少的飾品級別文字顯示
        if (this._item.get_greater() == 0) {
            this._os.writeC(39);
            this._os.writeS("飾品級別：耐性");
        }
        else if (this._item.get_greater() == 1) {
            this._os.writeC(39);
            this._os.writeS("飾品級別：熱情");
        }
        else if (this._item.get_greater() == 2) {
            this._os.writeC(39);
            this._os.writeS("飾品級別：意志");
        }

        if (this._itemInstance.get_time() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this._os.writeC(39);
            this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
        }
        int pw_s1 = this._item.get_addstr() + this._itemInstance.getItemStr() + attr_str;
        int pw_s2 = this._item.get_adddex() + this._itemInstance.getItemDex() + attr_dex;
        int pw_s3 = this._item.get_addcon() + this._itemInstance.getItemCon() + attr_con;
        int pw_s4 = this._item.get_addwis() + this._itemInstance.getItemWis() + attr_wis;
        int pw_s5 = this._item.get_addint() + this._itemInstance.getItemInt() + attr_int;
        int pw_s6 = this._item.get_addcha() + this._itemInstance.getItemCha() + attr_cha;
        int pw_sHp = this._itemPower.get_addhp() + this._itemInstance.getItemHp() + attr_hp;
        int pw_sMp = this._item.get_addmp() + this._itemInstance.getItemMp() + attr_mp;
        int pw_sMr = this._itemPower.getMr() + this._itemInstance.getItemMr() + attr_mr;
        int pw_sSp = this._itemPower.getSp() + this._itemInstance.getItemSp() + attr_sp;
        int pw_sDg = this._item.getDmgModifierByArmor() + this._itemInstance.getItemAttack();
        int pw_sHi = this._itemPower.getHitModifierByArmor() + this._itemInstance.getItemHit();
        int pw_mHi = this._item.getMagicHitModifierByArmor();
        int pw_bDg = this._item.getBowDmgModifierByArmor();
        int pw_bHi = this._item.getBowHitModifierByArmor();
        int pw_d4_1 = this._item.get_defense_fire();
        int pw_d4_2 = this._item.get_defense_water();
        int pw_d4_3 = this._item.get_defense_wind();
        int pw_d4_4 = this._item.get_defense_earth();
        int pw_k6_1 = this._item.get_regist_freeze();
        int pw_k6_2 = this._item.get_regist_stone();
        int pw_k6_3 = this._item.get_regist_sleep();
        int pw_k6_4 = this._item.get_regist_blind();
        int pw_k6_5 = this._item.get_regist_stun();
        int pw_k6_6 = this._item.get_regist_sustain();
        int pw_sHpr = this._item.get_addhpr() + this._itemInstance.getItemHpr() + attr_hpr;
        int pw_sMpr = this._itemPower.getMpr() + this._itemInstance.getItemMpr() + attr_mpr;
        int addexp = this._item.getExpPoint();
        int pw_drd = this._itemPower.getDamageReduction() + this._itemInstance.getItemReductionDmg();
        int uhp = this._item.get_up_hp_potion() + this._itemInstance.getItemhppotion();
        int uhp_num = this._item.get_uhp_number();
        if (pw_sHi != 0) {
            this._os.writeC(5);
            this._os.writeC(pw_sHi);
        }
        int add_sdg = pw_sDg;
        if (add_sdg != 0) {
            this._os.writeC(6);
            this._os.writeC(add_sdg);
        }
        if (pw_bHi != 0) {
            this._os.writeC(24);
            this._os.writeC(pw_bHi);
        }
        int add_bdg = pw_bDg;
        if (add_bdg != 0) {
            this._os.writeC(35);
            this._os.writeC(add_bdg);
        }
        int bit = 0;
        bit |= (this._item.isUseRoyal() ? 1 : 0);
        bit |= (this._item.isUseKnight() ? 2 : 0);
        bit |= (this._item.isUseElf() ? 4 : 0);
        bit |= (this._item.isUseMage() ? 8 : 0);
        bit |= (this._item.isUseDarkelf() ? 16 : 0);
        bit |= (this._item.isUseDragonknight() ? 32 : 0);
        bit |= (this._item.isUseIllusionist() ? 64 : 0);
        this._os.writeC(7);
        this._os.writeC(bit);
        int addstr = pw_s1;
        int adddex = pw_s2;
        int addcon = pw_s3;
        int addwis = pw_s4;
        int addint = pw_s5;
        int addcha = pw_s6;
        if (addstr == 1 && adddex == 1 && addcon == 1 && addwis == 1 && addint == 1 && addcha == 1) {
            this._os.writeC(39);
            this._os.writeS("全能力值 +1");
        }
        else {
            if (addstr != 0) {
                this._os.writeC(8);
                this._os.writeC(addstr);
            }
            if (adddex != 0) {
                this._os.writeC(9);
                this._os.writeC(adddex);
            }
            if (addcon != 0) {
                this._os.writeC(10);
                this._os.writeC(addcon);
            }
            if (addwis != 0) {
                this._os.writeC(11);
                this._os.writeC(addwis);
            }
            if (addint != 0) {
                this._os.writeC(12);
                this._os.writeC(addint);
            }
            if (addcha != 0) {
                this._os.writeC(13);
                this._os.writeC(addcha);
            }
        }
        int addhp = pw_sHp;
        if (addhp != 0) {
            this._os.writeC(14);
            this._os.writeH(addhp);
        }
        int addmp = pw_sMp;
        if (addmp != 0) {
            if (addmp <= 120) {
                this._os.writeC(32);
                this._os.writeC(addmp);
            }
            else {
                this._os.writeC(39);
                this._os.writeS("魔力上限+" + addmp);
            }
        }
        int addhpr = pw_sHpr;
        if (addhpr != 0) {
            this._os.writeC(37);
            this._os.writeC(addhpr);
        }
        int addmpr = pw_sMpr;
        if (addmpr != 0) {
            this._os.writeC(38);
            this._os.writeC(addmpr);
        }
        int freeze = pw_k6_1;
        if (freeze != 0) {
            this._os.writeC(15);
            this._os.writeH(freeze);
            this._os.writeC(33);
            this._os.writeC(1);
        }
        int stone = pw_k6_2;
        if (stone != 0) {
            this._os.writeC(15);
            this._os.writeH(stone);
            this._os.writeC(33);
            this._os.writeC(2);
        }
        int sleep = pw_k6_3;
        if (sleep != 0) {
            this._os.writeC(15);
            this._os.writeH(sleep);
            this._os.writeC(33);
            this._os.writeC(3);
        }
        int blind = pw_k6_4;
        if (blind != 0) {
            this._os.writeC(15);
            this._os.writeH(blind);
            this._os.writeC(33);
            this._os.writeC(4);
        }
        int stun = pw_k6_5;
        if (stun != 0) {
            this._os.writeC(15);
            this._os.writeH(stun);
            this._os.writeC(33);
            this._os.writeC(5);
        }
        int sustain = pw_k6_6;
        if (sustain != 0) {
            this._os.writeC(15);
            this._os.writeH(sustain);
            this._os.writeC(33);
            this._os.writeC(6);
        }
        int addmr = pw_sMr;
        if (addmr != 0) {
            this._os.writeC(15);
            this._os.writeH(addmr);
        }
        int addsp = pw_sSp;
        if (addsp != 0) {
            this._os.writeC(17);
            this._os.writeC(addsp);
        }
        boolean haste = this._item.isHasteItem();
        if (haste) {
            this._os.writeC(18);
        }
        int defense_fire = pw_d4_1;
        if (defense_fire != 0) {
            this._os.writeC(27);
            this._os.writeC(defense_fire);
        }
        int defense_water = pw_d4_2;
        if (defense_water != 0) {
            this._os.writeC(28);
            this._os.writeC(defense_water);
        }
        int defense_wind = pw_d4_3;
        if (defense_wind != 0) {
            this._os.writeC(29);
            this._os.writeC(defense_wind);
        }
        int defense_earth = pw_d4_4;
        if (defense_earth != 0) {
            this._os.writeC(30);
            this._os.writeC(defense_earth);
        }
        if (addexp != 0) {
            this._os.writeC(39);
            this._os.writeS("狩獵經驗值+" + addexp);
        }
        if (pw_drd != 0) {
            this._os.writeC(39);
            this._os.writeS("傷害減免+" + pw_drd);
        }
        if (pw_mHi != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法命中+" + pw_mHi);
        }
        if (drain_hp_rand != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(drain_hp_rand) + "%吸血:" + drain_min_hp + "~" + drain_max_hp);
        }
        if (drain_mp_rand != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(drain_mp_rand) + "%吸魔:" + drain_min_mp + "~" + drain_max_mp);
        }
        if (skill_gfxid != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(skill_rand) + "%特效增傷+" + skill_dmg);
        }
        if (attr_物理格檔 != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(attr_物理格檔) + "%物理格檔");
        }
        if (attr_魔法格檔 != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(attr_魔法格檔) + "%魔法格檔");
        }
        StringBuilder name = new StringBuilder();
        int adduhp = uhp + this.greater()[1];
        if (adduhp != 0) {
            name.append("藥水回復量+" + adduhp + "%");
            if (uhp_num != 0) {
                name.append("+" + uhp_num);
            }
            this._os.writeC(39);
            this._os.writeS(name.toString());
        }
        if (attr_potion_heal != 0) {
            this._os.writeC(39);
            this._os.writeS("藥水回復增量+" + attr_potion_heal);
        }
        if (PVP減傷 != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害減免+" + PVP減傷);
        }
        if (PVP增傷 != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害增加+" + PVP增傷);
        }
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        if (this._statusx) {
            if (!this._item.isTradable()) {
                this._os.writeC(39);
                this._os.writeS("無法交易");
            }
            if (this._item.isCantDelete()) {
                this._os.writeC(39);
                this._os.writeS("無法刪除");
            }
            if (this._item.get_safeenchant() < 0) {
                this._os.writeC(39);
                this._os.writeS("無法強化");
            }
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream weapon() {
        int attr_DmgSmall = 0;
        int attr_DmgLarge = 0;
        int attr_HitModifier = 0;
        int attr_DmgModifier = 0;
        int attr_str = 0;
        int attr_con = 0;
        int attr_dex = 0;
        int attr_int = 0;
        int attr_wis = 0;
        int attr_cha = 0;
        int attr_hp = 0;
        int attr_mp = 0;
        int attr_sp = 0;
        int attr_mr = 0;
        int drain_min_hp = 0;
        int drain_max_hp = 0;
        int drain_hp_rand = 0;
        int attr_hpr = 0;
        int attr_mpr = 0;
        int drain_min_mp = 0;
        int drain_max_mp = 0;
        int drain_mp_rand = 0;
        int skill_gfxid = 0;
        int skill_rand = 0;
        int skill_dmg = 0;
        int attr_魔法格檔 = 0;
        int attr_物理格檔 = 0;
        int PVP增傷 = 0;
        int PVP減傷 = 0;
        int attr_potion_heal = 0;
        L1ItemSpecialAttributeChar attr_char = this._itemInstance.get_ItemAttrName();
        if (attr_char != null) {
            L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
            attr_DmgSmall = attr.get_dmg_small();
            attr_DmgLarge = attr.get_dmg_large();
            attr_HitModifier = attr.get_hitmodifier();
            attr_DmgModifier = attr.get_dmgmodifier();
            attr_str = attr.get_add_str();
            attr_con = attr.get_add_con();
            attr_dex = attr.get_add_dex();
            attr_int = attr.get_add_int();
            attr_wis = attr.get_add_wis();
            attr_cha = attr.get_add_cha();
            attr_hp = attr.get_add_hp();
            attr_mp = attr.get_add_mp();
            attr_sp = attr.get_add_sp();
            attr_mr = attr.get_add_m_def();
            drain_min_hp = attr.get_add_drain_min_hp();
            drain_max_hp = attr.get_add_drain_max_hp();
            drain_hp_rand = attr.get_drain_hp_rand();
            drain_min_mp = attr.get_add_drain_min_mp();
            drain_max_mp = attr.get_add_drain_max_mp();
            drain_mp_rand = attr.get_drain_mp_rand();
            attr_hpr = attr.get_add_hpr();
            attr_mpr = attr.get_add_mpr();
            skill_gfxid = attr.get_add_skill_gfxid();
            skill_rand = attr.get_add_skill_rand();
            skill_dmg = attr.get_add_skill_dmg();
            attr_魔法格檔 = attr.get魔法格檔();
            attr_物理格檔 = attr.get物理格檔();
            PVP增傷 = attr.get_add_pvp_dmg();
            PVP減傷 = attr.get_add_pvp_redmg();
            attr_potion_heal = attr.get_add_potion_heal();
        }
        L1MagicWeapon magicWeapon = this._itemInstance.get_magic_weapon();
        if (magicWeapon != null) {
            this._os.writeC(39);
            StringBuilder name1 = new StringBuilder().append(magicWeapon.getSkillName());
            this._os.writeS(name1.toString());
        }
        L1ItemPower_name power = this._itemInstance.get_power_name();
        this._os.writeC(1);
        this._os.writeC(this._item.getDmgSmall() + attr_DmgSmall);
        this._os.writeC(this._item.getDmgLarge() + attr_DmgLarge);
        this._os.writeC(this._item.getMaterial());
        this._os.writeD(this._itemInstance.getWeight());
        if (this._itemInstance.getEnchantLevel() != 0) {
            this._os.writeC(2);
            this._os.writeC(this._itemInstance.getEnchantLevel());
        }
        if (this._itemInstance.get_durability() != 0) {
            this._os.writeC(3);
            this._os.writeC(this._itemInstance.get_durability());
        }
        if (this._item.isTwohandedWeapon()) {
            this._os.writeC(4);
        }
        if (this._itemInstance.get_time() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this._os.writeC(39);
            this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
        }
        int get_addstr = this._item.get_addstr() + this._itemInstance.getItemStr() + attr_str;
        int get_adddex = this._item.get_adddex() + this._itemInstance.getItemDex() + attr_dex;
        int get_addcon = this._item.get_addcon() + this._itemInstance.getItemCon() + attr_con;
        int get_addwis = this._item.get_addwis() + this._itemInstance.getItemWis() + attr_wis;
        int get_addint = this._item.get_addint() + this._itemInstance.getItemInt() + attr_int;
        int get_addcha = this._item.get_addcha() + this._itemInstance.getItemCha() + attr_cha;
        int get_addhp = this._itemPower.get_addhp() + this._itemInstance.getItemHp() + attr_hp;
        int get_addmp = this._item.get_addmp() + this._itemInstance.getItemMp() + attr_mp;
        int mr = this._itemPower.getMr() + attr_mr;
        int addWeaponSp = this._itemPower.getSp() + attr_sp;
        int addDmgModifier = this._item.getDmgModifier() + attr_DmgModifier;
        int addHitModifier = this._item.getHitModifier() + this._itemInstance.getItemHit() + attr_HitModifier;
        int addmagicdmg = this._item.getMagicDmgModifier();
        int addBowDmgModifier = 0;
        int addBowHitModifier = 0;
        int pw_d4_1 = this._item.get_defense_fire();
        int pw_d4_2 = this._item.get_defense_water();
        int pw_d4_3 = this._item.get_defense_wind();
        int pw_d4_4 = this._item.get_defense_earth();
        int pw_k6_1 = this._item.get_regist_freeze();
        int pw_k6_2 = this._item.get_regist_stone();
        int pw_k6_3 = this._item.get_regist_sleep();
        int pw_k6_4 = this._item.get_regist_blind();
        int pw_k6_5 = this._item.get_regist_stun();
        int pw_k6_6 = this._item.get_regist_sustain();
        int pw_sHpr = this._item.get_addhpr() + this._itemInstance.getItemHpr() + attr_hpr;
        int pw_sMpr = this._itemPower.getMpr() + this._itemInstance.getItemMpr() + attr_mpr;
        int addexp = this._item.getExpPoint();
        int PVPdmgReduction = this._item.getPVPdmgReduction() + PVP減傷;
        int PVPdmg = this._item.getPVPdmg() + PVP增傷;
        int pw_drd = this._itemInstance.getItemReductionDmg();
        int uhp = this._itemInstance.getItemhppotion();
        int uhp_num = 0;
        int WeaponSkillDmg = 0;
        int WeaponSkillChance = 0;
        if (this._itemInstance.get_power_name() != null) {
            pw_sHpr += this._l1power.getItem(power.get_hole_1()).add_hpr;
            pw_sMpr += this._l1power.getItem(power.get_hole_1()).add_mpr;
            get_addstr += this._l1power.getItem(power.get_hole_1()).add_str;
            get_adddex += this._l1power.getItem(power.get_hole_1()).add_dex;
            get_addcon += this._l1power.getItem(power.get_hole_1()).add_con;
            get_addwis += this._l1power.getItem(power.get_hole_1()).add_wis;
            get_addint += this._l1power.getItem(power.get_hole_1()).add_int;
            get_addcha += this._l1power.getItem(power.get_hole_1()).add_cha;
            get_addhp += this._l1power.getItem(power.get_hole_1()).addMaxHP;
            get_addmp += this._l1power.getItem(power.get_hole_1()).addMaxMP;
            addDmgModifier += this._l1power.getItem(power.get_hole_1()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_1()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_1()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_1()).bow_hit_modifier;
            mr += this._l1power.getItem(power.get_hole_1()).m_def;
            addWeaponSp += this._l1power.getItem(power.get_hole_1()).add_sp;
            pw_sHpr += this._l1power.getItem(power.get_hole_2()).add_hpr;
            pw_sMpr += this._l1power.getItem(power.get_hole_2()).add_mpr;
            get_addstr += this._l1power.getItem(power.get_hole_2()).add_str;
            get_adddex += this._l1power.getItem(power.get_hole_2()).add_dex;
            get_addcon += this._l1power.getItem(power.get_hole_2()).add_con;
            get_addwis += this._l1power.getItem(power.get_hole_2()).add_wis;
            get_addint += this._l1power.getItem(power.get_hole_2()).add_int;
            get_addcha += this._l1power.getItem(power.get_hole_2()).add_cha;
            get_addhp += this._l1power.getItem(power.get_hole_2()).addMaxHP;
            get_addmp += this._l1power.getItem(power.get_hole_2()).addMaxMP;
            addDmgModifier += this._l1power.getItem(power.get_hole_2()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_2()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_2()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_2()).bow_hit_modifier;
            mr += this._l1power.getItem(power.get_hole_2()).m_def;
            addWeaponSp += this._l1power.getItem(power.get_hole_2()).add_sp;
            pw_sHpr += this._l1power.getItem(power.get_hole_3()).add_hpr;
            pw_sMpr += this._l1power.getItem(power.get_hole_3()).add_mpr;
            get_addstr += this._l1power.getItem(power.get_hole_3()).add_str;
            get_adddex += this._l1power.getItem(power.get_hole_3()).add_dex;
            get_addcon += this._l1power.getItem(power.get_hole_3()).add_con;
            get_addwis += this._l1power.getItem(power.get_hole_3()).add_wis;
            get_addint += this._l1power.getItem(power.get_hole_3()).add_int;
            get_addcha += this._l1power.getItem(power.get_hole_3()).add_cha;
            get_addhp += this._l1power.getItem(power.get_hole_3()).addMaxHP;
            get_addmp += this._l1power.getItem(power.get_hole_3()).addMaxMP;
            addDmgModifier += this._l1power.getItem(power.get_hole_3()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_3()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_3()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_3()).bow_hit_modifier;
            mr += this._l1power.getItem(power.get_hole_3()).m_def;
            addWeaponSp += this._l1power.getItem(power.get_hole_3()).add_sp;
            pw_sHpr += this._l1power.getItem(power.get_hole_4()).add_hpr;
            pw_sMpr += this._l1power.getItem(power.get_hole_4()).add_mpr;
            get_addstr += this._l1power.getItem(power.get_hole_4()).add_str;
            get_adddex += this._l1power.getItem(power.get_hole_4()).add_dex;
            get_addcon += this._l1power.getItem(power.get_hole_4()).add_con;
            get_addwis += this._l1power.getItem(power.get_hole_4()).add_wis;
            get_addint += this._l1power.getItem(power.get_hole_4()).add_int;
            get_addcha += this._l1power.getItem(power.get_hole_4()).add_cha;
            get_addhp += this._l1power.getItem(power.get_hole_4()).addMaxHP;
            get_addmp += this._l1power.getItem(power.get_hole_4()).addMaxMP;
            addDmgModifier += this._l1power.getItem(power.get_hole_4()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_4()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_4()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_4()).bow_hit_modifier;
            mr += this._l1power.getItem(power.get_hole_4()).m_def;
            addWeaponSp += this._l1power.getItem(power.get_hole_4()).add_sp;
            pw_sHpr += this._l1power.getItem(power.get_hole_5()).add_hpr;
            pw_sMpr += this._l1power.getItem(power.get_hole_5()).add_mpr;
            get_addstr += this._l1power.getItem(power.get_hole_5()).add_str;
            get_adddex += this._l1power.getItem(power.get_hole_5()).add_dex;
            get_addcon += this._l1power.getItem(power.get_hole_5()).add_con;
            get_addwis += this._l1power.getItem(power.get_hole_5()).add_wis;
            get_addint += this._l1power.getItem(power.get_hole_5()).add_int;
            get_addcha += this._l1power.getItem(power.get_hole_5()).add_cha;
            get_addhp += this._l1power.getItem(power.get_hole_5()).addMaxHP;
            get_addmp += this._l1power.getItem(power.get_hole_5()).addMaxMP;
            addDmgModifier += this._l1power.getItem(power.get_hole_5()).dmg_modifier;
            addHitModifier += this._l1power.getItem(power.get_hole_5()).hit_modifier;
            addBowDmgModifier += this._l1power.getItem(power.get_hole_5()).bow_dmg_modifier;
            addBowHitModifier += this._l1power.getItem(power.get_hole_5()).bow_hit_modifier;
            mr += this._l1power.getItem(power.get_hole_5()).m_def;
            addWeaponSp += this._l1power.getItem(power.get_hole_5()).add_sp;
        }
        L1WilliamEnchantOrginal accessoryOrginal = null;
        L1WilliamEnchantOrginal accessoryOrginalOk = null;
        L1WilliamEnchantOrginal[] accessoryOrginalSize = EnchantOrginal.getInstance().getArmorList();
        int j = 0;
        while (j < accessoryOrginalSize.length) {
            accessoryOrginalOk = EnchantOrginal.getInstance().getTemplate(j);
            if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == this._itemInstance.getItem().getItemId() && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal = accessoryOrginalOk;
                break;
            }
            else if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == 0 && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal = accessoryOrginalOk;
                break;
            }
            else if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == 0 && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
                accessoryOrginal = accessoryOrginalOk;
                break;
            }
            else {
                ++j;
            }
        }
        if (accessoryOrginal != null) {
            get_addstr += accessoryOrginal.getAddStr();
            get_adddex += accessoryOrginal.getAddDex();
            get_addcon += accessoryOrginal.getAddCon();
            get_addint += accessoryOrginal.getAddInt();
            get_addwis += accessoryOrginal.getAddWis();
            get_addcha += accessoryOrginal.getAddCha();
            get_addhp += accessoryOrginal.getAddMaxHp();
            get_addmp += accessoryOrginal.getAddMaxMp();
            pw_sHpr += accessoryOrginal.getAddHpr();
            pw_sMpr += accessoryOrginal.getAddMpr();
            addDmgModifier += accessoryOrginal.getAddDmg();
            addHitModifier += accessoryOrginal.getAddHit();
            addBowDmgModifier += accessoryOrginal.getAddBowDmg();
            addBowHitModifier += accessoryOrginal.getAddBowHit();
            pw_drd += accessoryOrginal.getAddDmgReduction();
            mr += accessoryOrginal.getAddMr();
            addWeaponSp += accessoryOrginal.getAddSp();
            PVPdmg += accessoryOrginal.getPVPdmg();
            PVPdmgReduction += accessoryOrginal.getPVPdmgReduction();
            uhp += accessoryOrginal.getPotion_Heal();
            uhp_num += accessoryOrginal.getPotion_Healling();
            WeaponSkillDmg = (int)((double)WeaponSkillDmg + accessoryOrginal.getWeaponSkillDmg());
            WeaponSkillChance += accessoryOrginal.getWeaponSkillChance();
        }
        int safeenchant = this._item.get_safeenchant();
        if (safeenchant >= 0) {
            this._os.writeC(39);
            this._os.writeS("安定值: " + this._item.get_safeenchant());
        }
        if (addHitModifier != 0) {
            this._os.writeC(5);
            this._os.writeC(addHitModifier);
        }
        int BowHit = this._itemInstance.getItemBowHit() + addBowHitModifier;
        if (BowHit != 0) {
            this._os.writeC(39);
            this._os.writeS("遠距離命中+" + BowHit);
        }
        if (addDmgModifier != 0 || this._itemInstance.getItemAttack() != 0) {
            this._os.writeC(6);
            this._os.writeC(addDmgModifier + this._itemInstance.getItemAttack());
        }
        int BowAttack = this._itemInstance.getItemBowAttack() + addBowDmgModifier;
        if (BowAttack != 0) {
            this._os.writeC(39);
            this._os.writeS("遠距離攻擊+" + BowAttack);
        }
        if (addmagicdmg != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法傷害: " + addmagicdmg);
        }
        if (this._itemInstance.getItemMag_Hit() != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法命中: " + this._itemInstance.getItemMag_Hit());
        }
        int addsp = this._itemInstance.getItemSp();
        if (addsp != 0) {
            this._os.writeC(17);
            this._os.writeC(addsp);
        }
        int bit = 0;
        bit |= (this._item.isUseRoyal() ? 1 : 0);
        bit |= (this._item.isUseKnight() ? 2 : 0);
        bit |= (this._item.isUseElf() ? 4 : 0);
        bit |= (this._item.isUseMage() ? 8 : 0);
        bit |= (this._item.isUseDarkelf() ? 16 : 0);
        bit |= (this._item.isUseDragonknight() ? 32 : 0);
        bit |= (this._item.isUseIllusionist() ? 64 : 0);
        this._os.writeC(7);
        this._os.writeC(bit);
        if (this._itemInstance.getItemId() == 126 || this._itemInstance.getItemId() == 127 || this._itemInstance.getItemId() == 259 || this._itemInstance.getItemId() == 305 || this._itemInstance.getItemId() == 310 || this._itemInstance.getItemId() == 315) {
            this._os.writeC(16);
        }
        if (this._itemInstance.getItemId() == 262 || this._itemInstance.getItemId() == 410157 || this._itemInstance.getItemId() == 12 || this._itemInstance.getItemId() == 410117 || this._itemInstance.getItemId() == 410164) {
            this._os.writeC(34);
        }
        if (get_addstr != 0) {
            this._os.writeC(8);
            this._os.writeC(get_addstr);
        }
        if (get_adddex != 0) {
            this._os.writeC(9);
            this._os.writeC(get_adddex);
        }
        if (get_addcon != 0) {
            this._os.writeC(10);
            this._os.writeC(get_addcon);
        }
        if (get_addwis != 0) {
            this._os.writeC(11);
            this._os.writeC(get_addwis);
        }
        if (get_addint != 0) {
            this._os.writeC(12);
            this._os.writeC(get_addint);
        }
        if (get_addcha != 0) {
            this._os.writeC(13);
            this._os.writeC(get_addcha);
        }
        if (get_addhp != 0) {
            this._os.writeC(14);
            this._os.writeH(get_addhp);
        }
        if (get_addmp != 0) {
            if (get_addmp <= 120) {
                this._os.writeC(32);
                this._os.writeC(get_addmp);
            }
            else {
                this._os.writeC(39);
                this._os.writeS("魔力上限+" + get_addmp);
            }
        }
        if (pw_sHpr != 0) {
            this._os.writeC(37);
            this._os.writeC(pw_sHpr);
        }
        if (pw_sMpr != 0) {
            this._os.writeC(38);
            this._os.writeC(pw_sMpr);
        }
        if (mr != 0) {
            this._os.writeC(15);
            this._os.writeH(mr);
        }
        if (addWeaponSp != 0) {
            this._os.writeC(17);
            this._os.writeC(addWeaponSp);
        }
        if (this._item.isHasteItem()) {
            this._os.writeC(18);
        }
        if (pw_d4_1 != 0) {
            this._os.writeC(27);
            this._os.writeC(pw_d4_1);
        }
        if (pw_d4_2 != 0) {
            this._os.writeC(28);
            this._os.writeC(pw_d4_2);
        }
        if (pw_d4_3 != 0) {
            this._os.writeC(29);
            this._os.writeC(pw_d4_3);
        }
        if (pw_d4_4 != 0) {
            this._os.writeC(30);
            this._os.writeC(pw_d4_4);
        }
        if (pw_k6_1 != 0) {
            this._os.writeC(15);
            this._os.writeH(pw_k6_1);
            this._os.writeC(33);
            this._os.writeC(1);
        }
        if (pw_k6_2 != 0) {
            this._os.writeC(15);
            this._os.writeH(pw_k6_2);
            this._os.writeC(33);
            this._os.writeC(2);
        }
        if (pw_k6_3 != 0) {
            this._os.writeC(15);
            this._os.writeH(pw_k6_3);
            this._os.writeC(33);
            this._os.writeC(3);
        }
        if (pw_k6_4 != 0) {
            this._os.writeC(15);
            this._os.writeH(pw_k6_4);
            this._os.writeC(33);
            this._os.writeC(4);
        }
        if (pw_k6_5 != 0) {
            this._os.writeC(15);
            this._os.writeH(pw_k6_5);
            this._os.writeC(33);
            this._os.writeC(5);
        }
        if (pw_k6_6 != 0) {
            this._os.writeC(15);
            this._os.writeH(pw_k6_6);
            this._os.writeC(33);
            this._os.writeC(6);
        }
        if (addexp != 0) {
            this._os.writeC(39);
            this._os.writeS("狩獵經驗值 +" + addexp);
        }
        if (uhp != 0) {
            this._os.writeC(39);
            this._os.writeS("藥水回復量+" + uhp + "%" + uhp_num);
        }
        if (attr_potion_heal != 0) {
            this._os.writeC(39);
            this._os.writeS("藥水回復增量+" + attr_potion_heal);
        }
        int dr = this._itemInstance.getItemprobability();
        L1WeaponSkill weaponSkill = WeaponSkillTable.get().getTemplate(this._itemInstance.getItemId());
        if (weaponSkill != null) {
            int lv = 0;
            if (weaponSkill.getweapon_lv() != 0 && this._itemInstance.getEnchantLevel() == weaponSkill.getweapon_lv()) {
                lv = 1;
            }
            else if (weaponSkill.getweapon_lv() != 0 && this._itemInstance.getEnchantLevel() > weaponSkill.getweapon_lv()) {
                lv = this._itemInstance.getEnchantLevel() - weaponSkill.getweapon_lv() + 1;
            }
            if (weaponSkill.getProbability() + WeaponSkillChance != 0) {
                this._os.writeC(39);
                this._os.writeS("魔法發動率:" + Math.min(weaponSkill.getProbability() + WeaponSkillChance + dr + lv, 100) + "%");
            }
        }
        if (WeaponSkillDmg != 0) {
            this._os.writeC(39);
            this._os.writeS("魔法武器傷害*:" + WeaponSkillDmg + "%");
        }
        if (pw_drd != 0) {
            this._os.writeC(39);
            this._os.writeS("物理減傷+" + this._itemInstance.getItemReductionDmg());
        }
        if (this._item.getpenetrate() != 0) {
            this._os.writeC(39);
            this._os.writeS("貫穿效果");
        }
        if (this._item.getNoweaponRedmg() != 0) {
            this._os.writeC(39);
            this._os.writeS("無視傷害減免+" + this._item.getNoweaponRedmg());
        }
        if (this._item.getaddStunLevel() != 0) {
            this._os.writeC(39);
            this._os.writeS("昏迷命中+" + this._item.getaddStunLevel());
        }
        if (PVPdmgReduction != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害減免+" + PVPdmgReduction);
        }
        if (PVPdmg != 0) {
            this._os.writeC(39);
            this._os.writeS("PVP傷害增加+" + PVPdmg);
        }
        if (drain_hp_rand != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(drain_hp_rand) + "%吸血:" + drain_min_hp + "~" + drain_max_hp);
        }
        if (drain_mp_rand != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(drain_mp_rand) + "%吸魔:" + drain_min_mp + "~" + drain_max_mp);
        }
        if (skill_gfxid != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(skill_rand) + "%特效增傷+" + skill_dmg);
        }
        if (attr_物理格檔 != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(attr_物理格檔) + "%物理格檔");
        }
        if (attr_魔法格檔 != 0) {
            this._os.writeC(39);
            this._os.writeS(String.valueOf(attr_魔法格檔) + "%魔法格檔");
        }
        if (this._statusx) {
            if (!this._item.isTradable()) {
                this._os.writeC(39);
                this._os.writeS("無法交易");
            }
            if (this._item.isCantDelete()) {
                this._os.writeC(39);
                this._os.writeS("無法刪除");
            }
            if (this._item.get_safeenchant() < 0) {
                this._os.writeC(39);
                this._os.writeS("無法強化");
            }
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream etcitem() {
        if (this._item.getItemId() == 40312) {
            this._os.writeC(39);
            this._os.writeS("旅館編號:" + this._itemInstance.getInnKeyName());
            this._os.writeC(39);
            this._os.writeS("到期時間:(" + this._itemInstance.getDueTime() + ")");
        }
        if (this._item.getItemId() == 82503) {
            this._os.writeC(39);
            this._os.writeS("訓練所編號:" + this._itemInstance.getKeyId());
            this._os.writeC(39);
            this._os.writeS("到期時間:(" + this._itemInstance.getDueTime() + ")");
        }
        if (this._item.getItemId() == 82504) {
            String npcname = NpcTable.get().getNpcName(this._itemInstance.getInnNpcId());
            this._os.writeC(39);
            this._os.writeS(npcname);
            this._os.writeC(39);
            this._os.writeS("副本編號:" + this._itemInstance.getKeyId());
            this._os.writeC(39);
            this._os.writeS("到期時間:(" + this._itemInstance.getDueTime() + ")");
        }
        this._os.writeC(23);
        this._os.writeC(this._item.getMaterial());
        this._os.writeD(this._itemInstance.getWeight());
        if (this._itemInstance.get_time() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this._os.writeC(39);
            this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
        }
        if (this._statusx) {
            if (!this._item.isTradable()) {
                this._os.writeC(39);
                this._os.writeS("無法交易");
            }
            if (this._item.isCantDelete()) {
                this._os.writeC(39);
                this._os.writeS("無法刪除");
            }
            if (this._item.get_safeenchant() < 0) {
                this._os.writeC(39);
                this._os.writeS("無法強化");
            }
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream petarmor(L1PetItem petItem) {
        this._os.writeC(19);
        int ac = petItem.getAddAc();
        if (ac < 0) {
            ac = Math.abs(ac);
        }
        this._os.writeC(ac);
        if (this._itemInstance.get_time() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this._os.writeC(39);
            this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
        }
        this._os.writeC(this._item.getMaterial());
        this._os.writeC(-1);
        this._os.writeD(this._itemInstance.getWeight());
        if (petItem.getHitModifier() != 0) {
            this._os.writeC(5);
            this._os.writeC(petItem.getHitModifier());
        }
        if (petItem.getDamageModifier() != 0) {
            this._os.writeC(6);
            this._os.writeC(petItem.getDamageModifier());
        }
        if (petItem.isHigher()) {
            this._os.writeC(7);
            this._os.writeC(128);
        }
        if (petItem.getAddStr() != 0) {
            this._os.writeC(8);
            this._os.writeC(petItem.getAddStr());
        }
        if (petItem.getAddDex() != 0) {
            this._os.writeC(9);
            this._os.writeC(petItem.getAddDex());
        }
        if (petItem.getAddCon() != 0) {
            this._os.writeC(10);
            this._os.writeC(petItem.getAddCon());
        }
        if (petItem.getAddWis() != 0) {
            this._os.writeC(11);
            this._os.writeC(petItem.getAddWis());
        }
        if (petItem.getAddInt() != 0) {
            this._os.writeC(12);
            this._os.writeC(petItem.getAddInt());
        }
        if (petItem.getAddHp() != 0) {
            this._os.writeC(14);
            this._os.writeH(petItem.getAddHp());
        }
        if (petItem.getAddMp() != 0) {
            this._os.writeC(32);
            this._os.writeC(petItem.getAddMp());
        }
        if (petItem.getAddMr() != 0) {
            this._os.writeC(15);
            this._os.writeH(petItem.getAddMr());
        }
        if (petItem.getAddSp() != 0) {
            this._os.writeC(17);
            this._os.writeC(petItem.getAddSp());
        }
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        ArrayList<String> as = new ArrayList();
        try {
            for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
                this._os.writeC(39);
                this._os.writeS(s);
            }
        }
        finally {
            as.clear();
        }
        return this._os;
    }
    
    private BinaryOutputStream petweapon(L1PetItem petItem) {
        this._os.writeC(1);
        this._os.writeC(0);
        this._os.writeC(0);
        this._os.writeC(this._item.getMaterial());
        this._os.writeD(this._itemInstance.getWeight());
        if (petItem.getHitModifier() != 0) {
            this._os.writeC(5);
            this._os.writeC(petItem.getHitModifier());
        }
        if (petItem.getDamageModifier() != 0) {
            this._os.writeC(6);
            this._os.writeC(petItem.getDamageModifier());
        }
        if (petItem.isHigher()) {
            this._os.writeC(7);
            this._os.writeC(128);
        }
        if (petItem.getAddStr() != 0) {
            this._os.writeC(8);
            this._os.writeC(petItem.getAddStr());
        }
        if (petItem.getAddDex() != 0) {
            this._os.writeC(9);
            this._os.writeC(petItem.getAddDex());
        }
        if (petItem.getAddCon() != 0) {
            this._os.writeC(10);
            this._os.writeC(petItem.getAddCon());
        }
        if (petItem.getAddWis() != 0) {
            this._os.writeC(11);
            this._os.writeC(petItem.getAddWis());
        }
        if (petItem.getAddInt() != 0) {
            this._os.writeC(12);
            this._os.writeC(petItem.getAddInt());
        }
        if (petItem.getAddHp() != 0) {
            this._os.writeC(14);
            this._os.writeH(petItem.getAddHp());
        }
        if (petItem.getAddMp() != 0) {
            this._os.writeC(32);
            this._os.writeC(petItem.getAddMp());
        }
        if (petItem.getAddMr() != 0) {
            this._os.writeC(15);
            this._os.writeH(petItem.getAddMr());
        }
        if (petItem.getAddSp() != 0) {
            this._os.writeC(17);
            this._os.writeC(petItem.getAddSp());
        }
        if (!this._item.isTradable()) {
            this._os.writeC(39);
            this._os.writeS("無法交易");
        }
        if (this._item.isCantDelete()) {
            this._os.writeC(39);
            this._os.writeS("無法刪除");
        }
        if (this._item.get_safeenchant() < 0) {
            this._os.writeC(39);
            this._os.writeS("無法強化");
        }
        return this._os;
    }
    
    private int[] greater() {
        int level = this._itemInstance.getEnchantLevel();
        if (level < 0) {
            level = 0;
        }
        int[] rint = new int[13];
        switch (this._itemInstance.getItem().get_greater()) {
        }
        return rint;
    }
}

package com.lineage.william;

import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;

public class L1W
{
    private static Random _random;
    private int _id;
    private boolean _activity;
    private int _check_MapId;
    private int _check_random;
    private int _check_type;
    private int _item_dex;
    private int _item_int;
    private int _item_con;
    private int _item_wis;
    private int _item_cha;
    private int _item_sp;
    private int _item_hp;
    private int _item_mp;
    
    static {
        L1W._random = new Random();
    }
    
    public L1W(int id, boolean activity, int check_MapId, int check_random, int check_type, int item_str, int item_dex, int item_int, int item_con, int item_wis, int item_cha, int item_sp, int item_hp, int item_mp) {
        this._id = id;
        this._activity = activity;
        this._check_MapId = check_MapId;
        this._check_random = check_random;
        this._check_type = check_type;
        this._item_dex = item_dex;
        this._item_int = item_int;
        this._item_con = item_con;
        this._item_wis = item_wis;
        this._item_cha = item_cha;
        this._item_sp = item_sp;
        this._item_hp = item_hp;
        this._item_mp = item_mp;
    }
    
    public int get_id() {
        return this._id;
    }
    
    public boolean isActivity() {
        return this._activity;
    }
    
    public void set_Activity(boolean i) {
        this._activity = i;
    }
    
    public int get_check_MapId() {
        return this._check_MapId;
    }
    
    public int get_check_random() {
        return this._check_random;
    }
    
    public int get_check_type() {
        return this._check_type;
    }
    
    public int get_item_dex() {
        return this._item_dex;
    }
    
    public int get_item_int() {
        return this._item_int;
    }
    
    public int get_item_con() {
        return this._item_con;
    }
    
    public int get_item_wis() {
        return this._item_wis;
    }
    
    public int get_item_cha() {
        return this._item_cha;
    }
    
    public int get_item_sp() {
        return this._item_sp;
    }
    
    public int get_item_hp() {
        return this._item_hp;
    }
    
    public int get_item_mp() {
        return this._item_mp;
    }
    
    public static void check_Task_For_Level(L1PcInstance pc, L1ItemInstance item, int type) {
        L1W W = null;
        if (pc.getMapId() != W.get_check_MapId() && W.isActivity() && L1W._random.nextInt(100) + 1 <= W.get_check_random()) {
            switch (type) {
                case 1: {
                    item.setItemStr(L1W._random.nextInt(3) + 1);
                    break;
                }
                case 2: {
                    item.setItemDex(L1W._random.nextInt(3) + 1);
                    item.setItemStr(L1W._random.nextInt(3) + 1);
                    break;
                }
                case 3: {
                    item.setItemDex(L1W._random.nextInt(3) + 1);
                    item.setItemStr(L1W._random.nextInt(3) + 1);
                    item.setItemInt(L1W._random.nextInt(3) + 1);
                    break;
                }
            }
            pc.sendPackets(new S_ItemStatus(item));
            CharItemsTable cit = new CharItemsTable();
            try {
                cit.updateItemStr(item);
                cit.updateItemDex(item);
                cit.updateItemInt(item);
                cit.updateItemHp(item);
                cit.updateItemMp(item);
                cit.updateItemAttack(item);
                cit.updateItemprobability(item);
                cit.updateItemReductionDmg(item);
                pc.save();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

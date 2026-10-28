package com.lineage.server.model;

import java.util.List;
import com.lineage.echo.ClientExecutor;
import java.util.Iterator;
import com.lineage.list.OnlineUser;
import com.lineage.server.datatables.sql.DwarfTable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.templates.L1Item;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.lock.DwarfReading;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public class L1DwarfInventory extends L1Inventory
{
    public static final Log _log;
    private static final long serialVersionUID = 1L;
    private final L1PcInstance _owner;
    
    static {
        _log = LogFactory.getLog(L1DwarfInventory.class);
    }
    
    public L1DwarfInventory(L1PcInstance owner) {
        this._owner = owner;
    }
    
    @Override
    public void loadItems() {
        try {
            CopyOnWriteArrayList<L1ItemInstance> items = DwarfReading.get().loadItems(this._owner.getAccountName());
            if (items != null) {
                this._items = (List)items;
            }
        }
        catch (Exception e) {
            L1DwarfInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void insertItem(L1ItemInstance item) {
        if (item.getCount() <= 0L) {
            return;
        }
        else {
            try {
                DwarfReading.get().insertItem(this._owner.getAccountName(), item);
            }
            catch (Exception e) {
                L1DwarfInventory._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    @Override
    public void updateItem(L1ItemInstance item) {
        try {
            DwarfReading.get().updateItem(item);
        }
        catch (Exception e) {
            L1DwarfInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void deleteItem(L1ItemInstance item) {
        try {
            this._items.remove(item);
            DwarfReading.get().deleteItem(this._owner.getAccountName(), item);
            World.get().removeObject(item);
        }
        catch (Exception e) {
            L1DwarfInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onRemoveItem(L1ItemInstance item) {
        this._items.remove(item);
    }
    
    public static void present(int minlvl, int maxlvl, int itemid, int count) throws Exception {
        L1Item temp = ItemTable.get().getTemplate(itemid);
        if (temp == null) {
            return;
        }
        else {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("SELECT distinct(account_name) as account_name FROM characters WHERE level between ? and ?");
                pstm.setInt(1, minlvl);
                pstm.setInt(2, maxlvl);
                rs = pstm.executeQuery();
                ArrayList<String> accountList = new ArrayList();
                while (rs.next()) {
                    accountList.add(rs.getString("account_name"));
                }
                present(accountList, itemid, count);
            }
            catch (SQLException e) {
                L1DwarfInventory._log.error(e.getLocalizedMessage(), e);
                throw e;
            }
            finally {
                SQLUtil.close(rs);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
            return;
        }
    }
    
    private static void present(ArrayList<String> accountList, int itemid, int count) throws Exception {
        L1Item temp = ItemTable.get().getTemplate(itemid);
        if (temp == null) {
            throw new Exception("指定的道具編號不存在。");
        }
        else {
            Connection con = null;
            PreparedStatement ps = null;
            try {
                con = DatabaseFactory.get().getConnection();
                con.setAutoCommit(false);
                for (String account : accountList) {
                    if (temp.isStackable()) {
                        L1ItemInstance item = ItemTable.get().createItem(itemid);
                        item.setCount((long)count);
                        item.setBless(item.getItem().getBless());
                        DwarfTable.addItem(account, item);
                        ps = con.prepareStatement("INSERT INTO `character_warehouse` SET `id`=?,`account_name`=?,`item_id`= ?,`item_name`=?,`count`=?,`is_equipped`=0,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
                        int i = 0;
                        ps.setInt(++i, item.getId());
                        ps.setString(++i, account);
                        ps.setInt(++i, item.getItemId());
                        ps.setString(++i, item.getItem().getName());
                        ps.setLong(++i, item.getCount());
                        ps.setInt(++i, item.getEnchantLevel());
                        ps.setInt(++i, item.isIdentified() ? 1 : 0);
                        ps.setInt(++i, item.get_durability());
                        ps.setInt(++i, item.getChargeCount());
                        ps.setInt(++i, item.getRemainingTime());
                        if (item.getLastUsed() != null) {
                            System.out.println(item.getLastUsed().getTime());
                        }
                        ps.setTimestamp(++i, item.getLastUsed());
                        ps.setInt(++i, item.getBless());
                        ps.setInt(++i, item.getAttrEnchantKind());
                        ps.setInt(++i, item.getAttrEnchantLevel());
                        ps.setInt(++i, item.getItemAttack());
                        ps.setInt(++i, item.getItemBowAttack());
                        ps.setInt(++i, item.getItemHit());
                        ps.setInt(++i, item.getItemBowHit());
                        ps.setInt(++i, item.getItemReductionDmg());
                        ps.setInt(++i, item.getItemSp());
                        ps.setInt(++i, item.getItemprobability());
                        ps.setInt(++i, item.getItemStr());
                        ps.setInt(++i, item.getItemDex());
                        ps.setInt(++i, item.getItemInt());
                        ps.setInt(++i, item.getItemCon());
                        ps.setInt(++i, item.getItemCha());
                        ps.setInt(++i, item.getItemWis());
                        ps.setInt(++i, item.getItemHp());
                        ps.setInt(++i, item.getItemMp());
                        ps.setInt(++i, item.getItemMr());
                        ps.setInt(++i, item.getItemAc());
                        ps.setInt(++i, item.getItemMag_Red());
                        ps.setInt(++i, item.getItemMag_Hit());
                        ps.setInt(++i, item.getItemDg());
                        ps.setInt(++i, item.getItemistSustain());
                        ps.setInt(++i, item.getItemistStun());
                        ps.setInt(++i, item.getItemistStone());
                        ps.setInt(++i, item.getItemistSleep());
                        ps.setInt(++i, item.getItemistFreeze());
                        ps.setInt(++i, item.getItemistBlind());
                        ps.setInt(++i, item.getItemArmorType());
                        ps.setInt(++i, item.getItemArmorLv());
                        ps.setInt(++i, item.getskilltype());
                        ps.setInt(++i, item.getskilltypelv());
                        ps.setInt(++i, item.getItemType());
                        ps.setInt(++i, item.getItemHpr());
                        ps.setInt(++i, item.getItemMpr());
                        ps.setInt(++i, item.getItemhppotion());
                        ps.setInt(++i, item.getGamNo());
                        ps.setInt(++i, item.getGamNpcId());
                        ps.setString(++i, item.getStarNpcId());
                        ps.setString(++i, item.getraceGamNo());
                        ps.execute();
                    }
                    ClientExecutor cl = OnlineUser.get().get(account);
                    if (cl != null) {
                        L1PcInstance tgpc = cl.getActiveChar();
                        if (tgpc != null) {
                            tgpc.getDwarfInventory().loadItems();
                        }
                        else {
                            continue;
                        }
                    }
                }
                con.commit();
                con.setAutoCommit(true);
            }
            catch (SQLException e) {
                try {
                    con.rollback();
                }
                catch (SQLException ex) {}
                L1DwarfInventory._log.error(e.getLocalizedMessage(), e);
                throw new Exception(".present在處理中發生錯誤。");
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(con);
            }
            return;
        }
    }
}

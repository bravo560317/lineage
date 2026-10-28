package com.lineage.server.templates;

import java.sql.Timestamp;
import com.lineage.DatabaseFactoryLogin;
import java.util.Collection;
import com.lineage.server.world.World;
import com.lineage.server.datatables.lock.ServerCnInfoReading;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.server.datatables.lock.DwarfShopReading;
import com.lineage.data.npc.shop.Npc_ShopX;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.timecontroller.event.GamblingTime;
import com.lineage.server.datatables.lock.GamblingReading;
import com.lineage.data.event.GamblingSet;
import com.lineage.server.datatables.ShopCnTable;
import com.lineage.server.datatables.ItemPowerUpdateTable;
import com.add.system.L1FireCrystal;
import com.add.system.L1FireSmithCrystalTable;
import java.util.Iterator;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.utils.RangeInt;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ShopTable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.ListMapUtil;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1IllusoryInstance;
import com.lineage.data.event.gambling.GamblingNpc;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Map;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public class L1PcOtherList
{
    private static final Log _log;
    private L1PcInstance _pc;
    public Map<Integer, L1ItemInstance> DELIST;
    private Map<Integer, L1ShopItem> _cnList;
    private Map<Integer, L1ItemInstance> _cnSList;
    private Map<Integer, GamblingNpc> _gamList;
    private Map<Integer, L1Gambling> _gamSellList;
    private Map<Integer, L1IllusoryInstance> _illusoryList;
    private Map<Integer, L1TeleportLoc> _teleport;
    private Map<Integer, Integer> _uplevelList;
    private Map<Integer, String[]> _shiftingList;
    private Map<Integer, L1ItemInstance> _sitemList;
    private Map<Integer, Integer> _sitemList2;
    public Map<Integer, L1Quest> QUESTMAP;
    public Map<Integer, L1ShopS> SHOPXMAP;
    public ArrayList<Integer> ATKNPC;
    private int[] _is;
    public Map<Integer, int[]> EZPAYLIST;
    public Map<Integer, int[]> SHOPLIST;
    
    static {
        _log = LogFactory.getLog(L1PcOtherList.class);
    }
    
    public L1PcOtherList(L1PcInstance pc) {
        this._pc = pc;
        this.DELIST = new HashMap();
        this._cnList = new HashMap();
        this._cnSList = new HashMap();
        this._gamList = new HashMap();
        this._gamSellList = new HashMap();
        this._illusoryList = new HashMap();
        this._teleport = new HashMap();
        this._uplevelList = new HashMap();
        this._shiftingList = new HashMap();
        this._sitemList = new HashMap();
        this._sitemList2 = new HashMap();
        this.QUESTMAP = new HashMap();
        this.SHOPXMAP = new HashMap();
        this.ATKNPC = new ArrayList();
        this.EZPAYLIST = new HashMap();
        this.SHOPLIST = new HashMap();
    }
    
    public void clearAll() {
        try {
            ListMapUtil.clear(this.DELIST);
            ListMapUtil.clear(this._cnList);
            ListMapUtil.clear(this._cnSList);
            ListMapUtil.clear(this._gamList);
            ListMapUtil.clear(this._gamSellList);
            ListMapUtil.clear(this._illusoryList);
            ListMapUtil.clear(this._teleport);
            ListMapUtil.clear(this._uplevelList);
            ListMapUtil.clear(this._shiftingList);
            ListMapUtil.clear(this._sitemList);
            ListMapUtil.clear(this._sitemList2);
            ListMapUtil.clear(this.QUESTMAP);
            ListMapUtil.clear(this.SHOPXMAP);
            ListMapUtil.clear(this.ATKNPC);
            ListMapUtil.clear(this.EZPAYLIST);
            ListMapUtil.clear(this.SHOPLIST);
            this.DELIST = null;
            this._cnList = null;
            this._cnSList = null;
            this._gamList = null;
            this._gamSellList = null;
            this._illusoryList = null;
            this._teleport = null;
            this._uplevelList = null;
            this._shiftingList = null;
            this._sitemList = null;
            this._sitemList2 = null;
            this.QUESTMAP = null;
            this.SHOPXMAP = null;
            this.ATKNPC = null;
            this.EZPAYLIST = null;
            this.SHOPLIST = null;
            this._is = null;
            this._pc = null;
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public Map<Integer, Integer> get_sitemList2() {
        return this._sitemList2;
    }
    
    public void add_sitemList2(Integer key, Integer value) {
        this._sitemList2.put(key, value);
    }
    
    public void clear_sitemList2() {
        this._sitemList2.clear();
    }
    
    public Map<Integer, L1ItemInstance> get_sitemList() {
        return this._sitemList;
    }
    
    public void add_sitemList(Integer key, L1ItemInstance value) {
        this._sitemList.put(key, value);
    }
    
    public void clear_sitemList() {
        this._sitemList.clear();
    }
    
    public Map<Integer, String[]> get_shiftingList() {
        return this._shiftingList;
    }
    
    public void add_shiftingList(Integer key, String[] value) {
        this._shiftingList.put(key, value);
    }
    
    public void remove_shiftingList(Integer key) {
        this._shiftingList.remove(key);
    }
    
    public void set_shiftingList() {
        try {
            this._shiftingList.clear();
            Connection conn = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                conn = DatabaseFactory.get().getConnection();
                pstm = conn.prepareStatement("SELECT * FROM `characters` WHERE `account_name`=?");
                pstm.setString(1, this._pc.getAccountName());
                rs = pstm.executeQuery();
                int key = 0;
                while (rs.next()) {
                    int objid = rs.getInt("objid");
                    String name = rs.getString("char_name");
                    if (!name.equalsIgnoreCase(this._pc.getName())) {
                        ++key;
                        this.add_shiftingList(key, new String[] { String.valueOf(objid), name });
                    }
                }
            }
            catch (Exception e) {
                L1PcOtherList._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(rs);
                SQLUtil.close(pstm);
                SQLUtil.close(conn);
            }
        }
        catch (Exception e2) {
            L1PcOtherList._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    public Map<Integer, L1IllusoryInstance> get_illusoryList() {
        return this._illusoryList;
    }
    
    public void addIllusoryList(Integer key, L1IllusoryInstance value) {
        this._illusoryList.put(key, value);
    }
    
    public void removeIllusoryList(Integer key) {
        try {
            if (this._illusoryList == null) {
                return;
            }
            else if (this._illusoryList.get(key) != null) {
                this._illusoryList.remove(key);
            }
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void teleport(HashMap<Integer, L1TeleportLoc> teleportMap) {
        try {
            ListMapUtil.clear(this._teleport);
            this._teleport.putAll(teleportMap);
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public Map<Integer, L1TeleportLoc> teleportMap() {
        return this._teleport;
    }
    
    public void sellall(Map<Integer, Integer> sellallMap) {
        try {
            int getprice = 0;
            int totalprice = 0;
            for (Integer integer : sellallMap.keySet()) {
                L1ItemInstance item = this._pc.getInventory().getItem(integer);
                if (item != null) {
                    int key = item.getItemId();
                    int price = ShopTable.get().getPrice(key);
                    Integer count = sellallMap.get(integer);
                    if (price >= 200000 && count > 9999) {
                        this._pc.sendPackets(new S_SystemMessage("販賣數量無法超過 9999個。"));
                        return;
                    }
                    else {
                        totalprice += price * count;
                        if (!RangeInt.includes(totalprice, 0, 2000000000)) {
                            this._pc.sendPackets(new S_SystemMessage("總共販賣價格無法超過 20億金幣。"));
                            return;
                        }
                        else {
                            long remove = this._pc.getInventory().removeItem(integer, (long)(int)count);
                            if (remove == (long)(int)count) {
                                getprice += price * count;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            if (getprice > 0) {
                L1ItemInstance item2 = ItemTable.get().createItem(40308);
                item2.setCount((long)getprice);
                this.createNewItem(item2);
            }
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void sellforfirecrystal(Map<Integer, Integer> FCMap) {
        try {
            int allcount = 0;
            Iterator<Integer> iterator = FCMap.keySet().iterator();
            while (iterator.hasNext()) {
                int objid = (Integer)iterator.next();
                L1ItemInstance item = this._pc.getInventory().getItem(objid);
                if (item != null) {
                    int key = item.getItemId();
                    if (item.getBless() == 0) {
                        key = item.getItemId() - 100000;
                    }
                    else if (item.getBless() == 2) {
                        key = item.getItemId() - 200000;
                    }
                    L1FireCrystal firecrystal = L1FireSmithCrystalTable.get().getTemplate(key);
                    int crystalcount = firecrystal.get_CrystalCount(item);
                    int count = FCMap.get((Integer)objid);
                    long remove = this._pc.getInventory().removeItem(objid, (long)count);
                    if (remove == (long)count) {
                        allcount += crystalcount * count;
                    }
                    else {
                        continue;
                    }
                }
            }
            if (allcount > 0) {
                L1ItemInstance item2 = ItemTable.get().createItem(80029);
                item2.setCount((long)allcount);
                this.createNewItem(item2);
            }
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void sellcnitem(Map<Integer, Integer> cnsellMap) {
        try {
            int allprice = 0;
            ArrayList<Integer> uplist = ItemPowerUpdateTable.get().get_updeatitemidlist();
            Iterator<Integer> iterator = cnsellMap.keySet().iterator();
            while (iterator.hasNext()) {
                int objid = (Integer)iterator.next();
                L1ItemInstance item = this._pc.getInventory().getItem(objid);
                if (item != null) {
                    int key = item.getItemId();
                    int price = 0;
                    int type_id = ItemPowerUpdateTable.get().get_original_type(key);
                    int original_itemid = ItemPowerUpdateTable.get().get_original_itemid(type_id);
                    if (uplist.contains((Integer)key)) {
                        price = ShopCnTable.get().getPrice(original_itemid);
                    }
                    else {
                        price = ShopCnTable.get().getPrice(key);
                    }
                    int count = cnsellMap.get((Integer)objid);
                    long remove = this._pc.getInventory().removeItem(objid, (long)count);
                    if (remove == (long)count) {
                        allprice += price * count;
                    }
                    else {
                        continue;
                    }
                }
            }
            if (allprice > 0) {
                L1ItemInstance item2 = ItemTable.get().createItem(44070);
                item2.setCount((long)allprice);
                this.createNewItem(item2);
            }
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void clear() {
        try {
            ListMapUtil.clear(this._cnList);
            ListMapUtil.clear(this._gamList);
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void set_gamSellList(Map<Integer, L1Gambling> sellList) {
        try {
            ListMapUtil.clear(this._gamSellList);
            this._gamSellList.putAll(sellList);
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void get_sellGam(int objid, int count) {
        try {
            L1Gambling element = this._gamSellList.get((Integer)objid);
            if (element == null) {
                return;
            }
            else {
                long countx = (long)(element.get_rate() * (double)GamblingSet.GAMADENA) * (long)count;
                long remove = this._pc.getInventory().removeItem(objid, (long)count);
                if (remove == (long)count) {
                    int outcount = element.get_outcount() - count;
                    if (outcount < 0) {
                        return;
                    }
                    else {
                        element.set_outcount(outcount);
                        GamblingReading.get().updateGambling(element.get_id(), outcount);
                        L1ItemInstance item = ItemTable.get().createItem(GamblingSet.ADENAITEM);
                        item.setCount(countx);
                        this.createNewItem(item);
                    }
                }
            }
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void add_gamList(GamblingNpc element, int index) {
        this._gamList.put(new Integer(index), element);
    }
    
    public void get_buyGam(Map<Integer, Integer> gamMap) {
        try {
            for (Integer integer : gamMap.keySet()) {
                int index = integer;
                int count = gamMap.get(integer);
                this.get_gamItem(index, count);
            }
            ListMapUtil.clear(this._gamList);
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void get_gamItem(int index, int count) {
        try {
            if (count <= 0) {
                return;
            }
            else {
                GamblingNpc element = this._gamList.get((Integer)index);
                if (element == null) {
                    return;
                }
                else {
                    int npcid = element.get_npc().getNpcId();
                    int no = GamblingTime.get_gamblingNo();
                    long adena = (long)(GamblingSet.GAMADENA * count);
                    long srcCount = this._pc.getInventory().countItems(GamblingSet.ADENAITEM);
                    if (srcCount >= adena) {
                        L1ItemInstance item = ItemTable.get().createItem(40309);
                        if (this._pc.getInventory().checkAddItem(item, (long)count) == 0) {
                            this._pc.getInventory().consumeItem(GamblingSet.ADENAITEM, adena);
                            item.setCount((long)count);
                            item.setraceGamNo(String.valueOf(no) + "-" + npcid);
                            this.createNewItem(item);
                            element.add_adena(adena);
                        }
                        else {
                            this._pc.sendPackets(new S_ServerMessage(270));
                        }
                    }
                    else {
                        L1Item item2 = ItemTable.get().getTemplate(GamblingSet.ADENAITEM);
                        long nc = adena - srcCount;
                        this._pc.sendPackets(new S_ServerMessage(337, item2.getNameId() + "(" + nc + ")"));
                    }
                }
            }
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void add_cnSList(L1ItemInstance shopItem, int index) {
        this._cnSList.put(new Integer(index), shopItem);
    }
    
    public void get_buyCnS(Map<Integer, Integer> cnMap) {
        try {
            int itemid_cn = Npc_ShopX._itemid;
            for (Integer integer : cnMap.keySet()) {
                int count = cnMap.get(integer);
                if (count > 0) {
                    L1ItemInstance element = this._cnSList.get((Integer)(int)integer);
                    L1ShopS shopS = DwarfShopReading.get().getShopS(element.getId());
                    if (element != null && shopS != null) {
                        if (shopS.get_end() != 0) {
                            continue;
                        }
                        else if (shopS.get_item() == null) {
                            continue;
                        }
                        else {
                            L1ItemInstance itemT = this._pc.getInventory().checkItemX(itemid_cn, (long)shopS.get_adena());
                            if (itemT == null) {
                                this._pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(Npc_ShopX._itemid).getName()));
                            }
                            else {
                                shopS.set_end(1);
                                shopS.set_item(null);
                                DwarfShopReading.get().updateShopS(shopS);
                                DwarfShopReading.get().deleteItem(element.getId());
                                this._pc.getInventory().consumeItem(itemid_cn, (long)shopS.get_adena());
                                this._pc.getInventory().storeTradeItem(element);
                                this._pc.sendPackets(new S_ServerMessage(403, element.getLogName()));
                            }
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
            ListMapUtil.clear(this._cnList);
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void add_cnList(int index, L1ShopItem shopItem) {
        this._cnList.put(index, shopItem);
    }
    
    public void get_buyCn(Map<Integer, Integer> cnMap) {
        try {
            if (this._pc.getInventory().getSize() + cnMap.size() >= 170) {
                this._pc.sendPackets(new S_ServerMessage(263));
            }
            else {
                Iterator<Integer> iterator = cnMap.keySet().iterator();
                while (iterator.hasNext()) {
                    int index = (Integer)iterator.next();
                    int count = cnMap.get((Integer)index);
                    if (count > 0) {
                        L1ShopItem element = this._cnList.get((Integer)index);
                        if (element != null) {
                            this.get_cnItem(element, count);
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            this.bonusCheck(this._pc.get_consume_point());
            this._pc.set_consume_point(0L);
            this._pc.set_temp_adena(0);
            ListMapUtil.clear(this._cnList);
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void get_cnItem(L1ShopItem element, int count) {
        try {
            int itemid_cn = this._pc.get_temp_adena();
            int itemid = element.getItemId();
            int packcount = element.getPackCount();
            int getCount = count;
            if (packcount > 1) {
                getCount = element.getPackCount() * count;
            }
            int enchantlevel = element.getEnchantLevel();
            int adenaCount = element.getPrice() * count;
            int DailyBuyingCount = element.getDailyBuyingCount();
            int DailyBuyinglevel = element.getDailyBuyinglevel();
            if (this._pc.getInventory().checkAddItem(element.getItem(), (long)getCount) != 0) {
                return;
            }
            else if (adenaCount <= 0) {
                IpReading.get().add(this._pc.getAccountName(), "負數洗物:" + adenaCount);
                this._pc.getNetConnection().kick();
                return;
            }
            else {
                long srcCount = this._pc.getInventory().countItems(itemid_cn);
                if (srcCount >= (long)adenaCount && this._pc.getInventory().checkItem(itemid_cn, (long)adenaCount)) {
                    L1Item itemtmp = ItemTable.get().getTemplate(itemid);
                    if (!itemtmp.isStackable() && this._pc.getInventory().getSize() + getCount >= 170) {
                        this._pc.sendPackets(new S_ServerMessage(263));
                        return;
                    }
                    else if (DailyBuyinglevel != 0 && this._pc.getLevel() < DailyBuyinglevel) {
                        this._pc.sendPackets(new S_ServerMessage("該商品限制購買等級:" + DailyBuyinglevel));
                        return;
                    }
                    else {
                        if (itemtmp.isStackable()) {
                            if (DailyBuyingCount > 0) {
                                int AlreadyBoughtCount = this._pc.getQuest().get_step(itemid);
                                int buyingcount = getCount;
                                if (buyingcount > DailyBuyingCount) {
                                    this._pc.sendPackets(new S_ServerMessage("超過每日限制購買數量上限:" + element.getDailyBuyingCount()));
                                    return;
                                }
                                else if (AlreadyBoughtCount < DailyBuyingCount && buyingcount + AlreadyBoughtCount <= DailyBuyingCount) {
                                    this._pc.getQuest().set_step(itemid, buyingcount + AlreadyBoughtCount);
                                }
                                else {
                                    this._pc.sendPackets(new S_ServerMessage("超過每日限制購買數量上限:" + element.getDailyBuyingCount()));
                                    return;
                                }
                            }
                            L1ItemInstance item = ItemTable.get().createItem(itemid);
                            item.setCount((long)getCount);
                            item.setEnchantLevel(enchantlevel);
                            item.setIdentified(true);
                            this.bonusCheck1((long)adenaCount);
                            this.createNewItem(item);
                            this.toGmMsg(itemtmp, adenaCount, true, itemid_cn);
                            L1Item temp = ItemTable.get().getTemplate(itemid_cn);
                            RecordTable.get().recordeShop1(this._pc.getName(), temp.getName(), adenaCount, item.getAllName(), getCount, item.getId(), this._pc.getIp());
                        }
                        else {
                            if (DailyBuyingCount > 0) {
                                int AlreadyBoughtCount = this._pc.getQuest().get_step(itemid);
                                int buyingcount = getCount;
                                if (buyingcount > DailyBuyingCount) {
                                    this._pc.sendPackets(new S_ServerMessage("超過每日限制購買數量上限:" + element.getDailyBuyingCount()));
                                    return;
                                }
                                else if (AlreadyBoughtCount < DailyBuyingCount && buyingcount + AlreadyBoughtCount <= DailyBuyingCount) {
                                    this._pc.getQuest().set_step(itemid, buyingcount + AlreadyBoughtCount);
                                }
                                else {
                                    this._pc.sendPackets(new S_ServerMessage("超過每日限制購買數量上限:" + element.getDailyBuyingCount()));
                                    return;
                                }
                            }
                            L1ItemInstance item2 = ItemTable.get().createItem(itemid);
                            L1Item temp = ItemTable.get().getTemplate(itemid_cn);
                            RecordTable.get().recordeShop1(this._pc.getName(), temp.getName(), adenaCount, item2.getAllName(), getCount, item2.getId(), this._pc.getIp());
                            for (int i = 0; i < getCount; ++i) {
                                L1ItemInstance item3 = ItemTable.get().createItem(itemid);
                                item3.setEnchantLevel(enchantlevel);
                                item3.setIdentified(true);
                                this.createNewItem(item3);
                                this.toGmMsg(itemtmp, adenaCount, true, itemid_cn);
                            }
                        }
                        this._pc.getInventory().consumeItem(itemid_cn, (long)adenaCount);
                        this._pc.set_consume_point(this._pc.get_consume_point() + (long)adenaCount);
                    }
                }
                else {
                    long nc = (long)adenaCount - srcCount;
                    L1Item item4 = ItemTable.get().getTemplate(itemid_cn);
                    this._pc.sendPackets(new S_ServerMessage(337, item4.getName() + "(" + nc + ")"));
                }
            }
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void toGmMsg(L1Item itemtmp, int adenaCount, boolean mode, int itemid_cn) {
        try {
            ServerCnInfoReading.get().create(this._pc, itemtmp, (long)adenaCount, mode, itemid_cn);
            Collection<L1PcInstance> allPc = World.get().getAllPlayers();
            for (L1PcInstance tgpc : allPc) {
                if (tgpc.isGm()) {
                    StringBuilder topc = new StringBuilder();
                    if (mode) {
                        topc.append("人物:" + this._pc.getName() + " 買入:" + itemtmp.getName() + " 花費商城幣:" + adenaCount);
                        tgpc.sendPackets(new S_ServerMessage(166, topc.toString()));
                    }
                    else {
                        topc.append("人物:" + this._pc.getName() + " 賣出:" + itemtmp.getName() + " 獲得商城幣:" + adenaCount);
                        tgpc.sendPackets(new S_ServerMessage(166, topc.toString()));
                    }
                }
            }
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void createNewItem(L1ItemInstance item) {
        try {
            this._pc.getInventory().storeItem(item);
            this._pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
        }
        catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void add_levelList(int key, int value) {
        this._uplevelList.put(key, value);
    }
    
    public Map<Integer, Integer> get_uplevelList() {
        return this._uplevelList;
    }
    
    public Integer get_uplevelList(int key) {
        return this._uplevelList.get((Integer)key);
    }
    
    public void clear_uplevelList() {
        ListMapUtil.clear(this._uplevelList);
    }
    
    public void set_newPcOriginal(int[] is) {
        this._is = is;
    }
    
    public int[] get_newPcOriginal() {
        return this._is;
    }
    
private void bonusCheck(long count) {
    if (count == 0L) {
        return;
    }
    else {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `shop_cn_商城滿額獎勵` ORDER BY `滿額金額` ASC");
            rs = ps.executeQuery();

            Timestamp now_time = new Timestamp(System.currentTimeMillis());
            int playerItemId = this._pc.get_temp_adena();

            while (rs.next()) {

                Timestamp end_time = rs.getTimestamp("限制結束時間");
                if (end_time != null && now_time.after(end_time)) {
                    continue;
                }

                // 幣種
                int need_item_id;
                try {
                    need_item_id = rs.getInt("幣種");
                } catch (Exception e) {
                    need_item_id = Npc_ShopX._itemid; // fallback
                }

                if (playerItemId != need_item_id)
                    continue;

                int need_counts = rs.getInt("滿額金額");
                if (need_counts <= 0)
                    continue;

                // ➤ 計算倍數（你最想要的）
                long times = count / need_counts;
                if (times <= 0)
                    continue;

                // 獎勵
                int item_id = rs.getInt("物品編號");
                int amount = rs.getInt("物品數量");

                long giveCount = amount * times; // 依倍數給

                L1ItemInstance item = ItemTable.get().createItem(item_id);
                if (item == null) {
                    L1PcOtherList._log.error("給予物件失敗 物品不存在(" + item_id + ")");
                    continue;
                }

                if (item.isStackable()) {
                    item.setCount(giveCount);
                    item.setIdentified(true);
                    this._pc.getInventory().storeItem(item);
                } else {
                    for (long i = 0; i < giveCount; i++) {
                        L1ItemInstance item2 = ItemTable.get().createItem(item_id);
                        item2.setIdentified(true);
                        this._pc.getInventory().storeItem(item2);
                    }
                }

                this._pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
            }

        } catch (Exception e) {
            L1PcOtherList._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return;
    }
}
    
    private void bonusCheck1(long count) {
        if (count == 0L) {
            return;
        }
        else {
            Connection co = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                co = DatabaseFactoryLogin.get().getConnection();
                String sqlstr = "SELECT * FROM `shop_商城一比一回饋` ORDER BY `道具編號` DESC";
                ps = co.prepareStatement("SELECT * FROM `shop_商城一比一回饋` ORDER BY `道具編號` DESC");
                rs = ps.executeQuery();
                Timestamp now_time = new Timestamp(System.currentTimeMillis());
                while (rs.next()) {
                    Timestamp end_time = rs.getTimestamp("限制結束時間");
                    if (end_time != null && now_time.after(end_time)) {
                        continue;
                    }
                    else {
                        int item_id = rs.getInt("道具編號");
                        L1ItemInstance item = ItemTable.get().createItem(item_id);
                        if (item == null) {
                            L1PcOtherList._log.error("給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")");
                            break;
                        }
                        else {
                            if (item.isStackable()) {
                                item.setCount(count);
                                item.setIdentified(true);
                                this._pc.getInventory().storeItem(item);
                            }
                            else {
                                item.setIdentified(true);
                                this._pc.getInventory().storeItem(item);
                            }
                            this._pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                            break;
                        }
                    }
                }
            }
            catch (Exception e) {
                L1PcOtherList._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(co);
                SQLUtil.close(rs);
            }
            return;
        }
    }
    
    public void add_cnList(L1ShopItem shopItem, int index) {
        this._cnList.put(new Integer(index), shopItem);
    }
}

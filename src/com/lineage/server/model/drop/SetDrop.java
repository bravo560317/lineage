package com.lineage.server.model.drop;

import com.lineage.server.templates.L1DropEnchant;
import com.lineage.server.datatables.DropItemEnchantTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.datatables.ItemTable;
import java.util.Calendar;
import java.util.Iterator;
import com.lineage.server.datatables.ItemLimitation;
import com.lineage.server.datatables.DropLimitTable;
import com.lineage.server.datatables.MapsTable;
import com.lineage.config.ConfigRate;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.templates.L1DropMob;
import com.lineage.server.templates.L1DropMap;
import com.lineage.server.templates.L1Drop;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;

public class SetDrop implements SetDropExecutor {

    private static final Log _log;
    private static Map<Integer, ArrayList<L1Drop>> _droplist;
    private static Map<Integer, ArrayList<L1DropMap>> _droplistX;
    private static Map<Integer, L1DropMob> _moblist;
    private static final Random _random;

    static {
        _log = LogFactory.getLog(SetDrop.class);
        _random = new Random();
    }

    @Override
    public void addDropMap(Map<Integer, ArrayList<L1Drop>> droplists) {
        if (_droplist != null) {
            _droplist.clear();
        }
        _droplist = droplists;
    }

    @Override
    public void addDropMapX(Map<Integer, ArrayList<L1DropMap>> droplists) {
        if (_droplistX != null) {
            _droplistX.clear();
        }
        _droplistX = droplists;
    }

    @Override
    public void addDropMob(Map<Integer, L1DropMob> droplists) {
        if (_moblist != null) {
            _moblist.clear();
        }
        _moblist = droplists;
    }

    @Override
    public void setDrop(L1NpcInstance npc, L1Inventory inventory) {
        this.setDrop(npc, inventory, 0.0);
    }

    @Override
    public void setDrop(L1NpcInstance npc, L1Inventory inventory, double random) {

        int mobId = npc.getNpcTemplate().get_npcId();
        int mapid = npc.getMapId();

        ArrayList<L1DropMap> droplistX = _droplistX.get(mapid);
        if (droplistX != null) {
            this.setDrop(npc, inventory, droplistX);
        }

        if (_moblist != null) {
            this.setDrop(npc, inventory, _moblist);
        }

        ArrayList<L1Drop> dropList = _droplist.get(mobId);
        if (dropList == null) {
            return;
        }

        double droprate = ConfigRate.RATE_DROP_ITEMS;
        if (droprate < 0.0) {
            droprate = 0.0;
        }
        droprate += random;

        double adenarate = ConfigRate.RATE_DROP_ADENA;
        if (adenarate < 0.0) {
            adenarate = 0.0;
        }

        if (droprate <= 0.0 && adenarate <= 0.0) {
            return;
        }

        for (L1Drop drop : dropList) {

            int itemId = drop.getItemid();
            if (adenarate == 0.0 && itemId == 40308) {
                continue;
            }

            int randomChance = _random.nextInt(1000000) + 1;
            double rateOfMapId = MapsTable.get().getDropRate(npc.getMapId());

            if (droprate != 0.0) {
                if (drop.getChance() * droprate * rateOfMapId < randomChance) {
                    continue;
                }

                long min = drop.getMin();
                long max = drop.getMax();
                long itemCount = min;
                long addCount = max - min + 1;

                if (addCount > 1) {
                    itemCount += _random.nextInt((int) addCount);
                }

                if (itemId == 40308) {
                    itemCount = (long) (itemCount * adenarate);
                }

                if (itemCount <= 0) {
                    _log.error("NPC加入背包物件數量為0(" + mobId + " itemId: " + itemId + ")");
                    continue;
                }

                if (itemCount > 2000000000L) {
                    itemCount = 2000000000L;
                }

                if (DropLimitTable.getInstance().giveCheck(itemId, (int) itemCount)) {
                    continue;
                }

                if (!ItemLimitation.get().checkLimitation(itemId)) {
                    continue;
                }

                this.additem(npc, inventory, itemId, itemCount);
            }
        }
    }

    private void setDrop(L1NpcInstance npc, L1Inventory inventory, Map<Integer, L1DropMob> moblist) {

        Calendar date = Calendar.getInstance();
        int nowWeek = date.get(Calendar.DAY_OF_WEEK) - 1;
        if (nowWeek == 0) nowWeek = 7;
        int nowHour = date.get(Calendar.HOUR_OF_DAY);

        for (Integer itemId : moblist.keySet()) {

            L1DropMob drop = moblist.get(itemId);
            int random = _random.nextInt(1000000) + 1;

            int week = drop.getweek();
            int starttime = drop.getStarttime();
            int unitytime = drop.getUnitytime();

            // 機率判斷
            if (drop.getChance() < random) {
                continue;
            }

            // 星期判斷
            if (week > 0 && week != nowWeek) {
                continue;
            }

            // 時間判斷
            if (starttime >= 0 && unitytime >= 0) {

                // 正常範圍：例 1~6
                if (starttime < unitytime) {
                    if (nowHour < starttime || nowHour >= unitytime) {
                        continue;
                    }
                }

                // 跨日：例 22~3
                else if (starttime > unitytime) {
                    if (!(nowHour >= starttime || nowHour < unitytime)) {
                        continue;
                    }
                }

                // 相等：24H
            }

            int min = drop.getMin();
            int max = drop.getMax();
            int addCount = max - min + 1;

            if (addCount > 1) {
                min += _random.nextInt(addCount);
            }

            if (min <= 0) {
                _log.error("NPC加入背包物件數量為0(" + npc.getNpcId() + " itemId: " + itemId + ") 掉落");
                continue;
            }

            if (DropLimitTable.getInstance().giveCheck(itemId, min)) {
                continue;
            }

            this.additem(inventory, itemId, min);
        }
    }

    private void additem(L1Inventory inventory, int itemId, long itemCount) {
        this.additem(inventory, itemId, itemCount, 0, 0);
    }

    private void additem(L1Inventory inventory, int itemId, long itemCount, int enchant_min, int enchant_max) {
        try {

            L1Item tmp = ItemTable.get().getTemplate(itemId);
            if (tmp == null) {
                _log.error("掉落物品設置錯誤(無這編號物品): " + itemId);
                return;
            }

            if (tmp.isStackable()) {

                L1ItemInstance item = ItemTable.get().createItem(itemId);
                if (item == null) return;

                item.setCount(itemCount);

                if (enchant_min > 0 && enchant_max > 0) {
                    if (enchant_min == enchant_max) {
                        item.setEnchantLevel(enchant_min);
                    } else {
                        item.setEnchantLevel(_random.nextInt(enchant_max) + enchant_min);
                    }
                }
				
// ⭐ 新增：掉落瞬間給予隨機炫色與屬性 (加入雙重開關防護：可堆疊)
if (com.lineage.config.ConfigOther.dropcolor && com.lineage.data.event.droptype.START) {
    int useType = item.getItem().getUseType();
    // 判斷是否為飾品 (23=項鍊, 24=戒指, 37=腰帶, 40=耳環)
    boolean isAccessory = (useType == 23 || useType == 24 || useType == 37 || useType == 40);
    
    // 如果是可炫色裝備，或者是飾品，就讓它骰機率！
    if ((item.getItem().isdropcolor() || isAccessory) && item.getItemArmorType() == 0) {
        com.lineage.william.drop_type_armor_item.forIntensifyArmor(null, item);
    }
}
				

                inventory.storeItem(item);

            } else {

                for (int i = 0; i < itemCount; i++) {

                    L1ItemInstance item2 = ItemTable.get().createItem(itemId);
                    if (item2 == null) continue;

                    item2.setCount(1);

if (enchant_min > 0 && enchant_max > 0) {
                        if (enchant_min == enchant_max) {
                            item2.setEnchantLevel(enchant_min);
                        } else {
                            item2.setEnchantLevel(_random.nextInt(enchant_max) + enchant_min);
                        }
                    }

// ⭐ 新增：掉落瞬間給予隨機炫色與屬性 (加入雙重開關防護：不可堆疊)
if (com.lineage.config.ConfigOther.dropcolor && com.lineage.data.event.droptype.START) {
    int useType = item2.getItem().getUseType();
    // 判斷是否為飾品 (23=項鍊, 24=戒指, 37=腰帶, 40=耳環)
    boolean isAccessory = (useType == 23 || useType == 24 || useType == 37 || useType == 40);
    
    // 如果是可炫色裝備，或者是飾品，就讓它骰機率！
    if ((item2.getItem().isdropcolor() || isAccessory) && item2.getItemArmorType() == 0) {
        com.lineage.william.drop_type_armor_item.forIntensifyArmor(null, item2);
    }
}

                    inventory.storeItem(item2);
                }
            }

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }
    private void setDrop(L1NpcInstance npc, L1Inventory inventory, ArrayList<L1DropMap> dropListX) {

        double droprate = ConfigRate.RATE_DROP_ITEMS;
        if (droprate < 0.0) droprate = 0.0;

        double adenarate = ConfigRate.RATE_DROP_ADENA;
        if (adenarate < 0.0) adenarate = 0.0;

        if (droprate <= 0.0 && adenarate <= 0.0) {
            return;
        }

        for (L1DropMap drop : dropListX) {

            int itemId = drop.getItemid();

            if (adenarate == 0.0 && itemId == 40308) {
                continue;
            }

            if (npc.getMapId() != drop.get_mapid()) {
                continue;
            }

            int randomChance = _random.nextInt(1000000) + 1;
            double rateOfMapId = MapsTable.get().getDropRate(npc.getMapId());

            boolean noadd = drop.getChance() * droprate * rateOfMapId < randomChance;

            if (droprate == 0.0) {
                continue;
            }

            if (noadd) {
                continue;
            }

            long min = drop.getMin();
            long max = drop.getMax();
            long itemCount = min;
            long addCount = max - min + 1;

            if (addCount > 1) {
                itemCount += _random.nextInt((int) addCount);
            }

            if (itemId == 40308) {
                itemCount = (long) (itemCount * adenarate);
            }

            if (itemCount <= 0) {
                _log.error("NPC加入背包物件數量為0(" + npc.getNpcId() + " itemId: " + itemId + ") 指定地圖");
                continue;
            }

            if (itemCount > 2000000000L) {
                itemCount = 2000000000L;
            }

            if (DropLimitTable.getInstance().giveCheck(itemId, (int) itemCount)) {
                continue;
            }

            if (!ItemLimitation.get().checkLimitation(itemId)) {
                continue;
            }

            this.additem(npc, inventory, itemId, itemCount);
        }
    }


    private void additem(L1NpcInstance npc, L1Inventory inventory, int itemId, long itemCount) {
        try {

            L1Item tmp = ItemTable.get().getTemplate(itemId);
            if (tmp == null) {
                _log.error("掉落物品設置錯誤(無這編號物品): " + itemId);
                return;
            }

            if (tmp.isStackable()) {

                L1ItemInstance item = ItemTable.get().createItem(itemId);
                if (item == null) return;

                item.setCount(itemCount);

                ArrayList<L1DropEnchant> datalist = DropItemEnchantTable.get().getDatalist(npc.getNpcId());
                if (datalist != null) {
                    for (L1DropEnchant data : datalist) {
                        if (data.getItemid() == itemId) {
                            int level = DropItemEnchantTable.get().getEnchant(data);
                            item.setEnchantLevel(level);
                            break;
                        }
                    }
                }
				
				
// ⭐ 新增：掉落瞬間給予隨機炫色與屬性 (可堆疊)
if (item.getItem().isdropcolor() && item.getItemArmorType() == 0) {
    com.lineage.william.drop_type_armor_item.forIntensifyArmor(null, item);
}

                inventory.storeItem(item);

            } else {

                for (int i = 0; i < itemCount; ++i) {

                    L1ItemInstance item2 = ItemTable.get().createItem(itemId);
                    if (item2 == null) continue;

                    item2.setCount(1);

ArrayList<L1DropEnchant> datalist2 = DropItemEnchantTable.get().getDatalist(npc.getNpcId());
                    if (datalist2 != null) {
                        for (L1DropEnchant data2 : datalist2) {
                            if (data2.getItemid() == itemId) {
                                int level2 = DropItemEnchantTable.get().getEnchant(data2);
                                item2.setEnchantLevel(level2);
                                break;
                            }
                        }
                    }

// ⭐ 新增：掉落瞬間給予隨機炫色與屬性 (加入雙重開關防護：不可堆疊)
if (com.lineage.config.ConfigOther.dropcolor && com.lineage.data.event.droptype.START) {
    int useType = item2.getItem().getUseType();
    // 判斷是否為飾品 (23=項鍊, 24=戒指, 37=腰帶, 40=耳環)
    boolean isAccessory = (useType == 23 || useType == 24 || useType == 37 || useType == 40);
    
    // 如果是可炫色裝備，或者是飾品，就讓它骰機率！
    if ((item2.getItem().isdropcolor() || isAccessory) && item2.getItemArmorType() == 0) {
        com.lineage.william.drop_type_armor_item.forIntensifyArmor(null, item2);
    }
}

                    inventory.storeItem(item2);
                }
            }

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }
}

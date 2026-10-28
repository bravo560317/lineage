package com.lineage.server.model.drop;

import com.lineage.server.model.L1Location;
import java.util.List;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.utils.ListMapUtil;
import com.lineage.server.world.World;
import com.lineage.server.datatables.RecordTable;
import com.lineage.config.ConfigDrop;
import com.lineage.server.datatables.ItemMsgTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.william.drop_type_armor_npc;
import com.lineage.william.drop_type_weapon_npc;
import com.lineage.config.ConfigOther;
import com.lineage.data.event.Armortype;
import com.lineage.server.datatables.ItemdropTable;
import com.lineage.config.ConfigGuaji;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.eric.gui.J_Main;
import com.lineage.config.ConfigAlt;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Character;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class DropShare implements DropShareExecutor {

    private static final Log _log;
    private static final byte[] HEADING_TABLE_X;
    private static final byte[] HEADING_TABLE_Y;
    private static Random _random;

    static {
        _log = LogFactory.getLog(DropShare.class);
        HEADING_TABLE_X = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        HEADING_TABLE_Y = new byte[] { -1, -1, 0, 1, 1, 1, 0, -1 };
        DropShare._random = new Random();
    }

    @Override
    public void dropShare(L1NpcInstance npc, ArrayList<L1Character> acquisitorList, ArrayList<Integer> hateList) {
        DropShareR dropShareR = new DropShareR(npc, acquisitorList, hateList);
        GeneralThreadPool.get().execute(dropShareR);
    }

    private class DropShareR implements Runnable {

        final L1NpcInstance _npc;
        final ArrayList<L1Character> _acquisitorList;
        final ArrayList<Integer> _hateList;

        private DropShareR(L1NpcInstance npc, ArrayList<L1Character> acquisitorList, ArrayList<Integer> hateList) {
            this._npc = npc;
            this._acquisitorList = acquisitorList;
            this._hateList = hateList;
        }

        @Override
        public void run() {
            try {
                L1Inventory inventory = this._npc.getInventory();
                if (inventory == null || inventory.getSize() <= 0) {
                    return;
                }

                if (this._acquisitorList.size() != this._hateList.size()) {
                    return;
                }

                int totalHate = 0;
                for (int i = this._hateList.size() - 1; i >= 0; --i) {
                    L1Character acquisitor = this._acquisitorList.get(i);

                    if (ConfigAlt.AUTO_LOOT == 2 && (acquisitor instanceof L1SummonInstance || acquisitor instanceof L1PetInstance)) {
                        this._acquisitorList.remove(i);
                        this._hateList.remove(i);
                    } else if (acquisitor != null
                            && acquisitor.getMapId() == this._npc.getMapId()
                            && acquisitor.getLocation().getTileLineDistance(this._npc.getLocation()) <= ConfigAlt.LOOTING_RANGE) {
                        totalHate += this._hateList.get(i);
                    } else {
                        this._acquisitorList.remove(i);
                        this._hateList.remove(i);
                    }
                }

                Random random = DropShare._random;
                boolean guajidrop = false;
                boolean guajidrop2 = false;

// ===== 找最高傷害（含寵物）=====
L1PcInstance lootPc = null;
int maxHate = 0;

for (int i = 0; i < this._hateList.size(); i++) {

    L1Character cha = this._acquisitorList.get(i);

    if (this._hateList.get(i) > maxHate) {

        // 玩家
        if (cha instanceof L1PcInstance) {
            maxHate = this._hateList.get(i);
            lootPc = (L1PcInstance) cha;
        }

        // 寵物 → 找主人
        else if (cha instanceof L1PetInstance) {
            L1PcInstance master = (L1PcInstance) ((L1PetInstance) cha).getMaster();
            if (master != null) {
                maxHate = this._hateList.get(i);
                lootPc = master;
            }
        }

        // 召喚獸 → 找主人
        else if (cha instanceof L1SummonInstance) {
            L1PcInstance master = (L1PcInstance) ((L1SummonInstance) cha).getMaster();
            if (master != null) {
                maxHate = this._hateList.get(i);
                lootPc = master;
            }
        }
    }
}

// ===== VIP判斷（90121 + 贊助500）=====
boolean allowVipLoot = false;

// if (lootPc != null &&
//     lootPc.getNetConnection() != null &&
//     lootPc.getNetConnection().getAccount() != null) {
// 
//     if (lootPc.getNetConnection().getAccount().get_first_pay() >= 500
//         || lootPc.getInventory().checkItem(90121)) {
// 
//         allowVipLoot = true;
//     }
// }

                boolean bypassGuajiPenalty = false;
                if (lootPc != null && (lootPc.isActived() || lootPc.get_followmaster() != null)) {
                    if (ConfigGuaji.guajiitem > 0 && lootPc.getInventory().consumeItem(ConfigGuaji.guajiitem, 1)) {
                        bypassGuajiPenalty = true;
                    }
                }

                List<L1ItemInstance> list = inventory.getItems();
                if (list == null || list.isEmpty()) {
                    return;
                }

                for (L1ItemInstance item : list) {
                    L1Inventory targetInventory = null;
                    int itemId = item.getItemId();

                    if (item.getItem().getType2() == 0 && item.getItem().getType() == 2) {
                        item.setNowLighting(false);
                    }

                    // 套用掛機掉寶機率衰減 (對主要擊殺者判定，並排除金幣 40308)
                    if (lootPc != null && (lootPc.isActived() || lootPc.get_followmaster() != null)) {
                        if (itemId != 40308 && !bypassGuajiPenalty) {
                            if (random.nextDouble() > ConfigGuaji.guajidrop) {
                                continue; // 機率沒中，直接不掉落
                            }
                        }
                    }
                    

                    if (this._npc.getNpcId() == 97259 && itemId == 80026) {
                        this.equalityDrop(this._npc, this._acquisitorList, itemId);
                        continue;
                    } else if (this._npc.getNpcId() == 97258 && itemId == 80024) {
                        this.equalityDrop(this._npc, this._acquisitorList, itemId);
                        continue;
                    } else if (this._npc.getNpcId() == 107034 && itemId == 82239) {
                        this.equalityDrop(this._npc, this._acquisitorList, itemId);
                        continue;
                    } else if (this._npc.getNpcId() == 107035 && itemId == 82236) {
                        this.equalityDrop(this._npc, this._acquisitorList, itemId);
                        continue;
                    } else if (this._npc.getNpcId() == 99019 && itemId == 85010) {
                        this.equalityDrop(this._npc, this._acquisitorList, itemId);
                        continue;
                    } else if (this._npc.getNpcId() == 99012 && itemId == 56313) {
                        this.equalityDropScreen(this._npc, itemId);
                        continue;
                    }

int mapId = this._npc.getMapId();
boolean allowAutoLoot;

// 90121 優先 (暫時取消，保留註解備查)
// if (allowVipLoot) {
//     allowAutoLoot = true;
// 
// } else 
if (ConfigAlt.AUTO_LOOT == 2) {
    // 全部地圖自動撿
    allowAutoLoot = true;

} else if (ConfigAlt.AUTO_LOOT == 0) {
    // 全部掉地上，但指定地圖例外
    allowAutoLoot = ConfigAlt.AUTO_LOOT2_MAP_LIST.contains(mapId);

} else if (ConfigAlt.AUTO_LOOT == 1) {
    // 寵物模式
    allowAutoLoot = false;

} else {
    allowAutoLoot = false;
}

                    // 只有指定地圖才自動分配進玩家身上，其餘全部掉地上
                    if (allowAutoLoot && totalHate > 0) {
                        int randomInt = random.nextInt(totalHate);
                        int chanceHate = 0;
                        long itemCount = item.getCount();

                        for (int j = this._hateList.size() - 1; j >= 0; --j) {
                            chanceHate += this._hateList.get(j);

                            if (chanceHate > randomInt) {
                                L1Character acquisitor = this._acquisitorList.get(j);

                                if (acquisitor.getInventory().checkAddItem(item, item.getCount()) == 0) {
                                    targetInventory = acquisitor.getInventory();

                                    if (acquisitor instanceof L1PcInstance) {
                                        L1PcInstance player = (L1PcInstance) acquisitor;

                                        int typeadena = 1;
                                        if (player.getadenapoint() > 0) {
                                            typeadena = player.getadenapoint() / 100;
                                        }

                                        if (itemId == 40308) {
                                            item.setCount(itemCount * (long) typeadena);
                                            double addadena = player.getGF();
                                            if ((player.isActived() || player.get_followmaster() != null) && !bypassGuajiPenalty) {
                                                itemCount = (long) (((double) itemCount + (double) itemCount * addadena) * ConfigGuaji.adena) * (long) typeadena;
                                                item.setCount(itemCount);
                                            } else if (addadena > 0.0) {
                                                itemCount = (long) (((double) itemCount + (double) itemCount * addadena) * (double) typeadena);
                                                item.setCount(itemCount);
                                            }
                                        }

                                        if (player.isActived() && ItemdropTable.RESTRICTIONS.contains((Integer) item.getItemId())) {
                                            guajidrop = true;
                                        } else {
                                            if (Armortype.START) {
                                                if (item.getItem().getType2() == 1 && item.getItem().isdropcolor() && ConfigOther.dropcolor) {
                                                    drop_type_weapon_npc.forIntensifyArmor(player, item);
                                                }
                                                if (item.getItem().getType2() == 2 && item.getItem().isdropcolor() && ConfigOther.dropcolor) {
                                                    drop_type_armor_npc.forIntensifyArmor(player, item);
                                                }
                                            }

                                            if (player.isInParty()) {
                                                Object[] pcs = player.getParty().partyUsers().values().toArray();
                                                if (pcs.length <= 0) {
                                                    break;
                                                }
                                                for (Object obj : pcs) {
                                                    if (obj instanceof L1PcInstance) {
                                                        L1PcInstance tgpc = (L1PcInstance) obj;
                                                        if (itemId != 40308
                                                                && tgpc.hasSkillEffect(1689)
                                                                && (!player.isActived() || player.get_followmaster() != null)) {
                                                            tgpc.sendPackets(new S_ServerMessage(813, this._npc.getNameId(), item.getLogName(), player.getName()));
                                                        }
                                                    }
                                                }
                                            } else if (player.hasSkillEffect(1690)
        && (!player.isActived() || player.get_followmaster() != null)) {
    player.sendPackets(new S_ServerMessage(143, this._npc.getNameId(), item.getLogName()));
}

                                            if (itemId != 40308 && ItemMsgTable.get().contains(item.getItemId()) && player.getWeapon() != null) {
                                                if (player.hasSkillEffect(1691)) {
                                                    ConfigDrop.msg(player.getName(), this._npc.getNameId(), item.getLogName(), player.getWeapon().getLogName());
                                                    String log = String.format("[掉落] 玩家: %s  怪物: %s  道具: %s  武器: %s",
                                                            player.getName(), this._npc.getNameId(), item.getLogName(), player.getWeapon().getLogName());
                                                    J_Main.getInstance().addConsol(log);
                                                }
                                                RecordTable.get().recordeItemEcho(player.getName(), item.getName(), (int) item.getCount(), item.getId(), this._npc.getName(), player.getIp());
                                                break;
                                            } else {
                                                break;
                                            }
                                        }
                                    } else {
                                        break;
                                    }
                                } else {
                                    item.set_showId(this._npc.get_showId());
                                    targetInventory = World.get().getInventory(acquisitor.getX(), acquisitor.getY(), acquisitor.getMapId());
                                    break;
                                }
                            }
                        }

                        if (guajidrop && ItemdropTable.RESTRICTIONS.contains((Integer) item.getItemId())) {
                            continue;
                        } else {
                            if (targetInventory != null) {
                                inventory.tradeItem(item, item.getCount(), targetInventory);
                            }
                            guajidrop = false;
                            guajidrop2 = false;
                        }

} else {

    // ⭐ AutoLoot=2 → 禁止掉地（直接吃掉）
    if (ConfigAlt.AUTO_LOOT == 2) {
        continue;
    }

    // ⭐ 掉地上前先設定 showId
    item.set_showId(this._npc.get_showId());

    // ⭐ 取得地板 inventory
    L1Inventory ground = World.get().getInventory(
            this._npc.getX(),
            this._npc.getY(),
            this._npc.getMapId());

    // ⭐ 炫色（重點）
    if (item.getItem().isdropcolor() && ConfigOther.dropcolor) {

        if (item.getItem().getType2() == 1) {
            drop_type_weapon_npc.forIntensifyArmor(null, item);
        }

        if (item.getItem().getType2() == 2) {
            drop_type_armor_npc.forIntensifyArmor(null, item);
        }
    }

    // ⭐ 最後才丟地上
    inventory.tradeItem(item, item.getCount(), ground);
                    }
                }

                ListMapUtil.clear(list);

            } catch (Exception ex) {
                _log.error("DropShare error", ex);
            } finally {
                ListMapUtil.clear(this._acquisitorList);
                ListMapUtil.clear(this._hateList);
            }
        }

        public void equalityDrop(L1NpcInstance npc, ArrayList<?> recipientList, int itemId) {
            L1Inventory inventory = npc.getInventory();
            if (inventory.getSize() == 0) {
                return;
            } else {
                L1Location pt = npc.getLocation();
                for (int i = recipientList.size() - 1; i >= 0; --i) {
                    L1Character recipient = (L1Character) recipientList.get(i);
                    if (recipient instanceof L1SummonInstance || recipient instanceof L1PetInstance) {
                        recipientList.remove(i);
                    } else if (recipient == null) {
                        recipientList.remove(i);
                    } else if (npc.getMapId() != recipient.getMapId()) {
                        recipientList.remove(i);
                    } else if (!pt.isInScreen(recipient.getLocation()) && npc.getNpcId() != 99019) {
                        recipientList.remove(i);
                    }
                }

                for (L1ItemInstance drop : inventory.getItems()) {
                    for (int j = recipientList.size() - 1; j >= 0; --j) {
                        L1PcInstance pc = (L1PcInstance) recipientList.get(j);
                        if (drop.getItemId() == itemId) {
                            if (pc.getInventory().checkAddItem(drop, drop.getCount()) == 0) {
                                pc.getInventory().storeItem(drop.getItemId(), drop.getCount());
                                if (pc.isInParty()) {
                                    Object[] pcs = pc.getParty().partyUsers().values().toArray();
                                    if (pcs.length <= 0) {
                                        return;
                                    } else {
                                        for (Object obj : pcs) {
                                            if (obj instanceof L1PcInstance) {
                                                L1PcInstance tgpc = (L1PcInstance) obj;
                                                if (drop.getItemId() != 40308) {
                                                    tgpc.sendPackets(new S_ServerMessage(813, this._npc.getNameId(), drop.getLogName(), pc.getName()));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (drop.getItemId() != 40308) {
                                        pc.sendPackets(new S_ServerMessage(143, this._npc.getNameId(), drop.getLogName()));
                                    }
                                }
                            } else {
                                drop.set_showId(npc.get_showId());
                                L1Inventory ground = World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId());
                                ground.storeItem(drop.getItemId(), drop.getCount());
                            }
                        }
                    }
                }
                return;
            }
        }

        public void equalityDropScreen(L1NpcInstance npc, int itemId) {
            L1Inventory inventory = npc.getInventory();
            if (inventory.getSize() == 0) {
                return;
            } else {
                ArrayList<L1PcInstance> recipientList = World.get().getVisiblePlayer(npc);
                for (L1ItemInstance drop : inventory.getItems()) {
                    for (int i = recipientList.size() - 1; i >= 0; --i) {
                        L1PcInstance pc = recipientList.get(i);
                        if (drop.getItemId() == itemId) {
                            if (pc.getInventory().checkAddItem(drop, drop.getCount()) == 0) {
                                pc.getInventory().storeItem(drop.getItemId(), drop.getCount());
                                if (pc.isInParty()) {
                                    Object[] pcs = pc.getParty().partyUsers().values().toArray();
                                    if (pcs.length <= 0) {
                                        return;
                                    } else {
                                        for (Object obj : pcs) {
                                            if (obj instanceof L1PcInstance) {
                                                L1PcInstance tgpc = (L1PcInstance) obj;
                                                if (drop.getItemId() != 40308) {
                                                    tgpc.sendPackets(new S_ServerMessage(813, this._npc.getNameId(), drop.getLogName(), pc.getName()));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (drop.getItemId() != 40308) {
                                        pc.sendPackets(new S_ServerMessage(143, this._npc.getNameId(), drop.getLogName()));
                                    }
                                }
                            } else {
                                drop.set_showId(npc.get_showId());
                                L1Inventory ground = World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId());
                                ground.storeItem(drop.getItemId(), drop.getCount());
                            }
                        }
                    }
                }
                return;
            }
        }
    }
}
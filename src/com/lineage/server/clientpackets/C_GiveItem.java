package com.lineage.server.clientpackets;

import java.util.Random;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1ServerItemGive;
import com.lineage.server.templates.L1PetType;
import com.lineage.server.templates.L1PetItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.ServerItemGiveTable;
import com.lineage.server.datatables.PetTypeTable;
import com.lineage.server.datatables.PetItemTable;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_GiveItem extends ClientBasePacket {

    private static final Log _log;
    private static final String[] receivableImpls;

    static {
        _log = LogFactory.getLog(C_GiveItem.class);
        receivableImpls = new String[] { "L1Npc", "L1Monster", "L1Guardian", "L1Teleporter", "L1Guard" };
    }

    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost() || pc.isDead() || pc.isTeleport() || pc.isPrivateShop()) {
                return;
            }

            int targetId = this.readD();
            int x = this.readH();
            int y = this.readH();
            int itemObjId = this.readD();
            long count = this.readD();
            count = Math.max(0L, Math.min(count, Integer.MAX_VALUE));

            L1Object object = World.get().findObject(targetId);
            if (!(object instanceof L1NpcInstance)) {
                return;
            }

            L1NpcInstance target = (L1NpcInstance) object;
            if (!this.isNpcItemReceivable(target.getNpcTemplate())) {
                return;
            }

            L1Inventory targetInv = target.getInventory();
            L1Inventory inv = pc.getInventory();
            L1ItemInstance item = inv.getItem(itemObjId);
            if (item == null || item.getCount() <= 0L) {
                return;
            }
            if (item.isEquipped()) {
                pc.sendPackets(new S_ServerMessage(141));
                return;
            }
            if (!item.getItem().isTradable() || item.getBless() >= 128 || item.get_time() != null || ItemRestrictionsTable.RESTRICTIONS.contains(item.getItemId())) {
                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                return;
            }

            L1PetItem petItem = PetItemTable.get().getTemplate(item.getItemId());
            L1PetType petType = PetTypeTable.getInstance().get(target.getNpcTemplate().get_npcId());
            if (petType != null && item.getItem().getUseType() == -12 && petType.getPetArmor() == 0) {
                pc.sendPackets(new S_ServerMessage(166, "該寵物無法穿著該裝備"));
                return;
            }

            L1ServerItemGive itemGive = ServerItemGiveTable.get().getTemplate(item.getItemId());
            if (!pc.isGm()) {
                if (itemGive == null) {
                    pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                    return;
                }
                if (!itemGive.ispet() && object instanceof L1PetInstance) {
                    pc.sendPackets(new S_ServerMessage(166, "只能給予怪物"));
                    return;
                }
                int maxCount = itemGive.get_max();
                if (maxCount > 0 && count > maxCount) {
                    pc.sendPackets(new S_ServerMessage(166, item.getName() + "最多只能給予" + maxCount + "個"));
                    return;
                }
            }

            if (Math.abs(pc.getX() - x) >= 3 || Math.abs(pc.getY() - y) >= 3) {
                pc.sendPackets(new S_ServerMessage(142));
                return;
            }

            for (L1NpcInstance petObject : pc.getPetList().values()) {
                if (petObject instanceof L1PetInstance) {
                    L1PetInstance pet = (L1PetInstance) petObject;
                    if (item.getId() == pet.getItemObjId()) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        return;
                    }
                }
            }

            if (item.getId() == pc.getQuest().get_step(74)) {
                pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
                return;
            }
            if (pc.getDoll(item.getId()) != null) {
                pc.sendPackets(new S_ServerMessage(1181));
                return;
            }
            if (!pc.isGm() && targetInv.checkAddItem(item, count) != 0) {
                pc.sendPackets(new S_ServerMessage(942));
                return;
            }

            RecordTable.get().recordgivenpc(pc.getName(), item.getAllName(), (int) count, item.getId(), pc.getIp());
            L1ItemInstance getItem = inv.tradeItem(item, count, targetInv);
            target.onGetItem(getItem);
            target.turnOnOffLight();
            pc.turnOnOffLight();

            if (petType == null || target.isDead()) {
                return;
            }

            if (getItem.getItemId() == 40057 && target.getInventory() != null) {
                target.getInventory().removeItem(getItem, 1L);
                pc.sendPackets(new com.lineage.server.serverpackets.S_Sound(30001));
            }

            if (getItem.getItemId() == petType.getItemIdForTaming()) {
                this.tamePet(pc, target);
            }

            if (target instanceof L1PetInstance) {
                L1PetInstance tgPet = (L1PetInstance) target;
                if (getItem.getItemId() == petType.getEvolvItemId() && petType.canEvolve()) {
                    this.evolvePet(pc, tgPet);
                    return;
                }
                if (petItem != null) {
                    if (petItem.isWeapom()) {
                        C_UsePetItem.usePetWeapon(pc, tgPet, item, petItem);
                    } else {
                        C_UsePetItem.usePetArmor(pc, tgPet, item, petItem);
                    }
                    return;
                }
            }
        } catch (Exception ex) {
            _log.error(ex.getLocalizedMessage(), ex);
        } finally {
            this.over();
        }
    }

    private boolean isNpcItemReceivable(L1Npc npc) {
        for (String impl : receivableImpls) {
            if (npc.getImpl().equals(impl)) {
                return true;
            }
        }
        return false;
    }

    private void tamePet(L1PcInstance pc, L1NpcInstance target) {
        // 如果對象已經是別人的寵物或召喚物，就不能再抓
        if (target instanceof L1PetInstance || target instanceof L1SummonInstance) {
            return;
        }

        int petcost = 0;
        int divisor = 6;
        Object[] petList = pc.getPetList().values().toArray();

        // 計算目前召喚在外的寵物總成本
        for (Object pet : petList) {
            petcost += ((L1NpcInstance) pet).getPetcost();
        }

        // 1. 取得玩家原始魅力
        int charisma = pc.getCha();
        // 妖精天生魅力額外 +6
        if (pc.isElf()) {
            charisma += 6;
        }
        // 扣除目前已帶出寵物佔用的魅力值
        charisma -= petcost;

        // 判斷是否為特定高魅力需求寵物 (例如：高麗犬、虎男等)
        int npcId = target.getNpcId();
        if (npcId >= 96501 && npcId <= 96521) {
            divisor = 10;
        } else if (npcId >= 96601 && npcId <= 96626) {
            divisor = 12;
        } else if (npcId == 45313 || npcId == 45710 || npcId == 45711 || npcId == 45712) {
            divisor = 12; // 特殊寵物需求魅力較高 (通常一隻佔 12 點)
        }

        // 計算剩餘魅力還能抓幾隻
        int petCount = charisma / divisor;
        int currentPets = petList.length;
        int maxPets = 6; // 最大攜帶數量限制

        // 如果剩餘魅力不足以再抓一隻，或已達攜帶上限，則失敗
        if (petCount <= 0 || currentPets >= maxPets) {
            pc.sendPackets(new S_ServerMessage(489)); // 傳送魅力不足訊息
            return;
        }

        L1PcInventory inv = pc.getInventory();
        // 檢查玩家背包空間是否已滿 (上限 180 格)
        if (inv.getSize() >= 180) {
            return;
        }

        // 限制：當怪物等級比玩家高的時候無法捕捉
        if (target.getLevel() > pc.getLevel()) {
            pc.sendPackets(new S_ServerMessage(166, "等級高於怪物等級才可捕捉"));
            return;
        }

        // 限制：血量必須低於 50% 才能抓
        if (target.getCurrentHp() > target.getMaxHp() / 2) {
            pc.sendPackets(new S_ServerMessage(166, "血量過高，無法馴服"));
            return;
        }

        // 呼叫 getTameRate 計算出目前的抓寵機率，並發送系統訊息提示玩家
        double captureRate = this.getTameRate(target);
        pc.sendPackets(new S_ServerMessage(166, "抓寵成功率：" + String.format("%.2f", captureRate) + "%"));

        // 執行最終捕捉判定
        if (this.isTamePet(target)) {
            // 抓寵成功：給予寵物項圈 (道具ID: 40314)
            L1ItemInstance petamu = inv.storeItem(40314, 1L);
            if (petamu != null) {
                // 將怪物轉化為寵物實體，並綁定給玩家與項圈
                new L1PetInstance(target, pc, petamu.getId());
                // 發送獲得道具的訊息 (顯示獲得項圈)
                pc.sendPackets(new S_ItemName(petamu));
            }
        } else {
            // 抓寵失敗：發送失敗訊息
            pc.sendPackets(new S_ServerMessage(324)); // 馴服失敗
        }
    }

    private void evolvePet(L1PcInstance pc, L1PetInstance pet) {
        L1PetType petType = PetTypeTable.getInstance().get(pet.getNpcTemplate().get_npcId());
        L1PcInventory inv = pc.getInventory();
        L1ItemInstance petamu = inv.getItem(pet.getItemObjId());
        int level = ConfigOther.PetLevelUp;

        if (petType == null) {
            return;
        }

        if (pet.getLevel() >= level && pc == pet.getMaster() && petamu != null) {
            // 修正重點：
            // 進化道具在 start() 裡已經用 tradeItem(item, count, targetInv) 交給寵物，
            // 這裡不能再 pet.getInventory().consumeItem(...) 扣一次，
            // 否則玩家身上有 2 枚同道具時，會出現丟 1 枚卻少 2 枚的情況。

            L1ItemInstance highpetamu = inv.storeItem(40316, 1L);
            if (highpetamu != null) {
                pet.evolvePet(highpetamu.getId());
                pc.sendPackets(new S_ItemName(highpetamu));
                inv.removeItem(petamu, 1L);
            }
        }
    }

    private double getTameRate(L1NpcInstance npc) {
        // 1. 計算怪物目前的血量百分比
        double hpRate = (double) npc.getCurrentHp() / npc.getMaxHp();
        // 2. 計算血量低於 50% 的「殘血比例」(例如 hpRate=0.1 時，ratio=0.8)
        // 血量越低，ratio 越接近 1.0；血量剛好 50% 時，ratio 為 0.0
        double ratio = (0.5 - hpRate) / 0.5;
        // 3. 設定基礎機率範圍：最低 1%，最高 30%
        double minRate = 1.0;
        double maxRate = 30.0;
        // 4. 根據殘血比例，計算出初步的捕捉機率 (介於 1% ~ 30% 之間)
        double finalRate = minRate + (maxRate - minRate) * ratio;

        // 5. 等級懲罰：怪物等級若超過 30 級，每超過 5 級，捕捉機率會除以 1.5 倍
        if (npc.getLevel() > 30) {
            int diff = npc.getLevel() - 30;
            int step = diff / 5;
            for (int i = 0; i < step; i++) {
                finalRate /= 1.5;
            }
        }
        // 6. 保底機率：不管怎麼扣，機率最低不會小於 0.01%
        if (finalRate < 0.01) {
            finalRate = 0.01;
        }
        return finalRate;
    }

    private boolean isTamePet(L1NpcInstance npc) {
        // 血量大於一半，捕捉機率強制為 0
        if (npc.getCurrentHp() > npc.getMaxHp() / 2) {
            return false;
        }
        // 取得上方計算出來的捕捉機率 (例如回傳 15.5，表示 15.5%)
        double rate = this.getTameRate(npc);
        // 將機率放大 100 倍轉為整數 (例如 15.5% 變成 1550)
        int chance = (int) (rate * 100);
        Random random = new Random();
        // 產生 0 ~ 9999 的亂數，小於 chance 則判定捕捉成功
        return random.nextInt(10000) < chance;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
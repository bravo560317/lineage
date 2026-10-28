package com.lineage.data.item_etcitem.extra;

import com.lineage.server.templates.L1MagicWeapon;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.lock.CharWeaponTimeReading;
import java.sql.Timestamp;
import com.lineage.server.datatables.ExtraMagicWeaponTable;
import com.lineage.server.datatables.sql.CharWeaponTimeTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class MagicWeapon extends ItemExecutor
{
    private static final Random _random;
    
    static {
        _random = new Random();
    }
    
    private MagicWeapon() {
    }
    
    public static ItemExecutor get() {
        return new MagicWeapon();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.getItem().getType2() != 1) {
            pc.sendPackets(new S_SystemMessage("\\aD注意！只能對武器附加，並且再次強化可覆蓋或更換其他附魔技能。"));
            return;
        }
        else {
            int steps = CharWeaponTimeTable.get_steps(targObjId);
            if (steps >= 0) {
                ++steps;
            }
            if (steps == -1) {
                steps = 0;
            }
            
            // 檢查是否更換了不同種類的魔法石
            boolean isChangeType = false;
            if (tgItem.get_magic_weapon() != null && tgItem.get_magic_weapon().getItemId() != item.getItemId()) {
                steps = 0;
                isChangeType = true; // 標記為更換類型
            }
            
            L1MagicWeapon magicWeapon = ExtraMagicWeaponTable.getInstance().get(item.getItemId(), steps);
            if (magicWeapon == null && steps >= 0) {
                pc.sendPackets(new S_SystemMessage("\\aD請注意！已達該屬性最高階級。"));
                return;
            }
            else if (magicWeapon == null && steps == -1) {
                return;
            }
            else {
                pc.getInventory().removeItem(item, 1L);
                if (MagicWeapon._random.nextInt(100) >= magicWeapon.getSuccessRandom()) {
                    pc.sendPackets(new S_SystemMessage(magicWeapon.getFailureMsg()));
                    return;
                }
                else {
                    boolean isUpgrade = (tgItem.get_magic_weapon() != null && !isChangeType);
                    tgItem.set_magic_weapon(magicWeapon);
                    
                    // --- 時間繼承核心邏輯 ---
                    // 只有在「第一次附加」或「更換不同魔法」時才重新計算 30 天
                    if (tgItem.get_time() == null || isChangeType) {
                        if (magicWeapon.getMaxUseTime() > 0) {
                            Timestamp ts = new Timestamp(System.currentTimeMillis() + (magicWeapon.getMaxUseTime() * 60L * 1000L));
                            tgItem.set_time(ts);
                        } else {
                            tgItem.set_time(null);
                        }
                    } 
                    // 如果是同系列升級，我們「不」重新設定 tgItem 的時間，讓它保持原本的 Timestamp
                    
                    if (tgItem.get_magic_weapon() != null) {
                        if (isUpgrade) {
                            // 【關鍵點】升級同系列，傳入 null 給 updateTime，觸發資料庫不更新時間欄位
                            CharWeaponTimeReading.get().updateTime(tgItem.getId(), null, tgItem.get_magic_weapon().getItemId(), steps, 1);
                        }
                        else if (isChangeType) {
                            // 更換不同系列，傳入新算好的時間，重新開始計時
                            CharWeaponTimeReading.get().updateTime(tgItem.getId(), tgItem.get_time(), tgItem.get_magic_weapon().getItemId(), steps, 0);
                        }
                        else {
                            // 第一次新增魔法
                            CharWeaponTimeReading.get().addTime(tgItem.getId(), tgItem.get_time(), tgItem.get_magic_weapon().getItemId());
                        }
                    }
                    
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.sendPackets(new S_SystemMessage(magicWeapon.getSuccessMsg()));
                    return;
                }
            }
        }
    }
}
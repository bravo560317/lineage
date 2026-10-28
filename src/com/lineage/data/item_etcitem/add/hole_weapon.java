package com.lineage.data.item_etcitem.add;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.lock.CharItemPowerReading;
import java.sql.Timestamp;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.data.event.PowerItemSet;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class hole_weapon extends ItemExecutor
{
    private int _type;
    private int a;
    private static final Random _random;
    

    static {
        _random = new Random();
    }
    

    private hole_weapon() {
    }
    

    public static ItemExecutor get() {
        return new hole_weapon();
    }
    

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        // 取得目標物品的 object ID
        int targObjId = data[0];
        // 從玩家背包中取得目標物品實體
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        // 如果目標物品不存在，則返回
        if (tgItem == null) {
            return;
        }
        // 如果目標物品正在裝備中
        else if (tgItem.isEquipped()) {
            // 發送訊息提示玩家必須先解除裝備
            pc.sendPackets(new S_ServerMessage("\\fR你必須先解除物品裝備!"));
            return;
        }
        else {
            L1ItemPower_name power = null;
            boolean update = false;
            int count = 0;
            // 根據物品的使用類型進行處理
            switch (tgItem.getItem().getUseType()) {
                // 如果是武器類型
                case 1: {
                    // 設置最大洞數
                    count = PowerItemSet.WEAPONHOLE;
                    // 如果物品已有洞數資料
                    if (tgItem.get_power_name() != null) {
                        // 取得現有洞數資料
                        power = tgItem.get_power_name();
                        // 標記為更新操作
                        update = true;
                        break;
                    }
                    // 如果物品沒有洞數資料
                    else {
                        // 創建新的洞數資料物件
                        power = new L1ItemPower_name();
                        break;
                    }
                }
            }
            // 再次確認是武器類型
            if (tgItem.getItem().getUseType() == 1) {
                // 如果洞數已達上限
                if (power.get_hole_count() == PowerItemSet.WEAPONHOLE) {
                    // 發送訊息提示已達擴充上限
                    pc.sendPackets(new S_ServerMessage("\\fT該物品已經無法擴充"));
                    return;
                }
                else {
                    // 根據設定的類型進行處理
                    // 由於 case 1~5 的邏輯完全相同，可以直接處理，無需使用 switch
                    if (power != null) {
                        // 隨機判斷是否成功
                        if (_random.nextInt(100) <= this.a) {
                            // 設定物品 object ID
                            power.set_item_obj_id(tgItem.getId());
                            // 洞數+1
                            power.set_hole_count(power.get_hole_count() + 1);
                            // 更新物品的洞數資料
                            tgItem.set_power_name(power);
                            // 發送成功訊息
                            pc.sendPackets(new S_ServerMessage("\\fR成功增加了洞數-最高可達" + PowerItemSet.WEAPONHOLE + "個洞(請注意)"));
                            // 記錄成功日誌
                            打洞成功("玩家 :" + pc.getName() + "使用 " + item.getName() + "(成功打出1洞) ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
                            // 如果是更新操作
                            if (update) {
                                // 更新資料庫中的物品洞數資料
                                CharItemPowerReading.get().updateItem(tgItem.getId(), tgItem.get_power_name());
                            }
                            // 如果是新增操作
                            else {
                                // 將新的洞數資料存入資料庫
                                CharItemPowerReading.get().storeItem(tgItem.getId(), tgItem.get_power_name());
                            }
                            // 更新客戶端物品狀態
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.sendPackets(new S_ItemName(tgItem));
                        }
                        // 如果失敗
                        else {
                            // 發送失敗訊息
                            pc.sendPackets(new S_ServerMessage("\\fR強化失敗..運氣不好"));
                        }
                    }
                    // 消耗打洞道具
                    pc.getInventory().removeItem(item, 1L);
                }
            }
            else {
                // 如果不是可擴充的物品類型，發送訊息
                pc.sendPackets(new S_ServerMessage("\\fR該物品無法擴充"));
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        // 從設定檔讀取類型
        try {
            this._type = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        // 從設定檔讀取成功率
        try {
            this.a = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
    

    /**
     * 記錄打洞成功日誌
     * @param info 日誌訊息
     */
    public static void 打洞成功(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/打洞成功.txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

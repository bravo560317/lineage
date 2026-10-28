package com.lineage.data.item_etcitem;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.william.New_BlessItem;
import com.lineage.william.New_BlessItem1;
import java.util.Random;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Reel_item_bless_great extends ItemExecutor
{
    private int _xianzhi;
    private boolean type2;
    
    private Reel_item_bless_great() {
        this._xianzhi = 0;
        this.type2 = false;
    }
    
    public static ItemExecutor get() {
        return new Reel_item_bless_great();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else if (item2.getItem().getUseType() != 23 && item2.getItem().getUseType() != 24 && item2.getItem().getUseType() != 37 && item2.getItem().getUseType() != 40) {
            pc.sendPackets(new S_ServerMessage("該物品不是飾品無法祝福"));
            return;
        }
        else if (item2.getBless() == 0) {
            pc.sendPackets(new S_ServerMessage("已經是祝福的物品"));
            return;
        }
        else if (item2.getBless() == 128) {
            pc.sendPackets(new S_ServerMessage("已經是祝福的物品"));
            return;
        }
        // 補上防具卷軸有的詛咒防呆機制，避免詛咒品被異常洗白
        else if (item2.getBless() == 2) {
            pc.sendPackets(new S_ServerMessage("該物品是詛咒品,無法祝福"));
            return;
        }
        // 暫時註解掉 isgivebless 的判斷，強制允許所有飾品(包含炫彩系統的飾品)都能被祝福
        // else if (!item2.getItem().isgivebless()) {
        //     pc.sendPackets(new S_SystemMessage("該物品無法進行祝福。"));
        //     return;
        // }
        else if (item2.isEquipped()) {
            pc.sendPackets(new S_ServerMessage("(預防誤點機制啟動)裝備中無法祝福"));
            return;
        }
        else {
            Random _random = new Random();
            int _xianzhi1 = this._xianzhi;
            if (pc.isGm()) {
                _xianzhi1 = 100;
            }
            if (_random.nextInt(100) + 1 <= _xianzhi1) {
                item2.setBless(0);
                if (item2.getItem().getnewbless() == 1) {
                    New_BlessItem1.forIntensifyArmor(pc, item2);
                }
                else {
                    New_BlessItem.forIntensifyArmor(pc, item2);
                }
                pc.getInventory().updateItem(item2, 576);
                pc.getInventory().saveItem(item2, 576);
                // 補上提示文字，讓玩家知道已經祝福成功
                pc.sendPackets(new S_ServerMessage("祝福成功"));
                pc.getInventory().removeItem(item, 1L);
                pc.sendPackets(new S_ItemStatus(item2));
                祝福升級成功("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (" + item2.getViewName() + ")升級成功 ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
            }
            else {
                pc.getInventory().removeItem(item, 1L);
                pc.sendPackets(new S_SystemMessage("祝福失敗"));
                祝福升級失敗("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (" + item2.getViewName() + ")升級(失敗) ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
                if (this.type2) {
                    pc.getInventory().removeItem(item2, 1L);
                }
            }
            return;
        }
    }
    
    public static void 祝福升級成功(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[祝福升級成功].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void 祝福升級失敗(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[祝福升級失敗].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._xianzhi = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this.type2 = Boolean.parseBoolean(set[2]);
        }
        catch (Exception ex2) {}
    }
}

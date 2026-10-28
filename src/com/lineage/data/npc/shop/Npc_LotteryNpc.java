package com.lineage.data.npc.shop;

import com.lineage.config.ConfigOther;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.WriteLogTxt;
import com.lineage.server.model.L1LotteryGame;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_HowManyMake;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

public class Npc_LotteryNpc extends NpcExecutor {

    private Npc_LotteryNpc() {}

    @Override
    public int type() {
        return 3;
    }

    public static NpcExecutor get() {
        return new Npc_LotteryNpc();
    }

    @Override
    public void talk(final L1PcInstance pc, final L1NpcInstance npc) {
        String[] lotteryData = L1LotteryGame.getInstance().getLotteryData(pc);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "lottery", lotteryData));
    }

    @Override
    public void action(final L1PcInstance pc, final L1NpcInstance npc, final String cmd, final long amount) {
        final L1ItemInstance itemExist = pc.getInventory().findItemId(ConfigOther.Dubo);
        if (itemExist == null) {
            pc.sendPackets(new S_SystemMessage("\\F2連籌碼都沒有，無法購買彩券！"));
            return;
        }

        if (cmd.equalsIgnoreCase("buy_ticket")) {
            // 彈出數量輸入框，讓玩家選擇要買幾張（上限 100 張）
            pc.sendPackets(new S_HowManyMake(npc.getId(), 100, "do_buy_ticket"));
            return; // 彈出視窗後直接返回，不要往下執行刷新對話窗
        } else if (cmd.equalsIgnoreCase("do_buy_ticket")) {
            // 玩家輸入張數後執行購買
            L1LotteryGame.getInstance().buyTickets(pc, (int) amount);
        } else if (cmd.equalsIgnoreCase("online")) {
            final int onlecount = L1LotteryGame.getInstance().getOnlineCount(pc);
            if (onlecount > 0) {
                pc.getInventory().storeItem(ConfigOther.Dubo, onlecount);
                pc.sendPackets(new S_ServerMessage(143, "彩券大師", "籌碼(" + onlecount + ")"));
                WriteLogTxt.Recording("彩券記錄", "玩家:" + pc.getName() + "領取掉線存儲彩券獎金(" + onlecount + ")");
            } else {
                pc.sendPackets(new S_SystemMessage("你沒有掉線存儲的獎金，或者已經領取過了。"));
            }
        }

        // 只有在非彈窗操作（如購買完成或領獎）時，才重新整理介面更新數據
        String[] lotteryData = L1LotteryGame.getInstance().getLotteryData(pc);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "lottery", lotteryData));
    }
}
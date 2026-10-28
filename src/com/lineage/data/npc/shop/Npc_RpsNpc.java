package com.lineage.data.npc.shop;

import com.lineage.config.ConfigOther;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.WriteLogTxt;
import com.lineage.server.model.L1RpsGame;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_HowManyMake;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

public class Npc_RpsNpc extends NpcExecutor {

    private Npc_RpsNpc() {}

    @Override
    public int type() {
        return 3;
    }

    public static NpcExecutor get() {
        return new Npc_RpsNpc();
    }

    @Override
    public void talk(final L1PcInstance pc, final L1NpcInstance npc) {
        String[] rpsData = L1RpsGame.getInstance().getRpsData(pc);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rps", rpsData));
    }

    @Override
    public void action(final L1PcInstance pc, final L1NpcInstance npc, final String cmd, final long amount) {
        final L1ItemInstance itemExist = pc.getInventory().findItemId(ConfigOther.Dubo);
        if (itemExist == null) {
            pc.sendPackets(new S_SystemMessage("\\F2連籌碼都沒有，無法參與對決！"));
            return;
        }

        L1RpsGame.WaitingPlayer waiting = L1RpsGame.getInstance().getWaitingPlayer();

        // 點擊剪刀(1)、石頭(2)、布(3)
        if (cmd.equalsIgnoreCase("play_scissors") || cmd.equalsIgnoreCase("play_stone") || cmd.equalsIgnoreCase("play_cloth")) {
            int choice = cmd.equalsIgnoreCase("play_scissors") ? 1 : (cmd.equalsIgnoreCase("play_stone") ? 2 : 3);

            if (waiting == null) {
                // 【發起人】：沒有人排隊。點擊出拳後，彈出視窗輸入下注金額！
                String actionName = "do_create_" + choice;
                pc.sendPackets(new S_HowManyMake(npc.getId(), (int) Math.min(itemExist.getCount(), 100000000), actionName));
                return; // 直接返回，不要執行後面的刷新覆蓋
            } else {
                // 【挑戰者】：已經有人排隊。直接用對方的金額應戰，開打！
                L1RpsGame.getInstance().playRps(pc, waiting.betCount, choice);
            }
        } 
        // 發起人輸入完金額後送出，正式建立對決
        else if (cmd.startsWith("do_create_")) {
            int choice = Integer.parseInt(cmd.replace("do_create_", ""));
            L1RpsGame.getInstance().playRps(pc, (int) amount, choice);
        } 
        else if (cmd.equalsIgnoreCase("cancel_queue")) {
            L1RpsGame.getInstance().cancelQueue(pc);
        } 
        else if (cmd.equalsIgnoreCase("online")) {
            final int onlecount = L1RpsGame.getInstance().getOnlineCount(pc);
            if (onlecount > 0) {
                pc.getInventory().storeItem(ConfigOther.Dubo, onlecount);
                pc.sendPackets(new S_ServerMessage(143, "博弈大師", "籌碼(" + onlecount + ")"));
                WriteLogTxt.Recording("對決記錄", "玩家:" + pc.getName() + "領取掉線存儲籌碼(" + onlecount + ")");
            } else {
                pc.sendPackets(new S_SystemMessage("你沒有掉線存儲，或者你已經領取過了。"));
            }
        }

        // 重新整理對話介面
        String[] rpsData = L1RpsGame.getInstance().getRpsData(pc);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rps", rpsData));
    }
}
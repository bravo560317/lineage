package com.lineage.data.npc.shop;

import com.lineage.server.templates.L1Item;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1Account;
import java.util.Iterator;
import java.util.Map;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.william.PayBonus;
import com.lineage.william.ezpay;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.william.ezpayfirst;
import com.lineage.william.Ezpay_bz;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.lock.EzpayReading1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class NPC_Ezpay_1 extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(NPC_Ezpay_1.class);
    }
    
    private NPC_Ezpay_1() {
    }
    
    public static NpcExecutor get() {
        return new NPC_Ezpay_1();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_s_0"));
        }
        catch (Exception e) {
            NPC_Ezpay_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (cmd.equals("up")) {
            int page = pc.get_other().get_page() - 1;
            showPage(pc, npc, page);
        }
        else if (cmd.equals("dn")) {
            int page = pc.get_other().get_page() + 1;
            showPage(pc, npc, page);
        }
        else if (cmd.equalsIgnoreCase("1")) {
            pc.get_otherList().SHOPLIST.clear();
            Map<Integer, int[]> info = EzpayReading1.get().ezpayInfo(pc.getAccountName().toLowerCase());
            if (info.size() <= 0) {
                isCloseList = true;
                pc.sendPackets(new S_ServerMessage("\\fV並沒有查詢到您的相關商品記錄!!"));
            }
            else {
                pc.get_other().set_page(0);
                int index = 0;
                for (Integer key : info.keySet()) {
                    int[] value = info.get(key);
                    if (value != null) {
                        pc.get_otherList().SHOPLIST.put(index, value);
                        ++index;
                    }
                }
                showPage(pc, npc, 0);
            }
        }
        else if (cmd.equalsIgnoreCase("2")) {
            Map<Integer, int[]> info = EzpayReading1.get().ezpayInfo(pc.getAccountName().toLowerCase());
            if (info.size() <= 0) {
                isCloseList = true;
                pc.sendPackets(new S_ServerMessage("\\fV並沒有查詢到您的相關商品記錄!!"));
            }
            else {
                for (Integer key2 : info.keySet()) {
                    int[] value2 = info.get(key2);
                    int id = value2[0];
                    int itemid = value2[1];
                    int count = value2[2];
                    if (EzpayReading1.get().update(pc.getAccountName(), id, pc.getName(), pc.getNetConnection().getIp().toString())) {
                        L1Account account = pc.getNetConnection().getAccount();
                        pc.setfirst_pay(account.get_first_pay());
                        Ezpay_bz.getItem(pc, count);
                        if (account.get_first_pay() == 0) {
                            ezpayfirst.getItem(pc, (long)count);
                        }
                        account.set_first_pay(account.get_first_pay() + count);
                        AccountReading.get().updatefp(pc.getAccountName(), account.get_first_pay());
                        ezpay.getItem(pc, (long)account.get_first_pay());
                        PayBonus.getItem(pc, count);
                        NPC_Ezpay_1._log.fatal("帳號:" + pc.getAccountName().toLowerCase() + " 人物:" + pc.getName() + " 領取交易序號:" + id + "(" + itemid + ") 數量:" + count + " 完成!!");
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("\\fV領取失敗!!請聯繫線上GM!! ID:" + id));
                        NPC_Ezpay_1._log.fatal("帳號:" + pc.getAccountName().toLowerCase() + " 人物:" + pc.getName() + " 領取交易序號:" + id + " 領取失敗!!");
                        isCloseList = true;
                    }
                }
            }
            isCloseList = true;
        }
        else {
            isCloseList = true;
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private static final void showPage(L1PcInstance pc, L1NpcInstance npc, int page) {
        Map<Integer, int[]> list = pc.get_otherList().SHOPLIST;
        int allpage = list.size() / 10;
        if (page > allpage || page < 0) {
            page = 0;
        }
        if (list.size() % 10 != 0) {
            ++allpage;
        }
        pc.get_other().set_page(page);
        int showId = page * 10;
        StringBuilder stringBuilder = new StringBuilder();
        for (int key = showId; key < showId + 10; ++key) {
            int[] info = list.get((Integer)key);
            if (info != null) {
                L1Item itemtmp = ItemTable.get().getTemplate(info[1]);
                if (itemtmp != null) {
                    stringBuilder.append(itemtmp.getName() + "(" + info[2] + "),");
                }
            }
        }
        String[] clientStrAry = stringBuilder.toString().split(",");
        if (allpage == 1) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_s_1", clientStrAry));
        }
        else if (page < 1) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_s_3", clientStrAry));
        }
        else if (page >= allpage - 1) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_s_4", clientStrAry));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_s_2", clientStrAry));
        }
    }
}

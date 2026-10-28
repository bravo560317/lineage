package com.lineage.data.npc.event;

import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.templates.L1Bank;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.server.datatables.lock.AccountBankReading;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.utils.RangeLong;
import com.lineage.data.event.BankSet;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Bank extends NpcExecutor
{
    private static final Log _log;
    private static final Map<String, Npc_Bank.BankType> _bankTypeMap;
    
    static {
        _log = LogFactory.getLog(Npc_Bank.class);
        _bankTypeMap = new ConcurrentHashMap();
    }
    
    public static NpcExecutor get() {
        return new Npc_Bank();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_1", new String[] { RangeLong.scount(BankSet.BANKMAX).toString(), RangeLong.scount(BankSet.BANK_INTEREST_OVER).toString() }));
        }
        catch (Exception e) {
            Npc_Bank._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            Npc_Bank.BankType bankType = null;
            if (Npc_Bank._bankTypeMap.get(pc.getAccountName()) == null) {
                bankType = new Npc_Bank.BankType();
                bankType._account_name = pc.getAccountName();
                Npc_Bank._bankTypeMap.put(bankType._account_name, bankType);
            }
            else {
                bankType = Npc_Bank._bankTypeMap.get(pc.getAccountName());
            }
            L1Bank bank = AccountBankReading.get().get(pc.getAccountName());
            boolean isClose = false;
            String pass = "n";
            if (cmd.equalsIgnoreCase("A")) {
                if (bank == null) {
                    bankType._isNew = true;
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_4", new String[] { "" }));
                }
                else {
                    bankType._isNew = false;
                    bankType._pass_x1 = "";
                    bankType._pass = "";
                    bankType._type = false;
                    bankType._repass = false;
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_2", new String[] { RangeLong.scount(bank.get_adena_count()).toString(), String.valueOf(BankSet.BANK_TIME), RangeLong.scount(BankSet.BANKMAX).toString(), String.valueOf(BankSet.BANK_INTEREST) }));
                }
            }
            else if (cmd.equalsIgnoreCase("B")) {
                bankType._type = false;
                pc.sendPackets(new S_ItemCount(npc.getId(), 100, 2000000000, "1y_bank_3", "I"));
            }
            else if (cmd.equalsIgnoreCase("I")) {
                if (!bankType._type && bank != null) {
                    if (bank.get_adena_count() >= BankSet.BANKMAX) {
                        isClose = true;
                        pc.sendPackets(new S_ServerMessage("\\fR你的存款已經超過可存款最大限額!"));
                    }
                    else {
                        L1ItemInstance item = pc.getInventory().checkItemX(BankSet.BANK_ITEMID, amount);
                        if (item != null) {
                            long count = amount + bank.get_adena_count();
                            pc.getInventory().removeItem(item, amount);
                            isClose = true;
                            bank.set_adena_count(count);
                            AccountBankReading.get().updateAdena(bankType._account_name, count);
                            pc.sendPackets(new S_ServerMessage("\\fU你要求的 " + item.getName() + "(" + RangeLong.scount(amount) + ") 已經存入銀行"));
                        }
                        else {
                            isClose = true;
                            L1Item itemtmp = ItemTable.get().getTemplate(BankSet.BANK_ITEMID);
                            pc.sendPackets(new S_ServerMessage("\\fR你身上的 " + itemtmp.getNameId() + " 數量低於您要求存入的數字"));
                        }
                    }
                }
            }
            else if (cmd.equalsIgnoreCase("C")) {
                if (bank != null) {
                    if (bank.isEmpty()) {
                        isClose = true;
                        pc.sendPackets(new S_ServerMessage("\\fR你的帳戶目前沒有餘額!"));
                    }
                    else {
                        bankType._type = true;
                        bankType._pass = "";
                        bankType._pass_x1 = "";
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_8", new String[] { "" }));
                    }
                }
            }
            else if (cmd.equalsIgnoreCase("O")) {
                if (bankType._type && bank != null) {
                    long count2 = amount;
                    if (bank.get_adena_count() - amount <= 0L) {
                        count2 = bank.get_adena_count();
                    }
                    long newCount = bank.get_adena_count() - count2;
                    bank.set_adena_count(newCount);
                    AccountBankReading.get().updateAdena(bankType._account_name, newCount);
                    CreateNewItem.createNewItem(pc, BankSet.BANK_ITEMID, count2);
                }
            }
            else if (cmd.equalsIgnoreCase("D")) {
                Npc_Bank._bankTypeMap.remove(pc.getAccountName());
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_1", new String[] { RangeLong.scount(BankSet.BANKMAX).toString(), String.valueOf(BankSet.BANK_INTEREST_OVER) }));
            }
            else if (cmd.equalsIgnoreCase("E")) {
                bankType._pass = "";
                bankType._pass_x1 = "";
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_4", new String[] { "" }));
            }
            else if (cmd.equalsIgnoreCase("F")) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_8", new String[] { "" }));
                bankType._repass = true;
            }
            else if (cmd.equalsIgnoreCase("Z")) {
                isClose = true;
            }
            else if (cmd.equalsIgnoreCase("0")) {
                pass = "0";
            }
            else if (cmd.equalsIgnoreCase("1")) {
                pass = "1";
            }
            else if (cmd.equalsIgnoreCase("2")) {
                pass = "2";
            }
            else if (cmd.equalsIgnoreCase("3")) {
                pass = "3";
            }
            else if (cmd.equalsIgnoreCase("4")) {
                pass = "4";
            }
            else if (cmd.equalsIgnoreCase("5")) {
                pass = "5";
            }
            else if (cmd.equalsIgnoreCase("6")) {
                pass = "6";
            }
            else if (cmd.equalsIgnoreCase("7")) {
                pass = "7";
            }
            else if (cmd.equalsIgnoreCase("8")) {
                pass = "8";
            }
            else if (cmd.equalsIgnoreCase("9")) {
                pass = "9";
            }
            if (bankType._isNew) {
                if (!pass.equals("n")) {
                    String out = "";
                    if (bankType._pass_x1.length() == 6) {
                        switch (bankType._pass.length()) {
                            case 0: {
                                bankType._pass = pass;
                                out = "*";
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_5", new String[] { out }));
                                break;
                            }
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5: {
                                Npc_Bank.BankType bankType2 = bankType;
                                bankType2._pass = bankType2._pass + pass;
                                for (int i = 0; i < bankType._pass.length(); ++i) {
                                    out = out + "*";
                                }
                                if (bankType._pass.length() == 6) {
                                    if (bankType._pass_x1.equals(bankType._pass)) {
                                        if (!bankType._repass) {
                                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_6", new String[] { bankType._pass }));
                                            bank = new L1Bank();
                                            bank.set_account_name(bankType._account_name);
                                            bank.set_adena_count(0L);
                                            bank.set_pass(bankType._pass);
                                            AccountBankReading.get().create(bankType._account_name, bank);
                                            break;
                                        }
                                        else {
                                            isClose = true;
                                            bank.set_pass(bankType._pass);
                                            AccountBankReading.get().updatePass(bankType._account_name, bankType._pass);
                                            pc.sendPackets(new S_ServerMessage("\\fR你的密碼變更完成!!"));
                                            break;
                                        }
                                    }
                                    else {
                                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_7"));
                                        break;
                                    }
                                }
                                else {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_5", new String[] { out }));
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        switch (bankType._pass_x1.length()) {
                            case 0: {
                                bankType._pass_x1 = pass;
                                out = "*";
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_4", new String[] { out }));
                                break;
                            }
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5: {
                                Npc_Bank.BankType bankType3 = bankType;
                                bankType3._pass_x1 = bankType3._pass_x1 + pass;
                                for (int i = 0; i < bankType._pass_x1.length(); ++i) {
                                    out = out + "*";
                                }
                                if (bankType._pass_x1.length() == 6) {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_5", new String[] { "" }));
                                    break;
                                }
                                else {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_4", new String[] { out }));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            else if (!pass.equals("n") && (bankType._type || bankType._repass)) {
                String out = "";
                switch (bankType._pass.length()) {
                    case 0: {
                        bankType._pass = pass;
                        out = "*";
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_8", new String[] { out }));
                        break;
                    }
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5: {
                        Npc_Bank.BankType bankType4 = bankType;
                        bankType4._pass = bankType4._pass + pass;
                        for (int i = 0; i < bankType._pass.length(); ++i) {
                            out = out + "*";
                        }
                        if (bankType._pass.length() == 6) {
                            if (bankType._pass.equals(bank.get_pass())) {
                                if (bankType._repass) {
                                    bankType._pass = "";
                                    bankType._pass_x1 = "";
                                    bankType._isNew = true;
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_4", new String[] { "" }));
                                    break;
                                }
                                else {
                                    pc.sendPackets(new S_ItemCount(npc.getId(), 100, 2000000000, "1y_bank_3", "O"));
                                    break;
                                }
                            }
                            else {
                                isClose = true;
                                pc.sendPackets(new S_ServerMessage(835));
                                break;
                            }
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "1y_bank_8", new String[] { out }));
                            break;
                        }
                    }
                }
            }
            if (isClose) {
                Npc_Bank._bankTypeMap.remove(pc.getAccountName());
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        catch (Exception e) {
            Npc_Bank._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private class BankType
    {
        String _account_name;
        boolean _isNew;
        String _pass_x1;
        String _pass;
        boolean _type;
        boolean _repass;
        
        private BankType() {
            this._account_name = null;
            this._isNew = false;
            this._pass_x1 = "";
            this._pass = "";
            this._type = false;
            this._repass = false;
        }
    }
}

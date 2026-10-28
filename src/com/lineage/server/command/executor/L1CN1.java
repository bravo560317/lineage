package com.lineage.server.command.executor;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Account;
import com.lineage.list.OnlineUser;
import com.lineage.server.datatables.lock.DwarfReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.lock.AccountReading;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1CN1 implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1CN1.class);
    }
    
    private L1CN1() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1CN1();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc == null) {
                L1CN1._log.warn("這個命令只能在遊戲中執行!");
            }
            StringTokenizer st = new StringTokenizer(arg);
            String accname = st.nextToken();
            L1Account acc = AccountReading.get().getAccount(accname.toLowerCase());
            if (acc != null) {
                int item_id = Integer.parseInt(st.nextToken());
                int count = 0;
                try {
                    count = Integer.parseInt(st.nextToken());
                }
                catch (Exception e) {
                    count = 1;
                }
                L1ItemInstance item = ItemTable.get().createItem(70475);
                if (item != null) {
                    item.setCount((long)count);
                    pc.sendPackets(new S_ServerMessage(166, "指定帳號(" + acc.get_login() + ") 加入物品" + item_id + " 數量" + count));
                    DwarfReading.get().insertItem(acc.get_login(), item);
                    ClientExecutor cl = OnlineUser.get().get(acc.get_login());
                    if (cl != null) {
                        L1PcInstance tgpc = cl.getActiveChar();
                        if (tgpc != null) {
                            tgpc.sendPackets(new S_ServerMessage(166, "GM在您的倉庫加入物品" + item.getName() + " 數量" + count));
                            tgpc.getDwarfInventory().loadItems();
                        }
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(166, "指令異常: 沒有該帳號(" + accname + ")!!"));
            }
        }
        catch (Exception e2) {
            L1CN1._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}

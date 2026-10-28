package com.lineage.server.command.executor;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Account;
import com.lineage.list.OnlineUser;
import com.lineage.server.datatables.lock.DwarfReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1CN2 implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1CN2.class);
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1CN2();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc == null) {
                L1CN2._log.warn("這個命令只能在遊戲中執行!");
            }
            StringTokenizer st = new StringTokenizer(arg);
            String char_name = st.nextToken();
            String account_name = null;
            L1PcInstance target = World.get().getPlayer(char_name);
            if (target == null) {
                account_name = AccountReading.get().getPlayer(char_name);
            }
            else {
                account_name = target.getAccountName();
            }
            L1Account acc = AccountReading.get().getAccount(account_name);
            if (acc != null) {
                int item_id = Integer.parseInt(st.nextToken());
                int count = 0;
                try {
                    count = Integer.parseInt(st.nextToken());
                }
                catch (Exception e) {
                    count = 1;
                }
                L1ItemInstance item = ItemTable.get().createItem(item_id);
                if (item != null) {
                    item.setCount((long)count);
                    pc.sendPackets(new S_ServerMessage(166, "指定角色名(" + char_name + ")對應帳號(" + acc.get_login() + ")加入物品:" + item.getName() + '(' + item_id + ") 數量" + count));
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
                pc.sendPackets(new S_ServerMessage(166, "指令異常: 角色名(" + char_name + ")没有找到对应的账号!!"));
            }
        }
        catch (Exception e2) {
            L1CN2._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName() + e2.getMessage());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}

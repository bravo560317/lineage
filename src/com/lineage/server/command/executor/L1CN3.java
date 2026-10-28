package com.lineage.server.command.executor;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.list.OnlineUser;
import com.lineage.server.datatables.lock.DwarfReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.sql.CharacterTable;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1CN3 implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1CN3.class);
    }
    
    private L1CN3() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1CN3();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String name = st.nextToken();
            String acc = CharacterTable.getAccountName(name);
            if (acc != "") {
                int item_id = Integer.parseInt(st.nextToken());
                int count = 1;
                int enchant_lv = 0;
                if (st.hasMoreTokens()) {
                    count = Integer.parseInt(st.nextToken());
                }
                if (st.hasMoreTokens()) {
                    enchant_lv = Integer.parseInt(st.nextToken());
                }
                L1ItemInstance item = ItemTable.get().createItem(item_id);
                if (item != null) {
                    item.setCount((long)count);
                    item.setEnchantLevel(enchant_lv);
                    item.setIdentified(true);
                    if (pc == null) {
                        L1CN3._log.info("指定帳號(" + acc + ") 加入物品：" + item.getLogName());
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(166, "指定帳號(" + acc + ") 加入物品：" + item.getLogName()));
                    }
                    DwarfReading.get().insertItem(acc, item);
                    ClientExecutor cl = OnlineUser.get().get(acc);
                    if (cl != null) {
                        L1PcInstance tgpc = cl.getActiveChar();
                        if (tgpc != null) {
                            tgpc.sendPackets(new S_ServerMessage(166, "管理員在你的倉庫加入物品：" + item.getLogName()));
                            tgpc.getDwarfInventory().loadItems();
                        }
                    }
                }
            }
            else if (pc == null) {
                L1CN3._log.info("指令異常: 沒有該角色帳號資料  (" + name + ")!!");
            }
            else {
                pc.sendPackets(new S_ServerMessage(166, "指令異常: 沒有該角色帳號資料  (" + name + ")!!"));
            }
        }
        catch (Exception e) {
            L1CN3._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}

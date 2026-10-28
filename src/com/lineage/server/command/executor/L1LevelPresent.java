package com.lineage.server.command.executor;

import com.lineage.server.templates.L1Item;
import com.lineage.server.model.L1DwarfInventory;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemTable;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

public class L1LevelPresent implements L1CommandExecutor
{
    private static Logger _log;
    
    static {
        L1LevelPresent._log = Logger.getLogger(L1LevelPresent.class.getName());
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1LevelPresent();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int minlvl = Integer.parseInt(st.nextToken(), 10);
            int maxlvl = Integer.parseInt(st.nextToken(), 10);
            int itemid = Integer.parseInt(st.nextToken(), 10);
            int count = Integer.parseInt(st.nextToken(), 10);
            L1Item temp = ItemTable.get().getTemplate(itemid);
            if (temp == null) {
                pc.sendPackets(new S_SystemMessage("不存在的道具編號。"));
                return;
            }
            else {
                L1DwarfInventory.present(minlvl, maxlvl, itemid, count);
                pc.sendPackets(new S_SystemMessage(temp.getName() + "數量" + count + "個發送出去了。(Lv" + minlvl + "~" + maxlvl + ")"));
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage(".等級送禮 minlvl maxlvl 道具編號  數量  請輸入。"));
        }
    }
}

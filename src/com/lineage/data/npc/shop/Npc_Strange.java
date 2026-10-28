package com.lineage.data.npc.shop;

import com.lineage.server.serverpackets.S_ShopSellListCn;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Strange extends NpcExecutor
{
    private static final Log _log;
    private int _itemid;
    private String _htmlid;
    
    static {
        _log = LogFactory.getLog(Npc_Strange.class);
    }
    
    public Npc_Strange() {
        this._itemid = 0;
        this._htmlid = null;
    }
    
    public static NpcExecutor get() {
        return new Npc_Strange();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (this._htmlid != null) {
            pc.set_temp_adena(this._itemid);
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), this._htmlid));
        }
        else {
            pc.set_temp_adena(this._itemid);
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_shop"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("a")) {
            pc.sendPackets(new S_ShopSellListCn(pc, npc));
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._itemid = Integer.parseInt(set[1]);
        }
        catch (Exception e) {
            Npc_Strange._log.error("NPC專屬貨幣設置錯誤:檢查CLASSNAME為Npc_Strange的NPC設置!");
        }
        try {
            this._htmlid = set[2];
        }
        catch (Exception ex) {}
    }
}

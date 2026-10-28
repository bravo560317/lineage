package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1Clan;
import com.lineage.config.ConfigClan;
import com.lineage.server.datatables.sql.ClanTable;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class clan_glass extends ItemExecutor
{
    private int a;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(clan_glass.class);
    }
    
    private clan_glass() {
    }
    
    public static ItemExecutor get() {
        return new clan_glass();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getClanid() == 0) {
            pc.sendPackets(new S_ServerMessage("\\fY您尚未加入血盟無法使用"));
            return;
        }
        else if (pc.getQuest().get_step(8544) == 1) {
            pc.sendPackets(new S_SystemMessage("今日貢獻能量已達上限"));
            return;
        }
        else {
            if (!pc.getClanname().equals(pc.getClanNameContribution())) {
                pc.setClanNameContribution(pc.getClanname());
            }
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            clan.setClanContribution(clan.getClanContribution() + this.a);
            ClanTable.getInstance().updateClan(clan);
            pc.setPcContribution(pc.getPcContribution() + this.a);
            pc.setClanContribution(pc.getClanContribution() + this.a);
            pc.getInventory().storeItem(92164, (long)this.a);
            pc.sendPackets(new S_SystemMessage("獲得血盟貢獻幣:【" + this.a + "】個。"));
            pc.sendPackets(new S_SystemMessage("你給予目前的血盟貢獻度有【" + pc.getClanContribution() + "】點。"));
            pc.sendPackets(new S_SystemMessage("你的血盟目前所獲得的貢獻度總合有【" + clan.getClanContribution() + "】點。"));
            pc.sendPackets(new S_SystemMessage("獲得[" + pc.getPcContribution() + "/" + ConfigClan.PcContribution + "]點血盟能量值。"));
            pc.getInventory().removeItem(item, 1L);
            if (pc.getPcContribution() >= ConfigClan.PcContribution) {
                pc.sendPackets(new S_ServerMessage("\\fY每日血盟能量上限:" + ConfigClan.PcContribution));
                pc.setPcContribution(ConfigClan.PcContribution);
                pc.getQuest().set_step(8544, 1);
                pc.setPcContribution(0);
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        this.a = Integer.parseInt(set[1]);
    }
}

package com.lineage.data.item_etcitem.doll;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import java.util.List;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.world.WorldItem;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import com.lineage.server.model.Instance.L1DollInstance;
import java.util.Map;
import com.lineage.data.executor.ItemExecutor;

public class Magic_Doll extends ItemExecutor
{
    private Map<Integer, L1DollInstance> _dolls2;
    private Map<Integer, L1DollInstance> _dolls3;
    
    private Magic_Doll() {
        this._dolls2 = new HashMap();
        this._dolls3 = new HashMap();
    }
    
    public static ItemExecutor get() {
        return new Magic_Doll();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        int itemobj = item.getId();
        this.useMagicDoll(pc, itemId, itemobj);
    }
    
    private void useMagicDoll(L1PcInstance pc, int itemId, int itemObjectId) {
        L1Doll type = DollPowerTable.get().get_type(itemId);
        if (pc.getDoll(itemObjectId) != null) {
            pc.getDoll(itemObjectId).deleteDoll();
            pc.setopendoll(0);
            return;
        }
        else if (pc.isInvisble()) {
            return;
        }
        else if (type.get_ArrowGifx() != 0 && pc.getQuest().get_step(type.get_ArrowGifx()) != 1) {
            return;
        }
        else {
            int castle_id = 1;
            while (castle_id < 8) {
                if (!ConfigOther.WAR_DOLL && ServerWarExecutor.get().isNowWar(castle_id) && pc.getMapId() == 4) {
                    pc.sendPackets(new S_ServerMessage("攻城戰期間無法使用娃娃!!"));
                    return;
                }
                else {
                    ++castle_id;
                }
            }
            if (pc.getdollcount() >= ConfigOther.dollcount && type.get_mode() == 0) {
                pc.sendPackets(new S_SystemMessage("已攜帶:" + pc.getdollcount() + "隻魔法娃娃，不能帶更多了"));
                return;
            }
            else if (pc.getdollcount1() >= ConfigOther.dollcount1 && type.get_mode() == 1) {
                pc.sendPackets(new S_SystemMessage("已攜帶:" + pc.getdollcount1() + "隻補助娃娃，不能帶更多了"));
                return;
            }
            else {
                if (!pc.getDolls().isEmpty()) {
                    if (pc.getdollcount_itemid() == itemId && type.get_mode() == 0) {
                        pc.sendPackets(new S_ServerMessage("\\fY不能攜帶相同的娃娃"));
                        return;
                    }
                    else if (pc.getdollcount1_itemid() == itemId && type.get_mode() == 1) {
                        pc.sendPackets(new S_ServerMessage("\\fY不能攜帶相同的輔助娃娃"));
                        return;
                    }
                }
                if (!ConfigOther.WAR_DOLL && pc.getClan() != null) {
                    boolean inWar = false;
                    if (pc.getClan().getCastleId() != 0) {
                        if (ServerWarExecutor.get().isNowWar(pc.getClan().getCastleId())) {
                            inWar = true;
                        }
                    }
                    else {
                        List<L1War> warList = WorldWar.get().getWarList();
                        for (L1War war : warList) {
                            if (war.checkClanInWar(pc.getClan().getClanName())) {
                                inWar = true;
                                break;
                            }
                        }
                    }
                    if (inWar) {
                        pc.sendPackets(new S_ServerMessage(1531));
                        return;
                    }
                }
                boolean iserror = false;
                if (type != null) {
                    if (type.get_need() != null) {
                        int[] itemids = type.get_need();
                        int[] counts = type.get_counts();
                        for (int i = 0; i < itemids.length; ++i) {
                            if (!pc.getInventory().checkItem(itemids[i], (long)counts[i])) {
                                L1Item temp = ItemTable.get().getTemplate(itemids[i]);
                                pc.sendPackets(new S_ServerMessage(337, temp.getNameId()));
                                iserror = true;
                            }
                        }
                        if (!iserror) {
                            for (int i = 0; i < itemids.length; ++i) {
                                pc.getInventory().consumeItem(itemids[i], (long)counts[i]);
                            }
                        }
                    }
                    if (!iserror) {
                        L1Npc template = NpcTable.get().getTemplate(71082);
                        if (type.get_mode() == 0) {
                            L1DollInstance doll = new L1DollInstance(template, pc, itemObjectId, type, false);
                            L1ItemInstance item = WorldItem.get().getItem(itemObjectId);
                            item.startEquipmentTimer(pc);
                            pc.add_dollcount(1);
                            pc.add_dollcount_itemid(itemId);
                            pc.setopendoll(itemId);
                        }
                        if (type.get_mode() == 1) {
                            L1DollInstance doll = new L1DollInstance(template, pc, itemObjectId, type, true);
                            L1ItemInstance item = WorldItem.get().getItem(itemObjectId);
                            item.startEquipmentTimer(pc);
                            pc.add_dollcount1(1);
                            pc.add_dollcount1_itemid(itemId);
                            pc.setopendoll(itemId);
                        }
                    }
                }
                return;
            }
        }
    }
}

package com.lineage.data.item_etcitem.wand;

import java.util.Comparator;
import com.lineage.server.templates.L1Item;
import java.util.Map;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1Drop;
import java.util.HashMap;
import com.lineage.server.datatables.DropTable;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;
import java.text.DecimalFormat; 

public class Npc_Wand extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Npc_Wand();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int spellsc_objid = data[0];
        this.useNpcCarriedItemsWand(pc, spellsc_objid, item);
    }
    
    private void useNpcCarriedItemsWand(L1PcInstance pc, int targetId, L1ItemInstance item) {
        L1Object target = World.get().findObject(targetId);
        if (target == null) {
            return;
        }
        else {
            pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 17));
            if (target instanceof L1MonsterInstance) {
                L1MonsterInstance tgnpc = (L1MonsterInstance)target;
                ArrayList<L1Drop> dropList = DropTable.get().getdroplist(tgnpc.getNpcId());
                Map<Integer, String> msgstable = new HashMap<>();

                // 1. 整理怪物基本屬性
                msgstable.put(0, tgnpc.getName());
                msgstable.put(1, String.valueOf(tgnpc.getLevel()));
                msgstable.put(2, tgnpc.getCurrentHp() + " / " + tgnpc.getMaxHp());
                msgstable.put(3, tgnpc.getCurrentMp() + " / " + tgnpc.getMaxMp());
                msgstable.put(4, String.valueOf(tgnpc.getAc()));
                msgstable.put(5, String.valueOf(tgnpc.getMr()) + "%");
                msgstable.put(6, String.valueOf(tgnpc.getFire()) + "%");
                msgstable.put(7, String.valueOf(tgnpc.getWater()) + "%");
                msgstable.put(8, String.valueOf(tgnpc.getWind()) + "%");
                msgstable.put(9, String.valueOf(tgnpc.getEarth()) + "%");
                msgstable.put(10, String.valueOf(tgnpc.getStr()));
                msgstable.put(11, String.valueOf(tgnpc.getInt()));
                msgstable.put(12, String.valueOf(tgnpc.getCon()));
                msgstable.put(13, String.valueOf(tgnpc.getDex()));
                msgstable.put(14, String.valueOf(tgnpc.getWis()));
                msgstable.put(15, String.valueOf(tgnpc.getExp()));
                msgstable.put(16, String.valueOf(tgnpc.getDamageReduction())); 

                // 2. 整理掉落物清單
                if (dropList != null) {
                    dropList.sort(Comparator.comparingInt(L1Drop::getItemid));
                    
                    DecimalFormat df = new DecimalFormat("0.####"); 
                    
                    for (int i = 0; i < dropList.size(); ++i) {
                        L1Drop drop = dropList.get(i);
                        L1Item dropitem = ItemTable.get().getTemplate(drop.getItemid());
                        
                        // 計算機率 (10000 = 1%)
                        double chancePercent = (double) drop.getChance() / 10000.0;
                        String displayChance;
                        
                        // [修改] 判斷如果機率小於 0.01%，則模糊顯示
                        if (chancePercent < 0.1) {
                            displayChance = "0.1% >>";
                        } else {
                            displayChance = df.format(chancePercent) + "%";
                        }
                        
                        // 組合字串
                        msgstable.put(i + 17, dropitem.getName() + " - " + displayChance); 
                    }
                }
                else {
                    pc.sendPackets(new S_SystemMessage("此怪物沒有掉寶資料。"));
                }

                // 3. 組合所有資訊並發送封包
                String[] msgs = new String[msgstable.size()];
                for (int i = 0; i < msgstable.size(); ++i) {
                    msgs[i] = msgstable.get(i);
                }
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "mobdroplist", msgs));
            }
            else {
                pc.sendPackets(new S_SystemMessage("只能對怪物使用。"));
            }
            return;
        }
    }
}
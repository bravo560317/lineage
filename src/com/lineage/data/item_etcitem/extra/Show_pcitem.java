package com.lineage.data.item_etcitem.extra;

import com.lineage.server.serverpackets.S_ShopSellList;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Show_pcitem extends ItemExecutor
{
    private static Show_pcitem _instance;
    private static L1PcInstance tgpc;
    private int _del;
    
    public static Show_pcitem get() {
        if (Show_pcitem._instance == null) {
            Show_pcitem._instance = new Show_pcitem();
        }
        return Show_pcitem._instance;
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance useItem) {
        int spellsc_objid = data[0];
        L1Object target = World.get().findObject(spellsc_objid);
        if (pc.getMapId() == 5153) {
            pc.sendPackets(new S_ServerMessage("此地圖無法使用"));
            return;
        }
        else {
            if (target != null) {
                if (target instanceof L1PcInstance) {
                    String msg0 = "基本資料";
                    String msg2 = "玩家名稱";
                    String msg3 = "等級";
                    String msg4 = "現在HP/最大HP";
                    String msg5 = "現在MP/最大MP";
                    String msg6 = "防禦";
                    String msg7 = "魔防";
                    String msg8 = "力量";
                    String msg9 = "敏捷";
                    String msg10 = "體力";
                    String msg11 = "智力";
                    String msg12 = "精神";
                    String msg13 = "魅力";
                    String msg14 = "魔攻";
                    String msg15 = "抗火";
                    String msg16 = "抗風";
                    String msg17 = "抗地";
                    String msg18 = "抗水";
                    L1PcInstance target_pc = Show_pcitem.tgpc = (L1PcInstance)target;
                    msg2 = new StringBuilder().append(target_pc.getName()).toString();
                    msg3 = new StringBuilder().append(target_pc.getLevel()).toString();
                    msg4 = target_pc.getCurrentHp() + " / " + target_pc.getMaxHp();
                    msg5 = target_pc.getCurrentMp() + " / " + target_pc.getMaxMp();
                    msg6 = new StringBuilder().append(target_pc.getAc()).toString();
                    msg7 = target_pc.getMr() + " %";
                    msg8 = new StringBuilder().append((int)target_pc.getStr()).toString();
                    msg9 = new StringBuilder().append((int)target_pc.getDex()).toString();
                    msg10 = new StringBuilder().append((int)target_pc.getCon()).toString();
                    msg11 = new StringBuilder().append((int)target_pc.getInt()).toString();
                    msg12 = new StringBuilder().append((int)target_pc.getWis()).toString();
                    msg13 = new StringBuilder().append((int)target_pc.getCha()).toString();
                    msg14 = new StringBuilder().append(target_pc.getSp()).toString();
                    msg15 = new StringBuilder().append(target_pc.getFire()).toString();
                    msg16 = new StringBuilder().append(target_pc.getWind()).toString();
                    msg17 = new StringBuilder().append(target_pc.getEarth()).toString();
                    msg18 = new StringBuilder().append(target_pc.getWater()).toString();
                    String[] msg19 = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18 };
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "pcitem_1", msg19));
                    if (this._del > 0) {
                        pc.getInventory().removeItem(useItem, 1L);
                    }
                }
                else {
                    pc.sendPackets(new S_SystemMessage("只能使用在玩家身上"));
                }
            }
            return;
        }
    }
    
    public boolean PcCommand(L1PcInstance pc, String cmd) {
        if (cmd.equalsIgnoreCase("showpcequ")) {
            pc.sendPackets(new S_ShopSellList(Show_pcitem.tgpc, false));
            return true;
        }
        else {
            return false;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._del = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}

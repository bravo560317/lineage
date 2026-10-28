package com.lineage.data.npc;

import com.lineage.william.daily_bonus;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_dailybonus extends NpcExecutor
{
    private Npc_dailybonus() {
    }
    
    public static NpcExecutor get() {
        return new Npc_dailybonus();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        int vipDay = pc.getQuest().get_step(8992);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dailybonus", new String[] { 
            String.valueOf(pc.getday()), 
            String.valueOf(vipDay) 
        }));
    }
    
    @Override
    public void action(L1PcInstance _pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            // 1. 一般簽到動作
            if (cmd.equalsIgnoreCase("dailybonusok")) {
                int maxDay = daily_bonus.getMaxDay();
                if (_pc.getday() >= maxDay) {
                    _pc.sendPackets(new S_SystemMessage("一般簽到已全部完成！"));
                    return;
                }
                else if (_pc.getQuest().get_step(8991) > 0) {
                    _pc.sendPackets(new S_SystemMessage("今日一般簽到已完成"));
                    return;
                }
                else if (_pc.getLevel() < ConfigOther.day_level) {
                    _pc.sendPackets(new S_SystemMessage("等級不足:[" + ConfigOther.day_level + "].無法簽到"));
                    return;
                }
                else if (_pc.getInventory().getWeight240() >= 197) {
                    _pc.sendPackets(new S_ServerMessage(110));
                    return;
                }
                else if (_pc.getInventory().getSize() >= 170) {
                    _pc.sendPackets(new S_SystemMessage("身上物品太多無法進行簽到"));
                    return;
                }
                
                _pc.setday(_pc.getday() + 1);
                daily_bonus.dailybonus(_pc);
            }
            
            // 2. 高級 VIP 簽到動作（同步等級限制與各項條件）
            else if (cmd.equalsIgnoreCase("vipbonusok")) {
                if (!daily_bonus.isVip(_pc.getId())) {
                    _pc.sendPackets(new S_SystemMessage("您尚未具備 VIP 資格，無法進行高級簽到。"));
                    return;
                }
                
                int maxVipDay = daily_bonus.getMaxVipDay();
                int currentVipDay = _pc.getQuest().get_step(8992);
                
                if (currentVipDay >= maxVipDay) {
                    _pc.sendPackets(new S_SystemMessage("高級簽到已全部完成！"));
                    return;
                }
                else if (_pc.getQuest().get_step(8993) > 0) {
                    _pc.sendPackets(new S_SystemMessage("今日高級簽到已完成"));
                    return;
                }
                else if (_pc.getLevel() < ConfigOther.day_level) {
                    _pc.sendPackets(new S_SystemMessage("等級不足:[" + ConfigOther.day_level + "].無法進行高級簽到"));
                    return;
                }
                else if (_pc.getInventory().getWeight240() >= 197) {
                    _pc.sendPackets(new S_ServerMessage(110));
                    return;
                }
                else if (_pc.getInventory().getSize() >= 170) {
                    _pc.sendPackets(new S_SystemMessage("身上物品太多無法進行高級簽到"));
                    return;
                }
                
                int nextVipDay = currentVipDay + 1;
                daily_bonus.dailybonusVip(_pc, nextVipDay);
            }
        }
        catch (Exception ex) {}
    }
}
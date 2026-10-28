package com.lineage.data.npc.event;

import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.L1ActionPc;
import com.lineage.server.utils.CalcInitHpMp;
import com.lineage.data.event.BaseResetSet;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.lock.CharBuffReading;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_BaseReset2 extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_BaseReset2.class);
    }
    
    private Npc_BaseReset2() {
    }
    
    public static NpcExecutor get() {
        return new Npc_BaseReset2();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getMeteLevel() > 0) {
                pc.sendPackets(new S_ServerMessage("回憶蠟燭使用轉身限制0轉"));
                return;
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "baseReset"));
            }
        }
        catch (Exception e) {
            Npc_BaseReset2._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            if (cmd.equalsIgnoreCase("ent")) {
                if (!pc.getInventory().checkItem(49142)) {
                    pc.sendPackets(new S_ServerMessage(1290));
                    return;
                }
                else {
                    pc.setoldexp(pc.getExp());
                    pc.setInCharReset(true);
                    pc.clearAllSkill();
                    CharBuffReading.get().deleteBuff(pc);
                    pc.getInventory().takeoffEquip(945);
                    L1Teleport.teleport(pc, 32737, 32789, (short)997, 4, false);
                    pc.sendPackets(new S_Paralysis(4, true));
                    pc.setTempMaxLevel(pc.getHighLevel());
                    pc.setTempLevel(1);
                    pc.setTempInitPoint(0);
                    pc.setTempElixirstats(0);
                    if (pc.isCrown()) {
                        pc.setTempStr(13);
                        pc.setTempDex(10);
                        pc.setTempCon(10);
                        pc.setTempWis(11);
                        pc.setTempCha(13);
                        pc.setTempInt(10);
                    }
                    else if (pc.isKnight()) {
                        pc.setTempStr(16);
                        pc.setTempDex(12);
                        pc.setTempCon(14);
                        pc.setTempWis(9);
                        pc.setTempCha(12);
                        pc.setTempInt(8);
                    }
                    else if (pc.isElf()) {
                        pc.setTempStr(11);
                        pc.setTempDex(12);
                        pc.setTempCon(12);
                        pc.setTempWis(12);
                        pc.setTempCha(9);
                        pc.setTempInt(12);
                    }
                    else if (pc.isWizard()) {
                        pc.setTempStr(8);
                        pc.setTempDex(7);
                        pc.setTempCon(12);
                        pc.setTempWis(12);
                        pc.setTempCha(8);
                        pc.setTempInt(12);
                    }
                    else if (pc.isDarkelf()) {
                        pc.setTempStr(12);
                        pc.setTempDex(15);
                        pc.setTempCon(8);
                        pc.setTempWis(10);
                        pc.setTempCha(9);
                        pc.setTempInt(11);
                    }
                    else if (pc.isDragonKnight()) {
                        pc.setTempStr(13);
                        pc.setTempDex(11);
                        pc.setTempCon(14);
                        pc.setTempWis(12);
                        pc.setTempCha(8);
                        pc.setTempInt(11);
                    }
                    else if (pc.isIllusionist()) {
                        pc.setTempStr(11);
                        pc.setTempDex(10);
                        pc.setTempCon(12);
                        pc.setTempWis(12);
                        pc.setTempCha(8);
                        pc.setTempInt(12);
                    }
                    pc.setExp(1L);
                    pc.resetLevel();
                    int hp = 0;
                    int mp = 0;
                    if (BaseResetSet.RETAIN != 0) {
                        hp = pc.getMaxHp() * BaseResetSet.RETAIN / 100;
                        mp = pc.getMaxMp() * BaseResetSet.RETAIN / 100;
                    }
                    else {
                        hp = CalcInitHpMp.calcInitHp(pc);
                        mp = CalcInitHpMp.calcInitMp(pc);
                    }
                    L1ActionPc.initCharStatus(pc, hp, mp, pc.getTempStr(), pc.getTempInt(), pc.getTempWis(), pc.getTempDex(), pc.getTempCon(), pc.getTempCha());
                    pc.sendPackets(new S_SPMR(pc));
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_PacketBox(132, pc.getEr()));
                    L1ActionPc.checkInitPower(pc);
                }
            }
        }
        catch (Exception e) {
            Npc_BaseReset2._log.error(e.getLocalizedMessage(), e);
        }
    }
}

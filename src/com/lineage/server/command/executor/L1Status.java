package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_Lawful;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Status implements L1CommandExecutor
{
    private static final Log _log;
    private static final int _max_int = 20000;
    
    static {
        _log = LogFactory.getLog(L1Status.class);
    }
    
    private L1Status() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Status();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc == null) {
                L1Status._log.warn("系統命令執行: " + cmdName + " " + arg + " 重置指定人物屬性。");
            }
            StringTokenizer st = new StringTokenizer(arg);
            String char_name = st.nextToken();
            String param = st.nextToken();
            int value = Integer.parseInt(st.nextToken());
            String e1 = "指令異常: 指定人物不在線上，這個命令必須輸入正確人物名稱才能執行。";
            L1PcInstance target = null;
            if (char_name.equalsIgnoreCase("me")) {
                if (pc == null) {
                    L1Status._log.error("指令異常: 指定人物不在線上，這個命令必須輸入正確人物名稱才能執行。");
                    return;
                }
                else {
                    target = pc;
                }
            }
            else {
                target = World.get().getPlayer(char_name);
            }
            if (target == null) {
                if (pc == null) {
                    L1Status._log.error("指令異常: 指定人物不在線上，這個命令必須輸入正確人物名稱才能執行。");
                    return;
                }
                else {
                    pc.sendPackets(new S_ServerMessage(73, char_name));
                    return;
                }
            }
            else {
                if (param.equalsIgnoreCase("AC")) {
                    target.addAc((int)(byte)(value - target.getAc()));
                }
                else if (param.equalsIgnoreCase("MR")) {
                    target.addMr((int)(short)(value - target.getMr()));
                    target.sendPackets(new S_SPMR(target));
                }
                else if (param.equalsIgnoreCase("HIT")) {
                    target.addHitup((int)(short)(value - target.getHitup()));
                }
                else if (param.equalsIgnoreCase("DMG")) {
                    target.addDmgup((int)(short)(value - target.getDmgup()));
                }
                else if (param.equalsIgnoreCase("SP")) {
                    target.addSp((int)(short)(value - target.getSp()));
                    target.sendPackets(new S_SPMR(target));
                }
                else {
                    if (param.equalsIgnoreCase("HP")) {
                        if (value > 20000) {
                            value = 20000;
                        }
                        int maxHP = value - target.getBaseMaxHp();
                        if (target.getBaseMaxHp() + maxHP > 20000) {
                            maxHP = 20000 - target.getBaseMaxHp();
                        }
                        target.addBaseMaxHp((short)maxHP);
                        target.setCurrentHpDirect(target.getMaxHp());
                    }
                    else if (param.equalsIgnoreCase("MP")) {
                        if (value > 20000) {
                            value = 20000;
                        }
                        int maxMP = value - target.getBaseMaxMp();
                        if (target.getBaseMaxMp() + maxMP > 20000) {
                            maxMP = 20000 - target.getBaseMaxMp();
                        }
                        target.addBaseMaxMp((short)maxMP);
                        target.setCurrentMpDirect(target.getMaxMp());
                    }
                    else if (param.equalsIgnoreCase("LAWFUL") || param.equalsIgnoreCase("L")) {
                        target.setLawful(value);
                        target.sendPacketsAll(new S_Lawful(target));
                    }
                    else if (param.equalsIgnoreCase("KARMA") || param.equalsIgnoreCase("K")) {
                        target.setKarma(value);
                    }
                    else if (param.equalsIgnoreCase("GM")) {
                        if (value > 200) {
                            value = 200;
                        }
                        target.setAccessLevel((short)value);
                        target.sendPackets(new S_SystemMessage("取得GM權限"));
                    }
                    else if (param.equalsIgnoreCase("STR")) {
                        target.addBaseStr((int)(byte)(value - target.getBaseStr()));
                    }
                    else if (param.equalsIgnoreCase("CON")) {
                        target.addBaseCon((int)(byte)(value - target.getBaseCon()));
                    }
                    else if (param.equalsIgnoreCase("DEX")) {
                        target.addBaseDex((int)(byte)(value - target.getBaseDex()));
                    }
                    else if (param.equalsIgnoreCase("INT")) {
                        target.addBaseInt((int)(byte)(value - target.getBaseInt()));
                        target.sendPackets(new S_SPMR(target));
                    }
                    else if (param.equalsIgnoreCase("WIS")) {
                        target.addBaseWis((int)(byte)(value - target.getBaseWis()));
                    }
                    else if (param.equalsIgnoreCase("CHA")) {
                        target.addBaseCha((int)(byte)(value - target.getBaseCha()));
                    }
                    else {
                        String e2 = "指令異常: 指令 " + param + " 不明確";
                        if (pc == null) {
                            L1Status._log.error(e2);
                            return;
                        }
                        else {
                            pc.sendPackets(new S_SystemMessage(e2));
                            return;
                        }
                    }
                    target.save();
                }
                target.sendPackets(new S_OwnCharStatus(target));
                String ok = target.getName() + "的" + param + "屬性" + value + "變更完成";
                if (pc == null) {
                    L1Status._log.info(ok);
                    return;
                }
                else {
                    pc.sendPackets(new S_SystemMessage(ok));
                }
            }
        }
        catch (Exception e3) {
            if (pc == null) {
                L1Status._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1Status._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}

package com.lineage.server.clientpackets;

import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.model.skill.L1SkillMode;
import java.util.Iterator;
import com.lineage.server.model.L1Party;
import java.util.concurrent.RejectedExecutionException;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.server.command.executor.L1AllBuff;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1Location;
import com.lineage.server.datatables.lock.CharBuffReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_CallPlayer extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_CallPlayer.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGm()) {
                return;
            }
            else {
                String arg = this.readS();
                StringTokenizer stringtokenizer = new StringTokenizer(arg, ":");
                int mode = pc.getTempID();
                try {
                    int mapid = Integer.parseInt(stringtokenizer.nextToken());
                    String name = stringtokenizer.nextToken();
                    if (name.isEmpty()) {
                        return;
                    }
                    else {
                        L1PcInstance target = World.get().getPlayer(name);
                        if (target == null) {
                            pc.sendPackets(new S_ServerMessage(73, name));
                            return;
                        }
                        else {
                            switch (mode) {
                                case 0: {
                                    target.clearAllSkill();
                                    CharBuffReading.get().deleteBuff(target);
                                    this.stopSkill(target);
                                    pc.sendPackets(new S_ServerMessage(166, target.getName() + " Buff清除!"));
                                    break;
                                }
                                case 1: {
                                    L1Location loc = L1Location.randomLocation(target.getLocation(), 1, 2, false);
                                    pc.set_showId(target.get_showId());
                                    L1Teleport.teleport(pc, loc.getX(), loc.getY(), target.getMapId(), pc.getHeading(), false);
                                    pc.sendPackets(new S_ServerMessage(166, "移動座標至指定人物身邊: " + name));
                                    break;
                                }
                                case 2: {
                                    L1Location gmloc = L1Location.randomLocation(pc.getLocation(), 1, 2, false);
                                    L1Teleport.teleport(target, gmloc.getX(), gmloc.getY(), pc.getMapId(), target.getHeading(), false);
                                    pc.sendPackets(new S_ServerMessage(166, "召回指定人物: " + name));
                                    break;
                                }
                                case 3: {
                                    L1Party party = target.getParty();
                                    if (party != null) {
                                        int x = pc.getX();
                                        int y = pc.getY() + 2;
                                        short map = pc.getMapId();
                                        HashMap<Integer, L1PcInstance> pcs = new HashMap();
                                        pcs.putAll(party.partyUsers());
                                        if (pcs.isEmpty()) {
                                            return;
                                        }
                                        else if (pcs.size() <= 0) {
                                            return;
                                        }
                                        else {
                                            for (L1PcInstance pc2 : pcs.values()) {
                                                try {
                                                    L1Teleport.teleport(pc2, x, y, map, 5, true);
                                                    pc2.sendPackets(new S_SystemMessage("管理員召喚!"));
                                                }
                                                catch (Exception ex) {}
                                            }
                                            pcs.clear();
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 4: {
                                    L1AllBuff.startPc(target);
                                    break;
                                }
                                case 5: {
                                    pc.sendPackets(new S_SystemMessage(target.getName() + " 踢除下線。"));
                                    target.getNetConnection().kick();
                                    break;
                                }
                                case 6: {
                                    StringBuilder ipaddr = target.getNetConnection().getIp();
                                    StringBuilder macaddr = target.getNetConnection().getMac();
                                    if (ipaddr != null) {
                                        IpReading.get().add(ipaddr.toString(), "GM命令:L1PowerKick 封鎖");
                                    }
                                    if (macaddr != null) {
                                        IpReading.get().add(macaddr.toString(), "GM命令:L1PowerKick 封鎖");
                                    }
                                    pc.sendPackets(new S_SystemMessage(target.getName() + " 封鎖IP/MAC。"));
                                    target.getNetConnection().kick();
                                    break;
                                }
                                case 7: {
                                    IpReading.get().add(target.getAccountName(), "GM命令:L1AccountBanKick 封鎖帳號");
                                    pc.sendPackets(new S_SystemMessage(target.getName() + " 帳號封鎖。"));
                                    target.getNetConnection().kick();
                                    break;
                                }
                                case 8: {
                                    target.setCurrentHp(0);
                                    target.death(null);
                                    pc.sendPackets(new S_SystemMessage(target.getName() + " 人物死亡。"));
                                    break;
                                }
                            }
                        }
                    }
                }
                catch (RejectedExecutionException e) {
                    String name = arg;
                    if (name.isEmpty()) {
                        return;
                    }
                    else {
                        L1PcInstance target = World.get().getPlayer(name);
                        if (target == null) {
                            pc.sendPackets(new S_ServerMessage(73, name));
                            return;
                        }
                        else {
                            L1Location loc = L1Location.randomLocation(target.getLocation(), 1, 2, false);
                            L1Teleport.teleport(pc, loc.getX(), loc.getY(), target.getMapId(), pc.getHeading(), false);
                            pc.sendPackets(new S_ServerMessage(166, "移動座標至指定人物身邊: " + name));
                        }
                    }
                }
            }
        }
        catch (Exception ex2) {}
        finally {
            this.over();
        }
    }
    
    private void stopSkill(L1PcInstance pc) {
        for (int skillNum = 1; skillNum <= 220; ++skillNum) {
            if (!L1SkillMode.get().isNotCancelable(skillNum) || pc.isDead()) {
                pc.removeSkillEffect(skillNum);
            }
        }
        pc.curePoison();
        pc.cureParalaysis();
        for (int skillNum = 998; skillNum <= 1027; ++skillNum) {
            pc.removeSkillEffect(skillNum);
        }
        for (int skillNum = 3000; skillNum <= 3051; ++skillNum) {
            if (!L1SkillMode.get().isNotCancelable(skillNum)) {
                pc.removeSkillEffect(skillNum);
            }
        }
        if (pc.getHasteItemEquipped() > 0) {
            pc.setMoveSpeed(0);
            pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
        }
        pc.removeSkillEffect(4000);
        pc.sendPacketsAll(new S_CharVisualUpdate(pc));
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

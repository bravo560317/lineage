package com.lineage.server.clientpackets;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.model.L1Clan;
import com.lineage.server.templates.L1BookMark;
import com.lineage.server.templates.L1BookConfig;
import java.util.Iterator;
import com.lineage.server.model.L1Object;
import java.util.Calendar;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.datatables.lock.CharBookConfigReading;
import com.lineage.server.serverpackets.S_PacketBoxLoc;
import com.lineage.server.serverpackets.S_MapTimerOut;
import com.lineage.server.utils.L1SpawnUtil;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Windows extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Windows.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            int type = this.readC();
            switch (type) {
                case 0: {
                    int targetId = this.readD();
                    L1Object temp = World.get().findObject(targetId);
                    if (temp instanceof L1PcInstance) {
                        L1PcInstance targetPc = (L1PcInstance)temp;
                        if (pc.getId() == targetPc.getId()) {
                            pc.sendPackets(new S_ServerMessage(3130));
                        }
                        else if (pc.getLevel() < 10) {
                            pc.sendPackets(new S_ServerMessage(3129));
                        }
                        else {
                            for (L1PcInstance object43 : World.get().getAllPlayers()) {
                                Object visible = object43;
                                if (visible instanceof L1PcInstance) {
                                    L1PcInstance GM = (L1PcInstance)visible;
                                    if (GM.isGm() && pc.getId() != GM.getId()) {
                                        GM.sendPackets(new S_ServerMessage("\\fW玩家" + pc.getName() + " 申訴:(" + targetPc.getName() + ")使用外掛"));
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                            if (!pc.isGm()) {
                                pc.sendPackets(new S_SystemMessage("\\fT感謝您申訴，為了遊戲品質我們會嚴加監控"));
                                pc.sendPackets(new S_SystemMessage("\\fT系統立即回報給予線上GM以及備份回報!!"));
                                玩家檢舉("玩家:【 " + pc.getName() + " 】 " + "舉報玩家" + ":【 " + targetPc.getName() + " 】" + "為外掛使用者，請注意，" + "檢舉時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        return;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(1023));
                        break;
                    }
                }
                case 6: {
                    int itemobjid = this.readD();
                    int selectdoor = this.readD();
                    if (pc.getInventory().getItem(itemobjid) != null) {
                        switch (selectdoor) {
                            case 0: {
                                if (pc.getInventory().consumeItem(47010, 1L)) {
                                    L1SpawnUtil.spawn(pc, 70932, 0, 7200);
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 1: {
                                if (pc.getInventory().consumeItem(47010, 1L)) {
                                    L1SpawnUtil.spawn(pc, 70937, 0, 7200);
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 2: {
                                if (pc.getInventory().consumeItem(47010, 1L)) {
                                    L1SpawnUtil.spawn(pc, 70934, 0, 7200);
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 3: {
                                pc.sendPackets(new S_SystemMessage("該副本尚未實裝。"));
                                break;
                            }
                            default: {
                                return;
                            }
                        }
                    }
                    else {
                        break;
                    }
                }
                case 9: {
                    pc.sendPackets(new S_MapTimerOut(pc));
                    break;
                }
                case 11: {
                    String name = this.readS();
                    int mapid = this.readH();
                    int x = this.readH();
                    int y = this.readH();
                    int zone = this.readD();
                    L1PcInstance target = World.get().getPlayer(name);
                    if (target != null) {
                        target.sendPackets(new S_PacketBoxLoc(pc.getName(), mapid, x, y, zone));
                        pc.sendPackets(new S_ServerMessage(1783, name));
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(1782));
                        break;
                    }
                }
                case 34: {
                    byte[] data = this.readBytes();
                    L1BookConfig config = CharBookConfigReading.get().get(pc.getId());
                    if (config == null) {
                        CharBookConfigReading.get().storeCharBookConfig(pc.getId(), data);
                        break;
                    }
                    else {
                        CharBookConfigReading.get().updateCharBookConfig(pc.getId(), data);
                        break;
                    }
                }
                case 39: {
                    for (int changeCount = this.readD(), i = 0; i < changeCount; ++i) {
                        int bookId = this.readD();
                        L1BookMark bookm = CharBookReading.get().getBookMark(pc, bookId);
                        if (bookm != null) {
                            String changeName = this.readS();
                            bookm.setName(changeName);
                            CharBookReading.get().updateBookmarkName(bookm);
                        }
                    }
                    break;
                }
                case 44: {
                    pc.setKillCount(0);
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    break;
                }
                case 46: {
                    if (pc.getClanRank() != 4 && pc.getClanRank() != 10) {
                        return;
                    }
                    else {
                        int emblemStatus = this.readC();
                        L1Clan clan = pc.getClan();
                        clan.setEmblemStatus(emblemStatus);
                        ClanReading.get().updateClan(clan);
                        L1PcInstance[] onlineClanMember;
                        for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, j = 0; j < length; ++j) {
                            L1PcInstance member = onlineClanMember[j];
                            member.sendPackets(new S_PacketBox(173, emblemStatus));
                        }
                        break;
                    }
                }
                case 48: {
                    int mapIndex = this.readH();
                    int point = this.readH();
                    int locx = 0;
                    int locy = 0;
                    if (mapIndex == 1) {
                        if (point == 0) {
                            locx = 34079 + (int)(Math.random() * 12.0);
                            locy = 33136 + (int)(Math.random() * 15.0);
                        }
                        else if (point == 1) {
                            locx = 33970 + (int)(Math.random() * 10.0);
                            locy = 33243 + (int)(Math.random() * 14.0);
                        }
                        else if (point == 2) {
                            locx = 33925 + (int)(Math.random() * 14.0);
                            locy = 33351 + (int)(Math.random() * 9.0);
                        }
                    }
                    else if (mapIndex == 2) {
                        if (point == 0) {
                            locx = 32615 + (int)(Math.random() * 11.0);
                            locy = 32719 + (int)(Math.random() * 7.0);
                        }
                        else if (point == 1) {
                            locx = 32621 + (int)(Math.random() * 9.0);
                            locy = 32788 + (int)(Math.random() * 13.0);
                        }
                    }
                    else if (mapIndex == 3) {
                        if (point == 0) {
                            locx = 33501 + (int)(Math.random() * 11.0);
                            locy = 32765 + (int)(Math.random() * 9.0);
                        }
                        else if (point == 1) {
                            locx = 33440 + (int)(Math.random() * 11.0);
                            locy = 32784 + (int)(Math.random() * 11.0);
                        }
                    }
                    else if (mapIndex == 4) {
                        int[][] loc = { { 32838, 32886 }, { 32800, 32874 }, { 32755, 32899 }, { 32741, 32938 }, { 32740, 32964 }, { 32801, 32982 }, { 32845, 32986 }, { 32852, 32932 }, { 32799, 32927 } };
                        locx = loc[point][0];
                        locy = loc[point][1];
                    }
                    L1Teleport.teleport(pc, locx, locy, pc.getMapId(), pc.getHeading(), true);
                    pc.sendPackets(new S_PacketBox(176, pc));
                    break;
                }
            }
            if (pc != null && pc.get_mazu_time() != 0L && pc.is_mazu()) {
                Calendar cal = Calendar.getInstance();
                long h_time = cal.getTimeInMillis() / 1000L;
                if (h_time - pc.get_mazu_time() >= 1800L) {
                    pc.set_mazu_time(0L);
                    pc.set_mazu(false);
                }
            }
        }
        catch (Exception e) {
            C_Windows._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this.over();
        }
    }
    
    public static void 玩家檢舉(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[玩家檢舉].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

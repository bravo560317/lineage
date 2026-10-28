package com.lineage.server.command;

import java.util.List;
import com.lineage.server.templates.L1Skills;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ItemError;
import com.lineage.server.serverpackets.S_ChatOut;
import com.lineage.server.serverpackets.S_ChangeShape;
import com.lineage.server.serverpackets.S_WarTime;
import com.lineage.server.serverpackets.S_TaxRate;
import com.lineage.server.serverpackets.S_Drawal;
import com.lineage.server.serverpackets.S_Deposit;
import com.lineage.server.serverpackets.S_SelectTarget;
import com.lineage.server.serverpackets.S_TradeStatus;
import com.lineage.server.serverpackets.S_Trade;
import com.lineage.server.serverpackets.S_FixWeaponList;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.serverpackets.S_DelSkill;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_CurseBlind;
import com.lineage.server.serverpackets.S_Strup;
import com.lineage.server.serverpackets.S_Dexup;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_Invis;
import com.lineage.server.serverpackets.S_SkillIconBlessOfEva;
import com.lineage.server.serverpackets.S_TrueTarget;
import com.lineage.server.serverpackets.S_SkillIconShield;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.serverpackets.S_BoardRead;
import com.lineage.server.serverpackets.S_Board;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_Lawful;
import com.lineage.server.serverpackets.S_Exp;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.serverpackets.S_Weather;
import com.lineage.server.serverpackets.S_Light;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.serverpackets.S_IdentifyDesc;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.server.serverpackets.S_Bonusstats;
import com.lineage.server.serverpackets.S_War;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.L1Location;
import com.lineage.server.serverpackets.S_DoActionShop;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_Resurrection;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_HPMeter;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.serverpackets.S_PinkName;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_NewMaster;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_ChatWhisperFrom;
import com.lineage.server.serverpackets.S_Chat;
import com.lineage.server.serverpackets.S_ChatGlobal;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_CastleMaster;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.serverpackets.OpcodesServer;

public class SOpTest extends OpcodesServer
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(SOpTest.class);
    }
    
    public static void testOpA(L1PcInstance pc, int opid) throws Exception {
        try {
            World.get().broadcastPacketToAll(new S_CastleMaster(opid, pc.getId()));
            System.out.println(String.valueOf(opid) + "/ " + pc.getId());
        }
        catch (Exception e) {
            SOpTest._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void testOp(L1PcInstance pc, int opid) throws Exception {
        if (opid != 139 && opid != 233 && opid != 223 && opid != 48) {
            if (opid == 250) {
                pc.sendPackets(new S_PacketBox(56, 1800));
            }
            else if (opid == 227) {
                pc.sendPackets(new S_Disconnect());
            }
            else if (opid == 243) {
                pc.sendPackets(new S_ChatGlobal(pc, "廣播頻道測試"));
            }
            else if (opid == 81) {
                pc.sendPackets(new S_Chat(pc, "一般頻道測試"));
            }
            else if (opid == 73) {
                pc.sendPackets(new S_ChatWhisperFrom(pc, "密語交談(接收)頻道測試"));
            }
            else if (opid == 161) {
                for (L1Object obj : pc.getKnownObjects()) {
                    if (obj instanceof L1NpcInstance) {
                        L1NpcInstance npc = (L1NpcInstance)obj;
                        npc.broadcastPacketX8(new S_NpcChat(npc, pc.getName() + " 打3小~~"));
                    }
                }
            }
            else if (opid == 88) {
                for (L1Object obj : pc.getKnownObjects()) {
                    if (obj instanceof L1NpcInstance) {
                        L1NpcInstance npc = (L1NpcInstance)obj;
                        npc.broadcastPacketX8(new S_NewMaster(pc.getName(), npc));
                    }
                }
            }
            else if (opid != 178 && opid != 93 && opid != 98 && opid != 127 && opid != 6) {
                if (opid == 8) {
                    pc.sendPackets(new S_OwnCharStatus(pc, 101));
                }
                else if (opid == 155) {
                    pc.sendPackets(new S_OwnCharStatus2(pc, 103));
                }
                else if (opid != 118) {
                    if (opid == 183) {
                        StringBuilder title = new StringBuilder();
                        title.append("\\f=測試角色封號");
                        pc.sendPackets(new S_CharTitle(pc.getId(), title));
                    }
                    else if (opid == 60) {
                        for (L1Object obj : pc.getKnownObjects()) {
                            if (obj instanceof L1NpcInstance) {
                                L1NpcInstance npc = (L1NpcInstance)obj;
                                npc.broadcastPacketX8(new S_PinkName(npc.getId(), 1000));
                            }
                        }
                        pc.sendPacketsAll(new S_PinkName(pc.getId(), 1000));
                    }
                    else if (opid == 69) {
                        pc.sendPackets(new S_CastleMaster(5, pc.getId()));
                    }
                    else if (opid == 64) {
                        pc.sendPackets(new S_CharReset(pc));
                    }
                    else if (opid != 87) {
                        if (opid == 120) {
                            for (L1Object obj : pc.getKnownObjects()) {
                                if (obj instanceof L1NpcInstance) {
                                    L1NpcInstance npc = (L1NpcInstance)obj;
                                    pc.sendPackets(new S_RemoveObject(npc));
                                }
                            }
                        }
                        else if (opid == 237) {
                            for (L1Object obj : pc.getKnownObjects()) {
                                if (obj instanceof L1NpcInstance) {
                                    L1NpcInstance npc = (L1NpcInstance)obj;
                                    pc.sendPackets(new S_HPMeter(npc.getId(), 100 * npc.getCurrentHp() / npc.getMaxHp()));
                                }
                            }
                        }
                        else if (opid != 209 && opid != 15 && opid != 57 && opid != 240) {
                            if (opid == 85) {
                                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 230));
                                pc.resurrect(pc.getMaxHp());
                                pc.setCurrentHp(pc.getMaxHp());
                                pc.startHpRegeneration();
                                pc.startMpRegeneration();
                                pc.stopPcDeleteTimer();
                                pc.sendPacketsAll(new S_Resurrection(pc, pc, 0));
                                pc.sendPacketsAll(new S_CharVisualUpdate(pc));
                                if (pc.getExpRes() == 1 && pc.isGres() && pc.isGresValid()) {
                                    pc.resExp();
                                    pc.setExpRes(0);
                                    pc.setGres(false);
                                }
                            }
                            else if (opid != 10 && opid != 30 && opid != 119) {
                                if (opid == 158) {
                                    pc.sendPackets(new S_DoActionShop(pc.getId(), "物件動作種類(短時間)"));
                                }
                                else if (opid == 55) {
                                    pc.sendPackets(new S_SkillSound(pc.getId(), 3819));
                                    for (int i = 7013; i < 7100; ++i) {
                                        System.out.println("i: " + i);
                                        pc.sendPackets(new S_SkillSound(pc.getId(), i, 150));
                                        Thread.sleep(500L);
                                    }
                                }
                                else if (opid == 106) {
                                    int x = pc.getX();
                                    int y = pc.getY();
                                    int mapId = pc.getMapId();
                                    pc.sendPackets(new S_EffectLocation(new L1Location(x - 2, y - 2, mapId), 4842));
                                    pc.sendPackets(new S_EffectLocation(new L1Location(x + 2, y - 2, mapId), 4842));
                                    pc.sendPackets(new S_EffectLocation(new L1Location(x + 2, y + 2, mapId), 4842));
                                    pc.sendPackets(new S_EffectLocation(new L1Location(x - 2, y + 2, mapId), 4842));
                                }
                                else if (opid != 42 && opid != 149 && opid != 186) {
                                    if (opid == 84) {
                                        pc.sendPackets(new S_War(7, "測試0血盟", "測試1血盟"));
                                    }
                                    else if (opid == 39) {
                                        pc.sendPackets(new S_Bonusstats(pc.getId()));
                                    }
                                    else if (opid == 136) {
                                        pc.sendPackets(new S_ItemCount(pc.getId(), 1000, "XXXX"));
                                    }
                                    else if (opid == 71) {
                                        pc.sendPackets(new S_ServerMessage(96, "測試"));
                                    }
                                    else if (opid == 219) {
                                        pc.sendPackets(new S_Message_YN(748));
                                    }
                                    else if (opid == 245) {
                                        pc.sendPackets(new S_IdentifyDesc());
                                    }
                                    else if (opid == 105) {
                                        pc.sendPackets(new S_BlueMessage(552, "100", "3"));
                                    }
                                    else if (opid != 100 && opid != 24) {
                                        if (opid == 40) {
                                            pc.sendPackets(new S_Light(pc.getId(), 20));
                                        }
                                        else if (opid == 115) {
                                            pc.sendPackets(new S_Weather(19));
                                        }
                                        else if (opid == 122) {
                                            try {
                                                pc.setHeading(0);
                                                pc.sendPackets(new S_ChangeHeading(pc));
                                                Thread.sleep(500L);
                                                pc.setHeading(1);
                                                pc.sendPackets(new S_ChangeHeading(pc));
                                                Thread.sleep(500L);
                                                pc.setHeading(2);
                                                pc.sendPackets(new S_ChangeHeading(pc));
                                                Thread.sleep(500L);
                                                pc.setHeading(3);
                                                pc.sendPackets(new S_ChangeHeading(pc));
                                                Thread.sleep(500L);
                                                pc.setHeading(4);
                                                pc.sendPackets(new S_ChangeHeading(pc));
                                                Thread.sleep(500L);
                                                pc.setHeading(5);
                                                pc.sendPackets(new S_ChangeHeading(pc));
                                                Thread.sleep(500L);
                                                pc.setHeading(6);
                                                pc.sendPackets(new S_ChangeHeading(pc));
                                                Thread.sleep(500L);
                                                pc.setHeading(7);
                                                pc.sendPackets(new S_ChangeHeading(pc));
                                            }
                                            catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        else if (opid != 76) {
                                            if (opid == 46) {
                                                pc.sendPackets(new S_ChangeName(pc.getId(), "更新物件名稱"));
                                            }
                                            else if (opid == 225) {
                                                pc.sendPackets(new S_HPUpdate(32767, 32767));
                                            }
                                            else if (opid == 33) {
                                                pc.sendPackets(new S_MPUpdate(32767, 32767));
                                            }
                                            else if (opid != 206 && opid != 123) {
                                                if (opid == 113) {
                                                    pc.sendPackets(new S_Exp());
                                                }
                                                else if (opid == 34) {
                                                    pc.sendPackets(new S_Lawful(pc.getId()));
                                                }
                                                else if (opid == 174) {
                                                    pc.sendPackets(new S_OwnCharAttrDef());
                                                }
                                                else if (opid == 68) {
                                                    pc.sendPackets(new S_Board(pc.getId()));
                                                }
                                                else if (opid == 148) {
                                                    pc.sendPackets(new S_BoardRead());
                                                }
                                                else if (opid != 156 && opid != 187) {
                                                    if (opid == 165) {
                                                        pc.sendPackets(new S_Poison(pc.getId(), 1));
                                                        Thread.sleep(1000L);
                                                        pc.sendPackets(new S_Poison(pc.getId(), 2));
                                                        Thread.sleep(1000L);
                                                        pc.sendPackets(new S_Poison(pc.getId(), 0));
                                                    }
                                                    else if (opid == 67) {
                                                        pc.sendPackets(new S_SkillBrave(pc.getId(), 3));
                                                        Thread.sleep(1000L);
                                                        pc.sendPackets(new S_SkillBrave(pc.getId(), 5));
                                                        Thread.sleep(1000L);
                                                        pc.sendPackets(new S_SkillBrave(pc.getId(), 0));
                                                    }
                                                    else if (opid == 216) {
                                                        pc.sendPackets(new S_SkillIconShield(5, 3600));
                                                    }
                                                    else if (opid != 255) {
                                                        if (opid == 11) {
                                                            for (L1Object obj : pc.getKnownObjects()) {
                                                                if (obj instanceof L1NpcInstance) {
                                                                    L1NpcInstance npc = (L1NpcInstance)obj;
                                                                    npc.broadcastPacketX8(new S_TrueTarget(npc.getId(), pc.getId(), "魔法效果 精準目標"));
                                                                }
                                                            }
                                                        }
                                                        else if (opid == 126) {
                                                            pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), 300));
                                                        }
                                                        else if (opid == 171) {
                                                            pc.sendPackets(new S_Invis(pc.getId(), 1));
                                                            Thread.sleep(1000L);
                                                            pc.sendPackets(new S_Invis(pc.getId(), 0));
                                                        }
                                                        else if (opid == 103) {
                                                            pc.sendPackets(new S_Liquor(pc.getId(), 1));
                                                            Thread.sleep(1000L);
                                                            pc.sendPackets(new S_Liquor(pc.getId(), 2));
                                                            Thread.sleep(1000L);
                                                            pc.sendPackets(new S_Liquor(pc.getId(), 3));
                                                            Thread.sleep(1000L);
                                                            pc.sendPackets(new S_Liquor(pc.getId(), 0));
                                                        }
                                                        else if (opid == 202) {
                                                            pc.sendPackets(new S_Paralysis(2, true));
                                                        }
                                                        else if (opid == 188) {
                                                            pc.sendPackets(new S_Dexup(pc, 5, 1800));
                                                        }
                                                        else if (opid == 166) {
                                                            pc.sendPackets(new S_Strup(pc, 5, 1800));
                                                        }
                                                        else if (opid == 47) {
                                                            pc.sendPackets(new S_CurseBlind(1));
                                                            Thread.sleep(1000L);
                                                            pc.sendPackets(new S_CurseBlind(2));
                                                            Thread.sleep(1000L);
                                                            pc.sendPackets(new S_CurseBlind(0));
                                                        }
                                                        else if (opid != 5 && opid != 41 && opid != 41) {
                                                            if (opid == 164) {
                                                                for (int i = 0; i < 300; ++i) {
                                                                    L1Skills skill = SkillsTable.get().getTemplate(i);
                                                                    if (skill != null && skill.getSkillLevel() > 0) {
                                                                        pc.sendPackets(new S_DelSkill(pc, i));
                                                                    }
                                                                }
                                                                Thread.sleep(2000L);
                                                                System.out.println("TEST START!!");
                                                                int[] level = new int[28];
                                                                int dx = 1;
                                                                for (int mode = 0; mode < 28; ++mode) {
                                                                    while (dx < 255) {
                                                                        int[] array = level;
                                                                        int n = mode;
                                                                        array[n] += dx;
                                                                        pc.sendPackets(new S_AddSkill(pc, mode, level[mode]));
                                                                        System.out.println("dx: " + dx + " " + level[mode]);
                                                                        dx <<= 1;
                                                                        Thread.sleep(100L);
                                                                    }
                                                                    if (dx >= 255) {
                                                                        dx = 1;
                                                                    }
                                                                }
                                                            }
                                                            else if (opid == 160) {
                                                                for (int i = 0; i < 300; ++i) {
                                                                    L1Skills skill = SkillsTable.get().getTemplate(i);
                                                                    if (skill != null && skill.getSkillLevel() > 0) {
                                                                        pc.sendPackets(new S_DelSkill(pc, i));
                                                                    }
                                                                    Thread.sleep(10L);
                                                                }
                                                            }
                                                            else if (opid != 176 && opid != 92) {
                                                                if (opid == 83) {
                                                                    List<L1ItemInstance> weaponList = new ArrayList();
                                                                    List<L1ItemInstance> itemList = pc.getInventory().getItems();
                                                                    for (L1ItemInstance item : itemList) {
                                                                        switch (item.getItem().getType2()) {
                                                                            case 1: {
                                                                                if (item.get_durability() > 0) {
                                                                                    weaponList.add(item);
                                                                                    continue;
                                                                                }
                                                                                else {
                                                                                    continue;
                                                                                }
                                                                            }
                                                                            default: {
                                                                                continue;
                                                                            }
                                                                        }
                                                                    }
                                                                    pc.sendPackets(new S_FixWeaponList(weaponList));
                                                                }
                                                                else if (opid != 65 && opid != 70) {
                                                                    if (opid == 52) {
                                                                        pc.sendPackets(new S_Trade("測試交易封包"));
                                                                    }
                                                                    else if (opid == 112) {
                                                                        pc.sendPackets(new S_TradeStatus(1));
                                                                    }
                                                                    else if (opid != 140 && opid != 36 && opid != 22 && opid != 566 && opid != 241) {
                                                                        if (opid == 236) {
                                                                            pc.sendPackets(new S_SelectTarget(pc.getId()));
                                                                        }
                                                                        else if (opid == 4) {
                                                                            pc.sendPackets(new S_Deposit(pc.getId()));
                                                                        }
                                                                        else if (opid == 141) {
                                                                            pc.sendPackets(new S_Drawal(pc.getId(), 1234567L));
                                                                        }
                                                                        else if (opid == 185) {
                                                                            pc.sendPackets(new S_TaxRate(pc.getId()));
                                                                        }
                                                                        else if (opid == 231) {
                                                                            pc.sendPackets(new S_WarTime(231));
                                                                        }
                                                                        else if (opid != 105) {
                                                                            if (opid == 76) {
                                                                                for (L1Object obj : pc.getKnownObjects()) {
                                                                                    if (obj instanceof L1NpcInstance) {
                                                                                        L1NpcInstance npc = (L1NpcInstance)obj;
                                                                                        npc.broadcastPacketX8(new S_ChangeShape(pc, npc, 1080));
                                                                                    }
                                                                                }
                                                                                pc.sendPackets(new S_ChatOut(pc));
                                                                            }
                                                                            else if (opid != 88 && opid == 197) {
                                                                                for (int i = 0; i < 10; ++i) {
                                                                                    pc.sendPackets(new S_ItemError(i));
                                                                                    Thread.sleep(250L);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

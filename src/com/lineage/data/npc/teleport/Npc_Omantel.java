package com.lineage.data.npc.teleport;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Omantel extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Omantel();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "Omantel1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        short mapid = pc.getMapId();
        short newmapid = 0;
        int scroll = 0;
        int locx = 0;
        int locy = 0;
        if (cmd.equals("A")) {
            switch (mapid) {
                case 106: {
                    locx = 32800;
                    locy = 32800;
                    newmapid = 110;
                    scroll = 40308;
                    break;
                }
                case 116: {
                    locx = 32800;
                    locy = 32800;
                    newmapid = 120;
                    scroll = 40104;
                    break;
                }
                case 126: {
                    locx = 32800;
                    locy = 32800;
                    newmapid = 130;
                    scroll = 40105;
                    break;
                }
                case 136: {
                    locx = 32800;
                    locy = 32800;
                    newmapid = 140;
                    scroll = 40106;
                    break;
                }
                case 146: {
                    locx = 32796;
                    locy = 32796;
                    newmapid = 150;
                    scroll = 40107;
                    break;
                }
                case 156: {
                    locx = 32720;
                    locy = 32821;
                    newmapid = 160;
                    scroll = 40108;
                    break;
                }
                case 166: {
                    locx = 32720;
                    locy = 32821;
                    newmapid = 170;
                    scroll = 40109;
                    break;
                }
                case 176: {
                    locx = 32724;
                    locy = 32822;
                    newmapid = 180;
                    scroll = 40110;
                    break;
                }
                case 186: {
                    locx = 32722;
                    locy = 32827;
                    newmapid = 190;
                    scroll = 40111;
                    break;
                }
                case 196: {
                    locx = 32693;
                    locy = 32876;
                    newmapid = 200;
                    scroll = 40112;
                    break;
                }
            }
            L1ItemInstance item = pc.getInventory().checkItemX(scroll, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, locx, locy, newmapid, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "Omantel2"));
            }
        }
        else if (cmd.equals("B")) {
            switch (mapid) {
                case 106: {
                    locx = 32784;
                    locy = 32814;
                    newmapid = 101;
                    scroll = 40308;
                    break;
                }
                case 116: {
                    locx = 32630;
                    locy = 32935;
                    newmapid = 111;
                    scroll = 40104;
                    break;
                }
                case 126: {
                    locx = 32630;
                    locy = 32935;
                    newmapid = 121;
                    scroll = 40105;
                    break;
                }
                case 136: {
                    locx = 32630;
                    locy = 32935;
                    newmapid = 131;
                    scroll = 40106;
                    break;
                }
                case 146: {
                    locx = 32630;
                    locy = 32935;
                    newmapid = 141;
                    scroll = 40107;
                    break;
                }
                case 156: {
                    locx = 32630;
                    locy = 32935;
                    newmapid = 151;
                    scroll = 40108;
                    break;
                }
                case 166: {
                    locx = 32630;
                    locy = 32935;
                    newmapid = 161;
                    scroll = 40109;
                    break;
                }
                case 176: {
                    locx = 32630;
                    locy = 32935;
                    newmapid = 171;
                    scroll = 40110;
                    break;
                }
                case 186: {
                    locx = 32630;
                    locy = 32935;
                    newmapid = 181;
                    scroll = 40111;
                    break;
                }
                case 196: {
                    locx = 32630;
                    locy = 32935;
                    newmapid = 191;
                    scroll = 40112;
                    break;
                }
            }
            L1ItemInstance item = pc.getInventory().checkItemX(scroll, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, locx, locy, newmapid, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "Omantel2"));
            }
        }
        else if (cmd.equals("C")) {
            pc.getInventory().consumeItem(40308, 300L);
            switch (mapid) {
                case 106: {
                    if (pc.getX() < 33790) {
                        L1Teleport.teleport(pc, 33813, 32866, mapid, 5, true);
                        break;
                    }
                    else {
                        L1Teleport.teleport(pc, 33767, 32864, mapid, 5, true);
                        break;
                    }
                }
                case 116:
                case 126:
                case 136:
                case 146: {
                    if (pc.getX() < 32766) {
                        L1Teleport.teleport(pc, 32789, 32866, mapid, 5, true);
                        break;
                    }
                    else {
                        L1Teleport.teleport(pc, 32744, 32864, mapid, 5, true);
                        break;
                    }
                }
                case 156:
                case 166:
                case 176:
                case 186:
                case 196: {
                    if (pc.getX() < 32770) {
                        L1Teleport.teleport(pc, 32792, 32802, mapid, 5, true);
                        break;
                    }
                    else {
                        L1Teleport.teleport(pc, 32737, 32801, mapid, 5, true);
                        break;
                    }
                }
            }
        }
    }
}

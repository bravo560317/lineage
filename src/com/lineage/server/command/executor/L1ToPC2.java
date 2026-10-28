package com.lineage.server.command.executor;

import com.lineage.server.utils.Teleportation;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.templates.L1Account;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.World;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import java.text.SimpleDateFormat;
import org.apache.commons.logging.Log;

public class L1ToPC2 implements L1CommandExecutor
{
    private static final Log _log;
    private static final SimpleDateFormat sdf;
    private static final int PAGE_SIZE = 12;
    private static List<L1PcInstance> _all_players;
    
    static {
        _log = LogFactory.getLog(L1ToPC2.class);
        sdf = new SimpleDateFormat("MM-dd HH:mm");
    }
    
    private L1ToPC2() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ToPC2();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            L1PcInstance target = World.get().getPlayer(arg);
            if (target != null) {
                pc.set_showId(target.get_showId());
                L1Teleport.teleport(pc, target.getX(), target.getY(), target.getMapId(), pc.getHeading(), false);
                pc.sendPackets(new S_SystemMessage("移動至指定人物身邊: " + arg));
            }
            else {
                checkTPhtml(pc, 0);
            }
        }
        catch (Exception e) {
            L1ToPC2._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
    
    public static final void checkTPhtml(L1PcInstance pc, int page) {
        List<L1PcInstance> players = L1ToPC2._all_players;
        if (players == null) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "topcscreen"));
            return;
        }
        else {
            int length = players.size();
            int size = length / 12 + ((length % 12 == 0) ? 0 : 1);
            if (page < 0 || page >= size) {
                return;
            }
            else {
                pc.get_other().set_page(page);
                String[] html_data = new String[13];
                html_data[0] = String.valueOf(page + 1) + "/" + size;
                for (int index = 1, i = page * 12, end = i + 12; i < end && i < length; ++i, ++index) {
                    L1PcInstance player = World.get().getPlayer((players.get(i)).getName());
                    if (player != null) {
                        L1Account acc = player.getNetConnection().getAccount();
                        if (acc != null) {
                            html_data[index] = player.getName() + " (Lv." + player.getLevel() + ")[" + player.getClanname() + "]";
                        }
                    }
                }
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "topcscreen", html_data));
                return;
            }
        }
    }
    
    public static final void checkTPhtmlPredicate(L1PcInstance pc, int page, boolean refreshAll) {
        List<L1PcInstance> all_players = new ArrayList();
        if (refreshAll) {
            for (L1PcInstance tgpc : World.get().getAllPlayers()) {
                if (tgpc.getMap().isUsableShop() == 0 && tgpc.getMapId() != 5143 && tgpc.getMapId() != 5301 && tgpc.getMapId() != 5300) {
                    all_players.add(tgpc);
                }
            }
        }
        else {
            for (L1PcInstance tgpc : World.get().getAllPlayers()) {
                if (tgpc.getMapId() == pc.getMapId()) {
                    all_players.add(tgpc);
                }
            }
        }
        L1ToPC2._all_players = all_players;
        List<L1PcInstance> players = L1ToPC2._all_players;
        if (players == null) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "topcscreen"));
            return;
        }
        else {
            int length = players.size();
            int size = length / 12 + ((length % 12 == 0) ? 0 : 1);
            if (page < 0 || page >= size) {
                return;
            }
            else {
                pc.get_other().set_page(page);
                String[] html_data = new String[13];
                html_data[0] = String.valueOf(page + 1) + "/" + size;
                for (int index = 1, i = page * 12, end = i + 12; i < end && i < length; ++i, ++index) {
                    L1PcInstance player = World.get().getPlayer((players.get(i)).getName());
                    if (player != null) {
                        L1Account acc = player.getNetConnection().getAccount();
                        if (acc != null) {
                            html_data[index] = player.getName() + " (Lv." + player.getLevel() + ")[" + player.getClanname() + "]";
                        }
                    }
                }
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "topcscreen", html_data));
                return;
            }
        }
    }
    
    public static final void teleport2Player(final L1PcInstance pc, int index) {
        if (L1ToPC2._all_players == null) {
            pc.sendPackets(new S_SystemMessage("請點選(刷新)選項。"));
            return;
        }
        else {
            int point = pc.get_other().get_page() * 12 + index;
            if (point < 0 || point >= L1ToPC2._all_players.size()) {
                pc.sendPackets(new S_SystemMessage("玩家列表被變動過，請重新輸入(.topc)來取得目前列表。"));
                return;
            }
            else {
                L1PcInstance target = L1ToPC2._all_players.get(point);
                if (target != null) {
                    final L1PcInstance player = World.get().getPlayer(target.getName());
                    if (player != null) {
                        GeneralThreadPool.get().schedule(new Runnable() {
                            @Override
                            public void run() {
                                pc.set_showId(player.get_showId());
                                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 2236));
                                pc.setTeleportX(player.getX());
                                pc.setTeleportY(player.getY());
                                pc.setTeleportMapId(player.getMapId());
                                Teleportation.teleportation(pc);
                                pc.sendPackets(new S_SystemMessage("移動至指定人物身邊: " + player.getName()));
                                L1ToPC2.checkTPhtml(pc, pc.get_other().get_page());
                            }
                        }, 440L);
                        return;
                    }
                }
                pc.sendPackets(new S_SystemMessage("該人物不存在或已離線。"));
                return;
            }
        }
    }
}

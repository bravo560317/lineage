package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_ChatGlobal;
import java.util.Iterator;
import com.lineage.server.datatables.RecordTable;
import com.lineage.config.ConfigRecord;
import com.lineage.server.serverpackets.S_ChatTransaction;
import com.lineage.server.model.Instance.L1PcInstance;
import com.eric.gui.J_Main;
import com.lineage.config.Config;
import java.util.Calendar;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ChatGlobal extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ChatGlobal.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (decrypt.length > 108) {
                C_ChatGlobal._log.warn("人物:" + pc.getName() + "對話(廣播)長度超過限制:" + client.getIp().toString());
                client.set_error(client.get_error() + 1);
                return;
            }
            else {
                boolean isStop = false;
                boolean errMessage = false;
                if (pc.hasSkillEffect(64) && !pc.isGm()) {
                    isStop = true;
                }
                if (pc.hasSkillEffect(161) && !pc.isGm()) {
                    isStop = true;
                }
                if (pc.hasSkillEffect(1007) && !pc.isGm()) {
                    isStop = true;
                }
                if (pc.hasSkillEffect(4002)) {
                    isStop = true;
                    errMessage = true;
                }
                if (isStop) {
                    if (errMessage) {
                        pc.sendPackets(new S_ServerMessage(242));
                    }
                    return;
                }
                else {
                    if (!pc.isGm()) {
                        if (pc.getLevel() < ConfigAlt.GLOBAL_CHAT_LEVEL) {
                            pc.sendPackets(new S_ServerMessage(195, String.valueOf((int)ConfigAlt.GLOBAL_CHAT_LEVEL)));
                            return;
                        }
                        else if (!World.get().isWorldChatElabled()) {
                            pc.sendPackets(new S_ServerMessage(510));
                            return;
                        }
                    }
                    if (pc.hasSkillEffect(9990)) {
                        pc.sendPackets(new S_SystemMessage("目前受到監禁狀態中無法正常說話。"));
                        return;
                    }
                    else {
                        if (ConfigOther.SET_GLOBAL_TIME > 0 && !pc.isGm()) {
                            Calendar cal = Calendar.getInstance();
                            long time = cal.getTimeInMillis() / 1000L;
                            if (time - pc.get_global_time() < (long)ConfigOther.SET_GLOBAL_TIME) {
                                return;
                            }
                            else {
                                pc.set_global_time(time);
                            }
                        }
                        int chatType = this.readC();
                        String chatText = this.readS();
                        switch (chatType) {
                            case 3: {
                                this.chatType_3(pc, chatText);
                                if (Config.GUI) {
                                    J_Main.getInstance().addWorldChat(pc.getName(), chatText);
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 12: {
                                this.chatType_12(pc, chatText);
                                if (Config.GUI) {
                                    J_Main.getInstance().addWorldChat(pc.getName(), chatText);
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        if (!pc.isGm()) {
                            pc.checkChatInterval();
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    private void chatType_12(L1PcInstance pc, String chatText) {
        S_ChatTransaction packet = new S_ChatTransaction(pc, chatText);
        String name = pc.getName();
        for (L1PcInstance listner : World.get().getAllPlayers()) {
            if (listner.getExcludingList().contains(name)) {
                continue;
            }
            else if (!listner.isShowTradeChat()) {
                continue;
            }
            else if (listner.isBadInEnemyList(pc.getName())) {
                continue;
            }
            else {
                listner.sendPackets(packet);
            }
        }
        if (ConfigRecord.LOGGING_CHAT_BUSINESS) {
            RecordTable.get().recordeTalk("買賣", pc.getName(), pc.getClanname(), null, chatText);
        }
    }
    
    private void chatType_3(L1PcInstance pc, String chatText) {
        S_ChatGlobal packet = new S_ChatGlobal(pc, chatText);
        if (pc.isGm()) {
            World.get().broadcastPacketToAll(packet);
            return;
        }
        else {
            String name = pc.getName();
            if (!pc.isGm()) {
                switch (ConfigOther.SET_GLOBAL) {
                    case 0: {
                        if (pc.get_food() >= 6) {
                            pc.set_food(pc.get_food() - ConfigOther.SET_GLOBAL_COUNT);
                            pc.sendPackets(new S_PacketBox(11, pc.get_food()));
                            break;
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(462));
                            return;
                        }
                    }
                    default: {
                        L1ItemInstance item = pc.getInventory().checkItemX(ConfigOther.SET_GLOBAL, (long)ConfigOther.SET_GLOBAL_COUNT);
                        if (item != null) {
                            pc.getInventory().removeItem(item, (long)ConfigOther.SET_GLOBAL_COUNT);
                            break;
                        }
                        else {
                            L1Item itemtmp = ItemTable.get().getTemplate(ConfigOther.SET_GLOBAL);
                            pc.sendPackets(new S_ServerMessage(337, itemtmp.getNameId()));
                            return;
                        }
                    }
                }
            }
            for (L1PcInstance listner : World.get().getAllPlayers()) {
                if (listner.getExcludingList().contains(name)) {
                    continue;
                }
                else if (listner.isBadInEnemyList(pc.getName())) {
                    continue;
                }
                else if (!listner.isShowWorldChat()) {
                    continue;
                }
                else {
                    listner.sendPackets(packet);
                }
            }
            if (ConfigRecord.LOGGING_CHAT_WORLD) {
                RecordTable.get().recordeTalk("公頻", pc.getName(), pc.getClanname(), null, chatText);
            }
            return;
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

package com.lineage.server.clientpackets;

import java.util.Collection;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.WorldNpc;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1DeInstance;
import com.eric.gui.J_Main;
import com.lineage.config.Config;
import com.lineage.server.datatables.RecordTable;
import com.lineage.config.ConfigRecord;
import com.lineage.server.serverpackets.S_ChatWhisperFrom;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ChatWhisperTo;
import com.lineage.server.world.World;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ChatWhisper extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ChatWhisper.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance whisperFrom = client.getActiveChar();
            if (whisperFrom.hasSkillEffect(4002)) {
                whisperFrom.sendPackets(new S_ServerMessage(242));
            }
            else if (whisperFrom.getLevel() < ConfigAlt.WHISPER_CHAT_LEVEL && !whisperFrom.isGm()) {
                whisperFrom.sendPackets(new S_ServerMessage(404, String.valueOf((int)ConfigAlt.WHISPER_CHAT_LEVEL)));
            }
            else {
                String targetName = this.readS();
                String text = this.readS();
                if (text.length() > 52) {
                    C_ChatWhisper._log.warn("人物:" + whisperFrom.getName() + "對話長度超過限制:" + client.getIp().toString());
                    client.set_error(client.get_error() + 1);
                    return;
                }
                else {
                    L1PcInstance whisperTo = World.get().getPlayer(targetName);
                    L1DeInstance de = null;
                    if (whisperTo == null) {
                        de = getDe(targetName);
                    }
                    if (de != null) {
                        whisperFrom.sendPackets(new S_ChatWhisperTo(de, text));
                        return;
                    }
                    else if (whisperTo == null) {
                        whisperFrom.sendPackets(new S_ServerMessage(73, targetName));
                        return;
                    }
                    else if (whisperTo.isBadInEnemyList(whisperFrom.getName())) {
                        return;
                    }
                    else if (whisperTo.equals(whisperFrom)) {
                        return;
                    }
                    else if (whisperTo.getExcludingList().contains(whisperFrom.getName())) {
                        whisperFrom.sendPackets(new S_ServerMessage(117, whisperTo.getName()));
                        return;
                    }
                    else if (!whisperTo.isCanWhisper()) {
                        whisperFrom.sendPackets(new S_ServerMessage(205, whisperTo.getName()));
                        return;
                    }
                    else if (whisperTo.hasSkillEffect(9990)) {
                        whisperTo.sendPackets(new S_SystemMessage("目前受到監禁狀態中無法正常說話。"));
                        return;
                    }
                    else {
                        if (ConfigAlt.GM_OVERHEARD) {
                            for (L1PcInstance object41 : World.get().getAllPlayers()) {
                                L1Object visible = object41;
                                if (visible instanceof L1PcInstance) {
                                    L1PcInstance GM = (L1PcInstance)visible;
                                    if (GM.getcheckgm() && GM.isGm() && whisperFrom.getId() != GM.getId()) {
                                        GM.sendPackets(new S_SystemMessage("\\fV【密語】" + whisperFrom.getName() + " -> " + targetName + ":" + text));
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                        }
                        whisperFrom.sendPackets(new S_ChatWhisperTo(whisperTo, text));
                        whisperTo.sendPackets(new S_ChatWhisperFrom(whisperFrom, text));
                        if (ConfigRecord.LOGGING_CHAT_WHISPER) {
                            RecordTable.get().recordeTalk("密語", whisperFrom.getName(), whisperFrom.getClanname(), whisperTo.getName(), text);
                        }
                        if (Config.GUI) {
                            J_Main.getInstance().addPrivateChat(whisperFrom.getName(), whisperTo.getName(), text);
                            return;
                        }
                        else {
                            return;
                        }
                    }
                }
            }
            return;
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    public static L1DeInstance getDe(String s) {
        Collection<L1NpcInstance> allNpc = WorldNpc.get().all();
        if (allNpc.isEmpty()) {
            return null;
        }
        else {
            for (L1NpcInstance npc : allNpc) {
                if (npc instanceof L1DeInstance) {
                    L1DeInstance de = (L1DeInstance)npc;
                    if (de.getNameId().equalsIgnoreCase(s)) {
                        return de;
                    }
                    else {
                        continue;
                    }
                }
            }
            return null;
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

package com.lineage.server.clientpackets;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.pr_type_name;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.config.ConfigRate;
import com.lineage.config.ConfigWho;
import com.lineage.server.timecontroller.server.ServerRestartTimer;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_WhoCharinfo;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Who extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Who.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            String s = this.readS();
            L1Character find = World.get().getPlayer(s);
            L1PcInstance pc = client.getActiveChar();
            if (find == null) {
                find = this.getDe(s);
            }
            if (find != null) {
                S_WhoCharinfo whoChar = new S_WhoCharinfo(find, client);
                pc.sendPackets(whoChar);
            }
            else {
                int de = deCount();
                int count = World.get().getAllPlayers().size();
                String amount = String.valueOf((int)((double)count * ConfigAlt.ALT_WHO_COUNT) + de);
                if (pc.isGm()) {
                    pc.sendPackets(new S_ServerMessage("\\f=(GM顯示)目前真實人數: " + amount));
                    pc.sendPackets(new S_ServerMessage("\\f=(GM顯示)目前假人人數: " + de));
                }
                if (ConfigAlt.ALT_WHO_COMMANDX) {
                    String nowDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                    switch (ConfigAlt.ALT_WHO_TYPE) {
                        case 0: {
                            pc.sendPackets(new S_ServerMessage("\\fV啟動時間: " + ServerRestartTimer.get_startTime())); // 此行保持不變
                                pc.sendPackets(new S_ServerMessage("\\fV經驗倍率: " + ConfigRate.RATE_XP));
                            pc.sendPackets(new S_ServerMessage("\\fV金錢倍率: " + ConfigRate.RATE_DROP_ADENA));
                            if (ConfigWho.check_who_weapon) {
                                pc.sendPackets(new S_ServerMessage("\\fV衝武倍率: " + ConfigWho.whoweapon));
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage("\\fV衝武倍率: " + ConfigRate.ENCHANT_CHANCE_WEAPON));
                            }
                            if (ConfigWho.check_who_armor) {
                                pc.sendPackets(new S_ServerMessage("\\fV衝防倍率: " + ConfigWho.whoarmor));
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage("\\fV衝防倍率: " + ConfigRate.ENCHANT_CHANCE_ARMOR));
                            }
                            pc.sendPackets(new S_ServerMessage("\\fV現實時間: " + nowDate));
                            pc.sendPackets(new S_ServerMessage("\\fV重啟時間: " + ServerRestartTimer.get_restartTime()));
                            if (pc.hasSkillEffect(7596)) {
                                pc.sendPackets(new S_SystemMessage("請稍候在進行操作。"));
                                return;
                            }
                            else {
                                pc.setSkillEffect(7596, 60000);
                                if (pc.getnewaicount_2() > 0) {
                                    pc.sendPackets(new S_ServerMessage("\\fU您的驗證答案數字請輸入: " + pc.getnewaicount_2()));
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        case 1: {
                            String[] info = new String[13];
                            info[1] = amount;
                            info[2] = String.valueOf(ConfigRate.RATE_XP); // 始終使用當前實際經驗倍率
                            info[3] = String.valueOf(ConfigRate.RATE_DROP_ITEMS);
                            info[4] = String.valueOf(ConfigRate.RATE_DROP_ADENA);
                            if (ConfigWho.check_who_weapon) {
                                info[5] = String.valueOf(ConfigWho.whoweapon);
                            }
                            else {
                                info[5] = String.valueOf(ConfigRate.ENCHANT_CHANCE_WEAPON);
                            }
                            if (ConfigWho.check_who_armor) {
                                info[6] = String.valueOf(ConfigWho.whoarmor);
                            }
                            else {
                                info[6] = String.valueOf(ConfigRate.ENCHANT_CHANCE_ARMOR);
                            }
                            info[7] = String.valueOf(ConfigAlt.POWER);
                            info[8] = String.valueOf(ConfigAlt.POWERMEDICINE);
                            info[9] = String.valueOf(ConfigAlt.MEDICINE);
                            info[10] = nowDate;
                            info[11] = ServerRestartTimer.get_restartTime();
                            info[12] = String.valueOf(pc.getElixirStats());
                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_who", info));
                            if (pc.getPrestigeLv() != 0) {
                                pc.sendPackets(new S_ServerMessage("目前" + pr_type_name._1 + ":" + pc.getPrestige(), 11));
                            }
                            if (pc.hasSkillEffect(7596)) {
                                pc.sendPackets(new S_SystemMessage("請稍候在進行操作。"));
                                return;
                            }
                            else {
                                pc.setSkillEffect(7596, 60000);
                                if (pc.getnewaicount_2() > 0) {
                                    pc.sendPackets(new S_ServerMessage("\\fU您的驗證答案數字請輸入: " + pc.getnewaicount_2()));
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
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
    
    public static int deCount() {
        int count = 0;
        Collection<L1NpcInstance> allObj = WorldNpc.get().all();
        if (allObj.isEmpty()) {
            return count;
        }
        else {
            for (L1NpcInstance obj : allObj) {
                if (obj instanceof L1DeInstance) {
                    ++count;
                }
            }
            return count;
        }
    }
    
    private L1DeInstance getDe(String s) {
        Collection<L1NpcInstance> allObj = WorldNpc.get().all();
        if (allObj.isEmpty()) {
            return null;
        }
        else {
            for (L1NpcInstance obj : allObj) {
                if (obj instanceof L1DeInstance) {
                    L1DeInstance de = (L1DeInstance)obj;
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

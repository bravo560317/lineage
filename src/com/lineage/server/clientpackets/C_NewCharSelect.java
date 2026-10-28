package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.config.ConfigIpCheck;
import com.lineage.server.serverpackets.S_PacketBoxSelect;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_NewCharSelect extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_NewCharSelect.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            if (client.getActiveChar() != null) {
                L1PcInstance pc = client.getActiveChar();
                if (World.get().getPlayer(pc.getName()) == null) {
                    client.kick("角色切換時發生錯誤");
                }
                else if (pc.getOnlineStatus() == 1) {
                    pc.sendPackets(new S_ChangeName(pc, false));
                    pc.sendPackets(new S_PacketBox(132, pc.getEr()));
                    pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
                    Thread.sleep(750L);
                    client.quitGame();
                    client.out().encrypt(new S_PacketBoxSelect());
                    C_NewCharSelect._log.info("角色切換: " + pc.getName());
                    if (ConfigIpCheck.timeOutSocket != 0) {
                        client.SetSocket(ConfigIpCheck.timeOutSocket);
                        return;
                    }
                    else {
                        return;
                    }
                }
                return;
            }
            else {
                C_NewCharSelect._log.error("帳號：" + client.getAccountName() + " 空角色退出服務器。");
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

package com.lineage.data.item_etcitem.shop;

import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class UserName extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(UserName.class);
    }
    
    public static ItemExecutor get() {
        return new UserName();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else if (pc.isGhost()) {
            return;
        }
        else if (pc.isDead()) {
            return;
        }
        else if (pc.isTeleport()) {
            return;
        }
        else if (pc.getLawful() < 32767) {
            pc.sendPackets(new S_ServerMessage("\\fT正義值必須為32767才可以使用"));
            return;
        }
        else if (pc.isPrivateShop()) {
            pc.sendPackets(new S_ServerMessage("\\fT請先結束商店村模式!"));
            return;
        }
        else {
            Object[] petList = pc.getPetList().values().toArray();
            if (petList.length > 0) {
                pc.sendPackets(new S_ServerMessage("\\fT請先回收寵物!"));
                return;
            }
            else if (pc.getHierarchs() != null) {
                pc.sendPackets(new S_ServerMessage("\\fT請先回收祭司!"));
                return;
            }
            else if (!pc.getDolls().isEmpty()) {
                pc.sendPackets(new S_ServerMessage("\\fT請先回收魔法娃娃!"));
                return;
            }
            else if (pc.getParty() != null) {
                pc.sendPackets(new S_ServerMessage("\\fT請先退出隊伍!"));
                return;
            }
            else if (pc.getClanid() != 0) {
                pc.sendPackets(new S_ServerMessage("\\fT請先退出血盟!"));
                return;
            }
            else {
                try {
                    pc.sendPackets(new S_Message_YN(325));
                    pc.rename(true);
                }
                catch (Exception e) {
                    UserName._log.error(e.getLocalizedMessage(), e);
                }
                return;
            }
        }
    }
}

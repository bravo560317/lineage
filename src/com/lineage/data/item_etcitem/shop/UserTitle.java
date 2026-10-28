package com.lineage.data.item_etcitem.shop;

import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class UserTitle extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(UserTitle.class);
    }
    
    public static ItemExecutor get() {
        return new UserTitle();
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
        else if (pc.is_repass() != 0) {
            pc.sendPackets(new S_ServerMessage("\\fU你不是要變更密碼嗎?"));
            return;
        }
        else if (!ConfigOther.CLANTITLE) {
            pc.sendPackets(new S_ServerMessage("\\fT尚未開放"));
            return;
        }
        else if (pc.isPrivateShop()) {
            pc.sendPackets(new S_ServerMessage("\\fT請先結束商店村模式!"));
            return;
        }
        else {
            try {
                pc.retitle(true);
                pc.sendPackets(new S_ServerMessage("\\fR輸入封號後直接按下ENTER"));
            }
            catch (Exception e) {
                UserTitle._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
}

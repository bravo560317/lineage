package com.lineage.data.item_etcitem.mp;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class UserMprBlue extends ItemExecutor
{
    private static final Log _log;
    private int _time;
    private int _gfxid;
    
    static {
        _log = LogFactory.getLog(UserMprBlue.class);
    }
    
    public static ItemExecutor get() {
        return new UserMprBlue();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            if (item == null) {
                return;
            }
            else if (pc == null) {
                return;
            }
            else if (L1BuffUtil.stopPotion(pc)) {
                pc.getInventory().removeItem(item, 1L);
                if (pc.hasSkillEffect(1002)) {
                    pc.killSkillEffectTimer(1002);
                }
                L1BuffUtil.cancelAbsoluteBarrier(pc);
                if (this._gfxid > 0) {
                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), this._gfxid));
                }
                pc.sendPackets(new S_PacketBox(34, this._time));
                pc.setSkillEffect(1002, this._time * 1000);
                pc.sendPackets(new S_ServerMessage(1007));
            }
        }
        catch (Exception e) {
            UserMprBlue._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._time = Integer.parseInt(set[1]);
            if (this._time <= 0) {
                this._time = 600;
                UserMprBlue._log.error("UserMpr 設置錯誤:時效小於等於0! 使用預設600");
            }
        }
        catch (Exception ex) {}
        try {
            this._gfxid = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}

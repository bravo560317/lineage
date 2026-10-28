package com.lineage.data.item_etcitem.shop;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class ResetPassword extends ItemExecutor
{
    private static final Log _log;
    public static final Random _random;
    
    static {
        _log = LogFactory.getLog(ResetPassword.class);
        _random = new Random();
    }
    
    public static ItemExecutor get() {
        return new ResetPassword();
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
            else if (pc.is_retitle()) {
                pc.sendPackets(new S_ServerMessage("\\fU你不是要變更封號嗎?"));
                return;
            }
            else {
                pc.repass(1);
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_pass_01", new String[] { "請輸入您的舊密碼" }));
            }
        }
        catch (Exception e) {
            ResetPassword._log.error(e.getLocalizedMessage(), e);
        }
    }
}

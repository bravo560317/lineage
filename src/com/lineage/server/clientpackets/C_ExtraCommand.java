package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ExtraCommand extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ExtraCommand.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        if (!pc.isInvisble()) {
                            if (!pc.hasSkillEffect(7914)) {
                                int actionId = this.readC();
                                switch (actionId) {
                                    case 66: {
                                        return;
                                    }
                                    case 67: {
                                        return;
                                    }
                                    case 68: {
                                        return;
                                    }
                                    default: {
                                        return;
                                    }
                                }
                            }
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
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

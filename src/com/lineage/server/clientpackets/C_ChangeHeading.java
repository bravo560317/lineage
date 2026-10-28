package com.lineage.server.clientpackets;

import java.util.Iterator;
import java.util.Map;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1SkinInstance;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ChangeHeading extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ChangeHeading.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {}
            if (pc.isDead() || pc.isTeleport()) {
                return;
            }
            else {
                int heading = this.readC();
                switch (heading) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7: {
                        pc.setHeading(heading);
                        if (!pc.isGmInvis() && !pc.isGhost() && !pc.isInvisble()) {
                            pc.broadcastPacketAll(new S_ChangeHeading(pc));
                        }
                        Map<Integer, L1SkinInstance> skinList = pc.getSkins();
                        if (skinList.size() > 0) {
                            for (Integer gfxid : skinList.keySet()) {
                                L1SkinInstance skin = skinList.get(gfxid);
                                skin.setHeading(heading);
                                skin.broadcastPacketAll(new S_ChangeHeading(skin));
                            }
                            break;
                        }
                        else {
                            break;
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
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

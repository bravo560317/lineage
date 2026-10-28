package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1Config;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.lock.CharacterConfigReading;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_CharcterConfig extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_CharcterConfig.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc == null) {
                return;
            }
            else {
                int objid = pc.getId();
                int length = this.readD() - 3;
                byte[] data = this.readByte();
                L1Config config = CharacterConfigReading.get().get(objid);
                if (config == null) {
                    CharacterConfigReading.get().storeCharacterConfig(objid, length, data);
                }
                else {
                    CharacterConfigReading.get().updateCharacterConfig(objid, length, data);
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

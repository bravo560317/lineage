package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_LoginToServerOK extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_LoginToServerOK.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int type = this.readC();
            int button = this.readC();
            L1PcInstance pc = client.getActiveChar();
            switch (type) {
                case 0: {
                    if (button == 0) {
                        pc.setShowWorldChat(false);
                        break;
                    }
                    else if (button == 1) {
                        pc.setShowWorldChat(true);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (button == 0) {
                        pc.setCanWhisper(false);
                        break;
                    }
                    else if (button == 1) {
                        pc.setCanWhisper(true);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 6: {
                    if (button == 0) {
                        pc.setShowTradeChat(false);
                        break;
                    }
                    else if (button == 1) {
                        pc.setShowTradeChat(true);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 255: {
                    switch (button) {
                        case 90:
                        case 122: {
                            pc.setShowWorldChat(false);
                            pc.setCanWhisper(false);
                            break;
                        }
                        case 91:
                        case 123: {
                            pc.setShowWorldChat(true);
                            pc.setCanWhisper(false);
                            break;
                        }
                        case 94:
                        case 126: {
                            pc.setShowWorldChat(false);
                            pc.setCanWhisper(true);
                            break;
                        }
                        case 95:
                        case 127: {
                            pc.setShowWorldChat(true);
                            pc.setCanWhisper(true);
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

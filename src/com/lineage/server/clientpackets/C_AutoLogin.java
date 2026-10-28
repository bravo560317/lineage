package com.lineage.server.clientpackets;

import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_AutoLogin extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_AutoLogin.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int mode = this.readC();
            if (mode == 0) {
                String loginName = this.readS().toLowerCase();
                String srcpassword = this.readS();
                C_AuthLogin authLogin = new C_AuthLogin();
                authLogin.checkLogin(client, loginName, srcpassword, true);
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

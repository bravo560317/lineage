package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1Account;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.list.OnlineUser;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ReturnToLogin extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ReturnToLogin.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance pc = client.getActiveChar();
            if (pc != null) {
                client.quitGame();
            }
            L1Account account = client.getAccount();
            if (account != null) {
                OnlineUser.get().remove(account.get_login());
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

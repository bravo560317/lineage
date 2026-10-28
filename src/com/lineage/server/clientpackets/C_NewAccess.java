package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1Account;
import com.lineage.server.serverpackets.S_LoginResult;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_NewAccess extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_NewAccess.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            String loginName = this.readS();
            String password = this.readS();
            StringBuilder ip = client.getIp();
            StringBuilder mac = client.getMac();
            if (mac == null) {
                mac = ip;
            }
            C_NewAccess._log.info("E-MAIL: " + this.readS());
            String spwd = this.readS();
            C_NewAccess._log.info("TYPE: " + this.readC());
            C_NewAccess._log.info("ADDRESS: " + this.readS());
            C_NewAccess._log.info("PHONE: " + this.readS());
            C_NewAccess._log.info("FAX: " + this.readS());
            L1Account account = AccountReading.get().getAccount(loginName);
            if (account == null) {
                account = AccountReading.get().create(loginName, password, ip.toString(), mac.toString(), spwd);
                client.out().encrypt(new S_LoginResult(4));
            }
            client.out().encrypt(new S_LoginResult(7));
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

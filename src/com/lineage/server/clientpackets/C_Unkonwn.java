package com.lineage.server.clientpackets;

import java.util.Date;
import java.text.SimpleDateFormat;
import com.lineage.echo.encryptions.PacketPrint;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Unkonwn extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Unkonwn.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            C_Unkonwn._log.info("未處理封包: " + (decrypt[0] & 0xFF) + " (" + this.getNow_YMDHMS() + " 核心管理者紀錄用!)");
            C_Unkonwn._log.info(PacketPrint.get().printData(decrypt, decrypt.length));
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
    
    private final String getNow_YMDHMS() {
        String nowDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        return nowDate;
    }
}

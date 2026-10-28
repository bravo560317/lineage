package com.lineage.server.clientpackets;

import com.lineage.echo.ClientExecutor;

public class C_ExtraPacket extends ClientBasePacket
{
    @Override
    public void start(byte[] decrypt, ClientExecutor client) throws Exception {
        this.read(decrypt);
        int type = this.readC();
        switch (type) {
            case 9: {
                this.readD();
                break;
            }
        }
    }
}

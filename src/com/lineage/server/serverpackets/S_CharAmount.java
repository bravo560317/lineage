package com.lineage.server.serverpackets;

import com.lineage.config.ConfigAlt;
import com.lineage.echo.ClientExecutor;

public class S_CharAmount extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CharAmount(int value, ClientExecutor client) {
        this._byte = null;
        this.buildPacket(value, client);
    }
    
    private void buildPacket(int value, ClientExecutor client) {
        int characterSlot = client.getAccount().get_character_slot();
        int maxAmount = ConfigAlt.DEFAULT_CHARACTER_SLOT + characterSlot;
        this.writeC(178);
        this.writeC(value);
        this.writeC(maxAmount);
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

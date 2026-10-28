package com.lineage.server.serverpackets;

public class S_SkillIconGFX extends ServerBasePacket
{
    public S_SkillIconGFX(int tempgfx, boolean changeWeapon) {
        this.writeC(250);
        this.writeC(160);
        if (changeWeapon) {
            this.writeC(1);
        }
        else {
            this.writeC(0);
        }
        this.writeH(0);
        this.writeC(2);
        this.writeH(tempgfx);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
}

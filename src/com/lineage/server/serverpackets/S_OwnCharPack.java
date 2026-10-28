package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;

public class S_OwnCharPack extends ServerBasePacket
{
    private static final int STATUS_INVISIBLE = 2;
    private static final int STATUS_PC = 4;
    private static final int STATUS_BRAVE = 16;
    private static final int STATUS_ELFBRAVE = 32;
    private static final int STATUS_FASTMOVABLE = 64;
    private static final int STATUS_GHOST = 128;
    public static final Random _random;
    private byte[] _byte;
    
    static {
        _random = new Random();
    }
    
    public S_OwnCharPack(L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(pc);
    }
    
    private void buildPacket(L1PcInstance pc) {
        int status = 4;
        if (pc.isInvisble() || pc.isGmInvis()) {
            status |= 0x2;
        }
        if (pc.isBrave()) {
            status |= 0x10;
        }
        if (pc.isElfBrave()) {
            status |= 0x10;
            status |= 0x20;
        }
        if (pc.isFastMovable()) {
            status |= 0x40;
        }
        if (pc.isGhost()) {
            status |= 0x80;
        }
        this.writeC(87);
        this.writeH(pc.getX());
        this.writeH(pc.getY());
        this.writeD(pc.getId());
        if (pc.isDead()) {
            this.writeH(pc.getTempCharGfxAtDead());
        }
        else {
            this.writeH(pc.getTempCharGfx());
        }
        if (pc.isDead()) {
            this.writeC(pc.getStatus());
        }
        else {
            this.writeC(pc.getCurrentWeapon());
        }
        this.writeC(pc.getHeading());
        this.writeC(pc.getOwnLightSize());
        this.writeC(pc.getMoveSpeed());
        this.writeD((int)pc.getExp());
        this.writeH(pc.getLawful());
        StringBuilder stringBuilder = new StringBuilder();
        if (pc.isProtector()) {
            if (pc.getSuper() > 0) {
                this.writeS(pc.getSuper() + "**守護者**");
            }
            else {
                this.writeS("**守護者**");
            }
        }
        else {
            stringBuilder.append(pc.getViewName());
            if (pc.get_c_power() != null && pc.get_c_power().get_c1_type() != 0 && pc.get_c_power().get_power().get_c1_name_title() != null) {
                String type = pc.get_c_power().get_power().get_c1_name_title();
                stringBuilder.append(type);
            }
            if (pc.get_other().get_color() != 0) {
                stringBuilder.append(pc.get_other().color());
            }
            this.writeS(stringBuilder.toString());
        }
        if (pc.getSuper() > 0) {
            this.writeS("\\f2驗證碼:[ " + pc.getSuper() + " ]");
        }
        else {
            this.writeS(pc.getTitle());
        }
        this.writeC(status);
        this.writeD((pc.getClanid() > 0) ? pc.getClan().getEmblemId() : 0);
        this.writeS(pc.getClanname());
        this.writeS(null);
        this.writeC((pc.getClanRank() > 0) ? (pc.getClanRank() << 4) : 176);
        if (pc.isInParty()) {
            this.writeC(100 * pc.getCurrentHp() / pc.getMaxHp());
        }
        else {
            this.writeC(255);
        }
        if (pc.hasSkillEffect(998)) {
            this.writeC(8);
        }
        else {
            this.writeC(0);
        }
        this.writeC(0);
        this.writeC(0);
        this.writeC(255);
        this.writeC(255);
        this.writeS(null);
        this.writeC(0);
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

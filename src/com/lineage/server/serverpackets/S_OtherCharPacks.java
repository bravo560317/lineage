package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_OtherCharPacks extends ServerBasePacket
{
    private static final int STATUS_POISON = 1;
    private static final int STATUS_INVISIBLE = 2;
    private static final int STATUS_PC = 4;
    private static final int STATUS_BRAVE = 16;
    private static final int STATUS_ELFBRAVE = 32;
    private static final int STATUS_FASTMOVABLE = 64;
    private byte[] _byte;
    
    public S_OtherCharPacks(L1PcInstance pc) {
        this._byte = null;
        int status = 4;
        if (pc.getPoison() != null && pc.getPoison().getEffectId() == 1) {
            status |= 0x1;
        }
        if (pc.isInvisble()) {
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
        else if (pc.isFishing()) {
            this.writeC(71);
        }
        else if (pc.isPrivateShop()) {
            this.writeC(70);
        }
        else {
            this.writeC(pc.getCurrentWeapon());
        }
        this.writeC(pc.getHeading());
        this.writeC(pc.getChaLightSize());
        this.writeC(pc.getMoveSpeed());
        this.writeD(0);
        this.writeH(pc.getLawful());
        if (pc.isProtector()) {
            this.writeS("**守護者**");
        }
        else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(pc.getViewName());
            if (pc.get_other().get_color() != 0) {
                stringBuilder.append(pc.get_other().color());
            }
            if (pc.get_c_power() != null && pc.get_c_power().get_c1_type() != 0 && pc.get_c_power().get_power().get_c1_name_title() != null) {
                String type = pc.get_c_power().get_power().get_c1_name_type();
                stringBuilder.append(type);
            }
            this.writeS(stringBuilder.toString());
        }
        this.writeS(pc.getTitle());
        this.writeC(status);
        this.writeD(pc.getEmblemId());
        this.writeS(pc.getClanname());
        this.writeS(null);
        this.writeC(pc.getClanRank() << 4);
        this.writeC(255);
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

package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_CharVisualUpdate extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CharVisualUpdate(int objid, int weaponType) {
        this._byte = null;
        this.writeC(119);
        this.writeD(objid);
        this.writeC(weaponType);
    }
    
    public S_CharVisualUpdate(L1PcInstance cha) {
        this._byte = null;
        this.writeC(119);
        this.writeD(cha.getId());
        if (cha.getWeapon() != null) {
            int weapontype = cha.getWeapon().getItem().getType1();
            if (weapontype == 24) {
                switch (cha.getTempCharGfx()) {
                    case 6137:
                    case 6142:
                    case 6147:
                    case 6152:
                    case 6157:
                    case 9205:
                    case 12280:
                    case 12681:
                    case 13152:
                    case 13153:
                    case 17541: {
                        this.writeC(83);
                        cha.sendPackets(new S_SkillIconGFX(cha.getTempCharGfx(), true));
                        break;
                    }
                    default: {
                        this.writeC(cha.getCurrentWeapon());
                        cha.sendPackets(new S_SkillIconGFX(cha.getTempCharGfx(), false));
                        break;
                    }
                }
            }
            else {
                this.writeC(cha.getCurrentWeapon());
            }
            this.writeC(255);
            this.writeC(255);
        }
    }
    
    public S_CharVisualUpdate(L1Character cha, int status) {
        this._byte = null;
        this.writeC(119);
        this.writeD(cha.getId());
        this.writeC(status);
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
}

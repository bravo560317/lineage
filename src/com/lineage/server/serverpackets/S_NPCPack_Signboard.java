package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1SignboardInstance;

public class S_NPCPack_Signboard extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NPCPack_Signboard(L1SignboardInstance signboard) {
        this._byte = null;
        this.writeC(87);
        this.writeH(signboard.getX());
        this.writeH(signboard.getY());
        this.writeD(signboard.getId());
        this.writeH(signboard.getGfxId());
        this.writeC(0);
        this.writeC(this.getDirection(signboard.getHeading()));
        this.writeC(0);
        this.writeC(0);
        this.writeD(0);
        this.writeH(0);
        this.writeS(null);
        this.writeS(signboard.getName());
        this.writeC(0);
        this.writeD(0);
        this.writeS(null);
        this.writeS(null);
        this.writeC(0);
        this.writeC(255);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(255);
        this.writeC(255);
    }
    
    private int getDirection(int heading) {
        int dir = 0;
        switch (heading) {
            case 2: {
                dir = 1;
                break;
            }
            case 3: {
                dir = 2;
                break;
            }
            case 4: {
                dir = 3;
                break;
            }
            case 6: {
                dir = 4;
                break;
            }
            case 7: {
                dir = 5;
                break;
            }
        }
        return dir;
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

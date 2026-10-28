package com.lineage.server.serverpackets;

public class S_CharPacks extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CharPacks(String name, String clanName, int type, int sex, int lawful, int hp, int mp, int ac, int lv, int str, int dex, int con, int wis, int cha, int intel, int time) {
        this._byte = null;
        this.writeC(93);
        this.writeS(name);
        this.writeS(clanName);
        this.writeC(type);
        this.writeC(sex);
        this.writeH(lawful);
        this.writeH(hp);
        this.writeH(mp);
        if (ac > 10) {
            this.writeC(10);
        }
        else {
            this.writeC(ac);
        }
        if (lv > 127) {
            this.writeC(127);
        }
        else {
            this.writeC(lv);
        }
        this.writeC(str);
        this.writeC(dex);
        this.writeC(con);
        this.writeC(wis);
        this.writeC(cha);
        this.writeC(intel);
        this.writeC(0);
        String times = Integer.toHexString(time);
        if (times.length() < 8) {
            times = "0" + times;
        }
        this.writeC(Integer.decode("0x" + times.substring(6, 8)));
        this.writeC(Integer.decode("0x" + times.substring(4, 6)));
        this.writeC(Integer.decode("0x" + times.substring(2, 4)));
        this.writeC(Integer.decode("0x" + times.substring(0, 2)));
        if (lv > 127) {
            lv = 127;
        }
        int checkcode = lv ^ str ^ dex ^ con ^ wis ^ cha ^ intel;
        this.writeC(checkcode & 0xFF);
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

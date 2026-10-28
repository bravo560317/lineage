package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_EquipmentWindow extends ServerBasePacket
{
    private static final String S_EQUIPMENTWINDOWS = "[S] S_EquipmentWindow";
    private byte[] _byte;
    public static final byte EQUIPMENT_INDEX_HEML = 1;
    public static final byte EQUIPMENT_INDEX_ARMOR = 2;
    public static final byte EQUIPMENT_INDEX_T = 3;
    public static final byte EQUIPMENT_INDEX_CLOAK = 4;
    public static final byte EQUIPMENT_INDEX_BOOTS = 5;
    public static final byte EQUIPMENT_INDEX_GLOVE = 6;
    public static final byte EQUIPMENT_INDEX_SHIELD = 7;
    public static final byte EQUIPMENT_INDEX_WEAPON = 8;
    public static final byte EQUIPMENT_INDEX_NECKLACE = 10;
    public static final byte EQUIPMENT_INDEX_BELT = 11;
    public static final byte EQUIPMENT_INDEX_EARRING1 = 12;
    public static final byte EQUIPMENT_INDEX_EARRING2 = 13;
    public static final byte EQUIPMENT_INDEX_PANTS = 14;
    public static final byte EQUIPMENT_INDEX_RING1 = 18;
    public static final byte EQUIPMENT_INDEX_RING2 = 19;
    public static final byte EQUIPMENT_INDEX_RING3 = 20;
    public static final byte EQUIPMENT_INDEX_RING4 = 21;
    public static final byte EQUIPMENT_INDEX_RUNE1 = 22;
    public static final byte EQUIPMENT_INDEX_RUNE2 = 23;
    public static final byte EQUIPMENT_INDEX_RUNE3 = 24;
    public static final byte EQUIPMENT_INDEX_TALISMAN = 25;
    public static final byte EQUIPMENT_INDEX_TALISMAN2 = 26;
    public static final byte EQUIPMENT_INDEX_TALISMAN3 = 27;
    
    public S_EquipmentWindow(L1PcInstance pc, int itemObjId, int index, boolean isEq) {
        this._byte = null;
        this.writeC(64);
        this.writeC(66);
        this.writeD(itemObjId);
        this.writeC(index);
        if (isEq) {
            this.writeC(1);
        }
        else {
            this.writeC(0);
        }
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
        return "[S] S_EquipmentWindow";
    }
}

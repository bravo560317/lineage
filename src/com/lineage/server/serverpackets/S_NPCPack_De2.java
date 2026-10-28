package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1DeInstance2;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_NPCPack_De2 extends ServerBasePacket {

    private static final String S_NPCPACK_DE2 = "[S] S_NPCPack_De2";

    private byte[] _byte = null;

    public S_NPCPack_De2(final L1DeInstance2 npc) {
        this.buildPacket(npc);
    }

    private void buildPacket(final L1DeInstance2 npc) {
        // 狀態旗標 (中毒, 隱身, 勇氣等)
        int status = 0;
        if (npc.getPoison() != null && npc.getPoison().getEffectId() == 1) {
            status |= 1; // STATUS_POISON
        }
        // 為了讓名稱只在滑鼠指向時顯示，必須加入 STATUS_PC 旗標
        status |= 4; // STATUS_PC

        this.writeC(S_OPCODE_CHARPACK);
        this.writeH(npc.getX());
        this.writeH(npc.getY());
        this.writeD(npc.getId());
        this.writeH(npc.getTempCharGfx());
        this.writeC(npc.getStatus());
        this.writeC(npc.getHeading());
        this.writeC(npc.getChaLightSize());
        this.writeC(npc.getMoveSpeed());
        this.writeD(0);
        this.writeH(npc.getLawful());
        this.writeS(npc.getNameId()); // 修正：應使用 NameId 來寫入假人自身名稱
        this.writeS(npc.getTitle());
        this.writeC(status); // ★ 修正：傳送正確的狀態旗標
        this.writeD(0); // clan id - 固定為 0
        this.writeS(null); // clan name - 固定為 null
        this.writeS(null); // master name - 此處應為 null
        this.writeC(0);
        this.writeC(255);
        this.writeC(0);
        this.writeC(npc.getLevel());
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
        return S_NPCPACK_DE2;
    }
}

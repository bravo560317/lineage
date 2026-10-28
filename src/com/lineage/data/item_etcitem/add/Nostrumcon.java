package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Nostrumcon extends ItemExecutor
{
    private int a;
    private int b;
    
    private Nostrumcon() {
    }
    
    public static ItemExecutor get() {
        return new Nostrumcon();
    }
    
    @Override
    public final void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            for (int i = pc.getBaseCon(); i < this.a; ++i) {
                pc.addBaseCon(1);
                pc.setBonusStats(pc.getBonusStats() + 1);
            }
            for (int i = pc.getBaseWis(); i < this.a; ++i) {
                pc.addBaseWis(1);
                pc.setBonusStats(pc.getBonusStats() + 1);
            }
            for (int i = pc.getBaseStr(); i < this.a; ++i) {
                pc.addBaseStr(1);
                pc.setBonusStats(pc.getBonusStats() + 1);
            }
            for (int i = pc.getBaseInt(); i < this.a; ++i) {
                pc.addBaseInt(1);
                pc.setBonusStats(pc.getBonusStats() + 1);
            }
            for (int i = pc.getBaseCha(); i < this.a; ++i) {
                pc.addBaseCha(1);
                pc.setBonusStats(pc.getBonusStats() + 1);
            }
            for (int i = pc.getBaseDex(); i < this.a; ++i) {
                pc.addBaseDex(1);
                pc.setBonusStats(pc.getBonusStats() + 1);
            }
            pc.setElixirStats(this.b);
            pc.sendPackets(new S_OwnCharStatus2(pc));
            pc.save();
            pc.getInventory().removeItem(item, 1L);
            pc.sendPackets(new S_ServerMessage("服用後請登出後在進來"));
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void set_set(String[] set) {
        this.a = Integer.parseInt(set[1]);
        this.b = Integer.parseInt(set[2]);
    }
}

package com.lineage.server.clientpackets;

import java.util.Iterator;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SkillBuy;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.lock.CharSkillReading;
import java.util.ArrayList;
import com.lineage.list.PcLvSkillList;
import com.lineage.server.serverpackets.S_SkillBuyCN;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_SkillBuy extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_SkillBuy.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        if (!pc.isPrivateShop()) {
                            int objid = this.readD();
                            L1Object obj = World.get().findObject(objid);
                            if (obj == null) {
                                return;
                            }
                            else {
                                L1NpcInstance npc = null;
                                if (obj instanceof L1NpcInstance) {
                                    npc = (L1NpcInstance)obj;
                                }
                                if (npc == null) {
                                    return;
                                }
                                else if (npc.getNpcId() == 70754) {
                                    pc.get_other().set_shopSkill(true);
                                    pc.sendPackets(new S_SkillBuyCN(pc, npc));
                                    return;
                                }
                                else {
                                    pc.get_other().set_shopSkill(false);
                                    ArrayList<Integer> skillList = PcLvSkillList.scount(pc);
                                    ArrayList<Integer> newSkillList = new ArrayList();
                                    for (Integer integer : skillList) {
                                        if (!CharSkillReading.get().spellCheck(pc.getId(), integer + 1)) {
                                            newSkillList.add(integer);
                                        }
                                    }
                                    switch (npc.getNpcId()) {
                                        case 70009: {
                                            if (pc.getLawful() < 0) {
                                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengEv2"));
                                                return;
                                            }
                                            else if (newSkillList.size() <= 0) {
                                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengEv3"));
                                                return;
                                            }
                                            else {
                                                pc.sendPackets(new S_SkillBuy(pc, newSkillList));
                                                return;
                                            }
                                        }
                                        default: {
                                            pc.sendPackets(new S_SkillBuy(pc, newSkillList));
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return;
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

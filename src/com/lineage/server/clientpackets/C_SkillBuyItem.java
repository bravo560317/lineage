package com.lineage.server.clientpackets;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SkillBuyItem;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_SkillBuyItem extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_SkillBuyItem.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {}
            if (pc.isDead() || pc.isTeleport() || pc.isPrivateShop()) {
                return;
            }
            else {
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
                    else {
                        npc.getNpcId();
                        pc.get_other().set_shopSkill(false);
                        pc.sendPackets(new S_SkillBuyItem(pc, npc));
                    }
                }
            }
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

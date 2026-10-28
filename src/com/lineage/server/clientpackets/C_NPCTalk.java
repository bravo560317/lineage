package com.lineage.server.clientpackets;

import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.npc.action.L1NpcAction;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.william.new_npc_talk;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.NpcActionTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_NPCTalk extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_NPCTalk.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isDead()) {}
            if (pc.isTeleport() || pc.isPrivateShop()) {
                return;
            }
            else {
                int objid = this.readD();
                L1Object obj = World.get().findObject(objid);
                pc.get_otherList().clear();
                pc.get_other().set_gmHtml(null);
                if (obj != null && pc != null) {
                    pc.setTempID(objid);
                    if (obj instanceof L1NpcInstance) {
                        L1NpcInstance npc = (L1NpcInstance)obj;
                        if (npc.TALK != null) {
                            npc.TALK.talk(pc, npc);
                            return;
                        }
                    }
                    L1NpcAction action = NpcActionTable.getInstance().get(pc, obj);
                    if (action != null) {
                        L1NpcHtml html = action.execute("", pc, obj, new byte[0]);
                        if (html != null) {
                            pc.sendPackets(new S_NPCTalkReturn(obj.getId(), html));
                        }
                        return;
                    }
                    else if (new_npc_talk.giveBuff(pc, (L1NpcInstance)obj)) {
                        return;
                    }
                    else {
                        obj.onTalkAction(pc);
                    }
                }
                else {
                    C_NPCTalk._log.error("指定的OBJID不存在: " + objid);
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

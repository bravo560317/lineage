package com.lineage.server.model.Instance;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.L1PcQuest;
import com.lineage.server.model.L1NpcTalkData;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.NPCTalkDataTable;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1TeleporterInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private boolean _isNowDely;
    
    static {
        _log = LogFactory.getLog(L1TeleporterInstance.class);
    }
    
    public L1TeleporterInstance(L1Npc template) {
        super(template);
        this._isNowDely = false;
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            L1AttackMode attack = new L1AttackPc(pc, this);
            attack.action();
        }
        catch (Exception e) {
            L1TeleporterInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance player) {
        int objid = this.getId();
        L1NpcTalkData talking = NPCTalkDataTable.get().getTemplate(this.getNpcTemplate().get_npcId());
        int npcid = this.getNpcTemplate().get_npcId();
        L1PcQuest quest = player.getQuest();
        String htmlid = null;
        if (talking != null) {
            if (npcid == 50001) {
                if (player.isElf()) {
                    htmlid = "barnia3";
                }
                else if (player.isKnight() || player.isCrown()) {
                    htmlid = "barnia2";
                }
                else if (player.isWizard() || player.isDarkelf()) {
                    htmlid = "barnia1";
                }
            }
            if (htmlid != null) {
                player.sendPackets(new S_NPCTalkReturn(objid, htmlid));
            }
            else if (player.getLawful() < -1000) {
                player.sendPackets(new S_NPCTalkReturn(talking, objid, 2));
            }
            else {
                player.sendPackets(new S_NPCTalkReturn(talking, objid, 1));
            }
        }
    }
    
    @Override
    public void onFinalAction(L1PcInstance player, String action) {
        int objid = this.getId();
        L1NpcTalkData talking = NPCTalkDataTable.get().getTemplate(this.getNpcTemplate().get_npcId());
        if (action.equalsIgnoreCase("teleportURL")) {
            L1NpcHtml html = new L1NpcHtml(talking.getTeleportURL());
            player.sendPackets(new S_NPCTalkReturn(objid, html));
        }
        else if (action.equalsIgnoreCase("teleportURLA")) {
            L1NpcHtml html = new L1NpcHtml(talking.getTeleportURLA());
            player.sendPackets(new S_NPCTalkReturn(objid, html));
        }
        if (action.startsWith("teleport ")) {
            this.doFinalAction(player, action);
        }
    }
    
    private void doFinalAction(L1PcInstance player, String action) {
        int objid = this.getId();
        int npcid = this.getNpcTemplate().get_npcId();
        String htmlid = null;
        boolean isTeleport = true;
        if (npcid == 50043) {
            if (this._isNowDely) {
                isTeleport = false;
            }
        }
        else if (npcid == 50625 && this._isNowDely) {
            isTeleport = false;
        }
        if (isTeleport) {
            try {
                if (action.equalsIgnoreCase("teleport 29")) {
                    L1PcInstance kni = null;
                    L1PcInstance elf = null;
                    L1PcInstance wiz = null;
                    if (kni != null && elf != null && wiz != null) {
                        L1Teleport.teleport(player, 32723, 32850, (short)2000, 2, true);
                        L1Teleport.teleport(kni, 32750, 32851, (short)2000, 6, true);
                        L1Teleport.teleport(elf, 32878, 32980, (short)2000, 6, true);
                        L1Teleport.teleport(wiz, 32876, 33003, (short)2000, 0, true);
                        L1TeleporterInstance.TeleportDelyTimer timer = new L1TeleporterInstance.TeleportDelyTimer();
                        GeneralThreadPool.get().execute(timer);
                    }
                }
                else if (action.equalsIgnoreCase("teleport barlog")) {
                    L1Teleport.teleport(player, 32755, 32844, (short)2002, 5, true);
                    L1TeleporterInstance.TeleportDelyTimer timer2 = new L1TeleporterInstance.TeleportDelyTimer();
                    GeneralThreadPool.get().execute(timer2);
                }
            }
            catch (Exception ex) {}
        }
        if (htmlid != null) {
            player.sendPackets(new S_NPCTalkReturn(objid, htmlid));
        }
    }
    
    /*
    static void access$0(L1TeleporterInstance l1TeleporterInstance, boolean isNowDely) {
        l1TeleporterInstance._isNowDely = isNowDely;
    }
    */
    
    class TeleportDelyTimer implements Runnable
    {
        public TeleportDelyTimer() {
        }
        
        @Override
        public void run() {
            try {
                L1TeleporterInstance.this._isNowDely = true;
                Thread.sleep(900000L);
            }
            catch (Exception e) {
                L1TeleporterInstance.this._isNowDely = false;
            }
            L1TeleporterInstance.this._isNowDely = false;
        }
    }
}

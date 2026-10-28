package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.utils.FaceToFace;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Propose extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Propose.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int c = this.readC();
            L1PcInstance pc = client.getActiveChar();
            if (c == 0) {
                if (!pc.isGhost()) {
                    if (pc.getPartnerId() == 0 || pc.getQuest().get_step(74) == 0) {
                        L1PcInstance target = FaceToFace.faceToFace(pc);
                        if (target.getPartnerId() != 0 || target.getQuest().get_step(74) != 0) {
                            pc.sendPackets(new S_ServerMessage(658));
                            return;
                        }
                        else if (pc.get_sex() == target.get_sex()) {
                            pc.sendPackets(new S_ServerMessage(661));
                            return;
                        }
                        else if (pc.getLevel() + target.getLevel() < 50) {
                            pc.sendPackets(new S_SystemMessage("雙方總等級未等於50以上。"));
                            return;
                        }
                        else if (!this.checkPcItem(pc)) {
                            pc.sendPackets(new S_ServerMessage(659));
                            return;
                        }
                        else if (!this.checkTargetItem(target)) {
                            pc.sendPackets(new S_ServerMessage(660));
                            return;
                        }
                        else if (pc.getX() >= 33974 && pc.getX() <= 33976 && pc.getY() >= 33362 && pc.getY() <= 33365 && pc.getMapId() == 4 && target.getX() >= 33974 && target.getX() <= 33976 && target.getY() >= 33362 && target.getY() <= 33365 && target.getMapId() == 4) {
                            target.setTempID(pc.getId());
                            target.sendPackets(new S_Message_YN(654, pc.getName()));
                            return;
                        }
                        else {
                            pc.sendPackets(new S_SystemMessage("必須在教堂中才能進行。"));
                            return;
                        }
                    }
                    else {
                        return;
                    }
                }
            }
            else if (c == 1) {
                if (pc.getPartnerId() == 0) {
                    pc.sendPackets(new S_ServerMessage(662));
                }
                else {
                    pc.sendPackets(new S_Message_YN(653, ""));
                    return;
                }
            }
            else {
                return;
            }
            return;
        }
        catch (Exception e) {
            C_Propose._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    public boolean checkPcItem(L1PcInstance pc) {
        boolean PcRing = false;
        int[] Ties = { 40901, 40902, 40903, 40904, 40905, 40906, 40907, 40908 };
        for (int i = 0; i < Ties.length; ++i) {
            if (pc.getInventory().checkItem(Ties[i])) {
                PcRing = true;
            }
        }
        return PcRing;
    }
    
    public boolean checkTargetItem(L1PcInstance target) {
        boolean TargetRing = false;
        int[] Ties = { 40901, 40902, 40903, 40904, 40905, 40906, 40907, 40908 };
        for (int i = 0; i < Ties.length; ++i) {
            if (target.getInventory().checkItem(Ties[i])) {
                TargetRing = true;
            }
        }
        return TargetRing;
    }
}

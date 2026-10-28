package com.lineage.server.model.poison;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;

public abstract class L1Poison
{
    protected static boolean isValidTarget(L1Character cha) {
        if (cha == null) {
            return false;
        }
        else if (cha.getPoison() != null) {
            return false;
        }
        else if (!(cha instanceof L1PcInstance)) {
            return true;
        }
        else {
            L1PcInstance player = (L1PcInstance)cha;
            return player.get_venom_resist() <= 0 && !player.hasSkillEffect(104) && !player.hasSkillEffect(6687);
        }
    }
    
    protected static void sendMessageIfPlayer(L1Character cha, int msgId) {
        if (!(cha instanceof L1PcInstance)) {
            return;
        }
        else {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.sendPackets(new S_ServerMessage(msgId));
            return;
        }
    }
    
    public abstract int getEffectId();
    
    public abstract void cure();
}

package com.lineage.server.model.Instance;

import com.lineage.server.model.L1AttackMode;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.utils.CalcExp;
import java.util.ArrayList;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ScarecrowInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1ScarecrowInstance.class);
    }
    
    public L1ScarecrowInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onAction(L1PcInstance player) {
        try {
            L1AttackMode attack = new L1AttackPc(player, this);
            if (attack.calcHit()) {
                int damage = attack.calcDamage();
                if (this.getNpcId() >= 45001 && this.getNpcId() <= 45003) {
                    player.sendPackets(new S_ServerMessage(166, "傷害輸出: " + damage));
                }
                if (player.getLevel() < ConfigOther.Scarecrowlevel) {
                    ArrayList<L1PcInstance> targetList = new ArrayList();
                    targetList.add(player);
                    ArrayList<Integer> hateList = new ArrayList();
                    hateList.add(1);
                    int exp = (int)this.getExp();
                    CalcExp.calcExp(player, this.getId(), targetList, hateList, (long)exp);
                }
                this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 2));
            }
            else if (this.getNpcId() >= 45001 && this.getNpcId() <= 45003) {
                player.sendPackets(new S_ServerMessage(166, "未命中"));
            }
            attack.action();
        }
        catch (Exception e) {
            L1ScarecrowInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance l1pcinstance) {
    }
    
    public void onFinalAction() {
    }
    
    public void doFinalAction() {
    }
}

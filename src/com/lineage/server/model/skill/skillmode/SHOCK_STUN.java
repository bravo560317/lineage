package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1GuardInstance;
import com.lineage.server.model.Instance.L1GuardianInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.utils.L1SpawnUtil;
import java.util.Random;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class SHOCK_STUN extends SkillMode
{
	@Override
	public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
	    int dmg = 0;
	    
	    boolean isPrince = (srcpc.getClassId() == 0);
	    L1ItemInstance weapon = srcpc.getWeapon();

	    if (isPrince || (weapon != null && weapon.getItem().getType() == 3)) {
	        if (cha.hasSkillEffect(8002)) {
	            return dmg;
	        } else {
	            Random random = new Random();
	            int shock = random.nextInt(5) + 1;

	            if (cha instanceof L1PcInstance && cha.hasSkillEffect(87)) {
	                shock += cha.getSkillEffectTimeSec(87);
	            }

	            if (shock > 6) {
	                shock = 6;
	                cha.setSkillEffect(8002, shock * 1000);
	            }

	            cha.setSkillEffect(87, shock * 1000);
	            L1SpawnUtil.spawnEffect(81162, shock, cha.getX(), cha.getY(), cha.getMapId(), cha, 0);

	            if (cha instanceof L1PcInstance) {
	                L1PcInstance pc = (L1PcInstance) cha;
	                pc.sendPackets(new S_Paralysis(5, true));
	            } else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
	                L1NpcInstance tgnpc = (L1NpcInstance) cha;
	                tgnpc.setParalyzed(true);
	            }
	        }
	    }

	    return dmg;
	}

    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = magic.calcMagicDamage(87);
        if (cha.hasSkillEffect(8002)) {
            return dmg;
        }
        else {
            Random random = new Random();
            int shock = random.nextInt(4) + 1;
            if (cha instanceof L1PcInstance && cha.hasSkillEffect(87)) {
                shock += cha.getSkillEffectTimeSec(87);
            }
            if (shock > 6) {
                shock = 6;
                cha.setSkillEffect(8002, shock * 1000);
            }
            cha.setSkillEffect(87, shock * 1000);
            L1SpawnUtil.spawnEffect(81162, shock, cha.getX(), cha.getY(), cha.getMapId(), cha, 0);
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                pc.sendPackets(new S_Paralysis(5, true));
            }
            else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1GuardianInstance || cha instanceof L1GuardInstance || cha instanceof L1PetInstance) {
                L1NpcInstance tgnpc = (L1NpcInstance)cha;
                tgnpc.setParalyzed(true);
            }
            return 0;
        }
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.sendPackets(new S_Paralysis(5, false));
        }
        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1GuardianInstance || cha instanceof L1GuardInstance || cha instanceof L1PetInstance) {
            L1NpcInstance npc = (L1NpcInstance)cha;
            npc.setParalyzed(false);
        }
    }
}

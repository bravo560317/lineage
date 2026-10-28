package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1SkinInstance;
import com.lineage.server.model.Instance.L1IllusoryInstance;
import com.lineage.server.model.Instance.L1GamInstance;
import com.lineage.server.model.Instance.L1GamblingInstance;
import com.lineage.server.model.Instance.L1CnInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Character;
import com.lineage.server.world.WorldPet;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_SelectTarget extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_SelectTarget.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int petId = this.readD();
            int type = this.readC();
            int targetId = this.readD();
            L1PetInstance pet = WorldPet.get().get(petId);
            if (pet == null) {
                return;
            }
            else {
                L1Character target = (L1Character)World.get().findObject(targetId);
                if (target == null) {
                    return;
                }
                else {
                    boolean isCheck = false;
                    if (target instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)target;
                        if (tgpc.checkNonPvP(tgpc, pet)) {
                            return;
                        }
                        else {
                            isCheck = true;
                        }
                    }
                    else if (target instanceof L1DeInstance) {
                        isCheck = true;
                    }
                    else if (target instanceof L1PetInstance) {
                        isCheck = true;
                    }
                    else if (target instanceof L1SummonInstance) {
                        isCheck = true;
                    }
                    else if (target instanceof L1CnInstance) {
                        return;
                    }
                    else if (target instanceof L1GamblingInstance) {
                        return;
                    }
                    else if (target instanceof L1GamInstance) {
                        return;
                    }
                    else if (target instanceof L1IllusoryInstance) {
                        return;
                    }
                    else if (target instanceof L1SkinInstance) {
                        this.over();
                        return;
                    }
                    if (isCheck && target.isSafetyZone()) {
                        return;
                    }
                    else {
                        pet.setMasterSelectTarget(target);
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

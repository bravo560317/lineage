package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_PetInventory;
import com.lineage.server.world.WorldPet;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_PetMenu extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_PetMenu.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc == null) {}
            if (pc.isGhost() || pc.isTeleport() || pc.isPrivateShop()) {
                return;
            }
            else {
                int petId = this.readD();
                L1PetInstance pet = WorldPet.get().get(petId);
                if (pet == null) {
                    return;
                }
                else if (pc.getPetList().get((Integer)petId) == null) {
                    return;
                }
                else {
                    pc.sendPackets(new S_PetInventory(pet, true));
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

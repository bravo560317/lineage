package com.lineage.server.clientpackets;

import java.util.Iterator;
import java.util.List;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_FixWeaponList;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.ArrayList;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_FixWeaponList extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_FixWeaponList.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance pc = client.getActiveChar();
            List<L1ItemInstance> weaponList = new ArrayList();
            List<L1ItemInstance> itemList = pc.getInventory().getItems();
            for (L1ItemInstance item : itemList) {
                switch (item.getItem().getType2()) {
                    case 1: {
                        if (item.get_durability() > 0) {
                            weaponList.add(item);
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    default: {
                        continue;
                    }
                }
            }
            pc.sendPackets(new S_FixWeaponList(weaponList));
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

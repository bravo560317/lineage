package com.lineage.data.item_etcitem.reel;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Destroys_Helmet_Reel extends ItemExecutor
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(Destroys_Helmet_Reel.class);
        _random = new Random();
    }
    
    public static ItemExecutor get() {
        return new Destroys_Helmet_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int itemobj = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(itemobj);
            if (tgItem.getBless() >= 128) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else if (tgItem.isEquipped()) {
                pc.sendPackets(new S_ServerMessage("(預防誤點機制啟動)裝備中無法強化"));
                return;
            }
            else {
                int getItemId_1 = 0;
                int getItemId_2 = 0;
                if (tgItem.getItem().getType2() == 2) {
                    switch (tgItem.getItem().getItemId()) {
                        case 20137:
                        case 120137: {
                            getItemId_1 = 26001;
                            getItemId_2 = 26003;
                            break;
                        }
                        case 20138: {
                            getItemId_1 = 26007;
                            getItemId_2 = 26009;
                            break;
                        }
                        case 26000: {
                            getItemId_1 = 26004;
                            getItemId_2 = 26006;
                            break;
                        }
                    }
                    pc.getInventory().removeItem(item, 1L);
                    if (getItemId_1 == 0) {
                        pc.sendPackets(new S_ServerMessage(154));
                    }
                    else {
                        int random = Destroys_Helmet_Reel._random.nextInt(1000);
                        if (random >= 980 && random < 1000) {
                            pc.sendPackets(new S_ServerMessage(3101, tgItem.getLogName()));
                            pc.getInventory().removeItem(tgItem, tgItem.getCount());
                            CreateNewItem.createNewItem(pc, getItemId_1, 1L);
                            CreateNewItem.createNewItem(pc, getItemId_2, 1L);
                        }
                        else if (random > 750 && random < 980) {
                            pc.sendPackets(new S_ServerMessage(169));
                            pc.getInventory().removeItem(tgItem, tgItem.getCount());
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(3100, tgItem.getLogName()));
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(79));
                }
            }
        }
        catch (Exception e) {
            Destroys_Helmet_Reel._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void execute2(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else if (item2.getBless() >= 128) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            if (item2.getItem().getType2() == 2) {
                int msg = 0;
                switch (item2.getItem().getType()) {
                    case 1: {
                        msg = 171;
                        break;
                    }
                    case 2: {
                        msg = 169;
                        break;
                    }
                    case 3: {
                        msg = 170;
                        break;
                    }
                    case 4: {
                        msg = 168;
                        break;
                    }
                    case 5: {
                        msg = 172;
                        break;
                    }
                    case 6: {
                        msg = 173;
                        break;
                    }
                    case 7: {
                        msg = 174;
                        break;
                    }
                    default: {
                        msg = 167;
                        break;
                    }
                }
                pc.sendPackets(new S_ServerMessage(msg));
                pc.getInventory().removeItem(item2, 1L);
            }
            else {
                pc.sendPackets(new S_ServerMessage(154));
            }
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
}

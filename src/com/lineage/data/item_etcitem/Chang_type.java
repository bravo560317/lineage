package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Chang_type extends ItemExecutor
{
    private int a;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Chang_type.class);
    }
    
    private Chang_type() {
    }
    
    public static ItemExecutor get() {
        return new Chang_type();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        L1ItemInstance item2 = pc.getInventory().getItemEquipped(2, 7);
        L1ItemInstance item3 = pc.getInventory().getItemEquipped(2, 13);
        L1ItemInstance t1 = pc.getInventory().findItemId(pc.getchangtype1());
        L1ItemInstance t2 = pc.getInventory().findItemId(pc.getchangtype2());
        L1ItemInstance t3 = pc.getInventory().findItemId(pc.getchangtype4());
        L1ItemInstance t4 = pc.getInventory().findItemId(pc.getchangtype5());
        if (pc.getWeapon() == null) {
            pc.sendPackets(new S_ServerMessage("未裝備武器"));
            return;
        }
        else {
            L1ItemInstance weapon = pc.getWeapon();
            if (pc.getchangtype3() <= 2) {
                if (pc.getchangtype3() == 0 && pc.getchangtype1() == 0 && pc.getchangtype2() == 0 && weapon != null && pc.getInventory().getItemEquipped(2, 7) != null) {
                    pc.setchangtype1(weapon.getItemId());
                    pc.setchangtype2(item2.getItemId());
                    pc.setchangtype3(1);
                    pc.sendPackets(new S_ServerMessage("已儲存 第一組武器切換 記憶"));
                    pc.setchangtypename1(weapon.getName());
                    pc.setchangtypename2(item2.getName());
                    pc.sendPackets(new S_ServerMessage("\\aH武器=>" + pc.getchangtypename1()));
                    pc.sendPackets(new S_ServerMessage("\\aH盾牌=>" + pc.getchangtypename2()));
                }
                else if (pc.getchangtype3() == 1 && pc.getchangtype4() == 0 && pc.getchangtype5() == 0 && weapon != null && pc.getInventory().getItemEquipped(2, 13) != null) {
                    pc.setchangtype4(weapon.getItemId());
                    pc.setchangtype5(item3.getItemId());
                    pc.setchangtype3(2);
                    pc.sendPackets(new S_ServerMessage("已儲存 第二組武器切換 記憶"));
                    pc.setchangtypename3(weapon.getName());
                    pc.setchangtypename4(item3.getName());
                    pc.sendPackets(new S_ServerMessage("\\aH武器=>" + pc.getchangtypename3()));
                    pc.sendPackets(new S_ServerMessage("\\aH盾牌=>" + pc.getchangtypename4()));
                    return;
                }
                else if (pc.getchangtype3() == 0 && pc.getchangtype1() == 0 && pc.getchangtype2() == 0) {
                    pc.sendPackets(new S_ServerMessage("第一組記憶:偵測您未裝備武器 跟 盾牌"));
                }
                else if (pc.getchangtype3() == 1 && pc.getchangtype4() == 0 && pc.getchangtype5() == 0) {
                    pc.sendPackets(new S_ServerMessage("第二組記憶:偵測您未裝備武器 跟 臂甲"));
                }
            }
            if (pc.getchangtype1() > 0 && pc.getchangtype2() > 0 && pc.getchangtype4() > 0 && pc.getchangtype5() > 0) {
                if (pc.getchangtype3() == 2) {
                    pc.setchangtype3(1);
                    pc.getInventory().setEquipped(pc.getWeapon(), false, false, false);
                    if (item3 != null) {
                        pc.getInventory().setEquipped(item3, false, false, false);
                    }
                    if (item2 != null) {
                        pc.getInventory().setEquipped(item2, false, false, false);
                    }
                    try {
                        Thread.sleep(400L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pc.getInventory().setEquipped(t1, true, false, false);
                    pc.getInventory().setEquipped(t2, true, false, false);
                    pc.sendPackets(new S_ServerMessage("\\aD切換第一組裝備"));
                    pc.sendPackets(new S_ServerMessage("\\aE武器=>" + pc.getchangtypename1()));
                    pc.sendPackets(new S_ServerMessage("\\aE盾牌=>" + pc.getchangtypename2()));
                }
                else if (pc.getchangtype3() == 1) {
                    pc.setchangtype3(2);
                    pc.getInventory().setEquipped(pc.getWeapon(), false, false, false);
                    if (item3 != null) {
                        pc.getInventory().setEquipped(item3, false, false, false);
                    }
                    if (item2 != null) {
                        pc.getInventory().setEquipped(item2, false, false, false);
                    }
                    try {
                        Thread.sleep(400L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pc.getInventory().setEquipped(t3, true, false, false);
                    pc.getInventory().setEquipped(t4, true, false, false);
                    pc.sendPackets(new S_ServerMessage("\\aD切換第二組裝備"));
                    pc.sendPackets(new S_ServerMessage("\\aE武器=>" + pc.getchangtypename3()));
                    pc.sendPackets(new S_ServerMessage("\\aE盾牌=>" + pc.getchangtypename4()));
                }
            }
            return;
        }
    }
}

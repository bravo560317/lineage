package com.lineage.data.cmd;

import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.config.Configtype;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class EnchantWeapondiy extends EnchantExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(EnchantWeapondiy.class);
    }
    
    @Override
    public void failureEnchant(L1PcInstance pc, L1ItemInstance item) {
        StringBuilder s = new StringBuilder();
        if (pc.get_other().get_item() != null) {
            pc.sendPackets(new S_SystemMessage("\\fT物品正在進行託售中.請在重新操作一次"));
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.get_other().set_item(null);
            return;
        }
        else {
            String pm = "";
            if (item.getEnchantLevel() > 0) {
                pm = "+";
            }
            s.append(pm + item.getEnchantLevel() + " " + item.getName());
            if (!pc.isGm() && Configtype.weaponbroadfail == 1 && item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.weapon_savebroad) {
                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Configtype.msg1_fail, pc.getName(), s.toString())));
            }
            pc.sendPackets(new S_ServerMessage("武器似乎沒發生什麼事情。"));
            return;
        }
    }
    
    public void failureEnchantdiy(L1PcInstance pc, L1ItemInstance item) {
        StringBuilder s = new StringBuilder();
        if (pc.get_other().get_item() != null) {
            pc.sendPackets(new S_SystemMessage("\\fT物品正在進行託售中.請在重新操作一次"));
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.get_other().set_item(null);
            return;
        }
        else {
            String pm = "";
            if (item.getEnchantLevel() > 0) {
                pm = "+";
            }
            s.append(pm + item.getEnchantLevel() + " " + item.getName());
            if (!pc.isGm() && Configtype.weaponbroadfail == 1 && item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.weapon_savebroad) {
                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Configtype.msg1_fail, pc.getName(), s.toString())));
            }
            pc.sendPackets(new S_ServerMessage(164, s.toString(), "$252"));
            return;
        }
    }
    
    @Override
    public void successEnchant(L1PcInstance pc, L1ItemInstance item, int i) {
        StringBuilder s = new StringBuilder();
        StringBuilder sa = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        if (!item.isIdentified()) {
            s.append(item.getName());
        }
        else {
            s.append(item.getLogName());
        }
        switch (i) {
            case -1: {
                sa.append("$246");
                sb.append("$247");
                break;
            }
            case 0: {
                pc.sendPackets(new S_ServerMessage(160, s.toString(), "$252", "$248"));
                return;
            }
            case 1: {
                sa.append("$245");
                sb.append("$247");
                break;
            }
            case 2:
            case 3: {
                sa.append("$245");
                sb.append("$248");
                break;
            }
        }
        pc.sendPackets(new S_ServerMessage(161, s.toString(), sa.toString(), sb.toString()));
        int oldEnchantLvl = item.getEnchantLevel();
        int newEnchantLvl = oldEnchantLvl + i;
        item.setEnchantLevel(newEnchantLvl);
        pc.getInventory().updateItem(item, 4);
        pc.getInventory().saveItem(item, 4);
        if (oldEnchantLvl != newEnchantLvl) {
            if (!pc.isGm() && Configtype.weaponbroadtrue == 1 && item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.weapon_savebroad) {
                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Configtype.msg1_true, pc.getName(), s.toString())));
            }
            pc.sendPackets(new S_ItemStatus(item));
            pc.getInventory().saveItem(item, 4);
        }
    }
}

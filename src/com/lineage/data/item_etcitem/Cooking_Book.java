package com.lineage.data.item_etcitem;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import java.util.Random;
import com.lineage.server.serverpackets.S_PacketBoxCooking;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Cooking_Book extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Cooking_Book();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        int cookStatus = data[0];
        int cookNo = data[1];
        if (cookStatus == 0) {
            pc.sendPackets(new S_PacketBoxCooking(itemId - 41255));
        }
        else {
            this.makeCooking(pc, cookNo);
        }
    }
    
    private void makeCooking(L1PcInstance pc, int cookNo) {
        Random random = new Random();
        boolean isNearFire = false;
        for (L1Object obj : World.get().getVisibleObjects(pc, 3)) {
            if (obj instanceof L1EffectInstance) {
                L1EffectInstance effect = (L1EffectInstance)obj;
                if (effect.getGfxId() == 5943) {
                    isNearFire = true;
                    break;
                }
                else {
                    continue;
                }
            }
        }
        if (!isNearFire) {
            pc.sendPackets(new S_ServerMessage(1160));
            return;
        }
        else if (pc.getMaxWeight() <= (double)pc.getInventory().getWeight()) {
            pc.sendPackets(new S_ServerMessage(1103));
            return;
        }
        else if (pc.hasSkillEffect(2999)) {
            return;
        }
        else {
            pc.setSkillEffect(2999, 3000);
            int chance = random.nextInt(100) + 1;
            boolean is6392 = false;
            boolean is6393 = false;
            boolean is6394 = false;
            boolean isErr = false;
            int itemid = 0;
            switch (cookNo) {
                case 0: {
                    if (pc.getInventory().checkItem(40057, 1L)) {
                        pc.getInventory().consumeItem(40057, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 41277;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 41285;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 1: {
                    if (pc.getInventory().checkItem(41275, 1L)) {
                        pc.getInventory().consumeItem(41275, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 41278;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 41286;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 2: {
                    if (pc.getInventory().checkItem(41263, 1L) && pc.getInventory().checkItem(41265, 1L)) {
                        pc.getInventory().consumeItem(41263, 1L);
                        pc.getInventory().consumeItem(41265, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 41279;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 41287;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 3: {
                    if (pc.getInventory().checkItem(41274, 1L) && pc.getInventory().checkItem(41267, 1L)) {
                        pc.getInventory().consumeItem(41274, 1L);
                        pc.getInventory().consumeItem(41267, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 41280;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 41288;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 4: {
                    if (pc.getInventory().checkItem(40062, 1L) && pc.getInventory().checkItem(40069, 1L) && pc.getInventory().checkItem(40064, 1L)) {
                        pc.getInventory().consumeItem(40062, 1L);
                        pc.getInventory().consumeItem(40069, 1L);
                        pc.getInventory().consumeItem(40064, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 41281;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 41289;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 5: {
                    if (pc.getInventory().checkItem(40056, 1L) && pc.getInventory().checkItem(40060, 1L) && pc.getInventory().checkItem(40061, 1L)) {
                        pc.getInventory().consumeItem(40056, 1L);
                        pc.getInventory().consumeItem(40060, 1L);
                        pc.getInventory().consumeItem(40061, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 41282;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 41290;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 6: {
                    if (pc.getInventory().checkItem(41276, 1L)) {
                        pc.getInventory().consumeItem(41276, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 41283;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 41291;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 7: {
                    if (pc.getInventory().checkItem(40499, 1L) && pc.getInventory().checkItem(40060, 1L)) {
                        pc.getInventory().consumeItem(40499, 1L);
                        pc.getInventory().consumeItem(40060, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 41284;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 41292;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 8: {
                    if (pc.getInventory().checkItem(49040, 1L) && pc.getInventory().checkItem(49048, 1L)) {
                        pc.getInventory().consumeItem(49040, 1L);
                        pc.getInventory().consumeItem(49048, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49049;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49057;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 9: {
                    if (pc.getInventory().checkItem(49041, 1L) && pc.getInventory().checkItem(49048, 1L)) {
                        pc.getInventory().consumeItem(49041, 1L);
                        pc.getInventory().consumeItem(49048, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49050;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49058;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 10: {
                    if (pc.getInventory().checkItem(49042, 1L) && pc.getInventory().checkItem(41265, 1L) && pc.getInventory().checkItem(49048, 1L)) {
                        pc.getInventory().consumeItem(49042, 1L);
                        pc.getInventory().consumeItem(41265, 1L);
                        pc.getInventory().consumeItem(49048, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49051;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49059;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 11: {
                    if (pc.getInventory().checkItem(49043, 1L) && pc.getInventory().checkItem(49048, 1L)) {
                        pc.getInventory().consumeItem(49043, 1L);
                        pc.getInventory().consumeItem(49048, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49052;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49060;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 12: {
                    if (pc.getInventory().checkItem(49044, 1L) && pc.getInventory().checkItem(49048, 1L)) {
                        pc.getInventory().consumeItem(49044, 1L);
                        pc.getInventory().consumeItem(49048, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49053;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49061;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 13: {
                    if (pc.getInventory().checkItem(49045, 1L) && pc.getInventory().checkItem(49048, 1L)) {
                        pc.getInventory().consumeItem(49045, 1L);
                        pc.getInventory().consumeItem(49048, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49054;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49062;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 14: {
                    if (pc.getInventory().checkItem(49046, 1L) && pc.getInventory().checkItem(49048, 1L)) {
                        pc.getInventory().consumeItem(49046, 1L);
                        pc.getInventory().consumeItem(49048, 1L);
                        if (chance >= 1 && chance <= 30) {
                            itemid = 49055;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 31 && chance <= 65) {
                            itemid = 49063;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 66 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 15: {
                    if (pc.getInventory().checkItem(49047, 1L) && pc.getInventory().checkItem(40499, 1L) && pc.getInventory().checkItem(49048, 1L)) {
                        pc.getInventory().consumeItem(49047, 1L);
                        pc.getInventory().consumeItem(40499, 1L);
                        pc.getInventory().consumeItem(49048, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49056;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49064;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 16: {
                    if (pc.getInventory().checkItem(49048, 1L) && pc.getInventory().checkItem(49243, 1L) && pc.getInventory().checkItem(49260, 1L)) {
                        pc.getInventory().consumeItem(49048, 1L);
                        pc.getInventory().consumeItem(49243, 1L);
                        pc.getInventory().consumeItem(49260, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49244;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49252;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 17: {
                    if (pc.getInventory().checkItem(49048, 1L) && pc.getInventory().checkItem(49243, 1L) && pc.getInventory().checkItem(49261, 1L)) {
                        pc.getInventory().consumeItem(49048, 1L);
                        pc.getInventory().consumeItem(49243, 1L);
                        pc.getInventory().consumeItem(49261, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49245;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49253;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 18: {
                    if (pc.getInventory().checkItem(49048, 1L) && pc.getInventory().checkItem(49243, 1L) && pc.getInventory().checkItem(49262, 1L)) {
                        pc.getInventory().consumeItem(49048, 1L);
                        pc.getInventory().consumeItem(49243, 1L);
                        pc.getInventory().consumeItem(49262, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49246;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49254;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 19: {
                    if (pc.getInventory().checkItem(49048, 1L) && pc.getInventory().checkItem(49243, 1L) && pc.getInventory().checkItem(49263, 1L)) {
                        pc.getInventory().consumeItem(49048, 1L);
                        pc.getInventory().consumeItem(49243, 1L);
                        pc.getInventory().consumeItem(49263, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49247;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49255;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 20: {
                    if (pc.getInventory().checkItem(49048, 1L) && pc.getInventory().checkItem(49243, 1L) && pc.getInventory().checkItem(49264, 1L)) {
                        pc.getInventory().consumeItem(49048, 1L);
                        pc.getInventory().consumeItem(49243, 1L);
                        pc.getInventory().consumeItem(49264, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49248;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49256;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 21: {
                    if (pc.getInventory().checkItem(49048, 1L) && pc.getInventory().checkItem(49243, 1L) && pc.getInventory().checkItem(49265, 1L)) {
                        pc.getInventory().consumeItem(49048, 1L);
                        pc.getInventory().consumeItem(49243, 1L);
                        pc.getInventory().consumeItem(49265, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49249;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49257;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 22: {
                    if (pc.getInventory().checkItem(49048, 1L) && pc.getInventory().checkItem(49243, 1L) && pc.getInventory().checkItem(49266, 1L)) {
                        pc.getInventory().consumeItem(49048, 1L);
                        pc.getInventory().consumeItem(49243, 1L);
                        pc.getInventory().consumeItem(49266, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49250;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49258;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
                case 23: {
                    if (pc.getInventory().checkItem(49048, 1L) && pc.getInventory().checkItem(49243, 1L) && pc.getInventory().checkItem(49267, 1L)) {
                        pc.getInventory().consumeItem(49048, 1L);
                        pc.getInventory().consumeItem(49243, 1L);
                        pc.getInventory().consumeItem(49267, 1L);
                        if (chance >= 1 && chance <= 90) {
                            itemid = 49251;
                            is6392 = true;
                            break;
                        }
                        else if (chance >= 91 && chance <= 95) {
                            itemid = 49259;
                            is6393 = true;
                            break;
                        }
                        else if (chance >= 96 && chance <= 100) {
                            is6394 = true;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        isErr = true;
                        break;
                    }
                }
            }
            if (is6392) {
                if (itemid != 0) {
                    CreateNewItem.createNewItem(pc, itemid, 1L);
                }
                pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 6392));
            }
            if (is6393) {
                if (itemid != 0) {
                    CreateNewItem.createNewItem(pc, itemid, 1L);
                }
                pc.sendPacketsAll(new S_SkillSound(pc.getId(), 6390));
            }
            if (is6394) {
                pc.sendPackets(new S_ServerMessage(1101));
                pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 6394));
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(1102));
            }
            return;
        }
    }
}

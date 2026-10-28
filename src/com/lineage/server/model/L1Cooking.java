package com.lineage.server.model;

import com.lineage.server.serverpackets.S_PacketBoxCooking;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Cooking
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Cooking.class);
    }
    
    private L1Cooking() {
    }
    
    public static void useCookingItem(L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItem().getItemId();
        switch (itemId) {
            case 41284:
            case 41292:
            case 49056:
            case 49064:
            case 49251:
            case 49259:
            case 49828: {
                if (pc.get_food() != 225) {
                    pc.sendPackets(new S_ServerMessage("飽食度未滿無法使用"));
                    return;
                }
                else {
                    break;
                }
            }
        }
        if ((itemId >= 41277 && itemId <= 41283) || (itemId >= 41285 && itemId <= 41291) || (itemId >= 49049 && itemId <= 49055) || (itemId >= 49057 && itemId <= 49063) || (itemId >= 49244 && itemId <= 49250) || (itemId >= 49252 && itemId <= 49258) || (itemId >= 49825 && itemId <= 49827)) {
            int cookingId = pc.getCookingId();
            if (cookingId != 0) {
                pc.removeSkillEffect(cookingId);
            }
        }
        if (itemId == 41284 || itemId == 41292 || itemId == 49056 || itemId == 49064 || itemId == 49251 || itemId == 49259 || itemId == 49828) {
            int dessertId = pc.getDessertId();
            if (dessertId != 0) {
                pc.removeSkillEffect(dessertId);
            }
        }
        int time = 900;
        if (itemId == 41277 || itemId == 41285) {
            int cookingId;
            if (itemId == 41277) {
                cookingId = 3000;
            }
            else {
                cookingId = 3008;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 41278 || itemId == 41286) {
            int cookingId;
            if (itemId == 41278) {
                cookingId = 3001;
            }
            else {
                cookingId = 3009;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 41279 || itemId == 41287) {
            int cookingId;
            if (itemId == 41279) {
                cookingId = 3002;
            }
            else {
                cookingId = 3010;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 41280 || itemId == 41288) {
            int cookingId;
            if (itemId == 41280) {
                cookingId = 3003;
            }
            else {
                cookingId = 3011;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 41281 || itemId == 41289) {
            int cookingId;
            if (itemId == 41281) {
                cookingId = 3004;
            }
            else {
                cookingId = 3012;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 41282 || itemId == 41290) {
            int cookingId;
            if (itemId == 41282) {
                cookingId = 3005;
            }
            else {
                cookingId = 3013;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 41283 || itemId == 41291) {
            int cookingId;
            if (itemId == 41283) {
                cookingId = 3006;
            }
            else {
                cookingId = 3014;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 41284 || itemId == 41292) {
            int cookingId;
            if (itemId == 41284) {
                cookingId = 3007;
            }
            else {
                cookingId = 3015;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49049 || itemId == 49057) {
            int cookingId;
            if (itemId == 49049) {
                cookingId = 3016;
            }
            else {
                cookingId = 3024;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49050 || itemId == 49058) {
            int cookingId;
            if (itemId == 49050) {
                cookingId = 3017;
            }
            else {
                cookingId = 3025;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49051 || itemId == 49059) {
            int cookingId;
            if (itemId == 49051) {
                cookingId = 3018;
            }
            else {
                cookingId = 3026;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49052 || itemId == 49060) {
            int cookingId;
            if (itemId == 49052) {
                cookingId = 3019;
            }
            else {
                cookingId = 3027;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49053 || itemId == 49061) {
            int cookingId;
            if (itemId == 49053) {
                cookingId = 3020;
            }
            else {
                cookingId = 3028;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49054 || itemId == 49062) {
            int cookingId;
            if (itemId == 49054) {
                cookingId = 3021;
            }
            else {
                cookingId = 3029;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49055 || itemId == 49063) {
            int cookingId;
            if (itemId == 49055) {
                cookingId = 3022;
            }
            else {
                cookingId = 3030;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49056 || itemId == 49064) {
            int cookingId;
            if (itemId == 49056) {
                cookingId = 3023;
            }
            else {
                cookingId = 3031;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49244 || itemId == 49252) {
            int cookingId;
            if (itemId == 49244) {
                cookingId = 3032;
            }
            else {
                cookingId = 3040;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49245 || itemId == 49253) {
            int cookingId;
            if (itemId == 49245) {
                cookingId = 3033;
            }
            else {
                cookingId = 3041;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49246 || itemId == 49254) {
            int cookingId;
            if (itemId == 49246) {
                cookingId = 3034;
            }
            else {
                cookingId = 3042;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49247 || itemId == 49255) {
            int cookingId;
            if (itemId == 49247) {
                cookingId = 3035;
            }
            else {
                cookingId = 3043;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49248 || itemId == 49256) {
            int cookingId;
            if (itemId == 49248) {
                cookingId = 3036;
            }
            else {
                cookingId = 3044;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49249 || itemId == 49257) {
            int cookingId;
            if (itemId == 49249) {
                cookingId = 3037;
            }
            else {
                cookingId = 3045;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49250 || itemId == 49258) {
            int cookingId;
            if (itemId == 49250) {
                cookingId = 3038;
            }
            else {
                cookingId = 3046;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49251 || itemId == 49259) {
            int cookingId;
            if (itemId == 49251) {
                cookingId = 3039;
            }
            else {
                cookingId = 3047;
            }
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49825) {
            int cookingId = 3048;
            time = 1800;
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49826) {
            int cookingId = 3049;
            time = 1800;
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49827) {
            int cookingId = 3050;
            time = 1800;
            eatCooking(pc, cookingId, time);
        }
        else if (itemId == 49828) {
            int cookingId = 3051;
            time = 1800;
            eatCooking(pc, cookingId, time);
        }
        pc.sendPackets(new S_ServerMessage(76, item.getNumberedName(1L)));
        pc.getInventory().removeItem(item, 1L);
    }
    
    public static void eatCooking(L1PcInstance pc, int cookingId, int time) {
        int cookingType = 0;
        switch (cookingId) {
            case 3000:
            case 3008: {
                cookingType = 0;
                pc.addWind(10);
                pc.addWater(10);
                pc.addFire(10);
                pc.addEarth(10);
                pc.sendPackets(new S_OwnCharAttrDef(pc));
                break;
            }
            case 3001:
            case 3009: {
                cookingType = 1;
                pc.addMaxHp(30);
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                if (pc.isInParty()) {
                    pc.getParty().updateMiniHP(pc);
                    break;
                }
                else {
                    break;
                }
            }
            case 3002:
            case 3010: {
                cookingType = 2;
                break;
            }
            case 3003:
            case 3011: {
                cookingType = 3;
                pc.addAc(-1);
                pc.sendPackets(new S_OwnCharStatus(pc));
                break;
            }
            case 3004:
            case 3012: {
                cookingType = 4;
                pc.addMaxMp(20);
                pc.sendPackets(new S_MPUpdate(pc));
                break;
            }
            case 3005:
            case 3013: {
                cookingType = 5;
                break;
            }
            case 3006:
            case 3014: {
                cookingType = 6;
                pc.addMr(5);
                pc.sendPackets(new S_SPMR(pc));
                break;
            }
            case 3007:
            case 3015: {
                cookingType = 7;
                break;
            }
            case 3016:
            case 3024: {
                cookingType = 16;
                break;
            }
            case 3017:
            case 3025: {
                cookingType = 17;
                pc.addMaxHp(30);
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                if (pc.isInParty()) {
                    pc.getParty().updateMiniHP(pc);
                }
                pc.addMaxMp(30);
                pc.sendPackets(new S_MPUpdate(pc));
                break;
            }
            case 3018:
            case 3026: {
                cookingType = 18;
                pc.addAc(-2);
                pc.sendPackets(new S_OwnCharStatus(pc));
                break;
            }
            case 3019:
            case 3027: {
                cookingType = 19;
                break;
            }
            case 3020:
            case 3028: {
                cookingType = 20;
                break;
            }
            case 3021:
            case 3029: {
                cookingType = 21;
                pc.addMr(10);
                pc.sendPackets(new S_SPMR(pc));
                break;
            }
            case 3022:
            case 3030: {
                cookingType = 22;
                pc.addSp(1);
                pc.sendPackets(new S_SPMR(pc));
                break;
            }
            case 3023:
            case 3031: {
                cookingType = 32;
                break;
            }
            case 3032:
            case 3040: {
                cookingType = 37;
                break;
            }
            case 3033:
            case 3041: {
                cookingType = 38;
                pc.addMaxHp(50);
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                if (pc.isInParty()) {
                    pc.getParty().updateMiniHP(pc);
                }
                pc.addMaxMp(50);
                pc.sendPackets(new S_MPUpdate(pc));
                break;
            }
            case 3034:
            case 3042: {
                cookingType = 39;
                break;
            }
            case 3035:
            case 3043: {
                cookingType = 40;
                pc.addAc(-3);
                pc.sendPackets(new S_OwnCharStatus(pc));
                break;
            }
            case 3036:
            case 3044: {
                cookingType = 41;
                pc.addMr(15);
                pc.sendPackets(new S_SPMR(pc));
                pc.addWind(10);
                pc.addWater(10);
                pc.addFire(10);
                pc.addEarth(10);
                pc.sendPackets(new S_OwnCharAttrDef(pc));
                break;
            }
            case 3037:
            case 3045: {
                cookingType = 42;
                pc.addSp(2);
                pc.sendPackets(new S_SPMR(pc));
                break;
            }
            case 3038:
            case 3046: {
                cookingType = 43;
                pc.addMaxHp(30);
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                if (pc.isInParty()) {
                    pc.getParty().updateMiniHP(pc);
                    break;
                }
                else {
                    break;
                }
            }
            case 3039:
            case 3047: {
                cookingType = 44;
                break;
            }
            case 3048: {
                cookingType = 157;
                pc.addMr(10);
                pc.addEarth(10);
                pc.addWater(10);
                pc.addFire(10);
                pc.addWind(10);
                pc.addHpr(2);
                pc.addMpr(2);
                pc.sendPackets(new S_SPMR(pc));
                pc.sendPackets(new S_OwnCharAttrDef(pc));
                break;
            }
            case 3049: {
                cookingType = 158;
                pc.addMr(10);
                pc.addEarth(10);
                pc.addWater(10);
                pc.addFire(10);
                pc.addWind(10);
                pc.addHpr(2);
                pc.addMpr(2);
                pc.sendPackets(new S_SPMR(pc));
                pc.sendPackets(new S_OwnCharAttrDef(pc));
                break;
            }
            case 3050: {
                cookingType = 159;
                pc.addSp(2);
                pc.addMr(10);
                pc.addEarth(10);
                pc.addWater(10);
                pc.addFire(10);
                pc.addWind(10);
                pc.addHpr(2);
                pc.addMpr(3);
                pc.sendPackets(new S_SPMR(pc));
                pc.sendPackets(new S_OwnCharAttrDef(pc));
                break;
            }
            case 3051: {
                cookingType = 160;
                break;
            }
        }
        try {
            pc.sendPackets(new S_PacketBoxCooking(pc, cookingType, time));
            pc.setSkillEffect(cookingId, time * 1000);
            if ((cookingId >= 3000 && cookingId <= 3006) || (cookingId >= 3008 && cookingId <= 3014) || (cookingId >= 3016 && cookingId <= 3022) || (cookingId >= 3024 && cookingId <= 3030) || (cookingId >= 3032 && cookingId <= 3038) || (cookingId >= 3040 && cookingId <= 3046) || (cookingId >= 3048 && cookingId <= 3050)) {
                pc.setCookingId(cookingId);
            }
            else if (cookingId == 3007 || cookingId == 3015 || cookingId == 3023 || cookingId == 3031 || cookingId == 3039 || cookingId == 3047 || cookingId == 3051) {
                pc.setDessertId(cookingId);
            }
        }
        catch (Exception e) {
            L1Cooking._log.error(e.getLocalizedMessage(), e);
        }
    }
}

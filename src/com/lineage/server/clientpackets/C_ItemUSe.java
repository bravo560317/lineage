package com.lineage.server.clientpackets;

import com.lineage.server.model.L1PcInventory;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.templates.L1Box;
import java.util.ArrayList;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.william.ItemIntegration;
import com.lineage.server.model.L1ItemDelay;
import java.sql.Timestamp;
import com.lineage.server.datatables.ItemBoxTable;
import com.lineage.server.serverpackets.S_AddItem;
import com.lineage.server.serverpackets.S_DeleteInventoryItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ScrollShopSellList;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.data.ItemClass;
import com.lineage.server.datatables.DollPowerTable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;

import com.lineage.server.serverpackets.S_PacketBoxItemLv;
import com.lineage.server.templates.L1EtcItem;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.utils.CheckUtil;
import com.add.system.L1ItemNpc;
import com.lineage.server.datatables.Char_use_Item;
import com.lineage.server.datatables.Acc_use_Item;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class C_ItemUSe extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ItemUSe.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isFreezeAtion()) {
                        if (pc.isTeleport()) {
                            pc.setTeleport(false);
                            pc.sendPackets(new S_Paralysis(7, false));
                        }
                        else if (pc.get_isTeleportToOk()) {
                            pc.setTeleport(false);
                            pc.sendPackets(new S_Paralysis(7, false));
                        }
                        else {
                            int itemObjid = this.readD();
                            L1ItemInstance useItem = pc.getInventory().getItem(itemObjid);
                            if (useItem == null) {
                                return;
                            }
                            else {
                                int[] bow_GFX_Arrow = ConfigOther.WAR_DISABLE_ITEM;
                                boolean castle_area = L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId());
                                int[] array;
                                int length = (array = bow_GFX_Arrow).length;
                                int i = 0;
                                while (i < length) {
                                    int gfx = array[i];
                                    if (useItem.getItemId() == gfx && castle_area && pc.castleWarResult()) {
                                        pc.sendPackets(new S_ServerMessage("攻城中禁止使用此道具"));
                                        return;
                                    }
                                    else {
                                        ++i;
                                    }
                                }
                                int[] MAP_IDSKILL = ConfigOther.MAP_IDITEM;
                                int[] MAP_SKILL = ConfigOther.MAP_ITEM;
                                int[] array2;
                                for (int length2 = (array2 = MAP_IDSKILL).length, j = 0; j < length2; ++j) {
                                    int mapid = array2[j];
                                    int[] array3;
                                    int length3 = (array3 = MAP_SKILL).length;
                                    int k = 0;
                                    while (k < length3) {
                                        int mapskill = array3[k];
                                        if (pc.getMapId() == mapid && useItem.getItemId() == mapskill) {
                                            pc.sendPackets(new S_ServerMessage("此地圖無法使用此道具"));
                                            pc.sendPackets(new S_Paralysis(7, false));
                                            return;
                                        }
                                        else {
                                            ++k;
                                        }
                                    }
                                }
                                if (!this.useItem(pc, useItem)) {
                                    return;
                                }
                                else {
                                    if (useItem.getItem().getacccount() == 1) {
                                        if (!Acc_use_Item.get().checkAreaArmorOff(pc, useItem.getItemId())) {
                                            pc.sendPackets(new S_ServerMessage("該帳號已使用過:" + useItem.getAllName(), 17));
                                            return;
                                        }
                                        else {
                                            Acc_use_Item.get().Add(pc, useItem.getItemId());
                                            Acc_use_Item.get().load();
                                        }
                                    }
                                    if (useItem.getItem().getcharcount() == 1) {
                                        if (!Char_use_Item.get().checkAreaArmorOff(pc, useItem.getItemId())) {
                                            pc.sendPackets(new S_ServerMessage("該腳色已使用過:" + useItem.getAllName(), 17));
                                            return;
                                        }
                                        else {
                                            Char_use_Item.get().Add(pc, useItem.getItemId());
                                            Char_use_Item.get().load();
                                        }
                                    }
                                    useItem.set_char_objid(pc.getId());
                                    boolean isStop = false;
                                    boolean isStop2 = false;
                                    if (pc.hasSkillEffect(78)) {
                                        pc.killSkillEffectTimer(78);
                                        pc.startHpRegeneration();
                                        pc.startMpRegeneration();
                                    }
                                    L1ItemNpc.forRequestItemUSe(client, useItem);
                                    if (!pc.getMap().isUsableItem()) {
                                        pc.sendPackets(new S_ServerMessage(563));
                                        isStop = true;
                                    }
                                    if (pc.isParalyzedX() && !isStop) {
                                        isStop = true;
                                    }
                                    if (!isStop) {
                                        switch (pc.getMapId()) {
                                            case 22: {
                                                switch (useItem.getItemId()) {
                                                    case 30:
                                                    case 40017: {
                                                        break;
                                                    }
                                                    default: {
                                                        pc.sendPackets(new S_ServerMessage(563));
                                                        isStop = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    /*switch (useItem.getItemId()) {
                                    case 132468: {}
                                    }*/
                                    if (!CheckUtil.getUseItemAll(pc) && !isStop) {
                                        isStop = true;
                                    }
                                    if (pc.isPrivateShop() && !isStop) {
                                        isStop = true;
                                    }
                                    if (pc.hasSkillEffect(8872)) {
                                        pc.sendPackets(new S_SystemMessage("\\fY白息之光狀態下無法使用"));
                                        isStop = true;
                                    }
                                    if (pc.hasSkillEffect(9990) && pc.getAccessLevel() == 0) {
                                        isStop2 = true;
                                    }
                                    if (isStop) {
                                        pc.sendPackets(new S_Paralysis(7, false));
                                        return;
                                    }
                                    else if (isStop2) {
                                        pc.sendPackets(new S_Paralysis(7, false));
                                        pc.sendPackets(new S_SystemMessage("目前受到監禁狀態中無法使用道具。"));
                                        return;
                                    }
                                    else if (pc.getCurrentHp() > 0) {
                                        int delay_id = 0;
                                        if (useItem.getItem().getType2() == 0) {
                                            delay_id = ((L1EtcItem)useItem.getItem()).get_delayid();
                                            if (delay_id != 0 && pc.hasItemDelay(delay_id)) {
                                                pc.sendPackets(new S_Paralysis(7, false));
                                                return;
                                            }
                                        }
                                        if (useItem.getCount() <= 0L) {
                                            pc.sendPackets(new S_ServerMessage(329, useItem.getLogName()));
                                            return;
                                        }
                                        else {
                                            int min = useItem.getItem().getMinLevel();
                                            int max = useItem.getItem().getMaxLevel();
                                            int minlogpcpower = useItem.getItem().getminlogpcpower();
                                            int maxlogpcpower = useItem.getItem().getmaxlogpcpower();
                                            if (min != 0 && min > pc.getLevel()) {
                                                if (min < 50) {
                                                    S_PacketBoxItemLv toUser = new S_PacketBoxItemLv(min, 0);
                                                    pc.sendPackets(toUser);
                                                }
                                                else {
                                                    S_ServerMessage toUser2 = new S_ServerMessage(318, String.valueOf(min));
                                                    pc.sendPackets(toUser2);
                                                }
                                                return;
                                            }
                                            else if (max != 0 && max < pc.getLevel() && (useItem.getItem().getType2() == 0 || !useItem.isEquipped())) {
                                                S_PacketBoxItemLv toUser = new S_PacketBoxItemLv(0, max);
                                                pc.sendPackets(toUser);
                                                return;
                                            }
                                            else if (useItem.getItem().getType2() == 0 && minlogpcpower != -1 && pc.getMeteLevel() < minlogpcpower) {
                                                pc.sendPackets(new S_SystemMessage("您的轉生次數不夠~暫時無法使用此物品"));
                                                return;
                                            }
                                            else if (useItem.getItem().getType2() == 0 && maxlogpcpower != -1 && pc.getMeteLevel() > maxlogpcpower) {
                                                pc.sendPackets(new S_SystemMessage("您的轉生次太高~無法使用此物品唷"));
                                                return;
                                            }
                                            else {
                                                boolean isDelayEffect = false;
                                                if (useItem.getItem().getType2() == 0) {
                                                    int delayEffect = ((L1EtcItem)useItem.getItem()).get_delayEffect();
                                                    if (delayEffect > 0) {
                                                        isDelayEffect = true;
                                                        Timestamp lastUsed = useItem.getLastUsed();
                                                        if (lastUsed != null) {
                                                            Calendar cal = Calendar.getInstance();
                                                            long useTime = (cal.getTimeInMillis() - lastUsed.getTime()) / 1000L;
                                                            if (useTime <= (long)delayEffect) {
                                                                useTime = (long)delayEffect - useTime;
                                                                String useTimeS = String.valueOf(useTime / 60L);
                                                                if (useTime / 60L > 0L) {
                                                                    pc.sendPackets(new S_ServerMessage(useItem.getLogName() + " " + useTimeS + "分鐘之內無法使用。"));
                                                                }
                                                                else {
                                                                    pc.sendPackets(new S_ServerMessage(useItem.getLogName() + " " + useTime + "秒之內無法使用。"));
                                                                }
                                                                return;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (!CheckUtil.getUseItemAll(pc) && !isStop) {
                                                    isStop = true;
                                                }
                                                if (pc.isPrivateShop() && !isStop) {
                                                    isStop = true;
                                                }
                                                int use_type = useItem.getItem().getUseType();
                                                boolean isClass = false;
                                                String className = useItem.getItem().getclassname();
                                                if (!className.equals("0")) {
                                                    isClass = true;
                                                }
                                                switch (use_type) {
                                                    case -12:
                                                    case -5:
                                                    case -1: {
                                                        pc.sendPackets(new S_ServerMessage(74, useItem.getLogName()));
                                                        break;
                                                    }
                                                    case -11: {
                                                        L1Doll doll = DollPowerTable.get().get_type(useItem.getItemId());
                                                        int maxusetime = useItem.getItem().getMaxUseTime();
                                                        if (doll != null && maxusetime > 0 && useItem.getRemainingTime() <= 0) {
                                                            pc.sendPackets(new S_ServerMessage("\\fY必須先使用能量石進行充電。"));
                                                            return;
                                                        }
                                                        else if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case -10: {
                                                        if (!CheckUtil.getUseItem(pc)) {
                                                            return;
                                                        }
                                                        else if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case -9: {
                                                        if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case -8: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readC(), this.readC() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case -7: {
                                                        if (!CheckUtil.getUseItem(pc)) {
                                                            return;
                                                        }
                                                        else if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case -6: {
                                                        if (!CheckUtil.getUseItem(pc)) {
                                                            return;
                                                        }
                                                        else if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case -4: {
                                                        com.lineage.server.templates.L1Pet l1pet = com.lineage.server.datatables.lock.PetReading.get().getTemplate(useItem.getId());
                                                        if (l1pet != null) {
                                                            boolean isRecall = false;
                                                            // 檢查寵物是否已在外面 (是收回還是放出)
                                                            for (Object p : pc.getPetList().values().toArray()) {
                                                                if (p instanceof com.lineage.server.model.Instance.L1PetInstance) {
                                                                    com.lineage.server.model.Instance.L1PetInstance petInst = (com.lineage.server.model.Instance.L1PetInstance) p;
                                                                    if (petInst.getItemObjId() == useItem.getId()) {
                                                                        isRecall = true;
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                            // 放出寵物時，執行強制魅力防線檢查
                                                            if (!isRecall) {
                                                                int npcId = l1pet.get_npcid();
                                                                int requiredCha = 6;
                                                                if (npcId >= 96501 && npcId <= 96521) {
                                                                    requiredCha = 10;
                                                                } else if (npcId >= 96601 && npcId <= 96626) {
                                                                    requiredCha = 12;
                                                                } else if (npcId == 45313 || npcId == 45710 || npcId == 45711 || npcId == 45712) {
                                                                    requiredCha = 12;
                                                                }
                                                                int charisma = pc.getCha();
                                                                if (pc.isElf()) {
                                                                    charisma += 12;
                                                                }
                                                                else if (pc.isCrown() || pc.isWizard() || pc.isDarkelf()
                                                                        || pc.isKnight() || pc.isDragonKnight() || pc.isIllusionist()) {
                                                                    charisma += 6;
                                                                }
                                                                int currentPetCost = 0;
                                                                for (Object p : pc.getPetList().values().toArray()) {
                                                                    currentPetCost += ((com.lineage.server.model.Instance.L1NpcInstance) p).getPetcost();
                                                                }
                                                                if (charisma - currentPetCost < requiredCha) {
                                                                    pc.sendPackets(new S_ServerMessage(489)); // 你無法一次帶領多隻寵物
                                                                    pc.sendPackets(new S_Paralysis(7, false));
                                                                    return;
                                                                }
                                                            }
                                                        }
                                                        if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case -3: {
                                                        pc.getInventory().setSting(useItem.getItemId());
                                                        pc.sendPackets(new S_ServerMessage(452, useItem.getLogName()));
                                                        break;
                                                    }
                                                    case -2: {
                                                        pc.getInventory().setArrow(useItem.getItemId());
                                                        pc.sendPackets(new S_ServerMessage(452, useItem.getLogName()));
                                                        break;
                                                    }
                                                    case 0: {
                                                        if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 1: {
                                                        if (this.useItem(pc, useItem)) {
                                                            this.useWeapon(pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 2:
                                                    case 18:
                                                    case 19:
                                                    case 20:
                                                    case 21:
                                                    case 22:
                                                    case 23:
                                                    case 24:
                                                    case 25:
                                                    case 37:
                                                    case 40:
                                                    case 43:
                                                    case 44:
                                                    case 45:
                                                    case 47:
                                                    case 48:
                                                    case 49:
                                                    case 51: {
                                                        if (useItem.getItemId() >= 21157 && useItem.getItemId() <= 21178 && useItem.get_time() == null) {
                                                            pc.sendPackets(new S_ServerMessage("\\fY必須先使用魔法氣息解除封印。"));
                                                            return;
                                                        }
                                                        else if (useItem.getItemId() >= 301060 && useItem.getItemId() <= 301099 && useItem.getRemainingTime() <= 0) {
                                                            pc.sendPackets(new S_ServerMessage("\\fY必須先使用龍的血液解除封印。"));
                                                            return;
                                                        }
                                                        else if (this.useItem(pc, useItem)) {
                                                            this.useArmor(pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 3: {
                                                        if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 4: {
                                                        break;
                                                    }
                                                    case 5: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readD(), this.readH(), this.readH() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 6: {
                                                        if (!CheckUtil.getUseItem(pc, useItem.getItem())) {
                                                            return;
                                                        }
                                                        else if (isClass) {
                                                            try {
                                                                int[] newData = { 0, this.readH() };
                                                                newData[0] = this.readD();
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                pc.sendPackets(new S_Paralysis(7, false));
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 7: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readD() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 8: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readD() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 9: {
                                                        if (!CheckUtil.getUseItem(pc)) {
                                                            return;
                                                        }
                                                        else if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 10: {
                                                        if (useItem.getRemainingTime() <= 0 && useItem.getItemId() != 40004) {
                                                            return;
                                                        }
                                                        else if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 29: {
                                                        if (!L1BuffUtil.getUseItemTeleport(pc)) {
                                                            pc.sendPackets(new S_Paralysis(7, false));
                                                            return;
                                                        }
                                                        else if (isClass) {
                                                            try {
                                                                int[] newData = { 0, this.readH() };
                                                                newData[0] = this.readD();
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                pc.sendPackets(new S_Paralysis(7, false));
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 30: {
                                                        if (isClass) {
                                                            try {
                                                                int obj = this.readD();
                                                                int[] newData2 = { obj };
                                                                ItemClass.get().item(newData2, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 12:
                                                    case 31:
                                                    case 33:
                                                    case 35: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readH() };
                                                                pc.setText(this.readS());
                                                                pc.setTextByte(this.readByte());
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 13:
                                                    case 32:
                                                    case 34:
                                                    case 36: {
                                                        if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 14: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readD() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 15: {
                                                        if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 16: {
                                                        if (isClass) {
                                                            String cmd = this.readS();
                                                            pc.setText(cmd);
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 17: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readD(), this.readH(), this.readH() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 26:
                                                    case 27:
                                                    case 46: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readD() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 28: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readC() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 38: {
                                                        if (!CheckUtil.getUseItem(pc)) {
                                                            return;
                                                        }
                                                        else if (isClass) {
                                                            ItemClass.get().item((int[])null, pc, useItem);
                                                            break;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 39: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readD(), this.readH(), this.readH() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 42: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readH(), this.readH(), 0 };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                    case 55: {
                                                        if (isClass) {
                                                            try {
                                                                int[] newData = { this.readD() };
                                                                ItemClass.get().item(newData, pc, useItem);
                                                                break;
                                                            }
                                                            catch (Exception e) {
                                                                return;
                                                            }
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (useItem.getItem().getType2() == 0 && use_type == 0) {
                                                    int itemId = useItem.getItem().getItemId();
                                                    switch (itemId) {
                                                        case 40630: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "diegodiary"));
                                                            break;
                                                        }
                                                        case 40663: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "sonsletter"));
                                                            break;
                                                        }
                                                        case 40701: {
                                                            if (pc.getQuest().get_step(23) == 1) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "firsttmap"));
                                                                break;
                                                            }
                                                            else if (pc.getQuest().get_step(23) == 2) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "secondtmapa"));
                                                                break;
                                                            }
                                                            else if (pc.getQuest().get_step(23) == 3) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "secondtmapb"));
                                                                break;
                                                            }
                                                            else if (pc.getQuest().get_step(23) == 4) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "secondtmapc"));
                                                                break;
                                                            }
                                                            else if (pc.getQuest().get_step(23) == 5) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmapd"));
                                                                break;
                                                            }
                                                            else if (pc.getQuest().get_step(23) == 6) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmape"));
                                                                break;
                                                            }
                                                            else if (pc.getQuest().get_step(23) == 7) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmapf"));
                                                                break;
                                                            }
                                                            else if (pc.getQuest().get_step(23) == 8) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmapg"));
                                                                break;
                                                            }
                                                            else if (pc.getQuest().get_step(23) == 9) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmaph"));
                                                                break;
                                                            }
                                                            else if (pc.getQuest().get_step(23) == 10) {
                                                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmapi"));
                                                                break;
                                                            }
                                                            else {
                                                                break;
                                                            }
                                                        }
                                                        case 41007: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "erisscroll"));
                                                            break;
                                                        }
                                                        case 41009: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "erisscroll2"));
                                                            break;
                                                        }
                                                        case 41060: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "nonames"));
                                                            break;
                                                        }
                                                        case 41061: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "kames"));
                                                            break;
                                                        }
                                                        case 41062: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "bakumos"));
                                                            break;
                                                        }
                                                        case 41063: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "bukas"));
                                                            break;
                                                        }
                                                        case 41064: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "huwoomos"));
                                                            break;
                                                        }
                                                        case 41065: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "noas"));
                                                            break;
                                                        }
                                                        case 41317: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "rarson"));
                                                            break;
                                                        }
                                                        case 41318: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "kuen"));
                                                            break;
                                                        }
                                                        case 41329: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "anirequest"));
                                                            break;
                                                        }
                                                        case 41340: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tion"));
                                                            break;
                                                        }
                                                        case 41356: {
                                                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "rparum3"));
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (useItem.getItem().getType2() == 0 && useItem.getItem().getType() == 16) {
                                                    if (pc.getInventory().getSize() >= 160) {
                                                        pc.sendPackets(new S_ServerMessage("角色身上攜帶超過160個道具時無法開啟寶箱。"));
                                                        return;
                                                    }
                                                    else if (pc.getInventory().getWeight240() >= 180) {
                                                        pc.sendPackets(new S_ServerMessage(82));
                                                        return;
                                                    }
                                                    else if (!pc.hasSkillEffect(90991)) {
                                                        ArrayList<L1Box> list = null;
                                                        try {
                                                            list = ItemBoxTable.get().get(pc, useItem);
                                                            pc.setSkillEffect(90991, 1000);
                                                            if (list == null) {
                                                                ItemBoxTable.get().get_all(pc, useItem);
                                                                pc.setSkillEffect(90991, 1000);
                                                            }
                                                        }
                                                        catch (Exception ex) {}
                                                        finally {
                                                            list = null;
                                                        }
                                                    }
                                                }
                                                if (isDelayEffect && !isClass) {
                                                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                                                    useItem.setLastUsed(ts);
                                                    pc.getInventory().updateItem(useItem, 32);
                                                    pc.getInventory().saveItem(useItem, 32);
                                                }
                                                try {
                                                    L1ItemDelay.onItemUse(client, useItem);
                                                }
                                                catch (Exception e3) {
                                                    C_ItemUSe._log.error("分類道具使用延遲異常:" + useItem.getItemId(), e3);
                                                }
                                                int itemId = useItem.getItem().getItemId();
                                                if (!isClass) {
                                                    int l = 0;
                                                    int blanksc_skillid = 0;
                                                    int spellsc_objid = 0;
                                                    int spellsc_x = 0;
                                                    int spellsc_y = 0;
                                                    int resid = 0;
                                                    int letterCode = 0;
                                                    String letterReceiver = "";
                                                    byte[] letterText = null;
                                                    int cookStatus = 0;
                                                    int cookNo = 0;
                                                    int fishX = 0;
                                                    int fishY = 0;
                                                    int mapid2 = 0;
                                                    if (itemId == 40126 || itemId == 40098 || itemId == 41029 || itemId == 40317 || itemId == 41036 || itemId == 41245 || (itemId >= 41048 && itemId <= 41057) || (itemId >= 40925 && itemId <= 40929) || (itemId >= 40931 && itemId <= 40958) || itemId == 40964 || itemId == 41426 || itemId == 41427 || itemId == 40075 || itemId == 70370 || useItem.getItem().getType() == 19 || useItem.getItem().getType() == 20 || useItem.getItem().getType() == 21 || useItem.getItem().getType() == 24) {
                                                        l = this.readD();
                                                    }
                                                    else if (itemId == 40090 || itemId == 40091 || itemId == 40092 || itemId == 40093 || itemId == 40094) {
                                                        blanksc_skillid = this.readC();
                                                    }
                                                    else if (use_type == 30 || itemId == 40870 || itemId == 40879) {
                                                        spellsc_objid = this.readD();
                                                    }
                                                    else if (use_type == 5 || use_type == 17) {
                                                        spellsc_objid = this.readD();
                                                        spellsc_x = this.readH();
                                                        spellsc_y = this.readH();
                                                    }
                                                    else if (itemId == 40089 || itemId == 140089) {
                                                        resid = this.readD();
                                                    }
                                                    else if (itemId == 40310 || itemId == 40311 || itemId == 40730 || itemId == 40731 || itemId == 40732) {
                                                        letterCode = this.readH();
                                                        letterReceiver = this.readS();
                                                        letterText = this.readByte();
                                                    }
                                                    else if (itemId >= 41255 && itemId <= 41259) {
                                                        cookStatus = this.readC();
                                                        cookNo = this.readC();
                                                    }
                                                    else if (itemId == 41293 || itemId == 41294 || itemId == 70533) {
                                                        fishX = this.readH();
                                                        fishY = this.readH();
                                                    }
                                                    else if (use_type == 9) {
                                                        mapid2 = this.readH();
                                                    }
                                                    else {
                                                        l = this.readC();
                                                    }
                                                    L1ItemInstance tgItem = pc.getInventory().getItem(l);
                                                    int itemType = useItem.getItem().getType();
                                                    switch (itemType) {
                                                        case 19: {
                                                            ItemIntegration.forItemIntegration(pc, useItem, tgItem);
                                                            break;
                                                        }
                                                    }
                                                    return;
                                                }
                                                else {
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return;
        }
        catch (Exception ex2) {}
        finally {
            this.over();
        }
    }
    
    private boolean useItem(L1PcInstance pc, L1ItemInstance useItem) {
        boolean isEquipped = false;
        if (pc.isCrown()) {
            if (useItem.getItem().isUseRoyal()) {
                isEquipped = true;
            }
        }
        else if (pc.isKnight()) {
            if (useItem.getItem().isUseKnight()) {
                isEquipped = true;
            }
        }
        else if (pc.isElf()) {
            if (useItem.getItem().isUseElf()) {
                isEquipped = true;
            }
        }
        else if (pc.isWizard()) {
            if (useItem.getItem().isUseMage()) {
                isEquipped = true;
            }
        }
        else if (pc.isDarkelf()) {
            if (useItem.getItem().isUseDarkelf()) {
                isEquipped = true;
            }
        }
        else if (pc.isDragonKnight()) {
            if (useItem.getItem().isUseDragonknight()) {
                isEquipped = true;
            }
        }
        else if (pc.isIllusionist() && useItem.getItem().isUseIllusionist()) {
            isEquipped = true;
        }
        if (!isEquipped) {
            pc.sendPackets(new S_ServerMessage(264));
        }
        return isEquipped;
    }
    
    private void useArmor(L1PcInstance pc, L1ItemInstance armor) {
        int itemid = armor.getItem().getItemId();
        int type = armor.getItem().getType();
        L1PcInventory pcInventory = pc.getInventory();
        if (pc.getLevel() < 15 && armor.getItem().get_addcon() < 0) {
            pc.sendPackets(new S_SystemMessage("未滿 15 級的人物將無法裝備會減少「體質」點數的道具。"));
            return;
        }
        else {
            boolean equipeSpace;
            if (type == 9) {
                equipeSpace = (pcInventory.getTypeEquipped(2, 9) <= 3);
            }
            else if (type == 12) {
                equipeSpace = (pcInventory.getTypeEquipped(2, 12) <= 1);
            }
            else {
                equipeSpace = (pcInventory.getTypeEquipped(2, type) <= 0);
            }
            if (equipeSpace && !armor.isEquipped()) {
                if (type == 9 && pcInventory.getTypeEquipped(2, 9) == 2 && pc.getQuest().get_step(58003) != 1) {
                    pc.sendPackets(new S_ServerMessage(3253));
                    return;
                }
                else if (type == 9 && pcInventory.getTypeEquipped(2, 9) == 3 && pc.getQuest().get_step(58002) != 1) {
                    pc.sendPackets(new S_ServerMessage(3253));
                    return;
                }
                else if (pcInventory.getTypeAndItemIdEquipped(2, 9, itemid) == 2) {
                    pc.sendPackets(new S_ServerMessage(3278));
                    return;
                }
                else if (type == 12 && pcInventory.getTypeEquipped(2, 12) == 1 && pc.getQuest().get_step(58001) != 1) {
                    pc.sendPackets(new S_ServerMessage(3253));
                    return;
                }
                else if (pcInventory.getTypeAndItemIdEquipped(2, 12, itemid) == 1) {
                    pc.sendPackets(new S_ServerMessage(3278));
                    return;
                }
                else {
                    int polyid = pc.getTempCharGfx();
                    if (!L1PolyMorph.isEquipableArmor(polyid, type)) {
                        return;
                    }
                    else if ((type == 7 && pcInventory.getTypeEquipped(2, 13) >= 1) || (type == 13 && pcInventory.getTypeEquipped(2, 7) >= 1)) {
                        pc.sendPackets(new S_ServerMessage(124));
                        return;
                    }
                    else if (type == 7 && pc.getWeapon() != null && pc.getWeapon().getItem().isTwohandedWeapon()) {
                        pc.sendPackets(new S_ServerMessage(129));
                        return;
                    }
                    else if (type == 3 && pcInventory.getTypeEquipped(2, 4) >= 1) {
                        pc.sendPackets(new S_ServerMessage(126, "$224", "$225"));
                        return;
                    }
                    else if (type == 3 && pcInventory.getTypeEquipped(2, 2) >= 1) {
                        pc.sendPackets(new S_ServerMessage(126, "$224", "$226"));
                        return;
                    }
                    else if (type == 2 && pcInventory.getTypeEquipped(2, 4) >= 1) {
                        pc.sendPackets(new S_ServerMessage(126, "$226", "$225"));
                        return;
                    }
                    else {
                        pcInventory.setEquipped(armor, true);
                    }
                }
            }
            else if (armor.isEquipped()) {
                if (armor.getItem().getBless() == 2) {
                    pc.sendPackets(new S_ServerMessage(150));
                    return;
                }
                else if (type == 3 && pcInventory.getTypeEquipped(2, 2) >= 1) {
                    pc.sendPackets(new S_ServerMessage(127));
                    return;
                }
                else if ((type == 2 || type == 3) && pcInventory.getTypeEquipped(2, 4) >= 1) {
                    pc.sendPackets(new S_ServerMessage(127));
                    return;
                }
                else {
                    if ((type == 7 || type == 13) && pc.hasSkillEffect(90)) {
                        pc.removeSkillEffect(90);
                    }
                    pcInventory.setEquipped(armor, false);
                }
            }
            else if (armor.getItem().getUseType() == 23) {
                pc.sendPackets(new S_SystemMessage("你已經戴著四個戒指。"));
                return;
            }
            else {
                pc.sendPackets(new S_ServerMessage(124));
                return;
            }
            pc.setCurrentHp(pc.getCurrentHp());
            pc.setCurrentMp(pc.getCurrentMp());
            pc.sendPackets(new S_OwnCharAttrDef(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_SPMR(pc));
            return;
        }
    }
    
    private void useWeapon(L1PcInstance pc, L1ItemInstance weapon) {
        if (pc.getLevel() < 15 && weapon.getItem().get_addcon() < 0) {
            pc.sendPackets(new S_SystemMessage("未滿 15 級的人物將無法裝備會減少「體質」點數的道具。"));
            return;
        }
        else {
            switch (weapon.getItemId()) {
                case 65:
                case 133:
                case 191:
                case 192: {
                    if (pc.getMapId() != 2000 && !pc.getWeapon().equals(weapon)) {
                        pc.sendPackets(new S_ServerMessage(563));
                        return;
                    }
                    else {
                        break;
                    }
                }
                default: {
                    if (pc.hasSkillEffect(4007)) {
                        pc.sendPackets(new S_ServerMessage(563));
                        return;
                    }
                    else {
                        break;
                    }
                }
            }
            L1PcInventory pcInventory = pc.getInventory();
            if (pc.getWeapon() == null || !pc.getWeapon().equals(weapon)) {
                int weapon_type = weapon.getItem().getType();
                int polyid = pc.getTempCharGfx();
                if (!L1PolyMorph.isEquipableWeapon(polyid, weapon_type)) {
                    return;
                }
                else if (weapon.getItem().isTwohandedWeapon() && pcInventory.getTypeEquipped(2, 7) >= 1) {
                    pc.sendPackets(new S_ServerMessage(128));
                    return;
                }
            }
            if (pc.hasSkillEffect(78)) {
                pc.killSkillEffectTimer(78);
                pc.startHpRegeneration();
                pc.startMpRegeneration();
            }
            if (pc.getWeapon() != null) {
                if (pc.getWeapon().getItem().getBless() == 2) {
                    pc.sendPackets(new S_ServerMessage(150));
                    return;
                }
                else if (pc.getWeapon().equals(weapon)) {
                    pcInventory.setEquipped(pc.getWeapon(), false, false, false);
                    return;
                }
                else {
                    pcInventory.setEquipped(pc.getWeapon(), false, false, true);
                }
            }
            if (weapon.getItem().getBless() == 2) {
                pc.sendPackets(new S_ServerMessage(149, weapon.getLogName()));
            }
            pcInventory.setEquipped(weapon, true, false, false);
            return;
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

package com.lineage.data.item_etcitem.add;

import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Ajplayer extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Ajplayer();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int spellsc_objid = data[0];
        this.sneakpeek(pc, spellsc_objid, item);
    }
    
    private void sneakpeek(L1PcInstance pc, int targetId, L1ItemInstance item) {
        L1Object target = World.get().findObject(targetId);
        if (target != null) {
            String msg0 = "";
            String msg2 = "";
            String msg3 = "";
            String msg4 = "";
            String msg5 = "";
            String msg6 = "";
            String msg7 = "";
            String msg8 = "";
            String msg9 = "";
            String msg10 = "";
            String msg11 = "";
            String msg12 = "";
            String msg13 = "";
            String msg14 = "";
            String msg15 = "";
            String msg16 = "";
            String msg17 = "";
            String msg18 = "";
            String msg19 = "";
            String msg20 = "";
            String msg21 = "";
            String msg22 = "";
            String msg23 = "";
            String msg24 = "";
            String msg25 = "";
            String msg26 = "";
            String msg27 = "";
            String msg28 = "";
            String msg29 = "";
            String msg30 = "";
            String msg31 = "";
            String msg32 = "";
            String msg33 = "";
            String msg34 = "";
            String msg35 = "";
            String msg36 = "";
            String msg37 = "";
            String msg38 = "";
            String msg39 = "";
            String msg40 = "";
            String msg41 = "";
            String msg42 = "";
            String msg43 = "";
            String msg44 = "";
            String msg45 = "";
            String msg46 = "";
            if (target instanceof L1PcInstance) {
                L1PcInstance target_pc = (L1PcInstance)target;
                L1ItemInstance item2 = target_pc.getInventory().getItemEquipped(2, 1);
                L1ItemInstance item3 = target_pc.getInventory().getItemEquipped(2, 2);
                L1ItemInstance item4 = target_pc.getInventory().getItemEquipped(2, 3);
                L1ItemInstance item5 = target_pc.getInventory().getItemEquipped(2, 4);
                L1ItemInstance item6 = target_pc.getInventory().getItemEquipped(2, 5);
                L1ItemInstance item7 = target_pc.getInventory().getItemEquipped(2, 6);
                L1ItemInstance item8 = target_pc.getInventory().getItemEquipped(2, 7);
                L1ItemInstance item9 = target_pc.getInventory().getItemEquipped(2, 8);
                L1ItemInstance item10 = target_pc.getInventory().getItemEquipped(2, 9);
                L1ItemInstance item11 = target_pc.getInventory().getItemEquipped(2, 10);
                L1ItemInstance item12 = target_pc.getInventory().getItemEquipped(2, 12);
                L1ItemInstance item13 = target_pc.getInventory().getItemEquipped(2, 40);
                L1ItemInstance item14 = target_pc.getInventory().getItemEquipped(2, 13);
                L1ItemInstance item15 = target_pc.getInventory().getItemEquipped(2, 14);
                L1ItemInstance item16 = target_pc.getInventory().getItemEquipped(2, 15);
                L1ItemInstance item17 = target_pc.getInventory().getItemEquipped(2, 16);
                L1ItemInstance item18 = target_pc.getInventory().getItemEquipped(2, 17);
                L1ItemInstance item19 = target_pc.getInventory().getItemEquipped(2, 18);
                L1ItemInstance item20 = target_pc.getInventory().getItemEquipped(2, 19);
                L1ItemInstance item21 = target_pc.getInventory().getItemEquipped(2, 20);
                L1ItemInstance item22 = target_pc.getInventory().getItemEquipped(2, 11);
                L1ItemInstance item23 = target_pc.getInventory().getItemEquipped(2, 23);
                if (target_pc.getInventory().getItemEquipped(2, 1) != null) {
                    msg13 = "+" + item2.getEnchantLevel() + " " + item2.getName();
                }
                else {
                    msg13 = "無裝備頭盔";
                }
                if (target_pc.getInventory().getItemEquipped(2, 2) != null) {
                    msg14 = "+" + item3.getEnchantLevel() + " " + item3.getName();
                }
                else {
                    msg14 = "無裝備盔甲";
                }
                if (target_pc.getInventory().getItemEquipped(2, 3) != null) {
                    msg15 = "+" + item4.getEnchantLevel() + " " + item4.getName();
                }
                else {
                    msg15 = "無裝備T恤";
                }
                if (target_pc.getInventory().getItemEquipped(2, 4) != null) {
                    msg16 = "+" + item5.getEnchantLevel() + " " + item5.getName();
                }
                else {
                    msg16 = "無裝備斗篷";
                }
                if (target_pc.getInventory().getItemEquipped(2, 5) != null) {
                    msg17 = "+" + item6.getEnchantLevel() + " " + item6.getName();
                }
                else {
                    msg17 = "無裝備手套";
                }
                if (target_pc.getInventory().getItemEquipped(2, 6) != null) {
                    msg18 = "+" + item7.getEnchantLevel() + " " + item7.getName();
                }
                else {
                    msg18 = "無裝備長靴";
                }
                if (target_pc.getInventory().getItemEquipped(2, 7) != null) {
                    msg19 = "+" + item8.getEnchantLevel() + " " + item8.getName();
                }
                else {
                    msg19 = "無裝備盾牌";
                }
                if (target_pc.getInventory().getItemEquipped(2, 8) != null) {
                    msg20 = "+" + item9.getEnchantLevel() + " " + item9.getName();
                }
                else {
                    msg20 = "無裝備項鍊";
                }
                if (target_pc.getInventory().getItemEquipped(2, 9) != null) {
                    msg21 = "+" + item10.getEnchantLevel() + " " + item10.getName();
                }
                else {
                    msg21 = "無裝備戒指1(9)";
                }
                if (target_pc.getInventory().getItemEquipped(2, 11) != null) {
                    msg22 = "+" + item22.getEnchantLevel() + " " + item22.getName();
                }
                else {
                    msg22 = "無裝備戒指2(11)";
                }
                if (target_pc.getInventory().getItemEquipped(2, 11) != null) {
                    msg23 = "+" + item22.getEnchantLevel() + " " + item22.getName();
                }
                else {
                    msg23 = "無裝備戒指3(11)";
                }
                if (target_pc.getInventory().getItemEquipped(2, 23) != null) {
                    msg24 = "+" + item23.getEnchantLevel() + " " + item23.getName();
                }
                else {
                    msg24 = "無裝備戒指4(23)";
                }
                if (target_pc.getInventory().getItemEquipped(2, 10) != null) {
                    msg25 = "+" + item11.getEnchantLevel() + " " + item11.getName();
                }
                else {
                    msg25 = "無裝備腰帶";
                }
                if (target_pc.getInventory().getItemEquipped(2, 12) != null) {
                    msg26 = "+" + item12.getEnchantLevel() + " " + item12.getName();
                }
                else {
                    msg26 = "無裝備耳環1";
                }
                if (target_pc.getInventory().getItemEquipped(2, 40) != null) {
                    msg27 = "+" + item13.getEnchantLevel() + " " + item13.getName();
                }
                else {
                    msg27 = "無裝備耳環2";
                }
                if (target_pc.getInventory().getItemEquipped(2, 13) != null) {
                    msg28 = "+" + item14.getEnchantLevel() + " " + item14.getName();
                }
                else {
                    msg28 = "無裝備臂甲";
                }
                if (target_pc.getInventory().getItemEquipped(2, 14) != null) {
                    msg29 = "+" + item15.getEnchantLevel() + " " + item15.getName();
                }
                else {
                    msg29 = "無裝備符石 左";
                }
                if (target_pc.getInventory().getItemEquipped(2, 15) != null) {
                    msg30 = "+" + item16.getEnchantLevel() + " " + item16.getName();
                }
                else {
                    msg30 = "無裝備符石 右";
                }
                if (target_pc.getInventory().getItemEquipped(2, 16) != null) {
                    msg31 = "+" + item17.getEnchantLevel() + " " + item17.getName();
                }
                else {
                    msg31 = "無裝備符石 中";
                }
                if (target_pc.getInventory().getItemEquipped(2, 17) != null) {
                    msg32 = "+" + item18.getEnchantLevel() + " " + item18.getName();
                }
                else {
                    msg32 = "無裝備脛甲";
                }
                if (target_pc.getInventory().getItemEquipped(2, 18) != null) {
                    msg33 = "+" + item19.getEnchantLevel() + " " + item19.getName();
                }
                else {
                    msg33 = "無裝備六芒星護身符系列";
                }
                if (target_pc.getInventory().getItemEquipped(2, 19) != null) {
                    msg34 = "+" + item20.getEnchantLevel() + " " + item20.getName();
                }
                else {
                    msg34 = "無裝備蒂蜜特的紋樣系列";
                }
                if (target_pc.getInventory().getItemEquipped(2, 20) != null) {
                    msg35 = "+" + item21.getEnchantLevel() + " " + item21.getName();
                }
                else {
                    msg35 = "無裝備蒂蜜特的符文";
                }
                msg0 = target_pc.getName();
                msg2 = new StringBuilder().append(target_pc.getLevel()).toString();
                msg3 = target_pc.getCurrentHp() + " / " + target_pc.getMaxHp();
                msg4 = target_pc.getCurrentMp() + " / " + target_pc.getMaxMp();
                msg5 = new StringBuilder().append(target_pc.getAc()).toString();
                msg6 = new StringBuilder().append(target_pc.getEr()).toString();
                msg7 = target_pc.getMr() + " %";
                msg8 = target_pc.getFire() + " %";
                msg9 = target_pc.getWater() + " %";
                msg10 = target_pc.getWind() + " %";
                msg11 = target_pc.getEarth() + " %";
                msg36 = new StringBuilder().append((int)target_pc.getStr()).toString();
                msg37 = new StringBuilder().append((int)target_pc.getCon()).toString();
                msg38 = new StringBuilder().append((int)target_pc.getDex()).toString();
                msg39 = new StringBuilder().append((int)target_pc.getWis()).toString();
                msg40 = new StringBuilder().append((int)target_pc.getInt()).toString();
                msg41 = new StringBuilder().append((int)target_pc.getCha()).toString();
                msg42 = "0";
                msg43 = new StringBuilder().append(target_pc.getGfxId()).toString();
                msg44 = new StringBuilder().append(target_pc.getExp()).toString();
                msg45 = new StringBuilder().append(target_pc.getLawful()).toString();
                msg46 = new StringBuilder().append(target_pc.getSp()).toString();
                L1ItemInstance weapon = target_pc.getWeapon();
                if (weapon != null) {
                    msg12 = new StringBuilder().append(weapon.getLogName()).toString();
                }
                else {
                    msg12 = "無裝備武器";
                }
                String[] msg47 = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20, msg21, msg22, msg23, msg24, msg25, msg26, msg27, msg28, msg29, msg30, msg31, msg32, msg33, msg34, msg35, msg36, msg37, msg38, msg39, msg40, msg41, msg42, msg43, msg44, msg45, msg46 };
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ajplayer", msg47));
                pc.getInventory().removeItem(item, 1L);
            }
            else if (pc.isGm()) {
                L1NpcInstance target_npc = null;
                if (target instanceof L1MonsterInstance) {
                    target_npc = (L1MonsterInstance)target;
                }
                else if (target instanceof L1NpcInstance) {
                    target_npc = (L1NpcInstance)target;
                }
                msg0 = target_npc.getName();
                msg2 = new StringBuilder().append(target_npc.getLevel()).toString();
                msg3 = target_npc.getCurrentHp() + " / " + target_npc.getMaxHp();
                msg4 = target_npc.getCurrentMp() + " / " + target_npc.getMaxMp();
                msg5 = new StringBuilder().append(target_npc.getAc()).toString();
                msg6 = "0";
                msg7 = target_npc.getMr() + " %";
                msg8 = target_npc.getFire() + " %";
                msg9 = target_npc.getWater() + " %";
                msg10 = target_npc.getWind() + " %";
                msg11 = target_npc.getEarth() + " %";
                msg12 = "砂鍋大的拳頭";
                msg13 = new StringBuilder().append((int)target_npc.getStr()).toString();
                msg14 = new StringBuilder().append((int)target_npc.getCon()).toString();
                msg15 = new StringBuilder().append((int)target_npc.getDex()).toString();
                msg16 = new StringBuilder().append((int)target_npc.getWis()).toString();
                msg17 = new StringBuilder().append((int)target_npc.getInt()).toString();
                msg18 = new StringBuilder().append((int)target_npc.getCha()).toString();
                msg19 = new StringBuilder().append(target_npc.getNpcId()).toString();
                msg20 = new StringBuilder().append(target_npc.getGfxId()).toString();
                msg21 = new StringBuilder().append(target_npc.getExp()).toString();
                msg22 = new StringBuilder().append(target_npc.getLawful()).toString();
                msg23 = new StringBuilder().append(target_npc.getSp()).toString();
                String[] msg48 = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20, msg21, msg22, msg23 };
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ajplayer", msg48));
                pc.getInventory().removeItem(item, 1L);
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
        }
    }
}

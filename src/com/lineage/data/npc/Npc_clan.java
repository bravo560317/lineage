package com.lineage.data.npc;

import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.world.World;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import java.util.Iterator;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Queue;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.datatables.ItemTable;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.templates.L1ClanSkills;
import java.sql.Timestamp;
import com.lineage.server.datatables.RewardClanSkillsTable;
import com.lineage.data.event.ClanSkillDBSet;
import java.text.SimpleDateFormat;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.event.ClanSkillSet;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_clan extends NpcExecutor
{
    private static final Log _log;
    private static final String[] _skillName;
    public static final String[] SKILLINFO;
    private static final int[][] _items;
    
    static {
        _log = LogFactory.getLog(Npc_clan.class);
        _skillName = new String[] { "無", "狂暴", "寂靜", "魔擊", "消魔" };
        SKILLINFO = new String[] { "\\fR血盟狂暴的爆發力流遍全身!", "\\fR血盟寂靜的氣息壟罩著你!", "\\fR血盟魔擊的光緩緩的發亮!", "\\fR血盟消魔的光環保護著你!" };
        _items = new int[][] { { 40308, 50000 }, { 40308, 50000 }, { 40308, 50000 } };
    }
    
    private Npc_clan() {
    }
    
    public static NpcExecutor get() {
        return new Npc_clan();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (ClanSkillSet.START) {
                int clanid = pc.getClanid();
                if (pc.isCrown()) {
                    if (clanid == 0) {
                        if (pc.getLevel() < 5) {
                            pc.sendPackets(new S_ServerMessage("等級沒到5等不要跟我說話。"));
                            return;
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c1"));
                        }
                    }
                    else if (!pc.getClan().isClanskill()) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c1b"));
                    }
                    else {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Timestamp time = pc.getClan().get_skilltime();
                        String[] times = { sdf.format(time) };
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c1aL", times));
                    }
                }
                else if (clanid != 0) {
                    if (!pc.getClan().isClanskill()) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c1g"));
                    }
                    else {
                        Timestamp time2 = pc.getClan().get_skilltime();
                        int index = time2.toString().indexOf(".");
                        String[] times = { time2.toString().substring(0, index) };
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c1a", times));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c1c"));
                }
            }
            else if (ClanSkillDBSet.START) {
                int clanid = pc.getClanid();
                if (pc.isCrown()) {
                    if (clanid == 0) {
                        if (pc.getLevel() < 5) {
                            pc.sendPackets(new S_ServerMessage("等級沒到5等不要跟我說話。"));
                            return;
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1"));
                        }
                    }
                    else {
                        int clanSkillId = pc.getClan().getClanSkillId();
                        if (clanSkillId == 0) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1b"));
                        }
                        else {
                            int clanSkillLv = pc.getClan().getClanSkillLv();
                            L1ClanSkills clanSkills = RewardClanSkillsTable.get().getClanSkillsList(clanSkillId, clanSkillLv);
                            String skillName = clanSkills.getClanSkillName();
                            String note = clanSkills.getNote();
                            if (clanSkillLv < 10) {
                                String NextSkillName = RewardClanSkillsTable.get().getSkillsName(clanSkillId, clanSkillLv + 1);
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1a", new String[] { skillName, note, NextSkillName }));
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1a2", new String[] { skillName, note }));
                            }
                        }
                    }
                }
                else if (clanid != 0) {
                    int clanSkillId = pc.getClan().getClanSkillId();
                    if (clanSkillId == 0) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1g"));
                    }
                    else {
                        int clanSkillLv = pc.getClan().getClanSkillLv();
                        L1ClanSkills clanSkills = RewardClanSkillsTable.get().getClanSkillsList(clanSkillId, clanSkillLv);
                        String skillName = clanSkills.getClanSkillName();
                        String note = clanSkills.getNote();
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1a2", new String[] { skillName, note }));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1c"));
                }
            }
            else if (pc.isCrown() && pc.getClanid() == 0) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bpledge2"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bpledge1"));
            }
        }
        catch (Exception e) {
            Npc_clan._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            String htmlid = null;
            boolean html = false;
            boolean updatePc = false;
            int clanid = pc.getClanid();
            if (ClanSkillSet.START) {
                if (clanid == 0) {
                    if (pc.isCrown()) {
                        htmlid = "y_c1d";
                    }
                    else {
                        htmlid = "y_c1e";
                    }
                }
                if (htmlid != null) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), htmlid));
                    return;
                }
                else {
                    if (cmd.equals("1")) {
                        if (pc.getClan().isClanskill()) {
                            html = true;
                            htmlid = "y_c3";
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c1g"));
                        }
                    }
                    else if (cmd.equals("2")) {
                        if (pc.isCrown()) {
                            if (!pc.getClan().isClanskill()) {
                                this.get_clanSkill(pc, npc);
                            }
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c1f"));
                        }
                    }
                    else if (cmd.equals("3")) {
                        if (pc.isCrown()) {
                            if (pc.getId() == pc.getClan().getLeaderId()) {
                                this.delClan(pc);
                                pc.sendPackets(new S_CloseList(pc.getId()));
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c1f"));
                            }
                        }
                    }
                    else if (cmd.equals("a")) {
                        if (pc.getClan().isClanskill()) {
                            pc.get_other().set_clanskill(1);
                            updatePc = true;
                        }
                    }
                    else if (cmd.equals("b")) {
                        if (pc.getClan().isClanskill()) {
                            pc.get_other().set_clanskill(2);
                            updatePc = true;
                        }
                    }
                    else if (cmd.equals("c")) {
                        if (pc.getClan().isClanskill()) {
                            pc.get_other().set_clanskill(4);
                            updatePc = true;
                        }
                    }
                    else if (cmd.equals("d") && pc.getClan().isClanskill()) {
                        pc.get_other().set_clanskill(8);
                        updatePc = true;
                    }
                    if (updatePc) {
                        try {
                            html = true;
                            htmlid = "y_c3a";
                            pc.save();
                        }
                        catch (Exception e) {
                            Npc_clan._log.error(e.getLocalizedMessage(), e);
                        }
                    }
                    if (html) {
                        int clanMan = pc.getClan().getOnlineClanMemberSize();
                        String skillName = "";
                        String start1 = " ";
                        String start2 = " ";
                        String start3 = " ";
                        String start4 = " ";
                        switch (pc.get_other().get_clanskill()) {
                            case 0: {
                                skillName = Npc_clan._skillName[0];
                                break;
                            }
                            case 1: {
                                skillName = Npc_clan._skillName[1];
                                start1 = "啟用";
                                break;
                            }
                            case 2: {
                                skillName = Npc_clan._skillName[2];
                                start2 = "啟用";
                                break;
                            }
                            case 4: {
                                skillName = Npc_clan._skillName[3];
                                start3 = "啟用";
                                break;
                            }
                            case 8: {
                                skillName = Npc_clan._skillName[4];
                                start4 = "啟用";
                                break;
                            }
                        }
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), htmlid, new String[] { skillName, String.valueOf(clanMan), start1, start2, start3, start4, Npc_clan._skillName[1], Npc_clan._skillName[2], Npc_clan._skillName[3], Npc_clan._skillName[4] }));
                    }
                }
            }
            else if (ClanSkillDBSet.START) {
                if (clanid == 0) {
                    if (pc.isCrown()) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1d"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1e"));
                    }
                    return;
                }
                else if (!pc.isCrown()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1f"));
                    return;
                }
                else if (cmd.equals("1")) {
                    int clanSkillId = pc.getClan().getClanSkillId();
                    int clanSkillLv = pc.getClan().getClanSkillLv();
                    if (clanSkillId != 0 && clanSkillLv < 10) {
                        String MaterialName = RewardClanSkillsTable.get().getMaterialName(clanSkillId, clanSkillLv + 1);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c3", new String[] { MaterialName }));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1g"));
                    }
                }
                else if (cmd.equals("2")) {
                    int clanSkillId = pc.getClan().getClanSkillId();
                    if (clanSkillId == 0) {
                        if (pc.getAmount() == 0) {
                            clanSkillId = 1;
                        }
                        else {
                            clanSkillId = pc.getAmount();
                        }
                    }
                    pc.setAmount(0);
                    int clanSkillLv = pc.getClan().getClanSkillLv() + 1;
                    if (clanSkillId <= 10) {
                        Queue<String> itemListX = new ConcurrentLinkedQueue();
                        L1ClanSkills clanSkills = RewardClanSkillsTable.get().getClanSkillsList(clanSkillId, clanSkillLv);
                        if (clanSkills != null) {
                            int[] Material = clanSkills.getMaterial();
                            int[] MaterialCount = clanSkills.getMaterialCount();
                            int[] MaterialLevel = clanSkills.getMaterialLevel();
                            if (Material != null && MaterialCount != null && MaterialLevel != null) {
                                for (int i = 0; i < Material.length; ++i) {
                                    L1ItemInstance item = pc.getInventory().checkItemX_Lv(Material[i], MaterialLevel[i], (long)MaterialCount[i]);
                                    if (item == null) {
                                        long countX = pc.getInventory().countItems(Material[i]);
                                        L1Item itemX2 = ItemTable.get().getTemplate(Material[i]);
                                        if (countX > 0L) {
                                            if (MaterialLevel[i] != 0) {
                                                itemListX.offer("+" + MaterialLevel[i] + " " + itemX2.getNameId() + " (" + ((long)MaterialCount[i] - countX) + ")");
                                            }
                                            else {
                                                itemListX.offer(itemX2.getNameId() + " (" + ((long)MaterialCount[i] - countX) + ")");
                                            }
                                        }
                                        else if (MaterialLevel[i] != 0) {
                                            itemListX.offer("+" + MaterialLevel[i] + " " + itemX2.getNameId() + " (" + MaterialCount[i] + ")");
                                        }
                                        else {
                                            itemListX.offer(itemX2.getNameId() + " (" + MaterialCount[i] + ")");
                                        }
                                    }
                                }
                                if (itemListX.size() > 0) {
                                    for (String tgitem : itemListX) {
                                        pc.sendPackets(new S_ServerMessage(337, tgitem));
                                    }
                                    itemListX.clear();
                                    pc.sendPackets(new S_CloseList(pc.getId()));
                                    return;
                                }
                                else {
                                    for (int i = 0; i < Material.length; ++i) {
                                        L1ItemInstance item = pc.getInventory().checkItemX_Lv(Material[i], MaterialLevel[i], (long)MaterialCount[i]);
                                        if (item != null) {
                                            if (MaterialLevel[i] != 0) {
                                                pc.getInventory().consumeEnchantItem(Material[i], MaterialLevel[i], (long)MaterialCount[i]);
                                            }
                                            else {
                                                pc.getInventory().consumeItem(Material[i], (long)MaterialCount[i]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        pc.getClan().setClanSkillId(clanSkillId);
                        pc.getClan().setClanSkillLv(clanSkillLv);
                        ClanReading.get().updateClanSkill(pc.getClan());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c2a"));
                    }
                }
                else if (cmd.equals("3")) {
                    if (pc.getId() == pc.getClan().getLeaderId()) {
                        this.delClan(pc);
                        pc.sendPackets(new S_CloseList(pc.getId()));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c1f"));
                    }
                }
                else if (cmd.equals("4")) {
                    int clanSkillId = pc.getClan().getClanSkillId();
                    int clanSkillLv = pc.getClan().getClanSkillLv();
                    if (clanSkillId != 0 && clanSkillLv != 0) {
                        String NextSkillName = RewardClanSkillsTable.get().getSkillsName(clanSkillId, clanSkillLv);
                        pc.getClan().setClanSkillId(0);
                        pc.getClan().setClanSkillLv(0);
                        ClanReading.get().updateClanSkill(pc.getClan());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c2a"));
                        pc.sendPackets(new S_ServerMessage(166, "成功遺忘掉血盟技能:" + NextSkillName));
                        pc.sendPackets(new S_CloseList(pc.getId()));
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(166, "您尚未學習血盟技能"));
                    }
                }
                else if (cmd.equals("s")) {
                    int clanskillId = pc.getClan().getClanSkillId();
                    if (clanskillId == 0) {
                        int clanskillLv = pc.getClan().getClanSkillLv();
                        if (clanskillLv == 0) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c2", ClanSkillDBSet._skillNameNote));
                        }
                    }
                }
                else if (cmd.equals("a")) {
                    int clanskillId = pc.getClan().getClanSkillId();
                    if (clanskillId == 0) {
                        int clanskillLv = pc.getClan().getClanSkillLv();
                        if (clanskillLv == 0) {
                            String MaterialName = RewardClanSkillsTable.get().getMaterialName(1, 1);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c3", new String[] { MaterialName }));
                            pc.setAmount(1);
                        }
                    }
                }
                else if (cmd.equals("b")) {
                    int clanskillId = pc.getClan().getClanSkillId();
                    if (clanskillId == 0) {
                        int clanskillLv = pc.getClan().getClanSkillLv();
                        if (clanskillLv == 0) {
                            String MaterialName = RewardClanSkillsTable.get().getMaterialName(2, 1);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c3", new String[] { MaterialName }));
                            pc.setAmount(2);
                        }
                    }
                }
                else if (cmd.equals("c")) {
                    int clanskillId = pc.getClan().getClanSkillId();
                    if (clanskillId == 0) {
                        int clanskillLv = pc.getClan().getClanSkillLv();
                        if (clanskillLv == 0) {
                            String MaterialName = RewardClanSkillsTable.get().getMaterialName(3, 1);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c3", new String[] { MaterialName }));
                            pc.setAmount(3);
                        }
                    }
                }
                else if (cmd.equals("d")) {
                    int clanskillId = pc.getClan().getClanSkillId();
                    if (clanskillId == 0) {
                        int clanskillLv = pc.getClan().getClanSkillLv();
                        if (clanskillLv == 0) {
                            String MaterialName = RewardClanSkillsTable.get().getMaterialName(4, 1);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_c3", new String[] { MaterialName }));
                            pc.setAmount(4);
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            Npc_clan._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    private void get_clanSkill(L1PcInstance pc, L1NpcInstance npc) {
        Queue<String> itemListX = new ConcurrentLinkedQueue();
        if (!pc.isGm()) {
            int[][] items;
            for (int length = (items = Npc_clan._items).length, i = 0; i < length; ++i) {
                int[] itemid = items[i];
                L1ItemInstance item = pc.getInventory().checkItemX(itemid[0], (long)itemid[1]);
                if (item == null) {
                    long countX = pc.getInventory().countItems(itemid[0]);
                    L1Item itemX2 = ItemTable.get().getTemplate(itemid[0]);
                    if (countX > 0L) {
                        itemListX.offer(itemX2.getNameId() + " (" + ((long)itemid[1] - countX) + ")");
                    }
                    else {
                        itemListX.offer(itemX2.getNameId() + " (" + itemid[1] + ")");
                    }
                }
            }
            if (itemListX.size() > 0) {
                for (String tgitem : itemListX) {
                    pc.sendPackets(new S_ServerMessage(337, tgitem));
                }
                itemListX.clear();
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                itemListX.clear();
                int[][] items2;
                for (int length2 = (items2 = Npc_clan._items).length, j = 0; j < length2; ++j) {
                    int[] itemid = items2[j];
                    int ritemid = itemid[0];
                    int rcount = itemid[1];
                    L1ItemInstance item2 = pc.getInventory().checkItemX(ritemid, (long)rcount);
                    if (item2 != null) {
                        long rcountx = pc.getInventory().removeItem(item2, (long)rcount);
                        if (rcountx != (long)rcount) {
                            pc.sendPackets(new S_CloseList(pc.getId()));
                            return;
                        }
                    }
                }
            }
        }
        long time = System.currentTimeMillis();
        long x1 = 2592000L;
        long x2 = x1 * 1000L;
        long upTime = x2 + time;
        Timestamp ts = new Timestamp(upTime);
        pc.getClan().set_clanskill(true);
        pc.getClan().set_skilltime(ts);
        ClanReading.get().updateClan(pc.getClan());
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_c2a"));
    }
    
    private void delClan(L1PcInstance pc) {
        L1Clan clan = pc.getClan();
        if (clan != null) {
            int clan_id = clan.getClanId();
            String player_name = pc.getName();
            String clan_name = clan.getClanName();
            String[] clan_member_name = clan.getAllMembers();
            int castleId = clan.getCastleId();
            int houseId = clan.getHouseId();
            if (castleId != 0) {
                pc.sendPackets(new S_ServerMessage(665));
                return;
            }
            else if (houseId != 0) {
                pc.sendPackets(new S_ServerMessage(665));
                return;
            }
            else {
                for (L1War war : WorldWar.get().getWarList()) {
                    if (war.checkClanInWar(clan_name)) {
                        pc.sendPackets(new S_ServerMessage(302));
                        return;
                    }
                }
                try {
                    for (int i = 0; i < clan_member_name.length; ++i) {
                        L1PcInstance online_pc = World.get().getPlayer(clan_member_name[i]);
                        L1PcInstance tg_pc = null;
                        boolean isReTitle = false;
                        if (online_pc != null) {
                            tg_pc = online_pc;
                            online_pc.sendPacketsAll(new S_CharTitle(online_pc.getId()));
                            isReTitle = true;
                            online_pc.sendPackets(new S_ServerMessage(269, player_name, clan_name));
                        }
                        else {
                            tg_pc = CharacterTable.get().restoreCharacter(clan_member_name[i]);
                            isReTitle = true;
                        }
                        if (tg_pc != null) {
                            tg_pc.setClanid(0);
                            tg_pc.setClanname("");
                            tg_pc.setClanRank(0);
                            if (isReTitle) {
                                tg_pc.setTitle("");
                            }
                            tg_pc.save();
                        }
                    }
                    ClanEmblemReading.get().deleteIcon(clan_id);
                    ClanReading.get().deleteClan(clan_name);
                }
                catch (Exception e) {
                    Npc_clan._log.error(e.getLocalizedMessage(), e);
                }
            }
        }
    }
}

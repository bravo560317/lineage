package com.lineage.william;

import java.util.logging.Level;
import java.util.StringTokenizer;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;


public class L1WilliamLimitedReward {

    private static Logger _log;
    private int _id;
    private boolean _activity;
    private int _check_classId;
    private int _check_level;
    private int _check_item;
    private int _check_itemCount;
    private String _surplus_msg;
    private int _surplus_msg_color;
    private String _itemId;
    private String _count;
    private String _enchantlvl;
    private int _totalCount;
    private int _appearCount;
    private int _quest_id;
    private int _quest_step;
    private String _message;
    private String _message_end;
    private boolean _isCrown;
    private boolean _isKnight;
    private boolean _isElf;
    private boolean _isWizard;
    private boolean _isDarkelf;
    private boolean _isDragonKnight;
    private boolean _isIllusionist;
    private boolean _isWarrior;
    private static final int _int8 = 128;
    private static final int _int7 = 64;
    private static final int _int6 = 32;
    private static final int _int5 = 16;
    private static final int _int4 = 8;
    private static final int _int3 = 4;
    private static final int _int2 = 2;
    private static final int _int1 = 1;
    private int _use_type;

    static {
        L1WilliamLimitedReward._log = Logger.getLogger(L1WilliamLimitedReward.class.getName());
    }

    public L1WilliamLimitedReward(int id, boolean activity, int check_classId, int check_level, int check_item, int check_itemCount, String surplus_msg, int surplus_msg_color, String itemId, String count, String enchantlvl, int totalCount, int appearCount, int quest_id, int quest_step, String message, String message_end) {
        this._use_type = 255;
        this._id = id;
        this._activity = activity;
        this._check_classId = check_classId;
        this._check_level = check_level;
        this._check_item = check_item;
        this._check_itemCount = check_itemCount;
        this._surplus_msg = surplus_msg;
        this._surplus_msg_color = surplus_msg_color;
        this._itemId = itemId;
        this._count = count;
        this._enchantlvl = enchantlvl;
        this._totalCount = totalCount;
        this._appearCount = appearCount;
        this._quest_id = quest_id;
        this._quest_step = quest_step;
        this._message = message;
        this._message_end = message_end;
    }

    public int get_id() {
        return this._id;
    }

    public boolean isActivity() {
        return this._activity;
    }

    public void set_Activity(boolean i) {
        this._activity = i;
    }

    public int get_check_classId() {
        return this._check_classId;
    }

    public int get_check_level() {
        return this._check_level;
    }

    public int get_check_item() {
        return this._check_item;
    }

    public int get_check_itemCount() {
        return this._check_itemCount;
    }

    public String get_surplus_msg() {
        return this._surplus_msg;
    }

    public int get_surplus_msg_color() {
        return this._surplus_msg_color;
    }

    public String get_itemId() {
        return this._itemId;
    }

    public String get_count() {
        return this._count;
    }

    public String get_enchantlvl() {
        return this._enchantlvl;
    }

    public int getTotalCount() {
        return this._totalCount;
    }

    public int get_appearCount() {
        return this._appearCount;
    }

    public void set_appearCount(int i) {
        this._appearCount = i;
    }

    public int get_quest_id() {
        return this._quest_id;
    }

    public int get_quest_step() {
        return this._quest_step;
    }

    public String get_message() {
        return this._message;
    }

    public String get_message_end() {
        return this._message_end;
    }
/**
 * ★ 寫入 character_quests.note
 */
private static void writeQuestNote(L1PcInstance pc, L1WilliamLimitedReward r) {
    if (pc == null) return;
    if (r == null) return;
    if (r.get_quest_id() == 0) return;

    Connection cn = null;
    PreparedStatement ps = null;

    try {
        cn = DatabaseFactory.get().getConnection();

        // 查是否已有紀錄
        ps = cn.prepareStatement("SELECT quest_id FROM character_quests WHERE char_id=? AND quest_id=?");
        ps.setInt(1, pc.getId());
        ps.setInt(2, r.get_quest_id());
        ResultSet rs = ps.executeQuery();
        boolean exist = rs.next();
        rs.close();
        ps.close();

        if (exist) {
            // UPDATE
            ps = cn.prepareStatement(
                    "UPDATE character_quests SET note=? WHERE char_id=? AND quest_id=?");
            ps.setString(1, r.get_surplus_msg());
            ps.setInt(2, pc.getId());
            ps.setInt(3, r.get_quest_id());
            ps.execute();

        } else {
            // INSERT
            ps = cn.prepareStatement(
                    "INSERT INTO character_quests (char_id, quest_id, quest_step, note, mob_count, clean) " +
                    "VALUES (?, ?, ?, ?, 0, 0)");
            ps.setInt(1, pc.getId());
            ps.setInt(2, r.get_quest_id());
            ps.setInt(3, r.get_quest_step());
            ps.setString(4, r.get_surplus_msg());
            ps.execute();
        }

    } catch (Exception e) {
        _log.warning("writeQuestNote error: " + e.getLocalizedMessage());
    } finally {
        SQLUtil.close(ps);
        SQLUtil.close(cn);
    }
}


    //====================================================
    // 任務類型：觸發條件為取得道具
    //====================================================
    public static void check_Task_For_Item(L1PcInstance pc, int itemId, int count) {
        L1WilliamLimitedReward limitedReward = LimitedReward.getInstance().getTemplate1(itemId);
        if (limitedReward == null) {
            return;
        } else if (!limitedReward.isActivity()) {
            return;
        } else if (limitedReward.getTotalCount() == limitedReward.get_appearCount()) {
            return;
        } else if (limitedReward.get_quest_id() != 0 && hasQuestStep(pc, limitedReward.get_quest_id(), limitedReward.get_quest_step())) {
            return;
        } else {

            // 🔸【只新增這段】── 檢查需求道具＆數量（缺一不可）
            if (limitedReward.get_check_item() > 0 && limitedReward.get_check_itemCount() > 0) {
                long haveCount = 0L;
                for (L1ItemInstance invItem : pc.getInventory().getItems()) {
                    if (invItem.getItemId() == limitedReward.get_check_item()) {
                        haveCount += invItem.getCount();
                    }
                }
                if (haveCount < limitedReward.get_check_itemCount()) {
                    // 不足就直接不給獎勵
                    pc.sendPackets(new S_ServerMessage("條件不足，需持有指定道具數量才可領取獎勵。"));
                    return;
                }
            }
            // 🔸【新增結束，下面全部照你原本的】

            limitedReward.set_use_type(limitedReward.get_check_classId());
            if (!limitedReward.is_use(pc)) {
                return;
            } else {
                int codes = 0;
                if (limitedReward.get_itemId() != null && !limitedReward.get_itemId().equals("")) {
                    int[] itemGive = (int[]) getArray(limitedReward.get_itemId(), ",", 1);
                    int[] itemCount = (int[]) getArray(limitedReward.get_count(), ",", 1);
                    int[] itemEnchantlvl = (int[]) getArray(limitedReward.get_enchantlvl(), ",", 1);
                    for (int j = 0; j < itemGive.length; ++j) {
                        CreateNewItem.createNewItem(pc, itemGive[j], itemCount[j], itemEnchantlvl[j]);
                    }
                }
                if (limitedReward.get_quest_id() != 0) {
                    pc.getQuest().set_step(limitedReward.get_quest_id(), limitedReward.get_quest_step());
writeQuestNote(pc, limitedReward);  // ★新增
                }
                limitedReward.set_appearCount(limitedReward.get_appearCount() + 1);
                LimitedReward.limitedRewardToList(limitedReward.get_id(), (long) limitedReward.get_appearCount());
                if (limitedReward.get_message() != null && !limitedReward.get_message().equals("")) {
                    int qq = limitedReward.getTotalCount() - limitedReward.get_appearCount();
                    if (qq <= 0) {
                        World.get().broadcastPacketToAll(new S_ServerMessage(String.format(limitedReward.get_message_end(), new Object[0])));
                    } else {
                        World.get().broadcastPacketToAll(new S_ServerMessage(String.format(limitedReward.get_message(), (Integer) qq)));
                    }
                }
                return;
            }
        }
    }

    //====================================================
    // 任務類型：觸發條件為角色等級
    //====================================================
    public static void check_Task_For_Level(L1PcInstance pc) {
        if (pc == null) {
            return;
        }
        L1WilliamLimitedReward limitedReward = null;
        L1WilliamLimitedReward[] limitedRewardSize = LimitedReward.getInstance().getLimitedRewardList();

        for (int i = 0; i < limitedRewardSize.length; ++i) {
            limitedReward = LimitedReward.getInstance().getTemplate(i);

            if (limitedReward.isActivity()
                && limitedReward.getTotalCount() > limitedReward.get_appearCount()
                && (limitedReward.get_quest_id() == 0
                    || !hasQuestStep(pc, limitedReward.get_quest_id(), limitedReward.get_quest_step()))
                && limitedReward.get_check_level() <= pc.getLevel()) {

                limitedReward.set_use_type(limitedReward.get_check_classId());

                if (limitedReward.is_use(pc)) {

                    // 🔹 檢查是否需要指定道具（只檢查不扣除）
                    if (limitedReward.get_check_item() > 0 && limitedReward.get_check_itemCount() > 0) {
                        long haveCount = 0;
                        for (L1ItemInstance item : pc.getInventory().getItems()) {
                            if (item.getItemId() == limitedReward.get_check_item()) {
                                haveCount += item.getCount();
                            }
                        }
                        if (haveCount < limitedReward.get_check_itemCount()) {
                            continue; // 不足就跳過
                        }
                    }

                    int codes = 0;
                    if (limitedReward.get_surplus_msg_color() != -1) {
                        codes = limitedReward.get_surplus_msg_color();
                    } else {
                        int[] _codes = {14, 7, 24, 13, 47, 4, 3, 53, 10, 1, 2, 11};
                        codes = _codes[RandomArrayList.getInt(_codes.length)];
                    }

                    if (limitedReward.get_itemId() != null && !limitedReward.get_itemId().equals("")) {
                        int[] itemGive = (int[]) getArray(limitedReward.get_itemId(), ",", 1);
                        int[] itemCount = (int[]) getArray(limitedReward.get_count(), ",", 1);
                        int[] itemEnchantlvl = (int[]) getArray(limitedReward.get_enchantlvl(), ",", 1);
                        for (int j = 0; j < itemGive.length; ++j) {
                            CreateNewItem.createNewItem(pc, itemGive[j], itemCount[j], itemEnchantlvl[j]);
                        }
                    }
                    if (limitedReward.get_quest_id() != 0 && pc.getQuest() != null) {
                        pc.getQuest().set_step(limitedReward.get_quest_id(), limitedReward.get_quest_step());
writeQuestNote(pc, limitedReward);  // ★新增
                    }

                    limitedReward.set_appearCount(limitedReward.get_appearCount() + 1);
                    LimitedReward.limitedRewardToList(i, (long) limitedReward.get_appearCount());

                    if (limitedReward.get_message() != null && !limitedReward.get_message().equals("")) {
                        int qq = limitedReward.getTotalCount() - limitedReward.get_appearCount();
                        if (qq <= 0) {
                            limitedReward.set_Activity(false);
                            LimitedReward.limitedRewardToList_isOver(i, 0);
                            World.get().broadcastPacketToAll(new S_ServerMessage(String.format(limitedReward.get_message_end(), (Integer) codes)));
                        } else {
                            World.get().broadcastPacketToAll(new S_ServerMessage(String.format(limitedReward.get_message(), (Integer) qq)));
                        }
                    }
                }
            }
        }
    }

    /**
     * 🔒 安全檢查：任務進度防呆
     * 確保不會發生 NullPointerException。
     */
    private static boolean hasQuestStep(L1PcInstance pc, int questId, int questStep) {
        try {
            if (pc == null) return false;
            if (pc.getQuest() == null) return false;

            int step = pc.getQuest().get_step(questId);

            if (step <= 0) return false;

            return step == questStep;

        } catch (Exception e) {
            _log.log(Level.WARNING, "hasQuestStep 檢查發生例外: " + e.getLocalizedMessage(), e);
            return false;
        }
    }

    private static Object getArray(String s, String sToken, int iType) {
        StringTokenizer st = new StringTokenizer(s, sToken);
        int iSize = st.countTokens();
        String sTemp = null;
        if (iType == 1) {
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                iReturn[i] = Integer.parseInt(sTemp);
            }
            return iReturn;
        } else if (iType == 2) {
            String[] sReturn = new String[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                sReturn[i] = sTemp;
            }
            return sReturn;
        } else if (iType == 3) {
            String sReturn2 = null;
            for (int i = 0; i < iSize; ++i) {
                sTemp = (sReturn2 = st.nextToken());
            }
            return sReturn2;
        } else if (iType == 4) {
            short[] iReturn2 = new short[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                iReturn2[i] = Short.parseShort(sTemp);
            }
            return iReturn2;
        } else {
            return null;
        }
    }

    public int get_use_type() {
        return this._use_type;
    }

    public void set_use_type(int use_type) {
        this._use_type = use_type;
        if (use_type >= 128) {
            use_type -= 128;
            this._isWarrior = true;
        }
        if (use_type >= 64) {
            use_type -= 64;
            this._isIllusionist = true;
        }
        if (use_type >= 32) {
            use_type -= 32;
            this._isDragonKnight = true;
        }
        if (use_type >= 16) {
            use_type -= 16;
            this._isDarkelf = true;
        }
        if (use_type >= 8) {
            use_type -= 8;
            this._isWizard = true;
        }
        if (use_type >= 4) {
            use_type -= 4;
            this._isElf = true;
        }
        if (use_type >= 2) {
            use_type -= 2;
            this._isKnight = true;
        }
        if (use_type >= 1) {
            --use_type;
            this._isCrown = true;
        }
    }

    public boolean is_use(L1PcInstance pc) {
        try {
            if (pc.isCrown() && this._isCrown) {
                return true;
            } else if (pc.isKnight() && this._isKnight) {
                return true;
            } else if (pc.isElf() && this._isElf) {
                return true;
            } else if (pc.isWizard() && this._isWizard) {
                return true;
            } else if (pc.isDarkelf() && this._isDarkelf) {
                return true;
            } else if (pc.isDragonKnight() && this._isDragonKnight) {
                return true;
            } else if (pc.isIllusionist() && this._isIllusionist) {
                return true;
            } else if (this._isWarrior) {
                // 你的核心尚未支援戰士，這裡保留空位以免編譯錯誤
                return false;
            }
        } catch (Exception e) {
            L1WilliamLimitedReward._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return false;
    }
}

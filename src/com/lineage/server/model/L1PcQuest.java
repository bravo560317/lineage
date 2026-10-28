package com.lineage.server.model;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.data.event.QuestMobSet;
import com.lineage.server.datatables.ServerQuestMobTable;
import com.lineage.server.datatables.lock.CharacterQuestReading;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.CharQuest;

public class L1PcQuest {

    private static final Log _log = LogFactory.getLog(L1PcQuest.class);

    private L1PcInstance _owner;
    private Map<Integer, CharQuest> _quest = new HashMap<>(); // ✅ 初始化避免 null

    public static final int QUEST_NOT = 0;
    public static final int QUEST_END = 255;

    public L1PcQuest(L1PcInstance owner) {
        this._owner = owner;
    }

    public L1PcInstance get_owner() {
        return _owner;
    }

    // 取得任務進度，沒有資料則回傳 0
    public int get_step(int quest_id) {
        try {
            if (_quest == null) { // ✅ 防止空指標
                _quest = new HashMap<>();
                return 0;
            }
            CharQuest step = _quest.get(quest_id);
            if (step == null) {
                return 0;
            }
            return step.get_quest_step();
        } catch (Exception e) {
            _log.error("get_step 錯誤: " + e.getLocalizedMessage(), e);
            return 0;
        }
    }

    // 設定任務進度
    public void set_step(int quest_id, int step) {
        try {
            if (_quest == null) { // ✅ 防止空指標
                _quest = new HashMap<>();
            }

            CharQuest quest = _quest.get(quest_id);
            if (quest == null) {
                quest = new CharQuest();
                quest.set_quest_step(step);
                if (QuestMobSet.START) {
                    quest.set_mob_count(ServerQuestMobTable.get().getMobCount(quest_id, step));
                } else {
                    quest.set_mob_count(null);
                }
                CharacterQuestReading.get().storeQuest(_owner.getId(), quest_id, quest);
            } else {
                quest.set_quest_step(step);
                CharacterQuestReading.get().updateQuest(_owner.getId(), quest_id, quest);
            }
            _quest.put(quest_id, quest);

        } catch (Exception e) {
            _log.error("set_step 錯誤: " + e.getLocalizedMessage(), e);
        }
    }

    public void set_end(int quest_id) {
        try {
            if (_quest == null) return; // ✅ 防止空指標
            CharQuest quest = _quest.get(quest_id);
            if (quest != null) {
                quest.set_quest_step(255);
                CharacterQuestReading.get().updateQuest(_owner.getId(), quest_id, quest);
            }
        } catch (Exception e) {
            _log.error("set_end 錯誤: " + e.getLocalizedMessage(), e);
        }
    }

    public boolean isStart(int quest_id) {
        try {
            int step = get_step(quest_id);
            return (step > 0 && step < 255);
        } catch (Exception e) {
            _log.error("isStart 錯誤: " + e.getLocalizedMessage(), e);
            return false;
        }
    }

    public boolean isEnd(int quest_id) {
        try {
            return get_step(quest_id) == 255;
        } catch (Exception e) {
            _log.error("isEnd 錯誤: " + e.getLocalizedMessage(), e);
            return false;
        }
    }

    public void load() {
        try {
            _quest = CharacterQuestReading.get().get(_owner.getId());
            if (_quest == null) {
                _quest = new HashMap<>(); // ✅ 確保永不為 null
            }
        } catch (Exception e) {
            _log.error("load 錯誤: " + e.getLocalizedMessage(), e);
            _quest = new HashMap<>();
        }
    }

    public void set_step(int quest_id, int step, int clear) {
        try {
            if (_quest == null) _quest = new HashMap<>();

            CharQuest quest = _quest.get(quest_id);
            if (quest == null) {
                quest = new CharQuest();
                quest.set_quest_step(step);
                if (QuestMobSet.START) {
                    quest.set_mob_count(ServerQuestMobTable.get().getMobCount(quest_id, step));
                } else {
                    quest.set_mob_count(null);
                }
                CharacterQuestReading.get().storeQuest(_owner.getId(), quest_id, quest, clear);
            } else {
                quest.set_quest_step(step);
                CharacterQuestReading.get().updateQuest(_owner.getId(), quest_id, quest);
            }
            _quest.put(quest_id, quest);
        } catch (Exception e) {
            _log.error("set_step(clear) 錯誤: " + e.getLocalizedMessage(), e);
        }
    }

    public int[] get_mob_count(int quest_id) {
        try {
            if (_quest == null) return null;
            CharQuest cq = _quest.get(quest_id);
            return cq == null ? null : cq.get_mob_count();
        } catch (Exception e) {
            _log.error("get_mob_count 錯誤: " + e.getLocalizedMessage(), e);
            return null;
        }
    }

    public void add_mob_count(int quest_id, int nob) {
        try {
            if (_quest == null) return;
            CharQuest quest = _quest.get(quest_id);
            if (quest != null) {
                quest.add_mob_count(nob);
                CharacterQuestReading.get().updateQuest(_owner.getId(), quest_id, quest);
            }
        } catch (Exception e) {
            _log.error("add_mob_count 錯誤: " + e.getLocalizedMessage(), e);
        }
    }

    public void set_mob_count(int quest_id, int step) {
        try {
            if (_quest == null) return;
            CharQuest quest = _quest.get(quest_id);
            if (quest != null) {
                quest.set_mob_count(ServerQuestMobTable.get().getMobCount(quest_id, step));
                CharacterQuestReading.get().updateQuest(_owner.getId(), quest_id, quest);
            }
        } catch (Exception e) {
            _log.error("set_mob_count 錯誤: " + e.getLocalizedMessage(), e);
        }
    }
}

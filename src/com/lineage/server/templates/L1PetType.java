package com.lineage.server.templates;

import java.util.Random;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.utils.RangeInt;

public class L1PetType {

    // 寵物對應的 NPC 模板 ID
    private final int _baseNpcId;

    // 該寵物對應的 NPC 範本資料
    private final L1Npc _baseNpcTemplate;

    // 寵物名稱
    private final String _name;

    // 捕捉該寵物所需的道具 ID
    private final int _itemIdForTaming;

    // 每次升級時增加的 HP 範圍
    private final RangeInt _hpUpRange;

    // 每次升級時增加的 MP 範圍
    private final RangeInt _mpUpRange;

    // 可隨機進化成的 NPC ID 列表
    private final int[] _npcIdForEvolving;

    // 進化所需道具 ID
    private final int _evolvItemId;

    // 訊息 ID 列表，用於寵物等級或狀態變化時顯示文字
    private final int[] _msgIds;

    // 反抗訊息 ID
    private final int _defyMsgId;

    // 寵物護甲值
    private final int _PetArmor;

    private static final Random _random = new Random();

    public L1PetType(
            int baseNpcId,
            String name,
            int itemIdForTaming,
            RangeInt hpUpRange,
            RangeInt mpUpRange,
            int evolvItemId,
            String npcIdForEvolving,
            int[] msgIds,
            int defyMsgId,
            int PetArmor) {

        _baseNpcId = baseNpcId;
        _baseNpcTemplate = NpcTable.get().getTemplate(baseNpcId);
        _name = name;
        _evolvItemId = evolvItemId;
        _itemIdForTaming = itemIdForTaming;
        _hpUpRange = hpUpRange;
        _mpUpRange = mpUpRange;
        _msgIds = msgIds;
        _defyMsgId = defyMsgId;
        _PetArmor = PetArmor;

        // 解析逗號分隔的進化 NPC ID 字串，例如 "96501,96502,96503"
        if (npcIdForEvolving != null && npcIdForEvolving.length() > 0) {

            String[] split = npcIdForEvolving.split(",");

            _npcIdForEvolving = new int[split.length];

            for (int i = 0; i < split.length; i++) {
                _npcIdForEvolving[i] = Integer.parseInt(split[i].trim());
            }

        } else {
            _npcIdForEvolving = new int[0];
        }
    }

    // 隨機取得可進化的 NPC ID
    public int getNpcIdForEvolvingRandom() {

        if (_npcIdForEvolving.length == 0) {
            return 0;
        }

        int index = _random.nextInt(_npcIdForEvolving.length);

        return _npcIdForEvolving[index];
    }

    // 是否可以進化
    public boolean canEvolve() {
        return _npcIdForEvolving.length > 0;
    }

    // 取得寵物護甲值
    public int getPetArmor() {
        return _PetArmor;
    }

    // 取得基礎 NPC ID
    public int getBaseNpcId() {
        return _baseNpcId;
    }

    // 取得基礎 NPC 範本
    public L1Npc getBaseNpcTemplate() {
        return _baseNpcTemplate;
    }

    // 取得寵物名稱
    public String getName() {
        return _name;
    }

    // 取得馴服寵物所需道具 ID
    public int getItemIdForTaming() {
        return _itemIdForTaming;
    }

    // 是否可以馴服
    public boolean canTame() {
        return _itemIdForTaming != 0;
    }

    // 取得 HP 增加範圍
    public RangeInt getHpUpRange() {
        return _hpUpRange;
    }

    // 取得 MP 增加範圍
    public RangeInt getMpUpRange() {
        return _mpUpRange;
    }

    // 根據編號取得訊息 ID
    public int getMessageId(int num) {
        if (num == 0) {
            return 0;
        }
        return _msgIds[num - 1];
    }

    // 根據寵物等級回傳訊息等級索引
    public static int getMessageNumber(int level) {

        if (50 <= level) return 5;
        if (48 <= level) return 4;
        if (36 <= level) return 3;
        if (24 <= level) return 2;
        if (12 <= level) return 1;

        return 0;
    }

    // 取得反抗訊息 ID
    public int getDefyMessageId() {
        return _defyMsgId;
    }

    // 取得進化所需道具 ID
    public int getEvolvItemId() {
        return _evolvItemId;
    }
}
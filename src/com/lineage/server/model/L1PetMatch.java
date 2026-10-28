package com.lineage.server.model;

import java.util.Timer;
import java.util.TimerTask;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.lock.PetReading;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;

public class L1PetMatch
{
    public static final int STATUS_NONE = 0;
    public static final int STATUS_READY1 = 1;
    public static final int STATUS_READY2 = 2;
    public static final int STATUS_PLAYING = 3;
    public static final int MAX_PET_MATCH = 1;

    private static final short[] PET_MATCH_MAPID;
    private String[] _pc1Name;
    private String[] _pc2Name;
    private L1PetInstance[] _pet1;
    private L1PetInstance[] _pet2;
    private static L1PetMatch _instance;

    // ===== 入場費設定 =====
    private static final int FEE_ITEM_ID = 40308;          // 金幣
    private static final int FEE_ITEM_COUNT = 200000;      // 需要 20 萬個
    private static final String FEE_ITEM_NAME = "金幣";     // 顯示用名稱
    private static final int PAID_FLAG_SKILL = 852239;     // 付費旗標（自訂）
    private static final int PAID_FLAG_TIME_MS = 10 * 30 * 1000; // 5 分鐘

    static {
        PET_MATCH_MAPID = new short[] { 5125, 5131, 5132, 5133, 5134 };
    }

    public L1PetMatch() {
        this._pc1Name = new String[1];
        this._pc2Name = new String[1];
        this._pet1 = new L1PetInstance[1];
        this._pet2 = new L1PetInstance[1];
    }

    public static L1PetMatch getInstance() {
        if (L1PetMatch._instance == null) {
            L1PetMatch._instance = new L1PetMatch();
        }
        return L1PetMatch._instance;
    }

    public int setPetMatchPc(int petMatchNo, L1PcInstance pc, L1PetInstance pet) {
        int status = this.getPetMatchStatus(petMatchNo);
        if (status == 0) {
            this._pc1Name[petMatchNo] = pc.getName();
            this._pet1[petMatchNo] = pet;
            return 1;
        } else if (status == 1) {
            this._pc2Name[petMatchNo] = pc.getName();
            this._pet2[petMatchNo] = pet;
            return 3;
        } else if (status == 2) {
            this._pc1Name[petMatchNo] = pc.getName();
            this._pet1[petMatchNo] = pet;
            return 3;
        } else {
            return 0;
        }
    }

    private synchronized int getPetMatchStatus(int petMatchNo) {
        L1PcInstance pc1 = null;
        if (this._pc1Name[petMatchNo] != null) {
            pc1 = World.get().getPlayer(this._pc1Name[petMatchNo]);
        }
        L1PcInstance pc2 = null;
        if (this._pc2Name[petMatchNo] != null) {
            pc2 = World.get().getPlayer(this._pc2Name[petMatchNo]);
        }
        if (pc1 == null && pc2 == null) {
            return 0;
        } else if (pc1 == null && pc2 != null) {
            if (pc2.getMapId() == L1PetMatch.PET_MATCH_MAPID[petMatchNo]) {
                return 2;
            } else {
                this._pc2Name[petMatchNo] = null;
                this._pet2[petMatchNo] = null;
                return 0;
            }
        } else if (pc1 != null && pc2 == null) {
            if (pc1.getMapId() == L1PetMatch.PET_MATCH_MAPID[petMatchNo]) {
                return 1;
            } else {
                this._pc1Name[petMatchNo] = null;
                this._pet1[petMatchNo] = null;
                return 0;
            }
        } else if (pc1.getMapId() == L1PetMatch.PET_MATCH_MAPID[petMatchNo] && pc2.getMapId() == L1PetMatch.PET_MATCH_MAPID[petMatchNo]) {
            return 3;
        } else if (pc1.getMapId() == L1PetMatch.PET_MATCH_MAPID[petMatchNo]) {
            this._pc2Name[petMatchNo] = null;
            this._pet2[petMatchNo] = null;
            return 1;
        } else if (pc2.getMapId() == L1PetMatch.PET_MATCH_MAPID[petMatchNo]) {
            this._pc1Name[petMatchNo] = null;
            this._pet1[petMatchNo] = null;
            return 2;
        } else {
            return 0;
        }
    }

    private int decidePetMatchNo() {
        int i = 0;
        while (i < 1) {
            int status = this.getPetMatchStatus(i);
            if (status == 1 || status == 2) {
                return i;
            } else {
                ++i;
            }
        }
        i = 0;
        while (i < 1) {
            int status = this.getPetMatchStatus(i);
            if (status == 0) {
                return i;
            } else {
                ++i;
            }
        }
        return -1;
    }

    // 進場 + 扣費（不足時顯示中文）
    public synchronized boolean enterPetMatch(L1PcInstance pc, int amuletId) {
        int petMatchNo = this.decidePetMatchNo();
        if (petMatchNo == -1) {
            pc.sendPackets(new S_ServerMessage(1182)); // 目前無法參加
            return false;
        }

        // 扣入場費（金幣）
        if (!pc.getInventory().consumeItem(FEE_ITEM_ID, FEE_ITEM_COUNT)) {
            pc.sendPackets(new S_ServerMessage("\\fR" + FEE_ITEM_NAME + "不足，無法進入（需要 " + FEE_ITEM_COUNT + " 個）。"));
            return false;
        }
        // 設定付費旗標
        pc.setSkillEffect(PAID_FLAG_SKILL, PAID_FLAG_TIME_MS);
        // 顯示扣除提示
        pc.sendPackets(new S_ServerMessage("\\fR已扣除 " + FEE_ITEM_NAME + " ×" + FEE_ITEM_COUNT));

        // 生成寵物；失敗退費
        L1PetInstance pet = this.withdrawPet(pc, amuletId);
        if (pet == null) {
            L1ItemInstance refund = ItemTable.get().createItem(FEE_ITEM_ID);
            if (refund != null) {
                refund.setCount(FEE_ITEM_COUNT);
                pc.getInventory().storeItem(refund);
                pc.sendPackets(new S_ServerMessage(403, refund.getLogName()));
            }
            pc.removeSkillEffect(PAID_FLAG_SKILL);
            pc.sendPackets(new S_ServerMessage(1182));
            return false;
        }

        L1Teleport.teleport(pc, 32799, 32868, L1PetMatch.PET_MATCH_MAPID[petMatchNo], 0, true);
        L1PetMatch.L1PetMatchReadyTimer timer = new L1PetMatch.L1PetMatchReadyTimer(petMatchNo, pc, pet);
        timer.begin();
        return true;
    }

    private L1PetInstance withdrawPet(L1PcInstance pc, int amuletId) {
        L1Pet l1pet = PetReading.get().getTemplate(amuletId);
        if (l1pet == null) {
            return null;
        } else {
            L1Npc npcTemp = NpcTable.get().getTemplate(l1pet.get_npcid());
            L1PetInstance pet = new L1PetInstance(npcTemp, pc, l1pet);
            pet.setPetcost(6);
            return pet;
        }
    }

    public void startPetMatch(int petMatchNo) {
        this._pet1[petMatchNo].setCurrentPetStatus(1);
        this._pet1[petMatchNo].setTarget(this._pet2[petMatchNo]);
        this._pet2[petMatchNo].setCurrentPetStatus(1);
        this._pet2[petMatchNo].setTarget(this._pet1[petMatchNo]);
        L1PetMatch.L1PetMatchTimer timer = new L1PetMatch.L1PetMatchTimer(this._pet1[petMatchNo], this._pet2[petMatchNo], petMatchNo);
        timer.begin();
    }

    public void endPetMatch(int petMatchNo, int winNo) {
        L1PcInstance pc1 = World.get().getPlayer(this._pc1Name[petMatchNo]);
        L1PcInstance pc2 = World.get().getPlayer(this._pc2Name[petMatchNo]);
        if (winNo == 1) {
            this.giveMedal(pc1, petMatchNo, true);
            this.giveMedal(pc2, petMatchNo, false);
        } else if (winNo == 2) {
            this.giveMedal(pc1, petMatchNo, false);
            this.giveMedal(pc2, petMatchNo, true);
        } else if (winNo == 3) {
            this.giveMedal(pc1, petMatchNo, false);
            this.giveMedal(pc2, petMatchNo, false);
        }
        this.qiutPetMatch(petMatchNo);
    }

    private void giveMedal(L1PcInstance pc, int petMatchNo, boolean isWin) {
        if (pc == null) return;
        if (pc.getMapId() != L1PetMatch.PET_MATCH_MAPID[petMatchNo]) return;

        // 付費旗標檢查（避免繞過入場流程）
        if (!pc.hasSkillEffect(PAID_FLAG_SKILL)) {
            return;
        }
        pc.removeSkillEffect(PAID_FLAG_SKILL);

        if (isWin) {
            pc.sendPackets(new S_ServerMessage(1166, pc.getName()));
            L1ItemInstance item = ItemTable.get().createItem(40308);
            if (item != null && pc.getInventory().checkAddItem(item, 300000L) == 0) {
                item.setCount(300000L);
                pc.getInventory().storeItem(item);
                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
            }
        } else {
            L1ItemInstance item = ItemTable.get().createItem(40308);
            if (item != null && pc.getInventory().checkAddItem(item, 100000L) == 0) {
                item.setCount(100000L);
                pc.getInventory().storeItem(item);
                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
            }
        }
    }

    private void qiutPetMatch(int petMatchNo) {
        L1PcInstance pc1 = World.get().getPlayer(this._pc1Name[petMatchNo]);
        if (pc1 != null && pc1.getMapId() == L1PetMatch.PET_MATCH_MAPID[petMatchNo]) {
            Object[] array;
            for (int length = (array = pc1.getPetList().values().toArray()).length, i = 0; i < length; ++i) {
                Object object = array[i];
                if (object instanceof L1PetInstance) {
                    L1PetInstance pet = (L1PetInstance)object;
                    pet.dropItem();
                    pc1.getPetList().remove((Integer)pet.getId());
                    pet.deleteMe();
                }
            }
            pc1.removeSkillEffect(PAID_FLAG_SKILL);
            L1Teleport.teleport(pc1, 32630, 32744, (short)4, 4, true);
        }
        this._pc1Name[petMatchNo] = null;
        this._pet1[petMatchNo] = null;

        L1PcInstance pc2 = World.get().getPlayer(this._pc2Name[petMatchNo]);
        if (pc2 != null && pc2.getMapId() == L1PetMatch.PET_MATCH_MAPID[petMatchNo]) {
            Object[] array2;
            for (int length2 = (array2 = pc2.getPetList().values().toArray()).length, j = 0; j < length2; ++j) {
                Object object2 = array2[j];
                if (object2 instanceof L1PetInstance) {
                    L1PetInstance pet = (L1PetInstance)object2;
                    pet.dropItem();
                    pc2.getPetList().remove((Integer)pet.getId());
                    pet.deleteMe();
                }
            }
            pc2.removeSkillEffect(PAID_FLAG_SKILL);
            L1Teleport.teleport(pc2, 32630, 32744, (short)4, 4, true);
        }
        this._pc2Name[petMatchNo] = null;
        this._pet2[petMatchNo] = null;
    }

    public class L1PetMatchWaitTimer extends TimerTask {
        private final int _petMatchNo;
        private final L1PcInstance _pc;

        public L1PetMatchWaitTimer(int petMatchNo, L1PcInstance pc) {
            this._petMatchNo = petMatchNo;
            this._pc = pc;
        }

        public void begin() {
            Timer timer = new Timer();
            timer.schedule(this, 180000L); // 3分鐘 (180秒)
        }

        @Override
        public void run() {
            try {
                int status = L1PetMatch.getInstance().getPetMatchStatus(this._petMatchNo);
                // 若狀態還是 1 或是 2，代表對手沒出現，比賽沒有開始
                if (status == 1 || status == 2) {
                    if (this._pc != null && this._pc.getMapId() == L1PetMatch.PET_MATCH_MAPID[this._petMatchNo]) {
                        this._pc.sendPackets(new S_ServerMessage("\\fR等待對手超時，結束匹配，退回入場費。"));
                        L1ItemInstance refund = ItemTable.get().createItem(FEE_ITEM_ID);
                        if (refund != null) {
                            refund.setCount(FEE_ITEM_COUNT); // 依照我們設定的入場費變數自動退還
                            this._pc.getInventory().storeItem(refund);
                            this._pc.sendPackets(new S_ServerMessage(403, refund.getLogName()));
                        }
                    }
                    // 將玩家傳送出去並重置該房間狀態
                    L1PetMatch.getInstance().qiutPetMatch(this._petMatchNo);
                }
            } catch (Throwable t) {
            }
        }
    }

    public class L1PetMatchReadyTimer extends TimerTask
    {
        private final int _petMatchNo;
        private final L1PcInstance _pc;
        private final L1PetInstance _pet;

        public L1PetMatchReadyTimer(int petMatchNo, L1PcInstance pc, L1PetInstance pet) {
            this._petMatchNo = petMatchNo;
            this._pc = pc;
            this._pet = pet;
        }

        public void begin() {
            Timer timer = new Timer();
            timer.schedule(this, 3000L);
        }

        @Override
        public void run() {
            try {
                do {
                    Thread.sleep(1000L);
                    if (this._pc == null || this._pet == null) {
                        this.cancel();
                        return;
                    }
                } while (this._pc.isTeleport());
                
                // 註冊玩家與寵物進入房間，並取得當前房間狀態
                int matchStatus = L1PetMatch.getInstance().setPetMatchPc(this._petMatchNo, this._pc, this._pet);
                
                // 判斷是 A玩家 還是 B玩家，分別傳送到對戰場地兩側
                if (this._pc.getName().equals(L1PetMatch.this._pc1Name[this._petMatchNo])) {
                    this._pet.teleport(32801, 32864, 5); // A玩家的寵物
                } else {
                    this._pet.teleport(32797, 32864, 5); // B玩家的寵物
                }

                if (matchStatus == 3) {
                    L1PetMatch.getInstance().startPetMatch(this._petMatchNo);
                } else {
                    // 若未滿 2 人，提示玩家並啟動 3 分鐘超時倒數
                    this._pc.sendPackets(new S_ServerMessage("\\fU正在等待對手加入... (3分鐘後若無人匹配將自動退出)"));
                    L1PetMatchWaitTimer waitTimer = new L1PetMatchWaitTimer(this._petMatchNo, this._pc);
                    waitTimer.begin();
                }
                this.cancel();
                return;
            }
            catch (Throwable t) {
                return;
            }
        }
    }

    public class L1PetMatchTimer extends TimerTask
    {
        private final L1PetInstance _pet1;
        private final L1PetInstance _pet2;
        private final int _petMatchNo;
        private int _counter;

        public L1PetMatchTimer(L1PetInstance pet1, L1PetInstance pet2, int petMatchNo) {
            this._counter = 0;
            this._pet1 = pet1;
            this._pet2 = pet2;
            this._petMatchNo = petMatchNo;
        }

        public void begin() {
            Timer timer = new Timer();
            timer.schedule(this, 0L);
        }

        @Override
        public void run() {
            try {
                do {
                    Thread.sleep(3000L);
                    ++this._counter;
                    if (this._pet1 == null || this._pet2 == null) {
                        this.cancel();
                        return;
                    } else if (this._pet1.isDead() || this._pet2.isDead()) {
                        int winner = 0;
                        if (!this._pet1.isDead() && this._pet2.isDead()) {
                            winner = 1;
                        } else if (this._pet1.isDead() && !this._pet2.isDead()) {
                            winner = 2;
                        } else {
                            winner = 3;
                        }
                        L1PetMatch.getInstance().endPetMatch(this._petMatchNo, winner);
                        this.cancel();
                        return;
                    } else {
                        continue;
                    }
                } while (this._counter != 100);
                L1PetMatch.getInstance().endPetMatch(this._petMatchNo, 3);
                this.cancel();
                return;
            }
            catch (Throwable t) {
                return;
            }
        }
    }
}
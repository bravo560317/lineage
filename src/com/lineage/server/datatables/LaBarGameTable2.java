package com.lineage.server.datatables;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.World;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class LaBarGameTable2 {

    private static final Log _log;
    private static ArrayList<ArrayList<Object>> _array;
    private static final String TOKEN = ",";
    private static LaBarGameTable2 _instance;

    // ===== 無上限對應：以 玩家ID -> (道具ID -> 對話檔名) 暫存 =====
    private static final ConcurrentHashMap<Integer, Map<Integer, String>> CTX = new ConcurrentHashMap<>();

    static {
        _log = LogFactory.getLog(LaBarGameTable2.class);
        LaBarGameTable2._array = new ArrayList<>();
    }

    public static LaBarGameTable2 get() {
        if (LaBarGameTable2._instance == null) {
            LaBarGameTable2._instance = new LaBarGameTable2();
        }
        return LaBarGameTable2._instance;
    }

    private LaBarGameTable2() {
        PerformanceTimer timer = new PerformanceTimer();
        this.getData();
        if (LaBarGameTable2._array.size() <= 0) {
            LaBarGameTable2._array.clear();
            LaBarGameTable2._array = null;
        }
    }

    public static boolean forLaBarGame(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        if (LaBarGameTable2._array == null || LaBarGameTable2._array.size() <= 0) {
            return false;
        } else {
            ArrayList<?> aTempData = null;
            for (int i = 0; i < LaBarGameTable2._array.size(); ++i) {
                aTempData = LaBarGameTable2._array.get(i);
                if (aTempData.get(0) != null
                        && ((Integer) aTempData.get(0)) == npcid
                        && ((String) aTempData.get(1)).equals(s)) {

                    // 節流
                    if (pc.hasSkillEffect(123456)) {
                        pc.sendPackets(new S_SystemMessage("延遲中請稍後在操作"));
                        return false;
                    }

                    boolean isCreate = true;
                    // 確認材料
                    if (((Integer) aTempData.get(8)) != 0
                            && ((Integer) aTempData.get(9)) != 0
                            && !pc.getInventory().checkItem(((Integer) aTempData.get(8)),
                                    (long) ((Integer) aTempData.get(9)).intValue())) {
                        L1Item temp = ItemTable.get().getTemplate(((Integer) aTempData.get(8)));
                        pc.sendPackets(new S_ServerMessage(337,
                                temp.getName() + "("
                                        + (((long) ((Integer) aTempData.get(9)).intValue())
                                                - pc.getInventory().countItems(temp.getItemId()))
                                        + ")"));
                        isCreate = false;
                    }
                    if (isCreate) {
                        pc.getInventory().consumeItem(((Integer) aTempData.get(8)),
                                (long) ((Integer) aTempData.get(9)).intValue());
                    }

                    if (isCreate) {
                        String[] text = (String[]) (Object) aTempData.get(2);
                        int[] ids = (int[]) (Object) aTempData.get(3);

                        if (text == null || ids == null || text.length == 0 || ids.length == 0) {
                            return false;
                        }

                        // 取對齊長度（避免越界），不限數量
                        int pairLen = Math.min(text.length, ids.length);

                        // 清掉舊的對應（避免前一次殘留）
                        text00(pc);

                        int delayMs = ((Integer) aTempData.get(10)).intValue();

                        // 跑對話 + 建立對應（無上限）
                        for (int j = 0; j < pairLen; ++j) {
                            try {
                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), text[j]));
                                // 存入 Map：道具ID -> 對話檔名
                                save0(pc, ids[j], text[j]);
                                if (delayMs > 0) {
                                    Thread.sleep(delayMs);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        // 從已配對的池子中抽，確保抽到必有對應
                        int rndItem = RandomArrayList.getInt(pairLen);
                        int giveItemGet = ids[rndItem];

                        L1ItemInstance item = ItemTable.get().createItem(giveItemGet);
                        if (item == null) {
                            return false;
                        }

                        if (item.isStackable()) {
                            item.setCount((long) ((Integer) aTempData.get(4)).intValue());
                        } else {
                            item.setCount(1L);
                        }

                        // 入背包或丟地上
                        if (pc.getInventory().checkAddItem(item,
                                (long) ((Integer) aTempData.get(4)).intValue()) == 0) {
                            pc.getInventory().storeItem(item);
                            give_text(pc, item); // 顯示對應對話
                            text00(pc); // 清空對應
                        } else {
                            World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                            give_text(pc, item);
                            text00(pc);
                        }

                        // 顯示/廣播訊息
                        pc.sendPackets(new S_SystemMessage(String.format((String) aTempData.get(5), item.getLogName())));
                        if (((Integer) aTempData.get(6)) == 1) {
                            World.get().broadcastPacketToAll(new S_SystemMessage(
                                    String.format((String) aTempData.get(7), pc.getName(), item.getLogName())));
                        }

                        // 冷卻
                        pc.setSkillEffect(123456, 5000);
                     // ★ 新增：若是指定 NPC，3 秒後回到 comtext
                        reopenDialogAfter(pc, npc, npcid, "comtext", 3000);
                    }
                }
            }
            return false;
        }
    }

    // ===== Map 版的 save/give/clear（保留原介面名稱） =====

    /** 建立一組「道具ID -> 對話檔名」對應（無上限） */
    public static void save0(L1PcInstance pc, int itemId, String dialogFile) {
        if (pc == null || itemId <= 0 || dialogFile == null) return;
        CTX.computeIfAbsent(pc.getId(), k -> new ConcurrentHashMap<>()).put(itemId, dialogFile);
    }

    /** 依玩家剛獲得的道具，送出對應對話 */
    public static void give_text(L1PcInstance pc, L1ItemInstance item) {
        if (pc == null || item == null) return;
        Map<Integer, String> m = CTX.get(pc.getId());
        if (m == null) return;
        String dlg = m.get(item.getItemId());
        if (dlg != null) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), dlg));
        }
    }

    /** 清除該玩家本次所有對應（避免殘留） */
    public static void text00(L1PcInstance pc) {
        if (pc == null) return;
        CTX.remove(pc.getId());
    }

    // ===== DB 載入 =====

    private void getData() {
        Connection cn = null;
        Statement ps = null;
        ResultSet rset = null;
        String sTemp = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.createStatement();
            rset = ps.executeQuery("SELECT * FROM w_npc跑圖對話text");
            while (rset.next()) {
                ArrayList<Object> aReturn = new ArrayList<>();
                aReturn.add(0, Integer.valueOf(rset.getInt("npcid")));
                sTemp = rset.getString("action");
                aReturn.add(1, sTemp);

                if (rset.getString("對話檔名") != null && !rset.getString("對話檔名").equals("")
                        && !rset.getString("對話檔名").equals("0")) {
                    aReturn.add(2, getArray(rset.getString("對話檔名"), ",", 2));
                } else {
                    aReturn.add(2, null);
                }
                if (rset.getString("抽道具編號") != null && !rset.getString("抽道具編號").equals("")
                        && !rset.getString("抽道具編號").equals("0")) {
                    aReturn.add(3, getArray(rset.getString("抽道具編號"), ",", 1));
                } else {
                    aReturn.add(3, null);
                }
                aReturn.add(4, Integer.valueOf(rset.getInt("抽中數量")));
                aReturn.add(5, rset.getString("顯示文字"));
                aReturn.add(6, Integer.valueOf(rset.getInt("是否廣播")));
                aReturn.add(7, rset.getString("廣播文字"));
                aReturn.add(8, Integer.valueOf(rset.getInt("確認道具")));
                aReturn.add(9, Integer.valueOf(rset.getInt("確認道具數量")));
                aReturn.add(10, Integer.valueOf(rset.getInt("跑圖延遲毫秒")));
                LaBarGameTable2._array.add(aReturn);
            }
        } catch (SQLException e) {
            LaBarGameTable2._log.error(e.getLocalizedMessage(), e);
        } finally {
            SQLUtil.close(rset);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
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
        } else {
            return null;
        }
    }
    
    /** 若 npcid 符合，延遲 delayMs 後自動打開指定對話檔 */
    private static void reopenDialogAfter(final L1PcInstance pc,
                                          final L1NpcInstance npc,
                                          final int npcid,
                                          final String dialog,
                                          final int delayMs) {
        if (pc == null || npc == null) return;
        if (npcid != 97209) return;

        // 建議用排程，避免 Thread.sleep 佔用執行緒
        GeneralThreadPool.get().schedule(new Runnable() {
            @Override public void run() {
                try {
                    // 這裡一定要用 npc.getId()，不要用 pc.getId()
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), dialog));
                } catch (Throwable ignore) {}
            }
        }, delayMs);
    }




    public static ArrayList<ArrayList<Object>> getSetList() {
        return LaBarGameTable2._array;
    }
}

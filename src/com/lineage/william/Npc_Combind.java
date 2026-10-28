package com.lineage.william;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.config.ConfigRate;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.utils.RandomArrayList;
import java.util.Random;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;
import java.util.List;

public class Npc_Combind {
    private static ArrayList<ArrayList<Object>> aData = new ArrayList<>();
    private static boolean NO_GET_DATA = false;
    public static final String TOKEN = ",";

    public static boolean forNpcQuest(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        if (!NO_GET_DATA) {
            NO_GET_DATA = true;
            getData();
        }
        for (ArrayList<Object> aTempData : aData) {
            if ((Integer) aTempData.get(0) == npcid && ((String) aTempData.get(1)).equals(s)) {
                int[] olddolls = (int[]) aTempData.get(2);
                int needcount = (Integer) aTempData.get(3);
                int[] giveItem = (int[]) aTempData.get(5);
                int giveItemGet = giveItem[RandomArrayList.getInt(giveItem.length)];
                Random _random = new Random();

                // ✅ 改成允許同樣 itemId 的重複數量合成
                List<L1ItemInstance> consumedItems = new ArrayList<>();

                if (ConfigRate.dolllength) {
                    // ✅ 模式開啟：允許任意重複 itemId
                    for (L1ItemInstance item : pc.getInventory().getItems()) {
                        if (item == null) continue;
                        int itemId = item.getItemId();
                        for (int validId : olddolls) {
                            if (itemId == validId) {
                                long count = item.getCount();
                                for (int i = 0; i < count; i++) {
                                    consumedItems.add(item);
                                    if (consumedItems.size() == needcount) break;
                                }
                            }
                            if (consumedItems.size() == needcount) break;
                        }
                        if (consumedItems.size() == needcount) break;
                    }
                } else {
                    // ❌ 模式關閉：必須 4 張不同 itemId（不同卡片）
                    List<Integer> usedIds = new ArrayList<>();
                    for (int validId : olddolls) {
                        if (usedIds.contains(validId)) continue;
                        L1ItemInstance[] found = pc.getInventory().findItemsId1(validId);
                        if (found != null && found.length > 0) {
                            consumedItems.add(found[0]);
                            usedIds.add(validId);
                        }
                        if (consumedItems.size() == needcount) break;
                    }
                }

                // 材料不足
                if (consumedItems.size() < needcount) {
                    if (aTempData.get(6) != null) {
                        pc.sendPackets(new S_SystemMessage("\\fY" + aTempData.get(6)));
                    }
                    return false;
                }


                // 成功取得材料，扣除道具
                boolean success = false;
                for (L1ItemInstance item : consumedItems) {
                    pc.getInventory().removeItem(item, 1);
                }

                if (_random.nextInt(100) + 1 <= (Integer) aTempData.get(4)) {
                    success = true;
                    if (aTempData.get(8) != null) {
                        pc.sendPackets(new S_SystemMessage("\\fT" + aTempData.get(8)));
                    }
                    L1ItemInstance item = ItemTable.get().createItem(giveItemGet);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    if (aTempData.get(9) != null) {
                        World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String) aTempData.get(9), pc.getName(), item.getLogName())));
                    }
                    pc.getQuest().set_step((Integer) aTempData.get(11), 0);
                } else {
                    if (aTempData.get(7) != null) {
                        pc.sendPackets(new S_CloseList(pc.getId()));
                        pc.sendPackets(new S_SystemMessage("\\fY" + aTempData.get(7)));
                    }
                }

                return success;
            }
        }
        return false;
    }

    private static void getData() {
        try (Connection con = DatabaseFactory.get().getConnection();
             Statement stat = con.createStatement();
             ResultSet rset = stat.executeQuery("SELECT * FROM w_npc天M合成")) {

            while (rset.next()) {
                ArrayList<Object> aReturn = new ArrayList<>();
                aReturn.add(0, rset.getInt("npcid"));
                aReturn.add(1, rset.getString("action"));
                aReturn.add(2, getArray(rset.getString("合成需求編號"), ",", 1));
                aReturn.add(3, rset.getInt("物品合成數量"));
                aReturn.add(4, rset.getInt("機率"));
                aReturn.add(5, getArray(rset.getString("獲取合成編號"), ",", 1));
                aReturn.add(6, rset.getString("物品不足Msg"));
                aReturn.add(7, rset.getString("失敗Msg"));
                aReturn.add(8, rset.getString("成功Msg"));
                aReturn.add(9, rset.getString("世界廣播"));
                aReturn.add(10, rset.getInt("失敗是否退還"));
                aReturn.add(11, rset.getInt("保底紀錄編號"));
                aReturn.add(12, rset.getInt("保底次數"));
                aData.add(aReturn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getArray(String s, String sToken, int iType) {
        StringTokenizer st = new StringTokenizer(s, sToken);
        int iSize = st.countTokens();
        if (iType == 1) {
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; ++i) {
                iReturn[i] = Integer.parseInt(st.nextToken());
            }
            return iReturn;
        } else if (iType == 2) {
            String[] sReturn = new String[iSize];
            for (int i = 0; i < iSize; ++i) {
                sReturn[i] = st.nextToken();
            }
            return sReturn;
        } else if (iType == 3) {
            String sReturn2 = null;
            for (int i = 0; i < iSize; ++i) {
                sReturn2 = st.nextToken();
            }
            return sReturn2;
        } else {
            return null;
        }
    }
}

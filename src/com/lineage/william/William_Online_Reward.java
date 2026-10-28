package com.lineage.william;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.utils.PerformanceTimer;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.server.datatables.ItemTable;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import java.util.logging.Logger;

public class William_Online_Reward
{
    private static Logger _log;
    private static final Log _logx;
    private static William_Online_Reward _instance;
    public static final HashMap<Integer, William_Online_Reward> _itemIdIndex;
    private static final ConcurrentHashMap<String, L1PcInstance> playerList;
    private int _dayWeek;
    private int _hour;
    private int _minute;
    private int _people;
    private int _give_item;
    private int _give_count_min;
    private int _give_count_max;
    private int _check_level;
    private int _check_mapId;
    
    static {
        William_Online_Reward._log = Logger.getLogger(William_Online_Reward.class.getName());
        _logx = LogFactory.getLog(William_Online_Reward.class.getName());
        _itemIdIndex = new HashMap();
        playerList = new ConcurrentHashMap();
    }
    
    public static William_Online_Reward getInstance() {
        if (William_Online_Reward._instance == null) {
            William_Online_Reward._instance = new William_Online_Reward();
        }
        return William_Online_Reward._instance;
    }
    
    public static void reload() {
        William_Online_Reward oldInstance = William_Online_Reward._instance;
        William_Online_Reward._instance = new William_Online_Reward();
        William_Online_Reward._itemIdIndex.clear();
    }
    
    private William_Online_Reward() {
        loadChackDrop();
    }
    
    private static void loadChackDrop() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_線上即時抽獎系統");
            rs = pstm.executeQuery();
            fillChackDrop(rs);
        }
        catch (SQLException e) {
            William_Online_Reward._log.log(Level.SEVERE, "error while creating 系統_線上即時抽獎系統 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private static void fillChackDrop(ResultSet rs) throws SQLException {
        PerformanceTimer timer = new PerformanceTimer();
        int d = 0;
        while (rs.next()) {
            int dayWeek = rs.getInt("星期幾");
            int hour = rs.getInt("幾點(24H)");
            int minute = rs.getInt("幾分");
            int people = rs.getInt("人數到達開獎");
            int give_item = rs.getInt("物品編號");
            int give_count_min = rs.getInt("物品最小數量");
            int give_count_max = rs.getInt("物品最大數量");
            int check_level = rs.getInt("等級資格");
            int check_mapId = rs.getInt("地圖");
            William_Online_Reward armor_upgrade = new William_Online_Reward(dayWeek, hour, minute, people, give_item, give_count_min, give_count_max, check_level, check_mapId);
            William_Online_Reward._itemIdIndex.put(d, armor_upgrade);
            ++d;
        }
        William_Online_Reward._logx.info("讀取->系統_線上即時抽獎系統資料數量: " + William_Online_Reward._itemIdIndex.size() + "(" + timer.get() + "ms)");
    }
    
    public William_Online_Reward getTemplate(int d) {
        return William_Online_Reward._itemIdIndex.get((Integer)d);
    }
    
    public William_Online_Reward[] getDissolveList() {
        return (William_Online_Reward[])William_Online_Reward._itemIdIndex.values().toArray(new William_Online_Reward[William_Online_Reward._itemIdIndex.size()]);
    }
    
    public static boolean isTime(int dw, int hh, int mm) {
        William_Online_Reward[] william_Online_Reward = getInstance().getDissolveList();
        William_Online_Reward william_Online_Reward2 = null;
        William_Online_Reward william_Online_Reward_ok = null;
        for (int i = 0; i < william_Online_Reward.length; ++i) {
            william_Online_Reward2 = getInstance().getTemplate(i);

            // 判斷星期是否符合
            boolean dayMatch = false;
            if (william_Online_Reward2.getDayWeek() == -1) { // -1 代表每天
                dayMatch = true;
            } else if (william_Online_Reward2.getDayWeek() == 7 && dw == 1) { // 星期日 (資料庫設定7, 系統傳入1)
                dayMatch = true;
            } else if (william_Online_Reward2.getDayWeek() == dw - 1) { // 星期一到六 (資料庫設定1~6, 系統傳入2~7)
                dayMatch = true;
            }

            // 如果星期和時間都符合，就設定為目標任務
            if (dayMatch) {
                if (william_Online_Reward2.getHour() == hh && william_Online_Reward2.getMinute() == mm) {
                    william_Online_Reward_ok = william_Online_Reward2;
                    break;
                }
            }
        }
        if (william_Online_Reward_ok != null) {
            if (World.get().getAllPlayers().size() > william_Online_Reward_ok.get_people()) {
                RandomGiftsOnline(william_Online_Reward_ok.get_people(), william_Online_Reward_ok.get_give_item(), william_Online_Reward_ok.get_give_count_max(), william_Online_Reward_ok.get_give_count_min(), william_Online_Reward_ok.get_check_level(), william_Online_Reward_ok.get_check_mapId());
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    public static L1PcInstance[] getAllPlayersToArray() {
        return (L1PcInstance[])William_Online_Reward.playerList.values().toArray(new L1PcInstance[William_Online_Reward.playerList.size()]);
    }
    
    public static void RandomGiftsOnline(int people, int itemId, int max, int min, int level, int mapid) {
        L1PcInstance[] player = World.get().getAllPlayersToArray();
        L1PcInstance pc = null;
        for (int i = 0; i < World.get().getAllPlayers().size(); ++i) {
            pc = player[i];
            // 如果 mapid 為 -1，則代表所有地圖；否則，比對地圖編號
            if (pc.getLevel() >= level && !pc.isGm() && (mapid == -1 || pc.getMapId() == mapid)) {
                William_Online_Reward.playerList.put(pc.getName(), pc);
            }
        }
        // ★ 保護機制：如果沒有任何玩家符合資格，則提前結束
        if (William_Online_Reward.playerList.isEmpty()) {
            _logx.info("線上抽獎：本次不符合觸發條件，抽獎取消。");
            World.get().broadcastPacketToAll(new S_SystemMessage("線上抽獎：本次不符合觸發條件，抽獎取消。"));
            return;
        }

        L1PcInstance[] player2 = getAllPlayersToArray();
        L1PcInstance pc2 = null;
        try {
            for (int j = 0; j < people; ++j) {
                World.get().broadcastPacketToAll(new S_SystemMessage("5秒後系統隨機抽取一位中獎者！"));
                World.get().broadcastPacketToAll(new S_PacketBoxGree("5秒後系統隨機抽取一位中獎者！"));
                Thread.sleep(1500L);
                World.get().broadcastPacketToAll(new S_SystemMessage("4秒後系統隨機抽取一位中獎者！"));
                World.get().broadcastPacketToAll(new S_PacketBoxGree("4秒後系統隨機抽取一位中獎者！"));
                Thread.sleep(1500L);
                World.get().broadcastPacketToAll(new S_SystemMessage("3秒後系統隨機抽取一位中獎者！"));
                World.get().broadcastPacketToAll(new S_PacketBoxGree("3秒後系統隨機抽取一位中獎者！"));
                Thread.sleep(1500L);
                World.get().broadcastPacketToAll(new S_SystemMessage("2秒後系統隨機抽取一位中獎者！"));
                World.get().broadcastPacketToAll(new S_PacketBoxGree("2秒後系統隨機抽取一位中獎者！"));
                Thread.sleep(1500L);
                World.get().broadcastPacketToAll(new S_SystemMessage("1秒後系統隨機抽取一位中獎者！"));
                World.get().broadcastPacketToAll(new S_PacketBoxGree("1秒後系統隨機抽取一位中獎者！"));
                Thread.sleep(1500L);
                pc2 = player2[RandomArrayList.getInt(William_Online_Reward.playerList.size())]; // 抽出幸運玩家
                int count = RandomArrayList.getInt(max) + min; // 決定獎勵數量
                String itemName = ItemTable.get().getTemplate(itemId).getName(); // 取得物品名稱

                // 組合新的公告訊息
                String message = "恭喜" + pc2.getName() + "成為本次得主【" + itemName + "x" + count + "】。";
                World.get().broadcastPacketToAll(new S_SystemMessage(message));
                World.get().broadcastPacketToAll(new S_PacketBoxGree(message));
                CreateNewItem.createNewItemRandomGifts(pc2, itemId, count); // 發放獎勵
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        William_Online_Reward.playerList.clear();
    }
    
    public William_Online_Reward(int dayWeek, int hour, int minute, int people, int give_item, int give_count_min, int give_count_max, int check_level, int check_mapId) {
        this._dayWeek = dayWeek;
        this._hour = hour;
        this._minute = minute;
        this._people = people;
        this._give_item = give_item;
        this._give_count_min = give_count_min;
        this._give_count_max = give_count_max;
        this._check_level = check_level;
        this._check_mapId = check_mapId;
    }
    
    public int getDayWeek() {
        return this._dayWeek;
    }
    
    public int getHour() {
        return this._hour;
    }
    
    public int getMinute() {
        return this._minute;
    }
    
    public int get_people() {
        return this._people;
    }
    
    public int get_give_item() {
        return this._give_item;
    }
    
    public int get_give_count_min() {
        return this._give_count_min;
    }
    
    public int get_give_count_max() {
        return this._give_count_max;
    }
    
    public int get_check_level() {
        return this._check_level;
    }
    
    public int get_check_mapId() {
        return this._check_mapId;
    }
}

package com.add.system;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ACardTable_doll
{
    private static final Log _log;
    private static ACardTable_doll _instance;
    private static final Map<Integer, ACard_doll> _cardIndex;
    
    static {
        _log = LogFactory.getLog(ACardTable_doll.class);
        _cardIndex = new HashMap();
    }
    
    public static ACardTable_doll get() {
        if (ACardTable_doll._instance == null) {
            ACardTable_doll._instance = new ACardTable_doll();
        }
        return ACardTable_doll._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i = 0;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `卡冊娃娃卡片能力登入`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("流水號");
                String msg1 = rs.getString("獲得能力時的訊息");
                String msg2 = rs.getString("出現顯示能力頁面名稱");
                String cmd = rs.getString("對話檔指令");
                int questid = rs.getInt("任務編號");
                int polyid = rs.getInt("娃娃道具編號");
                int polytime = rs.getInt("不使用");
                int polyitemid = rs.getInt("不使用");
                int polyitemcount = rs.getInt("不使用");
                int str = rs.getInt("力量");
                int dex = rs.getInt("敏捷");
                int con = rs.getInt("體質");
                int Int = rs.getInt("智力");
                int wis = rs.getInt("精神");
                int cha = rs.getInt("魅力");
                int ac = rs.getInt("防禦");
                int hp = rs.getInt("血量");
                int mp = rs.getInt("魔量");
                int hpr = rs.getInt("回血量");
                int mpr = rs.getInt("回魔量");
                int dmg = rs.getInt("近距離傷害");
                int bowdmg = rs.getInt("遠距離傷害");
                int hit = rs.getInt("近距離命中");
                int bowhit = rs.getInt("遠戰攻擊命中");
                int dmgr = rs.getInt("物理減免傷害");
                int mdmgr = rs.getInt("魔法減免傷害");
                int sp = rs.getInt("魔攻");
                int mhit = rs.getInt("魔法命中");
                int mr = rs.getInt("魔法防禦");
                int fire = rs.getInt("火屬性防禦");
                int water = rs.getInt("水屬性防禦");
                int wind = rs.getInt("風屬性防禦");
                int earth = rs.getInt("地屬性防禦");
                final String hgfx = rs.getString("黑色圖片");
         		final String cgfx = rs.getString("彩色圖片");
                ACard_doll card = new ACard_doll(id, msg1, msg2, cmd, questid, polyid, polytime, polyitemid, polyitemcount, str, dex, con, Int, wis, cha, ac, hp, mp, hpr, mpr, dmg, bowdmg, hit, bowhit, dmgr, mdmgr, sp, mhit, mr, fire, water, wind, earth,hgfx,cgfx);
                ACardTable_doll._cardIndex.put(id, card);
                ++i;
            }
        }
        catch (SQLException e) {
            ACardTable_doll._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
	
    
    public int ACardSize() {
        return ACardTable_doll._cardIndex.size();
    }
    
    public ACard_doll getCard(int id) {
        return ACardTable_doll._cardIndex.get((Integer)id);
    }
}

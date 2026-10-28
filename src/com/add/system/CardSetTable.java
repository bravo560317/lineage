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

public class CardSetTable
{
    private static final Log _log;
    private static CardSetTable _instance;
    private static final Map<Integer, CardPolySet> _cardIndex;
    
    static {
        _log = LogFactory.getLog(CardSetTable.class);
        _cardIndex = new HashMap();
    }
    
    public static CardSetTable get() {
        if (CardSetTable._instance == null) {
            CardSetTable._instance = new CardSetTable();
        }
        return CardSetTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int t = 0;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `卡冊變身卡能力組套卡`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("流水號");
                String msg1 = rs.getString("組合套卡名稱");
                String needisd = rs.getString("需求的變身卡編號").replaceAll(" ", "");
                String[] needi_tmp = needisd.split(",");
                int[] needids = new int[needi_tmp.length];
                for (int i = 0; i < needi_tmp.length; ++i) {
                    needids[i] = Integer.parseInt(needi_tmp[i]);
                }
                String needquest = rs.getString("需求的玩家任務編號").replaceAll(" ", "");
                String[] needq_tmp = needquest.split(",");
                int[] needquests = new int[needq_tmp.length];
                for (int k = 0; k < needq_tmp.length; ++k) {
                    needquests[k] = Integer.parseInt(needq_tmp[k]);
                }
                String needname = rs.getString("需求的變身卡名稱").replaceAll(" ", "");
                String[] needn_tmp = needname.split(",");
                String[] neednames = new String[needn_tmp.length];
                for (int j = 0; j < needn_tmp.length; ++j) {
                    neednames[j] = needn_tmp[j];
                }
                int questid = rs.getInt("任務紀錄編號");
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
                CardPolySet card = new CardPolySet(id, msg1, needids, needquests, neednames, questid, str, dex, con, Int, wis, cha, ac, hp, mp, hpr, mpr, dmg, bowdmg, hit, bowhit, dmgr, mdmgr, sp, mhit, mr, fire, water, wind, earth);
                CardSetTable._cardIndex.put(id, card);
                ++t;
            }
        }
        catch (SQLException e) {
            CardSetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CardSetTable._log.info("讀取->卡冊變身卡能力組套卡系統: " + t + "(" + timer.get() + "ms)");
    }
    
    public int CardCardSize() {
        return CardSetTable._cardIndex.size();
    }
    
    public CardPolySet getCard(int id) {
        return CardSetTable._cardIndex.get((Integer)id);
    }
}

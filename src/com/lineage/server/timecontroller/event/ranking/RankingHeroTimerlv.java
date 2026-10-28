package com.lineage.server.timecontroller.event.ranking;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class RankingHeroTimerlv extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static boolean _load;
    private static String[] _userNameAll;
    private static String[] _userNameC;
    private static String[] _userNameK;
    private static String[] _userNameE;
    private static String[] _userNameW;
    private static String[] _userNameD;
    private static String[] _userNameG;
    private static String[] _userNameI;
    private static String[] _userNameWarrior;
    private static Map<Integer, String> _top10;
    private static Map<Integer, String> _top3C;
    private static Map<Integer, String> _top3K;
    private static Map<Integer, String> _top3E;
    private static Map<Integer, String> _top3W;
    private static Map<Integer, String> _top3D;
    private static Map<Integer, String> _top3G;
    private static Map<Integer, String> _top3I;
    private static Map<Integer, String> _top3Warrior;
    
    static {
        _log = LogFactory.getLog(RankingHeroTimerlv.class);
        RankingHeroTimerlv._load = false;
        RankingHeroTimerlv._top10 = new HashMap();
        RankingHeroTimerlv._top3C = new HashMap();
        RankingHeroTimerlv._top3K = new HashMap();
        RankingHeroTimerlv._top3E = new HashMap();
        RankingHeroTimerlv._top3W = new HashMap();
        RankingHeroTimerlv._top3D = new HashMap();
        RankingHeroTimerlv._top3G = new HashMap();
        RankingHeroTimerlv._top3I = new HashMap();
        RankingHeroTimerlv._top3Warrior = new HashMap();
    }
    
    public void start() {
        restart();
        int timeMillis = 600000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 600000L, 600000L);
    }
    
    public static Map<Integer, String> get_top10() {
        return RankingHeroTimerlv._top10;
    }
    
    public static Map<Integer, String> get_top3C() {
        return RankingHeroTimerlv._top3C;
    }
    
    public static Map<Integer, String> get_top3K() {
        return RankingHeroTimerlv._top3K;
    }
    
    public static Map<Integer, String> get_top3E() {
        return RankingHeroTimerlv._top3E;
    }
    
    public static Map<Integer, String> get_top3W() {
        return RankingHeroTimerlv._top3W;
    }
    
    public static Map<Integer, String> get_top3D() {
        return RankingHeroTimerlv._top3D;
    }
    
    public static Map<Integer, String> get_top3G() {
        return RankingHeroTimerlv._top3G;
    }
    
    public static Map<Integer, String> get_top3I() {
        return RankingHeroTimerlv._top3I;
    }
    
    public static Map<Integer, String> get_top3Warrior() {
        return RankingHeroTimerlv._top3Warrior;
    }
    
    public static String[] userNameAll() {
        if (!RankingHeroTimerlv._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingHeroTimerlv._userNameAll[i].split(",");
            newUserName[i] = set[0];
            if (CharacterTable.doesCharNameExist(newUserName[i])) {
                RankingHeroTimerlv._top10.put(i, newUserName[i]);
            }
        }
        return newUserName;
    }
    
    public static String[] userNameC() {
        if (!RankingHeroTimerlv._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingHeroTimerlv._userNameC[i].split(",");
            newUserName[i] = set[0];
            if (i < 3 && CharacterTable.doesCharNameExist(newUserName[i])) {
                RankingHeroTimerlv._top3C.put(i, newUserName[i]);
            }
        }
        return newUserName;
    }
    
    public static String[] userNameK() {
        if (!RankingHeroTimerlv._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingHeroTimerlv._userNameK[i].split(",");
            newUserName[i] = set[0];
            if (i < 3 && CharacterTable.doesCharNameExist(newUserName[i])) {
                RankingHeroTimerlv._top3K.put(i, newUserName[i]);
            }
        }
        return newUserName;
    }
    
    public static String[] userNameE() {
        if (!RankingHeroTimerlv._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingHeroTimerlv._userNameE[i].split(",");
            newUserName[i] = set[0];
            if (i < 3 && CharacterTable.doesCharNameExist(newUserName[i])) {
                RankingHeroTimerlv._top3E.put(i, newUserName[i]);
            }
        }
        return newUserName;
    }
    
    public static String[] userNameW() {
        if (!RankingHeroTimerlv._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingHeroTimerlv._userNameW[i].split(",");
            newUserName[i] = set[0];
            if (i < 3 && CharacterTable.doesCharNameExist(newUserName[i])) {
                RankingHeroTimerlv._top3W.put(i, newUserName[i]);
            }
        }
        return newUserName;
    }
    
    public static String[] userNameD() {
        if (!RankingHeroTimerlv._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingHeroTimerlv._userNameD[i].split(",");
            newUserName[i] = set[0];
            if (i < 3 && CharacterTable.doesCharNameExist(newUserName[i])) {
                RankingHeroTimerlv._top3D.put(i, newUserName[i]);
            }
        }
        return newUserName;
    }
    
    public static String[] userNameG() {
        if (!RankingHeroTimerlv._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingHeroTimerlv._userNameG[i].split(",");
            newUserName[i] = set[0];
            if (i < 3 && CharacterTable.doesCharNameExist(newUserName[i])) {
                RankingHeroTimerlv._top3G.put(i, newUserName[i]);
            }
        }
        return newUserName;
    }
    
    public static String[] userNameI() {
        if (!RankingHeroTimerlv._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingHeroTimerlv._userNameI[i].split(",");
            newUserName[i] = set[0];
            if (i < 3 && CharacterTable.doesCharNameExist(newUserName[i])) {
                RankingHeroTimerlv._top3I.put(i, newUserName[i]);
            }
        }
        return newUserName;
    }
    
    public static String[] userNameWarrior() {
        if (!RankingHeroTimerlv._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingHeroTimerlv._userNameWarrior[i].split(",");
            newUserName[i] = set[0];
            if (i < 3 && CharacterTable.doesCharNameExist(newUserName[i])) {
                RankingHeroTimerlv._top3Warrior.put(i, newUserName[i]);
            }
        }
        return newUserName;
    }
    
    @Override
    public void run() {
        try {
            load();
        }
        catch (Exception e) {
            RankingHeroTimerlv._log.error("英雄風雲榜時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            RankingHeroTimerlv heroTimer = new RankingHeroTimerlv();
            heroTimer.start();
        }
    }
    
    public static void load() {
        try {
            RankingHeroTimerlv._load = true;
            restart();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("SELECT * FROM `characters` WHERE `logpcpower` >= 0 and `level` > 0 and `AccessLevel` <= 0 ORDER BY `logpcpower` DESC,`Exp` DESC");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String char_name = rs.getString("char_name");
                    int level = rs.getInt("level");
                    int type = rs.getInt("Type");
                    if (type == 0) {
                        int i = 0;
                        int n = 10;
                        while (i < n) {
                            if (RankingHeroTimerlv._userNameC[i].equals(" ")) {
                                StringBuffer sbr = new StringBuffer().append(char_name);
                                sbr.append(" :Lv.").append(level).append("");
                                RankingHeroTimerlv._userNameC[i] = sbr.toString();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else if (type == 1) {
                        int i = 0;
                        int n = 10;
                        while (i < n) {
                            if (RankingHeroTimerlv._userNameK[i].equals(" ")) {
                                StringBuffer sbr = new StringBuffer().append(char_name);
                                sbr.append(" :Lv.").append(level).append("");
                                RankingHeroTimerlv._userNameK[i] = sbr.toString();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else if (type == 2) {
                        int i = 0;
                        int n = 10;
                        while (i < n) {
                            if (RankingHeroTimerlv._userNameE[i].equals(" ")) {
                                StringBuffer sbr = new StringBuffer().append(char_name);
                                sbr.append(" :Lv.").append(level).append("");
                                RankingHeroTimerlv._userNameE[i] = sbr.toString();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else if (type == 3) {
                        int i = 0;
                        int n = 10;
                        while (i < n) {
                            if (RankingHeroTimerlv._userNameW[i].equals(" ")) {
                                StringBuffer sbr = new StringBuffer().append(char_name);
                                sbr.append(" :Lv.").append(level).append("");
                                RankingHeroTimerlv._userNameW[i] = sbr.toString();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else if (type == 4) {
                        int i = 0;
                        int n = 10;
                        while (i < n) {
                            if (RankingHeroTimerlv._userNameD[i].equals(" ")) {
                                StringBuffer sbr = new StringBuffer().append(char_name);
                                sbr.append(" :Lv.").append(level).append("");
                                RankingHeroTimerlv._userNameD[i] = sbr.toString();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else if (type == 5) {
                        int i = 0;
                        int n = 10;
                        while (i < n) {
                            if (RankingHeroTimerlv._userNameG[i].equals(" ")) {
                                StringBuffer sbr = new StringBuffer().append(char_name);
                                sbr.append(" :Lv.").append(level).append("");
                                RankingHeroTimerlv._userNameG[i] = sbr.toString();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else if (type == 6) {
                        int i = 0;
                        int n = 10;
                        while (i < n) {
                            if (RankingHeroTimerlv._userNameI[i].equals(" ")) {
                                StringBuffer sbr = new StringBuffer().append(char_name);
                                sbr.append(" :Lv.").append(level).append("");
                                RankingHeroTimerlv._userNameI[i] = sbr.toString();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else if (type == 7) {
                        int i = 0;
                        int n = 10;
                        while (i < n) {
                            if (RankingHeroTimerlv._userNameWarrior[i].equals(" ")) {
                                StringBuffer sbr = new StringBuffer().append(char_name);
                                sbr.append(" :Lv.").append(level).append("");
                                RankingHeroTimerlv._userNameWarrior[i] = sbr.toString();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    int i = 0;
                    int n = 10;
                    while (i < n) {
                        if (RankingHeroTimerlv._userNameAll[i].equals(" ")) {
                            StringBuffer sbr = new StringBuffer().append(char_name);
                            sbr.append(" :Lv.").append(level).append("");
                            RankingHeroTimerlv._userNameAll[i] = sbr.toString();
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                    Thread.sleep(1L);
                }
            }
            catch (SQLException e) {
                RankingHeroTimerlv._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(rs);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
        }
        catch (Exception e2) {
            RankingHeroTimerlv._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    private static void restart() {
        RankingHeroTimerlv._userNameAll = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
        RankingHeroTimerlv._userNameC = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
        RankingHeroTimerlv._userNameK = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
        RankingHeroTimerlv._userNameE = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
        RankingHeroTimerlv._userNameW = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
        RankingHeroTimerlv._userNameD = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
        RankingHeroTimerlv._userNameG = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
        RankingHeroTimerlv._userNameI = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
        RankingHeroTimerlv._userNameWarrior = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }
}

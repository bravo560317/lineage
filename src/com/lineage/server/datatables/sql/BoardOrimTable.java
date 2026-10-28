package com.lineage.server.datatables.sql;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.lock.DwarfReading;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import java.sql.Timestamp;
import java.util.Calendar;
import com.lineage.server.utils.PerformanceTimer;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Rank;
import java.util.List;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.BoardOrimStorage;

public class BoardOrimTable implements BoardOrimStorage
{
    private static final Log _log;
    private static final List<L1Rank> _totalList;
    private static int _maxid;
    
    static {
        _log = LogFactory.getLog(BoardOrimTable.class);
        _totalList = new CopyOnWriteArrayList();
        BoardOrimTable._maxid = 0;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(5) == 28) {
            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
            List<L1Rank> _checkList = new CopyOnWriteArrayList();
            try {
                co = DatabaseFactory.get().getConnection();
                ps = co.prepareStatement("SELECT * FROM `server_board_orim` WHERE `time_order`<=? ORDER BY `score` DESC");
                ps.setTimestamp(1, timestamp);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String[] partyMember = rs.getString("partyMember").split(",");
                    List<String> checkList = new CopyOnWriteArrayList();
                    String[] array;
                    for (int length = (array = partyMember).length, l = 0; l < length; ++l) {
                        String str = array[l];
                        checkList.add(str);
                    }
                    String leader = rs.getString("leader");
                    int score = rs.getInt("score");
                    L1Rank rank = new L1Rank(leader, checkList, score);
                    _checkList.add(rank);
                }
                List<String> _overList = new CopyOnWriteArrayList();
                for (int i = 0, r = 5, n = _checkList.size(); i < r && i < n; ++i) {
                    int itemCount = 0;
                    if (i == 0) {
                        itemCount = 3;
                    }
                    else if (i == 1 || i == 2) {
                        itemCount = 2;
                    }
                    else if (i == 3 || i == 4) {
                        itemCount = 1;
                    }
                    L1Rank rank2 = _checkList.get(i);
                    for (int j = 0, k = rank2.getMemberSize(); j < k; ++j) {
                        String value = null;
                        if (!_overList.contains(rank2.getPartyLeader())) {
                            value = rank2.getPartyLeader();
                        }
                        else {
                            for (String memberName : rank2.getPartyMember()) {
                                if (!_overList.contains(memberName)) {
                                    value = memberName;
                                }
                            }
                        }
                        if (value != null && CharObjidTable.get().charObjid(value) > 0) {
                            try {
                                L1ItemInstance item = ItemTable.get().createItem(56256);
                                item.setCount((long)itemCount);
                                DwarfReading.get().insertItem(CharacterTable.getAccountName(value), item);
                            }
                            catch (Exception e) {
                                BoardOrimTable._log.error(e.getLocalizedMessage(), e);
                            }
                            _overList.add(value);
                        }
                    }
                }
                _checkList.clear();
                _overList.clear();
                ps = co.prepareStatement("DELETE FROM `server_board_orim` WHERE `time_order`<=?");
                ps.setTimestamp(1, timestamp);
                ps.execute();
            }
            catch (SQLException e2) {
                BoardOrimTable._log.error(e2.getLocalizedMessage(), e2);
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(co);
            }
        }
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `server_board_orim` ORDER BY `score` DESC");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                if (id > BoardOrimTable._maxid) {
                    BoardOrimTable._maxid = id;
                }
                String[] partyMember2 = rs.getString("partyMember").split(",");
                List<String> checkList2 = new CopyOnWriteArrayList();
                String[] array2;
                for (int length2 = (array2 = partyMember2).length, n2 = 0; n2 < length2; ++n2) {
                    String str2 = array2[n2];
                    checkList2.add(str2);
                }
                String leader2 = rs.getString("leader");
                int score2 = rs.getInt("score");
                for (L1Rank rank : BoardOrimTable._totalList) {
                    if (leader2.equals(rank.getPartyLeader()) || rank.getPartyMember().contains(leader2)) {
                        leader2 = null;
                        break;
                    }
                }
                for (String check_member : checkList2) {
                    for (L1Rank rank3 : BoardOrimTable._totalList) {
                        if (check_member.equals(rank3.getPartyLeader()) || rank3.getPartyMember().contains(check_member)) {
                            checkList2.remove(check_member);
                            break;
                        }
                    }
                }
                L1Rank rank4 = new L1Rank(leader2, checkList2, score2);
                BoardOrimTable._totalList.add(rank4);
            }
        }
        catch (SQLException e3) {
            BoardOrimTable._log.error(e3.getLocalizedMessage(), e3);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        BoardOrimTable._log.info("載入歐林佈告欄資料數量: " + BoardOrimTable._totalList.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public List<L1Rank> getTotalList() {
        return BoardOrimTable._totalList;
    }
    
    @Override
    public int writeTopic(int score, String leader, List<String> partyMember) {
        int length = partyMember.size();
        if (length <= 0) {
            return 0;
        }
        else {
            StringBuilder sbr = new StringBuilder();
            int i = 0;
            while (true) {
                sbr.append(partyMember.get(i));
                if (++i >= length) {
                    break;
                }
                else {
                    sbr.append(",");
                }
            }
            Connection co = null;
            PreparedStatement ps = null;
            try {
                co = DatabaseFactory.get().getConnection();
                ps = co.prepareStatement("INSERT INTO `server_board_orim` SET `id`=?,`score`=?,`leader`=?,`partyMember`=?,`time_order`=?");
                ps.setInt(1, ++BoardOrimTable._maxid);
                ps.setInt(2, score);
                ps.setString(3, leader);
                ps.setString(4, sbr.toString());
                ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                ps.execute();
            }
            catch (SQLException e) {
                BoardOrimTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(co);
            }
            for (L1Rank rank : BoardOrimTable._totalList) {
                if (leader.equals(rank.getPartyLeader()) || rank.getPartyMember().contains(leader)) {
                    leader = null;
                    break;
                }
            }
            for (String check_member : partyMember) {
                for (L1Rank rank2 : BoardOrimTable._totalList) {
                    if (check_member.equals(rank2.getPartyLeader()) || rank2.getPartyMember().contains(check_member)) {
                        partyMember.remove(check_member);
                        break;
                    }
                }
            }
            L1Rank rank = new L1Rank(leader, partyMember, score);
            int index;
            int size = index = BoardOrimTable._totalList.size();
            i = 0;
            while (i < size) {
                if (score > (BoardOrimTable._totalList.get(i)).getScore()) {
                    index = i;
                    break;
                }
                else {
                    ++i;
                }
            }
            BoardOrimTable._totalList.add(index, rank);
            return index + 1;
        }
    }
    
    @Override
    public void renewPcName(String oriName, String newName) {
        this.updateLeaderName(oriName, newName);
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `server_board_orim`");
            rs = ps.executeQuery();
            while (rs.next()) {
                String partyMember = rs.getString("partyMember");
                boolean checkOkay = false;
                String[] split;
                int length = (split = partyMember.split(",")).length;
                int i = 0;
                while (i < length) {
                    String str = split[i];
                    if (oriName.equals(str)) {
                        checkOkay = true;
                        break;
                    }
                    else {
                        ++i;
                    }
                }
                if (checkOkay) {
                    this.updateMemberName(partyMember, partyMember.replace(oriName, newName));
                }
            }
        }
        catch (SQLException e) {
            BoardOrimTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        for (L1Rank rank : BoardOrimTable._totalList) {
            if (oriName.equals(rank.getPartyLeader())) {
                rank.setPartyLeader(newName);
            }
            else {
                int index = rank.getPartyMember().indexOf(oriName);
                if (index >= 0) {
                    rank.getPartyMember().set(index, newName);
                }
                else {
                    continue;
                }
            }
        }
    }
    
    private final void updateLeaderName(String oriName, String newName) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("UPDATE `server_board_orim` SET `leader`=? WHERE `leader`=?");
            ps.setString(1, newName);
            ps.setString(2, oriName);
            ps.execute();
        }
        catch (SQLException e) {
            BoardOrimTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    private final void updateMemberName(String oriName, String newName) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("UPDATE `server_board_orim` SET `partyMember`=? WHERE `partyMember`=?");
            ps.setString(1, newName);
            ps.setString(2, oriName);
            ps.execute();
        }
        catch (SQLException e) {
            BoardOrimTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
}

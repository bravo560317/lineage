package com.lineage.server.model;

import com.lineage.server.world.WorldClan;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class L1ClanMatching
{
    private static final Log _log;
    private static L1ClanMatching _instance;
    private ArrayList<L1ClanMatching.ClanMatchingList> _list;
    
    static {
        _log = LogFactory.getLog(L1ClanMatching.class);
    }
    
    public L1ClanMatching() {
        this._list = new ArrayList();
    }
    
    public static L1ClanMatching getInstance() {
        if (L1ClanMatching._instance == null) {
            L1ClanMatching._instance = new L1ClanMatching();
        }
        return L1ClanMatching._instance;
    }
    
    public void writeClanMatching(String clanname, String text, int htype) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO clan_matching_list SET clanname = ?, text = ?, type = ?");
            pstm.setString(1, clanname);
            pstm.setString(2, text);
            pstm.setInt(3, htype);
            L1ClanMatching.ClanMatchingList CML = new L1ClanMatching.ClanMatchingList(clanname, text, htype);
            this.addMatching(CML);
            pstm.execute();
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void updateClanMatching(String clanname, String text, int htype) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE clan_matching_list SET text = ?, type = ? WHERE clanname = ?");
            pstm.setString(1, text);
            pstm.setInt(2, htype);
            pstm.setString(3, clanname);
            L1ClanMatching.ClanMatchingList CML = this.getClanMatchingList(clanname);
            CML._text = text;
            CML._type = htype;
            pstm.execute();
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteClanMatching(L1Clan clan) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_matching_list WHERE clanname=?");
            pstm.setString(1, clan.getClanName());
            pstm.execute();
            this.removeMatching(clan.getClanName());
            L1PcInstance[] onlineClanMember;
            for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                L1PcInstance clanuser = onlineClanMember[i];
                switch (clanuser.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        clanuser.getInviteList().clear();
                        break;
                    }
                }
            }
            for (L1PcInstance player : World.get().getAllPlayers()) {
                if (player.getClanid() == 0 && player.getCMAList().contains(clan.getClanName())) {
                    player.removeCMAList(clan.getClanName());
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteClanMatching(String clanname) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_matching_list WHERE clanname=?");
            pstm.setString(1, clanname);
            pstm.execute();
            this.removeMatching(clanname);
            for (L1PcInstance player : World.get().getAllPlayers()) {
                if (player.getClanid() == 0 && player.getCMAList().contains(clanname)) {
                    player.removeCMAList(clanname);
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteClanMatching(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_matching_list WHERE clanname=?");
            pstm.setString(1, pc.getClanname());
            pstm.execute();
            this.removeMatching(pc.getClanname());
            L1PcInstance[] onlineClanMember;
            for (int length = (onlineClanMember = pc.getClan().getOnlineClanMember()).length, i = 0; i < length; ++i) {
                L1PcInstance clanuser = onlineClanMember[i];
                switch (clanuser.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        clanuser.getInviteList().clear();
                        break;
                    }
                }
            }
            for (L1PcInstance player : World.get().getAllPlayers()) {
                if (player.getClanid() == 0 && player.getCMAList().contains(pc.getClanname())) {
                    player.removeCMAList(pc.getClanname());
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void loadClanMatching() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String clanname = null;
        String text = null;
        int type = 0;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM clan_matching_list");
            rs = pstm.executeQuery();
            while (rs.next()) {
                clanname = rs.getString("clanname");
                text = rs.getString("text");
                type = rs.getInt("type");
                L1ClanMatching.ClanMatchingList CML = new L1ClanMatching.ClanMatchingList(clanname, text, type);
                this.addMatching(CML);
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void writeClanMatchingApcList_User(L1PcInstance pc, L1Clan clan) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO clan_matching_apclist SET pc_name=?, pc_objid=?, clan_name=?, clan_id=?");
            pstm.setString(1, pc.getName());
            pstm.setInt(2, pc.getId());
            pstm.setString(3, clan.getClanName());
            pstm.setInt(4, clan.getClanId());
            pstm.execute();
            pc.addCMAList(clan.getClanName());
            L1PcInstance[] onlineClanMember;
            for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                L1PcInstance clanuser = onlineClanMember[i];
                switch (clanuser.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        clanuser.addInviteList(pc.getName());
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void loadClanMatchingApcList_User(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String clanname = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM clan_matching_apclist WHERE pc_name = ?");
            pstm.setString(1, pc.getName());
            rs = pstm.executeQuery();
            while (rs.next()) {
                clanname = rs.getString("clan_name");
                pc.addCMAList(clanname);
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void loadClanMatchingApcList_Crown(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String username = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM clan_matching_apclist WHERE clan_name = ?");
            pstm.setString(1, pc.getClanname());
            rs = pstm.executeQuery();
            while (rs.next()) {
                username = rs.getString("pc_name");
                pc.addInviteList(username);
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteClanMatchingApcList(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_matching_apclist WHERE pc_name=?");
            pstm.setString(1, pc.getName());
            pstm.execute();
            pc.getCMAList().clear();
            L1PcInstance[] onlineClanMember;
            for (int length = (onlineClanMember = pc.getClan().getOnlineClanMember()).length, i = 0; i < length; ++i) {
                L1PcInstance clanuser = onlineClanMember[i];
                switch (clanuser.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        clanuser.removeInviteList(pc.getName());
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteClanMatchingApcList(L1Clan clan) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_matching_apclist WHERE clan_name=?");
            pstm.setString(1, clan.getClanName());
            pstm.execute();
            L1PcInstance[] onlineClanMember;
            for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                L1PcInstance clanuser = onlineClanMember[i];
                switch (clanuser.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        clanuser.getInviteList().clear();
                        break;
                    }
                }
            }
            for (L1PcInstance player : World.get().getAllPlayers()) {
                if (player.getClanid() == 0 && player.getCMAList().contains(clan.getClanName())) {
                    player.removeCMAList(clan.getClanName());
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteClanMatchingApcList(String clanname) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_matching_apclist WHERE clan_name=?");
            pstm.setString(1, clanname);
            pstm.execute();
            L1Clan clan = WorldClan.get().getClan(clanname);
            L1PcInstance[] onlineClanMember;
            for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                L1PcInstance clanuser = onlineClanMember[i];
                switch (clanuser.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        clanuser.getInviteList().clear();
                        break;
                    }
                }
            }
            for (L1PcInstance player : World.get().getAllPlayers()) {
                if (player.getClanid() == 0 && player.getCMAList().contains(clanname)) {
                    player.removeCMAList(clanname);
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteClanMatchingApcList(L1Clan clan, String charname) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_matching_apclist WHERE clan_id=? AND clan_name=?");
            pstm.setInt(1, clan.getClanId());
            pstm.setString(2, clan.getClanName());
            pstm.execute();
            L1PcInstance[] onlineClanMember;
            for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                L1PcInstance clanuser = onlineClanMember[i];
                switch (clanuser.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        clanuser.removeInviteList(charname);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteClanMatchingApcList(L1PcInstance pc, int objid, L1Clan clan) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pc.removeCMAList(clan.getClanName());
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_matching_apclist WHERE pc_objid=? AND clan_name=?");
            pstm.setInt(1, objid);
            pstm.setString(2, clan.getClanName());
            pstm.execute();
            String pcname = pc.getName();
            L1PcInstance[] onlineClanMember;
            for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                L1PcInstance clanuser = onlineClanMember[i];
                switch (clanuser.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        clanuser.removeInviteList(pcname);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteClanMatchingApcList(L1PcInstance pc, L1Clan clan) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_matching_apclist WHERE pc_name=? AND clan_name=?");
            pstm.setString(1, pc.getName());
            pstm.setString(2, clan.getClanName());
            pstm.execute();
            pc.removeCMAList(clan.getClanName());
            L1PcInstance[] onlineClanMember;
            for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                L1PcInstance clanuser = onlineClanMember[i];
                switch (clanuser.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        clanuser.removeInviteList(pc.getName());
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            L1ClanMatching._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void addMatching(L1ClanMatching.ClanMatchingList list) {
        if (this._list.contains(list)) {
            return;
        }
        else {
            this._list.add(list);
            return;
        }
    }
    
    public void removeMatching(String clanname) {
        if (!this.isClanMatchingList(clanname)) {
            return;
        }
        else {
            this._list.remove(this.getClanMatchingList(clanname));
            return;
        }
    }
    
    public ArrayList<L1ClanMatching.ClanMatchingList> getMatchingList() {
        return this._list;
    }
    
    public boolean isClanMatchingList(String clanname) {
        int i = 0;
        while (i < this._list.size()) {
            if ((this._list.get(i))._clanname.equalsIgnoreCase(clanname)) {
                return true;
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    public L1ClanMatching.ClanMatchingList getClanMatchingList(String clanname) {
        L1ClanMatching.ClanMatchingList CML = null;
        int i = 0;
        while (i < this._list.size()) {
            if ((this._list.get(i))._clanname.equalsIgnoreCase(clanname)) {
                CML = this._list.get(i);
                break;
            }
            else {
                ++i;
            }
        }
        return CML;
    }
    
    public static class ClanMatchingList
    {
        public String _clanname;
        public String _text;
        public int _type;
        
        public ClanMatchingList(String clanname, String text, int type) {
            this._clanname = null;
            this._text = null;
            this._type = 0;
            this._clanname = clanname;
            this._text = text;
            this._type = type;
        }
    }
}

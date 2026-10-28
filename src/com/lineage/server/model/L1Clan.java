package com.lineage.server.model;

import com.lineage.server.world.WorldClan;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.serverpackets.ServerBasePacket;
import java.util.Iterator;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.LogFactory;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.Log;

public class L1Clan
{
    private static final Log _log;
    private final Lock _lock;
    public static final int CLAN_RANK_LEAGUE_PUBLIC = 2;
    public static final int CLAN_RANK_LEAGUE_VICEPRINCE = 3;
    public static final int CLAN_RANK_LEAGUE_PRINCE = 4;
    public static final int CLAN_RANK_LEAGUE_PROBATION = 5;
    public static final int CLAN_RANK_LEAGUE_GUARDIAN = 6;
    public static final int CLAN_RANK_PUBLIC = 7;
    public static final int CLAN_RANK_PROBATION = 8;
    public static final int CLAN_RANK_GUARDIAN = 9;
    public static final int CLAN_RANK_PRINCE = 10;
    private int _clanId;
    private String _clanName;
    private int _leaderId;
    private String _leaderName;
    private int _castleId;
    private int _houseId;
    private int _warehouse;
    private int _ClanSkillId;
    private int _ClanSkillLv;
    private int _clanadena;
    private final L1DwarfForClanInventory _dwarfForClan;
    private final ArrayList<String> _membersNameList;
    private int _emblemId;
    String[] _rank;
    private boolean _clanskill;
    private Timestamp _skilltime;
    private int _maxuser;
    private Timestamp _foundDate;
    private String _announcement;
    private int _emblemStatus;
    private int _clanLevel;
    private int _clanContribution;
    private ArrayList<Integer> allianceList;
    
    static {
        _log = LogFactory.getLog(L1Clan.class);
    }
    
    public L1Clan() {
        this._lock = new ReentrantLock(true);
        this._warehouse = 0;
        this._dwarfForClan = new L1DwarfForClanInventory(this);
        this._membersNameList = new ArrayList();
        this._rank = new String[] { "", "", "[聯盟一般]", "[聯盟副君主]", "[聯盟君主]", "[聯盟修習騎士]", "[聯盟守護騎士]", "[一般]", "[修習騎士]", "[守護騎士]", "[君主]" };
        this._clanskill = false;
        this._skilltime = null;
        this.allianceList = new ArrayList();
    }
    
    public int getClanId() {
        return this._clanId;
    }
    
    public void setClanId(int clan_id) {
        this._clanId = clan_id;
    }
    
    public String getClanName() {
        return this._clanName;
    }
    
    public void setClanName(String clan_name) {
        this._clanName = clan_name;
    }
    
    public int getLeaderId() {
        return this._leaderId;
    }
    
    public void setLeaderId(int leader_id) {
        this._leaderId = leader_id;
    }
    
    public String getLeaderName() {
        return this._leaderName;
    }
    
    public void setLeaderName(String leader_name) {
        this._leaderName = leader_name;
    }
    
    public int getCastleId() {
        return this._castleId;
    }
    
    public void setCastleId(int hasCastle) {
        this._castleId = hasCastle;
    }
    
    public int getHouseId() {
        return this._houseId;
    }
    
    public void setHouseId(int hasHideout) {
        this._houseId = hasHideout;
    }
    
    public void CheckClan_Exp20(L1PcInstance leavePc) {
        L1PcInstance[] clanMembers = this.getOnlineClanMember();
        int OnlineMemberSize = this.getOnlineClanMemberSize();
        if (leavePc != null && leavePc.hasSkillEffect(8070)) {
            leavePc.removeNoTimerSkillEffect(8070);
            leavePc.add_exp(-ConfigOther.clancountexp);
            leavePc.sendPackets(new S_PacketBox(165, 0));
        }
        if (OnlineMemberSize >= ConfigOther.clancount) {
            L1PcInstance[] array;
            for (int length = (array = clanMembers).length, i = 0; i < length; ++i) {
                L1PcInstance clanMember = array[i];
                if (!clanMember.hasSkillEffect(8070)) {
                    clanMember.setSkillEffect(8070, 0);
                    clanMember.add_exp(ConfigOther.clancountexp);
                    clanMember.sendPackets(new S_PacketBox(165, 1));
                }
            }
        }
        else {
            L1PcInstance[] array2;
            for (int length2 = (array2 = clanMembers).length, j = 0; j < length2; ++j) {
                L1PcInstance clanMember = array2[j];
                if (clanMember.hasSkillEffect(8070)) {
                    clanMember.removeNoTimerSkillEffect(8070);
                    clanMember.add_exp(-ConfigOther.clancountexp);
                    clanMember.sendPackets(new S_PacketBox(165, 0));
                }
            }
        }
    }
    
    public void addMemberName(String member_name) {
        this._lock.lock();
        try {
            if (!this._membersNameList.contains(member_name)) {
                this._membersNameList.add(member_name);
            }
            this.CheckClan_Exp20(null);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void delMemberName(String member_name) {
        this._lock.lock();
        try {
            if (this._membersNameList.contains(member_name)) {
                this._membersNameList.remove(member_name);
            }
            L1PcInstance leavePc = World.get().getPlayer(member_name);
            this.CheckClan_Exp20(leavePc);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public int getOnlineClanMemberSize() {
        int count = 0;
        try {
            for (String name : this._membersNameList) {
                L1PcInstance pc = World.get().getPlayer(name);
                if (pc != null) {
                    ++count;
                }
            }
        }
        catch (Exception e) {
            L1Clan._log.error(e.getLocalizedMessage(), e);
        }
        return count;
    }
    
    public int getAllMembersSize() {
        try {
            return this._membersNameList.size();
        }
        catch (Exception e) {
            L1Clan._log.error(e.getLocalizedMessage(), e);
            return 0;
        }
    }
    
    public void sendPacketsAll(ServerBasePacket packet) {
        try {
            Object[] array;
            for (int length = (array = this._membersNameList.toArray()).length, i = 0; i < length; ++i) {
                Object nameobj = array[i];
                String name = (String)nameobj;
                L1PcInstance pc = World.get().getPlayer(name);
                if (pc != null) {
                    pc.sendPackets(packet);
                }
            }
        }
        catch (Exception e) {
            L1Clan._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public L1PcInstance[] getOnlineClanMember() {
        ArrayList<String> temp = new ArrayList();
        ArrayList<L1PcInstance> onlineMembers = new ArrayList();
        try {
            temp.addAll(this._membersNameList);
            for (String name : temp) {
                L1PcInstance pc = World.get().getPlayer(name);
                if (pc != null && !onlineMembers.contains(pc)) {
                    onlineMembers.add(pc);
                }
            }
        }
        catch (Exception e) {
            L1Clan._log.error(e.getLocalizedMessage(), e);
        }
        return (L1PcInstance[])onlineMembers.toArray(new L1PcInstance[onlineMembers.size()]);
    }
    
    public StringBuilder getOnlineMembersFP() {
        ArrayList<String> temp = new ArrayList();
        StringBuilder result = new StringBuilder();
        try {
            temp.addAll(this._membersNameList);
            for (String name : temp) {
                L1PcInstance pc = World.get().getPlayer(name);
                if (pc != null) {
                    result.append(name + " ");
                }
            }
        }
        catch (Exception e) {
            L1Clan._log.error(e.getLocalizedMessage(), e);
        }
        return result;
    }
    
    public StringBuilder getAllMembersFP() {
        ArrayList<String> temp = new ArrayList();
        StringBuilder result = new StringBuilder();
        try {
            temp.addAll(this._membersNameList);
            for (String name : temp) {
                result.append(name + " ");
            }
        }
        catch (Exception e) {
            L1Clan._log.error(e.getLocalizedMessage(), e);
        }
        return result;
    }
    
    public int getEmblemId() {
        return this._emblemId;
    }
    
    public void setEmblemId(int i) {
        this._emblemId = i;
    }
    
    public StringBuilder getOnlineMembersFPWithRank() {
        ArrayList<String> temp = new ArrayList();
        StringBuilder result = new StringBuilder();
        try {
            temp.addAll(this._membersNameList);
            for (String name : temp) {
                L1PcInstance pc = World.get().getPlayer(name);
                if (pc != null) {
                    result.append(name + this.getRankString(pc) + " ");
                }
            }
        }
        catch (Exception e) {
            L1Clan._log.error(e.getLocalizedMessage(), e);
        }
        return result;
    }
    
    public StringBuilder getAllMembersFPWithRank() {
        ArrayList<String> temp = new ArrayList();
        StringBuilder result = new StringBuilder();
        try {
            temp.addAll(this._membersNameList);
            for (String name : temp) {
                L1PcInstance pc = CharacterTable.get().restoreCharacter(name);
                if (pc != null) {
                    result.append(name + " ");
                }
            }
        }
        catch (Exception e) {
            L1Clan._log.error(e.getLocalizedMessage(), e);
        }
        return result;
    }
    
    private String getRankString(L1PcInstance pc) {
        if (pc != null && pc.getClanRank() > 0) {
            return this._rank[pc.getClanRank()];
        }
        else {
            return "";
        }
    }
    
    public String[] getAllMembers() {
        return (String[])this._membersNameList.toArray(new String[this._membersNameList.size()]);
    }
    
    public L1DwarfForClanInventory getDwarfForClanInventory() {
        return this._dwarfForClan;
    }
    
    public int getWarehouseUsingChar() {
        return this._warehouse;
    }
    
    public void setWarehouseUsingChar(int objid) {
        this._warehouse = objid;
    }
    
    public void set_clanskill(boolean boolean1) {
        this._clanskill = boolean1;
    }
    
    public boolean isClanskill() {
        return this._clanskill;
    }
    
    public void set_skilltime(Timestamp skilltime) {
        this._skilltime = skilltime;
    }
    
    public Timestamp get_skilltime() {
        return this._skilltime;
    }
    
    public Timestamp getFoundDate() {
        return this._foundDate;
    }
    
    public void setFoundDate(Timestamp _foundDate) {
        this._foundDate = _foundDate;
    }
    
    public int getOnlineMaxUser() {
        return this._maxuser;
    }
    
    public void setOnlineMaxUser(int i) {
        this._maxuser = i;
    }
    
    public String getAnnouncement() {
        return this._announcement;
    }
    
    public void setAnnouncement(String announcement) {
        this._announcement = announcement;
    }
    
    public int getEmblemStatus() {
        return this._emblemStatus;
    }
    
    public void setEmblemStatus(int emblemStatus) {
        this._emblemStatus = emblemStatus;
    }
    
    public int getClanLevel() {
        return this._clanLevel;
    }
    
    public void setClanLevel(int clanLevel) {
        this._clanLevel = clanLevel;
    }
    
    public int getClanContribution() {
        return this._clanContribution;
    }
    
    public void setClanContribution(int clanContribution) {
        this._clanContribution = clanContribution;
    }
    
    public int getClanSkillId() {
        return this._ClanSkillId;
    }
    
    public void setClanSkillId(int i) {
        this._ClanSkillId = i;
    }
    
    public int getClanSkillLv() {
        return this._ClanSkillLv;
    }
    
    public void setClanSkillLv(int i) {
        this._ClanSkillLv = i;
    }
    
    public ArrayList<L1PcInstance> getOnlineMemberList() {
        ArrayList<L1PcInstance> onlineMembers = new ArrayList();
        for (String name : this._membersNameList) {
            L1PcInstance pc = World.get().getPlayer(name);
            if (pc != null && !onlineMembers.contains(pc)) {
                onlineMembers.add(pc);
            }
        }
        return onlineMembers;
    }
    
    public int getclanadena() {
        return this._clanadena;
    }
    
    public void setclanadena(int clanadena) {
        this._clanadena = clanadena;
    }
    
    public Integer[] Alliance() {
        Integer[] i = (Integer[])this.allianceList.toArray(new Integer[this.allianceList.size()]);
        return i;
    }
    
    public void addAlliance(int i) {
        if (i == 0) {
            return;
        }
        else {
            if (!this.allianceList.contains((Integer)i)) {
                this.allianceList.add(i);
            }
            return;
        }
    }
    
    public int AllianceSize() {
        return this.allianceList.size();
    }
    
    public L1Clan getAlliance(int i) {
        if (this.allianceList.size() > 0) {
            Iterator<Integer> iterator = this.allianceList.iterator();
            while (iterator.hasNext()) {
                int id = (Integer)iterator.next();
                if (id == i) {
                    return WorldClan.get().getClan(i);
                }
            }
        }
        return null;
    }
    
    public void AllianceDelete() {
        if (this.allianceList.size() > 0) {
            this.allianceList.clear();
        }
    }
    
    public void removeAlliance(int i) {
        if (i == 0) {
            return;
        }
        else {
            if (this.allianceList.contains((Integer)i)) {
                this.allianceList.remove((Integer)i);
            }
            return;
        }
    }
}

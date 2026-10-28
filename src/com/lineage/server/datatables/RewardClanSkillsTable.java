package com.lineage.server.datatables;

import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ClanSkills;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;

public class RewardClanSkillsTable
{
    private static final Log _log;
    private static final Map<Integer, HashMap<Integer, L1ClanSkills>> _list;
    public static boolean START;
    private static RewardClanSkillsTable _instance;
    
    static {
        _log = LogFactory.getLog(RewardClanSkillsTable.class);
        _list = new HashMap();
        RewardClanSkillsTable.START = false;
    }
    
    public static RewardClanSkillsTable get() {
        if (RewardClanSkillsTable._instance == null) {
            RewardClanSkillsTable._instance = new RewardClanSkillsTable();
        }
        return RewardClanSkillsTable._instance;
    }
    
    private RewardClanSkillsTable() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_血盟技能等級`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int ClanSkillId = rs.getInt("ClanSkillId");
                int ClanSkillLv = rs.getInt("ClanSkillLv");
                String ClanSkillName = rs.getString("ClanSkillName");
                String Note = rs.getString("Note");
                int[] Material = getArrayInt(rs.getString("Material"));
                int[] MaterialCount = getArrayInt(rs.getString("MaterialCount"));
                int[] MaterialLevel = getArrayInt(rs.getString("MaterialLevel"));
                int CheckLvturn = rs.getInt("CheckLvturn");
                int CheckLevel = rs.getInt("CheckLevel");
                int AddMaxHp = rs.getInt("AddMaxHp");
                int AddMaxMp = rs.getInt("AddMaxMp");
                int AddHpr = rs.getInt("AddHpr");
                int AddMpr = rs.getInt("AddMpr");
                int AddStr = rs.getInt("AddStr");
                int AddBowDmg = rs.getInt("AddBowDmg");
                int AddHit = rs.getInt("AddHit");
                int AddBowHit = rs.getInt("AddBowHit");
                int ReductionDmg = rs.getInt("ReductionDmg");
                int AddDex = rs.getInt("AddDex");
                int AddCon = rs.getInt("AddCon");
                int AddInt = rs.getInt("AddInt");
                int AddWis = rs.getInt("AddWis");
                int AddCha = rs.getInt("AddCha");
                int AddAc = rs.getInt("AddAc");
                int AddSp = rs.getInt("AddSp");
                int AddMr = rs.getInt("AddMr");
                int AddDmg = rs.getInt("AddDmg");
                int ReductionMagicDmg = rs.getInt("ReductionMagicDmg");
                int AddWater = rs.getInt("AddWater");
                int AddWind = rs.getInt("AddWind");
                int AddFire = rs.getInt("AddFire");
                int AddEarth = rs.getInt("AddEarth");
                L1ClanSkills skill = new L1ClanSkills();
                skill.setClanSkillName(ClanSkillName);
                skill.setNote(Note);
                skill.setMaterial(Material);
                skill.setMaterialCount(MaterialCount);
                skill.setMaterialLevel(MaterialLevel);
                skill.setCheckLvturn(CheckLvturn);
                skill.setCheckLevel(CheckLevel);
                skill.setAddMaxHp(AddMaxHp);
                skill.setAddMaxMp(AddMaxMp);
                skill.setAddHpr(AddHpr);
                skill.setAddMpr(AddMpr);
                skill.setAddStr(AddStr);
                skill.setAddBowDmg(AddBowDmg);
                skill.setAddHit(AddHit);
                skill.setAddBowHit(AddBowHit);
                skill.setReductionDmg(ReductionDmg);
                skill.setAddDex(AddDex);
                skill.setAddCon(AddCon);
                skill.setAddInt(AddInt);
                skill.setAddWis(AddWis);
                skill.setAddCha(AddCha);
                skill.setAddAc(AddAc);
                skill.setAddSp(AddSp);
                skill.setAddMr(AddMr);
                skill.setAddDmg(AddDmg);
                skill.setReductionMagicDmg(ReductionMagicDmg);
                skill.setAddWater(AddWater);
                skill.setAddWind(AddWind);
                skill.setAddFire(AddFire);
                skill.setAddEarth(AddEarth);
                HashMap<Integer, L1ClanSkills> _map = RewardClanSkillsTable._list.get((Integer)ClanSkillId);
                if (_map == null) {
                    _map = new HashMap();
                    _map.put(ClanSkillLv, skill);
                    RewardClanSkillsTable._list.put(ClanSkillId, _map);
                }
                else {
                    _map.put(ClanSkillLv, skill);
                }
            }
        }
        catch (SQLException e) {
            RewardClanSkillsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        RewardClanSkillsTable._log.info("血盟技能DB化設置資料數量:" + RewardClanSkillsTable._list.size() + "(" + timer.get() + "ms)");
    }
    
    private static int[] getArrayInt(String s) {
        if (s == null || s.isEmpty() || s.equals("")) {
            return null;
        }
        else {
            StringTokenizer st = new StringTokenizer(s, ",");
            int iSize = st.countTokens();
            String sTemp = null;
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                iReturn[i] = Integer.parseInt(sTemp);
            }
            return iReturn;
        }
    }
    
    public String getSkillsName(int skillId, int skillLv) {
        if (!RewardClanSkillsTable._list.isEmpty() && RewardClanSkillsTable._list.containsKey((Integer)skillId)) {
            HashMap<Integer, L1ClanSkills> list = RewardClanSkillsTable._list.get((Integer)skillId);
            if (list.containsKey((Integer)skillLv)) {
                return (list.get((Integer)skillLv)).getClanSkillName();
            }
        }
        return null;
    }
    
    public String[] getSkillsNameNote() {
        String[] name = (String[])null;
        if (!RewardClanSkillsTable._list.isEmpty()) {
            int size = RewardClanSkillsTable._list.size() * 2;
            name = new String[size];
            int i = 0;
            for (Integer clanSkillsId : RewardClanSkillsTable._list.keySet()) {
                HashMap<Integer, L1ClanSkills> list = RewardClanSkillsTable._list.get(clanSkillsId);
                for (Integer clanSkillsLv : list.keySet()) {
                    if (clanSkillsLv == 1) {
                        name[i] = (list.get(clanSkillsLv)).getClanSkillName();
                        ++i;
                        name[i] = (list.get(clanSkillsLv)).getNote();
                        ++i;
                        break;
                    }
                }
            }
        }
        return name;
    }
    
    public String getMaterialName(int skillId, int skillLv) {
        StringBuilder note = null;
        if (!RewardClanSkillsTable._list.isEmpty() && RewardClanSkillsTable._list.containsKey((Integer)skillId)) {
            HashMap<Integer, L1ClanSkills> list = RewardClanSkillsTable._list.get((Integer)skillId);
            if (list.containsKey((Integer)skillLv)) {
                L1ClanSkills clanSkills = list.get((Integer)skillLv);
                int[] Material = clanSkills.getMaterial();
                int[] MaterialCount = clanSkills.getMaterialCount();
                int[] MaterialLevel = clanSkills.getMaterialLevel();
                if (Material != null && MaterialCount != null && MaterialLevel != null) {
                    note = new StringBuilder();
                    for (int i = 0; i < Material.length; ++i) {
                        int k = 0;
                        StringBuilder itemName = new StringBuilder();
                        L1Item tgItem = ItemTable.get().getTemplate(Material[i]);
                        if (MaterialLevel[i] != 0) {
                            itemName.append("+");
                            itemName.append(MaterialLevel[i]);
                            itemName.append(" ");
                        }
                        itemName.append(tgItem.getName());
                        k = tgItem.getName().length();
                        if (MaterialCount[i] != 0) {
                            itemName.append("x");
                            itemName.append(MaterialCount[i]);
                            itemName.append("\n");
                        }
                        for (k += itemName.length(); k < 30; ++k) {
                            if (k < 30) {
                                itemName.append("   ");
                                ++k;
                            }
                        }
                        note.append(itemName);
                    }
                }
            }
        }
        return note.toString();
    }
    
    public L1ClanSkills getClanSkillsList(int skillId, int skillLv) {
        if (!RewardClanSkillsTable._list.isEmpty() && RewardClanSkillsTable._list.containsKey((Integer)skillId)) {
            HashMap<Integer, L1ClanSkills> list = RewardClanSkillsTable._list.get((Integer)skillId);
            if (list.containsKey((Integer)skillLv)) {
                return list.get((Integer)skillLv);
            }
        }
        return null;
    }
}

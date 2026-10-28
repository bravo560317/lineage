package com.lineage.server.datatables;

import com.lineage.server.templates.L1Skills;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class AutoAddSkillTable
{
    private static final Log _log;
    private static HashMap<Integer, HashMap<Integer, int[]>> _array;
    private static AutoAddSkillTable _instance;
    
    static {
        _log = LogFactory.getLog(AutoAddSkillTable.class);
        AutoAddSkillTable._array = new HashMap();
    }
    
    public static AutoAddSkillTable get() {
        if (AutoAddSkillTable._instance == null) {
            AutoAddSkillTable._instance = new AutoAddSkillTable();
        }
        return AutoAddSkillTable._instance;
    }
    
    private AutoAddSkillTable() {
        this.load();
        AutoAddSkillTable._log.info("升級自學技能->" + AutoAddSkillTable._array.size());
        if (AutoAddSkillTable._array.size() <= 0) {
            AutoAddSkillTable._array.clear();
            AutoAddSkillTable._array = null;
        }
    }
    
    private void load() {
        Connection cn = null;
        Statement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.createStatement();
            rs = ps.executeQuery("SELECT * FROM w_自動學習魔法");
            while (rs.next()) {
                int level = rs.getInt("Level");
                int[] skills = getArray(rs.getString("Skill"));
                boolean r = rs.getBoolean("Royal");
                boolean k = rs.getBoolean("Knight");
                boolean m = rs.getBoolean("Mage");
                boolean e = rs.getBoolean("Elf");
                boolean d = rs.getBoolean("Darkelf");
                boolean g = rs.getBoolean("DragonKnight");
                boolean i = rs.getBoolean("Illusionist");
                int type = 0;
                if (r) {
                    type = 0;
                }
                else if (k) {
                    type = 1;
                }
                else if (m) {
                    type = 3;
                }
                else if (e) {
                    type = 2;
                }
                else if (d) {
                    type = 4;
                }
                else if (g) {
                    type = 5;
                }
                else if (i) {
                    type = 6;
                }
                HashMap<Integer, int[]> _type = AutoAddSkillTable._array.get((Integer)type);
                if (_type == null) {
                    _type = new HashMap();
                }
                _type.put(level, skills);
                AutoAddSkillTable._array.put(type, _type);
            }
        }
        catch (SQLException e2) {
            AutoAddSkillTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static int[] getArray(String s) {
        if (s == null || s.equals("") || s.equals("0")) {
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
    
    public void forAutoAddSkill(L1PcInstance pc) {
        if (AutoAddSkillTable._array == null) {
            return;
        }
        else {
            int classtype = pc.getType();
            if (!AutoAddSkillTable._array.containsKey((Integer)classtype)) {
                return;
            }
            else {
                HashMap<Integer, int[]> _classtype = AutoAddSkillTable._array.get((Integer)classtype);
                int level = pc.getLevel();
                for (Integer key : _classtype.keySet()) {
                    if (key > level) {
                        continue;
                    }
                    else {
                        int[] skills = _classtype.get(key);
                        int i = 0;
                        int skillId = 0;
                        while (i < skills.length) {
                            skillId = skills[i];
                            if (!pc.isSkillMastery(skillId)) {
                                pc.sendPackets(new S_AddSkill(pc, skillId));
                                S_SkillSound sound = new S_SkillSound(pc.getId(), 227);
                                pc.sendPacketsX8(sound);
                                L1Skills skill = SkillsTable.get().getTemplate(skillId);
                                String skillName = skill.getName();
                                CharSkillReading.get().spellMastery(pc.getId(), skillId, skillName, 0, 0);
                                pc.sendPackets(new S_SystemMessage("自動學習 ( " + skillName + " ) 技能"));
                            }
                            ++i;
                        }
                    }
                }
                return;
            }
        }
    }
}

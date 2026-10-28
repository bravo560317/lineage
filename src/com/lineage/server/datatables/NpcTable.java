package com.lineage.server.datatables;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.data.executor.NpcExecutor;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.lang.reflect.Constructor;
import com.lineage.server.templates.L1Npc;
import java.util.Map;
import org.apache.commons.logging.Log;

public class NpcTable
{
    private static final Log _log;
    private static NpcTable _instance;
    public static int ORC;
    private static final Map<Integer, L1Npc> _npcs;
    private static final Map<String, Constructor<?>> _constructorCache;
    private static final Map<String, Integer> _familyTypes;
    
    static {
        _log = LogFactory.getLog(NpcTable.class);
        NpcTable.ORC = -1;
        _npcs = new HashMap();
        _constructorCache = new HashMap();
        _familyTypes = buildFamily();
    }
    
    public static NpcTable get() {
        if (NpcTable._instance == null) {
            NpcTable._instance = new NpcTable();
        }
        return NpcTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        this.loadNpcData();
        NpcTable._log.info("載入NPC設置資料數量: " + NpcTable._npcs.size() + "(" + timer.get() + "ms)");
    }
    
    private Constructor<?> getConstructor(String implName) {
        try {
            String implFullName = "com.lineage.server.model.Instance." + implName + "Instance";
            Constructor<?> con = Class.forName(implFullName).getConstructors()[0];
            return con;
        }
        catch (ClassNotFoundException e) {
            NpcTable._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    private void registerConstructorCache(String implName) {
        if (implName.isEmpty() || NpcTable._constructorCache.containsKey(implName)) {
            return;
        }
        else {
            NpcTable._constructorCache.put(implName, this.getConstructor(implName));
            return;
        }
    }
    
    private void loadNpcData() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `npc`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1Npc npc = new L1Npc();
                int npcId = rs.getInt("npcid");
                npc.set_npcId(npcId);
                npc.set_name(rs.getString("name"));
                npc.set_nameid(rs.getString("nameid"));
                String classname = rs.getString("classname");
                npc.set_classname(classname);
                npc.setNpcExecutor(this.addClass(npcId, classname));
                npc.setImpl(rs.getString("impl"));
                npc.set_gfxid(rs.getInt("gfxid"));
                npc.set_level(rs.getInt("lvl"));
                npc.set_hp(rs.getInt("hp"));
                npc.set_mp(rs.getInt("mp"));
                npc.set_ac(rs.getInt("ac"));
                npc.set_str((int)rs.getByte("str"));
                npc.set_con((int)rs.getByte("con"));
                npc.set_dex((int)rs.getByte("dex"));
                npc.set_wis((int)rs.getByte("wis"));
                npc.set_int((int)rs.getByte("intel"));
                npc.set_mr(rs.getInt("mr"));
                npc.set_exp(rs.getInt("exp"));
                npc.set_lawful(rs.getInt("lawful"));
                npc.set_size(rs.getString("size"));
                npc.set_weakAttr(rs.getInt("weakAttr"));
                npc.set_ranged(rs.getInt("ranged"));
                npc.setTamable(rs.getBoolean("tamable"));
                npc.set_passispeed(rs.getInt("passispeed"));
                npc.set_atkspeed(rs.getInt("atkspeed"));
                npc.setAtkMagicSpeed(rs.getInt("atk_magic_speed"));
                npc.setSubMagicSpeed(rs.getInt("sub_magic_speed"));
                npc.set_undead(rs.getInt("undead"));
                npc.set_poisonatk(rs.getInt("poison_atk"));
                npc.set_paralysisatk(rs.getInt("paralysis_atk"));
                npc.set_agro(rs.getBoolean("agro"));
                npc.set_agrososc(rs.getBoolean("agrososc"));
                npc.set_agrocoi(rs.getBoolean("agrocoi"));
                Integer family = NpcTable._familyTypes.get(rs.getString("family"));
                if (family == null) {
                    npc.set_family(0);
                }
                else {
                    npc.set_family(family);
                }
                int agrofamily = rs.getInt("agrofamily");
                if (npc.get_family() == 0 && agrofamily == 1) {
                    npc.set_agrofamily(0);
                }
                else {
                    npc.set_agrofamily(agrofamily);
                }
                npc.set_agrogfxid1(rs.getInt("agrogfxid1"));
                npc.set_agrogfxid2(rs.getInt("agrogfxid2"));
                npc.set_picupitem(rs.getBoolean("picupitem"));
                npc.set_digestitem(rs.getInt("digestitem"));
                npc.set_bravespeed(rs.getBoolean("bravespeed"));
                npc.set_hprinterval(rs.getInt("hprinterval"));
                npc.set_hpr(rs.getInt("hpr"));
                npc.set_mprinterval(rs.getInt("mprinterval"));
                npc.set_mpr(rs.getInt("mpr"));
                npc.set_teleport(rs.getBoolean("teleport"));
                npc.set_randomlevel(rs.getInt("randomlevel"));
                npc.set_randomhp(rs.getInt("randomhp"));
                npc.set_randommp(rs.getInt("randommp"));
                npc.set_randomac(rs.getInt("randomac"));
                npc.set_randomexp(rs.getInt("randomexp"));
                npc.set_randomlawful(rs.getInt("randomlawful"));
                npc.set_damagereduction(rs.getInt("damage_reduction"));
                npc.set_hard(rs.getBoolean("hard"));
                npc.set_doppel(rs.getBoolean("doppel"));
                npc.set_IsTU(rs.getBoolean("IsTU"));
                npc.set_IsErase(rs.getBoolean("IsErase"));
                npc.setBowActId(rs.getInt("bowActId"));
                npc.setKarma(rs.getInt("karma"));
                npc.setTransformId(rs.getInt("transform_id"));
                npc.setTransformGfxId(rs.getInt("transform_gfxid"));
                npc.setLightSize(rs.getInt("light_size"));
                npc.setAmountFixed(rs.getBoolean("amount_fixed"));
                npc.setChangeHead(rs.getBoolean("change_head"));
                npc.setCantResurrect(rs.getBoolean("cant_resurrect"));
                npc.set_quest_score(rs.getInt("quest_score"));
                npc.set_Atkpc(rs.getBoolean("不主動攻擊"));
                npc.set_Atkpc_red(rs.getBoolean("強制攻擊紅人"));
                npc.set_npcbroad(rs.getInt("死亡是否廣播"));
                this.registerConstructorCache(npc.getImpl());
                NpcTable._npcs.put(npcId, npc);
            }
        }
        catch (SQLException e) {
            NpcTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private NpcExecutor addClass(int npcid, String className) {
        try {
            if (!className.equals("0")) {
                String newclass = className;
                String[] set = null;
                if (className.indexOf(" ") != -1) {
                    set = className.split(" ");
                    try {
                        newclass = set[0];
                    }
                    catch (Exception ex) {}
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("com.lineage.data.npc.");
                stringBuilder.append(newclass);
                Class<?> cls = Class.forName(stringBuilder.toString());
                NpcExecutor exe = (NpcExecutor)cls.getMethod("get", new Class[0]).invoke(null, new Object[0]);
                if (set != null) {
                    exe.set_set(set);
                }
                return exe;
            }
        }
        catch (ClassNotFoundException e) {
            String error = "發生[NPC檔案]錯誤, 檢查檔案是否存在:" + className + " NpcId:" + npcid;
            NpcTable._log.error(error);
        }
        catch (Exception e2) {
            NpcTable._log.error(e2.getLocalizedMessage(), e2);
        }
        return null;
    }
    
    public L1Npc getTemplate(int npcid) {
        return NpcTable._npcs.get((Integer)npcid);
    }
    
    public String getNpcName(int id) {
        L1Npc npcTemp = this.getTemplate(id);
        if (npcTemp == null) {
            NpcTable._log.error("取回NPC名稱錯誤 沒有這個編號的NPC: " + id);
            return null;
        }
        else {
            return npcTemp.get_name();
        }
    }
    
    public L1NpcInstance newNpcInstance(int id) {
        try {
            L1Npc npcTemp = this.getTemplate(id);
            if (npcTemp == null) {
                NpcTable._log.error("依照NPCID取回新的L1NpcInstance資料發生異常(沒有這編號的NPC): " + id);
                return null;
            }
            else {
                return this.newNpcInstance(npcTemp);
            }
        }
        catch (Exception e) {
            NpcTable._log.error("NPCID:" + id + "/" + e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public L1NpcInstance newNpcInstance(L1Npc template) {
        try {
            if (template == null) {
                NpcTable._log.error("依照NPCID取回新的L1NpcInstance資料發生異常(NPC資料為空)");
                return null;
            }
            else {
                Constructor<?> con = NpcTable._constructorCache.get(template.getImpl());
                return (L1NpcInstance)con.newInstance(template);
            }
        }
        catch (Exception e) {
            NpcTable._log.error("NPCID:" + template.get_npcId() + "/" + e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    private static Map<String, Integer> buildFamily() {
        Map<String, Integer> result = new HashMap();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select distinct(family) as family from npc WHERE NOT trim(family) =''");
            rs = pstm.executeQuery();
            int id = 1;
            while (rs.next()) {
                String family = rs.getString("family");
                int oid = id++;
                if (family.equalsIgnoreCase("orc")) {
                    NpcTable.ORC = oid;
                }
                result.put(family, oid);
            }
        }
        catch (SQLException e) {
            NpcTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return result;
    }
    
    public int findNpcIdByName(String name) {
        for (L1Npc npc : NpcTable._npcs.values()) {
            if (npc.get_name().equals(name)) {
                return npc.get_npcId();
            }
        }
        return 0;
    }
    
    public int findNpcIdByNameWithoutSpace(String name) {
        for (L1Npc npc : NpcTable._npcs.values()) {
            if (npc.get_name().replace(" ", "").equals(name)) {
                return npc.get_npcId();
            }
        }
        return 0;
    }
}

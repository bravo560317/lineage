package com.lineage.server.datatables.sql;

import java.util.Iterator;
import com.lineage.server.templates.L1PetType;
import com.lineage.server.utils.Random;
import com.lineage.server.datatables.PetTypeTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.world.World;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Pet;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.PetStorage;

public class PetTable implements PetStorage
{
    private static final Log _log;
    private static final Map<Integer, L1Pet> _pets;
    
    static {
        _log = LogFactory.getLog(PetTable.class);
        _pets = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_pets`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int item_obj_id = rs.getInt("item_obj_id");
                if (World.get().findObject(item_obj_id) != null) {
                    L1Pet pet = new L1Pet();
                    pet.set_itemobjid(item_obj_id);
                    pet.set_objid(rs.getInt("objid"));
                    pet.set_npcid(rs.getInt("npcid"));
                    pet.set_name(rs.getString("name"));
                    pet.set_level(rs.getInt("lvl"));
                    pet.set_hp(rs.getInt("hp"));
                    pet.set_mp(rs.getInt("mp"));
                    pet.set_exp(rs.getInt("exp"));
                    pet.set_lawful(rs.getInt("lawful"));
// ★ 強制清裝備顯示（關鍵）
pet.setWeaponObjId(0);
pet.setWeaponItemId(0);
pet.setWeaponName("");

pet.setArmorObjId(0);
pet.setArmorItemId(0);
pet.setArmorName("");
                    PetTable._pets.put(new Integer(item_obj_id), pet);
                }
                else {
                    delete(item_obj_id);
                }
            }
        }
        catch (SQLException e) {
            PetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        PetTable._log.info("載入寵物資料數量: " + PetTable._pets.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int item_obj_id) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("DELETE FROM `character_pets` WHERE `item_obj_id`=?");
            pm.setInt(1, item_obj_id);
            pm.execute();
        }
        catch (SQLException e) {
            PetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void storeNewPet(L1NpcInstance pet, int objid, int itemobjid) {
        L1Pet l1pet = new L1Pet();
        l1pet.set_itemobjid(itemobjid);
        l1pet.set_objid(objid);
        l1pet.set_npcid(pet.getNpcTemplate().get_npcId());
        l1pet.set_name(pet.getNpcTemplate().get_name());
        l1pet.set_level(pet.getLevel());
        l1pet.set_hp(pet.getMaxHp());
        l1pet.set_mp(pet.getMaxMp());
        l1pet.set_exp((int)pet.getExp());
        l1pet.set_lawful(0);
        PetTable._pets.put(new Integer(itemobjid), l1pet);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `character_pets` SET `item_obj_id`=?,`objid`=?,`npcid`=?,`name`=?,`lvl`=?,`hp`=?,`mp`=?,`exp`=?,`lawful`=?");
            pstm.setInt(1, l1pet.get_itemobjid());
            pstm.setInt(2, l1pet.get_objid());
            pstm.setInt(3, l1pet.get_npcid());
            pstm.setString(4, l1pet.get_name());
            pstm.setInt(5, l1pet.get_level());
            pstm.setInt(6, l1pet.get_hp());
            pstm.setInt(7, l1pet.get_mp());
            pstm.setInt(8, l1pet.get_exp());
            pstm.setInt(9, l1pet.get_lawful());
            pstm.execute();
        }
        catch (Exception e) {
            PetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void storePet(L1Pet pet) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(
"UPDATE `character_pets` SET " +
"`objid`=?,`npcid`=?,`name`=?,`lvl`=?,`hp`=?,`mp`=?,`exp`=?,`lawful`=?," +
"`weapon_objid`=?,`weapon_itemid`=?,`weapon_name`=?," +
"`armor_objid`=?,`armor_itemid`=?,`armor_name`=? " +
"WHERE `item_obj_id`=?");
pstm.setInt(1, pet.get_objid());
pstm.setInt(2, pet.get_npcid());
pstm.setString(3, pet.get_name());
pstm.setInt(4, pet.get_level());
pstm.setInt(5, pet.get_hp());
pstm.setInt(6, pet.get_mp());
pstm.setInt(7, pet.get_exp());
pstm.setInt(8, pet.get_lawful());

pstm.setInt(9, pet.getWeaponObjId());
pstm.setInt(10, pet.getWeaponItemId());
pstm.setString(11, pet.getWeaponName());

pstm.setInt(12, pet.getArmorObjId());
pstm.setInt(13, pet.getArmorItemId());
pstm.setString(14, pet.getArmorName());

pstm.setInt(15, pet.get_itemobjid());
            pstm.execute();
        }
        catch (Exception e) {
            PetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void deletePet(int itemobjid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `character_pets` WHERE `item_obj_id`=?");
            pstm.setInt(1, itemobjid);
            pstm.execute();
        }
        catch (SQLException e) {
            PetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        PetTable._pets.remove((Integer)itemobjid);
    }
    
    @Override
    public boolean isNameExists(String nameCaseInsensitive) {
        String nameLower = nameCaseInsensitive.toLowerCase();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT `item_obj_id` FROM `character_pets` WHERE LOWER(`name`)=?");
            pstm.setString(1, nameLower);
            rs = pstm.executeQuery();
            if (!rs.next() || PetTypeTable.getInstance().isNameDefault(nameLower)) {
                return false;
            }
        }
        catch (SQLException e) {
            PetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return true;
    }
    
    @Override
    public void buyNewPet(int petNpcId, int objid, int itemobjid, int upLv, long lvExp) {
        L1PetType petType = PetTypeTable.getInstance().get(petNpcId);
        L1Pet l1pet = new L1Pet();
        l1pet.set_itemobjid(itemobjid);
        l1pet.set_objid(objid);
        l1pet.set_npcid(petNpcId);
        l1pet.set_name(petType.getName());
        l1pet.set_level(upLv);
        int hpUpMin = petType.getHpUpRange().getLow();
        int hpUpMax = petType.getHpUpRange().getHigh();
        int mpUpMin = petType.getMpUpRange().getLow();
        int mpUpMax = petType.getMpUpRange().getHigh();
        short randomhp = (short)((hpUpMin + hpUpMax) / 2);
        short randommp = (short)((mpUpMin + mpUpMax) / 2);
        for (int i = 1; i < upLv; ++i) {
            randomhp += (short)(Random.nextInt(hpUpMax - hpUpMin) + hpUpMin + 1);
            randommp += (short)(Random.nextInt(mpUpMax - mpUpMin) + mpUpMin + 1);
        }
        l1pet.set_hp((int)randomhp);
        l1pet.set_mp((int)randommp);
        l1pet.set_exp((int)lvExp);
        l1pet.set_lawful(0);
        PetTable._pets.put(new Integer(itemobjid), l1pet);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `character_pets` SET `item_obj_id`=?,`objid`=?,`npcid`=?,`name`=?,`lvl`=?,`hp`=?,`mp`=?,`exp`=?,`lawful`=?");
            pstm.setInt(1, l1pet.get_itemobjid());
            pstm.setInt(2, l1pet.get_objid());
            pstm.setInt(3, l1pet.get_npcid());
            pstm.setString(4, l1pet.get_name());
            pstm.setInt(5, l1pet.get_level());
            pstm.setInt(6, l1pet.get_hp());
            pstm.setInt(7, l1pet.get_mp());
            pstm.setInt(8, l1pet.get_exp());
            pstm.setInt(9, l1pet.get_lawful());
            pstm.execute();
        }
        catch (Exception e) {
            PetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public L1Pet getTemplate(int itemobjid) {
        return PetTable._pets.get(new Integer(itemobjid));
    }
    
    @Override
    public L1Pet getTemplateX(int npcobjid) {
        for (L1Pet pet : PetTable._pets.values()) {
            if (pet.get_objid() == npcobjid) {
                return pet;
            }
        }
        return null;
    }
    
    @Override
    public L1Pet[] getPetTableList() {
        return (L1Pet[])PetTable._pets.values().toArray(new L1Pet[PetTable._pets.size()]);
    }
}

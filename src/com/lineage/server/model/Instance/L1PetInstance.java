package com.lineage.server.model.Instance;

import java.util.Arrays;
import com.lineage.server.serverpackets.S_PetMenuPacket;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.serverpackets.S_HPMeter;
import com.lineage.server.serverpackets.S_NPCPack_Pet;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1GroundInventory;
import com.lineage.server.templates.L1PetItem;
import java.util.List;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.L1Object;
import com.lineage.server.datatables.PetItemTable;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Location;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.lock.PetReading;
import com.lineage.server.IdFactory;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.PetTypeTable;
import com.lineage.server.templates.L1Pet;
import java.util.Iterator;
import com.lineage.server.world.World;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1PetType;
import java.util.Random;
import org.apache.commons.logging.Log;

public class L1PetInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private static Random _random;
    private int _currentPetStatus;
    private int _checkMove;
    private L1PcInstance _petMaster;
    private int _itemObjId;
    private L1PetType _type;
    private int _expPercent;
    private L1ItemInstance _weapon;
    private L1ItemInstance _armor;
    private int _hitByWeapon;
    private int _damageByWeapon;
    private int _tempModel;
private int _weaponObjId;
private int _weaponItemId;
private String _weaponName;

private int _armorObjId;
private int _armorItemId;
private String _armorName;
    
    static {
        _log = LogFactory.getLog(L1PetInstance.class);
        L1PetInstance._random = new Random();
    }
    
    @Override
    public boolean noTarget() {
        switch (this._currentPetStatus) {
            case 3: {
                return true;
            }
            case 4: {
                if (this._petMaster != null && this._petMaster.getMapId() == this.getMapId() && this.getLocation().getTileLineDistance(this._petMaster.getLocation()) < 5) {
                    if (this._npcMove != null) {
                        int dir = this._npcMove.targetReverseDirection(this._petMaster.getX(), this._petMaster.getY());
                        dir = this._npcMove.checkObject(dir);
                        this._npcMove.setDirectionMove(dir);
                        this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    this._currentPetStatus = 3;
                    return true;
                }
            }
            case 5: {
                if ((Math.abs(this.getHomeX() - this.getX()) > 1 || Math.abs(this.getHomeY() - this.getY()) > 1) && this._npcMove != null) {
                    int dir = this._npcMove.moveDirection(this.getHomeX(), this.getHomeY());
                    if (dir == -1) {
                        this.setHomeX(this.getX());
                        this.setHomeY(this.getY());
                        break;
                    }
                    else {
                        this._npcMove.setDirectionMove(dir);
                        this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                        break;
                    }
                }
                else {
                    break;
                }
            }
            case 7: {
                if (this._petMaster != null && this._petMaster.getMapId() == this.getMapId() && this.getLocation().getTileLineDistance(this._petMaster.getLocation()) <= 1) {
                    this._currentPetStatus = 3;
                    return true;
                }
                else if (this._npcMove != null) {
                    int locx = this._petMaster.getX() + L1PetInstance._random.nextInt(1);
                    int locy = this._petMaster.getY() + L1PetInstance._random.nextInt(1);
                    int dirx = this._npcMove.moveDirection(locx, locy);
                    if (dirx == -1) {
                        this._currentPetStatus = 3;
                        return true;
                    }
                    else {
                        this._npcMove.setDirectionMove(dirx);
                        this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                        break;
                    }
                }
                else {
                    break;
                }
            }
            default: {
                if (this._petMaster != null && this._petMaster.getMapId() == this.getMapId()) {
                    if (this.getLocation().getTileLineDistance(this._petMaster.getLocation()) > 2 && this._npcMove != null) {
                        int dir = this._npcMove.moveDirection(this._petMaster.getX(), this._petMaster.getY());
                        if (dir == -1) {
                            ++this._checkMove;
                            if (this._checkMove >= 10) {
                                this._checkMove = 0;
                                this._currentPetStatus = 3;
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            this._checkMove = 0;
                            this._npcMove.setDirectionMove(dir);
                            this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                else {
                    this._currentPetStatus = 3;
                    return true;
                }
            }
        }
        return false;
    }
    
    public L1PetInstance(L1Npc template, L1PcInstance master) {
        super(template);
        this._checkMove = 0;
        this._tempModel = 3;
        this._petMaster = master;
        this._itemObjId = -1;
        this._type = null;
        this.setId(IdFactoryNpc.get().nextId());
        this.set_showId(master.get_showId());
        this.setName(template.get_name());
        this.setLevel(template.get_level());
        this.setMaxHp(template.get_hp());
        this.setCurrentHpDirect(template.get_hp());
        this.setMaxMp(template.get_mp());
        this.setCurrentMpDirect(template.get_mp());
        this.setExp((long)template.get_exp());
        this.setExpPercent(ExpTable.getExpPercentage(template.get_level(), (long)template.get_exp()));
        this.setLawful(template.get_lawful());
        this.setTempLawful(template.get_lawful());
        this.setMaster(master);
        this.setX(master.getX() + L1PetInstance._random.nextInt(5) - 2);
        this.setY(master.getY() + L1PetInstance._random.nextInt(5) - 2);
        this.setMap(master.getMapId());
        this.setHeading(master.getHeading());
        this.setLightSize(template.getLightSize());

        int npcId = template.get_npcId();
        int requiredCha = 6;
        if (npcId >= 96501 && npcId <= 96521) {
            requiredCha = 10;
        } else if (npcId >= 96601 && npcId <= 96626) {
            requiredCha = 12;
        } else if (npcId == 45313 || npcId == 45710 || npcId == 45711 || npcId == 45712) {
            requiredCha = 12;
        }
        this.setPetcost(requiredCha);

        this._currentPetStatus = 3;
        World.get().storeObject(this);
        World.get().addVisibleObject(this);
        for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
            this.onPerceive(pc);
        }
        master.addPet(this);
        this.addMaster(master);
    }
    
    public L1PetInstance(L1Npc template, L1PcInstance master, L1Pet pet) {
        super(template);
        this._checkMove = 0;
        this._tempModel = 3;
        this._petMaster = master;
        this._itemObjId = pet.get_itemobjid();
        this._type = PetTypeTable.getInstance().get(template.get_npcId());
        this.setId(pet.get_objid());
        this.set_showId(master.get_showId());
        this.setName(pet.get_name());
        this.setLevel(pet.get_level());
this.setMaxHp(pet.get_hp());
this.setCurrentHpDirect(pet.get_hp());
this.setMaxMp(pet.get_mp());
this.setCurrentMpDirect(pet.get_mp());


this.setCurrentHp(Math.min(this.getCurrentHp(), this.getMaxHp()));
this.setCurrentMp(Math.min(this.getCurrentMp(), this.getMaxMp()));
        this.setExp((long)pet.get_exp());
        this.setExpPercent(ExpTable.getExpPercentage(pet.get_level(), (long)pet.get_exp()));
        this.setLawful(pet.get_lawful());
        this.setTempLawful(pet.get_lawful());
        this.setMaster(master);
        this.setX(master.getX() + L1PetInstance._random.nextInt(5) - 2);
        this.setY(master.getY() + L1PetInstance._random.nextInt(5) - 2);
        this.setMap(master.getMapId());
        this.setHeading(5);
        this.setLightSize(template.getLightSize());

        int npcId = template.get_npcId();
        int requiredCha = 6;
        if (npcId >= 96501 && npcId <= 96521) {
            requiredCha = 10;
        } else if (npcId >= 96601 && npcId <= 96626) {
            requiredCha = 12;
        } else if (npcId == 45313 || npcId == 45710 || npcId == 45711 || npcId == 45712) {
            requiredCha = 12;
        }
        this.setPetcost(requiredCha);

        this._currentPetStatus = 1;
        World.get().storeObject(this);
        World.get().addVisibleObject(this);
        for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
            this.onPerceive(pc);
        }
        this.broadcastPacketAll(new S_SkillSound(this.getId(), 3104));
        this.broadcastPacketAll(new S_SkillHaste(this.getId(), 1, 0));
        this.setMoveSpeed(1);
        this.setSkillEffect(43, 1800000);
        master.addPet(this);
        this.addMaster(master);
    }
    
    public L1PetInstance(L1NpcInstance target, L1PcInstance master, int itemobjid) {
        super(null);
        this._checkMove = 0;
        this._tempModel = 3;
        this._petMaster = master;
        this._itemObjId = itemobjid;
        this._type = PetTypeTable.getInstance().get(target.getNpcTemplate().get_npcId());
        this.setId(IdFactory.get().nextId());
        this.set_showId(master.get_showId());
        this.setting_template(target.getNpcTemplate());
        this.setCurrentHpDirect(target.getCurrentHp());
        this.setCurrentMpDirect(target.getCurrentMp());
        this.setExp(750L);
        this.setExpPercent(0);
        this.setLawful(0);
        this.setTempLawful(0);
        this.setMaster(master);
        this.setX(target.getX());
        this.setY(target.getY());
        this.setMap(target.getMapId());
        this.setHeading(target.getHeading());
        this.setLightSize(target.getLightSize());
        int npcId = target.getNpcTemplate().get_npcId();
        int requiredCha = 6;
        if (npcId >= 96501 && npcId <= 96521) {
            requiredCha = 10;
        } else if (npcId >= 96601 && npcId <= 96626) {
            requiredCha = 12;
        } else if (npcId == 45313 || npcId == 45710 || npcId == 45711 || npcId == 45712) {
            requiredCha = 12;
        }
        this.setPetcost(requiredCha);
        this.setInventory(target.getInventory());
        target.setInventory(null);
        this._currentPetStatus = 3;
        target.deleteMe();
        World.get().storeObject(this);
        World.get().addVisibleObject(this);
        for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
            this.onPerceive(pc);
        }
        master.addPet(this);
        PetReading.get().storeNewPet(target, this.getId(), itemobjid);
        this.addMaster(master);
    }
    
    public L1PetInstance(int npcid, L1PcInstance master, int itemobjid) {
        super(null);
        this._checkMove = 0;
        this._tempModel = 3;
        this._petMaster = master;
        this._itemObjId = itemobjid;
        this._type = PetTypeTable.getInstance().get(npcid);
        L1Npc npc = NpcTable.get().getTemplate(npcid);
        this.setId(IdFactory.get().nextId());
        this.set_showId(master.get_showId());
        this.setting_template(npc);
        this.setCurrentHpDirect(npc.get_hp());
        this.setCurrentMpDirect(npc.get_mp());
        long exp = 750L;
        this.setExp(exp);
        this.setLevel(ExpTable.getLevelByExp(exp));
        this.setExpPercent(0);
        this.setLawful(0);
        this.setTempLawful(0);
        this.setMaster(master);
        L1Location loc = master.getLocation().randomLocation(5, false);
        this.setX(loc.getX());
        this.setY(loc.getY());
        this.setMap((short)loc.getMapId());
        this.setHeading(5);
        this.setLightSize(npc.getLightSize());
        int requiredCha = 6;
        if (npcid >= 96501 && npcid <= 96521) {
            requiredCha = 10;
        } else if (npcid >= 96601 && npcid <= 96626) {
            requiredCha = 12;
        } else if (npcid == 45313 || npcid == 45710 || npcid == 45711 || npcid == 45712) {
            requiredCha = 12;
        }
        this.setPetcost(requiredCha);
        this._currentPetStatus = 3;
        World.get().storeObject(this);
        World.get().addVisibleObject(this);
        for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
            this.onPerceive(pc);
        }
        master.addPet(this);
        PetReading.get().storeNewPet(this, this.getId(), itemobjid);
        this.addMaster(master);
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        this.ISASCAPE = false;
        if (this.getCurrentHp() > 0) {
            if (damage > 0) {
                this.setHate(attacker, 0);
                this.removeSkillEffect(66);
                this.removeSkillEffect(212);
            }
            if (attacker instanceof L1PcInstance && damage > 0) {
                L1PcInstance player = (L1PcInstance)attacker;
                player.setPetTarget(this);
            }
            int newHp = this.getCurrentHp() - damage;
            if (newHp <= 0) {
                this.death(attacker);
            }
            else {
                this.setCurrentHp(newHp);
            }
        }
        else if (!this.isDead()) {
            this.death(attacker);
        }
    }
    
    public synchronized void death(L1Character lastAttacker) {
        if (!this.isDead()) {
            this.setDead(true);
            this.setStatus(8);
            this.setCurrentHp(0);
            this.setExpPercent(this.getExpPercent() - 5);
            this._currentPetStatus = 3;
            this.getMap().setPassable(this.getLocation(), true);
            this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 8));
        }
    }
public void evolvePet(int new_itemobjid) {
    try {
        // 1. 取得舊寵物資料（DB）
        L1Pet oldPet = PetReading.get().getTemplate(this._itemObjId);
        if (oldPet == null) {
            return;
        }

        // 2. 隨機取得進化後 NPC ID
        int newNpcId = this._type.getNpcIdForEvolvingRandom();
        if (newNpcId <= 0) {
            _log.warn("寵物進化失敗：無可用 NPC ID");
            return;
        }

        // 3. 確認 NPC 存在
        L1Npc newNpc = NpcTable.get().getTemplate(newNpcId);
        if (newNpc == null) {
            _log.warn("寵物進化失敗：NPC 不存在 id=" + newNpcId);
            return;
        }

        // 4. 保存舊寵關鍵數值
        int tmpMaxHp = this.getMaxHp();
        int tmpMaxMp = this.getMaxMp();
        int evolvItem = this._type.getEvolvItemId();
        L1PcInstance master = this._petMaster;

        // 5. 先把寵物身上所有裝備「完整回收給主人」
        this.collect(true);

        // 6. 扣除進化道具（從主人背包）
        master.getInventory().consumeItem(evolvItem, 1L);

        // 7. 從世界與 DB 移除舊寵
        master.getPetList().remove(this.getId());
        this.deleteMe();
        PetReading.get().deletePet(this._itemObjId);

        // 8. 建立新寵（⚠ 只會 INSERT 一次）
        L1PetInstance newPet = new L1PetInstance(newNpcId, master, new_itemobjid);

        // 9. 設定新寵能力
        newPet.setLevel(1);
        newPet.setMaxHp(tmpMaxHp); // 保留原寵物的最大血量
        newPet.setMaxMp(tmpMaxMp); // 保留原寵物的最大魔力
        newPet.setCurrentHpDirect(newPet.getMaxHp());
        newPet.setCurrentMpDirect(newPet.getMaxMp());
        newPet.setExp(0L);
        newPet.setExpPercent(0);

        // 10. 特效
        newPet.broadcastPacketAll(new S_SkillSound(newPet.getId(), 2127));

    } catch (Throwable t) {
        _log.error("寵物進化發生例外", t);
        if (this._petMaster != null) {
            this._petMaster.sendPackets(
                new S_ServerMessage("寵物進化失敗，請聯絡管理員")
            );
        }
    }
}
    public void liberate() {
        L1MonsterInstance monster = new L1MonsterInstance(this.getNpcTemplate());
        monster.setId(IdFactoryNpc.get().nextId());
        monster.setX(this.getX());
        monster.setY(this.getY());
        monster.setMap(this.getMapId());
        monster.setHeading(this.getHeading());
        monster.set_storeDroped(true);
        monster.setInventory(this.getInventory());
        this.setInventory(null);
        monster.setLevel(this.getLevel());
        monster.setMaxHp(this.getMaxHp());
        monster.setCurrentHpDirect(this.getCurrentHp());
        monster.setMaxMp(this.getMaxMp());
        monster.setCurrentMpDirect(this.getCurrentMp());
        this._petMaster.getPetList().remove((Integer)this.getId());
        this.deleteMe();
        this._petMaster.getInventory().removeItem(this._itemObjId, 1L);
        PetReading.get().deletePet(this._itemObjId);
        World.get().storeObject(monster);
        World.get().addVisibleObject(monster);
        for (L1PcInstance pc : World.get().getRecognizePlayer(monster)) {
            this.onPerceive(pc);
        }
    }
    
    public void collect(boolean isDepositnpc) {
        L1Inventory masterInv = this._petMaster.getInventory();
        List<L1ItemInstance> items = this._inventory.getItems();
        for (L1ItemInstance item : items) {
            // 移除 isEquipped() 判斷，讓所有道具都能被處理
            if (item.isEquipped()) {
                int itemId = item.getItem().getItemId();
                L1PetItem petItem = PetItemTable.get().getTemplate(itemId);
                if (petItem != null) {
                    this.setHitByWeapon(0);
                    this.setDamageByWeapon(0);
                    this.addStr(-petItem.getAddStr());
                    this.addCon(-petItem.getAddCon());
                    this.addDex(-petItem.getAddDex());
                    this.addInt(-petItem.getAddInt());
                    this.addWis(-petItem.getAddWis());
                    this.addMaxHp(-petItem.getAddHp());
                    this.addMaxMp(-petItem.getAddMp());
                    this.addSp(-petItem.getAddSp());
                    this.addMr(-petItem.getAddMr());
                    this.setWeapon(null);
                    this.setArmor(null);
                    item.setEquipped(false);
                }
            }
            
            if (this._petMaster.getInventory().checkAddItem(item, item.getCount()) == 0) {
                this._inventory.tradeItem(item, item.getCount(), masterInv);
                this._petMaster.sendPackets(new S_ServerMessage(143, this.getName(), item.getLogName()));
            } else {
                item.set_showId(this.get_showId());
                masterInv = World.get().getInventory(this.getX(), this.getY(), this.getMapId());
                this._inventory.tradeItem(item, item.getCount(), masterInv);
            }
        }
        this.savePet();
// ★ 清除DB裝備紀錄（唯一正解）
try {
    java.sql.Connection con = com.lineage.DatabaseFactory.get().getConnection();
    java.sql.PreparedStatement ps = con.prepareStatement(
        "UPDATE character_pets SET weapon_objid=0, weapon_itemid=0, weapon_name='', armor_objid=0, armor_itemid=0, armor_name='' WHERE item_obj_id=?"
    );
    ps.setInt(1, this._itemObjId);
    ps.executeUpdate();
    ps.close();
    con.close();
} catch (Exception e) {
    e.printStackTrace();
}
    }
    
    public void dropItem() {
        L1Inventory worldInv = World.get().getInventory(this.getX(), this.getY(), this.getMapId());
        List<L1ItemInstance> items = this._inventory.getItems();
        for (L1ItemInstance item : items) {
            item.set_showId(this.get_showId());
            if (item.isEquipped()) {
                int itemId = item.getItem().getItemId();
                L1PetItem petItem = PetItemTable.get().getTemplate(itemId);
                if (petItem != null) {
                    this.setHitByWeapon(0);
                    this.setDamageByWeapon(0);
                    this.addStr(-petItem.getAddStr());
                    this.addCon(-petItem.getAddCon());
                    this.addDex(-petItem.getAddDex());
                    this.addInt(-petItem.getAddInt());
                    this.addWis(-petItem.getAddWis());
                    this.addMaxHp(-petItem.getAddHp());
                    this.addMaxMp(-petItem.getAddMp());
                    this.addSp(-petItem.getAddSp());
                    this.addMr(-petItem.getAddMr());
                    this.setWeapon(null);
                    this.setArmor(null);
                    item.setEquipped(false);
                }
            }
            this._inventory.tradeItem(item, item.getCount(), worldInv);
        }
        this.savePet();
    }
    
private void savePet() {
    try {
        L1Pet pet = PetReading.get().getTemplate(this._itemObjId);
        if (pet != null) {
            pet.set_exp((int)this.getExp());
            pet.set_level(this.getLevel());

            int baseHp = this.getMaxHp();
            int baseMp = this.getMaxMp();

            if (this.getWeapon() != null) {
                L1PetItem petItem = PetItemTable.get().getTemplate(this.getWeapon().getItem().getItemId());
                if (petItem != null) {
                    baseHp -= petItem.getAddHp();
                    baseMp -= petItem.getAddMp();
                }
            }

            if (this.getArmor() != null) {
                L1PetItem petItem = PetItemTable.get().getTemplate(this.getArmor().getItem().getItemId());
                if (petItem != null) {
                    baseHp -= petItem.getAddHp();
                    baseMp -= petItem.getAddMp();
                }
            }

            pet.set_hp(baseHp);
            pet.set_mp(baseMp);

            PetReading.get().storePet(pet);
        }
    }
    catch (Exception e) {
        L1PetInstance._log.error(e.getLocalizedMessage(), e);
    }
}
    
    public void call() {
        if (this._type != null) {
            int id = this._type.getMessageId(L1PetType.getMessageNumber(this.getLevel()));
            if (id != 0) {
                this.broadcastPacketAll(new S_NpcChat(this, "$" + id));
            }
        }
        this.setCurrentPetStatus(7);
    }
    
    public void setTarget(L1Character target) {
        if (target != null && (this._currentPetStatus == 1 || this._currentPetStatus == 2 || this._currentPetStatus == 5)) {
            this.setHate(target, 0);
            if (!this.isAiRunning()) {
                this.startAI();
            }
        }
    }
    
    public void setMasterTarget(L1Character target) {
        if (target != null && (this._currentPetStatus == 1 || this._currentPetStatus == 5)) {
            this.setHate(target, 10);
            if (!this.isAiRunning()) {
                this.startAI();
            }
        }
    }
    
    public void setMasterSelectTarget(L1Character target) {
        if (target != null) {
            this.setHate(target, 0);
            if (!this.isAiRunning()) {
                this.startAI();
            }
        }
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            else {
                perceivedFrom.addKnownObject(this);
                perceivedFrom.sendPackets(new S_NPCPack_Pet(this, perceivedFrom));
                if (this.getMaster() != null && perceivedFrom.getId() == this.getMaster().getId()) {
                    perceivedFrom.sendPackets(new S_HPMeter(this.getId(), 100 * this.getCurrentHp() / this.getMaxHp()));
                }
                if (this.isDead()) {
                    perceivedFrom.sendPackets(new S_DoActionGFX(this.getId(), 8));
                }
            }
        }
        catch (Exception e) {
            L1PetInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onAction(L1PcInstance player) {
        if (player == null) {
            return;
        }
        else {
            L1Character cha = this.getMaster();
            if (cha == null) {
                return;
            }
            else {
                L1PcInstance master = (L1PcInstance)cha;
                if (master.isTeleport()) {
                    return;
                }
                else if (master.equals(player)) {
                    L1AttackMode attack_mortion = new L1AttackPc(player, this);
                    attack_mortion.action();
                    return;
                }
                else if (this.isSafetyZone() || player.isSafetyZone()) {
                    L1AttackMode attack_mortion = new L1AttackPc(player, this);
                    attack_mortion.action();
                    return;
                }
                else if (player.checkNonPvP(player, this)) {
                    return;
                }
                else {
                    L1AttackMode attack = new L1AttackPc(player, this);
                    if (attack.calcHit()) {
                        attack.calcDamage();
                    }
                    attack.action();
                    attack.commit();
                    return;
                }
            }
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance player) {
        if (this.isDead()) {
            return;
        }
        else {
            if (this._petMaster.equals(player)) {
                player.sendPackets(new S_PetMenuPacket(this, this.getExpPercent()));
                this.savePet();
            }
            return;
        }
    }
    
    @Override
    public void onFinalAction(L1PcInstance player, String action) {
        int status = Integer.parseInt(action);
        switch (status) {
            case 0: {
                return;
            }
            case 6: {
                this.liberate();
                break;
            }
            default: {
                Object[] petList = this._petMaster.getPetList().values().toArray();
                Object[] array;
                for (int length = (array = petList).length, i = 0; i < length; ++i) {
                    Object petObject = array[i];
                    if (petObject instanceof L1PetInstance) {
                        L1PetInstance pet = (L1PetInstance)petObject;
                        if (this._petMaster != null && this._petMaster.isGm()) {
                            pet.setCurrentPetStatus(status);
                        }
                        else if (this._petMaster.getLevel() >= pet.getLevel()) {
                            pet.setCurrentPetStatus(status);
                        }
                        else if (this._type != null) {
                            int id = this._type.getDefyMessageId();
                            if (id != 0) {
                                this.broadcastPacketAll(new S_NpcChat(pet, "$" + id));
                            }
                        }
                    }
                }
                break;
            }
        }
    }
    
    @Override
    public void onItemUse() {
        // 1. 加速藥水：無論是否在戰鬥中，只要沒有加速狀態，就嘗試喝加速藥水
        this.useItem(1, 100);
        
        // 2. 治癒藥水：血量低於 80% 就喝補血藥水
        if (this.getCurrentHp() * 100 / this.getMaxHp() < 80) {
            this.useItem(0, 100);
        }
    }
    
    @Override
    public void onGetItem(L1ItemInstance item) {
        if (this.getNpcTemplate().get_digestitem() > 0) {
            this.setDigestItem(item);
        }
        Arrays.sort(L1PetInstance.healPotions);
        Arrays.sort(L1PetInstance.haestPotions);
        if (Arrays.binarySearch(L1PetInstance.healPotions, item.getItem().getItemId()) >= 0) {
            if (this.getCurrentHp() != this.getMaxHp()) {
                this.useItem(0, 100);
            }
        }
        else if (Arrays.binarySearch(L1PetInstance.haestPotions, item.getItem().getItemId()) >= 0) {
            this.useItem(1, 100);
        }
    }
    
    @Override
    public void setCurrentHp(int i) {
        int currentHp = Math.min(i, this.getMaxHp());
        if (this.getCurrentHp() == currentHp) {
            return;
        }
        else {
            this.setCurrentHpDirect(currentHp);
            if (this._petMaster != null) {
                int hpRatio = 100 * currentHp / this.getMaxHp();
                L1PcInstance master = this._petMaster;
                master.sendPackets(new S_HPMeter(this.getId(), hpRatio));
            }
            return;
        }
    }
    
    @Override
    public void setCurrentMp(int i) {
        int currentMp = Math.min(i, this.getMaxMp());
        if (this.getCurrentMp() == currentMp) {
            return;
        }
        else {
            this.setCurrentMpDirect(currentMp);
            return;
        }
    }
    
    public void setCurrentPetStatus(int i) {
        this._currentPetStatus = i;
        this.set_tempModel();
        switch (this._currentPetStatus) {
            case 3: {
                this.allTargetClear();
                break;
            }
            case 5: {
                this.setHomeX(this.getX());
                this.setHomeY(this.getY());
                break;
            }
default:
    // 只有「攻擊 / 警戒狀態」才允許啟動 AI
    if ((this._currentPetStatus == 1 || this._currentPetStatus == 2)
            && !this.isAiRunning()) {
        this.startAI();
    }
    break;
}
}

    
    public int getCurrentPetStatus() {
        return this._currentPetStatus;
    }
    
    public int getItemObjId() {
        return this._itemObjId;
    }
public int getWeaponObjId() { return _weaponObjId; }
public void setWeaponObjId(int id) { _weaponObjId = id; }

public int getWeaponItemId() { return _weaponItemId; }
public void setWeaponItemId(int id) { _weaponItemId = id; }

public String getWeaponName() { return _weaponName; }
public void setWeaponName(String name) { _weaponName = name; }

public int getArmorObjId() { return _armorObjId; }
public void setArmorObjId(int id) { _armorObjId = id; }

public int getArmorItemId() { return _armorItemId; }
public void setArmorItemId(int id) { _armorItemId = id; }

public String getArmorName() { return _armorName; }
public void setArmorName(String name) { _armorName = name; }
    
    public void setExpPercent(int expPercent) {
        this._expPercent = expPercent;
    }
    
    public int getExpPercent() {
        return this._expPercent;
    }
    
    public void setWeapon(L1ItemInstance weapon) {
        this._weapon = weapon;
    }
    
    public L1ItemInstance getWeapon() {
        return this._weapon;
    }
    
    public void setArmor(L1ItemInstance armor) {
        this._armor = armor;
    }
    
    public L1ItemInstance getArmor() {
        return this._armor;
    }
    
    public void setHitByWeapon(int i) {
        this._hitByWeapon = i;
    }
    
    public int getHitByWeapon() {
        return this._hitByWeapon;
    }
    
    public void setDamageByWeapon(int i) {
        this._damageByWeapon = i;
    }
    
    public int getDamageByWeapon() {
        return this._damageByWeapon;
    }
    
    public L1PetType getPetType() {
        return this._type;
    }
    
    public void set_tempModel() {
        this._tempModel = this._currentPetStatus;
    }
    
    public void get_tempModel() {
        this._currentPetStatus = this._tempModel;
    }
    
    public void usePetWeapon(L1PetInstance pet, L1ItemInstance weapon) {
        if (pet.getWeapon() == null) {
            this.setPetWeapon(pet, weapon);
        }
        else if (pet.getWeapon().equals(weapon)) {
            this.removePetWeapon(pet, pet.getWeapon());
        }
        else {
            this.removePetWeapon(pet, pet.getWeapon());
            this.setPetWeapon(pet, weapon);
        }
    }
    
    public void usePetArmor(L1PetInstance pet, L1ItemInstance armor) {
        if (pet.getArmor() == null) {
            this.setPetArmor(pet, armor);
        }
        else if (pet.getArmor().equals(armor)) {
            this.removePetArmor(pet, pet.getArmor());
        }
        else {
            this.removePetArmor(pet, pet.getArmor());
            this.setPetArmor(pet, armor);
        }
    }
    
    private void setPetWeapon(L1PetInstance pet, L1ItemInstance weapon) {
        int itemId = weapon.getItem().getItemId();
        L1PetItem petItem = PetItemTable.get().getTemplate(itemId);
        if (petItem == null) {
            return;
        }
        else {
            pet.setHitByWeapon(petItem.getHitModifier());
            pet.setDamageByWeapon(petItem.getDamageModifier());
            pet.addStr(petItem.getAddStr());
            pet.addCon(petItem.getAddCon());
            pet.addDex(petItem.getAddDex());
            pet.addInt(petItem.getAddInt());
            pet.addWis(petItem.getAddWis());
            pet.addMaxHp(petItem.getAddHp());
            pet.addMaxMp(petItem.getAddMp());
            pet.addSp(petItem.getAddSp());
            pet.addMr(petItem.getAddMr());
            pet.setWeapon(weapon);
            weapon.setEquipped(true);
pet.setCurrentHp(Math.min(pet.getCurrentHp(), pet.getMaxHp()));
pet.setCurrentMp(Math.min(pet.getCurrentMp(), pet.getMaxMp()));
            return;
        }
    }
    
    private void removePetWeapon(L1PetInstance pet, L1ItemInstance weapon) {
        int itemId = weapon.getItem().getItemId();
        L1PetItem petItem = PetItemTable.get().getTemplate(itemId);
        if (petItem == null) {
            return;
        }
        else {
            pet.setHitByWeapon(0);
            pet.setDamageByWeapon(0);
            pet.addStr(-petItem.getAddStr());
            pet.addCon(-petItem.getAddCon());
            pet.addDex(-petItem.getAddDex());
            pet.addInt(-petItem.getAddInt());
            pet.addWis(-petItem.getAddWis());
            pet.addMaxHp(-petItem.getAddHp());
            pet.addMaxMp(-petItem.getAddMp());
            pet.addSp(-petItem.getAddSp());
            pet.addMr(-petItem.getAddMr());
            pet.setWeapon(null);
            weapon.setEquipped(false);
pet.setCurrentHp(Math.min(pet.getCurrentHp(), pet.getMaxHp()));
pet.setCurrentMp(Math.min(pet.getCurrentMp(), pet.getMaxMp()));
            return;
        }
    }
    

    private void setPetArmor(L1PetInstance pet, L1ItemInstance armor) {
        int itemId = armor.getItem().getItemId();
        L1PetItem petItem = PetItemTable.get().getTemplate(itemId);
        if (petItem == null) {
            return;
        }
        else {
            pet.addAc(petItem.getAddAc());
            pet.addStr(petItem.getAddStr());
            pet.addCon(petItem.getAddCon());
            pet.addDex(petItem.getAddDex());
            pet.addInt(petItem.getAddInt());
            pet.addWis(petItem.getAddWis());
            pet.addMaxHp(petItem.getAddHp());
            pet.addMaxMp(petItem.getAddMp());
            pet.addSp(petItem.getAddSp());
            pet.addMr(petItem.getAddMr());
            pet.setArmor(armor);
            armor.setEquipped(true);
pet.setCurrentHp(Math.min(pet.getCurrentHp(), pet.getMaxHp()));
pet.setCurrentMp(Math.min(pet.getCurrentMp(), pet.getMaxMp()));
            return;
        }
    }
    
    private void removePetArmor(L1PetInstance pet, L1ItemInstance armor) {
        int itemId = armor.getItem().getItemId();
        L1PetItem petItem = PetItemTable.get().getTemplate(itemId);
        if (petItem == null) {
            return;
        }
        else {
            pet.addAc(-petItem.getAddAc());
            pet.addStr(-petItem.getAddStr());
            pet.addCon(-petItem.getAddCon());
            pet.addDex(-petItem.getAddDex());
            pet.addInt(-petItem.getAddInt());
            pet.addWis(-petItem.getAddWis());
            pet.addMaxHp(-petItem.getAddHp());
            pet.addMaxMp(-petItem.getAddMp());
            pet.addSp(-petItem.getAddSp());
            pet.addMr(-petItem.getAddMr());
            pet.setArmor(null);
            armor.setEquipped(false);
pet.setCurrentHp(Math.min(pet.getCurrentHp(), pet.getMaxHp()));
pet.setCurrentMp(Math.min(pet.getCurrentMp(), pet.getMaxMp()));
            return;
        }
    }
    
    public void call_New() {
        if (this._type != null) {
            int id = this._type.getMessageId(L1PetType.getMessageNumber(this.getLevel()));
            if (id != 0) {
                this.broadcastPacketAll(new S_NpcChat(this, "$" + id));
            }
        }
        this.setCurrentPetStatus(1);
    }
}

package com.lineage.server.clientpackets;

import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.templates.L1PetItem;
import java.util.List;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.PetItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.WorldPet;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.datatables.lock.PetReading;

public class C_UsePetItem extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_UsePetItem.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int data = this.readC();
            int petId = this.readD();
            int listNo = this.readC();
            L1PcInstance pc = client.getActiveChar();
            if (pc == null) {
                return;
            }
            else {
                L1PetInstance pet = WorldPet.get().get(petId);
                if (pet == null) {
                    return;
                }
                else if (pc.getPetList().get((Integer)petId) == null) {
                    return;
                }
                else {
                    switch (pet.getNpcId()) {
                        case 45034:
                        case 45039:
                        case 45040:
                        case 45042:
                        case 45043:
                        case 45044:
                        case 45046:
                        case 45047:
                        case 45048:
                        case 45049:
                        case 45053:
                        case 45054:
                        case 45313:
                        case 45711:
                        case 46042:
                        case 46044: {
                            return;
                        }
                        default: {
                            L1Inventory inventory = pet.getInventory();
                            List<L1ItemInstance> itemList = inventory.getItems();
                            if (itemList.size() <= 0) {
                                return;
                            }
                            else {
if (listNo < 0 || listNo >= itemList.size()) return;
L1ItemInstance item = itemList.get(listNo);
                                if (item == null) {
                                    return;
                                }
                                else {
                                    int itemId = item.getItemId();
                                    L1PetItem petItem = PetItemTable.get().getTemplate(itemId);
                                    if (petItem != null) {
                                        if (petItem.isWeapom()) {
                                            usePetWeapon(pc, pet, item, petItem);
                                            break;
                                        }
                                        else {
                                            usePetArmor(pc, pet, item, petItem);
                                            break;
                                        }
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage(79));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    public static void usePetWeapon(L1PcInstance pc, L1PetInstance pet, L1ItemInstance weapon, L1PetItem petItem) {
        if (pet.getWeapon() == null) {
            setPetWeapon(pc, pet, weapon, petItem);
        }
        else if (pet.getWeapon().equals(weapon)) {
            removePetWeapon(pc, pet, pet.getWeapon(), petItem);
        }
        else {
            removePetWeapon(pc, pet, pet.getWeapon(), petItem);
            pc.sendPackets(new S_ItemName(pet.getWeapon()));
            setPetWeapon(pc, pet, weapon, petItem);
        }
    }
    
    public static void usePetArmor(L1PcInstance pc, L1PetInstance pet, L1ItemInstance armor, L1PetItem petItem) {
        if (pet.getArmor() == null) {
            setPetArmor(pc, pet, armor, petItem);
        }
        else if (pet.getArmor().equals(armor)) {
            removePetArmor(pc, pet, pet.getArmor(), petItem);
        }
        else {
            removePetArmor(pc, pet, pet.getArmor(), petItem);
            pc.sendPackets(new S_ItemName(pet.getArmor()));
            setPetArmor(pc, pet, armor, petItem);
        }
    }
    
    private static void setPetWeapon(L1PcInstance pc, L1PetInstance pet, L1ItemInstance weapon, L1PetItem petItem) {
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
//  放入後記錄(武器)
pet.setWeaponObjId(weapon.getId());
pet.setWeaponItemId(weapon.getItemId());
pet.setWeaponName(weapon.getLogName());
L1Pet petdb = PetReading.get().getTemplate(pet.getItemObjId());
if (petdb != null) {
    petdb.setWeaponObjId(weapon.getId());
    petdb.setWeaponItemId(weapon.getItemId());
    petdb.setWeaponName(weapon.getLogName());

    PetReading.get().storePet(petdb);
}
    }
    
    private static void removePetWeapon(L1PcInstance pc, L1PetInstance pet, L1ItemInstance weapon, L1PetItem petItem) {
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
pet.setWeaponObjId(0);
pet.setWeaponItemId(0);
pet.setWeaponName("");
L1Pet petdb = PetReading.get().getTemplate(pet.getItemObjId());
if (petdb != null) {
    petdb.setWeaponObjId(0);
    petdb.setWeaponItemId(0);
    petdb.setWeaponName("");
    PetReading.get().storePet(petdb);
}

    }
    
    private static void setPetArmor(L1PcInstance pc, L1PetInstance pet, L1ItemInstance armor, L1PetItem petItem) {
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
//  放入後記錄(防具)
pet.setArmorObjId(armor.getId());
pet.setArmorItemId(armor.getItemId());
pet.setArmorName(armor.getLogName());
L1Pet petdb = PetReading.get().getTemplate(pet.getItemObjId());
if (petdb != null) {
    petdb.setArmorObjId(armor.getId());
    petdb.setArmorItemId(armor.getItemId());
    petdb.setArmorName(armor.getLogName());

    PetReading.get().storePet(petdb);
}
    }
    
    private static void removePetArmor(L1PcInstance pc, L1PetInstance pet, L1ItemInstance armor, L1PetItem petItem) {
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
pet.setArmorObjId(0);
pet.setArmorItemId(0);
pet.setArmorName("");
L1Pet petdb = PetReading.get().getTemplate(pet.getItemObjId());
if (petdb != null) {
    petdb.setArmorObjId(0);
    petdb.setArmorItemId(0);
    petdb.setArmorName("");
    PetReading.get().storePet(petdb);
}


    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

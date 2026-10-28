package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class type18 extends ItemExecutor
{
    private int _ran;
    private int _type;
    private int _type1;
    private boolean type2;
    
    private type18() {
        this.type2 = false;
    }
    
    public static ItemExecutor get() {
        return new type18();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance l1iteminstance = pc.getInventory().getItem(targObjId);
        Random random = new Random();
        if (l1iteminstance == null) {
            return;
        }
        else if (l1iteminstance.isEquipped()) {
            pc.sendPackets(new S_ServerMessage("\\fR你必須先解除物品裝備!"));
            return;
        }
        else if (l1iteminstance.getItemStr() > 0 && !this.type2) {
            pc.sendPackets(new S_ServerMessage("\\fU該內衣以附上潘朵拉香水能力了"));
            return;
        }
        else if (l1iteminstance.getItemDex() > 0 && !this.type2) {
            pc.sendPackets(new S_ServerMessage("\\fU該內衣以附上潘朵拉香水能力了"));
            return;
        }
        else if (l1iteminstance.getItemInt() > 0 && !this.type2) {
            pc.sendPackets(new S_ServerMessage("\\fU該內衣以附上潘朵拉香水能力了"));
            return;
        }
        else if (l1iteminstance.getItemWis() > 0 && !this.type2) {
            pc.sendPackets(new S_ServerMessage("\\fU該內衣以附上潘朵拉香水能力了"));
            return;
        }
        else if (l1iteminstance.getItemCha() > 0 && !this.type2) {
            pc.sendPackets(new S_ServerMessage("\\fU該內衣以附上潘朵拉香水能力了"));
            return;
        }
        else if (l1iteminstance.getItemCon() > 0 && !this.type2) {
            pc.sendPackets(new S_ServerMessage("\\fU該內衣以附上潘朵拉香水能力了"));
            return;
        }
        else {
            switch (this._type) {
                case 1: {
                    if (l1iteminstance.getItem().getUseType() == 18) {
                        if (random.nextInt(100) + 1 <= this._ran) {
                            l1iteminstance.setItemStr(0);
                            l1iteminstance.setItemDex(0);
                            l1iteminstance.setItemInt(0);
                            l1iteminstance.setItemCon(0);
                            l1iteminstance.setItemCha(0);
                            l1iteminstance.setItemWis(0);
                            l1iteminstance.setItemStr(this._type1);
                            pc.sendPackets(new S_ServerMessage("\\fU成功在內衣上附加潘朵拉香水能力"));
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                pc.save();
                                cit.updateItemStr(l1iteminstance);
                                cit.updateItemDex(l1iteminstance);
                                cit.updateItemInt(l1iteminstance);
                                cit.updateItemCon(l1iteminstance);
                                cit.updateItemCha(l1iteminstance);
                                cit.updateItemWis(l1iteminstance);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            pc.sendPackets(new S_ItemStatus(l1iteminstance));
                            pc.getInventory().saveItem(l1iteminstance, 4);
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage("\\fU潘朵拉的香水附加失敗"));
                        }
                        pc.getInventory().removeItem(item, 1L);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("\\fU該裝備不是內衣無法使用"));
                        break;
                    }
                }
                case 2: {
                    if (l1iteminstance.getItem().getUseType() == 18) {
                        if (random.nextInt(100) + 1 <= this._ran) {
                            l1iteminstance.setItemStr(0);
                            l1iteminstance.setItemDex(0);
                            l1iteminstance.setItemInt(0);
                            l1iteminstance.setItemCon(0);
                            l1iteminstance.setItemCha(0);
                            l1iteminstance.setItemWis(0);
                            l1iteminstance.setItemDex(this._type1);
                            pc.sendPackets(new S_ServerMessage("\\fU成功在內衣上附加潘朵拉香水能力"));
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                pc.save();
                                cit.updateItemStr(l1iteminstance);
                                cit.updateItemDex(l1iteminstance);
                                cit.updateItemInt(l1iteminstance);
                                cit.updateItemCon(l1iteminstance);
                                cit.updateItemCha(l1iteminstance);
                                cit.updateItemWis(l1iteminstance);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            pc.sendPackets(new S_ItemStatus(l1iteminstance));
                            pc.getInventory().saveItem(l1iteminstance, 4);
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage("\\fU潘朵拉的香水附加失敗"));
                        }
                        pc.getInventory().removeItem(item, 1L);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("\\fU該裝備不是內衣無法使用"));
                        break;
                    }
                }
                case 3: {
                    if (l1iteminstance.getItem().getUseType() == 18) {
                        if (random.nextInt(100) + 1 <= this._ran) {
                            l1iteminstance.setItemStr(0);
                            l1iteminstance.setItemDex(0);
                            l1iteminstance.setItemInt(0);
                            l1iteminstance.setItemCon(0);
                            l1iteminstance.setItemCha(0);
                            l1iteminstance.setItemWis(0);
                            l1iteminstance.setItemInt(this._type1);
                            pc.sendPackets(new S_ServerMessage("\\fU成功在內衣上附加潘朵拉香水能力"));
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                pc.save();
                                cit.updateItemStr(l1iteminstance);
                                cit.updateItemDex(l1iteminstance);
                                cit.updateItemInt(l1iteminstance);
                                cit.updateItemCon(l1iteminstance);
                                cit.updateItemCha(l1iteminstance);
                                cit.updateItemWis(l1iteminstance);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            pc.sendPackets(new S_ItemStatus(l1iteminstance));
                            pc.getInventory().saveItem(l1iteminstance, 4);
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage("\\fU潘朵拉的香水附加失敗"));
                        }
                        pc.getInventory().removeItem(item, 1L);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("\\fU該裝備不是內衣無法使用"));
                        break;
                    }
                }
                case 4: {
                    if (l1iteminstance.getItem().getUseType() == 18) {
                        if (random.nextInt(100) + 1 <= this._ran) {
                            l1iteminstance.setItemStr(0);
                            l1iteminstance.setItemDex(0);
                            l1iteminstance.setItemInt(0);
                            l1iteminstance.setItemCon(0);
                            l1iteminstance.setItemCha(0);
                            l1iteminstance.setItemWis(0);
                            l1iteminstance.setItemCon(this._type1);
                            pc.sendPackets(new S_ServerMessage("\\fU成功在內衣上附加潘朵拉香水能力"));
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                pc.save();
                                cit.updateItemStr(l1iteminstance);
                                cit.updateItemDex(l1iteminstance);
                                cit.updateItemInt(l1iteminstance);
                                cit.updateItemCon(l1iteminstance);
                                cit.updateItemCha(l1iteminstance);
                                cit.updateItemWis(l1iteminstance);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            pc.sendPackets(new S_ItemStatus(l1iteminstance));
                            pc.getInventory().saveItem(l1iteminstance, 4);
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage("\\fU潘朵拉的香水附加失敗"));
                        }
                        pc.getInventory().removeItem(item, 1L);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("\\fU該裝備不是內衣無法使用"));
                        break;
                    }
                }
                case 5: {
                    if (l1iteminstance.getItem().getUseType() == 18) {
                        if (random.nextInt(100) + 1 <= this._ran) {
                            l1iteminstance.setItemStr(0);
                            l1iteminstance.setItemDex(0);
                            l1iteminstance.setItemInt(0);
                            l1iteminstance.setItemCon(0);
                            l1iteminstance.setItemCha(0);
                            l1iteminstance.setItemWis(0);
                            l1iteminstance.setItemCha(this._type1);
                            pc.sendPackets(new S_ServerMessage("\\fU成功在內衣上附加潘朵拉香水能力"));
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                pc.save();
                                cit.updateItemStr(l1iteminstance);
                                cit.updateItemDex(l1iteminstance);
                                cit.updateItemInt(l1iteminstance);
                                cit.updateItemCon(l1iteminstance);
                                cit.updateItemCha(l1iteminstance);
                                cit.updateItemWis(l1iteminstance);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            pc.sendPackets(new S_ItemStatus(l1iteminstance));
                            pc.getInventory().saveItem(l1iteminstance, 4);
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage("\\fU潘朵拉的香水附加失敗"));
                        }
                        pc.getInventory().removeItem(item, 1L);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("\\fU該裝備不是內衣無法使用"));
                        break;
                    }
                }
                case 6: {
                    if (l1iteminstance.getItem().getUseType() == 18) {
                        if (random.nextInt(100) + 1 <= this._ran) {
                            l1iteminstance.setItemStr(0);
                            l1iteminstance.setItemDex(0);
                            l1iteminstance.setItemInt(0);
                            l1iteminstance.setItemCon(0);
                            l1iteminstance.setItemCha(0);
                            l1iteminstance.setItemWis(0);
                            l1iteminstance.setItemWis(this._type1);
                            pc.sendPackets(new S_ServerMessage("\\fU成功在內衣上附加潘朵拉香水能力"));
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                pc.save();
                                cit.updateItemStr(l1iteminstance);
                                cit.updateItemDex(l1iteminstance);
                                cit.updateItemInt(l1iteminstance);
                                cit.updateItemCon(l1iteminstance);
                                cit.updateItemCha(l1iteminstance);
                                cit.updateItemWis(l1iteminstance);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            pc.sendPackets(new S_ItemStatus(l1iteminstance));
                            pc.getInventory().saveItem(l1iteminstance, 4);
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage("\\fU潘朵拉的香水附加失敗"));
                        }
                        pc.getInventory().removeItem(item, 1L);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("\\fU該裝備不是內衣無法使用"));
                        break;
                    }
                }
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._type = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._ran = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._type1 = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this.type2 = Boolean.parseBoolean(set[4]);
        }
        catch (Exception ex4) {}
    }
}

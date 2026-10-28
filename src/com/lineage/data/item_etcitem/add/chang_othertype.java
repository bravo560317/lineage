package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1User_Power;
import com.lineage.server.datatables.C1_Name_Table;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.datatables.lock.CharacterC1Reading;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class chang_othertype extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(chang_othertype.class);
    }
    
    private chang_othertype() {
    }
    
    public static ItemExecutor get() {
        return new chang_othertype();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItem().getItemId();
        try {
            if (pc == null) {
                return;
            }
            else if (pc.get_c_power() != null && pc.get_c_power().get_c1_type() == 0) {
                pc.sendPackets(new S_SystemMessage("您尚未加入陣營!!"));
                return;
            }
            else {
                if (itemId == 92104) {
                    if (pc.get_c_power().get_c1_type() > 0) {
                        pc.get_c_power().set_c1_type(0);
                        pc.sendPackets(new S_SystemMessage("您成功脫離了陣營"));
                        CharacterC1Reading.get().updateCharacterC1(pc.getId(), 0, "無");
                        pc.get_c_power().set_power(pc, false);
                        pc.sendPacketsAll(new S_ChangeName(pc, true));
                        pc.save();
                    }
                    pc.get_c_power().set_c1_type(1);
                    CharacterC1Reading.get().updateCharacterC1(pc.getId(), 1, "轉換-火屬");
                    pc.sendPackets(new S_SystemMessage("您以強制轉入陣營國度-火屬"));
                    pc.sendPackets(new S_SystemMessage("請登出在進來!!進行更新"));
                    this.add_type(1, pc);
                }
                else if (itemId == 92105) {
                    if (pc.get_c_power().get_c1_type() > 0) {
                        pc.get_c_power().set_c1_type(0);
                        pc.sendPackets(new S_SystemMessage("您成功脫離了陣營"));
                        CharacterC1Reading.get().updateCharacterC1(pc.getId(), 0, "無");
                        pc.get_c_power().set_power(pc, false);
                        pc.sendPacketsAll(new S_ChangeName(pc, true));
                        pc.save();
                    }
                    pc.get_c_power().set_c1_type(2);
                    CharacterC1Reading.get().updateCharacterC1(pc.getId(), 2, "轉換-風屬");
                    pc.sendPackets(new S_SystemMessage("您以強制轉入陣營國度-風屬"));
                    pc.sendPackets(new S_SystemMessage("請登出在進來!!進行更新"));
                    this.add_type2(2, pc);
                }
                else if (itemId == 92106) {
                    if (pc.get_c_power().get_c1_type() > 0) {
                        pc.get_c_power().set_c1_type(0);
                        pc.sendPackets(new S_SystemMessage("您成功脫離了陣營"));
                        CharacterC1Reading.get().updateCharacterC1(pc.getId(), 0, "無");
                        pc.get_c_power().set_power(pc, false);
                        pc.sendPacketsAll(new S_ChangeName(pc, true));
                        pc.save();
                    }
                    pc.get_c_power().set_c1_type(3);
                    CharacterC1Reading.get().updateCharacterC1(pc.getId(), 3, "轉換-地屬");
                    pc.sendPackets(new S_SystemMessage("您以強制轉入陣營國度-地屬"));
                    pc.sendPackets(new S_SystemMessage("請登出在進來!!進行更新"));
                    this.add_type3(3, pc);
                }
else if (itemId == 92107) {
    if (pc.get_c_power().get_c1_type() > 0) {
        pc.get_c_power().set_c1_type(0);
        pc.sendPackets(new S_SystemMessage("您成功脫離了陣營"));
        CharacterC1Reading.get().updateCharacterC1(pc.getId(), 0, "無");
        pc.get_c_power().set_power(pc, false);
        pc.sendPacketsAll(new S_ChangeName(pc, true));
        pc.save();
    }
    pc.get_c_power().set_c1_type(4);
    CharacterC1Reading.get().updateCharacterC1(pc.getId(), 4, "轉換-水屬");
    pc.sendPackets(new S_SystemMessage("您以強制轉入陣營國度-水屬"));
    pc.sendPackets(new S_SystemMessage("請登出在進來!!進行更新"));
    this.add_type4(4, pc);
}
                pc.getInventory().removeItem(item, 1L);
                pc.save();
            }
        }
        catch (Exception e) {
            chang_othertype._log.error(e.getLocalizedMessage(), e);
            chang_othertype._log.error("轉換陣營設定異常", e);
        }
    }
    
    private void add_type(int cmd, L1PcInstance pc) {
        int type = 1;
        String typeName = C1_Name_Table.get().get(type);
        if (pc.get_c_power() == null) {
            L1User_Power power = new L1User_Power();
            power.set_object_id(pc.getId());
            power.set_c1_type(type);
            power.set_note(typeName);
            pc.set_c_power(power);
            CharacterC1Reading.get().storeCharacterC1(pc);
        }
        else {
            pc.get_c_power().set_c1_type(type);
            pc.get_c_power().set_note(typeName);
            CharacterC1Reading.get().updateCharacterC1(pc.getId(), type, typeName);
        }
        pc.get_c_power().set_power(pc, false);
        pc.sendPacketsAll(new S_ChangeName(pc, true));
        pc.sendPackets(new S_ServerMessage("\\fR成功加入陣營：" + typeName));
    }
    
    private void add_type2(int cmd, L1PcInstance pc) {
        int type = 2;
        String typeName = C1_Name_Table.get().get(type);
        if (pc.get_c_power() == null) {
            L1User_Power power = new L1User_Power();
            power.set_object_id(pc.getId());
            power.set_c1_type(type);
            power.set_note(typeName);
            pc.set_c_power(power);
            CharacterC1Reading.get().storeCharacterC1(pc);
        }
        else {
            pc.get_c_power().set_c1_type(type);
            pc.get_c_power().set_note(typeName);
            CharacterC1Reading.get().updateCharacterC1(pc.getId(), type, typeName);
        }
        pc.get_c_power().set_power(pc, false);
        pc.sendPacketsAll(new S_ChangeName(pc, true));
        pc.sendPackets(new S_ServerMessage("\\fR成功加入陣營：" + typeName));
    }
    
    private void add_type3(int cmd, L1PcInstance pc) {
        int type = 3;
        String typeName = C1_Name_Table.get().get(type);
        if (pc.get_c_power() == null) {
            L1User_Power power = new L1User_Power();
            power.set_object_id(pc.getId());
            power.set_c1_type(type);
            power.set_note(typeName);
            pc.set_c_power(power);
            CharacterC1Reading.get().storeCharacterC1(pc);
        }
        else {
            pc.get_c_power().set_c1_type(type);
            pc.get_c_power().set_note(typeName);
            CharacterC1Reading.get().updateCharacterC1(pc.getId(), type, typeName);
        }
        pc.get_c_power().set_power(pc, false);
        pc.sendPacketsAll(new S_ChangeName(pc, true));
        pc.sendPackets(new S_ServerMessage("\\fR成功加入陣營：" + typeName));
    }
private void add_type4(int cmd, L1PcInstance pc) {
    int type = 4;
    String typeName = C1_Name_Table.get().get(type);
    if (pc.get_c_power() == null) {
        L1User_Power power = new L1User_Power();
        power.set_object_id(pc.getId());
        power.set_c1_type(type);
        power.set_note(typeName);
        pc.set_c_power(power);
        CharacterC1Reading.get().storeCharacterC1(pc);
    } else {
        pc.get_c_power().set_c1_type(type);
        pc.get_c_power().set_note(typeName);
        CharacterC1Reading.get().updateCharacterC1(pc.getId(), type, typeName);
    }
    pc.get_c_power().set_power(pc, false);
    pc.sendPacketsAll(new S_ChangeName(pc, true));
    pc.sendPackets(new S_ServerMessage("\\fR成功加入陣營：" + typeName));
}

}

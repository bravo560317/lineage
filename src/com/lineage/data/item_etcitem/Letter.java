package com.lineage.data.item_etcitem;

import com.lineage.server.datatables.sql.LetterTable;
import java.util.Calendar;
import java.util.TimeZone;
import com.lineage.config.Config;
import java.text.SimpleDateFormat;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.world.World;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Letter extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Letter.class);
    }
    
    private Letter() {
    }
    
    public static ItemExecutor get() {
        return new Letter();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        int letterCode = data[0];
        String letterReceiver = pc.getText();
        byte[] letterText = pc.getTextByte();
        if (this.writeLetter(itemId, pc, letterCode, letterReceiver, letterText)) {
            pc.getInventory().removeItem(item, 1L);
        }
    }
    
    private boolean writeLetter(int itemId, L1PcInstance pc, int letterCode, String letterReceiver, byte[] letterText) {
        int newItemId = 0;
        if (itemId == 40310) {
            newItemId = 49016;
        }
        else if (itemId == 40730) {
            newItemId = 49020;
        }
        else if (itemId == 40731) {
            newItemId = 49022;
        }
        else if (itemId == 40732) {
            newItemId = 49024;
        }
        L1ItemInstance item = ItemTable.get().createItem(newItemId);
        if (item == null) {
            return false;
        }
        else {
            item.setCount(1L);
            if (this.sendLetter(pc, letterReceiver, item, true)) {
                this.saveLetter(item.getId(), letterCode, pc.getName(), letterReceiver, letterText);
                return true;
            }
            else {
                return false;
            }
        }
    }
    
    private boolean sendLetter(L1PcInstance pc, String name, L1ItemInstance item, boolean isFailureMessage) {
        L1PcInstance target = World.get().getPlayer(name);
        if (target != null) {
            if (target.getInventory().checkAddItem(item, 1L) == 0) {
                target.getInventory().storeItem(item);
                target.sendPackets(new S_SkillSound(target.getId(), 1091));
                target.sendPackets(new S_ServerMessage(428));
            }
            else {
                if (isFailureMessage) {
                    pc.sendPackets(new S_ServerMessage(942));
                }
                return false;
            }
        }
        else {
            if (CharacterTable.doesCharNameExist(name)) {
                try {
                    int objid = CharObjidTable.get().charObjid(name);
                    CopyOnWriteArrayList<L1ItemInstance> list = CharItemsReading.get().loadItems(objid);
                    if (list.size() < 180) {
                        CharItemsReading.get().storeItem(objid, item);
                        return true;
                    }
                    else {
                        if (isFailureMessage) {
                            pc.sendPackets(new S_ServerMessage(942));
                        }
                        return false;
                    }
                }
                catch (Exception e) {
                    Letter._log.error(e.getLocalizedMessage(), e);
                    return true;
                }
            }
            if (isFailureMessage) {
                pc.sendPackets(new S_ServerMessage(109, name));
            }
            return false;
        }
        return true;
    }
    
    private void saveLetter(int itemObjectId, int code, String sender, String receiver, byte[] text) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        String date = sdf.format(Calendar.getInstance(tz).getTime());
        int spacePosition1 = 0;
        int spacePosition2 = 0;
        for (int i = 0; i < text.length; i += 2) {
            if (text[i] == 0 && text[i + 1] == 0) {
                if (spacePosition1 == 0) {
                    spacePosition1 = i;
                }
                else if (spacePosition1 != 0 && spacePosition2 == 0) {
                    spacePosition2 = i;
                    break;
                }
            }
        }
        int subjectLength = spacePosition1 + 2;
        int contentLength = spacePosition2 - spacePosition1;
        if (contentLength <= 0) {
            contentLength = 1;
        }
        byte[] subject = new byte[subjectLength];
        byte[] content = new byte[contentLength];
        System.arraycopy(text, 0, subject, 0, subjectLength);
        System.arraycopy(text, subjectLength, content, 0, contentLength);
        LetterTable.getInstance().writeLetter(itemObjectId, code, sender, receiver, date, 0, subject, content);
    }
}

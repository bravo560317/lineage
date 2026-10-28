package com.lineage.data.item_etcitem.skill;

import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class C3_WIND_Item extends ItemExecutor
{
    private static final Log _log;
    private int _itemid;
    private int _count;
    private int _time;
    
    static {
        _log = LogFactory.getLog(C3_WIND_Item.class);
    }
    
    public C3_WIND_Item() {
        this._itemid = 44070;
        this._count = 1;
        this._time = 60;
    }
    
    public static ItemExecutor get() {
        return new C3_WIND_Item();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            if (item == null) {
                return;
            }
            else if (pc == null) {
                return;
            }
            else if (pc.getLevel() < 50) {
                pc.sendPackets(new S_ServerMessage("\\fV50級以下無法使用!"));
                return;
            }
            else if (pc.hasSkillEffect(7005)) {
                pc.sendPackets(new S_ServerMessage("\\fV不能與[火轉術]共存!"));
                return;
            }
            else if (pc.hasSkillEffect(7006)) {
                pc.sendPackets(new S_ServerMessage("\\fV不能與[水攻術]共存!"));
                return;
            }
            else if (pc.hasSkillEffect(7007)) {
                pc.sendPackets(new S_ServerMessage("\\fV[風傷術]效果尚存:" + pc.getSkillEffectTimeSec(7007)));
                return;
            }
            else if (pc.hasSkillEffect(7008)) {
                pc.sendPackets(new S_ServerMessage("\\fV不能與[地氣術]共存!"));
                return;
            }
            else if (pc.hasSkillEffect(7009)) {
                pc.sendPackets(new S_ServerMessage("\\fR屬性技能尚未冷卻"));
                return;
            }
            else {
                if (this._itemid != 0) {
                    L1ItemInstance ned_item = pc.getInventory().checkItemX(this._itemid, (long)this._count);
                    if (ned_item != null) {
                        pc.getInventory().removeItem(ned_item, (long)this._count);
                    }
                    else {
                        L1Item tgItem = ItemTable.get().getTemplate(this._itemid);
                        pc.sendPackets(new S_ServerMessage(337, tgItem.getNameId()));
                        return;
                    }
                }
                pc.setSkillEffect(7009, (this._time + 30) * 1000);
                pc.setSkillEffect(7007, this._time * 1000);
                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 212));
            }
        }
        catch (Exception e) {
            C3_WIND_Item._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._itemid = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._count = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._time = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
    }
}

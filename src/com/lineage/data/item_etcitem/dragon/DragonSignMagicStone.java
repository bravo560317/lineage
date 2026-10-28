package com.lineage.data.item_etcitem.dragon;

import com.lineage.server.utils.BinaryOutputStream;
import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.server.serverpackets.S_SkillSound;
import java.sql.Timestamp;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class DragonSignMagicStone extends ItemExecutor
{
    private static final Log _log;
    private int _mode;
    private int _skillid;
    private int _gfxid;
    private int _skill_time;
    private int _itemid;
    private int _count;
    
    static {
        _log = LogFactory.getLog(DragonSignMagicStone.class);
    }
    
    public DragonSignMagicStone() {
        this._mode = -1;
        this._skillid = 0;
        this._gfxid = 0;
        this._skill_time = -1;
        this._itemid = 41246;
        this._count = 0;
    }
    
    public static ItemExecutor get() {
        return new DragonSignMagicStone();
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
            else {
                L1BuffUtil.cancelBuffStone(pc);
                int time = L1BuffUtil.cancelDragonSign(pc);
                if (time != -1) {
                    pc.sendPackets(new S_ServerMessage(1139, item.getLogName() + " " + String.valueOf(time / 60)));
                    return;
                }
                else {
                    if (this._count > 0 && this._itemid > 0) {
                        if (!pc.getInventory().checkItem(this._itemid, (long)this._count)) {
                            L1Item temp = ItemTable.get().getTemplate(this._itemid);
                            pc.sendPackets(new S_ServerMessage(337, temp.getNameId()));
                            return;
                        }
                        else {
                            pc.getInventory().consumeItem(this._itemid, (long)this._count);
                        }
                    }
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    item.setLastUsed(ts);
                    pc.getInventory().updateItem(item, 32);
                    pc.getInventory().saveItem(item, 32);
                    if (this._gfxid > 0) {
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), this._gfxid));
                    }
                    SkillMode mode = L1SkillMode.get().getSkill(this._skillid);
                    if (mode != null) {
                        mode.start(pc, null, null, this._skill_time);
                    }
                }
            }
        }
        catch (Exception e) {
            DragonSignMagicStone._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            switch (this._mode = Integer.parseInt(set[1])) {
                case 0: {
                    this._skillid = 4500;
                    break;
                }
                case 1: {
                    this._skillid = 4501;
                    break;
                }
                case 2: {
                    this._skillid = 4502;
                    break;
                }
                case 3: {
                    this._skillid = 4503;
                    break;
                }
                case 4: {
                    this._skillid = 4504;
                    break;
                }
                case 5: {
                    this._skillid = 4505;
                    break;
                }
                case 6: {
                    this._skillid = 4506;
                    break;
                }
                case 7: {
                    this._skillid = 4507;
                    break;
                }
                case 8: {
                    this._skillid = 4508;
                    break;
                }
                case 9: {
                    this._skillid = 4509;
                    break;
                }
                case 10: {
                    this._skillid = 4510;
                    break;
                }
                case 11: {
                    this._skillid = 4511;
                    break;
                }
                case 12: {
                    this._skillid = 4512;
                    break;
                }
                case 13: {
                    this._skillid = 4513;
                    break;
                }
                case 14: {
                    this._skillid = 4514;
                    break;
                }
                case 15: {
                    this._skillid = 4515;
                    break;
                }
                case 16: {
                    this._skillid = 4516;
                    break;
                }
                case 17: {
                    this._skillid = 4517;
                    break;
                }
                case 18: {
                    this._skillid = 4518;
                    break;
                }
                case 19: {
                    this._skillid = 4519;
                    break;
                }
                case 20: {
                    this._skillid = 4520;
                    break;
                }
                case 21: {
                    this._skillid = 4521;
                    break;
                }
                case 22: {
                    this._skillid = 4522;
                    break;
                }
                case 23: {
                    this._skillid = 4523;
                    break;
                }
                case 24: {
                    this._skillid = 4524;
                    break;
                }
                case 25: {
                    this._skillid = 4525;
                    break;
                }
                case 26: {
                    this._skillid = 4526;
                    break;
                }
                case 27: {
                    this._skillid = 4527;
                    break;
                }
                case 28: {
                    this._skillid = 4528;
                    break;
                }
                case 29: {
                    this._skillid = 4529;
                    break;
                }
                case 30: {
                    this._skillid = 4530;
                    break;
                }
                case 31: {
                    this._skillid = 4531;
                    break;
                }
                case 32: {
                    this._skillid = 4532;
                    break;
                }
                case 33: {
                    this._skillid = 4533;
                    break;
                }
                case 34: {
                    this._skillid = 4534;
                    break;
                }
                case 35: {
                    this._skillid = 4535;
                    break;
                }
                case 36: {
                    this._skillid = 4536;
                    break;
                }
                case 37: {
                    this._skillid = 4537;
                    break;
                }
                case 38: {
                    this._skillid = 4538;
                    break;
                }
                case 39: {
                    this._skillid = 4539;
                    break;
                }
            }
        }
        catch (Exception ex) {}
        try {
            this._skill_time = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._gfxid = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this._itemid = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
        try {
            this._count = Integer.parseInt(set[5]);
        }
        catch (Exception ex5) {}
    }
    
    @Override
    public BinaryOutputStream itemStatus(L1ItemInstance item) {
        BinaryOutputStream os = new BinaryOutputStream();
        os.writeC(23);
        os.writeC(item.getItem().getMaterial());
        os.writeD(item.getWeight());
        switch (this._mode) {
            case 0: {
                os.writeC(14);
                os.writeH(10);
                break;
            }
            case 1: {
                os.writeC(14);
                os.writeH(20);
                break;
            }
            case 2: {
                os.writeC(14);
                os.writeH(30);
                break;
            }
            case 3: {
                os.writeC(14);
                os.writeH(40);
                break;
            }
            case 4: {
                os.writeC(14);
                os.writeH(50);
                break;
            }
            case 5: {
                os.writeC(14);
                os.writeH(50);
                os.writeC(5);
                os.writeC(1);
                break;
            }
            case 6: {
                os.writeC(14);
                os.writeH(70);
                os.writeC(5);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5539 +1");
                break;
            }
            case 7: {
                os.writeC(14);
                os.writeH(70);
                os.writeC(5);
                os.writeC(1);
                os.writeC(6);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5539 +2");
                break;
            }
            case 8: {
                os.writeC(14);
                os.writeH(90);
                os.writeC(5);
                os.writeC(2);
                os.writeC(6);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5539 +3");
                break;
            }
            case 9: {
                os.writeC(14);
                os.writeH(120);
                os.writeC(5);
                os.writeC(3);
                os.writeC(6);
                os.writeC(3);
                os.writeC(8);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5539 +6");
                break;
            }
            case 10: {
                os.writeC(14);
                os.writeH(5);
                os.writeC(32);
                os.writeC(3);
                break;
            }
            case 11: {
                os.writeC(14);
                os.writeH(10);
                os.writeC(32);
                os.writeC(6);
                break;
            }
            case 12: {
                os.writeC(14);
                os.writeH(15);
                os.writeC(32);
                os.writeC(9);
                break;
            }
            case 13: {
                os.writeC(14);
                os.writeH(20);
                os.writeC(32);
                os.writeC(12);
                break;
            }
            case 14: {
                os.writeC(14);
                os.writeH(25);
                os.writeC(32);
                os.writeC(15);
                break;
            }
            case 15: {
                os.writeC(14);
                os.writeH(25);
                os.writeC(32);
                os.writeC(15);
                os.writeC(24);
                os.writeC(1);
                break;
            }
            case 16: {
                os.writeC(14);
                os.writeH(35);
                os.writeC(32);
                os.writeC(20);
                os.writeC(24);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5539 +1");
                break;
            }
            case 17: {
                os.writeC(14);
                os.writeH(35);
                os.writeC(32);
                os.writeC(22);
                os.writeC(24);
                os.writeC(1);
                os.writeC(35);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5539 +1");
                break;
            }
            case 18: {
                os.writeC(14);
                os.writeH(40);
                os.writeC(32);
                os.writeC(30);
                os.writeC(24);
                os.writeC(2);
                os.writeC(35);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5539 +2");
                os.writeC(39);
                os.writeS("$5541 +2");
                break;
            }
            case 19: {
                os.writeC(14);
                os.writeH(60);
                os.writeC(32);
                os.writeC(43);
                os.writeC(24);
                os.writeC(3);
                os.writeC(35);
                os.writeC(3);
                os.writeC(9);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5539 +3");
                os.writeC(39);
                os.writeS("$5541 +3");
                break;
            }
            case 20: {
                os.writeC(32);
                os.writeC(10);
                break;
            }
            case 21: {
                os.writeC(32);
                os.writeC(20);
                break;
            }
            case 22: {
                os.writeC(32);
                os.writeC(30);
                break;
            }
            case 23: {
                os.writeC(32);
                os.writeC(40);
                break;
            }
            case 24: {
                os.writeC(32);
                os.writeC(50);
                break;
            }
            case 25: {
                os.writeC(32);
                os.writeC(50);
                os.writeC(39);
                os.writeS("$5541 +1");
                break;
            }
            case 26: {
                os.writeC(32);
                os.writeC(55);
                os.writeC(39);
                os.writeS("$5541 +1");
                break;
            }
            case 27: {
                os.writeC(32);
                os.writeC(55);
                os.writeC(17);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5541 +2");
                break;
            }
            case 28: {
                os.writeC(32);
                os.writeC(60);
                os.writeC(17);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5541 +3");
                break;
            }
            case 29: {
                os.writeC(32);
                os.writeC(70);
                os.writeC(17);
                os.writeC(3);
                os.writeC(12);
                os.writeC(1);
                os.writeC(39);
                os.writeS("$5541 +5");
                break;
            }
            case 30: {
                os.writeC(39);
                os.writeS("$5569 +1");
                break;
            }
            case 31: {
                os.writeC(39);
                os.writeS("$5569 +2");
                break;
            }
            case 32: {
                os.writeC(39);
                os.writeS("$5569 +3");
                break;
            }
            case 33: {
                os.writeC(39);
                os.writeS("$5569 +4");
                break;
            }
            case 34: {
                os.writeC(39);
                os.writeS("$5569 +5");
                break;
            }
            case 35: {
                os.writeC(39);
                os.writeS("$5569 +5");
                os.writeC(39);
                os.writeS("$5538 +1");
                break;
            }
            case 36: {
                os.writeC(39);
                os.writeS("$5569 +5");
                os.writeC(39);
                os.writeS("$5538 +6");
                break;
            }
            case 37: {
                os.writeC(39);
                os.writeS("$5569 +5");
                os.writeC(39);
                os.writeS("$5538 +9");
                break;
            }
            case 38: {
                os.writeC(39);
                os.writeS("$5569 +5");
                os.writeC(39);
                os.writeS("$5538 +15");
                os.writeC(39);
                os.writeS("傷害減免 +2");
                os.writeC(33);
                os.writeC(214);
                os.writeC(15);
                os.writeH(2);
                os.writeC(33);
                os.writeC(5);
                break;
            }
            case 39: {
                os.writeC(39);
                os.writeS("$5569 +5");
                os.writeC(39);
                os.writeS("$5538 +21");
                os.writeC(39);
                os.writeS("傷害減免 +4");
                os.writeC(10);
                os.writeC(1);
                os.writeC(33);
                os.writeC(214);
                os.writeC(15);
                os.writeH(5);
                os.writeC(33);
                os.writeC(5);
                break;
            }
        }
        return os;
    }
}

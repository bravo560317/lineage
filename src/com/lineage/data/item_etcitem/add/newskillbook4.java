package com.lineage.data.item_etcitem.add;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class newskillbook4 extends ItemExecutor
{
    private int _count;
    private int _dmgMin;
    private int _dmgMax;
    private int _chance;
    private int _dmgMin_bon;
    private int _dmgMax_bon;
    private int _gfxId;
    private int _itemId;
    private int _itemCount;
    private Random _randon;
    
    private newskillbook4() {
        this._count = 0;
        this._dmgMin = 0;
        this._dmgMax = 0;
        this._chance = 0;
        this._dmgMin_bon = 0;
        this._dmgMax_bon = 0;
        this._gfxId = 0;
        this._itemId = 0;
        this._itemCount = 0;
        this._randon = new Random();
    }
    
    public static ItemExecutor get() {
        return new newskillbook4();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance user, L1ItemInstance item) {
        if (user == null) {
            return;
        }
        else if (item == null) {
            return;
        }
        else if (!user.getInventory().checkItem(this._itemId, (long)this._itemCount)) {
            user.sendPackets(new S_SystemMessage("施法材料不足"));
            return;
        }
        else {
            user.getInventory().consumeItem(this._itemId, (long)this._itemCount);
            user.sendPacketsAll(new S_SkillSound(user.getId(), this._gfxId));
            int dmg = 0;
            if (this._dmgMax - this._dmgMin <= 0) {
                dmg = this._dmgMin;
            }
            else {
                dmg = this._randon.nextInt(this._dmgMax - this._dmgMin) + this._dmgMin;
            }
            if (this._randon.nextInt(100) < this._chance) {
                if (this._dmgMax_bon - this._dmgMin_bon <= 0) {
                    dmg = this._dmgMin_bon;
                }
                else {
                    dmg = this._randon.nextInt(this._dmgMax_bon - this._dmgMin_bon) + this._dmgMin_bon;
                }
            }
            if (this._count > 1) {
                ArrayList<L1Object> list = World.get().getVisibleObjects(user, this._count);
                if (list != null) {
                    for (L1Object object : list) {
                        if (object instanceof L1MonsterInstance) {
                            L1MonsterInstance mob = (L1MonsterInstance)object;
                            mob.receiveDamage(user, dmg);
                            mob.broadcastPacketX10(new S_DoActionGFX(mob.getId(), 2));
                        }
                    }
                    user.sendPacketsX10(new S_SkillSound(user.getId(), this._gfxId));
                }
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._count = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._dmgMin = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._dmgMax = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this._chance = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
        try {
            this._dmgMin_bon = Integer.parseInt(set[5]);
        }
        catch (Exception ex5) {}
        try {
            this._dmgMax_bon = Integer.parseInt(set[6]);
        }
        catch (Exception ex6) {}
        try {
            this._gfxId = Integer.parseInt(set[7]);
        }
        catch (Exception ex7) {}
        try {
            this._itemId = Integer.parseInt(set[8]);
        }
        catch (Exception ex8) {}
        try {
            this._itemCount = Integer.parseInt(set[9]);
        }
        catch (Exception ex9) {}
    }
}

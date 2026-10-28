package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Firework extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Firework();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        int soundid = 2046;
        switch (itemId) {
            case 40136: {
                soundid = 2046;
                break;
            }
            case 40137: {
                soundid = 2047;
                break;
            }
            case 40138: {
                soundid = 2048;
                break;
            }
            case 40139: {
                soundid = 2040;
                break;
            }
            case 40140: {
                soundid = 2051;
                break;
            }
            case 40141: {
                soundid = 2028;
                break;
            }
            case 40142: {
                soundid = 2036;
                break;
            }
            case 40143: {
                soundid = 2041;
                break;
            }
            case 40144: {
                soundid = 2053;
                break;
            }
            case 40145: {
                soundid = 2029;
                break;
            }
            case 40146: {
                soundid = 2039;
                break;
            }
            case 40147: {
                soundid = 2045;
                break;
            }
            case 40148: {
                soundid = 2043;
                break;
            }
            case 40149: {
                soundid = 2034;
                break;
            }
            case 40150: {
                soundid = 2055;
                break;
            }
            case 40151: {
                soundid = 2032;
                break;
            }
            case 40152: {
                soundid = 2031;
                break;
            }
            case 40153: {
                soundid = 2038;
                break;
            }
            case 40154: {
                soundid = 3198;
                break;
            }
            case 40155: {
                soundid = 2044;
                break;
            }
            case 40156: {
                soundid = 2042;
                break;
            }
            case 40157: {
                soundid = 2035;
                break;
            }
            case 40158: {
                soundid = 2049;
                break;
            }
            case 40159: {
                soundid = 2033;
                break;
            }
            case 40160: {
                soundid = 2030;
                break;
            }
            case 40161: {
                soundid = 2037;
                break;
            }
        }
        pc.sendPacketsAll(new S_SkillSound(pc.getId(), soundid));
        pc.getInventory().removeItem(item, 1L);
    }
}

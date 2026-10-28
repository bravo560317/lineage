package com.lineage.data.item_etcitem.power;

import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class PanaceaPotion extends ItemExecutor
{
    private static final Log _log;
    private String[] _panaceaList;
    private int _value;
    
    static {
        _log = LogFactory.getLog(PanaceaPotion.class);
    }
    
    private PanaceaPotion() {
    }
    
    public static ItemExecutor get() {
        return new PanaceaPotion();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int checkPoint = 0;
        String[] panaceaList;
        for (int length = (panaceaList = this._panaceaList).length, i = 0; i < length; ++i) {
            String value = panaceaList[i];
            if (value.equalsIgnoreCase("STR")) {
                if (pc.getBaseStr() >= this._value) {
                    ++checkPoint;
                }
            }
            else if (value.equalsIgnoreCase("CON")) {
                if (pc.getBaseCon() >= this._value) {
                    ++checkPoint;
                }
            }
            else if (value.equalsIgnoreCase("DEX")) {
                if (pc.getBaseDex() >= this._value) {
                    ++checkPoint;
                }
            }
            else if (value.equalsIgnoreCase("CHA")) {
                if (pc.getBaseCha() >= this._value) {
                    ++checkPoint;
                }
            }
            else if (value.equalsIgnoreCase("INT")) {
                if (pc.getBaseInt() >= this._value) {
                    ++checkPoint;
                }
            }
            else if (value.equalsIgnoreCase("WIS") && pc.getBaseWis() >= this._value) {
                ++checkPoint;
            }
        }
        if (checkPoint >= this._panaceaList.length) {
            pc.sendPackets(new S_SystemMessage("你的能力值已經達到要求了。"));
            return;
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            StringBuilder sbr = new StringBuilder();
            sbr.append("你的");
            String[] panaceaList2;
            for (int length2 = (panaceaList2 = this._panaceaList).length, j = 0; j < length2; ++j) {
                String value2 = panaceaList2[j];
                if (value2.equalsIgnoreCase("STR")) {
                    pc.addBaseStr(this._value - pc.getBaseStr());
                    sbr.append("[力量]");
                }
                else if (value2.equalsIgnoreCase("CON")) {
                    pc.addBaseCon(this._value - pc.getBaseCon());
                    sbr.append("[體質]");
                }
                else if (value2.equalsIgnoreCase("DEX")) {
                    pc.addBaseDex(this._value - pc.getBaseDex());
                    sbr.append("[敏捷]");
                }
                else if (value2.equalsIgnoreCase("CHA")) {
                    pc.addBaseCha(this._value - pc.getBaseCha());
                    sbr.append("[魅力]");
                }
                else if (value2.equalsIgnoreCase("INT")) {
                    pc.addBaseInt(this._value - pc.getBaseInt());
                    sbr.append("[智力]");
                }
                else if (value2.equalsIgnoreCase("WIS")) {
                    pc.addBaseWis(this._value - pc.getBaseWis());
                    sbr.append("[精神]");
                }
            }
            pc.sendPackets(new S_OwnCharStatus2(pc));
            sbr.append("已成功提昇至[").append(this._value).append("]點。");
            pc.sendPackets(new S_SystemMessage(sbr.toString()));
            try {
                pc.save();
            }
            catch (Exception e) {
                PanaceaPotion._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._panaceaList = set[1].split("/");
            this._value = Integer.parseInt(set[2]);
        }
        catch (Exception ex) {}
    }
}

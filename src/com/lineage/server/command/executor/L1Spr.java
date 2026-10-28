package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Spr implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Spr.class);
    }
    
    private L1Spr() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Spr();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            int sprid = Integer.parseInt(arg);
            int attack1 = SprTable.get().getAttackSpeed(sprid, 1);
            int attack2 = SprTable.get().getAttackSpeed(sprid, 5);
            int attack3 = SprTable.get().getAttackSpeed(sprid, 12);
            int attack4 = SprTable.get().getAttackSpeed(sprid, 21);
            int attack5 = SprTable.get().getAttackSpeed(sprid, 25);
            int attack6 = SprTable.get().getAttackSpeed(sprid, 41);
            int attack7 = SprTable.get().getAttackSpeed(sprid, 47);
            int attack8 = SprTable.get().getAttackSpeed(sprid, 51);
            int attack9 = SprTable.get().getAttackSpeed(sprid, 55);
            int attack10 = SprTable.get().getAttackSpeed(sprid, 59);
            int attack11 = SprTable.get().getAttackSpeed(sprid, 63);
            int move = SprTable.get().getMoveSpeed(sprid, 0);
            int dmg = SprTable.get().getDmg(sprid);
            int attack12 = SprTable.get().getDirSpellSpeed(sprid);
            int attack13 = SprTable.get().getNodirSpellSpeed(sprid);
            int attack14 = SprTable.get().getDirSpellSpeed30(sprid);
            String info = "sprid:" + sprid + "\n\r 走路速度:" + move + "\n\r 攻擊速度-空手:" + attack1 + "\n\r 攻擊速度-單手劍:" + attack2 + "\n\r 攻擊速度-斧頭:" + attack3 + "\n\r 攻擊速度-弓:" + attack4 + "\n\r 攻擊速度-矛:" + attack5 + "\n\r 攻擊速度-杖:" + attack6 + "\n\r 攻擊速度-匕首:" + attack7 + "\n\r 攻擊速度-雙手劍:" + attack8 + "\n\r 攻擊速度-雙刀:" + attack9 + "\n\r 攻擊速度-爪:" + attack10 + "\n\r 攻擊速度-鐵手甲:" + attack11 + "\n\r 受傷動作速度:" + dmg + "\n\r 有向施法速度:" + attack12 + "\n\r 無向施法速度:" + attack13 + "\n\r 特攻30速度:" + attack14;
            if (pc == null) {
                L1Spr._log.warn("系統命令執行: spr" + sprid + "\n\r" + info);
            }
            else {
                pc.sendPackets(new S_SystemMessage(info));
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1Spr._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1Spr._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}

package com.add;

import com.lineage.data.npc.other.exp_game09;
import com.lineage.data.npc.other.exp_game08;
import com.lineage.data.npc.other.exp_game07;
import com.lineage.data.npc.other.exp_game06;
import com.lineage.data.npc.other.exp_game05;
import com.lineage.data.npc.other.exp_game04;
import com.lineage.data.npc.other.exp_game03;
import com.lineage.data.npc.other.exp_game02;
import com.lineage.data.npc.other.exp_game01;
import com.lineage.data.npc.other.exp_game9;
import com.lineage.data.npc.other.exp_game8;
import com.lineage.data.npc.other.exp_game7;
import com.lineage.data.npc.other.exp_game6;
import com.lineage.data.npc.other.exp_game5;
import com.lineage.data.npc.other.exp_game4;
import com.lineage.data.npc.other.exp_game3;
import com.lineage.data.npc.other.exp_game2;
import com.lineage.data.npc.other.exp_game1;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

public class ExpFb
{
    private static Logger _log;
    private static ExpFb _instance;
    
    static {
        ExpFb._log = Logger.getLogger(ExpFb.class.getName());
    }
    
    public static ExpFb getInstance() {
        if (ExpFb._instance == null) {
            ExpFb._instance = new ExpFb();
        }
        return ExpFb._instance;
    }
    
    public void PcCommand(L1PcInstance pc, String cmd) {
        if (cmd.equalsIgnoreCase("exp_game1")) {
            pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
            exp_game1.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game2")) {
            exp_game2.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game3")) {
            exp_game3.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game4")) {
            exp_game4.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game5")) {
            exp_game5.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game6")) {
            exp_game6.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game7")) {
            exp_game7.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game8")) {
            exp_game8.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game9")) {
            exp_game9.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game01")) {
            exp_game01.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game02")) {
            exp_game02.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game03")) {
            exp_game03.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game04")) {
            exp_game04.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game05")) {
            exp_game05.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game06")) {
            exp_game06.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game07")) {
            exp_game07.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game08")) {
            exp_game08.getInstance().enterTextTask(pc);
        }
        else if (cmd.equalsIgnoreCase("exp_game09")) {
            exp_game09.getInstance().enterTextTask(pc);
        }
    }
}

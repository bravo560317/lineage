package com.lineage.server.command.executor;

import com.lineage.server.model.map.L1Map;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SetMap implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1SetMap.class);
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1SetMap();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            if (tok.hasMoreTokens()) {
                if (tok.nextToken().equalsIgnoreCase("1")) {
                    if (tok.hasMoreTokens()) {
                        int and = Integer.parseInt(tok.nextToken());
                        if (tok.hasMoreTokens()) {
                            int and2 = Integer.parseInt(tok.nextToken());
                            this.set_src_map(15, pc, and, and2);
                        }
                        else {
                            this.set_src_map(15, pc, and);
                        }
                    }
                    else {
                        this.set_src_map(8, pc);
                    }
                }
            }
            else {
                this.set_map(15, pc);
            }
        }
        catch (Exception e) {
            L1SetMap._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
    
    private void set_src_map(int hc, L1PcInstance pc, int and, int and2) {
        int x = pc.getX();
        int y = pc.getY();
        int x2 = x - hc;
        int y2 = y - hc;
        int x3 = x + hc;
        int y3 = y + hc;
        int rows = x3 - x2;
        int columns = y3 - y2;
        L1Map map = pc.getMap();
        System.out.println("==============================================================================");
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int cx = x2 + i;
                int cy = y2 + j;
                if (cx == x && cy == y) {
                    System.out.print("[]");
                }
                else {
                    int gab = map.getOriginalTile(cx, cy);
                    int a = gab & and;
                    int a2 = gab & and2;
                    if (a == 0 && a2 == 0) {
                        System.out.print("##");
                    }
                    else {
                        System.out.print("  ");
                    }
                }
            }
            System.out.print("\n");
        }
    }
    
    private void set_src_map(int hc, L1PcInstance pc, int and) {
        int x = pc.getX();
        int y = pc.getY();
        int x2 = x - hc;
        int y2 = y - hc;
        int x3 = x + hc;
        int y3 = y + hc;
        int rows = x3 - x2;
        int columns = y3 - y2;
        L1Map map = pc.getMap();
        System.out.println("==============================================================================");
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int cx = x2 + i;
                int cy = y2 + j;
                if (cx == x && cy == y) {
                    System.out.print("[]");
                }
                else {
                    int gab = map.getOriginalTile(cx, cy);
                    int a = gab & and;
                    if (a == 0) {
                        System.out.print("##");
                    }
                    else {
                        System.out.print("  ");
                    }
                }
            }
            System.out.print("\n");
        }
    }
    
    private void set_src_map(int hc, L1PcInstance pc) {
        int x = pc.getX();
        int y = pc.getY();
        int x2 = x - hc;
        int y2 = y - hc;
        int x3 = x + hc;
        int y3 = y + hc;
        int rows = x3 - x2;
        int columns = y3 - y2;
        L1Map map = pc.getMap();
        System.out.println("==============================================================================");
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int cx = x2 + i;
                int cy = y2 + j;
                if (cx == x && cy == y) {
                    System.out.print("[] ");
                }
                else {
                    int gab = map.getOriginalTile(cx, cy);
                    if (gab == 0) {
                        System.out.print("## ");
                    }
                    else {
                        System.out.print(String.valueOf((gab < 10) ? "0" : "") + gab + " ");
                    }
                }
            }
            System.out.print("\n");
        }
    }
    
    private void set_map(int hc, L1PcInstance pc) {
        int x = pc.getX();
        int y = pc.getY();
        int x2 = x - hc;
        int y2 = y - hc;
        int x3 = x + hc;
        int y3 = y + hc;
        int rows = x3 - x2;
        int columns = y3 - y2;
        L1Map map = pc.getMap();
        System.out.println("==============================================================================");
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int cx = x2 + i;
                int cy = y2 + j;
                if (cx == x && cy == y) {
                    System.out.print("[]");
                }
                else if (!map.isPassable(cx, cy, pc)) {
                    System.out.print("##");
                }
                else {
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }
}

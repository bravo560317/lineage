package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_GmMessage;
import com.lineage.server.world.World;
import com.lineage.config.ConfigRate;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1ChangeDrop implements L1CommandExecutor
{
    private Thread changeExpThread;
    
    private L1ChangeDrop() {
        this.changeExpThread = null;
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ChangeDrop();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int index = Integer.parseInt(st.nextToken());
            String arg2 = null;
            String arg3 = null;
            if (index >= 0) {
                StringTokenizer token = new StringTokenizer(arg, " ");
                if (token.hasMoreTokens()) {
                    arg2 = token.nextToken().trim();
                }
                if (token.hasMoreTokens()) {
                    arg3 = token.nextToken().trim();
                }
            }
            if (arg2 != null && !arg2.equals("")) {
                final double rateDrop = Double.parseDouble(arg2);
                final double oldrateDrop = ConfigRate.RATE_DROP_ITEMS;
                if (arg3 != null && !arg3.equals("")) {
                    final int interval = Integer.parseInt(arg3) * 60 * 1000;
                    if (this.changeExpThread == null) {
                        (this.changeExpThread = new Thread() {
                            @Override
                            public void run() {
                                ConfigRate.RATE_DROP_ITEMS = rateDrop;
                                try {
                                    Thread.sleep((long)interval);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                }
                                ConfigRate.RATE_DROP_ITEMS = oldrateDrop;
                            }
                        }).start();
                    }
                    else {
                        this.changeExpThread.interrupt();
                        (this.changeExpThread = new Thread() {
                            @Override
                            public void run() {
                                ConfigRate.RATE_DROP_ITEMS = rateDrop;
                                try {
                                    Thread.sleep((long)interval);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                }
                                ConfigRate.RATE_DROP_ITEMS = oldrateDrop;
                            }
                        }).start();
                    }
                }
                else {
                    ConfigRate.RATE_DROP_ITEMS = rateDrop;
                }
                try {
                    Thread.sleep(400L);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                World.get().broadcastPacketToAll(new S_GmMessage("掉寶倍率變更為" + ConfigRate.RATE_DROP_ITEMS + "倍。", "\\aE"));
            }
            else {
                World.get().broadcastPacketToAll(new S_GmMessage("掉寶倍率變更為" + ConfigRate.RATE_DROP_ITEMS + "倍。", "\\aE"));
            }
        }
        catch (Exception e2) {
            pc.sendPackets(new S_SystemMessage("請輸入『.drop 倍率』。"));
        }
    }
}

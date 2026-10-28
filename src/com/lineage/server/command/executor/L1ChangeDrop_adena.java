package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.config.ConfigRate;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1ChangeDrop_adena implements L1CommandExecutor
{
    private Thread changeExpThread;
    
    private L1ChangeDrop_adena() {
        this.changeExpThread = null;
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ChangeDrop_adena();
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
                final double rateDrop_adena = Double.parseDouble(arg2);
                final double oldrateDrop_adena = ConfigRate.RATE_DROP_ADENA;
                if (arg3 != null && !arg3.equals("")) {
                    final int interval = Integer.parseInt(arg3) * 60 * 1000;
                    if (this.changeExpThread == null) {
                        (this.changeExpThread = new Thread() {
                            @Override
                            public void run() {
                                ConfigRate.RATE_DROP_ADENA = rateDrop_adena;
                                try {
                                    Thread.sleep((long)interval);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                }
                                ConfigRate.RATE_DROP_ADENA = oldrateDrop_adena;
                            }
                        }).start();
                    }
                    else {
                        this.changeExpThread.interrupt();
                        (this.changeExpThread = new Thread() {
                            @Override
                            public void run() {
                                ConfigRate.RATE_DROP_ADENA = rateDrop_adena;
                                try {
                                    Thread.sleep((long)interval);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                }
                                ConfigRate.RATE_DROP_ADENA = oldrateDrop_adena;
                            }
                        }).start();
                    }
                }
                else {
                    ConfigRate.RATE_DROP_ADENA = rateDrop_adena;
                }
                try {
                    Thread.sleep(400L);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                World.get().broadcastServerMessage("系統公告: 掉落金幣已調整為【" + ConfigRate.RATE_DROP_ITEMS + " 】" + "倍" + "。");
                pc.sendPackets(new S_SystemMessage("掉落金幣已調整為【" + ConfigRate.RATE_DROP_ITEMS + " 】" + "倍" + "。"));
            }
            else {
                pc.sendPackets(new S_SystemMessage("目前的掉落金幣為【" + ConfigRate.RATE_DROP_ITEMS + " 】" + "倍" + "。"));
            }
        }
        catch (Exception e2) {
            pc.sendPackets(new S_SystemMessage("請輸入『.drop 倍率』。"));
        }
    }
}

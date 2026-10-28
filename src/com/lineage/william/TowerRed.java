package com.lineage.william;

import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.datatables.DoorSpawnTable;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class TowerRed
{
    private final ArrayList<L1PcInstance> _members;
    private static TowerRed _instance;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_READY = 1;
    public static final int STATUS_PLAYING = 2;
    public static final int STATUS_CLEANING = 4;
    public static final int _minPlayer = 2;
    public static final int _maxPlayer = 50;
    private static final int _adena = 500000;
    private static final int _count = 1;
    private int _TowerRedStatus;
    
    public TowerRed() {
        this._members = new ArrayList();
        this._TowerRedStatus = 0;
    }
    
    public static TowerRed getInstance() {
        if (TowerRed._instance == null) {
            TowerRed._instance = new TowerRed();
        }
        return TowerRed._instance;
    }
    
    public String enterTowerRed(L1PcInstance pc) {
        if (getInstance().getTowerRedStatus() == 4) {
            pc.sendPackets(new S_SystemMessage("搶塔遊戲正在重新啟動中。"));
            return "";
        }
        else if (getInstance().getTowerRedStatus() == 2 && !this.isMember(pc)) {
            pc.sendPackets(new S_ServerMessage(1182));
            return "";
        }
        else if (this.getMembersCount() >= 2 && !this.isMember(pc)) {
            pc.sendPackets(new S_SystemMessage("人數已經滿了，請等下一場。"));
            return "";
        }
        else if (!pc.getInventory().checkItem(40308, 500000L) && !this.isMember(pc)) {
            pc.sendPackets(new S_SystemMessage("金幣不足"));
            return "";
        }
        else {
            if (!this.isMember(pc)) {
                pc.setTowerIsWhat(1);
            }
            L1Teleport.teleport(pc, 32857, 32678, (short)511, pc.getHeading(), true);
            this.addMember(pc);
            this.setDoorClose(true);
            return "";
        }
    }
    
    private void addMember(L1PcInstance pc) {
        if (!this._members.contains(pc)) {
            this._members.add(pc);
            pc.getInventory().consumeItem(40308, 500000L);
        }
        if (this.getMembersCount() == 1 && this.getTowerRedStatus() == 0) {
            GeneralThreadPool.get().execute(new TowerRed.runTowerRed());
        }
    }
    
    public void endTowerRed() {
        this.setTowerRedStatus(0);
        this.sendMessage("活動結束，請下次再來");
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            pc.setTowerIsWhat(0);
            pc.setPinkName(false);
            L1Teleport.teleport(pc, 33089, 33396, (short)4, pc.getHeading(), true);
        }
        this.clearMembers();
        this.setTowerRedStatus(0);
    }
    
    private void sendMessage(String msg) {
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            if (pc.getMapId() == 511) {
                pc.sendPackets(new S_BlueMessage(166, "\\f3" + msg));
            }
        }
    }
    
    private void setTowerRedStatus(int i) {
        this._TowerRedStatus = i;
    }
    
    private int getTowerRedStatus() {
        return this._TowerRedStatus;
    }
    
    private void setDoorClose(boolean isClose) {
        L1DoorInstance[] list = DoorSpawnTable.get().getDoorList();
        L1DoorInstance[] array;
        for (int length = (array = list).length, i = 0; i < length; ++i) {
            L1DoorInstance door = array[i];
            if (door.getMapId() == 511) {
                if (isClose) {
                    door.close();
                }
                else {
                    door.open();
                }
            }
        }
    }
    
    public void clearMembers() {
        this._members.clear();
    }
    
    public boolean isMember(L1PcInstance pc) {
        return this._members.contains(pc);
    }
    
    public L1PcInstance[] getMembersArray() {
        return (L1PcInstance[])this._members.toArray(new L1PcInstance[this._members.size()]);
    }
    
    public int getMembersCount() {
        return this._members.size();
    }
    
    private class runTowerRed implements Runnable
    {
        @Override
        public void run() {
            try {
                TowerRed.this.setTowerRedStatus(1);
                Thread.sleep(10000L);
                TowerRed.this.sendMessage("2分鐘後開始搶塔活動，請做好『組隊』準備");
                Thread.sleep(60000L);
                TowerRed.this.sendMessage("1分鐘後開始搶塔活動，請做好『組隊』準備");
                Thread.sleep(50000L);
                TowerRed.this.sendMessage("倒數10秒");
                Thread.sleep(5000L);
                TowerRed.this.sendMessage("倒數5秒");
                Thread.sleep(1000L);
                TowerRed.this.sendMessage("4秒");
                Thread.sleep(1000L);
                TowerRed.this.sendMessage("3秒");
                Thread.sleep(1000L);
                TowerRed.this.sendMessage("2秒");
                Thread.sleep(1000L);
                TowerRed.this.sendMessage("1秒");
                Thread.sleep(1000L);
                TowerRed.this.sendMessage("籃隊【" + TowerBlue.getInstance().getMembersCount() + "】人 VS 紅隊【" + TowerRed.this.getMembersCount() + "】人");
                TowerRed.this.setDoorClose(false);
                TowerRed.this.setTowerRedStatus(2);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

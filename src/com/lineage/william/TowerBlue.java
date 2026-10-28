package com.lineage.william;

import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.datatables.DoorSpawnTable;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class TowerBlue
{
    private final ArrayList<L1PcInstance> _members;
    private static TowerBlue _instance;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_READY = 1;
    public static final int STATUS_PLAYING = 2;
    public static final int STATUS_CLEANING = 4;
    public static final int _minPlayer = 2;
    public static final int _maxPlayer = 50;
    private int _TowerBlueStatus;
    
    public TowerBlue() {
        this._members = new ArrayList();
        this._TowerBlueStatus = 0;
    }
    
    public static TowerBlue getInstance() {
        if (TowerBlue._instance == null) {
            TowerBlue._instance = new TowerBlue();
        }
        return TowerBlue._instance;
    }
    
    public String enterTowerBlue(L1PcInstance pc) {
        this.setDoorClose(true);
        if (getInstance().getTowerBlueStatus() == 4) {
            pc.sendPackets(new S_SystemMessage("搶塔遊戲正在重新啟動中。"));
            return "";
        }
        else if (getInstance().getTowerBlueStatus() == 2 && !this.isMember(pc)) {
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
                pc.setTowerIsWhat(2);
            }
            L1Teleport.teleport(pc, 32862, 32599, (short)511, pc.getHeading(), true);
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
        if (this.getMembersCount() == 1 && this.getTowerBlueStatus() == 0) {
            GeneralThreadPool.get().execute(new TowerBlue.runTowerBlue());
        }
    }
    
    public void endTowerBlue() {
        this.setTowerBlueStatus(0);
        this.sendMessage("活動結束，請下次再來");
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            pc.setTowerIsWhat(0);
            L1Teleport.teleport(pc, 33089, 33396, (short)4, 0, true);
        }
        this.clearMembers();
        this.setTowerBlueStatus(0);
    }
    
    private void sendMessage(String msg) {
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            if (pc.getMapId() == 511) {
                pc.sendPackets(new S_SystemMessage("\\f3" + msg));
            }
        }
    }
    
    private void setTowerBlueStatus(int i) {
        this._TowerBlueStatus = i;
    }
    
    private int getTowerBlueStatus() {
        return this._TowerBlueStatus;
    }
    
    public void clearMembers() {
        this._members.clear();
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
    
    public boolean isMember(L1PcInstance pc) {
        return this._members.contains(pc);
    }
    
    public L1PcInstance[] getMembersArray() {
        return (L1PcInstance[])this._members.toArray(new L1PcInstance[this._members.size()]);
    }
    
    public int getMembersCount() {
        return this._members.size();
    }
    
    private class runTowerBlue implements Runnable
    {
        @Override
        public void run() {
            try {
                TowerBlue.this.setTowerBlueStatus(1);
                Thread.sleep(10000L);
                TowerBlue.this.sendMessage("2分鐘後開始搶塔活動，請做好『組隊』準備");
                Thread.sleep(60000L);
                TowerBlue.this.sendMessage("1分鐘後開始搶塔活動，請做好『組隊』準備");
                Thread.sleep(50000L);
                TowerBlue.this.sendMessage("倒數10秒");
                Thread.sleep(5000L);
                TowerBlue.this.sendMessage("倒數5秒");
                Thread.sleep(1000L);
                TowerBlue.this.sendMessage("4秒");
                Thread.sleep(1000L);
                TowerBlue.this.sendMessage("3秒");
                Thread.sleep(1000L);
                TowerBlue.this.sendMessage("2秒");
                Thread.sleep(1000L);
                TowerBlue.this.sendMessage("1秒");
                Thread.sleep(1000L);
                World.get().broadcastPacketToAll(new S_SystemMessage("鬥塔活動已經開始，停止報名。"));
                TowerBlue.this.sendMessage("籃隊【" + TowerBlue.this.getMembersCount() + "】人 VS 紅隊【" + TowerRed.getInstance().getMembersCount() + "】人");
                TowerBlue.this.setDoorClose(false);
                TowerBlue.this.setTowerBlueStatus(2);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

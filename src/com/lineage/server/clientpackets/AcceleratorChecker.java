package com.lineage.server.clientpackets;

import com.lineage.server.datatables.SprTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.lock.IpReading;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_ToGmMessage;
import com.lineage.server.world.World;
import com.lineage.config.ConfigOther;
import org.apache.commons.logging.LogFactory;
import java.util.EnumMap;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public class AcceleratorChecker
{
    private static final Log _log;
    private final L1PcInstance _pc;
    private int _injusticeCount;
    private int _justiceCount;
    private static final int INJUSTICE_COUNT_LIMIT = 10;
    private static final int JUSTICE_COUNT_LIMIT = 4;
    public static double CHECK_STRICTNESS;
    private static final double HASTE_RATE = 0.75;
    private static final double WAFFLE_RATE = 0.87;
    private static final double DOUBLE_HASTE_RATE = 0.375;
    private static final double STANDARD_RATE = 1.0;
    private final EnumMap<AcceleratorChecker.ACT_TYPE, Long> _actTimers;
    private final EnumMap<AcceleratorChecker.ACT_TYPE, Long> _checkTimers;
    public static final int R_OK = 0;
    public static final int R_DETECTED = 1;
    public static final int R_DISPOSED = 2;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE;
    
    static {
        _log = LogFactory.getLog(AcceleratorChecker.class);
        AcceleratorChecker.CHECK_STRICTNESS = ConfigOther.SPEED_TIME;
    }
    
    public AcceleratorChecker(L1PcInstance pc) {
        this._actTimers = new EnumMap(AcceleratorChecker.ACT_TYPE.class);
        this._checkTimers = new EnumMap(AcceleratorChecker.ACT_TYPE.class);
        this._pc = pc;
        this._injusticeCount = 0;
        this._justiceCount = 0;
        long now = System.currentTimeMillis();
        AcceleratorChecker.ACT_TYPE[] values;
        for (int length = (values = AcceleratorChecker.ACT_TYPE.values()).length, i = 0; i < length; ++i) {
            AcceleratorChecker.ACT_TYPE each = values[i];
            this._actTimers.put(each, now);
            this._checkTimers.put(each, now);
        }
    }
    
    private void toGmErrMsg(String name, int count) {
        try {
            if (count >= 5) {
                Collection<L1PcInstance> allPc = World.get().getAllPlayers();
                for (L1PcInstance tgpc : allPc) {
                    if (tgpc.isGm()) {
                        tgpc.sendPackets(new S_ToGmMessage("人物:" + name + " 速度異常!(" + count + "次)"));
                    }
                }
            }
        }
        catch (Exception e) {
            AcceleratorChecker._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void toGmKickMsg(String name) {
        try {
            Collection<L1PcInstance> allPc = World.get().getAllPlayers();
            for (L1PcInstance tgpc : allPc) {
                if (tgpc.isGm()) {
                    tgpc.sendPackets(new S_ToGmMessage("人物:" + name + " 速度異常斷線!"));
                }
            }
        }
        catch (Exception e) {
            AcceleratorChecker._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int checkInterval(AcceleratorChecker.ACT_TYPE type) {
        if (!ConfigOther.SPEED) {
            return 0;
        }
        else {
            int result = 0;
            long now = System.currentTimeMillis();
            long interval = now - this._actTimers.get(type);
            int rightInterval = this.getRightInterval(type);
            interval = (long)((double)interval * AcceleratorChecker.CHECK_STRICTNESS);
            double rate = (double)interval / (double)rightInterval;
            if (0.0 < rate && rate < 1.0) {
                ++this._injusticeCount;
                this.toGmErrMsg(this._pc.getName(), this._injusticeCount);
                this._justiceCount = 0;
                if (this._injusticeCount >= 10) {
                    this.doPunishment(ConfigOther.ILLEGAL_SPEEDUP_PUNISHMENT);
                    return 2;
                }
                else {
                    result = 1;
                }
            }
            else if (rate >= 1.0) {
                ++this._justiceCount;
                if (this._justiceCount >= 4) {
                    this._injusticeCount = 0;
                    this._justiceCount = 0;
                }
            }
            this._actTimers.put(type, now);
            return result;
        }
    }
    
    private void doPunishment(int punishmaent) {
        if (!this._pc.isGm()) {
            int x = this._pc.getX();
            int y = this._pc.getY();
            int mapid = this._pc.getMapId();
            switch (punishmaent) {
                case 0: {
                    this._pc.setSpeedError(this._pc.getSpeedError() + 1);
                    if (this._pc.getSpeedError() >= 10) {
                        long time = -1702967296L;
                        Timestamp UnbanTime = new Timestamp(System.currentTimeMillis() + time);
                        IpReading.get().setUnbanTime(UnbanTime);
                        IpReading.get().add(this._pc.getAccountName(), "加速器檢測 自動封鎖帳號三十日");
                        this._pc.saveInventory();
                        RecordTable.get().r_speed(this._pc.getName(), "偵測異常(攻速or走速");
                        this._pc.sendPackets(new S_Disconnect());
                        World.get().broadcastServerMessage("玩家 : " + this._pc.getName() + " 因當日加速器檢測斷線十次，已封鎖其帳號三十日。");
                        AcceleratorChecker._log.info(String.format("玩家 : %s 因當日加速器檢測斷線十次，已封鎖其帳號三十日。", this._pc.getName()));
                        break;
                    }
                    else {
                        this._pc.sendPackets(new S_ServerMessage(945));
                        this._pc.saveInventory();
                        this._pc.sendPackets(new S_Disconnect());
                        StringBuilder name = new StringBuilder();
                        name.append(this._pc.getName());
                        this.toGmKickMsg(name.toString());
                        RecordTable.get().r_speed(this._pc.getName(), "偵測異常(攻速or走速");
                        World.get().broadcastServerMessage("玩家 : " + this._pc.getName() + " 因為檢測到加速器，已強制切斷其連線。");
                        AcceleratorChecker._log.info(String.format("因為檢測到%s正在使用加速器，強制切斷其連線。", this._pc.getName()));
                        break;
                    }
                }
                case 1: {
                    this._pc.sendPackets(new S_Paralysis(5, true));
                    this._pc.sendPackets(new S_ServerMessage("因為檢測到加速器，懲罰暈眩10秒"));
                    RecordTable.get().r_speed(this._pc.getName(), "偵測異常(攻速or走速");
                    try {
                        Thread.sleep(10000L);
                    }
                    catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    this._pc.sendPackets(new S_Paralysis(5, false));
                    break;
                }
                case 2: {
                    L1Teleport.teleport(this._pc, 32746, 32740, (short)666, 5, false);
                    this._pc.sendPackets(new S_SystemMessage("因為你使用加速器，被傳送到了地域，10秒後傳回。"));
                    RecordTable.get().r_speed(this._pc.getName(), "偵測異常(攻速or走速");
                    try {
                        Thread.sleep(10000L);
                    }
                    catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    L1Teleport.teleport(this._pc, x, y, (short)mapid, 5, false);
                    break;
                }
                case 3: {
                    L1Teleport.teleport(this._pc, 32737, 32796, (short)99, 5, false);
                    this._pc.sendPackets(new S_SystemMessage("因為你使用加速器，被傳送到了GM房，30秒後傳回。"));
                    try {
                        Thread.sleep(30000L);
                    }
                    catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    L1Teleport.teleport(this._pc, x, y, (short)mapid, 5, false);
                    RecordTable.get().r_speed(this._pc.getName(), "偵測異常(攻速or走速");
                    break;
                }
                case 4: {
                    this._pc.sendPackets(new S_Paralysis(6, true));
                    this._pc.sendPackets(new S_ServerMessage("因為檢測到加速器，限制移動10秒"));
                    RecordTable.get().r_speed(this._pc.getName(), "偵測異常(攻速or走速");
                    try {
                        Thread.sleep(10000L);
                    }
                    catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    this._pc.sendPackets(new S_Paralysis(6, false));
                    break;
                }
            }
        }
        else {
            this._pc.sendPackets(new S_SystemMessage("遊戲管理員在遊戲中使用加速器檢測中。"));
            this._injusticeCount = 0;
            this._justiceCount = 0;
        }
    }
    
    public int getRightInterval(AcceleratorChecker.ACT_TYPE type) {
        int interval = 0;
        switch ($SWITCH_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE()[type.ordinal()]) {
            case 1: {
                interval = SprTable.get().getMoveSpeed(this._pc.getTempCharGfx(), this._pc.getCurrentWeapon());
                break;
            }
            case 2: {
                interval = SprTable.get().getAttackSpeed(this._pc.getTempCharGfx(), this._pc.getCurrentWeapon() + 1);
                break;
            }
            case 3: {
                interval = SprTable.get().getDirSpellSpeed(this._pc.getTempCharGfx());
                break;
            }
            case 4: {
                interval = SprTable.get().getNodirSpellSpeed(this._pc.getTempCharGfx());
                break;
            }
            default: {
                return 0;
            }
        }
        switch (this._pc.getMoveSpeed()) {
            case 1: {
                interval = (int)((double)interval * 0.75);
                break;
            }
            case 2: {
                interval = (int)((double)interval / 0.75);
                break;
            }
        }
        switch (this._pc.getBraveSpeed()) {
            case 1: {
                interval = (int)((double)interval * 0.75);
                break;
            }
            case 3: {
                interval = (int)((double)interval * 0.87);
                break;
            }
            case 4: {
                if (type.equals(AcceleratorChecker.ACT_TYPE.MOVE) && this._pc.isFastMovable()) {
                    interval = (int)((double)interval * 0.75);
                    break;
                }
                else {
                    break;
                }
            }
            case 5: {
                interval = (int)((double)interval * 0.375);
                break;
            }
            case 6: {
                if (type.equals(AcceleratorChecker.ACT_TYPE.ATTACK) && this._pc.isFastAttackable()) {
                    interval = (int)((double)interval * 0.75);
                    break;
                }
                else {
                    break;
                }
            }
        }
        if (this._pc.isBraveX()) {
            interval = (int)((double)interval * 0.87);
        }
        if (this._pc.isWindShackle() && !type.equals(AcceleratorChecker.ACT_TYPE.MOVE)) {
            interval /= 2;
        }
        if (this._pc.getMapId() == 5143) {
            interval = (int)((double)interval * 0.1);
        }
        return interval;
    }
    
    static /* synthetic */ int[] $SWITCH_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE() {
        int[] $switch_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE = AcceleratorChecker.$SWITCH_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE;
        if ($switch_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE != null) {
            return $switch_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE;
        }
        else {
            int[] $switch_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE2 = new int[AcceleratorChecker.ACT_TYPE.values().length];
            try {
                $switch_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE2[AcceleratorChecker.ACT_TYPE.ATTACK.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE2[AcceleratorChecker.ACT_TYPE.MOVE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE2[AcceleratorChecker.ACT_TYPE.SPELL_DIR.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE2[AcceleratorChecker.ACT_TYPE.SPELL_NODIR.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            return AcceleratorChecker.$SWITCH_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE = $switch_TABLE$com$lineage$server$clientpackets$AcceleratorChecker$ACT_TYPE2;
        }
    }
    
    public enum ACT_TYPE
    {
        MOVE("MOVE", 0), 
        ATTACK("ATTACK", 1), 
        SPELL_DIR("SPELL_DIR", 2), 
        SPELL_NODIR("SPELL_NODIR", 3);
        
        private ACT_TYPE(String s, int n) {
        }
    }
}

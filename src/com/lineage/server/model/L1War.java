package com.lineage.server.model;

import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.server.templates.L1Castle;
import java.util.Iterator;
import java.util.Set;
import com.lineage.server.serverpackets.S_War;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldWar;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.WorldClan;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class L1War
{
    private static final Log _log;
    private final ConcurrentHashMap<String, L1Clan> _attackList;
    private String _attackClanName;
    private String _defenceClanName;
    private L1Clan _defenceClan;
    private int _warType;
    private int _castleId;
    private boolean _isWarTimerDelete;
    
    static {
        _log = LogFactory.getLog(L1War.class);
    }
    
    public L1War() {
        this._attackClanName = null;
        this._defenceClanName = null;
        this._defenceClan = null;
        this._warType = 0;
        this._castleId = 0;
        this._isWarTimerDelete = false;
        this._attackList = new ConcurrentHashMap();
    }
    
    public int get_castleId() {
        return this._castleId;
    }
    
    public String get_defenceClanName() {
        return this._defenceClanName;
    }
    
    public String get_attackClanName() {
        return this._attackClanName;
    }
    
    public boolean isWarTimerDelete() {
        return this._isWarTimerDelete;
    }
    
    public void handleCommands(int war_type, String attack_clan_name, String defence_clan_name) {
        try {
            this._attackList.clear();
            this._warType = war_type;
            this._defenceClanName = defence_clan_name;
            this._defenceClan = WorldClan.get().getClan(this._defenceClanName);
            this.addAttackClan(attack_clan_name);
            this.declareWar(attack_clan_name, defence_clan_name);
            switch (war_type) {
                case 1: {
                    this._castleId = this.getCastleId();
                    break;
                }
                case 2: {
                    L1War.SimWarTimer sim_war_timer = new L1War.SimWarTimer();
                    GeneralThreadPool.get().execute(sim_war_timer);
                    break;
                }
                case 3: {
                    L1War.SimWarTimerPVP sim_war_timerPVP = new L1War.SimWarTimerPVP();
                    GeneralThreadPool.get().execute(sim_war_timerPVP);
                    break;
                }
            }
            WorldWar.get().addWar(this);
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void requestCastleWar(int type, String attack_clan_name) {
        if (attack_clan_name == null) {
            return;
        }
        else {
            try {
                L1Clan attack_clan = WorldClan.get().getClan(attack_clan_name);
                if (attack_clan != null && this._defenceClan != null) {
                    switch (type) {
                        case 1: {
                            World.get().broadcastPacketToAll(new S_War(1, attack_clan_name, this._defenceClanName));
                            break;
                        }
                        case 2: {
                            World.get().broadcastPacketToAll(new S_War(2, attack_clan_name, this._defenceClanName));
                            World.get().broadcastPacketToAll(new S_War(4, this._defenceClanName, attack_clan_name));
                            this.removeAttackClan(attack_clan_name);
                            break;
                        }
                        case 3: {
                            World.get().broadcastPacketToAll(new S_War(3, attack_clan_name, this._defenceClanName));
                            this.removeAttackClan(attack_clan_name);
                            break;
                        }
                    }
                }
            }
            catch (Exception e) {
                L1War._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    private void requestSimWar(int type, String clan1_name, String clan2_name) {
        try {
            if (clan1_name == null || clan2_name == null) {
                return;
            }
            else {
                L1Clan clan1 = WorldClan.get().getClan(clan1_name);
                if (clan1 == null) {
                    return;
                }
                else {
                    L1Clan clan2 = WorldClan.get().getClan(clan2_name);
                    if (clan2 == null) {
                        return;
                    }
                    else {
                        switch (type) {
                            case 1: {
                                clan1.sendPacketsAll(new S_War(1, clan1_name, clan2_name));
                                clan2.sendPacketsAll(new S_War(1, clan1_name, clan2_name));
                                break;
                            }
                            case 2: {
                                clan1.sendPacketsAll(new S_War(2, clan1_name, clan2_name));
                                clan2.sendPacketsAll(new S_War(4, clan2_name, clan1_name));
                                clan1.sendPacketsAll(new S_War(3, clan1_name, clan2_name));
                                clan2.sendPacketsAll(new S_War(3, clan1_name, clan2_name));
                                break;
                            }
                            case 3: {
                                clan1.sendPacketsAll(new S_War(3, clan1_name, clan2_name));
                                clan2.sendPacketsAll(new S_War(3, clan1_name, clan2_name));
                                break;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            switch (type) {
                case 2:
                case 3: {
                    WorldWar.get().removeWar(this);
                    this._isWarTimerDelete = true;
                    this.delete();
                    break;
                }
            }
        }
    }
    
    public void winCastleWar(String clan_name) {
        try {
            World.get().broadcastPacketToAll(new S_War(4, clan_name, this._defenceClanName));
            Set<String> clanList = this.getAttackClanList();
            if (!clanList.isEmpty()) {
                for (String enemy_clan_name : clanList) {
                    World.get().broadcastPacketToAll(new S_War(3, this._defenceClanName, enemy_clan_name));
                }
            }
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this._isWarTimerDelete = true;
            this.delete();
            System.out.println(clan_name + " 王冠奪取,進攻方獲勝");
        }
    }
    
    public void ceaseCastleWar() {
        try {
            Set<String> clanList = this.getAttackClanList();
            if (!clanList.isEmpty()) {
                for (String enemy_clan_name : clanList) {
                    World.get().broadcastPacketToAll(new S_War(4, this._defenceClanName, enemy_clan_name));
                }
                for (String enemy_clan_name : clanList) {
                    World.get().broadcastPacketToAll(new S_War(3, this._defenceClanName, enemy_clan_name));
                }
            }
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this._isWarTimerDelete = true;
            this.delete();
            System.out.println(this._defenceClanName + " 城堡戰爭時間終止,防禦方獲勝");
        }
    }
    
    public void declareWar(String attack_clan_name, String defence_clan_name) {
        try {
            if (this.getWarType() == 1) {
                this.requestCastleWar(1, attack_clan_name);
            }
            else {
                this.requestSimWar(1, this._attackClanName = attack_clan_name, defence_clan_name);
            }
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void surrenderWar(String clan1_name, String clan2_name) {
        try {
            if (this.getWarType() == 1) {
                this.requestCastleWar(2, clan1_name);
            }
            else {
                this.requestSimWar(2, clan1_name, clan2_name);
            }
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void ceaseWar(String clan1_name, String clan2_name) {
        try {
            if (this.getWarType() == 1) {
                this.requestCastleWar(3, clan1_name);
            }
            else {
                this.requestSimWar(3, clan1_name, clan2_name);
            }
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void ceaseWar() {
        try {
            this.ceaseWar(this._attackClanName, this._defenceClanName);
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void winWar(String clan1_name, String clan2_name) {
        try {
            if (this.getWarType() == 1) {
                this.requestCastleWar(4, clan1_name);
            }
            else {
                this.requestSimWar(4, clan1_name, clan2_name);
            }
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean checkClanInWar(String clan_name) {
        if (this._isWarTimerDelete) {
            WorldWar.get().removeWar(this);
            this.delete();
            return false;
        }
        else {
            boolean ret = false;
            ret = (this._defenceClanName.equalsIgnoreCase(clan_name) || this.checkAttackClan(clan_name));
            return ret;
        }
    }
    
    public boolean checkClanInSameWar(String player_clan_name, String target_clan_name) {
        boolean player_clan_flag = false;
        boolean target_clan_flag = false;
        player_clan_flag = (this._defenceClanName.equalsIgnoreCase(player_clan_name) || this.checkAttackClan(player_clan_name));
        target_clan_flag = (this._defenceClanName.equalsIgnoreCase(target_clan_name) || this.checkAttackClan(target_clan_name));
        return player_clan_flag && target_clan_flag;
    }
    
    public String getEnemyClanName(String player_clan_name) {
        if (this._defenceClanName.toLowerCase().equals(player_clan_name.toLowerCase())) {
            Set<String> clanList = this.getAttackClanList();
            if (!clanList.isEmpty()) {
                Iterator<String> iter = (Iterator)clanList.iterator();
                if (iter.hasNext()) {
                    return iter.next();
                }
            }
            return null;
        }
        else {
            return this._defenceClanName;
        }
    }
    
    public void delete() {
        try {
            this._attackList.clear();
            WorldWar.get().removeWar(this);
            L1War._log.info(this._defenceClanName + " 戰爭終止完成 剩餘戰爭清單數量:" + WorldWar.get().getWarList().size());
        }
        catch (Exception e) {
            L1War._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int getWarType() {
        return this._warType;
    }
    
    public void addAttackClan(String attack_clan_name) {
        L1Clan attack_clan = WorldClan.get().getClan(attack_clan_name);
        if (attack_clan != null) {
            this._attackList.put(attack_clan_name.toLowerCase(), attack_clan);
        }
    }
    
    public void removeAttackClan(String attack_clan_name) {
        if (this._attackList.get(attack_clan_name.toLowerCase()) != null) {
            this._attackList.remove(attack_clan_name.toLowerCase());
        }
    }
    
    public boolean checkAttackClan(String attack_clan_name) {
        return this._attackList.get(attack_clan_name.toLowerCase()) != null;
    }
    
    public Set<String> getAttackClanList() {
        return (Set)this._attackList.keySet();
    }
    
    public int getCastleId() {
        switch (this._warType) {
            case 1: {
                L1Clan clan = WorldClan.get().getClan(this._defenceClanName);
                if (clan != null) {
                    int castle_id = clan.getCastleId();
                    return castle_id;
                }
                else {
                    break;
                }
            }
        }
        return 0;
    }
    
    public L1Castle getCastle() {
        switch (this._warType) {
            case 1: {
                L1Clan clan = WorldClan.get().getClan(this._defenceClanName);
                if (clan != null) {
                    int castle_id = clan.getCastleId();
                    L1Castle castle = CastleReading.get().getCastleTable(castle_id);
                    return castle;
                }
                else {
                    break;
                }
            }
        }
        return null;
    }
    
    class SimWarTimer implements Runnable
    {
        public SimWarTimer() {
        }
        
        @Override
        public void run() {
            int loop = 0;
            while (loop < 240) {
                try {
                    Thread.sleep(60000L);
                }
                catch (Exception exception) {
                    break;
                }
                if (L1War.this._isWarTimerDelete) {
                    return;
                }
                else {
                    ++loop;
                }
            }
            WorldWar.get().removeWar(L1War.this);
            L1War.this.ceaseWar(L1War.this._attackClanName, L1War.this._defenceClanName);
            L1War.this.delete();
        }
    }
    
    class SimWarTimerPVP implements Runnable
    {
        @Override
        public void run() {
            int loop = 0;
            while (loop < 20) {
                try {
                    Thread.sleep(60000L);
                }
                catch (Exception exception) {
                    break;
                }
                if (L1War.this._isWarTimerDelete) {
                    return;
                }
                else {
                    ++loop;
                }
            }
            L1War.this.ceaseWar(L1War.this._attackClanName, L1War.this._defenceClanName);
            L1War.this.delete();
        }
    }
}

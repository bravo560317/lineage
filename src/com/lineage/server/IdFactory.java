package com.lineage.server;

import com.lineage.server.datatables.lock.ServerReading;
import com.lineage.server.utils.PerformanceTimer;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.Log;

public class IdFactory
{
    private static final Log _log;
    private static IdFactory _instance;
    private Object _monitor;
    private AtomicInteger _nextId;
    private int _yuanbaoId;
    private int _gamId;
    private int _bankId;
    private int _PayId;
    private int _BusinessId;
    private final ArrayList<Integer> _yuanbaoList;
    private final ArrayList<Integer> _gamblingList;
    private final ArrayList<Integer> _bankList;
    private final ArrayList<Integer> _PayList;
    private final ArrayList<Integer> _BusinessList;
    private int _BigHotId;
    private int _starId;
    private final ArrayList<Integer> _BigHotblingList;
    private final ArrayList<Integer> _starblingList;
    private int _curId;
    private ArrayList<Integer> idList;
    private int i;
    private boolean isLoadState;
    
    static {
        _log = LogFactory.getLog(IdFactory.class);
    }
    
    public static IdFactory get() {
        if (IdFactory._instance == null) {
            IdFactory._instance = new IdFactory();
        }
        return IdFactory._instance;
    }
    
    public static IdFactory getId() {
        if (IdFactory._instance == null) {
            IdFactory._instance = new IdFactory();
        }
        return IdFactory._instance;
    }
    
    public static IdFactory getInstance() {
        return IdFactory._instance;
    }
    
    private IdFactory() {
        this._monitor = new Object();
        this._yuanbaoList = new ArrayList();
        this._gamblingList = new ArrayList();
        this._bankList = new ArrayList();
        this._PayList = new ArrayList();
        this._BusinessList = new ArrayList();
        this._BigHotblingList = new ArrayList();
        this._starblingList = new ArrayList();
        this.idList = new ArrayList();
        this.i = 0;
        this.isLoadState = false;
        this.resetId();
        if (this.idList.size() > 1) {
            while (this.i + 1 < this.idList.size()) {
                if (this.idList.get(this.i + 1) - this.idList.get(this.i) > 1) {
                    this._curId = this.idList.get(this.i) + 1;
                    break;
                }
                else {
                    ++this.i;
                }
            }
            if (this._curId > 0) {
                IdFactory._log.info("目前對像使用回收ID: " + this._curId);
            }
            else {
                this.loadState();
                IdFactory._log.info("目前對像使用新ID: " + this._curId);
            }
        }
        else {
            this.loadState();
            IdFactory._log.info("目前對像使用新ID: " + this._curId);
        }
    }
    
    public int nextId() {
        synchronized (this._monitor) {
            if (this.isLoadState) {
                int n = ++this._curId;
                return n;
            }
            else {
                if (this._curId + 1 < this.idList.get(this.i + 1)) {
                    ++this._curId;
                }
                else {
                    ++this.i;
                    this._curId = 0;
                    while (this.i + 1 < this.idList.size()) {
                        if (this.idList.get(this.i + 1) - this.idList.get(this.i) > 1) {
                            this._curId = this.idList.get(this.i) + 1;
                            break;
                        }
                        else {
                            ++this.i;
                        }
                    }
                    if (this._curId <= 0) {
                        this.loadState();
                    }
                }
                int curId = this._curId;
                return curId;
            }
        }
    }
    
    private void loadState() {
        if (this.idList.size() > 0) {
            this._curId = this.idList.get(this.idList.size() - 1) + 1;
            if (this._curId < 128) {
                this._curId = 128;
            }
        }
        else {
            this._curId = 128;
        }
        this.isLoadState = true;
    }
    
    private void resetId() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT id FROM (SELECT `id` FROM `character_items` UNION ALL SELECT `id` FROM `character_warehouse` UNION ALL SELECT `id` FROM `character_elf_warehouse` UNION ALL SELECT `id` FROM `clan_warehouse` UNION ALL SELECT `id` FROM `character_shopinfo` UNION ALL SELECT `objid` AS `id` FROM `characters` UNION ALL SELECT `clan_id` AS `id` FROM `clan_data` UNION ALL SELECT `id` FROM `character_teleport` UNION ALL SELECT `id` FROM `character_mail` UNION ALL SELECT `objid` AS `id` FROM `character_pets`) t order by id");
            rs = pstm.executeQuery();
            while (rs.next()) {
                this.idList.add(rs.getInt("id"));
            }
        }
        catch (SQLException e) {
            IdFactory._log.error("id數據加載異常!", e);
        }
        finally {
            SQLUtil.close(rs, pstm, con);
        }
    }
    
    public int maxId() {
        synchronized (this._monitor) {
            int value = this._nextId.get();
            return value;
        }
    }
    
    public void addBusinessId(int i) {
        this._BusinessList.add(i);
    }
    
    public int nextBusinessId() {
        ++this._BusinessId;
        while (this._BusinessList.contains((Integer)this._BusinessId)) {
            ++this._BusinessId;
        }
        return this._BusinessId;
    }
    
    public void addPayId(int i) {
        this._PayList.add(i);
    }
    
    public int nextPayId() {
        ++this._PayId;
        while (this._PayList.contains((Integer)this._PayId)) {
            ++this._PayId;
        }
        return this._PayId;
    }
    
    public void addYuanbaoId(int i) {
        this._yuanbaoList.add(i);
    }
    
    public int nextYuanbaoId() {
        ++this._yuanbaoId;
        while (this._yuanbaoList.contains((Integer)this._yuanbaoId)) {
            ++this._yuanbaoId;
        }
        return this._yuanbaoId;
    }
    
    public void addGamId(int i) {
        this._gamblingList.add(i);
    }
    
    public int nextGamId() {
        ++this._gamId;
        while (this._gamblingList.contains((Integer)this._gamId)) {
            ++this._gamId;
        }
        return this._gamId;
    }
    
    public void addBankId(int i) {
        this._bankList.add(i);
    }
    
    public int nextBankId() {
        ++this._bankId;
        while (this._bankList.contains((Integer)this._bankId)) {
            ++this._bankId;
        }
        return this._bankId;
    }
    
    public void addBigHotId(int i) {
        this._BigHotblingList.add(i);
    }
    
    public int nextBigHotId() {
        ++this._BigHotId;
        while (this._BigHotblingList.contains((Integer)this._BigHotId)) {
            ++this._BigHotId;
        }
        return this._BigHotId;
    }
    
    public void addStarId(int i) {
        this._starblingList.add(i);
    }
    
    public int nextStarId() {
        ++this._starId;
        while (this._starblingList.contains((Integer)this._starId)) {
            ++this._starId;
        }
        return this._starId;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT MAX(id)+1 AS NEXTID FROM (SELECT `id` FROM `character_items` UNION ALL SELECT `id` FROM `character_warehouse` UNION ALL SELECT `id` FROM `character_elf_warehouse` UNION ALL SELECT `id` FROM `clan_warehouse` UNION ALL SELECT `id` FROM `character_shopinfo` UNION ALL SELECT `objid` AS `id` FROM `characters` UNION ALL SELECT `clan_id` AS `id` FROM `clan_data` UNION ALL SELECT `id` FROM `character_teleport` UNION ALL SELECT `id` FROM `character_mail` UNION ALL SELECT `objid` AS `id` FROM `character_pets`) t");
            rs = ps.executeQuery();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("nextid");
            }
            if (id < ServerReading.get().minId()) {
                id = ServerReading.get().minId();
            }
            if (id < ServerReading.get().maxId()) {
                id = ServerReading.get().maxId();
            }
            this._nextId = new AtomicInteger(id);
            IdFactory._log.info("載入已用最大id編號: " + id + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            IdFactory._log.error("id數據加載異常!", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}

package com.lineage.server.datatables;

import java.util.Collection;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Iterator;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1CastleLocation;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;

public class CastleWarGiftTable
{
    private static final Log _log;
    private static final Map<Integer, ArrayList<CastleWarGiftTable.Gift>> _gifts;
    private static CastleWarGiftTable _instance;
    
    static {
        _log = LogFactory.getLog(CastleWarGiftTable.class);
        _gifts = new HashMap();
    }
    
    public static CastleWarGiftTable get() {
        if (CastleWarGiftTable._instance == null) {
            CastleWarGiftTable._instance = new CastleWarGiftTable();
        }
        return CastleWarGiftTable._instance;
    }
    
    public void load() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `w_城堡攻打獲勝獎勵`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("castle_id");
                int itemid = rs.getInt("itemid");
                int count = rs.getInt("count");
                boolean recover = rs.getBoolean("recover");
                CastleWarGiftTable.Gift e = new CastleWarGiftTable.Gift();
                e._itemid = itemid;
                e._count = count;
                e._recover = recover;
                ArrayList<CastleWarGiftTable.Gift> list = CastleWarGiftTable._gifts.get((Integer)key);
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(e);
                CastleWarGiftTable._gifts.put(key, list);
            }
        }
        catch (SQLException e2) {
            CastleWarGiftTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public boolean isGift(int key) {
        ArrayList<CastleWarGiftTable.Gift> list = CastleWarGiftTable._gifts.get((Integer)key);
        return list != null;
    }
    
    public void get_gift(int key) {
        L1Clan castle_clan = null;
        ArrayList<CastleWarGiftTable.Gift> list = CastleWarGiftTable._gifts.get((Integer)key);
        if (list == null) {
            return;
        }
        else {
            try {
                castle_clan = L1CastleLocation.castleClan(key);
            }
            catch (Exception e) {
                CastleWarGiftTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                if (castle_clan != null) {
                    for (CastleWarGiftTable.Gift gift : list) {
                        if (gift._recover) {
                            this.recover_item(gift._itemid);
                        }
                        this.get_gift(castle_clan, gift._itemid, gift._count, gift);
                    }
                }
            }
            return;
        }
    }
    
    private void get_gift(L1Clan castle_clan, int itemid, int count, CastleWarGiftTable.Gift gift) {
        try {
            if (castle_clan.getOnlineClanMemberSize() > 0) {
                L1PcInstance[] onlineClanMember;
                for (int length = (onlineClanMember = castle_clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                    L1PcInstance tgpc = onlineClanMember[i];
                    L1ItemInstance item = ItemTable.get().createItem(itemid);
                    if (item != null) {
                        item.setCount((long)count);
                        tgpc.getInventory().storeItem(item);
                        tgpc.sendPackets(new S_ServerMessage("獲得攻城獎勵:" + item.getLogName()));
                        String[] castleName = { "", "肯特城", "妖魔城", "風木城", "奇岩城", "海音城", "侏儒城", "亞丁城", "狄亞得要塞" };
                        RecordTable.get().recordCastleReward(castleName[castle_clan.getCastleId()], tgpc.getName(), item.getLogName(), gift._count, item.getId(), tgpc.getIp());
                    }
                }
            }
        }
        catch (Exception e) {
            CastleWarGiftTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void recover_item(int itemid) {
        try {
            Collection<L1PcInstance> allpc = World.get().getAllPlayers();
            for (L1PcInstance tgpc : allpc) {
                L1ItemInstance t1 = tgpc.getInventory().findItemId(itemid);
                if (t1 != null) {
                    if (t1.isEquipped()) {
                        tgpc.getInventory().setEquipped(t1, false, false, false);
                    }
                    tgpc.getInventory().removeItem(t1);
                    tgpc.sendPackets(new S_ServerMessage(158, t1.getLogName()));
                }
            }
            CharItemsReading.get().del_item(itemid);
        }
        catch (Exception e) {
            CastleWarGiftTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private class Gift
    {
        private int _itemid;
        private int _count;
        private boolean _recover;
        
        private Gift() {
            this._recover = false;
        }
        
        /*
        static void access$1(CastleWarGiftTable.Gift gift, int itemid) {
            gift._itemid = itemid;
        }
        */
        
        /*
        static void access$2(CastleWarGiftTable.Gift gift, int count) {
            gift._count = count;
        }
        */
        
        /*
        static void access$3(CastleWarGiftTable.Gift gift, boolean recover) {
            gift._recover = recover;
        }
        */
    }
}

package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.utils.RangeInt;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashSet;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Set;
import com.lineage.server.templates.L1PetType;
import java.util.Map;
import org.apache.commons.logging.Log;

public class PetTypeTable {

    private static final Log _log;
    private static PetTypeTable _instance;
    private static final Map<Integer, L1PetType> _types;
    private static final Set<String> _defaultNames;

    static {
        _log = LogFactory.getLog(PetTypeTable.class);
        _types = new HashMap<>();
        _defaultNames = new HashSet<>();
    }

    public static void load() {
        PetTypeTable._instance = new PetTypeTable();
    }

    public static PetTypeTable getInstance() {
        return PetTypeTable._instance;
    }

    private PetTypeTable() {
        PerformanceTimer timer = new PerformanceTimer();
        loadTypes();
        _log.info("載入寵物種族資料數量: " + _types.size() + "(" + timer.get() + "ms)");
    }

    private void loadTypes() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM pettypes");
            rs = pstm.executeQuery();

            while (rs.next()) {

                int baseNpcId = rs.getInt("BaseNpcId");
                String name = rs.getString("Name");

                int itemIdForTaming = rs.getInt("ItemIdForTaming");

                int hpUpMin = rs.getInt("HpUpMin");
                int hpUpMax = rs.getInt("HpUpMax");

                int mpUpMin = rs.getInt("MpUpMin");
                int mpUpMax = rs.getInt("MpUpMax");

                int evolvItemId = rs.getInt("EvolvItemId");

                // ★ 重要修改：改為讀取字串，支援 96501,96502,96503
                String npcIdForEvolving = rs.getString("NpcIdForEvolving");

                int[] msgIds = new int[5];
                for (int i = 0; i < 5; ++i) {
                    msgIds[i] = rs.getInt("MessageId" + (i + 1));
                }

                int defyMsgId = rs.getInt("DefyMessageId");

                int PetArmor = rs.getInt("是否可裝備");

                RangeInt hpUpRange = new RangeInt(hpUpMin, hpUpMax);
                RangeInt mpUpRange = new RangeInt(mpUpMin, mpUpMax);

                L1PetType type = new L1PetType(
                        baseNpcId,
                        name,
                        itemIdForTaming,
                        hpUpRange,
                        mpUpRange,
                        evolvItemId,
                        npcIdForEvolving,
                        msgIds,
                        defyMsgId,
                        PetArmor);

                _types.put(baseNpcId, type);

                if (name != null) {
                    _defaultNames.add(name.toLowerCase());
                }
            }

        } catch (SQLException e) {
            _log.error(e.getLocalizedMessage(), e);

        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    public L1PetType get(int baseNpcId) {
        return _types.get(baseNpcId);
    }

    public boolean isNameDefault(String name) {
        return _defaultNames.contains(name.toLowerCase());
    }
}
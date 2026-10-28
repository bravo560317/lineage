package com.lineage.data.item_etcitem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.lock.VIPReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.data.executor.ItemExecutor;

public class VIPCard extends ItemExecutor {

    // 預設天數（若資料庫未設定參數時的 fallback 值）
    private int _vipDays = 30; 

    public static ItemExecutor get() {
        return new VIPCard();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        // 1. 取得玩家現有的 VIP 到期時間
        Timestamp currentVipTime = VIPReading.get().getOther(pc);
        long now = System.currentTimeMillis();
        long addTimeMillis = (long) _vipDays * 24L * 60L * 60L * 1000L;
        Timestamp newOvertime;

        // 2. 計算新的到期時間
        if (currentVipTime != null && currentVipTime.getTime() > now) {
            newOvertime = new Timestamp(currentVipTime.getTime() + addTimeMillis);
        } else {
            newOvertime = new Timestamp(now + addTimeMillis);
        }

        // 3. 維持原本安全穩定的寫入方式
        VIPReading.get().storeOther(pc.getId(), newOvertime);

        // 4. 同步更新帳號與角色名稱到 character_vip 資料庫
        try (Connection con = DatabaseFactory.get().getConnection();
             PreparedStatement pstm = con.prepareStatement("UPDATE character_vip SET account_name=?, char_name=? WHERE char_obj_id=?")) {
            pstm.setString(1, pc.getAccountName());
            pstm.setString(2, pc.getName());
            pstm.setInt(3, pc.getId());
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. 消耗掉一張 VIP 卡
        pc.getInventory().removeItem(item, 1);

        // 6. 轉換成無毫秒的時間格式 (只到分鐘)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formatTime = sdf.format(newOvertime);

        // 7. 發送系統訊息提醒玩家
        pc.sendPackets(new S_SystemMessage("已啟用 VIP 卡！時效: " + formatTime));
        pc.sendPackets(new S_SystemMessage("\\fY[提醒]月卡效果需要重新登入才刷新"));
        pc.sendPackets(new S_SystemMessage("[前300天]每增加30天多+10%傷害，共+100%"));
        pc.sendPackets(new S_SystemMessage("[300天後]每增加30天多+2%傷害，最高+220%"));
    }

    @Override
    public void set_set(String[] set) {
        try {
            this._vipDays = Integer.parseInt(set[1]);
        } catch (Exception e) {
            // 解析失敗時維持預設值
        }
    }
}
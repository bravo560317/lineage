package com.lineage.server.command.executor;

import com.lineage.DatabaseFactoryLogin;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.utils.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class L1Post implements L1CommandExecutor {

    private static final Log _log = LogFactory.getLog(L1Post.class);

    private L1Post() {
    }

    public static L1CommandExecutor getInstance() {
        return new L1Post();
    }

    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            if (st.countTokens() < 3) {
                pc.sendPackets(new S_ServerMessage(166, ".L1Post 角色名稱 物品ID 數量"));
                return;
            }

            String charName = st.nextToken();
            int itemId = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());

            // 取得角色對象
            L1PcInstance target = World.get().getPlayer(charName);
            if (target == null) {
                pc.sendPackets(new S_ServerMessage(166, "角色不在線上，無法發送"));
                return;
            }

            String accountName = target.getAccountName().toLowerCase();

            // 插入資料到 w_npc玩家即時領取
            Connection con = null;
            PreparedStatement pstm = null;

            try {
                con = DatabaseFactoryLogin.get().getConnection();
                pstm = con.prepareStatement("INSERT INTO `w_npc玩家即時領取` " +
                        "(`玩家帳號`, `發送物品編號`, `發送物品數量`, `領取狀態`, `是否手動發放`) " +
                        "VALUES (?, ?, ?, 0, 1)");
                pstm.setString(1, accountName);
                pstm.setInt(2, itemId);
                pstm.setInt(3, count);
                pstm.execute();

                L1ItemInstance item = ItemTable.get().createItem(itemId);
                String itemName = (item != null) ? item.getName() : "未知物品";

                pc.sendPackets(new S_ServerMessage(166, String.format(
                        "推文獎勵已送出：%s (%s) 數量：%d",
                        charName, itemName, count
                )));
            } catch (Exception e) {
                _log.error("L1Post 插入錯誤", e);
                pc.sendPackets(new S_ServerMessage(166, "資料庫錯誤，請查看日誌"));
            } finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }

        } catch (NumberFormatException e) {
            pc.sendPackets(new S_ServerMessage(166, "請確認數值格式正確：物品ID 與 數量必須為數字"));
        } catch (Exception e) {
            _log.error("錯誤的GM指令格式: L1Post 執行者:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(166, ".L1Post 角色名稱 物品ID 數量"));
        }
    }
}

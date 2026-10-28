package com.lineage.data.event;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.ScheduledFuture;

import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1Event;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

/**
 * 贊助排行榜（每 5 分鐘自動重算）
 * 來源：資料表 `紀錄_贊助`，依「玩家」彙總 SUM(贊助) 後取前 10 名
 */
public class RankingSet extends EventExecutor {

    private static final Log _log = LogFactory.getLog(RankingSet.class);

    public static boolean START;
    public static String[] _killList = new String[10];

    // 5 分鐘排程
    private static ScheduledFuture<?> _task;

    private static final long PERIOD_MILLIS = 5L * 60L * 1000L; // 5 分鐘

    private RankingSet() { }

    public static EventExecutor get() {
        return new RankingSet();
    }

    @Override
    public void execute(L1Event event) {
        try {
            START = true;

            // 先立即計算一次
            getRankingKill();

            // 避免重複排程：若已存在則先取消
            if (_task != null && ! _task.isCancelled()) {
                _task.cancel(false);
            }

            // 每 5 分鐘自動重算
            _task = GeneralThreadPool.get().scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getRankingKill();
                        } catch (Throwable t) {
                            _log.error("RankingSet 排程計算失敗: " + t.getMessage(), t);
                        }
                    }
                },
                PERIOD_MILLIS,   // 首次延遲
                PERIOD_MILLIS    // 固定間隔
            );

            _log.info("RankingSet 啟動完成：每 5 分鐘更新贊助排行榜。");

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 贊助排行榜：對 `紀錄_贊助` 依玩家彙總 SUM(贊助)，取前 10 名
     * 備註：若你之後要把「額外」也算進去，可將 SQL 的 SUM(贊助) 改為 SUM(贊助 + 額外)
     */
    public static void getRankingKill() {
        String[] list = new String[10];
        PreparedStatement ps = null;
        Connection cn = null;
        ResultSet rs = null;
        int key = 0;

        try {
            cn = DatabaseFactory.get().getConnection();

            // 以玩家名稱彙總贊助總額；中文表名與欄位名使用反引號包起來避免轉義問題
            ps = cn.prepareStatement(
                "SELECT `玩家`, SUM(`贊助`) AS `總額` " +
                "FROM `紀錄_贊助` " +
                "GROUP BY `玩家` " +
                "ORDER BY `總額` DESC " +
                "LIMIT 10"
            );

            rs = ps.executeQuery();
            while (rs.next() && key < 10) {
                final String name = rs.getString("玩家");
                final int total = rs.getInt("總額");
                // 顯示「名字 (總額)」，若只想顯示名字，改成：list[key] = name;
                list[key] = (name != null ? name : "") + " (" + total + ")";
                key++;
            }

        } catch (Exception e) {
            _log.error("讀取贊助排行榜失敗: " + e.getLocalizedMessage(), e);
        } finally {
            // 正確關閉順序：rs -> ps -> cn
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }

        // 補滿 10 筆，避免 NPC 對話樣板取值時空指標
        while (key < 10) {
            list[key] = " ";
            key++;
        }

        // 一次性替換，減少多執行緒讀取時的可見性問題
        _killList = list;
    }

    /**
     * 可選：提供停止方法（若你的系統有 event 停用流程可呼叫）
     */
    public static void stop() {
        try {
            if (_task != null && ! _task.isCancelled()) {
                _task.cancel(false);
                _task = null;
                _log.info("RankingSet 已停止排程。");
            }
        } catch (Exception ignore) { }
        START = false;
    }
}

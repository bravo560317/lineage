package com.add;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import com.lineage.config.ConfigGuaji;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class new_AutoBuyTime extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    protected static final Random _random;
    private int[] list;
    private int[] list2;

    static {
        _log = LogFactory.getLog(new_AutoBuyTime.class);
        _random = new Random();
    }

    public new_AutoBuyTime() {

        this.list = new int[] {
            ConfigGuaji.itemadena1, ConfigGuaji.itemadena2, ConfigGuaji.itemadena3,
            ConfigGuaji.itemadena4, ConfigGuaji.itemadena5, ConfigGuaji.itemadena6,
            ConfigGuaji.itemadena7, ConfigGuaji.itemadena8, ConfigGuaji.itemadena9
        };

        this.list2 = new int[] {
            ConfigGuaji.itemid1, ConfigGuaji.itemid2, ConfigGuaji.itemid3,
            ConfigGuaji.itemid4, ConfigGuaji.itemid5, ConfigGuaji.itemid6,
            ConfigGuaji.itemid7, ConfigGuaji.itemid8, ConfigGuaji.itemid9
        };
    }

    public void start() {
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 10000L, 10000L);
    }

    @Override
    public void run() {
        try {
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) return;

            for (L1PcInstance tgpc : all) {

                if (tgpc == null || tgpc.isDead()) continue;

                if (tgpc.isActived() && !tgpc.isSimpleAutoHunt() && tgpc.IsAu_Shop()
                    && tgpc.getInventory().getSize() <= 165
                    && tgpc.getInventory().getWeight240() < 197) {

                    this.checkBuyItem(tgpc);
                }

                Thread.sleep(1L);
            }

        } catch (Exception e) {
            _log.error("PC 內掛自動購買物品處理異常, 系統自動重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            new_AutoBuyTime abi = new new_AutoBuyTime();
            abi.start();
        }
    }

    private void checkBuyItem(L1PcInstance pc) {
        try {
            for (int i = 0; i < 9; ++i) {

                if (pc.getAu_BuyItemSwitch(i) == 0) continue;

                int buyCount = pc.getAu_BuyItemCount(i);
                if (buyCount <= 0) continue;

                int price = this.list[i];
                int needAdena = price * buyCount;
                int threshold = (int)(buyCount * 0.1);
                if (threshold < 1) threshold = 1;

                L1ItemInstance item = pc.getInventory().findItemId(this.list2[i]);
                boolean needBuy = false;

                if (item == null) needBuy = true;
                else if (item.getCount() < threshold) needBuy = true;

                if (!needBuy) continue;

                // 抓中文名稱
                String itemName = com.lineage.server.datatables.ItemTable.get()
                        .getTemplate(this.list2[i]).getName();

                // 金幣不足
                if (!pc.getInventory().checkItem(40308, (long)needAdena)) {
                    pc.sendPackets(new S_SystemMessage("金幣不足：" + itemName + " 無法自動補給"));
                    continue;
                }

                // 扣金幣
                pc.getInventory().consumeItem(40308, (long)needAdena);

                // 第一項（藥水）給五倍
                if (i == 0) {
                    pc.getInventory().storeItem(this.list2[i], (long)(buyCount * 5));
                } else {
                    pc.getInventory().storeItem(this.list2[i], (long)buyCount);
                }

                // 補給訊息
                pc.sendPackets(new S_SystemMessage(
                        "系統自動補足：" + itemName +
                        " (低於購買數量的10% 自動補給)"
                ));
            }

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }
}

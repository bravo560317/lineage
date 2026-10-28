package com.lineage.server.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.ConfigOther;
import com.lineage.server.WriteLogTxt;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.World;

public class L1DuBo {
    private static L1DuBo _instance;
    private static final Log _log = LogFactory.getLog(L1DuBo.class);
    /**
     * 當前押注的所有玩家列表 不包括莊家
     */
    private final HashMap<Integer, YaZhuTemp> _allmembers = new HashMap<Integer, YaZhuTemp>();
    /**
     * 掉線存儲
     */
    private final HashMap<Integer, Integer> _onlemembers = new HashMap<Integer, Integer>();
    /**
     * 隨機
     */
    private static final Random _random = new Random();
    /**
     * 當前npc對象 功後期使用
     */
    private L1NpcInstance _npc = null;
    /**
     * 莊家對象
     */
    private L1PcInstance _zhuangpc = null;
    /**
     * 莊家的總數量
     */
    private int _zhuangcount = 0;
    /**
     * 押大的總數量
     */
    private int _dacount = 0;
    /**
     * 押小的總數量
     */
    private int _xiaocount = 0;

    /**
     * 返回當前還能押大的數量
     * 
     * @return
     */
    public int getNextDaCount() {
        return _zhuangcount - _dacount + _xiaocount;
    }

    /**
     * 返回當前還能押小的數量
     * 
     * @return
     */
    public int getNextXiaoCount() {
        return _zhuangcount - _xiaocount + _dacount;
    }

    public static L1DuBo get(final L1NpcInstance npc) {
        if (_instance == null) {
            _instance = new L1DuBo(npc);
        }
        return _instance;
    }

    private class YaZhuTemp {
        private L1PcInstance _pc;

        public void setCha(final L1PcInstance pc) {
            _pc = pc;
        }

        public L1PcInstance getCha() {
            return _pc;
        }

        private int _BetType = 0;

        public void setBetType(final int i) {
            _BetType = i;
        }

        public int getBetType() {
            return _BetType;
        }

        // 賭博押注數量
        private int _DuBoCount = 0;

        public void addDuBoCount(final int i) {
            _DuBoCount += i;
        }

        public int getDuBoCount() {
            return _DuBoCount;
        }
    }

    private L1DuBo(final L1NpcInstance npc) {
        _npc = npc;
    }

    class DuboThread extends Thread {
        /**
         * 30秒搶莊時間
         * 
         * @throws InterruptedException
         */
        private void QiangZhuang() throws InterruptedException {
            for (int loop = 30; loop > 0; loop--) {// 30秒搶莊時間
                L1DuBo.this.setNextLoop(loop);
                Thread.sleep(1000);
            }
            World.get().broadcastServerMessage("搶莊停止 現在開始押注 .");
        }

        /**
         * 30押注時間
         * 
         * @throws InterruptedException
         */
        private void YaZhu() throws InterruptedException {
            for (int loop = 30; loop > 0; loop--) {// 30秒押注時間
                L1DuBo.this.setNextLoop(loop);
                Thread.sleep(1000);
            }
            World.get().broadcastServerMessage("押注停止 .");
        }

        @Override
        public void run() {
            try {
                _allmembers.clear();// 清空所有押注信息 方便下次押注

                L1DuBo.this.setStartOn(1);// 開始搶莊
                this.QiangZhuang();// 搶莊
                L1DuBo.this.setStartOn(2);// 開始押注
                this.YaZhu();// 押注
                L1DuBo.this.setStartOn(3);// 開始開住
                for (int i = 10; i > 0; i--) { // 倒計10秒後 開
                    L1DuBo.this.setNextLoop(i);
                    Thread.sleep(1000); // 僅保留時間等待，不發送廣播
                }
                // 3顆骰子
                int dian1 = _random.nextInt(6) + 1;
                int dian2 = _random.nextInt(6) + 1;
                int dian3 = _random.nextInt(6) + 1;

                if (L1DuBo.this._gmbaoz) {
                    dian1 = _random.nextInt(6) + 1;// 隨機出個豹子
                    dian2 = dian1;
                    dian3 = dian1;
                    L1DuBo.this._gmbaoz = false;
                }
                if (L1DuBo.this._gmda) {
                    dian1 = 4 + _random.nextInt(3);// 隨機出4以上點 必大
                    dian2 = 4 + _random.nextInt(3);// 隨機出4以上點 必大
                    dian3 = 4 + _random.nextInt(3);// 隨機出4以上點 必大
                    if (dian1 == dian2 && dian2 == dian3) {// 如果是豹子 重置點1
                        while (dian1 == dian2) {
                            dian1 = 4 + _random.nextInt(3);
                        }
                    }
                    L1DuBo.this._gmda = false;
                }
                if (L1DuBo.this._gmxiao) {
                    dian1 = _random.nextInt(3) + 1;// 3
                    dian2 = _random.nextInt(3) + 1;// 3
                    dian3 = _random.nextInt(3) + 1;// 3
                    if (dian1 == dian2 && dian2 == dian3) {// 如果是豹子 重置點1
                        while (dian1 == dian2) {
                            dian1 = _random.nextInt(3) + 1;// 3
                        }
                    }
                    L1DuBo.this._gmxiao = false;
                }

                World.get().broadcastServerMessage(String.format("第一次點數:%d點", dian1));
                Thread.sleep(2000);
                World.get().broadcastServerMessage(String.format("第二次點數:%d點", dian2));
                Thread.sleep(2000);
                World.get().broadcastServerMessage(String.format("第三次點數:%d點", dian3));
                Thread.sleep(2000);

                final StringBuilder npcChatMsg = new StringBuilder();
                npcChatMsg.append(String.format("當前點數:%d、%d、%d 共%d點 ", dian1, dian2, dian3, dian1 + dian2 + dian3));
                
                int XYbCount = 0;
                int ZYbCount = 0;
                if (dian1 == dian2 && dian2 == dian3) {// 3顆點一樣大 豹子 莊家通吃
                    ZYbCount = _zhuangcount + _dacount + _xiaocount;// 獎勵規則
                    npcChatMsg.append("豹子 莊家通吃.");
                } else if ((dian1 + dian2 + dian3) < 10) {// 3顆加起來小於10 為【小】
                    for (final YaZhuTemp element : L1DuBo.this._allmembers.values()) {
                        if (element.getBetType() == 2) { // 獎勵所有 押 小的玩家
                            int betCount = element.getDuBoCount();
                            int winProfit = (int)(betCount * (1 - 0.02)); // 僅對盈利抽 2%
                            XYbCount = betCount + winProfit;
                            
                            if (element.getCha().getOnlineStatus() == 1) {
                                element.getCha().getInventory().storeItem(ConfigOther.Dubo , XYbCount);
                                element.getCha().sendPackets(new S_ServerMessage(143, "賭神阿特", "籌碼(" + XYbCount + ")"));
                                WriteLogTxt.Recording("賭博記錄", "玩家:" + element.getCha().getName() + "贏得籌碼(" + XYbCount + ")在線");
                            } else {
                                _onlemembers.put(element.getCha().getId(), XYbCount);
                                WriteLogTxt.Recording("賭博記錄", "玩家:" + element.getCha().getName() + "贏得籌碼(" + XYbCount + ")離線");
                            }
                        }
                    }
                    ZYbCount = _zhuangcount - _xiaocount + _dacount;
                    npcChatMsg.append("小");
                } else if ((dian1 + dian2 + dian3) >= 10) { // 3顆加起來大於或等於10 為【大】
                    for (final YaZhuTemp element : L1DuBo.this._allmembers.values()) {
                        if (element.getBetType() == 1) {// 獎勵押 大 的玩家
                            int betCount = element.getDuBoCount();
                            int winProfit = (int)(betCount * (1 - 0.02)); // 僅對盈利抽 2%
                            XYbCount = betCount + winProfit;
                            
                            if (element.getCha().getOnlineStatus() == 1) {
                                element.getCha().getInventory().storeItem(ConfigOther.Dubo , XYbCount);
                                element.getCha().sendPackets(new S_ServerMessage(143, "賭博大師", "籌碼(" + XYbCount + ")"));
                                WriteLogTxt.Recording("賭博記錄", "玩家:" + element.getCha().getName() + "贏得籌碼(" + XYbCount + ")在線");
                            } else {
                                _onlemembers.put(element.getCha().getId(), XYbCount);
                                WriteLogTxt.Recording("賭博記錄", "玩家:" + element.getCha().getName() + "贏得籌碼(" + XYbCount + ")離線");
                            }
                        }
                    }
                    ZYbCount = _zhuangcount - _dacount + _xiaocount;
                    npcChatMsg.append("大");
                }

                World.get().broadcastServerMessage(npcChatMsg.toString());

                if (_zhuangpc != null) {
                    if (ZYbCount > 0) {
                        if (ZYbCount >= _zhuangcount - 100) {
                            ZYbCount -= (ZYbCount - _zhuangcount) * 0.02;
                        }
                        if (_zhuangpc.getOnlineStatus() == 1) {
                            _zhuangpc.getInventory().storeItem(ConfigOther.Dubo , ZYbCount);
                            _zhuangpc.sendPackets(new S_ServerMessage(143, "賭博大師", "籌碼(" + ZYbCount + ")"));
                            WriteLogTxt.Recording("賭博記錄", "玩家:" + _zhuangpc.getName() + "收回莊家本錢籌碼(" + ZYbCount + ")在線");
                        } else {
                            _onlemembers.put(_zhuangpc.getId(), ZYbCount);
                            WriteLogTxt.Recording("賭博記錄", "玩家:" + _zhuangpc.getName() + "收回莊家本錢籌碼(" + ZYbCount + ")離線");
                        }
                    }
                    World.get().broadcastServerMessage(String.format("莊家:%s 收回了成本%d籌碼", _zhuangpc.getName(), ZYbCount));
                }

                // 完畢 初始化屬性
                _allmembers.clear();// 清空所有壓住信息 方便下次壓住
                _zhuangpc = null;
                _zhuangcount = 0;
                _dacount = 0;
                _xiaocount = 0;
                L1DuBo.this.setStartOn(0);
                L1DuBo.this.setNextLoop(0);

            } catch (Exception e) {
                _log.error(e.getMessage(), e);
            }
        }
    }

    public int getOnlineCount(final L1PcInstance pc) {
        int count = 0;
        if (_onlemembers.containsKey(pc.getId())) {
            count = _onlemembers.get(pc.getId());
            _onlemembers.remove(pc.getId());
        }
        return count;
    }

    private int _START_ON = 0;

    /**
     * 0 空閒 1 搶莊 2 壓住 3 開住
     * 
     * @param o
     */
    private void setStartOn(final int o) {
        _START_ON = o;
    }

    /**
     * 0 空閒 1 搶莊 2 壓住 3 開住
     */
    public int getStartOn() {
        return _START_ON;
    }

    private int _nextloop = 0;

    private void setNextLoop(final int s) {
        _nextloop = s;
    }

    private int getNextLoop() {
        return _nextloop;
    }

    /**
     * 開始游戲
     */
    private void StartGame() {
        final DuboThread dubo = new DuboThread();
        GeneralThreadPool.get().execute(dubo);
    }

    /**
     * 
     * @param pc
     * @param count
     * @param type
     *              0搶莊 1壓大 2壓小
     */
    public synchronized void setDuBoPc(final L1PcInstance pc, final int count,
            final int type) {
        if (count <= 0) {
            return;
        }
        if (count > 105000000) {
            pc.sendPackets(new S_SystemMessage("為了防止沉迷賭博 賭博數量不能超過1億500萬"));
            return;
        }
        final L1ItemInstance deaItem = pc.getInventory().findItemId(ConfigOther.Dubo );
        if (deaItem == null || deaItem.getCount() < count) {
            pc.sendPackets(new S_SystemMessage("你的籌碼不足!"));
            return;
        }
        final long dea_count = deaItem.getCount();
        if (type == 0 && (getStartOn() == 0 || getStartOn() == 1)) {
            if (_zhuangpc == null) {
                if (count < 100000) {
                    pc.sendPackets(new S_SystemMessage("莊家數量不能低於10萬!"));
                    return;
                }
                if (pc.getInventory().removeItem(deaItem, count) == count) {
                    _zhuangpc = pc;
                    _zhuangcount = count;
                    this.StartGame();
                    World.get().broadcastServerMessage(
                            String.format("%s使用了 %d籌碼坐莊 想贏籌碼的趕快去押注吧.",
                                    pc.getName(), _zhuangcount));
                    final L1ItemInstance item = pc.getInventory().getItem(
                            deaItem.getId());
                    final long next_count = item == null ? 0 : item.getCount();
                    WriteLogTxt.Recording("賭博記錄", "玩家:" + pc.getName()
                            + " 使用籌碼(" + count + ")坐莊.原籌碼數量:" + dea_count
                            + " 剩余籌碼數量:" + next_count);
                }
            } else {
                if (_zhuangcount >= 100000000) {
                    pc.sendPackets(new S_SystemMessage(
                            "為了防止沉迷賭博 當前莊家數量已達到了1億 不能再搶莊了"));
                    return;
                }
                if (_zhuangpc.getId() == pc.getId()) {
                    if (count < 50000) {
                        pc.sendPackets(new S_SystemMessage("數量必須大於5萬"));
                        return;
                    }
                    if (pc.getInventory().removeItem(deaItem, count) == count) {
                        _zhuangpc = pc;
                        _zhuangcount += count;
                        World.get().broadcastServerMessage(
                                String.format("%s使用了 %d籌碼坐莊 想贏籌碼的趕快去押注吧.",
                                        pc.getName(), _zhuangcount));
                        final L1ItemInstance item = pc.getInventory().getItem(
                                deaItem.getId());
                        final long next_count = item == null ? 0 : item
                                .getCount();
                        WriteLogTxt.Recording("賭博記錄", "玩家:" + pc.getName()
                                + " 使用籌碼(" + count + ") 增加莊的本錢.原籌碼數量:"
                                + dea_count + " 剩余籌碼數量:" + next_count);
                    }
                } else {
                    if (count >= _zhuangcount + 50000) {
                        if (pc.getInventory().removeItem(deaItem, count) == count) {
                            final L1ItemInstance item = pc.getInventory()
                                    .getItem(deaItem.getId());
                            final long next_count = item == null ? 0 : item
                                    .getCount();
                            WriteLogTxt.Recording("賭博記錄", "玩家:" + pc.getName()
                                    + " 使用籌碼(" + count + ") 搶莊.原籌碼數量:"
                                    + dea_count + " 剩余籌碼數量:" + next_count);
                            if (_zhuangpc.getOnlineStatus() == 1) {
                                _zhuangpc.getInventory().storeItem(ConfigOther.Dubo ,
                                        _zhuangcount);
                                _zhuangpc.sendPackets(new S_ServerMessage(143,
                                        "賭博大師", "籌碼(" + _zhuangcount + ")"));
                                WriteLogTxt.Recording("賭博記錄",
                                        "玩家:" + _zhuangpc.getName() + " 被  "
                                                + pc.getName() + " 搶莊.已退還籌碼("
                                                + _zhuangcount + ") 在線");
                            } else {
                                _onlemembers.put(_zhuangpc.getId(),
                                        _zhuangcount);
                                WriteLogTxt.Recording("賭博記錄",
                                        "玩家:" + _zhuangpc.getName() + " 被  "
                                                + pc.getName() + " 搶莊.已退還籌碼("
                                                + _zhuangcount + ") 離線");
                            }
                            _zhuangcount = count;
                            _zhuangpc = pc;// 重新定義新的莊家
                            World.get().broadcastServerMessage(
                                    String.format("%s使用了 %d籌碼坐莊 想贏籌碼的趕快去押注吧.",
                                            pc.getName(), _zhuangcount));
                        }
                    } else {
                        pc.sendPackets(new S_SystemMessage("搶莊數量必須大於當前莊家數量的5萬."));
                    }
                }
            }
        } else if (type == 1 && getStartOn() == 2) {
            if (count < 10000) {
                pc.sendPackets(new S_SystemMessage("壓住不能低於1萬"));
                return;
            }
            if (_zhuangpc.getId() == pc.getId()) {
                pc.sendPackets(new S_SystemMessage("你是莊 不能押注."));
                return;
            }
            YaZhuTemp temp = _allmembers.get(pc.getId());
            if (temp != null && temp.getBetType() == 2) {
                pc.sendPackets(new S_SystemMessage("你已經押了【小】 不能再押【大】了."));
                return;
            }
            if (getNextDaCount() < count) {
                pc.sendPackets(new S_SystemMessage(String.format("當前壓大最多還能壓%d",
                        getNextDaCount())));
                return;
            }
            if (pc.getInventory().removeItem(deaItem, count) == count) {
                if (temp == null) {
                    temp = new YaZhuTemp();
                    _allmembers.put(pc.getId(), temp);
                }
                _dacount += count;
                temp.addDuBoCount(count);
                temp.setBetType(1);
                temp.setCha(pc);
                pc.sendPackets(new S_SystemMessage(String.format(
                        "你使用了%d籌碼壓大 你當前壓住總數量%d", count, temp.getDuBoCount())));
                World.get().broadcastServerMessage(
                        String.format("%s 押了:%d籌碼【大】", pc.getName(), count));
                final L1ItemInstance item = pc.getInventory().getItem(
                        deaItem.getId());
                final long next_count = item == null ? 0 : item.getCount();
                WriteLogTxt.Recording(
                        "賭博記錄",
                        "玩家:" + pc.getName() + "使用籌碼(" + count + ") 壓大 當前"
                                + pc.getName() + "壓大總數量:" + temp.getDuBoCount()
                                + "原籌碼數量:" + dea_count + " 剩余籌碼數量:"
                                + next_count);
            }
        } else if (type == 2 && getStartOn() == 2) {
            if (count < 10000) {
                pc.sendPackets(new S_SystemMessage("壓住不能低於1萬"));
                return;
            }
            if (_zhuangpc.getId() == pc.getId()) {
                pc.sendPackets(new S_SystemMessage("你是莊 不能押注."));
                return;
            }
            YaZhuTemp temp = _allmembers.get(pc.getId());
            if (temp != null && temp.getBetType() == 1) {
                pc.sendPackets(new S_SystemMessage("你已經押了【大】 不能再押【小】了."));
                return;
            }
            if (getNextXiaoCount() < count) {
                pc.sendPackets(new S_SystemMessage(String.format("當前壓小最多還能壓%d",
                        getNextXiaoCount())));
                return;
            }
            if (pc.getInventory().removeItem(deaItem, count) == count) {
                if (temp == null) {
                    temp = new YaZhuTemp();
                    _allmembers.put(pc.getId(), temp);
                }
                _xiaocount += count;
                temp.addDuBoCount(count);
                temp.setBetType(2);
                temp.setCha(pc);
                pc.sendPackets(new S_SystemMessage(String.format(
                        "你使用了%d籌碼壓小 你當前壓住總數量%d", count, temp.getDuBoCount())));
                World.get().broadcastServerMessage(
                        String.format("%s 押了:%d籌碼【小】", pc.getName(), count));
                final L1ItemInstance item = pc.getInventory().getItem(
                        deaItem.getId());
                final long next_count = item == null ? 0 : item.getCount();
                WriteLogTxt.Recording(
                        "賭博記錄",
                        "玩家:" + pc.getName() + "使用籌碼(" + count + ") 壓小 當前"
                                + pc.getName() + "壓小總數量:" + temp.getDuBoCount()
                                + "原籌碼數量:" + dea_count + " 剩余籌碼數量:"
                                + next_count);
            }
        }
    }

    public String[] MsgDate(L1PcInstance pc) {
        String zpcname = "";
        String pcstyle = "";
        String starton = "";
        int count = 0;

        if (_zhuangpc != null) {
            zpcname = _zhuangpc.getName();
            if (pc.getId() == _zhuangpc.getId()) {
                pcstyle = "莊";
                count = _zhuangcount;
            } else {
                final YaZhuTemp temp = _allmembers.get(pc.getId());
                if (temp != null) {
                    if (temp.getBetType() == 1) {
                        pcstyle = "大";
                    } else if (temp.getBetType() == 2) {
                        pcstyle = "小";
                    } else {
                        pcstyle = "";
                    }
                    count = temp.getDuBoCount();
                    temp.setCha(pc);
                }
            }
        }

        if (getStartOn() == 0) {
            starton = "空閒";
        } else if (getStartOn() == 1) {
            starton = "搶莊中";
        } else if (getStartOn() == 2) {
            starton = "壓住中";
        } else if (getStartOn() == 3) {
            starton = "開住中";
        }

        final String[] md = new String[] { zpcname, insertComma(_zhuangcount),
                starton, String.valueOf(getNextLoop()), insertComma(_dacount),
                insertComma(_xiaocount), String.valueOf(pcstyle),
                insertComma(count), insertComma(this.getNextDaCount()),
                insertComma(this.getNextXiaoCount()) };
        return md;
    }

    /**
     * 金額格式化
     * 
     * @param s
     *              金額
     * @return 格式後的金額 999,000,000,000
     */
    private String insertComma(int s) {
        final NumberFormat formater = new DecimalFormat("###,###,###,###");
        return formater.format(s);
    }

    private boolean _gmbaoz = false;

    private boolean _gmda = false;

    private boolean _gmxiao = false;

    public void setGmBaoz(final boolean b) {
        _gmbaoz = b;
        _gmda = false;
        _gmxiao = false;
    }

    public void setGmDa(final boolean b) {
        _gmbaoz = false;
        _gmda = b;
        _gmxiao = false;
    }

    public void setGmXiao(final boolean b) {
        _gmbaoz = false;
        _gmda = false;
        _gmxiao = b;
    }
}
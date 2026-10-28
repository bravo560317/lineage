package com.lineage.data.npc.gam;

import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.Shutdown;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.lock.MaryReading;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Mary extends NpcExecutor
{
    private static final Log _log;
    private static final Random _random;
    private static int _itemid1;
    private static int _count;
    private static int _x_a1;
    private static int _x_a2;
    private static int _x_b1;
    private static int _x_b2;
    private static int _x_c1;
    private static int _x_c2;
    private static int _x_d1;
    private static int _x_d2;
    private static int _x_e1;
    private static int _x_e2;
    private static int _x_f1;
    private static int _x_f2;
    private static int _x_g1;
    private static int _x_g2;
    private static int _out_prize;
    private static long _all_stake;
    private static long _all_user_prize;
    private static final Map<Integer, Npc_Mary.MaryTemp> _maryUsers;
    private static final int[] _min;
    
    static {
        _log = LogFactory.getLog(Npc_Mary.class);
        _random = new Random();
        Npc_Mary._itemid1 = 0;
        Npc_Mary._x_a1 = 100;
        Npc_Mary._x_a2 = 50;
        Npc_Mary._x_b1 = 40;
        Npc_Mary._x_b2 = 2;
        Npc_Mary._x_c1 = 30;
        Npc_Mary._x_c2 = 2;
        Npc_Mary._x_d1 = 20;
        Npc_Mary._x_d2 = 2;
        Npc_Mary._x_e1 = 15;
        Npc_Mary._x_e2 = 2;
        Npc_Mary._x_f1 = 10;
        Npc_Mary._x_f2 = 2;
        Npc_Mary._x_g1 = 5;
        Npc_Mary._x_g2 = 2;
        _maryUsers = new HashMap();
        _min = new int[] { 2, 4, 6, 7, 10, 12, 14, 15 };
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            Npc_Mary._itemid1 = Integer.parseInt(set[1]);
        }
        catch (Exception e) {
            Npc_Mary._log.error("NPC專屬貨幣設置錯誤:檢查CLASSNAME為Npc_Strange的NPC設置!");
        }
    }
    
    public static void set_itemid(int itemid) {
        Npc_Mary._itemid1 = itemid;
    }
    
    public static void set_count(int count) {
        Npc_Mary._count = count;
    }
    
    public static void set_x_a1(int x_a1) {
        Npc_Mary._x_a1 = x_a1;
    }
    
    public static void set_x_a2(int x_a2) {
        Npc_Mary._x_a2 = x_a2;
    }
    
    public static void set_x_b1(int x_b1) {
        Npc_Mary._x_b1 = x_b1;
    }
    
    public static void set_x_b2(int x_b2) {
        Npc_Mary._x_b2 = x_b2;
    }
    
    public static void set_x_c1(int x_c1) {
        Npc_Mary._x_c1 = x_c1;
    }
    
    public static void set_x_c2(int x_c2) {
        Npc_Mary._x_c2 = x_c2;
    }
    
    public static void set_x_d1(int x_d1) {
        Npc_Mary._x_d1 = x_d1;
    }
    
    public static void set_x_d2(int x_d2) {
        Npc_Mary._x_d2 = x_d2;
    }
    
    public static void set_x_e1(int x_e1) {
        Npc_Mary._x_e1 = x_e1;
    }
    
    public static void set_x_e2(int x_e2) {
        Npc_Mary._x_e2 = x_e2;
    }
    
    public static void set_x_f1(int x_f1) {
        Npc_Mary._x_f1 = x_f1;
    }
    
    public static void set_x_f2(int x_f2) {
        Npc_Mary._x_f2 = x_f2;
    }
    
    public static void set_x_g1(int x_g1) {
        Npc_Mary._x_g1 = x_g1;
    }
    
    public static void set_x_g2(int x_g2) {
        Npc_Mary._x_g2 = x_g2;
    }
    
    public static void set_out_prize(int out_prize) {
        Npc_Mary._out_prize = out_prize;
    }
    
    public static void set_all_stake(long all_stake) {
        Npc_Mary._all_stake = all_stake;
    }
    
    public static void set_all_user_prize(long all_user_prize) {
        Npc_Mary._all_user_prize = all_user_prize;
    }
    
    public static void update() {
        MaryReading.get().update(Npc_Mary._all_stake, Npc_Mary._all_user_prize, Npc_Mary._count);
    }
    
    private static long all_prize() {
        long out = Npc_Mary._all_stake * (long)Npc_Mary._out_prize / 100L;
        long out_prize = out - Npc_Mary._all_user_prize;
        if (out_prize <= 0L) {
            return 0L;
        }
        else {
            long out_prize2 = (long)(int)((double)out_prize * 0.5);
            if (Npc_Mary._random.nextInt(100) <= 10) {
                return out_prize2 + (long)Npc_Mary._random.nextInt(20);
            }
            else {
                return out_prize2;
            }
        }
    }
    
    public static NpcExecutor get() {
        return new Npc_Mary();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        Npc_Mary.MaryTemp tmp = Npc_Mary._maryUsers.get((Integer)pc.getId());
        if (tmp == null) {
            tmp = new Npc_Mary.MaryTemp();
            Npc_Mary._maryUsers.put(pc.getId(), tmp);
        }
        tmp._x_a = 0;
        tmp._x_b = 0;
        tmp._x_c = 0;
        tmp._x_d = 0;
        tmp._x_e = 0;
        tmp._x_f = 0;
        tmp._x_g = 0;
        tmp._x_a_t = 0;
        tmp._x_b_t = 0;
        tmp._x_c_t = 0;
        tmp._x_d_t = 0;
        tmp._x_e_t = 0;
        tmp._x_f_t = 0;
        tmp._x_g_t = 0;
        tmp._x_h_i = false;
        tmp._prize_all = all_prize();
        Npc_Mary.MaryTimer maryTimer = new Npc_Mary.MaryTimer(pc, npc, 0, -1);
        maryTimer.startCmd();
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        Npc_Mary.MaryTemp tmp = Npc_Mary._maryUsers.get((Integer)pc.getId());
        if (tmp == null) {
            tmp = new Npc_Mary.MaryTemp();
        }
        if (tmp._stop) {
            return;
        }
        else if (cmd.equalsIgnoreCase("start")) {
            if (Shutdown.SHUTDOWN) {
                pc.sendPackets(new S_ServerMessage(166, "系統暫停接受下注，請儘快取回您的獎金"));
                return;
            }
            else {
                long down = (long)(tmp._x_a + tmp._x_b + tmp._x_c + tmp._x_d + tmp._x_e + tmp._x_f + tmp._x_g);
                if (down != 0L) {
                    L1ItemInstance tgitem = pc.getInventory().checkItemX(Npc_Mary._itemid1, down);
                    if (tgitem != null) {
                        Npc_Mary.MaryTimer maryTimer = new Npc_Mary.MaryTimer(pc, npc, 1, -1);
                        maryTimer.startCmd();
                        Npc_Mary.MaryTemp maryTemp = tmp;
                        maryTemp._count = maryTemp._count + 1;
                    }
                    else {
                        L1Item tgItem = ItemTable.get().getTemplate(Npc_Mary._itemid1);
                        pc.sendPackets(new S_ServerMessage(337, tgItem.getNameId()));
                        pc.sendPackets(new S_CloseList(pc.getId()));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("\\fR您還沒有下注!!"));
                }
                return;
            }
        }
        else {
            if (cmd.equalsIgnoreCase("re")) {
                tmp._x_a = tmp._x_a_t;
                tmp._x_b = tmp._x_b_t;
                tmp._x_c = tmp._x_c_t;
                tmp._x_d = tmp._x_d_t;
                tmp._x_e = tmp._x_e_t;
                tmp._x_f = tmp._x_f_t;
                tmp._x_g = tmp._x_g_t;
            }
            else if (cmd.equalsIgnoreCase("get")) {
                if (tmp._prize != 0L) {
                    Npc_Mary._all_user_prize += tmp._prize;
                    Npc_Mary._log.warn("小瑪莉管理員 紀錄：" + pc.getName() + " 領取獎金(" + tmp._prize + ")");
                    CreateNewItem.createNewItem(pc, Npc_Mary._itemid1, tmp._prize);
                    tmp._prize = 0L;
                }
                tmp._x_a_t = tmp._x_a;
                tmp._x_b_t = tmp._x_b;
                tmp._x_c_t = tmp._x_c;
                tmp._x_d_t = tmp._x_d;
                tmp._x_e_t = tmp._x_e;
                tmp._x_f_t = tmp._x_f;
                tmp._x_g_t = tmp._x_g;
                tmp._x_a = 0;
                tmp._x_b = 0;
                tmp._x_c = 0;
                tmp._x_d = 0;
                tmp._x_e = 0;
                tmp._x_f = 0;
                tmp._x_g = 0;
                tmp._x_h_i = false;
                tmp._prize = 0L;
                long tgitem_count = pc.getInventory().countItems(Npc_Mary._itemid1);
                tmp._prize_all = all_prize();
                String[] info = { "資本:" + tgitem_count + "  彩金:" + tmp._prize_all, "00", "00", "00", "00", "00", "00", "00", (tmp._x10 == 1) ? "一次下注10點" : "一次下注1點" };
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bar_00", info));
                return;
            }
            else if (cmd.equalsIgnoreCase("a")) {
                Npc_Mary.MaryTemp maryTemp2 = tmp;
                maryTemp2._x_a = maryTemp2._x_a + 1 * tmp._x10;
            }
            else if (cmd.equalsIgnoreCase("b")) {
                Npc_Mary.MaryTemp maryTemp3 = tmp;
                maryTemp3._x_b = maryTemp3._x_b + 1 * tmp._x10;
            }
            else if (cmd.equalsIgnoreCase("c")) {
                Npc_Mary.MaryTemp maryTemp4 = tmp;
                maryTemp4._x_c = maryTemp4._x_c + 1 * tmp._x10;
            }
            else if (cmd.equalsIgnoreCase("d")) {
                Npc_Mary.MaryTemp maryTemp5 = tmp;
                maryTemp5._x_d = maryTemp5._x_d + 1 * tmp._x10;
            }
            else if (cmd.equalsIgnoreCase("e")) {
                Npc_Mary.MaryTemp maryTemp6 = tmp;
                maryTemp6._x_e = maryTemp6._x_e + 1 * tmp._x10;
            }
            else if (cmd.equalsIgnoreCase("f")) {
                Npc_Mary.MaryTemp maryTemp7 = tmp;
                maryTemp7._x_f = maryTemp7._x_f + 1 * tmp._x10;
            }
            else if (cmd.equalsIgnoreCase("g")) {
                Npc_Mary.MaryTemp maryTemp8 = tmp;
                maryTemp8._x_g = maryTemp8._x_g + 1 * tmp._x10;
            }
            else if (cmd.equalsIgnoreCase("XL99")) {
                Npc_Mary.MaryTemp maryTemp9 = tmp;
                maryTemp9._x_a = maryTemp9._x_a + 99;
                Npc_Mary.MaryTemp maryTemp10 = tmp;
                maryTemp10._x_b = maryTemp10._x_b + 99;
                Npc_Mary.MaryTemp maryTemp11 = tmp;
                maryTemp11._x_c = maryTemp11._x_c + 99;
                Npc_Mary.MaryTemp maryTemp12 = tmp;
                maryTemp12._x_d = maryTemp12._x_d + 99;
                Npc_Mary.MaryTemp maryTemp13 = tmp;
                maryTemp13._x_e = maryTemp13._x_e + 99;
                Npc_Mary.MaryTemp maryTemp14 = tmp;
                maryTemp14._x_f = maryTemp14._x_f + 99;
                Npc_Mary.MaryTemp maryTemp15 = tmp;
                maryTemp15._x_g = maryTemp15._x_g + 99;
            }
            else if (cmd.equalsIgnoreCase("XL50")) {
                Npc_Mary.MaryTemp maryTemp16 = tmp;
                maryTemp16._x_a = maryTemp16._x_a + 50;
                Npc_Mary.MaryTemp maryTemp17 = tmp;
                maryTemp17._x_b = maryTemp17._x_b + 50;
                Npc_Mary.MaryTemp maryTemp18 = tmp;
                maryTemp18._x_c = maryTemp18._x_c + 50;
                Npc_Mary.MaryTemp maryTemp19 = tmp;
                maryTemp19._x_d = maryTemp19._x_d + 50;
                Npc_Mary.MaryTemp maryTemp20 = tmp;
                maryTemp20._x_e = maryTemp20._x_e + 50;
                Npc_Mary.MaryTemp maryTemp21 = tmp;
                maryTemp21._x_f = maryTemp21._x_f + 50;
                Npc_Mary.MaryTemp maryTemp22 = tmp;
                maryTemp22._x_g = maryTemp22._x_g + 50;
            }
            else if (cmd.equalsIgnoreCase("XL10")) {
                Npc_Mary.MaryTemp maryTemp23 = tmp;
                maryTemp23._x_a = maryTemp23._x_a + 10;
                Npc_Mary.MaryTemp maryTemp24 = tmp;
                maryTemp24._x_b = maryTemp24._x_b + 10;
                Npc_Mary.MaryTemp maryTemp25 = tmp;
                maryTemp25._x_c = maryTemp25._x_c + 10;
                Npc_Mary.MaryTemp maryTemp26 = tmp;
                maryTemp26._x_d = maryTemp26._x_d + 10;
                Npc_Mary.MaryTemp maryTemp27 = tmp;
                maryTemp27._x_e = maryTemp27._x_e + 10;
                Npc_Mary.MaryTemp maryTemp28 = tmp;
                maryTemp28._x_f = maryTemp28._x_f + 10;
                Npc_Mary.MaryTemp maryTemp29 = tmp;
                maryTemp29._x_g = maryTemp29._x_g + 10;
            }
            else if (cmd.equalsIgnoreCase("XL05")) {
                Npc_Mary.MaryTemp maryTemp30 = tmp;
                maryTemp30._x_a = maryTemp30._x_a + 5;
                Npc_Mary.MaryTemp maryTemp31 = tmp;
                maryTemp31._x_b = maryTemp31._x_b + 5;
                Npc_Mary.MaryTemp maryTemp32 = tmp;
                maryTemp32._x_c = maryTemp32._x_c + 5;
                Npc_Mary.MaryTemp maryTemp33 = tmp;
                maryTemp33._x_d = maryTemp33._x_d + 5;
                Npc_Mary.MaryTemp maryTemp34 = tmp;
                maryTemp34._x_e = maryTemp34._x_e + 5;
                Npc_Mary.MaryTemp maryTemp35 = tmp;
                maryTemp35._x_f = maryTemp35._x_f + 5;
                Npc_Mary.MaryTemp maryTemp36 = tmp;
                maryTemp36._x_g = maryTemp36._x_g + 5;
            }
            else if (cmd.equalsIgnoreCase("XL01")) {
                Npc_Mary.MaryTemp maryTemp37 = tmp;
                maryTemp37._x_a = maryTemp37._x_a + 1;
                Npc_Mary.MaryTemp maryTemp38 = tmp;
                maryTemp38._x_b = maryTemp38._x_b + 1;
                Npc_Mary.MaryTemp maryTemp39 = tmp;
                maryTemp39._x_c = maryTemp39._x_c + 1;
                Npc_Mary.MaryTemp maryTemp40 = tmp;
                maryTemp40._x_d = maryTemp40._x_d + 1;
                Npc_Mary.MaryTemp maryTemp41 = tmp;
                maryTemp41._x_e = maryTemp41._x_e + 1;
                Npc_Mary.MaryTemp maryTemp42 = tmp;
                maryTemp42._x_f = maryTemp42._x_f + 1;
                Npc_Mary.MaryTemp maryTemp43 = tmp;
                maryTemp43._x_g = maryTemp43._x_g + 1;
            }
            else if (cmd.equalsIgnoreCase("h")) {
                if (Shutdown.SHUTDOWN) {
                    pc.sendPackets(new S_ServerMessage(166, "系統暫停接受下注，請儘快取回您的獎金"));
                    return;
                }
                else if (tmp._prize <= 0L) {
                    return;
                }
                else if (tmp._x_h_i_count >= 5) {
                    pc.sendPackets(new S_ServerMessage("\\fS比大小最多只能連勝五次!"));
                    return;
                }
                else {
                    tmp._x_h_i = true;
                    Npc_Mary.MaryTimer maryTimer2 = new Npc_Mary.MaryTimer(pc, npc, 2, -1);
                    maryTimer2.startCmd();
                    return;
                }
            }
            else if (cmd.equalsIgnoreCase("i")) {
                if (Shutdown.SHUTDOWN) {
                    pc.sendPackets(new S_ServerMessage(166, "系統暫停接受下注，請儘快取回您的獎金"));
                    return;
                }
                else if (tmp._prize <= 0L) {
                    return;
                }
                else if (tmp._x_h_i_count >= 5) {
                    pc.sendPackets(new S_ServerMessage("\\fS比大小最多只能連勝五次!"));
                    return;
                }
                else {
                    tmp._x_h_i = false;
                    Npc_Mary.MaryTimer maryTimer2 = new Npc_Mary.MaryTimer(pc, npc, 2, -1);
                    maryTimer2.startCmd();
                    return;
                }
            }
            else if (cmd.equalsIgnoreCase("x10")) {
                if (tmp._x10 == 1) {
                    tmp._x10 = 10;
                }
                else {
                    tmp._x10 = 1;
                }
            }
            else if (cmd.equalsIgnoreCase("HL")) {
                String[] info2 = { String.valueOf(Npc_Mary._x_a1), String.valueOf(Npc_Mary._x_a2), String.valueOf(Npc_Mary._x_b1), String.valueOf(Npc_Mary._x_b2), String.valueOf(Npc_Mary._x_c1), String.valueOf(Npc_Mary._x_c2), String.valueOf(Npc_Mary._x_d1), String.valueOf(Npc_Mary._x_d2), String.valueOf(Npc_Mary._x_e1), String.valueOf(Npc_Mary._x_e2), String.valueOf(Npc_Mary._x_f1), String.valueOf(Npc_Mary._x_f2), String.valueOf(Npc_Mary._x_g1), String.valueOf(Npc_Mary._x_g2) };
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bar_hl", info2));
                return;
            }
            if (tmp._x_a > 99) {
                tmp._x_a = 99;
            }
            if (tmp._x_b > 99) {
                tmp._x_b = 99;
            }
            if (tmp._x_c > 99) {
                tmp._x_c = 99;
            }
            if (tmp._x_d > 99) {
                tmp._x_d = 99;
            }
            if (tmp._x_e > 99) {
                tmp._x_e = 99;
            }
            if (tmp._x_f > 99) {
                tmp._x_f = 99;
            }
            if (tmp._x_g > 99) {
                tmp._x_g = 99;
            }
            long tgitem_count = pc.getInventory().countItems(Npc_Mary._itemid1);
            tmp._prize_all = all_prize();
            String[] info = { "資本:" + tgitem_count + "  彩金:" + tmp._prize_all, (tmp._x_a < 10) ? String.valueOf("0" + tmp._x_a) : String.valueOf(tmp._x_a), (tmp._x_b < 10) ? String.valueOf("0" + tmp._x_b) : String.valueOf(tmp._x_b), (tmp._x_c < 10) ? String.valueOf("0" + tmp._x_c) : String.valueOf(tmp._x_c), (tmp._x_d < 10) ? String.valueOf("0" + tmp._x_d) : String.valueOf(tmp._x_d), (tmp._x_e < 10) ? String.valueOf("0" + tmp._x_e) : String.valueOf(tmp._x_e), (tmp._x_f < 10) ? String.valueOf("0" + tmp._x_f) : String.valueOf(tmp._x_f), (tmp._x_g < 10) ? String.valueOf("0" + tmp._x_g) : String.valueOf(tmp._x_g), (tmp._x10 == 1) ? "一次下注10點" : "一次下注1點" };
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bar_00", info));
            return;
        }
    }
    
    /*
    static void access$4(long all_user_prize) {
        Npc_Mary._all_user_prize = all_user_prize;
    }
    */
    
    /*
    static void access$6(int count) {
        Npc_Mary._count = count;
    }
    */
    
    /*
    static void access$8(long all_stake) {
        Npc_Mary._all_stake = all_stake;
    }
    */
    
    private class MaryTemp
    {
        private long _prize_all;
        private int _count;
        private int _x_a;
        private int _x_b;
        private int _x_c;
        private int _x_d;
        private int _x_e;
        private int _x_f;
        private int _x_g;
        private boolean _x_h_i;
        private int _x_h_i_count;
        private long _prize;
        private boolean _stop;
        private int _x10;
        private int _x_a_t;
        private int _x_b_t;
        private int _x_c_t;
        private int _x_d_t;
        private int _x_e_t;
        private int _x_f_t;
        private int _x_g_t;
        
        private MaryTemp() {
            this._prize_all = 0L;
            this._count = 0;
            this._x_a = 0;
            this._x_b = 0;
            this._x_c = 0;
            this._x_d = 0;
            this._x_e = 0;
            this._x_f = 0;
            this._x_g = 0;
            this._x_h_i = false;
            this._x_h_i_count = 0;
            this._prize = 0L;
            this._stop = false;
            this._x10 = 1;
            this._x_a_t = 0;
            this._x_b_t = 0;
            this._x_c_t = 0;
            this._x_d_t = 0;
            this._x_e_t = 0;
            this._x_f_t = 0;
            this._x_g_t = 0;
        }
        
        /*
        static void access$0(Npc_Mary.MaryTemp maryTemp, boolean stop) {
            maryTemp._stop = stop;
        }
        */
        
        /*
        static void access$2(Npc_Mary.MaryTemp maryTemp, long prize) {
            maryTemp._prize = prize;
        }
        */
        
        /*
        static void access$5(Npc_Mary.MaryTemp maryTemp, int x_h_i_count) {
            maryTemp._x_h_i_count = x_h_i_count;
        }
        */
        
        /*
        static void access$16(Npc_Mary.MaryTemp maryTemp, int x_a) {
            maryTemp._x_a = x_a;
        }
        */
        
        /*
        static void access$17(Npc_Mary.MaryTemp maryTemp, int x_b) {
            maryTemp._x_b = x_b;
        }
        */
        
        /*
        static void access$18(Npc_Mary.MaryTemp maryTemp, int x_c) {
            maryTemp._x_c = x_c;
        }
        */
        
        /*
        static void access$19(Npc_Mary.MaryTemp maryTemp, int x_d) {
            maryTemp._x_d = x_d;
        }
        */
        
        /*
        static void access$20(Npc_Mary.MaryTemp maryTemp, int x_e) {
            maryTemp._x_e = x_e;
        }
        */
        
        /*
        static void access$21(Npc_Mary.MaryTemp maryTemp, int x_f) {
            maryTemp._x_f = x_f;
        }
        */
        
        /*
        static void access$22(Npc_Mary.MaryTemp maryTemp, int x_g) {
            maryTemp._x_g = x_g;
        }
        */
        
        /*
        static void access$23(Npc_Mary.MaryTemp maryTemp, int x_a_t) {
            maryTemp._x_a_t = x_a_t;
        }
        */
        
        /*
        static void access$24(Npc_Mary.MaryTemp maryTemp, int x_b_t) {
            maryTemp._x_b_t = x_b_t;
        }
        */
        
        /*
        static void access$25(Npc_Mary.MaryTemp maryTemp, int x_c_t) {
            maryTemp._x_c_t = x_c_t;
        }
        */
        
        /*
        static void access$26(Npc_Mary.MaryTemp maryTemp, int x_d_t) {
            maryTemp._x_d_t = x_d_t;
        }
        */
        
        /*
        static void access$27(Npc_Mary.MaryTemp maryTemp, int x_e_t) {
            maryTemp._x_e_t = x_e_t;
        }
        */
        
        /*
        static void access$28(Npc_Mary.MaryTemp maryTemp, int x_f_t) {
            maryTemp._x_f_t = x_f_t;
        }
        */
        
        /*
        static void access$29(Npc_Mary.MaryTemp maryTemp, int x_g_t) {
            maryTemp._x_g_t = x_g_t;
        }
        */
        
        /*
        static void access$30(Npc_Mary.MaryTemp maryTemp, boolean x_h_i) {
            maryTemp._x_h_i = x_h_i;
        }
        */
        
        /*
        static void access$31(Npc_Mary.MaryTemp maryTemp, long prize_all) {
            maryTemp._prize_all = prize_all;
        }
        */
        
        /*
        static void access$34(Npc_Mary.MaryTemp maryTemp, int count) {
            maryTemp._count = count;
        }
        */
        
        /*
        static void access$42(Npc_Mary.MaryTemp maryTemp, int x10) {
            maryTemp._x10 = x10;
        }
        */
    }
    
    private class MaryTimer implements Runnable
    {
        private final L1PcInstance _pc;
        private final L1NpcInstance _npc;
        private int _mode;
        private Npc_Mary.MaryTemp _tmp;
        private int _html_id;
        
        public MaryTimer(L1PcInstance pc, L1NpcInstance npc, int mode, int src_html) {
            this._html_id = -1;
            this._pc = pc;
            this._npc = npc;
            this._mode = mode;
            this._tmp = Npc_Mary._maryUsers.get((Integer)pc.getId());
            this._tmp._stop = true;
            this._html_id = src_html;
        }
        
        public void startCmd() {
            GeneralThreadPool.get().schedule(this, 10L);
        }
        
        @Override
        public void run() {
            int src_html = -1;
            try {
                if (this._tmp == null) {
                    Npc_Mary._log.error("小瑪莉管理員 異常:找不到 人物" + this._pc.getName() + " 的小瑪莉記錄!");
                    return;
                }
                else {
                    long tgitem_count = this._pc.getInventory().countItems(Npc_Mary._itemid1);
                    switch (this._mode) {
                        case 0: {
                            if (this._tmp._prize != 0L) {
                                this._pc.sendPackets(new S_ServerMessage("\\fT未領取彩金(" + this._tmp._prize + ")，現在發還給您"));
                                Npc_Mary._all_user_prize = Npc_Mary._all_user_prize + this._tmp._prize;
                                Npc_Mary._log.warn("小瑪莉管理員 紀錄：" + this._pc.getName() + " 發還未領取獎金(" + this._tmp._prize + ")");
                                CreateNewItem.createNewItem(this._pc, Npc_Mary._itemid1, this._tmp._prize);
                                this._tmp._prize = 0L;
                            }
                            String[] info0 = { "資本:" + tgitem_count + "  彩金:" + this._tmp._prize_all, "00", "00", "00", "00", "00", "00", "00", (this._tmp._x10 == 1) ? "一次下注10" : "一次下注1" };
                            for (int i = 6; i > 0; --i) {
                                Thread.sleep(250L);
                                if (i % 2 == 0) {
                                    this._pc.sendPackets(new S_NPCTalkReturn(this._npc.getId(), "bar_00", info0));
                                }
                                else {
                                    this._pc.sendPackets(new S_NPCTalkReturn(this._npc.getId(), "bar_20", info0));
                                }
                            }
                            break;
                        }
                        case 1: {
                            Npc_Mary._count = Npc_Mary._count + 1;
                            this._tmp._x_h_i_count = 0;
                            long count = (long)(this._tmp._x_a + this._tmp._x_b + this._tmp._x_c + this._tmp._x_d + this._tmp._x_e + this._tmp._x_f + this._tmp._x_g);
                            if (this._html_id == -1) {
                                L1ItemInstance tgitem = this._pc.getInventory().checkItemX(Npc_Mary._itemid1, count);
                                if (tgitem != null) {
                                    Npc_Mary._all_stake = Npc_Mary._all_stake + count;
                                    this._pc.getInventory().removeItem(tgitem.getId(), count);
                                }
                                else {
                                    L1Item tgItem = ItemTable.get().getTemplate(Npc_Mary._itemid1);
                                    this._pc.sendPackets(new S_ServerMessage(337, tgItem.getNameId()));
                                    this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                    break;
                                }
                            }
                            String[] info2 = { "資本:" + (tgitem_count - count) + "  彩金:" + this._tmp._prize_all, (this._tmp._x_a < 10) ? String.valueOf("0" + this._tmp._x_a) : String.valueOf(this._tmp._x_a), (this._tmp._x_b < 10) ? String.valueOf("0" + this._tmp._x_b) : String.valueOf(this._tmp._x_b), (this._tmp._x_c < 10) ? String.valueOf("0" + this._tmp._x_c) : String.valueOf(this._tmp._x_c), (this._tmp._x_d < 10) ? String.valueOf("0" + this._tmp._x_d) : String.valueOf(this._tmp._x_d), (this._tmp._x_e < 10) ? String.valueOf("0" + this._tmp._x_e) : String.valueOf(this._tmp._x_e), (this._tmp._x_f < 10) ? String.valueOf("0" + this._tmp._x_f) : String.valueOf(this._tmp._x_f), (this._tmp._x_g < 10) ? String.valueOf("0" + this._tmp._x_g) : String.valueOf(this._tmp._x_g) };
                            int index = 1;
                            int win = this.check(this._tmp._prize_all);
                            int count2 = 32 + win;
                            if (this._html_id != -1) {
                                if (this._html_id == 15) {
                                    count2 += 2;
                                }
                                else if (this._html_id == 7) {
                                    count2 += 10;
                                }
                                index = this._html_id;
                            }
                            int html_id = -1;
                            for (int j = 0; j < count2; ++j) {
                                Thread.sleep((long)(j * 3));
                                html_id = index;
                                this._pc.sendPackets(new S_NPCTalkReturn(this._npc.getId(), "bar_" + ((html_id < 10) ? ("0" + html_id) : (html_id)), info2));
                                if (++index > 16) {
                                    index = 1;
                                }
                            }
                            int add = 0;
                            String out = "";
                            switch (html_id) {
                                case 1: {
                                    add += this._tmp._x_b * Npc_Mary._x_b1;
                                    out = "(大半瓜:" + html_id + " 下注:" + this._tmp._x_b + ")";
                                    break;
                                }
                                case 2: {
                                    add += this._tmp._x_b * Npc_Mary._x_b2;
                                    out = "(小半瓜:" + html_id + " 下注:" + this._tmp._x_b + ")";
                                    break;
                                }
                                case 3: {
                                    add += this._tmp._x_a * Npc_Mary._x_a1;
                                    out = "(大BAR:" + html_id + " 下注:" + this._tmp._x_a + ")";
                                    break;
                                }
                                case 4: {
                                    add += this._tmp._x_d * Npc_Mary._x_d2;
                                    out = "(小西瓜:" + html_id + " 下注:" + this._tmp._x_d + ")";
                                    break;
                                }
                                case 5: {
                                    add += this._tmp._x_d * Npc_Mary._x_d1;
                                    out = "(大西瓜:" + html_id + " 下注:" + this._tmp._x_d + ")";
                                    break;
                                }
                                case 6: {
                                    add += this._tmp._x_e * Npc_Mary._x_e2;
                                    out = "(小香蕉:" + html_id + " 下注:" + this._tmp._x_e + ")";
                                    break;
                                }
                                case 7: {
                                    src_html = html_id;
                                    Thread.sleep(1000L);
                                    break;
                                }
                                case 8: {
                                    add += this._tmp._x_f * Npc_Mary._x_f1;
                                    out = "(大檸檬:" + html_id + " 下注:" + this._tmp._x_f + ")";
                                    break;
                                }
                                case 9: {
                                    add += this._tmp._x_g * Npc_Mary._x_g1;
                                    out = "(大橘子:" + html_id + " 下注:" + this._tmp._x_g + ")";
                                    break;
                                }
                                case 10: {
                                    add += this._tmp._x_g * Npc_Mary._x_g2;
                                    out = "(小橘子:" + html_id + " 下注:" + this._tmp._x_g + ")";
                                    break;
                                }
                                case 11: {
                                    add += this._tmp._x_a * Npc_Mary._x_a2;
                                    out = "(小BAR:" + html_id + " 下注:" + this._tmp._x_a + ")";
                                    break;
                                }
                                case 12: {
                                    add += this._tmp._x_c * Npc_Mary._x_c2;
                                    out = "(小蘋果:" + html_id + " 下注:" + this._tmp._x_c + ")";
                                    break;
                                }
                                case 13: {
                                    add += this._tmp._x_c * Npc_Mary._x_c1;
                                    out = "(大蘋果:" + html_id + " 下注:" + this._tmp._x_c + ")";
                                    break;
                                }
                                case 14: {
                                    add += this._tmp._x_f * Npc_Mary._x_f2;
                                    out = "(小檸檬:" + html_id + " 下注:" + this._tmp._x_f + ")";
                                    break;
                                }
                                case 15: {
                                    src_html = html_id;
                                    Thread.sleep(1000L);
                                    break;
                                }
                                case 16: {
                                    add += this._tmp._x_e * Npc_Mary._x_e1;
                                    out = "(大香蕉:" + html_id + " 下注:" + this._tmp._x_e + ")";
                                    break;
                                }
                            }
                            if (src_html == -1) {
                                Npc_Mary.MaryTemp tmp = this._tmp;
                                tmp._prize = tmp._prize + (long)add;
                                if (add > 0) {
                                    String bar = "";
                                    if (this._tmp._x_a == 99 && html_id == 3) {
                                        Npc_Mary.MaryTemp tmp2 = this._tmp;
                                        tmp2._prize = tmp2._prize + this._tmp._prize_all;
                                        bar = " 拉彩金:" + this._tmp._prize_all;
                                        this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 5763));
                                        World.get().broadcastPacketToAll(new S_ServerMessage("\\fV恭喜玩家" + this._pc.getName() + "BAR拉彩金 獎金:" + this._tmp._prize));
                                    }
                                    Npc_Mary._log.warn("小瑪莉管理員 紀錄：" + this._pc.getName() + " 下注:" + count + " 中獎:" + add + " 當時彩金:" + this._tmp._prize_all + "下注細項:" + this._tmp._x_a + "/" + this._tmp._x_b + "/" + this._tmp._x_c + "/" + this._tmp._x_d + "/" + this._tmp._x_e + "/" + this._tmp._x_f + "/" + this._tmp._x_g + " 開出" + out + bar + " (" + Npc_Mary._count + ")");
                                }
                                else {
                                    Npc_Mary._log.warn("小瑪莉管理員 紀錄：" + this._pc.getName() + " 下注:" + count + " 未中獎!" + " 當時彩金:" + this._tmp._prize_all + "下注細項:" + this._tmp._x_a + "/" + this._tmp._x_b + "/" + this._tmp._x_c + "/" + this._tmp._x_d + "/" + this._tmp._x_e + "/" + this._tmp._x_f + "/" + this._tmp._x_g + " 開出" + out + " (" + Npc_Mary._count + ")");
                                }
                                String[] info1_1 = { "得獎:" + this._tmp._prize + "  彩金:" + this._tmp._prize_all, "00", "00", "00", "00", "00", "00", "00" };
                                this._pc.sendPackets(new S_NPCTalkReturn(this._npc.getId(), "bar_" + ((html_id < 10) ? ("0" + html_id) : (html_id)), info1_1));
                                this._pc.sendPackets(new S_ServerMessage(166, "下注" + this._tmp._x_a + "/" + this._tmp._x_b + "/" + this._tmp._x_c + "/" + this._tmp._x_d + "/" + this._tmp._x_e + "/" + this._tmp._x_f + "/" + this._tmp._x_g + " 開出:" + out + " (" + Npc_Mary._count + ")"));
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        case 2: {
                            int count3 = 0;
                            String hi = "";
                            if (this._tmp._x_h_i) {
                                hi = "您目前選大 ";
                                count3 = 20;
                            }
                            else {
                                hi = "您目前選小 ";
                                count3 = 19;
                            }
                            String[] info3 = { hi + "得獎:" + this._tmp._prize, "00", "00", "00", "00", "00", "00", "00" };
                            int random = this.random();
                            if (random != 0) {
                                int r = (int)(Math.random() * 1000.0) + 1;
                                if (r < random) {
                                    if (this._tmp._x_h_i) {
                                        count3 = 19;
                                    }
                                    else {
                                        count3 = 20;
                                    }
                                }
                            }
                            for (int k = 0; k < count3; ++k) {
                                Thread.sleep((long)(k * 15));
                                if (k % 2 == 0) {
                                    this._pc.sendPackets(new S_NPCTalkReturn(this._npc.getId(), "bar_18", info3));
                                }
                                else {
                                    this._pc.sendPackets(new S_NPCTalkReturn(this._npc.getId(), "bar_19", info3));
                                }
                            }
                            String htmlid = "";
                            if (this._tmp._x_h_i) {
                                switch (count3) {
                                    case 19: {
                                        Npc_Mary.MaryTemp tmp3 = this._tmp;
                                        tmp3._prize = tmp3._prize * 2L;
                                        htmlid = "bar_21";
                                        hi = "您贏了 ";
                                        Npc_Mary.MaryTemp tmp4 = this._tmp;
                                        tmp4._x_h_i_count = tmp4._x_h_i_count + 1;
                                        switch (this._tmp._x_h_i_count) {
                                            case 1:
                                            case 2: {
                                                this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 7476));
                                                break;
                                            }
                                            case 3:
                                            case 4: {
                                                this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 7473));
                                                break;
                                            }
                                        }
                                    }
                                    case 20: {
                                        this._tmp._prize = 0L;
                                        htmlid = "bar_19";
                                        hi = "您輸了 ";
                                        break;
                                    }
                                }
                            }
                            else if (!this._tmp._x_h_i) {
                                switch (count3) {
                                    case 19: {
                                        this._tmp._prize = 0L;
                                        htmlid = "bar_18";
                                        hi = "您輸了 ";
                                        break;
                                    }
                                    case 20: {
                                        Npc_Mary.MaryTemp tmp5 = this._tmp;
                                        tmp5._prize = tmp5._prize * 2L;
                                        htmlid = "bar_22";
                                        hi = "您贏了 ";
                                        Npc_Mary.MaryTemp tmp6 = this._tmp;
                                        tmp6._x_h_i_count = tmp6._x_h_i_count + 1;
                                        switch (this._tmp._x_h_i_count) {
                                            case 1:
                                            case 2: {
                                                this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 7476));
                                                break;
                                            }
                                            case 3:
                                            case 4: {
                                                this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 7473));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (this._tmp._prize > 0L) {
                                if (this._tmp._x_h_i_count >= 5) {
                                    Npc_Mary.MaryTemp tmp7 = this._tmp;
                                    tmp7._prize = tmp7._prize * 2L;
                                    this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 7470));
                                    World.get().broadcastPacketToAll(new S_ServerMessage("\\fV恭喜玩家" + this._pc.getName() + "比大小過五關"));
                                }
                                Npc_Mary._log.warn("小瑪莉管理員 紀錄：" + this._pc.getName() + " 比大小獲勝 金額:" + this._tmp._prize + " 次數:" + this._tmp._x_h_i_count + " (" + Npc_Mary._count + ")");
                            }
                            else {
                                Npc_Mary._log.warn("小瑪莉管理員 紀錄：" + this._pc.getName() + " 比大小失敗 次數:" + this._tmp._x_h_i_count + " (" + Npc_Mary._count + ")");
                            }
                            String[] info2_1 = { hi + "得獎:" + this._tmp._prize, "00", "00", "00", "00", "00", "00", "00" };
                            this._pc.sendPackets(new S_NPCTalkReturn(this._npc.getId(), htmlid, info2_1));
                            this._pc.sendPackets(new S_ServerMessage("\\fT比大小" + ((this._tmp._prize > 0L) ? ("獲勝 獎金:" + this._tmp._prize) : "失敗") + " (" + Npc_Mary._count + ")"));
                            break;
                        }
                    }
                }
            }
            catch (Exception e) {
                Npc_Mary._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                this._tmp._stop = false;
                if (src_html != -1) {
                    Npc_Mary.MaryTimer maryTimer = new Npc_Mary.MaryTimer(this._pc, this._npc, 1, src_html);
                    maryTimer.startCmd();
                }
            }
        }
        
        private int random() {
            try {
                long out_prize1 = Npc_Mary._all_stake * (long)Npc_Mary._out_prize / 100L;
                long out_prize2 = out_prize1 - Npc_Mary._all_user_prize;
                if (out_prize2 <= 0L) {
                    if (Npc_Mary._random.nextInt(100) < 5) {
                        return 1000;
                    }
                    else {
                        return 0;
                    }
                }
                else if (this._tmp._prize << 1 > out_prize2) {
                    return 0;
                }
                else {
                    int lostRate = (int)((10000L - (this._tmp._prize * 10000L + 50000L) / 10000L) * 30L / 1000L);
                    switch (this._tmp._x_h_i_count) {
                        case 1: {
                            lostRate -= 20;
                            break;
                        }
                        case 2: {
                            lostRate -= 40;
                            break;
                        }
                        case 3: {
                            lostRate -= 60;
                            break;
                        }
                        case 4: {
                            lostRate -= 80;
                            break;
                        }
                        case 5: {
                            lostRate -= 100;
                            break;
                        }
                    }
                    if (this._tmp._prize << 1 <= out_prize2 / 10L) {
                        return lostRate + 200 + Npc_Mary._random.nextInt(200);
                    }
                    else {
                        return lostRate;
                    }
                }
            }
            catch (Exception e) {
                Npc_Mary._log.error(e.getLocalizedMessage(), e);
                return 0;
            }
        }
        
        private int check(long prize_all) {
            try {
                long out_prize1 = Npc_Mary._all_stake * (long)Npc_Mary._out_prize / 100L;
                long out_prize2 = out_prize1 - Npc_Mary._all_user_prize - prize_all;
                if (out_prize2 <= 0L) {
                    return Npc_Mary._min[Npc_Mary._random.nextInt(Npc_Mary._min.length)];
                }
                else {
                    boolean is_out = false;
                    int html_id = Npc_Mary._min[Npc_Mary._random.nextInt(Npc_Mary._min.length)];
                    int count = 0;
                    while (!is_out) {
                        ++count;
                        html_id = Npc_Mary._random.nextInt(16) + 1;
                        switch (html_id) {
                            case 1: {
                                if ((long)(this._tmp._x_b * Npc_Mary._x_b1) < out_prize2) {
                                    is_out = true;
                                    break;
                                }
                                else if (Npc_Mary._random.nextInt(100) <= 2) {
                                    is_out = true;
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 2: {
                                is_out = true;
                                break;
                            }
                            case 3: {
                                if ((long)(this._tmp._x_a * Npc_Mary._x_a1) < out_prize2) {
                                    if (this._tmp._x_a == 99) {
                                        if (Npc_Mary._random.nextInt(100) <= 5) {
                                            is_out = true;
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        is_out = true;
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                            case 4: {
                                is_out = true;
                                break;
                            }
                            case 5: {
                                if ((long)(this._tmp._x_d * Npc_Mary._x_d1) < out_prize2) {
                                    is_out = true;
                                    break;
                                }
                                else if (Npc_Mary._random.nextInt(100) <= 6) {
                                    is_out = true;
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 6: {
                                is_out = true;
                                break;
                            }
                            case 7: {
                                is_out = true;
                                break;
                            }
                            case 8: {
                                if ((long)(this._tmp._x_f * Npc_Mary._x_f1) < out_prize2) {
                                    is_out = true;
                                    break;
                                }
                                else if (Npc_Mary._random.nextInt(100) <= 10) {
                                    is_out = true;
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 9: {
                                if ((long)(this._tmp._x_g * Npc_Mary._x_g1) < out_prize2) {
                                    is_out = true;
                                    break;
                                }
                                else if (Npc_Mary._random.nextInt(100) <= 15) {
                                    is_out = true;
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 10: {
                                is_out = true;
                                break;
                            }
                            case 11: {
                                if ((long)(this._tmp._x_a * Npc_Mary._x_a2) < out_prize2) {
                                    is_out = true;
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 12: {
                                is_out = true;
                                break;
                            }
                            case 13: {
                                if ((long)(this._tmp._x_c * Npc_Mary._x_c1) < out_prize2) {
                                    is_out = true;
                                    break;
                                }
                                else if (Npc_Mary._random.nextInt(100) <= 4) {
                                    is_out = true;
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            case 14: {
                                is_out = true;
                                break;
                            }
                            case 15: {
                                is_out = true;
                                break;
                            }
                            case 16: {
                                if ((long)(this._tmp._x_e * Npc_Mary._x_e1) < out_prize2) {
                                    is_out = true;
                                    break;
                                }
                                else if (Npc_Mary._random.nextInt(100) <= 8) {
                                    is_out = true;
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        if (count >= 50) {
                            break;
                        }
                    }
                    if (is_out) {
                        return html_id;
                    }
                }
            }
            catch (Exception e) {
                Npc_Mary._log.error(e.getLocalizedMessage(), e);
            }
            return Npc_Mary._min[Npc_Mary._random.nextInt(Npc_Mary._min.length)];
        }
    }
}

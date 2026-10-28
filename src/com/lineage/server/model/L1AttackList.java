package com.lineage.server.model;

import java.util.Iterator;
import com.lineage.server.datatables.lock.SpawnBossReading;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Mon;

import java.util.HashMap;

public class L1AttackList
{
    protected static final HashMap<Integer, Integer> SKU1;
    protected static final HashMap<Integer, Integer> SKU2;
    protected static final HashMap<Integer, Integer> SKNPC;
    protected static final HashMap<Integer, Integer> PLNPC;
    protected static final HashMap<Integer, Integer> SKD1;
    protected static final HashMap<Integer, Integer> SKD2;
    public static final HashMap<Integer, Integer> SKD3;
    public static final HashMap<Integer, Integer> SKM0;
    protected static final HashMap<Integer, Integer> STRH;
    protected static final HashMap<Integer, Integer> DEXH;
    protected static final HashMap<Integer, Integer> STRD;
    protected static final HashMap<Integer, Integer> DEXD;
    protected static final HashMap<Integer, Integer[]> DNNPC;
    protected static final HashMap<Integer, Boolean> NZONE;
    public static final HashMap<Integer, Integer> INTH;
    protected static final HashMap<Integer, Integer> INTD;
    public static final HashMap<Integer, Integer> INTMPCONSUME;
    
    static {
        SKU1 = new HashMap();
        SKU2 = new HashMap();
        SKNPC = new HashMap();
        PLNPC = new HashMap();
        SKD1 = new HashMap();
        SKD2 = new HashMap();
        SKD3 = new HashMap();
        SKM0 = new HashMap();
        STRH = new HashMap();
        DEXH = new HashMap();
        STRD = new HashMap();
        DEXD = new HashMap();
        DNNPC = new HashMap();
        NZONE = new HashMap();
        INTH = new HashMap();
        INTD = new HashMap();
        INTMPCONSUME = new HashMap();
    }
    
    public static void load() {
        for (Integer bossid : SpawnBossReading.get().bossIds()) {
            Integer[] ids = { new Integer(66), new Integer(50), new Integer(33), new Integer(157), new Integer(103), new Integer(40), new Integer(20), new Integer(64), new Integer(56), new Integer(27), new Integer(183), new Integer(188), new Integer(193), new Integer(202), new Integer(212), new Integer(217), new Integer(80) };
            if (L1AttackList.DNNPC.get(bossid) == null) {
                L1AttackList.DNNPC.put(bossid, ids);
            }
        }
        L1AttackList.NZONE.put(new Integer(27), false);
        L1AttackList.NZONE.put(new Integer(29), false);
        L1AttackList.NZONE.put(new Integer(33), false);
        L1AttackList.NZONE.put(new Integer(39), false);
        L1AttackList.NZONE.put(new Integer(40), false);
        L1AttackList.NZONE.put(new Integer(47), false);
        L1AttackList.NZONE.put(new Integer(56), false);
        L1AttackList.NZONE.put(new Integer(44), false);
        L1AttackList.NZONE.put(new Integer(71), false);
        L1AttackList.NZONE.put(new Integer(76), false);
        L1AttackList.NZONE.put(new Integer(152), false);
        L1AttackList.NZONE.put(new Integer(153), false);
        L1AttackList.NZONE.put(new Integer(157), false);
        L1AttackList.NZONE.put(new Integer(161), false);
        L1AttackList.NZONE.put(new Integer(167), false);
        L1AttackList.NZONE.put(new Integer(174), false);
        L1AttackList.NZONE.put(new Integer(87), false);
        L1AttackList.NZONE.put(new Integer(66), false);
        L1AttackList.NZONE.put(new Integer(50), false);
        L1AttackList.NZONE.put(new Integer(80), false);
        L1AttackList.NZONE.put(new Integer(194), false);
        L1AttackList.NZONE.put(new Integer(173), false);
        L1AttackList.NZONE.put(new Integer(133), false);
        L1AttackList.NZONE.put(new Integer(145), false);
        L1AttackList.NZONE.put(new Integer(64), false);
        L1AttackList.NZONE.put(new Integer(193), false);
        L1AttackList.NZONE.put(new Integer(188), false);
        L1AttackList.NZONE.put(new Integer(183), false);
        L1AttackList.NZONE.put(new Integer(208), false);
        L1AttackList.NZONE.put(new Integer(67), false);
        L1AttackList.SKD1.put(new Integer(3016), new Integer(1));
        L1AttackList.SKD1.put(new Integer(3024), new Integer(1));
        L1AttackList.SKD1.put(new Integer(3016), new Integer(1));
        L1AttackList.SKD1.put(new Integer(3016), new Integer(1));
        L1AttackList.SKD1.put(new Integer(3048), new Integer(2));
        L1AttackList.SKD2.put(new Integer(3019), new Integer(1));
        L1AttackList.SKD2.put(new Integer(3027), new Integer(1));
        L1AttackList.SKD2.put(new Integer(3032), new Integer(1));
        L1AttackList.SKD2.put(new Integer(3040), new Integer(1));
        L1AttackList.SKD2.put(new Integer(3049), new Integer(2));
        L1AttackList.SKD3.put(new Integer(3008), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3009), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3010), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3011), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3012), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3013), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3014), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3024), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3025), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3026), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3027), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3028), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3029), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3030), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3040), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3041), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3042), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3043), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3044), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3045), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3046), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3015), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3031), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3047), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(3048), new Integer(-2));
        L1AttackList.SKD3.put(new Integer(3049), new Integer(-2));
        L1AttackList.SKD3.put(new Integer(3050), new Integer(-2));
        L1AttackList.SKD3.put(new Integer(3051), new Integer(-2));
        L1AttackList.SKD3.put(new Integer(181), new Integer(-5));
        L1AttackList.SKD3.put(new Integer(211), new Integer(-2));
        L1AttackList.SKD3.put(new Integer(68), new Integer(68));
        L1AttackList.SKM0.put(new Integer(78), new Integer(0));
        L1AttackList.SKM0.put(new Integer(50), new Integer(0));
        L1AttackList.SKM0.put(new Integer(80), new Integer(0));
        L1AttackList.SKM0.put(new Integer(157), new Integer(0));
        L1AttackList.SKU1.put(new Integer(3016), new Integer(1));
        L1AttackList.SKU1.put(new Integer(3024), new Integer(1));
        L1AttackList.SKU1.put(new Integer(3034), new Integer(2));
        L1AttackList.SKU1.put(new Integer(3042), new Integer(2));
        L1AttackList.SKU1.put(new Integer(3048), new Integer(1));
        L1AttackList.SKU2.put(new Integer(3019), new Integer(1));
        L1AttackList.SKU2.put(new Integer(3027), new Integer(1));
        L1AttackList.SKU2.put(new Integer(3032), new Integer(1));
        L1AttackList.SKU2.put(new Integer(3040), new Integer(1));
        L1AttackList.SKU2.put(new Integer(3049), new Integer(1));
        L1AttackList.SKNPC.put(new Integer(45912), new Integer(1013));
        L1AttackList.SKNPC.put(new Integer(45913), new Integer(1013));
        L1AttackList.SKNPC.put(new Integer(45914), new Integer(1013));
        L1AttackList.SKNPC.put(new Integer(45915), new Integer(1013));
        L1AttackList.SKNPC.put(new Integer(45916), new Integer(1014));
        L1AttackList.SKNPC.put(new Integer(45941), new Integer(1015));
        L1AttackList.SKNPC.put(new Integer(45752), new Integer(4005));
        L1AttackList.SKNPC.put(new Integer(45753), new Integer(4005));
        L1AttackList.SKNPC.put(new Integer(45675), new Integer(4006));
        L1AttackList.SKNPC.put(new Integer(81082), new Integer(4006));
        L1AttackList.SKNPC.put(new Integer(45625), new Integer(4006));
        L1AttackList.SKNPC.put(new Integer(45674), new Integer(4006));
        L1AttackList.SKNPC.put(new Integer(45685), new Integer(4006));
        L1AttackList.SKNPC.put(new Integer(87000), new Integer(4007));
        L1AttackList.SKNPC.put(new Integer(45020), new Integer(4008));
        L1AttackList.SKNPC.put(new Integer(99019), new Integer(1027));
        L1AttackList.PLNPC.put(new Integer(46069), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46070), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46071), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46072), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46073), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46074), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46075), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46076), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46077), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46078), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46079), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46080), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46081), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46082), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46083), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46084), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46085), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46086), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46087), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46088), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46089), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46090), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46091), new Integer(6035));
        L1AttackList.PLNPC.put(new Integer(46092), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46093), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46094), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46095), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46096), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46097), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46098), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46099), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46100), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46100), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46101), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46102), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46103), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46104), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46105), new Integer(6034));
        L1AttackList.PLNPC.put(new Integer(46106), new Integer(6034));
        for (int str = 0; str <= 7; ++str) {
            L1AttackList.STRH.put(new Integer(str), new Integer(4));
        }
        int strH = 4;
        for (int str2 = 8; str2 <= 254; ++str2) {
            if (str2 % 3 == 0 || str2 % 3 == 2) {
                ++strH;
            }
            L1AttackList.STRH.put(new Integer(str2), new Integer(strH));
        }
        for (int dex = 0; dex <= 7; ++dex) {
            L1AttackList.DEXH.put(new Integer(dex), new Integer(-3));
        }
        int dexH = -3;
        for (int dex2 = 8; dex2 <= 254; ++dex2) {
            ++dexH;
            L1AttackList.DEXH.put(new Integer(dex2), new Integer(dexH));
        }
        int dmgStr = -6;
        for (int str3 = 0; str3 <= 22; ++str3) {
            if (str3 % 2 == 1) {
                ++dmgStr;
            }
            L1AttackList.STRD.put(new Integer(str3), new Integer(dmgStr));
        }
        for (int str3 = 23; str3 <= 28; ++str3) {
            if (str3 % 3 == 2) {
                ++dmgStr;
            }
            L1AttackList.STRD.put(new Integer(str3), new Integer(dmgStr));
        }
        for (int str3 = 29; str3 <= 32; ++str3) {
            if (str3 % 2 == 1) {
                ++dmgStr;
            }
            L1AttackList.STRD.put(new Integer(str3), new Integer(dmgStr));
        }
        for (int str3 = 33; str3 <= 34; ++str3) {
            ++dmgStr;
            L1AttackList.STRD.put(new Integer(str3), new Integer(dmgStr));
        }
        for (int str3 = 35; str3 <= 254; ++str3) {
            if (str3 % 4 == 1) {
                ++dmgStr;
            }
            L1AttackList.STRD.put(new Integer(str3), new Integer(dmgStr));
        }
        for (int dex3 = 0; dex3 <= 8; ++dex3) {
            L1AttackList.DEXD.put(new Integer(dex3), new Integer(2));
        }
        int dmgDex = 2;
        for (int dex4 = 9; dex4 <= 254; ++dex4) {
            if (dex4 % 3 == 0) {
                ++dmgDex;
            }
            L1AttackList.DEXD.put(new Integer(dex4), new Integer(dmgDex));
        }
        for (int intel = 0; intel <= 22; ++intel) {
            L1AttackList.INTH.put(new Integer(intel), new Integer(0));
        }
        int intH = 0;
        for (int intel2 = 23; intel2 <= 254; ++intel2) {
            if (intel2 % 3 == 2) {
                ++intH;
            }
            L1AttackList.INTH.put(new Integer(intel2), new Integer(intH));
        }
        for (int intel2 = 0; intel2 <= 14; ++intel2) {
            L1AttackList.INTD.put(new Integer(intel2), new Integer(0));
        }
        int dmgint = 0;
        for (int intel3 = 15; intel3 <= 254; ++intel3) {
            if (intel3 % 5 == 0) {
                ++dmgint;
            }
            L1AttackList.INTD.put(new Integer(intel3), new Integer(dmgint));
        }
        for (int intel3 = 0; intel3 <= 7; ++intel3) {
            L1AttackList.INTMPCONSUME.put(new Integer(intel3), new Integer(0));
        }
        for (int intel3 = 0; intel3 <= 7; ++intel3) {
            L1AttackList.INTMPCONSUME.put(new Integer(intel3), new Integer(0));
        }
        int minusmpconsume = 5;
        for (int intel4 = 8; intel4 <= 254; ++intel4) {
            if (intel4 % 3 == 0 || intel4 % 3 == 2) {
                ++minusmpconsume;
            }
            if (minusmpconsume > 30) {
                minusmpconsume = 30;
            }
            L1AttackList.INTMPCONSUME.put(new Integer(intel4), new Integer(minusmpconsume));
        }
    }
    
	public static void accumulateNpcDamage(L1PcInstance pc, L1NpcInstance npc, double finalDmg) {
		if (npc == null || pc == null || finalDmg <= 0) {
			return;
		}
	
		int npcId = npc.getNpcTemplate().get_npcId();
		if (npcId != L1Mon.CheckNpcMid(npcId)) {
			return;
		}
	
		if (pc.getnpciddmg() != 0 && pc.getnpciddmg() != npcId) {
			pc.sendPackets(new S_ServerMessage("\\fU您攻擊累積傷害的怪物不是同一隻，累積傷害已歸0。"));
			pc.setnpcdmg(0.0);
			pc.setnpciddmg(0);
			// 雖然重置了，但這次的傷害還是要算，所以繼續往下走
		}
	
		// 設定新的目標怪物ID並累積傷害
		pc.setnpciddmg(npcId);
		double newTotal = pc.getnpcdmg() + finalDmg;
		pc.setnpcdmg(newTotal);
		pc.sendPackets(new S_ServerMessage("\\fU累積攻擊傷害 " + (int) newTotal + " 點"));
	}
}

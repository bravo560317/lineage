package com.lineage.data.npc;

import java.util.ArrayList;
import java.util.Random;

import com.lineage.config.Configpoly;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.datatables.polyHeChengTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.templates.L1polyHeCheng;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.World;


public class Npc_PolyCombind extends NpcExecutor {
	
	private int[] poly1 = Configpoly.poly_LIST_1;
	private int[] poly2 = Configpoly.poly_LIST_2;
	private int[] poly3 = Configpoly.poly_LIST_3;
	private int[] poly4 = Configpoly.poly_LIST_4;
	private int[] poly5 = Configpoly.poly_LIST_5;
	private int[] poly6 = Configpoly.poly_LIST_6;
	private int[] poly7 = Configpoly.poly_LIST_7;
	Random _random = new Random();
	
	public static NpcExecutor get() {
		return new Npc_PolyCombind();
	}

	public int type() {
		return 3;
	}

	public void talk(L1PcInstance pc, L1NpcInstance npc) {
		pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind1"));
	}

	public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
//////////////////////////////////////////////////////////////////////////////////////////
		if (cmd.equalsIgnoreCase("A1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(Configpoly.CONSUME2 + pc.getpolyrun2());
			data[1] = String.valueOf(Configpoly.SHULIANG2 + pc.getpolyCount());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind2",data));
		}
		if (cmd.equalsIgnoreCase("A11")) {
			if (Configpoly.CONSUME2 + pc.getpolyrun2() >= Configpoly.ZUIDAJILV2) {
				pc.sendPackets(new S_SystemMessage("合成二階段變身卡最高機率" + Configpoly.ZUIDAJILV2 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount(pc.getpolyCount() + 1);
			pc.setpolyrun2(pc.getpolyrun2() + Configpoly.ZENGJIAJILV2);	
			data[0] = String.valueOf(Configpoly.CONSUME2 + pc.getpolyrun2());
			data[1] = String.valueOf(Configpoly.SHULIANG2 + pc.getpolyCount());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind2",data));
		}
		if (cmd.equalsIgnoreCase("A12")) {
			if (Configpoly.CONSUME2 + pc.getpolyrun2() <= Configpoly.ZUIXIAOJILV2) {
				pc.sendPackets(new S_SystemMessage("合成二階段變身卡最低機率" + Configpoly.ZUIXIAOJILV2 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount(pc.getpolyCount() - 1);
			pc.setpolyrun2(pc.getpolyrun2() - Configpoly.ZENGJIAJILV2);	
			data[0] = String.valueOf(Configpoly.CONSUME2 + pc.getpolyrun2());
			data[1] = String.valueOf(Configpoly.SHULIANG2 + pc.getpolyCount());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind2",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////		
		if (cmd.equalsIgnoreCase("B1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(Configpoly.CONSUME3 + pc.getpolyrun3());
			data[1] = String.valueOf(Configpoly.SHULIANG3 + pc.getpolyCount2());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind3",data));
		}
		if (cmd.equalsIgnoreCase("B11")) {
			if (Configpoly.CONSUME3 + pc.getpolyrun3() >= Configpoly.ZUIDAJILV3) {
				pc.sendPackets(new S_SystemMessage("合成三階段變身卡最高機率" + Configpoly.ZUIDAJILV3 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount2(pc.getpolyCount2() + 1);
			pc.setpolyrun3(pc.getpolyrun3() + Configpoly.ZENGJIAJILV3);
			data[0] = String.valueOf(Configpoly.CONSUME3 + pc.getpolyrun3());
			data[1] = String.valueOf(Configpoly.SHULIANG3 + pc.getpolyCount2());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind3",data));
		}
		if (cmd.equalsIgnoreCase("B12")) {
			if (Configpoly.CONSUME3 + pc.getpolyrun3() <= Configpoly.ZUIXIAOJILV3) {
				pc.sendPackets(new S_SystemMessage("合成三階段變身卡最低機率" + Configpoly.ZUIXIAOJILV3 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount2(pc.getpolyCount2() - 1);
			pc.setpolyrun3(pc.getpolyrun3() - Configpoly.ZENGJIAJILV3);	
			data[0] = String.valueOf(Configpoly.CONSUME3 + pc.getpolyrun3());
			data[1] = String.valueOf(Configpoly.SHULIANG3 + pc.getpolyCount2());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind3",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////
		if (cmd.equalsIgnoreCase("C1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(Configpoly.CONSUME4 + pc.getpolyrun4());
			data[1] = String.valueOf(Configpoly.SHULIANG4 + pc.getpolyCount3());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind4",data));
		}
		if (cmd.equalsIgnoreCase("C11")) {
			if (Configpoly.CONSUME4 + pc.getpolyrun4() >= Configpoly.ZUIDAJILV4) {
				pc.sendPackets(new S_SystemMessage("合成四階段變身卡最高機率" + Configpoly.ZUIDAJILV4 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount3(pc.getpolyCount3() + 1);
			pc.setpolyrun4(pc.getpolyrun4() + Configpoly.ZENGJIAJILV4);	
			data[0] = String.valueOf(Configpoly.CONSUME4 + pc.getpolyrun4());
			data[1] = String.valueOf(Configpoly.SHULIANG4 + pc.getpolyCount3());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind4",data));
		}
		if (cmd.equalsIgnoreCase("C12")) {
			if (Configpoly.CONSUME4 + pc.getpolyrun4() <= Configpoly.ZUIXIAOJILV4) {
				pc.sendPackets(new S_SystemMessage("合成四階段變身卡最低機率" + Configpoly.ZUIXIAOJILV4 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount3(pc.getpolyCount3() - 1);
			pc.setpolyrun4(pc.getpolyrun4() - Configpoly.ZENGJIAJILV4);	
			data[0] = String.valueOf(Configpoly.CONSUME4 + pc.getpolyrun4());
			data[1] = String.valueOf(Configpoly.SHULIANG4 + pc.getpolyCount3());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind4",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////
		if (cmd.equalsIgnoreCase("D1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(Configpoly.CONSUME5 + pc.getpolyrun5());
			data[1] = String.valueOf(Configpoly.SHULIANG5 + pc.getpolyCount4());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind5",data));
		}
		if (cmd.equalsIgnoreCase("D11")) {
			if (Configpoly.CONSUME5 + pc.getpolyrun5() >= Configpoly.ZUIDAJILV5) {
				pc.sendPackets(new S_SystemMessage("合成五階段變身卡最高機率" + Configpoly.ZUIDAJILV5 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount4(pc.getpolyCount4() + 1);
			pc.setpolyrun5(pc.getpolyrun5() + Configpoly.ZENGJIAJILV5);	
			data[0] = String.valueOf(Configpoly.CONSUME5 + pc.getpolyrun5());
			data[1] = String.valueOf(Configpoly.SHULIANG5 + pc.getpolyCount4());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind5",data));
		}
		if (cmd.equalsIgnoreCase("D12")) {
			if (Configpoly.CONSUME5 + pc.getpolyrun5() <= Configpoly.ZUIXIAOJILV5) {
				pc.sendPackets(new S_SystemMessage("合成五階段變身卡最低機率" + Configpoly.ZUIXIAOJILV5 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount4(pc.getpolyCount4() - 1);
			pc.setpolyrun5(pc.getpolyrun5() - Configpoly.ZENGJIAJILV5);	
			data[0] = String.valueOf(Configpoly.CONSUME5 + pc.getpolyrun5());
			data[1] = String.valueOf(Configpoly.SHULIANG5 + pc.getpolyCount4());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind5",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////
		if (cmd.equalsIgnoreCase("E1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(Configpoly.CONSUME6 + pc.getpolyrun6());
			data[1] = String.valueOf(Configpoly.SHULIANG6 + pc.getpolyCount5());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind6",data));
		}
		if (cmd.equalsIgnoreCase("E11")) {
			if (Configpoly.CONSUME6 + pc.getpolyrun6() >= Configpoly.ZUIDAJILV6) {
				pc.sendPackets(new S_SystemMessage("合成六階段變身卡最高機率" + Configpoly.ZUIDAJILV6 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount5(pc.getpolyCount5() + 1);
			pc.setpolyrun6(pc.getpolyrun6() + Configpoly.ZENGJIAJILV6);	
			data[0] = String.valueOf(Configpoly.CONSUME6 + pc.getpolyrun6());
			data[1] = String.valueOf(Configpoly.SHULIANG6 + pc.getpolyCount5());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind6",data));
		}
		if (cmd.equalsIgnoreCase("E12")) {
			if (Configpoly.CONSUME6 + pc.getpolyrun6() <= Configpoly.ZUIXIAOJILV6) {
				pc.sendPackets(new S_SystemMessage("合成六階段變身卡最低機率" + Configpoly.ZUIXIAOJILV6 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount5(pc.getpolyCount5() - 1);
			pc.setpolyrun6(pc.getpolyrun6() - Configpoly.ZENGJIAJILV6);	
			data[0] = String.valueOf(Configpoly.CONSUME6 + pc.getpolyrun6());
			data[1] = String.valueOf(Configpoly.SHULIANG6 + pc.getpolyCount5());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind6",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////
		if (cmd.equalsIgnoreCase("F1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(Configpoly.CONSUME7 + pc.getpolyrun7());
			data[1] = String.valueOf(Configpoly.SHULIANG7 + pc.getpolyCount6());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind7",data));
		}
		if (cmd.equalsIgnoreCase("F11")) {
			if (Configpoly.CONSUME7 + pc.getpolyrun7() >= Configpoly.ZUIDAJILV7) {
				pc.sendPackets(new S_SystemMessage("合成七階段變身卡最高機率" + Configpoly.ZUIDAJILV7 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount6(pc.getpolyCount6() + 1);
			pc.setpolyrun7(pc.getpolyrun7() + Configpoly.ZENGJIAJILV7);	
			data[0] = String.valueOf(Configpoly.CONSUME7 + pc.getpolyrun7());
			data[1] = String.valueOf(Configpoly.SHULIANG7 + pc.getpolyCount6());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind7",data));
		}
		if (cmd.equalsIgnoreCase("F12")) {
			if (Configpoly.CONSUME7 + pc.getpolyrun7() <= Configpoly.ZUIXIAOJILV7) {
				pc.sendPackets(new S_SystemMessage("合成七階段變身卡最低機率" + Configpoly.ZUIXIAOJILV7 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setpolyCount6(pc.getpolyCount6() - 1);
			pc.setpolyrun7(pc.getpolyrun7() - Configpoly.ZENGJIAJILV7);	
			data[0] = String.valueOf(Configpoly.CONSUME7 + pc.getpolyrun7());
			data[1] = String.valueOf(Configpoly.SHULIANG7 + pc.getpolyCount6());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind7",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////
		boolean enough = false;
		ArrayList<Integer> cousumepolys = new ArrayList<Integer>();
		int chance = 0;
		int needcount = 0;
		int[] oldpolys = null;
		int newpoly = 0;
		final String[] data = new String[3];
		switch (cmd) {
		case "A":
			chance = Configpoly.CONSUME2 + pc.getpolyrun2();
			needcount = Configpoly.SHULIANG2 + pc.getpolyCount();
			oldpolys = poly1;
			newpoly = poly2[_random.nextInt(poly2.length)];
			break;
		case "B":
			chance = Configpoly.CONSUME3 + pc.getpolyrun3();
			needcount = Configpoly.SHULIANG3 + pc.getpolyCount2();
			oldpolys = poly2;
			newpoly = poly3[_random.nextInt(poly3.length)];
			break;
		case "C":
			chance = Configpoly.CONSUME4 + pc.getpolyrun4();
			needcount = Configpoly.SHULIANG4 + pc.getpolyCount3();
			oldpolys = poly3;
			newpoly = poly4[_random.nextInt(poly4.length)];
			break;
		case "D":
			chance = Configpoly.CONSUME5 + pc.getpolyrun5();
			needcount = Configpoly.SHULIANG5 + pc.getpolyCount4();
			oldpolys = poly4;
			newpoly = poly5[_random.nextInt(poly5.length)];
			break;
		case "E":
			chance = Configpoly.CONSUME6 + pc.getpolyrun6();
			needcount = Configpoly.SHULIANG6 + pc.getpolyCount5();
			oldpolys = poly5;
			newpoly = poly6[_random.nextInt(poly6.length)];
			break;	
		case "F":
			chance = Configpoly.CONSUME7 + pc.getpolyrun7();
			needcount = Configpoly.SHULIANG7 + pc.getpolyCount6();
			oldpolys = poly6;
			newpoly = poly7[_random.nextInt(poly7.length)];
			break;	
		
		}

int collected = 0;
for (int i = 0; i < oldpolys.length && collected < needcount; i++) {
    L1ItemInstance[] polys = pc.getInventory().findItemsId(oldpolys[i]);
    if (polys != null) {
        for (L1ItemInstance item : polys) {
            int itemid = item.getItemId();
            long count = item.getCount();

            for (int n = 0; n < count && collected < needcount; n++) {
                cousumepolys.add(itemid);
                collected++;
            }
            if (collected >= needcount) break;
        }
    }
}

		if (cousumepolys.size() == needcount) {
			enough = true;
			if (pc.getInventory().consumeItemsIdArray(cousumepolys)) {
				try {
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc01", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc02", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc03", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc04", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc05", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc06", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc07", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc08", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc11", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc12", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc11", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc12", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc11", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc12", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc11", data));
					Thread.sleep(70);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc12", data));
					Thread.sleep(100);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc11", data));
					Thread.sleep(200);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc12", data));
					Thread.sleep(300);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc11", data));
					Thread.sleep(400);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc12", data));
					Thread.sleep(500);
				    pc.setpolyCount(0);
				    pc.setpolyCount2(0);
				    pc.setpolyCount3(0);
				    pc.setpolyCount4(0);
				    pc.setpolyCount5(0);
				    pc.setpolyCount6(0);
				    pc.setpolyrun2(0);
				    pc.setpolyrun3(0);
				    pc.setpolyrun4(0);
				    pc.setpolyrun5(0);
				    pc.setpolyrun6(0);
				    pc.setpolyrun7(0);
				if (_random.nextInt(100) < chance) {
					final L1ItemInstance item = ItemTable.get().createItem(newpoly);
					int itemId = item.getItem().getItemId();
					final StringBuilder stringBuilder = new StringBuilder();
					final L1polyHeCheng card1 = polyHeChengTable.getInstance().getTemplate(itemId);
		
					if (card1 != null) {
					   stringBuilder.append(String.valueOf(card1.getGfxid()) + ",");
					   if (card1.getNot() != 0) {
						  World.get().broadcastPacketToAll(new S_BlueMessage(166, 
								  "\\f=恭喜玩家\\fN【" + pc.getName() + "】\\f=合成了變身卡\\fN【" + item.getLogName() + "】"));
					   }
					}
					final String[] msg = stringBuilder.toString().split(",");
					item.setIdentified(true);
					pc.getInventory().storeItem(item);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "bkhccg", msg));
					pc.sendPackets(new S_SystemMessage("恭喜你，合成了" + item.getLogName()));
					Thread.sleep(2000);
					pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind1"));

				} else {
					pc.sendPackets(new S_SystemMessage("合成變身卡失敗了。"));
					final L1ItemInstance item = ItemTable.get()
							.createItem(cousumepolys.get(_random.nextInt(cousumepolys.size())));
					int itemId = item.getItem().getItemId();
					final StringBuilder stringBuilder = new StringBuilder();
					final L1polyHeCheng card1 = polyHeChengTable.getInstance().getTemplate(itemId);
					
						if (card1 != null) {														
						stringBuilder.append(String.valueOf(card1.getGfxid()) + ",");
						}
					final String[] msg = stringBuilder.toString().split(",");
					item.setIdentified(true);
					pc.getInventory().storeItem(item);
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "bkhcsb", msg));
					pc.sendPackets(new S_SystemMessage("很遺憾，合成失敗，返還" + item.getLogName()));
					Thread.sleep(2000);
					pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "polycombind1"));
				}
			}catch (InterruptedException e) {
					// TODO 自動生成的 catch 塊
					e.printStackTrace();
		    }
	      }
	   }
		if (!enough) {
		 pc.sendPackets(new S_SystemMessage("你的變身卡不足【" + needcount + "】張"));
	  }
    }
	
}

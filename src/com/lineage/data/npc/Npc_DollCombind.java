package com.lineage.data.npc;

import java.util.ArrayList;
import java.util.Random;

import com.lineage.config.ConfigDoll;
import com.lineage.config.ConfigRate;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.datatables.DollHeChengTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.templates.L1DollHeCheng;
import com.lineage.server.world.World;


public class Npc_DollCombind extends NpcExecutor {
	
	private int[] doll1 = ConfigDoll.DOLL_LIST_1;
	private int[] doll2 = ConfigDoll.DOLL_LIST_2;
	private int[] doll3 = ConfigDoll.DOLL_LIST_3;
	private int[] doll4 = ConfigDoll.DOLL_LIST_4;
	private int[] doll5 = ConfigDoll.DOLL_LIST_5;
	private int[] doll6 = ConfigDoll.DOLL_LIST_6;
	Random _random = new Random();
	
	public static NpcExecutor get() {
		return new Npc_DollCombind();
	}

	public int type() {
		return 3;
	}

	public void talk(L1PcInstance pc, L1NpcInstance npc) {
		pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind1"));
	}

	public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
//////////////////////////////////////////////////////////////////////////////////////////
		if (cmd.equalsIgnoreCase("A1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(ConfigDoll.CONSUME2 + pc.getDollrun2());
			data[1] = String.valueOf(ConfigDoll.SHULIANG2 + pc.getDollCount());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind2",data));
		}
		if (cmd.equalsIgnoreCase("A11")) {
			if (ConfigDoll.CONSUME2 + pc.getDollrun2() >= ConfigDoll.ZUIDAJILV2) {
				pc.sendPackets(new S_SystemMessage("合成二階段娃娃最高機率" + ConfigDoll.ZUIDAJILV2 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount(pc.getDollCount() + 1);
			pc.setDollrun2(pc.getDollrun2() + ConfigDoll.ZENGJIAJILV2);	
			data[0] = String.valueOf(ConfigDoll.CONSUME2 + pc.getDollrun2());
			data[1] = String.valueOf(ConfigDoll.SHULIANG2 + pc.getDollCount());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind2",data));
		}
		if (cmd.equalsIgnoreCase("A12")) {
			if (ConfigDoll.CONSUME2 + pc.getDollrun2() <= ConfigDoll.ZUIXIAOJILV2) {
				pc.sendPackets(new S_SystemMessage("合成二階段娃娃最低機率" + ConfigDoll.ZUIXIAOJILV2 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount(pc.getDollCount() - 1);
			pc.setDollrun2(pc.getDollrun2() - ConfigDoll.ZENGJIAJILV2);	
			data[0] = String.valueOf(ConfigDoll.CONSUME2 + pc.getDollrun2());
			data[1] = String.valueOf(ConfigDoll.SHULIANG2 + pc.getDollCount());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind2",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////		
		if (cmd.equalsIgnoreCase("B1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(ConfigDoll.CONSUME3 + pc.getDollrun3());
			data[1] = String.valueOf(ConfigDoll.SHULIANG3 + pc.getDollCount2());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind3",data));
		}
		if (cmd.equalsIgnoreCase("B11")) {
			if (ConfigDoll.CONSUME3 + pc.getDollrun3() >= ConfigDoll.ZUIDAJILV3) {
				pc.sendPackets(new S_SystemMessage("合成三階段娃娃最高機率" + ConfigDoll.ZUIDAJILV3 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount2(pc.getDollCount2() + 1);
			pc.setDollrun3(pc.getDollrun3() + ConfigDoll.ZENGJIAJILV3);	
			data[0] = String.valueOf(ConfigDoll.CONSUME3 + pc.getDollrun3());
			data[1] = String.valueOf(ConfigDoll.SHULIANG3 + pc.getDollCount2());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind3",data));
		}
		if (cmd.equalsIgnoreCase("B12")) {
			if (ConfigDoll.CONSUME3 + pc.getDollrun3() <= ConfigDoll.ZUIXIAOJILV3) {
				pc.sendPackets(new S_SystemMessage("合成三階段娃娃最低機率" + ConfigDoll.ZUIXIAOJILV3 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount2(pc.getDollCount2() - 1);
			pc.setDollrun3(pc.getDollrun3() - ConfigDoll.ZENGJIAJILV3);	
			data[0] = String.valueOf(ConfigDoll.CONSUME3 + pc.getDollrun3());
			data[1] = String.valueOf(ConfigDoll.SHULIANG3 + pc.getDollCount2());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind3",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////
		if (cmd.equalsIgnoreCase("C1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(ConfigDoll.CONSUME4 + pc.getDollrun4());
			data[1] = String.valueOf(ConfigDoll.SHULIANG4 + pc.getDollCount3());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind4",data));
		}
		if (cmd.equalsIgnoreCase("C11")) {
			if (ConfigDoll.CONSUME4 + pc.getDollrun4() >= ConfigDoll.ZUIDAJILV4) {
				pc.sendPackets(new S_SystemMessage("合成四階段娃娃最高機率" + ConfigDoll.ZUIDAJILV4 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount3(pc.getDollCount3() + 1);
			pc.setDollrun4(pc.getDollrun4() + ConfigDoll.ZENGJIAJILV4);	
			data[0] = String.valueOf(ConfigDoll.CONSUME4 + pc.getDollrun4());
			data[1] = String.valueOf(ConfigDoll.SHULIANG4 + pc.getDollCount3());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind4",data));
		}
		if (cmd.equalsIgnoreCase("C12")) {
			if (ConfigDoll.CONSUME4 + pc.getDollrun4() <= ConfigDoll.ZUIXIAOJILV4) {
				pc.sendPackets(new S_SystemMessage("合成四階段娃娃最低機率" + ConfigDoll.ZUIXIAOJILV4 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount3(pc.getDollCount3() - 1);
			pc.setDollrun4(pc.getDollrun4() - ConfigDoll.ZENGJIAJILV4);	
			data[0] = String.valueOf(ConfigDoll.CONSUME4 + pc.getDollrun4());
			data[1] = String.valueOf(ConfigDoll.SHULIANG4 + pc.getDollCount3());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind4",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////
		if (cmd.equalsIgnoreCase("D1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(ConfigDoll.CONSUME5 + pc.getDollrun5());
			data[1] = String.valueOf(ConfigDoll.SHULIANG5 + pc.getDollCount4());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind5",data));
		}
		if (cmd.equalsIgnoreCase("D11")) {
			if (ConfigDoll.CONSUME5 + pc.getDollrun5() >= ConfigDoll.ZUIDAJILV5) {
				pc.sendPackets(new S_SystemMessage("合成五階段娃娃最高機率" + ConfigDoll.ZUIDAJILV5 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount4(pc.getDollCount4() + 1);
			pc.setDollrun5(pc.getDollrun5() + ConfigDoll.ZENGJIAJILV5);	
			data[0] = String.valueOf(ConfigDoll.CONSUME5 + pc.getDollrun5());
			data[1] = String.valueOf(ConfigDoll.SHULIANG5 + pc.getDollCount4());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind5",data));
		}
		if (cmd.equalsIgnoreCase("D12")) {
			if (ConfigDoll.CONSUME5 + pc.getDollrun5() <= ConfigDoll.ZUIXIAOJILV5) {
				pc.sendPackets(new S_SystemMessage("合成五階段娃娃最低機率" + ConfigDoll.ZUIXIAOJILV5 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount4(pc.getDollCount4() - 1);
			pc.setDollrun5(pc.getDollrun5() - ConfigDoll.ZENGJIAJILV5);	
			data[0] = String.valueOf(ConfigDoll.CONSUME5 + pc.getDollrun5());
			data[1] = String.valueOf(ConfigDoll.SHULIANG5 + pc.getDollCount4());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind5",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////
		if (cmd.equalsIgnoreCase("E1")) {
			String[] data = new String[3];
			data[0] = String.valueOf(ConfigDoll.CONSUME6 + pc.getDollrun6());
			data[1] = String.valueOf(ConfigDoll.SHULIANG6 + pc.getDollCount5());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind6",data));
		}
		if (cmd.equalsIgnoreCase("E11")) {
			if (ConfigDoll.CONSUME6 + pc.getDollrun6() >= ConfigDoll.ZUIDAJILV6) {
				pc.sendPackets(new S_SystemMessage("合成六階段娃娃最高機率" + ConfigDoll.ZUIDAJILV6 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount5(pc.getDollCount5() + 1);
			pc.setDollrun6(pc.getDollrun6() + ConfigDoll.ZENGJIAJILV6);	
			data[0] = String.valueOf(ConfigDoll.CONSUME6 + pc.getDollrun6());
			data[1] = String.valueOf(ConfigDoll.SHULIANG6 + pc.getDollCount5());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind6",data));
		}
		if (cmd.equalsIgnoreCase("E12")) {
			if (ConfigDoll.CONSUME6 + pc.getDollrun6() <= ConfigDoll.ZUIXIAOJILV6) {
				pc.sendPackets(new S_SystemMessage("合成六階段娃娃最低機率" + ConfigDoll.ZUIXIAOJILV6 + "%"));
				return;
			}
			String[] data = new String[3];
			pc.setDollCount5(pc.getDollCount5() - 1);
			pc.setDollrun6(pc.getDollrun6() - ConfigDoll.ZENGJIAJILV6);	
			data[0] = String.valueOf(ConfigDoll.CONSUME6 + pc.getDollrun6());
			data[1] = String.valueOf(ConfigDoll.SHULIANG6 + pc.getDollCount5());
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind6",data));
		}
///////////////////////////////////////////////////////////////////////////////////////////
		boolean enough = false;
		ArrayList<L1ItemInstance> consumedItems = new ArrayList<>();
		int chance = 0;
		int needcount = 0;
		int[] olddolls = null;
		int newdoll = 0;
		final String[] data = new String[3];

		switch (cmd) {
			case "A":
				chance = ConfigDoll.CONSUME2 + pc.getDollrun2();
				needcount = ConfigDoll.SHULIANG2 + pc.getDollCount();
				olddolls = doll1;
				newdoll = doll2[_random.nextInt(doll2.length)];
				break;
			case "B":
				chance = ConfigDoll.CONSUME3 + pc.getDollrun3();
				needcount = ConfigDoll.SHULIANG3 + pc.getDollCount2();
				olddolls = doll2;
				newdoll = doll3[_random.nextInt(doll3.length)];
				break;
			case "C":
				chance = ConfigDoll.CONSUME4 + pc.getDollrun4();
				needcount = ConfigDoll.SHULIANG4 + pc.getDollCount3();
				olddolls = doll3;
				newdoll = doll4[_random.nextInt(doll4.length)];
				break;
			case "D":
				chance = ConfigDoll.CONSUME5 + pc.getDollrun5();
				needcount = ConfigDoll.SHULIANG5 + pc.getDollCount4();
				olddolls = doll4;
				newdoll = doll5[_random.nextInt(doll5.length)];
				break;
			case "E":
				chance = ConfigDoll.CONSUME6 + pc.getDollrun6();
				needcount = ConfigDoll.SHULIANG6 + pc.getDollCount5();
				olddolls = doll5;
				newdoll = doll6[_random.nextInt(doll6.length)];
				break;
		}

		// 確保沒召喚娃娃中
		for (Object babyObject : pc.getPetList().values().toArray()) {
			if (babyObject instanceof L1DollInstance) {
				pc.sendPackets(new S_SystemMessage("請先收回娃娃"));
				return;
			}
		}

		if (ConfigRate.dolllength) {
			// ✅ 開啟 dolllength 模式：允許任意重複娃娃
			for (L1ItemInstance item : pc.getInventory().getItems()) {
				if (item == null)
					continue;

				int itemId = item.getItemId();
				for (int validId : olddolls) {
					if (itemId == validId) {
						long count = item.getCount();
						for (long i = 0; i < count; i++) {
							consumedItems.add(item);
							if (consumedItems.size() == needcount)
								break;
						}
					}
					if (consumedItems.size() == needcount)
						break;
				}
				if (consumedItems.size() == needcount)
					break;
			}
		} else {
			// ❌ 原始邏輯：每張卡片必須是不同 itemId
			ArrayList<Integer> usedIds = new ArrayList<>();
			for (int i = 0; i < olddolls.length; i++) {
				L1ItemInstance[] dolls = pc.getInventory().findItemsId(olddolls[i]);
				if (dolls != null && dolls.length > 0 && !usedIds.contains(olddolls[i])) {
					consumedItems.add(dolls[0]);
					usedIds.add(olddolls[i]);
				}
				if (consumedItems.size() == needcount)
					break;
			}
		}

		StringBuilder debug = new StringBuilder();
		for (L1ItemInstance item : consumedItems) {
			debug.append(item.getItemId()).append(",");
		}
		//pc.sendPackets(new S_SystemMessage("實際收集到的卡片：" + debug.toString()));

		if (consumedItems.size() == needcount) {
			enough = true;

			// 扣除道具
			for (L1ItemInstance item : consumedItems) {
				pc.getInventory().removeItem(item, 1);
			}

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
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc09", data));
				Thread.sleep(70);
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhc10", data));
				Thread.sleep(100);

				// 成功機率
				if (_random.nextInt(100) < chance) {
					L1ItemInstance item = ItemTable.get().createItem(newdoll);
					item.setIdentified(true);
					pc.getInventory().storeItem(item);

					// 廣播與視覺特效
					L1DollHeCheng card = DollHeChengTable.getInstance().getTemplate(item.getItemId());
					StringBuilder msg = new StringBuilder();
					if (card != null) {
						msg.append(card.getGfxid()).append(",");
						if (card.getNot() != 0) {
							World.get().broadcastPacketToAll(new S_BlueMessage(166,
								"\\f=恭喜玩家\\fN【" + pc.getName() + "】\\f=合成了娃娃\\fN【" + item.getLogName() + "】"));
						}
					}
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhccg", msg.toString().split(",")));
					pc.sendPackets(new S_SystemMessage("恭喜你，合成了" + item.getLogName()));

				} else {
					// 合成失敗
					pc.sendPackets(new S_SystemMessage("合成娃娃失敗了。"));

					// 補償其中一張
					L1ItemInstance failItem = ItemTable.get().createItem(
							consumedItems.get(_random.nextInt(consumedItems.size())).getItemId());
					failItem.setIdentified(true);
					pc.getInventory().storeItem(failItem);

					L1DollHeCheng card = DollHeChengTable.getInstance().getTemplate(failItem.getItemId());
					StringBuilder msg = new StringBuilder();
					if (card != null) {
						msg.append(card.getGfxid()).append(",");
					}
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhcsb", msg.toString().split(",")));
					pc.sendPackets(new S_SystemMessage("很遺憾，合成失敗，返還" + failItem.getLogName()));
				}

				Thread.sleep(2000);
				pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dollcombind1"));

				// 重設加乘參數
				pc.setDollCount(0);
				pc.setDollCount2(0);
				pc.setDollCount3(0);
				pc.setDollCount4(0);
				pc.setDollCount5(0);
				pc.setDollrun2(0);
				pc.setDollrun3(0);
				pc.setDollrun4(0);
				pc.setDollrun5(0);
				pc.setDollrun6(0);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			pc.sendPackets(new S_SystemMessage("你的娃娃卡不足【" + needcount + "】張"));
		}
	}
}
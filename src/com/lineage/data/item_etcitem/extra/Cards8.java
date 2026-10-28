package com.lineage.data.item_etcitem.extra;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;

import com.add.system.ACard;
import com.add.system.ACardTable;
import com.add.system.CardPolySet;
import com.add.system.CardSetTable;


public class Cards8 extends ItemExecutor {
	
	public static ItemExecutor get() {
		return new Cards8();
	}
	
	private int _quest;
	
	private int _gfx;

	private int _itemid;
	
	private static Cards8 _instance;

	public static Cards8 getInstance() {
		if (_instance == null) {
			_instance = new Cards8();
		}
		return _instance;
	}
	
	public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
		
		if (item == null) {
			return;
		}

		if (pc == null) {
			return;
		}
		
		if (pc.isPrivateShop()) {
			pc.sendPackets(new S_SystemMessage("擺攤狀態下 無法使用"));
			return;
		}
		
		if (pc.isFishing()) {
			pc.sendPackets(new S_SystemMessage("釣魚狀態下 無法使用"));
			return;
		}
		boolean ok = false;
		for (int i=0 ; i<=ACardTable.get().ACardSize();i++) {
			final ACard card = ACardTable.get().getCard(i);
			if (card != null) {
				if (card.getQuestId() == _quest) {
					if (pc.getQuest().get_step(card.getQuestId()) == 0) {
						pc.getQuest().set_step(card.getQuestId(), 1);
						pc.getInventory().removeItem(item, 1L);
						pc.sendPackets(
								new S_SystemMessage("你已成功登錄此卡片 請重新登入獲得能力加成 並享有此變身能力"));
						pc.sendPacketsAll(new S_SkillSound(pc.getId(), _gfx));
						ok = true;
					} else {
						pc.sendPackets(
								new S_SystemMessage("無法重複登錄卡片"));
					}
					break;
				}
			}
		}
		final StringBuilder stringBuilder = new StringBuilder();
		int size = ACardTable.get().ACardSize();
		for (int i = 0 ; i <= size;i++) {
			final ACard card1 = ACardTable.get().getCard(i);
			if (card1 != null) {
				if (pc.getQuest().get_step(card1.getQuestId()) != 0) {														
					stringBuilder.append(String.valueOf(card1.getAddcgfxid()) + ",");							
				} else {
					stringBuilder.append(String.valueOf(card1.getAddhgfxid()) + ",");							
				}
			}
		}			
		final String[] msg = stringBuilder.toString().split(",");
		if (ok) {
			for (int i=0 ; i<=CardSetTable.get().CardCardSize();i++) {
				final CardPolySet cards = CardSetTable.get().getCard(i);
				if (cards != null) {
					int k = 0;
					for (int j=0; j<cards.getNeedQuest().length; j++) {
						if (pc.getQuest().get_step(cards.getNeedQuest()[j]) != 0) {
							k++;
						}
					}
					
					if (k == cards.getNeedQuest().length) {
						pc.getQuest().set_step(cards.getQuestId(), 1);
						pc.sendPackets(
								new S_SystemMessage("恭喜你啟動此變身卡套裝卡能力"));
					}
				}
			}
		}
		pc.getInventory().storeItem(_itemid, 1);
	}
	
	public void set_set(String[] set) {
		try {
			_quest = Integer.parseInt(set[1]);
		} catch (Exception localException) {
			
		}
		
		try {
			_gfx = Integer.parseInt(set[2]);
		} catch (Exception localException) {
			
		}
		
		try {
			_itemid = Integer.parseInt(set[3]);
		} catch (Exception localException) {
			
		}
	}
}
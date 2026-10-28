package com.lineage.data.npc.shop;

import com.lineage.config.ConfigOther;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.WriteLogTxt;
import com.lineage.server.model.L1DuBo;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_HowManyMake;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;



public class Npc_DuBo extends NpcExecutor {

	private Npc_DuBo() {

	}

	@Override
	public int type() {
		return 3;
	}

	public static NpcExecutor get() {
		return new Npc_DuBo();
	}

	@Override
	public void talk(final L1PcInstance pc, final L1NpcInstance npc) {
		pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dugui", L1DuBo
				.get(npc).MsgDate(pc)));
	}

	@Override
	public void action(final L1PcInstance pc, final L1NpcInstance npc,
			final String cmd, final long amount) {
		final L1ItemInstance itemExist = pc.getInventory().findItemId(ConfigOther.Dubo );
		if (itemExist == null) {
			pc.sendPackets(new S_SystemMessage("\\F2連金幣都沒有..."));
			return;
		}
		long ybcount = 0;
		ybcount = itemExist.getCount();
		if (ybcount > 100000000) {
			ybcount = 100000000;
		}
		if (cmd.equalsIgnoreCase("a")) {
//			if (!Config.DuBo) {
//				pc.sendPackets(new S_SystemMessage("\\F2賭博未開啟."));
//				return;
//			}
			if (L1DuBo.get(npc).getStartOn() == 0
					|| L1DuBo.get(npc).getStartOn() == 1) {
				pc.sendPackets(new S_HowManyMake(npc.getId(), (int) ybcount, "a_a"));
			} else {
				pc.sendPackets(new S_SystemMessage("\\F2搶莊時間已過."));
			}
} else if (cmd.equalsIgnoreCase("b")) {
//          if (!Config.DuBo) {
//              pc.sendPackets(new S_SystemMessage("\\F2賭博未開啟."));
//              return;
//          }
            if (L1DuBo.get(npc).getStartOn() == 2) {
                pc.sendPackets(new S_HowManyMake(npc.getId(), (int) ybcount, "b_b"));
            } else if (L1DuBo.get(npc).getStartOn() == 0 || L1DuBo.get(npc).getStartOn() == 1) {
                pc.sendPackets(new S_SystemMessage("\\F2搶莊過程中，請稍後下注。"));
            } else {
                pc.sendPackets(new S_SystemMessage("\\F2壓住時間已過."));
            }
        } else if (cmd.equalsIgnoreCase("c")) {
//          if (!Config.DuBo) {
//              pc.sendPackets(new S_SystemMessage("\\F2賭博未開啟."));
//              return;
//          }
            if (L1DuBo.get(npc).getStartOn() == 2) {
                pc.sendPackets(new S_HowManyMake(npc.getId(), (int) ybcount, "c_c"));
            } else if (L1DuBo.get(npc).getStartOn() == 0 || L1DuBo.get(npc).getStartOn() == 1) {
                pc.sendPackets(new S_SystemMessage("\\F2搶莊過程中，請稍後下注。"));
            } else {
                pc.sendPackets(new S_SystemMessage("\\F2壓住時間已過."));
            }
		} else if (cmd.equalsIgnoreCase("a_a")) {
//			if (!Config.DuBo) {
//				return;
//			}
			if (L1DuBo.get(npc).getStartOn() == 0
					|| L1DuBo.get(npc).getStartOn() == 1) {
				L1DuBo.get(npc).setDuBoPc(pc, (int) amount, 0);
			} else {
				pc.sendPackets(new S_SystemMessage("\\F2搶莊時間已過."));
			}
		} else if (cmd.equalsIgnoreCase("b_b")) {
//			if (!Config.DuBo) {
//				return;
//			}
			if (L1DuBo.get(npc).getStartOn() == 2) {
				L1DuBo.get(npc).setDuBoPc(pc, (int) amount, 1);
			} else {
				pc.sendPackets(new S_SystemMessage("\\F2壓住時間已過."));
			}
		} else if (cmd.equalsIgnoreCase("c_c")) {
//			if (!Config.DuBo) {
//				return;
//			}
			if (L1DuBo.get(npc).getStartOn() == 2) {
				L1DuBo.get(npc).setDuBoPc(pc, (int) amount, 2);
			} else {
				pc.sendPackets(new S_SystemMessage("\\F2壓住時間已過."));
			}
		} else if (cmd.equalsIgnoreCase("online")) {
			final int onlecount = L1DuBo.get(npc).getOnlineCount(pc);
			if (onlecount > 0) {
				pc.getInventory().storeItem(ConfigOther.Dubo , onlecount);
				pc.sendPackets(new S_ServerMessage(143, "賭博大師", "金幣("
						+ onlecount + ")"));
				WriteLogTxt.Recording("賭博記錄", "玩家:" + pc.getName()
						+ "領取掉線存儲金幣(" + onlecount + ")");
			} else {
				pc.sendPackets(new S_SystemMessage("你沒有掉線存儲 或者你已經領取過了."));
			}
		}
	}
}

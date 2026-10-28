package com.add.system;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class CardBookCmd
{
    private static final Log _log;
    private static CardBookCmd _instance;
    
    static {
        _log = LogFactory.getLog(CardBookCmd.class);
    }
    
    public static CardBookCmd get() {
        if (CardBookCmd._instance == null) {
            CardBookCmd._instance = new CardBookCmd();
        }
        return CardBookCmd._instance;
    }
    
    public boolean Cmd(L1PcInstance pc, String cmd) {
        try {
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
            boolean ok = false;
            switch (cmd) {
            case "C_1":	
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_02", msg));
				ok = true;
				pc.setCarId(-pc.getCardId());
				break;
			case "C_2":
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_03", msg));
				ok = true;
				pc.setCarId(-pc.getCardId());
				break;
			case "C_3":
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_04", msg));
				ok = true;
				pc.setCarId(-pc.getCardId());
				break;
			case "C_4":
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_05", msg));
				ok = true;
				pc.setCarId(-pc.getCardId());
				break;
			case "C_5":
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_06", msg));
				ok = true;
				pc.setCarId(-pc.getCardId());
				break;
			case "C_6":
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_07", msg));
				ok = true;
				pc.setCarId(-pc.getCardId());
				break;
			case "C_7":
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_08", msg));
				ok = true;
				pc.setCarId(-pc.getCardId());
				break;
			case "C_8":
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_09", msg));
				ok = true;
				pc.setCarId(-pc.getCardId());
				break;
			case "cardset":
				CardSet(pc);
				ok = true;
				break;
			case "cardset2":
				CardAllSet(pc);
				ok = true;
				break;
            case "polycard":
                        ACard card = ACardTable.get().getCard(pc.getCardId());
                        if (card != null) {
                            if (pc.getQuest().get_step(card.getQuestId()) != 0) {
                                if (card.getPolyId() != 0) {
                                    if (card.getPolyItemId() != 0) {
                                        if (pc.getInventory().checkItem(card.getPolyItemId(), (long)card.getPolyItemCount())) {
                                            if (pc.getTempCharGfx() == pc.getcardpoly()) {
                                                pc.sendPackets(new S_SystemMessage("無法重複變身,請先取消變身"));
                                                return false;
                                            } else {
                                                L1PolyMorph.doPoly(pc, card.getPolyId(), card.getPolyTime(), 1);
                                                pc.getInventory().consumeItem(card.getPolyItemId(), (long)card.getPolyItemCount());
                                                pc.setcardpoly(card.getPolyId());
                                                //pc.sendPackets(new S_SystemMessage("222"));
                                            }
                                        } else {
                                            pc.sendPackets(new S_SystemMessage("變身需求道具不足"));
                                        }
                                    } else {
                                    	pc.getInventory().consumeItem(40088, 1);
                                        L1PolyMorph.doPoly(pc, card.getPolyId(), card.getPolyTime(), 1);
                                        pc.getInventory().consumeItem(card.getPolyItemId(), (long)card.getPolyItemCount());
                                        pc.setcardspoly(card.getPolyId());
                                        pc.sendPackets(new S_SystemMessage("自動循環變身"));
                                    }
                                } else {
                                    pc.sendPackets(new S_SystemMessage("無法變身"));
                                }
                            } 
                            else {
                                pc.sendPackets(new S_SystemMessage("尚未登入卡冊"));
                            }
                        }
                        ok = true;
                        break;
                    }                         
            if (ok) {
                return true;
            }
        }
        catch (Exception e) {
            CardBookCmd._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private void CardAllSet(L1PcInstance pc) {
        try {
            int str = 0;
            int dex = 0;
            int con = 0;
            int Int = 0;
            int wis = 0;
            int cha = 0;
            int ac = 0;
            int hp = 0;
            int mp = 0;
            int hpr = 0;
            int mpr = 0;
            int dmg = 0;
            int bdmg = 0;
            int hit = 0;
            int bhit = 0;
            int dr = 0;
            int mdr = 0;
            int sp = 0;
            int mhit = 0;
            int mr = 0;
            int f = 0;
            int wind = 0;
            int w = 0;
            int e = 0;
            for (int i = 0; i <= ACardTable.get().ACardSize(); ++i) {
                ACard card = ACardTable.get().getCard(i);
                if (card != null && pc.getQuest().get_step(card.getQuestId()) != 0) {
                    str += card.getAddStr();
                    dex += card.getAddDex();
                    con += card.getAddCon();
                    Int += card.getAddInt();
                    wis += card.getAddWis();
                    cha += card.getAddCha();
                    ac += card.getAddAc();
                    hp += card.getAddHp();
                    mp += card.getAddMp();
                    hpr += card.getAddHpr();
                    mpr += card.getAddMpr();
                    dmg += card.getAddDmg();
                    bdmg += card.getAddBowDmg();
                    hit += card.getAddHit();
                    bhit += card.getAddBowHit();
                    dr += card.getAddDmgR();
                    mdr += card.getAddMagicDmgR();
                    sp += card.getAddSp();
                    mhit += card.getAddMagicHit();
                    mr += card.getAddMr();
                    f += card.getAddFire();
                    wind += card.getAddWind();
                    e += card.getAddEarth();
                    w += card.getAddWater();
                }
            }
            for (int i = 0; i <= CardSetTable.get().CardCardSize(); ++i) {
                CardPolySet cards = CardSetTable.get().getCard(i);
                if (cards != null && pc.getQuest().get_step(cards.getQuestId()) != 0) {
                    str += cards.getAddStr();
                    dex += cards.getAddDex();
                    con += cards.getAddCon();
                    Int += cards.getAddInt();
                    wis += cards.getAddWis();
                    cha += cards.getAddCha();
                    ac += cards.getAddAc();
                    hp += cards.getAddHp();
                    mp += cards.getAddMp();
                    hpr += cards.getAddHpr();
                    mpr += cards.getAddMpr();
                    dmg += cards.getAddDmg();
                    bdmg += cards.getAddBowDmg();
                    hit += cards.getAddHit();
                    bhit += cards.getAddBowHit();
                    dr += cards.getAddDmgR();
                    mdr += cards.getAddMagicDmgR();
                    sp += cards.getAddSp();
                    mhit += cards.getAddMagicHit();
                    mr += cards.getAddMr();
                    f += cards.getAddFire();
                    wind += cards.getAddWind();
                    e += cards.getAddEarth();
                    w += cards.getAddWater();
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("力量 +" + str + ",");
            stringBuilder.append("敏捷 +" + dex + ",");
            stringBuilder.append("體質 +" + con + ",");
            stringBuilder.append("智力 +" + Int + ",");
            stringBuilder.append("精神 +" + wis + ",");
            stringBuilder.append("魅力 +" + cha + ",");
            stringBuilder.append("防禦提升 " + ac + ",");
            stringBuilder.append("HP +" + hp + ",");
            stringBuilder.append("MP +" + mp + ",");
            stringBuilder.append("血量回復 +" + hpr + ",");
            stringBuilder.append("魔力回復 +" + mpr + ",");
            stringBuilder.append("近距離傷害 +" + dmg + ",");
            stringBuilder.append("遠距離傷害 +" + bdmg + ",");
            stringBuilder.append("近距離命中 +" + hit + ",");
            stringBuilder.append("遠距離命中 +" + bhit + ",");
            stringBuilder.append("物理傷害減免 +" + dr + ",");
            stringBuilder.append("魔法傷害減免 +" + mdr + ",");
            stringBuilder.append("魔攻 +" + sp + ",");
            stringBuilder.append("魔法命中 +" + mhit + ",");
            stringBuilder.append("魔法防禦 +" + mr + ",");
            stringBuilder.append("火屬性防禦 +" + f + ",");
            stringBuilder.append("風屬性防禦 +" + wind + ",");
            stringBuilder.append("地屬性防禦 +" + e + ",");
            stringBuilder.append("水屬性防禦 +" + w + ",");
            String[] clientStrAry = stringBuilder.toString().split(",");
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_11", clientStrAry));
        }
        catch (Exception e2) {
            CardBookCmd._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    private void CardSet(L1PcInstance pc) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i <= CardSetTable.get().CardCardSize(); ++i) {
                CardPolySet cards = CardSetTable.get().getCard(i);
                if (cards != null) {
                    stringBuilder.append(cards.getMsg1() + ",");
                    int k = 0;
                    for (int j = 0; j < cards.getNeedQuest().length; ++j) {
                        if (pc.getQuest().get_step(cards.getNeedQuest()[j]) != 0) {
                            stringBuilder.append(cards.getNeedName()[j] + "(登入),");
                            ++k;
                        }
                        else {
                            stringBuilder.append(cards.getNeedName()[j] + "(未登),");
                        }
                    }
                    if (k == cards.getNeedQuest().length) {
                        if (cards.getAddStr() != 0) {
                            stringBuilder.append("力量 +" + cards.getAddStr() + ",");
                        }
                        if (cards.getAddDex() != 0) {
                            stringBuilder.append("敏捷 +" + cards.getAddDex() + ",");
                        }
                        if (cards.getAddCon() != 0) {
                            stringBuilder.append("體質 +" + cards.getAddCon() + ",");
                        }
                        if (cards.getAddInt() != 0) {
                            stringBuilder.append("智力 +" + cards.getAddInt() + ",");
                        }
                        if (cards.getAddWis() != 0) {
                            stringBuilder.append("精神 +" + cards.getAddWis() + ",");
                        }
                        if (cards.getAddCha() != 0) {
                            stringBuilder.append("魅力 +" + cards.getAddCha() + ",");
                        }
                        if (cards.getAddAc() != 0) {
                            stringBuilder.append("防禦提升 " + cards.getAddAc() + ",");
                        }
                        if (cards.getAddHp() != 0) {
                            stringBuilder.append("HP +" + cards.getAddHp() + ",");
                        }
                        if (cards.getAddMp() != 0) {
                            stringBuilder.append("MP +" + cards.getAddMp() + ",");
                        }
                        if (cards.getAddHpr() != 0) {
                            stringBuilder.append("血量回復 +" + cards.getAddHpr() + ",");
                        }
                        if (cards.getAddMpr() != 0) {
                            stringBuilder.append("魔力回復 +" + cards.getAddMpr() + ",");
                        }
                        if (cards.getAddDmg() != 0) {
                            stringBuilder.append("近距離傷害 +" + cards.getAddDmg() + ",");
                        }
                        if (cards.getAddBowDmg() != 0) {
                            stringBuilder.append("遠距離傷害 +" + cards.getAddBowDmg() + ",");
                        }
                        if (cards.getAddHit() != 0) {
                            stringBuilder.append("近距離命中 +" + cards.getAddHit() + ",");
                        }
                        if (cards.getAddBowHit() != 0) {
                            stringBuilder.append("遠距離命中 +" + cards.getAddBowHit() + ",");
                        }
                        if (cards.getAddDmgR() != 0) {
                            stringBuilder.append("物理傷害減免 +" + cards.getAddDmgR() + ",");
                        }
                        if (cards.getAddMagicDmgR() != 0) {
                            stringBuilder.append("魔法傷害減免 +" + cards.getAddMagicDmgR() + ",");
                        }
                        if (cards.getAddSp() != 0) {
                            stringBuilder.append("魔攻 +" + cards.getAddSp() + ",");
                        }
                        if (cards.getAddMagicHit() != 0) {
                            stringBuilder.append("魔法命中 +" + cards.getAddMagicHit() + ",");
                        }
                        if (cards.getAddMr() != 0) {
                            stringBuilder.append("魔法防禦 +" + cards.getAddMr() + ",");
                        }
                        if (cards.getAddFire() != 0) {
                            stringBuilder.append("火屬性防禦 +" + cards.getAddFire() + ",");
                        }
                        if (cards.getAddWind() != 0) {
                            stringBuilder.append("風屬性防禦 +" + cards.getAddWind() + ",");
                        }
                        if (cards.getAddEarth() != 0) {
                            stringBuilder.append("地屬性防禦 +" + cards.getAddEarth() + ",");
                        }
                        if (cards.getAddWater() != 0) {
                            stringBuilder.append("水屬性防禦 +" + cards.getAddWater() + ",");
                        }
                        stringBuilder.append("<以上為此套卡能力加成>,");
                    }
                }
            }
            String[] clientStrAry = stringBuilder.toString().split(",");
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_10", clientStrAry));
        }
        catch (Exception e) {
            CardBookCmd._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean PolyCmd(L1PcInstance pc, String cmd) {
        try {
            boolean ok = false;
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            while (i <= ACardTable.get().ACardSize()) {
                ACard card = ACardTable.get().getCard(i);
                if (card != null && cmd.equals(card.getCmd())) {
                    pc.setCarId(i);
                    stringBuilder.append(card.getMsg2() + ",");
                    stringBuilder.append(String.valueOf(card.getAddStr()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddDex()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddCon()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddInt()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddWis()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddCha()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddAc()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddHp()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddMp()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddHpr()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddMpr()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddDmg()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddBowDmg()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddHit()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddBowHit()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddDmgR()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddMagicDmgR()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddSp()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddMagicHit()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddMr()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddFire()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddWind()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddEarth()) + ",");
                    stringBuilder.append(String.valueOf(card.getAddWater()) + ",");
                    if (pc.getQuest().get_step(card.getQuestId()) != 0) {
                        stringBuilder.append("[已存入],");
                    }
                    else {
                        stringBuilder.append("未偵測[存入],");
                    }
                    String[] clientStrAry = stringBuilder.toString().split(",");
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_0", clientStrAry));
                    ok = true;
                    break;
                }
                else {
                    ++i;
                }
            }
            if (ok) {
                return true;
            }
        }
        catch (Exception e) {
            CardBookCmd._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}

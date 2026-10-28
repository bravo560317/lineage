package com.add.system;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import java.util.List;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1DollInstance;
import java.util.Map;
import org.apache.commons.logging.Log;

public class CardBookCmd_doll
{
    private static final Log _log;
    private Map<Integer, L1DollInstance> _dolls2;
    private Map<Integer, L1DollInstance> _dolls3;
    private static CardBookCmd_doll _instance;
    
    static {
        _log = LogFactory.getLog(CardBookCmd_doll.class);
    }
    
    public CardBookCmd_doll() {
        this._dolls2 = new HashMap();
        this._dolls3 = new HashMap();
    }
    
    public static CardBookCmd_doll get() {
        if (CardBookCmd_doll._instance == null) {
            CardBookCmd_doll._instance = new CardBookCmd_doll();
        }
        return CardBookCmd_doll._instance;
    }
    
    public boolean Cmd(L1PcInstance pc, String cmd) {
        try {
        	final StringBuilder stringBuilder = new StringBuilder();
    			int size = ACardTable_doll.get().ACardSize();
    			for (int i = 0 ; i <= size;i++) {
    				final ACard_doll card1 = ACardTable_doll.get().getCard(i);
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
            case "B_1":	
   				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_02_doll", msg));
   				ok = true;
   				pc.setCarId(-pc.getCardId());
   				break;
   			case "B_2":
   				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_03_doll", msg));
   				ok = true;
   				pc.setCarId(-pc.getCardId());
   				break;
   			case "B_3":
   				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_04_doll", msg));
   				ok = true;
   				pc.setCarId(-pc.getCardId());
   				break;
   			case "B_4":
   				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_05_doll", msg));
   				ok = true;
   				pc.setCarId(-pc.getCardId());
   				break;
   			case "B_5":
   				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_06_doll", msg));
   				ok = true;
   				pc.setCarId(-pc.getCardId());
   				break;
   			case "B_6":
   				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_07_doll", msg));
   				ok = true;
   				pc.setCarId(-pc.getCardId());
   				break;
   			case "B_7":
   				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_08_doll", msg));
   				ok = true;
   				pc.setCarId(-pc.getCardId());
   				break;
                case "cardset2_doll": {             
                        this.CardAllSet(pc);
                        ok = true;
                        break;
                 }  
                case "cardset_doll": {
                        this.CardSet(pc);
                        ok = true;
                        break;
                    }
                }           
            if (ok) {
                return true;
            }
        }
        catch (Exception e) {
            CardBookCmd_doll._log.error(e.getLocalizedMessage(), e);
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
            for (int i = 0; i <= ACardTable_doll.get().ACardSize(); ++i) {
                ACard_doll card = ACardTable_doll.get().getCard(i);
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
            for (int i = 0; i <= CardSetTable_doll.get().CardCardSize(); ++i) {
                CardPolySet_doll cards = CardSetTable_doll.get().getCard(i);
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
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_11_doll", clientStrAry));
        }
        catch (Exception e2) {
            CardBookCmd_doll._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    private void CardSet(L1PcInstance pc) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i <= CardSetTable_doll.get().CardCardSize(); ++i) {
                CardPolySet_doll cards = CardSetTable_doll.get().getCard(i);
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
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_10_doll", clientStrAry));
        }
        catch (Exception e) {
            CardBookCmd_doll._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean PolyCmd(L1PcInstance pc, String cmd) {
        try {
            boolean ok = false;
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            while (i <= ACardTable_doll.get().ACardSize()) {
                ACard_doll card = ACardTable_doll.get().getCard(i);
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
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "card_0_doll", clientStrAry));
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
            CardBookCmd_doll._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    public static void useMagicDoll(L1PcInstance pc, int itemId, int itemObjectId) {
        L1Doll type = DollPowerTable.get().get_type(itemId);
        if (pc.getDoll(itemObjectId) != null) {
            pc.getDoll(itemObjectId).deleteDoll();
            return;
        }
        else if (pc.isInvisble()) {
            return;
        }
        else {
            int castle_id = 1;
            while (castle_id < 8) {
                if (!ConfigOther.WAR_DOLL && ServerWarExecutor.get().isNowWar(castle_id) && pc.getMapId() == 4) {
                    pc.sendPackets(new S_ServerMessage("攻城戰期間無法使用娃娃!!"));
                    return;
                }
                else {
                    ++castle_id;
                }
            }
            if (pc.getdollcount() >= ConfigOther.dollcount && type.get_mode() == 0) {
                pc.sendPackets(new S_SystemMessage("已攜帶:" + pc.getdollcount() + "隻魔法娃娃，不能帶更多了"));
                return;
            }
            else if (pc.getdollcount1() >= ConfigOther.dollcount1 && type.get_mode() == 1) {
                pc.sendPackets(new S_SystemMessage("已攜帶:" + pc.getdollcount1() + "隻補助娃娃，不能帶更多了"));
                return;
            }
            else {
                if (!pc.getDolls().isEmpty()) {
                    if (pc.getdollcount_itemid() == itemId && type.get_mode() == 0) {
                        pc.sendPackets(new S_ServerMessage("\\fY不能攜帶相同的娃娃"));
                        return;
                    }
                    else if (pc.getdollcount1_itemid() == itemId && type.get_mode() == 1) {
                        pc.sendPackets(new S_ServerMessage("\\fY不能攜帶相同的輔助娃娃"));
                        return;
                    }
                }
                if (!ConfigOther.WAR_DOLL && pc.getClan() != null) {
                    boolean inWar = false;
                    if (pc.getClan().getCastleId() != 0) {
                        if (ServerWarExecutor.get().isNowWar(pc.getClan().getCastleId())) {
                            inWar = true;
                        }
                    }
                    else {
                        List<L1War> warList = WorldWar.get().getWarList();
                        for (L1War war : warList) {
                            if (war.checkClanInWar(pc.getClan().getClanName())) {
                                inWar = true;
                                break;
                            }
                        }
                    }
                    if (inWar) {
                        pc.sendPackets(new S_ServerMessage(1531));
                        return;
                    }
                }
                boolean iserror = false;
                if (type != null) {
                    if (type.get_need() != null) {
                        int[] itemids = type.get_need();
                        int[] counts = type.get_counts();
                        for (int i = 0; i < itemids.length; ++i) {
                            if (!pc.getInventory().checkItem(itemids[i], (long)counts[i])) {
                                L1Item temp = ItemTable.get().getTemplate(itemids[i]);
                                pc.sendPackets(new S_ServerMessage(337, temp.getNameId()));
                                iserror = true;
                            }
                        }
                        if (!iserror) {
                            for (int i = 0; i < itemids.length; ++i) {
                                pc.getInventory().consumeItem(itemids[i], (long)counts[i]);
                            }
                        }
                    }
                    if (!iserror) {
                        L1Npc template = NpcTable.get().getTemplate(71082);
                        if (type.get_mode() == 0) {
                            L1DollInstance doll = new L1DollInstance(template, pc, itemObjectId, type, false);
                            pc.add_dollcount(1);
                            pc.add_dollcount_itemid(itemId);
                        }
                        if (type.get_mode() == 1) {
                            L1DollInstance doll = new L1DollInstance(template, pc, itemObjectId, type, true);
                            pc.add_dollcount1(1);
                            pc.add_dollcount1_itemid(itemId);
                        }
                    }
                }
                return;
            }
        }
    }
}

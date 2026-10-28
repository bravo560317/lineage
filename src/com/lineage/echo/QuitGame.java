package com.lineage.echo;

import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1SkinInstance;
import java.util.Map;
import com.lineage.server.templates.L1Account;
import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.data.event.OnlineGiftSet;
import com.lineage.server.datatables.lock.shop_lxReading;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1FollowerInstance;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Trade;
import com.lineage.server.datatables.GetbackTable;
import com.lineage.server.model.Instance.L1IllusoryInstance;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class QuitGame
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(QuitGame.class);
    }
    
    public static void quitGame(L1PcInstance pc) {
        if (pc == null) {
            return;
        }
        else if (pc.getOnlineStatus() == 0) {
            return;
        }
        else {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null && clan.getWarehouseUsingChar() == pc.getId()) {
                clan.setWarehouseUsingChar(0);
            }
            if (!pc.getPetList().isEmpty()) {
                Object[] petList = pc.getPetList().values().toArray();
                if (petList != null) {
                    remove_pet(pc, petList);
                }
            }
            try {
                if (!pc.getDolls().isEmpty()) {
                    Object[] dolls = pc.getDolls().values().toArray();
                    Object[] array;
                    for (int length = (array = dolls).length, i = 0; i < length; ++i) {
                        Object obj = array[i];
                        L1DollInstance doll = (L1DollInstance)obj;
                        if (doll != null) {
                            doll.deleteDoll();
                        }
                    }
                    pc.getDolls().clear();
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                if (pc.getHierarchs() != null) {
                    pc.getHierarchs().deleteHierarch();
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                if (!pc.get_otherList().get_illusoryList().isEmpty()) {
                    Object[] illList = pc.get_otherList().get_illusoryList().values().toArray();
                    Object[] array2;
                    for (int length2 = (array2 = illList).length, j = 0; j < length2; ++j) {
                        Object obj = array2[j];
                        L1IllusoryInstance ill = (L1IllusoryInstance)obj;
                        if (ill != null) {
                            ill.deleteMe();
                        }
                    }
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                pc.get_otherList().clearAll();
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                if (pc.isDead()) {
                    int[] loc = GetbackTable.GetBack_Location(pc, true);
                    pc.setX(loc[0]);
                    pc.setY(loc[1]);
                    pc.setMap((short)loc[2]);
                    pc.setCurrentHp(pc.getLevel());
                    if (pc.get_food() > 40) {
                        pc.set_food(40);
                    }
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                if (pc.getTradeID() != 0) {
                    L1Trade trade = new L1Trade();
                    trade.tradeCancel(pc);
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                if (pc.getFightId() != 0) {
                    pc.setFightId(0);
                    L1PcInstance fightPc = (L1PcInstance)World.get().findObject(pc.getFightId());
                    if (fightPc != null) {
                        fightPc.setFightId(0);
                        fightPc.sendPackets(new S_PacketBox(5, 0, 0));
                    }
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                if (pc.isInParty()) {
                    pc.getParty().leaveMember(pc);
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                if (pc.isInChatParty()) {
                    pc.getChatParty().leaveMember(pc);
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                if (!pc.getFollowerList().isEmpty()) {
                    Object[] followerList = pc.getFollowerList().values().toArray();
                    Object[] array3;
                    for (int length3 = (array3 = followerList).length, k = 0; k < length3; ++k) {
                        Object obj = array3[k];
                        L1FollowerInstance follower = (L1FollowerInstance)obj;
                        follower.setParalyzed(true);
                        follower.spawn(follower.getNpcTemplate().get_npcId(), follower.getX(), follower.getY(), follower.getHeading(), follower.getMapId());
                        follower.deleteMe();
                    }
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            try {
                if (ConfigOther.Quitgameshop) {
                    L1Account acc = AccountReading.get().getAccount(pc.getAccountName());
                    String mac = null;
                    if (acc.get_mac() != null) {
                        mac = acc.get_mac();
                    }
                    if (pc.isPrivateShop() && !shop_lxReading.get().isExist(mac)) {
                        OnlineGiftSet.remove(pc);
                        pc.save();
                        pc.saveInventory();
                        shop_lxReading.get().updatemejs(pc.getId(), pc.getName());
                        pc.sendPackets(new S_Disconnect());
                        return;
                    }
                }
                pc.stopEtcMonitor();
                pc.setOnlineStatus(0);
                pc.save();
                pc.saveInventory();
                pc.logout();
                if (pc.getSkins() != null) {
                    Map<Integer, L1SkinInstance> skinList = pc.getSkins();
                    for (Integer gfxid : skinList.keySet()) {
                        pc.getSkin(gfxid).deleteMe();
                    }
                }
            }
            catch (Exception e) {
                QuitGame._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    private static void remove_pet(L1PcInstance pc, Object[] petList) {
        try {
            for (Object obj : petList) {
                L1NpcInstance petObject = (L1NpcInstance)obj;
                if (petObject != null) {
                    if (petObject instanceof L1PetInstance) {
                        L1PetInstance pet = (L1PetInstance)petObject;
                        pet.collect(true);
                        pc.removePet(pet);
                        pet.deleteMe();
                    }
                    if (petObject instanceof L1SummonInstance) {
                        L1SummonInstance summon = (L1SummonInstance)petObject;
                        if (summon != null) {
                            if (summon.destroyed()) {
                                return;
                            }
                            else {
                                summon.Death(null);
                            }
                        }
                    }
                }
            }
            pc.getPetList().clear();
        }
        catch (Exception e) {
            QuitGame._log.error(e.getLocalizedMessage(), e);
        }
    }
}

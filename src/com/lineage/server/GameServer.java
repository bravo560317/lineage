package com.lineage.server;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.sql.CharacterQuestTable;
import com.lineage.DatabaseFactory;
import java.util.Collection;
import java.util.Iterator;
import com.add.ItemTimeTableadd;
import com.lineage.william.William_Online_Reward;
import com.lineage.data.event.RandomGiftSet;
import com.lineage.server.datatables.lock.CharWeaponTimeReading;
import com.lineage.william.WilliamBuff;
import com.lineage.william.ExcavateTable;
import com.add.L1DayPresentTimer;
import com.lineage.william.ItemIntegration;
import com.lineage.server.datatables.sql.L1MonTable;
import com.lineage.server.datatables.lock.CharMapTimeReading;
import com.lineage.william.ClanOriginal;
import com.lineage.william.NpcTalk10;
import com.lineage.william.GfxIdOrginal;
import com.lineage.william.WilliamItemMessage;
import com.lineage.server.timecontroller.ArmorEffect;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.timecontroller.StartTimer_Skill;
import com.lineage.server.timecontroller.StartTimer_Pet;
import com.lineage.server.timecontroller.StartTimer_Npc;
import com.lineage.server.timecontroller.StartTimer_Pc;
import com.lineage.server.timecontroller.StartTimer_Server;
import com.add.system.ACardTable_doll;
import com.add.system.CardSetTable_doll;
import com.add.system.ACardTable;
import com.add.system.CardSetTable;
import com.lineage.config.ConfigAlt;
import com.add.system.L1FireSmithCrystalTable;
import com.lineage.william.EnchantAccessory;
import com.lineage.william.EnchantOrginal;
import com.add.system.L1BlendTable_1;
import com.add.system.L1BlendTable;
import com.lineage.server.timecontroller.event.ranking.RankingHeroTimer;
import com.lineage.server.timecontroller.event.ranking.RankingHeroTimerlv;
import com.lineage.william.new_npc_talk;
import com.lineage.server.templates.L1MapTile;
import com.lineage.server.datatables.lock.FurnitureSpawnReading;
import com.lineage.server.templates.L1PcOther;
import com.lineage.server.datatables.lock.CharItemsTimeReading;
import com.lineage.server.datatables.lock.PetReading;
import com.lineage.server.datatables.lock.DwarfForElfReading;
import com.lineage.server.datatables.lock.DwarfReading;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1ItemPower;
import com.lineage.server.model.L1AttackList;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.list.BadNamesList;
import com.lineage.server.datatables.lock.CharacterQuestReading;
import com.lineage.config.ConfigItem;
import com.lineage.server.utils.DBClearAllUtil;
import com.lineage.config.ConfigQuest;
import com.lineage.config.Configpoly;
import com.add.NewAutoPractice;
import com.lineage.config.ConfigClan;
import com.lineage.config.ConfigDoll;

import java.util.Calendar;
import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.lock.CharOtherReading3;
import com.lineage.server.datatables.lock.CharOtherReading2;
import com.lineage.server.datatables.lock.CharOtherReading1;
import com.lineage.server.datatables.lock.CharOtherReading;
import com.lineage.server.datatables.lock.CharBookConfigReading;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.datatables.lock.BuddyReading;
import com.lineage.server.datatables.lock.CharacterConfigReading;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.datatables.lock.CharBuffReading;
import com.lineage.server.datatables.lock.BoardReading;
import com.lineage.server.datatables.lock.AuctionBoardReading;
import com.lineage.server.datatables.sql.MailTable;
import com.lineage.server.datatables.lock.TownReading;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.server.datatables.lock.HouseReading;
import com.lineage.server.datatables.lock.SpawnBossReading;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.server.timecontroller.Special;
import com.lineage.data.event.NowTimeSpawn;
import com.eric.StartCheckWarTime;
import com.eric.RandomMobTable;
import com.lineage.data.event.ClanSkillDBSet;
import com.lineage.server.model.L1ClanMatching;
import com.lineage.server.datatables.lock.ClanAllianceReading;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.datatables.lock.DwarfForClanReading;
import com.lineage.server.world.WorldSummons;
import com.lineage.server.world.WorldPet;
import com.lineage.server.world.WorldIllusionist;
import com.lineage.server.world.WorldDragonKnight;
import com.lineage.server.world.WorldDarkelf;
import com.lineage.server.world.WorldWizard;
import com.lineage.server.world.WorldElf;
import com.lineage.server.world.WorldKnight;
import com.lineage.server.world.WorldDe;
import com.lineage.server.world.WorldCrown;
import com.lineage.server.world.World;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.model.map.L1WorldMap;
import com.add.L1SystemMessageTable;
import com.lineage.server.thread.DeAiThreadPool;
import com.lineage.server.thread.NpcAiThreadPool;
import com.lineage.server.thread.PcOtherThreadPool;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.datatables.*;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.datatables.lock.ServerReading;
import com.lineage.echo.PacketHandler;
import com.lineage.server.utils.DBClearitemb;
import com.lineage.server.utils.PerformanceTimer;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.timecontroller.server.RankingEffectTimer;

public class GameServer
{
    private static final Log _log;
    private static GameServer _instance;
    
    static {
        _log = LogFactory.getLog(GameServer.class);
    }
    
    public static GameServer getInstance() {
        if (GameServer._instance == null) {
            GameServer._instance = new GameServer();
        }
        return GameServer._instance;
    }
    
    public void initialize() throws Exception {
        PerformanceTimer timer = new PerformanceTimer();
        try {
            DBClearitemb.start();
            PacketHandler.load();
            ServerReading.get().load();
            IdFactory.get().load();
            CharObjidTable.get().load();
            AccountReading.get().load();
            GeneralThreadPool.get();
            PcOtherThreadPool.get();
            NpcAiThreadPool.get();
            DeAiThreadPool.get();
            L1SystemMessageTable.get().loadSystemMessage();
            ExpTable.get().load();
            SprTable.get().load();
            MapsTable.get().load();
            MapLevelTable.get().load();
            ItemTimeTable.get().load();
            L1WorldMap.get().load();
            L1GameTimeClock.init();
            NpcTable.get().load();
            NpcScoreTable.get().load();
            CharacterTable.loadAllCharName();
            CharacterTable.clearOnlineStatus();
            CharacterTable.clearSpeedError();
            World.get();
            WorldCrown.get();
            WorldDe.get();
            WorldKnight.get();
            WorldElf.get();
            WorldWizard.get();
            WorldDarkelf.get();
            WorldDragonKnight.get();
            WorldIllusionist.get();
            WorldPet.get();
            WorldSummons.get();
            TrapTable.get().load();
            TrapsSpawn.get().load();
            ItemTable.get().load();
            DropTable.get().load();
            DropMapTable.get().load();
            DropItemTable.get().load();
            DropItemEnchantTable.get().load();
            SkillsTable.get().load();
            SkillsItemTable.get().load();
            MobGroupTable.get().load();
            NPCTalkDataTable.get().load();
            NpcActionTable.load();
            SpawnTable.get().load();
            PolyTable.get().load();
            ShopTable.get().load();
            ShopCnTable.get().load();
            DungeonTable.get().load();
            DungeonRTable.get().load();
            NpcSpawnTable.get().load();
            DwarfForClanReading.get().load();
            ClanReading.get().load();
            ClanEmblemReading.get().load();
            ClanAllianceReading.get().load();
            L1ClanMatching.getInstance().loadClanMatching();
            if (ClanSkillDBSet.START) {
                ClanReading.get().load();
            }
            RandomMobTable.getInstance().startRandomMob();
            StartCheckWarTime.getInstance();
            if (NowTimeSpawn.START) {
                com.lineage.william.NowTimeSpawn.get();
            }
            Special.getStart();
            CastleReading.get().load();
            L1CastleLocation.setCastleTaxRate();
            GetBackRestartTable.get().load();
            DoorSpawnTable.get().load();
            WeaponSkillTable.get().load();
            WeaponSkillPowerTable.get().load();
            GetbackTable.loadGetBack();
            PetTypeTable.load();
            PetItemTable.get().load();
            ItemBoxTable.get().load();
            ResolventTable.get().load();
            NpcTeleportTable.get().load();
            NpcChatTable.get().load();
            ArmorSetTable.get().load();
            ItemTeleportTable.get().load();
            ItemPowerUpdateTable.get().load();
            CommandsTable.get().load();
            BeginnerTable.get().load();
            ItemRestrictionsTable.get().load();
            ItemdropTable.get().load();
            ItemdropdeadTable.get().load();
            Itemdeaddrop.get().load();
            SpawnBossReading.get().load();
            Skill_type.get();
            Skill_type.load();
            Skill_type1.get().load();
            HouseReading.get().load();
            IpReading.get().load();
            TownReading.get().load();
            MailTable.get().load();
            AuctionBoardReading.get().load();
            BoardReading.get().load();
            CharBuffReading.get().load();
            CharSkillReading.get().load();
            CharacterConfigReading.get().load();
            BuddyReading.get().load();
            CharBookReading.get().load();
            CharBookConfigReading.get().load();
            CharOtherReading.get().load();
            CharOtherReading1.get().load();
            CharOtherReading2.get().load();
            CharOtherReading3.get().load();
            polyHeChengTable.getInstance().load();
            DollHeChengTable.getInstance().load();
            QuizSetTable.getInstance().updateAllPcQuizSet();
            if (ConfigOther.monsec == 0) {
                this.cleanMissionStatus();
                GameServer._log.info(">>>>>>>>(每次重啟)角色任務媽祖欄位清理完畢。");
            }
            else if (ConfigOther.monsec > 0 && Calendar.getInstance().get(11) == ConfigOther.monsec) {
                this.cleanMissionStatus();
                GameServer._log.info(">>>>>>>>(時間重啟)角色任務媽祖欄位清理完畢。");
            }
            if (ConfigClan.clanskill) {
                this.clanaddskill();
                this.clanaddskill1();
                this.clanaddskill2();
                this.clanaddskill3();
                GameServer._log.info(">>>>>>>>(每次重啟)角色任務血盟貢獻清理完畢。");
            }
            else if (Calendar.getInstance().get(11) == ConfigClan.clanresttime) {
                this.clanaddskill();
                this.clanaddskill1();
                this.clanaddskill2();
                this.clanaddskill3();
                GameServer._log.info(">>>>>>>>(時間重啟)角色任務血盟貢獻清理完畢。");
            }
            if (ConfigOther.restday == 0) {
                this.day();
                GameServer._log.info(">>>>>>>>(每次重啟)角色每日簽到欄位清理完畢。");
            }
            else if (ConfigOther.restday > 0 && Calendar.getInstance().get(11) == ConfigOther.restday) {
                this.day();
                GameServer._log.info(">>>>>>>>(時間重啟)角色每日簽到欄位清理完畢。");
            }
            if (ConfigOther.shopitemrest == 0) {
                Iterator<Integer> iterator = ShopTable._DailyItem.keySet().iterator();
                while (iterator.hasNext()) {
                    int questid = (Integer)iterator.next();
                    deleteData(questid);
                }
                Iterator<Integer> iterator2 = ShopCnTable._DailyCnItem.keySet().iterator();
                while (iterator2.hasNext()) {
                    int questid = (Integer)iterator2.next();
                    deleteData(questid);
                }
            }
            else if (ConfigOther.shopitemrest > 0 && Calendar.getInstance().get(11) == ConfigOther.shopitemrest) {
                Iterator<Integer> iterator3 = ShopTable._DailyItem.keySet().iterator();
                while (iterator3.hasNext()) {
                    int questid = (Integer)iterator3.next();
                    deleteData(questid);
                }
                Iterator<Integer> iterator4 = ShopCnTable._DailyCnItem.keySet().iterator();
                while (iterator4.hasNext()) {
                    int questid = (Integer)iterator4.next();
                    deleteData(questid);
                }
            }
            NewAutoPractice.get().load();
            NewAutoPractice.get().load2();
            NewAutoPractice.get().load3();
            if (ConfigQuest.Time > 0 && Calendar.getInstance().get(11) == ConfigQuest.Time) {
                this.chcekQuest();
            }
            else if (ConfigQuest.Time == 0) {
                this.chcekQuest();
            }
            if (ConfigQuest.QuestMap_Time > 0 && Calendar.getInstance().get(11) == ConfigQuest.QuestMap_Time) {
                DBClearAllUtil.DBClearQuesemap.start();
                this.map_chcekmap_Quest();
            }
            else if (ConfigQuest.QuestMap_Time == 0) {
                DBClearAllUtil.DBClearQuesemap.start();
                this.map_chcekmap_Quest();
            }
            if (ConfigItem.bd_Time > 0 && Calendar.getInstance().get(11) == ConfigItem.bd_Time) {
                DBClearAllUtil.DBClearQuesemap.start();
                this.chcekitemid();
            }
            else if (ConfigItem.bd_Time == 0) {
                this.chcekitemid();
            }
            CharacterQuestReading.get().load();
            BadNamesList.get().load();
            SceneryTable.get().load();
            L1SkillMode.get().load();
            L1AttackList.load();
            L1ItemPower.load();
            L1PcInstance.load();
            CharItemsReading.get().load();
            DwarfReading.get().load();
            DwarfForElfReading.get().load();
            DollPowerTable.get().load();
            PetReading.get().load();
            CharItemsTimeReading.get().load();
            L1PcOther.load();
            EventTable.get().load();
            if (EventTable.get().size() > 0) {
                EventSpawnTable.get().load();
            }
            QuestMapTable.get().load();
            FurnitureSpawnReading.get().load();
            ItemMsgTable.get().load();
            WeaponPowerTable.get().load();
            FishingTable.get().load();
            ExtraMeteAbilityTable.getInstance().load();
            Acc_use_Item.get();
            Char_use_Item.get();
            ExtraAttrWeaponTable.getInstance().load();
            Collection<L1MapTile> mapTile = (Collection)MapTileTable.get().getList();
            if (!mapTile.isEmpty()) {
                for (L1MapTile tgMap : mapTile) {
                    L1WorldMap.get().getMap((short)tgMap.getMapid()).setTestTile(tgMap.getX(), tgMap.getY(), tgMap.getTile());
                }
            }
            MapCheckTable.get();
            new_npc_talk.load();
if (ConfigOther.RankLevel) {
    RankingHeroTimerlv.load();

    java.util.concurrent.Executors.newSingleThreadScheduledExecutor()
        .scheduleAtFixedRate(() -> RankingHeroTimerlv.load(), 10, 10,
            java.util.concurrent.TimeUnit.MINUTES);

} else {
    RankingHeroTimer.load();

    java.util.concurrent.Executors.newSingleThreadScheduledExecutor()
        .scheduleAtFixedRate(() -> RankingHeroTimer.load(), 10, 10,
            java.util.concurrent.TimeUnit.MINUTES);
}
            L1BlendTable.getInstance().loadBlendTable();
            L1BlendTable_1.getInstance().loadBlendTable();
            EnchantOrginal.getInstance();
            EnchantAccessory.getInstance();
            RevertHpMp.get().load();
            L1FireSmithCrystalTable.get().load();
            if (ConfigAlt.QUIZ_SET_SWITCH) {
                QuizSetTable.getInstance().load();
            }
            ItemVIPTable.get();
            ServerItemGiveTable.get();
            ItemHtmlTable.get();
            CardSetTable.get().load();
            ACardTable.get().load();
            CardSetTable_doll.get().load();
            ACardTable_doll.get().load();
            NpcHierarchTable.get();
            StartTimer_Server startTimer = new StartTimer_Server();
            startTimer.start();
            StartTimer_Pc pcTimer = new StartTimer_Pc();
            pcTimer.start();
            StartTimer_Npc npcTimer = new StartTimer_Npc();
            npcTimer.start();
            StartTimer_Pet petTimer = new StartTimer_Pet();
            petTimer.start();
            StartTimer_Skill skillTimer = new StartTimer_Skill();
            skillTimer.start();
/* ★ 排行榜第一名動畫 (10分鐘刷新一次) */
java.util.concurrent.ScheduledExecutorService rankingScheduler =
    java.util.concurrent.Executors.newSingleThreadScheduledExecutor();

rankingScheduler.scheduleAtFixedRate(
    new RankingEffectTimer(),
    0,
    10,
    java.util.concurrent.TimeUnit.MINUTES
);
            Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
            DeNameTable.get().load();
            DeClanTable.get().loadIcon();
            DeClanTable.get().load();
            DeTitleTable.get().load();
            DeShopChatTable.get().load();
            DeGlobalChatTable.get().load();
            DeShopItemTable.get().load();
            L1DoorInstance.openDoor();
            MapHprMprTable.get();
            new ArmorEffect();
            WilliamItemMessage.getData();
            GfxIdOrginal.getInstance();
            NpcTalk10.load();
            ClanOriginal.getInstance();
            Explogpcpower.get().load();
            RewardArmorTable.getInstance();
            CheckTimeController.getInstance().start();
            MapsGroupTable.get().load();
            CharMapTimeReading.get().load();
            DropMobTable.get().load();
            L1MonTable.get().load();
            ItemLimitation.get();
            AutoAddSkillTable.get();
            ItemIntegration.getInstance();
            PowerItemTable.get();
            ItemSpecialAttributeTable.get().load();
            ItemSpecialAttributeCharTable.get().load();
            if (ConfigOther.onlydaypre) {
                L1DayPresentTimer.initialize();
            }
            ItemNoENTable.get().load();
            ExcavateTable.get();
            WilliamBuff.load();
            ServerItemDropTable.get();
            ItemLimitTable.get().load();
            LaBarGameTable.get();
            LaBarGameTable1.get();
            LaBarGameTable2.get();
            ServerQuestMaPTable.get();
            ExtraMagicWeaponTable.getInstance().load();
            CharWeaponTimeReading.get().load();
            SkillsProbabilityTable.get();
            ItemUseEXTable.get();
            AutoDeSpawnTable.load();
            AutoDeSpawnTable2.load();
            BlendTable.getInstance().load();
            MonsterEnhanceTable.getInstance().load();
            PatternPowerTable.get().loadPower();
            PatternConfigTable.get().loadConfig();
            PatternRecordTable.get().loadRecord();
            if (RandomGiftSet.START) {
                William_Online_Reward.getInstance();
            }
            ItemTimeTableadd.get().load();
            if (ConfigOther.Reset_Map) {
                CharMapTimeReading.get().clearAllTime();
            }
            else if (Calendar.getInstance().get(11) == ConfigOther.Reset_Map_Time) {
                CharMapTimeReading.get().clearAllTime();
            }
            EchoServerTimer.get().start();
        }
        catch (Exception e) {
            GameServer._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            String ver = "\n\r--------------------------------------------------\n\r       2024[天堂架設版本]\n\r       特此聲明：架設者一切行為皆屬個人行，\n\r       核心作者僅提供技術修改服務\n\r       並未參與任何伺服器合作與營運管理\n\r       如果任何違法行為本人.將不附連帶責任\n\r       版本整合作者官方LINE:秘密天堂\n\r--------------------------------------------------";
            GameServer._log.info("\n\r--------------------------------------------------\n\r       2024[天堂架設版本]\n\r       特此聲明：架設者一切行為皆屬個人行，\n\r       核心作者僅提供技術修改服務\n\r       並未參與任何伺服器合作與營運管理\n\r       如果任何違法行為本人.將不附連帶責任\n\r       版本整合作者官方LINE:秘密天堂\n\r--------------------------------------------------");
            CmdEcho cmdEcho = new CmdEcho(timer.get());
            cmdEcho.runCmd();
        }
    }
    
    private void cleanMissionStatus() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 9955");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void cleanclanorg() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8599");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void day() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id IN (8991, 8993)");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void map_chcekmap_Quest() {
        if (ConfigQuest.map_Quest1 > 0) {
            deleteData(ConfigQuest.map_Quest1);
        }
        if (ConfigQuest.map_Quest2 > 0) {
            deleteData(ConfigQuest.map_Quest2);
        }
        if (ConfigQuest.map_Quest3 > 0) {
            deleteData(ConfigQuest.map_Quest3);
        }
        if (ConfigQuest.map_Quest4 > 0) {
            deleteData(ConfigQuest.map_Quest4);
        }
        if (ConfigQuest.map_Quest5 > 0) {
            deleteData(ConfigQuest.map_Quest5);
        }
        if (ConfigQuest.map_Quest6 > 0) {
            deleteData(ConfigQuest.map_Quest6);
        }
        if (ConfigQuest.map_Quest7 > 0) {
            deleteData(ConfigQuest.map_Quest7);
        }
        if (ConfigQuest.map_Quest8 > 0) {
            deleteData(ConfigQuest.map_Quest8);
        }
        if (ConfigQuest.map_Quest9 > 0) {
            deleteData(ConfigQuest.map_Quest9);
        }
        if (ConfigQuest.map_Quest10 > 0) {
            deleteData(ConfigQuest.map_Quest10);
        }
        if (ConfigQuest.map_Quest11 > 0) {
            deleteData(ConfigQuest.map_Quest11);
        }
        if (ConfigQuest.map_Quest12 > 0) {
            deleteData(ConfigQuest.map_Quest12);
        }
        if (ConfigQuest.map_Quest13 > 0) {
            deleteData(ConfigQuest.map_Quest13);
        }
        if (ConfigQuest.map_Quest14 > 0) {
            deleteData(ConfigQuest.map_Quest14);
        }
        if (ConfigQuest.map_Quest15 > 0) {
            deleteData(ConfigQuest.map_Quest15);
        }
        if (ConfigQuest.map_Quest16 > 0) {
            deleteData(ConfigQuest.map_Quest16);
        }
        if (ConfigQuest.map_Quest17 > 0) {
            deleteData(ConfigQuest.map_Quest17);
        }
        if (ConfigQuest.map_Quest18 > 0) {
            deleteData(ConfigQuest.map_Quest18);
        }
        if (ConfigQuest.map_Quest19 > 0) {
            deleteData(ConfigQuest.map_Quest19);
        }
        if (ConfigQuest.map_Quest20 > 0) {
            deleteData(ConfigQuest.map_Quest20);
        }
        if (ConfigQuest.map_Quest21 > 0) {
            deleteData(ConfigQuest.map_Quest21);
        }
        if (ConfigQuest.map_Quest22 > 0) {
            deleteData(ConfigQuest.map_Quest22);
        }
        if (ConfigQuest.map_Quest23 > 0) {
            deleteData(ConfigQuest.map_Quest23);
        }
        if (ConfigQuest.map_Quest24 > 0) {
            deleteData(ConfigQuest.map_Quest24);
        }
        if (ConfigQuest.map_Quest25 > 0) {
            deleteData(ConfigQuest.map_Quest25);
        }
        if (ConfigQuest.map_Quest26 > 0) {
            deleteData(ConfigQuest.map_Quest26);
        }
        if (ConfigQuest.map_Quest27 > 0) {
            deleteData(ConfigQuest.map_Quest27);
        }
        if (ConfigQuest.map_Quest28 > 0) {
            deleteData(ConfigQuest.map_Quest28);
        }
        if (ConfigQuest.map_Quest29 > 0) {
            deleteData(ConfigQuest.map_Quest29);
        }
        if (ConfigQuest.map_Quest30 > 0) {
            deleteData(ConfigQuest.map_Quest30);
        }
    }
    
    private void chcekitemid() {
        if (ConfigItem.itemid1 > 0) {
            deleteData99(ConfigItem.itemid1);
        }
        if (ConfigItem.itemid2 > 0) {
            deleteData99(ConfigItem.itemid2);
        }
        if (ConfigItem.itemid3 > 0) {
            deleteData99(ConfigItem.itemid3);
        }
        if (ConfigItem.itemid4 > 0) {
            deleteData99(ConfigItem.itemid4);
        }
        if (ConfigItem.itemid5 > 0) {
            deleteData99(ConfigItem.itemid5);
        }
        if (ConfigItem.itemid6 > 0) {
            deleteData99(ConfigItem.itemid6);
        }
        if (ConfigItem.itemid7 > 0) {
            deleteData99(ConfigItem.itemid7);
        }
        if (ConfigItem.itemid8 > 0) {
            deleteData99(ConfigItem.itemid8);
        }
        if (ConfigItem.itemid9 > 0) {
            deleteData99(ConfigItem.itemid9);
        }
        if (ConfigItem.itemid10 > 0) {
            deleteData99(ConfigItem.itemid10);
        }
        if (ConfigItem.itemid11 > 0) {
            deleteData99(ConfigItem.itemid11);
        }
        if (ConfigItem.itemid12 > 0) {
            deleteData99(ConfigItem.itemid12);
        }
        if (ConfigItem.itemid13 > 0) {
            deleteData99(ConfigItem.itemid13);
        }
        if (ConfigItem.itemid14 > 0) {
            deleteData99(ConfigItem.itemid14);
        }
        if (ConfigItem.itemid15 > 0) {
            deleteData99(ConfigItem.itemid15);
        }
        if (ConfigItem.itemid16 > 0) {
            deleteData99(ConfigItem.itemid16);
        }
        if (ConfigItem.itemid17 > 0) {
            deleteData99(ConfigItem.itemid17);
        }
        if (ConfigItem.itemid18 > 0) {
            deleteData99(ConfigItem.itemid18);
        }
        if (ConfigItem.itemid19 > 0) {
            deleteData99(ConfigItem.itemid19);
        }
        if (ConfigItem.itemid20 > 0) {
            deleteData99(ConfigItem.itemid20);
        }
        if (ConfigItem.itemid21 > 0) {
            deleteData99(ConfigItem.itemid21);
        }
        if (ConfigItem.itemid22 > 0) {
            deleteData99(ConfigItem.itemid22);
        }
        if (ConfigItem.itemid23 > 0) {
            deleteData99(ConfigItem.itemid23);
        }
        if (ConfigItem.itemid24 > 0) {
            deleteData99(ConfigItem.itemid24);
        }
        if (ConfigItem.itemid25 > 0) {
            deleteData99(ConfigItem.itemid25);
        }
        if (ConfigItem.itemid26 > 0) {
            deleteData99(ConfigItem.itemid26);
        }
        if (ConfigItem.itemid27 > 0) {
            deleteData99(ConfigItem.itemid27);
        }
        if (ConfigItem.itemid28 > 0) {
            deleteData99(ConfigItem.itemid28);
        }
        if (ConfigItem.itemid29 > 0) {
            deleteData99(ConfigItem.itemid29);
        }
        if (ConfigItem.itemid30 > 0) {
            deleteData99(ConfigItem.itemid30);
        }
    }
    
    private void chcekQuest() {
        if (ConfigQuest.Quest1 > 0) {
            deleteData(ConfigQuest.Quest1);
        }
        if (ConfigQuest.Quest2 > 0) {
            deleteData(ConfigQuest.Quest2);
        }
        if (ConfigQuest.Quest3 > 0) {
            deleteData(ConfigQuest.Quest3);
        }
        if (ConfigQuest.Quest4 > 0) {
            deleteData(ConfigQuest.Quest4);
        }
        if (ConfigQuest.Quest5 > 0) {
            deleteData(ConfigQuest.Quest5);
        }
        if (ConfigQuest.Quest6 > 0) {
            deleteData(ConfigQuest.Quest6);
        }
        if (ConfigQuest.Quest7 > 0) {
            deleteData(ConfigQuest.Quest7);
        }
        if (ConfigQuest.Quest8 > 0) {
            deleteData(ConfigQuest.Quest8);
        }
        if (ConfigQuest.Quest9 > 0) {
            deleteData(ConfigQuest.Quest9);
        }
        if (ConfigQuest.Quest10 > 0) {
            deleteData(ConfigQuest.Quest10);
        }
        if (ConfigQuest.Quest11 > 0) {
            deleteData(ConfigQuest.Quest11);
        }
        if (ConfigQuest.Quest12 > 0) {
            deleteData(ConfigQuest.Quest12);
        }
        if (ConfigQuest.Quest13 > 0) {
            deleteData(ConfigQuest.Quest13);
        }
        if (ConfigQuest.Quest14 > 0) {
            deleteData(ConfigQuest.Quest14);
        }
        if (ConfigQuest.Quest15 > 0) {
            deleteData(ConfigQuest.Quest15);
        }
        if (ConfigQuest.Quest16 > 0) {
            deleteData(ConfigQuest.Quest16);
        }
        if (ConfigQuest.Quest17 > 0) {
            deleteData(ConfigQuest.Quest17);
        }
        if (ConfigQuest.Quest18 > 0) {
            deleteData(ConfigQuest.Quest18);
        }
        if (ConfigQuest.Quest19 > 0) {
            deleteData(ConfigQuest.Quest19);
        }
        if (ConfigQuest.Quest20 > 0) {
            deleteData(ConfigQuest.Quest20);
        }
        if (ConfigQuest.Quest21 > 0) {
            deleteData(ConfigQuest.Quest21);
        }
        if (ConfigQuest.Quest22 > 0) {
            deleteData(ConfigQuest.Quest22);
        }
        if (ConfigQuest.Quest23 > 0) {
            deleteData(ConfigQuest.Quest23);
        }
        if (ConfigQuest.Quest24 > 0) {
            deleteData(ConfigQuest.Quest24);
        }
        if (ConfigQuest.Quest25 > 0) {
            deleteData(ConfigQuest.Quest25);
        }
        if (ConfigQuest.Quest26 > 0) {
            deleteData(ConfigQuest.Quest26);
        }
        if (ConfigQuest.Quest27 > 0) {
            deleteData(ConfigQuest.Quest27);
        }
        if (ConfigQuest.Quest28 > 0) {
            deleteData(ConfigQuest.Quest28);
        }
        if (ConfigQuest.Quest29 > 0) {
            deleteData(ConfigQuest.Quest29);
        }
        if (ConfigQuest.Quest30 > 0) {
            deleteData(ConfigQuest.Quest30);
        }
        if (ConfigQuest.Quest31 > 0) {
            deleteData(ConfigQuest.Quest31);
        }
        if (ConfigQuest.Quest32 > 0) {
            deleteData(ConfigQuest.Quest32);
        }
        if (ConfigQuest.Quest33 > 0) {
            deleteData(ConfigQuest.Quest33);
        }
        if (ConfigQuest.Quest34 > 0) {
            deleteData(ConfigQuest.Quest34);
        }
        if (ConfigQuest.Quest35 > 0) {
            deleteData(ConfigQuest.Quest35);
        }
        if (ConfigQuest.Quest36 > 0) {
            deleteData(ConfigQuest.Quest36);
        }
        if (ConfigQuest.Quest37 > 0) {
            deleteData(ConfigQuest.Quest37);
        }
        if (ConfigQuest.Quest38 > 0) {
            deleteData(ConfigQuest.Quest38);
        }
        if (ConfigQuest.Quest39 > 0) {
            deleteData(ConfigQuest.Quest39);
        }
        if (ConfigQuest.Quest40 > 0) {
            deleteData(ConfigQuest.Quest40);
        }
        if (ConfigQuest.Quest41 > 0) {
            deleteData(ConfigQuest.Quest41);
        }
        if (ConfigQuest.Quest42 > 0) {
            deleteData(ConfigQuest.Quest42);
        }
        if (ConfigQuest.Quest43 > 0) {
            deleteData(ConfigQuest.Quest43);
        }
        if (ConfigQuest.Quest44 > 0) {
            deleteData(ConfigQuest.Quest44);
        }
        if (ConfigQuest.Quest45 > 0) {
            deleteData(ConfigQuest.Quest45);
        }
        if (ConfigQuest.Quest46 > 0) {
            deleteData(ConfigQuest.Quest46);
        }
        if (ConfigQuest.Quest47 > 0) {
            deleteData(ConfigQuest.Quest47);
        }
        if (ConfigQuest.Quest48 > 0) {
            deleteData(ConfigQuest.Quest48);
        }
        if (ConfigQuest.Quest49 > 0) {
            deleteData(ConfigQuest.Quest49);
        }
        if (ConfigQuest.Quest50 > 0) {
            deleteData(ConfigQuest.Quest50);
        }
    }
    
    public static void deleteData99(int questid) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("DELETE FROM `character_items` WHERE `item_id`=?");
            pm.setInt(1, questid);
            pm.execute();
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    public static void deleteData(int questid) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("DELETE FROM `character_quests` WHERE `quest_id`=?");
            pm.setInt(1, questid);
            pm.execute();
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    private void clanaddskill() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8541");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void clanaddskill1() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8544");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void clanaddskill2() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8545");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void clanaddskill3() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8546");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void deleclan() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 50988");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void dele1() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `character_other` SET `usemap`=-1");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
        public static String getIp() throws IOException {
            URL whatismyip = new URL("http://icanhazip.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            return in.readLine();
        }
    }

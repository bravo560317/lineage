package com.add;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;

// 自製排行榜（SQL）
import com.add.L1FactionRanking;
import com.add.L1PrestigeRanking;
import com.add.L1WeaponRanking;
import com.add.L1PolyCardRanking;
import com.add.L1DollCardRanking;
import com.add.L1PetRanking;

// 原本NPC排行榜（一定要留）
import com.lineage.data.npc.quest.Npc_Ezpaycount;
import com.lineage.data.npc.quest.Npc_HeroRanking;
import com.lineage.data.npc.quest.Npc_KillerRanking;
import com.lineage.data.npc.quest.Npc_WealthRanking;
import com.lineage.data.npc.quest.Npc_ClanRanking;
import com.lineage.data.npc.quest.Npc_EmblemRanking;
import com.lineage.data.npc.quest.Npc_AotowerRanking;

public class L1RankingManager {

    public static void showRanking(L1PcInstance pc, L1NpcInstance npc, String type) {

        if (pc == null || npc == null || type == null) {
            return;
        }

        try {
            switch (type.toLowerCase()) {

                // ===== 自製（SQL排行榜）=====
                case "faction":
                    pc.sendPackets(new S_NPCTalkReturn(
                        npc.getId(), "ranking2",
                        L1FactionRanking.TopFaction()
                    ));
                    break;

                case "prestige":
                    pc.sendPackets(new S_NPCTalkReturn(
                        npc.getId(), "ranking",
                        L1PrestigeRanking.TopPrestige()
                    ));
                    break;

                case "weapon":
                    pc.sendPackets(new S_NPCTalkReturn(
                        npc.getId(), "weapon_rank2",
                        L1WeaponRanking.TopWeapon()
                    ));
                    break;

                case "polycard":
                    pc.sendPackets(new S_NPCTalkReturn(
                        npc.getId(), "rankingpoly",
                        L1PolyCardRanking.TopPolyCard()
                    ));
                    break;

                case "dollcard":
                    pc.sendPackets(new S_NPCTalkReturn(
                        npc.getId(), "rankingdoll",
                        L1DollCardRanking.TopDollCard()
                    ));
                    break;

                case "petrank":
                    pc.sendPackets(new S_NPCTalkReturn(
                        npc.getId(), "rankingpet",
                        L1PetRanking.TopPetHp()
                    ));
                    break;

                // ===== 原本NPC排行榜（保留）=====
                case "ezpay":
                    ((Npc_Ezpaycount) Npc_Ezpaycount.get()).talk(pc, npc);
                    break;

                case "hero":   // 等級排行
                    Npc_HeroRanking.get().action(pc, npc, "a", 0L);
                    break;

                case "killer": // 殺人排行
                    Npc_KillerRanking.get().action(pc, npc, "1", 0L);
                    break;

                case "wealth": // 財富排行
                    ((Npc_WealthRanking) Npc_WealthRanking.get()).talk(pc, npc);
                    break;

                case "clan":   // 血盟排行
                    ((Npc_ClanRanking) Npc_ClanRanking.get()).talk(pc, npc);
                    break;

                case "emblem":
                    ((Npc_EmblemRanking) Npc_EmblemRanking.get()).talk(pc, npc);
                    break;

                case "emblem1":
                case "emblem2":
                case "emblem3":
                case "emblem4":
                    ((Npc_EmblemRanking) Npc_EmblemRanking.get())
                            .action(pc, npc, type, 0L);
                    break;

                case "aotower":
                    ((Npc_AotowerRanking) Npc_AotowerRanking.get())
                            .action(pc, npc, "aotower", 0L);
                    break;

                default:
                    pc.sendPackets(new S_SystemMessage("未知排行榜類型: " + type));
                    break;
            }

        } catch (Exception e) {
            pc.sendPackets(new S_SystemMessage("排行榜錯誤"));
            e.printStackTrace();
        }
    }
}
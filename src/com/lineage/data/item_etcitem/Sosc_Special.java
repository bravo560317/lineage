package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ShowPolyList;
import com.lineage.server.timecontroller.event.ranking.RankingHeroTimer;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Sosc_Special extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Sosc_Special();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (RankingHeroTimer.get_top10().containsValue(pc.getName()) || RankingHeroTimer.get_top3C().containsValue(pc.getName()) || RankingHeroTimer.get_top3K().containsValue(pc.getName()) || RankingHeroTimer.get_top3E().containsValue(pc.getName()) || RankingHeroTimer.get_top3W().containsValue(pc.getName()) || RankingHeroTimer.get_top3D().containsValue(pc.getName()) || RankingHeroTimer.get_top3G().containsValue(pc.getName()) || RankingHeroTimer.get_top3I().containsValue(pc.getName()) || pc.isGm()) {
            if (pc.get_sex() == 0 && pc.isCrown()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polymp"));
            }
            else if (pc.get_sex() == 1 && pc.isCrown()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polyfp"));
            }
            else if (pc.get_sex() == 0 && pc.isKnight()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polymk"));
            }
            else if (pc.get_sex() == 1 && pc.isKnight()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polyfk"));
            }
            else if (pc.get_sex() == 0 && pc.isElf()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polyme"));
            }
            else if (pc.get_sex() == 1 && pc.isElf()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polyfe"));
            }
            else if (pc.get_sex() == 0 && pc.isWizard()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polymm"));
            }
            else if (pc.get_sex() == 1 && pc.isWizard()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polyfm"));
            }
            else if (pc.get_sex() == 0 && pc.isDarkelf()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polymd"));
            }
            else if (pc.get_sex() == 1 && pc.isDarkelf()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polyfd"));
            }
            else if (pc.get_sex() == 0 && pc.isDragonKnight()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polymr"));
            }
            else if (pc.get_sex() == 1 && pc.isDragonKnight()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polyfr"));
            }
            else if (pc.get_sex() == 0 && pc.isIllusionist()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polymi"));
            }
            else if (pc.get_sex() == 1 && pc.isIllusionist()) {
                pc.sendPackets(new S_ShowPolyList(pc.getId(), "top10polyfi"));
            }
        }
        else {
            pc.sendPackets(new S_ShowPolyList(pc.getId(), "specialpoly"));
        }
        if (!pc.isItemPoly()) {
            pc.setSummonMonster(false);
            pc.setItemPoly(true);
            pc.setItemPoly1(true);
            pc.setPolyScroll(item);
        }
    }
}

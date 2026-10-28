package com.lineage.data.item_etcitem.wand;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class FollowPc_Item extends ItemExecutor
{
    private FollowPc_Item() {
    }
    
    public static ItemExecutor get() {
        return new FollowPc_Item();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance _pc, L1ItemInstance item) {
        try {
            String type1 = "尚未設置";
            String type2 = "0";
            String type3 = "禁止跟隨";
            String type4 = "關閉";
            String type5 = "關閉";
            String type6 = "關閉";
            String type7 = "關閉";
            String type8 = "關閉";
            String type9 = "關閉";
            String type10 = "關閉";
            String type11 = "關閉";
            String type12 = "關閉";
            String type13 = "關閉";
            if (_pc.getfollowskilltype() == 0) {
                type1 = "尚未設置";
            }
            else if (_pc.getfollowskilltype() == 1) {
                type1 = "初級治癒術";
            }
            else if (_pc.getfollowskilltype() == 2) {
                type1 = "中級治癒術";
            }
            else if (_pc.getfollowskilltype() == 3) {
                type1 = "高級治癒術";
            }
            else if (_pc.getfollowskilltype() == 4) {
                type1 = "全部治癒術";
            }
            if (_pc.getfollowskillhp() > 0) {
                type2 = new StringBuilder().append(_pc.getfollowskillhp()).toString();
            }
            if (_pc.isfollowcheck()) {
                type3 = "允許跟隨";
            }
            if (_pc.isfollowskill42()) {
                type4 = "開啟";
            }
            if (_pc.isfollowskill26()) {
                type5 = "開啟";
            }
            if (_pc.isfollowskill55()) {
                type6 = "開啟";
            }
            if (_pc.isfollowskill68()) {
                type7 = "開啟";
            }
            if (_pc.isfollowskill79()) {
                type8 = "開啟";
            }
            if (_pc.isfollowskill148()) {
                type9 = "開啟";
            }
            if (_pc.isfollowskill151()) {
                type10 = "開啟";
            }
            if (_pc.isfollowskill149()) {
                type11 = "開啟";
            }
            if (_pc.isfollowskill158()) {
                type12 = "開啟";
            }
            _pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "Lh8", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13 }));
        }
        catch (Exception ex) {}
    }
}

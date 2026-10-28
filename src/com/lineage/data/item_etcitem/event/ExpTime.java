package com.lineage.data.item_etcitem.event;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ExpTime extends ItemExecutor {

    public static ItemExecutor get() {
        return new ExpTime();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        showExpTime(pc);
    }

    // ⭐ 主功能：查詢經驗藥水
    public void showExpTime(L1PcInstance pc) {

        if (pc == null) return;

        int time1 = 0;
        int time2 = 0;

        int id1 = 0;
        int id2 = 0;

        // ⭐ 第一段：6666~6681
        for (int id = 6666; id <= 6681; id++) {
            int t = pc.getSkillEffectTimeSec(id);
            if (t > 0) {
                time1 = t;
                id1 = id;
                break;
            }
        }

        // ⭐ 第二段：5000~5004
        for (int id = 5000; id <= 5004; id++) {
            int t = pc.getSkillEffectTimeSec(id);
            if (t > 0) {
                time2 = t;
                id2 = id;
                break;
            }
        }

        String name1 = getExp1Name(id1);
        String name2 = getExp2Name(id2);

        // ⭐ 顯示第一段
        if (time1 > 0) {
            pc.sendPackets(new S_ServerMessage(
                "\\fX第一段經驗(" + name1 + ") 剩餘：" + formatTime(time1)
            ));
        } else {
            pc.sendPackets(new S_ServerMessage("\\fX目前沒有第一段經驗藥水效果"));
        }

        // ⭐ 顯示第二段
        if (time2 > 0) {
            pc.sendPackets(new S_ServerMessage(
                "\\fY第二段經驗(" + name2 + ") 剩餘：" + formatTime(time2)
            ));
        } else {
            pc.sendPackets(new S_ServerMessage("\\fY目前沒有第二段經驗藥水效果"));
        }
    }

    // ⭐ 第一段倍率對照（6666~6681）
    private String getExp1Name(int id) {
        switch (id) {
            case 6666: return "130%";
            case 6667: return "150%";
            case 6668: return "170%";
            case 6669: return "200%";
            case 6670: return "250%";
            case 6671: return "300%";
            case 6672: return "350%";
            case 6673: return "400%";
            case 6674: return "450%";
            case 6675: return "500%";
            case 6676: return "550%";
            case 6677: return "600%";
            case 6678: return "650%";
            case 6679: return "700%";
            case 6680: return "750%";
            case 6681: return "800%";
            default: return "???";
        }
    }

    // ⭐ 第二段倍率對照（5000~5004）
    private String getExp2Name(int id) {
        switch (id) {
            case 5000: return "150%";
            case 5001: return "175%";
            case 5002: return "200%";
            case 5003: return "225%";
            case 5004: return "250%";
            default: return "???";
        }
    }

    // ⭐ 秒轉 分:秒
    private String formatTime(int sec) {
        int min = sec / 60;
        int s = sec % 60;
        return min + "分" + s + "秒";
    }
}
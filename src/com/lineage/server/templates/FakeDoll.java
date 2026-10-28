package com.lineage.server.templates;

import java.util.ArrayList;

public class FakeDoll extends L1Doll {

    public FakeDoll(int gfxid) {
        this.set_itemid(0);     // 不用 item
        this.set_gfxid(gfxid);  // 假娃娃外觀
        this.set_nameid("");    // 名稱
        this.set_time(0);       // 時間 0 = 永久

        // 完全沒有能力
        this.setPowerList(new ArrayList<>());
    }
}

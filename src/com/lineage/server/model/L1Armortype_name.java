package com.lineage.server.model;

public final class L1Armortype_name {

    public static String _1;
    public static String _2;
    public static String _3;
    public static String _4;
    public static String _5;
    public static String _6;
    public static String _7;
    public static String _8;
    public static String _9;
    public static String _10;
    public static String _11;
    public static String _12;
    public static String _13;
    public static String _14;
    public static String _15;
    public static String _16;
    public static String _17;
    public static String _18;
    public static String _19;
    public static String _20;
    public static String _21;
    public static String _22;
    public static String _23;
    public static String _24;
    public static String _25;
    public static String _26;
    public static String _27;
    public static String _28;
    public static String _29;
    public static String _30;
    public static String _31;
    public static String _32;
    public static String _33;
    public static String _34;
    public static String _35;
    public static String _36;
    public static String _37;
    public static String _38;
    public static String _39;
    public static String _40;
    public static String _41;
    public static String _42;
    public static String _43;
    public static String _44;
    public static String _45;
    public static String _46;
    public static String _47;
    public static String _48;
    public static String _49;
    public static String _50;
    public static String _51;
    public static String _52;
    public static String _53;
    public static String _54;
    public static String _55;
    public static String _56;
    public static String _57;
    public static String _58;
    public static String _59;
    public static String _60;
    // --- 以下為擴充的 61 ~ 100 ---
    public static String _61;
    public static String _62;
    public static String _63;
    public static String _64;
    public static String _65;
    public static String _66;
    public static String _67;
    public static String _68;
    public static String _69;
    public static String _70;
    public static String _71;
    public static String _72;
    public static String _73;
    public static String _74;
    public static String _75;
    public static String _76;
    public static String _77;
    public static String _78;
    public static String _79;
    public static String _80;
    public static String _81;
    public static String _82;
    public static String _83;
    public static String _84;
    public static String _85;
    public static String _86;
    public static String _87;
    public static String _88;
    public static String _89;
    public static String _90;
    public static String _91;
    public static String _92;
    public static String _93;
    public static String _94;
    public static String _95;
    public static String _96;
    public static String _97;
    public static String _98;
    public static String _99;
    public static String _100;

    static {
        // 將這裡的迴圈上限從 60 改為 100
        for (int i = 1; i <= 100; i++) {
            try {
                String msg = com.add.dr_name_message.ShowMessage(i);
                java.lang.reflect.Field f = L1Armortype_name.class.getDeclaredField("_" + i);
                f.set(null, msg);
            } catch (Exception e) {
                // 若找不到欄位或 SQL 無對應名稱，不中斷
            }
        }
    }
}
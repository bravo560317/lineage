/*     */ package com.lineage.server.datatables;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.templates.L1PatternPower;
/*     */ import com.lineage.server.utils.PerformanceTimer;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class PatternPowerTable {
/*  24 */   private static final Log _log = LogFactory.getLog(PatternPowerTable.class);
/*     */   
/*  25 */   private static final Map<Integer, L1PatternPower> powerMap = new HashMap<>();
/*     */   
/*     */   private static PatternPowerTable patternPowerTable;
/*     */   
/*     */   public static PatternPowerTable get() {
/*  29 */     if (patternPowerTable == null)
/*  30 */       return new PatternPowerTable(); 
/*  32 */     return patternPowerTable;
/*     */   }
/*     */   
/*     */   public static void modifierTemplatePower(L1PcInstance pc, int patternNo, int type) {
/*  43 */     L1PatternPower patternPower = powerMap.get(Integer.valueOf(patternNo));
/*  44 */     if (patternPower != null) {
/*  45 */       if (patternPower.getAddAc() > 0)
/*  46 */         pc.addAc(patternPower.getAddAc() * type * -1); 
/*  48 */       if (patternPower.getAddStr() > 0)
/*  49 */         pc.addStr(patternPower.getAddStr() * type); 
/*  51 */       if (patternPower.getAddCon() > 0)
/*  52 */         pc.addCon(patternPower.getAddCon() * type); 
/*  54 */       if (patternPower.getAddDex() > 0)
/*  55 */         pc.addDex(patternPower.getAddDex() * type); 
/*  57 */       if (patternPower.getAddInt() > 0)
/*  58 */         pc.addInt(patternPower.getAddInt() * type); 
/*  60 */       if (patternPower.getAddWis() > 0)
/*  61 */         pc.addWis(patternPower.getAddWis() * type); 
/*  63 */       if (patternPower.getAddCha() > 0)
/*  64 */         pc.addCha(patternPower.getAddCha() * type); 
/*  66 */       if (patternPower.getAddMaxHp() > 0)
/*  67 */         pc.addMaxHp(patternPower.getAddMaxHp() * type); 
/*  69 */       if (patternPower.getAddMaxMp() > 0)
/*  70 */         pc.addMaxMp(patternPower.getAddMaxMp() * type); 
/*  72 */       if (patternPower.getAddHpr() > 0)
/*  73 */         pc.addHpr(patternPower.getAddHpr() * type); 
/*  75 */       if (patternPower.getAddMpr() > 0)
/*  76 */         pc.addMpr(patternPower.getAddMpr() * type); 
/*  78 */       if (patternPower.getAddDmg() > 0)
/*  79 */         pc.addDmgup(patternPower.getAddDmg() * type); 
/*  81 */       if (patternPower.getAddBowDmg() > 0)
/*  82 */         pc.addBowDmgup(patternPower.getAddBowDmg() * type); 
/*  84 */       if (patternPower.getAddHit() > 0)
/*  85 */         pc.addHitup(patternPower.getAddHit() * type); 
/*  87 */       if (patternPower.getAddBowHit() > 0)
/*  88 */         pc.addBowHitup(patternPower.getAddBowHit() * type); 
/*  90 */       if (patternPower.getAddDmgReduction() > 0)
/*  91 */         pc.add_reduction_dmg(patternPower.getAddDmgReduction() * type); 
/*  93 */       if (patternPower.getAddMr() > 0)
/*  94 */         pc.addMr(patternPower.getAddMr() * type); 
/*  96 */       if (patternPower.getAddSp() > 0)
/*  97 */         pc.addSp(patternPower.getAddSp() * type); 
/*  99 */       if (patternPower.getAddRegisterStun() > 0)
/* 100 */         pc.addRegistStun(patternPower.getAddRegisterStun() * type); 
/* 102 */       if (patternPower.getAddRegisterStone() > 0)
/* 103 */         pc.addRegistStone(patternPower.getAddRegisterStone() * type); 
/* 105 */       if (patternPower.getAddRegisterSleep() > 0)
/* 106 */         pc.addRegistSleep(patternPower.getAddRegisterSleep() * type); 
/* 108 */       if (patternPower.getAddRegisterFreeze() > 0)
/* 109 */         pc.add_regist_freeze(patternPower.getAddRegisterFreeze() * type); 
/* 111 */       if (patternPower.getAddRegisterSustain() > 0)
/* 112 */         pc.addRegistSustain(patternPower.getAddRegisterSustain() * type); 
/* 114 */       if (patternPower.getAddRegisterBlind() > 0)
/* 115 */         pc.addRegistBlind(patternPower.getAddRegisterBlind() * type); 
/* 117 */       if (patternPower.getMagicCriticalChance() > 0)
/* 118 */         pc.addOriginalMagicCritical(patternPower.getMagicCriticalChance() * type); 
/* 120 */       if (patternPower.getMagicDmgReduction() > 0)
/* 121 */         pc.addMagicDmgReduction(patternPower.getMagicDmgReduction() * type); 
/* 123 */       if (patternPower.getMagicModifierDmg() > 0)
/* 124 */         pc.add_magic_modifier_dmg(patternPower.getMagicModifierDmg() * type); 
/* 127 */       if (patternPower.getAddUpHpPotion() > 0)
/* 128 */         pc.add_up_hp_potion(patternPower.getAddUpHpPotion() * type); 
/* 131 */       if (patternPower.getAddEvasion() > 0)
/* 132 */         pc.set_evasion(patternPower.getAddEvasion() * type); 
/* 135 */       if (patternPower.getAddPvpDmg() > 0)
/* 136 */         pc.addPVPdmg(patternPower.getAddPvpDmg() * type); 
/* 139 */       if (patternPower.getAddPvpReduction() > 0)
/* 140 */         pc.addPVPdmgReduction(patternPower.getAddPvpReduction() * type); 
/* 143 */       if (patternPower.getAddUpHpNumber() > 0)
/* 144 */         pc.add_uhp_number(patternPower.getAddUpHpNumber() * type); 
/* 147 */       if (patternPower.getAddExpPotion() > 0)
/* 148 */         pc.setExpPoint(patternPower.getAddExpPotion() * type); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String[] getPowerString(L1PatternPower power) {
/* 160 */     String[] data = new String[33];
/* 161 */     Arrays.fill((Object[])data, "0");
/* 162 */     int index = 0;
/* 163 */     if (power != null) {
/* 164 */       if (power.getAddAc() > 0)
/* 165 */         data[index] = String.valueOf(power.getAddAc()); 
/* 168 */       index++;
/* 170 */       if (power.getAddStr() > 0)
/* 171 */         data[index] = String.valueOf(power.getAddStr()); 
/* 174 */       index++;
/* 176 */       if (power.getAddCon() > 0)
/* 177 */         data[index] = String.valueOf(power.getAddCon()); 
/* 180 */       index++;
/* 182 */       if (power.getAddDex() > 0)
/* 183 */         data[index] = String.valueOf(power.getAddDex()); 
/* 186 */       index++;
/* 188 */       if (power.getAddInt() > 0)
/* 189 */         data[index] = String.valueOf(power.getAddInt()); 
/* 192 */       index++;
/* 194 */       if (power.getAddWis() > 0)
/* 195 */         data[index] = String.valueOf(power.getAddWis()); 
/* 198 */       index++;
/* 200 */       if (power.getAddCha() > 0)
/* 201 */         data[index] = String.valueOf(power.getAddCha()); 
/* 204 */       index++;
/* 206 */       if (power.getAddMaxHp() > 0)
/* 207 */         data[index] = String.valueOf(power.getAddMaxHp()); 
/* 210 */       index++;
/* 212 */       if (power.getAddMaxMp() > 0)
/* 213 */         data[index] = String.valueOf(power.getAddMaxMp()); 
/* 216 */       index++;
/* 218 */       if (power.getAddHpr() > 0)
/* 219 */         data[index] = String.valueOf(power.getAddHpr()); 
/* 222 */       index++;
/* 224 */       if (power.getAddMpr() > 0)
/* 225 */         data[index] = String.valueOf(power.getAddMpr()); 
/* 228 */       index++;
/* 230 */       if (power.getAddDmg() > 0)
/* 231 */         data[index] = String.valueOf(power.getAddDmg()); 
/* 234 */       index++;
/* 236 */       if (power.getAddBowDmg() > 0)
/* 237 */         data[index] = String.valueOf(power.getAddBowDmg()); 
/* 240 */       index++;
/* 242 */       if (power.getAddHit() > 0)
/* 243 */         data[index] = String.valueOf(power.getAddHit()); 
/* 246 */       index++;
/* 248 */       if (power.getAddBowHit() > 0)
/* 249 */         data[index] = String.valueOf(power.getAddBowHit()); 
/* 252 */       index++;
/* 254 */       if (power.getAddDmgReduction() > 0)
/* 255 */         data[index] = String.valueOf(power.getAddDmgReduction()); 
/* 258 */       index++;
/* 260 */       if (power.getAddMr() > 0)
/* 261 */         data[index] = String.valueOf(power.getAddMr()); 
/* 264 */       index++;
/* 266 */       if (power.getAddSp() > 0)
/* 267 */         data[index] = String.valueOf(power.getAddSp()); 
/* 270 */       index++;
/* 272 */       if (power.getMagicCriticalChance() > 0)
/* 273 */         data[index] = String.valueOf(power.getMagicCriticalChance()); 
/* 276 */       index++;
/* 278 */       if (power.getMagicDmgReduction() > 0)
/* 279 */         data[index] = String.valueOf(power.getMagicDmgReduction()); 
/* 282 */       index++;
/* 284 */       if (power.getMagicModifierDmg() > 0)
/* 285 */         data[index] = String.valueOf(power.getMagicModifierDmg()); 
/* 288 */       index++;
/* 290 */       if (power.getAddUpHpPotion() > 0)
/* 291 */         data[index] = String.valueOf(power.getAddUpHpPotion()); 
/* 294 */       index++;
/* 296 */       if (power.getAddRegisterStun() > 0)
/* 297 */         data[index] = String.valueOf(power.getAddRegisterStun()); 
/* 300 */       index++;
/* 302 */       if (power.getAddRegisterStone() > 0)
/* 303 */         data[index] = String.valueOf(power.getAddRegisterStone()); 
/* 306 */       index++;
/* 308 */       if (power.getAddRegisterSleep() > 0)
/* 309 */         data[index] = String.valueOf(power.getAddRegisterSleep()); 
/* 312 */       index++;
/* 314 */       if (power.getAddRegisterFreeze() > 0)
/* 315 */         data[index] = String.valueOf(power.getAddRegisterFreeze()); 
/* 318 */       index++;
/* 320 */       if (power.getAddRegisterSustain() > 0)
/* 321 */         data[index] = String.valueOf(power.getAddRegisterSustain()); 
/* 324 */       index++;
/* 326 */       if (power.getAddRegisterBlind() > 0)
/* 327 */         data[index] = String.valueOf(power.getAddRegisterBlind()); 
/*     */     } 
/* 331 */     return data;
/*     */   }
/*     */   

/*     */   public void loadPower() {
/* 338 */     PerformanceTimer timer = new PerformanceTimer();
/* 339 */     Connection con = null;
/* 340 */     PreparedStatement pstm = null;
/* 341 */     ResultSet rs = null;
/*     */     try {
/* 343 */       con = DatabaseFactory.get().getConnection();
/* 344 */       pstm = con.prepareStatement("SELECT * FROM `系統_紋樣能力`");
/* 345 */       rs = pstm.executeQuery();
/* 347 */       while (rs.next()) {
/* 349 */         int patternNo = rs.getInt("能力編碼");
/* 350 */         int addAc = rs.getInt("防禦");
/* 351 */         int addStr = rs.getInt("力量");
/* 352 */         int addCon = rs.getInt("體質");
/* 353 */         int addDex = rs.getInt("敏捷");
/* 354 */         int addInt = rs.getInt("智力");
/* 355 */         int addWis = rs.getInt("精神");
/* 356 */         int addCha = rs.getInt("魅力");
/* 357 */         int addMaxHp = rs.getInt("血量");
/* 358 */         int addMaxMp = rs.getInt("魔力");
/* 359 */         int addHpr = rs.getInt("血量回復");
/* 360 */         int addMpr = rs.getInt("魔力回復");
/* 361 */         int addDmg = rs.getInt("近戰攻擊");
/* 362 */         int addBowDmg = rs.getInt("遠程攻擊");
/* 363 */         int addHit = rs.getInt("近戰命中");
/* 364 */         int addBowHit = rs.getInt("遠程命中");
/* 365 */         int addDmgReduction = rs.getInt("傷害減免");
/* 366 */         int addMr = rs.getInt("魔防");
/* 367 */         int addSp = rs.getInt("魔攻");
/* 368 */         int addRegisterStun = rs.getInt("昏迷耐性");
/* 369 */         int addRegisterStone = rs.getInt("石化耐性");
/* 370 */         int addRegisterSleep = rs.getInt("睡眠耐性");
/* 371 */         int addRegisterFreeze = rs.getInt("冰凍耐性");
/* 372 */         int addRegisterSustain = rs.getInt("支撐耐性");
/* 373 */         int addRegisterBlind = rs.getInt("致盲耐性");
/* 374 */         int MagicCriticalChance = rs.getInt("魔法爆擊");
/* 375 */         int MagicDmgReduction = rs.getInt("魔法傷害減免");
/* 376 */         int magicModifierDmg = rs.getInt("魔法傷害值");
/* 377 */         int addEvasion = rs.getInt("閃避");
/* 378 */         int addPvpDmg = rs.getInt("PVP傷害");
/* 379 */         int addPvpReduction = rs.getInt("PVP減免");
/* 380 */         int addUpHpNumber = rs.getInt("藥水回復量");
/* 381 */         int addUpHpPotion = rs.getInt("藥水灰復%");
/* 382 */         int addExpPotion = rs.getInt("狩獵經驗%");
/* 383 */         powerMap.put(Integer.valueOf(patternNo), new L1PatternPower(addAc, addStr, addCon, addDex, addInt, addWis, addCha, addMaxHp, addMaxMp, addHpr, addMpr, addDmg, addBowDmg, addHit, addBowHit, addDmgReduction, addMr, addSp, addRegisterStun, addRegisterStone, addRegisterSleep, addRegisterFreeze, addRegisterSustain, addRegisterBlind, MagicCriticalChance, MagicDmgReduction, magicModifierDmg, addUpHpPotion, addEvasion, addPvpDmg, addPvpReduction, addUpHpNumber, addExpPotion));
/*     */       } 
/* 386 */     } catch (Exception e) {
/* 387 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/* 390 */       SQLUtil.close(rs);
/* 391 */       SQLUtil.close(pstm);
/* 392 */       SQLUtil.close(con);
/*     */     } 
/* 394 */     _log.info("載入系統_紋樣能力: " + powerMap.size() + "(" + timer.get() + "ms)");
/*     */   }
/*     */   
/*     */   public L1PatternPower getTemplate(int patternNo) {
/* 404 */     return powerMap.get(Integer.valueOf(patternNo));
/*     */   }
/*     */ }


/* Location:              C:\Users\User\Desktop\Server_Game.jar!\com\lineage\server\datatables\PatternPowerTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
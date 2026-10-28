/*    */ package com.lineage.server.datatables;
/*    */ 
/*    */ import com.lineage.DatabaseFactory;
/*    */ import com.lineage.server.templates.L1PatternConfig;
/*    */ import com.lineage.server.utils.PerformanceTimer;
/*    */ import com.lineage.server.utils.SQLUtil;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class PatternConfigTable {
/* 22 */   private static final Log _log = LogFactory.getLog(PatternConfigTable.class);
/*    */   
/* 23 */   private static final Map<Integer, Map<Integer, L1PatternConfig>> patternLevelMap = new HashMap<>();
/*    */   
/*    */   private static PatternConfigTable patternConfigTable;
/*    */   
/*    */   public static PatternConfigTable get() {
/* 27 */     if (patternConfigTable == null)
/* 28 */       return new PatternConfigTable(); 
/* 30 */     return patternConfigTable;
/*    */   }
/*    */   
/*    */   public static Map<Integer, L1PatternConfig> getTemplate(int patternType) {
/* 41 */     return patternLevelMap.get(Integer.valueOf(patternType));
/*    */   }
/*    */   
/*    */   public void loadConfig() {
/* 48 */     PerformanceTimer timer = new PerformanceTimer();
/* 49 */     Connection con = null;
/* 50 */     PreparedStatement pstm = null;
/* 51 */     ResultSet rs = null;
/*    */     try {
/* 53 */       con = DatabaseFactory.get().getConnection();
/* 54 */       pstm = con.prepareStatement("SELECT `紋樣類型`, `紋樣等級`, `所需貨幣數量`, `幾率`, `圖片編號` FROM `系統_紋樣設定`");
/* 55 */       rs = pstm.executeQuery();
/* 57 */       while (rs.next()) {
/*    */         Map<Integer, L1PatternConfig> levelMap;
/*    */         L1PatternConfig config;
/* 58 */         int patternType = rs.getInt("紋樣類型");
/* 59 */         int patternLevel = rs.getInt("紋樣等級");
/* 60 */         int requireCoin = rs.getInt("所需貨幣數量");
/* 61 */         int chance = rs.getInt("幾率");
/* 62 */         int imgNo = rs.getInt("圖片編號");
/* 66 */         if (patternLevelMap.get(Integer.valueOf(patternType)) == null) {
/* 67 */           levelMap = new HashMap<>();
/* 68 */           config = new L1PatternConfig(requireCoin, chance, imgNo);
/*    */         } else {
/* 70 */           levelMap = patternLevelMap.get(Integer.valueOf(patternType));
/* 71 */           if (levelMap.get(Integer.valueOf(patternLevel)) == null) {
/* 72 */             config = new L1PatternConfig(requireCoin, chance, imgNo);
/*    */           } else {
/* 74 */             config = levelMap.get(Integer.valueOf(patternLevel));
/*    */           } 
/*    */         } 
/* 77 */         levelMap.put(Integer.valueOf(patternLevel), config);
/* 78 */         patternLevelMap.put(Integer.valueOf(patternType), levelMap);
/*    */       } 
/* 81 */     } catch (Exception e) {
/* 82 */       _log.error(e.getLocalizedMessage(), e);
/*    */     } finally {
/* 84 */       SQLUtil.close(rs);
/* 85 */       SQLUtil.close(pstm);
/* 86 */       SQLUtil.close(con);
/*    */     } 
/* 88 */     _log.info("載入系統_紋樣設定: " + patternLevelMap.size() + "(" + timer.get() + "ms)");
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\Server_Game.jar!\com\lineage\server\datatables\PatternConfigTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
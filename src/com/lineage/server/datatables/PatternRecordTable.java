/*     */ package com.lineage.server.datatables;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.templates.L1PatternRecord;
/*     */ import com.lineage.server.utils.PerformanceTimer;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class PatternRecordTable {
/*  23 */   private static final Log _log = LogFactory.getLog(PatternRecordTable.class);
/*     */   
/*  24 */   private static final Map<Integer, Map<Integer, Integer>> patternRecordMap = new HashMap<>();
/*     */   
/*     */   private static PatternRecordTable patternRecordTable;
/*     */   
/*     */   public static PatternRecordTable get() {
/*  28 */     if (patternRecordTable == null)
/*  29 */       return new PatternRecordTable(); 
/*  31 */     return patternRecordTable;
/*     */   }
/*     */   
/*     */   public static Map<Integer, Integer> getTemplate(int charId) {
/*  42 */     return patternRecordMap.get(Integer.valueOf(charId));
/*     */   }
/*     */   
/*     */   public void loadRecord() {
/*  49 */     PerformanceTimer timer = new PerformanceTimer();
/*  50 */     Connection con = null;
/*  51 */     PreparedStatement pstm = null;
/*  52 */     ResultSet rs = null;
/*     */     try {
/*  54 */       con = DatabaseFactory.get().getConnection();
/*  55 */       pstm = con.prepareStatement("SELECT `角色objid`, `紋樣類型`, `紋樣等級` FROM `系統_紋樣紀錄`");
/*  56 */       rs = pstm.executeQuery();
/*  58 */       while (rs.next()) {
/*     */         Map<Integer, Integer> levelMap;
/*  59 */         int charId = rs.getInt("角色objid");
/*  60 */         int patternType = rs.getInt("紋樣類型");
/*  61 */         int patternLevel = rs.getInt("紋樣等級");
/*  64 */         if (patternRecordMap.get(Integer.valueOf(charId)) == null) {
/*  65 */           levelMap = new HashMap<>();
/*     */         } else {
/*  67 */           levelMap = patternRecordMap.get(Integer.valueOf(charId));
/*     */         } 
/*  69 */         levelMap.put(Integer.valueOf(patternType), Integer.valueOf(patternLevel));
/*  70 */         patternRecordMap.put(Integer.valueOf(charId), levelMap);
/*     */       } 
/*  73 */     } catch (Exception e) {
/*  74 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*  76 */       SQLUtil.close(rs);
/*  77 */       SQLUtil.close(pstm);
/*  78 */       SQLUtil.close(con);
/*     */     } 
/*  80 */     _log.info("載入系統_紋樣紀錄: " + patternRecordMap.size() + "(" + timer.get() + "ms)");
/*     */   }
/*     */   
/*     */   public void storeOrUpdateRecord(L1PatternRecord record) {
/*  89 */     Connection con = null;
/*  90 */     PreparedStatement pstm = null;
/*     */     try {
/*  93 */       con = DatabaseFactory.get().getConnection();
/*  94 */       Map<Integer, Integer> levelMap = patternRecordMap.get(Integer.valueOf(record.getCharId()));
/*  95 */       if (levelMap == null || levelMap.get(Integer.valueOf(record.getPatternType())) == null) {
/*  96 */         if (levelMap == null)
/*  97 */           levelMap = new HashMap<>(); 
/*  99 */         pstm = con.prepareStatement("INSERT INTO `系統_紋樣紀錄` SET `角色objid` = ?, `紋樣類型` = ?, `紋樣等級` = ?");
/* 100 */         int i = 0;
/* 101 */         pstm.setInt(++i, record.getCharId());
/* 102 */         pstm.setInt(++i, record.getPatternType());
/* 103 */         pstm.setInt(++i, record.getPatternLevel());
/*     */       } else {
/* 105 */         pstm = con.prepareStatement("UPDATE `系統_紋樣紀錄` SET `紋樣等級` = ? WHERE `角色objid` = ? AND `紋樣類型` = ?");
/* 106 */         int i = 0;
/* 107 */         pstm.setInt(++i, record.getPatternLevel());
/* 108 */         pstm.setInt(++i, record.getCharId());
/* 109 */         pstm.setInt(++i, record.getPatternType());
/*     */       } 
/* 111 */       levelMap.put(Integer.valueOf(record.getPatternType()), Integer.valueOf(record.getPatternLevel()));
/* 112 */       patternRecordMap.put(Integer.valueOf(record.getCharId()), levelMap);
/* 113 */       pstm.execute();
/* 115 */     } catch (SQLException e) {
/* 116 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/* 119 */       SQLUtil.close(pstm);
/* 120 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\User\Desktop\Server_Game.jar!\com\lineage\server\datatables\PatternRecordTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
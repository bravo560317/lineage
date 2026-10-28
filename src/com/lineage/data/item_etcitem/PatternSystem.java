 package com.lineage.data.item_etcitem;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.PatternConfigTable;
import com.lineage.server.datatables.PatternPowerTable;
import com.lineage.server.datatables.PatternRecordTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1Item;
import com.lineage.server.templates.L1PatternConfig;
import com.lineage.server.templates.L1PatternPower;
import com.lineage.server.templates.L1PatternRecord;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PatternSystem extends ItemExecutor {
  private static final Log _log = LogFactory.getLog(PatternSystem.class);
  
  private static final Random random = new Random();
  
  public static int PATTERN_ITEM_ID = 240102;
  
  private static PatternSystem patternSystem;
  
  public static PatternSystem get() {
    if (patternSystem == null)
      return new PatternSystem(); 
    return patternSystem;
  }
  
  public static void showHtml(L1PcInstance pc, String typeString) {
    int level;
    L1PatternConfig config;
    Map<Integer, L1PatternConfig> templateConfig;
    int patternType;
    Map<Integer, L1PatternConfig> template;
    L1PatternConfig patternConfig;
    if (pc == null || pc.isDead() || typeString == null)
      return; 
    int type = -1;
    try {
      type = Integer.parseInt(typeString);
    } catch (Exception e) {
      e.printStackTrace();
      _log.error("紋樣系統Action設置有誤，請檢查！！！");
    } 
    if (type < 0)
      return; 
    int baseNum = type;
    if (type >= 5913 && type <= 6098)
      type = 7; 
L1Item patternItem = ItemTable.get().getTemplate(PATTERN_ITEM_ID);

if (patternItem == null) {
    pc.sendPackets(new S_SystemMessage("紋樣貨幣不存在(ID=" + PATTERN_ITEM_ID + ")"));
    return;
}
    Map<Integer, Integer> templateRecord = PatternRecordTable.getTemplate(pc.getId());
    switch (type) {
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
templateConfig = PatternConfigTable.getTemplate(type);

if (templateConfig == null) {
    pc.sendPackets(new S_SystemMessage("紋樣設定錯誤(type=" + type + ")"));
    return;
}
Integer lv = null;

if (templateRecord != null) {
    lv = templateRecord.get(Integer.valueOf(type));
}

if (lv == null) {
    level = 0;
    PatternRecordTable.get().storeOrUpdateRecord(new L1PatternRecord(pc.getId(), type, level));
} else {
    level = lv;
}
        pc.setPatternType(type);
config = templateConfig.get(Integer.valueOf(level));

if (config == null) {
    pc.sendPackets(new S_SystemMessage("紋樣等級資料不存在"));
    return;
}
        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "pn_" + config.getImgNo(), new String[] { "下一級需要 " + patternItem.getName() + " " + config.getRequireCoin() + "個 幾率" + config.getChance() + "%" }));
        break;
      case 7:
        patternType = pc.getPatternType();
        level = ((Integer)templateRecord.get(Integer.valueOf(patternType))).intValue();
        template = PatternConfigTable.getTemplate(patternType);
        config = template.get(Integer.valueOf(level));
        patternConfig = template.get(Integer.valueOf(level + 1));
        if (patternConfig != null) {
          if (pc.getInventory().consumeItem(PATTERN_ITEM_ID, config.getRequireCoin())) {
            int daily = 15;
            int index = 0;
            for (Integer key : template.keySet()) {
              try {
                Thread.sleep((index * daily));
                index++;
              } catch (InterruptedException e) {
                e.printStackTrace();
              } 
              pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "pnr_" + ((L1PatternConfig)template.get(key)).getImgNo()));
            } 
            if (random.nextInt(100) + 1 < config.getChance()) {
              int patternNo = Integer.parseInt(String.valueOf(patternType) + level);
              PatternPowerTable.modifierTemplatePower(pc, patternNo, -1);
              int nextPatternNo = Integer.parseInt(String.valueOf(patternType) + (level + 1));
              PatternPowerTable.modifierTemplatePower(pc, nextPatternNo, 1);
              pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aH紋樣系統 強化成功"));
              PatternRecordTable.get().storeOrUpdateRecord(new L1PatternRecord(pc.getId(), patternType, level + 1));
              pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "pn_" + patternConfig.getImgNo(), new String[] { "下一級需要 " + patternItem.getName() + " " + patternConfig.getRequireCoin() + "個 幾率" + patternConfig.getChance() + "%" }));
              break;
            } 
            pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "pn_" + config.getImgNo(), new String[] { "下一級需要 " + patternItem.getName() + " " + config.getRequireCoin() + "個 幾率" + config.getChance() + "%" }));
            pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aG紋樣系統 強化失敗 請再接再厲~"));
            break;
          } 
          pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\f3紋樣強化貨幣不足!!!"));
          break;
        } 
                 pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "pn_" + config.getImgNo(), new String[] { "下一級需要 " + patternItem.getName() + " " + config.getRequireCoin() + "個 幾率" + config.getChance() + "%" }));
                 pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aD已強化至最高等級，更高等級敬請期待！！"));
                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
        break;
    } 
  }
  
  public static void showPower(L1PcInstance pc, String powerString) {
    if (pc == null || pc.isDead() || powerString == null)
      return; 
    int powerNo = -1;
    try {
      powerNo = Integer.parseInt(powerString);
    } catch (Exception e) {
      e.printStackTrace();
     _log.error("紋樣系統Action設置有誤，請檢查！！！");
    } 
    if (powerNo == -1)
      return; 
    if (powerNo < 1 || powerNo > 6)
      return; 
    Map<Integer, Integer> template = PatternRecordTable.getTemplate(pc.getId());
    if (template == null || template.get(Integer.valueOf(powerNo)) == null) {
      int pattern_level = 0;
      PatternRecordTable.get().storeOrUpdateRecord(new L1PatternRecord(pc.getId(), powerNo, pattern_level));
    } 
    String[] data = new String[33];
    Arrays.fill((Object[])data, "0");
    if (template != null) {
      Integer level = template.get(Integer.valueOf(powerNo));
      if (level != null && level.intValue() > 0) {
        int patternNo = Integer.parseInt(powerString + level);
        L1PatternPower power = PatternPowerTable.get().getTemplate(patternNo);
        if (power != null)
          data = PatternPowerTable.getPowerString(power); 
      } 
      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "pattern_p_s", data));
    } else {
        pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aG尚未激活紋樣系統，繼續加油哦"));
    } 
  }
  
  public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
    if (pc == null || pc.isDead() || item == null)
      return; 
    pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "patternlist"));
  }
  
  public void set_set(String[] set) {
    try {
      PATTERN_ITEM_ID = Integer.parseInt(set[1]);
    } catch (Exception e) {
      e.printStackTrace();
      PATTERN_ITEM_ID = 44070;
      _log.error("紋樣貨幣設置有誤，以將紋樣貨幣ID設置為44070，請檢查");
    } 
  }
}


package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAi;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class Ai_Glass extends ItemExecutor
{
    private static Random _random;
    
    static {
        Ai_Glass._random = new Random();
    }
    
    private Ai_Glass() {
    }
    
    public static ItemExecutor get() {
        return new Ai_Glass();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else {
            if (item.getItem().getItemId() == 95170) {
                if (pc.getnewaicount() == 1) {
                    pc.killSkillEffectTimer(7953);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg3));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg4));
                    pc.setnewaicount(0);
                    int time = ConfigAi.aitimelast + Ai_Glass._random.nextInt(ConfigAi.aitimeran) + 1;
                    pc.setSkillEffect(7950, time * 1000);
                    L1ItemInstance AiITEM1 = pc.getInventory().findItemId(95170);
                    L1ItemInstance AiITEM2 = pc.getInventory().findItemId(95171);
                    L1ItemInstance AiITEM3 = pc.getInventory().findItemId(95172);
                    L1ItemInstance AiITEM4 = pc.getInventory().findItemId(95173);
                    L1ItemInstance AiITEM5 = pc.getInventory().findItemId(95174);
                    L1ItemInstance AiITEM6 = pc.getInventory().findItemId(95175);
                    pc.getInventory().removeItem(AiITEM1);
                    pc.getInventory().removeItem(AiITEM2);
                    pc.getInventory().removeItem(AiITEM3);
                    pc.getInventory().removeItem(AiITEM4);
                    pc.getInventory().removeItem(AiITEM5);
                    pc.getInventory().removeItem(AiITEM6);
                }
                else {
                    pc.setInputError(pc.getInputError() + 1);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                    pc.sendPackets(new S_SkillSound(pc.getId(), ConfigAi.AIeffect));
                    if (pc.getInputError() == 2) {
                        pc.saveInventory();
                        pc.sendPackets(new S_Disconnect());
                    }
                }
            }
            else if (item.getItem().getItemId() == 95171) {
                if (pc.getnewaicount() == 2) {
                    pc.killSkillEffectTimer(7953);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg3));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg4));
                    pc.setnewaicount(0);
                    int time = ConfigAi.aitimelast + Ai_Glass._random.nextInt(ConfigAi.aitimeran) + 1;
                    pc.setSkillEffect(7950, time * 1000);
                    L1ItemInstance AiITEM1 = pc.getInventory().findItemId(95170);
                    L1ItemInstance AiITEM2 = pc.getInventory().findItemId(95171);
                    L1ItemInstance AiITEM3 = pc.getInventory().findItemId(95172);
                    L1ItemInstance AiITEM4 = pc.getInventory().findItemId(95173);
                    L1ItemInstance AiITEM5 = pc.getInventory().findItemId(95174);
                    L1ItemInstance AiITEM6 = pc.getInventory().findItemId(95175);
                    pc.getInventory().removeItem(AiITEM1);
                    pc.getInventory().removeItem(AiITEM2);
                    pc.getInventory().removeItem(AiITEM3);
                    pc.getInventory().removeItem(AiITEM4);
                    pc.getInventory().removeItem(AiITEM5);
                    pc.getInventory().removeItem(AiITEM6);
                }
                else {
                    pc.setInputError(pc.getInputError() + 1);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                    pc.sendPackets(new S_SkillSound(pc.getId(), ConfigAi.AIeffect));
                    if (pc.getInputError() == 2) {
                        pc.saveInventory();
                        pc.sendPackets(new S_Disconnect());
                    }
                }
            }
            else if (item.getItem().getItemId() == 95172) {
                if (pc.getnewaicount() == 3) {
                    pc.killSkillEffectTimer(7953);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg3));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg4));
                    pc.setnewaicount(0);
                    int time = ConfigAi.aitimelast + Ai_Glass._random.nextInt(ConfigAi.aitimeran) + 1;
                    pc.setSkillEffect(7950, time * 1000);
                    L1ItemInstance AiITEM1 = pc.getInventory().findItemId(95170);
                    L1ItemInstance AiITEM2 = pc.getInventory().findItemId(95171);
                    L1ItemInstance AiITEM3 = pc.getInventory().findItemId(95172);
                    L1ItemInstance AiITEM4 = pc.getInventory().findItemId(95173);
                    L1ItemInstance AiITEM5 = pc.getInventory().findItemId(95174);
                    L1ItemInstance AiITEM6 = pc.getInventory().findItemId(95175);
                    pc.getInventory().removeItem(AiITEM1);
                    pc.getInventory().removeItem(AiITEM2);
                    pc.getInventory().removeItem(AiITEM3);
                    pc.getInventory().removeItem(AiITEM4);
                    pc.getInventory().removeItem(AiITEM5);
                    pc.getInventory().removeItem(AiITEM6);
                }
                else {
                    pc.setInputError(pc.getInputError() + 1);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                    pc.sendPackets(new S_SkillSound(pc.getId(), ConfigAi.AIeffect));
                    if (pc.getInputError() == 2) {
                        pc.saveInventory();
                        pc.sendPackets(new S_Disconnect());
                    }
                }
            }
            else if (item.getItem().getItemId() == 95173) {
                if (pc.getnewaicount() == 4) {
                    pc.killSkillEffectTimer(7953);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg3));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg4));
                    pc.setnewaicount(0);
                    int time = ConfigAi.aitimelast + Ai_Glass._random.nextInt(ConfigAi.aitimeran) + 1;
                    pc.setSkillEffect(7950, time * 1000);
                    L1ItemInstance AiITEM1 = pc.getInventory().findItemId(95170);
                    L1ItemInstance AiITEM2 = pc.getInventory().findItemId(95171);
                    L1ItemInstance AiITEM3 = pc.getInventory().findItemId(95172);
                    L1ItemInstance AiITEM4 = pc.getInventory().findItemId(95173);
                    L1ItemInstance AiITEM5 = pc.getInventory().findItemId(95174);
                    L1ItemInstance AiITEM6 = pc.getInventory().findItemId(95175);
                    pc.getInventory().removeItem(AiITEM1);
                    pc.getInventory().removeItem(AiITEM2);
                    pc.getInventory().removeItem(AiITEM3);
                    pc.getInventory().removeItem(AiITEM4);
                    pc.getInventory().removeItem(AiITEM5);
                    pc.getInventory().removeItem(AiITEM6);
                }
                else {
                    pc.setInputError(pc.getInputError() + 1);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                    pc.sendPackets(new S_SkillSound(pc.getId(), ConfigAi.AIeffect));
                    if (pc.getInputError() == 2) {
                        pc.saveInventory();
                        pc.sendPackets(new S_Disconnect());
                    }
                }
            }
            else if (item.getItem().getItemId() == 95174) {
                if (pc.getnewaicount() == 5) {
                    pc.killSkillEffectTimer(7953);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg3));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg4));
                    pc.setnewaicount(0);
                    int time = ConfigAi.aitimelast + Ai_Glass._random.nextInt(ConfigAi.aitimeran) + 1;
                    pc.setSkillEffect(7950, time * 1000);
                    L1ItemInstance AiITEM1 = pc.getInventory().findItemId(95170);
                    L1ItemInstance AiITEM2 = pc.getInventory().findItemId(95171);
                    L1ItemInstance AiITEM3 = pc.getInventory().findItemId(95172);
                    L1ItemInstance AiITEM4 = pc.getInventory().findItemId(95173);
                    L1ItemInstance AiITEM5 = pc.getInventory().findItemId(95174);
                    L1ItemInstance AiITEM6 = pc.getInventory().findItemId(95175);
                    pc.getInventory().removeItem(AiITEM1);
                    pc.getInventory().removeItem(AiITEM2);
                    pc.getInventory().removeItem(AiITEM3);
                    pc.getInventory().removeItem(AiITEM4);
                    pc.getInventory().removeItem(AiITEM5);
                    pc.getInventory().removeItem(AiITEM6);
                }
                else {
                    pc.setInputError(pc.getInputError() + 1);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                    pc.sendPackets(new S_SkillSound(pc.getId(), ConfigAi.AIeffect));
                    if (pc.getInputError() == 2) {
                        pc.saveInventory();
                        pc.sendPackets(new S_Disconnect());
                    }
                }
            }
            else if (item.getItem().getItemId() == 95175) {
                if (pc.getnewaicount() == 6) {
                    pc.killSkillEffectTimer(7953);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg3));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg4));
                    pc.setnewaicount(0);
                    int time = ConfigAi.aitimelast + Ai_Glass._random.nextInt(ConfigAi.aitimeran) + 1;
                    pc.setSkillEffect(7950, time * 1000);
                    L1ItemInstance AiITEM1 = pc.getInventory().findItemId(95170);
                    L1ItemInstance AiITEM2 = pc.getInventory().findItemId(95171);
                    L1ItemInstance AiITEM3 = pc.getInventory().findItemId(95172);
                    L1ItemInstance AiITEM4 = pc.getInventory().findItemId(95173);
                    L1ItemInstance AiITEM5 = pc.getInventory().findItemId(95174);
                    L1ItemInstance AiITEM6 = pc.getInventory().findItemId(95175);
                    pc.getInventory().removeItem(AiITEM1);
                    pc.getInventory().removeItem(AiITEM2);
                    pc.getInventory().removeItem(AiITEM3);
                    pc.getInventory().removeItem(AiITEM4);
                    pc.getInventory().removeItem(AiITEM5);
                    pc.getInventory().removeItem(AiITEM6);
                }
                else {
                    pc.setInputError(pc.getInputError() + 1);
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                    pc.sendPackets(new S_SkillSound(pc.getId(), ConfigAi.AIeffect));
                    if (pc.getInputError() == 2) {
                        pc.saveInventory();
                        pc.sendPackets(new S_Disconnect());
                    }
                }
            }
            return;
        }
    }
}

package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_GmMessage extends ServerBasePacket
{
    private static final String AM = "\\aG[%s] \\aD%s: %s";
    private static final String[] TYPES;
    private static final String LOGIN = "\\aE[%s][%s](%s) 登錄。";
    private static final String SYS = "\\aD[系統] %s%s";
    
    static {
        TYPES = new String[] { "王族", "騎士", "妖精", "法師", "黑暗妖精", "龍騎士", "幻術師", "管理員GM" };
    }
    
    public S_GmMessage(L1PcInstance pc, L1PcInstance target, int type, String text) {
        String t = null;
        switch (type) {
            case 0: {
                t = "一般";
                break;
            }
            case 1: {
                t = "私密";
                break;
            }
            case 2: {
                t = "大喊";
                break;
            }
            case 3: {
                t = "全體";
                return;
            }
            case 4: {
                t = "血盟";
                break;
            }
            case 11:
            case 14: {
                t = "隊伍";
                break;
            }
            case 12: {
                t = "交易";
                return;
            }
            case 13: {
                t = "聯盟";
                break;
            }
            default: {
                t = "未知";
                return;
            }
        }
        String to = (target == null) ? pc.getName() : (pc.getName() + "->" + target.getName());
        this.writeC(243);
        this.writeC(9);
        this.writeS(String.format("\\aG[%s] \\aD%s: %s", t, to, text));
    }
    
    public S_GmMessage(L1PcInstance pc) {
        String type = (pc.isGm() || pc.isMonitor()) ? S_GmMessage.TYPES[7] : S_GmMessage.TYPES[pc.getType()];
        this.writeC(243);
        this.writeC(9);
        this.writeS(String.format("\\aE[%s][%s](%s) 登錄。", pc.getName(), type, pc.getNetConnection().getIp()));
    }
    
    public S_GmMessage(String text, String CLR) {
        this.writeC(243);
        this.writeC(9);
        this.writeS(String.format("\\aD[系統] %s%s", CLR, text));
    }
    
    @Override
    public final byte[] getContent() {
        return this.getBytes();
    }
}

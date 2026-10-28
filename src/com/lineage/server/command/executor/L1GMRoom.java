package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1GMRoom implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1GMRoom.class);
    }
    
    private L1GMRoom() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1GMRoom();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            int roomNo = Integer.parseInt(arg);
            switch (roomNo) {
                case 1: {
                    L1Teleport.teleport(pc, 32737, 32796, (short)99, 5, false);
                    break;
                }
                case 2: {
                    L1Teleport.teleport(pc, 32734, 32799, (short)17100, 5, false);
                    break;
                }
                case 3: {
                    L1Teleport.teleport(pc, 32644, 32955, (short)0, 5, false);
                    break;
                }
                case 4: {
                    L1Teleport.teleport(pc, 33429, 32814, (short)4, 5, false);
                    break;
                }
                case 5: {
                    L1Teleport.teleport(pc, 32894, 32535, (short)300, 5, false);
                    break;
                }
                case 6: {
                    L1Teleport.teleport(pc, 32679, 33169, (short)4, 5, false);
                    break;
                }
                case 7: {
                    L1Teleport.teleport(pc, 32863, 32936, (short)630, 5, false);
                    break;
                }
                case 8: {
                    L1Teleport.teleport(pc, 32734, 32802, (short)360, 5, false);
                    break;
                }
                case 9: {
                    L1Teleport.teleport(pc, 32737, 32789, (short)997, 5, false);
                    break;
                }
                case 10: {
                    L1Teleport.teleport(pc, 32959, 32874, (short)68, 5, false);
                    break;
                }
                case 11: {
                    L1Teleport.teleport(pc, 32707, 32846, (short)9000, 5, false);
                    break;
                }
                default: {
                    L1Teleport.teleport(pc, 32863, 32936, (short)630, 5, false);
                    break;
                }
            }
        }
        catch (Exception e) {
            L1GMRoom._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}

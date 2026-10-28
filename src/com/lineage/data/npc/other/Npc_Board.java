package com.lineage.data.npc.other;

import com.lineage.server.templates.L1Board;
import com.lineage.server.serverpackets.S_CloseList;
import java.text.SimpleDateFormat;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_BoardRead;
import com.lineage.server.datatables.lock.BoardReading;
import com.lineage.server.serverpackets.S_Board;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Board extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Board.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Board();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_Board(npc));
        }
        catch (Exception e) {
            Npc_Board._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            boolean isCloseList = false;
            if (cmd.equalsIgnoreCase("n")) {
                L1Board boardInfo = BoardReading.get().getBoardTable((int)amount);
                if (boardInfo != null) {
                    pc.sendPackets(new S_Board(npc, (int)amount));
                }
            }
            else if (cmd.equalsIgnoreCase("r")) {
                L1Board boardInfo = BoardReading.get().getBoardTable((int)amount);
                if (boardInfo != null) {
                    pc.sendPackets(new S_BoardRead((int)amount));
                }
                else {
                    pc.sendPackets(new S_ServerMessage(1243));
                }
            }
            else if (cmd.equalsIgnoreCase("d")) {
                L1Board boardInfo = BoardReading.get().getBoardTable((int)amount);
                if (boardInfo != null) {
                    BoardReading.get().deleteTopic((int)amount);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(1243));
                }
            }
            else if (cmd.equalsIgnoreCase("w")) {
                String title = pc.get_board_title();
                String content = pc.get_board_content();
                if (pc.getInventory().consumeItem(40308, 300L)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    BoardReading.get().writeTopic(pc, sdf.format((Long)System.currentTimeMillis()), title, content);
                    pc.set_board_title(null);
                    pc.set_board_content(null);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(189));
                }
            }
            if (isCloseList) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        catch (Exception e) {
            Npc_Board._log.error(e.getLocalizedMessage(), e);
        }
    }
}

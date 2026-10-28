package com.lineage.server.command;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.lineage.server.templates.L1Command;
import com.lineage.server.command.executor.L1CommandExecutor;
import com.lineage.server.datatables.CommandsTable;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_HelpMessage;
import com.lineage.server.world.World;
import com.lineage.config.Config;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class GMCommands {

    private static final Log _log;
    private static GMCommands _instance;
    private static Map<Integer, String> _lastCommands;

    static {
        _log = LogFactory.getLog(GMCommands.class);
        _lastCommands = new HashMap<>();
    }

    private GMCommands() {
    }

    public static GMCommands getInstance() {
        if (_instance == null) {
            _instance = new GMCommands();
        }
        return _instance;
    }

    private String complementClassName(String className) {
        if (className.contains(".")) {
            return className;
        } else {
            return "com.lineage.server.command.executor." + className;
        }
    }

    private void executeDatabaseCommand(String cmd, String arg) {
        try {
            if (Config.ISUBUNTU && cmd.equalsIgnoreCase("sudo")) {
                _log.info("******Linux 系統命令執行**************************");
                ubuntuCommand(cmd + " " + arg);
                _log.info("******Linux 系統命令完成**************************");
                return;
            } else if (cmd.equalsIgnoreCase("c")) {
                _log.info("系統公告: " + arg);
                World.get().broadcastPacketToAll(new S_HelpMessage(arg));
                RecordTable.get().recordeCmd("黑盒", cmd, arg, null);
                return;
            } else if (cmd.equalsIgnoreCase("debug")) {
                if (Config.DEBUG) {
                    _log.info("終止除錯模式!!");
                    Config.DEBUG = false;
                } else {
                    _log.info("啟用除錯模式!!");
                    Config.DEBUG = true;
                }
                return;
            } else {
                L1Command command = CommandsTable.get().get(cmd.toLowerCase());
                if (command == null) {
                    _log.error("指令異常: 沒有這個命令(" + cmd.toLowerCase() + ")");
                    return;
                } else if (!command.isSystem()) {
                    _log.error("指令異常: 這個命令必須進入遊戲才能執行(" + cmd.toLowerCase() + ")");
                    return;
                } else {
                    Class<?> cls = Class.forName(complementClassName(command.getExecutorClassName()));
                    L1CommandExecutor exe = (L1CommandExecutor) cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                    exe.execute(null, cmd.toLowerCase(), arg.toLowerCase());
                    RecordTable.get().recordeCmd("黑盒", cmd, arg, null);
                }
            }
        } catch (Exception e) {
            _log.error("管理者指令異常!", e);
        }
    }

    private void ubuntuCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                _log.info("Linux 系統命令執行: " + line);
            }
        } catch (IOException e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 啟動階段、非遊戲內系統呼叫使用
     */
    public void handleCommands(String cmdLine) {
        // ✅ 防呆處理：避免 null 或空字串造成 NPE
        if (cmdLine == null || cmdLine.trim().isEmpty()) {
            _log.error("系統命令空白或為 null，已忽略");
            return;
        }

        StringTokenizer token = new StringTokenizer(cmdLine);
        String cmd;
        try {
            cmd = token.nextToken();
        } catch (Exception e) {
            _log.error("系統命令空白!");
            return;
        }

        String param = "";
        while (token.hasMoreTokens()) {
            param = param + token.nextToken() + ' ';
        }
        param = param.trim();
        executeDatabaseCommand(cmd, param);
    }

    private boolean executeDatabaseCommand(L1PcInstance pc, String cmd, String arg) {
        try {
            L1Command command = CommandsTable.get().get(cmd.toLowerCase());
            if (command == null) {
                return false;
            } else if (pc.getAccessLevel() < command.getLevel()) {
                pc.sendPackets(new S_ServerMessage(74, "指令 " + cmd));
                return true;
            } else {
                Class<?> cls = Class.forName(complementClassName(command.getExecutorClassName()));
                L1CommandExecutor exe = (L1CommandExecutor) cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                exe.execute(pc, cmd.toLowerCase(), arg.toLowerCase());
                if (pc.getAccessLevel() > 0) {
                    RecordTable.get().recordeCmd(pc.getName(), cmd, arg, pc.getIp());
                }
                return true;
            }
        } catch (Exception e) {
            _log.error("管理者指令異常!", e);
            return false;
        }
    }

    /**
     * GM 在遊戲中輸入的指令入口
     */
    public void handleCommands(L1PcInstance gm, String cmdLine) {
        // ✅ 防呆處理：避免 null 或空字串造成 NPE
        if (cmdLine == null || cmdLine.trim().isEmpty()) {
            if (gm != null) {
                gm.sendPackets(new S_ServerMessage(261)); // 無效指令
            }
            _log.error("GM 指令空白或為 null，已忽略");
            return;
        }

        StringTokenizer token = new StringTokenizer(cmdLine);
        String cmd;
        try {
            cmd = token.nextToken();
        } catch (Exception e) {
            _log.error("管理者指令空白!");
            return;
        }

        String param = "";
        while (token.hasMoreTokens()) {
            param = param + token.nextToken() + ' ';
        }
        param = param.trim();

        if (executeDatabaseCommand(gm, cmd, param)) {
            if (!cmd.equalsIgnoreCase("r")) {
                _lastCommands.put(gm.getId(), cmdLine);
            }
            return;
        } else if (cmd.equalsIgnoreCase("r")) {
            if (!_lastCommands.containsKey(gm.getId())) {
                gm.sendPackets(new S_ServerMessage(261));
                return;
            } else {
                redo(gm, param);
                return;
            }
        } else {
            gm.sendPackets(new S_ServerMessage(329, cmd));
            return;
        }
    }

    private void redo(L1PcInstance pc, String arg) {
        try {
            String lastCmd = _lastCommands.get(pc.getId());
            if (arg.isEmpty()) {
                pc.sendPackets(new S_ServerMessage(166, "指令 " + lastCmd + " 重複執行"));
                handleCommands(pc, lastCmd);
            } else {
                StringTokenizer token = new StringTokenizer(lastCmd);
                String cmd = token.nextToken() + " " + arg;
                pc.sendPackets(new S_ServerMessage(166, "指令 " + lastCmd + " 紀錄"));
                handleCommands(pc, cmd);
            }
        } catch (Exception e) {
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}

package com.lineage.config;

import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.world.World;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ConfigBoxMsg
{
    private static final Log _log;
    private static final Map<Integer, String> _box_msg_list;
    private static final Random _random;
    public static boolean ISMSG;
    private static final String _box_text = "./config/box_desc.txt";
    
    static {
        _log = LogFactory.getLog(ConfigBoxMsg.class);
        _box_msg_list = new HashMap();
        _random = new Random();
        ConfigBoxMsg.ISMSG = false;
    }
    
    public static void load() throws ConfigErrorException {
        try {
            InputStream is = new FileInputStream(new File("./config/box_desc.txt"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            LineNumberReader lnr = new LineNumberReader(isr);
            boolean isWhile = false;
            int i = 1;
            String desc = null;
            while ((desc = lnr.readLine()) != null) {
                if (!isWhile) {
                    isWhile = true;
                }
                else if (desc.trim().length() != 0 && !desc.startsWith("#")) {
                    if (desc.startsWith("ISMSG")) {
                        desc = desc.replaceAll(" ", "");
                        ConfigBoxMsg.ISMSG = Boolean.parseBoolean(desc.substring(6));
                    }
                    else {
                        ConfigBoxMsg._box_msg_list.put(new Integer(i++), desc);
                    }
                }
                else {
                    continue;
                }
            }
            is.close();
            isr.close();
            lnr.close();
        }
        catch (Exception e) {
            ConfigBoxMsg._log.error("設置檔案遺失: ./config/box_desc.txt");
        }
    }
    
    public static void msg(String string1, String string2) {
        try {
            String msg = ConfigBoxMsg._box_msg_list.get((Integer)(ConfigBoxMsg._random.nextInt(ConfigBoxMsg._box_msg_list.size()) + 1));
            if (msg != null) {
                String out = String.format(msg, string1, string2);
                World.get().broadcastPacketToAll(new S_PacketBoxGree(2, out));
            }
        }
        catch (Exception e) {
            ConfigBoxMsg._log.error(e.getLocalizedMessage(), e);
        }
    }
}

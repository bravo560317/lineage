package com.lineage.config;

import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class ConfigBad
{
    private static final Log _log;
    public static final ArrayList<String> BAD_TEXT_LIST;
    private static final String _bad_text = "./data/badtext.txt";
    
    static {
        _log = LogFactory.getLog(ConfigBad.class);
        BAD_TEXT_LIST = new ArrayList();
    }
    
    public static void load() throws ConfigErrorException {
        try {
            InputStream is = new FileInputStream(new File("./data/badtext.txt"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            LineNumberReader lnr = new LineNumberReader(isr);
            boolean isWhile = false;
            String desc = null;
            while ((desc = lnr.readLine()) != null) {
                if (!isWhile) {
                    isWhile = true;
                }
                else if (desc.trim().length() != 0 && !desc.startsWith("#") && !ConfigBad.BAD_TEXT_LIST.contains(desc)) {
                    ConfigBad.BAD_TEXT_LIST.add(desc);
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
            ConfigBad._log.error("設置檔案遺失: ./data/badtext.txt");
        }
    }
}

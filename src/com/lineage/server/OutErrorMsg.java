package com.lineage.server;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class OutErrorMsg
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(OutErrorMsg.class);
    }
    
    public static void put(String className, String string, Throwable t) {
        OutErrorMsg._log.error(string);
        StringBuilder putInfo = new StringBuilder();
        putInfo.append(string + "###");
        StackTraceElement[] locations = t.getStackTrace();
        StackTraceElement[] array;
        for (int length = (array = locations).length, i = 0; i < length; ++i) {
            StackTraceElement stackTraceElement = array[i];
            putInfo.append("   " + stackTraceElement.toString() + "###");
        }
        overOut(className, putInfo);
    }
    
    public static void put(int oid, String string) {
        OutErrorMsg._log.error(string);
        StringBuilder putInfo = new StringBuilder();
        String nowDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        putInfo.append(string + "/" + nowDate);
        overOut(String.valueOf(oid), putInfo);
    }
    
    private static void overOut(String name, StringBuilder string) {
        try {
            File file = new File("./" + name + ".txt");
            file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file, true);
            OutputStreamWriter printWriter = new OutputStreamWriter(outStream, "utf-8");
            String[] clientStrAry = string.toString().split("###");
            String[] array;
            for (int length = (array = clientStrAry).length, i = 0; i < length; ++i) {
                String txt = array[i];
                printWriter.write(txt);
                printWriter.write("\r\n");
            }
            printWriter.write("\r\n");
            printWriter.flush();
            printWriter.close();
        }
        catch (Exception e) {
            OutErrorMsg._log.error(e.getLocalizedMessage(), e);
        }
    }
}

package com.lineage.server;

import java.util.Date;
import java.io.IOException;
import java.util.logging.Level;
import java.sql.Timestamp;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
public class WriteLogTxt
{
    private static Logger _log;
    
    static {
        WriteLogTxt._log = Logger.getLogger(WriteLogTxt.class.getName());
    }
    
    public static void Recording(String name, String info) {
        try {
            SimpleDateFormat sdfmt = new SimpleDateFormat("yyyy-MM-dd");
            Date d = Calendar.getInstance().getTime();
            String date = " " + sdfmt.format(d);
            String path = "玩家紀錄/搶莊紀錄" + date;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(path + "/" + name + date + ".txt", true);
            fos.write((info + " 時間：" + new Timestamp(System.currentTimeMillis()) + "\r\n").getBytes());
            fos.close();
        }
        catch (IOException e) {
            WriteLogTxt._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }
}

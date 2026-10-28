package com.lineage.list;

import java.net.URLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class L1Karma_Pc
{
    public static void main_check(long h) {
        String webUrl2 = "http://www.yahoo.com.tw";
        if (getWebsiteDatetime(webUrl2) > h) {
            System.exit(-1);
        }
    }
    
    private static long getWebsiteDatetime(String webUrl) {
        long h = 0L;
        try {
            URL url = new URL(webUrl);
            URLConnection uc = url.openConnection();
            uc.connect();
            long ld = uc.getDate();
            long c = System.currentTimeMillis();
            h = (ld - c) / 3600000L;
            if (h < 0L) {
                h = 0L - h;
            }
            return h;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return h;
    }
}

package com.lineage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.util.Random;
import java.util.ArrayList;

public class Itemup
{
    private static ArrayList aData17;
    private static Itemup _instance;
    private static boolean NO_MORE_GET_DATA17;
    private static Random _random;
    public static final String TOKEN = ",";
    
    static {
        Itemup.aData17 = new ArrayList();
        Itemup.NO_MORE_GET_DATA17 = false;
        Itemup._random = new Random();
    }
    
    public static Itemup get() {
        if (Itemup._instance == null) {
            Itemup._instance = new Itemup();
        }
        return Itemup._instance;
    }
    
    public static void forresolvent(String ip1) {
        ArrayList aTempData = null;
        boolean isGet = false;
        int[] iTemp = (int[])null;
        if (!Itemup.NO_MORE_GET_DATA17) {
            Itemup.NO_MORE_GET_DATA17 = true;
            getData17();
        }
        int i = 0;
        while (i < Itemup.aData17.size()) {
            aTempData = (ArrayList)Itemup.aData17.get(i);
            if ((String)aTempData.get(0) != ip1 && (Integer)aTempData.get(1) != 1 && (Integer)aTempData.get(3) != 3800) {
                ++i;
            }
            else {
                isGet = ((String)aTempData.get(0) == ip1 && (Integer)aTempData.get(1) == 1 && (Integer)aTempData.get(3) == 3800);
                if (!isGet) {
                    System.out.println("請聯繫管理員作者進行開通版本驗證");
                    System.out.println("並提供系統顯示IP與PORT給作者進行開通即可");
                    System.out.println("LINE: @1652ffffd");
                    System.out.println("5秒後自動嘗試重新連線測試");
                    try {
                        Thread.sleep(5000L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(-1);
                    return;
                }
                else if (isGet) {
                    System.out.println("[伺服器版本-驗證成功]");
                    return;
                }
                else {
                    break;
                }
            }
        }
    }
    
    private static void getData17() {
        Connection con = null;
        try {
            con = DatabaseFactoryIp.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM 版本驗證");
            ArrayList aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, rset.getString("ip"));
                    aReturn.add(1, new Integer(rset.getInt("安全鎖")));
                    aReturn.add(2, new Integer(rset.getInt("端口")));
                    aReturn.add(3, new Integer(rset.getInt("版本編號")));
                    Itemup.aData17.add(aReturn);
                }
            }
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        }
        catch (Exception ex) {}
    }
}

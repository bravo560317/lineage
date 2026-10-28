package com.add;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;

public class L1Ranking
{
    public static String[] All() throws SQLException {
        String no1 = "空缺";
        String no2 = "空缺";
        String no3 = "空缺";
        String no4 = "空缺";
        String no5 = "空缺";
        String no6 = "";
        String no7 = "";
        String no8 = "";
        String no9 = "";
        String no10 = "";
        String no11 = "";
        String no12 = "";
        String no13 = "";
        String no14 = "";
        String no15 = "";
        String no16 = "空缺";
        String no17 = "空缺";
        String no18 = "空缺";
        String no19 = "空缺";
        String no20 = "空缺";
        String no21 = "";
        String no22 = "";
        String no23 = "";
        String no24 = "";
        String no25 = "";
        String no26 = "";
        String no27 = "";
        String no28 = "";
        String no29 = "";
        String no30 = "";
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        PreparedStatement pstm5 = null;
        PreparedStatement pstm6 = null;
        PreparedStatement pstm7 = null;
        PreparedStatement pstm8 = null;
        PreparedStatement pstm9 = null;
        PreparedStatement pstm10 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        ResultSet rs10 = null;
        Connection con = null;
        String[] levelresult = null;
        con = DatabaseFactory.get().getConnection();
        pstm1 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 0,1;");
        pstm2 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 1,1;");
        pstm3 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 2,1;");
        pstm4 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 3,1;");
        pstm5 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 4,1;");
        pstm6 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 5,1;");
        pstm7 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 6,1;");
        pstm8 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 7,1;");
        pstm9 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 8,1;");
        pstm10 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0  order by (logpcpower*1800000000+exp) DESC limit 9,1;");
        rs1 = pstm1.executeQuery();
        if (rs1.next()) {
            no1 = rs1.getString("char_name");
            no6 = rs1.getString("level");
            no11 = rs1.getString("logpcpower");
        }
        rs2 = pstm2.executeQuery();
        if (rs2.next()) {
            no2 = rs2.getString("char_name");
            no7 = rs2.getString("level");
            no12 = rs2.getString("logpcpower");
        }
        rs3 = pstm3.executeQuery();
        if (rs3.next()) {
            no3 = rs3.getString("char_name");
            no8 = rs3.getString("level");
            no13 = rs3.getString("logpcpower");
        }
        rs4 = pstm4.executeQuery();
        if (rs4.next()) {
            no4 = rs4.getString("char_name");
            no9 = rs4.getString("level");
            no14 = rs4.getString("logpcpower");
        }
        rs5 = pstm5.executeQuery();
        if (rs5.next()) {
            no5 = rs5.getString("char_name");
            no10 = rs5.getString("level");
            no15 = rs5.getString("logpcpower");
        }
        rs6 = pstm6.executeQuery();
        if (rs6.next()) {
            no16 = rs6.getString("char_name");
            no21 = rs6.getString("level");
            no26 = rs6.getString("logpcpower");
        }
        rs7 = pstm7.executeQuery();
        if (rs7.next()) {
            no17 = rs7.getString("char_name");
            no22 = rs7.getString("level");
            no27 = rs7.getString("logpcpower");
        }
        rs8 = pstm8.executeQuery();
        if (rs8.next()) {
            no18 = rs8.getString("char_name");
            no23 = rs8.getString("level");
            no28 = rs8.getString("logpcpower");
        }
        rs9 = pstm9.executeQuery();
        if (rs9.next()) {
            no19 = rs9.getString("char_name");
            no24 = rs9.getString("level");
            no29 = rs9.getString("logpcpower");
        }
        rs10 = pstm10.executeQuery();
        if (rs10.next()) {
            no20 = rs10.getString("char_name");
            no25 = rs10.getString("level");
            no30 = rs10.getString("logpcpower");
        }
        levelresult = new String[] { "", no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14, no15, no16, no17, no18, no19, no20, no21, no22, no23, no24, no25, no26, no27, no28, no29, no30 };
        SQLUtil.close(rs1);
        SQLUtil.close(rs2);
        SQLUtil.close(rs3);
        SQLUtil.close(rs4);
        SQLUtil.close(rs5);
        SQLUtil.close(rs6);
        SQLUtil.close(rs7);
        SQLUtil.close(rs8);
        SQLUtil.close(rs9);
        SQLUtil.close(rs10);
        SQLUtil.close(pstm1);
        SQLUtil.close(pstm2);
        SQLUtil.close(pstm3);
        SQLUtil.close(pstm4);
        SQLUtil.close(pstm5);
        SQLUtil.close(pstm6);
        SQLUtil.close(pstm7);
        SQLUtil.close(pstm8);
        SQLUtil.close(pstm9);
        SQLUtil.close(pstm10);
        SQLUtil.close(con);
        return levelresult;
    }
    
    public static String[] Crown() throws SQLException {
        String no1 = "空缺";
        String no2 = "空缺";
        String no3 = "空缺";
        String no4 = "空缺";
        String no5 = "空缺";
        String no6 = "";
        String no7 = "";
        String no8 = "";
        String no9 = "";
        String no10 = "";
        String no11 = "";
        String no12 = "";
        String no13 = "";
        String no14 = "";
        String no15 = "";
        String no16 = "空缺";
        String no17 = "空缺";
        String no18 = "空缺";
        String no19 = "空缺";
        String no20 = "空缺";
        String no21 = "";
        String no22 = "";
        String no23 = "";
        String no24 = "";
        String no25 = "";
        String no26 = "";
        String no27 = "";
        String no28 = "";
        String no29 = "";
        String no30 = "";
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        PreparedStatement pstm5 = null;
        PreparedStatement pstm6 = null;
        PreparedStatement pstm7 = null;
        PreparedStatement pstm8 = null;
        PreparedStatement pstm9 = null;
        PreparedStatement pstm10 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        ResultSet rs10 = null;
        Connection con = null;
        String[] levelresult = null;
        con = DatabaseFactory.get().getConnection();
        pstm1 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 0,1;");
        pstm2 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 1,1;");
        pstm3 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 2,1;");
        pstm4 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 3,1;");
        pstm5 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 4,1;");
        pstm6 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 5,1;");
        pstm7 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 6,1;");
        pstm8 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 7,1;");
        pstm9 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 8,1;");
        pstm10 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 0 || class = 1) order by (logpcpower*1800000000+exp) DESC limit 9,1;");
        rs1 = pstm1.executeQuery();
        if (rs1.next()) {
            no1 = rs1.getString("char_name");
            no6 = rs1.getString("level");
            no11 = rs1.getString("logpcpower");
        }
        rs2 = pstm2.executeQuery();
        if (rs2.next()) {
            no2 = rs2.getString("char_name");
            no7 = rs2.getString("level");
            no12 = rs2.getString("logpcpower");
        }
        rs3 = pstm3.executeQuery();
        if (rs3.next()) {
            no3 = rs3.getString("char_name");
            no8 = rs3.getString("level");
            no13 = rs3.getString("logpcpower");
        }
        rs4 = pstm4.executeQuery();
        if (rs4.next()) {
            no4 = rs4.getString("char_name");
            no9 = rs4.getString("level");
            no14 = rs4.getString("logpcpower");
        }
        rs5 = pstm5.executeQuery();
        if (rs5.next()) {
            no5 = rs5.getString("char_name");
            no10 = rs5.getString("level");
            no15 = rs5.getString("logpcpower");
        }
        rs6 = pstm6.executeQuery();
        if (rs6.next()) {
            no16 = rs6.getString("char_name");
            no21 = rs6.getString("level");
            no26 = rs6.getString("logpcpower");
        }
        rs7 = pstm7.executeQuery();
        if (rs7.next()) {
            no17 = rs7.getString("char_name");
            no22 = rs7.getString("level");
            no27 = rs7.getString("logpcpower");
        }
        rs8 = pstm8.executeQuery();
        if (rs8.next()) {
            no18 = rs8.getString("char_name");
            no23 = rs8.getString("level");
            no28 = rs8.getString("logpcpower");
        }
        rs9 = pstm9.executeQuery();
        if (rs9.next()) {
            no19 = rs9.getString("char_name");
            no24 = rs9.getString("level");
            no29 = rs9.getString("logpcpower");
        }
        rs10 = pstm10.executeQuery();
        if (rs10.next()) {
            no20 = rs10.getString("char_name");
            no25 = rs10.getString("level");
            no30 = rs10.getString("logpcpower");
        }
        levelresult = new String[] { "", no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14, no15, no16, no17, no18, no19, no20, no21, no22, no23, no24, no25, no26, no27, no28, no29, no30 };
        SQLUtil.close(rs1);
        SQLUtil.close(rs2);
        SQLUtil.close(rs3);
        SQLUtil.close(rs4);
        SQLUtil.close(rs5);
        SQLUtil.close(rs6);
        SQLUtil.close(rs7);
        SQLUtil.close(rs8);
        SQLUtil.close(rs9);
        SQLUtil.close(rs10);
        SQLUtil.close(pstm1);
        SQLUtil.close(pstm2);
        SQLUtil.close(pstm3);
        SQLUtil.close(pstm4);
        SQLUtil.close(pstm5);
        SQLUtil.close(pstm6);
        SQLUtil.close(pstm7);
        SQLUtil.close(pstm8);
        SQLUtil.close(pstm9);
        SQLUtil.close(pstm10);
        SQLUtil.close(con);
        return levelresult;
    }
    
    public static String[] Elf() throws SQLException {
        String no1 = "空缺";
        String no2 = "空缺";
        String no3 = "空缺";
        String no4 = "空缺";
        String no5 = "空缺";
        String no6 = "";
        String no7 = "";
        String no8 = "";
        String no9 = "";
        String no10 = "";
        String no11 = "";
        String no12 = "";
        String no13 = "";
        String no14 = "";
        String no15 = "";
        String no16 = "空缺";
        String no17 = "空缺";
        String no18 = "空缺";
        String no19 = "空缺";
        String no20 = "空缺";
        String no21 = "";
        String no22 = "";
        String no23 = "";
        String no24 = "";
        String no25 = "";
        String no26 = "";
        String no27 = "";
        String no28 = "";
        String no29 = "";
        String no30 = "";
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        PreparedStatement pstm5 = null;
        PreparedStatement pstm6 = null;
        PreparedStatement pstm7 = null;
        PreparedStatement pstm8 = null;
        PreparedStatement pstm9 = null;
        PreparedStatement pstm10 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        ResultSet rs10 = null;
        Connection con = null;
        String[] levelresult = null;
        con = DatabaseFactory.get().getConnection();
        pstm1 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 0,1;");
        pstm2 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 1,1;");
        pstm3 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 2,1;");
        pstm4 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 3,1;");
        pstm5 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 4,1;");
        pstm6 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 5,1;");
        pstm7 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 6,1;");
        pstm8 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 7,1;");
        pstm9 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 8,1;");
        pstm10 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 138 || class = 37) order by (logpcpower*1800000000+exp) DESC limit 9,1;");
        rs1 = pstm1.executeQuery();
        if (rs1.next()) {
            no1 = rs1.getString("char_name");
            no6 = rs1.getString("level");
            no11 = rs1.getString("logpcpower");
        }
        rs2 = pstm2.executeQuery();
        if (rs2.next()) {
            no2 = rs2.getString("char_name");
            no7 = rs2.getString("level");
            no12 = rs2.getString("logpcpower");
        }
        rs3 = pstm3.executeQuery();
        if (rs3.next()) {
            no3 = rs3.getString("char_name");
            no8 = rs3.getString("level");
            no13 = rs3.getString("logpcpower");
        }
        rs4 = pstm4.executeQuery();
        if (rs4.next()) {
            no4 = rs4.getString("char_name");
            no9 = rs4.getString("level");
            no14 = rs4.getString("logpcpower");
        }
        rs5 = pstm5.executeQuery();
        if (rs5.next()) {
            no5 = rs5.getString("char_name");
            no10 = rs5.getString("level");
            no15 = rs5.getString("logpcpower");
        }
        rs6 = pstm6.executeQuery();
        if (rs6.next()) {
            no16 = rs6.getString("char_name");
            no21 = rs6.getString("level");
            no26 = rs6.getString("logpcpower");
        }
        rs7 = pstm7.executeQuery();
        if (rs7.next()) {
            no17 = rs7.getString("char_name");
            no22 = rs7.getString("level");
            no27 = rs7.getString("logpcpower");
        }
        rs8 = pstm8.executeQuery();
        if (rs8.next()) {
            no18 = rs8.getString("char_name");
            no23 = rs8.getString("level");
            no28 = rs8.getString("logpcpower");
        }
        rs9 = pstm9.executeQuery();
        if (rs9.next()) {
            no19 = rs9.getString("char_name");
            no24 = rs9.getString("level");
            no29 = rs9.getString("logpcpower");
        }
        rs10 = pstm10.executeQuery();
        if (rs10.next()) {
            no20 = rs10.getString("char_name");
            no25 = rs10.getString("level");
            no30 = rs10.getString("logpcpower");
        }
        levelresult = new String[] { "", no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14, no15, no16, no17, no18, no19, no20, no21, no22, no23, no24, no25, no26, no27, no28, no29, no30 };
        SQLUtil.close(rs1);
        SQLUtil.close(rs2);
        SQLUtil.close(rs3);
        SQLUtil.close(rs4);
        SQLUtil.close(rs5);
        SQLUtil.close(rs6);
        SQLUtil.close(rs7);
        SQLUtil.close(rs8);
        SQLUtil.close(rs9);
        SQLUtil.close(rs10);
        SQLUtil.close(pstm1);
        SQLUtil.close(pstm2);
        SQLUtil.close(pstm3);
        SQLUtil.close(pstm4);
        SQLUtil.close(pstm5);
        SQLUtil.close(pstm6);
        SQLUtil.close(pstm7);
        SQLUtil.close(pstm8);
        SQLUtil.close(pstm9);
        SQLUtil.close(pstm10);
        SQLUtil.close(con);
        return levelresult;
    }
    
    public static String[] Wizard() throws SQLException {
        String no1 = "空缺";
        String no2 = "空缺";
        String no3 = "空缺";
        String no4 = "空缺";
        String no5 = "空缺";
        String no6 = "";
        String no7 = "";
        String no8 = "";
        String no9 = "";
        String no10 = "";
        String no11 = "";
        String no12 = "";
        String no13 = "";
        String no14 = "";
        String no15 = "";
        String no16 = "空缺";
        String no17 = "空缺";
        String no18 = "空缺";
        String no19 = "空缺";
        String no20 = "空缺";
        String no21 = "";
        String no22 = "";
        String no23 = "";
        String no24 = "";
        String no25 = "";
        String no26 = "";
        String no27 = "";
        String no28 = "";
        String no29 = "";
        String no30 = "";
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        PreparedStatement pstm5 = null;
        PreparedStatement pstm6 = null;
        PreparedStatement pstm7 = null;
        PreparedStatement pstm8 = null;
        PreparedStatement pstm9 = null;
        PreparedStatement pstm10 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        ResultSet rs10 = null;
        Connection con = null;
        String[] levelresult = null;
        con = DatabaseFactory.get().getConnection();
        pstm1 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 0,1;");
        pstm2 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 1,1;");
        pstm3 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 2,1;");
        pstm4 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 3,1;");
        pstm5 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 4,1;");
        pstm6 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 5,1;");
        pstm7 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 6,1;");
        pstm8 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 7,1;");
        pstm9 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 8,1;");
        pstm10 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 1186 || class = 734) order by (logpcpower*1800000000+exp) DESC limit 9,1;");
        rs1 = pstm1.executeQuery();
        if (rs1.next()) {
            no1 = rs1.getString("char_name");
            no6 = rs1.getString("level");
            no11 = rs1.getString("logpcpower");
        }
        rs2 = pstm2.executeQuery();
        if (rs2.next()) {
            no2 = rs2.getString("char_name");
            no7 = rs2.getString("level");
            no12 = rs2.getString("logpcpower");
        }
        rs3 = pstm3.executeQuery();
        if (rs3.next()) {
            no3 = rs3.getString("char_name");
            no8 = rs3.getString("level");
            no13 = rs3.getString("logpcpower");
        }
        rs4 = pstm4.executeQuery();
        if (rs4.next()) {
            no4 = rs4.getString("char_name");
            no9 = rs4.getString("level");
            no14 = rs4.getString("logpcpower");
        }
        rs5 = pstm5.executeQuery();
        if (rs5.next()) {
            no5 = rs5.getString("char_name");
            no10 = rs5.getString("level");
            no15 = rs5.getString("logpcpower");
        }
        rs6 = pstm6.executeQuery();
        if (rs6.next()) {
            no16 = rs6.getString("char_name");
            no21 = rs6.getString("level");
            no26 = rs6.getString("logpcpower");
        }
        rs7 = pstm7.executeQuery();
        if (rs7.next()) {
            no17 = rs7.getString("char_name");
            no22 = rs7.getString("level");
            no27 = rs7.getString("logpcpower");
        }
        rs8 = pstm8.executeQuery();
        if (rs8.next()) {
            no18 = rs8.getString("char_name");
            no23 = rs8.getString("level");
            no28 = rs8.getString("logpcpower");
        }
        rs9 = pstm9.executeQuery();
        if (rs9.next()) {
            no19 = rs9.getString("char_name");
            no24 = rs9.getString("level");
            no29 = rs9.getString("logpcpower");
        }
        rs10 = pstm10.executeQuery();
        if (rs10.next()) {
            no20 = rs10.getString("char_name");
            no25 = rs10.getString("level");
            no30 = rs10.getString("logpcpower");
        }
        levelresult = new String[] { "", no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14, no15, no16, no17, no18, no19, no20, no21, no22, no23, no24, no25, no26, no27, no28, no29, no30 };
        SQLUtil.close(rs1);
        SQLUtil.close(rs2);
        SQLUtil.close(rs3);
        SQLUtil.close(rs4);
        SQLUtil.close(rs5);
        SQLUtil.close(rs6);
        SQLUtil.close(rs7);
        SQLUtil.close(rs8);
        SQLUtil.close(rs9);
        SQLUtil.close(rs10);
        SQLUtil.close(pstm1);
        SQLUtil.close(pstm2);
        SQLUtil.close(pstm3);
        SQLUtil.close(pstm4);
        SQLUtil.close(pstm5);
        SQLUtil.close(pstm6);
        SQLUtil.close(pstm7);
        SQLUtil.close(pstm8);
        SQLUtil.close(pstm9);
        SQLUtil.close(pstm10);
        SQLUtil.close(con);
        return levelresult;
    }
    
    public static String[] Knight() throws SQLException {
        String no1 = "空缺";
        String no2 = "空缺";
        String no3 = "空缺";
        String no4 = "空缺";
        String no5 = "空缺";
        String no6 = "";
        String no7 = "";
        String no8 = "";
        String no9 = "";
        String no10 = "";
        String no11 = "";
        String no12 = "";
        String no13 = "";
        String no14 = "";
        String no15 = "";
        String no16 = "空缺";
        String no17 = "空缺";
        String no18 = "空缺";
        String no19 = "空缺";
        String no20 = "空缺";
        String no21 = "";
        String no22 = "";
        String no23 = "";
        String no24 = "";
        String no25 = "";
        String no26 = "";
        String no27 = "";
        String no28 = "";
        String no29 = "";
        String no30 = "";
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        PreparedStatement pstm5 = null;
        PreparedStatement pstm6 = null;
        PreparedStatement pstm7 = null;
        PreparedStatement pstm8 = null;
        PreparedStatement pstm9 = null;
        PreparedStatement pstm10 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        ResultSet rs10 = null;
        Connection con = null;
        String[] levelresult = null;
        con = DatabaseFactory.get().getConnection();
        pstm1 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 0,1;");
        pstm2 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 1,1;");
        pstm3 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 2,1;");
        pstm4 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 3,1;");
        pstm5 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 4,1;");
        pstm6 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 5,1;");
        pstm7 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 6,1;");
        pstm8 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 7,1;");
        pstm9 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 8,1;");
        pstm10 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 61 || class = 48) order by (logpcpower*1800000000+exp) DESC limit 9,1;");
        rs1 = pstm1.executeQuery();
        if (rs1.next()) {
            no1 = rs1.getString("char_name");
            no6 = rs1.getString("level");
            no11 = rs1.getString("logpcpower");
        }
        rs2 = pstm2.executeQuery();
        if (rs2.next()) {
            no2 = rs2.getString("char_name");
            no7 = rs2.getString("level");
            no12 = rs2.getString("logpcpower");
        }
        rs3 = pstm3.executeQuery();
        if (rs3.next()) {
            no3 = rs3.getString("char_name");
            no8 = rs3.getString("level");
            no13 = rs3.getString("logpcpower");
        }
        rs4 = pstm4.executeQuery();
        if (rs4.next()) {
            no4 = rs4.getString("char_name");
            no9 = rs4.getString("level");
            no14 = rs4.getString("logpcpower");
        }
        rs5 = pstm5.executeQuery();
        if (rs5.next()) {
            no5 = rs5.getString("char_name");
            no10 = rs5.getString("level");
            no15 = rs5.getString("logpcpower");
        }
        rs6 = pstm6.executeQuery();
        if (rs6.next()) {
            no16 = rs6.getString("char_name");
            no21 = rs6.getString("level");
            no26 = rs6.getString("logpcpower");
        }
        rs7 = pstm7.executeQuery();
        if (rs7.next()) {
            no17 = rs7.getString("char_name");
            no22 = rs7.getString("level");
            no27 = rs7.getString("logpcpower");
        }
        rs8 = pstm8.executeQuery();
        if (rs8.next()) {
            no18 = rs8.getString("char_name");
            no23 = rs8.getString("level");
            no28 = rs8.getString("logpcpower");
        }
        rs9 = pstm9.executeQuery();
        if (rs9.next()) {
            no19 = rs9.getString("char_name");
            no24 = rs9.getString("level");
            no29 = rs9.getString("logpcpower");
        }
        rs10 = pstm10.executeQuery();
        if (rs10.next()) {
            no20 = rs10.getString("char_name");
            no25 = rs10.getString("level");
            no30 = rs10.getString("logpcpower");
        }
        levelresult = new String[] { "", no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14, no15, no16, no17, no18, no19, no20, no21, no22, no23, no24, no25, no26, no27, no28, no29, no30 };
        SQLUtil.close(rs1);
        SQLUtil.close(rs2);
        SQLUtil.close(rs3);
        SQLUtil.close(rs4);
        SQLUtil.close(rs5);
        SQLUtil.close(rs6);
        SQLUtil.close(rs7);
        SQLUtil.close(rs8);
        SQLUtil.close(rs9);
        SQLUtil.close(rs10);
        SQLUtil.close(pstm1);
        SQLUtil.close(pstm2);
        SQLUtil.close(pstm3);
        SQLUtil.close(pstm4);
        SQLUtil.close(pstm5);
        SQLUtil.close(pstm6);
        SQLUtil.close(pstm7);
        SQLUtil.close(pstm8);
        SQLUtil.close(pstm9);
        SQLUtil.close(pstm10);
        SQLUtil.close(con);
        return levelresult;
    }
    
    public static String[] Darkelf() throws SQLException {
        String no1 = "空缺";
        String no2 = "空缺";
        String no3 = "空缺";
        String no4 = "空缺";
        String no5 = "空缺";
        String no6 = "";
        String no7 = "";
        String no8 = "";
        String no9 = "";
        String no10 = "";
        String no11 = "";
        String no12 = "";
        String no13 = "";
        String no14 = "";
        String no15 = "";
        String no16 = "空缺";
        String no17 = "空缺";
        String no18 = "空缺";
        String no19 = "空缺";
        String no20 = "空缺";
        String no21 = "";
        String no22 = "";
        String no23 = "";
        String no24 = "";
        String no25 = "";
        String no26 = "";
        String no27 = "";
        String no28 = "";
        String no29 = "";
        String no30 = "";
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        PreparedStatement pstm5 = null;
        PreparedStatement pstm6 = null;
        PreparedStatement pstm7 = null;
        PreparedStatement pstm8 = null;
        PreparedStatement pstm9 = null;
        PreparedStatement pstm10 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        ResultSet rs10 = null;
        Connection con = null;
        String[] levelresult = null;
        con = DatabaseFactory.get().getConnection();
        pstm1 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 0,1;");
        pstm2 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 1,1;");
        pstm3 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 2,1;");
        pstm4 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 3,1;");
        pstm5 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 4,1;");
        pstm6 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 5,1;");
        pstm7 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 6,1;");
        pstm8 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 7,1;");
        pstm9 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 8,1;");
        pstm10 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 2786 || class = 2796) order by (logpcpower*1800000000+exp) DESC limit 9,1;");
        rs1 = pstm1.executeQuery();
        if (rs1.next()) {
            no1 = rs1.getString("char_name");
            no6 = rs1.getString("level");
            no11 = rs1.getString("logpcpower");
        }
        rs2 = pstm2.executeQuery();
        if (rs2.next()) {
            no2 = rs2.getString("char_name");
            no7 = rs2.getString("level");
            no12 = rs2.getString("logpcpower");
        }
        rs3 = pstm3.executeQuery();
        if (rs3.next()) {
            no3 = rs3.getString("char_name");
            no8 = rs3.getString("level");
            no13 = rs3.getString("logpcpower");
        }
        rs4 = pstm4.executeQuery();
        if (rs4.next()) {
            no4 = rs4.getString("char_name");
            no9 = rs4.getString("level");
            no14 = rs4.getString("logpcpower");
        }
        rs5 = pstm5.executeQuery();
        if (rs5.next()) {
            no5 = rs5.getString("char_name");
            no10 = rs5.getString("level");
            no15 = rs5.getString("logpcpower");
        }
        rs6 = pstm6.executeQuery();
        if (rs6.next()) {
            no16 = rs6.getString("char_name");
            no21 = rs6.getString("level");
            no26 = rs6.getString("logpcpower");
        }
        rs7 = pstm7.executeQuery();
        if (rs7.next()) {
            no17 = rs7.getString("char_name");
            no22 = rs7.getString("level");
            no27 = rs7.getString("logpcpower");
        }
        rs8 = pstm8.executeQuery();
        if (rs8.next()) {
            no18 = rs8.getString("char_name");
            no23 = rs8.getString("level");
            no28 = rs8.getString("logpcpower");
        }
        rs9 = pstm9.executeQuery();
        if (rs9.next()) {
            no19 = rs9.getString("char_name");
            no24 = rs9.getString("level");
            no29 = rs9.getString("logpcpower");
        }
        rs10 = pstm10.executeQuery();
        if (rs10.next()) {
            no20 = rs10.getString("char_name");
            no25 = rs10.getString("level");
            no30 = rs10.getString("logpcpower");
        }
        levelresult = new String[] { "", no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14, no15, no16, no17, no18, no19, no20, no21, no22, no23, no24, no25, no26, no27, no28, no29, no30 };
        SQLUtil.close(rs1);
        SQLUtil.close(rs2);
        SQLUtil.close(rs3);
        SQLUtil.close(rs4);
        SQLUtil.close(rs5);
        SQLUtil.close(rs6);
        SQLUtil.close(rs7);
        SQLUtil.close(rs8);
        SQLUtil.close(rs9);
        SQLUtil.close(rs10);
        SQLUtil.close(pstm1);
        SQLUtil.close(pstm2);
        SQLUtil.close(pstm3);
        SQLUtil.close(pstm4);
        SQLUtil.close(pstm5);
        SQLUtil.close(pstm6);
        SQLUtil.close(pstm7);
        SQLUtil.close(pstm8);
        SQLUtil.close(pstm9);
        SQLUtil.close(pstm10);
        SQLUtil.close(con);
        return levelresult;
    }
    
    public static String[] Dragon() throws SQLException {
        String no1 = "空缺";
        String no2 = "空缺";
        String no3 = "空缺";
        String no4 = "空缺";
        String no5 = "空缺";
        String no6 = "";
        String no7 = "";
        String no8 = "";
        String no9 = "";
        String no10 = "";
        String no11 = "";
        String no12 = "";
        String no13 = "";
        String no14 = "";
        String no15 = "";
        String no16 = "空缺";
        String no17 = "空缺";
        String no18 = "空缺";
        String no19 = "空缺";
        String no20 = "空缺";
        String no21 = "";
        String no22 = "";
        String no23 = "";
        String no24 = "";
        String no25 = "";
        String no26 = "";
        String no27 = "";
        String no28 = "";
        String no29 = "";
        String no30 = "";
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        PreparedStatement pstm5 = null;
        PreparedStatement pstm6 = null;
        PreparedStatement pstm7 = null;
        PreparedStatement pstm8 = null;
        PreparedStatement pstm9 = null;
        PreparedStatement pstm10 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        ResultSet rs10 = null;
        Connection con = null;
        String[] levelresult = null;
        con = DatabaseFactory.get().getConnection();
        pstm1 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 0,1;");
        pstm2 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 1,1;");
        pstm3 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 2,1;");
        pstm4 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 3,1;");
        pstm5 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 4,1;");
        pstm6 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 5,1;");
        pstm7 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 6,1;");
        pstm8 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 7,1;");
        pstm9 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 8,1;");
        pstm10 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6658 || class = 6661) order by (logpcpower*1800000000+exp) DESC limit 9,1;");
        rs1 = pstm1.executeQuery();
        if (rs1.next()) {
            no1 = rs1.getString("char_name");
            no6 = rs1.getString("level");
            no11 = rs1.getString("logpcpower");
        }
        rs2 = pstm2.executeQuery();
        if (rs2.next()) {
            no2 = rs2.getString("char_name");
            no7 = rs2.getString("level");
            no12 = rs2.getString("logpcpower");
        }
        rs3 = pstm3.executeQuery();
        if (rs3.next()) {
            no3 = rs3.getString("char_name");
            no8 = rs3.getString("level");
            no13 = rs3.getString("logpcpower");
        }
        rs4 = pstm4.executeQuery();
        if (rs4.next()) {
            no4 = rs4.getString("char_name");
            no9 = rs4.getString("level");
            no14 = rs4.getString("logpcpower");
        }
        rs5 = pstm5.executeQuery();
        if (rs5.next()) {
            no5 = rs5.getString("char_name");
            no10 = rs5.getString("level");
            no15 = rs5.getString("logpcpower");
        }
        rs6 = pstm6.executeQuery();
        if (rs6.next()) {
            no16 = rs6.getString("char_name");
            no21 = rs6.getString("level");
            no26 = rs6.getString("logpcpower");
        }
        rs7 = pstm7.executeQuery();
        if (rs7.next()) {
            no17 = rs7.getString("char_name");
            no22 = rs7.getString("level");
            no27 = rs7.getString("logpcpower");
        }
        rs8 = pstm8.executeQuery();
        if (rs8.next()) {
            no18 = rs8.getString("char_name");
            no23 = rs8.getString("level");
            no28 = rs8.getString("logpcpower");
        }
        rs9 = pstm9.executeQuery();
        if (rs9.next()) {
            no19 = rs9.getString("char_name");
            no24 = rs9.getString("level");
            no29 = rs9.getString("logpcpower");
        }
        rs10 = pstm10.executeQuery();
        if (rs10.next()) {
            no20 = rs10.getString("char_name");
            no25 = rs10.getString("level");
            no30 = rs10.getString("logpcpower");
        }
        levelresult = new String[] { "", no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14, no15, no16, no17, no18, no19, no20, no21, no22, no23, no24, no25, no26, no27, no28, no29, no30 };
        SQLUtil.close(rs1);
        SQLUtil.close(rs2);
        SQLUtil.close(rs3);
        SQLUtil.close(rs4);
        SQLUtil.close(rs5);
        SQLUtil.close(rs6);
        SQLUtil.close(rs7);
        SQLUtil.close(rs8);
        SQLUtil.close(rs9);
        SQLUtil.close(rs10);
        SQLUtil.close(pstm1);
        SQLUtil.close(pstm2);
        SQLUtil.close(pstm3);
        SQLUtil.close(pstm4);
        SQLUtil.close(pstm5);
        SQLUtil.close(pstm6);
        SQLUtil.close(pstm7);
        SQLUtil.close(pstm8);
        SQLUtil.close(pstm9);
        SQLUtil.close(pstm10);
        SQLUtil.close(con);
        return levelresult;
    }
    
    public static String[] Illusionist() throws SQLException {
        String no1 = "空缺";
        String no2 = "空缺";
        String no3 = "空缺";
        String no4 = "空缺";
        String no5 = "空缺";
        String no6 = "";
        String no7 = "";
        String no8 = "";
        String no9 = "";
        String no10 = "";
        String no11 = "";
        String no12 = "";
        String no13 = "";
        String no14 = "";
        String no15 = "";
        String no16 = "空缺";
        String no17 = "空缺";
        String no18 = "空缺";
        String no19 = "空缺";
        String no20 = "空缺";
        String no21 = "";
        String no22 = "";
        String no23 = "";
        String no24 = "";
        String no25 = "";
        String no26 = "";
        String no27 = "";
        String no28 = "";
        String no29 = "";
        String no30 = "";
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        PreparedStatement pstm5 = null;
        PreparedStatement pstm6 = null;
        PreparedStatement pstm7 = null;
        PreparedStatement pstm8 = null;
        PreparedStatement pstm9 = null;
        PreparedStatement pstm10 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;
        ResultSet rs9 = null;
        ResultSet rs10 = null;
        Connection con = null;
        String[] levelresult = null;
        con = DatabaseFactory.get().getConnection();
        pstm1 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 0,1;");
        pstm2 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 1,1;");
        pstm3 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 2,1;");
        pstm4 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 3,1;");
        pstm5 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 4,1;");
        pstm6 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 5,1;");
        pstm7 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 6,1;");
        pstm8 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 7,1;");
        pstm9 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 8,1;");
        pstm10 = con.prepareStatement("select char_name,level,logpcpower from characters where accesslevel = 0 && (class = 6671 || class = 6650) order by (logpcpower*1800000000+exp) DESC limit 9,1;");
        rs1 = pstm1.executeQuery();
        if (rs1.next()) {
            no1 = rs1.getString("char_name");
            no6 = rs1.getString("level");
            no11 = rs1.getString("logpcpower");
        }
        rs2 = pstm2.executeQuery();
        if (rs2.next()) {
            no2 = rs2.getString("char_name");
            no7 = rs2.getString("level");
            no12 = rs2.getString("logpcpower");
        }
        rs3 = pstm3.executeQuery();
        if (rs3.next()) {
            no3 = rs3.getString("char_name");
            no8 = rs3.getString("level");
            no13 = rs3.getString("logpcpower");
        }
        rs4 = pstm4.executeQuery();
        if (rs4.next()) {
            no4 = rs4.getString("char_name");
            no9 = rs4.getString("level");
            no14 = rs4.getString("logpcpower");
        }
        rs5 = pstm5.executeQuery();
        if (rs5.next()) {
            no5 = rs5.getString("char_name");
            no10 = rs5.getString("level");
            no15 = rs5.getString("logpcpower");
        }
        rs6 = pstm6.executeQuery();
        if (rs6.next()) {
            no16 = rs6.getString("char_name");
            no21 = rs6.getString("level");
            no26 = rs6.getString("logpcpower");
        }
        rs7 = pstm7.executeQuery();
        if (rs7.next()) {
            no17 = rs7.getString("char_name");
            no22 = rs7.getString("level");
            no27 = rs7.getString("logpcpower");
        }
        rs8 = pstm8.executeQuery();
        if (rs8.next()) {
            no18 = rs8.getString("char_name");
            no23 = rs8.getString("level");
            no28 = rs8.getString("logpcpower");
        }
        rs9 = pstm9.executeQuery();
        if (rs9.next()) {
            no19 = rs9.getString("char_name");
            no24 = rs9.getString("level");
            no29 = rs9.getString("logpcpower");
        }
        rs10 = pstm10.executeQuery();
        if (rs10.next()) {
            no20 = rs10.getString("char_name");
            no25 = rs10.getString("level");
            no30 = rs10.getString("logpcpower");
        }
        levelresult = new String[] { "", no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14, no15, no16, no17, no18, no19, no20, no21, no22, no23, no24, no25, no26, no27, no28, no29, no30 };
        SQLUtil.close(rs1);
        SQLUtil.close(rs2);
        SQLUtil.close(rs3);
        SQLUtil.close(rs4);
        SQLUtil.close(rs5);
        SQLUtil.close(rs6);
        SQLUtil.close(rs7);
        SQLUtil.close(rs8);
        SQLUtil.close(rs9);
        SQLUtil.close(rs10);
        SQLUtil.close(pstm1);
        SQLUtil.close(pstm2);
        SQLUtil.close(pstm3);
        SQLUtil.close(pstm4);
        SQLUtil.close(pstm5);
        SQLUtil.close(pstm6);
        SQLUtil.close(pstm7);
        SQLUtil.close(pstm8);
        SQLUtil.close(pstm9);
        SQLUtil.close(pstm10);
        SQLUtil.close(con);
        return levelresult;
    }
}

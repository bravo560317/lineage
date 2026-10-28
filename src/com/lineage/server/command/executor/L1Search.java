package com.lineage.server.command.executor;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_SystemMessage;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

public class L1Search implements L1CommandExecutor
{
    private static Logger _log;
    
    static {
        L1Search._log = Logger.getLogger(L1Search.class.getName());
    }
    
    private L1Search() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Search();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String type = "";
            String name = "";
            String add = "";
            boolean simpleS = true;
            int itCount = 0;
            if (arg == null) {
                return;
            }
            else {
                while (st.hasMoreTokens()) {
                    if (itCount == 1) {
                        add = "%";
                    }
                    String tempVar = st.nextToken();
                    if (itCount == 0 && (tempVar.equals("防具") || tempVar.equals("武器") || tempVar.equals("道具") || tempVar.equals("變身") || tempVar.equals("NPC"))) {
                        simpleS = false;
                        type = tempVar;
                    }
                    else {
                        name = name + add + tempVar;
                    }
                    ++itCount;
                }
                if (!simpleS) {
                    this.find_object(pc, type, name);
                }
                else {
                    this.find_object(pc, name);
                }
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage("請輸入 .find [防具,武器,道具,變身,NPC] [物件名稱]"));
        }
    }
    
    private void find_object(L1PcInstance pc, String type, String name) {
        try {
            String str1 = null;
            String str2 = null;
            int bless = 0;
            int count = 0;
            Connection con = null;
            con = DatabaseFactory.get().getConnection();
            PreparedStatement statement = null;
            boolean error = false;
            pc.sendPackets(new S_SystemMessage(" "));
            if (type.equals("防具")) {
                statement = con.prepareStatement("SELECT item_id,name,bless FROM armor WHERE name Like '%" + name + "%'");
            }
            else if (type.equals("武器")) {
                statement = con.prepareStatement("SELECT item_id,name,bless FROM weapon WHERE name Like '%" + name + "%'");
            }
            else if (type.equals("道具")) {
                statement = con.prepareStatement("SELECT item_id,name,bless FROM etcitem WHERE name Like '%" + name + "%'");
            }
            else if (type.equals("變身")) {
                statement = con.prepareStatement("SELECT polyid,name FROM polymorphs WHERE name Like '%" + name + "%'");
            }
            else if (type.equals("NPC")) {
                statement = con.prepareStatement("SELECT npcid,name FROM npc WHERE name Like '%" + name + "%'");
            }
            else {
                error = true;
                pc.sendPackets(new S_SystemMessage("請輸入 .find [防具,武器,道具,變身,NPC] [物件名稱]"));
            }
            String blessed = null;
            if (!error) {
                ResultSet rs = statement.executeQuery();
                pc.sendPackets(new S_SystemMessage("正在搜尋符合 '" + name.replace("%", " ") + " ' 的" + type + "名稱..."));
                while (rs.next()) {
                    str1 = rs.getString(1);
                    str2 = rs.getString(2);
                    if (type.equals("防具") || type.equals("武器") || type.equals("道具")) {
                        bless = rs.getInt(3);
                        if (bless == 1) {
                            blessed = "";
                        }
                        else if (bless == 0) {
                            blessed = "\\fR";
                        }
                        else {
                            blessed = "\\fY";
                        }
                        pc.sendPackets(new S_SystemMessage(blessed + "編號: " + str1 + ", " + str2));
                    }
                    else {
                        pc.sendPackets(new S_SystemMessage("編號: " + str1 + ", " + str2));
                    }
                    ++count;
                }
                rs.close();
                statement.close();
                con.close();
                pc.sendPackets(new S_SystemMessage("找到 " + count + " 個物件符合" + type + "類型。"));
            }
        }
        catch (Exception ex) {}
    }
    
    private void find_object(L1PcInstance pc, String name) {
        try {
            String str1 = null;
            String str2 = null;
            int bless = 0;
            String blessed = null;
            Connection con = null;
            con = DatabaseFactory.get().getConnection();
            PreparedStatement statement = null;
            pc.sendPackets(new S_SystemMessage(" "));
            pc.sendPackets(new S_SystemMessage("正在搜尋符合 '" + name.replace("%", " ") + " ' 的物件名稱:"));
            statement = con.prepareStatement("SELECT item_id,name,bless FROM armor WHERE name Like '%" + name + "%'");
            int count1 = 0;
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (count1 == 0) {
                    pc.sendPackets(new S_SystemMessage("防具:"));
                }
                str1 = rs.getString(1);
                str2 = rs.getString(2);
                bless = rs.getInt(3);
                if (bless == 1) {
                    blessed = "";
                }
                else if (bless == 0) {
                    blessed = "\\fR";
                }
                else {
                    blessed = "\\fY";
                }
                pc.sendPackets(new S_SystemMessage(blessed + "編號: " + str1 + ", " + str2));
                ++count1;
            }
            rs.close();
            statement.close();
            statement = con.prepareStatement("SELECT item_id,name,bless FROM weapon WHERE name Like '%" + name + "%'");
            int count2 = 0;
            rs = statement.executeQuery();
            while (rs.next()) {
                if (count2 == 0) {
                    pc.sendPackets(new S_SystemMessage("武器:"));
                }
                str1 = rs.getString(1);
                str2 = rs.getString(2);
                bless = rs.getInt(3);
                if (bless == 1) {
                    blessed = "";
                }
                else if (bless == 0) {
                    blessed = "\\fR";
                }
                else {
                    blessed = "\\fY";
                }
                pc.sendPackets(new S_SystemMessage(blessed + "編號: " + str1 + ", " + str2));
                ++count2;
            }
            rs.close();
            statement.close();
            statement = con.prepareStatement("SELECT item_id,name,bless FROM etcitem WHERE name Like '%" + name + "%'");
            int count3 = 0;
            rs = statement.executeQuery();
            while (rs.next()) {
                if (count3 == 0) {
                    pc.sendPackets(new S_SystemMessage("道具:"));
                }
                str1 = rs.getString(1);
                str2 = rs.getString(2);
                bless = rs.getInt(3);
                if (bless == 1) {
                    blessed = "";
                }
                else if (bless == 0) {
                    blessed = "\\fR";
                }
                else {
                    blessed = "\\fY";
                }
                pc.sendPackets(new S_SystemMessage(blessed + "編號: " + str1 + ", " + str2));
                ++count3;
            }
            rs.close();
            statement.close();
            statement = con.prepareStatement("SELECT polyid,name FROM polymorphs WHERE name Like '%" + name + "%'");
            int count4 = 0;
            rs = statement.executeQuery();
            while (rs.next()) {
                if (count4 == 0) {
                    pc.sendPackets(new S_SystemMessage("變身:"));
                }
                str1 = rs.getString(1);
                str2 = rs.getString(2);
                pc.sendPackets(new S_SystemMessage("編號: " + str1 + ", " + str2));
                ++count4;
            }
            rs.close();
            statement.close();
            statement = con.prepareStatement("SELECT npcid,name FROM npc WHERE name Like '%" + name + "%'");
            int count5 = 0;
            rs = statement.executeQuery();
            while (rs.next()) {
                if (count5 == 0) {
                    pc.sendPackets(new S_SystemMessage("NPC:"));
                }
                str1 = rs.getString(1);
                str2 = rs.getString(2);
                pc.sendPackets(new S_SystemMessage("編號: " + str1 + ", " + str2));
                ++count5;
            }
            rs.close();
            statement.close();
            con.close();
            pc.sendPackets(new S_SystemMessage("搜尋結果:"));
            String found = "";
            if (count1 > 0) {
                found = found + "防具: " + count1 + "、";
            }
            if (count2 > 0) {
                found = found + "武器: " + count2 + "、";
            }
            if (count3 > 0) {
                found = found + "道具: " + count3 + "、";
            }
            if (count4 > 0) {
                found = found + "變身: " + count4 + "、";
            }
            if (count5 > 0) {
                found = found + "NPC: " + count5 + "。";
            }
            if (found.length() > 0) {
                found = found.substring(0, found.length() - 1) + "。";
            }
            else {
                found = "找到 0 個物件";
            }
            pc.sendPackets(new S_SystemMessage(found));
        }
        catch (Exception ex) {}
    }
}

package com.lineage.config;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactoryLogin;

public final class ConfigPack
{
    public static String id;
    public static String set;
    public static String ver;
    
    public static void loadDB() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int size = 0;
        int i = 0;
        try {
            cn = DatabaseFactoryLogin.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `config_pack`");
            rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("ip");
                String set = rs.getString("驗證碼");
                String ver = rs.getString("版本");
                if (id.equalsIgnoreCase("ip")) {
                    ConfigPack.id = set;
                    ++i;
                }
                else if (id.equalsIgnoreCase("set")) {
                    ConfigPack.set = set;
                    ++i;
                }
                else if (id.equalsIgnoreCase("ver")) {
                    ConfigPack.ver = set;
                    ++i;
                }
                ++size;
            }
        }
        catch (SQLException e) {
            return;
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}

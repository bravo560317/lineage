package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.config.Config;
import com.lineage.DatabaseFactoryLogin;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class DeShopChatTable
{
    private static final Log _log;
    private static final ArrayList<String> _chatList;
    private static DeShopChatTable _instance;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(DeShopChatTable.class);
        _chatList = new ArrayList();
        _random = new Random();
    }
    
    public static DeShopChatTable get() {
        if (DeShopChatTable._instance == null) {
            DeShopChatTable._instance = new DeShopChatTable();
        }
        return DeShopChatTable._instance;
    }
    
    public void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `de_shopds_chat`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                String detitle = "";
                if (Config.CLIENT_LANGUAGE == 3) {
                    detitle = rs.getString("chatinfobig5");
                }
                else {
                    detitle = rs.getString("chatinfo");
                }
                DeShopChatTable._chatList.add(detitle);
            }
        }
        catch (SQLException e) {
            DeShopChatTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public String getChat() {
        int index = DeShopChatTable._random.nextInt(DeShopChatTable._chatList.size());
        return DeShopChatTable._chatList.get(index);
    }
}

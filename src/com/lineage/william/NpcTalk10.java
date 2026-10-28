package com.lineage.william;

import java.util.Iterator;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_NpcChatPacket;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.TimerTask;

public class NpcTalk10 extends TimerTask
{
    private static final Logger log;
    private static final ConcurrentHashMap<Integer, ArrayList<NpcTalk10.Chat>> _chatList;
    private static int index;
    private static NpcTalk10 _instance;
    
    static {
        log = Logger.getLogger(NpcTalk10.class.getName());
        _chatList = new ConcurrentHashMap();
    }
    
    private NpcTalk10() {
    }
    
    public static NpcTalk10 get() {
        if (NpcTalk10._instance == null) {
            NpcTalk10._instance = new NpcTalk10();
        }
        return NpcTalk10._instance;
    }
    
    public static void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM npc_talk");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int npcid = rs.getInt("npc_id");
                NpcTalk10.Chat chat = new NpcTalk10.Chat();
                chat.chatType = rs.getByte("shout");
                chat.chatContent = rs.getString("talk");
                chat.chatTime = rs.getInt("sec") * 1000;
                if (NpcTalk10._chatList.containsKey((Integer)npcid)) {
                    (NpcTalk10._chatList.get((Integer)npcid)).add(chat);
                }
                else {
                    ArrayList<NpcTalk10.Chat> fastTable = new ArrayList();
                    fastTable.add(chat);
                    NpcTalk10._chatList.put(npcid, fastTable);
                }
            }
        }
        catch (SQLException e) {
            NpcTalk10.log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs, pstm, con);
        }
        GeneralThreadPool.get().scheduleAtFixedRate(new NpcTalk10(), 0L, 1000L);
    }
    
    public static final void nextIndex() {
        ++NpcTalk10.index;
    }
    
    public static final void startDummyChat(L1NpcInstance npc) {
        ArrayList<NpcTalk10.Chat> chatTable = NpcTalk10._chatList.get((Integer)npc.getNpcId());
        if (chatTable == null) {
            return;
        }
        else {
            NpcTalk10.Chat c = chatTable.get(NpcTalk10.index % chatTable.size());
            long currentTime = System.currentTimeMillis();
            if (currentTime > c.lastTime) {
                c.lastTime = currentTime + (long)c.chatTime;
                String content = c.chatContent;
                byte type = c.chatType;
                npc.broadcastPacketAll(new S_NpcChatPacket(npc, content, (int)type));
            }
            return;
        }
    }
    
    @Override
    public final void run() {
        for (L1Object object : World.get().getAllVisibleObjects().values()) {
            if (object instanceof L1NpcInstance) {
                startDummyChat((L1NpcInstance)object);
            }
        }
    }
    
    private static class Chat
    {
        private byte chatType;
        private String chatContent;
        private int chatTime;
        private long lastTime;
        
        /*
        static void access$1(NpcTalk10.Chat chat, byte chatType) {
            chat.chatType = chatType;
        }
        */
        
        /*
        static void access$2(NpcTalk10.Chat chat, String chatContent) {
            chat.chatContent = chatContent;
        }
        */
        
        /*
        static void access$3(NpcTalk10.Chat chat, int chatTime) {
            chat.chatTime = chatTime;
        }
        */
        
        /*
        static void access$6(NpcTalk10.Chat chat, long lastTime) {
            chat.lastTime = lastTime;
        }
        */
    }
}

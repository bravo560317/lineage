package com.lineage.server.model;

import com.lineage.server.serverpackets.S_CharPacks;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.server.world.WorldClan;
import java.util.Calendar;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_CharSynAck;
import com.lineage.server.serverpackets.S_CharAmount;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1CharList
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1CharList.class);
    }
    
    public L1CharList(ClientExecutor client) {
        try {
            this.deleteCharacter(client);
            int amountOfChars = client.getAccount().get_countCharacters();
            client.out().encrypt(new S_CharAmount(amountOfChars, client));
            client.out().encrypt(new S_CharSynAck(10));
            if (amountOfChars > 0) {
                this.sendCharPacks(client);
            }
            client.out().encrypt(new S_CharSynAck(64));
        }
        catch (Exception e) {
            L1CharList._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void deleteCharacter(ClientExecutor client) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("SELECT * FROM `characters` WHERE `account_name`=? ORDER BY `objid`");
            pstm.setString(1, client.getAccountName());
            rs = pstm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("char_name");
                String clanname = rs.getString("Clanname");
                Timestamp deleteTime = rs.getTimestamp("DeleteTime");
                if (deleteTime != null) {
                    Calendar cal = Calendar.getInstance();
                    long checkDeleteTime = (cal.getTimeInMillis() - deleteTime.getTime()) / 1000L / 3600L;
                    if (checkDeleteTime >= 0L) {
                        L1Clan clan = WorldClan.get().getClan(clanname);
                        if (clan != null) {
                            clan.delMemberName(name);
                        }
                        int countCharacters = client.getAccount().get_countCharacters();
                        client.getAccount().set_countCharacters(countCharacters - 1);
                        CharObjidTable.get().charRemove(name);
                        CharacterTable.get().deleteCharacter(client.getAccountName(), name);
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (Exception e) {
            L1CharList._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
    }
    
    private void sendCharPacks(ClientExecutor client) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("SELECT * FROM `characters` WHERE `account_name`=? ORDER BY `objid`");
            pstm.setString(1, client.getAccountName());
            rs = pstm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("char_name");
                String clanname = rs.getString("Clanname");
                int type = rs.getInt("Type");
                byte sex = rs.getByte("Sex");
                int lawful = rs.getInt("Lawful");
                int currenthp = rs.getInt("CurHp");
                if (currenthp < 1) {
                    currenthp = 1;
                }
                else if (currenthp > 32767) {
                    currenthp = 32767;
                }
                int currentmp = rs.getInt("CurMp");
                if (currentmp < 1) {
                    currentmp = 1;
                }
                else if (currentmp > 32767) {
                    currentmp = 32767;
                }
                int lvl = rs.getInt("level");
                if (lvl < 1) {
                    lvl = 1;
                }
                int ac = rs.getInt("Ac");
                int str = rs.getInt("Str");
                int dex = rs.getInt("Dex");
                int con = rs.getInt("Con");
                int wis = rs.getInt("Wis");
                int cha = rs.getInt("Cha");
                int intel = rs.getInt("Intel");
                Timestamp createtime = rs.getTimestamp("CreateTime");
                SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyyMMdd");
                int time = Integer.parseInt(SimpleDate.format((Long)createtime.getTime()));
                S_CharPacks cpk = new S_CharPacks(name, clanname, type, (int)sex, lawful, currenthp, currentmp, ac, lvl, str, dex, con, wis, cha, intel, time);
                client.out().encrypt(cpk);
            }
        }
        catch (Exception e) {
            L1CharList._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
    }
}

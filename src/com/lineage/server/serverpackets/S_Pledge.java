package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import com.lineage.server.datatables.sql.CharacterTable;
import java.util.ArrayList;
import com.lineage.server.model.L1Clan;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import com.lineage.server.datatables.lock.ClanReading;

public class S_Pledge extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Pledge(int ClanId) {
        this._byte = null;
        L1Clan clan = ClanReading.get().getTemplate(ClanId);
        this.writeC(250);
        this.writeC(167);
        this.writeS(clan.getClanName());
        this.writeS(clan.getLeaderName());
        this.writeD(clan.getEmblemId());
        this.writeD((int)(clan.getFoundDate().getTime() / 1000L));
        try {
            byte[] text = new byte[478];
            Arrays.fill(text, (byte)0);
            int i = 0;
            byte[] bytes;
            for (int length = (bytes = clan.getAnnouncement().getBytes("Big5")).length, j = 0; j < length; ++j) {
                byte b = bytes[j];
                text[i++] = b;
            }
            this.writeByte(text);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public S_Pledge(int page, int current_page, ArrayList<String> list) {
        this._byte = null;
        this.writeC(250);
        this.writeC(170);
        this.writeC(page);
        this.writeC(current_page);
        this.writeC(list.size());
        for (String name : list) {
            if (name == null) {
                continue;
            }
            else {
                try {
                    L1PcInstance clanMember = CharacterTable.get().restoreCharacter(name);
                    this.writeS(clanMember.getName());
                    this.writeC(clanMember.getClanRank());
                    byte[] text = new byte[62];
                    Arrays.fill(text, (byte)0);
                    if (clanMember.getClanMemberNotes() != null) {
                        int i = 0;
                        byte[] bytes;
                        for (int length = (bytes = clanMember.getClanMemberNotes().getBytes("Big5")).length, j = 0; j < length; ++j) {
                            byte b = bytes[j];
                            text[i++] = b;
                        }
                    }
                    this.writeByte(text);
                    this.writeD(clanMember.getClanMemberId());
                    this.writeC(clanMember.getType());
                }
                catch (Exception ex) {}
            }
        }
    }
    
    public S_Pledge(String name, String notes) {
        this._byte = null;
        this.writeC(250);
        this.writeC(169);
        this.writeS(name);
        byte[] text = new byte[62];
        Arrays.fill(text, (byte)0);
        if (notes.length() != 0) {
            int i = 0;
            try {
                byte[] bytes;
                for (int length = (bytes = notes.getBytes("Big5")).length, j = 0; j < length; ++j) {
                    byte b = bytes[j];
                    text[i++] = b;
                }
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        this.writeByte(text);
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

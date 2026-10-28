package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.List;
import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.world.World;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.world.WorldClan;
import java.util.ArrayList;
import com.lineage.server.model.L1ClanMatching;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class S_ClanMatching extends ServerBasePacket
{
    private byte[] _byte;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(S_ClanMatching.class);
    }
    
    public S_ClanMatching(boolean postStatus, String clanname) {
        this._byte = null;
        this.writeC(0);
        this.writeC(postStatus ? 0 : 1);
        this.writeC(0);
        this.writeD(0);
        this.writeC(0);
    }
    
    public S_ClanMatching(L1PcInstance pc, int type, int objid, int htype) {
        this._byte = null;
        try {
            L1Clan clan = null;
            L1ClanMatching cml = L1ClanMatching.getInstance();
            String clanname = null;
            String text = null;
            this.writeC(0);
            this.writeC(type);
            if (type == 2) {
                ArrayList<L1ClanMatching.ClanMatchingList> showcmalist = new ArrayList();
                for (int i = 0; i < cml.getMatchingList().size(); ++i) {
                    clanname = (cml.getMatchingList().get(i))._clanname;
                    if (!pc.getCMAList().contains(clanname)) {
                        showcmalist.add(cml.getMatchingList().get(i));
                    }
                }
                int type2 = 0;
                int size = showcmalist.size();
                this.writeC(0);
                this.writeC(size);
                for (int j = 0; j < size; ++j) {
                    text = (showcmalist.get(j))._text;
                    type2 = (showcmalist.get(j))._type;
                    String clan_name = (showcmalist.get(j))._clanname;
                    clan = WorldClan.get().getClan(clan_name);
                    this.writeD(clan.getClanId());
                    this.writeS(clan.getClanName());
                    this.writeS(clan.getLeaderName());
                    this.writeD(clan.getOnlineMaxUser());
                    this.writeC(type2);
                    if (clan.getHouseId() != 0) {
                        this.writeC(1);
                    }
                    else {
                        this.writeC(0);
                    }
                    boolean inWar = false;
                    List<L1War> warList = WorldWar.get().getWarList();
                    for (L1War war : warList) {
                        if (war.checkClanInWar(clanname)) {
                            inWar = true;
                            break;
                        }
                    }
                    if (inWar) {
                        this.writeC(1);
                    }
                    else {
                        this.writeC(0);
                    }
                    this.writeC(0);
                    this.writeS(text);
                    this.writeD(clan.getEmblemId());
                }
                showcmalist.clear();
                showcmalist = null;
            }
            else if (type == 3) {
                int size2 = pc.getCMAList().size();
                this.writeC(0);
                this.writeC(size2);
                for (int i = 0; i < size2; ++i) {
                    clanname = pc.getCMAList().get(i);
                    clan = WorldClan.get().getClan(clanname);
                    this.writeD(clan.getClanId());
                    this.writeC(0);
                    this.writeD(clan.getClanId());
                    this.writeS(clan.getClanName());
                    this.writeS(clan.getLeaderName());
                    this.writeD(clan.getOnlineMaxUser());
                    this.writeC(cml.getClanMatchingList(clanname)._type);
                    if (clan.getHouseId() != 0) {
                        this.writeC(1);
                    }
                    else {
                        this.writeC(0);
                    }
                    boolean inWar2 = false;
                    List<L1War> warList2 = WorldWar.get().getWarList();
                    for (L1War war2 : warList2) {
                        if (war2.checkClanInWar(clanname)) {
                            inWar2 = true;
                            break;
                        }
                    }
                    if (inWar2) {
                        this.writeC(1);
                    }
                    else {
                        this.writeC(0);
                    }
                    this.writeC(0);
                    this.writeS(cml.getClanMatchingList(clanname)._text);
                    this.writeD(clan.getEmblemId());
                }
            }
            else if (type == 4) {
                if (!cml.isClanMatchingList(pc.getClanname())) {
                    this.writeC(130);
                }
                else {
                    int size2 = pc.getInviteList().size();
                    String username = null;
                    this.writeC(0);
                    this.writeC(2);
                    this.writeC(0);
                    this.writeC(size2);
                    L1PcInstance user = null;
                    for (int j = 0; j < size2; ++j) {
                        username = pc.getInviteList().get(j);
                        user = World.get().getPlayer(username);
                        if (user == null) {
                            try {
                                user = CharacterTable.get().restoreCharacter(username);
                                if (user == null) {
                                    return;
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        this.writeD(user.getId());
                        this.writeC(0);
                        this.writeC(user.getOnlineStatus());
                        this.writeS(username);
                        this.writeC(user.getType());
                        this.writeH(user.getLawful());
                        this.writeC(user.getLevel());
                        this.writeC(1);
                    }
                }
            }
            else if (type == 5 || type == 6) {
                this.writeC(0);
                this.writeD(objid);
                this.writeC(htype);
            }
        }
        catch (Exception e2) {
            S_ClanMatching._log.error(e2.getLocalizedMessage(), e2);
        }
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

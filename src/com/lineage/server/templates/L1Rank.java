package com.lineage.server.templates;

import java.util.List;

public final class L1Rank
{
    private String _partyLeader;
    private final List<String> _partyMember;
    private final int _score;
    
    public L1Rank(String partyLeader, List<String> partyMember, int score) {
        this._partyLeader = partyLeader;
        this._partyMember = partyMember;
        this._score = score;
    }
    
    public final String getPartyLeader() {
        return this._partyLeader;
    }
    
    public final void setPartyLeader(String partyLeader) {
        this._partyLeader = partyLeader;
    }
    
    public final List<String> getPartyMember() {
        return this._partyMember;
    }
    
    public final int getScore() {
        return this._score;
    }
    
    public final int getMemberSize() {
        return this._partyMember.size() + 1;
    }
}

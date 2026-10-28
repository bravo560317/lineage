package com.lineage.william;

import com.lineage.server.model.Instance.L1PcInstance;

public class L1WilliamPolyActid
{
    private int _gfxId;
    private int _ran;
    private int _actid;
    private int _dmg;
    private int _dmg1;
    
    public static void getAddGfxIdOrginal(L1PcInstance pc, int gfxId) {
        L1WilliamPolyActid gfxIdOrginal = PolyActid.getInstance().getTemplate(gfxId);
        if (gfxIdOrginal == null) {
            return;
        }
        else {
            pc.setpolyactidran(gfxIdOrginal.getran());
            pc.setpolyactidactid(gfxIdOrginal.getactid());
            pc.setpolyactiddmg(gfxIdOrginal.getdmg());
            pc.setpolyactiddmg1(gfxIdOrginal.getdmg1());
            return;
        }
    }
    
    public static void getReductionGfxIdOrginal(L1PcInstance pc, int gfxId) {
        L1WilliamPolyActid gfxIdOrginal = PolyActid.getInstance().getTemplate(gfxId);
        if (gfxIdOrginal == null) {
            return;
        }
        else {
            pc.setpolyactidran(0);
            pc.setpolyactidactid(0);
            pc.setpolyactiddmg(0);
            pc.setpolyactiddmg1(0);
            return;
        }
    }
    
    public L1WilliamPolyActid(int gfxId, int ran, int actid, int dmg, int dmg1) {
        this._gfxId = gfxId;
        this._ran = ran;
        this._actid = actid;
        this._dmg = dmg;
        this._dmg1 = dmg1;
    }
    
    public int getGfxId() {
        return this._gfxId;
    }
    
    public int getran() {
        return this._ran;
    }
    
    public int getactid() {
        return this._actid;
    }
    
    public int getdmg() {
        return this._dmg;
    }
    
    public int getdmg1() {
        return this._dmg1;
    }
}

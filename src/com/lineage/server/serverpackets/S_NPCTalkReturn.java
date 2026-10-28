package com.lineage.server.serverpackets;

import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.L1NpcTalkData;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import com.lineage.server.datatables.ItemTable;
import java.util.List;
import com.lineage.server.model.Instance.L1PcInstance;


public class S_NPCTalkReturn extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NPCTalkReturn(int objid, String htmlid, L1PcInstance pc, List<Integer> list) {
        this._byte = null;
        this.writeC(39);
        this.writeD(objid);
        this.writeS(htmlid);
        this.writeH(1);
        this.writeH(11);
        int t = 0;
        for (Integer v : list) {
            ++t;
            L1Item datum = ItemTable.get().getTemplate(v);
            pc.get_otherList().add_sitemList2(t, v);
            this.writeS(datum.getNameId());
        }
        if (list.size() < 11) {
            for (int x = 11 - list.size(), i = 0; i < x; ++i) {
                this.writeS(" ");
            }
        }
    }
    
    public S_NPCTalkReturn(int objid, String htmlid, List<L1ItemInstance> list, L1PcInstance pc) {
        this._byte = null;
        this.writeC(39);
        this.writeD(objid);
        this.writeS(htmlid);
        this.writeH(1);
        this.writeH(11);
        int t = 0;
        for (L1ItemInstance datum : list) {
            ++t;
            pc.get_otherList().add_sitemList(t, datum);
            this.writeS(datum.getViewName());
        }
        if (list.size() < 11) {
            for (int x = 11 - list.size(), i = 0; i < x; ++i) {
                this.writeS(" ");
            }
        }
    }
    
    public S_NPCTalkReturn(L1NpcTalkData npc, int objid, int action, String[] data) {
        this._byte = null;
        String htmlid = "";
        if (action == 1) {
            htmlid = npc.getNormalAction();
        }
        else if (action == 2) {
            htmlid = npc.getCaoticAction();
        }
        else {
            throw new IllegalArgumentException();
        }
        this.buildPacket(objid, htmlid, data);
    }
    
    public S_NPCTalkReturn(L1NpcTalkData npc, int objid, int action) {
        this(npc, objid, action, (String[])null);
    }
    
    public S_NPCTalkReturn(int objid, String htmlid, String[] data) {
        this._byte = null;
        this.buildPacket(objid, htmlid, data);
    }
    
    public S_NPCTalkReturn(int objid, String htmlid) {
        this._byte = null;
        this.buildPacket(objid, htmlid, (String[])null);
    }
    
    public S_NPCTalkReturn(int objid, L1NpcHtml html) {
        this._byte = null;
        this.buildPacket(objid, html.getName(), html.getArgs());
    }
    
    private void buildPacket(int objid, String htmlid, String[] data) {
        this.writeC(39);
        this.writeD(objid);
        this.writeS(htmlid);
        if (data != null && 1 <= data.length) {
            this.writeH(1);
            this.writeH(data.length);
            for (String datum : data) {
                this.writeS(datum);
            }
        }
        else {
            this.writeH(0);
            this.writeH(0);
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

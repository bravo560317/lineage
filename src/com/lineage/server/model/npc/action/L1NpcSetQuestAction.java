package com.lineage.server.model.npc.action;

import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import org.w3c.dom.Element;

public class L1NpcSetQuestAction extends L1NpcXmlAction
{
    private final int _id;
    private final int _step;
    
    public L1NpcSetQuestAction(Element element) {
        super(element);
        this._id = L1NpcXmlParser.parseQuestId(element.getAttribute("Id"));
        this._step = L1NpcXmlParser.parseQuestStep(element.getAttribute("Step"));
        if (this._id == -1 || this._step == -1) {
            throw new IllegalArgumentException();
        }
        else {
            return;
        }
    }
    
    @Override
    public L1NpcHtml execute(String actionName, L1PcInstance pc, L1Object obj, byte[] args) {
        pc.getQuest().set_step(this._id, this._step);
        return null;
    }
    
    @Override
    public void execute(String actionName, String npcid) {
    }
}

package com.lineage.server.model.npc.action;

import java.util.Iterator;
import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import org.w3c.dom.Element;
import java.util.List;

public class L1NpcListedAction extends L1NpcXmlAction
{
    private List<L1NpcAction> _actions;
    
    public L1NpcListedAction(Element element) {
        super(element);
        this._actions = L1NpcXmlParser.listActions(element);
    }
    
    @Override
    public L1NpcHtml execute(String actionName, L1PcInstance pc, L1Object obj, byte[] args) {
        L1NpcHtml result = null;
        for (L1NpcAction action : this._actions) {
            if (!action.acceptsRequest(actionName, pc, obj)) {
                continue;
            }
            else {
                L1NpcHtml r = action.execute(actionName, pc, obj, args);
                if (r != null) {
                    result = r;
                }
                else {
                    continue;
                }
            }
        }
        return result;
    }
    
    @Override
    public L1NpcHtml executeWithAmount(String actionName, L1PcInstance pc, L1Object obj, long amount) {
        L1NpcHtml result = null;
        for (L1NpcAction action : this._actions) {
            if (!action.acceptsRequest(actionName, pc, obj)) {
                continue;
            }
            else {
                L1NpcHtml r = action.executeWithAmount(actionName, pc, obj, amount);
                if (r != null) {
                    result = r;
                }
                else {
                    continue;
                }
            }
        }
        return result;
    }
    
    @Override
    public void execute(String actionName, String npcid) {
        for (L1NpcAction action : this._actions) {
            if (!action.acceptsRequest(actionName, null, null)) {
                continue;
            }
            else {
                action.execute(actionName, npcid);
            }
        }
    }
}

package com.lineage.server.model.npc.action;

import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import org.w3c.dom.NodeList;
import com.lineage.server.utils.IterableElementList;
import java.util.ArrayList;
import org.w3c.dom.Element;

public class L1NpcShowHtmlAction extends L1NpcXmlAction
{
    private final String _htmlId;
    private final String[] _args;
    
    public L1NpcShowHtmlAction(Element element) {
        super(element);
        this._htmlId = element.getAttribute("HtmlId");
        NodeList list = element.getChildNodes();
        ArrayList<String> dataList = new ArrayList();
        for (Element elem : new IterableElementList(list)) {
            if (elem.getNodeName().equalsIgnoreCase("Data")) {
                dataList.add(elem.getAttribute("Value"));
            }
        }
        this._args = (String[])dataList.toArray(new String[dataList.size()]);
    }
    
    @Override
    public L1NpcHtml execute(String actionName, L1PcInstance pc, L1Object obj, byte[] args) {
        return new L1NpcHtml(this._htmlId, this._args);
    }
    
    @Override
    public void execute(String actionName, String npcid) {
    }
}

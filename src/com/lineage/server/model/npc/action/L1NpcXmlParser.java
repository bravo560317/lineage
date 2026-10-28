package com.lineage.server.model.npc.action;

import java.util.Iterator;
import org.w3c.dom.NodeList;
import com.lineage.server.utils.IterableElementList;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import java.util.HashMap;
import java.util.Map;

public class L1NpcXmlParser
{
    private static final Map<String, Integer> _questIds;
    
    static {
        _questIds = new HashMap();
    }
    
    public static List<L1NpcAction> listActions(Element element) {
        List<L1NpcAction> result = new ArrayList();
        NodeList list = element.getChildNodes();
        for (Element elem : new IterableElementList(list)) {
            L1NpcAction action = L1NpcActionFactory.newAction(elem);
            if (action != null) {
                result.add(action);
            }
        }
        return result;
    }
    
    public static Element getFirstChildElementByTagName(Element element, String tagName) {
        IterableElementList list = new IterableElementList(element.getElementsByTagName(tagName));
        Iterator<Element> iterator = list.iterator();
        if (iterator.hasNext()) {
            Element elem = (Element)iterator.next();
            return elem;
        }
        else {
            return null;
        }
    }
    
    public static int getIntAttribute(Element element, String name, int defaultValue) {
        int result = defaultValue;
        try {
            result = Integer.valueOf(element.getAttribute(name));
        }
        catch (NumberFormatException ex) {}
        return result;
    }
    
    public static boolean getBoolAttribute(Element element, String name, boolean defaultValue) {
        boolean result = defaultValue;
        String value = element.getAttribute(name);
        if (!value.equals("")) {
            result = Boolean.valueOf(value);
        }
        return result;
    }
    
    public static int parseQuestId(String questId) {
        if (questId.equals("")) {
            return -1;
        }
        else {
            Integer result = L1NpcXmlParser._questIds.get(questId.toLowerCase());
            if (result == null) {
                throw new IllegalArgumentException();
            }
            else {
                return result;
            }
        }
    }
    
    public static int parseQuestStep(String questStep) {
        if (questStep.equals("")) {
            return -1;
        }
        else if (questStep.equalsIgnoreCase("End")) {
            return 255;
        }
        else {
            return Integer.parseInt(questStep);
        }
    }
}

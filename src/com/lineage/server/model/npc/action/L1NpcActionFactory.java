package com.lineage.server.model.npc.action;

import org.w3c.dom.Element;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.lang.reflect.Constructor;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1NpcActionFactory
{
    private static final Log _log;
    private static Map<String, Constructor<?>> _actions;
    
    static {
        _log = LogFactory.getLog(L1NpcActionFactory.class);
        L1NpcActionFactory._actions = new HashMap();
        try {
            L1NpcActionFactory._actions.put("Action", loadConstructor(L1NpcListedAction.class));
            L1NpcActionFactory._actions.put("MakeItem", loadConstructor(L1NpcMakeItemAction.class));
            L1NpcActionFactory._actions.put("ShowHtml", loadConstructor(L1NpcShowHtmlAction.class));
            L1NpcActionFactory._actions.put("SetQuest", loadConstructor(L1NpcSetQuestAction.class));
            L1NpcActionFactory._actions.put("Teleport", loadConstructor(L1NpcTeleportAction.class));
        }
        catch (NoSuchMethodException e) {
            L1NpcActionFactory._log.error("NpcAction加載失敗", e);
        }
    }
    
    private static Constructor<?> loadConstructor(Class<?> c) throws NoSuchMethodException {
        return c.getConstructor(Element.class);
    }
    
    public static L1NpcAction newAction(Element element) {
        try {
            Constructor<?> con = L1NpcActionFactory._actions.get(element.getNodeName());
            L1NpcAction action = (L1NpcAction)con.newInstance(element);
            return action;
        }
        catch (NullPointerException e) {
            L1NpcActionFactory._log.error("未定義的NPC對話設置", e);
        }
        catch (Exception e2) {
            L1NpcActionFactory._log.error("NpcAction加載失敗", e2);
        }
        return null;
    }
}

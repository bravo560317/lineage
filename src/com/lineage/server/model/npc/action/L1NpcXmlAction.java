package com.lineage.server.model.npc.action;

import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Object;
import java.util.StringTokenizer;
import com.lineage.server.datatables.ExpTable;
import java.util.Arrays;
import org.w3c.dom.Element;
import java.util.HashMap;
import java.util.Map;
import com.lineage.server.utils.RangeInt;

public abstract class L1NpcXmlAction implements L1NpcAction
{
    public String _name;
    public String _npcids;
    private final int[] _npcIds;
    private final RangeInt _level;
    private final int _questId;
    private final int _questStep;
    private final int[] _classes;
    private static final Map<Character, Integer> _charTypes;
    
    static {
        (_charTypes = new HashMap()).put('P', 0);
        L1NpcXmlAction._charTypes.put('K', 1);
        L1NpcXmlAction._charTypes.put('E', 2);
        L1NpcXmlAction._charTypes.put('W', 3);
        L1NpcXmlAction._charTypes.put('D', 4);
        L1NpcXmlAction._charTypes.put('R', 5);
        L1NpcXmlAction._charTypes.put('I', 6);
    }
    
    public L1NpcXmlAction(Element element) {
        this._npcids = null;
        this._name = element.getAttribute("Name");
        this._name = (this._name.equals("") ? null : this._name);
        this._npcids = element.getAttribute("NpcId");
        this._npcIds = this.parseNpcIds(this._npcids);
        this._level = this.parseLevel(element);
        this._questId = L1NpcXmlParser.parseQuestId(element.getAttribute("QuestId"));
        this._questStep = L1NpcXmlParser.parseQuestStep(element.getAttribute("QuestStep"));
        this._classes = this.parseClasses(element);
    }
    
    private int[] parseClasses(Element element) {
        String classes = element.getAttribute("Class").toUpperCase();
        int[] result = new int[classes.length()];
        int idx = 0;
        char[] charArray;
        for (int length = (charArray = classes.toCharArray()).length, i = 0; i < length; ++i) {
            Character cha = charArray[i];
            result[idx++] = L1NpcXmlAction._charTypes.get(cha);
        }
        Arrays.sort(result);
        return result;
    }
    
    private RangeInt parseLevel(Element element) {
        int level = L1NpcXmlParser.getIntAttribute(element, "Level", 0);
        int min = L1NpcXmlParser.getIntAttribute(element, "LevelMin", 1);
        int max = L1NpcXmlParser.getIntAttribute(element, "LevelMax", ExpTable.MAX_LEVEL);
        return (level == 0) ? new RangeInt(min, max) : new RangeInt(level, level);
    }
    
    private int[] parseNpcIds(String npcIds) {
        StringTokenizer tok = new StringTokenizer(npcIds.replace(" ", ""), ",");
        int[] result = new int[tok.countTokens()];
        for (int i = 0; i < result.length; ++i) {
            result[i] = Integer.parseInt(tok.nextToken());
        }
        Arrays.sort(result);
        return result;
    }
    
    private boolean acceptsNpcId(L1Object obj) {
        if (this._npcIds.length > 0) {
            if (!(obj instanceof L1NpcInstance)) {
                return false;
            }
            else {
                int npcId = ((L1NpcInstance)obj).getNpcTemplate().get_npcId();
                if (Arrays.binarySearch(this._npcIds, npcId) < 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean acceptsLevel(int level) {
        return this._level.includes(level);
    }
    
    private boolean acceptsCharType(int type) {
        return this._classes.length <= 0 || Arrays.binarySearch(this._classes, type) >= 0;
    }
    
    private boolean acceptsActionName(String name) {
        return this._name == null || name.equals(this._name);
    }
    
    private boolean acceptsQuest(L1PcInstance pc) {
        if (this._questId == -1) {
            return true;
        }
        else if (this._questStep == -1) {
            return pc.getQuest().get_step(this._questId) > 0;
        }
        else {
            return pc.getQuest().get_step(this._questId) == this._questStep;
        }
    }
    
    @Override
    public boolean acceptsRequest(String actionName, L1PcInstance pc, L1Object obj) {
        return this.acceptsNpcId(obj) && this.acceptsLevel(pc.getLevel()) && this.acceptsQuest(pc) && this.acceptsCharType(pc.getType()) && this.acceptsActionName(actionName);
    }
    
    @Override
    public abstract L1NpcHtml execute(String p0, L1PcInstance p1, L1Object p2, byte[] p3);
    
    @Override
    public abstract void execute(String p0, String p1);
    
    @Override
    public L1NpcHtml executeWithAmount(String actionName, L1PcInstance pc, L1Object obj, long amount) {
        return null;
    }
}

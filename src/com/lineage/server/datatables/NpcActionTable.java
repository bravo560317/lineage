package com.lineage.server.datatables;

import java.util.Iterator;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.FileUtil;
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import com.lineage.server.model.npc.action.L1NpcXmlParser;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.npc.action.L1NpcAction;
import java.util.List;
import org.apache.commons.logging.Log;

public class NpcActionTable
{
    private static final Log _log;
    private static NpcActionTable _instance;
    private static final List<L1NpcAction> _actions;
    private static final List<L1NpcAction> _talkActions;
    
    static {
        _log = LogFactory.getLog(LightSpawnTable.class);
        _actions = new ArrayList();
        _talkActions = new ArrayList();
    }
    
    private List<L1NpcAction> loadAction(File file, String nodeName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(file);
        if (!doc.getDocumentElement().getNodeName().equalsIgnoreCase(nodeName)) {
            return new ArrayList();
        }
        else {
            return L1NpcXmlParser.listActions(doc.getDocumentElement());
        }
    }
    
    private void loadAction(File file) throws Exception {
        NpcActionTable._actions.addAll(this.loadAction(file, "NpcActionList"));
    }
    
    private void loadTalkAction(File file) throws Exception {
        NpcActionTable._talkActions.addAll(this.loadAction(file, "NpcTalkActionList"));
    }
    
    private void loadDirectoryActions(File dir) throws Exception {
        String[] list;
        for (int length = (list = dir.list()).length, i = 0; i < length; ++i) {
            String file = list[i];
            File f = new File(dir, file);
            if (FileUtil.getExtension(f).equalsIgnoreCase("xml")) {
                this.loadAction(f);
                this.loadTalkAction(f);
            }
        }
    }
    
    private NpcActionTable() throws Exception {
        this.loadDirectoryActions(new File("./data/xml/NpcActions/"));
    }
    
    public static void load() {
        try {
            PerformanceTimer timer = new PerformanceTimer();
            NpcActionTable._instance = new NpcActionTable();
            NpcActionTable._log.info("載入NPC XML對話結果資料 (" + timer.get() + "ms)");
        }
        catch (Exception e) {
            NpcActionTable._log.error(e.getLocalizedMessage(), e);
            System.exit(0);
        }
    }
    
    public static NpcActionTable getInstance() {
        return NpcActionTable._instance;
    }
    
    public L1NpcAction get(String actionName, L1PcInstance pc, L1Object obj) {
        for (L1NpcAction action : NpcActionTable._actions) {
            if (action.acceptsRequest(actionName, pc, obj)) {
                return action;
            }
        }
        return null;
    }
    
    public L1NpcAction get(L1PcInstance pc, L1Object obj) {
        for (L1NpcAction action : NpcActionTable._talkActions) {
            if (action.acceptsRequest("", pc, obj)) {
                return action;
            }
        }
        return null;
    }
}

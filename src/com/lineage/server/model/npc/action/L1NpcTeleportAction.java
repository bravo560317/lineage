package com.lineage.server.model.npc.action;

import com.lineage.server.datatables.NpcTeleportTable;
import com.lineage.server.templates.L1MapsLimitTime;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.MapsGroupTable;
import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import org.w3c.dom.Element;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Location;
import org.apache.commons.logging.Log;

public class L1NpcTeleportAction extends L1NpcXmlAction
{
    private static final Log _log;
    private final L1Location _loc;
    private final int _heading;
    private final int _price;
    private final boolean _effect;
    
    static {
        _log = LogFactory.getLog(L1NpcTeleportAction.class);
    }
    
    public L1NpcTeleportAction(Element element) {
        super(element);
        int x = L1NpcXmlParser.getIntAttribute(element, "X", -1);
        int y = L1NpcXmlParser.getIntAttribute(element, "Y", -1);
        int mapId = L1NpcXmlParser.getIntAttribute(element, "Map", -1);
        this._loc = new L1Location(x, y, mapId);
        this._heading = L1NpcXmlParser.getIntAttribute(element, "Heading", 5);
        this._price = L1NpcXmlParser.getIntAttribute(element, "Price", 0);
        this._effect = L1NpcXmlParser.getBoolAttribute(element, "Effect", true);
    }
    
    @Override
    public L1NpcHtml execute(String actionName, L1PcInstance pc, L1Object obj, byte[] args) {
        try {
            L1MapsLimitTime mapsLimitTime = MapsGroupTable.get().findGroupMap(this._loc.getMapId());
            if (mapsLimitTime != null) {
                int order_id = mapsLimitTime.getOrderId();
                int used_time = pc.getMapsTime(order_id);
                int limit_time = mapsLimitTime.getLimitTime();
                if (used_time > limit_time) {
                    return L1NpcHtml.HTML_CLOSE;
                }
            }
            if (this._price > 0 && !pc.getInventory().consumeItem(40308, (long)this._price)) {
                pc.sendPackets(new S_ServerMessage(337, "$4"));
                return L1NpcHtml.HTML_CLOSE;
            }
            else {
                L1Teleport.teleport(pc, this._loc.getX(), this._loc.getY(), (short)this._loc.getMapId(), this._heading, this._effect);
            }
        }
        catch (Exception e) {
            L1NpcTeleportAction._log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    @Override
    public void execute(String actionName, String npcid) {
        NpcTeleportTable.get().set(actionName, this._loc.getX(), this._loc.getY(), this._loc.getMapId(), this._price, npcid);
    }
}

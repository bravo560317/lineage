package com.lineage.william;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1WilliamTeleportScroll
{
    private int _itemId;
    private int _tpLocX;
    private int _tpLocY;
    private int _tpMapId;
    private int _check_minLocX;
    private int _check_minLocY;
    private int _check_maxLocX;
    private int _check_maxLocY;
    private short _check_MapId;
    private int _removeItem;
    
    public L1WilliamTeleportScroll(int itemId, int tpLocX, int tpLocY, short tpMapId, int check_minLocX, int check_minLocY, int check_maxLocX, int check_maxLocY, short check_MapId, int removeItem) {
        this._itemId = itemId;
        this._tpLocX = tpLocX;
        this._tpLocY = tpLocY;
        this._tpMapId = tpMapId;
        this._check_minLocX = check_minLocX;
        this._check_minLocY = check_minLocY;
        this._check_maxLocX = check_maxLocX;
        this._check_maxLocY = check_maxLocY;
        this._check_MapId = check_MapId;
        this._removeItem = removeItem;
    }
    
    public int getItemId() {
        return this._itemId;
    }
    
    public int getTpLocX() {
        return this._tpLocX;
    }
    
    public int getTpLocY() {
        return this._tpLocY;
    }
    
    public int getTpMapId() {
        return this._tpMapId;
    }
    
    public int getCheckMinLocX() {
        return this._check_minLocX;
    }
    
    public int getCheckMinLocY() {
        return this._check_minLocY;
    }
    
    public int getCheckMaxLocX() {
        return this._check_maxLocX;
    }
    
    public int getCheckMaxLocY() {
        return this._check_maxLocY;
    }
    
    public int getCheckMapId() {
        return this._check_MapId;
    }
    
    public int getRemoveItem() {
        return this._removeItem;
    }
    
    public static int checkItemId(int itemId) {
        L1WilliamTeleportScroll teleport_scroll = TeleportScroll.getInstance().getTemplate(itemId);
        if (teleport_scroll == null) {
            return 0;
        }
        else {
            int item_id = teleport_scroll.getItemId();
            return item_id;
        }
    }
    
    public static void getTeleportScroll(L1PcInstance pc, L1ItemInstance l1iteminstance, int itemId) {
        L1WilliamTeleportScroll teleport_scroll = TeleportScroll.getInstance().getTemplate(itemId);
        if (teleport_scroll == null) {
            return;
        }
        else {
            if (teleport_scroll.getCheckMinLocX() != 0 && teleport_scroll.getCheckMinLocY() != 0 && teleport_scroll.getCheckMaxLocX() != 0 && teleport_scroll.getCheckMaxLocY() != 0 && pc.getX() >= teleport_scroll.getCheckMinLocX() && pc.getX() <= teleport_scroll.getCheckMaxLocX() && pc.getY() >= teleport_scroll.getCheckMinLocY() && pc.getY() <= teleport_scroll.getCheckMaxLocY() && pc.getMapId() == teleport_scroll.getCheckMapId()) {
                if (pc.getMap().isEscapable() || pc.isGm()) {
                    L1Teleport.teleport(pc, teleport_scroll.getTpLocX(), teleport_scroll.getTpLocY(), (short)teleport_scroll.getTpMapId(), pc.getHeading(), true);
                    if (teleport_scroll.getRemoveItem() != 0) {
                        pc.getInventory().removeItem(l1iteminstance, 1L);
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(647));
                }
            }
            else if (teleport_scroll.getCheckMinLocX() == 0 && teleport_scroll.getCheckMinLocY() == 0 && teleport_scroll.getCheckMaxLocX() == 0 && teleport_scroll.getCheckMaxLocY() == 0 && teleport_scroll.getTpLocX() != 0 && teleport_scroll.getTpLocY() != 0) {
                if (pc.getMap().isEscapable() || pc.isGm()) {
                    L1Teleport.teleport(pc, teleport_scroll.getTpLocX(), teleport_scroll.getTpLocY(), (short)teleport_scroll.getTpMapId(), pc.getHeading(), true);
                    if (teleport_scroll.getRemoveItem() != 0) {
                        pc.getInventory().removeItem(l1iteminstance, 1L);
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(647));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
            return;
        }
    }
}

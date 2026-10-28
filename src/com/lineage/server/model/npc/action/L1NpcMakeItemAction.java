package com.lineage.server.model.npc.action;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_HowManyMake;
import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import org.w3c.dom.NodeList;
import com.lineage.server.utils.IterableElementList;
import java.util.ArrayList;
import org.w3c.dom.Element;
import com.lineage.server.model.L1ObjectAmount;
import java.util.List;

public class L1NpcMakeItemAction extends L1NpcXmlAction
{
    private final List<L1ObjectAmount<Integer>> _materials;
    private final List<L1ObjectAmount<Integer>> _items;
    private final boolean _isAmountInputable;
    private final L1NpcAction _actionOnSucceed;
    private final L1NpcAction _actionOnFail;
    
    public L1NpcMakeItemAction(Element element) {
        super(element);
        this._materials = new ArrayList();
        this._items = new ArrayList();
        this._isAmountInputable = L1NpcXmlParser.getBoolAttribute(element, "AmountInputable", true);
        NodeList list = element.getChildNodes();
        for (Element elem : new IterableElementList(list)) {
            if (elem.getNodeName().equalsIgnoreCase("Material")) {
                int id = Integer.valueOf(elem.getAttribute("ItemId"));
                long amount = Long.valueOf(elem.getAttribute("Amount"));
                this._materials.add(new L1ObjectAmount((Integer)id, amount));
            }
            else if (elem.getNodeName().equalsIgnoreCase("Item")) {
                int id = Integer.valueOf(elem.getAttribute("ItemId"));
                long amount = Long.valueOf(elem.getAttribute("Amount"));
                this._items.add(new L1ObjectAmount((Integer)id, amount));
            }
            else {
                continue;
            }
        }
        if (this._items.isEmpty() || this._materials.isEmpty()) {
            throw new IllegalArgumentException();
        }
        else {
            Element elem = L1NpcXmlParser.getFirstChildElementByTagName(element, "Succeed");
            this._actionOnSucceed = ((elem == null) ? null : new L1NpcListedAction(elem));
            elem = L1NpcXmlParser.getFirstChildElementByTagName(element, "Fail");
            this._actionOnFail = ((elem == null) ? null : new L1NpcListedAction(elem));
            return;
        }
    }
    
    private boolean makeItems(L1PcInstance pc, String npcName, long amount) {
        if (amount <= 0L) {
            return false;
        }
        else {
            boolean isEnoughMaterials = true;
            for (L1ObjectAmount<Integer> material : this._materials) {
                if (!pc.getInventory().checkItemNotEquipped(material.getObject(), material.getAmount() * amount)) {
                    L1Item temp = ItemTable.get().getTemplate(material.getObject());
                    pc.sendPackets(new S_ServerMessage(337, temp.getNameId() + "(" + (material.getAmount() * amount - pc.getInventory().countItems(temp.getItemId())) + ")"));
                    isEnoughMaterials = false;
                }
            }
            if (!isEnoughMaterials) {
                return false;
            }
            else {
                int countToCreate = 0;
                int weight = 0;
                for (L1ObjectAmount<Integer> makingItem : this._items) {
                    L1Item temp2 = ItemTable.get().getTemplate(makingItem.getObject());
                    if (temp2.isStackable()) {
                        if (!pc.getInventory().checkItem(makingItem.getObject())) {
                            ++countToCreate;
                        }
                    }
                    else {
                        countToCreate = (int)((long)countToCreate + makingItem.getAmount() * amount);
                    }
                    weight = (int)((long)weight + (long)temp2.getWeight() * (makingItem.getAmount() * amount) / 1000L);
                }
                if (pc.getInventory().getSize() + countToCreate > 180) {
                    pc.sendPackets(new S_ServerMessage(263));
                    return false;
                }
                else if (pc.getMaxWeight() < (double)(pc.getInventory().getWeight() + weight)) {
                    pc.sendPackets(new S_ServerMessage(82));
                    return false;
                }
                else {
                    for (L1ObjectAmount<Integer> material2 : this._materials) {
                        pc.getInventory().consumeItem(material2.getObject(), material2.getAmount() * amount);
                    }
                    for (L1ObjectAmount<Integer> makingItem : this._items) {
                        L1ItemInstance item = pc.getInventory().storeItem(makingItem.getObject(), makingItem.getAmount() * amount);
                        if (item != null) {
                            String itemName = ItemTable.get().getTemplate(makingItem.getObject()).getNameId();
                            if (makingItem.getAmount() * amount > 1L) {
                                itemName = itemName + " (" + makingItem.getAmount() * amount + ")";
                            }
                            pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                        }
                    }
                    return true;
                }
            }
        }
    }
    
    private long countNumOfMaterials(L1PcInventory inv) {
        long count = Long.MAX_VALUE;
        for (L1ObjectAmount<Integer> material : this._materials) {
            long numOfSet = inv.countItems(material.getObject()) / material.getAmount();
            count = Math.min(count, numOfSet);
        }
        return count;
    }
    
    @Override
    public L1NpcHtml execute(String actionName, L1PcInstance pc, L1Object obj, byte[] args) {
        long numOfMaterials = this.countNumOfMaterials(pc.getInventory());
        if (1L < numOfMaterials && this._isAmountInputable) {
            pc.sendPackets(new S_HowManyMake(obj.getId(), (int)numOfMaterials, actionName));
            return null;
        }
        else {
            return this.executeWithAmount(actionName, pc, obj, 1L);
        }
    }
    
    @Override
    public L1NpcHtml executeWithAmount(String actionName, L1PcInstance pc, L1Object obj, long amount) {
        L1NpcInstance npc = (L1NpcInstance)obj;
        L1NpcHtml result = null;
        if (this.makeItems(pc, npc.getNameId(), amount)) {
            if (this._actionOnSucceed != null) {
                result = this._actionOnSucceed.execute(actionName, pc, obj, new byte[0]);
            }
        }
        else if (this._actionOnFail != null) {
            result = this._actionOnFail.execute(actionName, pc, obj, new byte[0]);
        }
        return (result == null) ? L1NpcHtml.HTML_CLOSE : result;
    }
    
    @Override
    public void execute(String actionName, String npcid) {
    }
}

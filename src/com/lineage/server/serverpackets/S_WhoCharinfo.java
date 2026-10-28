package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.List;
import com.lineage.config.ConfigWho;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.L1Character;

public class S_WhoCharinfo extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_WhoCharinfo(L1Character cha, ClientExecutor client) {
        this._byte = null;
        String lawfulness = "";
        int lawful = cha.getLawful();
        if (lawful < 0) {
            lawfulness = "($1503)";
        }
        else if (lawful >= 0 && lawful < 500) {
            lawfulness = "($1502)";
        }
        else if (lawful >= 500) {
            lawfulness = "($1501)";
        }
        this.writeC(71);
        this.writeH(166);
        this.writeC(1);
        String title = "";
        String clan = "";
        int lv = 0;
        if (!cha.getTitle().equalsIgnoreCase("")) {
            title = cha.getTitle() + " ";
        }
        String name = "";
        if (cha instanceof L1DeInstance) {
            L1DeInstance de = (L1DeInstance)cha;
            name = de.getNameId();
            if (de.getClanid() > 0) {
                clan = "[" + de.getClanname() + "]";
            }
            lv = de.getLevel();
        }
        else if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            L1PcInstance herotpc = client.getActiveChar();
            if (pc.isPrivateShop()) {
                herotpc.sendPackets(new S_SystemMessage("\\fT掛賣擺攤中"));
                return;
            }
            else {
                String msg = "";
                if (pc.isCrown()) {
                    msg = "\\fV王族";
                }
                else if (pc.isKnight()) {
                    msg = "\\fV騎士";
                }
                else if (pc.isElf()) {
                    msg = "\\fV妖精";
                }
                else if (pc.isWizard()) {
                    msg = "\\fV法師";
                }
                else if (pc.isDarkelf()) {
                    msg = "\\fV黑妖";
                }
                else if (pc.isDragonKnight()) {
                    msg = "\\fV龍騎士";
                }
                else if (pc.isIllusionist()) {
                    msg = "\\fV幻術士";
                }
                name = pc.getName();
                this.writeS("玩家: " + name + "\r\n血盟: " + pc.getClanname() + "\u3000職業: " + msg + " " + lawfulness);
                if (herotpc.isGm()) {
                    List<L1ItemInstance> items = pc.getInventory().getItems();
                    List<L1ItemInstance> itemsx = new CopyOnWriteArrayList();
                    for (L1ItemInstance item : items) {
                        itemsx.add(item);
                    }
                    herotpc.sendPackets(new S_WhoHero(herotpc, 0, itemsx));
                    String msg2 = "";
                    String msg3 = "";
                    String msg4 = "";
                    String msg5 = "";
                    String msg6 = "";
                    String msg7 = "";
                    String msg8 = "";
                    String msg9 = "";
                    String msg10 = "";
                    String msg11 = "";
                    String msg12 = "";
                    msg2 = new StringBuilder().append(pc.getLevel()).toString();
                    msg3 = pc.getCurrentHp() + " / " + pc.getMaxHp();
                    msg4 = pc.getCurrentMp() + " / " + pc.getMaxMp();
                    msg5 = new StringBuilder().append(pc.getAc()).toString();
                    msg6 = new StringBuilder().append((int)pc.getStr()).toString();
                    msg7 = new StringBuilder().append((int)pc.getDex()).toString();
                    msg8 = new StringBuilder().append((int)pc.getInt()).toString();
                    msg9 = new StringBuilder().append((int)pc.getCon()).toString();
                    msg10 = new StringBuilder().append((int)pc.getWis()).toString();
                    msg11 = new StringBuilder().append((int)pc.getCha()).toString();
                    msg12 = pc.getMr() + " %";
                    herotpc.sendPackets(new S_SystemMessage("\\fT等級:[" + msg2 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fW血量:[" + msg3 + "]  //  魔力:[" + msg4 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fU防禦:[" + msg5 + "]  //  魔防:[" + msg12 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fR力量:[" + msg6 + "]  //  敏捷:[" + msg7 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fR智力:[" + msg8 + "]  //  體質:[" + msg9 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fR精神:[" + msg10 + "]  //  魅力:[" + msg11 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("出生素質  力:" + pc.getOriginalStr() + " 敏:" + pc.getOriginalDex() + " 智:" + pc.getOriginalInt()));
                    herotpc.sendPackets(new S_SystemMessage("出生素質  精:" + pc.getOriginalWis() + " 體:" + pc.getOriginalCon() + " 魅:" + pc.getOriginalCha()));
                    return;
                }
                else if (ConfigWho.whoitemeq) {
                    List<L1ItemInstance> items = pc.getInventory().getItems();
                    List<L1ItemInstance> itemsx = new CopyOnWriteArrayList();
                    for (L1ItemInstance item : items) {
                        if (item.isEquipped()) {
                            itemsx.add(item);
                        }
                    }
                    herotpc.sendPackets(new S_WhoHero(herotpc, 0, itemsx));
                    String msg2 = "";
                    String msg3 = "";
                    String msg4 = "";
                    String msg5 = "";
                    String msg6 = "";
                    String msg7 = "";
                    String msg8 = "";
                    String msg9 = "";
                    String msg10 = "";
                    String msg11 = "";
                    String msg12 = "";
                    msg2 = new StringBuilder().append(pc.getLevel()).toString();
                    msg3 = pc.getCurrentHp() + " / " + pc.getMaxHp();
                    msg4 = pc.getCurrentMp() + " / " + pc.getMaxMp();
                    msg5 = new StringBuilder().append(pc.getAc()).toString();
                    msg6 = new StringBuilder().append((int)pc.getStr()).toString();
                    msg7 = new StringBuilder().append((int)pc.getDex()).toString();
                    msg8 = new StringBuilder().append((int)pc.getInt()).toString();
                    msg9 = new StringBuilder().append((int)pc.getCon()).toString();
                    msg10 = new StringBuilder().append((int)pc.getWis()).toString();
                    msg11 = new StringBuilder().append((int)pc.getCha()).toString();
                    msg12 = pc.getMr() + " %";
                    herotpc.sendPackets(new S_SystemMessage("\\fT等級:[" + msg2 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fW血量:[" + msg3 + "]  //  魔力:[" + msg4 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fU防禦:[" + msg5 + "]  //  魔防:[" + msg12 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fR力量:[" + msg6 + "]  //  敏捷:[" + msg7 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fR智力:[" + msg8 + "]  //  體質:[" + msg9 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("\\fR精神:[" + msg10 + "]  //  魅力:[" + msg11 + "]"));
                    herotpc.sendPackets(new S_SystemMessage("出生素質  力:" + pc.getOriginalStr() + " 敏:" + pc.getOriginalDex() + " 智:" + pc.getOriginalInt()));
                    herotpc.sendPackets(new S_SystemMessage("出生素質  精:" + pc.getOriginalWis() + " 體:" + pc.getOriginalCon() + " 魅:" + pc.getOriginalCha()));
                    return;
                }
            }
        }
    }
    
    public S_WhoCharinfo(L1PcInstance pc, L1PcInstance tpc) {
        this._byte = null;
        this.writeC(71);
        this.writeH(166);
        this.writeC(1);
        List<L1ItemInstance> items = tpc.getInventory().getItems();
        List<L1ItemInstance> itemsx = new CopyOnWriteArrayList();
        for (L1ItemInstance item : items) {
            if (pc.isGm()) {
                itemsx.add(item);
            }
            else if (item.isEquipped()) {
                itemsx.add(item);
            }
            else {
                continue;
            }
        }
        pc.sendPackets(new S_WhoXljnet(pc, 0, itemsx));
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

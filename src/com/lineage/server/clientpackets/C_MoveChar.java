package com.lineage.server.clientpackets;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;
import com.lineage.server.serverpackets.S_NPCPack_Skin;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1SkinInstance;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Iterator;
import java.util.List;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NewMaster;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.data.event.ProtectorSet;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.world.WorldTrap;
import com.lineage.server.serverpackets.S_MoveCharPacket;
import com.lineage.server.datatables.DungeonRTable;
import com.lineage.server.datatables.DungeonTable;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.utils.Teleportation;
import com.lineage.server.templates.L1MapCheck;
import com.lineage.server.datatables.MapCheckTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.sql.Timestamp;
import com.lineage.server.datatables.MapTileTable;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.command.executor.L1MoveXY;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.model.L1Trade;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_MoveChar extends ClientBasePacket
{
    private static final Log _log;
    private static final byte[] HEADING_TABLE_X;
    private static final byte[] HEADING_TABLE_Y;
    
    static {
        _log = LogFactory.getLog(C_MoveChar.class);
        HEADING_TABLE_X = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        HEADING_TABLE_Y = new byte[] { -1, -1, 0, 1, 1, 1, 0, -1 };
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isDead() && !pc.isTeleport()) {
                if (pc.getTradeID() != 0) {
                    L1Trade trade = new L1Trade();
                    trade.tradeCancel(pc);
                }
                int locx = 0;
                int locy = 0;
                int heading = 0;
                try {
                    locx = this.readH();
                    locy = this.readH();
                    heading = this.readC();
                    heading = Math.min(heading, 7);
                }
                catch (Exception e) {
                    return;
                }
                pc.killSkillEffectTimer(32);
                pc.setCallClanId(0);
                if (!pc.hasSkillEffect(78)) {
                    pc.setRegenState(2);
                }
                pc.getMap().setPassable(pc.getLocation(), true);
                int oleLocx = pc.getX();
                int oleLocy = pc.getY();
                int newlocx = locx + C_MoveChar.HEADING_TABLE_X[heading];
                int newlocy = locy + C_MoveChar.HEADING_TABLE_Y[heading];
                try {
                    boolean isError = false;
                    if (locx != oleLocx && locy != oleLocy) {
                        isError = true;
                    }
                    if (pc.isPrivateShop()) {
                        isError = true;
                    }
                    if (pc.isParalyzedX()) {
                        isError = true;
                    }
                    int minX = MapsTable.get().getStartX((int)pc.getMapId());
                    int minY = MapsTable.get().getStartY((int)pc.getMapId());
                    int maxX = MapsTable.get().getEndX((int)pc.getMapId());
                    int maxY = MapsTable.get().getEndY((int)pc.getMapId());
                    if (newlocx < minX || newlocx > maxX || newlocy < minY || newlocy > maxY) {
                        isError = true;
                    }
                    if (L1MoveXY.SHUTDOWN1) {
                        boolean isPassable = pc.getMap().isOriginalTile(newlocx, newlocy);
                        if (!isPassable) {
                            int tile = 0;
                            if (pc.getMap().isCombatZone(oleLocx, oleLocy)) {
                                tile = 47;
                            }
                            else if (pc.getMap().isNormalZone(oleLocx, oleLocy)) {
                                tile = 15;
                            }
                            else if (pc.getMap().isSafetyZone(oleLocx, oleLocy)) {
                                tile = 31;
                            }
                            L1WorldMap.get().getMap(pc.getMapId()).setTestTile(newlocx, newlocy, tile);
                            MapTileTable.get().addData((int)pc.getMapId(), newlocx, newlocy, tile);
                            movexy("IP(" + pc.getNetConnection().getIp() + ")" + "玩家採點座標地圖:" + "【 " + (int)pc.getMapId() + " 】 " + "X座標:" + "[" + newlocx + "] Y座標:[" + newlocy + "]區域(" + tile + ")" + " 】" + new Timestamp(System.currentTimeMillis()) + ")。");
                            if (pc.isGm()) {
                                pc.sendPackets(new S_ServerMessage("已解除障礙，該點設為可通行。"));
                            }
                        }
                    }
                    if (pc.isGm() && L1MoveXY.SHUTDOWN1) {
                        boolean isPassable = pc.getMap().isOriginalTile(newlocx, newlocy);
                        if (!isPassable) {
                            int tile = 0;
                            if (pc.getMap().isCombatZone(oleLocx, oleLocy)) {
                                tile = 47;
                            }
                            else if (pc.getMap().isNormalZone(oleLocx, oleLocy)) {
                                tile = 15;
                            }
                            else if (pc.getMap().isSafetyZone(oleLocx, oleLocy)) {
                                tile = 31;
                            }
                            L1WorldMap.get().getMap(pc.getMapId()).setTestTile(newlocx, newlocy, tile);
                            MapTileTable.get().addData((int)pc.getMapId(), newlocx, newlocy, tile);
                            movexy("IP(" + pc.getNetConnection().getIp() + ")" + "玩家採點座標地圖:" + "【 " + (int)pc.getMapId() + " 】 " + "X座標:" + "[" + newlocx + "] Y座標:[" + newlocy + "]區域(" + tile + ")" + " 】" + new Timestamp(System.currentTimeMillis()) + ")。");
                            if (pc.isGm()) {
                                pc.sendPackets(new S_ServerMessage("已解除障礙，該點設為可通行。"));
                            }
                        }
                    }
                    boolean isCheck = false;
                    List<L1MapCheck> check = MapCheckTable.get().getList();
                    for (L1MapCheck temp : check) {
                        if (pc.getMapId() == temp.getMapid()) {
                            if (temp.getStartX() == 0) {
                                isCheck = true;
                                break;
                            }
                            else if (pc.getX() >= temp.getStartX() && pc.getX() <= temp.getEndX() && pc.getY() >= temp.getStartY() && pc.getY() <= temp.getEndY()) {
                                isCheck = true;
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    if (isCheck) {
                        boolean isPassable2 = pc.getMap().isOriginalTile(newlocx, newlocy);
                        if (!isPassable2) {
                            isError = true;
                        }
                    }
                    if (pc.checkPassable(newlocx, newlocy)) {
                        isError = true;
                    }
                    if (isError) {
                        pc.setTeleportX(pc.getOleLocX());
                        pc.setTeleportY(pc.getOleLocY());
                        pc.setTeleportMapId(pc.getMapId());
                        pc.setTeleportHeading(pc.getHeading());
                        Teleportation.teleportation(pc);
                        return;
                    }
                }
                catch (Exception e2) {
                    C_MoveChar._log.error(e2.getLocalizedMessage(), e2);
                }
                int result = pc.speed_Attack().checkInterval(AcceleratorChecker.ACT_TYPE.MOVE);
                if (result == 2) {
                    C_MoveChar._log.error("要求角色移動:速度異常(" + pc.getName() + ")");
                }
                CheckUtil.isUserMap(pc);
                if (DungeonTable.get().dg(newlocx, newlocy, pc.getMap().getId(), pc)) {
                    return;
                }
                else if (DungeonRTable.get().dg(newlocx, newlocy, pc.getMap().getId(), pc)) {
                    return;
                }
                else {
                    pc.setOleLocX(oleLocx);
                    pc.setOleLocY(oleLocy);
                    pc.getLocation().set(newlocx, newlocy);
                    pc.setHeading(heading);
                    if (!pc.isGmInvis() && !pc.isGhost() && !pc.isInvisble()) {
                        pc.broadcastPacketAll(new S_MoveCharPacket(pc));
                    }
                    pc.setNpcSpeed();
                    this.setNpcSpeed(pc);
                    pc.getMap().setPassable(pc.getLocation(), false);
                    WorldTrap.get().onPlayerMoved(pc);
                    if (pc.getHierarchs() != null && L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
                        pc.getHierarchs().deleteHierarch();
                    }
                    if (!ConfigOther.warProtector && pc.isProtector() && L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
                        L1ItemInstance item = pc.getInventory().findItemId(ProtectorSet.ITEM_ID);
                        if (item != null) {
                            pc.getInventory().removeItem(item, 1L);
                        }
                    }
                    if (!ConfigOther.war_pet_summ && !pc.getPetList().isEmpty() && isInWarAreaAndWarTime(pc)) {
                        Object[] array;
                        for (int length = (array = pc.getPetList().values().toArray()).length, i = 0; i < length; ++i) {
                            Object obj = array[i];
                            L1NpcInstance petObject = (L1NpcInstance)obj;
                            if (petObject != null) {
                                if (petObject instanceof L1PetInstance) {
                                    L1PetInstance pet = (L1PetInstance)petObject;
                                    pet.collect(true);
                                    pc.removePet(pet);
                                    pet.deleteMe();
                                }
                                if (petObject instanceof L1SummonInstance) {
                                    L1SummonInstance summon = (L1SummonInstance)petObject;
                                    S_NewMaster packet = new S_NewMaster(summon);
                                    if (summon != null) {
                                        if (summon.destroyed()) {
                                            return;
                                        }
                                        else {
                                            summon.Death(null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                return;
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    private static boolean isInWarAreaAndWarTime(L1PcInstance pc) {
        int castleId = L1CastleLocation.getCastleIdByArea(pc);
        return castleId != 0 && ServerWarExecutor.get().isNowWar(castleId);
    }
    
    private void setNpcSpeed(L1PcInstance pc) {
        Map<Integer, L1SkinInstance> skinList = pc.getSkins();
        if (skinList.size() > 0) {
            for (Integer gfxid : skinList.keySet()) {
                L1SkinInstance skin = skinList.get(gfxid);
                skin.setX(pc.getX());
                skin.setY(pc.getY());
                skin.setMap(pc.getMap());
                skin.setHeading(pc.getHeading());
                if (skin.getMoveType() == 0) {
                    for (L1PcInstance visiblePc : World.get().getVisiblePlayer(skin)) {
                        visiblePc.removeKnownObject(skin);
                    }
                    skin.broadcastPacketAll(new S_NPCPack_Skin(skin));
                }
                else {
                    skin.setNpcMoveSpeed();
                    skin.broadcastPacketAll(new S_MoveCharPacket(skin));
                }
            }
        }
    }
    
    public static void 玩家穿透(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[玩家穿透紀錄].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void movexy(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("玩家紀錄/玩家開通座標紀錄.txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

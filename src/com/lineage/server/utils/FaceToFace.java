package com.lineage.server.utils;

import java.util.Iterator;
import java.util.List;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;

public class FaceToFace
{
    public static L1PcInstance faceToFace(L1PcInstance pc) {
        int pcX = pc.getX();
        int pcY = pc.getY();
        int pcHeading = pc.getHeading();
        List<L1PcInstance> players = (List)World.get().getVisiblePlayer(pc, 1);
        List<L1Object> des = (List)World.get().getVisibleObjects(pc, 1);
        int i = players.size();
        for (L1Object tgde : des) {
            if (tgde instanceof L1DeInstance) {
                ++i;
            }
        }
        if (i == 0) {
            pc.sendPackets(new S_ServerMessage(93));
            return null;
        }
        else {
            for (L1PcInstance target : players) {
                int targetX = target.getX();
                int targetY = target.getY();
                int targetHeading = target.getHeading();
                if (pcHeading == 0 && pcX == targetX && pcY == targetY + 1) {
                    if (targetHeading == 4) {
                        return target;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(91, target.getName()));
                        return null;
                    }
                }
                else if (pcHeading == 1 && pcX == targetX - 1 && pcY == targetY + 1) {
                    if (targetHeading == 5) {
                        return target;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(91, target.getName()));
                        return null;
                    }
                }
                else if (pcHeading == 2 && pcX == targetX - 1 && pcY == targetY) {
                    if (targetHeading == 6) {
                        return target;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(91, target.getName()));
                        return null;
                    }
                }
                else if (pcHeading == 3 && pcX == targetX - 1 && pcY == targetY - 1) {
                    if (targetHeading == 7) {
                        return target;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(91, target.getName()));
                        return null;
                    }
                }
                else if (pcHeading == 4 && pcX == targetX && pcY == targetY - 1) {
                    if (targetHeading == 0) {
                        return target;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(91, target.getName()));
                        return null;
                    }
                }
                else if (pcHeading == 5 && pcX == targetX + 1 && pcY == targetY - 1) {
                    if (targetHeading == 1) {
                        return target;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(91, target.getName()));
                        return null;
                    }
                }
                else if (pcHeading == 6 && pcX == targetX + 1 && pcY == targetY) {
                    if (targetHeading == 2) {
                        return target;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(91, target.getName()));
                        return null;
                    }
                }
                else if (pcHeading == 7 && pcX == targetX + 1 && pcY == targetY + 1) {
                    if (targetHeading == 3) {
                        return target;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(91, target.getName()));
                        return null;
                    }
                }
                else {
                    continue;
                }
            }
            pc.sendPackets(new S_ServerMessage(93));
            return null;
        }
    }
}

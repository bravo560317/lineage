package com.lineage.server.model.Instance;

import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.map.L1Map;
import java.util.ArrayList;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import java.util.LinkedList;
import com.lineage.server.types.Point;
import com.lineage.server.serverpackets.S_MoveCharPacket;
import org.apache.commons.logging.LogFactory;
import java.util.Iterator;
import org.apache.commons.logging.Log;

public class NpcMove extends NpcMoveExecutor
{
    private static final Log _log;
    private static final int _hce = 25;
    private int[] _cxy;
    private int _aie;
    private Iterator<int[]> _list;
    private final L1NpcInstance _npc;
    
    static {
        _log = LogFactory.getLog(NpcMove.class);
    }
    
    public NpcMove(L1NpcInstance npc) {
        this._cxy = null;
        this._aie = 0;
        this._list = null;
        this._npc = npc;
    }
    
    @Override
    public void setDirectionMove(int dir) {
        if (dir >= 0) {
            int locx = this._npc.getX();
            int locy = this._npc.getY();
            locx += NpcMove.HEADING_TABLE_X[dir];
            locy += NpcMove.HEADING_TABLE_Y[dir];
            this._npc.setHeading(dir);
            if (!(this._npc instanceof L1DollInstance)) {
                this._npc.getMap().setPassable(this._npc.getLocation(), true);
            }
            this._npc.setX(locx);
            this._npc.setY(locy);
            if (!(this._npc instanceof L1DollInstance)) {
                this._npc.getMap().setPassable(this._npc.getLocation(), false);
            }
            this._npc.broadcastPacketAll(new S_MoveCharPacket(this._npc, locx, locy));
            if (this._npc.getMovementDistance() > 0 && (this._npc instanceof L1GuardInstance || this._npc instanceof L1MerchantInstance || this._npc instanceof L1MonsterInstance) && this._npc.getLocation().getLineDistance(new Point(this._npc.getHomeX(), this._npc.getHomeY())) > (double)this._npc.getMovementDistance()) {
                this._npc.teleport(this._npc.getHomeX(), this._npc.getHomeY(), this._npc.getHeading());
            }
            if (this._npc.getNpcTemplate().get_npcId() >= 45912 && this._npc.getNpcTemplate().get_npcId() <= 45916 && (this._npc.getX() < 32591 || this._npc.getX() > 32644 || this._npc.getY() < 32643 || this._npc.getY() > 32688 || this._npc.getMapId() != 4)) {
                this._npc.teleport(this._npc.getHomeX(), this._npc.getHomeY(), this._npc.getHeading());
            }
        }
    }
    
    @Override
    public void clear() {
        if (this._list != null) {
            this._list = null;
        }
        this._aie = 0;
        this._cxy = null;
    }
    
    @Override
    public int moveDirection(int x, int y) {
        if (!this._npc.isAiRunning()) {
            this._npc.startAI();
        }
        int dir = 0;
        try {
            double d = this._npc.getLocation().getLineDistance(new Point(x, y));
            if (this._npc.hasSkillEffect(40) && d >= 2.0) {
                return -1;
            }
            else if (d > 30.0) {
                return -1;
            }
            else if (d > 20.0) {
                dir = this.targetDirection(x, y);
                dir = this.checkObject(dir);
                dir = this.openDoor(dir);
            }
            else {
                dir = this._serchCource(x, y);
                if (dir == -1) {
                    dir = _targetDirection(this._npc.getHeading(), this._npc.getX(), this._npc.getY(), x, y);
                    if (!this._exsistCharacterBetweenTarget(dir)) {
                        dir = this.checkObject(dir);
                        dir = this.openDoor(dir);
                    }
                }
            }
        }
        catch (Exception e) {
            NpcMove._log.error(e.getLocalizedMessage(), e);
        }
        return dir;
    }
    
    private int targetDirection(int tx, int ty) {
        if (!this._npc.isAiRunning()) {
            this._npc.startAI();
        }
        float dis_x = (float)Math.abs(this._npc.getX() - tx);
        float dis_y = (float)Math.abs(this._npc.getY() - ty);
        float dis = Math.max(dis_x, dis_y);
        if (dis == 0.0f) {
            return this._npc.getHeading();
        }
        else {
            int avg_x = (int)Math.floor((double)(dis_x / dis + 0.59f));
            int avg_y = (int)Math.floor((double)(dis_y / dis + 0.59f));
            int dir_x = 0;
            int dir_y = 0;
            if (this._npc.getX() < tx) {
                dir_x = 1;
            }
            if (this._npc.getX() > tx) {
                dir_x = -1;
            }
            if (this._npc.getY() < ty) {
                dir_y = 1;
            }
            if (this._npc.getY() > ty) {
                dir_y = -1;
            }
            if (avg_x == 0) {
                dir_x = 0;
            }
            if (avg_y == 0) {
                dir_y = 0;
            }
            if (dir_x == 1 && dir_y == -1) {
                return 1;
            }
            else if (dir_x == 1 && dir_y == 0) {
                return 2;
            }
            else if (dir_x == 1 && dir_y == 1) {
                return 3;
            }
            else if (dir_x == 0 && dir_y == 1) {
                return 4;
            }
            else if (dir_x == -1 && dir_y == 1) {
                return 5;
            }
            else if (dir_x == -1 && dir_y == 0) {
                return 6;
            }
            else if (dir_x == -1 && dir_y == -1) {
                return 7;
            }
            else if (dir_x == 0 && dir_y == -1) {
                return 0;
            }
            else {
                return this._npc.getHeading();
            }
        }
    }
    
    private int _serchCource(int x, int y) {
        int locCenter = 21;
        int diff_x = x - 21;
        int diff_y = y - 21;
        int[] locBace = { this._npc.getX() - diff_x, this._npc.getY() - diff_y, 0, 0 };
        int[] locNext = new int[4];
        int[] dirFront = new int[5];
        boolean[][] serchMap = new boolean[43][43];
        LinkedList<int[]> queueSerch = new LinkedList();
        for (int j = 41; j > 0; --j) {
            for (int i = 20 - Math.abs(21 - j); i >= 0; --i) {
                serchMap[j][21 + i] = true;
                serchMap[j][21 - i] = true;
            }
        }
        int[] firstCource = { 2, 4, 6, 0, 1, 3, 5, 7 };
        int i = 0;
        while (i < 8) {
            System.arraycopy(locBace, 0, locNext, 0, 4);
            this._moveLocation(locNext, firstCource[i]);
            if (locNext[0] - 21 == 0 && locNext[1] - 21 == 0) {
                return firstCource[i];
            }
            else {
                if (serchMap[locNext[0]][locNext[1]]) {
                    int tmpX = locNext[0] + diff_x;
                    int tmpY = locNext[1] + diff_y;
                    boolean found = false;
                    switch (i) {
                        case 0: {
                            if (this._npc.getNpcTemplate().is_boss()) {
                                found = this._npc.getMap().isPassableDna(tmpX, tmpY + 1, i);
                                break;
                            }
                            else {
                                found = this._npc.getMap().isPassable(tmpX, tmpY + 1, i);
                                break;
                            }
                        }
                        case 1: {
                            if (this._npc.getNpcTemplate().is_boss()) {
                                found = this._npc.getMap().isPassableDna(tmpX - 1, tmpY + 1, i);
                                break;
                            }
                            else {
                                found = this._npc.getMap().isPassable(tmpX - 1, tmpY + 1, i, this._npc);
                                break;
                            }
                        }
                        case 2: {
                            if (this._npc.getNpcTemplate().is_boss()) {
                                found = this._npc.getMap().isPassableDna(tmpX - 1, tmpY, i);
                                break;
                            }
                            else {
                                found = this._npc.getMap().isPassable(tmpX - 1, tmpY, i, this._npc);
                                break;
                            }
                        }
                        case 3: {
                            if (this._npc.getNpcTemplate().is_boss()) {
                                found = this._npc.getMap().isPassableDna(tmpX - 1, tmpY - 1, i);
                                break;
                            }
                            else {
                                found = this._npc.getMap().isPassable(tmpX - 1, tmpY - 1, i, this._npc);
                                break;
                            }
                        }
                        case 4: {
                            if (this._npc.getNpcTemplate().is_boss()) {
                                found = this._npc.getMap().isPassableDna(tmpX, tmpY - 1, i);
                                break;
                            }
                            else {
                                found = this._npc.getMap().isPassable(tmpX, tmpY - 1, i, this._npc);
                                break;
                            }
                        }
                        case 5: {
                            if (this._npc.getNpcTemplate().is_boss()) {
                                found = this._npc.getMap().isPassableDna(tmpX + 1, tmpY - 1, i);
                                break;
                            }
                            else {
                                found = this._npc.getMap().isPassable(tmpX + 1, tmpY - 1, i, this._npc);
                                break;
                            }
                        }
                        case 6: {
                            if (this._npc.getNpcTemplate().is_boss()) {
                                found = this._npc.getMap().isPassableDna(tmpX + 1, tmpY, i);
                                break;
                            }
                            else {
                                found = this._npc.getMap().isPassable(tmpX + 1, tmpY, i, this._npc);
                                break;
                            }
                        }
                        case 7: {
                            if (this._npc.getNpcTemplate().is_boss()) {
                                found = this._npc.getMap().isPassableDna(tmpX + 1, tmpY + 1, i);
                                break;
                            }
                            else {
                                found = this._npc.getMap().isPassable(tmpX + 1, tmpY + 1, i, this._npc);
                                break;
                            }
                        }
                    }
                    if (found) {
                        int[] locCopy = new int[4];
                        System.arraycopy(locNext, 0, locCopy, 0, 4);
                        locCopy[2] = firstCource[i];
                        locCopy[3] = firstCource[i];
                        queueSerch.add(locCopy);
                    }
                    serchMap[locNext[0]][locNext[1]] = false;
                }
                ++i;
            }
        }
        locBace = null;
        while (queueSerch.size() > 0) {
            locBace = queueSerch.removeFirst();
            this._getFront(dirFront, locBace[2]);
            i = 4;
            while (i >= 0) {
                System.arraycopy(locBace, 0, locNext, 0, 4);
                this._moveLocation(locNext, dirFront[i]);
                if (locNext[0] - 21 == 0 && locNext[1] - 21 == 0) {
                    return locNext[3];
                }
                else {
                    if (serchMap[locNext[0]][locNext[1]]) {
                        int tmpX = locNext[0] + diff_x;
                        int tmpY = locNext[1] + diff_y;
                        boolean found = false;
                        switch (i) {
                            case 0: {
                                found = this._npc.getMap().isPassableDna(tmpX, tmpY + 1, i);
                                break;
                            }
                            case 1: {
                                found = this._npc.getMap().isPassableDna(tmpX - 1, tmpY + 1, i);
                                break;
                            }
                            case 2: {
                                found = this._npc.getMap().isPassableDna(tmpX - 1, tmpY, i);
                                break;
                            }
                            case 3: {
                                found = this._npc.getMap().isPassableDna(tmpX - 1, tmpY - 1, i);
                                break;
                            }
                            case 4: {
                                found = this._npc.getMap().isPassableDna(tmpX, tmpY - 1, i);
                                break;
                            }
                        }
                        if (found) {
                            int[] locCopy = new int[4];
                            System.arraycopy(locNext, 0, locCopy, 0, 4);
                            locCopy[2] = dirFront[i];
                            queueSerch.add(locCopy);
                        }
                        serchMap[locNext[0]][locNext[1]] = false;
                    }
                    --i;
                }
            }
            locBace = null;
        }
        return -1;
    }
    
    private void _getFront(int[] ary, int d) {
        switch (d) {
            case 0: {
                ary[4] = 2;
                ary[3] = 6;
                ary[2] = 0;
                ary[1] = 1;
                ary[0] = 7;
                break;
            }
            case 1: {
                ary[4] = 2;
                ary[3] = 0;
                ary[ary[2] = 1] = 3;
                ary[0] = 7;
                break;
            }
            case 2: {
                ary[4] = 2;
                ary[3] = 4;
                ary[2] = 0;
                ary[1] = 1;
                ary[0] = 3;
                break;
            }
            case 3: {
                ary[4] = 2;
                ary[3] = 4;
                ary[ary[2] = 1] = 3;
                ary[0] = 5;
                break;
            }
            case 4: {
                ary[4] = 2;
                ary[3] = 4;
                ary[2] = 6;
                ary[1] = 3;
                ary[0] = 5;
                break;
            }
            case 5: {
                ary[4] = 4;
                ary[3] = 6;
                ary[2] = 3;
                ary[1] = 5;
                ary[0] = 7;
                break;
            }
            case 6: {
                ary[4] = 4;
                ary[3] = 6;
                ary[2] = 0;
                ary[1] = 5;
                ary[0] = 7;
                break;
            }
            case 7: {
                ary[4] = 6;
                ary[3] = 0;
                ary[ary[2] = 1] = 5;
                ary[0] = 7;
                break;
            }
        }
    }
    
    private void _moveLocation(int[] ary, int d) {
        switch (d) {
            case 0: {
                --ary[1];
                break;
            }
            case 1: {
                ++ary[0];
                --ary[1];
                break;
            }
            case 2: {
                ++ary[0];
                break;
            }
            case 3: {
                ++ary[0];
                ++ary[1];
                break;
            }
            case 4: {
                ++ary[1];
                break;
            }
            case 5: {
                --ary[0];
                ++ary[1];
                break;
            }
            case 6: {
                --ary[0];
                break;
            }
            case 7: {
                --ary[0];
                --ary[1];
                break;
            }
        }
        ary[2] = d;
    }
    
    private boolean _exsistCharacterBetweenTarget(int dir) {
        try {
            if (!(this._npc instanceof L1MonsterInstance)) {
                return false;
            }
            else if (this._npc.is_now_target() == null) {
                return false;
            }
            else {
                int locX = this._npc.getX();
                int locY = this._npc.getY();
                int targetX = locX + NpcMove.HEADING_TABLE_X[dir];
                int targetY = locY + NpcMove.HEADING_TABLE_Y[dir];
                ArrayList<L1Object> objects = World.get().getVisibleObjects(this._npc, 1);
                for (L1Object object : objects) {
                    boolean isCheck = false;
                    if (object instanceof L1PcInstance) {
                        L1PcInstance pc = (L1PcInstance)object;
                        if (!pc.isGhost()) {
                            isCheck = true;
                        }
                    }
                    else if (object instanceof L1PetInstance) {
                        isCheck = true;
                    }
                    else if (object instanceof L1SummonInstance) {
                        isCheck = true;
                    }
                    if (isCheck && object.getX() == targetX && object.getY() == targetY && object.getMapId() == this._npc.getMapId()) {
                        L1Character cha = (L1Character)object;
                        this._npc._hateList.add(cha, 0);
                        this._npc._target = cha;
                        return true;
                    }
                }
            }
        }
        catch (Exception e) {
            NpcMove._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    @Override
    public int targetReverseDirection(int tx, int ty) {
        int dir = _targetDirection(this._npc.getHeading(), this._npc.getX(), this._npc.getY(), tx, ty);
        return NpcMove.HEADING_RD[dir];
    }
    
    private static int _targetDirection(int h, int x, int y, int tx, int ty) {
        try {
            float dis_x = (float)Math.abs(x - tx);
            float dis_y = (float)Math.abs(y - ty);
            float dis = Math.max(dis_x, dis_y);
            if (dis == 0.0f) {
                return h;
            }
            else {
                int avg_x = (int)Math.floor((double)(dis_x / dis + 0.59f));
                int avg_y = (int)Math.floor((double)(dis_y / dis + 0.59f));
                int dir_x = 0;
                int dir_y = 0;
                if (x < tx) {
                    dir_x = 1;
                }
                if (x > tx) {
                    dir_x = -1;
                }
                if (y < ty) {
                    dir_y = 1;
                }
                if (y > ty) {
                    dir_y = -1;
                }
                if (avg_x == 0) {
                    dir_x = 0;
                }
                if (avg_y == 0) {
                    dir_y = 0;
                }
                switch (dir_x) {
                    case -1: {
                        switch (dir_y) {
                            case -1: {
                                return 7;
                            }
                            case 0: {
                                return 6;
                            }
                            case 1: {
                                return 5;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                    case 0: {
                        switch (dir_y) {
                            case -1: {
                                return 0;
                            }
                            case 1: {
                                return 4;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                    case 1: {
                        switch (dir_y) {
                            case -1: {
                                return 1;
                            }
                            case 0: {
                                return 2;
                            }
                            case 1: {
                                return 3;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            NpcMove._log.error(e.getLocalizedMessage(), e);
        }
        return h;
    }
    
    private boolean glanceCheck(int tx, int ty, int th) {
        L1Map map = this._npc.getMap();
        int chx = this._npc.getX();
        int chy = this._npc.getY();
        int i = 0;
        while (i < 15 && ((chx != tx || chy != ty) && (chx + 1 != tx || chy - 1 != ty) && (chx + 1 != tx || chy != ty) && (chx + 1 != tx || chy + 1 != ty) && (chx != tx || chy + 1 != ty) && (chx - 1 != tx || chy + 1 != ty) && (chx - 1 != tx || chy != ty) && (chx - 1 != tx || chy - 1 != ty)) && (chx != tx || chy - 1 != ty)) {
            if (!map.isPassable(chx, chy, th, this._npc)) {
                return false;
            }
            else if (map.isExistDoor(chx, chy) == 3) {
                return false;
            }
            else {
                if (chx < tx) {
                    if (chy == ty) {
                        ++chx;
                    }
                    else if (chy > ty) {
                        ++chx;
                        --chy;
                    }
                    else if (chy < ty) {
                        ++chx;
                        ++chy;
                    }
                }
                else if (chx == tx) {
                    if (chy < ty) {
                        ++chy;
                    }
                    else if (chy > ty) {
                        --chy;
                    }
                }
                else if (chx > tx) {
                    if (chy == ty) {
                        --chx;
                    }
                    else if (chy < ty) {
                        --chx;
                        ++chy;
                    }
                    else if (chy > ty) {
                        --chx;
                        --chy;
                    }
                }
                ++i;
            }
        }
        return true;
    }
    
    @Override
    public int checkObject(int h) {
        if (!this._npc.isAiRunning()) {
            this._npc.startAI();
        }
        L1Map map = L1WorldMap.get().getMap(this._npc.getMapId());
        int x = this._npc.getX();
        int y = this._npc.getY();
        if (h == 1) {
            if (map.isPassable(x, y, 1, null)) {
                return 1;
            }
            else if (map.isPassable(x, y, 0, null)) {
                return 0;
            }
            else if (map.isPassable(x, y, 2, null)) {
                return 2;
            }
        }
        else if (h == 2) {
            if (map.isPassable(x, y, 2, null)) {
                return 2;
            }
            else if (map.isPassable(x, y, 1, null)) {
                return 1;
            }
            else if (map.isPassable(x, y, 3, null)) {
                return 3;
            }
        }
        else if (h == 3) {
            if (map.isPassable(x, y, 3, null)) {
                return 3;
            }
            else if (map.isPassable(x, y, 2, null)) {
                return 2;
            }
            else if (map.isPassable(x, y, 4, null)) {
                return 4;
            }
        }
        else if (h == 4) {
            if (map.isPassable(x, y, 4, null)) {
                return 4;
            }
            else if (map.isPassable(x, y, 3, null)) {
                return 3;
            }
            else if (map.isPassable(x, y, 5, null)) {
                return 5;
            }
        }
        else if (h == 5) {
            if (map.isPassable(x, y, 5, null)) {
                return 5;
            }
            else if (map.isPassable(x, y, 4, null)) {
                return 4;
            }
            else if (map.isPassable(x, y, 6, null)) {
                return 6;
            }
        }
        else if (h == 6) {
            if (map.isPassable(x, y, 6, null)) {
                return 6;
            }
            else if (map.isPassable(x, y, 5, null)) {
                return 5;
            }
            else if (map.isPassable(x, y, 7, null)) {
                return 7;
            }
        }
        else if (h == 7) {
            if (map.isPassable(x, y, 7, null)) {
                return 7;
            }
            else if (map.isPassable(x, y, 6, null)) {
                return 6;
            }
            else if (map.isPassable(x, y, 0, null)) {
                return 0;
            }
        }
        else if (h == 0) {
            if (map.isPassable(x, y, 0, null)) {
                return 0;
            }
            else if (map.isPassable(x, y, 7, null)) {
                return 7;
            }
            else if (map.isPassable(x, y, 1, null)) {
                return 1;
            }
        }
        return -1;
    }
    
    @Override
    public int openDoor(int h) {
        if (h != -1 && !this._npc.getMap().isDoorPassable(this._npc.getX(), this._npc.getY(), h, this._npc)) {
            this._aie = 26;
            return -1;
        }
        else {
            return h;
        }
    }
}

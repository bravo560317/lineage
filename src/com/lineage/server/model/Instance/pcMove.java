package com.lineage.server.model.Instance;

import java.util.LinkedList;
import com.lineage.server.model.map.L1Map;
import java.util.ArrayList;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.npcai.CheckPath;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_MoveCharPacket;
import org.apache.commons.logging.LogFactory;
import java.util.Iterator;
import java.util.Random;
import org.apache.commons.logging.Log;

public class pcMove extends NpcMoveExecutor
{
    private static final Log _log;
    private static final int _hce = 20;
    private static Random _random;
    private int[] _cxy;
    private int _error;
    private int _aie;
    private byte result;
    private byte resulttele;
    private Iterator<int[]> _list;
    private final L1PcInstance _pc;
    
    static {
        _log = LogFactory.getLog(pcMove.class);
        pcMove._random = new Random();
    }
    
    public pcMove(L1PcInstance pc) {
        this._cxy = null;
        this._error = 0;
        this._aie = 0;
        this.result = 0;
        this.resulttele = 0;
        this._list = null;
        this._pc = pc;
    }
    
    @Override
    public void setDirectionMove(int dir) {
        if (this._pc.hasSkillEffect(4000)) {
            return;
        }
        else if (this._pc.hasSkillEffect(33) || this._pc.hasSkillEffect(50) || this._pc.hasSkillEffect(66) || this._pc.hasSkillEffect(87)) {
            return;
        }
        else if (this._pc.hasSkillEffect(1011)) {
            return;
        }
        else if (this._pc.hasSkillEffect(1009)) {
            return;
        }
        else if (this._pc.hasSkillEffect(4017)) {
            return;
        }
        else if (this._pc.hasSkillEffect(192)) {
            return;
        }
        else if (this._pc.hasSkillEffect(87)) {
            return;
        }
        else if (this._pc.hasSkillEffect(208)) {
            return;
        }
        else if (this._pc.hasSkillEffect(1011)) {
            return;
        }
        else if (this._pc.hasSkillEffect(1009)) {
            return;
        }
        else {
            if (dir >= 0) {
                int locx = this._pc.getX();
                int locy = this._pc.getY();
                locx += pcMove.HEADING_TABLE_X[dir];
                locy += pcMove.HEADING_TABLE_Y[dir];
                this._pc.setHeading(dir);
                this._pc.getMap().setPassable(this._pc.getLocation(), true);
                this._pc.setX(locx);
                this._pc.setY(locy);
                this._pc.getMap().setPassable(this._pc.getLocation(), false);
                this._pc.broadcastPacketAll(new S_MoveCharPacket(this._pc));
                this._pc.sendPackets(new S_MoveCharPacket(this._pc));
                if (this.result > 6) {
                    L1Teleport.teleport(this._pc, this._pc.getLocation(), dir, false);
                    this._pc.targetClear();
                    this.result = 0;
                }
                ++this.result;
            }
            return;
        }
    }
    
    public static void randomTeleport(L1PcInstance pc, boolean effectable) {
        try {
            L1Location newLocation = pc.getLocation().randomLocation(200, true);
            int newX = newLocation.getX();
            int newY = newLocation.getY();
            short mapId = (short)newLocation.getMapId();
            L1Teleport.teleport(pc, newX, newY, mapId, 5, effectable);
        }
        catch (Exception e) {
            pcMove._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void clear() {
        if (this._list != null) {
            this._list = null;
        }
        this._aie = 0;
        this._error = 0;
        this._cxy = null;
    }
    
    @Override
    public int moveDirection(int x, int y) {
        int dir = 0;
        try {
            double d = this._pc.getLocation().getLineDistance(new Point(x, y));
            if (d > 20.0) {
                return -1;
            }
            else if (d > 20.0) {
                dir = this.targetReverseDirection(x, y);
                dir = _targetDirection(this._pc.getHeading(), this._pc.getX(), this._pc.getY(), x, y);
                dir = this.checkObject(dir);
            }
            else {
                dir = this._serchCource(x, y);
                if (this.glanceCheck(x, y, dir)) {
                    this.clear();
                }
                if (dir == -1) {
                    ++this._error;
                    if (this._error != 10) {
                        dir = this.targetReverseDirection(x, y);
                        dir = _targetDirection(this._pc.getHeading(), this._pc.getX(), this._pc.getY(), x, y);
                        if (!this._exsistCharacterBetweenTarget(dir)) {
                            dir = this.checkObject(dir);
                            if (dir == -1) {
                                dir = this._check_targe_re8(x, y, d);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            pcMove._log.error(e.getLocalizedMessage(), e);
        }
        return dir;
    }
    
    private int _check_targe_re8(int x, int y, double d) {
        int dir = this._check_targe(x - 1, y, d);
        if (dir != -1) {
            return dir;
        }
        else {
            dir = this._check_targe(x - 1, y - 1, d);
            if (dir != -1) {
                return dir;
            }
            else {
                dir = this._check_targe(x + 1, y, d);
                if (dir != -1) {
                    return dir;
                }
                else {
                    dir = this._check_targe(x + 1, y + 1, d);
                    if (dir != -1) {
                        return dir;
                    }
                    else {
                        dir = this._check_targe(x + 1, y - 1, d);
                        if (dir != -1) {
                            return dir;
                        }
                        else {
                            dir = this._check_targe(x - 1, y + 1, d);
                            if (dir != -1) {
                                return dir;
                            }
                            else {
                                dir = this._check_targe(x, y - 1, d);
                                if (dir != -1) {
                                    return dir;
                                }
                                else {
                                    dir = this._check_targe(x, y + 1, d);
                                    if (dir != -1) {
                                        return dir;
                                    }
                                    else {
                                        return -1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private int _check_targe(int x, int y, double d) {
        try {
            if (this._pc.is_now_target() == null) {
                this.clear();
                return -1;
            }
            else if (!this._pc.getMap().isPassableDna(x, y, 0)) {
                return -1;
            }
            else if (this._aie <= 20) {
                if (d <= 20.0) {
                    this._list = null;
                    CheckPath maze = new CheckPath(x, y, 20, this._pc);
                    this._cxy = maze.cxy();
                    this._list = (Iterator)maze.findBestPath().iterator();
                    if (!this._list.hasNext()) {
                        ++this._aie;
                        this._list = null;
                        this._cxy = null;
                        return -1;
                    }
                    else {
                        int[] xy = this._list.next();
                        this._list.remove();
                        int cx = this._cxy[0] + xy[0];
                        int cy = this._cxy[1] + xy[1];
                        return _targetDirection(this._pc.getHeading(), this._pc.getX(), this._pc.getY(), cx, cy);
                    }
                }
                else {
                    return -1;
                }
            }
        }
        catch (Exception e) {
            this.clear();
        }
        return -1;
    }
    
    private boolean _exsistCharacterBetweenTarget(int dir) {
        try {
            if (this._pc.is_now_target() == null) {
                return false;
            }
            else {
                int locX = this._pc.getX();
                int locY = this._pc.getY();
                int targetX = locX + pcMove.HEADING_TABLE_X[dir];
                int targetY = locY + pcMove.HEADING_TABLE_Y[dir];
                ArrayList<L1Object> objects = World.get().getVisibleObjects(this._pc, 1);
                for (L1Object object : objects) {
                    boolean isCheck = false;
                    if (object.getX() == targetX && object.getY() == targetY && object.getMapId() == this._pc.getMapId()) {
                        isCheck = true;
                    }
                    if (isCheck) {
                        boolean isHate = false;
                        if (object instanceof L1MonsterInstance) {
                            isHate = true;
                        }
                        if (isHate) {
                            L1Character cha = (L1Character)object;
                            this._pc._hateList.add(cha, 0);
                            this._pc.setNowTarget(cha);
                            return true;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            pcMove._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    @Override
    public int targetReverseDirection(int tx, int ty) {
        int dir = _targetDirection(this._pc.getHeading(), this._pc.getX(), this._pc.getY(), tx, ty);
        return pcMove.HEADING_RD[dir];
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
            pcMove._log.error(e.getLocalizedMessage(), e);
        }
        return h;
    }
    
    private boolean glanceCheck(int tx, int ty, int th) {
        L1Map map = this._pc.getMap();
        int chx = this._pc.getX();
        int chy = this._pc.getY();
        int i = 0;
        while (i < 15 && ((chx != tx || chy != ty) && (chx + 1 != tx || chy - 1 != ty) && (chx + 1 != tx || chy != ty) && (chx + 1 != tx || chy + 1 != ty) && (chx != tx || chy + 1 != ty) && (chx - 1 != tx || chy + 1 != ty) && (chx - 1 != tx || chy != ty) && (chx - 1 != tx || chy - 1 != ty)) && (chx != tx || chy - 1 != ty)) {
            if (!map.isPassable(chx, chy, th, this._pc)) {
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
        if (h >= 0 && h <= 7) {
            int x = this._pc.getX();
            int y = this._pc.getY();
            int h2 = pcMove._heading2[h];
            int h3 = pcMove._heading3[h];
            if (this._pc.getMap().isPassableDna(x, y, h)) {
                return h;
            }
            else if (this._pc.getMap().isPassableDna(x, y, h2)) {
                return h2;
            }
            else if (this._pc.getMap().isPassableDna(x, y, h3)) {
                return h3;
            }
        }
        return -1;
    }
    
    @Override
    public int openDoor(int h) {
        return h;
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
    
    private int _serchCource(int x, int y) {
        int locCenter = 21;
        int diff_x = x - 21;
        int diff_y = y - 21;
        int[] locBace = { this._pc.getX() - diff_x, this._pc.getY() - diff_y, 0, 0 };
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
                            found = this._pc.getMap().isPassable(tmpX, tmpY + 1, i, this._pc);
                            break;
                        }
                        case 1: {
                            found = this._pc.getMap().isPassable(tmpX - 1, tmpY + 1, i, this._pc);
                            break;
                        }
                        case 2: {
                            found = this._pc.getMap().isPassable(tmpX - 1, tmpY, i, this._pc);
                            break;
                        }
                        case 3: {
                            found = this._pc.getMap().isPassable(tmpX - 1, tmpY - 1, i, this._pc);
                            break;
                        }
                        case 4: {
                            found = this._pc.getMap().isPassable(tmpX, tmpY - 1, i, this._pc);
                            break;
                        }
                        case 5: {
                            found = this._pc.getMap().isPassable(tmpX + 1, tmpY - 1, i, this._pc);
                            break;
                        }
                        case 6: {
                            found = this._pc.getMap().isPassable(tmpX + 1, tmpY, i, this._pc);
                            break;
                        }
                        case 7: {
                            found = this._pc.getMap().isPassable(tmpX + 1, tmpY + 1, i, this._pc);
                            break;
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
                                found = this._pc.getMap().isPassable(tmpX, tmpY + 1, i, this._pc);
                                break;
                            }
                            case 1: {
                                found = this._pc.getMap().isPassable(tmpX - 1, tmpY + 1, i, this._pc);
                                break;
                            }
                            case 2: {
                                found = this._pc.getMap().isPassable(tmpX - 1, tmpY, i, this._pc);
                                break;
                            }
                            case 3: {
                                found = this._pc.getMap().isPassable(tmpX - 1, tmpY - 1, i, this._pc);
                                break;
                            }
                            case 4: {
                                found = this._pc.getMap().isPassable(tmpX, tmpY - 1, i, this._pc);
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
}

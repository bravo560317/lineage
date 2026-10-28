package com.lineage.server.model.Instance;

import com.lineage.server.serverpackets.S_Fishing;
import java.util.ArrayList;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1NewMap;
import com.lineage.server.utils.NewMapUtil;
import java.util.Random;

public class L1FishingPc extends L1PcInstance
{
    private static final Random R;
    private static final long serialVersionUID = 4798707454231555948L;
    private int fx;
    private int fy;
    
    static {
        R = new Random();
    }
    
    public final void join() {
        this.setMap((short)5300);
        ArrayList<L1NewMap> maps = NewMapUtil.getBlock((int)this.getMapId());
        int size = maps.size();
        int x = 0;
        int y = 0;
        if (size > 0) {
            L1NewMap map = maps.get(L1FishingPc.R.nextInt(size));
            int f = 1;
            int h = 0;
            while (true) {
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.fx = map.getX();
                this.fy = map.getY();
                x = L1FishingPc.R.nextInt(64);
                y = L1FishingPc.R.nextInt(64);
                f = L1FishingPc.R.nextInt(3) + 2;
                h = L1FishingPc.R.nextInt(8);
                if ((map.getTileType(x, y) & 0x1) != 0x1 && map.getX() + x <= 32805) {
                    this.setX(map.getX() + x);
                    this.setY(map.getY() + y);
                    if (map.getTileType(x + f, y, 64) && h == 2) {
                        this.fx += x + f;
                        this.fy += y;
                        this.setHeading(2);
                        break;
                    }
                    else if (map.getTileType(x - f, y, 64) && h == 6) {
                        this.fx += x - f;
                        this.fy += y;
                        this.setHeading(6);
                        break;
                    }
                    else if (map.getTileType(x, y - f, 64) && h == 4) {
                        this.fx += x;
                        this.fy += y + f;
                        this.setHeading(4);
                        break;
                    }
                    else if (map.getTileType(x, y - f, 64) && h == 0) {
                        this.fx += x;
                        this.fy += y - f;
                        this.setHeading(0);
                        break;
                    }
                    else if (map.getTileType(x + f, y + f, 64) && h == 3) {
                        this.fx += x + f;
                        this.fy += y + f;
                        this.setHeading(3);
                        break;
                    }
                    else if (map.getTileType(x - f, y - f, 64) && h == 7) {
                        this.fx += x - f;
                        this.fy += y - f;
                        this.setHeading(7);
                        break;
                    }
                    else if (map.getTileType(x + f, y - f, 64) && h == 1) {
                        this.fx += x + f;
                        this.fy += y - f;
                        this.setHeading(1);
                        break;
                    }
                    else if (map.getTileType(x - f, y + f, 64) && h == 5) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
            this.fx += x - f;
            this.fy += y + f;
            this.setHeading(5);
            this.setTempCharGfx(this.getClassId());
            this.setX(x + map.getX());
            this.setY(y + map.getY());
            World.get().storeObject(this);
            World.get().addVisibleObject(this);
        }
    }
    
    @Override
    public final void onPerceive(L1PcInstance perceivedFrom) {
        super.onPerceive(perceivedFrom);
        this.broadcastPacketAll(new S_Fishing(this.getId(), 71, this.fx, this.fy));
    }
}

package com.lineage.data.event.gambling;

import com.lineage.server.types.Point;

public class GamblingConfig
{
    public static boolean ISGFX;
    public static final int[] NPCID;
    public static int[][] GFXD;
    public static int[][] GFX;
    public static final Point[][] TGLOC;
    
    static {
        GamblingConfig.ISGFX = true;
        NPCID = new int[] { 86100, 86101, 86102, 86103, 86104, 86105, 86106, 86107, 86108, 86109, 86110, 86111, 86112, 86113, 86114, 86115, 86116, 86117, 86118, 86119 };
        GamblingConfig.GFXD = new int[][] { { 3478, 3479, 3480, 3481 }, { 3497, 3501, 3505, 3509 }, { 3498, 3502, 3506, 3510 }, { 3499, 3503, 3507, 3511 }, { 3500, 3504, 3508, 3512 } };
        GamblingConfig.GFX = new int[][] { { 1353, 1355, 1357, 1359 }, { 1461, 1465, 1469, 1473 }, { 1462, 1466, 1470, 1474 }, { 1463, 1467, 1471, 1475 }, { 1464, 1468, 1472, 1476 } };
        TGLOC = new Point[][] { { new Point(33521, 32861), new Point(33478, 32861), new Point(33473, 32858), new Point(33473, 32853), new Point(33472, 32852), new Point(33478, 32846), new Point(33490, 32847), new Point(33524, 32847) }, { new Point(33519, 32863), new Point(33478, 32863), new Point(33472, 32859), new Point(33472, 32853), new Point(33478, 32846), new Point(33497, 32846), new Point(33513, 32845), new Point(33524, 32845) }, { new Point(33517, 32865), new Point(33478, 32865), new Point(33471, 32860), new Point(33471, 32853), new Point(33478, 32846), new Point(33497, 32844), new Point(33513, 32843), new Point(33524, 32843) }, { new Point(33515, 32867), new Point(33478, 32867), new Point(33470, 32859), new Point(33469, 32853), new Point(33477, 32845), new Point(33497, 32843), new Point(33513, 32841), new Point(33524, 32841) }, { new Point(33513, 32869), new Point(33479, 32869), new Point(33470, 32860), new Point(33469, 32852), new Point(33480, 32841), new Point(33497, 32841), new Point(33513, 32840), new Point(33524, 32839) } };
    }
}

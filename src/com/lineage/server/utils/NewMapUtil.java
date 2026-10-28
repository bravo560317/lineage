package com.lineage.server.utils;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.io.FileFilter;
import java.io.File;
import com.lineage.server.templates.L1NewMap;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class NewMapUtil
{
    private static ConcurrentHashMap<Integer, ArrayList<L1NewMap>> M;
    
    static {
        NewMapUtil.M = new ConcurrentHashMap();
    }
    
    public static void load(String path) throws IOException {
        File f = new File(path);
        if (!f.isDirectory() || !f.exists()) {
            throw new NullPointerException("錯誤的地圖路徑或地圖不存在！");
        }
        else {
            FileChannel fc = null;
            File[] listFiles;
            for (int length = (listFiles = f.listFiles(new FileFilter() {
                @Override
                public final boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            })).length, i = 0; i < length; ++i) {
                File map = listFiles[i];
                int mapId = 0;
                try {
                    mapId = Integer.parseInt(map.getName());
                }
                catch (NumberFormatException e) {
                    continue;
                }
                ArrayList<L1NewMap> maps = new ArrayList();
                NewMapUtil.M.put(mapId, maps);
                File[] listFiles2;
                for (int length2 = (listFiles2 = map.listFiles(new FileFilter() {
                    @Override
                    public final boolean accept(File pathname) {
                        return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".bin");
                    }
                })).length, j = 0; j < length2; ++j) {
                    File fileMap = listFiles2[j];
                    ByteBuffer buf = ByteBuffer.allocate(4096);
                    fc = new FileInputStream(fileMap).getChannel();
                    fc.read(buf);
                    fc.close();
                    int x = Integer.parseInt(fileMap.getName().substring(0, 4), 16) & 0xFFFF;
                    int y = Integer.parseInt(fileMap.getName().substring(4, 8), 16) & 0xFFFF;
                    maps.add(new L1NewMap(x, y, buf.array()));
                }
            }
            System.out.println(NewMapUtil.class.getName() + ": 加載 " + NewMapUtil.M.size() + " 張地圖。");
            return;
        }
    }
    
    public static ArrayList<L1NewMap> getBlock(int mapId) {
        if (NewMapUtil.M.containsKey((Integer)mapId)) {
            return NewMapUtil.M.get((Integer)mapId);
        }
        else {
            return null;
        }
    }
}

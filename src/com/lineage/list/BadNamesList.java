package com.lineage.list;

import java.util.Iterator;
import com.lineage.server.utils.StreamUtil;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class BadNamesList
{
    private static final Log _log;
    private static BadNamesList _instance;
    private ArrayList<String> _nameList;
    
    static {
        _log = LogFactory.getLog(BadNamesList.class);
    }
    
    public BadNamesList() {
        this._nameList = new ArrayList();
    }
    
    public static BadNamesList get() {
        if (BadNamesList._instance == null) {
            BadNamesList._instance = new BadNamesList();
        }
        return BadNamesList._instance;
    }
    
    public void load() {
        LineNumberReader lnr = null;
        try {
            File file = new File("data/badnames.txt");
            FileInputStream fileIn = new FileInputStream(file);
            InputStreamReader inputStream = new InputStreamReader(fileIn, "utf8");
            lnr = new LineNumberReader(inputStream);
            boolean isWhile = false;
            String line = null;
            while ((line = lnr.readLine()) != null) {
                if (!isWhile) {
                    isWhile = true;
                }
                else if (line.trim().length() != 0) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    else {
                        StringTokenizer st = new StringTokenizer(line, ";");
                        String ban = st.nextToken();
                        this._nameList.add(ban);
                    }
                }
                else {
                    continue;
                }
            }
            BadNamesList._log.info("載入禁止名稱數量: " + this._nameList.size());
        }
        catch (FileNotFoundException e) {
            BadNamesList._log.error(e.getLocalizedMessage(), e);
        }
        catch (Exception e2) {
            BadNamesList._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            StreamUtil.close(new Closeable[] { lnr });
        }
    }
    
    public boolean isBadName(String name) {
        String checkName = name.toLowerCase();
        for (String badName : this._nameList) {
            if (checkName.indexOf(badName.toLowerCase()) != -1) {
                BadNamesList._log.info("新建人物名稱包含禁用字元: " + this._nameList.size());
                return true;
            }
        }
        return false;
    }
    
    public String[] getAllBadNames() {
        return (String[])this._nameList.toArray(new String[this._nameList.size()]);
    }
}

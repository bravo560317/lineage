package com.lineage.data;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.BinaryOutputStream;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.data.executor.ItemExecutor;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ItemClass
{
    private static final Log _log;
    private static final Map<Integer, ItemExecutor> _classList;
    private static ItemClass _instance;
    
    static {
        _log = LogFactory.getLog(ItemClass.class);
        _classList = new HashMap();
    }
    
    public static ItemClass get() {
        if (ItemClass._instance == null) {
            ItemClass._instance = new ItemClass();
        }
        return ItemClass._instance;
    }
    
    public void addList(int itemid, String className, int mode) {
        if (className.equals("0")) {
            return;
        }
        else {
            try {
                String newclass = className;
                String[] set = (String[])null;
                if (className.indexOf(" ") != -1) {
                    set = className.split(" ");
                    newclass = set[0];
                }
                StringBuilder stringBuilder = new StringBuilder();
                switch (mode) {
                    case 0: {
                        stringBuilder.append("com.lineage.data.item_etcitem.");
                        break;
                    }
                    case 1: {
                        stringBuilder.append("com.lineage.data.item_weapon.");
                        break;
                    }
                    case 2: {
                        stringBuilder.append("com.lineage.data.item_armor.");
                        break;
                    }
                }
                stringBuilder.append(newclass);
                Class<?> cls = Class.forName(stringBuilder.toString());
                ItemExecutor exe = (ItemExecutor)cls.getMethod("get", new Class[0]).invoke(null, new Object[0]);
                if (set != null) {
                    exe.set_set(set);
                }
                ItemClass._classList.put(new Integer(itemid), exe);
            }
            catch (ClassNotFoundException e) {
                String error = "發生[道具檔案]錯誤, 檢查檔案是否存在:" + className + " ItemId:" + itemid;
                ItemClass._log.error(error);
                DataError.isError(ItemClass._log, error, e);
            }
            catch (IllegalArgumentException e2) {
                ItemClass._log.error(e2.getLocalizedMessage(), e2);
            }
            catch (IllegalAccessException e3) {
                ItemClass._log.error(e3.getLocalizedMessage(), e3);
            }
            catch (InvocationTargetException e4) {
                ItemClass._log.error(e4.getLocalizedMessage(), e4);
            }
            catch (SecurityException e5) {
                ItemClass._log.error(e5.getLocalizedMessage(), e5);
            }
            catch (NoSuchMethodException e6) {
                ItemClass._log.error(e6.getLocalizedMessage(), e6);
            }
            return;
        }
    }
    
    public BinaryOutputStream status(L1ItemInstance item) {
        if (item == null) {
            return null;
        }
        else {
            try {
                ItemExecutor exe = ItemClass._classList.get(new Integer(item.getItemId()));
                if (exe != null) {
                    return exe.itemStatus(item);
                }
            }
            catch (Exception e) {
                ItemClass._log.error(e.getLocalizedMessage(), e);
            }
            return null;
        }
    }
    
    public void item(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null) {
            return;
        }
        else if (item == null) {
            return;
        }
        else {
            try {
                ItemExecutor exe = ItemClass._classList.get(new Integer(item.getItemId()));
                if (exe != null) {
                    exe.execute(data, pc, item);
                }
            }
            catch (Exception e) {
                ItemClass._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    public void item_weapon(boolean equipped, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null) {
            return;
        }
        else if (item == null) {
            return;
        }
        else {
            try {
                ItemExecutor exe = ItemClass._classList.get(new Integer(item.getItemId()));
                if (exe != null) {
                    int[] data = { equipped ? 1 : 0 };
                    exe.execute(data, pc, item);
                }
            }
            catch (Exception e) {
                ItemClass._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    public void item_armor(boolean equipped, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null) {
            return;
        }
        else if (item == null) {
            return;
        }
        else {
            try {
                ItemExecutor exe = ItemClass._classList.get(new Integer(item.getItemId()));
                if (exe != null) {
                    int[] data = { equipped ? 1 : 0 };
                    exe.execute(data, pc, item);
                }
            }
            catch (Exception e) {
                ItemClass._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
}

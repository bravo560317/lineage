package com.lineage.data;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Quest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.data.executor.QuestExecutor;
import java.util.Map;
import org.apache.commons.logging.Log;

public class QuestClass
{
    private static final Log _log;
    private static final Map<Integer, QuestExecutor> _classList;
    private static QuestClass _instance;
    
    static {
        _log = LogFactory.getLog(QuestClass.class);
        _classList = new HashMap();
    }
    
    public static QuestClass get() {
        if (QuestClass._instance == null) {
            QuestClass._instance = new QuestClass();
        }
        return QuestClass._instance;
    }
    
    public void addList(int questid, String className) {
        if (className.equals("0")) {
            return;
        }
        else {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("com.lineage.data.quest.");
                stringBuilder.append(className);
                Class<?> cls = Class.forName(stringBuilder.toString());
                QuestExecutor exe = (QuestExecutor)cls.getMethod("get", new Class[0]).invoke(null, new Object[0]);
                QuestClass._classList.put(new Integer(questid), exe);
            }
            catch (ClassNotFoundException e) {
                String error = "發生[Quest(任務)檔案]錯誤, 檢查檔案是否存在:" + className + " QuestId:" + questid;
                QuestClass._log.error(error);
                DataError.isError(QuestClass._log, error, e);
            }
            catch (IllegalArgumentException e2) {
                QuestClass._log.error(e2.getLocalizedMessage(), e2);
            }
            catch (IllegalAccessException e3) {
                QuestClass._log.error(e3.getLocalizedMessage(), e3);
            }
            catch (InvocationTargetException e4) {
                QuestClass._log.error(e4.getLocalizedMessage(), e4);
            }
            catch (SecurityException e5) {
                QuestClass._log.error(e5.getLocalizedMessage(), e5);
            }
            catch (NoSuchMethodException e6) {
                QuestClass._log.error(e6.getLocalizedMessage(), e6);
            }
            return;
        }
    }
    
    public void execute(L1Quest quest) {
        try {
            QuestExecutor exe = QuestClass._classList.get(new Integer(quest.get_id()));
            if (exe != null) {
                exe.execute(quest);
            }
        }
        catch (Exception e) {
            QuestClass._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void startQuest(L1PcInstance pc, int questid) {
        try {
            QuestExecutor exe = QuestClass._classList.get(new Integer(questid));
            if (exe != null) {
                exe.startQuest(pc);
            }
        }
        catch (Exception e) {
            QuestClass._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void endQuest(L1PcInstance pc, int questid) {
        try {
            QuestExecutor exe = QuestClass._classList.get(new Integer(questid));
            if (exe != null) {
                exe.endQuest(pc);
            }
        }
        catch (Exception e) {
            QuestClass._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void showQuest(L1PcInstance pc, int questid) {
        try {
            QuestExecutor exe = QuestClass._classList.get(new Integer(questid));
            if (exe != null) {
                exe.showQuest(pc);
            }
        }
        catch (Exception e) {
            QuestClass._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void stopQuest(L1PcInstance pc, int questid) {
        try {
            QuestExecutor exe = QuestClass._classList.get(new Integer(questid));
            if (exe != null) {
                exe.stopQuest(pc);
            }
        }
        catch (Exception e) {
            QuestClass._log.error(e.getLocalizedMessage(), e);
        }
    }
}

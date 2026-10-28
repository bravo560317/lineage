package com.lineage.data.item_etcitem.wand;

import com.lineage.server.thread.DeAiThreadPool;
import com.lineage.server.model.L1Object;
import com.lineage.server.command.GmHtml;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1DeInstance;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class GM_Blink extends ItemExecutor
{
    private static final Log _log;
    private static final Map<Integer, L1DeInstance> _list;
    
    static {
        _log = LogFactory.getLog(GM_Blink.class);
        _list = new HashMap();
    }
    
    public static ItemExecutor get() {
        return new GM_Blink();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int targObjId = data[0];
            L1Object target = World.get().findObject(targObjId);
            if (target == null) {
                L1DeInstance de = pc.get_outChat();
                if (de != null) {
                    int spellsc_x = data[1];
                    int spellsc_y = data[2];
                    if (de.isShop()) {
                        pc.sendPackets(new S_ServerMessage(166, de.getNameId() + "正在商店模式"));
                        return;
                    }
                    else if (de.isFishing()) {
                        pc.sendPackets(new S_ServerMessage(166, de.getNameId() + "正在釣魚模式"));
                        return;
                    }
                    else if (GM_Blink._list.get((Integer)de.getId()) != null) {
                        pc.sendPackets(new S_ServerMessage("該NPC還未完成上一次移動命令"));
                        return;
                    }
                    else if (de.getX() != spellsc_x && de.getY() != spellsc_y) {
                        GM_Blink.MoveTimer moveTimer = new GM_Blink.MoveTimer(de, spellsc_x, spellsc_y);
                        moveTimer.start();
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(79));
                }
                return;
            }
            else {
                if (target instanceof L1PcInstance) {
                    L1PcInstance tgpc = (L1PcInstance)target;
                    if (tgpc.equals(pc)) {
                        pc.set_outChat(null);
                        pc.sendPackets(new S_ServerMessage("\\fY解除控制"));
                        pc.get_other().set_gmHtml(null);
                        return;
                    }
                }
                if (target instanceof L1DeInstance) {
                    L1DeInstance de = (L1DeInstance)target;
                    pc.set_outChat(de);
                    pc.setSkillEffect(85578, 20000);
                    pc.sendPackets(new S_ServerMessage("\\fY控制對象:" + de.getNameId()));
                    pc.sendPackets(new S_ServerMessage("限制控制假人時間20秒後解除"));
                    GmHtml gmHtml = new GmHtml(pc, 1);
                    gmHtml.show(de);
                    return;
                }
            }
        }
        catch (Exception e) {
            GM_Blink._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private class MoveTimer implements Runnable
    {
        private final L1DeInstance _de;
        private final int _spellsc_x;
        private final int _spellsc_y;
        
        private MoveTimer(L1DeInstance de, int spellsc_x, int spellsc_y) {
            GM_Blink._list.put((Integer)de.getId(), de);
            this._de = de;
            this._spellsc_x = spellsc_x;
            this._spellsc_y = spellsc_y;
        }
        
        private void start() {
            DeAiThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            try {
                int i = 10;
                while (this._de != null) {
                    int moveDirection = this._de.getMove().moveDirection(this._spellsc_x, this._spellsc_y);
                    int dir = this._de.getMove().checkObject(moveDirection);
                    if (dir != -1) {
                        this._de.getMove().setDirectionMove(dir);
                        this._de.setNpcSpeed();
                    }
                    Thread.sleep((long)this._de.calcSleepTime(this._de.getPassispeed(), 0));
                    if (--i <= 0) {
                        break;
                    }
                    else if (this._de.getX() == this._spellsc_x) {
                        break;
                    }
                    else if (this._de.getY() == this._spellsc_y) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
            catch (Exception e) {
                GM_Blink._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                GM_Blink._list.remove((Integer)this._de.getId());
            }
        }
    }
}

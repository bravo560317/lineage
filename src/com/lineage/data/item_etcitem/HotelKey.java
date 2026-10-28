package com.lineage.data.item_etcitem;

import java.sql.Timestamp;
import com.lineage.server.templates.L1Inn;
import com.lineage.server.model.L1Teleport;
import java.util.Calendar;
import com.lineage.server.datatables.InnTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class HotelKey extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(HotelKey.class);
    }
    
    public static ItemExecutor get() {
        return new HotelKey();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            if (item == null) {
                return;
            }
            else if (pc == null) {
                return;
            }
            else {
                for (int i = 0; i < 16; ++i) {
                    L1Inn inn = InnTable.getInstance().getTemplate(item.getInnNpcId(), i);
                    if (inn.getKeyId() == item.getKeyId()) {
                        Timestamp dueTime = item.getDueTime();
                        if (dueTime != null) {
                            Calendar cal = Calendar.getInstance();
                            if ((cal.getTimeInMillis() - dueTime.getTime()) / 1000L < 0L) {
                                int[] locdata = (int[])null;
                                switch (item.getInnNpcId()) {
                                    case 70012: {
                                        locdata = new int[] { 32745, 32803, 16384, 32743, 32808, 16896 };
                                        break;
                                    }
                                    case 70019: {
                                        locdata = new int[] { 32743, 32803, 17408, 32744, 32807, 17920 };
                                        break;
                                    }
                                    case 70031: {
                                        locdata = new int[] { 32744, 32803, 18432, 32744, 32807, 18944 };
                                        break;
                                    }
                                    case 70054: {
                                        locdata = new int[] { 32744, 32803, 23552, 32744, 32807, 24064 };
                                        break;
                                    }
                                    case 70065: {
                                        locdata = new int[] { 32744, 32803, 19456, 32744, 32807, 19968 };
                                        break;
                                    }
                                    case 70070: {
                                        locdata = new int[] { 32744, 32803, 20480, 32744, 32807, 20992 };
                                        break;
                                    }
                                    case 70075: {
                                        locdata = new int[] { 32744, 32803, 21504, 32744, 32807, 22016 };
                                        break;
                                    }
                                    case 70084: {
                                        locdata = new int[] { 32744, 32803, 22528, 32744, 32807, 23040 };
                                        break;
                                    }
                                    case 70096: {
                                        locdata = new int[] { 32744, 32803, 24576, 32744, 32807, 25088 };
                                        break;
                                    }
                                }
                                if (!item.checkRoomOrHall()) {
                                    pc.set_showId(item.getKeyId());
                                    L1Teleport.teleport(pc, locdata[0], locdata[1], (short)locdata[2], 6, true);
                                    break;
                                }
                                else {
                                    pc.set_showId(item.getKeyId());
                                    L1Teleport.teleport(pc, locdata[3], locdata[4], (short)locdata[5], 6, true);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            HotelKey._log.error(e.getLocalizedMessage(), e);
        }
    }
}

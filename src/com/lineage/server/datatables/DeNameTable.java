package com.lineage.server.datatables;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.templates.DeClan;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.config.Config;
import com.lineage.DatabaseFactoryLogin;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.templates.DeName;
import java.util.Map;
import org.apache.commons.logging.Log;

public class DeNameTable
{
    private static final Log _log;
    private static final Map<String, DeName> _denameList;
    private static final Map<Integer, DeName> _denames;
    private static DeNameTable _instance;
    private static boolean _reclanInfo;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(DeNameTable.class);
        _denameList = new HashMap();
        _denames = new HashMap();
        DeNameTable._reclanInfo = false;
        _random = new Random();
    }
    
    public static DeNameTable get() {
        if (DeNameTable._instance == null) {
            DeNameTable._instance = new DeNameTable();
        }
        return DeNameTable._instance;
    }
    
    public void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `de_name`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int deobjid = rs.getInt("deobjid");
                String name = "";
                if (Config.CLIENT_LANGUAGE == 3) {
                    name = rs.getString("denamebig5");
                }
                else {
                    name = rs.getString("dename");
                }
                int type = rs.getInt("type");
                int sex = rs.getInt("sex");
                int clanid = rs.getInt("clanid");
                DeName deName = new DeName(deobjid, name, type, sex, clanid);
                if (DeNameTable._denameList.get(name) == null) {
                    DeNameTable._denameList.put(name, deName);
                    DeNameTable._denames.put(deobjid, deName);
                    CharObjidTable.get().addChar(deobjid, name);
                }
                else {
                    this.del(deobjid);
                }
                if (DeNameTable._reclanInfo) {
                    this.reclanInfo(clanid, deName);
                }
            }
        }
        catch (SQLException e) {
            DeNameTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void del(int deobjid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `de_name` WHERE `deobjid`=?");
            pstm.setInt(1, deobjid);
            pstm.execute();
        }
        catch (SQLException e) {
            DeNameTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void reclanInfo(int clanid, DeName deName) {
        DeClan deClan = DeClanTable.get().get(clanid);
        if (deClan != null) {
            deName.set_clanid(deClan.get_clan_id());
            deName.set_type(0);
            if (DeNameTable._random.nextBoolean()) {
                deName.set_sex(0);
            }
            else {
                deName.set_sex(1);
            }
        }
        else {
            if (DeNameTable._random.nextInt(100) < 40) {
                Collection<DeClan> list = DeClanTable.get().getList();
                int i = DeNameTable._random.nextInt(list.size());
                int x = 0;
                for (DeClan dxeClan : list) {
                    if (++x == i) {
                        deName.set_clanid(dxeClan.get_clan_id());
                    }
                }
            }
            else {
                deName.set_clanid(0);
            }
            int itype = DeNameTable._random.nextInt(6) + 1;
            deName.set_type(itype);
            if (DeNameTable._random.nextBoolean()) {
                deName.set_sex(0);
            }
            else {
                deName.set_sex(1);
            }
        }
        this.updata_deName(deName);
    }
    
    private void updata_deName(DeName deName) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactoryLogin.get().getConnection();
            ps = cn.prepareStatement("UPDATE `de_name` SET `type`=?,`sex`=?,`clanid`=? WHERE `deobjid`=?");
            int i = 0;
            ps.setInt(++i, deName.get_type());
            ps.setInt(++i, deName.get_sex());
            ps.setInt(++i, deName.get_clanid());
            ps.setInt(++i, deName.get_deobjid());
            ps.execute();
        }
        catch (SQLException e) {
            DeNameTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public DeName getDeName(int objid) {
        return DeNameTable._denames.get((Integer)objid);
    }
    
    public DeName getDeName(String name) {
        return DeNameTable._denameList.get(name);
    }
    
    public Collection<DeName> getList() {
        return (Collection)DeNameTable._denameList.values();
    }
    
    public DeName[] getDeNameList() {
        return (DeName[])DeNameTable._denameList.values().toArray(new DeName[DeNameTable._denameList.size()]);
    }
}

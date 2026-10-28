package com.lineage.server.clientpackets;

import com.lineage.server.model.L1Object;
import com.lineage.server.templates.L1Account;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_RetrieveChaList;
import com.lineage.server.serverpackets.S_RetrievePledgeList;
import com.lineage.server.serverpackets.S_RetrieveList;
import com.lineage.server.serverpackets.S_RetrieveElfList;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import org.apache.commons.logging.Log;

public class C_Password extends ClientBasePacket
{
    private static final Log _log;
    private static final List<Integer> password;
    
    static {
        _log = LogFactory.getLog(C_Password.class);
        (password = new ArrayList()).add(0, 994303243);
        C_Password.password.add(1, 994303242);
        C_Password.password.add(2, 994303241);
        C_Password.password.add(3, 994303240);
        C_Password.password.add(4, 994303247);
        C_Password.password.add(5, 994303246);
        C_Password.password.add(6, 994303245);
        C_Password.password.add(7, 994303244);
        C_Password.password.add(8, 994303235);
        C_Password.password.add(9, 994303234);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc == null) {
                return;
            }
            else {
                int type = this.readC();
                int pass1 = C_Password.password.indexOf((Integer)this.readD()) * 100000 + C_Password.password.indexOf((Integer)this.readD()) * 10000 + C_Password.password.indexOf((Integer)this.readD()) * 1000 + C_Password.password.indexOf((Integer)this.readD()) * 100 + C_Password.password.indexOf((Integer)this.readD()) * 10 + C_Password.password.indexOf((Integer)this.readD());
                L1Account account = client.getAccount();
                if (type == 0) {
                    int pass2 = C_Password.password.indexOf((Integer)this.readD()) * 100000 + C_Password.password.indexOf((Integer)this.readD()) * 10000 + C_Password.password.indexOf((Integer)this.readD()) * 1000 + C_Password.password.indexOf((Integer)this.readD()) * 100 + C_Password.password.indexOf((Integer)this.readD()) * 10 + C_Password.password.indexOf((Integer)this.readD());
                    if (pass1 < 0 && pass2 < 0) {
                        pc.sendPackets(new S_ServerMessage(79));
                    }
                    else if (pass1 < 0 && account.get_warehouse() == 0) {
                        account.set_warehouse(pass2);
                        AccountReading.get().updateWarehouse(account.get_login(), pass2);
                        pc.sendPackets(new S_SystemMessage("倉庫密碼設定完成，請牢記您的新密碼。"));
                    }
                    else if (pass1 > 0 && pass1 == account.get_warehouse()) {
                        if (pass1 == pass2) {
                            pc.sendPackets(new S_ServerMessage(342));
                            this.stopAction(client, pc);
                            return;
                        }
                        else {
                            account.set_warehouse(pass2);
                            AccountReading.get().updateWarehouse(account.get_login(), pass2);
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(835));
                        int error = client.get_error();
                        client.set_error(error + 1); // set_error 內部已有踢除機制
                        C_Password._log.error(pc.getName() + " 倉庫密碼輸入錯誤!!( " + client.get_error() + " 次)");
                    }
                }
                else if (account.get_warehouse() == pass1) {
                    int objid = this.readD();
                    if (pc.getLevel() >= 5) {
                        if (type == 1) {
                            L1Object obj = World.get().findObject(objid);
                            if (obj != null && obj instanceof L1NpcInstance) {
                                L1NpcInstance npc = (L1NpcInstance)obj;
                                switch (npc.getNpcId()) {
                                    case 60028: {
                                        if (!pc.isElf()) {
                                            break;
                                        }
                                        else {
                                            pc.sendPackets(new S_RetrieveElfList(objid, pc));
                                            break;
                                        }
                                    }
                                    default: {
                                        pc.sendPackets(new S_RetrieveList(objid, pc));
                                        break;
                                    }
                                }
                            }
                        }
                        else if (type == 2) {
                            if (pc.getClanid() == 0) {
                                pc.sendPackets(new S_ServerMessage(208));
                                return;
                            }
                            else if (pc.getClanRank() == 2) {
                                pc.sendPackets(new S_ServerMessage(728));
                                return;
                            }
                            else {
                                pc.sendPackets(new S_RetrievePledgeList(objid, pc));
                            }
                        }
                        else if (type == 4) {
                            pc.sendPackets(new S_RetrieveChaList(objid, pc));
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(835));
                }
                this.stopAction(client, pc);
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    private void stopAction(ClientExecutor client, L1PcInstance pc) {
        pc.setTempID(0);
        client.set_error(0);
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

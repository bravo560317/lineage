package com.lineage.server.clientpackets;

import com.lineage.server.utils.CalcStat;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_InitialAbilityGrowth;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.utils.CalcInitHpMp;
import com.lineage.data.event.BaseResetSet;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_CharReset extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_CharReset.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            int stage = this.readC();
            switch (stage) {
                case 1: {
                    int str = this.readC();
                    int intel = this.readC();
                    int wis = this.readC();
                    int dex = this.readC();
                    int con = this.readC();
                    int cha = this.readC();
                    int hp = 0;
                    int mp = 0;
                    if (BaseResetSet.RETAIN != 0) {
                        hp = pc.getMaxHp() * BaseResetSet.RETAIN / 100;
                        mp = pc.getMaxMp() * BaseResetSet.RETAIN / 100;
                    }
                    else {
                        hp = CalcInitHpMp.calcInitHp(pc);
                        mp = CalcInitHpMp.calcInitMp(pc);
                    }
                    pc.sendPackets(new S_CharReset(pc, 1, hp, mp, 10, str, intel, wis, dex, con, cha));
                    this.initCharStatus(pc, hp, mp, str, intel, wis, dex, con, cha);
                    CharacterTable.get();
                    CharacterTable.saveCharStatus(pc);
                    break;
                }
                case 2: {
                    int type2 = this.readC();
                    switch (type2) {
                        case 0: {
                            this.setLevelUp(pc, 1);
                            break;
                        }
                        case 1: {
                            pc.addBaseStr(1);
                            this.setLevelUp(pc, 1);
                            break;
                        }
                        case 2: {
                            pc.addBaseInt(1);
                            this.setLevelUp(pc, 1);
                            break;
                        }
                        case 3: {
                            pc.addBaseWis(1);
                            this.setLevelUp(pc, 1);
                            break;
                        }
                        case 4: {
                            pc.addBaseDex(1);
                            this.setLevelUp(pc, 1);
                            break;
                        }
                        case 5: {
                            pc.addBaseCon(1);
                            this.setLevelUp(pc, 1);
                            break;
                        }
                        case 6: {
                            pc.addBaseCha(1);
                            this.setLevelUp(pc, 1);
                            break;
                        }
                        case 7: {
                            if (pc.getTempMaxLevel() - pc.getTempLevel() < 10) {
                                break;
                            }
                            else {
                                this.setLevelUp(pc, 10);
                                break;
                            }
                        }
                        case 8: {
                            switch (this.readC()) {
                                case 1: {
                                    pc.addBaseStr(1);
                                    break;
                                }
                                case 2: {
                                    pc.addBaseInt(1);
                                    break;
                                }
                                case 3: {
                                    pc.addBaseWis(1);
                                    break;
                                }
                                case 4: {
                                    pc.addBaseDex(1);
                                    break;
                                }
                                case 5: {
                                    pc.addBaseCon(1);
                                    break;
                                }
                                case 6: {
                                    pc.addBaseCha(1);
                                    break;
                                }
                            }
                            if (pc.getElixirStats() > 0) {
                                pc.sendPackets(new S_CharReset(pc.getElixirStats()));
                                break;
                            }
                            else {
                                this.saveNewCharStatus(pc);
                                break;
                            }
                        }
                    }
                    break;
                }
                case 3: {
                    int read1 = this.readC();
                    int read2 = this.readC();
                    int read3 = this.readC();
                    int read4 = this.readC();
                    int read5 = this.readC();
                    int read6 = this.readC();
                    pc.addBaseStr((int)(byte)(read1 - pc.getBaseStr()));
                    pc.addBaseInt((int)(byte)(read2 - pc.getBaseInt()));
                    pc.addBaseWis((int)(byte)(read3 - pc.getBaseWis()));
                    pc.addBaseDex((int)(byte)(read4 - pc.getBaseDex()));
                    pc.addBaseCon((int)(byte)(read5 - pc.getBaseCon()));
                    pc.addBaseCha((int)(byte)(read6 - pc.getBaseCha()));
                    this.saveNewCharStatus(pc);
                    break;
                }
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    private void saveNewCharStatus(L1PcInstance pc) {
        pc.setInCharReset(false);
        if (pc.getOriginalAc() > 0) {
            pc.addAc(pc.getOriginalAc());
        }
        if (pc.getOriginalMr() > 0) {
            pc.addMr(0 - pc.getOriginalMr());
        }
        pc.refresh();
        pc.setCurrentHp(pc.getMaxHp());
        pc.setCurrentMp(pc.getMaxMp());
        if (pc.getTempMaxLevel() != pc.getLevel()) {
            pc.setLevel(pc.getTempMaxLevel());
            pc.setExp(ExpTable.getExpByLevel(pc.getTempMaxLevel()));
        }
        if (pc.getLevel() > 50) {
            pc.setBonusStats(pc.getLevel() - 50);
        }
        else {
            pc.setBonusStats(0);
        }
        pc.sendPackets(new S_OwnCharStatus(pc));
        S_InitialAbilityGrowth AbilityGrowth = new S_InitialAbilityGrowth(pc);
        pc.sendPackets(AbilityGrowth);
        L1ItemInstance item = pc.getInventory().findItemId(49142);
        if (item != null) {
            try {
                pc.getInventory().removeItem(item, 1L);
                pc.save();
            }
            catch (Exception e) {
                C_CharReset._log.error(e.getLocalizedMessage(), e);
            }
        }
        L1Teleport.teleport(pc, 32628, 32772, (short)4, 4, false);
    }
    
    private void initCharStatus(L1PcInstance pc, int hp, int mp, int str, int intel, int wis, int dex, int con, int cha) {
        pc.addBaseMaxHp((short)(hp - pc.getBaseMaxHp()));
        pc.addBaseMaxMp((short)(mp - pc.getBaseMaxMp()));
        pc.addBaseStr((int)(byte)(str - pc.getBaseStr()));
        pc.addBaseInt((int)(byte)(intel - pc.getBaseInt()));
        pc.addBaseWis((int)(byte)(wis - pc.getBaseWis()));
        pc.addBaseDex((int)(byte)(dex - pc.getBaseDex()));
        pc.addBaseCon((int)(byte)(con - pc.getBaseCon()));
        pc.addBaseCha((int)(byte)(cha - pc.getBaseCha()));
        pc.addMr(0 - pc.getMr());
        pc.addDmgup(0 - pc.getDmgup());
        pc.addHitup(0 - pc.getHitup());
    }
    
    private void setLevelUp(L1PcInstance pc, int addLv) {
        pc.setTempLevel(pc.getTempLevel() + addLv);
        for (int i = 0; i < addLv; ++i) {
            short randomHp = CalcStat.calcStatHp(pc.getType(), (int)pc.getBaseMaxHp(), pc.getBaseCon(), pc.getOriginalHpup());
            short randomMp = CalcStat.calcStatMp(pc.getType(), (int)pc.getBaseMaxMp(), pc.getBaseWis(), pc.getOriginalMpup());
            pc.addBaseMaxHp(randomHp);
            pc.addBaseMaxMp(randomMp);
        }
        int newAc = CalcStat.calcAc(pc.getTempLevel(), pc.getBaseDex());
        pc.sendPackets(new S_CharReset(pc, pc.getTempLevel(), (int)pc.getBaseMaxHp(), (int)pc.getBaseMaxMp(), newAc, pc.getBaseStr(), pc.getBaseInt(), pc.getBaseWis(), pc.getBaseDex(), pc.getBaseCon(), pc.getBaseCha()));
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

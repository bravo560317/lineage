package com.lineage.data.npc.event;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_GQuest1 extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_GQuest1.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_GQuest1();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        String type1 = "[未解]";
        String type2 = "[未解]";
        String type3 = "[未解]";
        String type4 = "[未解]";
        String type5 = "[未解]";
        String type6 = "[未解]";
        String type7 = "[未解]";
        String type8 = "[未解]";
        String type9 = "[未解]";
        String type10 = "[未解]";
        String type11 = "[未解]";
        String type12 = "[未解]";
        String type13 = "[未解]";
        String type14 = "[未解]";
        String type15 = "[未解]";
        String type16 = "[未解]";
        String type17 = "[未解]";
        String type18 = "[未解]";
        String type19 = "[未解]";
        String type20 = "[未解]";
        String type21 = "[未解]";
        String type22 = "[未解]";
        String type23 = "[未解]";
        String type24 = "[未解]";
        String type25 = "[未解]";
        String type26 = "[未解]";
        String type27 = "[未解]";
        String type28 = "[未解]";
        String type29 = "[未解]";
        String type30 = "[未解]";
        String type31 = "[未解]";
        String type32 = "[未解]";
        String type33 = "[未解]";
        String type34 = "[未解]";
        String type35 = "[未解]";
        String type36 = "[未解]";
        String type37 = "[未解]";
        String type38 = "[未解]";
        String type39 = "[未解]";
        String type40 = "[未解]";
        String type41 = "[未解]";
        String type42 = "[未解]";
        String type43 = "[未解]";
        String type44 = "[未解]";
        String type45 = "[未解]";
        String type46 = "[未解]";
        String type47 = "[未解]";
        String type48 = "[未解]";
        String type49 = "[未解]";
        String type50 = "[未解]";
        String type51 = "[未解]";
        String type52 = "[未解]";
        String type53 = "[未解]";
        String type54 = "[未解]";
        String type55 = "[未解]";
        String type56 = "[未解]";
        String type57 = "[未解]";
        String type58 = "[未解]";
        String type59 = "[未解]";
        String type60 = "[未解]";
        String type61 = "[未解]";
        String type62 = "[未解]";
        String type63 = "[未解]";
        String type64 = "[未解]";
        String type65 = "[未解]";
        String type66 = "[未解]";
        String type67 = "[未解]";
        String type68 = "[未解]";
        String type69 = "[未解]";
        String type70 = "[未解]";
        String type71 = "[未解]";
        String type72 = "[未解]";
        String type73 = "[未解]";
        String type74 = "[未解]";
        String type75 = "[未解]";
        String type76 = "[未解]";
        String type77 = "[未解]";
        String type78 = "[未解]";
        String type79 = "[未解]";
        String type80 = "[未解]";
        String type81 = "[未解]";
        String type82 = "[未解]";
        String type83 = "[未解]";
        String type84 = "[未解]";
        String type85 = "[未解]";
        String type86 = "[未解]";
        String type87 = "[未解]";
        String type88 = "[未解]";
        String type89 = "[未解]";
        String type90 = "[未解]";
        String type91 = "[未解]";
        String type92 = "[未解]";
        String type93 = "[未解]";
        String type94 = "[未解]";
        String type95 = "[未解]";
        String type96 = "[未解]";
        String type97 = "[未解]";
        String type98 = "[未解]";
        String type99 = "[未解]";
        String type100 = "[未解]";
        String type101 = "[未解]";
        String type102 = "[未解]";
        String type103 = "[未解]";
        String type104 = "[未解]";
        String type105 = "[未解]";
        String type106 = "[未解]";
        String type107 = "[未解]";
        String type108 = "[未解]";
        String type109 = "[未解]";
        String type110 = "[未解]";
        String type111 = "[未解]";
        String type112 = "[未解]";
        String type113 = "[未解]";
        String type114 = "[未解]";
        String type115 = "[未解]";
        String type116 = "[未解]";
        String type117 = "[未解]";
        String type118 = "[未解]";
        String type119 = "[未解]";
        String type120 = "[未解]";
        String type121 = "[未解]";
        String type122 = "[未解]";
        String type123 = "[未解]";
        String type124 = "[未解]";
        String type125 = "[未解]";
        String type126 = "[未解]";
        String type127 = "[未解]";
        String type128 = "[未解]";
        String type129 = "[未解]";
        String type130 = "[未解]";
        String type131 = "[未解]";
        String type132 = "[未解]";
        String type133 = "[未解]";
        String type134 = "[未解]";
        String type135 = "[未解]";
        String type136 = "[未解]";
        String type137 = "[未解]";
        String type138 = "[未解]";
        String type139 = "[未解]";
        String type140 = "[未解]";
        String type141 = "[未解]";
        String type142 = "[未解]";
        String type143 = "[未解]";
        String type144 = "[未解]";
        String type145 = "[未解]";
        String type146 = "[未解]";
        String type147 = "[未解]";
        String type148 = "[未解]";
        String type149 = "[未解]";
        String type150 = "[未解]";
        String type151 = "[未解]";
        String type152 = "[未解]";
        String type153 = "[未解]";
        String type154 = "[未解]";
        String type155 = "[未解]";
        String type156 = "[未解]";
        String type157 = "[未解]";
        String type158 = "[未解]";
        String type159 = "[未解]";
        String type160 = "[未解]";
        String type161 = "[未解]";
        String type162 = "[未解]";
        String type163 = "[未解]";
        String type164 = "[未解]";
        String type165 = "[未解]";
        String type166 = "[未解]";
        String type167 = "[未解]";
        String type168 = "[未解]";
        String type169 = "[未解]";
        String type170 = "[未解]";
        String type171 = "[未解]";
        String type172 = "[未解]";
        String type173 = "[未解]";
        String type174 = "[未解]";
        String type175 = "[未解]";
        String type176 = "[未解]";
        String type177 = "[未解]";
        String type178 = "[未解]";
        String type179 = "[未解]";
        String type180 = "[未解]";
        String type181 = "[未解]";
        String type182 = "[未解]";
        String type183 = "[未解]";
        String type184 = "[未解]";
        String type185 = "[未解]";
        String type186 = "[未解]";
        String type187 = "[未解]";
        String type188 = "[未解]";
        String type189 = "[未解]";
        String type190 = "[未解]";
        String type191 = "[未解]";
        String type192 = "[未解]";
        String type193 = "[未解]";
        String type194 = "[未解]";
        String type195 = "[未解]";
        String type196 = "[未解]";
        String type197 = "[未解]";
        String type198 = "[未解]";
        String type199 = "[未解]";
        String type200 = "[未解]";
        if (pc.getQuest().get_step(440000) == 2) {
            type1 = "[已完成]";
        }
        if (pc.getQuest().get_step(440001) == 2) {
            type2 = "[已完成]";
        }
        if (pc.getQuest().get_step(440002) == 2) {
            type3 = "[已完成]";
        }
        if (pc.getQuest().get_step(440003) == 2) {
            type4 = "[已完成]";
        }
        if (pc.getQuest().get_step(440004) == 2) {
            type5 = "[已完成]";
        }
        if (pc.getQuest().get_step(440005) == 2) {
            type6 = "[已完成]";
        }
        if (pc.getQuest().get_step(440006) == 2) {
            type7 = "[已完成]";
        }
        if (pc.getQuest().get_step(440007) == 2) {
            type8 = "[已完成]";
        }
        if (pc.getQuest().get_step(440008) == 2) {
            type9 = "[已完成]";
        }
        if (pc.getQuest().get_step(440009) == 2) {
            type10 = "[已完成]";
        }
        if (pc.getQuest().get_step(440010) == 2) {
            type11 = "[已完成]";
        }
        if (pc.getQuest().get_step(440011) == 2) {
            type12 = "[已完成]";
        }
        if (pc.getQuest().get_step(440012) == 2) {
            type13 = "[已完成]";
        }
        if (pc.getQuest().get_step(440013) == 2) {
            type14 = "[已完成]";
        }
        if (pc.getQuest().get_step(440014) == 2) {
            type15 = "[已完成]";
        }
        if (pc.getQuest().get_step(440015) == 2) {
            type16 = "[已完成]";
        }
        if (pc.getQuest().get_step(440016) == 2) {
            type17 = "[已完成]";
        }
        if (pc.getQuest().get_step(440017) == 2) {
            type18 = "[已完成]";
        }
        if (pc.getQuest().get_step(440018) == 2) {
            type19 = "[已完成]";
        }
        if (pc.getQuest().get_step(440019) == 2) {
            type20 = "[已完成]";
        }
        if (pc.getQuest().get_step(440020) == 2) {
            type21 = "[已完成]";
        }
        if (pc.getQuest().get_step(440021) == 2) {
            type22 = "[已完成]";
        }
        if (pc.getQuest().get_step(440022) == 2) {
            type23 = "[已完成]";
        }
        if (pc.getQuest().get_step(440023) == 2) {
            type24 = "[已完成]";
        }
        if (pc.getQuest().get_step(440024) == 2) {
            type25 = "[已完成]";
        }
        if (pc.getQuest().get_step(440025) == 2) {
            type26 = "[已完成]";
        }
        if (pc.getQuest().get_step(440026) == 2) {
            type27 = "[已完成]";
        }
        if (pc.getQuest().get_step(440027) == 2) {
            type28 = "[已完成]";
        }
        if (pc.getQuest().get_step(440028) == 2) {
            type29 = "[已完成]";
        }
        if (pc.getQuest().get_step(440029) == 2) {
            type30 = "[已完成]";
        }
        if (pc.getQuest().get_step(440030) == 2) {
            type31 = "[已完成]";
        }
        if (pc.getQuest().get_step(440031) == 2) {
            type32 = "[已完成]";
        }
        if (pc.getQuest().get_step(440032) == 2) {
            type33 = "[已完成]";
        }
        if (pc.getQuest().get_step(440033) == 2) {
            type34 = "[已完成]";
        }
        if (pc.getQuest().get_step(440034) == 2) {
            type35 = "[已完成]";
        }
        if (pc.getQuest().get_step(440035) == 2) {
            type36 = "[已完成]";
        }
        if (pc.getQuest().get_step(440036) == 2) {
            type37 = "[已完成]";
        }
        if (pc.getQuest().get_step(440037) == 2) {
            type38 = "[已完成]";
        }
        if (pc.getQuest().get_step(440038) == 2) {
            type39 = "[已完成]";
        }
        if (pc.getQuest().get_step(440039) == 2) {
            type40 = "[已完成]";
        }
        if (pc.getQuest().get_step(440040) == 2) {
            type41 = "[已完成]";
        }
        if (pc.getQuest().get_step(440041) == 2) {
            type42 = "[已完成]";
        }
        if (pc.getQuest().get_step(440042) == 2) {
            type43 = "[已完成]";
        }
        if (pc.getQuest().get_step(440043) == 2) {
            type44 = "[已完成]";
        }
        if (pc.getQuest().get_step(440044) == 2) {
            type45 = "[已完成]";
        }
        if (pc.getQuest().get_step(440045) == 2) {
            type46 = "[已完成]";
        }
        if (pc.getQuest().get_step(440046) == 2) {
            type47 = "[已完成]";
        }
        if (pc.getQuest().get_step(440047) == 2) {
            type48 = "[已完成]";
        }
        if (pc.getQuest().get_step(440048) == 2) {
            type49 = "[已完成]";
        }
        if (pc.getQuest().get_step(440049) == 2) {
            type50 = "[已完成]";
        }
        if (pc.getQuest().get_step(440050) == 2) {
            type51 = "[已完成]";
        }
        if (pc.getQuest().get_step(440051) == 2) {
            type52 = "[已完成]";
        }
        if (pc.getQuest().get_step(440052) == 2) {
            type53 = "[已完成]";
        }
        if (pc.getQuest().get_step(440053) == 2) {
            type54 = "[已完成]";
        }
        if (pc.getQuest().get_step(440054) == 2) {
            type55 = "[已完成]";
        }
        if (pc.getQuest().get_step(440055) == 2) {
            type56 = "[已完成]";
        }
        if (pc.getQuest().get_step(440056) == 2) {
            type57 = "[已完成]";
        }
        if (pc.getQuest().get_step(440057) == 2) {
            type58 = "[已完成]";
        }
        if (pc.getQuest().get_step(440058) == 2) {
            type59 = "[已完成]";
        }
        if (pc.getQuest().get_step(440059) == 2) {
            type60 = "[已完成]";
        }
        if (pc.getQuest().get_step(440060) == 2) {
            type61 = "[已完成]";
        }
        if (pc.getQuest().get_step(440061) == 2) {
            type62 = "[已完成]";
        }
        if (pc.getQuest().get_step(440062) == 2) {
            type63 = "[已完成]";
        }
        if (pc.getQuest().get_step(440063) == 2) {
            type64 = "[已完成]";
        }
        if (pc.getQuest().get_step(440064) == 2) {
            type65 = "[已完成]";
        }
        if (pc.getQuest().get_step(440065) == 2) {
            type66 = "[已完成]";
        }
        if (pc.getQuest().get_step(440066) == 2) {
            type67 = "[已完成]";
        }
        if (pc.getQuest().get_step(440067) == 2) {
            type68 = "[已完成]";
        }
        if (pc.getQuest().get_step(440068) == 2) {
            type69 = "[已完成]";
        }
        if (pc.getQuest().get_step(440069) == 2) {
            type70 = "[已完成]";
        }
        if (pc.getQuest().get_step(440070) == 2) {
            type71 = "[已完成]";
        }
        if (pc.getQuest().get_step(440071) == 2) {
            type72 = "[已完成]";
        }
        if (pc.getQuest().get_step(440072) == 2) {
            type73 = "[已完成]";
        }
        if (pc.getQuest().get_step(440073) == 2) {
            type74 = "[已完成]";
        }
        if (pc.getQuest().get_step(440074) == 2) {
            type75 = "[已完成]";
        }
        if (pc.getQuest().get_step(440075) == 2) {
            type76 = "[已完成]";
        }
        if (pc.getQuest().get_step(440076) == 2) {
            type77 = "[已完成]";
        }
        if (pc.getQuest().get_step(440077) == 2) {
            type78 = "[已完成]";
        }
        if (pc.getQuest().get_step(440078) == 2) {
            type79 = "[已完成]";
        }
        if (pc.getQuest().get_step(440079) == 2) {
            type80 = "[已完成]";
        }
        if (pc.getQuest().get_step(440080) == 2) {
            type81 = "[已完成]";
        }
        if (pc.getQuest().get_step(440081) == 2) {
            type82 = "[已完成]";
        }
        if (pc.getQuest().get_step(440082) == 2) {
            type83 = "[已完成]";
        }
        if (pc.getQuest().get_step(440083) == 2) {
            type84 = "[已完成]";
        }
        if (pc.getQuest().get_step(440084) == 2) {
            type85 = "[已完成]";
        }
        if (pc.getQuest().get_step(440085) == 2) {
            type86 = "[已完成]";
        }
        if (pc.getQuest().get_step(440086) == 2) {
            type87 = "[已完成]";
        }
        if (pc.getQuest().get_step(440087) == 2) {
            type88 = "[已完成]";
        }
        if (pc.getQuest().get_step(440088) == 2) {
            type89 = "[已完成]";
        }
        if (pc.getQuest().get_step(440089) == 2) {
            type90 = "[已完成]";
        }
        if (pc.getQuest().get_step(440090) == 2) {
            type91 = "[已完成]";
        }
        if (pc.getQuest().get_step(440091) == 2) {
            type92 = "[已完成]";
        }
        if (pc.getQuest().get_step(440092) == 2) {
            type93 = "[已完成]";
        }
        if (pc.getQuest().get_step(440093) == 2) {
            type94 = "[已完成]";
        }
        if (pc.getQuest().get_step(440094) == 2) {
            type95 = "[已完成]";
        }
        if (pc.getQuest().get_step(440095) == 2) {
            type96 = "[已完成]";
        }
        if (pc.getQuest().get_step(440096) == 2) {
            type97 = "[已完成]";
        }
        if (pc.getQuest().get_step(440097) == 2) {
            type98 = "[已完成]";
        }
        if (pc.getQuest().get_step(440098) == 2) {
            type99 = "[已完成]";
        }
        if (pc.getQuest().get_step(440099) == 2) {
            type100 = "[已完成]";
        }
        if (pc.getQuest().get_step(440100) == 2) {
            type101 = "[已完成]";
        }
        if (pc.getQuest().get_step(440101) == 2) {
            type102 = "[已完成]";
        }
        if (pc.getQuest().get_step(440102) == 2) {
            type103 = "[已完成]";
        }
        if (pc.getQuest().get_step(440103) == 2) {
            type104 = "[已完成]";
        }
        if (pc.getQuest().get_step(440104) == 2) {
            type105 = "[已完成]";
        }
        if (pc.getQuest().get_step(440105) == 2) {
            type106 = "[已完成]";
        }
        if (pc.getQuest().get_step(440106) == 2) {
            type107 = "[已完成]";
        }
        if (pc.getQuest().get_step(440107) == 2) {
            type108 = "[已完成]";
        }
        if (pc.getQuest().get_step(440108) == 2) {
            type109 = "[已完成]";
        }
        if (pc.getQuest().get_step(440109) == 2) {
            type110 = "[已完成]";
        }
        if (pc.getQuest().get_step(440110) == 2) {
            type111 = "[已完成]";
        }
        if (pc.getQuest().get_step(440111) == 2) {
            type112 = "[已完成]";
        }
        if (pc.getQuest().get_step(440112) == 2) {
            type113 = "[已完成]";
        }
        if (pc.getQuest().get_step(440113) == 2) {
            type114 = "[已完成]";
        }
        if (pc.getQuest().get_step(440114) == 2) {
            type115 = "[已完成]";
        }
        if (pc.getQuest().get_step(440115) == 2) {
            type116 = "[已完成]";
        }
        if (pc.getQuest().get_step(440116) == 2) {
            type117 = "[已完成]";
        }
        if (pc.getQuest().get_step(440117) == 2) {
            type118 = "[已完成]";
        }
        if (pc.getQuest().get_step(440118) == 2) {
            type119 = "[已完成]";
        }
        if (pc.getQuest().get_step(440119) == 2) {
            type120 = "[已完成]";
        }
        if (pc.getQuest().get_step(440120) == 2) {
            type121 = "[已完成]";
        }
        if (pc.getQuest().get_step(440121) == 2) {
            type122 = "[已完成]";
        }
        if (pc.getQuest().get_step(440122) == 2) {
            type123 = "[已完成]";
        }
        if (pc.getQuest().get_step(440123) == 2) {
            type124 = "[已完成]";
        }
        if (pc.getQuest().get_step(440124) == 2) {
            type125 = "[已完成]";
        }
        if (pc.getQuest().get_step(440125) == 2) {
            type126 = "[已完成]";
        }
        if (pc.getQuest().get_step(440126) == 2) {
            type127 = "[已完成]";
        }
        if (pc.getQuest().get_step(440127) == 2) {
            type128 = "[已完成]";
        }
        if (pc.getQuest().get_step(440128) == 2) {
            type129 = "[已完成]";
        }
        if (pc.getQuest().get_step(440129) == 2) {
            type130 = "[已完成]";
        }
        if (pc.getQuest().get_step(440130) == 2) {
            type131 = "[已完成]";
        }
        if (pc.getQuest().get_step(440131) == 2) {
            type132 = "[已完成]";
        }
        if (pc.getQuest().get_step(440132) == 2) {
            type133 = "[已完成]";
        }
        if (pc.getQuest().get_step(440133) == 2) {
            type134 = "[已完成]";
        }
        if (pc.getQuest().get_step(440134) == 2) {
            type135 = "[已完成]";
        }
        if (pc.getQuest().get_step(440135) == 2) {
            type136 = "[已完成]";
        }
        if (pc.getQuest().get_step(440136) == 2) {
            type137 = "[已完成]";
        }
        if (pc.getQuest().get_step(440137) == 2) {
            type138 = "[已完成]";
        }
        if (pc.getQuest().get_step(440138) == 2) {
            type139 = "[已完成]";
        }
        if (pc.getQuest().get_step(440139) == 2) {
            type140 = "[已完成]";
        }
        if (pc.getQuest().get_step(440140) == 2) {
            type141 = "[已完成]";
        }
        if (pc.getQuest().get_step(440141) == 2) {
            type142 = "[已完成]";
        }
        if (pc.getQuest().get_step(440142) == 2) {
            type143 = "[已完成]";
        }
        if (pc.getQuest().get_step(440143) == 2) {
            type144 = "[已完成]";
        }
        if (pc.getQuest().get_step(440144) == 2) {
            type145 = "[已完成]";
        }
        if (pc.getQuest().get_step(440145) == 2) {
            type146 = "[已完成]";
        }
        if (pc.getQuest().get_step(440146) == 2) {
            type147 = "[已完成]";
        }
        if (pc.getQuest().get_step(440147) == 2) {
            type148 = "[已完成]";
        }
        if (pc.getQuest().get_step(440148) == 2) {
            type149 = "[已完成]";
        }
        if (pc.getQuest().get_step(440149) == 2) {
            type150 = "[已完成]";
        }
        if (pc.getQuest().get_step(440150) == 2) {
            type151 = "[已完成]";
        }
        if (pc.getQuest().get_step(440151) == 2) {
            type152 = "[已完成]";
        }
        if (pc.getQuest().get_step(440152) == 2) {
            type153 = "[已完成]";
        }
        if (pc.getQuest().get_step(440153) == 2) {
            type154 = "[已完成]";
        }
        if (pc.getQuest().get_step(440154) == 2) {
            type155 = "[已完成]";
        }
        if (pc.getQuest().get_step(440155) == 2) {
            type156 = "[已完成]";
        }
        if (pc.getQuest().get_step(440156) == 2) {
            type157 = "[已完成]";
        }
        if (pc.getQuest().get_step(440157) == 2) {
            type158 = "[已完成]";
        }
        if (pc.getQuest().get_step(440158) == 2) {
            type159 = "[已完成]";
        }
        if (pc.getQuest().get_step(440159) == 2) {
            type160 = "[已完成]";
        }
        if (pc.getQuest().get_step(440160) == 2) {
            type161 = "[已完成]";
        }
        if (pc.getQuest().get_step(440161) == 2) {
            type162 = "[已完成]";
        }
        if (pc.getQuest().get_step(440162) == 2) {
            type163 = "[已完成]";
        }
        if (pc.getQuest().get_step(440163) == 2) {
            type164 = "[已完成]";
        }
        if (pc.getQuest().get_step(440164) == 2) {
            type165 = "[已完成]";
        }
        if (pc.getQuest().get_step(440165) == 2) {
            type166 = "[已完成]";
        }
        if (pc.getQuest().get_step(440166) == 2) {
            type167 = "[已完成]";
        }
        if (pc.getQuest().get_step(440167) == 2) {
            type168 = "[已完成]";
        }
        if (pc.getQuest().get_step(440168) == 2) {
            type169 = "[已完成]";
        }
        if (pc.getQuest().get_step(440169) == 2) {
            type170 = "[已完成]";
        }
        if (pc.getQuest().get_step(440170) == 2) {
            type171 = "[已完成]";
        }
        if (pc.getQuest().get_step(440171) == 2) {
            type172 = "[已完成]";
        }
        if (pc.getQuest().get_step(440172) == 2) {
            type173 = "[已完成]";
        }
        if (pc.getQuest().get_step(440173) == 2) {
            type174 = "[已完成]";
        }
        if (pc.getQuest().get_step(440174) == 2) {
            type175 = "[已完成]";
        }
        if (pc.getQuest().get_step(440175) == 2) {
            type176 = "[已完成]";
        }
        if (pc.getQuest().get_step(440176) == 2) {
            type177 = "[已完成]";
        }
        if (pc.getQuest().get_step(440177) == 2) {
            type178 = "[已完成]";
        }
        if (pc.getQuest().get_step(440178) == 2) {
            type179 = "[已完成]";
        }
        if (pc.getQuest().get_step(440179) == 2) {
            type180 = "[已完成]";
        }
        if (pc.getQuest().get_step(440180) == 2) {
            type181 = "[已完成]";
        }
        if (pc.getQuest().get_step(440181) == 2) {
            type182 = "[已完成]";
        }
        if (pc.getQuest().get_step(440182) == 2) {
            type183 = "[已完成]";
        }
        if (pc.getQuest().get_step(440183) == 2) {
            type184 = "[已完成]";
        }
        if (pc.getQuest().get_step(440184) == 2) {
            type185 = "[已完成]";
        }
        if (pc.getQuest().get_step(440185) == 2) {
            type186 = "[已完成]";
        }
        if (pc.getQuest().get_step(440186) == 2) {
            type187 = "[已完成]";
        }
        if (pc.getQuest().get_step(440187) == 2) {
            type188 = "[已完成]";
        }
        if (pc.getQuest().get_step(440188) == 2) {
            type189 = "[已完成]";
        }
        if (pc.getQuest().get_step(440189) == 2) {
            type190 = "[已完成]";
        }
        if (pc.getQuest().get_step(440190) == 2) {
            type191 = "[已完成]";
        }
        if (pc.getQuest().get_step(440191) == 2) {
            type192 = "[已完成]";
        }
        if (pc.getQuest().get_step(440192) == 2) {
            type193 = "[已完成]";
        }
        if (pc.getQuest().get_step(440193) == 2) {
            type194 = "[已完成]";
        }
        if (pc.getQuest().get_step(440194) == 2) {
            type195 = "[已完成]";
        }
        if (pc.getQuest().get_step(440195) == 2) {
            type196 = "[已完成]";
        }
        if (pc.getQuest().get_step(440196) == 2) {
            type197 = "[已完成]";
        }
        if (pc.getQuest().get_step(440197) == 2) {
            type198 = "[已完成]";
        }
        if (pc.getQuest().get_step(440198) == 2) {
            type199 = "[已完成]";
        }
        if (pc.getQuest().get_step(440199) == 2) {
            type200 = "[已完成]";
        }
        pc.setpag(0);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "collect11", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18, type19, type20, type21, type22, type23, type24, type25, type26, type27, type28, type29, type30, type31, type32, type33, type34, type35, type36, type37, type38, type39, type40, type41, type42, type43, type44, type45, type46, type47, type48, type49, type50, type51, type52, type53, type54, type55, type56, type57, type58, type59, type60, type61, type62, type63, type64, type65, type66, type67, type68, type69, type70, type71, type72, type73, type74, type75, type76, type77, type78, type79, type80, type81, type82, type83, type84, type85, type86, type87, type88, type89, type90, type91, type92, type93, type94, type95, type96, type97, type98, type99, type100, type101, type102, type103, type104, type105, type106, type107, type108, type109, type110, type111, type112, type113, type114, type115, type116, type117, type118, type119, type120, type121, type122, type123, type124, type125, type126, type127, type128, type129, type130, type131, type132, type133, type134, type135, type136, type137, type138, type139, type140, type141, type142, type143, type144, type145, type146, type147, type148, type149, type150, type151, type152, type153, type154, type155, type156, type157, type158, type159, type160, type161, type162, type163, type164, type165, type166, type167, type168, type169, type170, type171, type172, type173, type174, type175, type176, type177, type178, type179, type180, type181, type182, type183, type184, type185, type186, type187, type188, type189, type190, type191, type192, type193, type194, type195, type196, type197, type198, type199, type200 }));
    }
}

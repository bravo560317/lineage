package com.eric.gui;

import java.util.Date;
import java.io.FileOutputStream;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.templates.L1Account;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.templates.L1UserSkillTmp;
import com.lineage.echo.ClientExecutor;
import com.lineage.list.OnlineUser;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.datatables.SkillsItemTable;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.GameServer;
import com.lineage.server.Shutdown;
import javax.swing.JOptionPane;

import com.lineage.config.ConfigAlt;
import com.lineage.config.ConfigGUI;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigRate;
import com.lineage.data.cmd.EnchantArmor;
import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.data.cmd.EnchantExecutor_Acc;
import com.lineage.data.cmd.EnchantWeapon;
import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.server.datatables.sql.CharSkillTable;
import com.lineage.commons.system.LanSecurityManager;
import com.lineage.server.serverpackets.S_Disconnect;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Icon;
import java.util.Iterator;
import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

import com.lineage.server.datatables.lock.SpawnBossReading;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.templates.L1Npc;
import javax.swing.SwingUtilities;
import java.text.SimpleDateFormat;
import java.util.*;

import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1ClanMatching;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.clientpackets.C_ItemUSe;
import com.lineage.server.clientpackets.C_LoginToServer;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldClan;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.LayoutStyle;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import java.util.Calendar;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class J_Main extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private static J_Main instance;
    private int select;
    SimpleDateFormat sdf;
    ImageIcon img;
    private DefaultTableModel DTM;
    private DefaultTableModel DTM_Item;
    private JButton B_Item;
    private JButton B_Submit;
    private JComboBox<?> CB_Channel;
    private JComboBox CB_Item;
    private JDialog D_Item;
    private JFrame F_Player;
    private JLabel L_AccessLevel;
    private JLabel L_AccessLevel7;
    private JLabel L_Account;
    private JLabel L_Cha;
    private JLabel L_Clan;
    private JLabel L_Con;
    private JLabel L_Dex;
    private JLabel L_Exp;
    private JLabel L_Hp;
    private JLabel L_Image;
    private JLabel L_Int;
    private JLabel L_Leavl;
    private JLabel L_Map;
    private JLabel L_Mp;
    private JLabel L_Mp1;
    private JLabel L_Name;
    private JLabel L_Str;
    private JLabel L_Title;
    private JLabel L_Wis;
    private JLabel L_X;
    private JLabel L_Y;
    private JMenuBar MB;
    private JMenuItem MI_LA;
    private JMenuItem MI_ATTR_ENCHANT_CHANCE;
    private JMenuItem MI_CHANCE_ARMOR;
    private JMenuItem MI_CHANCE_WEAPON;
    private JMenuItem MI_XP_PET;
    private JMenuItem MI_Exp;
    private JMenuItem MI_Drop;
    private JMenuItem MI_Adena;
    private JMenuItem MI_AllBuff;
    private JMenuItem MI_AllRess;
    private JMenuItem MI_Angel;
    private JMenuItem MI_BanIP;
    private JMenuItem MI_Close;
    private JMenuItem MI_Kill;
    private JMenuItem MI_Save;
    private JMenuItem MI_SetClose;
    private JMenuItem MI_ShowPlayer;
    private JMenuItem MI_Whisper;
    private JMenu M_Edit;
    private JMenu M_File;
    private JMenu M_Special;
    private JPopupMenu PM_Player;
    private JScrollPane SP_;
    private JScrollPane SP_AllChat;
    private JScrollPane SP_Clan;
    private JScrollPane SP_Consol;
    private JScrollPane SP_Normal;
    private JSplitPane SP_Split;
    private JScrollPane SP_Team;
    private JScrollPane SP_World;
    private JScrollPane SP_player;
    private JTextArea TA_AllChat;
    private JTextArea TA_Clan;
    private JTextArea TA_Consol;
    private JTextArea TA_Normal;
    private JTextArea TA_Private;
    private JTextArea TA_Team;
    private JTextArea TA_World;
    private JTextField TF_Ac;
    private JTextField TF_AccessLevel;
    private JTextField TF_Account;
    private JTextField TF_Cha;
    private JTextField TF_Clan;
    private JTextField TF_Con;
    private JTextField TF_Dex;
    private JTextField TF_Exp;
    private JTextField TF_Hp;
    private JTextField TF_Int;
    private JTextField TF_Level;
    private JTextField TF_Map;
    private JTextField TF_Mp;
    private JTextField TF_Msg;
    private JTextField TF_Name;
    private JTextField TF_Sex;
    private JTextField TF_Str;
    private JTextField TF_Target;
    private JTextField TF_Title;
    private JTextField TF_Wis;
    private JTextField TF_X;
    private JTextField TF_Y;
    private JTabbedPane TP;
    private JTable T_Item;
    private JTable T_Player;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JMenuItem MI_BossStatus;
    private JPanel P_Equip;
    private JPanel P_Companions;
    private final JLabel[] L_EquipName = new JLabel[15];
    private final JLabel[] L_DollName  = new JLabel[6];
    private final JLabel[] L_PetName   = new JLabel[8];
    private javax.swing.Timer companionsTimer = null;
    private String currentFPlayerName = null;
    private DefaultTableModel DTM_Boss;
    private JTable T_Boss;
    
    private JTextArea TA_Boss = new JTextArea();
    private JScrollPane SP_Boss = new JScrollPane(TA_Boss);
    
    private int currentMode = -1;
    
    public J_Main() {
        this.select = 0;
        this.sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        this.img = new ImageIcon("img/icon.png");
        this.DTM = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        this.DTM_Item = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        this.iniPlayerTable();
        this.initComponents();
        this.TA_Consol.setForeground(new Color(255, 255, 255));
        this.TA_AllChat.setForeground(new Color(255, 255, 255));
        this.TA_Clan.setForeground(new Color(255, 255, 255));
        this.TA_Normal.setForeground(new Color(255, 255, 255));
        this.TA_Private.setForeground(new Color(255, 255, 255));
        this.TA_Team.setForeground(new Color(255, 255, 255));
        this.TA_World.setForeground(new Color(255, 255, 255));
        this.TA_Consol.setBackground(new Color(0, 0, 0));
        this.TA_AllChat.setBackground(new Color(0, 0, 0));
        this.TA_Clan.setBackground(new Color(0, 0, 0));
        this.TA_Normal.setBackground(new Color(0, 0, 0));
        this.TA_Private.setBackground(new Color(0, 0, 0));
        this.TA_Team.setBackground(new Color(0, 0, 0));
        this.TA_World.setBackground(new Color(0, 0, 0));
        this.setIconImage(this.img.getImage());
        this.iniAction();
        this.T_Item.setSize(300, 400);
        this.D_Item.pack();
        String[] s = { "物品名稱", "物品數量", "物品ID" };
        this.DTM_Item.setColumnIdentifiers(s);
    }
    
    private void iniAction() {
        this.MI_Kill.addActionListener(this);
        this.MI_BanIP.addActionListener(this);
        this.MI_ShowPlayer.addActionListener(this);
        this.MI_Whisper.addActionListener(this);
        this.MI_Save.addActionListener(this);
        this.MI_Close.addActionListener(this);
        this.MI_Angel.addActionListener(this);
        this.MI_SetClose.addActionListener(this);
        this.MI_AllBuff.addActionListener(this);
        this.MI_AllRess.addActionListener(this);
        this.MI_XP_PET.addActionListener(this);
        this.MI_CHANCE_WEAPON.addActionListener(this);
        this.MI_CHANCE_ARMOR.addActionListener(this);
        this.MI_ATTR_ENCHANT_CHANCE.addActionListener(this);
        this.MI_LA.addActionListener(this);
        this.MI_Adena.addActionListener(this);
        this.MI_Exp.addActionListener(this);
        this.MI_Drop.addActionListener(this);
        this.MI_BossStatus.addActionListener(this);
    }
    
    public static J_Main getInstance() {
        if (J_Main.instance == null) {
            J_Main.instance = new J_Main();
        }
        return J_Main.instance;
    }
    
    public void addWorldChat(String from, String text) {
        Calendar cal = Calendar.getInstance();
        this.AllChat(this.sdf.format(cal.getTime()) + "公頻『" + from + "』:" + text + "\r\n");
        this.TA_World.append(from + " : " + text + "\r\n");
        this.TA_World.setCaretPosition(this.TA_World.getDocument().getLength());
    }
    
    public void addClanChat(String from, String text) {
        Calendar cal = Calendar.getInstance();
        this.AllChat(this.sdf.format(cal.getTime()) + "血盟『" + from + "』:" + text + "\r\n");
        this.TA_Clan.append(from + " : " + text + "\r\n");
        this.TA_Clan.setCaretPosition(this.TA_Clan.getDocument().getLength());
    }
    
    public void addNormalChat(String from, String text) {
        Calendar cal = Calendar.getInstance();
        this.AllChat(this.sdf.format(cal.getTime()) + "一般『" + from + "』:" + text + "\r\n");
        this.TA_Normal.append(from + " : " + text + "\r\n");
        this.TA_Normal.setCaretPosition(this.TA_Normal.getDocument().getLength());
    }
    
    public void addTeamChat(String from, String text) {
        Calendar cal = Calendar.getInstance();
        this.AllChat(this.sdf.format(cal.getTime()) + "隊伍『" + from + "』:" + text + "\r\n");
        this.TA_Team.append(from + " : " + text + "\r\n");
        this.TA_Team.setCaretPosition(this.TA_Team.getDocument().getLength());
    }
    
    public void addConsol(String text) {
        this.TA_Consol.append(text + "\r\n");
        this.TA_Consol.setCaretPosition(this.TA_Consol.getDocument().getLength());
    }
    
    public void addConsolPost(String text) {
        this.TA_Consol.append(text + "\r\n");
        this.TA_Consol.setCaretPosition(this.TA_Consol.getDocument().getLength());
    }
    
    public void addConsolNoLR(String text) {
        this.TA_Consol.append(text);
        this.TA_Consol.setCaretPosition(this.TA_Consol.getDocument().getLength());
    }
    
    public void AllChat(String text) {
        this.TA_AllChat.append(text + "\r\n");
        this.TA_AllChat.setCaretPosition(this.TA_AllChat.getDocument().getLength());
    }
    
    public void addPrivateChat(String from, String to, String text) {
        Calendar cal = Calendar.getInstance();
        this.AllChat(this.sdf.format(cal.getTime()) + "密語『" + from + "->" + to + "』:" + text + "\r\n");
        this.TA_Private.append(from + "->" + to + " : " + text + "\r\n");
        this.TA_Private.setCaretPosition(this.TA_Private.getDocument().getLength());
    }
    
    public void addItemTable(String itemname, long l, long id) {
        Object[] o = { itemname, l, id };
        this.DTM_Item.addRow(o);
    }
    public void iniTable() {
        if (T_Item.getRowSorter() != null) {
            T_Item.setRowSorter(null);
        }
        if (DTM_Item != null) {
            DTM_Item.setRowCount(0);
        }
        T_Item.clearSelection();
    }
    public void addSkillTable(String skillName, String progress, long skillId, String materialInfo) {
        Object[] o = { skillName, progress, skillId, materialInfo };
        this.DTM_Item.addRow(o);
    }
    public void addBossChatmsg(String text) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(cal.getTime());
        String message = formattedTime +"  "+ text + "\r\n";
        this.TA_Boss.append(message);
        this.TA_Boss.setCaretPosition(this.TA_Boss.getDocument().getLength());
    }
    public void addPlayerTable(String account, String name, StringBuilder IP) {
        Object[] o = { account, name, IP };
        this.DTM.addRow(o);
    }
    public void queryBossStatus() {
        TA_Boss.setText("");

        List<String> respawnedBosses = new ArrayList<>();
        List<String> notRespawnedBosses = new ArrayList<>();

        long currentTime = System.currentTimeMillis();

        for (int bossId : SpawnBossReading.get().bossreid()) {
            L1Npc npcTemplate = SpawnBossReading.get().getTemplate(bossId).getNpcTemplate();
            int mapId = SpawnBossReading.get().getTemplate(bossId).getMapId();
            String mapName = MapsTable.get().getMapName(mapId);
            String bossName = SpawnBossReading.get().getTemplate(bossId).getName();
            long spawnTime = SpawnBossReading.get().getTemplate(bossId).get_nextSpawnTime() != null
                    ? SpawnBossReading.get().getTemplate(bossId).get_nextSpawnTime().getTimeInMillis()
                    : 0;

            String formattedBossName = "【" + mapName + "】" + bossName + " (Lv." + npcTemplate.get_level() + ")";

            if (spawnTime < currentTime) {
                respawnedBosses.add(formattedBossName);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String formattedTime = sdf.format(new Date(spawnTime));
                notRespawnedBosses.add("【重生時間: " + formattedTime + "】" + formattedBossName);
            }
        }
        notRespawnedBosses.sort((a, b) -> Long.compare(extractTime(b), extractTime(a)));
        List<String> finalOutput = new ArrayList<>();
        finalOutput.addAll(notRespawnedBosses);
        if (!respawnedBosses.isEmpty()) {
            finalOutput.add("");
            finalOutput.add("============================ 已重生的 BOSS ============================");
            finalOutput.addAll(respawnedBosses);
        }
        Collections.reverse(finalOutput);
        for (String line : finalOutput) {
            addBossChat(line);
        }
    }
    private long extractTime(String bossInfo) {
        try {
            int index = bossInfo.indexOf("【重生時間: ");
            if (index != -1) {
                String timeStr = bossInfo.substring(index + 7, bossInfo.indexOf("】"));
                if (timeStr.contains("月")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
                    Date date = sdf.parse(timeStr);
                    return date.getTime();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date date = sdf.parse(timeStr);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                    cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                    cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    if (cal.getTimeInMillis() < System.currentTimeMillis()) {
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                    }

                    return cal.getTimeInMillis();
                }
            }
        } catch (Exception e) {
            return Long.MIN_VALUE;
        }
        return Long.MIN_VALUE;
    }

    
    public void addBossChat(String text) {
        this.TA_Boss.append(text + "\r\n");
        this.TA_Boss.setCaretPosition(this.TA_Boss.getDocument().getLength());
    }
    
    
    private int findPlayer(String name) {
        try {
            int j = 0;
            while (j < this.DTM.getRowCount()) {
                if (name.equals(this.DTM.getValueAt(j, 1).toString())) {
                    return j;
                }
                else {
                    ++j;
                }
            }
            return -1;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public void delPlayerTable(String name) {
        int findNum = 0;
        if ((findNum = this.findPlayer(name)) != -1) {
            this.DTM.removeRow(findNum);
        }
    }
    
    private void iniPlayerTable() {
        String[] s = { "帳號", "角色名稱", "IP" };
        this.DTM.setColumnIdentifiers(s);
    }
    private JButton B_RefreshBoss;
    
    private void initComponents() {
        this.F_Player = new JFrame();
        this.L_Name = new JLabel();
        this.L_Title = new JLabel();
        this.L_Account = new JLabel();
        this.L_Leavl = new JLabel();
        this.L_AccessLevel = new JLabel();
        this.L_Exp = new JLabel();
        this.L_Hp = new JLabel();
        this.L_Mp = new JLabel();
        this.L_Int = new JLabel();
        this.L_Str = new JLabel();
        this.L_Con = new JLabel();
        this.L_Dex = new JLabel();
        this.L_Wis = new JLabel();
        this.L_Cha = new JLabel();
        this.jPanel1 = new JPanel();
        this.L_Image = new JLabel();
        this.L_Clan = new JLabel();
        this.L_AccessLevel7 = new JLabel();
        this.L_Mp1 = new JLabel();
        this.L_Map = new JLabel();
        this.L_X = new JLabel();
        this.L_Y = new JLabel();
        this.TF_Account = new JTextField();
        this.TF_Name = new JTextField();
        this.TF_Title = new JTextField();
        this.TF_Level = new JTextField();
        this.TF_AccessLevel = new JTextField();
        this.TF_Clan = new JTextField();
        this.TF_Exp = new JTextField();
        this.TF_Hp = new JTextField();
        this.TF_Mp = new JTextField();
        this.TF_Sex = new JTextField();
        this.TF_Str = new JTextField();
        this.TF_Con = new JTextField();
        this.TF_Dex = new JTextField();
        this.TF_Wis = new JTextField();
        this.TF_Int = new JTextField();
        this.TF_Cha = new JTextField();
        this.TF_Ac = new JTextField();
        this.TF_Map = new JTextField();
        this.TF_X = new JTextField();
        this.TF_Y = new JTextField();
        this.B_Item = new JButton();
        this.CB_Item = new JComboBox();
        this.PM_Player = new JPopupMenu();
        this.MI_Kill = new JMenuItem();
        this.MI_BanIP = new JMenuItem();
        this.jSeparator1 = new JSeparator();
        this.MI_ShowPlayer = new JMenuItem();
        this.jSeparator2 = new JSeparator();
        this.MI_Whisper = new JMenuItem();
        this.jLabel1 = new JLabel();
        this.D_Item = new JDialog();
        this.jScrollPane1 = new JScrollPane();
        this.T_Item = new JTable(this.DTM_Item);
        this.SP_Split = new JSplitPane();
        this.TP = new JTabbedPane();
        this.SP_Consol = new JScrollPane();
        this.TA_Consol = new JTextArea();
        this.SP_AllChat = new JScrollPane();
        this.TA_AllChat = new JTextArea();
        this.SP_World = new JScrollPane();
        this.TA_World = new JTextArea();
        this.SP_Normal = new JScrollPane();
        this.TA_Normal = new JTextArea();
        this.SP_ = new JScrollPane();
        this.TA_Private = new JTextArea();
        this.SP_Clan = new JScrollPane();
        this.TA_Clan = new JTextArea();
        this.SP_Team = new JScrollPane();
        this.TA_Team = new JTextArea();
        this.SP_player = new JScrollPane();
        this.T_Player = new JTable(this.DTM);
        this.T_Player.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    openSelectedRowPlayer();
                }
            }
        });
        this.jPanel2 = new JPanel();
        this.CB_Channel = new JComboBox();
        this.TF_Target = new JTextField();
        this.B_Submit = new JButton();
        this.TF_Msg = new JTextField();
        this.MB = new JMenuBar();
        this.M_File = new JMenu();
        this.MI_Save = new JMenuItem();
        this.jSeparator3 = new JSeparator();
        this.MI_SetClose = new JMenuItem();
        this.MI_Close = new JMenuItem();
        this.M_Edit = new JMenu();
        this.M_Special = new JMenu();
        this.MI_LA = new JMenuItem();
        this.MI_ATTR_ENCHANT_CHANCE = new JMenuItem();
        this.MI_CHANCE_ARMOR = new JMenuItem();
        this.MI_CHANCE_WEAPON = new JMenuItem();
        this.MI_XP_PET = new JMenuItem();
        this.MI_Exp = new JMenuItem();
        this.MI_Drop = new JMenuItem();
        this.MI_Adena = new JMenuItem();
        this.MI_Angel = new JMenuItem();
        this.MI_AllBuff = new JMenuItem();
        this.MI_AllRess = new JMenuItem();
        this.L_Name.setText("名字:");
        this.L_Title.setText("稱號:");
        this.L_Account.setText("帳號:");
        this.L_Leavl.setText("等級:");
        this.L_AccessLevel.setText("權限:");
        this.L_Exp.setText(" Exp:");
        this.L_Hp.setText("Hp:");
        this.L_Mp.setText("Mp:");
        this.L_Int.setText("智力:");
        this.L_Str.setText("力量:");
        this.L_Con.setText("體質:");
        this.L_Dex.setText("敏捷:");
        this.L_Wis.setText("精神:");
        this.L_Cha.setText("魅力:");
        TA_Boss.setEditable(false);
        TA_Boss.setLineWrap(true);
        TA_Boss.setWrapStyleWord(true);
        SP_Boss.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.MI_BossStatus = new JMenuItem("BOSS 查看(B)");
        this.MI_BossStatus.setMnemonic('B');
        this.M_Edit.add(this.MI_BossStatus);
        
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.L_Image, -1, 108, 32767).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.L_Image, -1, 180, 32767).addContainerGap()));
        this.L_Clan.setText("血盟:");
        this.L_AccessLevel7.setText("防禦力:");
        this.L_Mp1.setText("性別:");
        this.L_Map.setText("Map:");
        this.L_X.setText("X:");
        this.L_Y.setText("Y:");
        this.TF_Account.setEditable(false);
        this.TF_Name.setEditable(false);
        this.TF_Title.setEditable(false);
        this.TF_Level.setEditable(false);
        this.TF_AccessLevel.setEditable(false);
        this.TF_Clan.setEditable(false);
        this.TF_Exp.setEditable(false);
        this.TF_Hp.setEditable(false);
        this.TF_Mp.setEditable(false);
        this.TF_Sex.setEditable(false);
        this.TF_Str.setEditable(false);
        this.TF_Con.setEditable(false);
        this.TF_Dex.setEditable(false);
        this.TF_Wis.setEditable(false);
        this.TF_Int.setEditable(false);
        this.TF_Cha.setEditable(false);
        this.TF_Ac.setEditable(false);
        this.TF_Map.setEditable(false);
        this.TF_X.setEditable(false);
        this.TF_Y.setEditable(false);
        this.B_Item.setText("物品欄顯示");
        this.B_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                J_Main.this.B_ItemActionPerformed(evt);
            }
        });
        this.CB_Item.setModel(new DefaultComboBoxModel(new String[] { "0,身上物品", "1,倉庫", "2,血盟倉庫", "3,妖森倉庫" }));
        JPanel F_PlayerTop = new JPanel();
        GroupLayout F_PlayerLayout = new GroupLayout(F_PlayerTop);
        F_PlayerTop.setLayout(F_PlayerLayout);
        F_PlayerLayout.setHorizontalGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(F_PlayerLayout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addGap(18, 18, 18).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(F_PlayerLayout.createSequentialGroup().addComponent(this.L_Account).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.TF_Account, -2, 108, -2)).addGroup(F_PlayerLayout.createSequentialGroup().addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.L_Name).addComponent(this.L_Title).addComponent(this.L_Leavl).addComponent(this.L_AccessLevel).addComponent(this.L_Clan).addComponent(this.L_Exp, GroupLayout.Alignment.TRAILING, -1, 27, 32767).addComponent(this.L_Hp, GroupLayout.Alignment.TRAILING).addComponent(this.L_Mp, GroupLayout.Alignment.TRAILING).addComponent(this.L_Mp1, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.TF_Mp, -1, 108, 32767).addComponent(this.TF_Sex, -1, 108, 32767).addComponent(this.TF_Hp, -1, 108, 32767).addComponent(this.TF_Exp, -1, 108, 32767).addComponent(this.TF_Clan, -1, 108, 32767).addComponent(this.TF_AccessLevel, -1, 108, 32767).addComponent(this.TF_Level, -1, 108, 32767).addComponent(this.TF_Title, -1, 108, 32767).addComponent(this.TF_Name, -1, 108, 32767).addComponent(this.CB_Item, 0, -1, 32767)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(F_PlayerLayout.createSequentialGroup().addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.L_Int).addComponent(this.L_Wis).addComponent(this.L_Dex).addComponent(this.L_Cha).addComponent(this.L_AccessLevel7).addComponent(this.L_Con).addComponent(this.L_Str).addComponent(this.L_Map).addComponent(this.L_X)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TF_Str, -2, 108, -2).addComponent(this.TF_Con, -2, 108, -2).addComponent(this.TF_Dex, -2, 108, -2).addComponent(this.TF_Wis, -2, 108, -2).addComponent(this.TF_Int, -2, 108, -2).addComponent(this.TF_Cha, -2, 108, -2).addComponent(this.TF_Ac, -2, 108, -2).addComponent(this.TF_Map, -2, 108, -2).addComponent(this.TF_X, -2, 108, -2))).addGroup(F_PlayerLayout.createSequentialGroup().addComponent(this.L_Y).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.TF_Y, -2, 108, -2))).addComponent(this.B_Item)).addContainerGap(52, 32767)));
        F_PlayerLayout.setVerticalGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(F_PlayerLayout.createSequentialGroup().addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(F_PlayerLayout.createSequentialGroup().addContainerGap().addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Account).addComponent(this.TF_Account, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Name).addComponent(this.TF_Name, -2, 18, -2)).addGap(5, 5, 5).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Title).addComponent(this.TF_Title, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Leavl).addComponent(this.TF_Level, -2, 18, -2)).addGap(5, 5, 5).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_AccessLevel).addComponent(this.TF_AccessLevel, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Clan).addComponent(this.TF_Clan, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Exp).addComponent(this.TF_Exp, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Hp).addComponent(this.TF_Hp, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Mp).addComponent(this.TF_Mp, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Mp1).addComponent(this.TF_Sex, -2, 18, -2).addComponent(this.L_Y).addComponent(this.TF_Y, -2, 18, -2))).addGroup(F_PlayerLayout.createSequentialGroup().addGap(26, 26, 26).addComponent(this.jPanel1, -2, -1, -2)).addGroup(F_PlayerLayout.createSequentialGroup().addContainerGap().addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Str).addComponent(this.TF_Str, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Con).addComponent(this.TF_Con, -2, 18, -2)).addGap(5, 5, 5).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Dex).addComponent(this.TF_Dex, -2, 18, -2)).addGap(5, 5, 5).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Wis).addComponent(this.TF_Wis, -2, 18, -2)).addGap(5, 5, 5).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Int).addComponent(this.TF_Int, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Cha).addComponent(this.TF_Cha, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_AccessLevel7).addComponent(this.TF_Ac, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_Map).addComponent(this.TF_Map, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.L_X).addComponent(this.TF_X, -2, 18, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(F_PlayerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.CB_Item, -2, -1, -2).addComponent(this.B_Item)).addContainerGap(27, 32767)));
        P_Equip = new JPanel(new GridBagLayout());
        P_Equip.setBorder(BorderFactory.createTitledBorder("裝備"));
        GridBagConstraints eg = new GridBagConstraints();
        eg.insets = new Insets(4, 8, 4, 8);
        eg.anchor = GridBagConstraints.LINE_START;

        String[] slotTitles = {
            "武器","頭盔","盔甲","T恤","斗篷","靴子","手套","盾/臂甲",
            "項鍊","腰帶","耳環","戒指1","戒指2","戒指3","戒指4"
        };
        for (int i = 0; i < 15; i++) {
            if (L_EquipName[i] == null) L_EquipName[i] = new JLabel("-");
            JLabel slot = new JLabel(slotTitles[i] + "：");
            eg.gridx = 0; eg.gridy = i; eg.weightx = 0;
            P_Equip.add(slot, eg);
            eg.gridx = 1; eg.gridy = i; eg.weightx = 1; eg.fill = GridBagConstraints.HORIZONTAL;
            P_Equip.add(L_EquipName[i], eg);
        }
        P_Companions = new JPanel(new GridBagLayout());
        P_Companions.setBorder(BorderFactory.createTitledBorder("夥伴（娃娃 / 寵物）"));
        P_Companions.setMinimumSize(new Dimension(200, 0));
        GridBagConstraints cg = new GridBagConstraints();
        cg.insets = new Insets(4, 8, 4, 8);
        cg.anchor = GridBagConstraints.LINE_START;
        for (int i = 0; i < 6; i++) {
            if (L_DollName[i] == null) L_DollName[i] = new JLabel("-");
            cg.gridx = 0; cg.gridy = i;   cg.weightx = 0;
            P_Companions.add(new JLabel("娃娃" + (i + 1) + "："), cg);
            cg.gridx = 1; cg.gridy = i;   cg.weightx = 1; cg.fill = GridBagConstraints.HORIZONTAL;
            P_Companions.add(L_DollName[i], cg);
        }
        for (int i = 0; i < 8; i++) {
            if (L_PetName[i] == null) L_PetName[i] = new JLabel("-");
            int row = 6 + i;
            cg.gridx = 0; cg.gridy = row; cg.weightx = 0; cg.fill = GridBagConstraints.NONE;
            P_Companions.add(new JLabel("寵物" + (i + 1) + "："), cg);
            cg.gridx = 1; cg.gridy = row; cg.weightx = 1; cg.fill = GridBagConstraints.HORIZONTAL;
            P_Companions.add(L_PetName[i], cg);
        }
        JPanel bottomRow = new JPanel(new BorderLayout(12, 0));
        bottomRow.setBorder(BorderFactory.createEmptyBorder(10, 8, 8, 8));
        bottomRow.add(P_Equip, BorderLayout.CENTER);
        bottomRow.add(P_Companions, BorderLayout.EAST);
        JPanel F_PlayerRoot = new JPanel(new BorderLayout());
        F_PlayerRoot.add(F_PlayerTop, BorderLayout.NORTH);
        F_PlayerRoot.add(bottomRow,  BorderLayout.CENTER);
        this.F_Player.setContentPane(F_PlayerRoot);

        this.MI_Kill.setMnemonic('K');
        this.MI_Kill.setText("強制踢除(K)");
        this.PM_Player.add(this.MI_Kill);
        this.MI_BanIP.setMnemonic('B');
        this.MI_BanIP.setText("封鎖IP(B)");
        this.PM_Player.add(this.MI_BanIP);
        this.PM_Player.add(this.jSeparator1);
        this.MI_ShowPlayer.setMnemonic('P');
        this.MI_ShowPlayer.setText("玩家資料(P)");
        this.PM_Player.add(this.MI_ShowPlayer);
        this.PM_Player.add(this.jSeparator2);
        this.MI_Whisper.setMnemonic('W');
        this.MI_Whisper.setText("密語(W)");
        this.PM_Player.add(this.MI_Whisper);
        this.jLabel1.setText("jLabel1");
        this.D_Item.getContentPane().setLayout(new GridLayout(1, 0));
        this.jScrollPane1.setViewportView(this.T_Item);
        this.D_Item.getContentPane().add(this.jScrollPane1);
        this.setDefaultCloseOperation(3);
        String startTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        this.setTitle(ConfigGUI.GuiServerName + " - 啟動時間: " + startTime);
        this.setLocationByPlatform(true);
        this.setMinimumSize(new Dimension(1024, 768));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent evt) {
                J_Main.this.formWindowClosed(evt);
            }
        });
        
        this.DTM_Boss = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        this.SP_Boss.setAutoscrolls(true);
        this.TA_Boss.setColumns(20);
        this.TA_Boss.setEditable(false);
        this.TA_Boss.setRows(5);
        this.TA_Boss.setForeground(new Color(255, 255, 255));
        this.TA_Boss.setBackground(new Color(0, 0, 0));
        this.SP_Boss.setViewportView(this.TA_Boss);
        this.TP.addTab("BOSS 查看", this.SP_Boss);
        
        
        this.SP_Split.setDividerLocation(550);
        this.SP_Consol.setAutoscrolls(true);
        this.TA_Consol.setBackground(new Color(0, 0, 0));
        this.TA_Consol.setColumns(20);
        this.TA_Consol.setEditable(false);
        this.TA_Consol.setForeground(new Color(0, 0, 0));
        this.TA_Consol.setRows(5);
        this.TA_Consol.setEnabled(false);
        this.SP_Consol.setViewportView(this.TA_Consol);
        this.TP.addTab("掉寶公告", this.SP_Consol);
        this.SP_AllChat.setAutoscrolls(true);
        this.TA_AllChat.setBackground(new Color(0, 0, 0));
        this.TA_AllChat.setColumns(20);
        this.TA_AllChat.setEditable(false);
        this.TA_AllChat.setForeground(new Color(0, 0, 0));
        this.TA_AllChat.setRows(5);
        this.SP_AllChat.setViewportView(this.TA_AllChat);
        this.TP.addTab("全部頻道", this.SP_AllChat);
        this.SP_World.setAutoscrolls(true);
        this.TA_World.setColumns(20);
        this.TA_World.setEditable(false);
        this.TA_World.setForeground(new Color(0, 0, 0));
        this.TA_World.setRows(5);
        this.TA_World.setEnabled(false);
        this.SP_World.setViewportView(this.TA_World);
        this.TP.addTab("世界 ", this.SP_World);
        this.SP_Normal.setAutoscrolls(true);
        this.TA_Normal.setColumns(20);
        this.TA_Normal.setEditable(false);
        this.TA_Normal.setRows(5);
        this.TA_Normal.setEnabled(false);
        this.SP_Normal.setViewportView(this.TA_Normal);
        this.TP.addTab("一般", this.SP_Normal);
        this.SP_.setAutoscrolls(true);
        this.TA_Private.setColumns(20);
        this.TA_Private.setEditable(false);
        this.TA_Private.setForeground(new Color(0, 0, 0));
        this.TA_Private.setRows(5);
        this.TA_Private.setEnabled(false);
        this.SP_.setViewportView(this.TA_Private);
        this.TP.addTab("密語", this.SP_);
        this.SP_Clan.setAutoscrolls(true);
        this.TA_Clan.setColumns(20);
        this.TA_Clan.setEditable(false);
        this.TA_Clan.setForeground(new Color(0, 0, 0));
        this.TA_Clan.setRows(5);
        this.TA_Clan.setEnabled(false);
        this.SP_Clan.setViewportView(this.TA_Clan);
        this.TP.addTab("血盟", this.SP_Clan);
        this.SP_Team.setAutoscrolls(true);
        this.TA_Team.setColumns(20);
        this.TA_Team.setEditable(false);
        this.TA_Team.setForeground(new Color(0, 0, 0));
        this.TA_Team.setRows(5);
        this.TA_Team.setEnabled(false);
        this.SP_Team.setViewportView(this.TA_Team);
        this.TP.addTab("組隊", this.SP_Team);
        this.SP_Split.setLeftComponent(this.TP);
        this.T_Player.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent evt) { J_Main.this.T_PlayerMousePressed(evt); }
            @Override public void mouseReleased(MouseEvent evt) { J_Main.this.T_PlayerMouseReleased(evt); }
        });

        this.SP_player.setViewportView(this.T_Player);
        this.SP_Split.setRightComponent(this.SP_player);
        this.getContentPane().add(this.SP_Split, "Center");
        this.CB_Channel.setModel(new DefaultComboBoxModel(new String[] { "訊息頻道", "密語" }));
        this.B_Submit.setText("發送  刷新BOSS");
        this.B_Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                J_Main.this.B_SubmitActionPerformed(evt);
                queryBossStatus();
                TP.setSelectedComponent(SP_Boss);
            }
        });
        this.TF_Msg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                J_Main.this.TF_MsgKeyPressed(evt);
            }
        });
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.CB_Channel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.TF_Target, -2, 68, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.TF_Msg, -2, 310, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.B_Submit).addGap(175, 175, 175)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(6, 6, 6).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.CB_Channel, -2, -1, -2).addComponent(this.TF_Target, -2, -1, -2).addComponent(this.TF_Msg, -2, -1, -2).addComponent(this.B_Submit))));
        this.getContentPane().add(this.jPanel2, "South");
        this.M_File.setMnemonic('F');
        this.M_File.setText("檔案(F)");
        this.MI_Save.setAccelerator(KeyStroke.getKeyStroke(83, 2));
        this.MI_Save.setMnemonic('S');
        this.MI_Save.setText("儲存訊息(S)");
        this.M_File.add(this.MI_Save);
        this.M_File.add(this.jSeparator3);
        this.MI_SetClose.setAccelerator(KeyStroke.getKeyStroke(69, 2));
        this.MI_SetClose.setMnemonic('E');
        this.MI_SetClose.setText("設定關閉伺服器(E)...");
        this.M_File.add(this.MI_SetClose);
        this.MI_Close.setMnemonic('C');
        this.MI_Close.setText("關閉伺服器(C)");
        this.M_File.add(this.MI_Close);
        this.MB.add(this.M_File);
        this.M_Edit.setMnemonic('E');
        this.M_Edit.setText("編輯(E)");
        this.MI_LA.setMnemonic('W');
        this.MI_LA.setText("正義倍率(W)");
        this.M_Edit.add(this.MI_LA);
        this.MI_ATTR_ENCHANT_CHANCE.setMnemonic('X');
        this.MI_ATTR_ENCHANT_CHANCE.setText("屬性強化倍率(X)");
        this.M_Edit.add(this.MI_ATTR_ENCHANT_CHANCE);
        this.MI_CHANCE_ARMOR.setMnemonic('S');
        this.MI_CHANCE_ARMOR.setText("防具強化倍率(S)");
        this.M_Edit.add(this.MI_CHANCE_ARMOR);
        this.MI_CHANCE_WEAPON.setMnemonic('Q');
        this.MI_CHANCE_WEAPON.setText("武器強化倍率(Q)");
        this.M_Edit.add(this.MI_CHANCE_WEAPON);
        this.MI_XP_PET.setMnemonic('G');
        this.MI_XP_PET.setText("寵物經驗倍率(G)");
        this.M_Edit.add(this.MI_XP_PET);
        this.MI_Exp.setMnemonic('F');
        this.MI_Exp.setText("經驗倍率(F)");
        this.M_Edit.add(this.MI_Exp);
        this.MI_Exp.setMnemonic('F');
        this.MI_Exp.setText("經驗倍率(F)");
        this.MI_Drop.setMnemonic('D');
        this.MI_Drop.setText("掉寶率(D)");
        this.MI_Adena.setMnemonic('M');
        this.MI_Adena.setText("掉錢倍率(M)");
        this.M_Edit.add(this.MI_Exp);
        this.M_Edit.add(this.MI_Drop);
        this.M_Edit.add(this.MI_Adena);
        this.MB.add(this.M_Edit);
        this.M_Special.setMnemonic('S');
        this.M_Special.setText("特殊功能(S)");
        this.MI_Angel.setMnemonic('A');
        this.MI_Angel.setText("大天使祝福(A)");
        this.M_Special.add(this.MI_Angel);
        this.MI_AllBuff.setMnemonic('B');
        this.MI_AllBuff.setText("終極祝福(B)");
        this.M_Special.add(this.MI_AllBuff);
        this.MI_AllRess.setMnemonic('R');
        this.MI_AllRess.setText("全體復活補血魔(R)");
        this.M_Special.add(this.MI_AllRess);
        this.MB.add(this.M_Special);
        this.setJMenuBar(this.MB);
        this.pack();
    }
    
    private void openSelectedRowPlayer() {
        int viewRow = T_Player.getSelectedRow();
        if (viewRow < 0) return;
        int modelRow = T_Player.convertRowIndexToModel(viewRow);
        String login    = String.valueOf(DTM.getValueAt(modelRow, 0));
        String charName = String.valueOf(DTM.getValueAt(modelRow, 1));
        L1PcInstance pc = null;
        ClientExecutor cl = OnlineUser.get().get(login);
        if (cl != null) pc = cl.getActiveChar();
        if (pc == null) pc = World.get().getPlayer(charName);
        if (pc == null) return;
        final L1PcInstance target = pc;
        SwingUtilities.invokeLater(() -> {
            refreshEquipmentFromPc(target);
            refreshCompanionsPanel(target);
            startCompanionsAutoRefresh(target.getName());
        });
    }
    private void startCompanionsAutoRefresh(String pcName) {
        currentFPlayerName = pcName;
        if (companionsTimer != null) companionsTimer.stop();
        companionsTimer = new javax.swing.Timer(5000, e -> {
            if (!F_Player.isVisible() || currentFPlayerName == null) return;
            L1PcInstance p = World.get().getPlayer(currentFPlayerName);
            if (p != null) {
                refreshEquipmentFromPc(p);
                refreshCompanionsPanel(p);
            }
        });
        companionsTimer.start();
        F_Player.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosed(java.awt.event.WindowEvent e)  { if (companionsTimer != null) companionsTimer.stop(); }
            @Override public void windowClosing(java.awt.event.WindowEvent e){ if (companionsTimer != null) companionsTimer.stop(); }
        });
    }


    
    private void T_PlayerMouseReleased(MouseEvent evt) {
        int viewRow = T_Player.getSelectedRow();
        if (viewRow == -1) return;
        int modelRow = T_Player.convertRowIndexToModel(viewRow);
        this.select = modelRow;
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            this.setPlayerView((String) this.DTM.getValueAt(modelRow, 1));
            this.F_Player.pack();
            this.F_Player.setVisible(true);
        }
        if (evt.isPopupTrigger()) {
            this.PM_Player.show(this.T_Player, evt.getX(), evt.getY());
        }
    }
    private void formWindowClosed(WindowEvent evt) {
        this.closeServer();
    }
    private void closeServer() {
        this.saveChatData(false);
        System.exit(0);
    }
    private void T_PlayerMousePressed(MouseEvent evt) {
        this.processEvent(evt);
    }
    
    private void B_SubmitActionPerformed(ActionEvent evt) {
        this.submitMsg(this.CB_Channel.getSelectedIndex());
    }
    
    private void TF_MsgKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.submitMsg(this.CB_Channel.getSelectedIndex());
        }
    }
    
    private void submitMsg(int select) {
        if (this.TF_Msg.getText().equals("")) {
            return;
        }
        else {
            switch (select) {
                case 0: {
                    World.get().broadcastServerMessage("【管理器訊息】:" + this.TF_Msg.getText());
                    this.addWorldChat("【管理器】", this.TF_Msg.getText());
                    break;
                }
                case 1: {
                    if (World.get().getPlayer(this.TF_Target.getText()) == null) {
                        return;
                    }
                    else {
                        L1PcInstance target = World.get().getPlayer(this.TF_Target.getText());
                        target.sendPackets(new S_SystemMessage("【管理器密語】:" + this.TF_Msg.getText()));
                        this.addPrivateChat("【管理器】", this.TF_Target.getText(), this.TF_Msg.getText());
                        break;
                    }
                }
            }
            this.TF_Msg.setText("");
            return;
        }
    }
    
    private void showItemTable(int num) {
        this.iniTable();
        L1PcInstance pc = L1PcInstance.load(this.TF_Name.getText());
        if (pc.getInventory().getSize() == 0) {
            C_LoginToServer.items(pc);
        }
        L1Inventory inv = null;
        switch (num) {
            case 0: {
                if (pc.getInventory() == null) {
                    return;
                }
                else {
                    inv = pc.getInventory();
                    this.D_Item.setTitle("身上物品");
                    for (L1ItemInstance item : inv.getItems()) {
                        this.addItemTable(item.getName(), item.getCount(), (long)item.getItemId());
                    }
                    break;
                }
            }
            case 1: {
                if (pc.getDwarfInventory() == null) {
                    return;
                }
                else {
                    this.D_Item.setTitle("倉庫物品");
                    inv = pc.getDwarfInventory();
                    for (L1ItemInstance item : inv.getItems()) {
                        this.addItemTable(item.getName(), item.getCount(), (long)item.getItemId());
                    }
                    break;
                }
            }
            case 2: {
                if (pc.getClan().getDwarfForClanInventory() == null) {
                    return;
                }
                else {
                    this.D_Item.setTitle("血盟倉庫物品");
                    inv = pc.getClan().getDwarfForClanInventory();
                    for (L1ItemInstance item : inv.getItems()) {
                        this.addItemTable(item.getName(), item.getCount(), (long)item.getItemId());
                    }
                    break;
                }
            }
            case 3: {
                if (pc.getDwarfForElfInventory() == null) {
                    return;
                }
                else {
                    this.D_Item.setTitle("妖森倉庫物品");
                    inv = pc.getDwarfForElfInventory();
                    for (L1ItemInstance item : inv.getItems()) {
                        this.addItemTable(item.getName(), item.getCount(), (long)item.getItemId());
                    }
                    break;
                }
            }
        }
        this.D_Item.setVisible(true);
    }
    
    private void B_ItemActionPerformed(ActionEvent evt) {
        this.showItemTable(this.CB_Item.getSelectedIndex());
    }
    
    private void setPlayerView(String name) {
        L1PcInstance pc = L1PcInstance.load(name);
        int job = 0;
        switch (pc.getClassId()) {
            case 0: {
                job = 715;
                break;
            }
            case 1: {
                job = 647;
                break;
            }
            case 37: {
                job = 198;
                break;
            }
            case 48: {
                job = 317;
                break;
            }
            case 61: {
                job = 384;
                break;
            }
            case 138: {
                job = 247;
                break;
            }
            case 734: {
                job = 532;
                break;
            }
            case 1186: {
                job = 452;
                break;
            }
            case 2786: {
                job = 145;
                break;
            }
            case 2796: {
                job = 25;
                break;
            }
            case 6650: {
                job = 1056;
                break;
            }
            case 6658: {
                job = 903;
                break;
            }
            case 6661: {
                job = 930;
                break;
            }
            case 6671: {
                job = 1029;
                break;
            }
        }
        Icon icon;
        ImageIcon imageIcon = (ImageIcon)(icon = new ImageIcon("img/" + job + ".png"));
        this.L_Image.setIcon(icon);
        this.TF_Account.setText(pc.getAccountName());
        this.TF_Name.setText(pc.getName());
        this.TF_Title.setText(pc.getTitle());
        this.TF_AccessLevel.setText(new StringBuilder().append((int)pc.getAccessLevel()).toString());
        this.TF_Sex.setText((pc.get_sex() == 1) ? "女" : "男");
        this.TF_Ac.setText(new StringBuilder(String.valueOf(pc.getAc())).toString());
        this.TF_Cha.setText(new StringBuilder(String.valueOf((int)pc.getCha())).toString());
        this.TF_Int.setText(new StringBuilder(String.valueOf((int)pc.getInt())).toString());
        this.TF_Str.setText(new StringBuilder(String.valueOf((int)pc.getStr())).toString());
        this.TF_Con.setText(new StringBuilder(String.valueOf((int)pc.getCon())).toString());
        this.TF_Wis.setText(new StringBuilder(String.valueOf((int)pc.getWis())).toString());
        this.TF_Dex.setText(new StringBuilder(String.valueOf((int)pc.getDex())).toString());
        this.TF_Exp.setText(new StringBuilder(String.valueOf(pc.getExp())).toString());
        this.TF_Map.setText(new StringBuilder(String.valueOf((int)pc.getMapId())).toString());
        this.TF_X.setText(new StringBuilder(String.valueOf(pc.getX())).toString());
        this.TF_Y.setText(new StringBuilder(String.valueOf(pc.getY())).toString());
        this.TF_Clan.setText(pc.getClanname());
        this.TF_Level.setText(new StringBuilder(String.valueOf(pc.getLevel())).toString());
        this.TF_Hp.setText(String.valueOf(pc.getCurrentHp()) + " / " + pc.getMaxHp());
        this.TF_Mp.setText(String.valueOf(pc.getCurrentMp()) + " / " + pc.getMaxMp());
        refreshEquipmentFromPc(pc);
        refreshCompanionsPanel(pc);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new J_Main().setVisible(true);
            }
        });
    }

    private void refreshEquipmentFromPc(L1PcInstance pc) {
        for (int i = 0; i < L_EquipName.length; i++) {
            if (L_EquipName[i] == null) L_EquipName[i] = new JLabel("-");
            L_EquipName[i].setText("-");
        }
        if (pc == null || pc.getInventory() == null) return;

        for (com.lineage.server.model.Instance.L1ItemInstance it : pc.getInventory().getItems()) {
            if (it == null || it.getItem() == null) continue;
            if (!it.isEquipped()) continue;

            int type2 = it.getItem().getType2();
            int type  = it.getItem().getType();
            if (type2 == 1) {
                assignEquipWeapon(0, it);
            } else if (type2 == 2) {
                switch (type) {
                    case 1:  assignEquipArmor(1,  it); break;
                    case 2:  assignEquipArmor(2,  it); break;
                    case 3:  assignEquipArmor(3,  it); break;
                    case 4:  assignEquipArmor(4,  it); break;
                    case 6:  assignEquipArmor(5,  it); break;
                    case 5:  assignEquipArmor(6,  it); break;
                    case 7:
                    case 13: assignEquipArmor(7,  it); break;
                    case 8:  assignEquipArmor(8,  it); break;
                    case 10: assignEquipArmor(9,  it); break;
                    case 12: assignEquipArmor(10, it); break;
                    case 9:
                        for (int i = 11; i <= 14; i++) {
                            if ("-".equals(L_EquipName[i].getText())) { assignEquipArmor(i, it); break; }
                        }
                        break;
                }
            }
        }
    }


    private void assignEquipWeapon(int idx, com.lineage.server.model.Instance.L1ItemInstance it) {
        putEquipText(idx, formatItemName(it));
    }
    private void assignEquipArmor(int idx, com.lineage.server.model.Instance.L1ItemInstance it) {
        putEquipText(idx, formatItemName(it));
    }

    private void putEquipText(int idx, String text) {
        if (idx < 0 || idx >= L_EquipName.length) return;
        JLabel lab = L_EquipName[idx];
        if (lab == null) return;
        lab.setText((text != null && !text.isEmpty()) ? text : "-");
    }

    private String formatItemName(com.lineage.server.model.Instance.L1ItemInstance it) {
        if (it == null) return "-";
        String name = null;
        try { 
            name = it.getAllName();
        } catch (Throwable ignore) {}

        if (name == null || name.isEmpty()) {
            try { 
                name = it.getName();
            } catch (Throwable ignore) {}
        }
        if (name == null || name.isEmpty()) {
            name = "裝備";
        }
        return name;
    }



    private void refreshCompanionsPanel(L1PcInstance pc) {
        for (int i = 0; i < 6; i++) {
            if (L_DollName[i] == null) L_DollName[i] = new JLabel("-");
            L_DollName[i].setText("-");
        }
        for (int i = 0; i < 8; i++) {
            if (L_PetName[i] == null) L_PetName[i] = new JLabel("-");
            L_PetName[i].setText("-");
        }
        if (pc == null) return;

        int di = 0;
        try {
            Map<Integer, L1DollInstance> dolls = pc.getDolls();
            if (dolls != null && !dolls.isEmpty()) {
                for (L1DollInstance doll : dolls.values()) {
                    if (di >= 6) break;
                    L_DollName[di++].setText(getDollDisplayName(pc, doll));
                }
            }
        } catch (Throwable ignore) {}

        int pi = 0;
        try {
            Map<Integer, L1NpcInstance> pets = pc.getPetList();
            if (pets != null && !pets.isEmpty()) {
                for (L1NpcInstance pet : pets.values()) {
                    if (pi >= 8) break;
                    L_PetName[pi++].setText(safeNpcName(pet));
                }
            }
        } catch (Throwable ignore) {}
    }

    private static String getDollDisplayName(L1PcInstance pc, L1DollInstance doll) {
        try {
            L1ItemInstance it = pc.getInventory().getItem(doll.getItemObjId());
            if (it != null) {
                String n = it.getAllName();
                if (n == null || n.isEmpty()) n = it.getName();
                return normalizeDollName(n);
            }
        } catch (Throwable ignore) {}
        try {
            Object type = null;
            try { type = doll.getClass().getMethod("get_type").invoke(doll); }
            catch (NoSuchMethodException e) {
                try { type = doll.getClass().getMethod("getDoll").invoke(doll); } catch (Exception ignore) {}
            }
            if (type != null) {
                String n = null;
                try { n = (String) type.getClass().getMethod("get_nameid").invoke(type); } catch (Exception ignore) {}
                if (n == null || n.isEmpty()) {
                    try { n = (String) type.getClass().getMethod("get_typeNote").invoke(type); } catch (Exception ignore) {}
                }
                if (n != null && !n.isEmpty()) return normalizeDollName(n);
            }
        } catch (Throwable ignore) {}
        try { return normalizeDollName(doll.getName()); } catch (Throwable ignore) {}
        return "(魔法娃娃)";
    }
    private static String normalizeDollName(String name) {
        if (name == null) return "(魔法娃娃)";
        name = name.replace("魔法娃娃", "").replace("娃娃卡","").replace("卡片","").trim();
        return name.isEmpty() ? "(魔法娃娃)" : name;
    }
    private static String safeNpcName(L1NpcInstance n) {
        if (n == null) return "-";
        String s = n.getName();
        if (s != null && !s.isEmpty()) return s;

        L1Npc tpl = n.getNpcTemplate();
        if (tpl != null) {
            try {
                String n1;
                try { n1 = (String) tpl.getClass().getMethod("get_name").invoke(tpl); }
                catch (NoSuchMethodException e) {
                    n1 = (String) tpl.getClass().getMethod("getName").invoke(tpl);
                }
                if (n1 != null && !n1.isEmpty()) return n1;
            } catch (Exception ignore) {}
        }
        return "(未知)";
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ((long)(e.getModifiers() & 0x10) == 0L && ((e.getModifiers() & 0x4) != 0x0 || (e.getModifiers() & 0x8) != 0x0)) {
            return;
        }
        else {
            if (command.equals("強制踢除(K)")) {
                L1PcInstance target = World.get().getPlayer((String)this.DTM.getValueAt(this.select, 1));
                if (target != null) {
                    this.addConsol("您把玩家：" + (String)this.DTM.getValueAt(this.select, 1) + "強制剔除遊戲。");
                    target.sendPackets(new S_Disconnect());
                }
                else {
                    this.addConsol("此玩家不在線上。");
                }
            }
            else if (command.equals("封鎖IP(B)")) {
                L1PcInstance target = World.get().getPlayer((String)this.DTM.getValueAt(this.select, 2));
                if (target != null) {
                    ClientExecutor targetClient = target.getNetConnection();
                    String ipaddr = targetClient.getIp().toString();
                    if (ipaddr != null && !LanSecurityManager.BANIPMAP.containsKey(ipaddr)) {
                        IpReading.get().add(ipaddr.toString(), this.getName() + ":L1PowerKick 封鎖IP");
                    }
                    targetClient.kick();
                }
                this.addConsol("已封鎖" + target.getName() + "的IP");
            }
            else if (command.equals("玩家資料(P)")) {
                this.setPlayerView((String)this.DTM.getValueAt(this.select, 1));
                this.F_Player.pack();
                this.F_Player.setVisible(true);
            }
            else if (command.equals("密語(W)")) {
                this.TF_Target.setText((String)this.DTM.getValueAt(this.select, 1));
                this.CB_Channel.setSelectedIndex(1);
            }
            else if (command.equals("儲存訊息(S)")) {
                this.saveChatData(false);
            }
            else if (command.equals("大天使祝福(A)")) {
                this.angel();
            }
            else if (command.equals("關閉伺服器(C)")) {
                this.closeServer();
            }
            else if (command.equals("BOSS 刷新(B)")) {
                queryBossStatus();
            }
            else if (command.equals("正義倍率(W)")) {
                String temp = "";
                try {
                    temp = JOptionPane.showInputDialog("當前服務正義倍率：" + ConfigRate.RATE_LA + " 請輸入新倍率：");
                    if (temp == null || temp.equals("")) {
                        return;
                    }
                    else {
                        int second = Integer.valueOf(temp);
                        ConfigRate.RATE_LA = (double)second;
                        World.get().broadcastServerMessage("正義倍率變更為：" + ConfigRate.RATE_LA + "倍。");
                        this.addConsol(" 正義率變更為：" + ConfigRate.RATE_LA + "倍。");
                    }
                }
                catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(this, "請輸入整數!");
                }
            }
            else if (command.equals("屬性強化倍率(X)")) {
                String temp = "";
                try {
                    temp = JOptionPane.showInputDialog("當前服務屬性強化%：" + ConfigRate.ATTR_ENCHANT_CHANCE + " 請輸入新倍率：");
                    if (temp == null || temp.equals("")) {
                        return;
                    }
                    else {
                        int second = ConfigRate.ATTR_ENCHANT_CHANCE = Integer.valueOf(temp);
                        World.get().broadcastServerMessage("屬性強化%變更為：" + ConfigRate.ATTR_ENCHANT_CHANCE + "%。");
                        this.addConsol(" 屬性強化%變更為：" + ConfigRate.ATTR_ENCHANT_CHANCE + "%。");
                    }
                }
                catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(this, "請輸入整數!");
                }
            }
            else if (command.equals("防具強化倍率(S)")) {
                String temp = "";
                try {
                    temp = JOptionPane.showInputDialog("當前服務防具強化倍率：" + ConfigRate.ENCHANT_CHANCE_ARMOR + " 請輸入新倍率：");
                    if (temp == null || temp.equals("")) {
                        return;
                    }
                    else {
                        int second = ConfigRate.ENCHANT_CHANCE_ARMOR = Integer.valueOf(temp);
                        World.get().broadcastServerMessage("防具強化%變更為：" + ConfigRate.ENCHANT_CHANCE_ARMOR + "%。");
                        this.addConsol(" 防具強化率變更為：" + ConfigRate.ENCHANT_CHANCE_ARMOR + "%。");
                    }
                }
                catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(this, "請輸入整數!");
                }
            }
            else if (command.equals("武器強化倍率(Q)")) {
                String temp = "";
                try {
                    temp = JOptionPane.showInputDialog("當前服務武器強化%：" + ConfigRate.ENCHANT_CHANCE_WEAPON + " 請輸入新倍率：");
                    if (temp == null || temp.equals("")) {
                        return;
                    }
                    else {
                        int second = ConfigRate.ENCHANT_CHANCE_WEAPON = Integer.valueOf(temp);
                        World.get().broadcastServerMessage("武器強化%變更為：" + ConfigRate.ENCHANT_CHANCE_WEAPON + "%。");
                        this.addConsol(" 武器強化%變更為：" + ConfigRate.ENCHANT_CHANCE_WEAPON + "%。");
                    }
                }
                catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(this, "請輸入整數!");
                }
            }
            else if (command.equals("寵物經驗倍率(G)")) {
                String temp = "";
                try {
                    temp = JOptionPane.showInputDialog("當前服務器寵物經驗倍率：" + ConfigRate.RATE_XP + " 請輸入新倍率：");
                    if (temp == null || temp.equals("")) {
                        return;
                    }
                    else {
                        int second = Integer.valueOf(temp);
                        ConfigRate.RATE_XP = (double)second;
                        World.get().broadcastServerMessage("遊戲寵物經驗倍率變更為：" + ConfigRate.RATE_XP + "倍。");
                        this.addConsol(" 遊戲寵物經驗倍率變更為：" + ConfigRate.RATE_XP + "倍。");
                    }
                }
                catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(this, "請輸入整數!");
                }
            }
            else if (command.equals("經驗倍率(F)")) {
                String temp = "";
                try {
                    temp = JOptionPane.showInputDialog("當前服務器經驗倍率：" + ConfigRate.RATE_XP + " 請輸入新倍率：");
                    if (temp == null || temp.equals("")) {
                        return;
                    }
                    else {
                        int second = Integer.valueOf(temp);
                        ConfigRate.RATE_XP = (double)second;
                        World.get().broadcastServerMessage("遊戲經驗倍率變更為：" + ConfigRate.RATE_XP);
                        this.addConsol(" 遊戲經驗倍率變更為：" + ConfigRate.RATE_XP);
                    }
                }
                catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(this, "請輸入整數!");
                }
            }
            else if (command.equals("掉錢倍率(M)")) {
                String temp = "";
                try {
                    temp = JOptionPane.showInputDialog("當前服務器掉錢率：" + ConfigRate.RATE_DROP_ADENA + " 請輸入新倍率：");
                    if (temp == null || temp.equals("")) {
                        return;
                    }
                    else {
                        int second = Integer.valueOf(temp);
                        ConfigRate.RATE_DROP_ADENA = (double)second;
                        World.get().broadcastServerMessage("掉錢倍率變更為：" + ConfigRate.RATE_DROP_ADENA);
                        this.addConsol(" 掉錢倍率變更為：" + ConfigRate.RATE_DROP_ADENA);
                    }
                }
                catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(this, "請輸入整數!");
                }
            }
            else if (command.equals("掉寶率(D)")) {
                String temp = "";
                try {
                    temp = JOptionPane.showInputDialog("當前服務器掉寶倍率：" + ConfigRate.RATE_DROP_ITEMS + " 請輸入新倍率：");
                    if (temp == null || temp.equals("")) {
                        return;
                    }
                    else {
                        int second = Integer.valueOf(temp);
                        ConfigRate.RATE_DROP_ITEMS = (double)second;
                        World.get().broadcastServerMessage("掉寶率變更為：" + ConfigRate.RATE_DROP_ITEMS);
                        this.addConsol(" 掉寶率變更為：" + ConfigRate.RATE_DROP_ITEMS);
                    }
                }
                catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(this, "請輸入整數!");
                }
            }
            else if (command.equals("設定關閉伺服器(E)...")) {
                String temp = "";
                try {
                    temp = JOptionPane.showInputDialog("請輸入幾秒重後重開!");
                    if (temp == null || temp.equals("")) {
                        return;
                    }
                    else {
                        int second = Integer.valueOf(temp);
                        if (second == 0) {
                            this.closeServer();
                        }
                        Shutdown.getInstance().startShutdown(null, second, true);
                        World.get().broadcastServerMessage("伺服器將於(" + second + ")秒鐘後關閉伺服器!");
                        this.addWorldChat("管理器", "伺服器將於(" + second + ")秒鐘後關閉伺服器!");
                    }
                }
                catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(this, "請輸入整數!");
                }
            }
            else if (command.equals("終極祝福(B)")) {
                int[] allBuffSkill = { 14, 26, 42, 48, 55, 68, 79, 88, 89, 90, 98, 102, 104, 105, 106, 111, 114, 117, 129, 137, 147, 160, 163, 168, 169, 170, 171, 175, 176 };
                for (L1PcInstance targetpc : World.get().getAllPlayers()) {
                    L1BuffUtil.haste(targetpc, 3600000);
                    L1BuffUtil.brave(targetpc, 3600000);
                    int[] array;
                    for (int length = (array = allBuffSkill).length, i = 0; i < length; ++i) {
                        int element = array[i];
                        if (element == 26 || element == 42) {
                            L1Skills skill = SkillsTable.get().getTemplate(element);
                            new L1SkillUse().handleCommands(targetpc, element, targetpc.getId(), targetpc.getX(), targetpc.getY(), skill.getBuffDuration(), 4);
                        }
                        else {
                            L1Skills skill = SkillsTable.get().getTemplate(element);
                            new L1SkillUse().handleCommands(targetpc, element, targetpc.getId(), targetpc.getX(), targetpc.getY(), skill.getBuffDuration(), 4);
                        }
                    }
                    targetpc.sendPackets(new S_ServerMessage(166, "祝福降臨人世,全體玩家得到祝福GM是個大好人"));
                }
            }
            else if (command.equals("全體復活補血魔(R)")) {
                for (L1PcInstance tg : World.get().getAllPlayers()) {
                    if (tg.getCurrentHp() == 0 && tg.isDead()) {
                        tg.sendPackets(new S_SystemMessage("GM幫你復活嚕。"));
                        tg.broadcastPacketX10(new S_SkillSound(tg.getId(), 3944));
                        tg.sendPackets(new S_SkillSound(tg.getId(), 3944));
                        tg.setTempID(tg.getId());
                        tg.sendPackets(new S_Message_YN(322, ""));
                    }
                    else {
                        tg.sendPackets(new S_SystemMessage("GM幫你治癒嚕。"));
                        tg.broadcastPacketX10(new S_SkillSound(tg.getId(), 832));
                        tg.sendPackets(new S_SkillSound(tg.getId(), 832));
                        tg.setCurrentHp(tg.getMaxHp());
                        tg.setCurrentMp(tg.getMaxMp());
                    }
                }
            }
            return;
        }
    }
    
    private void angel() {
        for (L1PcInstance pc : World.get().getAllPlayers()) {
            if (pc.hasSkillEffect(71)) {
                pc.sendPackets(new S_ServerMessage(698));
                return;
            }
            else {
                int time = 3600;
                if (pc.hasSkillEffect(78)) {
                    pc.killSkillEffectTimer(78);
                    pc.startHpRegeneration();
                    pc.startMpRegeneration();
                    pc.startMpRegeneration();
                }
                if (pc.hasSkillEffect(1016)) {
                    pc.killSkillEffectTimer(1016);
                    pc.sendPackets(new S_SkillBrave(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillBrave(pc.getId(), 0, 0));
                    pc.setBraveSpeed(0);
                }
                if (pc.hasSkillEffect(52)) {
                    pc.killSkillEffectTimer(52);
                    pc.sendPackets(new S_SkillBrave(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillBrave(pc.getId(), 0, 0));
                    pc.setBraveSpeed(0);
                }
                if (pc.hasSkillEffect(101)) {
                    pc.killSkillEffectTimer(101);
                    pc.sendPackets(new S_SkillBrave(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillBrave(pc.getId(), 0, 0));
                    pc.setBraveSpeed(0);
                }
                if (pc.hasSkillEffect(150)) {
                    pc.killSkillEffectTimer(150);
                    pc.sendPackets(new S_SkillBrave(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillBrave(pc.getId(), 0, 0));
                    pc.setBraveSpeed(0);
                }
                if (pc.hasSkillEffect(1017)) {
                    pc.killSkillEffectTimer(1017);
                    pc.setBraveSpeed(0);
                }
                pc.sendPackets(new S_SkillBrave(pc.getId(), 1, time));
                pc.broadcastPacketX10(new S_SkillBrave(pc.getId(), 1, 0));
                pc.sendPackets(new S_SkillSound(pc.getId(), 751));
                pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 751));
                pc.setSkillEffect(1000, time * 1000);
                pc.setBraveSpeed(1);
                pc.setDrink(false);
                if (pc.hasSkillEffect(43)) {
                    pc.killSkillEffectTimer(43);
                    pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.setMoveSpeed(0);
                }
                else if (pc.hasSkillEffect(54)) {
                    pc.killSkillEffectTimer(54);
                    pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.setMoveSpeed(0);
                }
                else if (pc.hasSkillEffect(1001)) {
                    pc.killSkillEffectTimer(1001);
                    pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.setMoveSpeed(0);
                }
                if (pc.hasSkillEffect(29)) {
                    pc.killSkillEffectTimer(29);
                    pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillHaste(pc.getId(), 0, 0));
                }
                else if (pc.hasSkillEffect(76)) {
                    pc.killSkillEffectTimer(76);
                    pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillHaste(pc.getId(), 0, 0));
                }
                else if (pc.hasSkillEffect(152)) {
                    pc.killSkillEffectTimer(152);
                    pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.broadcastPacketX10(new S_SkillHaste(pc.getId(), 0, 0));
                }
                else {
                    pc.sendPackets(new S_SkillHaste(pc.getId(), 1, time));
                    pc.broadcastPacketX10(new S_SkillHaste(pc.getId(), 1, 0));
                    pc.setMoveSpeed(1);
                    pc.setSkillEffect(1001, time * 1000);
                }
                new L1SkillUse().handleCommands(pc, 42, pc.getId(), pc.getX(), pc.getY(), time, 4);
                new L1SkillUse().handleCommands(pc, 26, pc.getId(), pc.getX(), pc.getY(), time, 4);
                new L1SkillUse().handleCommands(pc, 79, pc.getId(), pc.getX(), pc.getY(), time, 4);
                pc.setCurrentHp(pc.getMaxHp());
                pc.setCurrentMp(pc.getMaxMp());
            }
        }
        World.get().broadcastServerMessage("大天使祝福降臨!所有玩家獲得狀態1小時!");
    }
    
    private void saveChatData(boolean bool) {
        SimpleDateFormat sdfmt = new SimpleDateFormat("yyyy-MM-dd");
        Date d = Calendar.getInstance().getTime();
        String date = " " + sdfmt.format(d);
        try {
            FileOutputStream fos = new FileOutputStream("chatLog/Consol" + date + ".txt");
            fos.write(this.TA_Consol.getText().getBytes());
            fos.close();
            fos = new FileOutputStream("chatLog/AllChat" + date + ".txt");
            fos.write(this.TA_AllChat.getText().getBytes());
            fos.close();
            fos = new FileOutputStream("chatLog/World" + date + ".txt");
            fos.write(this.TA_World.getText().getBytes());
            fos.close();
            fos = new FileOutputStream("chatLog/Clan" + date + ".txt");
            fos.write(this.TA_Clan.getText().getBytes());
            fos.close();
            fos = new FileOutputStream("chatLog/Normal" + date + ".txt");
            fos.write(this.TA_Normal.getText().getBytes());
            fos.close();
            fos = new FileOutputStream("chatLog/Team" + date + ".txt");
            fos.write(this.TA_Team.getText().getBytes());
            fos.close();
            fos = new FileOutputStream("chatLog/Whisper" + date + ".txt");
            fos.write(this.TA_Private.getText().getBytes());
            fos.close();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    
    private void processEvent(MouseEvent e) {
        if ((e.getModifiers() & 0x4) != 0x0) {
            int modifiers = e.getModifiers();
            modifiers -= 4;
            modifiers |= 0x10;
            MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), modifiers, e.getX(), e.getY(), e.getClickCount(), false);
            this.T_Player.dispatchEvent(ne);
        }
    }
}

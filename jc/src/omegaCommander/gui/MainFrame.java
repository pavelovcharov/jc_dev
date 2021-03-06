/*
 * This file is part of jc, http://www.jcommander.narod.ru
 * Copyright (C) 2005-2010 Pavel Ovcharov
 *
 * jc is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * MainFrame.java
 *
 * Created on 26 ������ 2005 �., 9:27
 */
package omegaCommander.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import omegaCommander.actions.*;
import omegaCommander.actions.manager.ActionManager;
import omegaCommander.fileSystem.BaseFile;
import omegaCommander.fileSystem.RootFileSystem;
import omegaCommander.fileSystem.FileSystemList;
import omegaCommander.fileSystem.FileHelper;
import omegaCommander.gui.listeners.*;
import omegaCommander.fileSystem.LocalFile;
import omegaCommander.gui.dialog.InputDialog;
import omegaCommander.gui.dialog.WarningDialog;
import omegaCommander.gui.listeners.combobox.BaseComboBoxListener;
import omegaCommander.gui.message.KeyShortcat;
import omegaCommander.gui.message.Message;
import omegaCommander.gui.message.MessageList;
import omegaCommander.gui.search.SearchDialog;
import omegaCommander.gui.table.Directive;
import omegaCommander.gui.table.FileTable;
import omegaCommander.gui.table.FileTablePanel;
import omegaCommander.gui.table.dragdrop.FileTransferHandler;
import omegaCommander.gui.table.tableHeader.TableHeader;
import omegaCommander.prefs.JCPreferenses;
import omegaCommander.threads.newThreads.NewSearchThread;
import omegaCommander.util.LanguageBundle;
import omegaCommander.util.LocaleWrapper;

/**
 * ����� ���������� �� JFrame � ��������� �������� ���� 
 * ����������
 * @author Pavel Ovcharov
 * @version 2005/04/26 9:27
 */
public class MainFrame extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc=" MainFrame Actions ">
    private ActionManager operationManager = new ActionManager(this);
    public final omegaCommander.actions.Action ACTION_COPY = new ActionCopy(this);
    public final omegaCommander.actions.Action ACTION_TAB = new ActionTab(this);
    public final omegaCommander.actions.Action ACTION_VIEW = new ActionView(this);
    public final omegaCommander.actions.Action ACTION_EDIT = new ActionEdit(this);
    public final omegaCommander.actions.Action ACTION_NEW_FILE = new ActionNewFile(this);
    public final omegaCommander.actions.Action ACTION_NEW_DIR = new ActionNewDir(this);
    public final omegaCommander.actions.Action ACTION_PARENT_DIR = new ActionParentDir(this);
    public final omegaCommander.actions.Action ACTION_MOVE = new ActionMove(this);
    public final omegaCommander.actions.Action ACTION_SELECT_ALL = new ActionSelectAll(this);
    public final omegaCommander.actions.Action ACTION_SYNC = new ActionSync(this);
    public final omegaCommander.actions.Action ACTION_RENAME = new ActionRename(this);
    public final omegaCommander.actions.Action ACTION_SHOW_LEFT_COMBO = new ActionShowComboBox(this, true);
    public final omegaCommander.actions.Action ACTION_SHOW_RIGHT_COMBO = new ActionShowComboBox(this, false);
    public final omegaCommander.actions.Action ACTION_SELECT_N_DOWN = new ActionSelectNDown(this);
    public final omegaCommander.actions.Action ACTION_SELECT_N_UP = new ActionSelectNUp(this);
    public final omegaCommander.actions.Action ACTION_PACK = new ActionPack(this);
    public final omegaCommander.actions.Action ACTION_DELETE = new ActionDelete(this);
    public final omegaCommander.actions.Action ACTION_REFRESH = new ActionRefresh(this);
    public final omegaCommander.actions.Action ACTION_ACTIVATE_LEFT_TABLE = new ActionActivateTable(this, true);
    public final omegaCommander.actions.Action ACTION_ACTIVATE_RIGHT_TABLE = new ActionActivateTable(this, false);
    public final omegaCommander.actions.Action ACTION_UP = new ActionUp(this);
    public final omegaCommander.actions.Action ACTION_DOWN = new ActionDown(this);
    public final omegaCommander.actions.Action ACTION_JUMP_UP = new ActionJump(this, true);
    public final omegaCommander.actions.Action ACTION_JUMP_DOWN = new ActionJump(this, false);
    public final omegaCommander.actions.Action ACTION_FIRST = new ActionFirst(this);
    public final omegaCommander.actions.Action ACTION_LAST = new ActionLast(this);
    public final omegaCommander.actions.Action ACTION_EXIT = new ActionExit(this);
    public final omegaCommander.actions.Action ACTION_FIND = new ActionFind(this);
    public final omegaCommander.actions.Action ACTION_SHOW_PANELS = new ActionShowPanels(this);
    public final omegaCommander.actions.Action ACTION_QUICK_SEARCH = new ActionQuickSearch(this);
    public final omegaCommander.actions.Action ACTION_CALC_SIZE = new ActionCalcSize(this);
    public final omegaCommander.actions.Action ACTION_ADD_TAB = new ActionAddTab(this);
    public final omegaCommander.actions.Action ACTION_REMOVE_TAB = new ActionRemoveTab(this);
    public final omegaCommander.actions.Action ACTION_NEXT_TAB = new ActionNextTab(this);
    public final omegaCommander.actions.Action ACTION_PREV_TAB = new ActionPrevTab(this);
    public final omegaCommander.actions.Action ACTION_COPY_NAME = new ActionCopyName(this);
    public final omegaCommander.actions.Action ACTION_COPY_PATH = new ActionCopyPath(this);
    public final omegaCommander.actions.Action ACTION_DECODE_HEX = new ActionDecodeHex(this);
    public final omegaCommander.actions.Action ACTION_GOTO_CMDLINE = new ActionGotoCmdLine(this);
    public final omegaCommander.actions.Action ACTION_ENTER = new ActionEnter(this);
    public final omegaCommander.actions.Action ACTION_SWAP = new ActionSwap(this);
    public final omegaCommander.actions.Action ACTION_CHANGE_ARRANGEMENT = new ActionChangeArrangement(this);
    public final omegaCommander.actions.Action ACTION_COPY_SAME_FOLDER = new ActionCopySameFolder(this);
    public final omegaCommander.actions.Action ACTION_EXPLORER = new ActionExplorer(this);
    public final omegaCommander.actions.Action ACTION_MOVE_TO_TRASH = new ActionMoveToTrash((this));
    // </editor-fold>

    /**
     * ������� ����� ������ ������ MainFrame
     * @param version ������ ����������
     * @param title ��������� ����
     */
    public MainFrame(String title, String version) {
        initComponents();

        loadPrefs();

        this.mainTitle = title;
        this.mainVersion = version;

        this.setTitle(mainTitle + " " + mainVersion);

        BaseFile root = RootFileSystem.getReadableRoot();
        if (null == root) {
            System.out.println("Can't find any root. Application halted...");
            System.exit(0xa);
        }
        System.out.println("File system root was found. Starting application...");

        initDriveComboBox(jComboBoxLeft, RootFileSystem.getRoots());
        initDriveComboBox(jComboBoxRight, RootFileSystem.getRoots());
        /***********************************/
        currentLeftTable = new FileTable(root);
        currentRightTable = new FileTable(root);

        currentLeftTable.addKeyListener(new TableKeyListener(this));
        currentRightTable.addKeyListener(new TableKeyListener(this));

        currentLeftTable.addFocusListener(new TableFocusListener(this));
        currentRightTable.addFocusListener(new TableFocusListener(this));

        leftFilePanel = new FileTablePanel(currentLeftTable);
        rightFilePanel = new FileTablePanel(currentRightTable);
        jPanelCenterLeft.add(leftFilePanel, "card3");
        jPanelCenterRight.add(rightFilePanel, "card3");

        leftFilePanel.setVisible(true);
        rightFilePanel.setVisible(true);
        jTabbedPaneLeft.setVisible(false);
        jTabbedPaneRight.setVisible(false);

        this.addFocusListener(new MainFrameFocusListener());

        currentLeftTable.addMouseInputAdapter(new TableMouseListener(this));
        currentRightTable.addMouseInputAdapter(new TableMouseListener(this));

        setIconImage(ImageArchive.getImageLogo().getImage());

        jScrollPane3.setVisible(false);

        setupComponents();

        setAboutDialog();
        setDeleteFavoritesDialog();
        setPreferencesDialog();

        jComboBoxLeft.addKeyListener(new BaseComboBoxListener(jComboBoxLeft, defaultKeyList, userKeyList));
        jComboBoxRight.addKeyListener(new BaseComboBoxListener(jComboBoxRight, defaultKeyList, userKeyList));

        jComboBoxLeft.setRenderer(new ComboCellRenderer());
        jComboBoxRight.setRenderer(new ComboCellRenderer());

        jTextFieldSearchLeft.addKeyListener(new SearchKeyListener(this));
        jTextFieldSearchRight.addKeyListener(new SearchKeyListener(this));

        loadFavorites();

        jPanelToolBar.setVisible(false);

        applyPrefs();
        updateMainWindow();

        leftFilePanel.setTransferHandler(new FileTransferHandler(this));
        currentLeftTable.setTransferHandler(new FileTransferHandler(this));
        rightFilePanel.setTransferHandler(new FileTransferHandler(this));
        currentRightTable.setTransferHandler(new FileTransferHandler(this));
    }

    public void setupComponents() {

        lb.generateBundle();
        try {
            currentLocaleWrapper = LocaleWrapper.getLocaleWrapper(jcPrefs.currentLocale);

            jButtonView.setText(lb.getString("ButtonView"));
            jButtonEdit.setText(lb.getString("ButtonEdit"));
            jButtonCopy.setText(lb.getString("ButtonCopy"));
            jButtonMove.setText(lb.getString("ButtonMove"));
            jButtonCreateDir.setText(lb.getString("ButtonNewDir"));
            jButtonCreateDir.setToolTipText(lb.getString("ButtonNewDir"));
            jButtonDel.setText(lb.getString("ButtonDelete"));
            jButtonPack.setText(lb.getString("ButtonPack"));
            jButtonExit.setText(lb.getString("Exit"));
            jMenuFile.setText(lb.getString("File"));
            jMenuItemExit.setText(lb.getString("Exit"));
            jMenuHelp.setText(lb.getString("Help"));
            jMenuItemHotKeys.setText(lb.getString("HotKeys"));
            jMenuItemAbout.setText(lb.getString("About"));
            jMenuItemPreferences.setText(lb.getString("Preferences"));
            jMenuItemFind.setText(lb.getString("Find"));
            jMenuItemAddTab.setText(lb.getString("AddTab"));
            jMenuItemRemoveTab.setText(lb.getString("RemoveTab"));
            jMenuEdit.setText(lb.getString("Tools"));
            jMenuFavorites.setText(lb.getString("Bookmarks"));
            jDialogHotKeys.setTitle(lb.getString("HotKeys"));
            jDialogPreferences.setTitle(lb.getString("Preferences"));

            jButtonDeleteFavorite.setText(lb.getString("StrDelete"));
            jButtonHideDeleteFavoriteDialog.setText(lb.getString("StrClose"));

            jButtonPrefApply.setText(lb.getString("StrApply"));
            jButtonPrefCancel.setText(lb.getString("StrCancel"));
            jLabelPrefCharset.setText(lb.getString("StrCharset"));
            ((TitledBorder) (jPanelPrefConsole.getBorder())).setTitle(lb.getString("StrConsole"));
            jLabelPrefLang.setText(lb.getString("StrLang"));
            jTabbedPanePrefs.setTitleAt(0, lb.getString("StrGeneral"));
            jTabbedPanePrefs.setTitleAt(1, lb.getString("StrAppearance"));
            jTabbedPanePrefs.setTitleAt(2, lb.getString("HotKeys"));
            searchDialog.setupComponents(lb);
            jCheckBoxMenuItemButtonBar.setText(lb.getString("StrShowButtons"));
            jCheckBoxMenuItemCommandLine.setText(lb.getString("StrShowCommandLine"));
            jCheckBoxMenuItemHiddenFiles.setText(lb.getString("StrShowHiddenFiles"));
            jCheckBoxMenuItemArrangement.setText(lb.getString("StrArrangement"));
            jMenuView.setText(lb.getString("StrView"));

            ((TitledBorder) (jPanelEditor.getBorder())).setTitle(lb.getString("StrExternEditor"));
            jLabelEditor.setText(lb.getString("StrEditor"));
            jCheckBoxEditor.setText(lb.getString("StrUseEditor"));
            jButtonEditor.setText(lb.getString("StrBrowse"));

            jMenuItemDecodeHex.setText(lb.getString("KeyDecodeHex"));

            jTextAreaHotKeysHelp.setText(lb.getString("StrHotKeysHelp"));
            
            jDialogAbout.setTitle(mainTitle);
            jDialogDeleteFavorites.setTitle(lb.getString("StrReady"));

            ((TitledBorder) (jPanelPrefView.getBorder())).setTitle(lb.getString("StrPrefView"));
            ((TitledBorder) (jPanelPrefTheme.getBorder())).setTitle(lb.getString("StrPrefTheme"));
            jLabelTheme.setText(lb.getString("StrTheme"));
            jCheckBoxPrefShowButtons.setText(lb.getString("StrShowButtons"));
            jCheckBoxPrefShowCmdline.setText(lb.getString("StrShowCommandLine"));
            jCheckBoxPrefShowHidden.setText(lb.getString("StrShowHiddenFiles"));
            jCheckBoxPrefShowTooltips.setText(lb.getString("StrShowTooltips"));
            jCheckBoxPrefsUseSystemIcons.setText(lb.getString("StrUseSystemIcons"));

            initMessageList();

            jTableHotKeys = createHotKeysTable();
            jTableHotKeys.addKeyListener(new HKTableListener(this));
            jScrollPanePrefHK.setViewportView(jTableHotKeys);

            setHotKeysDialog();
            initFavorites();

            updateStatusLabel(false);
            updateStatusLabel(true);

        } catch (Exception e) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateUI(String lnf) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        try {
            UIManager.setLookAndFeel(lnf);
        } catch (Exception e) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
        }
        SwingUtilities.updateComponentTreeUI(this);
        SwingUtilities.updateComponentTreeUI(jDialogAbout);
        SwingUtilities.updateComponentTreeUI(jDialogDeleteFavorites);
        SwingUtilities.updateComponentTreeUI(searchDialog);
        SwingUtilities.updateComponentTreeUI(jDialogHotKeys);
        SwingUtilities.updateComponentTreeUI(jDialogPreferences);
        SwingUtilities.updateComponentTreeUI(jPopupMenuFavorites);
        jMenuBar2.updateUI();

        leftFilePanel.setInputMap();
        rightFilePanel.setInputMap();


        InputMap imP = jSplitPane1.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).getParent();
        imP.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
    }

    private double getDividerLocation() {
        return jSplitPane1.getOrientation() == JSplitPane.HORIZONTAL_SPLIT
                ? ((double) jSplitPane1.getDividerLocation()) / jPanelCenter.getWidth()
                : ((double) jSplitPane1.getDividerLocation()) / jPanelCenter.getHeight();
    }

    private void hideSplitterMenu() {
        if (splitterPopupMenu != null) {
            splitterPopupMenu.setVisible(false);
        }
    }

    public void moveToFile(BaseFile af) {
        if (af == null) {
            return;
        }
        FileTable table = getActiveTable();
        table.setCurrentDir(af);
        updateActivePanel();
        table.setCurrentPosition(table.getFilePosition(af));
    }

    private void loadPrefs() {
        jcPrefs.loadPrefs();
    }

    private void saveTablePrefs(boolean left) {
        String paths = "";
        String headerSizes = "";
        String sortColumns = "";
        FileTable[] tables = getFileTables(left);
        for (int i = 0; i < tables.length; i++) {
            String path = tables[i].getCurrentDir().getAbsolutePath();
            int[] sizes = tables[i].getHeaderSizes();
            ArrayList sortingColumns = tables[i].getSortingColumns();

            paths += path + JCPreferenses.PRIMARY_DELIMITER;

            for (int j = 0; j < sizes.length - 1; j++) {
                headerSizes += "" + sizes[j] + JCPreferenses.SECONDARY_DELIMITER;
            }
            headerSizes += "" + sizes[sizes.length - 1] + JCPreferenses.PRIMARY_DELIMITER;

            for (int j = 0; j < sortingColumns.size(); j++) {
                Directive d = ((Directive) sortingColumns.get(j));
                sortColumns += "" + d.getColumn() + JCPreferenses.SECONDARY_DELIMITER + d.getDirection();
            }
            sortColumns += JCPreferenses.PRIMARY_DELIMITER;
        }
        if (left) {
            jcPrefs.leftPanelPath = paths;
            jcPrefs.leftHeaderSizes = headerSizes;
            jcPrefs.leftSortingColumns = sortColumns;
        } else {
            jcPrefs.rightPanelPath = paths;
            jcPrefs.rightHeaderSizes = headerSizes;
            jcPrefs.rightSortingColumns = sortColumns;
        }
    }

    private void savePrefs() {
        jcPrefs.dividerLocation = jSplitPane1.getDividerLocation();
        jcPrefs.extendedState = getExtendedState();
        jcPrefs.activeTable = currentLeftTable.isActive() ? 0 : 1;
        jcPrefs.currentLocale = currentLocaleWrapper.getLocale().toString();
        jcPrefs.consoleCharset = consoleCharset.displayName();
        jcPrefs.showButtons = jPanel7.isVisible();
        jcPrefs.showCommandLine = jTextField3.isVisible();
        jcPrefs.showHiddenFiles = jCheckBoxMenuItemHiddenFiles.isSelected();

        saveTablePrefs(true);
        saveTablePrefs(false);

        jcPrefs.savePrefs();
    }

    FileTable[] getFileTables(boolean left) {
        JTabbedPane jTabbedPane = getTabbedPane(left);
        if (jTabbedPane.getTabCount() < 2) {
            return new FileTable[]{getTable(left)};
        }
        FileTable[] tables = new FileTable[jTabbedPane.getTabCount()];
        for (int i = 0; i < jTabbedPane.getTabCount(); i++) {
            tables[i] = ((FileTablePanel) jTabbedPane.getComponentAt(i)).getFileTable();
        }
        return tables;
    }

    public void applyPrefs() {

        setSize(jcPrefs.windowSize);
        setLocation(jcPrefs.location);
        setExtendedState(jcPrefs.extendedState);
        jSplitPane1.setDividerLocation(jcPrefs.dividerLocation);

        String[] leftPaths = ParseHelper.parsePath(jcPrefs.leftPanelPath, JCPreferenses.DEFAULT_LEFT_PANEL_PATH);
        String[] rightPaths = ParseHelper.parsePath(jcPrefs.rightPanelPath, JCPreferenses.DEFAULT_RIGHT_PANEL_PATH);

        Directive[] leftDirective = ParseHelper.parseSortingColumns(jcPrefs.leftSortingColumns, JCPreferenses.DEFAULT_DIRECTIVE, leftPaths.length);
        Directive[] rightDirective = ParseHelper.parseSortingColumns(jcPrefs.rightSortingColumns, JCPreferenses.DEFAULT_DIRECTIVE, rightPaths.length);

        ArrayList<int[]> leftSizes = ParseHelper.parseHeaderSize(jcPrefs.leftHeaderSizes, JCPreferenses.DEFAULT_HEADER_SIZE, leftPaths.length);
        ArrayList<int[]> rightSizes = ParseHelper.parseHeaderSize(jcPrefs.rightHeaderSizes, JCPreferenses.DEFAULT_HEADER_SIZE, rightPaths.length);

        if (leftPaths.length == 1) {
            BaseFile file = FileHelper.getRealFile(leftPaths[0]);
            currentLeftTable.setCurrentDir(file);
            currentLeftTable.setHeaderSizes(leftSizes.get(0));
            currentLeftTable.setSortingColumns(leftDirective[0]);
        } else {
            for (int i = 0; i < leftPaths.length; i++) {
                BaseFile file = FileHelper.getRealFile(leftPaths[i]);
                ArrayList list = new ArrayList();
                list.add(leftDirective[i]);
                addTab(true, file, list, leftSizes.get(i));
            }
        }
        if (rightPaths.length == 1) {
            BaseFile file = FileHelper.getRealFile(rightPaths[0]);
            currentRightTable.setCurrentDir(file);
            currentRightTable.setHeaderSizes(leftSizes.get(0));
            currentRightTable.setSortingColumns(rightDirective[0]);
        } else {
            for (int i = 0; i < rightPaths.length; i++) {
                BaseFile file = FileHelper.getRealFile(rightPaths[i]);
                ArrayList list = new ArrayList();
                list.add(rightDirective[i]);
                addTab(false, file, list, rightSizes.get(i));
            }
        }

        if (0 == jcPrefs.activeTable) {
            setActiveTable(true);
            currentLeftTable.requestFocus();
        } else {
            setActiveTable(false);
            currentRightTable.requestFocus();
        }

        if (jcPrefs.consoleCharset.equals("")) {
            consoleCharset = Charset.defaultCharset();
        } else {
            try {
                consoleCharset = Charset.forName(jcPrefs.consoleCharset);
            } catch (UnsupportedCharsetException ex) {
                consoleCharset = Charset.defaultCharset();
            }
        }
        jCheckBoxMenuItemButtonBar.setSelected(jcPrefs.showButtons);
        jPanel7.setVisible(jcPrefs.showButtons);
        jCheckBoxMenuItemCommandLine.setSelected(jcPrefs.showCommandLine);
        jTextField3.setVisible(jcPrefs.showCommandLine);
        jCheckBoxMenuItemHiddenFiles.setSelected(jcPrefs.showHiddenFiles);
        
        currentLeftTable.setQuickSearchMode(jcPrefs.quickSearchMode);
        currentLeftTable.setHiddenFilesVisibility(jcPrefs.showHiddenFiles);
        currentLeftTable.showToolTip(jcPrefs.showToolTips);
        currentLeftTable.refreshTable();
        
        currentRightTable.setQuickSearchMode(jcPrefs.quickSearchMode);
        currentRightTable.setHiddenFilesVisibility(jcPrefs.showHiddenFiles);
        currentRightTable.showToolTip(jcPrefs.showToolTips);
        currentRightTable.refreshTable();
        
        if (jcPrefs.arrangement) {
            changeArrangement();
        }
        jCheckBoxMenuItemArrangement.setSelected(jcPrefs.arrangement);

        jDialogPreferencesReset();

        updateUI(jcPrefs.theme);
    }

    public void applyButtonPressed() {

        consoleCharset = Charset.forName(jComboBoxCharset.getSelectedItem().toString());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile));
            Message msg = null;
            KeyShortcat ks = null;
            userKeyList.clear();
            for (int i = 0; i < jTableHotKeys.getRowCount(); i++) {
                ks = (KeyShortcat) jTableHotKeys.getValueAt(i, 2);
                if (null == ks) {
                    continue;
                }
                msg = (Message) defaultKeyList.get(jTableHotKeys.getValueAt(i, 1));
                userKeyList.put(ks, msg);
            }
            oos.writeInt(userKeyList.size());
            Object[] keys = userKeyList.keySet().toArray();
            for (int i = 0; i < userKeyList.size(); i++) {
                ks = (KeyShortcat) keys[i];
                msg = (Message) userKeyList.get(keys[i]);
                oos.writeUTF(msg.getMessageText());
                oos.writeInt(ks.getKeyCode());
                oos.writeBoolean(ks.isAltDown());
                oos.writeBoolean(ks.isCtrlDown());
                oos.writeBoolean(ks.isShiftDown());

            }
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        initMessageList();

        jcPrefs.useExternEditor = jCheckBoxEditor.isSelected();
        jcPrefs.externEditor = jTextFieldEditor.getText();
        jcPrefs.showButtons = jCheckBoxPrefShowButtons.isSelected();
        jcPrefs.showCommandLine = jCheckBoxPrefShowCmdline.isSelected();
        jcPrefs.showHiddenFiles = jCheckBoxPrefShowHidden.isSelected();
        jcPrefs.showToolTips = jCheckBoxPrefShowTooltips.isSelected();
        jcPrefs.useSystemIcons = jCheckBoxPrefsUseSystemIcons.isSelected();

        LocaleWrapper l = ((LocaleWrapper) jComboBoxLang.getSelectedItem());
        if (false == l.equals(currentLocaleWrapper)) {
            currentLocaleWrapper = l;
            savePrefs();
            setupComponents();
        }
        setHotKeysDialog();

        jcPrefs.theme = UIManager.getInstalledLookAndFeels()[jComboBoxTheme.getSelectedIndex()].getClassName();
        updateUI(jcPrefs.theme);

        currentLeftTable.showToolTip(jcPrefs.showToolTips);
        currentRightTable.showToolTip(jcPrefs.showToolTips);

        showButtonsBar(jcPrefs.showButtons);
        showCmdLine(jcPrefs.showCommandLine);
        showHiddenFiles(jcPrefs.showHiddenFiles);
    }

    public void initMessageList() {
        defaultKeyList = new LinkedHashMap();
        userKeyList = new LinkedHashMap();
        MessageList messageList = MessageList.ensureMessageList();

        messageList.MESSAGE_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_TAB));
        messageList.MESSAGE_ACTIVE_LEFT.setDefaultKey(new KeyShortcat(KeyEvent.VK_LEFT, true, false, false));
        messageList.MESSAGE_ACTIVE_RIGHT.setDefaultKey(new KeyShortcat(KeyEvent.VK_RIGHT, true, false, false));
        messageList.MESSAGE_ENTER.setDefaultKey(new KeyShortcat(KeyEvent.VK_ENTER));
        messageList.MESSAGE_DIR_UP.setDefaultKey(new KeyShortcat(KeyEvent.VK_BACK_SPACE));
        messageList.MESSAGE_VIEW.setDefaultKey(new KeyShortcat(KeyEvent.VK_F3));
        messageList.MESSAGE_EDIT.setDefaultKey(new KeyShortcat(KeyEvent.VK_F4));
        messageList.MESSAGE_NEW_EDIT.setDefaultKey(new KeyShortcat(KeyEvent.VK_F4, false, false, true));
        messageList.MESSAGE_NEW_DIR.setDefaultKey(new KeyShortcat(KeyEvent.VK_F7));
        messageList.MESSAGE_COPY.setDefaultKey(new KeyShortcat(KeyEvent.VK_F5));
        messageList.MESSAGE_COPY_SAME_FOLDER.setDefaultKey(new KeyShortcat(KeyEvent.VK_F5, false, false, true));
        messageList.MESSAGE_MOVE.setDefaultKey(new KeyShortcat(KeyEvent.VK_F6));
        messageList.MESSAGE_RENAME.setDefaultKey(new KeyShortcat(KeyEvent.VK_F2));
        messageList.MESSAGE_MOVE_TO_TRASH.setDefaultKey(new KeyShortcat(KeyEvent.VK_F8));
        messageList.MESSAGE_DELETE.setDefaultKey(new KeyShortcat(KeyEvent.VK_F8, false, false, true));
        messageList.MESSAGE_PACK.setDefaultKey(new KeyShortcat(KeyEvent.VK_F9));
        messageList.MESSAGE_EXIT.setDefaultKey(new KeyShortcat(KeyEvent.VK_F4, true, false, false));
        messageList.MESSAGE_SHOW_LEFT_COMBOBOX.setDefaultKey(new KeyShortcat(KeyEvent.VK_F1, true, false, false));
        messageList.MESSAGE_SHOW_RIGHT_COMBOBOX.setDefaultKey(new KeyShortcat(KeyEvent.VK_F2, true, false, false));
        messageList.MESSAGE_REFRESH.setDefaultKey(new KeyShortcat(KeyEvent.VK_R, false, true, false));
        messageList.MESSAGE_SYNC.setDefaultKey(new KeyShortcat(KeyEvent.VK_O, true, false, false));
        messageList.MESSAGE_SWAP.setDefaultKey(new KeyShortcat(KeyEvent.VK_U, false, true, false));
        messageList.MESSAGE_SELECT_ALL.setDefaultKey(new KeyShortcat(KeyEvent.VK_MULTIPLY));
        messageList.MESSAGE_SELECT_N_DOWN.setDefaultKey(new KeyShortcat(KeyEvent.VK_INSERT));
        messageList.MESSAGE_SELECT_N_UP.setDefaultKey(new KeyShortcat(KeyEvent.VK_UP, false, false, true));
        messageList.MESSAGE_UP.setDefaultKey(new KeyShortcat(KeyEvent.VK_UP));
        messageList.MESSAGE_DOWN.setDefaultKey(new KeyShortcat(KeyEvent.VK_DOWN));
        messageList.MESSAGE_PG_UP.setDefaultKey(new KeyShortcat(KeyEvent.VK_PAGE_UP));
        messageList.MESSAGE_PG_DOWN.setDefaultKey(new KeyShortcat(KeyEvent.VK_PAGE_DOWN));
        messageList.MESSAGE_FIRST.setDefaultKey(new KeyShortcat(KeyEvent.VK_HOME));
        messageList.MESSAGE_LAST.setDefaultKey(new KeyShortcat(KeyEvent.VK_END));
        messageList.MESSAGE_QUICK_SEARCH.setDefaultKey(new KeyShortcat(KeyEvent.VK_S, false, true, false));
        messageList.MESSAGE_FIND.setDefaultKey(new KeyShortcat(KeyEvent.VK_F, false, true, false));
        messageList.MESSAGE_SHOW_PANELS.setDefaultKey(new KeyShortcat(KeyEvent.VK_O, false, true, false));
        messageList.MESSAGE_SPACE.setDefaultKey(new KeyShortcat(KeyEvent.VK_SPACE));
        messageList.MESSAGE_ADD_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_T, false, true, false));
        messageList.MESSAGE_REMOVE_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_D, false, true, false));
        messageList.MESSAGE_NEXT_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_N, false, true, false));
        messageList.MESSAGE_PREV_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_B, false, true, false));
        messageList.MESSAGE_COPY_PATH.setDefaultKey(new KeyShortcat(KeyEvent.VK_ENTER, false, true, true));
        messageList.MESSAGE_COPY_NAME.setDefaultKey(new KeyShortcat(KeyEvent.VK_ENTER, true, false, false));
        messageList.MESSAGE_GOTO_CMDLINE.setDefaultKey(new KeyShortcat(KeyEvent.VK_G, false, true, false));
        messageList.MESSAGE_DECODE_HEX.setDefaultKey(new KeyShortcat(KeyEvent.VK_H, false, true, false));


        for (Message message : messageList.getMessages()) {
            defaultKeyList.put(message.getDefaultKey(), message);
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile));
            int count = ois.readInt();
            for (int i = 0; i < count; i++) {
                String s = ois.readUTF();
                int code = ois.readInt();
                boolean alt = ois.readBoolean();
                boolean ctrl = ois.readBoolean();
                boolean shift = ois.readBoolean();


                Message message = messageList.getMessage(s);
                KeyShortcat userKey = new KeyShortcat(code, alt, ctrl, shift);
                message.setUserKey(userKey);
                userKeyList.put(userKey, message);
            }
            ois.close();

        } catch (IOException ioe) {
        }
    }

    public LinkedHashMap getDefaultKeyList() {
        return defaultKeyList;
    }

    public LinkedHashMap getUserKeyList() {
        return userKeyList;
    }

    /**
     * �������� ������� ���� ����������
     */
    public void updateMainWindow() {
        updatePanel(true);
        updatePanel(false);
    }

    public JDialog getPreferencesDialog() {
        return jDialogPreferences;
    }

    public JDialog getFindDialog() {
        return searchDialog;
    }

    /**
     * Updates left/right panel
     * @param left <B>true</B>Update left panel, <B>false</B> - update right panel
     */
    public void updatePanel(boolean left) {
        FileTable currentTable;

        currentTable = getTable(left);
        currentTable.refreshTable();
        getDirLabel(left).setText(currentTable.getCurrentDir().getPathWithSlash());
        getStatusLabel(left).setText(lb.getString("StrFiles") + ": " + currentTable.getFileList().getFilesCount() + ", " + lb.getString("StrDirs") + ": " + currentTable.getFileList().getDirCount());
        getComboBox(left).setSelectedItem(currentTable.getCurrentDir().getRoot());
        JTabbedPane jTabbedPane = getTabbedPane(left);
        if (2 <= jTabbedPane.getTabCount()) {
            jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(), currentTable.getCurrentDir().getAbstractFileName());
        }
    }

    /**
     * �������� �������� ������ ����������
     */
    public void updateActivePanel() {
        if (currentLeftTable.isActive()) {
            updatePanel(true);
        } else {
            updatePanel(false);
        }
    }

    public void updatePassivePanel() {
        if (currentLeftTable.isActive()) {
            updatePanel(false);
        } else {
            updatePanel(true);
        }
    }

    /**
     * � ����������� �� ����������� ��������� ���������� ����� ���
     * ������ �������
     * @param left <b>true</b> ��� ��������� ����� �������, <b>false</b> -
     * ��� ��������� ������
     * @return ������ ������ FileTable
     */
    public FileTable getTable(boolean left) {
        return left ? currentLeftTable : currentRightTable;
    }

    /**
     * � ����������� �� ����������� ��������� ���������� ����� ���
     * ������ ����� �������
     * @param left <b>true</b> ��� ��������� ����� �����, <b>false</b> -
     * ��� ��������� ������
     *
     * @return ������ ������ JLabel
     */
    public JLabel getStatusLabel(boolean left) {
        return left ? jLabelStatusLeft : jLabelStatusRight;
    }

    /**
     * � ����������� �� ����������� ��������� ���������� ����� ���
     * ������ ����� ����
     * @param left <b>true</b> ��� ��������� ����� �����, <b>false</b> -
     * ��� ��������� ������
     * @return ������ ������ JTextField
     */
    public JTextField getDirLabel(boolean left) {
        return left ? jTextField1 : jTextField2;
    }

    /**
     * ������� � cb ������ �������� File roots[]
     * @param cb ���������� ������, ���� ��������� �������
     * @param roots ������ ��������� ��������
     */
    protected void initDriveComboBox(final JComboBox cb, BaseFile[] roots) {
        setDriveComboBox(cb, roots);

        cb.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).setParent(null);
        cb.setKeySelectionManager(new JComboBox.KeySelectionManager() {

            @Override
            public int selectionForKey(char aKey, ComboBoxModel aModel) {
                return cb.getSelectedIndex();
            }
        });
    }

    /**
     * � ����������� �� ����������� ��������� ���������� ����� ���
     * ������ ������ ������
     * @param left <b>true</b> ��� ��������� ������ ������, <b>false</b> -
     * ��� ��������� �������
     * @return ������ ������ JComboBox
     */
    public JComboBox getComboBox(boolean left) {
        return left ? jComboBoxLeft : jComboBoxRight;
    }

    /**
     * �������� �������� �������
     * @return ������ ������ FileTable
     */
    public FileTable getActiveTable() {
        return currentRightTable.isActive() ? currentRightTable : currentLeftTable;
    }

    public FileTable getPassiveTable() {
        return currentRightTable.isActive() ? currentLeftTable : currentRightTable;
    }

    private void setAboutDialog() {
        jDialogAbout.setSize(300, 200);
        jLabelCurrentVersion.setText(mainTitle + " " + mainVersion);
        jLabelCopyright.setText(JCOM_COPYRIGHT);
        jLabelMail.setText(JCOM_MAIL);
        jLabelPage.setText(JCOM_PAGE);
    }

    private void setDriveComboBox(JComboBox comboBox, BaseFile[] roots) {
        comboBox.removeAllItems();
        for (int i = 0; i < roots.length; i++) {
            comboBox.addItem(roots[i]);
        }
        comboBox.setMaximumRowCount(roots.length);
    }

    private void setHotKeysDialog() {
        jDialogHotKeys.setSize(500, 400);
        JTable tb = createHotKeysTable();
        tb.setShowHorizontalLines(false);
        jScrollPaneHelpHK.setViewportView(tb);
    }

    private void setPreferencesDialog() {
        jDialogPreferences.setSize(450, 410);
        java.util.SortedMap sm = java.nio.charset.Charset.availableCharsets();
        for (Iterator it = sm.values().iterator(); it.hasNext();) {
            jComboBoxCharset.addItem(((java.nio.charset.Charset) it.next()).displayName());
        }
        jTableHotKeys = createHotKeysTable();
        jTableHotKeys.addKeyListener(new HKTableListener(this));
        jScrollPanePrefHK.setViewportView(jTableHotKeys);

        Collection<LocaleWrapper> locales = LocaleWrapper.getLocales();
        for (LocaleWrapper localeWrapper : locales) {
            jComboBoxLang.addItem(localeWrapper);
        }

        LookAndFeelInfo[] l = UIManager.getInstalledLookAndFeels();
        for (int i = 0; i < l.length; i++) {
            jComboBoxTheme.addItem(l[i].getName());
            if (l[i].getClassName().equals(jcPrefs.theme)) {
                jComboBoxTheme.setSelectedIndex(i);
            }
        }
    }

    private void setDeleteFavoritesDialog() {
        jDialogDeleteFavorites.setSize(300, 200);
    }

    private void showDeleteFavoritesDialog() {
        jListFavorites.setListData(favoriteFolders.toArray());
        jListFavorites.setSelectedIndex(0);
        jDialogDeleteFavorites.setLocationRelativeTo(this);
        jDialogDeleteFavorites.setVisible(true);
    }

    public JTable getHotKeysTable() {
        return jTableHotKeys;
    }

    /**
     * ������ ��� ������� �� ����� ������
     * @param visible <b>true</b> ��� ������ ������� �� �����, <b>false</b> -
     * ��� �������
     */
    public void setPanelsVisible(boolean visible) {
        jSplitPane1.setVisible(visible);
        jScrollPane3.setVisible(!visible);
    }

    /**
     * ���������� ������ �� ������
     * @return <b>true</b>, ���� ������ ������, <b>false</b> - �����
     */
    public boolean isPanelsVisible() {
        return jSplitPane1.isVisible();
    }

    public JTextField getActiveSearchLabel() {
        return getActive() ? jTextFieldSearchLeft : jTextFieldSearchRight;
    }

    private void onExit() {
        if (null != cmdProcess) {
            cmdProcess.destroy();
        }
        FileSystemList.clearTempDir();
        savePrefs();
        saveFavorites();
        System.out.println("\n\nClosing...\nThank you for using JCommander.");
    }

    public static void run(final String title, final String version) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainFrame(title, version).setVisible(true);
            }
        });
    }

    public void setActiveTable(boolean left) {
        currentLeftTable.setActive(left);
        currentRightTable.setActive(!left);
    }

    public JPanel getActiveSearchPanel() {
        return getActive() ? jPanelSearchMsgLeft : jPanelSearchMsgRight;
    }

    public JPanel getActiveStatusPanel() {
        if (currentLeftTable.isActive()) {
            return jPanelStatusMsgLeft;
        } else {
            return jPanelStatusMsgRight;
        }
    }

    public void setSearchVisible(boolean isVisible) {
        getActiveSearchPanel().setVisible(isVisible);
        getActiveStatusPanel().setVisible(!isVisible);
        if (isVisible) {
            getActiveSearchLabel().setText("");
            getActiveSearchLabel().requestFocus();
        } else {
            getActiveTable().requestFocus();
        }
    }

    public void exit() {
        onExit();
        System.exit(0);
    }

    private JTable createHotKeysTable() {
        JTable table = new JTable();

        Collection<Message> messages = MessageList.defaultMessageList.getMessages();
        Object[][] obj = new Object[messages.size()][3];
        int i = 0;
        for (Message message : messages) {
            obj[i][0] = message;
            obj[i][1] = message.getDefaultKey();
            obj[i++][2] = message.getUserKey();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(
                obj,
                new String[]{
                    lb.getString("StrEvent"), lb.getString("StrDefault"), lb.getString("StrUser")
                }) {

            Class[] types = new Class[]{
                Message.class, KeyShortcat.class, KeyShortcat.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table.setSelectionMode(0);
        table.setDefaultRenderer(Message.class, new HotKeysTableRenderer());
        return table;
    }

    public static Message findMessage(KeyShortcat ks) {
        Message msg = (Message) userKeyList.get(ks);
        if (null == msg) {
            msg = (Message) defaultKeyList.get(ks);
        }
        return msg;
    }

    public static Message findMessage(KeyEvent ke) {
        KeyShortcat ks = new KeyShortcat(ke);
        return findMessage(ks);
    }

    private void initFavorites() {
        JMenuItem menuItem;
        jPopupMenuFavorites.removeAll();
        menuItem = new JMenuItem(lb.getString("StrAddToBookmarks"));
        menuItem.addActionListener(new AddFavoritesListener(this));
        jPopupMenuFavorites.add(menuItem);
        menuItem = new JMenuItem(lb.getString("StrDeleteBookmark"));
        menuItem.addActionListener(new DeleteFavoritesListener());
        jPopupMenuFavorites.add(menuItem);
        JSeparator js = new JSeparator();
        jPopupMenuFavorites.add(js);
        for (BookmarkItem bookmarkItem : favoriteFolders) {
            menuItem = new JMenuItem(bookmarkItem.getItemName());
            menuItem.addActionListener(new SelectFavoritesListener(bookmarkItem.getFile()));
            jPopupMenuFavorites.add(menuItem);
        }

        jMenuFavorites.removeAll();
        menuItem = new JMenuItem(lb.getString("StrAddToBookmarks"));
        menuItem.addActionListener(new AddFavoritesListener(this));
        jMenuFavorites.add(menuItem);
        menuItem = new JMenuItem(lb.getString("StrDeleteBookmark"));
        menuItem.addActionListener(new DeleteFavoritesListener());
        jMenuFavorites.add(menuItem);
        js = new JSeparator();
        jMenuFavorites.add(js);
        for (BookmarkItem bookmarkItem : favoriteFolders) {
            menuItem = new JMenuItem(bookmarkItem.getItemName());
            menuItem.addActionListener(new SelectFavoritesListener(bookmarkItem.getFile()));
            jMenuFavorites.add(menuItem);
        }
    }

    private void loadFavorites() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(favoritesFile));
            favoriteFolders = new ArrayList();
            int size = ois.readInt();
            BookmarkItem fi;
            for (int i = 0; i < size; i++) {
                String name = ois.readUTF();
                String tmp = ois.readUTF();
                BaseFile af = FileHelper.getRealFile(tmp);
                fi = new BookmarkItem(name, af);
                favoriteFolders.add(fi);
            }
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.WARNING, null, ex);
        }
        initFavorites();
    }

    public FileTablePanel getFileTablePanel(boolean left) {
        if (left) {
            return leftFilePanel;
        } else {
            return rightFilePanel;
        }
    }

    public JTabbedPane getTabbedPane(boolean left) {
        if (left) {
            return jTabbedPaneLeft;
        } else {
            return jTabbedPaneRight;
        }
    }

    private void saveFavorites() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(favoritesFile));
            oos.writeInt(favoriteFolders.size());
            BookmarkItem fi;
            for (int i = 0; i < favoriteFolders.size(); i++) {
                fi = (BookmarkItem) favoriteFolders.get(i);
                String name = fi.getItemName();
                oos.writeUTF(name);
                String path = fi.getFile().getAbsolutePath();
                oos.writeUTF(path);
            }
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void onTabsChanged(boolean left) {
        JTabbedPane jTabbedPane = getTabbedPane(left);
        FileTablePanel fileTablePanel = getFileTablePanel(left);

        boolean tabsVisible = jTabbedPane.getTabCount() >= 2;

        jTabbedPane.setVisible(tabsVisible);
        fileTablePanel.setVisible(!tabsVisible);


        setTable(left, tabsVisible ? ((FileTablePanel) jTabbedPane.getSelectedComponent()).getFileTable() : fileTablePanel.getFileTable());
    }

    FileTable addTab(boolean left, BaseFile dir, ArrayList sortingColumns, int[] headerSizes) {
        JTabbedPane jTabbedPane = getTabbedPane(left);
        FileTable newTable = new FileTable(dir);
        newTable.setHeaderSizes(headerSizes);
        newTable.getSortingColumns().addAll(sortingColumns);
        newTable.addMouseInputAdapter(new TableMouseListener(this));
        newTable.addKeyListener(new TableKeyListener(this));
        newTable.addFocusListener(new TableFocusListener(this));
        FileTablePanel ftp = new FileTablePanel(newTable);
        ftp.setTransferHandler(new FileTransferHandler(this));
        jTabbedPane.addTab(dir.getAbstractFileName(), ftp);
        jTabbedPane.setIconAt(jTabbedPane.getTabCount() - 1, ImageArchive.getImageFolder());

        onTabsChanged(left);

        return newTable;
    }

    public void addTab(boolean left) {
        JTabbedPane jTabbedPane = getTabbedPane(left);
        FileTablePanel fileTablePanel = getFileTablePanel(left);
        FileTable table = getTable(left);

        BaseFile currentDir = table.getCurrentDir();
        FileTablePanel ftp = new FileTablePanel(currentDir);
        ArrayList list = table.getSortingColumns();

        FileTable newTable = ftp.getFileTable();
        newTable.setHeaderSizes(table.getHeaderSizes());
        newTable.getSortingColumns().addAll(list);
        newTable.addMouseInputAdapter(new TableMouseListener(this));
        newTable.addKeyListener(new TableKeyListener(this));
        newTable.addFocusListener(new TableFocusListener(this));
        jTabbedPane.addTab(currentDir.getAbstractFileName(), ftp);
        jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);

        setTable(left, newTable);

        newTable.setTransferHandler(new FileTransferHandler(this));
        ftp.setTransferHandler(new FileTransferHandler(this));

        jTabbedPane.setIconAt(jTabbedPane.getTabCount() - 1, ImageArchive.getImageFolder());

        if (false == jTabbedPane.isVisible()) {
            jTabbedPane.setVisible(true);
            fileTablePanel.setVisible(false);
            addTab(left);
        }
        newTable.setActive(true);
        getTable(!left).setActive(false);
        requestFocus();

        onTabsChanged(left);
    }

    public void removeTab(boolean left) {
        JTabbedPane jTabbedPane = getTabbedPane(left);
        FileTablePanel filePanel = getFileTablePanel(left);
        FileTable table;
        if (false == jTabbedPane.isVisible()) {
            return;
        }
        if (2 == jTabbedPane.getTabCount()) {
            filePanel.setVisible(true);
            jTabbedPane.remove(jTabbedPane.getSelectedIndex());
            FileTable ft = ((FileTablePanel) (jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex()))).getFileTable();
            table = filePanel.getFileTable();
            table.getFileList().setFileList(ft.getCurrentDir());
            jTabbedPane.setVisible(false);
            table.getSortingColumns().clear();
            table.getSortingColumns().addAll(ft.getSortingColumns());
            jTabbedPane.removeAll();
        } else {
            jTabbedPane.remove(jTabbedPane.getSelectedIndex());
            FileTablePanel ftp = (FileTablePanel) (jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex()));
            table = ftp.getFileTable();
        }
        setTable(left, table);
        setActiveTable(left);
        updatePanel(left);

        onTabsChanged(left);

        requestFocus();
    }

    public void selectNextTab() {
        JTabbedPane pane = getTabbedPane(getActive());
        int count = pane.getTabCount();
        if (count - 1 != pane.getSelectedIndex()) {
            pane.setSelectedIndex(pane.getSelectedIndex() + 1);
        }
    }

    public void selectPrevTab() {
        JTabbedPane pane = getTabbedPane(getActive());
        if (1 <= pane.getSelectedIndex()) {
            pane.setSelectedIndex(pane.getSelectedIndex() - 1);
        }

    }

    public void setTable(boolean left, FileTable table) {
        if (left) {
            currentLeftTable = table;
        } else {
            currentRightTable = table;
        }
    }

    public JLabel getActiveStatusLabel() {
        if (currentLeftTable.isActive()) {
            return jLabelStatusLeft;
        } else {
            return jLabelStatusRight;
        }
    }

    public void updateStatusLabel(boolean left) {
        FileTable currentTable = getTable(left);
        JLabel statusLabel = getStatusLabel(left);
        if (0 == currentTable.getSelectedList().size()) {
            statusLabel.setText(lb.getString("StrFiles") + ": " + currentTable.getFileList().getFilesCount() + ", " + lb.getString("StrDirs") + ": " + currentTable.getFileList().getDirCount());
        } else {
            long totalSize = currentTable.getSelectedFilesSize() / 1024;
            statusLabel.setText(lb.getString("StrSelected") + currentTable.getSelectedList().size() + ",    " + FileSystemList.getFormatedSize(totalSize) + " Kb");
        }
    }

    public void updateActiveStatusLabel() {
        updateStatusLabel(getActive());
    }

    public boolean getActive() {
        return currentLeftTable.isActive() ? true : false;
    }

    public boolean getExternEditor() {
        return jcPrefs.useExternEditor;
    }

    public String getExternEditorString() {
        return jcPrefs.externEditor;
    }

    public JTextField getCmdLine() {
        return jTextField3;
    }

    public void jDialogPreferencesReset() {
        jComboBoxLang.setSelectedItem(currentLocaleWrapper);
        jComboBoxCharset.setSelectedItem(consoleCharset.displayName());

        jCheckBoxEditor.setSelected(jcPrefs.useExternEditor);
        jButtonEditor.setEnabled(jcPrefs.useExternEditor);
        jTextFieldEditor.setText(jcPrefs.externEditor);

        jTableHotKeys = createHotKeysTable();
        jTableHotKeys.addKeyListener(new HKTableListener(this));
        jScrollPanePrefHK.setViewportView(jTableHotKeys);

        jCheckBoxPrefShowButtons.setSelected(jcPrefs.showButtons);
        jCheckBoxPrefShowCmdline.setSelected(jcPrefs.showCommandLine);
        jCheckBoxPrefShowHidden.setSelected(jcPrefs.showHiddenFiles);
        jCheckBoxPrefShowTooltips.setSelected(jcPrefs.showToolTips);
        jCheckBoxPrefsUseSystemIcons.setSelected(jcPrefs.useSystemIcons);
    }

    public void showButtonsBar(boolean show) {
        jPanel7.setVisible(show);
        pack();
    }

    public void showCmdLine(boolean show) {
        jTextField3.setVisible(show);
        pack();
    }

    public void showHiddenFiles(boolean show) {
        currentLeftTable.setHiddenFilesVisibility(show);
        currentLeftTable.refreshTable();
        currentRightTable.setHiddenFilesVisibility(show);
        currentRightTable.refreshTable();
    }

    public void viewFile(BaseFile file) {
        if (file == null || file.isDirectory()) {
            return;
        }
        omegaCommander.editor.Editor editor = new omegaCommander.editor.Editor(this);
        editor.openFile(file, false);
    }

    public void runFileInEditor(BaseFile file) {
        if (getExternEditor()) {
            String externEditorString = getExternEditorString();
            if (!externEditorString.trim().equals("")) {
                String command = externEditorString + " " + file;
                try {
                    Runtime.getRuntime().exec(command);
                } catch (IOException exc) {
                    WarningDialog.showMessage(this, lb.getString("StrErrorExecute") + "\n" + command, lb.getString("StrJC"), WarningDialog.MESSAGE_ERROR);
                }
            }
        } else {
            omegaCommander.editor.Editor editor = new omegaCommander.editor.Editor(this);
            editor.openFile(file, true);
        }
    }

    public void changeArrangement() {
        double d = getDividerLocation();
        if (jSplitPane1.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
            jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        } else {
            jSplitPane1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        }
        jcPrefs.arrangement = jSplitPane1.getOrientation() == JSplitPane.VERTICAL_SPLIT;
        jSplitPane1.setDividerLocation(d);
        hideSplitterMenu();
    }

    enum WindowState {

        Normal, Maximized, MaximizedHorizontal, MaximizedVertical
    };

    WindowState getWindowState() {
        int state = getExtendedState();
        if ((state & MAXIMIZED_BOTH) == MAXIMIZED_BOTH) {
            return WindowState.Maximized;
        } else {
            if ((state & MAXIMIZED_VERT) != 0) {
                return WindowState.MaximizedVertical;
            }
            if ((state & MAXIMIZED_HORIZ) != 0) {
                return WindowState.MaximizedHorizontal;
            }
            return WindowState.Normal;
        }
    }

    public ActionManager getOperationManager() {
        return operationManager;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jDialogAbout = new javax.swing.JDialog(this);
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabelCurrentVersion = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelCopyright = new javax.swing.JLabel();
        jLabelMail = new javax.swing.JLabel();
        jLabelPage = new javax.swing.JLabel();
        jDialogPreferences = new javax.swing.JDialog(this);
        jPanel15 = new javax.swing.JPanel();
        jTabbedPanePrefs = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanelPrefConsole = new javax.swing.JPanel();
        jLabelPrefCharset = new javax.swing.JLabel();
        jComboBoxCharset = new javax.swing.JComboBox();
        jPanel21 = new javax.swing.JPanel();
        jLabelPrefLang = new javax.swing.JLabel();
        jComboBoxLang = new javax.swing.JComboBox();
        jPanelEditor = new javax.swing.JPanel();
        jCheckBoxEditor = new javax.swing.JCheckBox();
        jPanel23 = new javax.swing.JPanel();
        jLabelEditor = new javax.swing.JLabel();
        jTextFieldEditor = new javax.swing.JTextField();
        jButtonEditor = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanelPrefTheme = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabelTheme = new javax.swing.JLabel();
        jComboBoxTheme = new javax.swing.JComboBox();
        jCheckBoxPrefsUseSystemIcons = new javax.swing.JCheckBox();
        jPanelPrefView = new javax.swing.JPanel();
        jCheckBoxPrefShowButtons = new javax.swing.JCheckBox();
        jCheckBoxPrefShowCmdline = new javax.swing.JCheckBox();
        jCheckBoxPrefShowHidden = new javax.swing.JCheckBox();
        jCheckBoxPrefShowTooltips = new javax.swing.JCheckBox();
        jPanel26 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPanePrefHK = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaHotKeysHelp = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jButtonPrefOk = new javax.swing.JButton();
        jButtonPrefCancel = new javax.swing.JButton();
        jButtonPrefApply = new javax.swing.JButton();
        jDialogHotKeys = new javax.swing.JDialog(this);
        jPanel13 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jScrollPaneHelpHK = new javax.swing.JScrollPane();
        jPopupMenuFavorites = new javax.swing.JPopupMenu();
        jDialogDeleteFavorites = new javax.swing.JDialog(this);
        jScrollPane5 = new javax.swing.JScrollPane();
        jListFavorites = new javax.swing.JList();
        jPanel20 = new javax.swing.JPanel();
        jButtonDeleteFavorite = new javax.swing.JButton();
        jButtonHideDeleteFavoriteDialog = new javax.swing.JButton();
        jDialogFileProperties = new javax.swing.JDialog();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanelCenter = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jComboBoxLeft = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jButtonFavorities1 = new javax.swing.JButton();
        jPanelStatusLeft = new javax.swing.JPanel();
        jPanelStatusMsgLeft = new javax.swing.JPanel();
        jLabelStatusLeft = new javax.swing.JLabel();
        jPanelSearchMsgLeft = new javax.swing.JPanel();
        jTextFieldSearchLeft = new javax.swing.JTextField();
        jPanelCenterLeft = new javax.swing.JPanel();
        jTabbedPaneLeft = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jComboBoxRight = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jButtonFavorities2 = new javax.swing.JButton();
        jPanelStatusRight = new javax.swing.JPanel();
        jPanelStatusMsgRight = new javax.swing.JPanel();
        jLabelStatusRight = new javax.swing.JLabel();
        jPanelSearchMsgRight = new javax.swing.JPanel();
        jTextFieldSearchRight = new javax.swing.JTextField();
        jPanelCenterRight = new javax.swing.JPanel();
        jTabbedPaneRight = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanelButtons = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jButtonView = new javax.swing.JButton();
        jButtonEdit = new javax.swing.JButton();
        jButtonCopy = new javax.swing.JButton();
        jButtonMove = new javax.swing.JButton();
        jButtonCreateDir = new javax.swing.JButton();
        jButtonDel = new javax.swing.JButton();
        jButtonPack = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jPanelToolBar = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuView = new javax.swing.JMenu();
        jCheckBoxMenuItemButtonBar = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemCommandLine = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemHiddenFiles = new javax.swing.JCheckBoxMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jCheckBoxMenuItemArrangement = new javax.swing.JCheckBoxMenuItem();
        jMenuFavorites = new javax.swing.JMenu();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemFind = new javax.swing.JMenuItem();
        jMenuItemAddTab = new javax.swing.JMenuItem();
        jMenuItemRemoveTab = new javax.swing.JMenuItem();
        jMenuItemDecodeHex = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuItemPreferences = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemHotKeys = new javax.swing.JMenuItem();
        jMenuItemAbout = new javax.swing.JMenuItem();

        jDialogAbout.setResizable(false);
        jDialogAbout.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                jDialogAboutWindowLostFocus(evt);
            }
        });
        jDialogAbout.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDialogAboutKeyPressed(evt);
            }
        });
        jDialogAbout.getContentPane().add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabelCurrentVersion.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelCurrentVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCurrentVersion.setText("jLabelCurrentVersion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 12;
        jPanel4.add(jLabelCurrentVersion, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 101;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPanel4.add(jSeparator1, gridBagConstraints);

        jLabelCopyright.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelCopyright.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCopyright.setText("copyright");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 101;
        gridBagConstraints.insets = new java.awt.Insets(18, 0, 0, 0);
        jPanel4.add(jLabelCopyright, gridBagConstraints);

        jLabelMail.setFont(new java.awt.Font("Arial", 0, 11));
        jLabelMail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMail.setText("mail");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 99;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        jPanel4.add(jLabelMail, gridBagConstraints);

        jLabelPage.setFont(new java.awt.Font("Arial", 0, 11));
        jLabelPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPage.setText("jLabel2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 99;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        jPanel4.add(jLabelPage, gridBagConstraints);

        jDialogAbout.getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        jDialogPreferences.setModal(true);
        jDialogPreferences.setResizable(false);
        jDialogPreferences.getContentPane().setLayout(new javax.swing.BoxLayout(jDialogPreferences.getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.Y_AXIS));

        jPanelPrefConsole.setBorder(javax.swing.BorderFactory.createTitledBorder("�������"));
        jPanelPrefConsole.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabelPrefCharset.setText("���������");
        jPanelPrefConsole.add(jLabelPrefCharset);

        jComboBoxCharset.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanelPrefConsole.add(jComboBoxCharset);

        jPanel12.add(jPanelPrefConsole);

        jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabelPrefLang.setText("����");
        jPanel21.add(jLabelPrefLang);
        jPanel21.add(jComboBoxLang);

        jPanel12.add(jPanel21);

        jPanelEditor.setBorder(javax.swing.BorderFactory.createTitledBorder("������� ��������"));
        jPanelEditor.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jCheckBoxEditor.setText("������������ ������� ��������");
        jCheckBoxEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxEditorActionPerformed(evt);
            }
        });
        jPanelEditor.add(jCheckBoxEditor);

        jPanel23.setEnabled(false);

        jLabelEditor.setText("��������:");
        jPanel23.add(jLabelEditor);

        jTextFieldEditor.setPreferredSize(new java.awt.Dimension(240, 25));
        jPanel23.add(jTextFieldEditor);

        jButtonEditor.setText("�����...");
        jButtonEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditorActionPerformed(evt);
            }
        });
        jPanel23.add(jButtonEditor);

        jPanelEditor.add(jPanel23);

        jPanel12.add(jPanelEditor);
        jPanel12.add(jPanel22);

        jTabbedPanePrefs.addTab("�������", jPanel12);

        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanelPrefTheme.setBorder(javax.swing.BorderFactory.createTitledBorder("LookAndFeel"));
        jPanelPrefTheme.setLayout(new java.awt.GridLayout(2, 2, 5, 0));

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabelTheme.setText("jLabel2");
        jPanel3.add(jLabelTheme);
        jPanel3.add(jComboBoxTheme);

        jPanelPrefTheme.add(jPanel3);

        jCheckBoxPrefsUseSystemIcons.setText("jCheckBox4");
        jPanelPrefTheme.add(jCheckBoxPrefsUseSystemIcons);

        jPanel24.add(jPanelPrefTheme, java.awt.BorderLayout.NORTH);

        jPanelPrefView.setBorder(javax.swing.BorderFactory.createTitledBorder("�����������"));
        jPanelPrefView.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanelPrefView.setLayout(new java.awt.GridLayout(4, 2));

        jCheckBoxPrefShowButtons.setText("jCheckBox1");
        jPanelPrefView.add(jCheckBoxPrefShowButtons);

        jCheckBoxPrefShowCmdline.setText("jCheckBox2");
        jPanelPrefView.add(jCheckBoxPrefShowCmdline);

        jCheckBoxPrefShowHidden.setText("jCheckBox3");
        jPanelPrefView.add(jCheckBoxPrefShowHidden);

        jCheckBoxPrefShowTooltips.setText("jCheckBox4");
        jPanelPrefView.add(jCheckBoxPrefShowTooltips);

        jPanel24.add(jPanelPrefView, java.awt.BorderLayout.CENTER);

        jPanel26.setPreferredSize(new java.awt.Dimension(10, 50));
        jPanel24.add(jPanel26, java.awt.BorderLayout.PAGE_END);

        jTabbedPanePrefs.addTab("������� ���", jPanel24);

        jPanel14.setLayout(new java.awt.BorderLayout());
        jPanel14.add(jScrollPanePrefHK, java.awt.BorderLayout.CENTER);

        jTextAreaHotKeysHelp.setEditable(false);
        jTextAreaHotKeysHelp.setFont(new java.awt.Font("Tahoma", 0, 10));
        jTextAreaHotKeysHelp.setLineWrap(true);
        jTextAreaHotKeysHelp.setRows(2);
        jTextAreaHotKeysHelp.setWrapStyleWord(true);
        jTextAreaHotKeysHelp.setMargin(new java.awt.Insets(1, 5, 1, 5));
        jScrollPane1.setViewportView(jTextAreaHotKeysHelp);

        jPanel14.add(jScrollPane1, java.awt.BorderLayout.SOUTH);

        jTabbedPanePrefs.addTab("������� �������", jPanel14);

        jPanel15.add(jTabbedPanePrefs, java.awt.BorderLayout.CENTER);

        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonPrefOk.setText("��");
        jButtonPrefOk.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonPrefOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrefOkActionPerformed(evt);
            }
        });
        jPanel9.add(jButtonPrefOk);

        jButtonPrefCancel.setText("������");
        jButtonPrefCancel.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonPrefCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrefCancelActionPerformed(evt);
            }
        });
        jPanel9.add(jButtonPrefCancel);

        jButtonPrefApply.setText("���������");
        jButtonPrefApply.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonPrefApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrefApplyActionPerformed(evt);
            }
        });
        jPanel9.add(jButtonPrefApply);

        jPanel15.add(jPanel9, java.awt.BorderLayout.SOUTH);

        jDialogPreferences.getContentPane().add(jPanel15);

        jDialogHotKeys.setModal(true);
        jDialogHotKeys.setResizable(false);

        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton5.setText("OK");
        jButton5.setPreferredSize(new java.awt.Dimension(90, 25));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jButton5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton5KeyPressed(evt);
            }
        });
        jPanel13.add(jButton5);

        jDialogHotKeys.getContentPane().add(jPanel13, java.awt.BorderLayout.SOUTH);
        jDialogHotKeys.getContentPane().add(jScrollPaneHelpHK, java.awt.BorderLayout.CENTER);

        jDialogDeleteFavorites.setModal(true);
        jDialogDeleteFavorites.setResizable(false);

        jScrollPane5.setViewportView(jListFavorites);

        jDialogDeleteFavorites.getContentPane().add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonDeleteFavorite.setText("�������");
        jButtonDeleteFavorite.setPreferredSize(new java.awt.Dimension(90, 25));
        jButtonDeleteFavorite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteFavoriteActionPerformed(evt);
            }
        });
        jPanel20.add(jButtonDeleteFavorite);

        jButtonHideDeleteFavoriteDialog.setText("�������");
        jButtonHideDeleteFavoriteDialog.setPreferredSize(new java.awt.Dimension(90, 25));
        jButtonHideDeleteFavoriteDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHideDeleteFavoriteDialogActionPerformed(evt);
            }
        });
        jPanel20.add(jButtonHideDeleteFavoriteDialog);

        jDialogDeleteFavorites.getContentPane().add(jPanel20, java.awt.BorderLayout.SOUTH);

        jDialogFileProperties.getContentPane().setLayout(new java.awt.FlowLayout());

        jCheckBox1.setText("CanRead");
        jDialogFileProperties.getContentPane().add(jCheckBox1);

        jCheckBox2.setText("CanExecute");
        jDialogFileProperties.getContentPane().add(jCheckBox2);

        jCheckBox3.setText("CanWrite");
        jDialogFileProperties.getContentPane().add(jCheckBox3);

        jButton3.setText("OK");
        jDialogFileProperties.getContentPane().add(jButton3);

        jButton4.setText("Cancel");
        jDialogFileProperties.getContentPane().add(jButton4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanelCenter.setLayout(new java.awt.CardLayout());

        jSplitPane1.setBorder(null);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setFocusable(false);
        jSplitPane1.setRequestFocusEnabled(false);
        jSplitPane1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSplitPane1PropertyChange(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jComboBoxLeft.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxLeftPopupMenuCanceled(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxLeftPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxLeftPopupMenuWillBecomeVisible(evt);
            }
        });
        jPanel5.add(jComboBoxLeft);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMaximumSize(new java.awt.Dimension(20, 32767));
        jSeparator2.setMinimumSize(new java.awt.Dimension(20, 50));
        jSeparator2.setPreferredSize(new java.awt.Dimension(10, 0));
        jPanel5.add(jSeparator2);

        jPanel10.setMinimumSize(new java.awt.Dimension(100, 21));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Dialog", 1, 12));
        jTextField1.setText("left");
        jTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel10.add(jTextField1);

        jPanel5.add(jPanel10);

        jPanel19.setLayout(new javax.swing.BoxLayout(jPanel19, javax.swing.BoxLayout.LINE_AXIS));

        jButtonFavorities1.setText("*");
        jButtonFavorities1.setToolTipText("��������");
        jButtonFavorities1.setFocusable(false);
        jButtonFavorities1.setMaximumSize(new java.awt.Dimension(23, 25));
        jButtonFavorities1.setMinimumSize(new java.awt.Dimension(23, 25));
        jButtonFavorities1.setPreferredSize(new java.awt.Dimension(23, 25));
        jButtonFavorities1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonFavorities1MouseClicked(evt);
            }
        });
        jPanel19.add(jButtonFavorities1);

        jPanel5.add(jPanel19);

        jPanel1.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanelStatusLeft.setPreferredSize(new java.awt.Dimension(25, 26));
        jPanelStatusLeft.setLayout(new java.awt.CardLayout());

        jLabelStatusLeft.setText("left");
        jPanelStatusMsgLeft.add(jLabelStatusLeft);

        jPanelStatusLeft.add(jPanelStatusMsgLeft, "card2");

        jPanelSearchMsgLeft.setLayout(new javax.swing.BoxLayout(jPanelSearchMsgLeft, javax.swing.BoxLayout.LINE_AXIS));

        jTextFieldSearchLeft.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldSearchLeftFocusLost(evt);
            }
        });
        jPanelSearchMsgLeft.add(jTextFieldSearchLeft);

        jPanelStatusLeft.add(jPanelSearchMsgLeft, "card3");

        jPanel1.add(jPanelStatusLeft, java.awt.BorderLayout.SOUTH);

        jPanelCenterLeft.setLayout(new java.awt.CardLayout());

        jTabbedPaneLeft.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPaneLeft.setFocusable(false);
        jTabbedPaneLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneLeftMouseClicked(evt);
            }
        });
        jTabbedPaneLeft.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneLeftStateChanged(evt);
            }
        });
        jPanelCenterLeft.add(jTabbedPaneLeft, "card3");

        jPanel1.add(jPanelCenterLeft, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jComboBoxRight.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxRightPopupMenuCanceled(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxRightPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBoxRightPopupMenuWillBecomeVisible(evt);
            }
        });
        jPanel6.add(jComboBoxRight);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setPreferredSize(new java.awt.Dimension(10, 10));
        jPanel6.add(jSeparator3);

        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.LINE_AXIS));

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Dialog", 1, 12));
        jTextField2.setText("right");
        jTextField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.add(jTextField2);

        jPanel6.add(jPanel11);

        jButtonFavorities2.setText("*");
        jButtonFavorities2.setToolTipText("��������");
        jButtonFavorities2.setFocusable(false);
        jButtonFavorities2.setMaximumSize(new java.awt.Dimension(23, 25));
        jButtonFavorities2.setMinimumSize(new java.awt.Dimension(23, 25));
        jButtonFavorities2.setPreferredSize(new java.awt.Dimension(23, 25));
        jButtonFavorities2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonFavorities2MouseClicked(evt);
            }
        });
        jPanel6.add(jButtonFavorities2);

        jPanel2.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanelStatusRight.setPreferredSize(new java.awt.Dimension(210, 26));
        jPanelStatusRight.setLayout(new java.awt.CardLayout());

        jLabelStatusRight.setText("right");
        jPanelStatusMsgRight.add(jLabelStatusRight);

        jPanelStatusRight.add(jPanelStatusMsgRight, "card2");

        jPanelSearchMsgRight.setLayout(new javax.swing.BoxLayout(jPanelSearchMsgRight, javax.swing.BoxLayout.LINE_AXIS));

        jTextFieldSearchRight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldSearchRightFocusLost(evt);
            }
        });
        jPanelSearchMsgRight.add(jTextFieldSearchRight);

        jPanelStatusRight.add(jPanelSearchMsgRight, "card3");

        jPanel2.add(jPanelStatusRight, java.awt.BorderLayout.SOUTH);

        jPanelCenterRight.setLayout(new java.awt.CardLayout());

        jTabbedPaneRight.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPaneRight.setFocusable(false);
        jTabbedPaneRight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneRightMouseClicked(evt);
            }
        });
        jTabbedPaneRight.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneRightStateChanged(evt);
            }
        });
        jPanelCenterRight.add(jTabbedPaneRight, "card3");

        jPanel2.add(jPanelCenterRight, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel2);

        jPanelCenter.add(jSplitPane1, "card2");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTextArea1ComponentShown(evt);
            }
        });
        jScrollPane3.setViewportView(jTextArea1);

        jPanelCenter.add(jScrollPane3, "card3");

        getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

        jPanelButtons.setLayout(new javax.swing.BoxLayout(jPanelButtons, javax.swing.BoxLayout.Y_AXIS));

        jTextField3.setPreferredSize(new java.awt.Dimension(6, 25));
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
        });
        jPanelButtons.add(jTextField3);

        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jButtonView.setFont(new java.awt.Font("MS Sans Serif", 1, 9));
        jButtonView.setFocusable(false);
        jButtonView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonView);

        jButtonEdit.setFont(new java.awt.Font("MS Sans Serif", 1, 9));
        jButtonEdit.setFocusable(false);
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonEdit);

        jButtonCopy.setFont(new java.awt.Font("MS Sans Serif", 1, 9));
        jButtonCopy.setFocusable(false);
        jButtonCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCopyActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonCopy);

        jButtonMove.setFont(new java.awt.Font("MS Sans Serif", 1, 9));
        jButtonMove.setFocusable(false);
        jButtonMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMoveActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonMove);

        jButtonCreateDir.setFont(new java.awt.Font("MS Sans Serif", 1, 9));
        jButtonCreateDir.setFocusable(false);
        jButtonCreateDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateDirActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonCreateDir);

        jButtonDel.setFont(new java.awt.Font("MS Sans Serif", 1, 9));
        jButtonDel.setFocusable(false);
        jButtonDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDelActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonDel);

        jButtonPack.setFont(new java.awt.Font("MS Sans Serif", 1, 9));
        jButtonPack.setFocusable(false);
        jButtonPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPackActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonPack);

        jButtonExit.setFont(new java.awt.Font("MS Sans Serif", 1, 9));
        jButtonExit.setFocusable(false);
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonExit);

        jPanelButtons.add(jPanel7);

        getContentPane().add(jPanelButtons, java.awt.BorderLayout.SOUTH);

        jPanelToolBar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        jPanelToolBar.add(jToolBar1);

        jButton1.setText("jButton1");
        jPanelToolBar.add(jButton1);

        getContentPane().add(jPanelToolBar, java.awt.BorderLayout.NORTH);

        jMenuFile.setText("File");

        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar2.add(jMenuFile);

        jMenuView.setText("View");

        jCheckBoxMenuItemButtonBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemButtonBarActionPerformed(evt);
            }
        });
        jMenuView.add(jCheckBoxMenuItemButtonBar);

        jCheckBoxMenuItemCommandLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemCommandLineActionPerformed(evt);
            }
        });
        jMenuView.add(jCheckBoxMenuItemCommandLine);

        jCheckBoxMenuItemHiddenFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemHiddenFilesActionPerformed(evt);
            }
        });
        jMenuView.add(jCheckBoxMenuItemHiddenFiles);
        jMenuView.add(jSeparator5);

        jCheckBoxMenuItemArrangement.setSelected(true);
        jCheckBoxMenuItemArrangement.setText("jCheckBoxMenuItem1");
        jCheckBoxMenuItemArrangement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemArrangementActionPerformed(evt);
            }
        });
        jMenuView.add(jCheckBoxMenuItemArrangement);

        jMenuBar2.add(jMenuView);

        jMenuFavorites.setText("Favorites");
        jMenuBar2.add(jMenuFavorites);

        jMenuEdit.setText("Edit");

        jMenuItemFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFindActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemFind);

        jMenuItemAddTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddTabActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemAddTab);

        jMenuItemRemoveTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoveTabActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemRemoveTab);

        jMenuItemDecodeHex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDecodeHexActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemDecodeHex);
        jMenuEdit.add(jSeparator4);

        jMenuItemPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPreferencesActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemPreferences);

        jMenuBar2.add(jMenuEdit);

        jMenuHelp.setText("Help");

        jMenuItemHotKeys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHotKeysActionPerformed(evt);
            }
        });
        jMenuHelp.add(jMenuItemHotKeys);

        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });
        jMenuHelp.add(jMenuItemAbout);

        jMenuBar2.add(jMenuHelp);

        setJMenuBar(jMenuBar2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc=" Generated functions ">
    private void jMenuItemRemoveTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoveTabActionPerformed
        ACTION_REMOVE_TAB.execute();
    }//GEN-LAST:event_jMenuItemRemoveTabActionPerformed

    private void jMenuItemAddTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddTabActionPerformed
        ACTION_ADD_TAB.execute();
    }//GEN-LAST:event_jMenuItemAddTabActionPerformed

    private void jButtonPrefApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrefApplyActionPerformed
        applyButtonPressed();
    }//GEN-LAST:event_jButtonPrefApplyActionPerformed

    private void jTabbedPaneRightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneRightStateChanged
        if (2 <= jTabbedPaneRight.getTabCount()) {
            FileTablePanel ftp = (FileTablePanel) (jTabbedPaneRight.getComponentAt(jTabbedPaneRight.getSelectedIndex()));
            currentRightTable = ftp.getFileTable();
            jTabbedPaneRight.setTitleAt(jTabbedPaneRight.getSelectedIndex(), getTable(false).getCurrentDir().getAbstractFileName());
            currentRightTable.requestFocus();
            currentRightTable.setHiddenFilesVisibility(jcPrefs.showHiddenFiles);
            currentRightTable.showToolTip(jcPrefs.showToolTips);
            updatePanel(false);
        }

    }//GEN-LAST:event_jTabbedPaneRightStateChanged

    private void jTabbedPaneLeftStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneLeftStateChanged
        if (2 <= jTabbedPaneLeft.getTabCount()) {
            FileTablePanel ftp = (FileTablePanel) (jTabbedPaneLeft.getComponentAt(jTabbedPaneLeft.getSelectedIndex()));
            currentLeftTable = ftp.getFileTable();
            jTabbedPaneLeft.setTitleAt(jTabbedPaneLeft.getSelectedIndex(), getTable(true).getCurrentDir().getAbstractFileName());
            currentLeftTable.requestFocus();
            currentLeftTable.setHiddenFilesVisibility(jcPrefs.showHiddenFiles);
            currentLeftTable.showToolTip(jcPrefs.showToolTips);
            updatePanel(true);
        }
    }//GEN-LAST:event_jTabbedPaneLeftStateChanged

    private void jMenuItemFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFindActionPerformed
        ACTION_FIND.execute();
    }//GEN-LAST:event_jMenuItemFindActionPerformed

    private void jButtonFavorities2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonFavorities2MouseClicked
        jPopupMenuFavorites.show(evt.getComponent(), evt.getX(), evt.getY());
        setActiveTable(false);
    }//GEN-LAST:event_jButtonFavorities2MouseClicked

    private void jButtonHideDeleteFavoriteDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHideDeleteFavoriteDialogActionPerformed
        jDialogDeleteFavorites.setVisible(false);
    }//GEN-LAST:event_jButtonHideDeleteFavoriteDialogActionPerformed

    private void jButtonDeleteFavoriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteFavoriteActionPerformed
        if (0 >= favoriteFolders.size()) {
            return;
        }
        int indices[] = jListFavorites.getSelectedIndices();
        for (int i = 0; i < indices.length; i++) {
            BookmarkItem fi = (BookmarkItem) jListFavorites.getModel().getElementAt(indices[i]);
            if (favoriteFolders.contains(fi)) {
                favoriteFolders.remove(fi);
            }
        }
        jListFavorites.setListData(favoriteFolders.toArray());
        initFavorites();
    }//GEN-LAST:event_jButtonDeleteFavoriteActionPerformed

    private void jTextFieldSearchRightFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSearchRightFocusLost
        setSearchVisible(false);
    }//GEN-LAST:event_jTextFieldSearchRightFocusLost

    private void jTextFieldSearchLeftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSearchLeftFocusLost
        setSearchVisible(false);
    }//GEN-LAST:event_jTextFieldSearchLeftFocusLost

    private void jSplitPane1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSplitPane1PropertyChange
        if (!this.isVisible()) {
            return;
        }
        if (evt.getPropertyName().equals("dividerLocation")) {
            double d = getDividerLocation() * 100;
            splitterPopupMenu = new JPopupMenu();
            splitterPopupMenu.add("" + String.format("%1$.1f%% / %2$.1f%%", d, 100 - d));
            if (jcPrefs.arrangement) {
                splitterPopupMenu.show(this, 100, jSplitPane1.getDividerLocation() + 20);
            } else {
                splitterPopupMenu.show(this, jSplitPane1.getDividerLocation(), 100);
            }
        }
    }//GEN-LAST:event_jSplitPane1PropertyChange

    private void jButton5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton5KeyPressed
        if ((KeyEvent.VK_ENTER == evt.getKeyCode()) || (KeyEvent.VK_ESCAPE == evt.getKeyCode())) {
            jDialogHotKeys.setVisible(false);
        }
    }//GEN-LAST:event_jButton5KeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jDialogHotKeys.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItemHotKeysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHotKeysActionPerformed
        jDialogHotKeys.setLocationRelativeTo(this);
        jDialogHotKeys.setVisible(true);
    }//GEN-LAST:event_jMenuItemHotKeysActionPerformed

    private void jButtonPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPackActionPerformed
        ACTION_PACK.execute();
    }//GEN-LAST:event_jButtonPackActionPerformed

    private void jDialogAboutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDialogAboutKeyPressed
        if (KeyEvent.VK_ESCAPE == evt.getKeyCode()) {
            jDialogAbout.setVisible(false);
        }
    }//GEN-LAST:event_jDialogAboutKeyPressed

    private void jButtonPrefCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrefCancelActionPerformed
        jDialogPreferences.setVisible(false);
        jDialogPreferencesReset();
    }//GEN-LAST:event_jButtonPrefCancelActionPerformed

    private void jButtonPrefOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrefOkActionPerformed
        jButtonPrefApplyActionPerformed(evt);
        jDialogPreferences.setVisible(false);
    }//GEN-LAST:event_jButtonPrefOkActionPerformed

    private void jMenuItemPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPreferencesActionPerformed
        jDialogPreferencesReset();
        jDialogPreferences.setLocationRelativeTo(this);
        jDialogPreferences.setVisible(true);
    }//GEN-LAST:event_jMenuItemPreferencesActionPerformed

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        jMenuItemExitActionPerformed(evt);
    }//GEN-LAST:event_jButtonExitActionPerformed

    private void jButtonDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDelActionPerformed
        ACTION_DELETE.execute();
    }//GEN-LAST:event_jButtonDelActionPerformed

    private void jButtonCreateDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateDirActionPerformed
        ACTION_NEW_DIR.execute();
    }//GEN-LAST:event_jButtonCreateDirActionPerformed

    private void jButtonMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMoveActionPerformed
        ACTION_MOVE.execute();
    }//GEN-LAST:event_jButtonMoveActionPerformed

    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed
        ACTION_EDIT.execute();
    }//GEN-LAST:event_jButtonEditActionPerformed

    private void jButtonViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewActionPerformed
        ACTION_VIEW.execute();
    }//GEN-LAST:event_jButtonViewActionPerformed

    private void jButtonCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCopyActionPerformed
        ACTION_COPY.execute();
    }//GEN-LAST:event_jButtonCopyActionPerformed

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed

        if (evt.isControlDown()) {
            if (KeyEvent.VK_C == evt.getKeyCode()) {
                if (null != cmdProcess) {
                    cmdProcess.destroy();
                    return;
                }
            }
        }

        Message msg = (Message) defaultKeyList.get(new KeyShortcat(evt));

        if (null == msg) {
            return;
        }
        int messageId = msg.getMessageID();

        if (MessageList.MSG_SHOW_PANELS == messageId) {
            setPanelsVisible(!isPanelsVisible());
        }

        if (MessageList.MSG_UP == messageId) {
            commandListPosition--;
            if (commandListPosition < 0) {
                commandListPosition = 0;
            } else {
                jTextField3.setText((String) commandList.get(commandListPosition));
            }
        }
        if (MessageList.MSG_GOTO_CMDLINE == messageId) {
            getActiveTable().requestFocus();
        }
        if (MessageList.MSG_DOWN == messageId) {
            commandListPosition++;
            if (commandListPosition >= commandList.size()) {
                commandListPosition = commandList.size();
                jTextField3.setText("");
            } else {
                jTextField3.setText((String) commandList.get(commandListPosition));
            }
        }
        if (MessageList.MSG_ENTER == messageId) { //enter
            String command = jTextField3.getText();
            if (command.trim().equals("")) {
                return;
            }
            if (commandList.contains(command)) {
                commandList.remove(command);
            }
            commandList.add(command);
            commandListPosition = commandList.size();
            jTextField3.setText("");
            StringTokenizer st = new StringTokenizer(command);
            int cmdCount = st.countTokens();
            String[] cmdArguments = new String[cmdCount];
            for (int i = 0; i < cmdCount; i++) {
                cmdArguments[i] = st.nextToken();
            }
            if (cmdArguments[0].equals("cd")) {
                if (cmdArguments.length < 2) {
                    return;
                }
                if (cmdArguments[1].equals(".")) {
                    return;
                }
                LocalFile af;
                if (cmdArguments[1].equals("..")) {
                    if (false == getActiveTable().getCurrentDir().hasParent()) {
                        return;
                    }
                    af = new LocalFile(getActiveTable().getCurrentDir().getAbsoluteParent());
                } else {
                    if (cmdArguments[1].equals(java.io.File.separator)) {
                        af = (LocalFile) getActiveTable().getCurrentDir().getRoot();
                    } else {
                        af = new LocalFile(cmdArguments[1]);
                    }
                }
                if (false == af.isAbsolute()) {
                    af = (LocalFile) FileHelper.getRealFile(getActiveTable().getCurrentDir(), cmdArguments[1]);
                }
                if (false == af.exists()) {
                    return;
                }
                try {
                    getActiveTable().setCurrentDir(af);
                    getActiveTable().setCurrentPosition(0);
                    updateActivePanel();
                } catch (NullPointerException e) {
                }
            } else {
                String lineSeparator = System.getProperty("line.separator");
                try {
                    jTextArea1.append(command);
                    jTextArea1.append(lineSeparator);
                    LocalFile executingFile = new LocalFile(cmdArguments[0]);
                    if (false == executingFile.exists()) {
                        if (false == executingFile.isAbsolute()) {
                            executingFile = (LocalFile) FileHelper.getRealFile(
                                    getActiveTable().getCurrentDir(), cmdArguments[0]);
                            if (true == executingFile.exists()) {
                                cmdArguments[0] = executingFile.getAbsolutePath();
                            }
                        }
                    }
                    cmdProcess = Runtime.getRuntime().exec(cmdArguments, null,
                            (LocalFile) getActiveTable().getCurrentDir());
                    ProcessStreamer ps = new ProcessStreamer(cmdProcess);
                    ps.start();
                } catch (java.io.IOException e) {
                    jTextArea1.append(lineSeparator + e.toString());
                    Logger.getLogger(MainFrame.class.getName()).log(Level.INFO, null, e);
                }
            }
        }
    }//GEN-LAST:event_jTextField3KeyPressed

    private void jDialogAboutWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialogAboutWindowLostFocus
        jDialogAbout.setVisible(false);
    }//GEN-LAST:event_jDialogAboutWindowLostFocus

    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
        jDialogAbout.setLocationRelativeTo(this);
        jDialogAbout.setVisible(true);
    }//GEN-LAST:event_jMenuItemAboutActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        ACTION_EXIT.execute();
    }//GEN-LAST:event_jMenuItemExitActionPerformed

private void jCheckBoxMenuItemButtonBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemButtonBarActionPerformed
    jcPrefs.showButtons = !jcPrefs.showButtons;
    showButtonsBar(jcPrefs.showButtons);
}//GEN-LAST:event_jCheckBoxMenuItemButtonBarActionPerformed

private void jCheckBoxMenuItemCommandLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemCommandLineActionPerformed
    jcPrefs.showCommandLine = !jcPrefs.showCommandLine;
    showCmdLine(jcPrefs.showCommandLine);
}//GEN-LAST:event_jCheckBoxMenuItemCommandLineActionPerformed

private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    setPreferredSize(getSize());
    hideSplitterMenu();
    switch (getWindowState()) {
        case Maximized:
            break;
        case MaximizedHorizontal:
            jcPrefs.windowSize.height = getSize().height;
            jcPrefs.location.y = getLocation().y;
            break;
        case MaximizedVertical:
            jcPrefs.windowSize.width = getSize().width;
            jcPrefs.location.x = getLocation().x;
            break;
        default:
            jcPrefs.windowSize.setSize(getSize());
            break;
    }
}//GEN-LAST:event_formComponentResized

private void jButtonEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditorActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    BaseFile file = FileHelper.getRealFile(jcPrefs.externEditor);
    if (null != file) {
        fileChooser.setCurrentDirectory((java.io.File) file.getAbsoluteParent());
    }

    if (JFileChooser.APPROVE_OPTION == fileChooser.showDialog(jDialogPreferences, lb.getString("StrChoose"))) {
        System.out.println(fileChooser.getSelectedFile().exists());
        jTextFieldEditor.setText(fileChooser.getSelectedFile().getPath());
    }

}//GEN-LAST:event_jButtonEditorActionPerformed

private void jCheckBoxMenuItemHiddenFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemHiddenFilesActionPerformed
    jcPrefs.showHiddenFiles = !jcPrefs.showHiddenFiles;
    showHiddenFiles(jcPrefs.showHiddenFiles);
}//GEN-LAST:event_jCheckBoxMenuItemHiddenFilesActionPerformed

private void jCheckBoxEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxEditorActionPerformed
    jButtonEditor.setEnabled(jCheckBoxEditor.isSelected());
    jTextFieldEditor.setEnabled(jCheckBoxEditor.isSelected());
}//GEN-LAST:event_jCheckBoxEditorActionPerformed

private void jMenuItemDecodeHexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDecodeHexActionPerformed
    ACTION_DECODE_HEX.execute();
}//GEN-LAST:event_jMenuItemDecodeHexActionPerformed

private void jTextArea1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTextArea1ComponentShown
    jTextArea1.append(getActiveTable().getCurrentDir().getAbsolutePath() + ">");
    jTextArea1.append(System.getProperty("line.separator"));

}//GEN-LAST:event_jTextArea1ComponentShown

private void jTabbedPaneLeftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneLeftMouseClicked
    setActiveTable(true);
    getTabbedPane(true).updateUI();
    if (MouseEvent.BUTTON2 == evt.getButton()) {
        ACTION_REMOVE_TAB.execute();
    }
}//GEN-LAST:event_jTabbedPaneLeftMouseClicked

private void jTabbedPaneRightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneRightMouseClicked
    setActiveTable(false);
    getTabbedPane(false).updateUI();
    if (MouseEvent.BUTTON2 == evt.getButton()) {
        ACTION_REMOVE_TAB.execute();
    }
}//GEN-LAST:event_jTabbedPaneRightMouseClicked

private void jButtonFavorities1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonFavorities1MouseClicked
    jPopupMenuFavorites.show(evt.getComponent(), evt.getX(), evt.getY());
    setActiveTable(true);
}//GEN-LAST:event_jButtonFavorities1MouseClicked

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    onExit();
}//GEN-LAST:event_formWindowClosing

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    jSplitPane1.setDividerLocation(jcPrefs.dividerLocation);
    hideSplitterMenu();
}//GEN-LAST:event_formWindowOpened

private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
    switch (getWindowState()) {
        case Maximized:
            break;
        case MaximizedHorizontal:
            jcPrefs.location.y = getLocation().y;
            break;
        case MaximizedVertical:
            jcPrefs.location.x = getLocation().x;
            break;
        default:
            jcPrefs.location.setLocation(getLocation());
            break;
    }
}//GEN-LAST:event_formComponentMoved

private void jCheckBoxMenuItemArrangementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemArrangementActionPerformed
    ACTION_CHANGE_ARRANGEMENT.execute();
}//GEN-LAST:event_jCheckBoxMenuItemArrangementActionPerformed

private void jComboBoxRightPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxRightPopupMenuWillBecomeVisible
    BaseFile[] roots = RootFileSystem.getRoots();
    rightComboBoxIndex = jComboBoxRight.getSelectedIndex();
    Object obj = jComboBoxRight.getSelectedItem();
    initDriveComboBox(jComboBoxRight, roots);
    jComboBoxRight.setSelectedItem(obj);
}//GEN-LAST:event_jComboBoxRightPopupMenuWillBecomeVisible

private void jComboBoxRightPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxRightPopupMenuWillBecomeInvisible
    if (rightComboBoxIndex == jComboBoxRight.getSelectedIndex()) {
        currentRightTable.requestFocus();
        return;
    }
    BaseFile root = FileHelper.getRealFile(jComboBoxRight.getSelectedItem().toString());
    if (false == root.exists()) {
        if ((false == root.canRead())) {
            WarningDialog.showMessage(this, lb.getString("StrNoRes"), lb.getString("StrError"), WarningDialog.MESSAGE_ERROR);
            updatePanel(false);
            requestFocus();
            return;
        }
    }

    rightComboBoxIndex = jComboBoxRight.getSelectedIndex();
    currentRightTable.setCurrentDir(root);
    currentRightTable.setCurrentPosition(0);
    updatePanel(false);
    currentRightTable.setActive(true);
    currentLeftTable.setActive(false);
    currentRightTable.requestFocus();
    currentRightTable.clearSelectedList();
}//GEN-LAST:event_jComboBoxRightPopupMenuWillBecomeInvisible

private void jComboBoxRightPopupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxRightPopupMenuCanceled
    jComboBoxRight.setSelectedIndex(rightComboBoxIndex);
    getActiveTable().requestFocus();
}//GEN-LAST:event_jComboBoxRightPopupMenuCanceled

private void jComboBoxLeftPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxLeftPopupMenuWillBecomeVisible
    BaseFile[] roots = RootFileSystem.getRoots();
    leftComboBoxIndex = jComboBoxLeft.getSelectedIndex();
    Object obj = jComboBoxLeft.getSelectedItem();
    initDriveComboBox(jComboBoxLeft, roots);

    jComboBoxLeft.setSelectedItem(obj);
}//GEN-LAST:event_jComboBoxLeftPopupMenuWillBecomeVisible

private void jComboBoxLeftPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxLeftPopupMenuWillBecomeInvisible
    if (leftComboBoxIndex == jComboBoxLeft.getSelectedIndex()) {
        currentLeftTable.requestFocus();
        return;
    }
    BaseFile root = FileHelper.getRealFile(jComboBoxLeft.getSelectedItem().toString());
    if (false == root.exists()) {
        if ((false == root.canRead())) {
            WarningDialog.showMessage(this, lb.getString("StrNoRes"), lb.getString("StrError"), WarningDialog.MESSAGE_ERROR);
            updatePanel(true);
            requestFocus();
            return;
        }
    }

    leftComboBoxIndex = jComboBoxLeft.getSelectedIndex();
    currentLeftTable.setCurrentDir(root);
    currentLeftTable.setCurrentPosition(0);
    updatePanel(true);
    currentLeftTable.setActive(true);
    currentRightTable.setActive(false);
    currentLeftTable.requestFocus();
    currentLeftTable.clearSelectedList();
}//GEN-LAST:event_jComboBoxLeftPopupMenuWillBecomeInvisible

private void jComboBoxLeftPopupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxLeftPopupMenuCanceled
    jComboBoxLeft.setSelectedIndex(leftComboBoxIndex);
    getActiveTable().requestFocus();
}//GEN-LAST:event_jComboBoxLeftPopupMenuCanceled

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        currentLeftTable.refreshTableIfNeeded();
        currentRightTable.refreshTableIfNeeded();
    }//GEN-LAST:event_formWindowActivated
// </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc=" Variables declaration ">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonCopy;
    private javax.swing.JButton jButtonCreateDir;
    private javax.swing.JButton jButtonDel;
    private javax.swing.JButton jButtonDeleteFavorite;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JButton jButtonEditor;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonFavorities1;
    private javax.swing.JButton jButtonFavorities2;
    private javax.swing.JButton jButtonHideDeleteFavoriteDialog;
    private javax.swing.JButton jButtonMove;
    private javax.swing.JButton jButtonPack;
    private javax.swing.JButton jButtonPrefApply;
    private javax.swing.JButton jButtonPrefCancel;
    private javax.swing.JButton jButtonPrefOk;
    private javax.swing.JButton jButtonView;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBoxEditor;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemArrangement;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemButtonBar;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemCommandLine;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemHiddenFiles;
    private javax.swing.JCheckBox jCheckBoxPrefShowButtons;
    private javax.swing.JCheckBox jCheckBoxPrefShowCmdline;
    private javax.swing.JCheckBox jCheckBoxPrefShowHidden;
    private javax.swing.JCheckBox jCheckBoxPrefShowTooltips;
    private javax.swing.JCheckBox jCheckBoxPrefsUseSystemIcons;
    private javax.swing.JComboBox jComboBoxCharset;
    private javax.swing.JComboBox jComboBoxLang;
    private javax.swing.JComboBox jComboBoxLeft;
    private javax.swing.JComboBox jComboBoxRight;
    private javax.swing.JComboBox jComboBoxTheme;
    private javax.swing.JDialog jDialogAbout;
    private javax.swing.JDialog jDialogDeleteFavorites;
    private javax.swing.JDialog jDialogFileProperties;
    private javax.swing.JDialog jDialogHotKeys;
    private javax.swing.JDialog jDialogPreferences;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelCopyright;
    private javax.swing.JLabel jLabelCurrentVersion;
    private javax.swing.JLabel jLabelEditor;
    private javax.swing.JLabel jLabelMail;
    private javax.swing.JLabel jLabelPage;
    private javax.swing.JLabel jLabelPrefCharset;
    private javax.swing.JLabel jLabelPrefLang;
    private javax.swing.JLabel jLabelStatusLeft;
    private javax.swing.JLabel jLabelStatusRight;
    private javax.swing.JLabel jLabelTheme;
    private javax.swing.JList jListFavorites;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFavorites;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemAddTab;
    private javax.swing.JMenuItem jMenuItemDecodeHex;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemFind;
    private javax.swing.JMenuItem jMenuItemHotKeys;
    private javax.swing.JMenuItem jMenuItemPreferences;
    private javax.swing.JMenuItem jMenuItemRemoveTab;
    private javax.swing.JMenu jMenuView;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelCenterLeft;
    private javax.swing.JPanel jPanelCenterRight;
    private javax.swing.JPanel jPanelEditor;
    private javax.swing.JPanel jPanelPrefConsole;
    private javax.swing.JPanel jPanelPrefTheme;
    private javax.swing.JPanel jPanelPrefView;
    private javax.swing.JPanel jPanelSearchMsgLeft;
    private javax.swing.JPanel jPanelSearchMsgRight;
    private javax.swing.JPanel jPanelStatusLeft;
    private javax.swing.JPanel jPanelStatusMsgLeft;
    private javax.swing.JPanel jPanelStatusMsgRight;
    private javax.swing.JPanel jPanelStatusRight;
    private javax.swing.JPanel jPanelToolBar;
    private javax.swing.JPopupMenu jPopupMenuFavorites;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPaneHelpHK;
    private javax.swing.JScrollPane jScrollPanePrefHK;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPaneLeft;
    private javax.swing.JTabbedPane jTabbedPanePrefs;
    private javax.swing.JTabbedPane jTabbedPaneRight;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextAreaHotKeysHelp;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextFieldEditor;
    private javax.swing.JTextField jTextFieldSearchLeft;
    private javax.swing.JTextField jTextFieldSearchRight;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
// </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc=" MainFrame variables  ">
    private FileTable currentLeftTable;
    private FileTable currentRightTable;
    private FileTablePanel leftFilePanel;
    private FileTablePanel rightFilePanel;
    private JTable jTableHotKeys;
    private String userFile = "./user.dat";
    private String favoritesFile = "./favorites.dat";
    private int leftComboBoxIndex;
    private int rightComboBoxIndex;
    private String mainTitle;
    private String mainVersion;
    private Process cmdProcess;
    private static LinkedHashMap defaultKeyList;
    private static LinkedHashMap userKeyList;
    private static final String JCOM_COPYRIGHT = "2005-2011 (c) kneeMade, Inc";
    private static final String JCOM_MAIL = "java.commander@gmail.com";
    private static final String JCOM_PAGE = "http://jcommander.narod.ru";
    private static Charset consoleCharset;
    private ArrayList commandList = new ArrayList();
    private int commandListPosition;
    private NewSearchThread searchThread;
    JCPreferenses jcPrefs = JCPreferenses.getJCPreferenses();
    private ArrayList<BookmarkItem> favoriteFolders = new ArrayList<BookmarkItem>();
    private LocaleWrapper currentLocaleWrapper = null;
    LanguageBundle lb = LanguageBundle.getInstance();
    JPopupMenu splitterPopupMenu;
    SearchDialog searchDialog = new SearchDialog(this, false);

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Inner classes  ">
    private class Streamer extends Thread {

        private Reader reader;

        public Streamer(Reader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            try {
                char[] buffer = new char[1024];

                int read;

                while (((read = reader.read(buffer)) != -1)) {
                    jTextArea1.append(new String(buffer, 0, read));
                    jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
                }
            } catch (java.io.IOException e) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private class ProcessStreamer extends Thread {

        Process process;

        public ProcessStreamer(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            Streamer streamer;
            Streamer errorStream;
            try {
                streamer = new Streamer(
                        new InputStreamReader(cmdProcess.getInputStream(), consoleCharset));
                errorStream = new Streamer(
                        new InputStreamReader(cmdProcess.getErrorStream(), consoleCharset));
            } catch (Exception e) {
                streamer = new Streamer(new InputStreamReader(cmdProcess.getInputStream()));
                errorStream = new Streamer(new InputStreamReader(cmdProcess.getErrorStream()));
            }
            streamer.start();
            errorStream.start();
            while (streamer.isAlive() && errorStream.isAlive()) {
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            jTextArea1.append(getActiveTable().getCurrentDir().getAbsolutePath() + ">");
        }
    }

    private class BookmarkItem {

        protected String itemName;
        protected BaseFile file;
        protected Icon icon;

        public BookmarkItem(BaseFile file) {
            this(file.toString(), file);
        }

        public BookmarkItem(String itemName, BaseFile file) {
            this.itemName = itemName;
            this.file = file;
            this.icon = ImageArchive.getImageFolder(file, jcPrefs.useSystemIcons);
        }

        public BaseFile getFile() {
            return file;
        }

        public String getItemName() {
            return itemName;
        }

        public Icon getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            return itemName;// + " ==> " + file.getAbsolutePath();
        }
    }

    private class SystemBookmarkItem extends BookmarkItem {

        public SystemBookmarkItem(BaseFile file) {
            super(file);
        }
    }

    private class SearchKeyListener implements KeyListener {

        private MainFrame parent;

        public SearchKeyListener(MainFrame parent) {
            this.parent = parent;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            FileTable table = parent.getActiveTable();
            JTextField searchField = parent.getActiveSearchLabel();
            if (KeyEvent.VK_ESCAPE == e.getKeyChar()) {
                parent.setSearchVisible(false);
                return;
            }

            Message m = MainFrame.findMessage(e);
            MessageList newMessageList = MessageList.defaultMessageList;
            if ((null != m) && ((m.equals(newMessageList.MESSAGE_ENTER))
                    || (m.equals(newMessageList.MESSAGE_UP))
                    || (m.equals(newMessageList.MESSAGE_DOWN)))) {
                table.requestFocus();
                table.getKeyListeners()[0].keyPressed(e);
                return;
            }
            if (KeyEvent.CHAR_UNDEFINED == e.getKeyChar()) {
                return;
            }
            String searchText = searchField.getText() + e.getKeyChar();
            int position = table.getElementPositionByString(searchText);
            if (-1 != position) {
                table.setCurrentPosition(position);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private class AddFavoritesListener implements ActionListener {

        private MainFrame parent;

        public AddFavoritesListener(MainFrame parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            BaseFile af = getActiveTable().getCurrentDir();
            String result = InputDialog.showInputDialog(parent, lb.getString("StrAddBookmark"), af.toString());
            if ((null == result) || (result.trim().equals(""))) {
                return;
            }
            BookmarkItem fi = new BookmarkItem(result, af);
            if (favoriteFolders.contains(fi)) {
            } else {
                favoriteFolders.add(fi);
                JMenuItem item = new JMenuItem(fi.getItemName());
                item.addActionListener(new SelectFavoritesListener(fi.getFile()));
                jPopupMenuFavorites.add(item);
                item = new JMenuItem(fi.getItemName());
                item.addActionListener(new SelectFavoritesListener(fi.getFile()));
                jMenuFavorites.add(item);
            }
        }
    }

    private class SelectFavoritesListener implements ActionListener {

        private BaseFile af;

        public SelectFavoritesListener(BaseFile af) {
            this.af = af;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            getActiveTable().getFileList().setFileList(af);
            getActiveTable().setCurrentPosition(0);
            updateActivePanel();
            getActiveTable().requestFocus();
        }
    }

    private class DeleteFavoritesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            showDeleteFavoritesDialog();
        }
    }

    public class HotKeysTableRenderer extends DefaultTableCellRenderer {

        public HotKeysTableRenderer() {
            super();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value instanceof Message) {
                if (!((Message) value).isAction()) {
                    Font f = new Font(getFont().getName(), Font.BOLD, getFont().getSize());
                    setFont(f);
                } else {
                }
            }
            return c;
        }
    }

    class ComboCellRenderer implements ListCellRenderer {

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            if (value instanceof BaseFile) {
                BaseFile file = (BaseFile) value;
                renderer.setIcon(ImageArchive.getImageFolder(file, true));
            }
            return renderer;
        }
    }

    static class ParseHelper {

        public static String[] parsePath(String paths, String defaultPath) {
            ArrayList<String> list = new ArrayList<String>();
            for (StringTokenizer stringTokenizer = new StringTokenizer(paths, JCPreferenses.PRIMARY_DELIMITER); stringTokenizer.hasMoreTokens();) {
                String token = stringTokenizer.nextToken();
                list.add(token);
            }
            return list.isEmpty() ? new String[]{defaultPath} : list.toArray(new String[list.size()]);
        }

        public static ArrayList<int[]> parseHeaderSize(String headerSize, int defaultSize, int length) {
            ArrayList<int[]> list = new ArrayList<int[]>();

            for (StringTokenizer stringTokenizer = new StringTokenizer(headerSize, JCPreferenses.PRIMARY_DELIMITER); stringTokenizer.hasMoreTokens();) {
                String token = stringTokenizer.nextToken();
                StringTokenizer st2 = new StringTokenizer(token, JCPreferenses.SECONDARY_DELIMITER);
                int tokenCount = 0;
                int[] sizes = new int[TableHeader.TITLE.length];
                for (int i = 0; i < sizes.length; i++) {
                    sizes[i] = st2.hasMoreTokens() ? parseInt(st2.nextToken(), defaultSize) : defaultSize;
                }
                list.add(sizes);
            }
            int count = list.size();
            if (count < length) {
                for (int i = count; i < length; i++) {
                    int[] sizes = new int[TableHeader.TITLE.length];
                    for (int j = 0; j < sizes.length; j++) {
                        sizes[j] = defaultSize;
                    }
                    list.add(sizes);
                }
            }
            return list;
        }

        public static int parseInt(String str, int value) {
            int v = value;
            try {
                v = Integer.parseInt(str);
            } catch (Exception e) {
            }
            return v;
        }

        public static Directive[] parseSortingColumns(String directives, Directive defaultDirective, int length) {
            Directive[] ds = new Directive[length];
            int count = 0;
            for (StringTokenizer stringTokenizer = new StringTokenizer(directives, JCPreferenses.PRIMARY_DELIMITER); stringTokenizer.hasMoreTokens();) {
                String token = stringTokenizer.nextToken();
                StringTokenizer st2 = new StringTokenizer(token, JCPreferenses.SECONDARY_DELIMITER);
                String col = st2.hasMoreTokens() ? st2.nextToken() : "1";
                String dir = st2.hasMoreTokens() ? st2.nextToken() : "0";

                Directive d = new Directive(parseInt(col, 0), parseInt(dir, 0));
                ds[count++] = d;
                if (count == length) {
                    break;
                }
            }
            if (count < length) {
                for (int i = count; i < length; i++) {
                    ds[count] = defaultDirective;
                }
            }
            return ds;
        }
    }
// </editor-fold>
}

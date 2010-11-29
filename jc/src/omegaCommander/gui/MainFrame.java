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
 * Created on 26 Апрель 2005 г., 9:27
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
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import omegaCommander.actions.*;
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
import omegaCommander.prefs.PrefKeys;
import omegaCommander.gui.table.TablePrefKeys;
import omegaCommander.gui.table.Directive;
import omegaCommander.gui.table.FileTable;
import omegaCommander.gui.table.FileTablePanel;
import omegaCommander.gui.table.FileTableSorter;
import omegaCommander.gui.table.dragdrop.FileTransferHandler;
import omegaCommander.gui.table.tableHeader.TableHeader;
import omegaCommander.prefs.JCPreferenses;
import omegaCommander.threads.newThreads.NewSearchThread;
import omegaCommander.threads.newThreads.SearchStatusThread;
import omegaCommander.util.LanguageBundle;

/**
 * Класс наследован от JFrame и описывает основное окно 
 * приложения
 * @author Pavel Ovcharov
 * @version 2005/04/26 9:27
 */
public class MainFrame extends javax.swing.JFrame implements PrefKeys, TablePrefKeys {

    public static final String DEFAULT_HANDLER_TEXT;// = "rundll32 url.dll,FileProtocolHandler";
    private Preferences pref = null;
    // <editor-fold defaultstate="collapsed" desc=" MainFrame Actions ">
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
    public final omegaCommander.actions.Action ACTION_EXPLORER = new ActionExplorer(this);
    // </editor-fold>

    static {
        if (System.getProperty("os.name").startsWith("Windows")) {
            DEFAULT_HANDLER_TEXT = "rundll32 url.dll,FileProtocolHandler";
        } else {
            DEFAULT_HANDLER_TEXT = "";
        }
    }

    /**
     * Создает новый объект класса MainFrame
     * @param version версия приложения
     * @param title заголовок окна
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

        /**********init comboboxes**********/
        setDriveComboBox(jComboBoxLeft, RootFileSystem.getRoots());
        setDriveComboBox(jComboBoxRight, RootFileSystem.getRoots());
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
        setSearchDialog();
        setDeleteFavoritesDialog();
        setPreferencesDialog();

        jComboBoxLeft.addKeyListener(new BaseComboBoxListener(jComboBoxLeft, defaultKeyList, userKeyList));
        jComboBoxRight.addKeyListener(new BaseComboBoxListener(jComboBoxRight, defaultKeyList, userKeyList));
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

            Locale defaultLocale = Locale.getDefault();
            currentLocale = new Locale(pref.get(PK_LOCALE, defaultLocale.getLanguage() + "_" + defaultLocale.getCountry()));

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
            jCheckBoxCase.setText(lb.getString("StrCase"));
            jButtonFind.setText(lb.getString("StrSearch"));
            jButtonCancelFind.setText(lb.getString("StrCancel"));
            jLabelSearchStatus.setText(lb.getString("StrReady"));
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

            jDialogFind.setTitle(lb.getString("StrSearch"));
            jDialogAbout.setTitle(mainTitle);
            jDialogDeleteFavorites.setTitle(lb.getString("StrReady"));

            ((TitledBorder) (jPanelPrefView.getBorder())).setTitle(lb.getString("StrPrefView"));
            ((TitledBorder) (jPanelPrefTheme.getBorder())).setTitle(lb.getString("StrPrefTheme"));
            jLabelTheme.setText(lb.getString("StrTheme"));
            jCheckBoxPrefShowButtons.setText(lb.getString("StrShowButtons"));
            jCheckBoxPrefShowCmdline.setText(lb.getString("StrShowCommandLine"));
            jCheckBoxPrefShowHidden.setText(lb.getString("StrShowHiddenFiles"));
            jCheckBoxPrefShowTooltips.setText(lb.getString("StrShowTooltips"));

            jLabelFindWhat.setText(lb.getString("StrFindWhat"));
            jLabelFindWhere.setText(lb.getString("StrFindWhere"));
            jCheckBoxFindText.setText(lb.getString("StrFindText"));

            initMessageList();

            jTableHotKeys = createHotKeysTable();
            jTableHotKeys.addKeyListener(new HKTableListener(this));
            jScrollPanePrefHK.setViewportView(jTableHotKeys);

            setHotKeysDialog();
            initFavorites();

            updateStatusLabel(false);
            updateStatusLabel(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateUI(String lnf) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        try {
            UIManager.setLookAndFeel(lnf);
        } catch (Exception e) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
        }
        SwingUtilities.updateComponentTreeUI(this);
        SwingUtilities.updateComponentTreeUI(jDialogAbout);
        SwingUtilities.updateComponentTreeUI(jDialogDeleteFavorites);
        SwingUtilities.updateComponentTreeUI(jDialogFind);
        SwingUtilities.updateComponentTreeUI(jDialogHotKeys);
        SwingUtilities.updateComponentTreeUI(jDialogPreferences);
        SwingUtilities.updateComponentTreeUI(jPopupMenuFavorites);
        jMenuBar2.updateUI();

        leftFilePanel.setInputMap();
        rightFilePanel.setInputMap();


        InputMap imP = jSplitPane1.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).getParent();
        imP.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
    }

    private void closeFindDialog() {
        jDialogFind.setVisible(false);
        stopSearchThread();
    }

    private void findDialogKeyPressed(KeyEvent evt) {
        if (KeyEvent.VK_ESCAPE == evt.getKeyCode()) {
            jDialogFind.setVisible(false);
        }
        Message msg = findMessage(evt);
        if (null == msg) {
            return;
        }
        if (MessageList.MSG_ENTER == msg.getMessageID()) {
            jButtonFind.doClick();
        }
        return;
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

    private void moveToFile(BaseFile af) {
        if (af == null) {
            return;
        }
        FileTable table = getActiveTable();
        table.setFileList(new FileSystemList(af));
        updateActivePanel();
        table.setCurrentPosition(table.getFilePosition(af));
    }

    private void savePrefs() {
        pref.putInt(PK_HEIGHT, jcPrefs.windowSize.height);
        pref.putInt(PK_WIDTH, jcPrefs.windowSize.width);
        pref.putInt(PK_DIVIDER_LOCATION, jSplitPane1.getDividerLocation());
        pref.putInt(PK_WINDOW_STATE, getExtendedState());
        pref.put(PK_LEFT_DIR, getTable(true).getCurrentDir().getAbsolutePath());
        pref.put(PK_RIGHT_DIR, getTable(false).getCurrentDir().getAbsolutePath());
        pref.putInt(PK_LOCATION_X, jcPrefs.location.x);
        pref.putInt(PK_LOCATION_Y, jcPrefs.location.y);
        if (currentLeftTable.isActive()) {
            pref.putInt(PK_ACTIVE_TABLE, 0);
        } else {
            pref.putInt(PK_ACTIVE_TABLE, 1);
        }

        int sizes[] = currentLeftTable.getHeaderSizes();
        for (int i = 0; i < TableHeader.TITLE.length; i++) {
            pref.putInt(TPK_LEFT[i], sizes[i]);
        }
        sizes = currentRightTable.getHeaderSizes();
        for (int i = 0; i < TableHeader.TITLE.length; i++) {
            pref.putInt(TPK_RIGHT[i], sizes[i]);
        }

        ArrayList list = currentLeftTable.getSortingColumns();
        if (0 < list.size()) {
            Directive d = (Directive) list.get(0);
            pref.putInt(TPK_LEFT_SORTER, d.getColumn());
            pref.putInt(TPK_LEFT_SORT_DIRECTION, d.getDirection());
        }

        list = currentRightTable.getSortingColumns();
        if (0 < list.size()) {
            Directive d = (Directive) list.get(0);
            pref.putInt(TPK_RIGHT_SORTER, d.getColumn());
            pref.putInt(TPK_RIGHT_SORT_DIRECTION, d.getDirection());
        }
        pref.put(PK_LOCALE, currentLocale.toString());

        pref.put(PK_CONSOLE_CHARSET, consoleCharset.displayName());
        pref.putBoolean(PK_SHOW_BUTTONS, jPanel7.isVisible());
        pref.putBoolean(PK_SHOW_COMMAND_LINE, jTextField3.isVisible());
        pref.putBoolean(PK_SHOW_HIDDEN_FILES, jCheckBoxMenuItemHiddenFiles.isSelected());

        pref.put(PK_EXTERNAL_EDITOR, jcPrefs.externEditor);
        pref.putBoolean(PK_USE_EXTERNAL_EDITOR, jcPrefs.useExternEditor);

        pref.put(PK_THEME, jcPrefs.theme);

        pref.putBoolean(PK_SHOW_TOOLTIPS, jcPrefs.showToolTips);
        pref.putBoolean(PK_ARRANGEMENT, jcPrefs.arrangement);
        try {
            pref.exportNode(new FileOutputStream(new java.io.File("jc.xml")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void loadPrefs() {

        try {
            Preferences.importPreferences(new FileInputStream(new java.io.File("jc.xml")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == pref) {
                pref = Preferences.userNodeForPackage(this.getClass());
            }
        }

        jcPrefs.windowSize.width = pref.getInt(PK_WIDTH, jcPrefs.DEFAULT_WINDOW_SIZE.width);
        jcPrefs.windowSize.height = pref.getInt(PK_HEIGHT, jcPrefs.DEFAULT_WINDOW_SIZE.height);
        jcPrefs.location.x = pref.getInt(PK_LOCATION_X, jcPrefs.DEFAULT_LOCATION.x);
        jcPrefs.location.y = pref.getInt(PK_LOCATION_Y, jcPrefs.DEFAULT_LOCATION.y);
        jcPrefs.extendedState = pref.getInt(PK_WINDOW_STATE, jcPrefs.DEFAULT_STATE);
        jcPrefs.dividerLocation = pref.getInt(PK_DIVIDER_LOCATION, jcPrefs.DEFAULT_DIVIDER_LOCATION);
        jcPrefs.leftPanelPath = pref.get(PK_LEFT_DIR, jcPrefs.DEFAULT_LEFT_PANEL_PATH);
        jcPrefs.rightPanelPath = pref.get(PK_RIGHT_DIR, jcPrefs.DEFAULT_RIGHT_PANEL_PATH);
        jcPrefs.activeTable = pref.getInt(PK_ACTIVE_TABLE, jcPrefs.DEFAULT_ACTIVE_TABLE);
        for (int i = 0; i < TableHeader.TITLE.length; i++) {
            jcPrefs.leftSizes[i] = pref.getInt(TPK_LEFT[i], jcPrefs.DEFAULT_HEADER_SIZE);
        }
        for (int i = 0; i < TableHeader.TITLE.length; i++) {
            jcPrefs.rightSizes[i] = pref.getInt(TPK_RIGHT[i], jcPrefs.DEFAULT_HEADER_SIZE);
        }

        int column = pref.getInt(TPK_LEFT_SORTER, FileTable.NAME);
        int direction = pref.getInt(TPK_LEFT_SORT_DIRECTION, FileTableSorter.ASCENDING);
        jcPrefs.leftSortingColumns.add(new Directive(column, direction));


        column = pref.getInt(TPK_RIGHT_SORTER, FileTable.NAME);
        direction = pref.getInt(TPK_RIGHT_SORT_DIRECTION, FileTableSorter.ASCENDING);
        jcPrefs.rightSortingColumns.add(new Directive(column, direction));

        jcPrefs.consoleCharset = pref.get(PK_CONSOLE_CHARSET, "");
        jcPrefs.showButtons = pref.getBoolean(PK_SHOW_BUTTONS, jcPrefs.DEFAULT_SHOW_BUTTONS);
        jcPrefs.showCommandLine = pref.getBoolean(PK_SHOW_COMMAND_LINE, jcPrefs.DEFAULT_SHOW_COMMAND_LINE);
        jcPrefs.showHiddenFiles = pref.getBoolean(PK_SHOW_HIDDEN_FILES, jcPrefs.showHiddenFiles);

        jcPrefs.useExternEditor = pref.getBoolean(PK_USE_EXTERNAL_EDITOR, jcPrefs.DEFAULT_USE_EXTERN_EDITOR);
        jcPrefs.externEditor = pref.get(PK_EXTERNAL_EDITOR, jcPrefs.DEFAULT_EXTERN_EDITOR);

        jcPrefs.theme = pref.get(PK_THEME, jcPrefs.DEFAULT_THEME);
        jcPrefs.showToolTips = pref.getBoolean(PK_SHOW_TOOLTIPS, jcPrefs.DEFAULT_SHOW_TOOLTIPS);
        jcPrefs.arrangement = pref.getBoolean(PK_ARRANGEMENT, jcPrefs.DEFAULT_ARRANGEMENT);
    }

    public void applyPrefs() {

        setSize(jcPrefs.windowSize);
        setLocation(jcPrefs.location);
        setExtendedState(jcPrefs.extendedState);
        jSplitPane1.setDividerLocation(jcPrefs.dividerLocation);

        //XXX createMessages tabs
        BaseFile file = FileHelper.getRealFile(jcPrefs.leftPanelPath);
        currentLeftTable.setFileList(file);
        file = FileHelper.getRealFile(jcPrefs.rightPanelPath);
        currentRightTable.setFileList(file);

        if (0 == jcPrefs.activeTable) {
            setActiveTable(true);
            currentLeftTable.requestFocus();
        } else {
            setActiveTable(false);
            currentRightTable.requestFocus();
        }
        currentLeftTable.setHeaderSizes(jcPrefs.leftSizes);
        currentRightTable.setHeaderSizes(jcPrefs.rightSizes);

        ArrayList list = currentLeftTable.getSortingColumns();
        list.clear();
        list.addAll(jcPrefs.leftSortingColumns);
        list = currentRightTable.getSortingColumns();
        list.clear();
        list.addAll(jcPrefs.rightSortingColumns);

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
        currentLeftTable.setHiddenFilesVisibility(jcPrefs.showHiddenFiles);
        currentRightTable.setHiddenFilesVisibility(jcPrefs.showHiddenFiles);
        currentLeftTable.refreshTable();
        currentRightTable.refreshTable();
        currentLeftTable.showToolTip(jcPrefs.showToolTips);
        currentRightTable.showToolTip(jcPrefs.showToolTips);

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
            ex.printStackTrace();
        }

        initMessageList();

        jcPrefs.useExternEditor = jCheckBoxEditor.isSelected();
        jcPrefs.externEditor = jTextFieldEditor.getText();
        jcPrefs.showButtons = jCheckBoxPrefShowButtons.isSelected();
        jcPrefs.showCommandLine = jCheckBoxPrefShowCmdline.isSelected();
        jcPrefs.showHiddenFiles = jCheckBoxPrefShowHidden.isSelected();
        jcPrefs.showToolTips = jCheckBoxPrefShowTooltips.isSelected();

        Locale l = ((Locale) jComboBoxLang.getSelectedItem());
        if (false == l.equals(currentLocale)) {
            currentLocale = l;
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
        MessageList messageListList = MessageList.ensureMessageList();

        messageListList.MESSAGE_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_TAB));
        messageListList.MESSAGE_ACTIVE_LEFT.setDefaultKey(new KeyShortcat(KeyEvent.VK_LEFT, true, false, false));
        messageListList.MESSAGE_ACTIVE_RIGHT.setDefaultKey(new KeyShortcat(KeyEvent.VK_RIGHT, true, false, false));
        messageListList.MESSAGE_ENTER.setDefaultKey(new KeyShortcat(KeyEvent.VK_ENTER));
        messageListList.MESSAGE_DIR_UP.setDefaultKey(new KeyShortcat(KeyEvent.VK_BACK_SPACE));
        messageListList.MESSAGE_VIEW.setDefaultKey(new KeyShortcat(KeyEvent.VK_F3));
        messageListList.MESSAGE_EDIT.setDefaultKey(new KeyShortcat(KeyEvent.VK_F4));
        messageListList.MESSAGE_NEW_EDIT.setDefaultKey(new KeyShortcat(KeyEvent.VK_F4, false, false, true));
        messageListList.MESSAGE_NEW_DIR.setDefaultKey(new KeyShortcat(KeyEvent.VK_F7));
        messageListList.MESSAGE_COPY.setDefaultKey(new KeyShortcat(KeyEvent.VK_F5));
        messageListList.MESSAGE_MOVE.setDefaultKey(new KeyShortcat(KeyEvent.VK_F6));
        messageListList.MESSAGE_RENAME.setDefaultKey(new KeyShortcat(KeyEvent.VK_F2));
        messageListList.MESSAGE_DELETE.setDefaultKey(new KeyShortcat(KeyEvent.VK_F8));
        messageListList.MESSAGE_PACK.setDefaultKey(new KeyShortcat(KeyEvent.VK_F9));
        messageListList.MESSAGE_EXIT.setDefaultKey(new KeyShortcat(KeyEvent.VK_F4, true, false, false));
        messageListList.MESSAGE_SHOW_LEFT_COMBOBOX.setDefaultKey(new KeyShortcat(KeyEvent.VK_F1, true, false, false));
        messageListList.MESSAGE_SHOW_RIGHT_COMBOBOX.setDefaultKey(new KeyShortcat(KeyEvent.VK_F2, true, false, false));
        messageListList.MESSAGE_REFRESH.setDefaultKey(new KeyShortcat(KeyEvent.VK_R, false, true, false));
        messageListList.MESSAGE_SYNC.setDefaultKey(new KeyShortcat(KeyEvent.VK_O, true, false, false));
        messageListList.MESSAGE_SWAP.setDefaultKey(new KeyShortcat(KeyEvent.VK_U, false, true, false));
        messageListList.MESSAGE_SELECT_ALL.setDefaultKey(new KeyShortcat(KeyEvent.VK_MULTIPLY));
        messageListList.MESSAGE_SELECT_N_DOWN.setDefaultKey(new KeyShortcat(KeyEvent.VK_INSERT));
        messageListList.MESSAGE_SELECT_N_UP.setDefaultKey(new KeyShortcat(KeyEvent.VK_UP, false, false, true));
        messageListList.MESSAGE_UP.setDefaultKey(new KeyShortcat(KeyEvent.VK_UP));
        messageListList.MESSAGE_DOWN.setDefaultKey(new KeyShortcat(KeyEvent.VK_DOWN));
        messageListList.MESSAGE_PG_UP.setDefaultKey(new KeyShortcat(KeyEvent.VK_PAGE_UP));
        messageListList.MESSAGE_PG_DOWN.setDefaultKey(new KeyShortcat(KeyEvent.VK_PAGE_DOWN));
        messageListList.MESSAGE_FIRST.setDefaultKey(new KeyShortcat(KeyEvent.VK_HOME));
        messageListList.MESSAGE_LAST.setDefaultKey(new KeyShortcat(KeyEvent.VK_END));
        messageListList.MESSAGE_QUICK_SEARCH.setDefaultKey(new KeyShortcat(KeyEvent.VK_S, false, true, false));
        messageListList.MESSAGE_FIND.setDefaultKey(new KeyShortcat(KeyEvent.VK_F, false, true, false));
        messageListList.MESSAGE_SHOW_PANELS.setDefaultKey(new KeyShortcat(KeyEvent.VK_O, false, true, false));
        messageListList.MESSAGE_SPACE.setDefaultKey(new KeyShortcat(KeyEvent.VK_SPACE));
        messageListList.MESSAGE_ADD_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_T, false, true, false));
        messageListList.MESSAGE_REMOVE_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_D, false, true, false));
        messageListList.MESSAGE_NEXT_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_N, false, true, false));
        messageListList.MESSAGE_PREV_TAB.setDefaultKey(new KeyShortcat(KeyEvent.VK_B, false, true, false));
        messageListList.MESSAGE_COPY_PATH.setDefaultKey(new KeyShortcat(KeyEvent.VK_ENTER, false, true, true));
        messageListList.MESSAGE_COPY_NAME.setDefaultKey(new KeyShortcat(KeyEvent.VK_ENTER, true, false, false));
        messageListList.MESSAGE_GOTO_CMDLINE.setDefaultKey(new KeyShortcat(KeyEvent.VK_G, false, true, false));
        messageListList.MESSAGE_DECODE_HEX.setDefaultKey(new KeyShortcat(KeyEvent.VK_H, false, true, false));


        for (Message message : messageListList.getMessages()) {
            defaultKeyList.put(message.getDefaultKey(), message);
        }


//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_TAB), newMessageList.MESSAGE_TAB);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_LEFT, true, false, false), newMessageList.MESSAGE_ACTIVE_LEFT);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_RIGHT, true, false, false), newMessageList.MESSAGE_ACTIVE_RIGHT);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_ENTER), newMessageList.MESSAGE_ENTER);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_BACK_SPACE), newMessageList.MESSAGE_DIR_UP);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F3), newMessageList.MESSAGE_VIEW);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F4), newMessageList.MESSAGE_EDIT);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F4, false, false, true), newMessageList.MESSAGE_NEW_EDIT);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F7), newMessageList.MESSAGE_NEW_DIR);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F5), newMessageList.MESSAGE_COPY);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F6), newMessageList.MESSAGE_MOVE);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F2), newMessageList.MESSAGE_RENAME);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F8), newMessageList.MESSAGE_DELETE);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F9), newMessageList.MESSAGE_PACK);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F4, true, false, false), newMessageList.MESSAGE_EXIT);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F1, true, false, false), newMessageList.MESSAGE_SHOW_LEFT_COMBOBOX);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F2, true, false, false), newMessageList.MESSAGE_SHOW_RIGHT_COMBOBOX);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_R, false, true, false), newMessageList.MESSAGE_REFRESH);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_O, true, false, false), newMessageList.MESSAGE_SYNC);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_U, false, true, false), newMessageList.MESSAGE_SWAP);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_MULTIPLY), newMessageList.MESSAGE_SELECT_ALL);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_INSERT), newMessageList.MESSAGE_SELECT_N_DOWN);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_UP, false, false, true), newMessageList.MESSAGE_SELECT_N_UP);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_UP), newMessageList.MESSAGE_UP);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_DOWN), newMessageList.MESSAGE_DOWN);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_PAGE_UP), newMessageList.MESSAGE_PG_UP);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_PAGE_DOWN), newMessageList.MESSAGE_PG_DOWN);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_HOME), newMessageList.MESSAGE_FIRST);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_END), newMessageList.MESSAGE_LAST);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_S, false, true, false), newMessageList.MESSAGE_QUICK_SEARCH);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_F, false, true, false), newMessageList.MESSAGE_FIND);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_O, false, true, false), newMessageList.MESSAGE_SHOW_PANELS);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_SPACE), newMessageList.MESSAGE_SPACE);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_T, false, true, false), newMessageList.MESSAGE_ADD_TAB);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_D, false, true, false), newMessageList.MESSAGE_REMOVE_TAB);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_N, false, true, false), newMessageList.MESSAGE_NEXT_TAB);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_B, false, true, false), newMessageList.MESSAGE_PREV_TAB);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_ENTER, false, true, true), newMessageList.MESSAGE_COPY_PATH);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_ENTER, true, false, false), newMessageList.MESSAGE_COPY_NAME);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_G, false, true, false), newMessageList.MESSAGE_GOTO_CMDLINE);
//        defaultKeyList.put(new KeyShortcat(KeyEvent.VK_H, false, true, false), newMessageList.MESSAGE_DECODE_HEX);

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile));
            int count = ois.readInt();
            for (int i = 0; i < count; i++) {
                String s = ois.readUTF();
                int code = ois.readInt();
                boolean alt = ois.readBoolean();
                boolean ctrl = ois.readBoolean();
                boolean shift = ois.readBoolean();


                Message message = messageListList.getMessage(s);
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
     * Обновить главное окно приложения
     */
    public void updateMainWindow() {
        updatePanel(true);
        updatePanel(false);
    }

    public JDialog getPreferencesDialog() {
        return jDialogPreferences;
    }

    public JDialog getFindDialog() {
        return jDialogFind;
    }

    /**
     * Обновить правую (левую) панель
     * @param left <B>true</B> для обновления левой панели, <B>false</B> - для обновления правой
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
     * Обновить активную панель приложения
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
     * В зависимости от переданного параметра возвращает левую или
     * правую таблицу
     * @param left <b>true</b> для получения левой таблицы, <b>false</b> -
     * для получения правой
     * @return объект класса FileTable
     */
    public FileTable getTable(boolean left) {
        return left ? currentLeftTable : currentRightTable;
    }

    /**
     * В зависимости от переданного параметра возвращает левую или
     * правую метку статуса
     * @param left <b>true</b> для получения левой метки, <b>false</b> -
     * для получения правой
     *
     * @return объект класса JLabel
     */
    public JLabel getStatusLabel(boolean left) {
        return left ? jLabelStatusLeft : jLabelStatusRight;
    }

    /**
     * В зависимости от переданного параметра возвращает левую или
     * правую метку пути
     * @param left <b>true</b> для получения левой метки, <b>false</b> -
     * для получения правой
     * @return объект класса JTextField
     */
    public JTextField getDirLabel(boolean left) {
        return left ? jTextField1 : jTextField2;
    }

    /**
     * Заносит в cb список разделов File roots[]
     * @param cb выпадающий список, куда заносятся разделы
     * @param roots список доступных разделов
     */
    protected void setDriveComboBox(final JComboBox cb, BaseFile[] roots) {
        for (int i = 0; i < roots.length; i++) {
            cb.addItem(roots[i]);
        }
        cb.setMaximumRowCount(roots.length);

        cb.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).setParent(null);

        cb.setKeySelectionManager(new JComboBox.KeySelectionManager() {

            @Override
            public int selectionForKey(char aKey, ComboBoxModel aModel) {
                return cb.getSelectedIndex();
            }
        });
    }

    /**
     * В зависимости от переданного параметра возвращает левый или
     * правый список дисков
     * @param left <b>true</b> для получения левого списка, <b>false</b> -
     * для получения правого
     * @return объект класса JComboBox
     */
    public JComboBox getComboBox(boolean left) {
        return left ? jComboBoxLeft : jComboBoxRight;
    }

    /**
     * Получить активную таблицу
     * @return объект класса FileTable
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

    private void setHotKeysDialog() {
        jDialogHotKeys.setSize(500, 400);
        JTable tb = createHotKeysTable();
        tb.setShowHorizontalLines(false);
        jScrollPaneHelpHK.setViewportView(tb);
    }

    private void setSearchDialog() {
        jDialogFind.setSize(500, 400);
//        jList1.setCellRenderer(new SearchResultListCellRenderer());
        jList1.setModel(new GeneratedListModel());
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
        jComboBoxLang.addItem(new Locale("ru_ru"));
        jComboBoxLang.addItem(new Locale("en_us"));

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
     * Скрыть или вывести на экран панели
     * @param visible <b>true</b> для вывода панелей на экран, <b>false</b> -
     * для скрытия
     */
    public void setPanelsVisible(boolean visible) {
        jSplitPane1.setVisible(visible);
        jScrollPane3.setVisible(!visible);
    }

    /**
     * Определить видимы ли панели
     * @return <b>true</b>, если панели видимы, <b>false</b> - иначе
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
        if (true == left) {
            currentLeftTable.setActive(true);
            currentRightTable.setActive(false);
        } else {
            currentLeftTable.setActive(false);
            currentRightTable.setActive(true);
        }
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
        for (int i = 0; i < favoriteFolders.size(); i++) {
            FavoriteItem fi = (FavoriteItem) favoriteFolders.get(i);
            menuItem = new JMenuItem(fi.getItemName());
            menuItem.addActionListener(new SelectFavoritesListener(fi.getFile()));
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
        for (int i = 0; i < favoriteFolders.size(); i++) {
            FavoriteItem fi = (FavoriteItem) favoriteFolders.get(i);
            menuItem = new JMenuItem(fi.getItemName());
            menuItem.addActionListener(new SelectFavoritesListener(fi.getFile()));
            jMenuFavorites.add(menuItem);
        }
    }

    private void loadFavorites() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(favoritesFile));
            favoriteFolders = new ArrayList();
            int size = ois.readInt();
            FavoriteItem fi;
            for (int i = 0; i < size; i++) {
                String name = ois.readUTF();
                String tmp = ois.readUTF();
                BaseFile af = FileHelper.getRealFile(tmp);
                fi = new FavoriteItem(name, af);
                favoriteFolders.add(fi);
            }
            ois.close();
        } catch (IOException ex) {
            System.out.println("Favorites file not found");
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
            FavoriteItem fi;
            for (int i = 0; i < favoriteFolders.size(); i++) {
                fi = (FavoriteItem) favoriteFolders.get(i);
                String name = fi.getItemName();
                oos.writeUTF(name);
                String path = fi.getFile().getAbsolutePath();
                oos.writeUTF(path);
            }
            oos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

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
        updatePanel(left);
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
        jComboBoxLang.setSelectedItem(currentLocale);
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

    private void startSearchThread(String findWhat, String findWhere, String findText, boolean matchCase) {
        ((GeneratedListModel) jList1.getModel()).clear();
        BaseFile file = FileHelper.getRealFile(findWhere);
        searchThread = new NewSearchThread(file, findWhat, findText, matchCase);
        SearchStatusThread sst =
                new SearchStatusThread(searchThread, jLabelSearchStatus, jList1, jButtonFind);
        sst.start();
        searchThread.start();
    }

    private void stopSearchThread() {
        if (searchThread != null) {
            searchThread.setInterrupt(true);
            while (searchThread.isAlive()) {
            }
        }
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
        jLabelTheme = new javax.swing.JLabel();
        jComboBoxTheme = new javax.swing.JComboBox();
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
        jDialogFind = new javax.swing.JDialog(this);
        jPanelFindButtons = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabelSearchStatus = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanelFindInput = new javax.swing.JPanel();
        jLabelFindWhat = new javax.swing.JLabel();
        jTextFieldFindWhat = new javax.swing.JTextField();
        jLabelFindWhere = new javax.swing.JLabel();
        jTextFieldFindWhere = new javax.swing.JTextField();
        jCheckBoxFindText = new javax.swing.JCheckBox();
        jTextFieldFindText = new javax.swing.JTextField();
        jCheckBoxCase = new javax.swing.JCheckBox();
        jPanelFindResults = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel16 = new javax.swing.JPanel();
        jButtonFind = new javax.swing.JButton();
        jButtonCancelFind = new javax.swing.JButton();
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
        jButton2 = new javax.swing.JButton();
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

        jPanelPrefConsole.setBorder(javax.swing.BorderFactory.createTitledBorder("Консоль"));
        jPanelPrefConsole.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabelPrefCharset.setText("Кодировка");
        jPanelPrefConsole.add(jLabelPrefCharset);

        jComboBoxCharset.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanelPrefConsole.add(jComboBoxCharset);

        jPanel12.add(jPanelPrefConsole);

        jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabelPrefLang.setText("Язык");
        jPanel21.add(jLabelPrefLang);
        jPanel21.add(jComboBoxLang);

        jPanel12.add(jPanel21);

        jPanelEditor.setBorder(javax.swing.BorderFactory.createTitledBorder("Внешний редактор"));
        jPanelEditor.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jCheckBoxEditor.setText("Использовать внешний редактор");
        jCheckBoxEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxEditorActionPerformed(evt);
            }
        });
        jPanelEditor.add(jCheckBoxEditor);

        jPanel23.setEnabled(false);

        jLabelEditor.setText("Редактор:");
        jPanel23.add(jLabelEditor);

        jTextFieldEditor.setPreferredSize(new java.awt.Dimension(240, 25));
        jPanel23.add(jTextFieldEditor);

        jButtonEditor.setText("Обзор...");
        jButtonEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditorActionPerformed(evt);
            }
        });
        jPanel23.add(jButtonEditor);

        jPanelEditor.add(jPanel23);

        jPanel12.add(jPanelEditor);
        jPanel12.add(jPanel22);

        jTabbedPanePrefs.addTab("Главное", jPanel12);

        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanelPrefTheme.setBorder(javax.swing.BorderFactory.createTitledBorder("LookAndFeel"));
        jPanelPrefTheme.setPreferredSize(new java.awt.Dimension(100, 70));
        jPanelPrefTheme.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabelTheme.setText("jLabel2");
        jPanelPrefTheme.add(jLabelTheme);
        jPanelPrefTheme.add(jComboBoxTheme);

        jPanel24.add(jPanelPrefTheme, java.awt.BorderLayout.NORTH);

        jPanelPrefView.setBorder(javax.swing.BorderFactory.createTitledBorder("Отображение"));
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

        jTabbedPanePrefs.addTab("Внешний вид", jPanel24);

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

        jTabbedPanePrefs.addTab("Горячие клавиши", jPanel14);

        jPanel15.add(jTabbedPanePrefs, java.awt.BorderLayout.CENTER);

        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonPrefOk.setText("ОК");
        jButtonPrefOk.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonPrefOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrefOkActionPerformed(evt);
            }
        });
        jPanel9.add(jButtonPrefOk);

        jButtonPrefCancel.setText("Отмена");
        jButtonPrefCancel.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonPrefCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrefCancelActionPerformed(evt);
            }
        });
        jPanel9.add(jButtonPrefCancel);

        jButtonPrefApply.setText("Применить");
        jButtonPrefApply.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonPrefApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrefApplyActionPerformed(evt);
            }
        });
        jPanel9.add(jButtonPrefApply);

        jPanel15.add(jPanel9, java.awt.BorderLayout.SOUTH);

        jDialogPreferences.getContentPane().add(jPanel15);

        jDialogFind.setResizable(false);
        jDialogFind.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                jDialogFindWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jDialogFindWindowClosing(evt);
            }
        });
        jDialogFind.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jDialogFindComponentShown(evt);
            }
        });

        jPanelFindButtons.setLayout(new java.awt.BorderLayout());

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        jLabelSearchStatus.setText("Готово");
        jPanel17.add(jLabelSearchStatus);

        jPanelFindButtons.add(jPanel17, java.awt.BorderLayout.SOUTH);

        jDialogFind.getContentPane().add(jPanelFindButtons, java.awt.BorderLayout.SOUTH);

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanelFindInput.setLayout(new java.awt.GridBagLayout());

        jLabelFindWhat.setText("jLabel2");
        jLabelFindWhat.setPreferredSize(new java.awt.Dimension(50, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 1, 0);
        jPanelFindInput.add(jLabelFindWhat, gridBagConstraints);

        jTextFieldFindWhat.setPreferredSize(new java.awt.Dimension(400, 25));
        jTextFieldFindWhat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldFindWhatKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 7, 0);
        jPanelFindInput.add(jTextFieldFindWhat, gridBagConstraints);

        jLabelFindWhere.setText("jLabel3");
        jLabelFindWhere.setPreferredSize(new java.awt.Dimension(50, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelFindInput.add(jLabelFindWhere, gridBagConstraints);

        jTextFieldFindWhere.setPreferredSize(new java.awt.Dimension(400, 25));
        jTextFieldFindWhere.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldFindWhereKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 7, 0);
        jPanelFindInput.add(jTextFieldFindWhere, gridBagConstraints);

        jCheckBoxFindText.setText("jCheckBox1");
        jCheckBoxFindText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxFindTextActionPerformed(evt);
            }
        });
        jCheckBoxFindText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBoxFindTextKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelFindInput.add(jCheckBoxFindText, gridBagConstraints);

        jTextFieldFindText.setEnabled(false);
        jTextFieldFindText.setPreferredSize(new java.awt.Dimension(400, 25));
        jTextFieldFindText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldFindTextKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 7, 0);
        jPanelFindInput.add(jTextFieldFindText, gridBagConstraints);

        jCheckBoxCase.setText("С учетом регистра");
        jCheckBoxCase.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxCase.setEnabled(false);
        jCheckBoxCase.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxCase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBoxCaseKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 7, 0);
        jPanelFindInput.add(jCheckBoxCase, gridBagConstraints);

        jPanel18.add(jPanelFindInput, java.awt.BorderLayout.NORTH);

        jPanelFindResults.setLayout(new javax.swing.BoxLayout(jPanelFindResults, javax.swing.BoxLayout.LINE_AXIS));

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList1KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jList1);

        jPanelFindResults.add(jScrollPane4);

        jPanel18.add(jPanelFindResults, java.awt.BorderLayout.CENTER);

        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonFind.setText("Поиск");
        jButtonFind.setPreferredSize(new java.awt.Dimension(90, 25));
        jButtonFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindActionPerformed(evt);
            }
        });
        jPanel16.add(jButtonFind);

        jButtonCancelFind.setText("Отмена");
        jButtonCancelFind.setPreferredSize(new java.awt.Dimension(90, 25));
        jButtonCancelFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelFindActionPerformed(evt);
            }
        });
        jPanel16.add(jButtonCancelFind);

        jPanel18.add(jPanel16, java.awt.BorderLayout.SOUTH);

        jDialogFind.getContentPane().add(jPanel18, java.awt.BorderLayout.CENTER);

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

        jButtonDeleteFavorite.setText("Удалить");
        jButtonDeleteFavorite.setPreferredSize(new java.awt.Dimension(90, 25));
        jButtonDeleteFavorite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteFavoriteActionPerformed(evt);
            }
        });
        jPanel20.add(jButtonDeleteFavorite);

        jButtonHideDeleteFavoriteDialog.setText("Закрыть");
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

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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
        jButtonFavorities1.setToolTipText("Закладки");
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

        jPanelStatusMsgLeft.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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
        jButtonFavorities2.setToolTipText("Закладки");
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

        jPanelStatusMsgRight.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        jButtonMove.setFont(new java.awt.Font("MS Sans Serif", 1, 9)); // NOI18N
        jButtonMove.setFocusable(false);
        jButtonMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMoveActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonMove);

        jButtonCreateDir.setFont(new java.awt.Font("MS Sans Serif", 1, 9)); // NOI18N
        jButtonCreateDir.setFocusable(false);
        jButtonCreateDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateDirActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonCreateDir);

        jButtonDel.setFont(new java.awt.Font("MS Sans Serif", 1, 9)); // NOI18N
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
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanelToolBar.add(jButton1);

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanelToolBar.add(jButton2);

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
            FavoriteItem fi = (FavoriteItem) jListFavorites.getModel().getElementAt(indices[i]);
            if (favoriteFolders.contains(fi)) {
                favoriteFolders.remove(fi);
            }
        }
        jListFavorites.setListData(favoriteFolders.toArray());
        initFavorites();
    }//GEN-LAST:event_jButtonDeleteFavoriteActionPerformed

    private void jDialogFindWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialogFindWindowActivated
        jTextFieldFindWhere.setText("" + getActiveTable().getCurrentDir());
    }//GEN-LAST:event_jDialogFindWindowActivated

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (2 == evt.getClickCount()) {
            BaseFile af = (BaseFile) jList1.getSelectedValue();
            if (af != null) {
                closeFindDialog();
                moveToFile(af);
            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jTextFieldFindWhereKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFindWhereKeyPressed
        findDialogKeyPressed(evt);
    }//GEN-LAST:event_jTextFieldFindWhereKeyPressed

    private void jTextFieldFindWhatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFindWhatKeyPressed
        findDialogKeyPressed(evt);
    }//GEN-LAST:event_jTextFieldFindWhatKeyPressed

    private void jButtonCancelFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelFindActionPerformed
        closeFindDialog();
    }//GEN-LAST:event_jButtonCancelFindActionPerformed

    private void jList1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            closeFindDialog();
            return;
        }
        Message msg = findMessage(evt);
        BaseFile af = (BaseFile) jList1.getSelectedValue();
        if (msg != null && af != null) {
            if (msg.getMessageID() == MessageList.MSG_ENTER) {
                closeFindDialog();
                moveToFile(af);
            }
            if (msg.getMessageID() == MessageList.MSG_VIEW) {
                viewFile(af);
            }
        }
    }//GEN-LAST:event_jList1KeyPressed

    private void jButtonFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindActionPerformed
        if (null != searchThread) {
            if (searchThread.isAlive()) {
                stopSearchThread();
                return;
            }
        }
        String what = jTextFieldFindWhat.getText().trim();
        String where = jTextFieldFindWhere.getText().trim();
        if (where.equals("")) {
            return;
        }
        String text = "";
        if (jCheckBoxFindText.isSelected()) {
            text = jTextFieldFindText.getText();
        }
        startSearchThread(what, where, text, jCheckBoxCase.isSelected());
        jButtonFind.setText(lb.getString("StrStop"));
    }//GEN-LAST:event_jButtonFindActionPerformed

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

    private void jComboBoxRightPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxRightPopupMenuWillBecomeVisible
        BaseFile[] roots = RootFileSystem.getRoots();
        rightComboBoxIndex = jComboBoxRight.getSelectedIndex();
        Object obj = jComboBoxRight.getSelectedItem();
        jComboBoxRight.removeAllItems();
        setDriveComboBox(jComboBoxRight, roots);
        jComboBoxRight.setSelectedItem(obj);
        jComboBoxRight.setMaximumRowCount(roots.length);
    }//GEN-LAST:event_jComboBoxRightPopupMenuWillBecomeVisible

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
                    getActiveTable().setFileList(af);
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
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_jTextField3KeyPressed

    private void jComboBoxLeftPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBoxLeftPopupMenuWillBecomeVisible
        BaseFile[] roots = RootFileSystem.getRoots();
        leftComboBoxIndex = jComboBoxLeft.getSelectedIndex();
        Object obj = jComboBoxLeft.getSelectedItem();
        jComboBoxLeft.removeAllItems();
        setDriveComboBox(jComboBoxLeft, roots);
        jComboBoxLeft.setSelectedItem(obj);
        jComboBoxLeft.setMaximumRowCount(roots.length);
    }//GEN-LAST:event_jComboBoxLeftPopupMenuWillBecomeVisible

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
        currentRightTable.setFileList(new FileSystemList(root));
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
        currentLeftTable.setFileList(new FileSystemList(root));
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

private void jDialogFindWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialogFindWindowClosing
    if (searchThread != null) {
        stopSearchThread();
    }
}//GEN-LAST:event_jDialogFindWindowClosing

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

private void jCheckBoxFindTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFindTextActionPerformed
    jTextFieldFindText.setEnabled(jCheckBoxFindText.isSelected());
    jCheckBoxCase.setEnabled(jCheckBoxFindText.isSelected());
    if (jCheckBoxFindText.isSelected()) {
        jTextFieldFindText.requestFocus();
    }
}//GEN-LAST:event_jCheckBoxFindTextActionPerformed

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

private void jTextFieldFindTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFindTextKeyPressed
    findDialogKeyPressed(evt);
}//GEN-LAST:event_jTextFieldFindTextKeyPressed

private void jCheckBoxCaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBoxCaseKeyPressed
    findDialogKeyPressed(evt);
}//GEN-LAST:event_jCheckBoxCaseKeyPressed

private void jCheckBoxFindTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBoxFindTextKeyPressed
    findDialogKeyPressed(evt);
}//GEN-LAST:event_jCheckBoxFindTextKeyPressed

private void jDialogFindComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jDialogFindComponentShown
    jTextFieldFindWhat.requestFocus();
}//GEN-LAST:event_jDialogFindComponentShown

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
}//GEN-LAST:event_jButton2ActionPerformed
// </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc=" Variables declaration ">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonCancelFind;
    private javax.swing.JButton jButtonCopy;
    private javax.swing.JButton jButtonCreateDir;
    private javax.swing.JButton jButtonDel;
    private javax.swing.JButton jButtonDeleteFavorite;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JButton jButtonEditor;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonFavorities1;
    private javax.swing.JButton jButtonFavorities2;
    private javax.swing.JButton jButtonFind;
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
    private javax.swing.JCheckBox jCheckBoxCase;
    private javax.swing.JCheckBox jCheckBoxEditor;
    private javax.swing.JCheckBox jCheckBoxFindText;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemArrangement;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemButtonBar;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemCommandLine;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemHiddenFiles;
    private javax.swing.JCheckBox jCheckBoxPrefShowButtons;
    private javax.swing.JCheckBox jCheckBoxPrefShowCmdline;
    private javax.swing.JCheckBox jCheckBoxPrefShowHidden;
    private javax.swing.JCheckBox jCheckBoxPrefShowTooltips;
    private javax.swing.JComboBox jComboBoxCharset;
    private javax.swing.JComboBox jComboBoxLang;
    private javax.swing.JComboBox jComboBoxLeft;
    private javax.swing.JComboBox jComboBoxRight;
    private javax.swing.JComboBox jComboBoxTheme;
    private javax.swing.JDialog jDialogAbout;
    private javax.swing.JDialog jDialogDeleteFavorites;
    private javax.swing.JDialog jDialogFileProperties;
    private javax.swing.JDialog jDialogFind;
    private javax.swing.JDialog jDialogHotKeys;
    private javax.swing.JDialog jDialogPreferences;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelCopyright;
    private javax.swing.JLabel jLabelCurrentVersion;
    private javax.swing.JLabel jLabelEditor;
    private javax.swing.JLabel jLabelFindWhat;
    private javax.swing.JLabel jLabelFindWhere;
    private javax.swing.JLabel jLabelMail;
    private javax.swing.JLabel jLabelPage;
    private javax.swing.JLabel jLabelPrefCharset;
    private javax.swing.JLabel jLabelPrefLang;
    private javax.swing.JLabel jLabelSearchStatus;
    private javax.swing.JLabel jLabelStatusLeft;
    private javax.swing.JLabel jLabelStatusRight;
    private javax.swing.JLabel jLabelTheme;
    private javax.swing.JList jList1;
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
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel26;
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
    private javax.swing.JPanel jPanelFindButtons;
    private javax.swing.JPanel jPanelFindInput;
    private javax.swing.JPanel jPanelFindResults;
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
    private javax.swing.JScrollPane jScrollPane4;
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
    private javax.swing.JTextField jTextFieldFindText;
    private javax.swing.JTextField jTextFieldFindWhat;
    private javax.swing.JTextField jTextFieldFindWhere;
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
    private static final String JCOM_COPYRIGHT = "2005-2010 (c) kneeMade, Inc";
    private static final String JCOM_MAIL = "java.commander@gmail.com";
    private static final String JCOM_PAGE = "http://jcommander.narod.ru";
    private static Charset consoleCharset;
    private ArrayList commandList = new ArrayList();
    private int commandListPosition;
    private NewSearchThread searchThread;
    JCPreferenses jcPrefs = new JCPreferenses() {
    };
    private ArrayList favoriteFolders = new ArrayList();
    private Locale currentLocale = null;
    LanguageBundle lb = LanguageBundle.getInstance();
    JPopupMenu splitterPopupMenu;

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
                e.printStackTrace();
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
                    ex.printStackTrace();
                }
            }
            jTextArea1.append(getActiveTable().getCurrentDir().getAbsolutePath() + ">");
        }
    }

    private class FavoriteItem {

        private String itemName;
        private BaseFile file;

        public FavoriteItem(String itemName, BaseFile file) {
            this.itemName = itemName;
            this.file = file;
        }

        public BaseFile getFile() {
            return file;
        }

        public String getItemName() {
            return itemName;
        }

        @Override
        public String toString() {
            return itemName;// + " ==> " + file.getAbsolutePath();
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
            FavoriteItem fi = new FavoriteItem(result, af);
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

    class SearchResultListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            Component retValue = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            if (value instanceof BaseFile) {
                BaseFile file = (BaseFile) value;
                ImageIcon icon = null;
                switch (FileHelper.getFileType(file)) {
                    case DIRECTORY:
                        icon = ImageArchive.getImageFolder();
                        break;
                    case ARCHIVE:
                        icon = ImageArchive.getImageArchive();
                        break;
                    default:
                        icon = ImageArchive.getImageFile();
                        break;
                }
                setIcon(icon);
            }
            return retValue;
        }
    }

    public class GeneratedListModel extends AbstractListModel {

        private final Vector data = new Vector();

        public GeneratedListModel() {
        }

        private void update() {
            this.fireContentsChanged(this, 0, getSize());
        }

        private void update(int start, int end) {
            this.fireIntervalAdded(this, start, end);
        }

        public void addData(Vector v) {
            data.addAll(v);
            update(getSize() - v.size(), getSize());
        }

        @Override
        public int getSize() {
            return data.size();
        }

        @Override
        public Object getElementAt(int index) {
            return data.elementAt(index);
        }

        public void sortByPath() {
            Collections.sort(data);
            update();
        }

        public void clear() {
            data.clear();
            update();
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

// </editor-fold>
}

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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.narod.jcommander.prefs;

import java.awt.Dimension;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.prefs.Preferences;
import ru.narod.jcommander.JCLogger;
import ru.narod.jcommander.gui.MainFrame;
import ru.narod.jcommander.gui.table.Directive;
import ru.narod.jcommander.gui.table.FileTable;
import ru.narod.jcommander.gui.table.tableHeader.TableHeader;

/**
 *
 * @author Programmer
 */
public class JCPreferenses implements PrefKeys {

    public static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);
    public static final Point DEFAULT_LOCATION = new Point(100, 100);
    public static final int DEFAULT_STATE = 0;
    public static final int DEFAULT_DIVIDER_LOCATION = DEFAULT_WINDOW_SIZE.width / 2;
    public static final String DEFAULT_LEFT_PANEL_PATH = "";
    public static final String DEFAULT_RIGHT_PANEL_PATH = "";
    public static final int DEFAULT_ACTIVE_TABLE = 0;
    public static final int DEFAULT_HEADER_SIZE = 50;
    public static final String DEFAULT_CONSOLE_CHARSET = "";
    public static final boolean DEFAULT_SHOW_BUTTONS = true;
    public static final boolean DEFAULT_SHOW_COMMAND_LINE = true;
    public static final boolean DEFAULT_SHOW_HIDDEN_FILES = true;
    public static final boolean DEFAULT_USE_EXTERN_EDITOR = false;
    public static final String DEFAULT_EXTERN_EDITOR = "";
    public static final String DEFAULT_THEME = "javax.swing.plaf.metal.MetalLookAndFeel";
    public static final boolean DEFAULT_SHOW_TOOLTIPS = true;
    public static final boolean DEFAULT_ARRANGEMENT = false;
    public static final boolean DEFAULT_USE_SYSTEM_ICONS = true;
    public static final Directive DEFAULT_DIRECTIVE = new Directive(1, 1);
    public static final int DEFAULT_QUICK_SEARCH_MODE = FileTable.QUICK_SEARCH_FROM_BEGINING;
    public static final boolean DEFAULT_ICON_COLUMN_VISIBLE = true;
    public static final boolean DEFAULT_NAME_COLUMN_VISIBLE = true;
    public static final boolean DEFAULT_EXT_COLUMN_VISIBLE = true;
    public static final boolean DEFAULT_SIZE_COLUMN_VISIBLE = true;
    public static final boolean DEFAULT_DATE_COLUMN_VISIBLE = true;
    public static final boolean DEFAULT_ATTR_COLUMN_VISIBLE = true;
    private static JCPreferenses instance = new JCPreferenses();
    public static final String PRIMARY_DELIMITER = ";";
    public static final String SECONDARY_DELIMITER = ",";
    public Dimension windowSize = new Dimension();
    public Point location = new Point();
    public int extendedState = 0;
    public int dividerLocation = 0;
    public String leftPanelPath = "";
    public String rightPanelPath = "";
    public int activeTable = 0;
    public String consoleCharset = "";
    public boolean showButtons = true;
    public boolean showCommandLine = true;
    public boolean showHiddenFiles = true;
    public boolean useExternEditor = false;
    public String externEditor = "";
    public String theme = "";
    public boolean showToolTips = true;
    public boolean arrangement = false;
    public boolean useSystemIcons = true;
    public String leftHeaderSizes = "";
    public String rightHeaderSizes = "";
    public String leftSortingColumns = "";
    public String rightSortingColumns = "";
    public String leftHeaderSizesCompatible = "";
    public String rightHeaderSizesCompatible = "";
    public String leftSortingCompatible = "";
    public String rightSortingCompatible = "";
    public String currentLocale = "";
    public int quickSearchMode = 0;
    public boolean iconColumnVisible = true;
    public boolean nameColumnVisible = true;
    public boolean extColumnVisible = true;
    public boolean sizeColumnVisible = true;
    public boolean dateColumnVisible = true;
    public boolean attrColumnVisible = true;

    private JCPreferenses() {
    }

    public static JCPreferenses getJCPreferenses() {
        return instance;
    }
    private Preferences pref = null;

    public void loadPrefs() {
        try {
            Preferences.importPreferences(new FileInputStream(new java.io.File("jc.xml")));
        } catch (Exception ex) {
            JCLogger.logSevere(null, ex);
        } finally {
            if (null == pref) {
                pref = Preferences.userNodeForPackage(MainFrame.class);
            }
        }

        windowSize.width = pref.getInt(PK_WIDTH, JCPreferenses.DEFAULT_WINDOW_SIZE.width);
        windowSize.height = pref.getInt(PK_HEIGHT, JCPreferenses.DEFAULT_WINDOW_SIZE.height);
        location.x = pref.getInt(PK_LOCATION_X, JCPreferenses.DEFAULT_LOCATION.x);
        location.y = pref.getInt(PK_LOCATION_Y, JCPreferenses.DEFAULT_LOCATION.y);
        extendedState = pref.getInt(PK_WINDOW_STATE, JCPreferenses.DEFAULT_STATE);
        dividerLocation = pref.getInt(PK_DIVIDER_LOCATION, JCPreferenses.DEFAULT_DIVIDER_LOCATION);
        leftPanelPath = pref.get(PK_LEFT_DIR, JCPreferenses.DEFAULT_LEFT_PANEL_PATH);
        rightPanelPath = pref.get(PK_RIGHT_DIR, JCPreferenses.DEFAULT_RIGHT_PANEL_PATH);

        activeTable = pref.getInt(PK_ACTIVE_TABLE, JCPreferenses.DEFAULT_ACTIVE_TABLE);

        leftHeaderSizes = pref.get(PK_LEFT_SIZE, "");
        rightHeaderSizes = pref.get(PK_RIGHT_SIZE, "");

        leftSortingColumns = pref.get(PK_LEFT_SORT, "");
        rightSortingColumns = pref.get(PK_RIGHT_SORT, "");

        for (int i = 0; i < TableHeader.TITLE.length; i++) {
            leftHeaderSizesCompatible += pref.get(PK_LEFT[i], "") + JCPreferenses.PRIMARY_DELIMITER;
        }
        for (int i = 0; i < TableHeader.TITLE.length; i++) {
            rightHeaderSizesCompatible += pref.get(PK_RIGHT[i], "") + JCPreferenses.PRIMARY_DELIMITER;
        }
        leftSortingCompatible = pref.get(PK_LEFT_SORTER, "") + JCPreferenses.SECONDARY_DELIMITER + pref.get(PK_LEFT_SORT_DIRECTION, "");
        rightSortingCompatible = pref.get(PK_RIGHT_SORTER, "") + JCPreferenses.SECONDARY_DELIMITER + pref.get(PK_RIGHT_SORT_DIRECTION, "");

        if (leftHeaderSizes.isEmpty()) {
            leftHeaderSizes = leftHeaderSizesCompatible;
        }
        if (rightHeaderSizes.isEmpty()) {
            rightHeaderSizes = rightHeaderSizesCompatible;
        }
        if (leftSortingColumns.isEmpty()) {
            leftSortingColumns = leftSortingCompatible;
        }
        if (rightSortingColumns.isEmpty()) {
            rightSortingColumns = rightSortingCompatible;
        }

        consoleCharset = pref.get(PK_CONSOLE_CHARSET, "");
        showButtons = pref.getBoolean(PK_SHOW_BUTTONS, JCPreferenses.DEFAULT_SHOW_BUTTONS);
        showCommandLine = pref.getBoolean(PK_SHOW_COMMAND_LINE, JCPreferenses.DEFAULT_SHOW_COMMAND_LINE);
        showHiddenFiles = pref.getBoolean(PK_SHOW_HIDDEN_FILES, showHiddenFiles);

        useExternEditor = pref.getBoolean(PK_USE_EXTERNAL_EDITOR, JCPreferenses.DEFAULT_USE_EXTERN_EDITOR);
        externEditor = pref.get(PK_EXTERNAL_EDITOR, JCPreferenses.DEFAULT_EXTERN_EDITOR);

        theme = pref.get(PK_THEME, JCPreferenses.DEFAULT_THEME);
        showToolTips = pref.getBoolean(PK_SHOW_TOOLTIPS, JCPreferenses.DEFAULT_SHOW_TOOLTIPS);
        arrangement = pref.getBoolean(PK_ARRANGEMENT, JCPreferenses.DEFAULT_ARRANGEMENT);
        useSystemIcons = pref.getBoolean(PK_USE_SYSTEM_ICONS, JCPreferenses.DEFAULT_USE_SYSTEM_ICONS);
        Locale defaultLocale = Locale.getDefault();
        currentLocale = pref.get(PK_LOCALE, defaultLocale.getLanguage() + "_" + defaultLocale.getCountry());
        quickSearchMode = pref.getInt(PK_QUICK_SEARCH_MODE, JCPreferenses.DEFAULT_QUICK_SEARCH_MODE);
        
        iconColumnVisible = pref.getBoolean(PK_ICON_VISIBLE, JCPreferenses.DEFAULT_ICON_COLUMN_VISIBLE);
        nameColumnVisible = pref.getBoolean(PK_NAME_VISIBLE, JCPreferenses.DEFAULT_NAME_COLUMN_VISIBLE);
        extColumnVisible = pref.getBoolean(PK_EXT_VISIBLE, JCPreferenses.DEFAULT_EXT_COLUMN_VISIBLE);
        sizeColumnVisible = pref.getBoolean(PK_SIZE_VISIBLE, JCPreferenses.DEFAULT_SIZE_COLUMN_VISIBLE);
        dateColumnVisible = pref.getBoolean(PK_DATE_VISIBLE, JCPreferenses.DEFAULT_DATE_COLUMN_VISIBLE);
        attrColumnVisible = pref.getBoolean(PK_ATTR_VISIBLE, JCPreferenses.DEFAULT_ATTR_COLUMN_VISIBLE);
    }

    private void saveTablePrefs(boolean left) {
        pref.put(left ? PK_LEFT_DIR : PK_RIGHT_DIR, left ? leftPanelPath : rightPanelPath);
        pref.put(left ? PK_LEFT_SIZE : PK_RIGHT_SIZE, left ? leftHeaderSizes : rightHeaderSizes);
        pref.put(left ? PK_LEFT_SORT : PK_RIGHT_SORT, left ? leftSortingColumns : rightSortingColumns);
    }

    public void savePrefs() {
        pref.putInt(PK_HEIGHT, windowSize.height);
        pref.putInt(PK_WIDTH, windowSize.width);
        pref.putInt(PK_DIVIDER_LOCATION, dividerLocation);
        pref.putInt(PK_WINDOW_STATE, extendedState);

        pref.putInt(PK_LOCATION_X, location.x);
        pref.putInt(PK_LOCATION_Y, location.y);
        pref.putInt(PK_ACTIVE_TABLE, activeTable);

        saveTablePrefs(true);
        saveTablePrefs(false);

        pref.put(PK_LOCALE, currentLocale);

        pref.put(PK_CONSOLE_CHARSET, consoleCharset);
        pref.putBoolean(PK_SHOW_BUTTONS, showButtons);
        pref.putBoolean(PK_SHOW_COMMAND_LINE, showCommandLine);
        pref.putBoolean(PK_SHOW_HIDDEN_FILES, showHiddenFiles);

        pref.put(PK_EXTERNAL_EDITOR, externEditor);
        pref.putBoolean(PK_USE_EXTERNAL_EDITOR, useExternEditor);

        pref.put(PK_THEME, theme);

        pref.putBoolean(PK_SHOW_TOOLTIPS, showToolTips);
        pref.putBoolean(PK_ARRANGEMENT, arrangement);
        pref.putBoolean(PK_USE_SYSTEM_ICONS, useSystemIcons);
        pref.putInt(PK_QUICK_SEARCH_MODE, quickSearchMode);
        try {
            pref.exportNode(new FileOutputStream(new java.io.File("jc.xml")));
        } catch (Exception ex) {
            JCLogger.logSevere(null, ex);
        }
    }
}

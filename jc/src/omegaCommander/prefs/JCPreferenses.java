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
package omegaCommander.prefs;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import omegaCommander.gui.table.tableHeader.TableHeader;

/**
 *
 * @author Programmer
 */
public class JCPreferenses {

    public final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);
    public final Point DEFAULT_LOCATION = new Point(100, 100);
    public final int DEFAULT_STATE = 0;
    public final int DEFAULT_DIVIDER_LOCATION = DEFAULT_WINDOW_SIZE.width / 2;
    public final String DEFAULT_LEFT_PANEL_PATH = "";
    public final String DEFAULT_RIGHT_PANEL_PATH = "";
    public final int DEFAULT_ACTIVE_TABLE = 0;
    public final int DEFAULT_HEADER_SIZE = 50;
    public final String DEFAULT_CONSOLE_CHARSET = "";
    public final boolean DEFAULT_SHOW_BUTTONS = true;
    public final boolean DEFAULT_SHOW_COMMAND_LINE = true;
    public final boolean DEFAULT_SHOW_HIDDEN_FILES = true;
    public final boolean DEFAULT_USE_EXTERN_EDITOR = false;
    public final String DEFAULT_EXTERN_EDITOR = "";
    public final String DEFAULT_THEME = "javax.swing.plaf.metal.MetalLookAndFeel";
    public final boolean DEFAULT_SHOW_TOOLTIPS = true;
    public final boolean DEFAULT_ARRANGEMENT = false;
    public final boolean DEFAULT_USE_SYSTEM_ICONS = true;
    public Dimension windowSize = new Dimension();
    public Point location = new Point();
    public int extendedState = 0;
    public int dividerLocation = 0;
    public String leftPanelPath = "";
    public String rightPanelPath = "";
    public int activeTable = 0;
    public int leftSizes[] = new int[TableHeader.TITLE.length];
    public int rightSizes[] = new int[TableHeader.TITLE.length];
    public ArrayList leftSortingColumns = new ArrayList();
    public ArrayList rightSortingColumns = new ArrayList();
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
    private static JCPreferenses instance = new JCPreferenses();

    private JCPreferenses() {
    }

    public static JCPreferenses getJCPreferenses() {
        return instance;
    }
}

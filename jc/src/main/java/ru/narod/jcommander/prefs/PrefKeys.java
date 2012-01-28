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
 * PrefKeys.java
 *
 * Created on 10 jan 2007, 10:42
 *
 */
package ru.narod.jcommander.prefs;

/**
 *
 * @author Programmer
 */
public interface PrefKeys {

    public final String PK_WIDTH = "PK_WIDTH";
    public final String PK_HEIGHT = "PK_HEIGHT";
    public final String PK_WINDOW_STATE = "PK_WINDOW_STATE";
    public final String PK_DIVIDER_LOCATION = "PK_DIVIDER_LOCATION";
    public final String PK_LOCATION_X = "PK_LOCATION_X";
    public final String PK_LOCATION_Y = "PK_LOCATION_Y";
    public final String PK_LEFT_DIR = "PK_LEFT_DIR";
    public final String PK_RIGHT_DIR = "PK_RIGHT_DIR";
    public final String PK_ACTIVE_TABLE = "PK_ACTIVE_TABLE";
    public final String PK_LOCALE = "PK_LOCALE";
    public final String PK_CONSOLE_CHARSET = "PK_CONSOLE_CHARSET";
    public final String PK_SHOW_COMMAND_LINE = "PK_SHOW_COMMAND_LINE";
    public final String PK_SHOW_BUTTONS = "PK_SHOW_BUTTONS";
    public final String PK_SHOW_HIDDEN_FILES = "PK_SHOW_HIDDEN_FILES";
    public final String PK_USE_EXTERNAL_EDITOR = "PK_USE_EXTERNAL_EDITOR";
    public final String PK_EXTERNAL_EDITOR = "PK_EXTERNAL_EDITOR";
    public final String PK_THEME = "PK_THEME";
    public final String PK_SHOW_TOOLTIPS = "PK_SHOW_TOOLTIPS";
    public final String PK_ARRANGEMENT = "PK_ARRANGEMENT";
    public final String PK_USE_SYSTEM_ICONS = "PK_USE_SYSTEM_ICONS";
    public final String PK_QUICK_SEARCH_MODE = "PK_QUICK_SEARCH_MODE";
    public final String PATH_TO_LANGUAGE = "ru/narod/jcommander/resources/lang";
    public final String PK_LEFT_SORT = "LEFT_SORT";
    public final String PK_RIGHT_SORT = "RIGHT_SORT";
    public final String PK_LEFT_SIZE = "LEFT_SIZE";
    public final String PK_RIGHT_SIZE = "RIGHT_SIZE";
    String PK_LEFT[] = {
        "ICON_LEFT",
        "NAME_LEFT",
        "EXTENTION_LEFT",
        "SIZE_LEFT",
        "DATE_LEFT",
        "ATTRIBUTE_LEFT"
    };
    String PK_RIGHT[] = {
        "ICON_RIGHT",
        "NAME_RIGHT",
        "EXTENTION_RIGHT",
        "SIZE_RIGHT",
        "DATE_RIGHT",
        "ATTRIBUTE_RIGHT"
    };
    public String PK_LEFT_SORTER = "LEFT_SORTER";
    public String PK_RIGHT_SORTER = "RIGHT_SORTER";
    public String PK_LEFT_SORT_DIRECTION = "RIGHT_LEFT_DIRECTION";
    public String PK_RIGHT_SORT_DIRECTION = "RIGHT_SORT_DIRECTION";
}

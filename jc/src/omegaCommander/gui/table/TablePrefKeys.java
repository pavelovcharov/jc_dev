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
 * TablePrefKeys.java
 *
 * Created on 10 январь 2007 г., 11:32
 *
 */

package omegaCommander.gui.table;

/**
 *
 * @author Programmer
 */
public interface TablePrefKeys {
    public String TPK_ICON_LEFT = "ICON_LEFT";
    public String TPK_NAME_LEFT = "NAME_LEFT";
    public String TPK_EXTENTION_LEFT = "EXTENTION_LEFT";
    public String TPK_SIZE_LEFT = "SIZE_LEFT";
    public String TPK_DATE_LEFT = "DATE_LEFT";
    public String TPK_ATTRIBUTE_LEFT = "ATTRIBUTE_LEFT";
    
    String TPK_LEFT[] = {
        "ICON_LEFT",
        "NAME_LEFT",
        "EXTENTION_LEFT",
        "SIZE_LEFT",
        "DATE_LEFT",
        "ATTRIBUTE_LEFT"
    };

    String TPK_RIGHT[] = {
        "ICON_RIGHT",
        "NAME_RIGHT",
        "EXTENTION_RIGHT",
        "SIZE_RIGHT",
        "DATE_RIGHT",
        "ATTRIBUTE_RIGHT"
    };

    public String TPK_ICON_RIGHT = "ICON_RIGHT";
    public String TPK_NAME_RIGHT = "NAME_RIGHT";
    public String TPK_EXTENTION_RIGHT = "EXTENTION_RIGHT";
    public String TPK_SIZE_RIGHT = "SIZE_RIGHT";
    public String TPK_DATE_RIGHT = "DATE_RIGHT";
    public String TPK_ATTRIBUTE_RIGHT = "ATTRIBUTE_RIGHT";
    
    public String TPK_LEFT_SORTER = "LEFT_SORTER";
    public String TPK_RIGHT_SORTER = "RIGHT_SORTER";
    public String TPK_LEFT_SORT_DIRECTION = "RIGHT_LEFT_DIRECTION";
    public String TPK_RIGHT_SORT_DIRECTION = "RIGHT_SORT_DIRECTION";
}

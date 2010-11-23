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
 * EditorPrefs.java
 *
 * Created on 12 Май 2007 г., 19:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package omegaCommander.editor;

/**
 *
 * @author Pavel Ovcharov
 */
interface EditorPrefs {
    public static String P_POS_X = "POS_X";
    public static String P_POS_Y = "POS_Y";
    public static String P_WIDTH = "WIDTH";
    public static String P_HEIGHT = "HEIGHT";
    public static String P_STATE = "STATE";
    public static String P_CHARSET = "CHARSET";
	public static String P_SCALE = "SCALE";
    public static int p_POS_X = 0;
    public static int p_POS_Y = 0;
    public static int p_WIDTH = 300;
    public static int p_HEIGHT = 500;
    public static int p_STATE = 0;
	public static int p_SCALE = 1;
    //public static String p_CHARSET = "windows-1251";
}

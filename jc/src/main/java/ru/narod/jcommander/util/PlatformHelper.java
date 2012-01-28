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
package ru.narod.jcommander.util;

/**
 *
 * @author master
 */
public class PlatformHelper {

    public static final String DEFAULT_HANDLER_TEXT;

    static {
        if (System.getProperty("os.name").startsWith("Windows")) {
            DEFAULT_HANDLER_TEXT = "rundll32 url.dll,FileProtocolHandler";
        } else {
            DEFAULT_HANDLER_TEXT = "";
        }
    }
}

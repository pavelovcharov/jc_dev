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
 * Support.java
 *
 * Created on 5 Декабрь 2006 г., 12:37
 *
 */
package omegaCommander.util;

/**
 *
 * @author Programmer
 */
public abstract class Support {

    public static String getStringRelativeTo(String sourceStr, String relativeStr) {
        if (!sourceStr.startsWith(relativeStr)) {
            return "";
        }
        return sourceStr.substring(relativeStr.length());
    }
}

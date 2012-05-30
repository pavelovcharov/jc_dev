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
 * Date.java
 *
 * Created on 25 jun 2007, 10:14
 *
 */
package ru.narod.jcommander.gui.table.tableElements;

import java.text.SimpleDateFormat;
import ru.narod.jcommander.fileSystem.BaseFile;

/**
 *
 * @author Programmer
 * @version
 */
public class Date extends Element {

    private long time;
    private String dateStr;
    static private final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy HH:mm";
    static private SimpleDateFormat sdate = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    /**
     * Creates a new instance of Date
     */
    public Date(BaseFile aFile) {
        java.util.Date date = new java.util.Date();
        time = aFile.getLastModifiedTime();
        date.setTime(time);
        dateStr = sdate.format(date);
    }

    public Date(String date) {
        this.dateStr = date;
    }

    @Override
    public String toString() {
        return dateStr;
    }

    public int compareTo(Object o) {
        if (!(o instanceof Date)) {
            return 1;
        }
        Date d = (Date) o;
        if (time > d.time) {
            return 1;
        }
        if (time < d.time) {
            return -1;
        }
        return 0;
    }
}
